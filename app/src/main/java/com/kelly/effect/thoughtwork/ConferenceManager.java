package com.kelly.effect.thoughtwork;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * author: zongkaili
 * data: 2018/9/27
 * desc:
 */
public class ConferenceManager {

    /**
     * obtain talk list and schedule conference.
     *
     * @param inputStr
     * @throws InvalidTalkException
     */
    public List<List<Talk>> scheduleConference(String inputStr) throws Exception {
        List<String> talkList = getTalkList(inputStr);
        return scheduleConference(talkList);
    }

    /**
     * create and schedule conference.
     *
     * @param talkList
     * @throws InvalidTalkException
     */
    public List<List<Talk>> scheduleConference(List<String> talkList) throws Exception {
        List<Talk> talksList = validateAndCreateTalksList(talkList);
        return getScheduleConferenceTrack(talksList);
    }

    /**
     * Read every talk from input string then put it in list.
     *
     * @param testStr
     * @return
     * @throws InvalidTalkException
     */
    public List<String> getTalkList(String testStr) throws InvalidTalkException {
        List<String> talkList = new ArrayList<>();
        if (testStr == null) {
            return talkList;
        }
        try {
            //read every line of testStr
            InputStream in = new ByteArrayInputStream(testStr.getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = br.readLine();
            while (strLine != null) {
                talkList.add(strLine);
                strLine = br.readLine();
            }

            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return talkList;
    }

    /**
     * Validate talk list, check the time for talk and initialize Talk Object accordingly.
     *
     * @param talkList
     * @throws Exception
     */
    private List<Talk> validateAndCreateTalksList(List<String> talkList) throws Exception {
        if (talkList == null) {
            throw new InvalidTalkException("Talk list is empty!");
        }

        List<Talk> validTalksList = new ArrayList<>();
        String minSuffix = "min";
        String lightningSuffix = "lightning";

        // Iterate list and validate time.
        for (String talk : talkList) {
            int lastSpaceIndex = talk.lastIndexOf(" ");
            // if talk does not have any space, means either title or time is missing.
            if (lastSpaceIndex == -1) {
                throw new InvalidTalkException("Invalid talk, " + talk + ". Talk time must be specify.");
            }

            String name = talk.substring(0, lastSpaceIndex);
            String timeStr = talk.substring(lastSpaceIndex + 1);
            // If title is missing or blank.
            if ("".equals(name.trim())) {
                throw new InvalidTalkException("Invalid talk name, " + talk);
            }
            // If time is not ended with min or lightning.
            else if (!timeStr.endsWith(minSuffix) && !timeStr.endsWith(lightningSuffix)) {
                throw new InvalidTalkException("Invalid talk time, " + talk + ". Time must be in min or in lightning");
            }

            int time = 0;
            // Parse time from the time string .
            try {
                if (timeStr.endsWith(minSuffix)) {
                    time = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(minSuffix)));
                }
                else if (timeStr.endsWith(lightningSuffix)) {
                    time = 5;
                }
            } catch (NumberFormatException e) {
                throw new InvalidTalkException("Unable to parse time " + timeStr + " for talk " + talk);
            }

            // Add talk to the valid talk List.
            validTalksList.add(new Talk(talk, name, time));
        }

        return validTalksList;
    }

    /**
     * Schedule Conference tracks for morning and evening session.
     *
     * @param talksList
     * @throws Exception
     */
    private List<List<Talk>> getScheduleConferenceTrack(List<Talk> talksList) throws Exception {
        // Find the total possible days.
        int minTimePerDay = 6 * 60;
        int totalTalksTime = getTotalTalksTime(talksList);
        int totalPossibleDays = totalTalksTime / minTimePerDay;

        // Sort the talkList.
        List<Talk> talksListForOperation = new ArrayList<Talk>();
        talksListForOperation.addAll(talksList);
        Collections.sort(talksListForOperation);

        // Find possible combinations for the morning session.
        List<List<Talk>> combForMornSessions = findPossibleCombSession(talksListForOperation, totalPossibleDays, true);

        // Remove all the scheduled talks for morning session, from the operationList.
        for (List<Talk> talkList : combForMornSessions) {
            talksListForOperation.removeAll(talkList);
        }

        // Find possible combinations for the afternoon session.
        List<List<Talk>> combForAfternoonSessions = findPossibleCombSession(talksListForOperation, totalPossibleDays, false);

        // Remove all the scheduled talks for evening session, from the operationList.
        for (List<Talk> talkList : combForAfternoonSessions) {
            talksListForOperation.removeAll(talkList);
        }

        // check if the operation list is not empty, then try to fill all the remaining talks in evening session.
        int maxSessionTimeLimit = 4 * 60;
        if (!talksListForOperation.isEmpty()) {
            List<Talk> scheduledTalkList = new ArrayList<Talk>();
            for (List<Talk> talkList : combForAfternoonSessions) {
                int totalTime = getTotalTalksTime(talkList);

                for (Talk talk : talksListForOperation) {
                    int talkTime = talk.getDuration();

                    if (talkTime + totalTime <= maxSessionTimeLimit) {
                        talkList.add(talk);
                        talk.setScheduled(true);
                        scheduledTalkList.add(talk);
                    }
                }

                talksListForOperation.removeAll(scheduledTalkList);
                if (talksListForOperation.isEmpty()) {
                    break;
                }
            }
        }

        // If operation list is still not empty, its mean the conference can not be scheduled with the provided data.
        if (!talksListForOperation.isEmpty()) {
            throw new Exception("Unable to schedule all task for conferencing.");
        }

        // Schedule the day event from morning session and evening session.
        return getScheduledTalksList(combForMornSessions, combForAfternoonSessions);
    }

    /**
     * Find possible combination for the session.
     * If morning session then each session must have total time 3 hr.
     * if afternoon session then each session must have total time greater then 3 hr.
     *
     * @param talksListForOperation
     * @param totalPossibleDays
     * @param morningSession
     * @return
     */
    private List<List<Talk>> findPossibleCombSession(List<Talk> talksListForOperation, int totalPossibleDays, boolean morningSession) {
        //a.m. 9:00——12:00 so min is 3 hours
        int minSessionTimeLimit = 3 * 60;
        //p.m. 13:00——17：00  so max is 4 hours
        int maxSessionTimeLimit = 4 * 60;

        if (morningSession) {
            maxSessionTimeLimit = minSessionTimeLimit;
        }

        int talkListSize = talksListForOperation.size();
        List<List<Talk>> possibleCombinationsOfTalks = new ArrayList<List<Talk>>();
        int possibleCombinationCount = 0;

        // Loop to get combination for total possible days.
        // Check one by one from each talk to get possible combination.
        for (int count = 0; count < talkListSize; count++) {
            int startPoint = count;
            int totalTime = 0;
            List<Talk> possibleCombinationList = new ArrayList<Talk>();

            // Loop to get possible combination.
            while (startPoint != talkListSize) {
                int currentCount = startPoint;
                startPoint++;
                Talk currentTalk = talksListForOperation.get(currentCount);
                if (currentTalk.isScheduled()) {
                    continue;
                }
                int talkTime = currentTalk.getDuration();
                // If the current talk time is greater than maxSessionTimeLimit or
                // sum of the current time and total of talk time added in list  is greater than maxSessionTimeLimit.
                // then continue.
                if (talkTime > maxSessionTimeLimit || talkTime + totalTime > maxSessionTimeLimit) {
                    continue;
                }

                possibleCombinationList.add(currentTalk);
                totalTime += talkTime;

                // If total time is completed for this session then break this loop.
                if (morningSession) {
                    if (totalTime == maxSessionTimeLimit) {
                        break;
                    }
                }
                else if (totalTime >= minSessionTimeLimit) {
                    break;
                }
            }

            // Valid session time for morning session is equal to maxSessionTimeLimit.
            // Valid session time for evening session is less than or eqaul to maxSessionTimeLimit and greater than or equal to minSessionTimeLimit.
            boolean validSession = false;
            if (morningSession) {
                validSession = (totalTime == maxSessionTimeLimit);
            }
            else {
                validSession = (totalTime >= minSessionTimeLimit && totalTime <= maxSessionTimeLimit);
            }

            // If session is valid than add this session in the possible combination list and set all added talk as scheduled.
            if (validSession) {
                possibleCombinationsOfTalks.add(possibleCombinationList);
                for (Talk talk : possibleCombinationList) {
                    talk.setScheduled(true);
                }
                possibleCombinationCount++;
                if (possibleCombinationCount == totalPossibleDays) {
                    break;
                }
            }
        }

        return possibleCombinationsOfTalks;
    }

    /**
     * Print the scheduled talks with the expected text msg.
     *
     * @param combForMornSessions
     * @param combForEveSessions
     */
    private List<List<Talk>> getScheduledTalksList(List<List<Talk>> combForMornSessions, List<List<Talk>> combForEveSessions) {
        List<List<Talk>> scheduledTalksList = new ArrayList<List<Talk>>();
        int totalPossibleDays = combForMornSessions.size();

        // for loop to schedule event for all days.
        for (int dayCount = 0; dayCount < totalPossibleDays; dayCount++) {
            List<Talk> talkList = new ArrayList<Talk>();

            // Create a date and initialize start time 09:00 AM.
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma ");
            date.setHours(9);
            date.setMinutes(0);
            date.setSeconds(0);

            int trackCount = dayCount + 1;
            String scheduledTime = dateFormat.format(date);

            System.out.println("Track " + trackCount + ":");

            // Morning Session - set the scheduled time in the talk and get the next time using time duration of current talk.
            List<Talk> mornSessionTalkList = combForMornSessions.get(dayCount);
            for (Talk talk : mornSessionTalkList) {
                talk.setScheduledTime(scheduledTime);
                System.out.println(scheduledTime + talk.getTitle());
                scheduledTime = getNextScheduledTime(date, talk.getDuration());
                talkList.add(talk);
            }

            // Scheduled Lunch Time for 60 mins.
            int lunchTimeDuration = 60;
            Talk lunchTalk = new Talk("Lunch", "Lunch", 60);
            lunchTalk.setScheduledTime(scheduledTime);
            talkList.add(lunchTalk);
            System.out.println(scheduledTime + "Lunch");

            // Evening Session - set the scheduled time in the talk and get the next time using time duration of current talk.
            scheduledTime = getNextScheduledTime(date, lunchTimeDuration);
            List<Talk> eveSessionTalkList = combForEveSessions.get(dayCount);
            for (Talk talk : eveSessionTalkList) {
                talk.setScheduledTime(scheduledTime);
                talkList.add(talk);
                System.out.println(scheduledTime + talk.getTitle());
                scheduledTime = getNextScheduledTime(date, talk.getDuration());
            }

            // Scheduled Networking Event at the end of session, Time duration is just to initialize the Talk object.
            Talk networkingTalk = new Talk("Networking Event", "Networking Event", 60);
            networkingTalk.setScheduledTime(scheduledTime);
            talkList.add(networkingTalk);
//            System.out.println(scheduledTime + "Networking Event\n");
            System.out.println("05:00PM " + "Networking Event\n");
            scheduledTalksList.add(talkList);
        }

        return scheduledTalksList;
    }

    /**
     * To get total time of talks of the given list.
     *
     * @param talksList
     * @return
     */
    public static int getTotalTalksTime(List<Talk> talksList) {
        if (talksList == null || talksList.isEmpty()) {
            return 0;
        }

        int totalTime = 0;
        for (Talk talk : talksList) {
            totalTime += talk.duration;
        }
        return totalTime;
    }

    /**
     * To get next scheduled time in form of String.
     *
     * @param date
     * @param timeDuration
     * @return
     */
    private String getNextScheduledTime(Date date, int timeDuration) {
        long timeInLong = date.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma ");

        long timeDurationInLong = timeDuration * 60 * 1000;
        long newTimeInLong = timeInLong + timeDurationInLong;

        date.setTime(newTimeInLong);
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * class Talk, to store and retrieve information about talk.
     * implements comparable to sort talk on the basis of time duration.
     * eg: Writing Fast Tests Against Enterprise Rails 60min
     */
    public static class Talk implements Comparable {
        /**
         * talk title.
         * eg: Writing Fast Tests Against Enterprise Rails 60min
         */
        String title;
        /**
         * talk name.
         * eg: Writing Fast Tests Against Enterprise Rails
         */
        String name;
        /**
         * duration of talk.
         * eg: 60
         */
        int duration;

        boolean scheduled = false;
        String scheduledTime;

        public Talk(String title, String name, int time) {
            this.title = title;
            this.name = name;
            this.duration = time;
        }

        /**
         * To set the flag scheduled.
         *
         * @param scheduled
         */
        public void setScheduled(boolean scheduled) {
            this.scheduled = scheduled;
        }

        /**
         * To get flag scheduled.
         * If talk is scheduled then returns true, else false.
         *
         * @return
         */
        public boolean isScheduled() {
            return scheduled;
        }

        /**
         * Set scheduled time for the talk. in format - hr:min AM/PM.
         *
         * @param scheduledTime
         */
        public void setScheduledTime(String scheduledTime) {
            this.scheduledTime = scheduledTime;
        }

        /**
         * To get scheduled time.
         *
         * @return
         */
        public String getScheduledTime() {
            return scheduledTime;
        }

        /**
         * To get duration for the talk.
         *
         * @return
         */
        public int getDuration() {
            return duration;
        }

        /**
         * To get the title of the talk.
         *
         * @return
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sort data in descending order.
         *
         * @param obj
         * @return
         */
        @Override
        public int compareTo(Object obj) {
            Talk talk = (Talk) obj;
            if (this.duration > talk.duration) {
                return -1;
            }
            else if (this.duration < talk.duration) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }
}

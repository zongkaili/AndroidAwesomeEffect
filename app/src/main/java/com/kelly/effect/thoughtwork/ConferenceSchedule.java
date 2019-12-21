package com.kelly.effect.thoughtwork;

/**
 * author: zongkaili
 * data: 2018/9/28
 * desc:
 */
public class ConferenceSchedule {

    public static void main(String[] args) {
        String testInputStr = "Writing Fast Tests Against Enterprise Rails 60min\n" +
                "Overdoing it in Python 45min\n" +
                "Lua for the Masses 30min\n" +
                "Ruby Errors from Mismatched Gem Versions 45min\n" +
                "Common Ruby Errors 45min\n" +
                "Rails for Python Developers lightning\n" +
                "Communicating Over Distance 60min\n" +
                "Accounting-Driven Development 45min\n" +
                "Woah 30min\n" +
                "Sit Down and Write 30min\n" +
                "Pair Programming vs Noise 45min\n" +
                "Rails Magic 60min\n" +
                "Ruby on Rails: Why We Should Move On 60min\n" +
                "Clojure Ate Scala (on my project) 45min\n" +
                "Programming in the Boondocks of Seattle 30min\n" +
                "Ruby vs. Clojure for Back-End Development 30min\n" +
                "Ruby on Rails Legacy App Maintenance 60min\n" +
                "A World Without HackerNews 30min\n" +
                "User Interface CSS in Rails Apps 30min";
        ConferenceManager conferenceManager = new ConferenceManager();
        try {
            conferenceManager.scheduleConference(testInputStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

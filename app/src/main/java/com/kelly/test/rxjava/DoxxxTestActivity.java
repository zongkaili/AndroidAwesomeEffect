package com.kelly.test.rxjava;

import android.annotation.SuppressLint;
import android.app.Activity;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

/**
 * author: zongkaili
 * data: 2019-09-15
 * desc:
 */
public class DoxxxTestActivity {
    public static void main(String[] arg) {
//         doXxx();
        coldObservable();
    }

    @SuppressLint("CheckResult")
    private static void doXxx() {
        Observable.just("Hello")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(" doOnNext : " + s);
                    }
                })
                .doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(" doAfterNext : " + s);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println(" doOnComplete : ");
                    }
                })
                //订阅之后回调的方法
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println(" doOnSubscribe : ");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println(" doAfterTerminate : ");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println(" doFinally : ");
                    }
                })
                //Observable每发射一个数据就会触发这个回调，不仅包括onNext,还包括onError和onCompleted
                .doOnEach(new Consumer<Notification<String>>() {
                    @Override
                    public void accept(Notification<String> stringNotification) throws Exception {
                        System.out.println(" doOnEach : " + (stringNotification.isOnNext() ? "onNext"
                                : stringNotification.isOnComplete() ? "onComplete" : "onError"));
                    }
                })
                //订阅后可以取消订阅
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println(" doOnLifecycle : " + disposable.isDisposed());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println(" doOnLifecycle run : ");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(" 收到消息 ： " + s);
                    }
                });
    }

    private static void coldObservable() {
        Consumer<Long> subscriber1 = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println(" subscriber1 : " + aLong);
            }
        };
        Consumer<Long> subscriber2 = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("  subscriber2 : " + aLong);
            }
        };
        Consumer<Long> subscriber3 = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("   subscriber3 : " + aLong);
            }
        };
        ConnectableObservable<Long> connectableObservable = (ConnectableObservable<Long>) Observable.create(new ObservableOnSubscribe<Long>() {
            @SuppressLint("CheckResult")
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(emitter::onNext);
            }
        }).observeOn(Schedulers.newThread()).publish();
        connectableObservable.connect();

//        PublishSubject<Long> subject = PublishSubject.create();
//        observable.subscribe(subject);

        //refCount用法
        Observable<Long> observable = connectableObservable.refCount();

        Disposable disposable1 = observable.subscribe(subscriber1);
        Disposable disposable2 = observable.subscribe(subscriber2);
        observable.subscribe(subscriber3);

        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        disposable1.dispose();
        disposable2.dispose();

        System.out.println("disposable1,disposable2 重新订阅");

        disposable1 = observable.subscribe(subscriber1);
        disposable2 = observable.subscribe(subscriber2);

        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}

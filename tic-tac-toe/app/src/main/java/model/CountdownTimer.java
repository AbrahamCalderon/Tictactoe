package model;

import android.os.CountDownTimer;

/**
 * Created by ismaelz on 4/16/14.
 */
public class CountdownTimer extends CountDownTimer{

    static long secondsUntilFinishaed;


        public CountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


    @Override
    public void onFinish() {



    }

    @Override
    public void onTick(long millisUntilFinished) {

        secondsUntilFinishaed = millisUntilFinished;

    }

    }

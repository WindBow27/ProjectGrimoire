package org.graphic;

public class TimerThread extends MatchingGameController implements Runnable {
    @Override
    public void run() {
        timer();
    }

    public void timer() {
        startTime = (double) System.currentTimeMillis() / 1000;
        while(startTimer) {
            endTime = (double) System.currentTimeMillis() / 1000;
            message.setText(String.valueOf(endTime - startTime));
        }
    }
}

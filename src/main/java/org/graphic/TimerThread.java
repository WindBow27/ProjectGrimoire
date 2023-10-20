package org.graphic;

import javafx.scene.control.Label;

public class TimerThread extends MatchingGameController implements Runnable {
    @Override
    public void run() {
        timer();
    }

    public synchronized void timer() {
        System.out.println("Timer is running");
        Label message = this.message;
        startTime = (double) System.currentTimeMillis() / 1000;
        while(startTimer) {
            endTime = (double) System.currentTimeMillis() / 1000;
            message.setText(String.valueOf(endTime - startTime));
        }
    }
}

package org.graphic;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.graphic.controller.MatchingGameController;

import java.util.Timer;
import java.util.TimerTask;

public class TimerThread extends MatchingGameController implements Runnable {
    private Timer timer;
    private long startTime;
    private Label label;

    public TimerThread(Label timerLabel) {
        this.timer = new Timer();
        this.startTime = System.currentTimeMillis();
        this.label = timerLabel;
    }

    @Override
    public void run() {
        // Schedule a task to be executed every second.
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Update the timer.
                double elapsedTime = System.currentTimeMillis() - startTime;

                // Display the timer on the screen.
                Platform.runLater(() -> {
                    label.setText(String.format("%.1f", elapsedTime / 1000));
                });
            }
        }, 0, 1);
    }
}

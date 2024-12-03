/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacerunner;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;
import java.util.Random;

/**
 *
 * @author Hp
 */

public class Star extends ObjekLangit implements RoleGame{
    private double velocityY = 3;
    private AnimationTimer timer;
    private AnchorPane pane;
    private boolean isActive = true;

    public Star(AnchorPane pane) {
        super("/spacerunner/img/Star.png", new Random().nextInt(1222), -100, 70, 70);
        this.pane = pane;
        pane.getChildren().add(getGambar());
        startGameLoop();
    }
    @Override
    public void updateY() {
        if (!isActive) return;
        
        double newY = this.getGambar().getLayoutY() + velocityY;
        this.getGambar().setLayoutY(newY);
        
        if (isOffScreen()) {
            removeAlien();
        }
    }

    public boolean isOffScreen() {
        return this.getGambar().getLayoutY() > pane.getHeight();
    }

    private void removeAlien() {
        if (!isActive) return;
        
        isActive = false;
        pane.getChildren().remove(getGambar());
        stopGameLoop();
    }

    private void startGameLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateY();
            }
        };
        timer.start();
    }

    private void stopGameLoop() {
        if (timer != null) {
            timer.stop();
        }
    }
    @Override
    public void remove() {
        pane.getChildren().remove(getGambar());
    }
}

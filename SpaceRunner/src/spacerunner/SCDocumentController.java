/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package spacerunner;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SCDocumentController implements Initializable {
    @FXML private ImageView pesawat;
    @FXML private AnchorPane ruang;
    
    private double velocityX = 0;
    private double velocityY = 0;    
    private final double damping = 0.95;
    private final double speed = 16.0;
    private Timeline gameLoop;
    private Timeline alienSpawner;
    private Timeline starSpawner;
    private List<Alien> aliens = new ArrayList<>();
    private List<Star> Stars = new ArrayList<>();
    private boolean gameOver = false;
    private int score = 0;
    private int nyawa = 3;

    @FXML
    private Text currentScore;
    @FXML
    private Text intitialGame;
    @FXML
    private ImageView nyawa1;
    @FXML
    private ImageView nyawa2;
    @FXML
    private ImageView nyawa3;
    @FXML
    private Text jumlahAlienSpawn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setupGameControls();
            startGameLoop();
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
        }
    }

    private void setupGameControls() {
        ruang.setOnKeyPressed(this::gerakPesawat);
        ruang.setOnKeyReleased(this::handleKeyRelease);
        ruang.setFocusTraversable(true);
        ruang.requestFocus();
        ruang.setOnMouseClicked(this::triggerPesawat);
        currentScore.setVisible(false);
        jumlahAlienSpawn.setVisible(false);
        pesawat.setVisible(false);
        nyawa1.setVisible(false);
        nyawa2.setVisible(false);
        nyawa3.setVisible(false);
    }
    private void startGameLoop() {
        try {
            gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> {
                try {
                    updateGame();
                } catch (Exception ex) {
                    System.err.println("Error in game loop: " + ex.getMessage());
                }
            }));
            gameLoop.setCycleCount(Timeline.INDEFINITE);
            gameLoop.play();
        } catch (Exception e) {
            System.err.println("Failed to start game loop: " + e.getMessage());
        }
    }

}
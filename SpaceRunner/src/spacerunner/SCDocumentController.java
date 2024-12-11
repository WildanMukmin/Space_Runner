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

    private void startAlienSpawner() {
        alienSpawner = new Timeline(new KeyFrame(Duration.seconds(1), e -> spawnAlien()));
        alienSpawner.setCycleCount(Timeline.INDEFINITE);
        alienSpawner.play();
    }

    private void startStarSpawner() {
        starSpawner = new Timeline(new KeyFrame(Duration.seconds(4), e -> spawnStar()));
        starSpawner.setCycleCount(Timeline.INDEFINITE);
        starSpawner.play();
    }

    private void updateGame() {
        if (gameOver) return;
        try {
            // Update ship position
            double newX = pesawat.getLayoutX() + velocityX;
            double newY = pesawat.getLayoutY() + velocityY;
    
            // Boundary checking for ship
            newX = Math.max(0, Math.min(ruang.getWidth() - pesawat.getFitWidth(), newX));
            newY = Math.max(0, Math.min(ruang.getHeight() - pesawat.getFitHeight(), newY));
    
            pesawat.setLayoutX(newX);
            pesawat.setLayoutY(newY);
    
            // Apply damping
            velocityX *= damping;
            velocityY *= damping;
    
            // Check collisions
            checkCollisions();
    
            // Clean up off-screen aliens
            aliens.removeIf(alien -> alien.isOffScreen());
        } catch (Exception e) {
            System.err.println("Error updating game: " + e.getMessage());
        }
    }

    private void checkCollisions() {
        try {
            // Check collisions with aliens
            for (Alien alien : new ArrayList<>(aliens)) {
                if (alien.getGambar().getBoundsInParent().intersects(pesawat.getBoundsInParent())) {
                    alien.remove();
                    aliens.remove(alien);
    
                    // Decrease life
                    nyawa--;
                    switch (nyawa) {
                        case 2:
                            nyawa3.setVisible(false);
                            break;
                        case 1:
                            nyawa2.setVisible(false);
                            break;
                        case 0:
                            nyawa1.setVisible(false);
                            ruang.getChildren().remove(pesawat);
                            currentScore.setText("GAME OVER SCORE " + score);
                            currentScore.setLayoutY(currentScore.getLayoutY() + 150);
                            currentScore.setLayoutX(currentScore.getLayoutX() - 35);
                            gameOver();
                            break;
                    }
                    break;
                }
            }

            // Check collisions with stars
            for (Star star : new ArrayList<>(Stars)) {
                if (star.getGambar().getBoundsInParent().intersects(pesawat.getBoundsInParent())) {
                    star.remove();
                    Stars.remove(star);
                    score++;
                    currentScore.setText("Score: " + score);
                }
            }
        } catch (Exception e) {
            System.err.println("Error during collision check: " + e.getMessage());
        }
    }

    private void gameOver() {
        gameOver = true;
        gameLoop.stop();
        alienSpawner.stop();
        // Add game over UI logic here
    }

    private void spawnAlien() {
        if (gameOver) return;
        try {
            Alien alien = new Alien(ruang);
            aliens.add(alien);
            jumlahAlienSpawn.setText("Jumlah Alien Spawn: " + Alien.getJumlahAlien());
        } catch (Exception e) {
            System.err.println("Error spawning alien: " + e.getMessage());
        }
    }

    private void spawnStar() {
        if (gameOver) return;
        try {
            Star star = new Star(ruang);
            Stars.add(star);
        } catch (Exception e) {
            System.err.println("Error spawning star: " + e.getMessage());
        }
    }

    @FXML
    private void triggerPesawat(MouseEvent event) {
        try {
            ruang.requestFocus();
            startAlienSpawner();
            startStarSpawner();
            currentScore.setVisible(true);
            intitialGame.setVisible(false);
            jumlahAlienSpawn.setVisible(true);
            pesawat.setVisible(true);
            if (nyawa == 3) {
                nyawa1.setVisible(true);
                nyawa2.setVisible(true);
                nyawa3.setVisible(true);
            }
        } catch (Exception e) {
            System.err.println("Error triggering spaceship: " + e.getMessage());
        }
    }
}

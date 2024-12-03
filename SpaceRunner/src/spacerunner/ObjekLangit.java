/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacerunner;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ObjekLangit {
    private ImageView gambar;
    private double x;
    private double y;

    public ObjekLangit(String imagePath, double startX, double startY, double width, double height) {
        this.gambar = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        this.x = startX;
        this.y = startY;

        gambar.setLayoutX(x);
        gambar.setLayoutY(y);
        gambar.setFitWidth(width);
        gambar.setFitHeight(height);
        gambar.setPreserveRatio(true);
    }

    public ImageView getGambar() {
        return gambar;
    }
}
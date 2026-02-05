package Model;

import javafx.scene.image.Image;

import java.io.File;

public class colorandimage {
    private String color;
    private String imagePath;
    private Image image;

    public colorandimage(String color, String imagePath) {
        this.color = color;
        this.imagePath = imagePath;
        this.image = new Image(new File(imagePath).toURI().toString());
    }

    public String getColor() { return color; }
    public Image getImage() { return image; }
    public String getImagePath() { return imagePath; }
}

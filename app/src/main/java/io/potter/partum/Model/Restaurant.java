package io.potter.partum.Model;

public class Restaurant {
    private String Name;
    private  String Image;

    public Restaurant() {
    }

    public Restaurant(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}

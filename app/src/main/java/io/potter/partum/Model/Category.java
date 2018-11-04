package io.potter.partum.Model;

public class Category {

    private String Name;
    private  String Image;
    private  String RestId;

    public Category() {
    }

    public Category(String name, String image, String restid) {
        Name = name;
        Image = image;
        RestId = restid;
    }

    public String getRestId() {
        return RestId;
    }

    public void setRestId(String restId) {
        RestId = restId;
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

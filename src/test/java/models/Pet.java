package models;

public class Pet {
    Integer id;
    Category category;
    String name;
    Tags tags;
    PhotoUrls photoUrls;
    Status status;

    public Pet(Integer id, Category category, String name, Tags tag, PhotoUrls photoUrls, Status status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.tags = tag;
        this.photoUrls = photoUrls;
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id +
                ", \"category\": " + category +
                ", \"name\": \"" + name + "\"" +
                ", \"tags\": " + tags +
                ", \"photoUrls\": " + photoUrls +
                ", \"status\": \"" + status +
                "\"}";
    }
}

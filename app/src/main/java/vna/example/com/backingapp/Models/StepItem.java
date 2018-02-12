package vna.example.com.backingapp.Models;

/**
 * Created by Google       Company on 05/12/2017.
 */

public class StepItem {

    String       id;
    String       shortDescription;
    String       description;
    String       videoURL;
    String     thumbnailURL;

    public StepItem(String id, String shortDescription, String description, String videoURL,String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL=thumbnailURL;
    }


    public String getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }


}

package vna.example.com.backingapp;

/**
 * Created by Google       Company on 05/12/2017.
 */

class StepItem {

    String       id;
    String       shortDescription;
    String       description;
    String       videoURL;

    public StepItem(String id, String shortDescription, String description, String videoURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
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
}

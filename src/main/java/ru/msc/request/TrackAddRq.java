package ru.msc.request;

import java.util.Date;

public class TrackAddRq {
    private String title;

    private String filepath;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

}

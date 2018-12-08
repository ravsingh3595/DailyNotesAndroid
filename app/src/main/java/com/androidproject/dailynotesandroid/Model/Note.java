package com.androidproject.dailynotesandroid.Model;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

    private int noteId;
    private String noteTitle;
    private String noteContent;
    private String audio;
    private Date dateTime;              //check for data type for time stamp
    private float latitude;
    private float longitude;
    private int imageId;

    public Note(){

    }

    public Note(int noteId, String noteTitle, String noteContent, String audio, Date dateTime, float latitude, float longitude, int imageId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.audio = audio;
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageId = imageId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

package com.androidproject.dailynotesandroid.Model;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

    private String subjectName;
    private int noteId;
    private String noteTitle;
    private String noteContent;
    private String audio;
    private String dateTime;              //check for data type for time stamp
    private double latitude;
    private double longitude;
    private int imageId;

    public Note(){

    }

    public Note(String noteTitle, String noteContent) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public Note(String subjectName, int noteId, String noteTitle, String noteContent, String audio, String dateTime, double latitude, double longitude, int imageId) {
        this.subjectName = subjectName;
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.audio = audio;
        this.dateTime = dateTime; // sonia change
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageId = imageId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {

        this.dateTime = dateTime;
//        this.dateTime = new Date(); // sonia change
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

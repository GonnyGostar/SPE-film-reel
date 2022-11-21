package com.nulight.FilmTool.film;
public class Resolution {
    int ID;
    String name;
    int pixelsPerFrame;

    public Resolution(){

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPixelsPerFrame() {
        return pixelsPerFrame;
    }

    public void setPixelsPerFrame(int pixelsPerFrame) {
        this.pixelsPerFrame = pixelsPerFrame;
    }
}

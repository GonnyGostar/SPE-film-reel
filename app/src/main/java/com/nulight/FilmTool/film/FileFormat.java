package com.nulight.FilmTool.film;

public class FileFormat {
    int ID;
    String name;
    float bytesPerPixel;

    public FileFormat(){
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

    public float getBytesPerPixel() {
        return bytesPerPixel;
    }

    public void setBytesPerPixel(float bytesPerPixel) {
        this.bytesPerPixel = bytesPerPixel;
    }
}


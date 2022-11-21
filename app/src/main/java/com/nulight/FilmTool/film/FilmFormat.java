package com.nulight.FilmTool.film;

public class FilmFormat {
    int ID;
    String name;
    double framesPerFoot;
    double thicknessOfFilm;

    public FilmFormat() {
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

    public double getFramesPerFoot() {
        return framesPerFoot;
    }

    public void setFramesPerFoot(double framesPerFoot) {
        this.framesPerFoot = framesPerFoot;
    }

    public double getThicknessOfFilm() {
        return thicknessOfFilm;
    }

    public void setThicknessOfFilm(double thicknessOfFilm) {
        this.thicknessOfFilm = thicknessOfFilm;
    }
}

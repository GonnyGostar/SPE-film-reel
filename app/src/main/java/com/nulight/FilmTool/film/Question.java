package com.nulight.FilmTool.film;

public class Question {
    int id;
    String label;
    String imgDir;
    String infoDir;
    Question yes;
    Question no;
    Question prev;

    Question(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }

    public void setYes(Question yes) {
        this.yes = yes;
    }

    public void setNo(Question no) {
        this.no = no;
    }

    public void setPrev(Question prev) {
        this.prev = prev;
    }

    public void setInfoDir(String infoDir) {
        this.infoDir = infoDir;
    }

    public Question getYes() {
        return yes;
    }

    public Question getNo() {
        return no;
    }

    public Question getPrev() {
        return prev;
    }

    public int getId() {
        return id;
    }

    public String getImgDir() {
        return imgDir;
    }

    public String getInfoDir() {
        return infoDir;
    }

    public String getLabel() {
        return label;
    }
}

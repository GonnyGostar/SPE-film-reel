package com.nulight.FilmTool.film;

import android.content.Context;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FilmReel {
    double reelDiameter;
    double coreDiameter;
    double framesPerSecond;
    double reelLength;
    int runtime;
    double fileSize;
    //TODO switch to enum
    Boolean unit;

    List<FilmFormat> filmFormatList;
    FilmFormat filmFormat;
    Map<Integer, FilmFormat> filmFormatMap;

    Resolution resolution;
    List<Resolution> resolutionList;
    Map<Integer, Resolution> resolutionMap;

    FileFormat fileFormat;
    List<FileFormat> fileFormatList;
    Map<Integer, FileFormat> fileFormatMap;

    List<Double> frameRateList;
    int frameRateID;

    //Film reel object stores all variables needed to calculate the formulas.
    //Is passed context so that it can access the XML files.
    public FilmReel(Context context) {
        XMLReader xmlReader = new XMLReader();

        //False = imperial true = metric
        this.setUnit(false);


        this.setFilmFormatList(xmlReader.getFilmFormats(context));
        this.setFrameRateList(xmlReader.getFrameRates(context));
        this.setFileFormatList(xmlReader.getFileFormats(context));
        this.setResolutionList(xmlReader.getResolutions(context));
        this.setFilmFormat(0);
        this.setReelDiameter(0);
        this.setCoreDiameter(0);
        this.setFileFormat(0);
        this.setResolution(0);
        // This is in seconds
        this.setRuntime(0);
        this.setFileSize(0);
    }

    //Formula 1.
    public double calculateReelLength(){
        //L = ( pi * (rd2 – cd2) /(4 * tof * mmf) ) * mtf
        double reelLength;
        if (getUnit()){
            //convert to mm
            double rd = this.getReelDiameter() * 25.4;
            double cd = this.getCoreDiameter() * 25.4;
            reelLength = ( (Math.PI * ( Math.pow(rd, 2) - Math.pow(cd,2)) / (4 * this.getThickness() * 0.0254)) * (1/304.8));
            reelLength = Math.round(reelLength * 100.0);
            reelLength = reelLength / 100;
        }
        else{
            reelLength = ( (Math.PI * ( Math.pow( this.getReelDiameter(), 2) - Math.pow(this.getCoreDiameter(),2)) / (4 * this.getThickness() * 0.0254)) * (1/304.8));
            reelLength = Math.round(reelLength * 100.0);
            reelLength = reelLength / 100;
        }

        if (reelLength > 0){
            return reelLength;
        }
        else{
            return 0;
        }
    }

    //Formula 2.
    public double calculateRuntime(){
        //R = L / (fps * 1/fpf)
        double runtime = (this.getReelLength()) / (this.getFramesPerSecond() * (1/this.getFramesPerFoot()));
        this.setRuntime((int)runtime);
        return runtime;

    }

    //Formula 3
    public double calculateReelLengthFromRuntime(){
        //L = R * fps * fpf (???)
        // Note - the document sent says R * fps * fpf, but it makes more sense for it to be 1/fpf...
        double reelLength = this.getRuntime() * this.getFramesPerSecond() * (1/this.getFramesPerFoot());
        reelLength = Math.round(reelLength * 100.0);
        reelLength = reelLength / 100;
        this.setReelLength(reelLength);
        return reelLength;
    }

    //Formula 4
    public double calculateFileSizeFromRuntime(){
        //F=fps * R * bpp * ppf * btg
        BigDecimal fileSize = BigDecimal.valueOf(this.getFramesPerSecond() * this.getRuntime() * this.getFileFormat().getBytesPerPixel() * this.getResolution().getPixelsPerFrame());
        fileSize = fileSize.divide(BigDecimal.valueOf(1073741824));
        System.out.println(fileSize);
        double fileSize2 = Math.round(fileSize.doubleValue() * 100.0);
        fileSize2 = fileSize2 / 100;
        this.setFileSize(fileSize2);
        return fileSize2;
    }

    //ToDo sort this out its not right.
    public int calculateRuntimeFromFileSize(){
        //R = F / (fps x bpp x ppf x btg)
        double denom = this.getFramesPerSecond() * this.getFileFormat().getBytesPerPixel() * this.getResolution().getPixelsPerFrame();
        denom = denom / 1073741824;
        double r = this.getFileSize() / denom;

        int runtime = (int) r;

        this.setRuntime(runtime);

        return runtime;


    }



    public double getReelDiameter() {
        return reelDiameter;
    }

    public void setReelDiameter(double reelDiameter) {
        // If in inches, we're going to want it to not round - people will use decimals
        if (!getUnit()) {
            this.reelDiameter = Math.round(reelDiameter);
        }
        else {
            this.reelDiameter = reelDiameter;
        }
    }

    public double getCoreDiameter() {
        return coreDiameter;
    }

    public void setCoreDiameter(double coreDiameter) {
        // If in inches, we're going to want it to not round - people will use decimals
        if (!getUnit()) {
            this.coreDiameter = Math.round(coreDiameter);
        }
        else {
            this.coreDiameter = coreDiameter;
        }
    }

    public double getReelLength() {
        return reelLength;
    }

    public void setReelLength(double reelLength) {
        this.reelLength = reelLength;
    }

    public int getRuntime() { return runtime;}

    public void setRuntime(int runtime) {this.runtime = runtime;}

    public double getFramesPerSecond() {
        return framesPerSecond;
    }

    public void setFramesPerSecond(int index) {
        this.framesPerSecond = frameRateList.get(index);
    }

    public void setFrameRateID(int index) {this.frameRateID = index;}

    public int getFrameRateID() {return frameRateID;}

    public List<Double> getFrameRateList() { return frameRateList;}

    // This is always going to be smallest to biggest
    public void setFrameRateList(List<Double> frameRateList) {
        Comparator<Double> doubleComparator = new Comparator<Double>()
        {
            @Override
            public int compare(Double o1, Double o2)
            {
                return Double.compare(o1, o2);
            }
        };
        Collections.sort(frameRateList, doubleComparator);
        this.frameRateList = frameRateList;
    }

    public List<String> getFrameRateArray(){
        List<String> array = new ArrayList<>();
        for (int i = 0; i < this.frameRateList.size(); i++){
            String f = String.valueOf(this.frameRateList.get(i));
            array.add(f);
        }
        return array;
    }

    public List<FilmFormat> getFilmFormatList() {
        return filmFormatList;
    }

    //Updating list recreates film format map aswell.
    public void setFilmFormatList(List<FilmFormat> filmFormatList) {
        this.filmFormatList = filmFormatList;
        setFilmFormatMap(filmFormatList);
    }

    //Film format map allows for conversion from the ID of a filFormat to the filmFormat object.
    private void setFilmFormatMap(List<FilmFormat> filmFormatList) {
        Map<Integer, FilmFormat> filmFormatMap = new HashMap<>();
        for (int i = 0; i < filmFormatList.size(); i++) {
            FilmFormat f = filmFormatList.get(i);
            filmFormatMap.put(f.getID(), f);
        }
        this.filmFormatMap = filmFormatMap;
    }

    public List<String> getFilmFormatArray(){
        List<String> array = new ArrayList<>();
        for (int i = 0; i < this.filmFormatList.size(); i++){
            FilmFormat f = this.filmFormatList.get(i);
            array.add(f.getName());
        }
        return array;
    }


    //Set film format is passed index from UI which is same as ID of filmformat object
    //the Id is the key in the map.
    public void setFilmFormat(int index){
        this.filmFormat = this.filmFormatMap.get(index);
    }

    public FilmFormat getFilmFormat(){
        return filmFormat;
    }

    //Returns thickness of current film format
    public double getThickness(){
        return this.getFilmFormat().getThicknessOfFilm();
    }

    //Returns fpf of current film format
    public double getFramesPerFoot(){
        return this.getFilmFormat().getFramesPerFoot();
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(int index) {
        this.resolution = this.resolutionMap.get(index);
        System.out.println("resolotion set ");
        System.out.println(this.resolution);
    }

    public List<Resolution> getResolutionList() {
        return resolutionList;
    }

    public void setResolutionList(List<Resolution> resolutionList) {
        this.resolutionList = resolutionList;
        setResolutionMap(resolutionList);
    }

    public Map<Integer, Resolution> getResolutionMap() {
        return resolutionMap;
    }

    public void setResolutionMap(List<Resolution> resolutionList) {
        Map<Integer, Resolution> resolutionMap = new HashMap<>();
        for (int i = 0; i < resolutionList.size(); i++) {
            Resolution r = resolutionList.get(i);
            resolutionMap.put(r.getID(), r);
        }
        this.resolutionMap = resolutionMap;
    }

    public List<String> getResolutionArray(){
        List<String> array = new ArrayList<>();
        for (int i = 0; i < this.resolutionList.size(); i++){
            Resolution r = this.resolutionList.get(i);
            array.add(r.getName());
        }
        return array;
    }

    public FileFormat getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(int index) {
        this.fileFormat = this.fileFormatMap.get(index);
    }

    public List<FileFormat> getFileFormatList() {
        return fileFormatList;
    }

    public void setFileFormatList(List<FileFormat> fileFormatList) {
        this.fileFormatList = fileFormatList;
        setFileFormatMap(fileFormatList);
    }

    public Map<Integer, FileFormat> getFileFormatMap() {
        return fileFormatMap;
    }

    public List<String> getFileFormatArray(){
        List<String> array = new ArrayList<>();
        for (int i = 0; i < this.fileFormatList.size(); i++){
            FileFormat f = this.fileFormatList.get(i);
            array.add(f.getName());
        }
        return array;
    }

    public void setFileFormatMap(List<FileFormat> fileFormatList) {
        Map<Integer, FileFormat> fileFormatMap = new HashMap<>();
        for (int i = 0; i < fileFormatList.size(); i++) {
            FileFormat f = fileFormatList.get(i);
            fileFormatMap.put(f.getID(), f);
        }
        this.fileFormatMap = fileFormatMap;
    }

    public Boolean getUnit() {
        return unit;
    }

    public void setUnit(Boolean unit) {
        this.unit = unit;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }
}

package com.nulight.FilmTool.film;

import android.content.Context;

import com.nulight.FilmTool.R;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;
import javax.xml.parsers.*;


public class XMLReader {

    public List<FilmFormat> getFilmFormats(Context context){

        List<FilmFormat> filmFormats = new ArrayList<FilmFormat>();
        FilmFormat format = new FilmFormat();

        try {
            XmlPullParser parser = context.getResources().getXml(R.xml.film_formats);

            String tag = "" , text = "";
            int event = parser.getEventType();
            while (event!= XmlPullParser.END_DOCUMENT) {
                tag = parser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (tag.equals("format")) format = new FilmFormat();
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (tag) {
                            case "id":
                                format.setID(Integer.parseInt(text));
                                break;
                            case "name":
                                format.setName(text);
                                break;
                            case "fpf":
                                format.setFramesPerFoot(Float.parseFloat(text));
                                break;
                            case "tof":
                                format.setThicknessOfFilm(Float.parseFloat(text));
                                break;
                            case "format":
                                if (format != null)
                                    filmFormats.add(format);
                                break;
                        }
                        break;
                }
                event = parser.next();
            }
        }

        catch (XmlPullParserException | IOException e1){
            e1.printStackTrace();
        }

        return filmFormats;
    }

    public List<Double> getFrameRates(Context context){

        List<Double> frameRates = new ArrayList<Double>();

        try {
            XmlPullParser parser = context.getResources().getXml(R.xml.frame_rates);

            String tag = "" , text = "";
            int event = parser.getEventType();
            while (event!= XmlPullParser.END_DOCUMENT) {
                tag = parser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                //        if (tag.equals("fps")) Double frameRate;
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tag.equals("fps")) {
                            Double frameRate = Double.parseDouble(text);
                            frameRates.add(frameRate);
                        }
                        break;
                }
                event = parser.next();
            }
            }
        catch (XmlPullParserException | IOException e1){
            e1.printStackTrace();
        }

        return frameRates;
    }

    public List<FileFormat> getFileFormats(Context context){

        List<FileFormat> fileFormats = new ArrayList<FileFormat>();
        FileFormat format = new FileFormat();

        try {
            XmlPullParser parser = context.getResources().getXml(R.xml.file_formats);

            String tag = "" , text = "";
            int event = parser.getEventType();
            while (event!= XmlPullParser.END_DOCUMENT) {
                tag = parser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (tag.equals("format")) format = new FileFormat();
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (tag) {
                            case "id":
                                format.setID(Integer.parseInt(text));
                                break;
                            case "name":
                                format.setName(text);
                                break;
                            case "bpp":
                                format.setBytesPerPixel(Float.parseFloat(text));
                                break;
                            case "format":
                                if (format != null)
                                    fileFormats.add(format);
                                break;
                        }
                        break;
                }
                event = parser.next();
            }
        }

        catch (XmlPullParserException | IOException e1){
            e1.printStackTrace();
        }

        return fileFormats;
    }

    public List<Resolution> getResolutions(Context context){

        List<Resolution> resolutions = new ArrayList<Resolution>();
        Resolution res = new Resolution();

        try {
            XmlPullParser parser = context.getResources().getXml(R.xml.resolutions);

            String tag = "" , text = "";
            int event = parser.getEventType();
            while (event!= XmlPullParser.END_DOCUMENT) {
                tag = parser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (tag.equals("resolution")) res = new Resolution();
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (tag) {
                            case "id":
                                res.setID(Integer.parseInt(text));
                                break;
                            case "name":
                                res.setName(text);
                                break;
                            case "ppf":
                                res.setPixelsPerFrame(Integer.parseInt(text));
                                break;
                            case "resolution":
                                if (res != null)
                                    resolutions.add(res);
                                break;
                        }
                        break;
                }
                event = parser.next();
            }
        }

        catch (XmlPullParserException | IOException e1){
            e1.printStackTrace();
        }

        return resolutions;
    }

    public Document generateFlowchart(Context context) throws ParserConfigurationException, SAXException, IOException {
        // Ignore XML dir for now, only one flowchart anyway
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream inputStream = context.getResources().openRawResource(R.raw.f16mm_flowchart);
        Document doc = builder.parse(inputStream);
        doc.getDocumentElement().normalize();
        return doc;
    }

}


package com.nulight.FilmTool.film;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class Flowchart {
    Document doc;
    Node currentQ;
    Node rootQ;

    public Flowchart(Context context) throws IOException, SAXException, ParserConfigurationException {
        XMLReader xmlReader = new XMLReader();
        this.doc = xmlReader.generateFlowchart(context);
        this.currentQ = this.doc.getFirstChild().getFirstChild().getNextSibling();
        this.rootQ = this.doc.getFirstChild().getFirstChild().getNextSibling();
    }

    public Node getRootQ() {
        return rootQ;
    }

    public void setCurrentQ(Node currentQ) {
        currentQ.normalize();
        this.currentQ = currentQ;
    }

    public Node getCurrentQ() {
        return currentQ;
    }


}

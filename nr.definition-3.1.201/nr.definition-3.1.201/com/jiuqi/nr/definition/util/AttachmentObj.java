/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.definition.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AttachmentObj {
    private ArrayList<String> document;
    private ArrayList<String> img;
    private ArrayList<String> stadio;
    private ArrayList<String> vedio;
    private ArrayList<String> zip;
    private String maxNumber;
    private String minNumber;
    private String maxSize;
    private String minSize;
    private String groupKey;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getMinSize() {
        return this.minSize;
    }

    public void setMinSize(String minSize) {
        this.minSize = minSize;
    }

    public ArrayList<String> getDocument() {
        return this.document;
    }

    public void setDocument(ArrayList<String> document) {
        this.document = document;
    }

    public ArrayList<String> getImg() {
        return this.img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public ArrayList<String> getStadio() {
        return this.stadio;
    }

    public void setStadio(ArrayList<String> stadio) {
        this.stadio = stadio;
    }

    public ArrayList<String> getVedio() {
        return this.vedio;
    }

    public void setVedio(ArrayList<String> vedio) {
        this.vedio = vedio;
    }

    public ArrayList<String> getZip() {
        return this.zip;
    }

    public void setZip(ArrayList<String> zip) {
        this.zip = zip;
    }

    public String getMaxNumber() {
        return this.maxNumber;
    }

    public void setMaxNumber(String maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getMinNumber() {
        return this.minNumber;
    }

    public void setMinNumber(String minNumber) {
        this.minNumber = minNumber;
    }
}


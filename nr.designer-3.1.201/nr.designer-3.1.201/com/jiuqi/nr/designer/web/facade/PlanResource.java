/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlanResource {
    @JsonProperty
    private String Name;
    @JsonProperty
    private int Type;
    @JsonProperty
    private String Description;
    @JsonProperty
    private int Participant;
    @JsonProperty
    private double StorageSize;
    @JsonProperty
    private int SMS;
    @JsonProperty
    private int ValidPeriod;
    @JsonProperty
    private String Price;

    @JsonIgnore
    public String getName() {
        return this.Name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.Name = name;
    }

    @JsonIgnore
    public int getType() {
        return this.Type;
    }

    @JsonIgnore
    public void setType(int type) {
        this.Type = type;
    }

    @JsonIgnore
    public String getDescription() {
        return this.Description;
    }

    @JsonIgnore
    public void setDescription(String description) {
        this.Description = description;
    }

    @JsonIgnore
    public int getParticipant() {
        return this.Participant;
    }

    @JsonIgnore
    public void setParticipant(int participant) {
        this.Participant = participant;
    }

    @JsonIgnore
    public double getStorageSize() {
        return this.StorageSize;
    }

    @JsonIgnore
    public void setStorageSize(double storageSize) {
        this.StorageSize = storageSize;
    }

    @JsonIgnore
    public int getSMS() {
        return this.SMS;
    }

    @JsonIgnore
    public void setSMS(int sMS) {
        this.SMS = sMS;
    }

    @JsonIgnore
    public int getValidPeriod() {
        return this.ValidPeriod;
    }

    @JsonIgnore
    public void setValidPeriod(int validPeriod) {
        this.ValidPeriod = validPeriod;
    }

    @JsonIgnore
    public String getPrice() {
        return this.Price;
    }

    @JsonIgnore
    public void setPrice(String price) {
        this.Price = price;
    }
}


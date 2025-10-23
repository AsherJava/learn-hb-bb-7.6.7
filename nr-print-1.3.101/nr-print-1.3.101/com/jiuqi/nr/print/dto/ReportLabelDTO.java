/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.print.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public class ReportLabelDTO
implements Serializable {
    private String id;
    private String title;
    private String location;
    private double x;
    private double y;

    public ReportLabelDTO() {
    }

    public ReportLabelDTO(String title, String location, double x, double y) {
        this.title = title;
        this.location = location;
        this.x = x;
        this.y = y;
    }

    @JsonIgnore
    public int getCode() {
        if (this.location == null || this.location.length() < 3) {
            return 13;
        }
        switch (this.location) {
            case "000": {
                return 1;
            }
            case "001": {
                return 2;
            }
            case "002": {
                return 3;
            }
            case "100": {
                return 4;
            }
            case "101": {
                return 5;
            }
            case "102": {
                return 6;
            }
            case "110": {
                return 7;
            }
            case "111": {
                return 8;
            }
            case "112": {
                return 9;
            }
            case "010": {
                return 10;
            }
            case "011": {
                return 11;
            }
            case "012": {
                return 12;
            }
        }
        return 13;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"").append(this.id).append('\"');
        sb.append(",\"title\":\"").append(this.title).append('\"');
        sb.append(",\"location\":\"").append(this.location).append('\"');
        sb.append(",\"x\":").append(this.x);
        sb.append(",\"y\":").append(this.y);
        sb.append('}');
        return sb.toString();
    }
}


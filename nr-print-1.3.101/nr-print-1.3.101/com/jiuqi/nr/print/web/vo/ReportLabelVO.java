/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.print.web.vo;

import com.jiuqi.nr.print.dto.ReportLabelDTO;

public class ReportLabelVO {
    private String id;
    private String title;
    private String location;
    private double x;
    private double y;

    public ReportLabelVO() {
    }

    public ReportLabelVO(ReportLabelDTO label) {
        this.id = label.getId();
        this.title = label.getTitle();
        this.location = label.getLocation();
        this.x = label.getX();
        this.y = label.getY();
    }

    public ReportLabelDTO toReportLabel() {
        ReportLabelDTO label = new ReportLabelDTO();
        label.setId(this.id);
        label.setTitle(this.title);
        label.setLocation(this.location);
        label.setX(this.x);
        label.setY(this.y);
        return label;
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
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.file.dto;

public class FileAreaDTO {
    private boolean defaultArea;
    private String areaName;
    private int areaExpireMills;

    public FileAreaDTO(boolean defaultArea) {
        this.defaultArea = defaultArea;
    }

    public FileAreaDTO(boolean defaultArea, String areaName) {
        this.defaultArea = defaultArea;
        this.areaName = areaName;
    }

    public FileAreaDTO(boolean defaultArea, String areaName, int areaExpireMills) {
        this.defaultArea = defaultArea;
        this.areaName = areaName;
        this.areaExpireMills = areaExpireMills;
    }

    public boolean getDefaultArea() {
        return this.defaultArea;
    }

    public void setDefaultArea(boolean defaultArea) {
        this.defaultArea = defaultArea;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaExpireMills() {
        return this.areaExpireMills;
    }

    public void setAreaExpireMills(int areaExpireMills) {
        this.areaExpireMills = areaExpireMills;
    }
}


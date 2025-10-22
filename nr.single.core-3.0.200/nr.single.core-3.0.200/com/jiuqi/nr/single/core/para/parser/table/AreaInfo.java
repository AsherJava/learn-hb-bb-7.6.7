/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.table;

import com.jiuqi.nr.single.core.para.MapCellInfo;
import com.jiuqi.nr.single.core.para.impl.FloatRegionImpl;
import java.util.ArrayList;
import java.util.List;

public class AreaInfo {
    private String mbCode;
    private String regionFlag;
    private FloatRegionImpl mapArea = new FloatRegionImpl();
    private List<MapCellInfo> mapCells = new ArrayList<MapCellInfo>();
    private int numberingColNum = 1;
    private String keyField = "";
    private String sumField = "";
    private String sumLevel = "";
    private String summaryField;
    private String summaryWidth = "";
    private boolean keyIsUnique;
    private int minKeepRecCount = 1;

    public final String getMbCode() {
        return this.mbCode;
    }

    public final void setMbCode(String mbCode) {
        this.mbCode = mbCode;
    }

    public final FloatRegionImpl getMapArea() {
        return this.mapArea;
    }

    public final void setMapArea(FloatRegionImpl mapArea) {
        this.mapArea = mapArea;
    }

    public final List<MapCellInfo> getMapCells() {
        return this.mapCells;
    }

    public final void setMapCells(List<MapCellInfo> mapCells) {
        this.mapCells = mapCells;
    }

    public final void addMapCell(MapCellInfo info) {
        this.mapCells.add(info);
    }

    public final int getNumberingColNum() {
        return this.numberingColNum;
    }

    public final void setNumberingColNum(int numberingColNum) {
        this.numberingColNum = numberingColNum;
    }

    public final String getKeyField() {
        return this.keyField;
    }

    public final void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public final String getSumField() {
        return this.sumField;
    }

    public final void setSumField(String sumField) {
        this.sumField = sumField;
    }

    public final String getSumLevel() {
        return this.sumLevel;
    }

    public final void setSumLevel(String sumLevel) {
        this.sumLevel = sumLevel;
    }

    public final void setSummaryWidh(String summaryWidth) {
        this.summaryWidth = summaryWidth;
    }

    public final String getSummaryWidth() {
        return this.summaryWidth;
    }

    public final void setSummaryField(String summaryField) {
        this.summaryField = summaryField;
    }

    public final String getSummaryField() {
        return this.summaryField;
    }

    public final void setSummaryWidth(String summaryWidth) {
        this.summaryWidth = summaryWidth;
    }

    public final void setKeyIsUnique(boolean value) {
        this.keyIsUnique = value;
    }

    public final boolean getKeyIsUnique() {
        return this.keyIsUnique;
    }

    public final void setMinKeepRecCount(int value) {
        this.minKeepRecCount = value;
    }

    public final int getMinKeepRecCount() {
        return this.minKeepRecCount;
    }

    public String getRegionFlag() {
        return this.regionFlag;
    }

    public void setRegionFlag(String regionFlag) {
        this.regionFlag = regionFlag;
    }
}


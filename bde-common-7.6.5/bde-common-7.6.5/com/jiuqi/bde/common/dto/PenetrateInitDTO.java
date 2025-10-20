/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.intf.PenetrateColumn;
import java.util.List;

public class PenetrateInitDTO {
    private String title;
    private String unitCode;
    private String unitName;
    private Integer startAcctYear;
    private Integer startPeriod;
    private Integer endAcctYear;
    private Integer endPeriod;
    private List<PenetrateColumn> columns;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getStartAcctYear() {
        return this.startAcctYear;
    }

    public void setStartAcctYear(Integer startAcctYear) {
        this.startAcctYear = startAcctYear;
    }

    public Integer getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(Integer startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Integer getEndAcctYear() {
        return this.endAcctYear;
    }

    public void setEndAcctYear(Integer endAcctYear) {
        this.endAcctYear = endAcctYear;
    }

    public Integer getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(Integer endPeriod) {
        this.endPeriod = endPeriod;
    }

    public List<PenetrateColumn> getColumns() {
        return this.columns;
    }

    public void setColumns(List<PenetrateColumn> columns) {
        this.columns = columns;
    }
}


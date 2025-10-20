/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.missmapping.client.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MissMappingQueryVO
implements Serializable {
    private static final long serialVersionUID = 1156039362952293952L;
    private Date startDateTime;
    private Date endDateTime;
    private String dataSchemeCode;
    private String runnerId;
    private String unitType;
    private List<String> unitCodes;
    private Integer year;
    private Integer period;
    private String vchrNum;
    private Integer page;
    private Integer pageSize;
    private Boolean export;

    public Date getStartDateTime() {
        return this.startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return this.endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getUnitType() {
        return this.unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPeriod() {
        return this.period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getExport() {
        return this.export;
    }

    public void setExport(Boolean export) {
        this.export = export;
    }
}


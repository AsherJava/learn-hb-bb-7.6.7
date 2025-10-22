/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.elementtable.impl.ElementTableTitleVO
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.inputdata.gcoppunit;

import com.jiuqi.gcreport.common.elementtable.impl.ElementTableTitleVO;
import com.jiuqi.gcreport.inputdata.gcoppunit.ActionEditEnum;
import com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitDataVO;
import com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitFieldVO;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.util.List;

public class GcOppUnitCondition {
    private ActionEditEnum type;
    private GcOppUnitDataVO selectData;
    private List<GcOppUnitDataVO> batchSelectData;
    private DataEntryContext envContext;
    private String subjectCode;
    private String subjectName;
    private String regionid;
    private List<GcOppUnitFieldVO> inputData;
    private List<String> subjectCodeList;
    private List<String> unitIdList;
    List<ElementTableTitleVO> columns;
    private Integer pageNum;
    private Integer pageSize;

    public List<String> getUnitIdList() {
        return this.unitIdList;
    }

    public void setUnitIdList(List<String> unitIdList) {
        this.unitIdList = unitIdList;
    }

    public List<String> getSubjectCodeList() {
        return this.subjectCodeList;
    }

    public void setSubjectCodeList(List<String> subjectCodeList) {
        this.subjectCodeList = subjectCodeList;
    }

    public DataEntryContext getEnvContext() {
        return this.envContext;
    }

    public void setEnvContext(DataEntryContext envContext) {
        this.envContext = envContext;
    }

    public ActionEditEnum getType() {
        return this.type;
    }

    public void setType(ActionEditEnum type) {
        this.type = type;
    }

    public void setSelectData(GcOppUnitDataVO selectData) {
        this.selectData = selectData;
    }

    public GcOppUnitDataVO getSelectData() {
        return this.selectData;
    }

    public void setRegionid(String regionid) {
        this.regionid = regionid;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getRegionid() {
        return this.regionid;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public List<GcOppUnitFieldVO> getInputData() {
        return this.inputData;
    }

    public void setInputData(List<GcOppUnitFieldVO> inputData) {
        this.inputData = inputData;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<GcOppUnitDataVO> getBatchSelectData() {
        return this.batchSelectData;
    }

    public void setBatchSelectData(List<GcOppUnitDataVO> batchSelectData) {
        this.batchSelectData = batchSelectData;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum == null ? Integer.valueOf(1) : pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
    }

    public Integer getStartPosition() {
        return (this.pageNum - 1) * this.pageSize;
    }

    public List<ElementTableTitleVO> getColumns() {
        return this.columns;
    }

    public void setColumns(List<ElementTableTitleVO> columns) {
        this.columns = columns;
    }
}


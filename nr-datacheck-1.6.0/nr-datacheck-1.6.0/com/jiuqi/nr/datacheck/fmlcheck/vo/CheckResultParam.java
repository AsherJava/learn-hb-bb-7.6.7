/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormulaSchemeData
 */
package com.jiuqi.nr.datacheck.fmlcheck.vo;

import com.jiuqi.nr.datacheck.fmlcheck.vo.DimensionVO;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CheckResultParam
implements Serializable {
    private static final long serialVersionUID = 8365849813471865356L;
    private String taskName;
    private String formSchemeTitle;
    private String formulaSchemeTitle;
    private Map<String, Object> sysParam;
    private String periodName;
    private String masterDimName;
    private List<EntityViewData> entityList;
    private BatchCheckInfo checkInfo;
    private List<FormulaSchemeData> formulaSchemeList;
    private boolean enableNrdb;
    private List<DimensionVO> dimDropdown;

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public Map<String, Object> getSysParam() {
        return this.sysParam;
    }

    public void setSysParam(Map<String, Object> sysParam) {
        this.sysParam = sysParam;
    }

    public String getPeriodName() {
        return this.periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public List<EntityViewData> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<EntityViewData> entityList) {
        this.entityList = entityList;
    }

    public BatchCheckInfo getCheckInfo() {
        return this.checkInfo;
    }

    public void setCheckInfo(BatchCheckInfo checkInfo) {
        this.checkInfo = checkInfo;
    }

    public List<FormulaSchemeData> getFormulaSchemeList() {
        return this.formulaSchemeList;
    }

    public void setFormulaSchemeList(List<FormulaSchemeData> formulaSchemeList) {
        this.formulaSchemeList = formulaSchemeList;
    }

    public String getMasterDimName() {
        return this.masterDimName;
    }

    public void setMasterDimName(String masterDimName) {
        this.masterDimName = masterDimName;
    }

    public boolean isEnableNrdb() {
        return this.enableNrdb;
    }

    public void setEnableNrdb(boolean enableNrdb) {
        this.enableNrdb = enableNrdb;
    }

    public List<DimensionVO> getDimDropdown() {
        return this.dimDropdown;
    }

    public void setDimDropdown(List<DimensionVO> dimDropdown) {
        this.dimDropdown = dimDropdown;
    }
}


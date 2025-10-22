/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.dataentry.model.DimensionObj;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckResultGroupInfo;
import com.jiuqi.nr.dataentry.paramInfo.ReviewSelectedInfo;
import java.io.Serializable;
import java.util.List;

public class ReviewInfoParam
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    private BatchCheckResultGroupInfo checkInfo;
    private String taskName;
    private String reviewDate;
    private String formSchemeTitle;
    private String formulaSchemeTitle;
    private String formSchemeDate;
    private String unitFormClob;
    private List<ReviewSelectedInfo> unitList;
    private List<ReviewSelectedInfo> formList;
    private String contextEntityId;
    private String contextFilterExpression;
    private String dateTitle;
    private String unitCorporateTitle;
    private List<DimensionObj> dimList;

    public List<DimensionObj> getDimList() {
        return this.dimList;
    }

    public void setDimList(List<DimensionObj> dimList) {
        this.dimList = dimList;
    }

    public String getUnitCorporateTitle() {
        return this.unitCorporateTitle;
    }

    public void setUnitCorporateTitle(String unitCorporateTitle) {
        this.unitCorporateTitle = unitCorporateTitle;
    }

    public String getDateTitle() {
        return this.dateTitle;
    }

    public void setDateTitle(String dateTitle) {
        this.dateTitle = dateTitle;
    }

    public List<ReviewSelectedInfo> getUnitList() {
        return this.unitList;
    }

    public void setUnitList(List<ReviewSelectedInfo> unitList) {
        this.unitList = unitList;
    }

    public List<ReviewSelectedInfo> getFormList() {
        return this.formList;
    }

    public void setFormList(List<ReviewSelectedInfo> formList) {
        this.formList = formList;
    }

    public BatchCheckResultGroupInfo getCheckInfo() {
        return this.checkInfo;
    }

    public void setCheckInfo(BatchCheckResultGroupInfo checkInfo) {
        this.checkInfo = checkInfo;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getReviewDate() {
        return this.reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
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

    public String getFormSchemeDate() {
        return this.formSchemeDate;
    }

    public void setFormSchemeDate(String formSchemeDate) {
        this.formSchemeDate = formSchemeDate;
    }

    public String getUnitFormClob() {
        return this.unitFormClob;
    }

    public void setUnitFormClob(String unitFormClob) {
        this.unitFormClob = unitFormClob;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public void setContextFilterExpression(String contextFilterExpression) {
        this.contextFilterExpression = contextFilterExpression;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}


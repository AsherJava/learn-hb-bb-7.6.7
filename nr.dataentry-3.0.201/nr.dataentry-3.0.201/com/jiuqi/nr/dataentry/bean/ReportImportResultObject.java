/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.JIOUnitImportResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportImportResultObject
extends ImportResultObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int netUnitNum;
    private int uploadUnitNum;
    private int allSuccesssUnitNum;
    private int successsUnitNum;
    private int successsReportNum;
    private int errorUnitNum;
    private int errorReportNum;
    private List<JIOUnitImportResult> errorUnits = new ArrayList<JIOUnitImportResult>();
    private List<JIOUnitImportResult> successUnits = new ArrayList<JIOUnitImportResult>();
    private String formSchemeKey;
    private String formulaSchemeKey;
    private String checkedFormulaSchemeKey;
    private String calFormulaSchemeKey;
    private Map<String, DimensionValue> dimensionSet;
    private String commitTaskKey;
    private boolean isChecked;
    private boolean isCommitted;
    private int deleteUnitNum;
    private List<JIOUnitImportResult> deleteUnits = new ArrayList<JIOUnitImportResult>();
    private boolean isCalForm;

    public int getDeleteUnitNum() {
        return this.deleteUnitNum;
    }

    public void setDeleteUnitNum(int deleteUnitNum) {
        this.deleteUnitNum = deleteUnitNum;
    }

    public List<JIOUnitImportResult> getDeleteUnits() {
        return this.deleteUnits;
    }

    public void setDeleteUnits(List<JIOUnitImportResult> deleteUnits) {
        this.deleteUnits = deleteUnits;
    }

    public int getSuccesssReportNum() {
        return this.successsReportNum;
    }

    public void setSuccesssReportNum(int successsReportNum) {
        this.successsReportNum = successsReportNum;
    }

    public int getErrorReportNum() {
        return this.errorReportNum;
    }

    public void setErrorReportNum(int errorReportNum) {
        this.errorReportNum = errorReportNum;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getCommitTaskKey() {
        return this.commitTaskKey;
    }

    public void setCommitTaskKey(String commitTaskKey) {
        this.commitTaskKey = commitTaskKey;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isCommitted() {
        return this.isCommitted;
    }

    public void setCommitted(boolean isCommitted) {
        this.isCommitted = isCommitted;
    }

    public int getSuccesssUnitNum() {
        return this.successsUnitNum;
    }

    public void setSuccesssUnitNum(int successsUnitNum) {
        this.successsUnitNum = successsUnitNum;
    }

    public int getErrorUnitNum() {
        return this.errorUnitNum;
    }

    public void setErrorUnitNum(int errorUnitNum) {
        this.errorUnitNum = errorUnitNum;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public List<JIOUnitImportResult> getErrorUnits() {
        return this.errorUnits;
    }

    public void setErrorUnits(List<JIOUnitImportResult> errorUnits) {
        this.errorUnits = errorUnits;
    }

    public List<JIOUnitImportResult> getSuccessUnits() {
        return this.successUnits;
    }

    public void setSuccessUnits(List<JIOUnitImportResult> successUnits) {
        this.successUnits = successUnits;
    }

    public boolean isCalForm() {
        return this.isCalForm;
    }

    public void setCalForm(boolean isCalForm) {
        this.isCalForm = isCalForm;
    }

    public String getCheckedFormulaSchemeKey() {
        return this.checkedFormulaSchemeKey;
    }

    public void setCheckedFormulaSchemeKey(String checkedFormulaSchemeKey) {
        this.checkedFormulaSchemeKey = checkedFormulaSchemeKey;
    }

    public String getCalFormulaSchemeKey() {
        return this.calFormulaSchemeKey;
    }

    public void setCalFormulaSchemeKey(String calFormulaSchemeKey) {
        this.calFormulaSchemeKey = calFormulaSchemeKey;
    }

    public int getUploadUnitNum() {
        return this.uploadUnitNum;
    }

    public void setUploadUnitNum(int uploadUnitNum) {
        this.uploadUnitNum = uploadUnitNum;
    }

    public int getAllSuccesssUnitNum() {
        return this.allSuccesssUnitNum;
    }

    public void setAllSuccesssUnitNum(int allSuccesssUnitNum) {
        this.allSuccesssUnitNum = allSuccesssUnitNum;
    }

    public int getNetUnitNum() {
        return this.netUnitNum;
    }

    public void setNetUnitNum(int netUnitNum) {
        this.netUnitNum = netUnitNum;
    }
}


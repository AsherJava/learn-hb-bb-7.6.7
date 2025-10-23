/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.bean;

import com.jiuqi.nr.mapping.bean.config.AutoAppendCode;
import com.jiuqi.nr.mapping.bean.config.SkipUnit;
import com.jiuqi.nr.mapping.bean.config.UpdateWay;
import java.util.List;
import java.util.Map;

public class MappingConfig {
    private boolean uploadCalc;
    private List<String> calcFSKeys;
    private boolean uploadCheck;
    private List<String> checkFSKeys;
    private boolean uploadReport;
    private boolean forceReport;
    private boolean checkParent;
    private boolean uploadEntityAndData;
    private UpdateWay unitUpdateWay;
    private AutoAppendCode autoAppendCode;
    private SkipUnit skipUnit;
    private List<String> coverDataStates;
    private Map<String, String> importRule;
    private Map<String, String> exportRule;

    public boolean isUploadCalc() {
        return this.uploadCalc;
    }

    public void setUploadCalc(boolean uploadCalc) {
        this.uploadCalc = uploadCalc;
    }

    public List<String> getCalcFSKeys() {
        return this.calcFSKeys;
    }

    public void setCalcFSKeys(List<String> calcFSKeys) {
        this.calcFSKeys = calcFSKeys;
    }

    public boolean isUploadCheck() {
        return this.uploadCheck;
    }

    public void setUploadCheck(boolean uploadCheck) {
        this.uploadCheck = uploadCheck;
    }

    public List<String> getCheckFSKeys() {
        return this.checkFSKeys;
    }

    public void setCheckFSKeys(List<String> checkFSKeys) {
        this.checkFSKeys = checkFSKeys;
    }

    public boolean isUploadReport() {
        return this.uploadReport;
    }

    public void setUploadReport(boolean uploadReport) {
        this.uploadReport = uploadReport;
    }

    public boolean isForceReport() {
        return this.forceReport;
    }

    public void setForceReport(boolean forceReport) {
        this.forceReport = forceReport;
    }

    public boolean isCheckParent() {
        return this.checkParent;
    }

    public void setCheckParent(boolean checkParent) {
        this.checkParent = checkParent;
    }

    public boolean isUploadEntityAndData() {
        return this.uploadEntityAndData;
    }

    public void setUploadEntityAndData(boolean uploadEntityAndData) {
        this.uploadEntityAndData = uploadEntityAndData;
    }

    public UpdateWay getUnitUpdateWay() {
        return this.unitUpdateWay;
    }

    public void setUnitUpdateWay(UpdateWay unitUpdateWay) {
        this.unitUpdateWay = unitUpdateWay;
    }

    public AutoAppendCode getAutoAppendCode() {
        return this.autoAppendCode;
    }

    public void setAutoAppendCode(AutoAppendCode autoAppendCode) {
        this.autoAppendCode = autoAppendCode;
    }

    public SkipUnit getSkipUnit() {
        return this.skipUnit;
    }

    public void setSkipUnit(SkipUnit skipUnit) {
        this.skipUnit = skipUnit;
    }

    public List<String> getCoverDataStates() {
        return this.coverDataStates;
    }

    public void setCoverDataStates(List<String> coverDataStates) {
        this.coverDataStates = coverDataStates;
    }

    public Map<String, String> getImportRule() {
        return this.importRule;
    }

    public void setImportRule(Map<String, String> importRule) {
        this.importRule = importRule;
    }

    public Map<String, String> getExportRule() {
        return this.exportRule;
    }

    public void setExportRule(Map<String, String> exportRule) {
        this.exportRule = exportRule;
    }
}


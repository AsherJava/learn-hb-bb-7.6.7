/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.bean;

import com.jiuqi.nr.mapping2.bean.config.AutoAppendCode;
import com.jiuqi.nr.mapping2.bean.config.SkipUnit;
import com.jiuqi.nr.mapping2.bean.config.UpdateWay;
import com.jiuqi.nr.mapping2.common.CompleteUser;
import com.jiuqi.nr.mapping2.dto.ImpExpRule;
import java.util.List;

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
    private List<ImpExpRule> importRule;
    private List<ImpExpRule> exportRule;
    private CompleteUser completeUser = CompleteUser.IMPORTUSER;
    private boolean configParentNode;
    private boolean impByUnitAllCover;

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

    public List<ImpExpRule> getImportRule() {
        return this.importRule;
    }

    public void setImportRule(List<ImpExpRule> importRule) {
        this.importRule = importRule;
    }

    public List<ImpExpRule> getExportRule() {
        return this.exportRule;
    }

    public void setExportRule(List<ImpExpRule> exportRule) {
        this.exportRule = exportRule;
    }

    public CompleteUser getCompleteUser() {
        return this.completeUser;
    }

    public void setCompleteUser(CompleteUser completeUser) {
        this.completeUser = completeUser;
    }

    public boolean isConfigParentNode() {
        return this.configParentNode;
    }

    public void setConfigParentNode(boolean configParentNode) {
        this.configParentNode = configParentNode;
    }

    public boolean isImpByUnitAllCover() {
        return this.impByUnitAllCover;
    }

    public void setImpByUnitAllCover(boolean impByUnitAllCover) {
        this.impByUnitAllCover = impByUnitAllCover;
    }
}


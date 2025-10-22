/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import java.io.Serializable;
import java.util.List;

public class ActionParam
implements Serializable {
    private boolean needbuildVersion;
    private String checkFilter;
    private boolean forceCommit;
    private boolean returnTypeEnable;
    private boolean needOptDesc = false;
    private boolean forceNeedOptDesc = false;
    private boolean needAutoCalculate = false;
    private boolean needAutoCheck = false;
    private List<Integer> ignoreErrorStatus;
    private List<Integer> needCommentErrorStatus;
    private boolean nodeCheck;
    private boolean stepByStep;
    private boolean stepByStepReport;
    private boolean stepByStepBack;
    private boolean stepByStepBackAll;
    private boolean batchOpt;
    private String dimensionName;
    private boolean disabled;
    private boolean mailShow;
    private boolean sysMsgShow;
    private boolean submitAfterFormula;
    private String submitAfterFormulaValue;
    private boolean needAutoAllCheck;
    private String checkFormulaValue;
    private String calcuteFormulaValue;
    private int checkCurrencyType;
    private String checkCurrencyValue;
    private int nodeCheckCurrencyType;
    private String nodeCheckCurrencyValue;
    private boolean isReturnExplain;
    private boolean forceControl;
    private boolean signStartMode;
    private boolean singleRejectAction;

    public boolean isSubmitAfterFormula() {
        return this.submitAfterFormula;
    }

    public void setSubmitAfterFormula(boolean submitAfterFormula) {
        this.submitAfterFormula = submitAfterFormula;
    }

    public String getSubmitAfterFormulaValue() {
        return this.submitAfterFormulaValue;
    }

    public void setSubmitAfterFormulaValue(String submitAfterFormulaValue) {
        this.submitAfterFormulaValue = submitAfterFormulaValue;
    }

    public boolean isNeedbuildVersion() {
        return this.needbuildVersion;
    }

    public void setNeedbuildVersion(boolean needbuildVersion) {
        this.needbuildVersion = needbuildVersion;
    }

    public String getCheckFilter() {
        return this.checkFilter;
    }

    public void setCheckFilter(String checkFilter) {
        this.checkFilter = checkFilter;
    }

    public boolean isForceCommit() {
        return this.forceCommit;
    }

    public void setForceCommit(boolean forceCommit) {
        this.forceCommit = forceCommit;
    }

    public boolean isReturnTypeEnable() {
        return this.returnTypeEnable;
    }

    public void setReturnTypeEnable(boolean returnTypeEnable) {
        this.returnTypeEnable = returnTypeEnable;
    }

    public boolean isNeedOptDesc() {
        return this.needOptDesc;
    }

    public void setNeedOptDesc(boolean needOptDesc) {
        this.needOptDesc = needOptDesc;
    }

    public boolean isNeedAutoCalculate() {
        return this.needAutoCalculate;
    }

    public void setNeedAutoCalculate(boolean needAutoCalculate) {
        this.needAutoCalculate = needAutoCalculate;
    }

    public boolean isNeedAutoCheck() {
        return this.needAutoCheck;
    }

    public void setNeedAutoCheck(boolean needAutoCheck) {
        this.needAutoCheck = needAutoCheck;
    }

    public List<Integer> getIgnoreErrorStatus() {
        return this.ignoreErrorStatus;
    }

    public void setIgnoreErrorStatus(List<Integer> ignoreErrorStatus) {
        this.ignoreErrorStatus = ignoreErrorStatus;
    }

    public List<Integer> getNeedCommentErrorStatus() {
        return this.needCommentErrorStatus;
    }

    public void setNeedCommentErrorStatus(List<Integer> needCommentErrorStatus) {
        this.needCommentErrorStatus = needCommentErrorStatus;
    }

    public boolean isNodeCheck() {
        return this.nodeCheck;
    }

    public void setNodeCheck(boolean nodeCheck) {
        this.nodeCheck = nodeCheck;
    }

    public boolean isStepByStepReport() {
        return this.stepByStepReport;
    }

    public void setStepByStepReport(boolean stepByStepReport) {
        this.stepByStepReport = stepByStepReport;
    }

    public boolean isBatchOpt() {
        return this.batchOpt;
    }

    public void setBatchOpt(boolean batchOpt) {
        this.batchOpt = batchOpt;
    }

    public boolean isStepByStepBack() {
        return this.stepByStepBack;
    }

    public void setStepByStepBack(boolean stepByStepBack) {
        this.stepByStepBack = stepByStepBack;
    }

    public boolean isStepByStep() {
        return this.stepByStep;
    }

    public void setStepByStep(boolean stepByStep) {
        this.stepByStep = stepByStep;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isMailShow() {
        return this.mailShow;
    }

    public void setMailShow(boolean mailShow) {
        this.mailShow = mailShow;
    }

    public boolean isSysMsgShow() {
        return this.sysMsgShow;
    }

    public void setSysMsgShow(boolean sysMsgShow) {
        this.sysMsgShow = sysMsgShow;
    }

    public boolean isForceNeedOptDesc() {
        return this.forceNeedOptDesc;
    }

    public void setForceNeedOptDesc(boolean forceNeedOptDesc) {
        this.forceNeedOptDesc = forceNeedOptDesc;
    }

    public boolean isStepByStepBackAll() {
        return this.stepByStepBackAll;
    }

    public void setStepByStepBackAll(boolean stepByStepBackAll) {
        this.stepByStepBackAll = stepByStepBackAll;
    }

    public boolean isNeedAutoAllCheck() {
        return this.needAutoAllCheck;
    }

    public void setNeedAutoAllCheck(boolean needAutoAllCheck) {
        this.needAutoAllCheck = needAutoAllCheck;
    }

    public String getCheckFormulaValue() {
        return this.checkFormulaValue;
    }

    public void setCheckFormulaValue(String checkFormulaValue) {
        this.checkFormulaValue = checkFormulaValue;
    }

    public String getCalcuteFormulaValue() {
        return this.calcuteFormulaValue;
    }

    public void setCalcuteFormulaValue(String calcuteFormulaValue) {
        this.calcuteFormulaValue = calcuteFormulaValue;
    }

    public String getCheckCurrencyValue() {
        return this.checkCurrencyValue;
    }

    public void setCheckCurrencyValue(String checkCurrencyValue) {
        this.checkCurrencyValue = checkCurrencyValue;
    }

    public String getNodeCheckCurrencyValue() {
        return this.nodeCheckCurrencyValue;
    }

    public void setNodeCheckCurrencyValue(String nodeCheckCurrencyValue) {
        this.nodeCheckCurrencyValue = nodeCheckCurrencyValue;
    }

    public int getCheckCurrencyType() {
        return this.checkCurrencyType;
    }

    public void setCheckCurrencyType(int checkCurrencyType) {
        this.checkCurrencyType = checkCurrencyType;
    }

    public int getNodeCheckCurrencyType() {
        return this.nodeCheckCurrencyType;
    }

    public void setNodeCheckCurrencyType(int nodeCheckCurrencyType) {
        this.nodeCheckCurrencyType = nodeCheckCurrencyType;
    }

    public boolean isReturnExplain() {
        return this.isReturnExplain;
    }

    public void setIsReturnExplain(boolean isReturnExplain) {
        this.isReturnExplain = isReturnExplain;
    }

    public void setReturnExplain(boolean returnExplain) {
        this.isReturnExplain = returnExplain;
    }

    public boolean isForceControl() {
        return this.forceControl;
    }

    public void setForceControl(boolean forceControl) {
        this.forceControl = forceControl;
    }

    public boolean isSignStartMode() {
        return this.signStartMode;
    }

    public void setSignStartMode(boolean signStartMode) {
        this.signStartMode = signStartMode;
    }

    public boolean isSingleRejectAction() {
        return this.singleRejectAction;
    }

    public void setSingleRejectAction(boolean singleRejectAction) {
        this.singleRejectAction = singleRejectAction;
    }
}


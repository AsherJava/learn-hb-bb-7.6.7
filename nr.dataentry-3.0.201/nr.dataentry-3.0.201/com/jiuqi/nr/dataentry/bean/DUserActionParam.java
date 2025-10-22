/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.io.Serializable;
import java.util.List;

public class DUserActionParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean needbuildVersion;
    private String checkFilter;
    private boolean openForceCommit;
    private boolean returnTypeEnable;
    private boolean needOptDesc = false;
    private boolean forceNeedOptDesc = false;
    private boolean needAutoCalculate = false;
    private boolean needAutoCheck = false;
    private boolean needAutoCheckAll = false;
    private boolean needAutoNodeCheck = false;
    private List<Integer> erroStatus;
    private List<Integer> needCommentErrorStatus;
    private boolean stepByStepUpload;
    private boolean batchOpt;
    private String currNode;
    private WorkFlowType workFlowType;
    private boolean mailShow;
    private boolean sysMsgShow;
    private boolean submitAfterFormula = false;
    private String submitAfterFormulaValue;
    private String checkFormulaValue;
    private String calculateFormulaValue;
    private int checkCurrencyType;
    private String checkCurrencyValue;
    private int nodeCheckCurrencyType;
    private String nodeCheckCurrencyValue;
    private boolean returnExplain;
    private boolean signBootMode;
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

    public WorkFlowType getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(WorkFlowType workFlowType) {
        this.workFlowType = workFlowType;
    }

    public String getCheckFilter() {
        return this.checkFilter;
    }

    public void setCheckFilter(String checkFilter) {
        this.checkFilter = checkFilter;
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

    public List<Integer> getNeedCommentErrorStatus() {
        return this.needCommentErrorStatus;
    }

    public void setNeedCommentErrorStatus(List<Integer> needCommentErrorStatus) {
        this.needCommentErrorStatus = needCommentErrorStatus;
    }

    public boolean isNeedbuildVersion() {
        return this.needbuildVersion;
    }

    public void setNeedbuildVersion(boolean needbuildVersion) {
        this.needbuildVersion = needbuildVersion;
    }

    public boolean isOpenForceCommit() {
        return this.openForceCommit;
    }

    public void setOpenForceCommit(boolean openForceCommit) {
        this.openForceCommit = openForceCommit;
    }

    public boolean isNeedAutoNodeCheck() {
        return this.needAutoNodeCheck;
    }

    public void setNeedAutoNodeCheck(boolean needAutoNodeCheck) {
        this.needAutoNodeCheck = needAutoNodeCheck;
    }

    public List<Integer> getErroStatus() {
        return this.erroStatus;
    }

    public void setErroStatus(List<Integer> erroStatus) {
        this.erroStatus = erroStatus;
    }

    public boolean isStepByStepUpload() {
        return this.stepByStepUpload;
    }

    public void setStepByStepUpload(boolean stepByStepUpload) {
        this.stepByStepUpload = stepByStepUpload;
    }

    public boolean isBatchOpt() {
        return this.batchOpt;
    }

    public void setBatchOpt(boolean batchOpt) {
        this.batchOpt = batchOpt;
    }

    public String getCurrNode() {
        return this.currNode;
    }

    public void setCurrNode(String currNode) {
        this.currNode = currNode;
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

    public boolean isNeedAutoCheckAll() {
        return this.needAutoCheckAll;
    }

    public void setNeedAutoCheckAll(boolean needAutoCheckAll) {
        this.needAutoCheckAll = needAutoCheckAll;
    }

    public String getCheckFormulaValue() {
        return this.checkFormulaValue;
    }

    public void setCheckFormulaValue(String checkFormulaValue) {
        this.checkFormulaValue = checkFormulaValue;
    }

    public String getCalculateFormulaValue() {
        return this.calculateFormulaValue;
    }

    public void setCalculateFormulaValue(String calculateFormulaValue) {
        this.calculateFormulaValue = calculateFormulaValue;
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
        return this.returnExplain;
    }

    public void setReturnExplain(boolean returnExplain) {
        this.returnExplain = returnExplain;
    }

    public boolean isSignBootMode() {
        return this.signBootMode;
    }

    public void setSignBootMode(boolean signBootMode) {
        this.signBootMode = signBootMode;
    }

    public boolean isSingleRejectAction() {
        return this.singleRejectAction;
    }

    public void setSingleRejectAction(boolean singleRejectAction) {
        this.singleRejectAction = singleRejectAction;
    }
}


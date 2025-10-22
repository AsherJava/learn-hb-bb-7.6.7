/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.nr.data.logic.facade.param.input.CheckMax;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParallelFormulaExecuteInfo
implements Serializable {
    private static final long serialVersionUID = 6869675910234165302L;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private List<String> forms;
    private boolean fmlJIT;
    private Map<String, List<String>> formulaMaps;
    private List<Integer> formulaCheckTypes;
    private boolean containBetween;
    private String mode;
    private String actionName;
    private String actionId;
    private String executeId;
    private CheckMax checkMax;
    private long actionTime;
    private boolean checkRecord;
    private boolean checkDes;
    private int batchSplitCount = -1;
    private Set<String> accessIgnoreItems;
    private BigDecimal allowableError;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public Map<String, List<String>> getFormulaMaps() {
        return this.formulaMaps;
    }

    public void setFormulaMaps(Map<String, List<String>> formulaMaps) {
        this.formulaMaps = formulaMaps;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public boolean isCheckRecord() {
        return this.checkRecord;
    }

    public void setCheckRecord(boolean checkRecord) {
        this.checkRecord = checkRecord;
    }

    public String getExecuteId() {
        return this.executeId;
    }

    public void setExecuteId(String executeId) {
        this.executeId = executeId;
    }

    public CheckMax getCheckMax() {
        return this.checkMax;
    }

    public void setCheckMax(CheckMax checkMax) {
        this.checkMax = checkMax;
    }

    public boolean isFmlJIT() {
        return this.fmlJIT;
    }

    public void setFmlJIT(boolean fmlJIT) {
        this.fmlJIT = fmlJIT;
    }

    public long getActionTime() {
        return this.actionTime;
    }

    public void setActionTime(long actionTime) {
        this.actionTime = actionTime;
    }

    public List<Integer> getFormulaCheckTypes() {
        return this.formulaCheckTypes;
    }

    public void setFormulaCheckTypes(List<Integer> formulaCheckTypes) {
        this.formulaCheckTypes = formulaCheckTypes;
    }

    public boolean isContainBetween() {
        return this.containBetween;
    }

    public void setContainBetween(boolean containBetween) {
        this.containBetween = containBetween;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isCheckDes() {
        return this.checkDes;
    }

    public void setCheckDes(boolean checkDes) {
        this.checkDes = checkDes;
    }

    public int getBatchSplitCount() {
        return this.batchSplitCount;
    }

    public void setBatchSplitCount(int batchSplitCount) {
        this.batchSplitCount = batchSplitCount;
    }

    public Set<String> getAccessIgnoreItems() {
        return this.accessIgnoreItems;
    }

    public void setAccessIgnoreItems(Set<String> accessIgnoreItems) {
        this.accessIgnoreItems = accessIgnoreItems;
    }

    public BigDecimal getAllowableError() {
        return this.allowableError;
    }

    public void setAllowableError(BigDecimal allowableError) {
        this.allowableError = allowableError;
    }
}


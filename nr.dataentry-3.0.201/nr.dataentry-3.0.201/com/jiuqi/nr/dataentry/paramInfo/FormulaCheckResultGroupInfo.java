/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import java.util.ArrayList;
import java.util.List;

public class FormulaCheckResultGroupInfo {
    private String key;
    private String code;
    private String title;
    private String formKey;
    private int count;
    private int hintCount;
    private int warnCount;
    private int errorCount;
    private String description;
    private List<FormulaCheckResultGroupInfo> childrenGroups = new ArrayList<FormulaCheckResultGroupInfo>();

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getHintCount() {
        return this.hintCount;
    }

    public void setHintCount(int hintCount) {
        this.hintCount = hintCount;
    }

    public int getWarnCount() {
        return this.warnCount;
    }

    public void setWarnCount(int warnCount) {
        this.warnCount = warnCount;
    }

    public int getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public void incrWranCount() {
        ++this.warnCount;
    }

    public void incrHintCount() {
        ++this.hintCount;
    }

    public void incrErrorCount() {
        ++this.errorCount;
    }

    public List<FormulaCheckResultGroupInfo> getChildrenGroups() {
        return this.childrenGroups;
    }

    public void setChildrenGroups(List<FormulaCheckResultGroupInfo> childrenGroups) {
        this.childrenGroups = childrenGroups;
    }
}


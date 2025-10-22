/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachmentcheck.bean;

import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckFieldResultItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttachmentCheckResultItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String unitDimName;
    private String unitKey;
    private String unitCode;
    private String unitTitle;
    private Map<String, String> dimNameValue;
    private Map<String, String> dimNameTitle;
    private String taskKey;
    private String taskTitle;
    private String formulaSchemeKey;
    private String formulaSchemeTitle;
    private String formSchemeKey;
    private String formSchemeTitle;
    private String formCode;
    private String formTitle;
    private String formKey;
    private String fieldCode;
    private String fieldTitle;
    private String fieldKey;
    private String fileName;
    private double fileSize;
    private double maxValue;
    private double minValue;
    private boolean nullable;
    private List<AttachmentCheckFieldResultItem> fieldItems = new ArrayList<AttachmentCheckFieldResultItem>();
    private String userName;

    public String getUnitDimName() {
        return this.unitDimName;
    }

    public void setUnitDimName(String unitDimName) {
        this.unitDimName = unitDimName;
    }

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
        this.unitCode = unitKey;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
        this.unitKey = unitCode;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public Map<String, String> getDimNameValue() {
        return this.dimNameValue;
    }

    public void setDimNameValue(Map<String, String> dimNameValue) {
        this.dimNameValue = dimNameValue;
    }

    public Map<String, String> getDimNameTitle() {
        return this.dimNameTitle;
    }

    public void setDimNameTitle(Map<String, String> dimNameTitle) {
        this.dimNameTitle = dimNameTitle;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return this.minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<AttachmentCheckFieldResultItem> getFieldItems() {
        return this.fieldItems;
    }

    public void setFieldItems(List<AttachmentCheckFieldResultItem> fieldItems) {
        this.fieldItems = fieldItems;
    }

    public void addFieldItem(AttachmentCheckFieldResultItem fieldItem) {
        this.fieldItems.add(fieldItem);
    }

    public boolean getNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}


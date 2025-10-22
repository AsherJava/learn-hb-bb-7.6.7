/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import com.jiuqi.nr.data.logic.facade.param.output.RelatedTaskInfo;
import java.io.Serializable;

public class FormulaNode
implements Serializable {
    private static final long serialVersionUID = 6858621187116455172L;
    private String fieldKey;
    private String fieldTitle;
    private String fieldType;
    private String dataLinkKey;
    private String regionKey;
    private String formKey;
    private String formTitle;
    private String dataId;
    private String value;
    private String nodeShow;
    private boolean complex;
    private RelatedTaskInfo relatedTaskInfo;

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNodeShow() {
        return this.nodeShow;
    }

    public void setNodeShow(String nodeShow) {
        this.nodeShow = nodeShow;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public boolean isComplex() {
        return this.complex;
    }

    public void setComplex(boolean complex) {
        this.complex = complex;
    }

    public RelatedTaskInfo getRelatedTaskInfo() {
        return this.relatedTaskInfo;
    }

    public void setRelatedTaskInfo(RelatedTaskInfo relatedTaskInfo) {
        this.relatedTaskInfo = relatedTaskInfo;
    }
}


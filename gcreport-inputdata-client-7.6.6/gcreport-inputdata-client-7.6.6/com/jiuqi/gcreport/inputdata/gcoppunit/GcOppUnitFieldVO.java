/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 */
package com.jiuqi.gcreport.inputdata.gcoppunit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.inputdata.gcoppunit.CascaderVO;
import com.jiuqi.gcreport.inputdata.gcoppunit.DataInputTypeEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GcOppUnitFieldVO {
    private String taskKey;
    private String formKey;
    private String regionKey;
    private String linkKey;
    private int row;
    private int col;
    private String title;
    private String fieldKey;
    private String fieldCode;
    private DataInputTypeEnum fieldType;
    private int fieldsize;
    private String fieldTitle;
    private String format;
    private String value;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String[] entityValue;
    private boolean canEdit;
    private String entityKey;
    private String entityCode;
    private String entityTitle;
    private Date DateValue;
    private GcBaseDataVO basedata;
    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    private List<CascaderVO> entityData;

    public Date getDateValue() {
        return this.DateValue;
    }

    public void setDateValue(Date dateValue) {
        this.DateValue = dateValue;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public DataInputTypeEnum getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(DataInputTypeEnum fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
        if (StringUtils.isEmpty((String)this.getTitle())) {
            this.setTitle(fieldTitle);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        if (!StringUtils.isEmpty((String)title)) {
            this.title = title;
        }
    }

    public String getValue() {
        return this.value;
    }

    public String getRealValue() {
        if (this.getDateValue() != null) {
            return DateUtils.format((Date)this.getDateValue(), (String)DateUtils.DEFAULT_DATE_FORMAT);
        }
        if (Objects.nonNull(this.basedata) && !StringUtils.isEmpty((String)this.basedata.getCode())) {
            return this.basedata.getCode();
        }
        if (this.entityValue != null && this.entityValue.length > 0) {
            return this.entityValue[this.entityValue.length - 1];
        }
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getEntityValue() {
        return this.entityValue;
    }

    public void setEntityValue(String[] entityValue) {
        this.entityValue = entityValue;
    }

    public boolean isCanEdit() {
        return this.canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityTitle() {
        return this.entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getFieldsize() {
        return this.fieldsize;
    }

    public void setFieldsize(int fieldsize) {
        this.fieldsize = fieldsize;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<CascaderVO> getEntityData() {
        return this.entityData;
    }

    public void setEntityData(List<CascaderVO> entityData) {
        this.entityData = entityData;
    }

    public GcBaseDataVO getBasedata() {
        return this.basedata;
    }

    public void setBasedata(GcBaseDataVO basedata) {
        this.basedata = basedata;
    }

    public void addEntity(CascaderVO vo) {
        if (this.entityData == null) {
            this.entityData = new ArrayList<CascaderVO>();
        }
        this.entityData.add(vo);
    }
}


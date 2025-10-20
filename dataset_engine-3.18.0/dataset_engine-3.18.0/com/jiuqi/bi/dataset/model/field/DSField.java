/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.model.field;

import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.bi.util.StringUtils;

public class DSField
implements Cloneable {
    private String name;
    private String title;
    private int valType;
    private FieldType fieldType;
    private String keyField;
    private String nameField;
    private AggregationType aggregation;
    private ApplyType applyType;
    private TimeGranularity timegranularity;
    private String dataPattern;
    private String showPattern;
    private String sourceType;
    private String sourceData;
    private boolean unitDim;
    private boolean isTimekey;
    private String messageAlias;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValType() {
        return this.valType;
    }

    public void setValType(int valType) {
        this.valType = valType;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getKeyField() {
        if (StringUtils.isEmpty((String)this.keyField)) {
            return this.name;
        }
        return this.keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public String getNameField() {
        if (StringUtils.isEmpty((String)this.nameField)) {
            return this.name;
        }
        return this.nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public AggregationType getAggregation() {
        return this.aggregation;
    }

    public void setAggregation(AggregationType aggregation) {
        this.aggregation = aggregation;
    }

    public ApplyType getApplyType() {
        return this.applyType;
    }

    public void setApplyType(ApplyType applyType) {
        this.applyType = applyType;
    }

    public TimeGranularity getTimegranularity() {
        return this.timegranularity;
    }

    public void setTimegranularity(TimeGranularity timegranularity) {
        this.timegranularity = timegranularity;
    }

    public String getDataPattern() {
        return this.dataPattern;
    }

    public void setDataPattern(String dataPattern) {
        this.dataPattern = dataPattern;
    }

    public String getShowPattern() {
        return this.showPattern;
    }

    public void setShowPattern(String showPattern) {
        this.showPattern = showPattern;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public boolean isDimention() {
        return this.fieldType == FieldType.GENERAL_DIM || this.fieldType == FieldType.TIME_DIM;
    }

    public boolean isUnitDim() {
        return this.unitDim;
    }

    public void setUnitDim(boolean unitDim) {
        this.unitDim = unitDim;
    }

    public boolean isTimekey() {
        return this.isTimekey;
    }

    public void setTimekey(boolean isTimekey) {
        this.isTimekey = isTimekey;
    }

    public String getMessageAlias() {
        return this.messageAlias;
    }

    public void setMessageAlias(String messageAlias) {
        this.messageAlias = messageAlias;
    }

    public String getSourceData() {
        return this.sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public DSField clone() {
        try {
            return (DSField)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('(').append(this.name).append(',').append(DataTypes.dataTypeToString((int)this.valType)).append(',').append(this.title).append(',').append(this.fieldType.title()).append(')');
        return buffer.toString();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DataTypes
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.DSCalcField;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.sql.DataTypes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BIDataSetFieldInfo
implements Cloneable,
Serializable {
    private static final long serialVersionUID = -5711695965298269224L;
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
    private boolean isCalcField = false;
    private String formula;
    private CalcMode calcMode;
    private boolean isUnitDim;
    private boolean isTimekey;
    private String messageAlias;
    private List<String> refDimCols;

    public BIDataSetFieldInfo() {
    }

    public BIDataSetFieldInfo(String name, int valType, String title) {
        this.name = name;
        this.title = title;
        this.valType = valType;
    }

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

    public boolean isNumber() {
        return this.valType == 3 || this.valType == 5 || this.valType == 10;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getKeyField() {
        return this.keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public String getNameField() {
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

    public void setUnitDim(boolean isUnitDim) {
        this.isUnitDim = isUnitDim;
    }

    public boolean isUnitDim() {
        return this.isUnitDim;
    }

    public boolean isTimekey() {
        return this.isTimekey;
    }

    public void setTimekey(boolean isTimekey) {
        this.isTimekey = isTimekey;
    }

    public CalcMode getCalcMode() {
        return this.calcMode;
    }

    public void setCalcMode(CalcMode calcMode) {
        this.calcMode = calcMode;
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

    public boolean isCalcField() {
        return this.isCalcField;
    }

    public boolean isKeyField() {
        return this.name.equalsIgnoreCase(this.keyField);
    }

    public void setCalcField(boolean isCalcField) {
        this.isCalcField = isCalcField;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public boolean isDimention() {
        return this.fieldType == FieldType.GENERAL_DIM || this.fieldType == FieldType.TIME_DIM || this.getName().equals("SYS_TIMEKEY");
    }

    public void addRefDim(String dimName) {
        if (this.refDimCols == null) {
            this.refDimCols = new ArrayList<String>();
        }
        this.refDimCols.add(dimName);
    }

    public List<String> getRefDimCols() {
        if (this.refDimCols == null) {
            this.refDimCols = new ArrayList<String>();
        }
        return this.refDimCols;
    }

    public boolean hasRefDim() {
        return this.refDimCols != null && !this.refDimCols.isEmpty();
    }

    public Iterator<String> refDimIterator() {
        if (this.refDimCols == null) {
            return null;
        }
        return this.refDimCols.iterator();
    }

    public void loadFromDSField(DSField field) {
        this.setAggregation(field.getAggregation());
        this.setApplyType(field.getApplyType());
        this.setDataPattern(field.getDataPattern());
        this.setFieldType(field.getFieldType());
        this.setKeyField(field.getKeyField());
        this.setName(field.getName());
        this.setNameField(field.getNameField());
        this.setShowPattern(field.getShowPattern());
        this.setSourceData(field.getSourceData());
        this.setSourceType(field.getSourceType());
        this.setTimegranularity(field.getTimegranularity());
        this.setTitle(field.getTitle());
        this.setValType(field.getValType());
        this.setUnitDim(field.isUnitDim());
        this.setTimekey(field.isTimekey());
        this.setMessageAlias(field.getMessageAlias());
        if (field instanceof DSCalcField) {
            this.setCalcField(true);
            this.setCalcMode(((DSCalcField)field).getCalcMode());
        } else {
            this.setCalcField(false);
        }
    }

    public BIDataSetFieldInfo clone() {
        BIDataSetFieldInfo info;
        try {
            info = (BIDataSetFieldInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        if (info.hasRefDim()) {
            List<String> refDims = info.refDimCols;
            info.refDimCols = new ArrayList<String>();
            for (String rd : refDims) {
                info.refDimCols.add(rd);
            }
        }
        return info;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('(').append(this.name).append(',').append(DataTypes.dataTypeToString((int)this.valType)).append(',').append(this.title).append(',').append(this.fieldType == null ? "null" : this.fieldType.title()).append(')');
        return buffer.toString();
    }
}


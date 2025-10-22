/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.ConditionField;
import com.jiuqi.nr.dafafill.model.Options;
import com.jiuqi.nr.dafafill.model.OrderField;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.model.enums.TableSample;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFillModel
implements Serializable {
    private static final long serialVersionUID = 1L;
    ModelType modelType;
    TableType tableType;
    List<QueryField> queryFields;
    List<QueryField> assistFields;
    List<ConditionField> conditionFields;
    TableSample tableSample = TableSample.NOTSUPPORTED;
    List<String> displayCols;
    List<OrderField> orderFields;
    Options otherOption;
    private Map<String, String> extendedData = new HashMap<String, String>();

    public List<QueryField> getQueryFields() {
        return this.queryFields;
    }

    public void setQueryFields(List<QueryField> queryFields) {
        this.queryFields = queryFields;
    }

    public List<QueryField> getAssistFields() {
        return this.assistFields;
    }

    public void setAssistFields(List<QueryField> assistFields) {
        this.assistFields = assistFields;
    }

    public List<ConditionField> getConditionFields() {
        return this.conditionFields;
    }

    public void setConditionFields(List<ConditionField> conditionFields) {
        this.conditionFields = conditionFields;
    }

    public TableSample getTableSample() {
        return this.tableSample;
    }

    public void setTableSample(TableSample tableSample) {
        this.tableSample = tableSample;
    }

    public List<String> getDisplayCols() {
        return this.displayCols;
    }

    public void setDisplayCols(List<String> displayCols) {
        this.displayCols = displayCols;
    }

    public List<OrderField> getOrderFields() {
        return this.orderFields;
    }

    public void setOrderFields(List<OrderField> orderFields) {
        this.orderFields = orderFields;
    }

    public Options getOtherOption() {
        return this.otherOption;
    }

    public void setOtherOption(Options otherOption) {
        this.otherOption = otherOption;
    }

    public ModelType getModelType() {
        return this.modelType;
    }

    public void setModelType(ModelType modelType) {
        this.modelType = modelType;
    }

    public TableType getTableType() {
        if (null == this.tableType) {
            if (this.modelType == ModelType.TASK) {
                return TableType.MASTER;
            }
            for (QueryField queryField : this.queryFields) {
                if (queryField.getFieldType() != FieldType.FIELD && queryField.getFieldType() != FieldType.TABLEDIMENSION) continue;
                return TableType.FLOAT;
            }
            return TableType.FIXED;
        }
        return this.tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public Map<String, String> getExtendedData() {
        return this.extendedData;
    }

    public void setExtendedData(Map<String, String> extendedData) {
        this.extendedData = extendedData;
    }

    public String toString() {
        return "DataFillModel [modelType=" + (Object)((Object)this.modelType) + ", queryFields=" + this.queryFields + ", assistFields=" + this.assistFields + ", conditionFields=" + this.conditionFields + ", tableSample=" + (Object)((Object)this.tableSample) + ", displayCols=" + this.displayCols + ", orderFields=" + this.orderFields + ", otherOption=" + this.otherOption + "]";
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
}


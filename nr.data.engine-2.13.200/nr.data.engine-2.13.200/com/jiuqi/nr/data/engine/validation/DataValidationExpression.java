/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 */
package com.jiuqi.nr.data.engine.validation;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.data.engine.validation.CompareType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataValidationExpression {
    private static final Logger logger = LoggerFactory.getLogger(DataValidationExpression.class);
    private final FieldDefine fieldDefine;
    private final CompareType compareType;
    private Object compareValue;
    private Object min;
    private Object max;
    private Object[] compareValueArray;
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    public DataValidationExpression(FieldDefine fieldDefine, CompareType compareType, Object compareValue) {
        this.fieldDefine = fieldDefine;
        this.compareType = compareType;
        this.compareValue = compareValue;
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanProvider.getBean(IDataDefinitionRuntimeController.class);
    }

    public DataValidationExpression(FieldDefine fieldDefine, CompareType compareType, Object min, Object max) {
        this.fieldDefine = fieldDefine;
        this.compareType = compareType;
        this.min = min;
        this.max = max;
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanProvider.getBean(IDataDefinitionRuntimeController.class);
    }

    public DataValidationExpression(FieldDefine fieldDefine, CompareType compareType, Object[] compareValueArray) {
        this.fieldDefine = fieldDefine;
        this.compareType = compareType;
        this.compareValueArray = compareValueArray;
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanProvider.getBean(IDataDefinitionRuntimeController.class);
    }

    public FieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public CompareType getCompareType() {
        return this.compareType;
    }

    public Object getMin() {
        return this.min;
    }

    public Object getMax() {
        return this.max;
    }

    public Object getCompareValue() {
        return this.compareValue;
    }

    public String toFormula() {
        StringBuilder buff = new StringBuilder();
        String tableCode = this.fieldDefine.getCode();
        try {
            TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(this.fieldDefine.getOwnerTableKey());
            tableCode = tableDefine.getCode();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        switch (this.compareType) {
            case BETWEEN: {
                buff.append(tableCode).append("[").append(this.fieldDefine.getCode()).append("]");
                buff.append(this.compareType.getSign());
                this.appendValue(buff, this.min);
                buff.append(" and ");
                buff.append(tableCode).append("[").append(this.fieldDefine.getCode()).append("]");
                buff.append(this.compareType.getSign2());
                this.appendValue(buff, this.max);
                break;
            }
            case NOT_BETWEEN: {
                buff.append(tableCode).append("[").append(this.fieldDefine.getCode()).append("]");
                buff.append(this.compareType.getSign());
                this.appendValue(buff, this.min);
                buff.append(" or ");
                buff.append(tableCode).append("[").append(this.fieldDefine.getCode()).append("]");
                buff.append(this.compareType.getSign2());
                this.appendValue(buff, this.max);
                break;
            }
            case CONTAINS: {
                buff.append("position(");
                buff.append("\"").append(this.compareValue).append("\",");
                buff.append(tableCode).append("[").append(this.fieldDefine.getCode()).append("]");
                buff.append(")>=1");
                break;
            }
            case NOT_CONTAINS: {
                buff.append("position(");
                buff.append("\"").append(this.compareValue).append("\",");
                buff.append(tableCode).append("[").append(this.fieldDefine.getCode()).append("]");
                buff.append(")<1");
                break;
            }
            case IN: {
                buff.append(tableCode).append("[").append(this.fieldDefine.getCode()).append("]");
                buff.append(" in {");
                if (this.compareValueArray.length <= 0) break;
                for (Object value : this.compareValueArray) {
                    this.appendValue(buff, value);
                    buff.append(",");
                }
                buff.setLength(buff.length() - 1);
                break;
            }
            default: {
                buff.append(tableCode).append("[").append(this.fieldDefine.getCode()).append("]");
                buff.append(this.compareType.getSign());
                this.appendValue(buff, this.compareValue);
            }
        }
        return buff.toString();
    }

    public void appendValue(StringBuilder buff, Object value) {
        if (this.fieldDefine.getType() == FieldType.FIELD_TYPE_STRING || this.fieldDefine.getType() == FieldType.FIELD_TYPE_TEXT) {
            buff.append("\"").append(value).append("\"");
        } else {
            buff.append(value);
        }
    }

    public String toString() {
        return "DataValidationExpression [fieldDefine=" + this.fieldDefine.getCode() + ", compareType=" + (Object)((Object)this.compareType) + ", compareValue=" + this.compareValue + ", min=" + this.min + ", max=" + this.max + "]";
    }
}


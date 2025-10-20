/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.query.sql.formula;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.query.sql.formula.QueryFormulaConvertUtil;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class QueryFormulaContext
implements IContext {
    private Map<String, Object> sumRow = new HashMap<String, Object>();
    private QueryTemplate template;
    private HashSet<String> triggerFields = new HashSet();

    public Map<String, Object> getSumRow() {
        return this.sumRow;
    }

    public void setSumRow(Map<String, Object> sumRow) {
        this.sumRow = sumRow;
    }

    public QueryFormulaContext(Map<String, Object> sumRow, QueryTemplate template) {
        this.sumRow = sumRow;
        this.template = template;
    }

    public QueryFormulaContext() {
    }

    public QueryTemplate getTemplate() {
        return this.template;
    }

    public void setTemplate(QueryTemplate template) {
        this.template = template;
    }

    public Object valueOf(Object value, String paramType) {
        if (value == null) {
            return null;
        }
        ParamTypeEnum paramTypeEnum = ParamTypeEnum.val(paramType);
        if (paramTypeEnum == null) {
            return value;
        }
        switch (paramTypeEnum) {
            case SEQUENCE: 
            case STRING: {
                if (value instanceof Double) {
                    return QueryFormulaConvertUtil.toInt(value);
                }
                return QueryFormulaConvertUtil.toString(value);
            }
            case BOOL: {
                return QueryFormulaConvertUtil.toBoolean(value);
            }
            case INTEGER: {
                return QueryFormulaConvertUtil.toInt(value);
            }
            case NUMBER: {
                return QueryFormulaConvertUtil.toDouble(value);
            }
            case DATE: {
                return this.formatDate(QueryFormulaConvertUtil.toDate(value), "yyyy-MM-dd");
            }
            case DATE_TIME: {
                return this.formatDate(QueryFormulaConvertUtil.toDate(value), "yyyy-MM-dd HH:mm:ss");
            }
        }
        return QueryFormulaConvertUtil.toString(value);
    }

    private Object formatDate(long time, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);
        return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    public HashSet<String> getTriggerFields() {
        return this.triggerFields;
    }

    public void setTriggerFields(HashSet<String> triggerFields) {
        this.triggerFields = triggerFields;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql;

import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchField;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchFieldMeta;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomFetchSelectFieldHolder {
    private List<SelectField> modelSelectFieldList = new ArrayList<SelectField>();
    private List<CustomCondition> modelCondiList = new ArrayList<CustomCondition>();
    private Set<String> selectRecordSet = new HashSet<String>(20);
    private Set<String> condiRecordSet = new HashSet<String>(20);
    static final String TMPL_FIELD_SQL = "%1$s AS %1$s";

    public CustomFetchSelectFieldHolder(List<SelectField> modelSelectFieldList, List<CustomCondition> modelCondiList) {
        this.modelSelectFieldList = modelSelectFieldList;
        this.modelCondiList = modelCondiList;
    }

    public void doAnalyze(String field, Map<String, CustomCondition> condiMap) {
        for (String condiKey : condiMap.keySet()) {
            if (this.hasAnalyze(condiKey)) continue;
            this.condiRecordSet.add(condiKey);
        }
        if (this.hasAnalyze(field)) {
            return;
        }
        this.selectRecordSet.add(field);
    }

    public String buildSql(FetchTaskContext fetchTaskContext) {
        ArrayList<String> fieldSqlList = new ArrayList<String>();
        HashSet<String> selectFieldSet = new HashSet<String>();
        for (CustomCondition condi : this.modelCondiList) {
            if (!this.hasAnalyze(condi.getParamsCode()) || selectFieldSet.contains(condi.getParamsCode()) || !this.condiRecordSet.contains(condi.getParamsCode())) continue;
            fieldSqlList.add(String.format(TMPL_FIELD_SQL, condi.getParamsCode()));
            selectFieldSet.add(condi.getParamsCode());
        }
        for (SelectField selectField : this.modelSelectFieldList) {
            if (!this.hasAnalyze(selectField.getFieldCode()) || selectFieldSet.contains(selectField.getFieldCode()) || !this.selectRecordSet.contains(selectField.getFieldCode())) continue;
            AggregateFuncEnum func = AggregateFuncEnum.getEnumByCode((String)selectField.getAggregateFuncCode());
            if (AggregateFuncEnum.COUNT == func && !selectFieldSet.contains("FETCH_EXE_CT_FIELD")) {
                fieldSqlList.add(String.format("COUNT(1) AS %1$s ", "FETCH_EXE_CT_FIELD"));
                selectFieldSet.add("FETCH_EXE_CT_FIELD");
                continue;
            }
            if (AggregateFuncEnum.AVG == func) {
                fieldSqlList.add(String.format(AggregateFuncEnum.SUM.getFuncStr(), selectField.getFieldCode()));
                selectFieldSet.add(selectField.getFieldCode());
                if (selectFieldSet.contains("FETCH_EXE_CT_FIELD")) continue;
                fieldSqlList.add(String.format("COUNT(1) AS %1$s ", "FETCH_EXE_CT_FIELD"));
                selectFieldSet.add("FETCH_EXE_CT_FIELD");
                continue;
            }
            fieldSqlList.add(String.format(func.getFuncStr(), selectField.getFieldCode()));
            selectFieldSet.add(selectField.getFieldCode());
        }
        return String.join((CharSequence)",", fieldSqlList);
    }

    public CustomFetchFieldMeta buildFieldMeta(FetchTaskContext fetchTaskContext) {
        CustomFetchFieldMeta fetchFieldMeta = new CustomFetchFieldMeta();
        HashSet<String> selectFieldSet = new HashSet<String>();
        for (CustomCondition condi : this.modelCondiList) {
            if (!this.hasAnalyze(condi.getParamsCode()) || selectFieldSet.contains(condi.getParamsCode()) || !this.condiRecordSet.contains(condi.getParamsCode())) continue;
            fetchFieldMeta.addFetchField(new CustomFetchField(condi.getParamsCode(), condi.getParamsName(), AggregateFuncEnum.ORIGINAL, ResultColumnType.STRING));
            selectFieldSet.add(condi.getParamsCode());
        }
        boolean includeCtField = false;
        boolean includeAvgField = false;
        for (SelectField selectField : this.modelSelectFieldList) {
            if (!this.hasAnalyze(selectField.getFieldCode()) || selectFieldSet.contains(selectField.getFieldCode()) || !this.selectRecordSet.contains(selectField.getFieldCode())) continue;
            ResultColumnType colType = null;
            AggregateFuncEnum func = AggregateFuncEnum.getEnumByCode((String)selectField.getAggregateFuncCode());
            switch (func) {
                case COUNT: {
                    colType = ResultColumnType.INT;
                    break;
                }
                case SUM: {
                    colType = ResultColumnType.NUMBER;
                    break;
                }
                case AVG: {
                    colType = ResultColumnType.NUMBER;
                    break;
                }
                default: {
                    colType = ResultColumnType.STRING;
                }
            }
            if (AggregateFuncEnum.COUNT == func && !selectFieldSet.contains("FETCH_EXE_CT_FIELD")) {
                fetchFieldMeta.addFetchField(new CustomFetchField("FETCH_EXE_CT_FIELD", "FETCH_EXE_CT_FIELD", func, ResultColumnType.NUMBER));
                selectFieldSet.add("FETCH_EXE_CT_FIELD");
                includeCtField = true;
                continue;
            }
            if (AggregateFuncEnum.AVG == func) {
                fetchFieldMeta.addFetchField(new CustomFetchField(selectField.getFieldCode(), selectField.getFieldName(), func, colType));
                selectFieldSet.add(selectField.getFieldCode());
                includeAvgField = true;
                if (selectFieldSet.contains("FETCH_EXE_CT_FIELD")) continue;
                fetchFieldMeta.addFetchField(new CustomFetchField("FETCH_EXE_CT_FIELD", "FETCH_EXE_CT_FIELD", func, ResultColumnType.NUMBER));
                selectFieldSet.add("FETCH_EXE_CT_FIELD");
                includeCtField = true;
                continue;
            }
            fetchFieldMeta.addFetchField(new CustomFetchField(selectField.getFieldCode(), selectField.getFieldName(), AggregateFuncEnum.getEnumByCode((String)selectField.getAggregateFuncCode()), colType));
            selectFieldSet.add(selectField.getFieldCode());
        }
        fetchFieldMeta.setIncludeAvgField(includeAvgField);
        fetchFieldMeta.setIncludeCtField(includeCtField);
        return fetchFieldMeta;
    }

    public boolean hasAnalyze(String key) {
        return this.selectRecordSet.contains(key) || this.condiRecordSet.contains(key);
    }
}


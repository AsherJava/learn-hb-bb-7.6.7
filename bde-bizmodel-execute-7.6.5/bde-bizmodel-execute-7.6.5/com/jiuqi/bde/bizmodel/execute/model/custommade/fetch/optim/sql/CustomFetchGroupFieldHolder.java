/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql;

import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomFetchGroupFieldHolder {
    private List<SelectField> modelSelectFieldList = new ArrayList<SelectField>();
    private Map<String, SelectField> modelSelectFieldMap = new HashMap<String, SelectField>();
    private List<CustomCondition> modelCondiList = new ArrayList<CustomCondition>();
    private Set<String> selectRecordSet = new HashSet<String>(20);
    private Set<String> condiRecordSet = new HashSet<String>(20);
    private static final String TMPL_FIELD_SQL = "%1$s";

    public CustomFetchGroupFieldHolder(List<SelectField> modelSelectFieldList, List<CustomCondition> modelCondiList) {
        this.modelSelectFieldList = modelSelectFieldList;
        this.modelSelectFieldMap = modelSelectFieldList.stream().collect(Collectors.toMap(SelectField::getFieldCode, item -> item, (k1, k2) -> k2));
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
        if (AggregateFuncEnum.getEnumByCode((String)this.modelSelectFieldMap.get(field).getAggregateFuncCode()).isEnableGroup()) {
            this.selectRecordSet.add(field);
        }
    }

    public String buildSql(FetchTaskContext fetchTaskContext) {
        ArrayList<String> groupSqlList = new ArrayList<String>();
        HashSet<String> hasGroupFieldSet = new HashSet<String>();
        for (CustomCondition customCondi : this.modelCondiList) {
            if (!this.hasAnalyze(customCondi.getParamsCode()) || hasGroupFieldSet.contains(customCondi.getParamsCode()) || !this.condiRecordSet.contains(customCondi.getParamsCode())) continue;
            groupSqlList.add(String.format(TMPL_FIELD_SQL, customCondi.getParamsCode()));
            hasGroupFieldSet.add(customCondi.getParamsCode());
        }
        for (SelectField selectField : this.modelSelectFieldList) {
            if (!this.hasAnalyze(selectField.getFieldCode()) || hasGroupFieldSet.contains(selectField.getFieldCode()) || !this.selectRecordSet.contains(selectField.getFieldCode())) continue;
            groupSqlList.add(String.format(TMPL_FIELD_SQL, selectField.getFieldCode()));
            hasGroupFieldSet.add(selectField.getFieldCode());
        }
        if (CollectionUtils.isEmpty(groupSqlList)) {
            return "";
        }
        return "GROUP BY " + String.join((CharSequence)",", groupSqlList);
    }

    public boolean hasAnalyze(String key) {
        return this.selectRecordSet.contains(key) || this.condiRecordSet.contains(key);
    }
}


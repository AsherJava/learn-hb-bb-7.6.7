/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.executor.query.ds;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

public class SummaryDSModel
extends DSModel {
    public static final String TYPE = "com.jiuqi.nr.summaryreport";
    private int row;
    private List<String> publicDimFields = new ArrayList<String>();
    private List<String> queryMeasureFields = new ArrayList<String>();
    private Map<String, String> keyToFieldNameMap = new HashMap<String, String>();
    private Set<String> fieldNameSet = new HashSet<String>();
    private Map<String, String> keyFieldToOrderNameMap = new HashMap<String, String>();

    public String getType() {
        return TYPE;
    }

    protected void saveExtToJSON(JSONObject jsonObject) throws Exception {
    }

    protected void loadExtFromJSON(JSONObject jsonObject) throws Exception {
    }

    public boolean addSummaryField(String fieldName, SummaryDSField dsField) {
        if (this.fieldNameSet.contains(fieldName)) {
            return false;
        }
        this.getCommonFields().add(dsField);
        return true;
    }

    public List<String> getPublicDimFields() {
        return this.publicDimFields;
    }

    public List<String> getQueryMeasureFields() {
        return this.queryMeasureFields;
    }

    public void addKey2NameRef(String key, String fieldName) {
        if (StringUtils.hasText(key)) {
            this.keyToFieldNameMap.put(key, fieldName);
        }
    }

    public DSField findDsFieldByKey(String key) {
        String fieldName = this.keyToFieldNameMap.get(key);
        if (StringUtils.hasText(fieldName)) {
            return this.findField(fieldName);
        }
        return null;
    }

    public void addKeyField2OrderRef(String keyField, String orderName) {
        if (StringUtils.hasText(keyField)) {
            this.keyFieldToOrderNameMap.put(keyField, orderName);
        }
    }

    public DSField findOrderFieldByKeyField(String keyField) {
        String orderName = this.keyFieldToOrderNameMap.get(keyField);
        if (StringUtils.hasText(orderName)) {
            return this.findField(orderName);
        }
        return null;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}


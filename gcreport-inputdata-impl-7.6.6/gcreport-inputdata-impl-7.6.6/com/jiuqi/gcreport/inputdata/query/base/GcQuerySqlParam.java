/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.query.base;

import java.util.HashMap;
import java.util.Map;

public class GcQuerySqlParam {
    private StringBuffer filter;
    private Map<String, String> refMap;
    private String currencyCode;
    private boolean useDnaSql;
    private String alias;

    public GcQuerySqlParam(String currencyCode, boolean useDnaSql) {
        this.currencyCode = currencyCode;
        this.useDnaSql = useDnaSql;
        this.filter = new StringBuffer();
        this.refMap = new HashMap<String, String>();
    }

    public boolean isUseDnaSql() {
        return this.useDnaSql;
    }

    public void initTableParam(String alias) {
        this.alias = alias;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void clear() {
        this.filter = new StringBuffer();
        this.refMap.clear();
    }

    public StringBuffer append(Object value) {
        return this.filter.append(value);
    }

    public void put(String key, String value) {
        this.refMap.put(key, value);
    }

    public Map<String, String> formatRefFieldMap(Map<String, String> rf) {
        if (rf != null) {
            rf.entrySet().stream().forEach(f -> this.refMap.put(this.fm((String)f.getKey()), this.fm((String)f.getValue())));
        }
        return this.refMap;
    }

    public boolean isEmpty() {
        return this.filter == null || this.filter.length() < 1;
    }

    public String formatSql(String sql) {
        if (this.filter.length() > 0) {
            String where = null;
            String and = " AND ";
            where = !this.filter.toString().endsWith(and) ? this.fm(this.filter.append(and).toString()) : this.fm(this.filter.toString());
            sql = sql.replace(" WHERE ", where);
            if (this.getRefMap() != null && this.getRefMap().size() > 0) {
                for (Map.Entry<String, String> entry : this.getRefMap().entrySet()) {
                    sql = sql.replaceAll(entry.getKey(), entry.getValue());
                }
            }
        }
        return sql;
    }

    public Map<String, String> getRefMap() {
        return this.refMap;
    }

    private String fm(String format) {
        return String.format(format, this.alias.toUpperCase());
    }
}


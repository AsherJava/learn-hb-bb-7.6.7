/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ttddyy.dsproxy.proxy.ParameterSetOperation;

public class QueryInfo {
    private String query;
    private List<List<ParameterSetOperation>> parametersList = new ArrayList<List<ParameterSetOperation>>();

    public QueryInfo() {
    }

    public QueryInfo(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Deprecated
    public List<Map<String, Object>> getQueryArgsList() {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (List<ParameterSetOperation> paramsList : this.parametersList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            for (ParameterSetOperation param : paramsList) {
                Object[] args = param.getArgs();
                map.put(args[0].toString(), args[1]);
            }
            result.add(map);
        }
        return result;
    }

    public List<List<ParameterSetOperation>> getParametersList() {
        return this.parametersList;
    }

    public void setParametersList(List<List<ParameterSetOperation>> parametersList) {
        this.parametersList = parametersList;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.va.datamodel.exchange.nvwa.dao;

import com.jiuqi.va.datamodel.exchange.nvwa.dao.FilterCallBack;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FNvwaBaseDefineDao<T extends Map<String, Object>> {
    public int queryDataCount(String var1, FilterCallBack var2);

    public List<Map<String, Object>> queryDataRows(String var1, Map<String, Boolean> var2, FilterCallBack var3);

    public boolean saveData(String var1, FilterCallBack var2, T var3);

    public boolean deleteData(String var1, FilterCallBack var2);

    default public void refreshDataCache(String tableName) {
    }

    default public String getLogMessage(Map<String, Object> basedata) {
        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("code", "\u4ee3\u7801");
        fields.put("name", "\u540d\u79f0");
        fields.put("validtime", "\u751f\u6548\u65f6\u95f4");
        fields.put("invalidtime", "\u5931\u6548\u65f6\u95f4");
        HashMap logs = new HashMap();
        fields.forEach((k, v) -> logs.put(v, basedata.get(k)));
        return JSONUtil.toJSONString(logs);
    }
}


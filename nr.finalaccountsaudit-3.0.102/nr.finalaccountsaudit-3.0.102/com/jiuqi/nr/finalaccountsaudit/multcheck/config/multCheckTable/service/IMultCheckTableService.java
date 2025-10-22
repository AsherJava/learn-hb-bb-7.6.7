/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.service;

import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.entity.MultCheckTableBean;
import java.util.List;
import java.util.Map;

public interface IMultCheckTableService {
    public int insertData(MultCheckTableBean var1);

    public int deleteData(String var1);

    public int updateData(String var1);

    public MultCheckTableBean selectData(String var1);

    public void tabaleUpdateOperation(Map var1);

    public List<String> getCheckItemList();

    public boolean deleteCheckResult(String var1, String var2, List<String> var3);

    public List<Map<String, Object>> getSchemeList(String var1);
}


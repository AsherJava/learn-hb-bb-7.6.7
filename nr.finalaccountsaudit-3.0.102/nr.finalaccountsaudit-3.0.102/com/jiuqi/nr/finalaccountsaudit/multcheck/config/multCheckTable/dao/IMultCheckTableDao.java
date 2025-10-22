/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.dao;

import java.util.List;
import java.util.Map;

public interface IMultCheckTableDao {
    public int insertData(Object[] var1);

    public int deleteData(String var1);

    public int updateData(String var1, List<Object> var2);

    public List<Map<String, Object>> selectData(String var1);

    public void updateDwCode(String var1, String var2);

    public List<Map<String, Object>> getCheckItemList();

    public boolean deleteLibTableName(String var1, String var2, StringBuilder var3);

    public List<Map<String, Object>> getSchemeList(String var1) throws Exception;
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.offsetitem.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.offsetitem.entity.GcUnOffsetSelectOptionEO;
import java.util.List;
import java.util.Map;

public interface GcUnOffsetSelectOptionDao
extends IDbSqlGenericDAO<GcUnOffsetSelectOptionEO, String> {
    public List<Map<String, Object>> listUnOffsetConfigDatas(String var1);

    public GcUnOffsetSelectOptionEO getUnOffsetConfigDataById(String var1);

    public GcUnOffsetSelectOptionEO getPreNodeByIdAndOrder(Integer var1, String var2, String var3);

    public GcUnOffsetSelectOptionEO getNextNodeByIdAndOrder(Integer var1, String var2, String var3);

    public void deleteOfId(GcUnOffsetSelectOptionEO var1);

    public void updateDataBatch(List<GcUnOffsetSelectOptionEO> var1);

    public void updateData(GcUnOffsetSelectOptionEO var1);

    public Integer getLastNode();

    public void saveSelectOption(Map<Object, List<Map<String, Object>>> var1, String var2);

    public String getCurDataSource();
}


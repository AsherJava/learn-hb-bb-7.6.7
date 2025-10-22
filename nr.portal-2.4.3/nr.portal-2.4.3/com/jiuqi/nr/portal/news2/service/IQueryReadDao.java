/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news2.service;

import java.util.List;

public interface IQueryReadDao {
    public List<String> queryReadList(String var1, String var2);

    public Boolean addReadItem(String var1, String var2, String var3);

    public Boolean batchInsertReadItem(String var1, List<String> var2, String var3);

    public Boolean queryReadItem(String var1, String var2, String var3);

    public Boolean deleteItemByInfoId(String var1);

    public Boolean batchDeleteItemByInfoIds(List<String> var1);
}


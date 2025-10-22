/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 */
package com.jiuqi.nr.batch.summary.database.dao;

import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import java.util.Set;

public interface IGatherTableDao {
    public void doCopyTableAndAddCol(Set<String> var1, String var2) throws ModelValidateException, Exception;

    public void doCopyCheckTableAndAddCol(Set<String> var1, String var2) throws Exception;

    public void deleteCopyTable(Set<String> var1, String var2, boolean var3) throws Exception;

    public void deleteCopyCheckTable(Set<String> var1, String var2, boolean var3) throws Exception;

    public String getRealName(String var1, String var2);
}


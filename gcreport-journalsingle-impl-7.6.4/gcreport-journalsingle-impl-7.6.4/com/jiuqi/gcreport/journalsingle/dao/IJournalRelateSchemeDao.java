/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.journalsingle.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.journalsingle.entity.JournalRelateSchemeEO;
import java.util.List;

public interface IJournalRelateSchemeDao
extends IDbSqlGenericDAO<JournalRelateSchemeEO, String> {
    public List<JournalRelateSchemeEO> listRelateSchemes();

    public Integer deleteRelateScheme(String var1, String var2, String var3);

    public String getRelateSchemeId(String var1, String var2, String var3);
}


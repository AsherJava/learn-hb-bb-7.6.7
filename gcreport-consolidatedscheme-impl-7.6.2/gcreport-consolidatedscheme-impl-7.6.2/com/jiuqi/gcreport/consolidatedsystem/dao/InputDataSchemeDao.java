/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO;

public interface InputDataSchemeDao
extends IDbSqlGenericDAO<InputDataSchemeEO, String> {
    public InputDataSchemeEO getInputDataSchemeByDataSchemeKey(String var1);

    public void deleteInputDataSchemeByDataSchemeKey(String var1);
}


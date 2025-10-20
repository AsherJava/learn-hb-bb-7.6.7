/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.samecontrol.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOrgViewEO;

public interface SameCtrlOrgViewDao
extends IDbSqlGenericDAO<SameCtrlOrgViewEO, String> {
    public String getSameCtrlViewIdByOriginalViewId(String var1);

    public SameCtrlOrgViewEO getSameCtrlView(String var1);
}


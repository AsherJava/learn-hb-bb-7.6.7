/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 */
package com.jiuqi.gcreport.org.impl.fieldManager.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.fieldManager.entity.GcOrgTypeEO;

public interface GcOrgTypeDao
extends IBaseSqlGenericDAO<GcOrgTypeEO> {
    public void copyTypeVerData(OrgVersionVO var1, String var2);
}


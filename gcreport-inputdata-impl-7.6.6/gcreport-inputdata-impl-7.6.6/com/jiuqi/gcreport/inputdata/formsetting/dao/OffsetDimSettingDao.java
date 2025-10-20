/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.inputdata.formsetting.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.inputdata.formsetting.entity.OffsetDimSettingEO;

public interface OffsetDimSettingDao
extends IDbSqlGenericDAO<OffsetDimSettingEO, String> {
    public OffsetDimSettingEO getOffsetDimSettingByFormId(String var1);
}


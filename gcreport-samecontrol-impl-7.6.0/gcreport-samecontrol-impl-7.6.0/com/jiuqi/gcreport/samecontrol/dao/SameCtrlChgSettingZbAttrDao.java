/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.samecontrol.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingZbAttrEO;
import java.util.List;

public interface SameCtrlChgSettingZbAttrDao
extends IDbSqlGenericDAO<SameCtrlChgSettingZbAttrEO, String> {
    public List<SameCtrlChgSettingZbAttrEO> getOptionZbAttrByTaskAndShcemeId(String var1, String var2);

    public List<SameCtrlChgSettingZbAttrEO> getOptionZbAttrByFormKey(String var1);

    public int deleteZbAttributesByTaskAndShcemeId(String var1, String var2);
}


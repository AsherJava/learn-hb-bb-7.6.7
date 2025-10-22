/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.samecontrol.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingOptionEO;
import java.util.List;

public interface SameCtrlChgOptionDao
extends IDbSqlGenericDAO<SameCtrlChgSettingOptionEO, String> {
    public SameCtrlChgSettingOptionEO getOptionByTaskAndShcemeId(String var1, String var2);

    public List<SameCtrlChgSettingOptionEO> listOptions();

    public String getOptionIdByTaskAndShcemeId(String var1, String var2);

    public int updateOption(SameCtrlChgSettingOptionEO var1);
}


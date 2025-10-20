/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.clbr.dao;

import com.jiuqi.gcreport.clbr.entity.ClbrReceiveSettingEO;
import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import java.util.List;

public interface ClbrReceiveSettingDao
extends IBaseSqlGenericDAO<ClbrReceiveSettingEO> {
    public List<ClbrReceiveSettingEO> listByUserOrRole(String var1, String var2, String var3);

    public void deleteByIds(List<String> var1);

    public List<ClbrReceiveSettingEO> selectOrderListByPaging(Integer var1, Integer var2);
}


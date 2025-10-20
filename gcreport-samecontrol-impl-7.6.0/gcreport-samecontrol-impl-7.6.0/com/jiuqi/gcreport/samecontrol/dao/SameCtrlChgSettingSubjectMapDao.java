/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.samecontrol.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingSubjectMapEO;
import java.util.List;

public interface SameCtrlChgSettingSubjectMapDao
extends IDbSqlGenericDAO<SameCtrlChgSettingSubjectMapEO, String> {
    public List<SameCtrlChgSettingSubjectMapEO> getOptionSubjectMapByTaskAndShcemeId(String var1, String var2);

    public int deleteSubjectByIds(List<String> var1);

    public int deleteSubjectBySchemeMappingIds(List<String> var1);

    public List<SameCtrlChgSettingSubjectMapEO> listSubjectMappingBySchemeMappingId(String var1);
}


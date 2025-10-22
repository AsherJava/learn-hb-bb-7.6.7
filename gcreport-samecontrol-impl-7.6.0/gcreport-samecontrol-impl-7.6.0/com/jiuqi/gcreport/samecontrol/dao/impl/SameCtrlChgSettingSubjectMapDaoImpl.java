/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.samecontrol.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgSettingSubjectMapDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingSubjectMapEO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class SameCtrlChgSettingSubjectMapDaoImpl
extends GcDbSqlGenericDAO<SameCtrlChgSettingSubjectMapEO, String>
implements SameCtrlChgSettingSubjectMapDao {
    public SameCtrlChgSettingSubjectMapDaoImpl() {
        super(SameCtrlChgSettingSubjectMapEO.class);
    }

    @Override
    public List<SameCtrlChgSettingSubjectMapEO> getOptionSubjectMapByTaskAndShcemeId(String taskId, String schemeId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRL_SUBJECTMAP", (String)"i") + " \n  from " + "GC_SAMECTRL_SUBJECTMAP" + "  i \n  where i.taskId = ? \n  and i.schemeId = ? \n";
        return this.selectEntity(sql, new Object[]{taskId, schemeId});
    }

    @Override
    public int deleteSubjectByIds(List<String> deleteIds) {
        String sql = "  delete from GC_SAMECTRL_SUBJECTMAP   \n  where \n" + SqlUtils.getConditionOfIdsUseOr(deleteIds, (String)"id");
        return this.execute(sql, new ArrayList());
    }

    @Override
    public int deleteSubjectBySchemeMappingIds(List<String> schemeMappingIds) {
        String sql = "  delete from GC_SAMECTRL_SUBJECTMAP  \n  where \n" + SqlUtils.getConditionOfIdsUseOr(schemeMappingIds, (String)"schemeMappingId");
        return this.execute(sql, new ArrayList());
    }

    @Override
    public List<SameCtrlChgSettingSubjectMapEO> listSubjectMappingBySchemeMappingId(String schemeMappingId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRL_SUBJECTMAP", (String)"i") + " \n  from " + "GC_SAMECTRL_SUBJECTMAP" + "  i \n  where i.schemeMappingId = ? \n";
        return this.selectEntity(sql, new Object[]{schemeMappingId});
    }
}


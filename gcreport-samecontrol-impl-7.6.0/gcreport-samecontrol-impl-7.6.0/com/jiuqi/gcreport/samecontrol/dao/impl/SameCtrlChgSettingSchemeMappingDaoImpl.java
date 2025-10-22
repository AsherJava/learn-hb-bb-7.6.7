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
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgSettingSchemeMappingDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingSchemeMappingEO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class SameCtrlChgSettingSchemeMappingDaoImpl
extends GcDbSqlGenericDAO<SameCtrlChgSettingSchemeMappingEO, String>
implements SameCtrlChgSettingSchemeMappingDao {
    public SameCtrlChgSettingSchemeMappingDaoImpl() {
        super(SameCtrlChgSettingSchemeMappingEO.class);
    }

    @Override
    public List<SameCtrlChgSettingSchemeMappingEO> listSchemeMappingByTaskAndShcemeId(String taskId, String schemeId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRL_SCHEME_MAPPING", (String)"i") + " \n  from " + "GC_SAMECTRL_SCHEME_MAPPING" + "  i \n  where i.currTaskId = ? \n  and i.currSchemeId = ? \n";
        return this.selectEntity(sql, new Object[]{taskId, schemeId});
    }

    @Override
    public int deleteSchemeMappingByIds(List<String> deleteIds) {
        String sql = "  delete from GC_SAMECTRL_SCHEME_MAPPING   \n  where \n" + SqlUtils.getConditionOfIdsUseOr(deleteIds, (String)"id");
        return this.execute(sql, new ArrayList());
    }
}


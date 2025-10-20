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
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgSettingZbAttrDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingZbAttrEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class SameCtrlChgSettingZbAttrDaoImpl
extends GcDbSqlGenericDAO<SameCtrlChgSettingZbAttrEO, String>
implements SameCtrlChgSettingZbAttrDao {
    public SameCtrlChgSettingZbAttrDaoImpl() {
        super(SameCtrlChgSettingZbAttrEO.class);
    }

    @Override
    public List<SameCtrlChgSettingZbAttrEO> getOptionZbAttrByTaskAndShcemeId(String taskId, String schemeId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRL_ZBATTR", (String)"i") + " \n  from " + "GC_SAMECTRL_ZBATTR" + "  i \n  where i.taskId = ? \n  and i.schemeId = ? \n";
        return this.selectEntity(sql, new Object[]{taskId, schemeId});
    }

    @Override
    public List<SameCtrlChgSettingZbAttrEO> getOptionZbAttrByFormKey(String formKey) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRL_ZBATTR", (String)"i") + " \n  from " + "GC_SAMECTRL_ZBATTR" + "  i \n  where i.formKey = ? \n";
        return this.selectEntity(sql, new Object[]{formKey});
    }

    @Override
    public int deleteZbAttributesByTaskAndShcemeId(String taskId, String schemeId) {
        String sql = "  delete from GC_SAMECTRL_ZBATTR   \n  where taskId = ? \n  and schemeId = ? \n";
        return this.execute(sql, new Object[]{taskId, schemeId});
    }
}


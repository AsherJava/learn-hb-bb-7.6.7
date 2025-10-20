/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.samecontrol.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOptionDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingOptionEO;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class SameCtrlChgOptionDaoImpl
extends GcDbSqlGenericDAO<SameCtrlChgSettingOptionEO, String>
implements SameCtrlChgOptionDao {
    public SameCtrlChgOptionDaoImpl() {
        super(SameCtrlChgSettingOptionEO.class);
    }

    @Override
    public SameCtrlChgSettingOptionEO getOptionByTaskAndShcemeId(String taskId, String schemeId) {
        String sql = " select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLSETING_OPTION", (String)"i") + " \n  from " + "GC_SAMECTRLSETING_OPTION" + "  i \n  where i.taskId = ? \n  and i.schemeId = ? \n";
        List results = this.selectEntity(sql, new Object[]{taskId, schemeId});
        if (CollectionUtils.isEmpty((Collection)results)) {
            return null;
        }
        return (SameCtrlChgSettingOptionEO)((Object)results.get(0));
    }

    @Override
    public List<SameCtrlChgSettingOptionEO> listOptions() {
        String sql = "select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLSETING_OPTION", (String)"i") + " \n  from " + "GC_SAMECTRLSETING_OPTION" + "  i \n";
        List results = this.selectEntity(sql, new Object[0]);
        return results;
    }

    @Override
    public String getOptionIdByTaskAndShcemeId(String taskId, String schemeId) {
        String sql = "  select id as optionId\n  from GC_SAMECTRLSETING_OPTION  i \n  where i.taskId = ? \n  and i.schemeId = ? \n";
        List re = this.selectFirstList(String.class, sql, new Object[]{taskId, schemeId});
        if (CollectionUtils.isEmpty((Collection)re) || re.get(0) == null) {
            return null;
        }
        return (String)re.get(0);
    }

    @Override
    public int updateOption(SameCtrlChgSettingOptionEO eo) {
        return super.updateSelective((BaseEntity)eo);
    }
}


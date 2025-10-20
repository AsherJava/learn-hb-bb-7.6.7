/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.invest.investworkpaper.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.invest.investworkpaper.dao.InvestWorkPaperSettingDao;
import com.jiuqi.gcreport.invest.investworkpaper.entity.InvestWorkPaperSettingEO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InvestWorkPaperSettingDaoImpl
extends GcDbSqlGenericDAO<InvestWorkPaperSettingEO, String>
implements InvestWorkPaperSettingDao {
    public InvestWorkPaperSettingDaoImpl() {
        super(InvestWorkPaperSettingEO.class);
    }

    @Override
    public InvestWorkPaperSettingEO getInvestWorkPaperSetting(String userId, String taskId, String orgType, String systemId) {
        String executeSql;
        List investWorkPaperSettingEOS;
        String sql = "select %1$s from %2$s t where t.userId=? and t.taskId=? and t.systemId=? ";
        ArrayList<String> params = new ArrayList<String>();
        params.addAll(Arrays.asList(userId, taskId, systemId));
        if (orgType != null) {
            sql = sql + "and t.orgType=?";
            params.add(orgType);
        }
        if (CollectionUtils.isEmpty((Collection)(investWorkPaperSettingEOS = this.selectEntity(executeSql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTWORKPAPERSETTING", (String)"t"), "GC_INVESTWORKPAPERSETTING"), params)))) {
            return null;
        }
        return (InvestWorkPaperSettingEO)((Object)investWorkPaperSettingEOS.get(0));
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.conversion.conversionrate.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionrate.dao.ConversionRateGroupDao;
import com.jiuqi.gcreport.conversion.conversionrate.entity.ConversionRateGroupEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ConversionRateGroupDaoImpl
extends GcDbSqlGenericDAO<ConversionRateGroupEO, String>
implements ConversionRateGroupDao {
    public ConversionRateGroupDaoImpl() {
        super(ConversionRateGroupEO.class);
    }

    @Override
    public Map<String, String> queryGroups(String periodId, String systemId) {
        String sql = "  select DISTINCT scheme.id as ID,scheme.groupName as GROUPNAME \n  from  GC_CONV_RATE_G   scheme  \n  where   scheme.periodId = ? \n  and   scheme.systemId = ?\n";
        List rs = this.selectMap(sql, new Object[]{periodId, systemId});
        HashMap<String, String> resutlMap = new HashMap<String, String>();
        rs.forEach(v -> resutlMap.put(String.valueOf(v.get("ID")), String.valueOf(v.get("GROUPNAME"))));
        return resutlMap;
    }

    @Override
    public List<String> queryPeriodList(String systemId) {
        String sql = "  select DISTINCT scheme.periodId as PERIODID \n  from  GC_CONV_RATE_G   scheme  \n  where   scheme.systemId = ? \n";
        List rs = this.selectFirstList(String.class, sql, new Object[]{systemId});
        return rs;
    }

    @Override
    public ConversionRateGroupEO get(String periodId, String groupName, String systemId) {
        String sql = "  select " + ConversionRateGroupEO.getAllFieldSql("scheme") + "  from  " + "GC_CONV_RATE_G" + "   scheme  \n  where   scheme.periodId = ? \n  and   scheme.groupName = ? \n  and   scheme.systemId = ? \n";
        List dataList = this.selectEntity(sql, new Object[]{periodId, groupName, systemId});
        if (dataList != null && dataList.size() > 0) {
            return (ConversionRateGroupEO)((Object)dataList.get(0));
        }
        return null;
    }

    @Override
    public List<ConversionRateGroupEO> queryByPeriod(String periodId, String systemId) {
        String sql = "  select " + ConversionRateGroupEO.getAllFieldSql("scheme") + "  from  " + "GC_CONV_RATE_G" + "   scheme  \n  where   scheme.periodId = ? \n  and   scheme.systemId = ? \n";
        return this.selectEntity(sql, new Object[]{periodId, systemId});
    }

    @Override
    public List<ConversionRateGroupEO> queryBySystem(String systemId) {
        String sql = " select " + ConversionRateGroupEO.getAllFieldSql("scheme") + "  from  " + "GC_CONV_RATE_G" + "   scheme  \n  where   scheme.systemId = ? \n";
        return this.selectEntity(sql, new Object[]{systemId});
    }

    @Override
    public void deleteByPeriod(String periodId, String systemId) {
        String sql = "  delete from GC_CONV_RATE_G  where  periodId =?  \n  and   systemId = ? \n";
        this.execute(sql, new Object[]{periodId, systemId});
    }

    @Override
    public void deleteBySystem(String systemId) {
        String sql = "  delete from GC_CONV_RATE_G  where    systemId = ? \n";
        this.execute(sql, new Object[]{systemId});
    }

    @Override
    public void updateGroupName(String id, String groupName) {
        String sql = "  update GC_CONV_RATE_G  scheme \n   set scheme.groupName = ?  \n  where   scheme.id = ? \n";
        this.execute(sql, new Object[]{id, groupName});
    }

    @Override
    public List<ConversionRateGroupEO> queryByIds(List<String> idList) {
        if (idList != null && idList.size() > 0) {
            String insql = "and  " + SqlUtils.getConditionOfIdsUseOr(idList, (String)"scheme.id") + " \n";
            String sql = "  select  " + ConversionRateGroupEO.getAllFieldSql("scheme") + " \n  from  " + "GC_CONV_RATE_G" + "   scheme  \n  where   1=1  \n" + insql;
            return this.selectEntity(sql, new Object[0]);
        }
        return null;
    }
}


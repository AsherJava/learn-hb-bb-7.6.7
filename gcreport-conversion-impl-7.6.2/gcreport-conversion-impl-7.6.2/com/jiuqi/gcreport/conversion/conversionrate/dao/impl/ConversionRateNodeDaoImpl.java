/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.conversion.conversionrate.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionrate.dao.ConversionRateNodeDao;
import com.jiuqi.gcreport.conversion.conversionrate.entity.ConversionRateNodeEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ConversionRateNodeDaoImpl
extends GcDbSqlGenericDAO<ConversionRateNodeEO, String>
implements ConversionRateNodeDao {
    public ConversionRateNodeDaoImpl() {
        super(ConversionRateNodeEO.class);
    }

    @Override
    public List<ConversionRateNodeEO> queryByGroupid(String groupID) {
        String sql = "select " + ConversionRateNodeEO.getAllFieldSql("scheme") + "  from  " + "GC_CONV_RATE_T" + "   scheme  \n  where   scheme.rategroupid = ? \n";
        return this.selectEntity(sql, new Object[]{groupID});
    }

    @Override
    public ConversionRateNodeEO get(String groupId, String sourceCurrencyCode, String targetCurrencyCode) {
        String sql = "  select " + ConversionRateNodeEO.getAllFieldSql("scheme") + "  from  " + "GC_CONV_RATE_T" + "   scheme  \n  where   scheme.rategroupid = ? \n  and   scheme.sourceCurrencyCode = ? \n  and   scheme.targetCurrencyCode = ? \n";
        List dataList = this.selectEntity(sql, new Object[]{groupId, sourceCurrencyCode, targetCurrencyCode});
        if (dataList != null && dataList.size() > 0) {
            return (ConversionRateNodeEO)((Object)dataList.get(0));
        }
        return null;
    }

    @Override
    public void deleteByGroupId(String groupId) {
        String sql = "  delete from GC_CONV_RATE_T   \n  where  rategroupid =?  \n";
        this.execute(sql, new Object[]{groupId});
    }

    @Override
    public List<ConversionRateNodeEO> queryByIds(List<String> idList) {
        if (idList != null && idList.size() > 0) {
            String insql = "and  " + SqlUtils.getConditionOfIdsUseOr(idList, (String)"scheme.id") + " \n";
            String sql = "  select  " + ConversionRateNodeEO.getAllFieldSql("scheme") + " \n  from  " + "GC_CONV_RATE_T" + "   scheme  \n  where   1=1  \n" + insql;
            return this.selectEntity(sql, new Object[0]);
        }
        return null;
    }
}


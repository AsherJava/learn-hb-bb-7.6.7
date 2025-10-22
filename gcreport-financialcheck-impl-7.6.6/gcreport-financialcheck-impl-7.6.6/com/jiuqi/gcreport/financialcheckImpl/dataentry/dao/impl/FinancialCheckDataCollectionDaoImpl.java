/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.dao.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.dao.FinancialCheckDataCollectionDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialCheckDataCollectionDaoImpl
extends AbstractEntDbSqlGenericDAO<GcRelatedItemEO>
implements FinancialCheckDataCollectionDao {
    public FinancialCheckDataCollectionDaoImpl() {
        super(GcRelatedItemEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_RELATED_ITEM");
    }

    @Override
    public List<GcRelatedItemEO> listSourceCFNotExistItem(String unitId, String dataTime) {
        Integer acctYear = ConverterUtils.getAsInteger((Object)dataTime.substring(0, 4));
        Integer acctPeriod = ConverterUtils.getAsInteger((Object)dataTime.substring(7));
        String sql = "select *  FROM GC_RELATED_ITEM a WHERE a.unitId = ? \n and a.ACCTYEAR = ? and a.ACCTPERIOD = ?  and NOT  EXISTS ( \n select 1 from GC_FINCUBES_CF_Y b WHERE a.SRCITEMID = b.id\n ) \n AND a.INPUTWAY  = 'DATACOLLECTION_CF' ";
        return this.selectEntity(sql, new Object[]{unitId, acctYear, acctPeriod});
    }

    @Override
    public List<GcRelatedItemEO> listSourceDIMNotExistItem(String unitId, String dataTime) {
        Integer acctYear = ConverterUtils.getAsInteger((Object)dataTime.substring(0, 4));
        Integer acctPeriod = ConverterUtils.getAsInteger((Object)dataTime.substring(7));
        String sql = "select *  FROM GC_RELATED_ITEM a WHERE a.unitId = ? \n and a.ACCTYEAR = ? and a.ACCTPERIOD = ?  and NOT  EXISTS ( \n select 1 from GC_FINCUBES_DIM_Y b WHERE a.SRCITEMID = b.id\n ) \n AND a.INPUTWAY  = 'DATACOLLECTION_DIM' ";
        return this.selectEntity(sql, new Object[]{unitId, acctYear, acctPeriod});
    }
}


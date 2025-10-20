/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.invest.investbillcarryover.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.invest.investbillcarryover.dao.InvestBillCarryOverSettingDao;
import com.jiuqi.gcreport.invest.investbillcarryover.entity.InvestBillCarryOverSettingEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InvestBillCarryOverSettingDaoImpl
extends GcDbSqlGenericDAO<InvestBillCarryOverSettingEO, String>
implements InvestBillCarryOverSettingDao {
    public InvestBillCarryOverSettingDaoImpl() {
        super(InvestBillCarryOverSettingEO.class);
    }

    @Override
    public List<InvestBillCarryOverSettingEO> listInvestBillCarryOverSetting(String carryOverSchemeId) {
        String sql = "select " + SqlUtils.getColumnsSqlByEntity(InvestBillCarryOverSettingEO.class, (String)"e") + " from " + "GC_INVESTCARRYOVERSETTING" + " e \n where e.carryOverSchemeId=? order by e.ORDINAL desc";
        return this.selectEntity(sql, new Object[]{carryOverSchemeId});
    }
}


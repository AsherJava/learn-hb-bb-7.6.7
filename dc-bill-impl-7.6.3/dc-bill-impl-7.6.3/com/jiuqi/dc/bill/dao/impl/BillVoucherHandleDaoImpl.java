/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.dc.bill.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.bill.dao.BillVoucherHandleDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class BillVoucherHandleDaoImpl
extends BaseDataCenterDaoImpl
implements BillVoucherHandleDao {
    @Override
    public void insertVouchers(List<String> voucherIds, Integer status) {
        if (CollectionUtils.isEmpty(voucherIds)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ETL_PROCESS_VOUCHER \n");
        sql.append("  (ID, \n");
        sql.append("   VCHRID, \n");
        sql.append("   UNITCODE, \n");
        sql.append("   ACCTYEAR, \n");
        sql.append("   ACCTPERIOD, \n");
        sql.append("   VCHRSTATE, \n");
        sql.append("   CREATETIME, \n");
        sql.append("   HANDLESTATE, \n");
        sql.append("   DELETESTATE, \n");
        sql.append("   VCHRNUM, \n");
        sql.append("   VCHRTYPECODE, \n");
        sql.append("   CREATEDATE) \n");
        sql.append("  SELECT ? AS ID, \n");
        sql.append("         T.ID AS VCHRID, \n");
        sql.append("         T.UNITCODE, \n");
        sql.append("         T.ACCTYEAR, \n");
        sql.append("         T.ACCTPERIOD, \n");
        sql.append("         ? AS VCHRSTATE, \n");
        sql.append("         ? AS CREATETIME, \n");
        sql.append("         0 AS HANDLESTATE, \n");
        sql.append("         0 AS DELETESTATE, \n");
        sql.append("         T.VCHRNUM, \n");
        sql.append("         T.VCHRTYPECODE, \n");
        sql.append("         T.CREATEDATE \n");
        sql.append("    FROM DC_BILL_VOUCHER T \n");
        sql.append("   WHERE ").append(SqlBuildUtil.getStrInCondi((String)"T.ID", voucherIds)).append(" \n");
        this.getJdbcTemplate().update(sql.toString(), new Object[]{UUIDUtils.newHalfGUIDStr(), status, DateUtils.now()});
    }
}


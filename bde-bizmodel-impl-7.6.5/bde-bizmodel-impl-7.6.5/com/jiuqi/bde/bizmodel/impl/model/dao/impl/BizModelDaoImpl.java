/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.StopFlagEnum
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.bde.bizmodel.impl.model.dao.impl;

import com.jiuqi.bde.bizmodel.impl.model.dao.BizModelDao;
import com.jiuqi.dc.base.common.enums.StopFlagEnum;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import java.math.BigDecimal;
import org.springframework.stereotype.Repository;

@Repository
public class BizModelDaoImpl
extends BaseDataCenterDaoImpl
implements BizModelDao {
    @Override
    public int updateStopFlagById(String id, StopFlagEnum stopFlag, String tableName) {
        String sql = "   UPDATE " + tableName + " SET STOPFLAG = ?   WHERE ID = ? \n";
        return this.getJdbcTemplate().update(sql, new Object[]{stopFlag.getCode(), id});
    }

    @Override
    public int updateOrdinalById(String id, BigDecimal ordinal, String tableName) {
        String sql = "   UPDATE " + tableName + " SET ORDINAL = ?   WHERE ID = ? \n";
        return this.getJdbcTemplate().update(sql, new Object[]{ordinal, id});
    }
}


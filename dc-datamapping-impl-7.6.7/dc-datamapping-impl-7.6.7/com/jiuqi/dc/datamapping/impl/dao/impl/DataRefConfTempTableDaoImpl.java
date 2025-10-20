/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.dc.datamapping.impl.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfTempTableDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DataRefConfTempTableDaoImpl
extends BaseDataCenterDaoImpl
implements DataRefConfTempTableDao {
    private static final String INIT_SQL = "INSERT INTO DC_TEMP_REFCONFQUERY (ID) VALUES (?)";
    private static final String CLEAN_SQL = "DELETE FROM DC_TEMP_REFCONFQUERY WHERE 1 = 1";

    @Override
    public void initTmpTable(List<String> odsIdList) {
        if (CollectionUtils.isEmpty(odsIdList)) {
            return;
        }
        ArrayList<Object[]> params = new ArrayList<Object[]>(1);
        for (String odsId : odsIdList) {
            params.add(new Object[]{odsId});
        }
        this.getJdbcTemplate().batchUpdate(INIT_SQL, params);
    }

    @Override
    public void cleanTmpTable() {
        this.getJdbcTemplate().batchUpdate(new String[]{CLEAN_SQL});
    }
}


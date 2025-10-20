/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.dc.base.impl.temptable.dao.impl;

import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.impl.temptable.dao.DcTempCodeDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DcTempCodeDaoImpl
extends BaseDataCenterDaoImpl
implements DcTempCodeDao {
    @Override
    public void batchInsert(List<String> codes) {
        String sql = "INSERT INTO DC_TEMP_CODE (CODE) VALUES(?)";
        ArrayList<Object[]> params = new ArrayList<Object[]>();
        for (String code : codes) {
            params.add(new Object[]{code});
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql, params);
    }

    @Override
    public void cleanTemp() {
        String sql = "DELETE FROM DC_TEMP_CODE";
        OuterDataSourceUtils.getJdbcTemplate().update(sql);
    }
}


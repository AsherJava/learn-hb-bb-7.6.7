/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.gc.financialcubes.common.dao.impl;

import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.gc.financialcubes.common.dao.TempCodeDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TempCodeDaoImpl
extends BaseDataCenterDaoImpl
implements TempCodeDao {
    @Override
    public void batchInsert(List<String> codes) {
        String sql = "INSERT INTO DC_TEMP_CODE (CODE) VALUES(?)";
        ArrayList<Object[]> params = new ArrayList<Object[]>();
        for (String code : codes) {
            params.add(new Object[]{code});
        }
        this.batchUpdate(sql, params);
    }

    @Override
    public void cleanTemp() {
        String sql = "DELETE FROM DC_TEMP_CODE";
        this.update(sql, new Object[0]);
    }
}


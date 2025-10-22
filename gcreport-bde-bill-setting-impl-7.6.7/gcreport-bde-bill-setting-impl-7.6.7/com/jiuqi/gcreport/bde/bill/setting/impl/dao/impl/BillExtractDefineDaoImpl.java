/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.gcreport.bde.bill.setting.impl.dao.BillExtractDefineDao;
import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillExtractDefineEO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BillExtractDefineDaoImpl
implements BillExtractDefineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BillExtractDefineEO> listByBillSettingType(String billSettingType) {
        Assert.isNotEmpty((String)billSettingType);
        String LIST_BY_SETTINGTYPE_SQL = "SELECT ID, BILLSETTINGTYPE, ORDINAL FROM BDE_BILLEXTRACT_DEFINE WHERE 1 = 1 AND BILLSETTINGTYPE = ? ORDER BY ORDINAL DESC";
        return this.jdbcTemplate.query("SELECT ID, BILLSETTINGTYPE, ORDINAL FROM BDE_BILLEXTRACT_DEFINE WHERE 1 = 1 AND BILLSETTINGTYPE = ? ORDER BY ORDINAL DESC", (RowMapper)new BeanPropertyRowMapper(BillExtractDefineEO.class), new Object[]{billSettingType});
    }

    @Override
    public void batchInsert(String billSettingType, List<BillExtractDefineEO> createDeineList) {
        Assert.isNotEmpty((String)billSettingType);
        if (CollectionUtils.isEmpty(createDeineList)) {
            return;
        }
        String INSERT_SQL = "INSERT INTO BDE_BILLEXTRACT_DEFINE (ID, BILLSETTINGTYPE, ORDINAL) VALUES(?, ?, ?)";
        ArrayList params = new ArrayList(createDeineList.size());
        createDeineList.forEach(defineEo -> params.add(new Object[]{defineEo.getId(), billSettingType, defineEo.getOrdinal()}));
        this.jdbcTemplate.batchUpdate("INSERT INTO BDE_BILLEXTRACT_DEFINE (ID, BILLSETTINGTYPE, ORDINAL) VALUES(?, ?, ?)", params);
    }

    @Override
    public void batchDelete(String billSettingType, List<String> deleteDeineList) {
        Assert.isNotEmpty((String)billSettingType);
        if (CollectionUtils.isEmpty(deleteDeineList)) {
            return;
        }
        String DELETE_SQL = "DELETE FROM BDE_BILLEXTRACT_DEFINE WHERE  1 = 1 AND %1$s AND BILLSETTINGTYPE = ? ";
        this.jdbcTemplate.update(String.format("DELETE FROM BDE_BILLEXTRACT_DEFINE WHERE  1 = 1 AND %1$s AND BILLSETTINGTYPE = ? ", SqlBuildUtil.getStrInCondi((String)"ID", deleteDeineList)), new Object[]{billSettingType});
    }
}


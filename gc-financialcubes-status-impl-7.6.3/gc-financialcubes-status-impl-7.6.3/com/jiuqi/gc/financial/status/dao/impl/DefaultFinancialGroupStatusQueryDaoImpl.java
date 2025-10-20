/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gc.financial.status.dto.FinancialGroupStatusDTO
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gc.financial.status.dao.impl;

import com.jiuqi.gc.financial.status.dao.DefaultFinancialGroupStatusQueryDao;
import com.jiuqi.gc.financial.status.dto.FinancialGroupStatusDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultFinancialGroupStatusQueryDaoImpl
implements DefaultFinancialGroupStatusQueryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FinancialGroupStatusDTO> listAllFinancialGroupStatusDataByModuleCode(String moduleCode) {
        String sql = " SELECT DATATIME,PERIODTYPE,STATUS FROM GC_FINANCIAL_GROUP_STATUS  WHERE MODULECODE = ? ";
        return this.jdbcTemplate.query(sql, new Object[]{moduleCode}, (rs, rowNum) -> {
            FinancialGroupStatusDTO financialGroupStatusDTO = new FinancialGroupStatusDTO();
            financialGroupStatusDTO.setDataTime(rs.getString(1));
            financialGroupStatusDTO.setPeriodType(rs.getString(2));
            financialGroupStatusDTO.setStatus(rs.getString(3));
            return financialGroupStatusDTO;
        });
    }
}


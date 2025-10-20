/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gc.financial.status.dto.FinancialUnitStatusDTO
 *  com.jiuqi.gc.financial.status.enums.FinancialStatusEnum
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gc.financial.status.dao.impl;

import com.jiuqi.gc.financial.status.dao.DefaultFinancialUnitStatusQueryDao;
import com.jiuqi.gc.financial.status.dto.FinancialUnitStatusDTO;
import com.jiuqi.gc.financial.status.enums.FinancialStatusEnum;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultFinancialUnitStatusQueryDaoImpl
implements DefaultFinancialUnitStatusQueryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FinancialUnitStatusDTO> listFinancialUnitStatusData(String moduleCode, String dataTime) {
        String sql = "SELECT UNITCODE,STATUS,VALIDTIME,INVALIDTIME \n  FROM GC_FINANCIAL_UNIT_STATUS  \n WHERE 1=1 \n   AND MODULECODE = ? \n   AND DATATIME = ? \n";
        Date now = new Date();
        return this.jdbcTemplate.query(sql, new Object[]{moduleCode, dataTime}, (rs, rowNum) -> {
            FinancialUnitStatusDTO financialUnitStatusDTO = new FinancialUnitStatusDTO();
            financialUnitStatusDTO.setDataTime(dataTime);
            financialUnitStatusDTO.setUnitCode(rs.getString(1));
            financialUnitStatusDTO.setStatus(FinancialStatusEnum.getStatusByCode((String)rs.getString(2)));
            financialUnitStatusDTO.setValidTime((Date)rs.getDate(3));
            financialUnitStatusDTO.setInvalidTime((Date)rs.getDate(4));
            return financialUnitStatusDTO;
        });
    }
}


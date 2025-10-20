/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 */
package com.jiuqi.common.financialcubes.fincubeadjustvchr.impl;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.financialcubes.dto.FinancialCubesAdjustTaskDTO;
import com.jiuqi.common.financialcubes.fincubeadjustvchr.FincubeCommonAdjustVchrDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FincubeCommonAdjustVchrDaoImpl
implements FincubeCommonAdjustVchrDao {
    @Override
    public String getDeleteIdByVchrIdList(Collection<String> vchrIdList) {
        String sql = " SELECT id FROM DC_ADJUSTVCHRITEM     WHERE " + SqlBuildUtil.getStrInCondi((String)"VCHRID", new ArrayList<String>(vchrIdList)) + " \n";
        return (String)OuterDataSourceUtils.getJdbcTemplate().query(sql, rs -> {
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        });
    }

    @Override
    public List<FinancialCubesAdjustTaskDTO> listAgingVchrMasterDimByVchrId(Collection<String> vchrIdList, String deleteId) {
        String sql = " SELECT ADJUST.UNITCODE,ADJUST.ACCTYEAR,ADJUST.ACCTPERIOD,ADJUST.PERIODTYPE \n      FROM DC_ADJUSTVCHRITEM ADJUST \n     WHERE " + SqlBuildUtil.getStrInCondi((String)"VCHRID", new ArrayList<String>(vchrIdList)) + " \n       AND BIZDATE IS NOT NULL \n     GROUP BY ADJUST.UNITCODE,ADJUST.ACCTYEAR,ADJUST.ACCTPERIOD,ADJUST.PERIODTYPE \n";
        return OuterDataSourceUtils.getJdbcTemplate().query(sql, (rs, rowNum) -> {
            FinancialCubesAdjustTaskDTO vchrMasterDim = new FinancialCubesAdjustTaskDTO();
            vchrMasterDim.setUnitCode(rs.getString(1));
            vchrMasterDim.setAcctYear(rs.getInt(2));
            vchrMasterDim.setAcctPeriod(rs.getInt(3));
            vchrMasterDim.setPeriodType(rs.getString(4));
            vchrMasterDim.setDeleteId(deleteId);
            return vchrMasterDim;
        });
    }

    @Override
    public List<FinancialCubesAdjustTaskDTO> listVchrMasterDimByVchrId(Collection<String> vchrIdList, String deleteId) {
        String sql = " SELECT ADJUST.UNITCODE,ADJUST.ACCTYEAR,ADJUST.ACCTPERIOD,ADJUST.PERIODTYPE \n      FROM DC_ADJUSTVCHRITEM ADJUST \n     WHERE " + SqlBuildUtil.getStrInCondi((String)"VCHRID", new ArrayList<String>(vchrIdList)) + " \n     GROUP BY ADJUST.UNITCODE,ADJUST.ACCTYEAR,ADJUST.ACCTPERIOD,ADJUST.PERIODTYPE \n";
        return OuterDataSourceUtils.getJdbcTemplate().query(sql, (rs, rowNum) -> {
            FinancialCubesAdjustTaskDTO vchrMasterDim = new FinancialCubesAdjustTaskDTO();
            vchrMasterDim.setUnitCode(rs.getString(1));
            vchrMasterDim.setAcctYear(rs.getInt(2));
            vchrMasterDim.setAcctPeriod(rs.getInt(3));
            vchrMasterDim.setPeriodType(rs.getString(4));
            vchrMasterDim.setDeleteId(deleteId);
            return vchrMasterDim;
        });
    }

    @Override
    public List<FinancialCubesAdjustTaskDTO> listVchrMasterCfByVchrId(Collection<String> vchrIdList, String deleteId) {
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        String sql = " SELECT ADJUST.UNITCODE,ADJUST.ACCTYEAR,ADJUST.ACCTPERIOD,ADJUST.PERIODTYPE \n      FROM DC_ADJUSTVCHRITEM ADJUST \n      LEFT JOIN DC_TEMP_CODE CASH_TEMP ON ADJUST.SUBJECTCODE = CASH_TEMP.CODE \n     WHERE " + SqlBuildUtil.getStrInCondi((String)"VCHRID", new ArrayList<String>(vchrIdList)) + " \n       AND (CASH_TEMP.CODE IS NOT NULL OR " + jdbcTemplate.getIDbSqlHandler().judgeEmpty("ADJUST.CFITEMCODE", false) + ") \n     GROUP BY ADJUST.UNITCODE,ADJUST.ACCTYEAR,ADJUST.ACCTPERIOD,ADJUST.PERIODTYPE \n";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            FinancialCubesAdjustTaskDTO vchrMasterDim = new FinancialCubesAdjustTaskDTO();
            vchrMasterDim.setUnitCode(rs.getString(1));
            vchrMasterDim.setAcctYear(rs.getInt(2));
            vchrMasterDim.setAcctPeriod(rs.getInt(3));
            vchrMasterDim.setPeriodType(rs.getString(4));
            vchrMasterDim.setDeleteId(deleteId);
            return vchrMasterDim;
        });
    }
}


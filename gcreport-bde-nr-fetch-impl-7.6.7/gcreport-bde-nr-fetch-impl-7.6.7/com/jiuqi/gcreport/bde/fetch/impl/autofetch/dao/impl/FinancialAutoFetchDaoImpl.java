/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.template.StringRowMapper
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.dao.impl;

import com.jiuqi.bde.common.template.StringRowMapper;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dao.FinancialAutoFetchDao;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FetchRangCondition;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialAutoFetchDaoImpl
extends BaseDataCenterDaoImpl
implements FinancialAutoFetchDao {
    private static final String BALANCEQUERYSQL = "SELECT SUBJECTCODE FROM %1$s_%2$s WHERE 1=1 AND MDCODE = ? AND DATATIME = ? AND SN >=? AND SN<=? GROUP BY SUBJECTCODE";
    private static final String CFQUERYSQL = "SELECT CFITEMCODE FROM %1$s_%2$s WHERE 1=1 AND MDCODE = ? AND DATATIME = ? AND SN >=? AND SN<=? GROUP BY CFITEMCODE";
    private static final String AGINGQUERYSQL = "SELECT SUBJECTCODE FROM %1$s_%2$s WHERE 1=1 AND MDCODE = ? AND DATATIME = ? AND SN >=? AND SN<=? GROUP BY SUBJECTCODE";

    @Override
    public List<String> getCashCodesByCf(FetchRangCondition financialFetchCondition) {
        return this.query(String.format(CFQUERYSQL, financialFetchCondition.getFinancialTableName(), financialFetchCondition.getPeriodType().getCode()), (RowMapper)new StringRowMapper(), new Object[]{financialFetchCondition.getOrgCode(), financialFetchCondition.getDataTime(), financialFetchCondition.getStartBatchNum(), financialFetchCondition.getEndBatchNum()});
    }

    @Override
    public List<String> getSubjectCodesByDim(FetchRangCondition financialFetchCondition) {
        return this.query(String.format("SELECT SUBJECTCODE FROM %1$s_%2$s WHERE 1=1 AND MDCODE = ? AND DATATIME = ? AND SN >=? AND SN<=? GROUP BY SUBJECTCODE", financialFetchCondition.getFinancialTableName(), financialFetchCondition.getPeriodType().getCode()), (RowMapper)new StringRowMapper(), new Object[]{financialFetchCondition.getOrgCode(), financialFetchCondition.getDataTime(), financialFetchCondition.getStartBatchNum(), financialFetchCondition.getEndBatchNum()});
    }

    @Override
    public List<String> getSubjectCodesByAging(FetchRangCondition financialFetchCondition) {
        return this.query(String.format("SELECT SUBJECTCODE FROM %1$s_%2$s WHERE 1=1 AND MDCODE = ? AND DATATIME = ? AND SN >=? AND SN<=? GROUP BY SUBJECTCODE", financialFetchCondition.getFinancialTableName(), financialFetchCondition.getPeriodType().getCode()), (RowMapper)new StringRowMapper(), new Object[]{financialFetchCondition.getOrgCode(), financialFetchCondition.getDataTime(), financialFetchCondition.getStartBatchNum(), financialFetchCondition.getEndBatchNum()});
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.penetrate.impl.core.model.res;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.penetrate.impl.common.RowTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.common.base.util.CollectionUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class BalanceResultSetExtractor<AcctAssist extends IAcctAssist>
implements ResultSetExtractor<List<PenetrateBalance>> {
    private List<AssistMappingBO<AcctAssist>> assistList;

    public BalanceResultSetExtractor(List<AssistMappingBO<AcctAssist>> assistList) {
        this.assistList = assistList == null ? CollectionUtils.newArrayList() : assistList;
    }

    public List<PenetrateBalance> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashSet<String> columnSet = new HashSet<String>(rs.getMetaData().getColumnCount());
        for (int colIdx = 1; colIdx <= rs.getMetaData().getColumnCount(); ++colIdx) {
            columnSet.add(rs.getMetaData().getColumnLabel(colIdx).toUpperCase());
        }
        ArrayList<PenetrateBalance> result = new ArrayList<PenetrateBalance>(rs.getRow());
        PenetrateBalance row = null;
        while (rs.next()) {
            row = new PenetrateBalance();
            row.put("ROWTYPE", RowTypeEnum.DETAIL.ordinal());
            if (columnSet.contains("ACCTORGCODE")) {
                row.put("ACCTORGCODE", rs.getString("ACCTORGCODE"));
            }
            if (columnSet.contains("ACCTORGNAME")) {
                row.put("ACCTORGNAME", rs.getString("ACCTORGNAME"));
            }
            if (columnSet.contains("ASSISTCODE")) {
                row.put("ASSISTCODE", rs.getString("ASSISTCODE"));
            }
            if (columnSet.contains("ASSISTNAME")) {
                row.put("ASSISTNAME", rs.getString("ASSISTNAME"));
            }
            row.put("SUBJECTCODE", rs.getString("SUBJECTCODE"));
            row.put("SUBJECTNAME", rs.getString("SUBJECTNAME"));
            row.put("NC", rs.getBigDecimal("NC"));
            row.put("ORGNNC", rs.getBigDecimal("ORGNNC"));
            row.put("QC", rs.getBigDecimal("QC"));
            row.put("ORGNQC", rs.getBigDecimal("ORGNQC"));
            for (AssistMappingBO<AcctAssist> assistMapping : this.assistList) {
                row.put(assistMapping.getAssistCode(), rs.getString(assistMapping.getAssistCode()));
                row.put(ModelExecuteUtil.getAssistFieldName((String)assistMapping.getAssistCode()), rs.getString(ModelExecuteUtil.getAssistFieldName((String)assistMapping.getAssistCode())));
            }
            row.put("DEBIT", rs.getBigDecimal("DEBIT"));
            row.put("CREDIT", rs.getBigDecimal("CREDIT"));
            row.put("ORGND", rs.getBigDecimal("ORGND"));
            row.put("ORGNC", rs.getBigDecimal("ORGNC"));
            row.put("DSUM", rs.getBigDecimal("DSUM"));
            row.put("CSUM", rs.getBigDecimal("CSUM"));
            row.put("ORGNDSUM", rs.getBigDecimal("ORGNDSUM"));
            row.put("ORGNCSUM", rs.getBigDecimal("ORGNCSUM"));
            row.put("YE", rs.getBigDecimal("YE"));
            row.put("ORGNYE", rs.getBigDecimal("ORGNYE"));
            result.add(row);
        }
        return result;
    }
}


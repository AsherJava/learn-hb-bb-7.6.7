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
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.common.base.util.CollectionUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class DetailLedgerResultSetExtractor<AcctAssist extends IAcctAssist>
implements ResultSetExtractor<List<PenetrateDetailLedger>> {
    private List<AssistMappingBO<AcctAssist>> assistCodeList;

    public DetailLedgerResultSetExtractor(List<AssistMappingBO<AcctAssist>> assistCodeList) {
        this.assistCodeList = assistCodeList == null ? CollectionUtils.newArrayList() : assistCodeList;
    }

    public List<PenetrateDetailLedger> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<PenetrateDetailLedger> result = new ArrayList<PenetrateDetailLedger>(rs.getRow());
        PenetrateDetailLedger row = null;
        while (rs.next()) {
            row = new PenetrateDetailLedger();
            row.put("ROWTYPE", rs.getInt("ROWTYPE"));
            row.put("ID", rs.getString("ID"));
            row.put("VCHRID", rs.getString("VCHRID"));
            row.put("ORIENT", rs.getInt("ORIENT"));
            row.put("ACCTYEAR", rs.getInt("ACCTYEAR"));
            row.put("ACCTPERIOD", rs.getInt("ACCTPERIOD"));
            row.put("ACCTDAY", rs.getString("ACCTDAY"));
            row.put("VCHRTYPE", rs.getString("VCHRTYPE"));
            row.put("SUBJECTCODE", rs.getString("SUBJECTCODE"));
            row.put("SUBJECTNAME", rs.getString("SUBJECTNAME"));
            for (AssistMappingBO<AcctAssist> assistMapping : this.assistCodeList) {
                row.put(assistMapping.getAssistCode(), rs.getString(assistMapping.getAssistCode()));
                row.put(ModelExecuteUtil.getAssistFieldName((String)assistMapping.getAssistCode()), rs.getString(ModelExecuteUtil.getAssistFieldName((String)assistMapping.getAssistCode())));
            }
            row.put("DIGEST", rs.getString("DIGEST"));
            row.put("DEBIT", rs.getBigDecimal("DEBIT"));
            row.put("CREDIT", rs.getBigDecimal("CREDIT"));
            row.put("ORGND", rs.getBigDecimal("ORGND"));
            row.put("ORGNC", rs.getBigDecimal("ORGNC"));
            row.put("YE", rs.getBigDecimal("YE"));
            row.put("ORGNYE", rs.getBigDecimal("ORGNYE"));
            result.add(row);
        }
        return result;
    }
}


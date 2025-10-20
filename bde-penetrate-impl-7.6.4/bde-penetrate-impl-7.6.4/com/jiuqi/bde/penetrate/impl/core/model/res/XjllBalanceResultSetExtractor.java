/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.penetrate.impl.core.model.res;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.RowTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class XjllBalanceResultSetExtractor<AcctAssist extends IAcctAssist>
implements ResultSetExtractor<List<PenetrateBalance>> {
    private PenetrateBaseDTO condi;
    private List<AssistMappingBO<AcctAssist>> assistList;

    public XjllBalanceResultSetExtractor(PenetrateBaseDTO condi, List<AssistMappingBO<AcctAssist>> assistList) {
        this.condi = condi;
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
            row.put("CASHCODE", rs.getString("MD_CFITEM"));
            row.put("CASHNAME", rs.getString(ModelExecuteUtil.getAssistFieldName((String)"MD_CFITEM")));
            if (!StringUtils.isEmpty((String)this.condi.getSubjectCode())) {
                row.put("SUBJECTCODE", rs.getString("MD_ACCTSUBJECT"));
                row.put("SUBJECTNAME", rs.getString(ModelExecuteUtil.getAssistFieldName((String)"MD_ACCTSUBJECT")));
            }
            for (AssistMappingBO<AcctAssist> assistMapping : this.assistList) {
                if ("SUBJECTCODE".equals(assistMapping.getAssistCode())) continue;
                row.put(assistMapping.getAssistCode(), rs.getString(assistMapping.getAssistCode()));
                row.put(ModelExecuteUtil.getAssistFieldName((String)assistMapping.getAssistCode()), rs.getString(ModelExecuteUtil.getAssistFieldName((String)assistMapping.getAssistCode())));
            }
            row.put("BQNUM", rs.getBigDecimal("BQNUM"));
            row.put("LJNUM", rs.getBigDecimal("LJNUM"));
            row.put("WBQNUM", rs.getBigDecimal("WBQNUM"));
            row.put("WLJNUM", rs.getBigDecimal("WLJNUM"));
            result.add(row);
        }
        return result;
    }
}


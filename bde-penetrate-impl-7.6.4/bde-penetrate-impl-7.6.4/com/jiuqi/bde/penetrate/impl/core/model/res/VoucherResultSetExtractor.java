/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist
 *  com.jiuqi.bde.common.i18n.util.BdeI18nHelper
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.penetrate.impl.core.model.res;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist;
import com.jiuqi.bde.common.i18n.util.BdeI18nHelper;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.penetrate.impl.common.RowTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateVoucher;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class VoucherResultSetExtractor<AcctAssist extends IAcctAssist>
implements ResultSetExtractor<List<PenetrateVoucher>> {
    private final BdeI18nHelper bdeI18nHelper = (BdeI18nHelper)ApplicationContextRegister.getBean(BdeI18nHelper.class);
    private List<AssistMappingBO<AcctAssist>> assistCodeList;

    public VoucherResultSetExtractor(List<AssistMappingBO<AcctAssist>> assistCodeList) {
        this.assistCodeList = assistCodeList == null ? CollectionUtils.newArrayList() : assistCodeList;
    }

    public List<PenetrateVoucher> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<PenetrateVoucher> result = new ArrayList<PenetrateVoucher>(rs.getRow());
        PenetrateVoucher row = null;
        ArrayList<Dimension> dimList = null;
        HashSet<String> columnSet = new HashSet<String>(rs.getMetaData().getColumnCount());
        for (int colIdx = 1; colIdx <= rs.getMetaData().getColumnCount(); ++colIdx) {
            columnSet.add(rs.getMetaData().getColumnLabel(colIdx).toUpperCase());
        }
        while (rs.next()) {
            row = new PenetrateVoucher();
            row.put("ROWTYPE", RowTypeEnum.DETAIL.ordinal());
            row.put("DIGEST", rs.getString("DIGEST"));
            row.put("SUBJECTCODE", rs.getString("SUBJECTCODE"));
            row.put("SUBJECTNAME", rs.getString("SUBJECTNAME"));
            row.put("ORGND", rs.getBigDecimal("ORGND"));
            row.put("ORGNC", rs.getBigDecimal("ORGNC"));
            row.put("DEBIT", rs.getBigDecimal("DEBIT"));
            row.put("CREDIT", rs.getBigDecimal("CREDIT"));
            dimList = new ArrayList<Dimension>();
            String ASSIST_COL_NAME_TMPL = "%1$s_NAME";
            String ASSIST_NAME_TMPL = "%1$s|%2$s";
            String assistColName = "";
            String dimVal = "";
            for (AssistMappingBO<AcctAssist> assistMapping : this.assistCodeList) {
                Dimension dim = new Dimension(assistMapping.getAssistCode());
                dim.setDimName(this.bdeI18nHelper.getMessage(assistMapping.getAssistCode(), assistMapping.getAssistName()));
                assistColName = String.format("%1$s_NAME", assistMapping.getAssistCode());
                dimVal = columnSet.contains(assistColName) && !StringUtils.isEmpty((String)rs.getString(assistMapping.getAssistCode())) ? (StringUtils.isEmpty((String)rs.getString(assistColName)) ? rs.getString(assistMapping.getAssistCode()) : String.format("%1$s|%2$s", rs.getString(assistMapping.getAssistCode()), rs.getString(assistColName))) : rs.getString(assistMapping.getAssistCode());
                dim.setDimValue(dimVal);
                dimList.add(dim);
            }
            row.put("ASSTYPELIST", dimList);
            result.add(row);
        }
        return result;
    }
}


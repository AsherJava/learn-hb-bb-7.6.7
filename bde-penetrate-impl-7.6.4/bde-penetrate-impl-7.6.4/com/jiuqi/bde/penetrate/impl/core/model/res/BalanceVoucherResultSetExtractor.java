/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.penetrate.impl.core.model.res;

import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.RowTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class BalanceVoucherResultSetExtractor
implements ResultSetExtractor<List<PenetrateBalance>> {
    private PenetrateBaseDTO condi;
    private List<FieldMappingDefineDTO> items;

    public BalanceVoucherResultSetExtractor(PenetrateBaseDTO condi, List<FieldMappingDefineDTO> items) {
        this.condi = condi;
        this.items = items == null ? CollectionUtils.newArrayList() : items;
    }

    public List<PenetrateBalance> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashSet assistDimSet = this.condi.getAssTypeList() == null ? new HashSet() : this.condi.getAssTypeList().stream().map(Dimension::getDimCode).collect(Collectors.toSet());
        ArrayList<PenetrateBalance> result = new ArrayList<PenetrateBalance>(rs.getRow());
        PenetrateBalance row = null;
        while (rs.next()) {
            row = new PenetrateBalance();
            row.put("ROWTYPE", RowTypeEnum.DETAIL.ordinal());
            row.put("SUBJECTCODE", rs.getString("SUBJECTCODE"));
            row.put("SUBJECTNAME", rs.getString("SUBJECTNAME"));
            for (FieldMappingDefineDTO defineItem : this.items) {
                if (!assistDimSet.contains(defineItem.getFieldName())) continue;
                row.put(defineItem.getFieldName(), rs.getString(defineItem.getFieldName()));
                row.put(ModelExecuteUtil.getAssistFieldName((String)defineItem.getFieldName()), rs.getString(ModelExecuteUtil.getAssistFieldName((String)defineItem.getFieldName())));
            }
            row.put("DEBIT", rs.getBigDecimal("DEBIT"));
            row.put("CREDIT", rs.getBigDecimal("CREDIT"));
            row.put("ORGND", rs.getBigDecimal("ORGND"));
            row.put("ORGNC", rs.getBigDecimal("ORGNC"));
            row.put("DSUM", rs.getBigDecimal("DSUM"));
            row.put("CSUM", rs.getBigDecimal("CSUM"));
            row.put("ORGNDSUM", rs.getBigDecimal("ORGNDSUM"));
            row.put("ORGNCSUM", rs.getBigDecimal("ORGNCSUM"));
            result.add(row);
        }
        return result;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.intf.Dimension
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.voucher;

import com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.intf.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class VoucherResultSetExtractor
implements ResultSetExtractor<Map<String, Map<String, List<AssBalanceData>>>> {
    private BalanceCondition condi;

    public VoucherResultSetExtractor(BalanceCondition condi) {
        this.condi = condi;
    }

    public Map<String, Map<String, List<AssBalanceData>>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<String, Map<String, List<AssBalanceData>>> result = new HashMap<String, Map<String, List<AssBalanceData>>>(128);
        while (rs.next()) {
            String fixedAssistKey;
            String subjectCode = rs.getString("SUBJECTCODE");
            if (result.get(subjectCode) == null) {
                result.put(subjectCode, new HashMap(64));
            }
            StringBuilder optimAssistKeyBuilder = new StringBuilder();
            HashMap<String, String> assistMap = new HashMap<String, String>(this.condi.getAssTypeList().size());
            for (Dimension assType : this.condi.getAssTypeList()) {
                assistMap.put(assType.getDimCode(), rs.getString(assType.getDimCode()));
                if (!"EQ".equals(assType.getDimRule())) continue;
                optimAssistKeyBuilder.append(assType.getDimCode()).append(":").append(rs.getString(assType.getDimCode())).append("|");
            }
            String string = fixedAssistKey = optimAssistKeyBuilder.length() == 0 ? "#" : optimAssistKeyBuilder.toString();
            if (((Map)result.get(subjectCode)).get(fixedAssistKey) == null) {
                ((Map)result.get(subjectCode)).put(fixedAssistKey, new ArrayList(32));
            }
            List list = (List)((Map)result.get(subjectCode)).get(fixedAssistKey);
            AssBalanceData balanceData = new AssBalanceData();
            list.add(balanceData);
            balanceData.setSubjectCode(subjectCode);
            balanceData.setCurrencyCode(rs.getString("CURRENCYCODE"));
            balanceData.setJf(rs.getBigDecimal(FetchTypeEnum.JF.name()));
            balanceData.setDf(rs.getBigDecimal(FetchTypeEnum.DF.name()));
            balanceData.setBqnum(rs.getBigDecimal(FetchTypeEnum.BQNUM.name()));
            balanceData.setJl(rs.getBigDecimal(FetchTypeEnum.JL.name()));
            balanceData.setDl(rs.getBigDecimal(FetchTypeEnum.DL.name()));
            balanceData.setLjnum(rs.getBigDecimal(FetchTypeEnum.LJNUM.name()));
            balanceData.setWjf(rs.getBigDecimal(FetchTypeEnum.WJF.name()));
            balanceData.setWdf(rs.getBigDecimal(FetchTypeEnum.WDF.name()));
            balanceData.setWbqnum(rs.getBigDecimal(FetchTypeEnum.WBQNUM.name()));
            balanceData.setWjl(rs.getBigDecimal(FetchTypeEnum.WJL.name()));
            balanceData.setWdl(rs.getBigDecimal(FetchTypeEnum.WDL.name()));
            balanceData.setWljnum(rs.getBigDecimal(FetchTypeEnum.WLJNUM.name()));
            balanceData.setAssistMap(assistMap);
        }
        return result;
    }
}


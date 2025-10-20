/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.intf.Dimension
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gcreport.fetch;

import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.plugin.gcreport.fetch.CedxBalanceCondition;
import com.jiuqi.bde.plugin.gcreport.fetch.CedxBalanceData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class CedxBalanceResultSetExtractor
implements ResultSetExtractor<Map<String, Map<String, List<CedxBalanceData>>>> {
    private CedxBalanceCondition condi;

    public CedxBalanceResultSetExtractor(CedxBalanceCondition condi) {
        this.condi = condi;
    }

    public Map<String, Map<String, List<CedxBalanceData>>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<String, Map<String, List<CedxBalanceData>>> result = new HashMap<String, Map<String, List<CedxBalanceData>>>(128);
        while (rs.next()) {
            String fixedAssistKey;
            String subjectCode = rs.getString("SUBJECTCODE");
            if (result.get(subjectCode) == null) {
                result.put(subjectCode, new HashMap(64));
            }
            StringBuilder optimAssistKeyBuilder = new StringBuilder();
            StringBuilder assistKey = new StringBuilder();
            HashMap<String, String> assistMap = new HashMap<String, String>(this.condi.getAssTypeList().size());
            for (Dimension assType : this.condi.getAssTypeList()) {
                if ("EQ".equals(assType.getDimRule())) {
                    optimAssistKeyBuilder.append(assType.getDimCode()).append(":").append(rs.getString(assType.getDimCode())).append("|");
                }
                assistMap.put(assType.getDimCode(), rs.getString(assType.getDimCode()));
                assistKey.append(assType.getDimCode()).append(":").append(rs.getString(assType.getDimCode())).append("|");
            }
            String string = fixedAssistKey = optimAssistKeyBuilder.length() == 0 ? "#" : optimAssistKeyBuilder.toString();
            if (((Map)result.get(subjectCode)).get(fixedAssistKey) == null) {
                ((Map)result.get(subjectCode)).put(fixedAssistKey, new ArrayList(32));
            }
            List list = (List)((Map)result.get(subjectCode)).get(fixedAssistKey);
            CedxBalanceData balanceData = new CedxBalanceData();
            list.add(balanceData);
            balanceData.setSubjectCode(subjectCode);
            balanceData.setCurrencyCode(rs.getString("CURRENCYCODE"));
            balanceData.setOrient(rs.getInt("ORIENT"));
            balanceData.setDxnc(rs.getBigDecimal(FetchTypeEnum.DXNC.name()));
            balanceData.setDxjnc(rs.getBigDecimal(FetchTypeEnum.DXJNC.name()));
            balanceData.setDxdnc(rs.getBigDecimal(FetchTypeEnum.DXDNC.name()));
            balanceData.setDxye(rs.getBigDecimal(FetchTypeEnum.DXYE.name()));
            balanceData.setDxjyh(rs.getBigDecimal(FetchTypeEnum.DXJYH.name()));
            balanceData.setDxdyh(rs.getBigDecimal(FetchTypeEnum.DXDYH.name()));
            balanceData.setAssistMap(assistMap);
            balanceData.setAssistKey(assistKey.toString());
        }
        return result;
    }
}


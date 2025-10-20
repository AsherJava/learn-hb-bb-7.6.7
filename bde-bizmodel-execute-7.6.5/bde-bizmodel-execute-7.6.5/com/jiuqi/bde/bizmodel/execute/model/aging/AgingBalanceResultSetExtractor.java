/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.AgingFetchTypeEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.intf.Dimension
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.aging;

import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition;
import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceData;
import com.jiuqi.bde.common.constant.AgingFetchTypeEnum;
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

public class AgingBalanceResultSetExtractor
implements ResultSetExtractor<Map<String, Map<String, List<AgingBalanceData>>>> {
    private AgingBalanceCondition condi;

    public AgingBalanceResultSetExtractor(AgingBalanceCondition condi) {
        this.condi = condi;
    }

    public Map<String, Map<String, List<AgingBalanceData>>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<String, Map<String, List<AgingBalanceData>>> result = new HashMap<String, Map<String, List<AgingBalanceData>>>(128);
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
            AgingBalanceData balanceData = new AgingBalanceData();
            list.add(balanceData);
            balanceData.setSubjectCode(subjectCode);
            balanceData.setCurrencyCode(rs.getString("CURRENCYCODE"));
            if (this.condi.getAgingFetchType() == AgingFetchTypeEnum.NC) {
                balanceData.setHxnc(rs.getBigDecimal(FetchTypeEnum.HXNC.name()));
                balanceData.setWhxnc(rs.getBigDecimal(FetchTypeEnum.WHXNC.name()));
            } else {
                balanceData.setHxye(rs.getBigDecimal(FetchTypeEnum.HXYE.name()));
                balanceData.setWhxye(rs.getBigDecimal(FetchTypeEnum.WHXYE.name()));
            }
            balanceData.setAssistMap(assistMap);
            balanceData.setAssistKey(assistKey.toString());
        }
        return result;
    }
}


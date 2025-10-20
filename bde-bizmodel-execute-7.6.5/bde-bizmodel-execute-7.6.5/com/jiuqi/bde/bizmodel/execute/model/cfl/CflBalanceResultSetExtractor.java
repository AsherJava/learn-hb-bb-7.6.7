/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.cfl;

import com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class CflBalanceResultSetExtractor
implements ResultSetExtractor<Map<String, Map<String, List<AssBalanceData>>>> {
    private BalanceCondition condi;
    private static final String BALANCEORGNENABLE = "DC_BALANCE_ORGN_ENABLE";

    public CflBalanceResultSetExtractor(BalanceCondition condi) {
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
            AssBalanceData balanceData = new AssBalanceData();
            list.add(balanceData);
            balanceData.setSubjectCode(subjectCode);
            balanceData.setCurrencyCode(rs.getString("CURRENCYCODE"));
            balanceData.setNc(rs.getBigDecimal(FetchTypeEnum.NC.name()));
            balanceData.setC(rs.getBigDecimal(FetchTypeEnum.C.name()));
            balanceData.setJf(rs.getBigDecimal(FetchTypeEnum.JF.name()));
            balanceData.setDf(rs.getBigDecimal(FetchTypeEnum.DF.name()));
            balanceData.setJl(rs.getBigDecimal(FetchTypeEnum.JL.name()));
            balanceData.setDl(rs.getBigDecimal(FetchTypeEnum.DL.name()));
            balanceData.setYe(rs.getBigDecimal(FetchTypeEnum.YE.name()));
            Boolean dcBalanceOrgnCurrencyEnable = this.isDcBalanceOrgnCurrencyEnable();
            if (Objects.isNull(dcBalanceOrgnCurrencyEnable) || dcBalanceOrgnCurrencyEnable.booleanValue()) {
                balanceData.setWnc(rs.getBigDecimal(FetchTypeEnum.WNC.name()));
                balanceData.setWc(rs.getBigDecimal(FetchTypeEnum.WC.name()));
                balanceData.setWjf(rs.getBigDecimal(FetchTypeEnum.WJF.name()));
                balanceData.setWdf(rs.getBigDecimal(FetchTypeEnum.WDF.name()));
                balanceData.setWjl(rs.getBigDecimal(FetchTypeEnum.WJL.name()));
                balanceData.setWdl(rs.getBigDecimal(FetchTypeEnum.WDL.name()));
                balanceData.setWye(rs.getBigDecimal(FetchTypeEnum.WYE.name()));
            }
            balanceData.setAssistMap(assistMap);
            balanceData.setAssistKey(assistKey.toString());
        }
        return result;
    }

    public Boolean isDcBalanceOrgnCurrencyEnable() {
        INvwaSystemOptionService sysOptionService = (INvwaSystemOptionService)BeanUtils.getBean(INvwaSystemOptionService.class);
        String valueById = sysOptionService.findValueById(BALANCEORGNENABLE);
        if (StringUtils.isEmpty((String)valueById)) {
            return null;
        }
        if ("1".equals(valueById)) {
            return true;
        }
        return false;
    }
}


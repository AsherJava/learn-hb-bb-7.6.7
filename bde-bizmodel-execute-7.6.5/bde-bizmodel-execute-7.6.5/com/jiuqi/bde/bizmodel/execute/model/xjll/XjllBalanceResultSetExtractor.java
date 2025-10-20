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
package com.jiuqi.bde.bizmodel.execute.model.xjll;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.model.xjll.XjllBalanceData;
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

public class XjllBalanceResultSetExtractor
implements ResultSetExtractor<Map<String, Map<String, List<XjllBalanceData>>>> {
    private BalanceCondition condi;
    private static final String BALANCEORGNENABLE = "DC_BALANCE_ORGN_ENABLE";

    public XjllBalanceResultSetExtractor(BalanceCondition condi) {
        this.condi = condi;
    }

    public Map<String, Map<String, List<XjllBalanceData>>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<String, Map<String, List<XjllBalanceData>>> result = new HashMap<String, Map<String, List<XjllBalanceData>>>(128);
        while (rs.next()) {
            String fixedAssistKey;
            String cfItemCode = rs.getString("CFITEMCODE");
            if (result.get(cfItemCode) == null) {
                result.put(cfItemCode, new HashMap(64));
            }
            StringBuilder optimAssistKeyBuilder = new StringBuilder();
            HashMap<String, String> assistMap = new HashMap<String, String>(this.condi.getAssTypeList().size());
            for (Dimension assType : this.condi.getAssTypeList()) {
                assistMap.put(assType.getDimCode(), rs.getString(assType.getDimCode()));
                if (!"EQ".equals(assType.getDimRule())) continue;
                optimAssistKeyBuilder.append(assType.getDimCode()).append(":").append(rs.getString(assType.getDimCode())).append("|");
            }
            String string = fixedAssistKey = optimAssistKeyBuilder.length() == 0 ? "#" : optimAssistKeyBuilder.toString();
            if (((Map)result.get(cfItemCode)).get(fixedAssistKey) == null) {
                ((Map)result.get(cfItemCode)).put(fixedAssistKey, new ArrayList(32));
            }
            List list = (List)((Map)result.get(cfItemCode)).get(fixedAssistKey);
            XjllBalanceData balanceData = new XjllBalanceData();
            list.add(balanceData);
            balanceData.setCfItemCode(cfItemCode);
            balanceData.setSubjectCode(rs.getString("SUBJECTCODE"));
            balanceData.setCurrencyCode(rs.getString("CURRENCYCODE"));
            balanceData.setBqnum(rs.getBigDecimal(FetchTypeEnum.BQNUM.name()));
            balanceData.setLjnum(rs.getBigDecimal(FetchTypeEnum.LJNUM.name()));
            Boolean dcBalanceOrgnCurrencyEnable = this.isDcBalanceOrgnCurrencyEnable();
            if (Objects.isNull(dcBalanceOrgnCurrencyEnable) || dcBalanceOrgnCurrencyEnable.booleanValue()) {
                balanceData.setWbqnum(rs.getBigDecimal(FetchTypeEnum.WBQNUM.name()));
                balanceData.setWljnum(rs.getBigDecimal(FetchTypeEnum.WLJNUM.name()));
            }
            balanceData.setAssistMap(assistMap);
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


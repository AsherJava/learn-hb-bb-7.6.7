/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.model.djye.fetch.DjyeBalanceModelExecute
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.plugin.va6.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.model.djye.fetch.DjyeBalanceModelExecute;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.plugin.va6.BdeVa6PluginType;
import com.jiuqi.bde.plugin.va6.fetch.Va6AssBalanceLoader;
import com.jiuqi.bde.plugin.va6.fetch.Va6BalanceLoader;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Va6DjyeBalanceModelExecute
extends DjyeBalanceModelExecute {
    @Autowired
    private Va6BalanceLoader loader;
    @Autowired
    private Va6AssBalanceLoader assLoader;
    @Autowired
    private BdeVa6PluginType va6PluginType;

    protected Map<String, Map<String, List<AssBalanceData>>> loadData(BalanceCondition condi) {
        if (!this.va6PluginType.getSymbol().equals(condi.getOrgMapping().getPluginType())) {
            return super.loadData(condi);
        }
        if (CollectionUtils.isEmpty(condi.getAssTypeMap().keySet())) {
            return this.convertResult(condi, this.loader.loadData(condi));
        }
        return this.convertResult(condi, this.assLoader.loadData(condi));
    }

    private Map<String, Map<String, List<AssBalanceData>>> convertResult(BalanceCondition condi, FetchData cache) {
        HashMap<String, Map<String, List<AssBalanceData>>> result = new HashMap<String, Map<String, List<AssBalanceData>>>(128);
        block0: for (Object[] rowData : cache.getRowDatas()) {
            String subjectCode = cache.getString(rowData, "SUBJECTCODE");
            if (result.get(subjectCode) == null) {
                result.put(subjectCode, new HashMap(64));
            }
            StringBuilder optimAssistKeyBuilder = new StringBuilder();
            StringBuilder assistKey = new StringBuilder();
            HashMap<String, String> assistMap = new HashMap<String, String>(condi.getAssTypeList().size());
            for (Dimension assType : condi.getAssTypeList()) {
                if (StringUtils.isEmpty((String)cache.getString(rowData, assType.getDimCode()))) continue block0;
                if ("EQ".equals(assType.getDimRule())) {
                    optimAssistKeyBuilder.append(assType.getDimCode()).append(":").append(cache.getString(rowData, assType.getDimCode())).append("|");
                }
                assistMap.put(assType.getDimCode(), cache.getString(rowData, assType.getDimCode()));
                assistKey.append(assType.getDimCode()).append(":").append(cache.getString(rowData, assType.getDimCode())).append("|");
            }
            String fixedAssistKey = optimAssistKeyBuilder.length() == 0 ? "#" : optimAssistKeyBuilder.toString();
            ((Map)result.get(subjectCode)).computeIfAbsent(fixedAssistKey, k -> new ArrayList(32));
            List list = (List)((Map)result.get(subjectCode)).get(fixedAssistKey);
            AssBalanceData balanceData = new AssBalanceData();
            list.add(balanceData);
            balanceData.setSubjectCode(subjectCode);
            balanceData.setOrient(Integer.valueOf(cache.getBigDecimal(rowData, "ORIENT").intValue()));
            balanceData.setCurrencyCode(cache.getString(rowData, "CURRENCYCODE"));
            balanceData.setNc(cache.getBigDecimal(rowData, FetchTypeEnum.NC.name()));
            balanceData.setC(cache.getBigDecimal(rowData, FetchTypeEnum.C.name()));
            balanceData.setJf(cache.getBigDecimal(rowData, FetchTypeEnum.JF.name()));
            balanceData.setDf(cache.getBigDecimal(rowData, FetchTypeEnum.DF.name()));
            balanceData.setJl(cache.getBigDecimal(rowData, FetchTypeEnum.JL.name()));
            balanceData.setDl(cache.getBigDecimal(rowData, FetchTypeEnum.DL.name()));
            balanceData.setYe(cache.getBigDecimal(rowData, FetchTypeEnum.YE.name()));
            balanceData.setWnc(cache.getBigDecimal(rowData, FetchTypeEnum.WNC.name()));
            balanceData.setWc(cache.getBigDecimal(rowData, FetchTypeEnum.WC.name()));
            balanceData.setWjf(cache.getBigDecimal(rowData, FetchTypeEnum.WJF.name()));
            balanceData.setWdf(cache.getBigDecimal(rowData, FetchTypeEnum.WDF.name()));
            balanceData.setWjl(cache.getBigDecimal(rowData, FetchTypeEnum.WJL.name()));
            balanceData.setWdl(cache.getBigDecimal(rowData, FetchTypeEnum.WDL.name()));
            balanceData.setWye(cache.getBigDecimal(rowData, FetchTypeEnum.WYE.name()));
            balanceData.setAssistMap(assistMap);
            balanceData.setAssistKey(assistKey.toString());
        }
        return result;
    }
}


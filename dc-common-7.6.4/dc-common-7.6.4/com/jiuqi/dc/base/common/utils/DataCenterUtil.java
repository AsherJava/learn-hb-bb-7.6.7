/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataCenterUtil {
    public static List<String> getRepCurrCode(String unitCode) {
        OrgDTO param = new OrgDTO();
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setCategoryname("MD_ORG");
        param.setCode(unitCode);
        param.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        PageVO response = ((OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class)).list(param);
        if (response.getRs().getCode() != 0) {
            throw new RuntimeException("\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u53d1\u751f\u9519\u8bef");
        }
        Map<String, OrgDO> orgMap = response.getRows().stream().collect(Collectors.toMap(OrgDO::getCode, org -> org, (k1, k2) -> k2));
        OrgDO org2 = orgMap.get(unitCode);
        return DataCenterUtil.getRepCurrCode(org2);
    }

    public static List<String> getRepCurrCode(OrgDO org) {
        if (org == null) {
            return CollectionUtils.newArrayList();
        }
        Object repCurrObj = org.getValueOf("CURRENCYIDS");
        if (repCurrObj == null || StringUtils.isEmpty((String)repCurrObj.toString())) {
            return CollectionUtils.newArrayList();
        }
        ArrayList<String> repCurrList = null;
        if (repCurrObj.toString().startsWith("[")) {
            repCurrObj = repCurrObj.toString().substring(1, repCurrObj.toString().length() - 1);
            repCurrObj = repCurrObj.toString().replace(" ", "");
            repCurrList = new ArrayList<String>(Arrays.asList(repCurrObj.toString().split(",")));
        } else {
            repCurrList = CollectionUtils.newArrayList();
            repCurrList.add(repCurrObj.toString());
        }
        return repCurrList;
    }

    public static List<String> getRepCurrCodeWithoutFincurr(String unitCode) {
        List<String> repCurrList = DataCenterUtil.getRepCurrCode(unitCode);
        String fincurr = DataCenterUtil.getFinCurrCode(unitCode);
        if (Objects.nonNull(repCurrList) && !repCurrList.isEmpty() && repCurrList.contains(fincurr)) {
            repCurrList.remove(fincurr);
        }
        return repCurrList;
    }

    public static String getFinCurrCode(String unitCode) {
        OrgDTO param = new OrgDTO();
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setCategoryname("MD_ORG");
        param.setCode(unitCode);
        param.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        PageVO response = ((OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class)).list(param);
        if (response.getRs().getCode() != 0) {
            throw new RuntimeException("\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u53d1\u751f\u9519\u8bef");
        }
        Map<String, OrgDO> orgMap = response.getRows().stream().collect(Collectors.toMap(OrgDO::getCode, org -> org, (k1, k2) -> k2));
        OrgDO org2 = orgMap.get(unitCode);
        return DataCenterUtil.getFinCurrCode(org2);
    }

    public static String getFinCurrCode(OrgDO org) {
        if (org == null) {
            return "CNY";
        }
        Object finCurrObj = org.getValueOf("CURRENCYID");
        return finCurrObj == null || StringUtils.isEmpty((String)finCurrObj.toString()) ? "CNY" : finCurrObj.toString();
    }

    public static OrgDO getUnitByCode(String unitCode) {
        OrgDTO param = new OrgDTO();
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setCategoryname("MD_ORG");
        param.setCode(unitCode);
        param.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        PageVO response = ((OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class)).list(param);
        if (response.getRs().getCode() != 0) {
            throw new RuntimeException("\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u53d1\u751f\u9519\u8bef");
        }
        Map<String, OrgDO> orgMap = response.getRows().stream().collect(Collectors.toMap(OrgDO::getCode, org -> org, (k1, k2) -> k2));
        return orgMap.get(unitCode);
    }

    public static List<OrgDO> getOrgData() {
        OrgDTO param = new OrgDTO();
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setCategoryname("MD_ORG");
        param.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        return ((OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class)).list(param).getRows();
    }
}


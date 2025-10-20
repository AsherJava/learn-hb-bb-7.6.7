/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.base.impl.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.impl.enums.UnitTypeEnum;
import com.jiuqi.dc.base.impl.rpunitmapping.mapper.Org2RpunitMappingMapper;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UnitRpConvertUtil {
    public static Set<String> getUnitSeting(String unitType, List<String> unitCodeList) {
        HashSet<String> orgCodes = new HashSet<String>();
        if (UnitTypeEnum.isReportUnit(unitType).booleanValue()) {
            Org2RpunitMappingQueryVO queryParam = new Org2RpunitMappingQueryVO();
            queryParam.setAcctYear(Integer.valueOf(DateUtils.getYearOfDate((Date)new Date())));
            queryParam.setOrgs(unitCodeList);
            queryParam.setPageNum(Integer.valueOf(-1));
            if (CollectionUtils.isEmpty(unitCodeList)) {
                orgCodes.addAll(((Org2RpunitMappingMapper)ApplicationContextRegister.getBean(Org2RpunitMappingMapper.class)).getAllOrgCode(queryParam));
            } else {
                for (int period = 1; period <= 12; ++period) {
                    queryParam.setAcctPeriod(Integer.valueOf(period));
                    orgCodes.addAll(((Org2RpunitMappingMapper)ApplicationContextRegister.getBean(Org2RpunitMappingMapper.class)).getAllOrgCodeByRpUnitCode(queryParam));
                }
            }
        } else {
            OrgDTO orgDataDTO = new OrgDTO();
            orgDataDTO.setCodeScope(unitCodeList);
            orgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
            orgDataDTO.setCategoryname("MD_ORG");
            orgDataDTO.setTenantName("__default_tenant__");
            orgDataDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
            orgCodes.addAll(((OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class)).list(orgDataDTO).getRows().stream().map(OrgDO::getCode).collect(Collectors.toSet()));
        }
        return orgCodes;
    }
}


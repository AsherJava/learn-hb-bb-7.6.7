/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCacheDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.extend.OrgDataAction
 *  com.jiuqi.va.extend.OrgDataParamInterceptor
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.organization.service.impl.help.OrgDataQueryService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCacheDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.extend.OrgDataAction;
import com.jiuqi.va.extend.OrgDataParamInterceptor;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.service.impl.help.OrgDataQueryService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class GcOrgDataParamInterceptor
implements OrgDataParamInterceptor {
    public void modify(OrgDTO param, OrgDataAction action) {
    }

    public void batchModify(OrgBatchOptDTO param, OrgDataAction action) {
        if (!InspectOrgUtils.enableMergeUnit()) {
            return;
        }
        List dataList = param.getDataList();
        if (CollectionUtils.isEmpty((Collection)dataList)) {
            return;
        }
        if (dataList.get(0) instanceof OrgCacheDO) {
            return;
        }
        HashMap<String, OrgDO> parentCodeMap = new HashMap<String, OrgDO>();
        Map<String, OrgDO> existCodeMap = dataList.stream().collect(Collectors.toMap(OrgDO::getCode, orgDO -> orgDO, (o, o2) -> o2));
        for (OrgDO aDo : dataList) {
            if (StringUtils.isEmpty((String)aDo.getParentcode())) continue;
            parentCodeMap.putIfAbsent(aDo.getParentcode(), existCodeMap.get(aDo.getParentcode()));
        }
        boolean autoCalc = !this.checkEnableAutoCalc(param.getQueryParam().getCategoryname());
        String selectSql = "select id from " + param.getQueryParam().getCategoryname() + " where id = ?";
        boolean doAutoCalc = false;
        OrgDataQueryService orgDataQueryService = (OrgDataQueryService)ApplicationContextRegister.getBean(OrgDataQueryService.class);
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        OrgDTO oldOrgDataDTO = new OrgDTO();
        oldOrgDataDTO.setCategoryname(param.getQueryParam().getCategoryname());
        oldOrgDataDTO.setVersionDate(param.getQueryParam().getVersionDate());
        oldOrgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
        oldOrgDataDTO.setStopflag(Integer.valueOf(-1));
        List orgDOS = orgDataQueryService.listNoOrder(oldOrgDataDTO);
        Map<String, OrgDO> codeMap = orgDOS.stream().collect(Collectors.toMap(OrgDO::getCode, orgDO -> orgDO, (o, o2) -> o2));
        for (OrgDO orgDO2 : dataList) {
            String bblx;
            boolean isParentOrg = parentCodeMap.containsKey(orgDO2.getCode());
            if (isParentOrg || !(bblx = String.valueOf(orgDO2.get((Object)"BBLX".toLowerCase()))).equalsIgnoreCase("1")) continue;
            orgDO2.put("ORGTYPEID".toLowerCase(), (Object)orgDO2.getCategoryname());
            OrgDO parentOrg = (OrgDO)parentCodeMap.get(orgDO2.getParentcode());
            if (parentOrg == null || !ObjectUtils.isEmpty(parentOrg.get((Object)"DIFFUNITID".toLowerCase()))) continue;
            parentOrg.put("DIFFUNITID".toLowerCase(), (Object)orgDO2.getCode());
        }
        for (OrgDO orgDO2 : dataList) {
            ArrayList<String> list;
            boolean calcField = true;
            if (!StringUtils.isEmpty((String)orgDO2.getCode())) {
                OrgDO existDO = codeMap.get(orgDO2.getCode());
                if (existDO != null) {
                    parentCodeMap.putIfAbsent(existDO.getParentcode(), orgDO2);
                    calcField = false;
                }
            } else if (!ObjectUtils.isEmpty(orgDO2.getId()) && !CollectionUtils.isEmpty((Collection)(list = jdbcTemplate.queryForList(selectSql, new Object[]{orgDO2.getId().toString()})))) {
                calcField = false;
            }
            if (!calcField) continue;
            doAutoCalc = true;
            orgDO2.put("gcparents", (Object)orgDO2.getCode());
            if (orgDO2.get((Object)"ORGTYPEID".toLowerCase()) == null) {
                if (autoCalc) {
                    orgDO2.put("ORGTYPEID".toLowerCase(), (Object)(parentCodeMap.containsKey(orgDO2.getCode()) ? orgDO2.getCategoryname() : "MD_ORG_CORPORATE"));
                } else {
                    orgDO2.put("ORGTYPEID".toLowerCase(), (Object)"MD_ORG_CORPORATE");
                }
            } else if (autoCalc) {
                orgDO2.put("ORGTYPEID".toLowerCase(), parentCodeMap.containsKey(orgDO2.getCode()) ? orgDO2.getCategoryname() : orgDO2.get((Object)"ORGTYPEID".toLowerCase()));
            } else {
                orgDO2.put("ORGTYPEID".toLowerCase(), orgDO2.get((Object)"ORGTYPEID".toLowerCase()));
            }
            orgDO2.putIfAbsent((Object)"CURRENCYID".toLowerCase(), (Object)"CNY");
            orgDO2.put("hasMultiValues", (Object)true);
            list = new ArrayList<String>();
            list.add("CNY");
            orgDO2.put("CURRENCYIDS".toLowerCase(), list);
            orgDO2.put("CURRENCYIDS".toLowerCase() + "_show", list);
        }
        if (doAutoCalc) {
            LogHelper.info((String)"\u5408\u5e76-\u673a\u6784\u6279\u91cf\u64cd\u4f5c\u62e6\u622a", (String)("\u673a\u6784\u7c7b\u578b" + param.getQueryParam().getCategoryname()), (String)"\u6dfb\u52a0\u4e86\u9ed8\u8ba4\u7684gcparents, \u9700\u8981\u64cd\u4f5c\u5b8c\u6bd5\u540e\u4fee\u590d");
        }
    }

    private boolean checkEnableAutoCalc(String orgType) {
        INvwaSystemOptionService systemOptionsService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        String option = systemOptionsService.get("gc_option_org_fieldCalc", "NOT_AUTO_CALC_FIELD");
        if (!StringUtils.isEmpty((String)option)) {
            ArrayList types = CollectionUtils.newArrayList((Object[])option.split(";"));
            return types.contains(orgType);
        }
        return false;
    }
}


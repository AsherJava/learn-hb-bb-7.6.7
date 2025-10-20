/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.enums.GcOrgOperateEnum
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.external.service.impl;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.enums.GcOrgOperateEnum;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.external.service.GcOrgExternalService;
import com.jiuqi.gcreport.org.impl.external.vo.GcOrgExternalApiParam;
import com.jiuqi.gcreport.org.impl.external.vo.GcOrgExternalVO;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgMangerCenterTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class GcOrgExternalServiceImpl
implements GcOrgExternalService {
    private GcOrgBaseTool getBaseOrgTool() {
        return GcOrgBaseTool.getInstance();
    }

    private GcOrgVerTool getOrgVerTool() {
        return GcOrgVerTool.getInstance();
    }

    private GcOrgMangerCenterTool getCenterTool(String orgType, String org_ver) {
        return GcOrgMangerCenterTool.getInstance(orgType, org_ver);
    }

    @Override
    public BusinessResponseEntity<Object> operate(GcOrgExternalApiParam param) {
        Assert.isNotEmpty(param.getOrgTypes(), (String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        List orgTypes = param.getOrgTypes().stream().map(String::toUpperCase).sorted(String::compareTo).collect(Collectors.toList());
        String code = param.getOrg().getCode();
        Assert.isNotEmpty((String)code, (String)"\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        String message = "\u64cd\u4f5c\u6210\u529f";
        boolean flag = true;
        for (String orgType : orgTypes) {
            try {
                if (orgType.equals("MD_ORG")) {
                    this.operateBaseOrg(param);
                    continue;
                }
                this.operateHBOrg(param, orgType);
            }
            catch (Exception e) {
                e.printStackTrace();
                message = "\u64cd\u4f5c[" + orgType + "]\u5931\u8d25:" + e.getMessage();
                flag = false;
            }
        }
        return flag ? BusinessResponseEntity.ok() : BusinessResponseEntity.error((String)message);
    }

    @Transactional(rollbackFor={Exception.class})
    public void operateBaseOrg(GcOrgExternalApiParam param) {
        GcOrgExternalVO org;
        GcOrgBaseTool baseOrgTool = this.getBaseOrgTool();
        OrgToJsonVO oldOrg = baseOrgTool.getOrgByCode((org = param.getOrg()).getCode());
        if (oldOrg == null) {
            OrgToJsonVO orgToJsonVO = new OrgToJsonVO();
            orgToJsonVO.setCode(org.getCode());
            orgToJsonVO.setTitle(org.getName());
            orgToJsonVO.setParentid(org.getParentCode());
            orgToJsonVO.setFieldValue("CURRENCYID", (Object)org.getCurrencyCode());
            orgToJsonVO.setFieldValue("SHORTNAME", (Object)org.getShortName());
            this.setFieldValue(org, orgToJsonVO);
            baseOrgTool.addBaseUnit(orgToJsonVO);
        } else {
            oldOrg.setTitle(org.getName());
            oldOrg.setParentid(org.getParentCode());
            oldOrg.setFieldValue("CURRENCYID", (Object)org.getCurrencyCode());
            oldOrg.setFieldValue("CURRENCYID", (Object)org.getCurrencyCode());
            this.setFieldValue(org, oldOrg);
            baseOrgTool.updateBaseUnit(oldOrg);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void operateHBOrg(GcOrgExternalApiParam param, String orgType) {
        OrgToJsonVO parentOrg;
        String orgVerCode = param.getOrgVerCode();
        if (!StringUtils.hasText(orgVerCode)) {
            OrgVersionVO orgVersionByDate = this.getOrgVerTool().getOrgVersionByDate(orgType, DateUtils.now());
            orgVerCode = DateUtils.format((Date)orgVersionByDate.getValidTime(), (DateCommonFormatEnum)DateCommonFormatEnum.FULL_DIGIT_BY_NONE);
        }
        GcOrgExternalVO org = param.getOrg();
        GcOrgMangerCenterTool centerTool = this.getCenterTool(orgType, orgVerCode);
        OrgToJsonVO oldOrg = centerTool.getOrgByCode(org.getCode());
        GcOrgOperateEnum operateType = param.getOperateType();
        if (operateType != null && operateType.equals((Object)GcOrgOperateEnum.DELETE)) {
            centerTool.delete(org.getCode());
            return;
        }
        if (oldOrg == null) {
            OrgToJsonVO orgToJsonVO = new OrgToJsonVO();
            orgToJsonVO.setCode(org.getCode());
            orgToJsonVO.setTitle(org.getName());
            orgToJsonVO.setParentid(org.getParentCode());
            orgToJsonVO.setFieldValue("CURRENCYID", (Object)org.getCurrencyCode());
            orgToJsonVO.setFieldValue("SHORTNAME", (Object)org.getShortName());
            this.setFieldValue(org, orgToJsonVO);
            centerTool.add(orgToJsonVO);
        } else {
            oldOrg.setTitle(org.getName());
            oldOrg.setParentid(org.getParentCode());
            oldOrg.setFieldValue("CURRENCYID", (Object)org.getCurrencyCode());
            oldOrg.setFieldValue("SHORTNAME", (Object)org.getShortName());
            this.setFieldValue(org, oldOrg);
            centerTool.update(oldOrg);
        }
        GcOrgKindEnum orgKind = org.getOrgKind();
        String parentCode = org.getParentCode();
        if (orgKind.equals((Object)GcOrgKindEnum.DIFFERENCE) && StringUtils.hasText(parentCode)) {
            parentOrg = centerTool.getOrgByCode(parentCode);
            Assert.isNotNull((Object)parentOrg, (String)"\u7236\u7ea7\u5355\u4f4d\u4e0d\u5b58\u5728", (Object[])new Object[0]);
            if (!parentOrg.getDiffUnitId().equalsIgnoreCase(org.getCode())) {
                parentOrg.setFieldValue("DIFFUNITID", (Object)org.getCode());
                centerTool.update(parentOrg);
            }
        }
        if (orgKind.equals((Object)GcOrgKindEnum.BASE) && StringUtils.hasText(parentCode)) {
            parentOrg = centerTool.getOrgByCode(parentCode);
            Assert.isNotNull((Object)parentOrg, (String)"\u7236\u7ea7\u5355\u4f4d\u4e0d\u5b58\u5728", (Object[])new Object[0]);
            if (!parentOrg.getBaseUnitId().equalsIgnoreCase(org.getCode())) {
                parentOrg.setFieldValue("BASEUNITID", (Object)org.getCode());
                centerTool.update(parentOrg);
            }
        }
    }

    private void setFieldValue(GcOrgExternalVO org, OrgToJsonVO orgToJsonVO) {
        if (org.getDatas() == null) {
            return;
        }
        for (String key : org.getDatas().keySet()) {
            orgToJsonVO.setFieldValue(key, org.getDatas().get(key));
        }
    }
}


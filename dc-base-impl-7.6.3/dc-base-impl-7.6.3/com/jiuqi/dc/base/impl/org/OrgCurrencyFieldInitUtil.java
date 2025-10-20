/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.organization.service.OrgCategoryService
 */
package com.jiuqi.dc.base.impl.org;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrgCurrencyFieldInitUtil {
    private static final Logger logger = LoggerFactory.getLogger(OrgCurrencyFieldInitUtil.class);

    public static void syncOrgCurrencyField() {
        ZB zb2;
        OrgCategoryService orgCategoryService = (OrgCategoryService)SpringContextUtils.getBean(OrgCategoryService.class);
        OrgCategoryDO parameter = new OrgCategoryDO();
        parameter.setTenantName("__default_tenant__");
        parameter.setName("MD_ORG");
        OrgCategoryDO orgCategoryDO = orgCategoryService.get(parameter);
        Map<String, ZB> zbMap = orgCategoryDO.getZbs().stream().collect(Collectors.toMap(ZB::getName, zb -> zb));
        boolean isChange = false;
        if (!zbMap.containsKey("CURRENCYID")) {
            zb2 = new ZB();
            zb2.setId(UUIDUtils.fromString36((String)"0bd2cc74-cf4c-5d75-9abe-02f5bb7ac42b"));
            zb2.setName("CURRENCYID");
            zb2.setTitle("\u5e01\u522b");
            zb2.setDatatype(Integer.valueOf(2));
            zb2.setPrecision(Integer.valueOf(200));
            zb2.setDecimal(Integer.valueOf(0));
            zb2.setRelatetype(Integer.valueOf(1));
            zb2.setRequiredflag(Integer.valueOf(0));
            zb2.setReltablename("MD_CURRENCY");
            zb2.setUniqueflag(Integer.valueOf(0));
            zb2.setSolidityflag(Integer.valueOf(0));
            zb2.setSystemfield(Integer.valueOf(0));
            zb2.setReadonly(Integer.valueOf(0));
            orgCategoryDO.syncZb(zb2);
            isChange = true;
        }
        if (!zbMap.containsKey("CURRENCYIDS")) {
            zb2 = new ZB();
            zb2.setId(UUIDUtils.fromString36((String)"4cdbb50a-0ac5-5556-911e-61f9d7676986"));
            zb2.setName("CURRENCYIDS");
            zb2.setTitle("\u62a5\u8868\u5e01\u79cd");
            zb2.setDatatype(Integer.valueOf(2));
            zb2.setPrecision(Integer.valueOf(300));
            zb2.setDecimal(Integer.valueOf(0));
            zb2.setRelatetype(Integer.valueOf(1));
            zb2.setRequiredflag(Integer.valueOf(0));
            zb2.setReltablename("MD_CURRENCY");
            zb2.setUniqueflag(Integer.valueOf(0));
            zb2.setSolidityflag(Integer.valueOf(0));
            zb2.setSystemfield(Integer.valueOf(0));
            zb2.setReadonly(Integer.valueOf(0));
            zb2.setMultiple(Integer.valueOf(1));
            orgCategoryDO.syncZb(zb2);
            isChange = true;
        }
        if (isChange) {
            R r = orgCategoryService.update(orgCategoryDO);
            logger.info(r.getMsg());
        }
    }
}


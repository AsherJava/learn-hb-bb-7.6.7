/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.ReflectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.organization.service.OrgDataService
 *  com.jiuqi.va.organization.service.impl.help.OrgDataParamService
 */
package com.jiuqi.common.billbasedopsorg.bill.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.ReflectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.va.organization.service.impl.help.OrgDataParamService;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcBillPushOrgModelImpl
extends BillModelImpl {
    private static final Logger logger = LoggerFactory.getLogger(GcBillPushOrgModelImpl.class);

    public void save() {
        super.save();
    }

    public boolean afterApproval() {
        super.afterApproval();
        try {
            this.pushToOrg();
        }
        catch (Exception e) {
            logger.error("\u5355\u636e\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u51fa\u9519:{}\uff1a", (Object)e.getMessage(), (Object)e);
        }
        return true;
    }

    private void pushToOrg() {
        int allowPushOrg = ConverterUtils.getAsIntValue((Object)this.getMaster().getValue("ALLOWPUSHORG"));
        if (allowPushOrg == 1) {
            return;
        }
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setVersionDate(new Date());
        List dataFields = this.getMasterTable().getFields().stream().filter(item -> item.getName().startsWith("ORG_")).collect(Collectors.toList());
        Set<String> fieldCodeSet = dataFields.stream().map(item -> item.getName()).collect(Collectors.toSet());
        fieldCodeSet.forEach(fieldCode -> ReflectionUtils.setFieldValue((Object)orgDTO, (String)fieldCode.substring(4).toLowerCase(), (Object)this.getMaster().getValue(fieldCode)));
        OrgDataParamService orgDataParamService = (OrgDataParamService)SpringContextUtils.getBean(OrgDataParamService.class);
        OrgDataService orgDataService = (OrgDataService)SpringContextUtils.getBean(OrgDataService.class);
        R rs = orgDataParamService.checkModify(orgDTO, false);
        OrgDO oldOrg = (OrgDO)rs.get((Object)"oldOrg");
        boolean addFlag = true;
        if (oldOrg != null && oldOrg.getRecoveryflag() == 0) {
            addFlag = false;
        }
        if (addFlag) {
            R resultInfo = orgDataService.add(orgDTO);
            if (resultInfo.getCode() == 0) {
                logger.info("\u7ec4\u7ec7\u673a\u6784\u65b0\u589e\u6210\u529f");
            } else {
                logger.info("\u7ec4\u7ec7\u673a\u6784\u65b0\u589e\u5931\u8d25\uff1a" + resultInfo.getMsg());
            }
        } else {
            R resultInfo = orgDataService.update(orgDTO);
            if (resultInfo.getCode() == 0) {
                logger.info("\u7ec4\u7ec7\u673a\u6784\u4fee\u6539\u6210\u529f");
            } else {
                logger.info("\u7ec4\u7ec7\u673a\u6784\u4fee\u6539\u5931\u8d25\uff1a" + resultInfo.getMsg());
            }
        }
    }
}


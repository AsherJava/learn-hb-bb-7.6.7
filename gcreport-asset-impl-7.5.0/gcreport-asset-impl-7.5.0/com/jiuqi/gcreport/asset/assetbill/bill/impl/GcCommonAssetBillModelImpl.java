/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.util.OrgUtil
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.billref.domain.BillRefRelationDO
 *  com.jiuqi.va.billref.domain.config.BaseInfo
 *  com.jiuqi.va.billref.domain.config.TemplateConfig
 *  com.jiuqi.va.billref.service.IBillRefRelationService
 *  com.jiuqi.va.billref.service.IBillRefTemplateService
 *  com.jiuqi.va.billref.utils.BillRefUtils
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.domain.bill.billref.BillRefTemplateDO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.gcreport.asset.assetbill.bill.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.asset.assetbill.bill.impl.GcAbstractAssetBillModelImpl;
import com.jiuqi.gcreport.asset.assetbill.service.CommonAssetBillService;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.util.OrgUtil;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.billref.domain.BillRefRelationDO;
import com.jiuqi.va.billref.domain.config.BaseInfo;
import com.jiuqi.va.billref.domain.config.TemplateConfig;
import com.jiuqi.va.billref.service.IBillRefRelationService;
import com.jiuqi.va.billref.service.IBillRefTemplateService;
import com.jiuqi.va.billref.utils.BillRefUtils;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.domain.bill.billref.BillRefTemplateDO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcCommonAssetBillModelImpl
extends GcAbstractAssetBillModelImpl {
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());

    public void save() {
        DataRow dataRow = this.getMaster();
        if (!InvestBillTool.validateUserId((String)dataRow.getString("CREATEUSER"))) {
            dataRow.setValue("CREATEUSER", null);
        }
        this.resetNpContext();
        String unitCode = dataRow.getString("UNITCODE");
        String oppunitCode = dataRow.getString("OPPUNITCODE");
        if (!OrgUtil.hasReadAuth((String)unitCode, (String)oppunitCode, (String)this.getOrgType())) {
            throw new BillException(String.format("\u627e\u4e0d\u5230\u5f53\u524d\u5355\u4f4d: %1s, %2s, \u662f\u5426\u6709\u5bf9\u8be5\u5355\u4f4d\u7684\u8bfb\u6743\u9650", unitCode, oppunitCode));
        }
        super.save();
        String buttonAction = dataRow.getString("BUTTONACTION");
        if ("BILLPUSH".equals(buttonAction)) {
            this.srcBillTransfer2FixedAsset();
        }
        this.addCommonAssetBillLog(dataRow);
    }

    public void loadByCode(String billCode) {
        super.loadByCode(billCode);
        this.resetBillDate();
    }

    private void resetBillDate() {
        Integer acctYear = ConverterUtils.getAsInteger((Object)this.getContext().getContextValue("X--acctyear"));
        if (null == acctYear) {
            return;
        }
        Calendar curCalendar = Calendar.getInstance();
        int curYear = curCalendar.get(1);
        if (acctYear < curYear) {
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.set(acctYear, 11, 31);
            this.getMaster().setValue("BILLDATE", (Object)newCalendar.getTime());
            return;
        }
        this.getMaster().setValue("BILLDATE", (Object)curCalendar.getTime());
    }

    private void srcBillTransfer2FixedAsset() {
        IBillRefRelationService relationService = (IBillRefRelationService)SpringContextUtils.getBean(IBillRefRelationService.class);
        IBillRefTemplateService templateService = (IBillRefTemplateService)SpringContextUtils.getBean(IBillRefTemplateService.class);
        CommonAssetBillService commonAssetBillService = (CommonAssetBillService)SpringContextUtils.getBean(CommonAssetBillService.class);
        Set templateIds = BillRefUtils.findByControlNames((Model)this);
        if (null == templateIds) {
            return;
        }
        for (String templateId : templateIds) {
            BillRefTemplateDO template = templateService.findById(UUID.fromString(templateId));
            if (template == null) continue;
            TemplateConfig templateConfig = (TemplateConfig)JsonUtils.readValue((String)template.getConfig(), TemplateConfig.class);
            BaseInfo baseInfo = templateConfig.getBaseInfo();
            DataRow masterData = this.getMaster();
            List currRelationValid = relationService.findByBillCodeAndTemplateId(baseInfo.getRelationTable(), masterData.getString("BILLCODE"), masterData.getUUID("ID"), UUID.fromString(templateId));
            if (null == currRelationValid) continue;
            for (BillRefRelationDO billRefRelationDO : currRelationValid) {
                commonAssetBillService.transfer2FixedAsset(billRefRelationDO.getSrcBillCode());
            }
        }
    }

    private void addCommonAssetBillLog(DataRow dataRow) {
        String unitCode = dataRow.getString("UNITCODE");
        String oppunitCode = dataRow.getString("OPPUNITCODE");
        String zcbh = null;
        try {
            zcbh = dataRow.getString("ZZBH");
        }
        catch (Exception e) {
            this.logger.warn(e.getMessage(), e);
        }
        String operateType = dataRow.getString("BUTTONACTION");
        String operateTypeTitle = null;
        if ("NEW".equals(operateType)) {
            operateTypeTitle = "\u65b0\u589e";
        }
        if ("EDIT".equals(operateType)) {
            operateTypeTitle = "\u4fee\u6539";
        }
        if ("DISPOSAL".equals(operateType)) {
            operateTypeTitle = "\u5904\u7f6e";
        }
        operateTypeTitle = String.format(StringUtils.isEmpty((String)zcbh) ? "%1s-\u91c7\u8d2d\u5355\u4f4d%2s-\u9500\u552e\u5355\u4f4d%3s" : "%1s-\u91c7\u8d2d\u5355\u4f4d%2s-\u9500\u552e\u5355\u4f4d%3s-\u8d44\u4ea7\u7f16\u53f7%4s", operateTypeTitle, unitCode, oppunitCode, zcbh);
        LogHelper.info((String)"\u5408\u5e76-\u5e38\u89c4\u8d44\u4ea7\u53f0\u8d26", (String)operateTypeTitle, (String)operateTypeTitle);
    }

    private void resetNpContext() {
        if (null == NpContextHolder.getContext() || null != NpContextHolder.getContext() && null == NpContextHolder.getContext().getUser()) {
            UserLoginDTO user = ShiroUtil.getUser();
            NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
            NpContextUser userContext = new NpContextUser();
            userContext.setId(user.getId());
            userContext.setName(user.getUsername());
            userContext.setNickname(user.getUsername());
            userContext.setOrgCode(user.getLoginUnit());
            NpContextIdentity identity = new NpContextIdentity();
            identity.setId(userContext.getId());
            identity.setTitle(userContext.getFullname());
            identity.setOrgCode(user.getLoginUnit());
            npContext.setIdentity((ContextIdentity)identity);
            npContext.setUser((ContextUser)userContext);
            npContext.setLoginDate(user.getLoginDate());
            npContext.setTenant(user.getTenantName());
            NpContextHolder.setContext((NpContext)npContext);
        }
    }
}


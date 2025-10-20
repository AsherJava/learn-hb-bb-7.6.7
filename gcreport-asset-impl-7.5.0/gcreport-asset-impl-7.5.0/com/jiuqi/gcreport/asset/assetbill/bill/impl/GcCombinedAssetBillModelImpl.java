/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.util.OrgUtil
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.billref.domain.BillRefRelationDO
 *  com.jiuqi.va.billref.domain.config.BaseInfo
 *  com.jiuqi.va.billref.domain.config.TemplateConfig
 *  com.jiuqi.va.billref.service.IBillRefRelationService
 *  com.jiuqi.va.billref.service.IBillRefTemplateService
 *  com.jiuqi.va.billref.utils.BillRefUtils
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.domain.bill.billref.BillRefTemplateDO
 */
package com.jiuqi.gcreport.asset.assetbill.bill.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.asset.assetbill.bill.impl.GcAbstractAssetBillModelImpl;
import com.jiuqi.gcreport.asset.assetbill.service.CombinedAssetBillService;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.util.OrgUtil;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.billref.domain.BillRefRelationDO;
import com.jiuqi.va.billref.domain.config.BaseInfo;
import com.jiuqi.va.billref.domain.config.TemplateConfig;
import com.jiuqi.va.billref.service.IBillRefRelationService;
import com.jiuqi.va.billref.service.IBillRefTemplateService;
import com.jiuqi.va.billref.utils.BillRefUtils;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.domain.bill.billref.BillRefTemplateDO;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GcCombinedAssetBillModelImpl
extends GcAbstractAssetBillModelImpl {
    public void save() {
        String oppunitCode;
        String unitCode;
        DataRow dataRow = this.getMaster();
        if (!InvestBillTool.validateUserId((String)dataRow.getString("CREATEUSER"))) {
            dataRow.setValue("CREATEUSER", null);
        }
        if (!OrgUtil.hasReadAuth((String)(unitCode = dataRow.getString("UNITCODE")), (String)(oppunitCode = dataRow.getString("OPPUNITCODE")), (String)this.getOrgType())) {
            throw new BillException(String.format("\u627e\u4e0d\u5230\u5f53\u524d\u5355\u4f4d: %1s, %2s, \u662f\u5426\u6709\u5bf9\u8be5\u5355\u4f4d\u7684\u8bfb\u6743\u9650", unitCode, oppunitCode));
        }
        List itemList = ((DataTableImpl)this.getData().getTables().get("GC_COMBINEDASSETBILLITEM")).getRowsData();
        if (null != itemList) {
            for (Map item : itemList) {
                item.put("UNITCODE", unitCode);
            }
        }
        ((DataTableImpl)this.getData().getTables().get("GC_COMBINEDASSETBILLITEM")).updateRows(itemList);
        super.save();
        String buttonAction = dataRow.getString("BUTTONACTION");
        if ("BILLPUSH".equals(buttonAction)) {
            this.srcBillTransfer2FixedAsset();
        }
    }

    private void srcBillTransfer2FixedAsset() {
        IBillRefRelationService relationService = (IBillRefRelationService)SpringContextUtils.getBean(IBillRefRelationService.class);
        IBillRefTemplateService templateService = (IBillRefTemplateService)SpringContextUtils.getBean(IBillRefTemplateService.class);
        CombinedAssetBillService assetBillService = (CombinedAssetBillService)SpringContextUtils.getBean(CombinedAssetBillService.class);
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
                assetBillService.transfer2FixedAsset(billRefRelationDO.getSrcBillCode());
            }
        }
    }
}


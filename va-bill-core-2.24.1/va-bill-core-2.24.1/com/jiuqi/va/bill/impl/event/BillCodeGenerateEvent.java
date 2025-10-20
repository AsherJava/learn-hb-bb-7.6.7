/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.domain.billcode.BillCodeDTO
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BillCodeClient
 */
package com.jiuqi.va.bill.impl.event;

import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModelService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.domain.billcode.BillCodeDTO;
import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BillCodeClient;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class BillCodeGenerateEvent
implements DataPostEvent {
    @Autowired
    private BillModelService billModelService;

    public BillCodeGenerateEvent(BillModelService billModelService) {
        this.billModelService = billModelService;
    }

    public void afterDelete(DataImpl data) {
    }

    public void afterSave(DataImpl data) {
    }

    public void beforeDelete(DataImpl data) {
    }

    public void beforeSave(DataImpl data) {
        DataRow masterData = (DataRow)data.getMasterTable().getRows().get(0);
        String billCode = (String)masterData.getValue("BILLCODE", String.class);
        if (StringUtils.hasText(billCode)) {
            return;
        }
        BillCodeDTO billCodeDTO = new BillCodeDTO();
        billCodeDTO.setDefineCode((String)masterData.getValue("DEFINECODE", String.class));
        billCodeDTO.setCreateTime(masterData.getDate("BILLDATE"));
        billCodeDTO.setTenantName(data.getModel().getContext().getTenantName());
        String unitCode = (String)masterData.getValue("UNITCODE", String.class);
        if (!StringUtils.hasText(unitCode)) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billcodegenerateevent.createbillcodefailed") + BillCoreI18nUtil.getMessage("va.billcore.billcodegenerateevent.billorgcodeisnull"));
        }
        BillCodeClient billCodeClient = this.billModelService.getBillCode();
        billCodeDTO.setUnitCode(unitCode);
        billCodeDTO.setDimFormulaValue(this.parseDimFormula(data, billCodeClient, "dimformula"));
        if (billCodeDTO.getExtInfo() == null) {
            HashMap<String, String> extInfoMap = new HashMap<String, String>();
            extInfoMap.put("datedimformula", this.parseDimFormula(data, billCodeClient, "datedimformula"));
            billCodeDTO.setExtInfo(extInfoMap);
        } else {
            billCodeDTO.getExtInfo().put("datedimformula", this.parseDimFormula(data, billCodeClient, "datedimformula"));
        }
        billCodeDTO.setGenerateOpt(Integer.valueOf("1"));
        R r = billCodeClient.createBillCode(billCodeDTO);
        if (Integer.valueOf(r.get((Object)"code").toString()) == 0) {
            masterData.setValue("BILLCODE", r.get((Object)"billcode"));
            return;
        }
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billcodegenerateevent.createbillcodefailed") + r.get((Object)"msg"));
    }

    private String parseDimFormula(DataImpl data, BillCodeClient billCodeClient, String name) {
        BillCodeRuleDTO billCodeRuleDTO = new BillCodeRuleDTO();
        billCodeRuleDTO.setUniqueCode(data.getModel().getDefine().getName());
        R r = billCodeClient.getDimFormulaByUniqueCode(billCodeRuleDTO);
        String dimFormula = (String)r.get((Object)name);
        Object dimFormulaValue = null;
        if (StringUtils.hasText(dimFormula)) {
            dimFormulaValue = FormulaUtils.evaluate((String)dimFormula, (Model)data.getModel());
        }
        return dimFormulaValue == null ? "" : String.valueOf(dimFormulaValue);
    }
}


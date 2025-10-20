/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.ActionCategory
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.trans.domain.VaTransMessageDTO
 *  com.jiuqi.va.trans.service.VaTransMessageService
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.ActionCategory;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.trans.domain.VaTransMessageDTO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class EditAction
extends BillActionBase {
    private static final String PARAM_INCSSC = "inCSSC";
    private static final String PARAM_INAPPROVAL = "inApproval";
    @Autowired
    private VaTransMessageService vaTransMessageService;

    public String getName() {
        return "bill-edit";
    }

    public String getTitle() {
        return "\u4fee\u6539";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_xiugai";
    }

    public String getActionPriority() {
        return "001";
    }

    public boolean before(Model model, ActionRequest request, ActionResponse response) {
        BillModel billModel = (BillModel)model;
        BillContext billContext = billModel.getContext();
        Object consistency = billContext.getContextValue("X--consistency");
        if (consistency != null && !((Boolean)consistency).booleanValue()) {
            Integer bizState;
            DataFieldDefine bizStateField;
            Map params = request.getParams();
            if (params != null) {
                Object inSSC = params.get(PARAM_INCSSC);
                if (inSSC != null && ((Boolean)inSSC).booleanValue()) {
                    return super.before((Model)billModel, request, response);
                }
                Object inApproval = params.get(PARAM_INAPPROVAL);
                if (inApproval != null && ((Boolean)inApproval).booleanValue()) {
                    return super.before((Model)billModel, request, response);
                }
            }
            if ((bizStateField = (DataFieldDefine)billModel.getMasterTable().getDefine().getFields().find("BIZSTATE")) != null && (bizState = (Integer)billModel.getMaster().getValue("BIZSTATE")) != null && (bizState == 0 || bizState == 1 || bizState == 33 || bizState == 43 || bizState == 44 || bizState == 441 || bizState == 58 || bizState == 59)) {
                return super.before((Model)billModel, request, response);
            }
            int billState = billModel.getMaster().getInt("BILLSTATE");
            if (billState == 0 || (billState & 1) == 1) {
                return super.before((Model)billModel, request, response);
            }
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.datachangerefresh"));
        }
        return super.before(model, request, response);
    }

    @Override
    public Object executeReturn(BillModel model, Map<String, Object> params) {
        LogUtil.add((String)"\u5355\u636e", (String)"\u4fee\u6539", (String)model.getDefine().getName(), (String)model.getMaster().getString("BILLCODE"), null);
        BillModel billModel = model;
        String billCode = (String)billModel.getMaster().getValue("BILLCODE", String.class);
        VaTransMessageDTO vaTransMessageDTO = new VaTransMessageDTO();
        vaTransMessageDTO.setBizcode(billCode);
        vaTransMessageDTO.setStatus(Integer.valueOf(0));
        vaTransMessageDTO.setFilterRoot(true);
        List transMsgs = this.vaTransMessageService.listTransMessage(vaTransMessageDTO);
        if (!CollectionUtils.isEmpty(transMsgs)) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.workflowaction.processing"));
        }
        model.edit();
        return null;
    }

    public ActionCategory getActionCategory() {
        return ActionCategory.EDIT;
    }
}


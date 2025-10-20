/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.action.BillWorkflowActionBase;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.LogUtils;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RejectAction
extends BillWorkflowActionBase {
    private static final Logger logger = LoggerFactory.getLogger(RejectAction.class);

    public String getName() {
        return "bill-reject";
    }

    public String getTitle() {
        return "\u9a73\u56de";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_bohui";
    }

    public String getActionPriority() {
        return "021";
    }

    public void invoke(Model model, ActionRequest request, ActionResponse response) {
        BillModel billModel = (BillModel)model;
        WorkflowDTO workflowDTO = (WorkflowDTO)request.getContext().get("workflowDTO");
        String billCode = workflowDTO.getBizCode();
        this.checkLock(billCode);
        boolean disableSendMailFlag = false;
        if (billModel.getMasterTable().getFields().find("DISABLESENDMAILFLAG") != null) {
            disableSendMailFlag = billModel.getMaster().getBoolean("DISABLESENDMAILFLAG");
        }
        LogUtils.addLog("\u5de5\u4f5c\u6d41", "\u9a73\u56de", billModel.getDefine().getName(), billCode, null, ShiroUtil.getTenantName());
        Map todoParamMap = workflowDTO.getTodoParamMap();
        Map<String, Object> todoParam = this.billDataEditService.loadTodoParam(billModel, workflowDTO.getBizDefine());
        todoParamMap.putAll(todoParam);
        workflowDTO.setTodoParamMap(todoParamMap);
        workflowDTO.setExtInfo(new HashMap(todoParamMap));
        workflowDTO.setDisableSendMailFlag(disableSendMailFlag);
        workflowDTO.setBizId((String)billModel.getMaster().getValue("ID", String.class));
        workflowDTO.setBizUnitcode((String)billModel.getMaster().getValue("UNITCODE", String.class));
        workflowDTO.setBizModule(model.getDefine().getName().split("_")[0]);
        workflowDTO.setTransDefineName("bill-reject");
        try {
            workflowDTO.setTraceId(Utils.getTraceId());
            R r = this.workflowServerClient.completeTask(workflowDTO);
            if (0 != r.getCode()) {
                throw new BillException(r.getMsg());
            }
            response.setReturnMessage((Object)r);
        }
        catch (BillException e) {
            logger.error("\u5355\u636e{}\u9a73\u56de\u5f02\u5e38", (Object)billCode, (Object)e);
            throw e;
        }
        catch (Exception e) {
            logger.error("\u5355\u636e{}\u9a73\u56de\u5f02\u5e38", (Object)billCode, (Object)e);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.rejectaction.rejectfailed"));
        }
    }
}


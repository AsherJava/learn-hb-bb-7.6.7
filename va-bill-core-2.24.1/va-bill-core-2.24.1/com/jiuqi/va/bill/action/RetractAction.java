/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.action.BillWorkflowActionBase;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RetractAction
extends BillWorkflowActionBase {
    private static final Logger logger = LoggerFactory.getLogger(RetractAction.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getName() {
        return "bill-retract";
    }

    public String getTitle() {
        return "\u53d6\u56de";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_quhui";
    }

    public String getActionPriority() {
        return "018";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void invoke(Model model, ActionRequest request, ActionResponse response) {
        block6: {
            String billCode = ((BillModel)model).getMaster().getString("BILLCODE");
            Object workflowObj = request.getContext().get("workflowDTO");
            WorkflowDTO workflowDTO = new WorkflowDTO();
            if (workflowObj != null) {
                workflowDTO = (WorkflowDTO)workflowObj;
            }
            String key = model.getContext().getTenantName() + billCode;
            String value = UUID.randomUUID().toString();
            boolean flag = true;
            try {
                if (Boolean.TRUE.equals(this.stringRedisTemplate.opsForValue().setIfAbsent((Object)key, (Object)value))) {
                    this.stringRedisTemplate.opsForValue().set((Object)key, (Object)value, 10L, TimeUnit.SECONDS);
                    R r = this.doRetract((BillModel)model, billCode, workflowDTO);
                    response.setReturnMessage((Object)r);
                    break block6;
                }
                flag = false;
                ArrayList<CheckResult> results = new ArrayList<CheckResult>();
                CheckResultImpl result = new CheckResultImpl();
                result.setFormulaName("\u53d6\u56de\u63d0\u793a");
                result.setTargetList(new ArrayList());
                result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.retractaction.retractfailed"));
                results.add((CheckResult)result);
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
            }
            finally {
                if (flag) {
                    this.stringRedisTemplate.delete((Object)key);
                }
            }
        }
    }

    private R doRetract(BillModel model, String billCode, WorkflowDTO workflowDTO) {
        this.checkLock(billCode);
        String defineCode = (String)model.getMaster().getValue("DEFINECODE", String.class);
        workflowDTO.setBizCode(billCode);
        workflowDTO.setBizType("BILL");
        workflowDTO.setBizId((String)model.getMaster().getValue("ID", String.class));
        workflowDTO.setBizUnitcode((String)model.getMaster().getValue("UNITCODE", String.class));
        workflowDTO.setBizDefine(defineCode);
        workflowDTO.setBizModule(model.getDefine().getName().split("_")[0]);
        workflowDTO.setTransDefineName("bill-retract");
        Map<String, Object> todoParam = this.billDataEditService.loadTodoParam(model, defineCode);
        workflowDTO.setTodoParamMap(todoParam);
        try {
            workflowDTO.setTraceId(Utils.getTraceId());
            R r = this.workflowServerClient.retractProcess(workflowDTO);
            if (0 != r.getCode()) {
                throw new BillException(r.getMsg());
            }
            model.loadByCode(billCode);
            return r;
        }
        catch (BillException e) {
            logger.error("\u5355\u636e{}\u53d6\u56de\u5f02\u5e38", (Object)billCode, (Object)e);
            throw e;
        }
        catch (Exception e) {
            logger.error("\u5355\u636e{}\u53d6\u56de\u5f02\u5e38", (Object)billCode, (Object)e);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.retractaction.retractfailed"));
        }
    }
}


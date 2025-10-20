/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.action.ActionReturnConfirmMessage
 *  com.jiuqi.va.biz.intf.action.ActionReturnConfirmTemplate
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.bi.util.Guid;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.action.ActionReturnConfirmMessage;
import com.jiuqi.va.biz.intf.action.ActionReturnConfirmTemplate;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/action"})
public class BillAduitController {
    private static final Logger log = LoggerFactory.getLogger(BillAduitController.class);
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private ActionManager actionManager;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/agree"})
    public R agree(@RequestBody WorkflowDTO workflowDTO) {
        BillAduitController.setTraceId(workflowDTO);
        String defineCode = null;
        Map todoParamMap = workflowDTO.getTodoParamMap();
        defineCode = todoParamMap.get("BIZDEFINE") != null ? String.valueOf(todoParamMap.get("BIZDEFINE")) : String.valueOf(todoParamMap.get("BIZTYPE"));
        if (todoParamMap.get("BIZCODE") == null || defineCode == null) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.aduitcontroller.billparamnull"));
        }
        String billcode = String.valueOf(todoParamMap.get("BIZCODE"));
        BillContextImpl billContextImpl = new BillContextImpl();
        billContextImpl.setDisableVerify(true);
        billContextImpl.setContextValue("nodeId", workflowDTO.getTaskId());
        billContextImpl.setContextValue("billCode", billcode);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)billContextImpl, defineCode);
        model.loadByCode(billcode);
        workflowDTO.setBizCode(billcode);
        workflowDTO.setBizDefine(defineCode);
        Action agreeAction = (Action)this.actionManager.get("bill-agree");
        ActionRequest request = new ActionRequest();
        HashMap<String, Object> params = new HashMap<String, Object>();
        if (workflowDTO.getExtInfo("confirmed") != null) {
            params.put("confirmed", true);
        }
        if (workflowDTO.getExtInfo("confirms") != null) {
            params.put("confirms", workflowDTO.getExtInfo("confirms"));
        }
        request.setParams(params);
        request.getContext().put("workflowDTO", workflowDTO);
        ActionResponse response = new ActionResponse();
        try {
            model.getRuler().getRulerExecutor().setEnable(true);
            model.executeAction(agreeAction, request, response);
            R r = R.ok();
            if (!response.isSuccess() && response.getCheckMessages() != null && !response.getCheckMessages().isEmpty()) {
                r = R.error();
                r.put("msg", (Object)response.getCheckMessages());
                R r2 = r;
                return r2;
            }
            Object executeReturn = null;
            if (response.getReturnValue() != null) {
                executeReturn = response.getReturnValue();
                this.setConfirms(params.get("confirms"), executeReturn);
                r.put("code", (Object)2);
                r.put("result", executeReturn);
                R r3 = r;
                return r3;
            }
            executeReturn = response.getReturnMessage();
            R r4 = (R)executeReturn;
            return r4;
        }
        catch (BillException e) {
            log.error("\u5ba1\u6279\u5f02\u5e38", e);
            R r = R.error();
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null) {
                r.put("isCheckMessage", (Object)true);
                r.put("checkMessages", checkMessages);
            }
            r.put("msg", (Object)e.getMessage());
            R r5 = r;
            return r5;
        }
        catch (Exception e) {
            log.error("\u5ba1\u6279\u5f02\u5e38", e);
            R r = R.error((String)e.getMessage());
            return r;
        }
        finally {
            model.getRuler().getRulerExecutor().setEnable(false);
        }
    }

    private void setConfirms(Object confirms, Object executeReturn) {
        R r;
        if (executeReturn instanceof ActionReturnConfirmMessage) {
            ActionReturnConfirmMessage confirmMessage = (ActionReturnConfirmMessage)executeReturn;
            String confirmFrom = confirmMessage.getConfirmFrom();
            if (StringUtils.hasText(confirmFrom)) {
                confirmMessage.setConfirms(confirms);
            }
        } else if (executeReturn instanceof ActionReturnConfirmTemplate) {
            ActionReturnConfirmTemplate confirmMessage = (ActionReturnConfirmTemplate)executeReturn;
            String confirmFrom = confirmMessage.getConfirmFrom();
            if (StringUtils.hasText(confirmFrom)) {
                confirmMessage.setConfirms(confirms);
            }
        } else if (executeReturn instanceof R && (r = (R)executeReturn).get((Object)"rejectInfos") != null) {
            r.put("confirms", confirms);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/reject"})
    public R reject(@RequestBody WorkflowDTO workflowDTO) {
        BillAduitController.setTraceId(workflowDTO);
        Map todoParamMap = workflowDTO.getTodoParamMap();
        String defineCode = null;
        defineCode = todoParamMap.get("BIZDEFINE") != null ? String.valueOf(todoParamMap.get("BIZDEFINE")) : String.valueOf(todoParamMap.get("BIZTYPE"));
        if (todoParamMap.get("BIZCODE") == null || defineCode == null) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.aduitcontroller.billparamnull"));
        }
        String billcode = todoParamMap.get("BIZCODE").toString();
        BillContextImpl billContextImpl = new BillContextImpl();
        billContextImpl.setDisableVerify(true);
        billContextImpl.setContextValue("nodeId", workflowDTO.getTaskId());
        billContextImpl.setContextValue("billCode", billcode);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)billContextImpl, defineCode);
        model.loadByCode(billcode);
        Action rejectAction = (Action)this.actionManager.get("bill-reject");
        ActionRequest request = new ActionRequest();
        request.getContext().put("workflowDTO", workflowDTO);
        ActionResponse response = new ActionResponse();
        try {
            model.getRuler().getRulerExecutor().setEnable(true);
            model.executeAction(rejectAction, request, response);
            if (!response.isSuccess() && response.getCheckMessages() != null) {
                R r = R.error();
                r.put("msg", (Object)((CheckResult)response.getCheckMessages().get(0)).getCheckMessage());
                R r2 = r;
                return r2;
            }
            R r = R.ok();
            Object executeReturn = null;
            if (response.getReturnValue() != null) {
                executeReturn = response.getReturnValue();
                r.put("code", (Object)2);
                r.put("result", executeReturn);
                R r3 = r;
                return r3;
            }
            executeReturn = response.getReturnMessage();
            R r4 = (R)executeReturn;
            return r4;
        }
        catch (Exception e) {
            log.error("\u9a73\u56de\u5f02\u5e38", e);
            R r = R.error((String)e.getMessage());
            return r;
        }
        finally {
            model.getRuler().getRulerExecutor().setEnable(false);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/retract"})
    public R retract(@RequestBody WorkflowDTO workflowDTO) {
        BillAduitController.setTraceId(workflowDTO);
        String bizCode = workflowDTO.getBizCode();
        String bizDefine = workflowDTO.getBizDefine();
        BillContextImpl billContextImpl = new BillContextImpl();
        billContextImpl.setDisableVerify(true);
        billContextImpl.setContextValue("nodeId", workflowDTO.getTaskId());
        billContextImpl.setContextValue("billCode", bizCode);
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)billContextImpl, bizDefine);
        model.loadByCode(bizCode);
        Action retractAction = (Action)this.actionManager.get("bill-retract");
        ActionRequest request = new ActionRequest();
        request.getContext().put("workflowDTO", workflowDTO);
        ActionResponse response = new ActionResponse();
        try {
            model.getRuler().getRulerExecutor().setEnable(true);
            model.executeAction(retractAction, request, response);
            if (!response.isSuccess() && response.getCheckMessages() != null) {
                R r = R.error();
                r.put("msg", (Object)((CheckResult)response.getCheckMessages().get(0)).getCheckMessage());
                R r2 = r;
                return r2;
            }
            R r = (R)response.getReturnMessage();
            return r;
        }
        catch (Exception e) {
            log.error("\u53d6\u56de\u5f02\u5e38", e);
            R r = R.error((String)e.getMessage());
            return r;
        }
        finally {
            model.getRuler().getRulerExecutor().setEnable(false);
        }
    }

    private static void setTraceId(WorkflowDTO workflowDTO) {
        String traceId = workflowDTO.getTraceId();
        if (StringUtils.hasText(traceId)) {
            Utils.setTraceId((String)traceId);
        } else {
            Utils.setTraceId((String)Guid.newGuid());
        }
    }
}


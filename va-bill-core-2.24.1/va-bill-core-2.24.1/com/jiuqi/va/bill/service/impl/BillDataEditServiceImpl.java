/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.BussinessState
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.feign.client.BillVerifyClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.service.BillCoreWorkFlowService;
import com.jiuqi.va.bill.service.BillDataEditService;
import com.jiuqi.va.bill.utils.Base64Utils;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.BussinessState;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.feign.client.BillVerifyClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillDataEditServiceImpl
implements BillDataEditService {
    private static final Logger log = LoggerFactory.getLogger(BillDataEditServiceImpl.class);
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private WorkflowServerClient workflowServerClient;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private BillVerifyClient billVerifyClient;
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private BillCoreWorkFlowService billCoreWorkFlowService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public R changeBillState(TenantDO tenantDO) {
        BillContextImpl billContextImpl = new BillContextImpl();
        billContextImpl.setDisableVerify(true);
        BillModel model = this.billDefineService.createModel((BillContext)billContextImpl, (String)tenantDO.getExtInfo("bizType"));
        model.loadByCode((String)tenantDO.getExtInfo("bizCode"));
        model.getData().edit();
        String state = (String)tenantDO.getExtInfo("bizState");
        model.getMaster().setValue("BILLSTATE", state.equals(BussinessState.UNSUBMITTED.name()) ? Integer.valueOf(BillState.SAVED.getValue()) : model.getMaster().getValue("BILLSTATE"));
        model.getData().save();
        return R.ok();
    }

    @Override
    public Map<String, Object> getBussinessParamVariables(TenantDO tenantDO) {
        String bizCode = (String)tenantDO.getExtInfo("bizCode");
        String bizType = (String)tenantDO.getExtInfo("bizType");
        String workflowdefinekey = (String)tenantDO.getExtInfo("workflowdefinekey");
        Long workflowdefineversion = (Long)tenantDO.getExtInfo("workflowdefineversion");
        BillContextImpl billContextImpl = new BillContextImpl();
        billContextImpl.setDisableVerify(true);
        BillModel model = this.billDefineService.createModel((BillContext)billContextImpl, bizType);
        model.loadByCode(bizCode);
        Map<String, Object> workflow = this.findworkflow(workflowdefinekey, workflowdefineversion, model);
        return this.billCoreWorkFlowService.getWorkFlowParamsValueMap(model, workflow);
    }

    @Override
    public Map<String, Object> getMessageTemplateParam(TenantDO tenantDO) {
        Object bizCode = tenantDO.getExtInfo("bizCode");
        Object bizDefine = tenantDO.getExtInfo("bizDefine");
        Object submitUser = tenantDO.getExtInfo("submitUser");
        String PCBillUrl = "/#/sso/bill/" + bizDefine + "/" + bizCode + "/BROWSE";
        String billUrl = "/gmt/bill/billApproval?fromType=1&defineCode=" + bizDefine + "&billCode=" + bizCode;
        TenantDO tenantDO1 = new TenantDO();
        tenantDO1.addExtInfo("defineCode", bizDefine);
        tenantDO1.setTraceId(Utils.getTraceId());
        R r = this.metaDataClient.findMetaInfoByDefineCode(tenantDO1);
        String bizDefineTitle = (String)r.get((Object)"title");
        String bizCodeTitle = bizCode.toString();
        HashMap<String, Object> appConfigJson = new HashMap<String, Object>();
        appConfigJson.put("defineCode", bizDefine);
        appConfigJson.put("billCode", bizCode);
        appConfigJson.put("dataState", "BROWSE");
        Object createUser = null;
        if (!submitUser.equals(createUser)) {
            ArrayList<String> userIds = new ArrayList<String>();
            userIds.add(submitUser.toString());
            BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
            billVerifyDTO.setBillCode(bizCode.toString());
            billVerifyDTO.setUserIds(userIds);
            billVerifyDTO.setAuth(15);
            r = this.billVerifyClient.encodeBillCode(billVerifyDTO);
            Map verifyMap = (Map)r.get((Object)"data");
            String verifyCode = (String)verifyMap.get(submitUser);
            PCBillUrl = PCBillUrl + "/" + verifyCode;
            billUrl = billUrl + "&verifyCode=" + verifyCode;
            appConfigJson.put("verifyCode", verifyCode);
        }
        HashMap<String, String> paramJson = new HashMap<String, String>();
        paramJson.put("appName", "bill-app");
        paramJson.put("appTitle", "\u5ba1\u6279\u7ed3\u679c-" + bizDefineTitle + bizCodeTitle);
        paramJson.put("funcName", "VaBill");
        paramJson.put("appConfig", JSONUtil.toJSONString(appConfigJson));
        HashMap<String, Object> resultJson = new HashMap<String, Object>();
        resultJson.put("bizDefineTitle", bizDefineTitle);
        resultJson.put("bizCodeTitle", bizCodeTitle);
        resultJson.put("url", PCBillUrl);
        resultJson.put("mobileUrl", Base64Utils.encodeToUrlSafeString(billUrl.getBytes()));
        resultJson.put("appParam", paramJson);
        return resultJson;
    }

    private Map<String, Object> findworkflow(String workflowdefinekey, Long workflowdefineversion, BillModel model) {
        WorkflowBusinessDTO business = new WorkflowBusinessDTO();
        business.setBusinesscode(model.getDefine().getName());
        business.setWorkflowdefinekey(workflowdefinekey);
        business.setWorkflowdefineversion(workflowdefineversion);
        business.setStopflag(Integer.valueOf(-1));
        business.setShowTitle(false);
        business.setTraceId(Utils.getTraceId());
        R r = this.workflowServerClient.getBusinessBoundedWorkflow(business);
        if (0 != r.getCode()) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.billcore.billdataeditservice.getworkflowbusinessexception"));
        }
        Map mapdata = (Map)r.get((Object)"data");
        List ja = (List)mapdata.get("workflows");
        return (Map)ja.get(0);
    }

    @Override
    public Map<String, Object> getTodoParam(TenantDO tenantDO) {
        String bizCode = (String)tenantDO.getExtInfo("bizCode");
        String bizDefine = (String)tenantDO.getExtInfo("bizDefine");
        BillContextImpl billContextImpl = new BillContextImpl();
        billContextImpl.setDisableVerify(true);
        BillModel model = this.billDefineService.createModel((BillContext)billContextImpl, bizDefine);
        model.loadByCode(bizCode);
        return this.loadTodoParam(model, bizDefine);
    }

    @Override
    public Map<String, Object> loadTodoParam(BillModel model, String definecode) {
        HashMap<String, Object> todoParamMap = new HashMap<String, Object>();
        this.addBasicParam(model, todoParamMap);
        try {
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("bizType", (Object)"BILL");
            tenantDO.addExtInfo("bizDefine", (Object)definecode);
            tenantDO.setTraceId(Utils.getTraceId());
            R r = this.todoClient.getToDoParamconfig(tenantDO);
            if (r != null && r.getCode() == 0) {
                Map data = (Map)r.get((Object)"todoParamConfig");
                List todoParams = (List)data.get("todoparam");
                for (Map todoParam : todoParams) {
                    this.processTodoParam(model, todoParam, todoParamMap);
                }
            }
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6\u5f85\u529e\u53c2\u6570\u53d6\u503c\u914d\u7f6e\u5931\u8d25", e);
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.billcore.workflowaction.paramsettingexception"));
        }
        return todoParamMap;
    }

    private void processTodoParam(BillModel model, Map<String, Object> todoParam, Map<String, Object> todoParamMap) {
        String paramName = BillUtils.valueToString(todoParam.get("columnName"));
        if ("BIZDEFINE".equals(paramName) || "UNITCODE".equals(paramName)) {
            return;
        }
        Map valueformula = (Map)todoParam.get("PRIORITY");
        String expression = BillUtils.valueToString(valueformula.get("expression"));
        if (expression == null || expression.isEmpty()) {
            return;
        }
        FormulaImpl formulaImpl = new FormulaImpl();
        formulaImpl.setExpression(expression);
        formulaImpl.setFormulaType(FormulaType.valueOf((String)((String)valueformula.get("formulaType"))));
        Object formulaResult = FormulaUtils.executeFormula((FormulaImpl)formulaImpl, (Model)model);
        if (formulaResult == null) {
            return;
        }
        formulaResult = this.processFormulaResult(todoParam, formulaResult, paramName);
        todoParamMap.put(paramName, formulaResult);
    }

    private Object processFormulaResult(Map<String, Object> todoParam, Object formulaResult, String paramName) {
        if (formulaResult instanceof Boolean) {
            formulaResult = (Boolean)formulaResult != false ? 1 : 0;
        }
        String paramTitle = BillUtils.valueToString(todoParam.get("columnTitle"));
        String paramType = BillUtils.valueToString(todoParam.get("columnType"));
        Class<Object> javaType = JdbcJavaType.valueOf(paramType).getName();
        if (javaType.isAssignableFrom(Date.class) && formulaResult instanceof GregorianCalendar) {
            formulaResult = ((GregorianCalendar)formulaResult).getTime();
        }
        if (javaType.isAssignableFrom(String.class)) {
            formulaResult = String.valueOf(formulaResult);
        }
        if (!(javaType.isAssignableFrom(formulaResult.getClass()) || "NUMERIC".equals(paramType) && BigDecimal.class.isAssignableFrom(formulaResult.getClass()))) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.billcore.workflowaction.paramtypeerror", new Object[]{paramTitle}));
        }
        if ("REMARK".equals(paramName) && formulaResult instanceof String) {
            String remark = String.valueOf(formulaResult);
            formulaResult = this.processRemark(remark);
        }
        return formulaResult;
    }

    private void addBasicParam(BillModel model, Map<String, Object> todoParamMap) {
        DataRow master = model.getMaster();
        NamedContainer fields = model.getMasterTable().getDefine().getFields();
        todoParamMap.put("PRIORITY", this.getUrgentState((NamedContainer<? extends DataFieldDefine>)fields, master));
        if (fields.find("BILLMONEY") != null) {
            todoParamMap.put("BILLMONEY", master.getValue("BILLMONEY"));
        }
        if (fields.find("MEMO") != null) {
            todoParamMap.put("REMARK", master.getValue("MEMO"));
        }
        todoParamMap.put("BIZDATE", new SimpleDateFormat("yyyy-MM-dd").format((Date)master.getValue("BILLDATE", Date.class)));
        todoParamMap.put("UNITCODE", master.getValue("UNITCODE", String.class));
        todoParamMap.put("CREATEUSER", master.getValue("CREATEUSER", String.class));
    }

    private String getUrgentState(NamedContainer<? extends DataFieldDefine> fields, DataRow master) {
        String urgentstate = null;
        if (fields.find("URGENTSTATECODE") != null) {
            urgentstate = (String)master.getValue("URGENTSTATECODE", String.class);
        }
        if (urgentstate == null || urgentstate.isEmpty()) {
            urgentstate = "01";
        }
        return urgentstate;
    }

    private String processRemark(String remark) {
        if (!remark.startsWith("[")) {
            return this.getOrSplitRemark(remark);
        }
        try {
            List remarkArray = JSONUtil.parseArray((String)remark, String.class);
            this.processRemarkArray(remarkArray);
            return remarkArray.size() > 1 ? JSONUtil.toJSONString((Object)remarkArray) : remark;
        }
        catch (Exception e) {
            return this.getOrSplitRemark(remark);
        }
    }

    private void processRemarkArray(List<String> remarkArray) {
        for (int i = 0; i < remarkArray.size(); ++i) {
            String temp;
            String[] array;
            if (i > 9) {
                remarkArray.remove(i);
            }
            if ((array = (temp = remarkArray.get(i)).split("\uff1a", 2)).length <= 1) continue;
            if (array[0].length() > 10) {
                array[0] = array[0].substring(0, 10);
            }
            if (array[1] != null && array[1].length() > 75) {
                array[1] = array[1].substring(0, 72) + "...";
            }
            remarkArray.set(i, array[0] + "\uff1a" + array[1]);
        }
    }

    private String getOrSplitRemark(String remark) {
        return remark.length() > 1024 ? remark.substring(0, 1024) : remark;
    }

    public static enum JdbcJavaType {
        UUID(UUID.class),
        NVARCHAR(String.class),
        INTEGER(Integer.class),
        NUMERIC(Double.class),
        DATE(Date.class),
        TIMESTAMP(Date.class),
        CLOB(Clob.class);

        private final Class<?> name;

        private JdbcJavaType(Class<?> name) {
            this.name = name;
        }

        public Class<?> getName() {
            return this.name;
        }
    }
}


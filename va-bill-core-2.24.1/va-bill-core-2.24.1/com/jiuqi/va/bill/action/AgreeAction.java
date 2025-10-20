/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.data.DataTargetType
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.ruler.ModelFormulaHandle
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.impl.DataTargetImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.BizBindingI18nUtil
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO
 *  com.jiuqi.va.feign.client.WorkflowProcessReviewClient
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.bill.action.BillWorkflowActionBase;
import com.jiuqi.va.bill.domain.option.BillRuleOptionVO;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.service.BillRuleOptionService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.bill.utils.LogUtils;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTargetType;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.impl.DataTargetImpl;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO;
import com.jiuqi.va.feign.client.WorkflowProcessReviewClient;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class AgreeAction
extends BillWorkflowActionBase {
    private static final Logger logger = LoggerFactory.getLogger(AgreeAction.class);
    @Autowired
    private BillRuleOptionService billRuleOptionService;
    @Autowired
    private WorkflowProcessReviewClient workflowProcessReviewClient;
    @Autowired
    private WorkflowServerClient workflowServerClient;

    public String getName() {
        return "bill-agree";
    }

    public String getTitle() {
        return "\u540c\u610f";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_tongyi";
    }

    public String getActionPriority() {
        return "020";
    }

    public void invoke(Model model, ActionRequest request, ActionResponse response) {
        BillModel billmodel = (BillModel)model;
        WorkflowDTO workflowDTO = (WorkflowDTO)request.getContext().get("workflowDTO");
        String defineCode = workflowDTO.getBizDefine();
        String billCode = workflowDTO.getBizCode();
        try {
            this.checkLock(billCode);
            LogUtils.addLog("\u5de5\u4f5c\u6d41", "\u540c\u610f", billmodel.getDefine().getName(), billCode, null, ShiroUtil.getTenantName());
            Map todoParamMap = workflowDTO.getTodoParamMap();
            String taskDefinekey = todoParamMap.get("TASKDEFINEKEY").toString();
            Map<String, Object> billWorkflowRelation = this.getbillWorkflowRelation(workflowDTO, defineCode);
            BillModel billModel = (BillModel)model;
            if (!workflowDTO.isConsult()) {
                this.checkEditableTable(billModel, taskDefinekey, billWorkflowRelation);
                this.checkEditableFields(billModel, taskDefinekey, billWorkflowRelation);
                if (workflowDTO.getExtInfo() == null || workflowDTO.getExtInfo().get("rejectInfos") == null) {
                    BillRuleOptionVO billRuleOptionVO;
                    OptionItemDTO optionItemDTO = new OptionItemDTO();
                    optionItemDTO.setName("BR1001");
                    optionItemDTO.setControlModel(Boolean.valueOf(true));
                    optionItemDTO.setUnitcode(billmodel.getMaster().getString("UNITCODE"));
                    List<BillRuleOptionVO> list = this.billRuleOptionService.list(optionItemDTO);
                    if (!CollectionUtils.isEmpty(list) && this.check(billRuleOptionVO = list.get(0), defineCode)) {
                        WorkflowProcessReviewDTO workflowProcessReviewDTO = new WorkflowProcessReviewDTO();
                        workflowProcessReviewDTO.setBizcode(billCode);
                        workflowProcessReviewDTO.setRejectednodeid(workflowDTO.getTaskId());
                        workflowProcessReviewDTO.setRequired(this.required(billRuleOptionVO));
                        workflowProcessReviewDTO.setTraceId(Utils.getTraceId());
                        String nextNodeCode = workflowDTO.getNextNodeCode();
                        if (StringUtils.hasText(nextNodeCode)) {
                            ProcessNodeDO processNodeDO;
                            ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
                            processNodeDTO.setNodecode(nextNodeCode);
                            processNodeDTO.setBizcode(billCode);
                            processNodeDTO.setSort("ordernum");
                            processNodeDTO.setOrder("desc");
                            processNodeDTO.setTraceId(Utils.getTraceId());
                            List processNodeDOS = this.workflowServerClient.listProcessNode(processNodeDTO);
                            if (!CollectionUtils.isEmpty(processNodeDOS) && (processNodeDO = (ProcessNodeDO)processNodeDOS.get(0)).getRejectstatus() != null) {
                                R rejectInfos = this.workflowProcessReviewClient.getRejectInfos(workflowProcessReviewDTO);
                                if (rejectInfos.getCode() == 1) {
                                    logger.error(rejectInfos.getMsg());
                                } else if (rejectInfos.getCode() == 0 && !CollectionUtils.isEmpty((List)rejectInfos.get((Object)"rejectInfos"))) {
                                    R r = R.ok();
                                    r.put("rejectInfos", rejectInfos.get((Object)"rejectInfos"));
                                    response.setReturnValue((Object)r);
                                    response.setSuccess(false);
                                    return;
                                }
                            }
                        } else {
                            R rejectInfos = this.workflowProcessReviewClient.getRejectInfos(workflowProcessReviewDTO);
                            if (rejectInfos.getCode() == 0 && !CollectionUtils.isEmpty((List)rejectInfos.get((Object)"rejectInfos"))) {
                                R r = R.ok();
                                r.put("rejectInfos", rejectInfos.get((Object)"rejectInfos"));
                                response.setReturnValue((Object)r);
                                response.setSuccess(false);
                                return;
                            }
                        }
                    }
                }
                if (workflowDTO.getExtInfo() != null && workflowDTO.getExtInfo().get("rejectInfos") != null) {
                    List list = JSONUtil.parseArray((String)JSONUtil.toJSONString(workflowDTO.getExtInfo().get("rejectInfos")), WorkflowProcessReviewDTO.class);
                    list.forEach(o -> o.setTraceId(Utils.getTraceId()));
                    R r = this.workflowProcessReviewClient.syncReviews(list);
                    if (r.getCode() == 1) {
                        logger.error(r.getMsg());
                    }
                }
            }
            boolean disableSendMailFlag = false;
            if (billModel.getMasterTable().getFields().find("DISABLESENDMAILFLAG") != null) {
                disableSendMailFlag = billModel.getMaster().getBoolean("DISABLESENDMAILFLAG");
            }
            workflowDTO.setDisableSendMailFlag(disableSendMailFlag);
            workflowDTO.setBizId((String)billModel.getMaster().getValue("ID", String.class));
            workflowDTO.setBizUnitcode((String)billModel.getMaster().getValue("UNITCODE", String.class));
            Map<String, Object> todoParam = this.billDataEditService.loadTodoParam(billModel, defineCode);
            todoParamMap.putAll(todoParam);
            workflowDTO.setTodoParamMap(todoParamMap);
            workflowDTO.setExtInfo(new HashMap(todoParamMap));
            workflowDTO.setBizModule(model.getDefine().getName().split("_")[0]);
            workflowDTO.setTransDefineName("bill-complete");
            workflowDTO.setTraceId(Utils.getTraceId());
            R r = this.workflowServerClient.completeTask(workflowDTO);
            if (0 != r.getCode()) {
                throw new BillException(r.getMsg());
            }
            response.setReturnMessage((Object)r);
        }
        catch (BillException e) {
            logger.error("\u5355\u636e{}\u5ba1\u6279\u5f02\u5e38", (Object)billCode, (Object)e);
            throw e;
        }
        catch (Exception e) {
            logger.error("\u5355\u636e{}\u5ba1\u6279\u5f02\u5e38", (Object)billCode, (Object)e);
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.agreeaction.agreefailed"));
        }
    }

    private Boolean required(BillRuleOptionVO billRuleOptionVO) {
        Map map = JSONUtil.parseMap((String)billRuleOptionVO.getVal());
        if (map.get("required") == null) {
            return false;
        }
        return (boolean)((Boolean)map.get("required"));
    }

    private boolean check(BillRuleOptionVO billRuleOptionVO, String definecode) {
        if (billRuleOptionVO.getVal() == null || billRuleOptionVO.getVal().equals("{}")) {
            return false;
        }
        Map map = JSONUtil.parseMap((String)billRuleOptionVO.getVal());
        Object fillModify = map.get("fillModify");
        if (fillModify == null || !((Boolean)fillModify).booleanValue()) {
            return false;
        }
        Object allBill = map.get("allBill");
        if (allBill != null && ((Boolean)allBill).booleanValue()) {
            return true;
        }
        String storageValue = billRuleOptionVO.getStorageValue();
        if (storageValue == null) {
            return false;
        }
        for (Map stringObjectMap : JSONUtil.parseMapArray((String)storageValue)) {
            if (!definecode.equals(stringObjectMap.get("uniqueCode"))) continue;
            return true;
        }
        return false;
    }

    private void checkEditableTable(BillModel model, String taskDefinekey, Map<String, Object> billWorkflowRelation) {
        List editableTableJsonArray = (List)billWorkflowRelation.get("editabletable");
        if (editableTableJsonArray != null) {
            for (int i = 0; i < editableTableJsonArray.size(); ++i) {
                List editableTables;
                Map editableTableJsonObject = (Map)editableTableJsonArray.get(i);
                String nodeId = BillUtils.valueToString(editableTableJsonObject.get("nodeId"));
                if (!taskDefinekey.equals(nodeId) || (editableTables = (List)editableTableJsonObject.get("editabletables")) == null) continue;
                for (int j = 0; j < editableTables.size(); ++j) {
                    ListContainer rowDatas;
                    Map editableTable = (Map)editableTables.get(j);
                    if (editableTable.get("required") == null || !((Boolean)editableTable.get("required")).booleanValue()) continue;
                    String tableName = BillUtils.valueToString(editableTable.get("tableName"));
                    DataTable table = (DataTable)model.getData().getTables().get(tableName);
                    String tableTitle = table.getDefine().getTitle();
                    if (!StringUtils.hasText(tableTitle)) {
                        tableTitle = BillUtils.valueToString(editableTable.get("tableTitle"));
                    }
                    if ((rowDatas = table.getRows()).size() != 0) continue;
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.agreeaction.tablerequired", new Object[]{tableTitle}));
                }
            }
        }
    }

    private void checkEditableFields(BillModel model, String taskDefinekey, Map<String, Object> billWorkflowRelation) {
        DataTableNodeContainerImpl tables = ((DataDefineImpl)((Plugin)model.getPlugins().get("data")).getDefine()).getTables();
        ArrayList<CheckResult> results = new ArrayList<CheckResult>();
        List editableFieldJsonArray = (List)billWorkflowRelation.get("editablefield");
        if (editableFieldJsonArray == null) {
            return;
        }
        int editTableArraySize = editableFieldJsonArray.size();
        for (int i = 0; i < editTableArraySize; ++i) {
            Map editableFieldJsonObject = (Map)editableFieldJsonArray.get(i);
            String nodeId = BillUtils.valueToString(editableFieldJsonObject.get("nodeId"));
            if (!taskDefinekey.equals(nodeId)) continue;
            List editTableFields = (List)editableFieldJsonObject.get("editablefields");
            if (editTableFields == null) {
                return;
            }
            int size = editTableFields.size();
            for (int j = 0; j < size; ++j) {
                Map editTableField = (Map)editTableFields.get(j);
                Object requiredParam = editTableField.get("required");
                if (ObjectUtils.isEmpty(requiredParam)) continue;
                BillModelImpl billModel = (BillModelImpl)model;
                DataTableNodeContainerImpl tablesData = billModel.getData().getTables();
                String tableName = BillUtils.valueToString(editTableField.get("tableName"));
                DataTableImpl dataTable = (DataTableImpl)tablesData.get(tableName);
                String fieldName = BillUtils.valueToString(editTableField.get("name"));
                ListContainer rows = dataTable.getRows();
                DataFieldDefineImpl dataFieldDefine = null;
                boolean requireFlag = false;
                if (requiredParam instanceof Map) {
                    Map<String, DataRow> rowMap;
                    Map requiredParamMap = (Map)requiredParam;
                    Object expression = requiredParamMap.get("expression");
                    if (ObjectUtils.isEmpty(expression)) continue;
                    Object formulaType = requiredParamMap.get("formulaType");
                    IExpression compiledExpression = null;
                    try {
                        compiledExpression = ModelFormulaHandle.getInstance().parse(new ModelDataContext((Model)model), String.valueOf(expression), FormulaType.valueOf((String)String.valueOf(formulaType)));
                    }
                    catch (ParseException e) {
                        throw new RuntimeException(BizBindingI18nUtil.getMessage((String)"va.bizbinding.formulautils.parseformulaexception", (Object[])new Object[]{expression}));
                    }
                    logger.info("\u516c\u5f0f\uff1a{}", (Object)JSONUtil.toJSONString(expression));
                    if (compiledExpression == null) {
                        logger.error("\u516c\u5f0f\u7f16\u8bd1\u540e\u4e3a\u7a7a" + j);
                        continue;
                    }
                    String propTable = FormulaUtils.computePropTable((ModelDefine)model.getDefine(), (IExpression)compiledExpression);
                    if (tableName.equals(propTable)) {
                        for (int i1 = 0; i1 < rows.size(); ++i1) {
                            DataRowImpl dataRow = (DataRowImpl)rows.get(i1);
                            rowMap = Stream.of(dataRow).collect(Collectors.toMap(k -> tableName, v -> v));
                            FormulaUtils.adjustFormulaRows((Data)((Data)billModel.getPlugins().get(Data.class)), rowMap);
                            try {
                                boolean required = (Boolean)Convert.cast((Object)FormulaUtils.evaluate((Model)billModel, (IExpression)compiledExpression, rowMap), Boolean.TYPE);
                                logger.info("\u516c\u5f0f\u6267\u884c\u7ed3\u679c\uff1a{}", (Object)required);
                                if (!required) continue;
                                this.checkFieldRequire(editTableField, dataFieldDefine, (DataTableNodeContainerImpl<? extends DataTableDefineImpl>)tables, dataRow, i1, results, tableName, fieldName);
                                continue;
                            }
                            catch (SyntaxException e) {
                                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.checkrequired.executerequiredformulafailed") + String.format("%s.%s", tableName, fieldName), e);
                            }
                        }
                        continue;
                    }
                    DataRow master = billModel.getMaster();
                    String masterTableName = billModel.getMasterTable().getName();
                    rowMap = Stream.of(master).collect(Collectors.toMap(k -> masterTableName, v -> v));
                    try {
                        requireFlag = (Boolean)Convert.cast((Object)FormulaUtils.evaluate((Model)billModel, (IExpression)compiledExpression, rowMap), Boolean.TYPE);
                        logger.info("\u516c\u5f0f\u6267\u884c\u7ed3\u679c\uff1a{}", (Object)requireFlag);
                    }
                    catch (SyntaxException e) {
                        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.checkrequired.executerequiredformulafailed") + String.format("%s.%s", tableName, fieldName), e);
                    }
                }
                if (requiredParam instanceof Boolean) {
                    requireFlag = (Boolean)requiredParam;
                }
                if (!requireFlag) continue;
                for (int i1 = 0; i1 < rows.size(); ++i1) {
                    DataRowImpl dataRow = (DataRowImpl)rows.get(i1);
                    this.checkFieldRequire(editTableField, dataFieldDefine, (DataTableNodeContainerImpl<? extends DataTableDefineImpl>)tables, dataRow, i1, results, tableName, fieldName);
                }
            }
            break;
        }
        if (results.size() >= 1) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.agreeaction.editfieldrequiredcheck"), results);
        }
    }

    private void checkFieldRequire(Map<String, Object> editTableField, DataFieldDefineImpl dataFieldDefine, DataTableNodeContainerImpl<? extends DataTableDefineImpl> tables, DataRowImpl dataRow, int rowIndex, List<CheckResult> results, String tableName, String fieldName) {
        String fieldTitle;
        Object fieldValue = dataRow.getValue(fieldName);
        if (fieldValue != null) {
            if (fieldValue instanceof Number) {
                double abs = Math.abs(((Number)fieldValue).doubleValue());
                if (abs >= 1.0E-6) {
                    return;
                }
                if (abs == 0.0) {
                    if (dataFieldDefine == null) {
                        dataFieldDefine = (DataFieldDefineImpl)((DataTableDefineImpl)tables.get(tableName)).getFields().get(fieldName);
                    }
                    if (dataFieldDefine.isDisZero() || dataFieldDefine.getRefTableType() != 0) {
                        return;
                    }
                }
            } else if (fieldValue instanceof String) {
                if (((String)fieldValue).length() > 0) {
                    return;
                }
            } else {
                return;
            }
        }
        if (!StringUtils.hasText(fieldTitle = ((DataFieldDefineImpl)((DataTableDefineImpl)tables.get(tableName)).getFields().get(fieldName)).getTitle())) {
            fieldTitle = BillUtils.valueToString(editTableField.get("title"));
        }
        ArrayList<DataTargetImpl> targetList = new ArrayList<DataTargetImpl>();
        DataTargetImpl dataTarget = new DataTargetImpl();
        dataTarget.setFieldName(fieldName);
        dataTarget.setTableName(tableName);
        dataTarget.setRowIndex(rowIndex);
        dataTarget.setTargetType(DataTargetType.DATACELL);
        targetList.add(dataTarget);
        CheckResultImpl checkResult = new CheckResultImpl();
        checkResult.setFormulaName("\u5b57\u6bb5\u5fc5\u586b");
        checkResult.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{fieldTitle}));
        checkResult.setTargetList(targetList);
        results.add((CheckResult)checkResult);
    }

    @Override
    public String[] getModelParams() {
        return super.getModelParams();
    }
}


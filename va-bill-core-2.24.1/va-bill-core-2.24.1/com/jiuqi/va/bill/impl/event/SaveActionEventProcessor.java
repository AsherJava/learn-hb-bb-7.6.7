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
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.action.GlobalActionEventProcessor
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
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 */
package com.jiuqi.va.bill.impl.event;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.action.GlobalActionEventProcessor;
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
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class SaveActionEventProcessor
implements GlobalActionEventProcessor {
    private static final Logger logger = LoggerFactory.getLogger(SaveActionEventProcessor.class);
    @Autowired
    private WorkflowServerClient workflowServerClient;

    public boolean beforeAction(Model model, Action action, ActionRequest request, ActionResponse response) {
        Map param;
        if (!"bill-save".equals(action.getName())) {
            return true;
        }
        BillModel billModel = (BillModel)model;
        int billState = (Integer)billModel.getMaster().getValue("BILLSTATE", Integer.TYPE);
        if (!(BillState.COMMITTED.getValue() != billState && BillState.AUDITING.getValue() != billState || (param = request.getParams()).containsKey("aduit") && "reject".equals(param.get("aduit")))) {
            String defineCode = (String)billModel.getMaster().getValue("DEFINECODE", String.class);
            if (ObjectUtils.isEmpty(param.get("PROCESSDEFINEKEY"))) {
                return true;
            }
            String workflowdefinekey = String.valueOf(param.get("PROCESSDEFINEKEY"));
            Long workflowdefineversion = Long.valueOf(String.valueOf(param.get("PROCESSDEFINEVERSION")));
            WorkflowDTO workflowDTO = new WorkflowDTO();
            workflowDTO.setUniqueCode(workflowdefinekey);
            workflowDTO.setProcessDefineVersion(workflowdefineversion);
            Map<String, Object> billWorkflowRelation = this.getbillWorkflowRelation(workflowDTO, defineCode);
            String taskDefinekey = String.valueOf(param.get("TASKDEFINEKEY"));
            this.checkEditableTable(billModel, taskDefinekey, billWorkflowRelation);
            this.checkEditableFields(billModel, taskDefinekey, billWorkflowRelation);
        }
        return true;
    }

    protected Map<String, Object> getbillWorkflowRelation(WorkflowDTO workflowDTO, String definecode) {
        WorkflowBusinessDTO business = new WorkflowBusinessDTO();
        business.setBusinesscode(definecode);
        business.setWorkflowdefinekey(workflowDTO.getUniqueCode());
        business.setWorkflowdefineversion(workflowDTO.getProcessDefineVersion());
        business.setStopflag(Integer.valueOf(-1));
        business.setShowTitle(false);
        business.setTraceId(Utils.getTraceId());
        R r = this.workflowServerClient.getBusinessBoundedWorkflow(business);
        if (0 != r.getCode()) {
            throw new BillException(r.getMsg());
        }
        Map mapdata = (Map)r.get((Object)"data");
        List ja = (List)mapdata.get("workflows");
        Map billWorkflowRelation = (Map)ja.get(0);
        return billWorkflowRelation;
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
}


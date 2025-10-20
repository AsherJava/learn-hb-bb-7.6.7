/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.biz.impl.data.DataEventImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.action.ActionReturnUtil
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.data.DataTargetType
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.impl.DataTargetImpl
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.FormulaRulerItem
 *  com.jiuqi.va.biz.ruler.intf.CheckException
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.ruler.intf.Formula
 *  com.jiuqi.va.biz.ruler.intf.RulerConsts
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.biz.view.impl.ViewDefineImpl
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.feign.client.BillCodeClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.ruler.CheckAttachmentRequired;
import com.jiuqi.va.bill.ruler.CheckTableRequired;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.data.DataEventImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.action.ActionReturnUtil;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTargetType;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.impl.DataTargetImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.intf.CheckException;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.feign.client.BillCodeClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NextStepAction
extends BillActionBase {
    public String getName() {
        return "bill-next-step";
    }

    public String getTitle() {
        return "\u4e0b\u4e00\u6b65";
    }

    public boolean isInner() {
        return true;
    }

    public boolean before(Model model, ActionRequest request, ActionResponse response) {
        BillModelImpl billModel = (BillModelImpl)model;
        String curView = String.valueOf(request.getParams().get("schemeCode"));
        String nextView = String.valueOf(request.getParams().get("nextView"));
        billModel.getContext().setContextValue("curView", curView);
        billModel.getContext().setContextValue("nextView", nextView);
        SaveAction.updateEmptyRow(billModel);
        billModel.getData().getTables().stream().forEach(DataTableImpl::deleteUnsetRows);
        Object finalObj = request.getParams().get("final");
        if (finalObj != null && ((Boolean)finalObj).booleanValue()) {
            billModel.getContext().setContextValue("allViews", request.getParams().get("schemeCodes"));
            ViewDefineImpl viewDefine = (ViewDefineImpl)billModel.getDefine().getPlugins().find(ViewDefineImpl.class);
            Map schemeFields = viewDefine.getSchemeFields();
            ArrayList<CheckResult> checkMessages = new ArrayList<CheckResult>();
            HashMap<String, Set<String>> tableFields = new HashMap<String, Set<String>>();
            List schemeCodes = (List)request.getParams().get("schemeCodes");
            for (String schemeCode : schemeCodes) {
                Map tmpTableFields = (Map)schemeFields.get(schemeCode);
                if (tmpTableFields == null || tmpTableFields.isEmpty()) continue;
                for (String tableName : tmpTableFields.keySet()) {
                    Set tmpFields = (Set)tmpTableFields.get(tableName);
                    Set fields = (Set)tableFields.get(tableName);
                    if (fields != null) {
                        fields.addAll(tmpFields);
                        continue;
                    }
                    tableFields.put(tableName, new HashSet(tmpFields));
                }
            }
            this.check(tableFields, billModel, checkMessages);
            List temp = billModel.getRuler().getRulerExecutor().beforeStepAction(Stream.of(FormulaType.WARN).collect(Collectors.toList()), true);
            if (temp != null && !temp.isEmpty()) {
                checkMessages.addAll(temp);
            }
            if (!checkMessages.isEmpty()) {
                SaveAction.handleErrorMsg(billModel, checkMessages);
            }
            return true;
        }
        ArrayList<CheckResult> checkMessages = new ArrayList<CheckResult>();
        ViewDefineImpl viewDefine = (ViewDefineImpl)billModel.getDefine().getPlugins().find(ViewDefineImpl.class);
        Map schemeFields = viewDefine.getSchemeFields();
        this.commonCheck(request.getParams(), schemeFields, billModel, checkMessages);
        if (!checkMessages.isEmpty()) {
            throw new BillException(null, checkMessages);
        }
        return super.before(model, request, response);
    }

    @Override
    public Object executeReturn(BillModel model, Map<String, Object> params) {
        BillModelImpl billModel = (BillModelImpl)model;
        SaveAction.updateEmptyRow(billModel);
        billModel.getData().getTables().stream().forEach(DataTableImpl::deleteUnsetRows);
        Object finalObj = params.get("final");
        ViewDefineImpl viewDefine = (ViewDefineImpl)billModel.getDefine().getPlugins().find(ViewDefineImpl.class);
        Map schemeFields = viewDefine.getSchemeFields();
        ArrayList<CheckResult> checkMessages = new ArrayList<CheckResult>();
        if (finalObj != null && ((Boolean)finalObj).booleanValue()) {
            List tmpCkeckMsg;
            HashMap<String, Set<String>> tableFields = new HashMap<String, Set<String>>();
            List schemeCodes = (List)params.get("schemeCodes");
            for (String schemeCode : schemeCodes) {
                Map tmpTableFields = (Map)schemeFields.get(schemeCode);
                if (tmpTableFields == null || tmpTableFields.isEmpty()) continue;
                for (String tableName : tmpTableFields.keySet()) {
                    Set tmpFields = (Set)tmpTableFields.get(tableName);
                    Set fields = (Set)tableFields.get(tableName);
                    if (fields != null) {
                        fields.addAll(tmpFields);
                        continue;
                    }
                    tableFields.put(tableName, new HashSet(tmpFields));
                }
            }
            this.check(tableFields, billModel, checkMessages);
            Object confirms = params.get("confirms");
            if (confirms != null && ((List)confirms).contains(this.getName())) {
                LogUtil.add((String)"\u5355\u636e", (String)"\u5411\u5bfc\u5236\u5355\u4fdd\u5b58", (String)billModel.getDefine().getName(), (String)billModel.getMaster().getString("BILLCODE"), null);
                tmpCkeckMsg = billModel.getRuler().getRulerExecutor().beforeStepAction(Stream.of(FormulaType.WARN).collect(Collectors.toList()), true);
                checkMessages.addAll(tmpCkeckMsg);
                if (!checkMessages.isEmpty()) {
                    SaveAction.handleErrorMsg(billModel, checkMessages);
                }
                billModel.save();
                return null;
            }
            tmpCkeckMsg = billModel.getRuler().getRulerExecutor().beforeStepAction(Stream.of(FormulaType.WARN).collect(Collectors.toList()), false);
            if (!tmpCkeckMsg.isEmpty()) {
                List unique = Utils.handleErrorMsg((String)billModel.getMasterTable().getName(), (List)tmpCkeckMsg);
                return ActionReturnUtil.returnConfirmMessage((List)unique, (String)this.getName());
            }
            LogUtil.add((String)"\u5355\u636e", (String)"\u5411\u5bfc\u5236\u5355\u4fdd\u5b58", (String)billModel.getDefine().getName(), (String)billModel.getMaster().getString("BILLCODE"), null);
            billModel.save();
            return null;
        }
        this.commonCheck(params, schemeFields, billModel, checkMessages);
        if (!checkMessages.isEmpty()) {
            throw new BillException(null, checkMessages);
        }
        return null;
    }

    private void commonCheck(Map<String, Object> params, Map<String, Map<String, Set<String>>> schemeFields, BillModelImpl billModel, List<CheckResult> checkMessages) {
        String schemeCode = String.valueOf(params.get("schemeCode"));
        Map<String, Set<String>> tableFields = schemeFields.get(schemeCode);
        if (tableFields != null) {
            this.check(tableFields, billModel, checkMessages);
            Map map = (Map)billModel.getRuler().getDefine().getObjectFormulaMap().get("table");
            for (String tableName : tableFields.keySet()) {
                CheckTableRequired.checkTableRequired(checkMessages, billModel, map, (DataTableImpl)billModel.getData().getTables().find(tableName));
            }
            CheckAttachmentRequired checkAttachment = new CheckAttachmentRequired();
            try {
                checkAttachment.execute(billModel, null);
            }
            catch (BillException exception) {
                CheckResultImpl result = new CheckResultImpl();
                result.setCheckMessage(exception.getMessage());
                result.setFormulaName("\u6821\u9a8c\u9644\u4ef6\u5fc5\u586b");
                ArrayList<DataTargetImpl> targetList = new ArrayList<DataTargetImpl>();
                DataTargetImpl target = new DataTargetImpl();
                target.setTargetType(DataTargetType.DATATABLE);
                targetList.add(target);
                result.setTargetList(targetList);
                checkMessages.add((CheckResult)result);
            }
        }
    }

    private void check(Map<String, Set<String>> tableFields, BillModelImpl billModel, List<CheckResult> checkMessages) {
        if (tableFields != null) {
            String masterTableName = billModel.getMasterTable().getName();
            for (Map.Entry<String, Set<String>> entry : tableFields.entrySet()) {
                String tableName = entry.getKey();
                Set<String> fields = entry.getValue();
                this.checkTable(checkMessages, billModel, tableName, fields, tableName.equals(masterTableName));
            }
        }
    }

    private void checkTable(List<CheckResult> checkMessages, BillModelImpl billModel, String tableName, Set<String> fields, boolean isMasterTable) {
        DataTable dataTable = (DataTable)billModel.getData().getTables().find(tableName);
        Map map = (Map)billModel.getRuler().getDefine().getObjectFormulaMap().get("field");
        dataTable.getRows().forEach((i, row) -> {
            NamedContainer dataFields = dataTable.getFields();
            fields.stream().forEach(o -> {
                DataField dataField = (DataField)dataFields.find(o);
                if (map == null) {
                    this.doRequiredCheck(billModel, tableName, (DataRow)row, dataField, null, checkMessages, isMasterTable);
                    return;
                }
                Map formulaMap = (Map)map.get(dataField.getDefine().getId());
                if (formulaMap == null) {
                    this.doRequiredCheck(billModel, tableName, (DataRow)row, dataField, null, checkMessages, isMasterTable);
                    return;
                }
                this.doRequiredCheck(billModel, tableName, (DataRow)row, dataField, formulaMap, checkMessages, isMasterTable);
                this.doInputCheck(checkMessages, billModel, dataTable, (DataRow)row, dataField, formulaMap);
            });
        });
    }

    private void doRequiredCheck(BillModelImpl billModel, String tableName, DataRow dataRow, DataField dataField, Map<String, List<Formula>> formulaMap, List<CheckResult> checkMessages, boolean isMasterTable) {
        Object value;
        CheckResultImpl result;
        if ("BILLCODE".equals(dataField.getName())) {
            if (!isMasterTable) {
                return;
            }
            BillCodeClient billCode = (BillCodeClient)ApplicationContextRegister.getBean(BillCodeClient.class);
            BillCodeRuleDTO billCodeRule = new BillCodeRuleDTO();
            billCodeRule.setUniqueCode(billModel.getDefine().getName());
            result = billCode.getDimFormulaByUniqueCode(billCodeRule);
            if (result.get((Object)"generateopt") != null && String.valueOf(result.get((Object)"generateopt")).equals("1")) {
                return;
            }
        }
        if ((value = dataRow.getValue(dataField.getName())) != null) {
            if (value instanceof Number) {
                double abs = Math.abs(((Number)value).doubleValue());
                DataFieldDefine define = dataField.getDefine();
                if (abs >= 1.0E-6) {
                    return;
                }
                if (abs == 0.0 && (define.isDisZero() || define.getRefTableType() != 0)) {
                    return;
                }
            } else if (value instanceof String) {
                if (!((String)value).isEmpty()) {
                    return;
                }
            } else {
                return;
            }
        }
        String checkMessage = null;
        if (!dataField.getDefine().isRequired()) {
            if (formulaMap == null) {
                return;
            }
            List<Formula> formulaList = formulaMap.get(RulerConsts.FORMULA_OBJECT_PROP_FIELD_REQUIRED);
            if (formulaList == null) {
                return;
            }
            checkMessage = formulaList.get(0).getCheckMessage();
            IExpression expression = (IExpression)formulaList.get(0).getCompiledExpression();
            try {
                Map<String, DataRow> rowMap = Stream.of(dataRow).collect(Collectors.toMap(k -> dataField.getDefine().getTable().getName(), v -> v));
                FormulaUtils.adjustFormulaRows((Data)((Data)billModel.getPlugins().get(Data.class)), rowMap);
                boolean required = Boolean.TRUE.equals(Convert.cast((Object)FormulaUtils.evaluate((Model)billModel, (IExpression)expression, rowMap), Boolean.TYPE));
                if (!required) {
                    return;
                }
            }
            catch (SyntaxException e) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.checkrequired.executerequiredformulafailed") + String.format("%s.%s", dataField.getDefine().getTable().getTitle(), dataField.getDefine().getTitle()), e);
            }
        }
        result = new CheckResultImpl();
        result.setCheckMessage(!StringUtils.hasText(checkMessage) ? BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{dataField.getDefine().getTitle()}) : checkMessage);
        result.setFormulaName("\u5b57\u6bb5\u5fc5\u586b");
        ArrayList<DataTargetImpl> targetList = new ArrayList<DataTargetImpl>();
        DataTargetImpl target = new DataTargetImpl();
        target.setTargetType(DataTargetType.DATACELL);
        target.setTableName(tableName);
        target.setFieldName(dataField.getName());
        target.setRowID((UUID)Convert.cast((Object)dataRow.getId(), UUID.class));
        targetList.add(target);
        result.setTargetList(targetList);
        checkMessages.add((CheckResult)result);
    }

    private void doInputCheck(List<CheckResult> checkMessages, BillModelImpl billModel, DataTable dataTable, DataRow row, DataField dataField, Map<String, List<Formula>> formulaMap) {
        List<Formula> inputFormulas;
        List<Formula> vaild2Formulas;
        ArrayList<Formula> formulaList = new ArrayList<Formula>();
        List<Formula> vaildFormulas = formulaMap.get(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE);
        if (vaildFormulas != null) {
            formulaList.addAll(vaildFormulas);
        }
        if ((vaild2Formulas = formulaMap.get(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE2)) != null) {
            formulaList.addAll(vaild2Formulas);
        }
        if ((inputFormulas = formulaMap.get(RulerConsts.FORMULA_OBJECT_PROP_FIELD_INPUT)) != null) {
            formulaList.addAll(inputFormulas);
        }
        if (formulaList.isEmpty()) {
            return;
        }
        for (Formula formula : formulaList) {
            FormulaRulerItem formulaRulerItem = new FormulaRulerItem((FormulaImpl)formula);
            ArrayList<DataEventImpl> events = new ArrayList<DataEventImpl>();
            if (formula.getPropertyType().equals(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE2)) {
                events.add(new DataEventImpl("after-set-value", null, null, null));
            } else {
                events.add(new DataEventImpl("after-set-value", dataTable, row, dataField));
            }
            try {
                formulaRulerItem.execute((Model)billModel, events.stream());
            }
            catch (CheckException e) {
                checkMessages.addAll(e.getCheckMessages());
            }
        }
    }
}


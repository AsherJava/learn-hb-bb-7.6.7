/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.biz.impl.data.DataFieldImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.intf.ActionCategory
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.scene.impl.SceneDefineImpl
 *  com.jiuqi.va.biz.scene.impl.SceneEditField
 *  com.jiuqi.va.biz.scene.impl.SceneEditTable
 *  com.jiuqi.va.biz.scene.intf.SceneDesign
 *  com.jiuqi.va.biz.scene.intf.SceneEditProp
 *  com.jiuqi.va.biz.scene.intf.SceneItem
 *  com.jiuqi.va.biz.utils.BizBindingI18nUtil
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.trans.domain.VaTransMessageDTO
 *  com.jiuqi.va.trans.service.VaTransMessageService
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.SuperEditCondition;
import com.jiuqi.va.bill.utils.VerifyUtils;
import com.jiuqi.va.biz.impl.data.DataFieldImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.intf.ActionCategory;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.scene.impl.SceneDefineImpl;
import com.jiuqi.va.biz.scene.impl.SceneEditField;
import com.jiuqi.va.biz.scene.impl.SceneEditTable;
import com.jiuqi.va.biz.scene.intf.SceneDesign;
import com.jiuqi.va.biz.scene.intf.SceneEditProp;
import com.jiuqi.va.biz.scene.intf.SceneItem;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.trans.domain.VaTransMessageDTO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
@Conditional(value={SuperEditCondition.class})
public class SuperEditAction
extends BillActionBase {
    private static final Logger logger = LoggerFactory.getLogger(SuperEditAction.class);
    @Autowired
    private VaTransMessageService vaTransMessageService;

    public String getName() {
        return "bill-super-edit";
    }

    public String getTitle() {
        return "\u8d85\u7ea7\u4fee\u6539";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_xiugai";
    }

    public String getActionPriority() {
        return "999";
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Object executeReturn(BillModel model, Map<String, Object> params) {
        BillModelImpl billModel = (BillModelImpl)model;
        if (params.get("skipCheck") != null) {
            this.handleEdit(billModel);
            return null;
        }
        Object sceneCode = params.get("scene");
        if (ObjectUtils.isEmpty(sceneCode)) {
            this.handleEdit(billModel);
            return null;
        }
        SceneDefineImpl sceneDefine = (SceneDefineImpl)billModel.getDefine().getPlugins().find(SceneDefineImpl.class);
        List scenes = sceneDefine.getScenes();
        Optional<SceneItem> optional = scenes.stream().filter(o -> Objects.equals(sceneCode, o.getName())).findFirst();
        if (!optional.isPresent()) {
            this.handleEdit(billModel);
            return null;
        }
        SceneItem sceneItem = optional.get();
        SceneDesign design = sceneItem.getDesign();
        if (design == null) {
            this.handleEdit(billModel);
            return null;
        }
        List editProps = design.getEditProps();
        if (CollectionUtils.isEmpty(editProps)) {
            this.handleEdit(billModel);
            return null;
        }
        String masterTableName = billModel.getMasterTable().getName();
        DataTableNodeContainerImpl tables = billModel.getData().getTables();
        HashMap<String, Map> superEidtField = new HashMap<String, Map>();
        HashSet<String> superEidtTable = new HashSet<String>();
        for (SceneEditProp sceneEditProp : editProps) {
            boolean evaluate;
            String tableName;
            String propTable;
            if (!sceneEditProp.isEnable() || !StringUtils.hasText(propTable = sceneEditProp.getPropTable())) continue;
            IExpression compileExpression = sceneEditProp.getCompileExpression();
            String type = sceneEditProp.getType();
            if (type.equals("field")) {
                DataTableImpl dataTable;
                SceneEditField sceneEditField = (SceneEditField)sceneEditProp;
                List fields = sceneEditField.getFields();
                HashMap<String, Set> tableFields = new HashMap<String, Set>();
                for (Map map : fields) {
                    DataFieldImpl dataField;
                    tableName = String.valueOf(map.get("tableName"));
                    String fieldName = String.valueOf(map.get("name"));
                    dataTable = (DataTableImpl)tables.find(tableName);
                    if (dataTable == null || (dataField = (DataFieldImpl)dataTable.getFields().find(fieldName)) == null) continue;
                    tableFields.computeIfAbsent(tableName, key -> new HashSet()).add(fieldName);
                }
                if (propTable.equals(masterTableName)) {
                    void evaluate2;
                    try {
                        Map<String, DataRow> map = Stream.of(billModel.getMaster()).collect(Collectors.toMap(o -> masterTableName, o -> o));
                        FormulaUtils.adjustFormulaRows((Data)billModel.getData(), map);
                        boolean evaluate22 = FormulaUtils.check((Model)model, (IExpression)compileExpression, map);
                    }
                    catch (Exception exception) {
                        logger.error(exception.getMessage(), exception);
                        throw new RuntimeException(BizBindingI18nUtil.getMessage((String)"va.bizbinding.formulautils.executeformulaexception", (Object[])new Object[]{sceneEditProp.getExpression()}), exception);
                    }
                    if (evaluate2 == false) continue;
                    for (Map.Entry tableField : tableFields.entrySet()) {
                        String tableName2 = (String)tableField.getKey();
                        Set fds = (Set)tableField.getValue();
                        DataTableImpl dataTable2 = (DataTableImpl)tables.find(tableName2);
                        ListContainer rows = dataTable2.getRows();
                        Map superEditFieldRows = superEidtField.computeIfAbsent(tableName2, key -> new HashMap());
                        rows.forEach((i, row) -> this.setEidtFields(fds, superEditFieldRows, (DataRow)row));
                    }
                    continue;
                }
                if (tableFields.keySet().size() > 1) {
                    throw new BillException(String.format("\u89c4\u5219\u573a\u666f\u3010%s\u3011\u914d\u7f6e\u9519\u8bef\uff1a\u53ef\u7f16\u8f91\u5b57\u6bb5\u8d85\u51fa\u516c\u5f0f\u8303\u56f4", sceneItem.getName()));
                }
                for (Map.Entry entry : tableFields.entrySet()) {
                    tableName = (String)entry.getKey();
                    Set fds = (Set)entry.getValue();
                    dataTable = (DataTableImpl)tables.find(tableName);
                    ListContainer rows = dataTable.getRows();
                    Map superEditFieldRows = superEidtField.computeIfAbsent(tableName, key -> new HashMap());
                    rows.forEach((i, row) -> {
                        boolean evaluate;
                        try {
                            Map<String, DataRow> rowMap = Stream.of(row).collect(Collectors.toMap(o -> tableName, o -> o));
                            FormulaUtils.adjustFormulaRows((Data)billModel.getData(), rowMap);
                            evaluate = FormulaUtils.check((Model)model, (IExpression)compileExpression, rowMap);
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            throw new RuntimeException(BizBindingI18nUtil.getMessage((String)"va.bizbinding.formulautils.executeformulaexception", (Object[])new Object[]{sceneEditProp.getExpression()}), e);
                        }
                        if (evaluate) {
                            this.setEidtFields(fds, superEditFieldRows, (DataRow)row);
                        }
                    });
                }
                continue;
            }
            if (!type.equals("table")) continue;
            SceneEditTable sceneEditTable = (SceneEditTable)sceneEditProp;
            List editTables = sceneEditTable.getTables();
            if (!masterTableName.equals(propTable)) {
                throw new BillException(String.format("\u89c4\u5219\u573a\u666f\u3010%s\u3011\u914d\u7f6e\u9519\u8bef\uff1a\u53ef\u7f16\u8f91\u5b50\u8868\u516c\u5f0f\u914d\u7f6e\u9519\u8bef\uff0c\u516c\u5f0f\u7ed3\u679c\u53ea\u80fd\u6709\u4e00\u4e2a", sceneItem.getName()));
            }
            try {
                Map<String, DataRow> rowMap = Stream.of(billModel.getMaster()).collect(Collectors.toMap(o -> masterTableName, o -> o));
                FormulaUtils.adjustFormulaRows((Data)billModel.getData(), rowMap);
                evaluate = FormulaUtils.check((Model)model, (IExpression)compileExpression, rowMap);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(BizBindingI18nUtil.getMessage((String)"va.bizbinding.formulautils.executeformulaexception", (Object[])new Object[]{sceneEditProp.getExpression()}), e);
            }
            if (!evaluate) continue;
            for (Map map : editTables) {
                tableName = String.valueOf(map.get("name"));
                DataTableImpl dataTable = (DataTableImpl)tables.find(tableName);
                if (dataTable == null) continue;
                superEidtTable.add(tableName);
            }
        }
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("superEditField", superEidtField);
        result.put("superEditTable", superEidtTable);
        result.put("superEdit", true);
        if (superEidtTable.size() > 0) {
            HashMap superEditRows = new HashMap();
            for (String table : superEidtTable) {
                List rowids = ((DataTableImpl)tables.find(table)).getRows().stream().map(DataRowImpl::getId).collect(Collectors.toList());
                superEditRows.put(table, rowids);
            }
            result.put("superEditRows", superEditRows);
        }
        this.handleEdit(billModel);
        return result;
    }

    private void setEidtFields(Set<String> fds, Map<String, Set<String>> fieldRowsMap, DataRow row) {
        for (String fd : fds) {
            Set integers = fieldRowsMap.computeIfAbsent(fd, key -> new HashSet());
            integers.add(String.valueOf(row.getId()));
        }
    }

    private void handleEdit(BillModelImpl billModel) {
        DataRow master = billModel.getMaster();
        LogUtil.add((String)"\u5355\u636e", (String)"\u8d85\u7ea7\u4fee\u6539", (String)billModel.getDefine().getName(), (String)master.getString("BILLCODE"), null);
        String billCode = (String)master.getValue("BILLCODE", String.class);
        VaTransMessageDTO vaTransMessageDTO = new VaTransMessageDTO();
        vaTransMessageDTO.setBizcode(billCode);
        vaTransMessageDTO.setStatus(Integer.valueOf(0));
        vaTransMessageDTO.setFilterRoot(true);
        List transMsgs = this.vaTransMessageService.listTransMessage(vaTransMessageDTO);
        if (!CollectionUtils.isEmpty(transMsgs)) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.workflowaction.processing"));
        }
        this.checkAdmin(billModel.getContext());
        VerifyUtils.verifyBill(billModel, 2);
        billModel.getData().edit();
    }

    private void checkAdmin(BillContext context) {
        UserLoginDTO user;
        String triggerOrigin = context.getTriggerOrigin();
        if (StringUtils.hasText(triggerOrigin) && (user = ShiroUtil.getUser()) != null && "super".equalsIgnoreCase(user.getMgrFlag())) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.bill.core.admin.no.permission"));
        }
    }

    public ActionCategory getActionCategory() {
        return ActionCategory.EDIT;
    }
}


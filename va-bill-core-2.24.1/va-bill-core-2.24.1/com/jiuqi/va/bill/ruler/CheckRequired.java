/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.data.DataTargetType
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.impl.DataTargetImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckItem
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.ruler.intf.Formula
 *  com.jiuqi.va.biz.ruler.intf.RulerConsts
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 *  com.jiuqi.va.biz.utils.FormulaUtils
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTargetType;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.impl.DataTargetImpl;
import com.jiuqi.va.biz.ruler.intf.CheckItem;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.biz.utils.FormulaUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.StringUtils;

public class CheckRequired
implements CheckItem {
    private Set<String> triggerTypes;

    public String getName() {
        return "BillCheckRequired";
    }

    public String getTitle() {
        return "\u6821\u9a8c\u5b57\u6bb5\u5fc5\u586b";
    }

    public String getRulerType() {
        return "Fixed";
    }

    public Set<String> getTriggerTypes() {
        if (this.triggerTypes == null) {
            this.triggerTypes = Stream.of("before-save").collect(Collectors.toSet());
        }
        return this.triggerTypes;
    }

    public void execute(Model model, Stream<TriggerEvent> events, List<CheckResult> checkMessages) {
        BillModelImpl billModel = (BillModelImpl)model;
        String masterTableName = billModel.getMasterTable().getName();
        billModel.getData().getTables().stream().forEach(o -> this.checkTable(checkMessages, billModel, (DataTable)o, o.getName().equals(masterTableName)));
    }

    private void checkTable(List<CheckResult> checkMessages, BillModelImpl billModel, DataTable table, boolean isMasterTable) {
        Map map = (Map)billModel.getRuler().getDefine().getObjectFormulaMap().get("field");
        table.getRows().forEach((i, row) -> table.getFields().stream().forEach(o -> {
            if (isMasterTable && "BILLCODE".equals(o.getName())) {
                return;
            }
            Object value = row.getValue(o.getName());
            if (value != null) {
                if (value instanceof Number) {
                    double abs = Math.abs(((Number)value).doubleValue());
                    DataFieldDefine define = o.getDefine();
                    if (abs >= 1.0E-6) {
                        return;
                    }
                    if (abs == 0.0 && (define.isDisZero() || define.getRefTableType() != 0)) {
                        return;
                    }
                } else if (value instanceof String) {
                    if (((String)value).length() > 0) {
                        return;
                    }
                } else {
                    return;
                }
            }
            String checkMessage = null;
            if (!o.getDefine().isRequired()) {
                if (map == null) {
                    return;
                }
                Map map2 = (Map)map.get(o.getDefine().getId());
                if (map2 == null) {
                    return;
                }
                List formulaList = (List)map2.get(RulerConsts.FORMULA_OBJECT_PROP_FIELD_REQUIRED);
                if (formulaList == null) {
                    return;
                }
                checkMessage = ((Formula)formulaList.get(0)).getCheckMessage();
                IExpression expression = (IExpression)((Formula)formulaList.get(0)).getCompiledExpression();
                try {
                    Map<String, DataRow> rowMap = Stream.of(row).collect(Collectors.toMap(k -> o.getDefine().getTable().getName(), v -> v));
                    FormulaUtils.adjustFormulaRows((Data)((Data)billModel.getPlugins().get(Data.class)), rowMap);
                    boolean required = (Boolean)Convert.cast((Object)FormulaUtils.evaluate((Model)billModel, (IExpression)expression, rowMap), Boolean.TYPE);
                    if (!required) {
                        return;
                    }
                }
                catch (SyntaxException e) {
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.checkrequired.executerequiredformulafailed") + String.format("%s.%s", o.getDefine().getTable().getTitle(), o.getDefine().getTitle()), e);
                }
            }
            CheckResultImpl result = new CheckResultImpl();
            result.setCheckMessage(!StringUtils.hasText(checkMessage) ? BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{o.getDefine().getTitle()}) : checkMessage);
            result.setFormulaName("\u5b57\u6bb5\u5fc5\u586b");
            ArrayList<DataTargetImpl> targetList = new ArrayList<DataTargetImpl>();
            DataTargetImpl target = new DataTargetImpl();
            target.setTargetType(DataTargetType.DATACELL);
            target.setTableName(table.getName());
            target.setFieldName(o.getName());
            target.setRowID((UUID)Convert.cast((Object)row.getId(), UUID.class));
            targetList.add(target);
            result.setTargetList(targetList);
            checkMessages.add((CheckResult)result);
        }));
    }
}


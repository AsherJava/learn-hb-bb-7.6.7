/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTargetType
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.impl.DataTargetImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckItem
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.ruler.intf.Formula
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 *  com.jiuqi.va.biz.utils.FormulaUtils
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTargetType;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.impl.DataTargetImpl;
import com.jiuqi.va.biz.ruler.intf.CheckItem;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.Formula;
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

public class CheckTableRequired
implements CheckItem {
    private Set<String> triggerTypes;

    public String getName() {
        return "BillCheckTableRequired";
    }

    public String getTitle() {
        return "\u6821\u9a8c\u8868\u5fc5\u586b";
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
        Map map = (Map)billModel.getRuler().getDefine().getObjectFormulaMap().get("table");
        billModel.getData().getTables().stream().forEach(o -> CheckTableRequired.checkTableRequired(checkMessages, billModel, map, o));
    }

    public static void checkTableRequired(List<CheckResult> checkMessages, BillModelImpl billModel, Map<UUID, Map<String, List<Formula>>> map, DataTableImpl o) {
        if (o == billModel.getMasterTable()) {
            return;
        }
        if (o.getRows().size() != 0) {
            return;
        }
        String checkMessage = null;
        if (!o.getDefine().isRequired()) {
            if (map == null) {
                return;
            }
            Map<String, List<Formula>> map2 = map.get(o.getId());
            if (map2 == null) {
                return;
            }
            List<Formula> formulaList = map2.get("required");
            if (formulaList == null) {
                return;
            }
            IExpression expression = (IExpression)formulaList.get(0).getCompiledExpression();
            checkMessage = formulaList.get(0).getCheckMessage();
            try {
                Map<String, DataRow> rowMap = Stream.of(billModel.getMaster()).collect(Collectors.toMap(k -> billModel.getMasterTable().getName(), v -> v));
                FormulaUtils.adjustFormulaRows((Data)((Data)billModel.getPlugins().get(Data.class)), rowMap);
                boolean required = (Boolean)Convert.cast((Object)FormulaUtils.evaluate((Model)billModel, (IExpression)expression, rowMap), Boolean.TYPE);
                if (!required) {
                    return;
                }
            }
            catch (SyntaxException e) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.checktablerequired.executerequiredformulafailed") + o.getTitle(), e);
            }
        }
        CheckResultImpl result = new CheckResultImpl();
        result.setCheckMessage(!StringUtils.hasText(checkMessage) ? BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{o.getDefine().getTitle()}) : checkMessage);
        result.setFormulaName("\u8868\u5fc5\u586b");
        ArrayList<DataTargetImpl> targetList = new ArrayList<DataTargetImpl>();
        DataTargetImpl target = new DataTargetImpl();
        target.setTargetType(DataTargetType.DATATABLE);
        target.setTableName(o.getName());
        target.setFieldName(o.getName());
        target.setRowIndex(0);
        targetList.add(target);
        result.setTargetList(targetList);
        checkMessages.add((CheckResult)result);
    }
}


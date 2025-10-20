/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.impl.data.DataEventImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.ruler.CountDataNode
 *  com.jiuqi.va.biz.ruler.ModelNode
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.va.bill.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.data.DataEventImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.CountDataNode;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class ExecuteRulerFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public ExecuteRulerFunction() {
        this.parameters().add(new Parameter("rulerName", 6, "\u56fa\u5316\u89c4\u5219\u540d\u79f0", false));
        this.parameters().add(new Parameter("triggerField", 0, "\u89e6\u53d1\u5b57\u6bb5", true));
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String name() {
        return "ExecuteRuler";
    }

    public String title() {
        return "\u6267\u884c\u56fa\u5316\u89c4\u5219";
    }

    public String addDescribe() {
        return "\u6267\u884c\u56fa\u5316\u89c4\u5219";
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 0;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node0 = parameters.get(0);
        Object node0Data = node0.evaluate(context);
        if (ObjectUtils.isEmpty(node0Data)) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.billcore.executerulerfunction.rulenamerequired"));
        }
        String rulerName = (String)node0Data;
        ModelDataContext modelDataContext = (ModelDataContext)context;
        BillModelImpl model = (BillModelImpl)modelDataContext.model;
        Optional<RulerItem> optional = model.getDefine().getRuler().getItemList().stream().filter(o -> o.getName().equals(rulerName)).findFirst();
        Map params = modelDataContext.getParams();
        if (params == null || params.size() == 0) {
            return null;
        }
        if (optional.isPresent()) {
            LinkedHashSet<DataEventImpl> eventSet = new LinkedHashSet<DataEventImpl>();
            RulerItem rulerItem = optional.get();
            if (parameters.size() > 1) {
                for (int i = 1; i < parameters.size(); ++i) {
                    IASTNode node = parameters.get(i);
                    if (node instanceof CountDataNode) {
                        DataEventImpl dataEventImpl = new DataEventImpl(rulerItem.getTriggerType(), model.getMasterTable(), model.getMaster(), null);
                        rulerItem.accept((ModelDefine)model.getDefine(), (TriggerEvent)dataEventImpl);
                        eventSet.add(dataEventImpl);
                        continue;
                    }
                    ModelNode modelNode = (ModelNode)parameters.get(i);
                    for (Map.Entry entry : params.entrySet()) {
                        DataTable dataTable = model.getTable((String)entry.getKey());
                        DataRowImpl dataRow = (DataRowImpl)entry.getValue();
                        DataField field = (DataField)dataTable.getFields().find(modelNode.getFieldName());
                        DataEventImpl dataEventImpl = new DataEventImpl(rulerItem.getTriggerType(), dataTable, (DataRow)dataRow, field);
                        rulerItem.accept((ModelDefine)model.getDefine(), (TriggerEvent)dataEventImpl);
                        eventSet.add(dataEventImpl);
                    }
                }
            } else {
                DataEventImpl dataEventImpl = new DataEventImpl(rulerItem.getTriggerType(), model.getMasterTable(), model.getMaster(), null);
                rulerItem.accept((ModelDefine)model.getDefine(), (TriggerEvent)dataEventImpl);
                eventSet.add(dataEventImpl);
            }
            return rulerItem.executeReturn((Model)model, eventSet.stream());
        }
        throw new RuntimeException(BillCoreI18nUtil.getMessage("va.billcore.executerulerfunction.nofindfixedrule", new Object[]{rulerName}));
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = FunctionUtils.handleFormula((IFunction)this);
        formulaDescription.setDescription(this.addDescribe());
        return formulaDescription;
    }
}


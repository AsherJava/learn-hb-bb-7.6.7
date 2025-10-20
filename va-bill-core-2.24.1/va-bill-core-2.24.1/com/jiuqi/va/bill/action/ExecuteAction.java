/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.feign.util.LogUtil
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ExecuteAction
extends BillActionBase {
    public String getName() {
        return "bill-query-grid-execute";
    }

    public String getTitle() {
        return "\u81ea\u5b9a\u4e49\u67e5\u8be2\u6267\u884c\u516c\u5f0f";
    }

    public String getIcon() {
        return null;
    }

    public boolean isInner() {
        return true;
    }

    public String getActionPriority() {
        return "026";
    }

    @Override
    public Object executeReturn(BillModel model, Map<String, Object> params) {
        LogUtil.add((String)"\u5355\u636e", (String)"\u81ea\u5b9a\u4e49\u67e5\u8be2\u6267\u884c\u516c\u5f0f", (String)model.getDefine().getName(), (String)model.getMaster().getString("BILLCODE"), null);
        ArrayList<CheckResult> results = new ArrayList<CheckResult>();
        CheckResultImpl result = new CheckResultImpl();
        Object formulaResult = null;
        String expression = (String)params.get("expression");
        String formulaType = "EVALUATE";
        if (expression != null && !expression.isEmpty()) {
            try {
                FormulaImpl formulaImpl = new FormulaImpl();
                formulaImpl.setExpression(expression);
                formulaImpl.setFormulaType(FormulaType.valueOf((String)formulaType));
                formulaResult = FormulaUtils.executeFormula((FormulaImpl)formulaImpl, (Model)model);
            }
            catch (Exception e) {
                result.setCheckMessage(BillCoreI18nUtil.getMessage("va.billcore.executeaction.formulaexecutefailed"));
                results.add((CheckResult)result);
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.hint"), results);
            }
        }
        return formulaResult;
    }
}


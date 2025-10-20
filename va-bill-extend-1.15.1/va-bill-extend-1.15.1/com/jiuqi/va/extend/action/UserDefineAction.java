/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.action.ActionReturnUtil
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.ruler.ModelFormulaHandle
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.FormulaRulerItem
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerImpl
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.extend.action;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.action.ActionReturnUtil;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.extend.utils.BillExtend18nUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class UserDefineAction
extends BillActionBase {
    public String getName() {
        return "bill-userdefine";
    }

    public String getTitle() {
        return "\u81ea\u5b9a\u4e49\u6309\u94ae";
    }

    public String getIcon() {
        return "@va/va-iconfont icona-16_GJ_A_VA_zidingyianniu";
    }

    /*
     * WARNING - void declaration
     */
    @Transactional
    public Object executeReturn(BillModel model, Map<String, Object> params) {
        void var11_16;
        Object ctlid = params.get("objectId");
        Object successPrompt = params.get("successPrompt");
        Object editEnabled = params.get("editEnabled");
        if (null == editEnabled) {
            return ActionReturnUtil.returnModalMessage((String)BillExtend18nUtil.getMessage("va.billextend.not.config.action.param"), (String)"error");
        }
        List<Object> formulas = new ArrayList();
        ArrayList<Object> ematchfms = new ArrayList<FormulaImpl>();
        ArrayList<Object> checkfms = new ArrayList<FormulaImpl>();
        if (ObjectUtils.isEmpty(ctlid)) {
            List list = (List)params.get("formulaData");
            for (Map map : list) {
                if (ObjectUtils.isEmpty(map.get("expression"))) continue;
                String formulaType = map.get("formulaType").toString();
                FormulaImpl formula = new FormulaImpl();
                formula.setExpression(map.get("expression").toString());
                formula.setFormulaType(FormulaType.valueOf((String)formulaType));
                formula.setName(map.get("name").toString());
                if (formulaType.equals(FormulaType.CHECK.name()) || formulaType.equals(FormulaType.WARN.name())) {
                    checkfms.add(formula);
                }
                if (!formulaType.equals(FormulaType.EXECUTE.name()) && !formulaType.equals(FormulaType.EVALUATE.name())) continue;
                ematchfms.add(formula);
            }
        } else {
            RulerDefineImpl rdi = (RulerDefineImpl)model.getDefine().getPlugins().get(RulerDefineImpl.class);
            formulas = rdi.getFormulas().stream().collect(Collectors.toList());
            for (FormulaImpl formulaImpl : formulas) {
                if (!ObjectUtils.isEmpty(ctlid) && (!formulaImpl.getObjectId().equals(UUID.fromString(ctlid.toString())) || !formulaImpl.getPropertyType().equals("action"))) continue;
                if (formulaImpl.getFormulaType().equals((Object)FormulaType.EXECUTE) || formulaImpl.getFormulaType().equals((Object)FormulaType.EVALUATE)) {
                    if (null == ematchfms) {
                        ematchfms = new ArrayList();
                    }
                    ematchfms.add(formulaImpl);
                }
                if (!formulaImpl.getFormulaType().equals((Object)FormulaType.CHECK) && !formulaImpl.getFormulaType().equals((Object)FormulaType.WARN)) continue;
                if (null == checkfms) {
                    checkfms = new ArrayList();
                }
                checkfms.add(formulaImpl);
            }
        }
        String message = successPrompt != null ? successPrompt.toString() : null;
        String msg = StringUtils.hasText(message) ? message : "\u6267\u884c\u6210\u529f";
        Object var11_14 = null;
        if (!checkfms.isEmpty()) {
            this.batchCompiledExpression(checkfms, (Model)model);
            this.executeFormula(checkfms, (Model)model, msg);
        }
        if (!ematchfms.isEmpty()) {
            this.batchCompiledExpression(ematchfms, (Model)model);
            Data data = model.getData();
            boolean editEnable = (Boolean)editEnabled;
            if (!editEnable) {
                data.edit();
            }
            Object object = this.executeFormula(ematchfms, (Model)model, msg);
            if (!editEnable) {
                data.save();
            }
        }
        return var11_16;
    }

    private void batchCompiledExpression(List<FormulaImpl> matchfms, Model model) {
        matchfms.forEach(formula -> {
            try {
                if (formula.getCompiledExpression() == null) {
                    IExpression expression = ModelFormulaHandle.getInstance().parse(new ModelDataContext(model), formula.getExpression(), formula.getFormulaType());
                    formula.setCompiledExpression(expression);
                }
            }
            catch (ParseException e) {
                throw new RuntimeException(String.format("\u89e3\u6790\u516c\u5f0f%s\u51fa\u73b0\u5f02\u5e38", formula.getExpression()));
            }
        });
    }

    private final Object executeFormula(List<FormulaImpl> matchfms, Model model, String successPrompt) {
        ArrayList results = null;
        for (FormulaImpl formula : matchfms) {
            List contexts = new FormulaRulerItem(formula).getAllContext(model, Stream.empty());
            if (contexts.size() == 0) continue;
            RulerImpl impl = new RulerImpl();
            for (ModelDataContext context : contexts) {
                List result = impl.execute(context, formula);
                if (result.isEmpty()) continue;
                if (results == null) {
                    results = new ArrayList();
                }
                results.addAll(result);
            }
        }
        if (results != null && !results.isEmpty()) {
            throw new BillException(BillExtend18nUtil.getMessage("va.billextend.tooltip"), (List)results);
        }
        return ActionReturnUtil.returnModalMessage((String)successPrompt, (String)"success");
    }

    public String getActionPriority() {
        return "022";
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.biz.view.impl.ViewImpl
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
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.view.impl.ViewImpl;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class CheckActionExitFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;
    private Map<String, UUID> actionObjectIdMap = new HashMap<String, UUID>();

    public CheckActionExitFunction() {
        this.parameters().add(new Parameter("id", 6, "ID", false));
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String name() {
        return "CheckActionExit";
    }

    public String title() {
        return "\u5224\u65ad\u5355\u636e\u52a8\u4f5c\u662f\u5426\u5b58\u5728";
    }

    public String addDescribe() {
        return "\u5224\u65ad\u5355\u636e\u52a8\u4f5c\u662f\u5426\u5b58\u5728";
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 0;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Map schemeInfo;
        IASTNode node0 = parameters.get(0);
        Object objectID = node0.evaluate(context);
        ModelDataContext modelDataContext = (ModelDataContext)context;
        BillModelImpl model = (BillModelImpl)modelDataContext.model;
        if (model.getDefine().getMetaInfo() == null) {
            return true;
        }
        BillContext billContext = model.getContext();
        String schemeCode = billContext.getSchemeCode();
        ViewImpl ViewImpl2 = (ViewImpl)model.getPlugins().find(ViewImpl.class);
        Map props = StringUtils.hasText(schemeCode) ? ((schemeInfo = (Map)ViewImpl2.getDefine().getSchemes().stream().filter(o -> schemeCode.equals(o.get("code"))).findFirst().orElse(null)) == null ? ViewImpl2.getDefine().getTemplate().getProps() : (Map)schemeInfo.get("template")) : ViewImpl2.getDefine().getTemplate().getProps();
        if ("v-wizard".equals(props.get("type"))) {
            return true;
        }
        Map schemeActions = ViewImpl2.getDefine().getSchemeActions();
        Set actions = (Set)schemeActions.get(StringUtils.hasText(schemeCode) ? schemeCode : ViewImpl2.getDefine().getDefaultSchemeCode());
        UUID actionsID = UUID.fromString(objectID.toString());
        if (!CollectionUtils.isEmpty(actions)) {
            return actions.stream().anyMatch(o -> {
                UUID uuid = this.actionObjectIdMap.computeIfAbsent((String)o, key -> Utils.normalizeId((Object)o));
                return uuid.equals(actionsID);
            });
        }
        return true;
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = FunctionUtils.handleFormula((IFunction)this);
        formulaDescription.setDescription(this.addDescribe());
        return formulaDescription;
    }
}


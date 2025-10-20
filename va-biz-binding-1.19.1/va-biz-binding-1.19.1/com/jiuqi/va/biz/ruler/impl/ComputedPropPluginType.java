/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginTypeBase;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.impl.ComputedPropDefineImpl;
import com.jiuqi.va.biz.ruler.impl.ComputedPropImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.FormulaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public abstract class ComputedPropPluginType
extends PluginTypeBase {
    private static final Logger log = LoggerFactory.getLogger(ComputedPropPluginType.class);
    public static final String NAME = "computedProp";
    public static final String TITLE = "\u8ba1\u7b97\u5c5e\u6027";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public Class<? extends PluginDefineImpl> getPluginDefineClass() {
        return ComputedPropDefineImpl.class;
    }

    public Class<? extends ComputedPropImpl> getPluginClass() {
        return ComputedPropImpl.class;
    }

    @Override
    public void initPlugin(Plugin plugin, PluginDefine pluginDefine, Model model) {
        super.initPlugin(plugin, pluginDefine, model);
    }

    @Override
    public String[] getDependPlugins() {
        return new String[]{"data"};
    }

    @Override
    public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine) {
        super.initPluginDefine(pluginDefine, modelDefine);
        DataDefineImpl dataDefineImpl = modelDefine.getPlugins().get(DataDefineImpl.class);
        ModelFormulaHandle handle = ModelFormulaHandle.getInstance();
        ModelDataContext context = new ModelDataContext(modelDefine);
        ComputedPropDefineImpl computedPropDefine = (ComputedPropDefineImpl)pluginDefine;
        for (Formula formula : computedPropDefine.getFormulas()) {
            IExpression expression;
            FormulaImpl formula2 = (FormulaImpl)formula;
            if (!formula2.isUsed() || ObjectUtils.isEmpty(formula2.getExpression())) continue;
            try {
                expression = handle.parse(context, formula2.getExpression(), formula2.getFormulaType());
                formula2.setCompiledExpression(expression);
            }
            catch (ParseException e) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerplugintype.parseformulafailed") + formula2.getExpression(), e);
            }
            catch (Exception e) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerplugintype.fieldformulaexception") + formula2.getExpression(), e);
            }
            FormulaUtils.handleCalcProps(dataDefineImpl, context, computedPropDefine, formula2, expression, null);
        }
    }
}


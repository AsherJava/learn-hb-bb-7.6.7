/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.facade.extend.ICheckFmlProvider;
import com.jiuqi.nr.data.logic.facade.extend.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.facade.extend.param.GetCheckFmlEnv;
import com.jiuqi.nr.data.logic.internal.helper.FormulaGetter;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultCheckFmlProvider
implements ICheckFmlProvider {
    public static final String TYPE = "DefaultCheckFmlProvider";
    @Autowired
    private FormulaParseUtil formulaParseUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Value(value="${jiuqi.nr.data.logic.auto-check-bj:false}")
    private boolean autoCheckBJ;

    @Override
    public List<FmlExecInfo> getFml(GetCheckFmlEnv env) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(env.getFormSchemeKey());
        String mainDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
        ArrayList<FmlExecInfo> result = new ArrayList<FmlExecInfo>();
        FormulaGetter formulaGetter = new FormulaGetter(env.getFormulaUnitGroups());
        Map<String, List<IParsedExpression>> map = formulaGetter.toMap();
        for (Map.Entry<String, List<IParsedExpression>> entry : map.entrySet()) {
            FmlExecInfo fmlExecInfo = new FmlExecInfo();
            if ("00000000-0000-0000-0000-000000000000".equals(entry.getKey())) {
                fmlExecInfo.setDimensionValueSet(env.getDimensionValueSet());
            } else {
                DimensionValueSet dimensionValueSet = new DimensionValueSet(env.getDimensionValueSet());
                dimensionValueSet.clearValue(mainDimName);
                dimensionValueSet.setValue(mainDimName, (Object)entry.getKey());
                fmlExecInfo.setDimensionValueSet(dimensionValueSet);
            }
            List<IParsedExpression> fml = new ArrayList<IParsedExpression>((Collection)entry.getValue());
            fml = this.formulaParseUtil.filterFormula(fml, env.getAccessForms(), env.getFormulaMaps(), env.getFormulaCheckTypes(), env.isWholeBetween(), this.autoCheckBJ, env.getMode());
            fmlExecInfo.setParsedExpressions(fml);
            result.add(fmlExecInfo);
        }
        return result;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean isUse(GetCheckFmlEnv env) {
        return false;
    }
}


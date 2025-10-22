/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormulaField
 *  com.jiuqi.nr.definition.facade.FormulaParsedExp
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.internal.service.IFmlGraphAccessVerifier;
import com.jiuqi.nr.data.logic.internal.util.BeanHelper;
import com.jiuqi.nr.data.logic.internal.util.FmlUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormulaField;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DftFmlGraphAccessVerifier
implements IFmlGraphAccessVerifier {
    private static final Logger logger = LoggerFactory.getLogger(DftFmlGraphAccessVerifier.class);
    private final Set<String> sysWriteForms;
    private final DataPermissionEvaluator fieldAccessEvaluator;
    private final BeanHelper beanHelper;

    public DftFmlGraphAccessVerifier(@NotNull Set<String> sysWriteForms, @NotNull DataPermissionEvaluator dataPermissionEvaluator, @NotNull BeanHelper beanHelper) {
        this.sysWriteForms = sysWriteForms;
        this.fieldAccessEvaluator = dataPermissionEvaluator;
        this.beanHelper = beanHelper;
    }

    @Override
    public boolean access(FormulaParsedExp formulaParsedExp, DimensionCombination dimensionCombination) {
        IParsedExpression parsedExpression = formulaParsedExp.getParsedExpression();
        String formKey = parsedExpression.getFormKey();
        if (formKey == null) {
            if (!FmlUtil.needZBWriteFml(parsedExpression)) {
                return true;
            }
            for (FormulaField writeField : formulaParsedExp.getWriteFields()) {
                boolean haveAccess;
                DataField dataFieldByColumnKey = this.beanHelper.getDataSchemeService().getDataFieldByColumnKey(writeField.getFieldKey());
                if (dataFieldByColumnKey == null || (haveAccess = this.fieldAccessEvaluator.haveAccess(dimensionCombination, dataFieldByColumnKey.getKey(), AuthType.SYS_WRITEABLE))) continue;
                return false;
            }
            return true;
        }
        return this.sysWriteForms.contains(formKey);
    }
}


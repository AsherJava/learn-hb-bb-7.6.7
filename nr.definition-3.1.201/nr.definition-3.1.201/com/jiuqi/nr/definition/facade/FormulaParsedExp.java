/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.facade.FormulaField;
import java.util.Collection;

public interface FormulaParsedExp {
    public String getExpKey();

    public IParsedExpression getParsedExpression();

    public Collection<FormulaField> getReadFields();

    public Collection<FormulaField> getWriteFields();
}


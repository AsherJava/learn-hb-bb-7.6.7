/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import java.util.Collection;

public interface FormulaField {
    public String getFieldKey();

    public Collection<FormulaParsedExp> getReadParsedExps();

    public Collection<FormulaParsedExp> getWriteParsedExps();
}


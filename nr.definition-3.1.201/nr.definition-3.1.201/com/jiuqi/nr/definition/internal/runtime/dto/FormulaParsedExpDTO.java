/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.facade.FormulaField;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FormulaParsedExpDTO
implements FormulaParsedExp {
    private final IParsedExpression expression;
    private final Collection<FormulaField> readFields;
    private final Collection<FormulaField> writeFields;

    public FormulaParsedExpDTO(IParsedExpression expression) {
        this.expression = expression;
        this.readFields = new ArrayList<FormulaField>();
        this.writeFields = new ArrayList<FormulaField>();
    }

    public void addReadField(FormulaField field) {
        this.readFields.add(field);
    }

    public void addWriteField(FormulaField field) {
        this.writeFields.add(field);
    }

    @Override
    public String getExpKey() {
        return this.expression.getKey();
    }

    @Override
    public IParsedExpression getParsedExpression() {
        return this.expression;
    }

    @Override
    public Collection<FormulaField> getReadFields() {
        return Collections.unmodifiableCollection(this.readFields);
    }

    @Override
    public Collection<FormulaField> getWriteFields() {
        return Collections.unmodifiableCollection(this.writeFields);
    }
}


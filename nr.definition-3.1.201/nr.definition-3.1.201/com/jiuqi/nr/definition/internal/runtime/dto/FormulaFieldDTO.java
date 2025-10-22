/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.nr.definition.facade.FormulaField;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import com.jiuqi.nr.definition.internal.runtime.dto.FormulaParsedExpDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class FormulaFieldDTO
implements FormulaField {
    private final String fieldKey;
    protected final Collection<FormulaParsedExp> readParsedExps;
    protected final Collection<FormulaParsedExp> writeParsedExps;

    public FormulaFieldDTO(String fieldKey) {
        this.fieldKey = fieldKey;
        this.readParsedExps = new ArrayList<FormulaParsedExp>();
        this.writeParsedExps = new ArrayList<FormulaParsedExp>();
    }

    public void addReadParsedExp(FormulaParsedExpDTO exp) {
        this.readParsedExps.add(exp);
    }

    public void addWriteParsedExp(FormulaParsedExpDTO exp) {
        this.writeParsedExps.add(exp);
    }

    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    @Override
    public Collection<FormulaParsedExp> getReadParsedExps() {
        return Collections.unmodifiableCollection(this.readParsedExps);
    }

    @Override
    public Collection<FormulaParsedExp> getWriteParsedExps() {
        return Collections.unmodifiableCollection(this.writeParsedExps);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FormulaFieldDTO dto = (FormulaFieldDTO)o;
        return Objects.equals(this.fieldKey, dto.fieldKey);
    }

    public int hashCode() {
        return Objects.hashCode(this.fieldKey);
    }
}


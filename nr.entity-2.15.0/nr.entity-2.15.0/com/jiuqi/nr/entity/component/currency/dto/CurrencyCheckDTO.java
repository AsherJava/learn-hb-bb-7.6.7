/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.component.currency.dto;

import java.util.HashSet;
import java.util.Set;

public class CurrencyCheckDTO {
    private Set<String> fixAttributes;
    private Set<String> generatorAttributes;

    public Set<String> getFixAttributes() {
        return this.fixAttributes;
    }

    public void setFixAttributes(Set<String> fixAttributes) {
        this.fixAttributes = fixAttributes;
    }

    public Set<String> getGeneratorAttributes() {
        return this.generatorAttributes;
    }

    public void setGeneratorAttributes(Set<String> generatorAttributes) {
        this.generatorAttributes = generatorAttributes;
    }

    public void addFix(String fixAttribute) {
        if (this.fixAttributes == null) {
            this.fixAttributes = new HashSet<String>(16);
        }
        this.fixAttributes.add(fixAttribute);
    }

    public void addGenerator(String generatorAttribute) {
        if (this.generatorAttributes == null) {
            this.generatorAttributes = new HashSet<String>(16);
        }
        this.generatorAttributes.add(generatorAttribute);
    }
}


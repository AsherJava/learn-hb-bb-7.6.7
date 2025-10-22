/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.copydes.IFmlMappingProvider;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine;
import java.util.List;

public class CopyErrorDesFmlMappingProvider
implements IFmlMappingProvider {
    private String sourceFmlScheme;
    private List<FormulaMappingDefine> formulaMapping;

    public void setSourceFmlScheme(String sourceFmlScheme) {
        this.sourceFmlScheme = sourceFmlScheme;
    }

    public void setFormulaMapping(List<FormulaMappingDefine> formulaMapping) {
        this.formulaMapping = formulaMapping;
    }

    @Override
    public String getSrcFmlScheme(String targetFmlScheme) {
        return this.sourceFmlScheme;
    }

    @Override
    public List<FormulaMappingDefine> getFormulaMapping(String targetFmlScheme) {
        return this.formulaMapping;
    }
}


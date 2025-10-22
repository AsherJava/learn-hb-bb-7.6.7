/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeFormulaController
 *  com.jiuqi.nr.definition.facade.FormulaField
 *  com.jiuqi.nr.definition.facade.FormulaParsedExp
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.internal.service.IFmlGraphAccessVerifier;
import com.jiuqi.nr.data.logic.internal.service.IFmlGraphDataCollector;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeFormulaController;
import com.jiuqi.nr.definition.facade.FormulaField;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FmlGraphDataCollectorImpl
implements IFmlGraphDataCollector {
    @Autowired
    private IRunTimeFormulaController formulaController;

    @Override
    public List<IParsedExpression> collect(String formulaSchemeKey, DimensionCombination masterKey, List<String> colIDs) {
        return this.collect(formulaSchemeKey, masterKey, colIDs, null);
    }

    @Override
    public List<IParsedExpression> collect(String formulaSchemeKey, DimensionCombination masterKey, List<String> colIDs, IFmlGraphAccessVerifier ... graphAccessVerifiers) {
        List formulaFields = this.formulaController.listFormulaFields(formulaSchemeKey, colIDs);
        if (CollectionUtils.isEmpty(formulaFields)) {
            return Collections.emptyList();
        }
        Validator validator = new Validator(masterKey, graphAccessVerifiers);
        ArrayList<IParsedExpression> result = new ArrayList<IParsedExpression>();
        this.traversal(validator, formulaFields, result);
        return result;
    }

    private void traversal(Validator validator, List<FormulaField> formulaFields, List<IParsedExpression> result) {
        if (CollectionUtils.isEmpty(formulaFields)) {
            return;
        }
        for (FormulaField formulaField : formulaFields) {
            Collection<FormulaParsedExp> readParsedExps = this.getCalReadParsedExps(formulaField);
            if (CollectionUtils.isEmpty(readParsedExps)) continue;
            for (FormulaParsedExp readParsedExp : readParsedExps) {
                if (!validator.access(readParsedExp)) continue;
                result.add(readParsedExp.getParsedExpression());
                Collection writeFields = readParsedExp.getWriteFields();
                if (CollectionUtils.isEmpty(writeFields)) continue;
                List<FormulaField> accessNodes = writeFields.stream().filter(validator::writeAccess).collect(Collectors.toList());
                this.traversal(validator, accessNodes, result);
            }
        }
    }

    private Collection<FormulaParsedExp> getCalReadParsedExps(FormulaField formulaField) {
        Collection readParsedExps = formulaField.getReadParsedExps();
        if (CollectionUtils.isEmpty(readParsedExps)) {
            return readParsedExps;
        }
        return readParsedExps.stream().filter(o -> DataEngineConsts.FormulaType.CALCULATE == o.getParsedExpression().getFormulaType()).collect(Collectors.toList());
    }

    private static class Validator {
        IFmlGraphAccessVerifier[] verifiers;
        Set<String> fml = new HashSet<String>();
        Set<String> queriedNodes = new HashSet<String>();
        DimensionCombination curMasterKey;

        public Validator(DimensionCombination masterKey, IFmlGraphAccessVerifier ... graphAccessVerifiers) {
            this.verifiers = graphAccessVerifiers;
            this.curMasterKey = masterKey;
        }

        public boolean writeAccess(FormulaField formulaField) {
            return this.queriedNodes.add(formulaField.getFieldKey());
        }

        public boolean access(FormulaParsedExp formulaParsedExp) {
            boolean addFml = this.addFml(formulaParsedExp.getParsedExpression().getKey());
            if (!addFml) {
                return false;
            }
            if (this.verifiers != null) {
                for (IFmlGraphAccessVerifier verifier : this.verifiers) {
                    if (verifier.access(formulaParsedExp, this.curMasterKey)) continue;
                    return false;
                }
            }
            return true;
        }

        public boolean addFml(String fmlKey) {
            return this.fml.add(fmlKey);
        }
    }
}


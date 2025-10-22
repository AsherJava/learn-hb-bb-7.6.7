/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.formulaschemeconfig.intf;

import com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeColumnProvider;
import org.springframework.stereotype.Component;

@Component
public class SamecontrolFormulaSchemeColumnProvider
implements IFormulaSchemeColumnProvider {
    @Override
    public String getCode() {
        return "SAMECONTROL";
    }
}


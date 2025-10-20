/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 */
package com.jiuqi.nr.designer.util;

import com.jiuqi.np.dataengine.definitions.Formula;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CycleUtil {
    public static List<Formula> distinctCycle(List<Formula> formulas) {
        ArrayList<Formula> disFormulas = new ArrayList<Formula>();
        HashSet<String> codeSet = new HashSet<String>();
        for (Formula formula : formulas) {
            if (!codeSet.add(formula.getCode())) continue;
            disFormulas.add(formula);
        }
        return disFormulas;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.data.engine.fml.var.VarAdjust
 *  com.jiuqi.nr.data.engine.fml.var.VarCCY
 *  com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIOD
 *  com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIODSTR
 *  com.jiuqi.nr.data.engine.fml.var.VarCUR_TIME
 *  com.jiuqi.nr.data.engine.fml.var.VarCUR_YEAR
 *  com.jiuqi.nr.data.engine.fml.var.VarSYS_SRC_TQRQ
 *  com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITCODE
 *  com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITTITLE
 *  com.jiuqi.nr.data.engine.fml.var.VarSYS_YEAR
 *  com.jiuqi.nr.data.engine.fml.var.VarUSER_DESCRIPTION
 *  com.jiuqi.nr.data.engine.fml.var.VarUSER_GUID
 *  com.jiuqi.nr.data.engine.fml.var.VarUSER_NAME
 *  com.jiuqi.nr.data.engine.fml.var.VarUSER_TITLE
 */
package com.jiuqi.nr.fmdm.internal.formula;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.data.engine.fml.var.VarAdjust;
import com.jiuqi.nr.data.engine.fml.var.VarCCY;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIOD;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIODSTR;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_TIME;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_YEAR;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_SRC_TQRQ;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITCODE;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITTITLE;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_YEAR;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_DESCRIPTION;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_GUID;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_NAME;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_TITLE;
import com.jiuqi.nr.fmdm.internal.formula.ContextVariableManager;
import com.jiuqi.nr.fmdm.internal.formula.PriorityContextVariableManager;
import com.jiuqi.nr.fmdm.internal.formula.VarFMDMCode;
import com.jiuqi.nr.fmdm.internal.formula.VarFMDMName;

public class DataEngineFormular {
    private DataEngineFormular() {
    }

    public static PriorityContextVariableManager initPriorityContextVariableManagerFmdm() {
        PriorityContextVariableManager normalContextVariableManager = new PriorityContextVariableManager();
        DataEngineFormular.initPriorityVars(normalContextVariableManager);
        return normalContextVariableManager;
    }

    public static ContextVariableManager initContextVariableManagerFmdm(String bizFieldCode) {
        ContextVariableManager normalContextVariableManager = new ContextVariableManager();
        DataEngineFormular.initVars(normalContextVariableManager, bizFieldCode);
        return normalContextVariableManager;
    }

    protected static void initPriorityVars(PriorityContextVariableManager contextVariableManager) {
        contextVariableManager.add((Variable)new VarCUR_YEAR());
        contextVariableManager.add((Variable)new VarSYS_YEAR());
        contextVariableManager.add((Variable)new VarCUR_PERIOD());
        contextVariableManager.add((Variable)new VarCUR_TIME());
        contextVariableManager.add((Variable)new VarCUR_PERIODSTR());
        contextVariableManager.add((Variable)new VarUSER_GUID());
        contextVariableManager.add((Variable)new VarUSER_NAME());
        contextVariableManager.add((Variable)new VarUSER_TITLE());
        contextVariableManager.add((Variable)new VarUSER_DESCRIPTION());
        contextVariableManager.add((Variable)new VarSYS_UNITCODE());
        contextVariableManager.add((Variable)new VarSYS_UNITTITLE());
        contextVariableManager.add((Variable)new VarSYS_SRC_TQRQ());
    }

    protected static void initVars(ContextVariableManager contextVariableManager, String bizFieldCode) {
        contextVariableManager.add((Variable)new VarCUR_YEAR());
        contextVariableManager.add((Variable)new VarSYS_YEAR());
        contextVariableManager.add((Variable)new VarCUR_PERIOD());
        contextVariableManager.add((Variable)new VarCUR_TIME());
        contextVariableManager.add((Variable)new VarCUR_PERIODSTR());
        contextVariableManager.add((Variable)new VarUSER_GUID());
        contextVariableManager.add((Variable)new VarUSER_NAME());
        contextVariableManager.add((Variable)new VarUSER_TITLE());
        contextVariableManager.add((Variable)new VarUSER_DESCRIPTION());
        contextVariableManager.add((Variable)new VarSYS_UNITCODE());
        contextVariableManager.add((Variable)new VarSYS_UNITTITLE());
        contextVariableManager.add(new VarFMDMCode(bizFieldCode));
        contextVariableManager.add(new VarFMDMName());
        contextVariableManager.add((Variable)new VarSYS_SRC_TQRQ());
        contextVariableManager.add((Variable)new VarAdjust());
        contextVariableManager.add((Variable)new VarCCY());
    }
}


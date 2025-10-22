/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManagerBase
 */
package com.jiuqi.nr.bql.interpret;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManagerBase;
import com.jiuqi.nr.bql.interpret.BiAdaptVariableNode;
import com.jiuqi.nr.bql.interpret.var.BiAdaptVariable;
import com.jiuqi.nr.bql.interpret.var.VarCUR_PERIOD;
import com.jiuqi.nr.bql.interpret.var.VarCUR_TIME;
import com.jiuqi.nr.bql.interpret.var.VarCUR_TIMEKEY;
import com.jiuqi.nr.bql.interpret.var.VarCUR_YEAR;
import com.jiuqi.nr.bql.interpret.var.VarDWDM;
import com.jiuqi.nr.bql.interpret.var.VarDWMC;
import com.jiuqi.nr.bql.interpret.var.VarSYS_UNITCODE;
import com.jiuqi.nr.bql.interpret.var.VarSYS_UNITKEY;
import com.jiuqi.nr.bql.interpret.var.VarSYS_YEAR;
import java.util.List;

public final class BiAdaptVariableManager
extends VariableManagerBase
implements IReportDynamicNodeProvider {
    public BiAdaptVariableManager() {
        this.add(new VarCUR_PERIOD());
        this.add(new VarCUR_TIME());
        this.add(new VarCUR_TIMEKEY());
        this.add(new VarCUR_YEAR());
        this.add(new VarDWDM());
        this.add(new VarDWMC());
        this.add(new VarSYS_UNITCODE());
        this.add(new VarSYS_UNITKEY());
        this.add(new VarSYS_YEAR());
    }

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        Variable var = this.find(refName.toUpperCase());
        if (var != null) {
            return new BiAdaptVariableNode(token, (BiAdaptVariable)var);
        }
        return null;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpecial(IContext context, Token token, String refName) throws DynamicNodeException {
        return this.find(context, token, refName);
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.EvalItem;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExprExecContext;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class ConditionExecutor
extends ExecutorBase {
    private ArrayList<EvalItem> evalItems = new ArrayList();
    private Map<String, Integer> evalItemMap = new HashMap<String, Integer>();

    public ConditionExecutor(ExprExecContext context) {
        super(context.GlobalContext);
    }

    public int size() {
        return this.evalItems.size();
    }

    public ASTNode get(int index) {
        return (ASTNode)this.evalItems.get(index).getExpression();
    }

    public EvalItem getItem(int index) {
        return this.evalItems.get(index);
    }

    public int add(IASTNode expression) {
        this.evalItems.add(new EvalItem(expression));
        return this.evalItems.size() - 1;
    }

    public int add(String key, IASTNode expression) {
        Integer index = this.evalItemMap.get(key);
        if (index != null) {
            return index;
        }
        this.evalItems.add(new EvalItem(expression));
        index = this.evalItems.size() - 1;
        this.evalItemMap.put(key, index);
        return index;
    }

    public void remove(IASTNode expression) {
        for (int i = this.evalItems.size() - 1; i >= 0; --i) {
            if (!this.evalItems.get(i).getExpression().equals(expression)) continue;
            this.evalItems.remove(i);
        }
    }

    public void combine(ConditionExecutor another) {
        this.evalItems.addAll(another.evalItems);
    }

    public AbstractData getResult(int index) {
        return this.evalItems.get(index).getReault();
    }

    @Override
    protected void doPreparation(Object taskInfo) throws Exception {
        super.doPreparation(taskInfo);
        if (this.state == 2) {
            for (int i = 0; i < this.evalItems.size(); ++i) {
                EvalItem evalItem = this.evalItems.get(i);
                evalItem.setReault(null);
            }
        }
    }

    @Override
    protected boolean doExecution(Object taskInfo) throws Exception {
        int count = this.evalItems.size();
        for (int i = 0; i < count; ++i) {
            StringBuilder msg;
            EvalItem evalItem = this.evalItems.get(i);
            IASTNode expression = evalItem.getExpression();
            try {
                Object resultValue = expression.evaluate((IContext)this.context);
                evalItem.setReault(AbstractData.valueOf(resultValue, expression.getType((IContext)this.context)));
                continue;
            }
            catch (SyntaxException se) {
                if (se.isCanIgnore()) continue;
                msg = new StringBuilder();
                msg.append("\u8ba1\u7b97\u53d6\u503c\u8868\u8fbe\u5f0f          ");
                expression.interpret((IContext)this.context, msg, Language.FORMULA, (Object)DataEngineConsts.FORMULA_SHOW_INFO_DATA);
                msg.append("   \u51fa\u9519:").append(se.getMessage());
                this.context.getMonitor().error(msg.toString(), this);
                if (!this.context.outFMLPlan()) continue;
                this.context.getMonitor().exception((Exception)((Object)se));
                continue;
            }
            catch (Exception ex) {
                msg = new StringBuilder();
                msg.append("\u8ba1\u7b97\u53d6\u503c\u8868\u8fbe\u5f0f          ");
                expression.interpret((IContext)this.context, msg, Language.FORMULA, (Object)DataEngineConsts.FORMULA_SHOW_INFO_DATA);
                msg.append("   \u51fa\u9519:").append(ex.getMessage());
                this.context.getMonitor().error(msg.toString(), this);
                if (!this.context.outFMLPlan()) continue;
                this.context.getMonitor().exception(ex);
            }
        }
        return true;
    }

    public boolean doEvaluate() throws Exception {
        int count = this.evalItems.size();
        for (int i = 0; i < count; ++i) {
            EvalItem evalItem = this.evalItems.get(i);
            IASTNode expression = evalItem.getExpression();
            try {
                Object resultValue = expression.evaluate((IContext)this.context);
                evalItem.setReault(AbstractData.valueOf(resultValue, expression.getType((IContext)this.context)));
                continue;
            }
            catch (Exception ex) {
                StringBuilder msg = new StringBuilder();
                msg.append("\u83b7\u53d6\u53d6\u503c\u8868\u8fbe\u5f0f          ");
                expression.interpret((IContext)this.context, msg, Language.FORMULA, (Object)DataEngineConsts.FORMULA_SHOW_INFO_DATA);
                msg.append("  \u8ba1\u7b97\u7ed3\u679c\u51fa\u9519:").append(ex.getMessage());
                this.context.getMonitor().error(msg.toString(), this);
                if (!this.context.outFMLPlan()) continue;
                this.context.getMonitor().exception(ex);
            }
        }
        return true;
    }

    public void print(StringBuilder buff) {
        FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA);
        for (int i = 0; i < this.size(); ++i) {
            ASTNode exp = this.get(i);
            try {
                exp.interpret((IContext)this.context, buff, Language.FORMULA, (Object)formulaShowInfo);
                buff.append("\n");
                continue;
            }
            catch (InterpretException interpretException) {
                // empty catch block
            }
        }
    }

    public void cloneExpressions(QueryContext context) {
        for (EvalItem evalItem : this.evalItems) {
            ASTNode newExp = (ASTNode)evalItem.getExpression().clone();
            for (IASTNode node : newExp) {
                if (!(node instanceof DynamicDataNode)) continue;
                DynamicDataNode dataNode = (DynamicDataNode)node;
                if (dataNode.isReadValueByIndex()) {
                    context.getMonitor().message("\u6d6e\u52a8\u516c\u5f0f\u8282\u70b9\u514b\u9686\u5931\u8d25\uff0c\u653e\u5f03\u4f18\u5316", this);
                    return;
                }
                dataNode.setReadValueByIndex(true);
                dataNode.setQueryFieldInfo(context.getDataReader().findQueryFieldInfo(dataNode.getQueryField()));
            }
            evalItem.setExpression((IASTNode)newExp);
        }
    }
}


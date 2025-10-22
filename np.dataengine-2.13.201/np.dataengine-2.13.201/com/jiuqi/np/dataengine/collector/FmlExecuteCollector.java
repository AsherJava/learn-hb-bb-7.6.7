/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.np.dataengine.collector;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.collector.FieldExecInfo;
import com.jiuqi.np.dataengine.collector.FmlExecuteCollectConfig;
import com.jiuqi.np.dataengine.collector.FocusInfoCollector;
import com.jiuqi.np.dataengine.collector.GlobalInfo;
import com.jiuqi.np.dataengine.collector.LogRowCollector;
import com.jiuqi.np.dataengine.collector.NodeCostCollector;
import com.jiuqi.np.dataengine.collector.WanningCollector;
import com.jiuqi.np.dataengine.log.LogRow;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.List;

public class FmlExecuteCollector {
    private FmlExecuteCollectConfig config;
    private WanningCollector wanningCollector = new WanningCollector();
    private List<String> errorMessages = new ArrayList<String>();
    private NodeCostCollector nodeCostCollector = new NodeCostCollector();
    private FocusInfoCollector focusInfoCollector;
    private LogRowCollector logRowCollector = new LogRowCollector();
    private GlobalInfo globalInfo = new GlobalInfo();

    public FmlExecuteCollector(FmlExecuteCollectConfig config) {
        this.config = config;
    }

    public void init(QueryContext qContext) {
        if (this.focusInfoCollector == null && this.config.getFocusZbExps().size() > 0) {
            this.focusInfoCollector = new FocusInfoCollector();
            for (String zbExp : this.config.getFocusZbExps()) {
                try {
                    IExpression exp = qContext.getFormulaParser().parseEval(zbExp, (IContext)qContext);
                    DynamicDataNode dataNode = null;
                    for (IASTNode node : exp) {
                        if (!(node instanceof DynamicDataNode)) continue;
                        dataNode = (DynamicDataNode)node;
                        break;
                    }
                    if (dataNode != null) {
                        this.getFocusInfoCollector().getFieldExecInfos().add(new FieldExecInfo(dataNode));
                        continue;
                    }
                    throw new ParseException("\u6839\u636e\u8868\u8fbe\u5f0f" + zbExp + "\u672a\u627e\u5230\u8981\u5173\u6ce8\u7684\u5b57\u6bb5");
                }
                catch (ParseException e) {
                    qContext.getMonitor().exception((Exception)((Object)e));
                }
            }
        }
    }

    public FmlExecuteCollectConfig getConfig() {
        return this.config;
    }

    public WanningCollector getWanningCollector() {
        return this.wanningCollector;
    }

    public List<String> getErrorMessages() {
        return this.errorMessages;
    }

    public NodeCostCollector getNodeCostCollector() {
        return this.nodeCostCollector;
    }

    public FocusInfoCollector getFocusInfoCollector() {
        return this.focusInfoCollector;
    }

    public LogRowCollector getLogRowCollector() {
        return this.logRowCollector;
    }

    public GlobalInfo getGlobalInfo() {
        return this.globalInfo;
    }

    public void addSqlLogRow(LogRow logRow) {
        this.logRowCollector.getLogRows().add(logRow);
        if (this.focusInfoCollector != null) {
            this.focusInfoCollector.collectSqlLogRow(logRow);
        }
    }
}


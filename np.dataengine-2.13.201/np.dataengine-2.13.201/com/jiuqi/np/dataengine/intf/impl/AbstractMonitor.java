/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.BinaryOperator
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.BinaryOperator;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.collector.FmlExecuteCollector;
import com.jiuqi.np.dataengine.collector.GlobalInfo;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.LoggerConfig;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataChangeListener;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.NodeAdapter;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractMonitor
implements IMonitor {
    private double currentProgress = 0.0;
    private double lastProgress = 0.0;
    private double step;
    protected List<String> errorMessage = new ArrayList<String>();
    protected DataEngineConsts.DataEngineRunType runType = DataEngineConsts.DataEngineRunType.UNKOWN;
    private static final Logger logger = LoggerFactory.getLogger(AbstractMonitor.class);
    protected long start = 0L;
    protected int recordCount = 0;
    protected int formulaCount = 0;
    protected int fieldCount = 0;
    protected int updateRecordCount = 0;
    protected boolean finished = false;
    protected final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LoggerConfig loggerConfig;
    private List<IDataChangeListener> dataChangeListeners = new ArrayList<IDataChangeListener>();
    private IFieldsInfo fieldsInfo;
    private QueryParam queryParam;
    private FmlExecuteCollector collector;

    @Override
    public boolean isCancel() {
        return false;
    }

    public AbstractMonitor() {
    }

    public AbstractMonitor(DataEngineConsts.DataEngineRunType runType) {
        this.runType = runType;
    }

    @Override
    public void error(FormulaCheckEventImpl event) {
        if (this.isOutFMLPlan()) {
            this.debug(event.toString(), DataEngineConsts.DebugLogType.COMMON);
        }
    }

    @Override
    public void message(String msg, Object sender) {
        String message = this.getLogMessage(msg);
        this.getLogger().info(message);
    }

    @Override
    public void error(String msg, Object sender) {
        String errorMsg = msg;
        if (sender != null) {
            errorMsg = sender.getClass().getSimpleName() + ":" + msg;
        }
        if (this.isDebug() || this.isOutFMLPlan()) {
            this.getLogger().info(errorMsg);
        } else {
            this.getLogger().error(errorMsg);
        }
    }

    @Override
    public void finish() {
        if (!this.finished) {
            if (!this.errorMessage.isEmpty()) {
                for (String msg : this.errorMessage) {
                    this.getLogger().error(msg);
                }
            }
            StringBuilder msg = new StringBuilder("\u6267\u884c\u5b8c\u6210");
            if (this.start > 0L) {
                long cost = System.currentTimeMillis() - this.start;
                msg.append(",\u8017\u65f6").append(Round.callFunction((Number)Float.valueOf((float)cost / 1000.0f), (int)2)).append("s    ");
                msg.append("\u5171\u6267\u884c").append(this.formulaCount).append("\u6761\u516c\u5f0f, \u6d89\u53ca").append(this.fieldCount).append("\u4e2a\u5b57\u6bb5, ");
                msg.append("\u67e5\u8be2\u4e86").append(this.recordCount).append("\u6761\u8bb0\u5f55, ");
                msg.append("\u66f4\u65b0\u4e86").append(this.updateRecordCount).append("\u6761\u8bb0\u5f55");
                this.message(msg.toString(), null);
                if (this.collector != null) {
                    GlobalInfo globalInfo = this.collector.getGlobalInfo();
                    globalInfo.setTotalCost(cost);
                    globalInfo.setFieldCount(this.fieldCount);
                    globalInfo.setFormulaCount(this.formulaCount);
                    globalInfo.setQueryRecordCount(this.recordCount);
                    this.collector.getErrorMessages().addAll(this.errorMessage);
                }
            }
            for (IDataChangeListener dataChangeListener : this.dataChangeListeners) {
                dataChangeListener.finish();
            }
            this.onProgress(1.0);
        }
        this.finished = true;
    }

    @Override
    public void exception(Exception e) {
        this.errorMessage.add(e.getMessage());
        if (this.isDebug() || this.isOutFMLPlan()) {
            this.getLogger().error(e.getMessage(), e);
        } else {
            Logger logger = this.getLogger();
            if (logger.isDebugEnabled()) {
                logger.debug(e.getMessage(), e);
            }
        }
    }

    public double getStep() {
        return this.step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public void step() {
        if (this.step == 0.0) {
            return;
        }
        this.currentProgress += this.step;
        if (this.currentProgress > 0.99) {
            this.currentProgress = 0.99;
        }
        if (this.currentProgress - this.lastProgress > 0.1) {
            this.lastProgress = this.currentProgress;
            this.onProgress(this.currentProgress);
        }
    }

    @Override
    public void onProgress(double progress) {
        this.currentProgress = progress;
        this.debug("progress:" + this.currentProgress * 100.0 + "%", DataEngineConsts.DebugLogType.COMMON);
    }

    @Override
    public void onDataChange(UpdateDataSet updateDatas) {
        if (DataEngineConsts.DATA_ENGINE_DEBUG_DATACHANGE) {
            this.debug("\u4fee\u6539\u7684\u6570\u636e\uff1a\n" + updateDatas.toString(), DataEngineConsts.DebugLogType.DATACHANGE);
        }
        for (IDataChangeListener dataChangeListener : this.dataChangeListeners) {
            dataChangeListener.onDataChange(this, updateDatas);
        }
    }

    @Override
    public void beforeDelete(List<DimensionValueSet> delRowKeys) {
        this.debug("del:" + delRowKeys, DataEngineConsts.DebugLogType.DATACHANGE);
        for (IDataChangeListener dataChangeListener : this.dataChangeListeners) {
            dataChangeListener.beforeDelete(delRowKeys, this.fieldsInfo);
        }
    }

    @Override
    public boolean beforeDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys) {
        this.debug("deleteAll", DataEngineConsts.DebugLogType.DATACHANGE);
        this.beforeDeleteAll(deleteKeys);
        for (IDataChangeListener dataChangeListener : this.dataChangeListeners) {
            dataChangeListener.beforeDeleteAll(masterKeys, deleteKeys, this.fieldsInfo);
        }
        return true;
    }

    @Override
    public void beforeUpdate(List<IDataRow> updateRows) {
        for (IDataChangeListener dataChangeListener : this.dataChangeListeners) {
            dataChangeListener.beforeUpdate(updateRows);
        }
    }

    @Override
    public void onCommitException(Exception ex, List<InsertRowEvent> insertRowEvents, List<UpdateRowEvent> updateRowEvents, List<DeleteRowEvent> deleteRowEvents, List<DeleteAllRowEvent> deleteAllRowEvents) {
        for (IDataChangeListener dataChangeListener : this.dataChangeListeners) {
            dataChangeListener.onCommitException(ex, insertRowEvents, updateRowEvents, deleteRowEvents, deleteAllRowEvents);
        }
    }

    @Override
    public void afterDelete(List<DimensionValueSet> delRowKeys) {
        for (IDataChangeListener dataChangeListener : this.dataChangeListeners) {
            dataChangeListener.afterDelete(delRowKeys, this.fieldsInfo);
        }
    }

    @Override
    public void afterUpdate(List<IDataRow> updateRows) {
        for (IDataChangeListener dataChangeListener : this.dataChangeListeners) {
            dataChangeListener.afterUpdate(updateRows);
        }
    }

    @Override
    public void afterDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys) {
        for (IDataChangeListener dataChangeListener : this.dataChangeListeners) {
            dataChangeListener.afterDeleteAll(masterKeys, deleteKeys, this.fieldsInfo);
        }
    }

    @Override
    public void afterDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys, IASTNode filterNode) {
        if (filterNode == null) {
            this.afterDeleteAll(masterKeys, deleteKeys);
            return;
        }
        for (IDataChangeListener dataChangeListener : this.dataChangeListeners) {
            if (dataChangeListener.supportDeleteAllWithFilter()) {
                dataChangeListener.afterDeleteAll(masterKeys, this.fieldsInfo, filterNode);
                continue;
            }
            dataChangeListener.afterDeleteAll(masterKeys, deleteKeys, this.fieldsInfo);
        }
    }

    public DataEngineConsts.DataEngineRunType getRunType() {
        return this.runType;
    }

    public void setRunType(DataEngineConsts.DataEngineRunType runType) {
        if (runType != null) {
            this.runType = runType;
        }
    }

    protected Logger getLogger() {
        return logger;
    }

    @Override
    public void debug(String msg, DataEngineConsts.DebugLogType type) {
        String message = this.getLogMessage(msg);
        if (this.isOutFMLPlan()) {
            this.getLogger().info(message);
            return;
        }
        if (this.isDebug()) {
            if (type == null) {
                type = DataEngineConsts.DebugLogType.COMMON;
            }
            boolean toLog = true;
            switch (type) {
                case SQL: {
                    toLog = this.getLoggerConfig().isOutSql();
                    break;
                }
                case DATACHANGE: {
                    toLog = DataEngineConsts.DATA_ENGINE_DEBUG_DATACHANGE;
                    break;
                }
            }
            if (toLog) {
                this.getLogger().info(message);
            }
        } else {
            this.getLogger().debug(message);
        }
    }

    public double getCurrentProgress() {
        return this.currentProgress;
    }

    @Override
    public void start() {
        if ((this.runType == DataEngineConsts.DataEngineRunType.CHECK || this.runType == DataEngineConsts.DataEngineRunType.CALCULATE) && this.start == 0L) {
            this.start = System.currentTimeMillis();
        }
    }

    public void addRecordCount(int recordCount) {
        this.recordCount += recordCount;
    }

    public void addFormulaCount(int formulaCount) {
        this.formulaCount += formulaCount;
    }

    public void addFieldCount(int fieldCount) {
        this.fieldCount += fieldCount;
    }

    public void addUpdateRecordCount(int updateRecordCount) {
        this.updateRecordCount += updateRecordCount;
    }

    private String getLogMessage(String msg) {
        String message = msg;
        if (this.runType != null) {
            message = this.runType.getTitle() + " - " + msg;
        }
        return message;
    }

    @Override
    public void error(CheckExpression expression, QueryContext context) throws SyntaxException, DataTypeException {
        FormulaCheckEventImpl event = this.buildErrorEvent(expression, context);
        this.error(event);
    }

    public FormulaCheckEventImpl buildErrorEvent(CheckExpression expression, QueryContext context) throws SyntaxException, DataTypeException {
        return this.buildErrorEvent(expression, context, null);
    }

    public FormulaCheckEventImpl buildErrorEvent(CheckExpression expression, QueryContext context, Exception e) throws SyntaxException, DataTypeException {
        FormulaCheckEventImpl event = new FormulaCheckEventImpl();
        Formula source = expression.getSource();
        event.setFormulaObj(source);
        event.setFormulaExpression(source.getFormula());
        expression.getCompliedFormulaExp();
        event.setCompliedFormulaExpression(expression.getCompliedFormulaExp() != null ? expression.getCompliedFormulaExp() : expression.interpret((IContext)context, Language.FORMULA, context.getExeContext().getFormulaShowInfo()));
        event.setFormulaMeaning(expression.getExplain() != null ? expression.getExplain() : expression.interpret((IContext)context, Language.EXPLAIN, context.getExeContext().getFormulaShowInfo()));
        event.setKey(expression.getKey());
        if (e == null) {
            IASTNode mainNode = expression.getChild(0);
            BinaryOperator compareNode = this.findCompareNode(mainNode, context);
            AbstractData diff = null;
            AbstractData leftValue = null;
            AbstractData rightValue = null;
            if (compareNode != null) {
                Object leftValueObj = this.evaluateNode(context, compareNode.getChild(0));
                Object rightValueObj = this.evaluateNode(context, compareNode.getChild(1));
                int leftType = compareNode.getChild(0).getType((IContext)context);
                if (leftType == 0) {
                    leftType = DataType.typeOf((Object)leftValueObj);
                }
                leftValue = AbstractData.valueOf(leftValueObj, leftType);
                int rightType = compareNode.getChild(1).getType((IContext)context);
                if (rightType == 0) {
                    rightType = DataType.typeOf((Object)rightValueObj);
                }
                rightValue = AbstractData.valueOf(rightValueObj, rightType);
                if (DataTypes.isNum(leftValue.dataType) && (leftValueObj != null || rightValueObj != null)) {
                    BigDecimal left = new BigDecimal(leftValue.isNull ? "0" : leftValueObj.toString());
                    BigDecimal right = new BigDecimal(rightValue.isNull ? "0" : rightValueObj.toString());
                    diff = AbstractData.valueOf(left.subtract(right), leftValue.dataType);
                }
            }
            if (leftValue == null) {
                leftValue = DataTypes.getNullValue(6);
            }
            event.setLeftValue(leftValue);
            if (rightValue == null) {
                rightValue = DataTypes.getNullValue(6);
            }
            event.setRightValue(rightValue);
            if (diff == null) {
                diff = DataTypes.getNullValue(6);
            }
            event.setDifferenceValue(diff);
        } else {
            event.setCheckException(e);
        }
        ArrayList<IASTNode> nodeList = new ArrayList<IASTNode>();
        this.collectDataNodes((IASTNode)expression, nodeList, context);
        for (IASTNode dataNode : nodeList) {
            Object nodeValue = this.evaluateNode(context, dataNode);
            AbstractData dataValue = AbstractData.valueOf(nodeValue, dataNode.getType((IContext)context));
            DynamicDataNode dynamicDataNode = null;
            CellDataNode cellDataNode = null;
            if (dataNode instanceof DynamicDataNode) {
                dynamicDataNode = (DynamicDataNode)dataNode;
            } else if (dataNode instanceof CellDataNode && dataNode.getChild(0) instanceof DynamicDataNode) {
                dynamicDataNode = (DynamicDataNode)dataNode.getChild(0);
                cellDataNode = (CellDataNode)dataNode;
            }
            if (dynamicDataNode == null) continue;
            NodeAdapter nodeAdapter = new NodeAdapter(dynamicDataNode, dataValue);
            nodeAdapter.setCellInfo(cellDataNode);
            event.getNodes().add(nodeAdapter);
        }
        DimensionValueSet rowKey = new DimensionValueSet(context.getRowKey());
        if (!rowKey.hasValue("DATATIME")) {
            rowKey.setValue("DATATIME", context.getDimensionValue("DATATIME"));
        }
        event.setRowkey(rowKey);
        event.setWildcardCol(expression.getWildcardCol());
        event.setWildcardRow(expression.getWildcardRow());
        return event;
    }

    private Object evaluateNode(QueryContext context, IASTNode dataNode) {
        Object nodeValue = null;
        try {
            nodeValue = dataNode.evaluate((IContext)context);
            int scale = dataNode.getScale((IContext)context);
            if (scale >= 0) {
                if (nodeValue instanceof BigDecimal) {
                    BigDecimal bigDecimalValue = (BigDecimal)nodeValue;
                    nodeValue = bigDecimalValue.setScale(scale, 4);
                } else if (nodeValue instanceof Number) {
                    double floatValue = Convert.toDouble((Object)nodeValue);
                    nodeValue = Round.callFunction((Number)floatValue, (int)scale);
                }
            }
        }
        catch (Exception e) {
            this.exception(e);
        }
        return nodeValue;
    }

    private void collectDataNodes(IASTNode mainNode, List<IASTNode> nodeList, QueryContext queryContext) {
        for (int i = 0; i < mainNode.childrenSize(); ++i) {
            DynamicDataNode fmlNode;
            IASTNode childNode = mainNode.getChild(i);
            if (childNode instanceof IfThenElse) {
                IfThenElse ifThenElse = (IfThenElse)childNode;
                this.collectDataNodes(ifThenElse.getChild(0), nodeList, queryContext);
                boolean resultValue = false;
                try {
                    resultValue = ifThenElse.getChild(0).judge((IContext)queryContext);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (resultValue) {
                    this.collectDataNodes(ifThenElse.getChild(1), nodeList, queryContext);
                    continue;
                }
                if (ifThenElse.childrenSize() <= 2) continue;
                this.collectDataNodes(ifThenElse.getChild(2), nodeList, queryContext);
                continue;
            }
            if (childNode instanceof DynamicDataNode) {
                fmlNode = (DynamicDataNode)childNode;
                if (this.containsDataNode(nodeList, fmlNode)) continue;
                nodeList.add(childNode);
                continue;
            }
            if (childNode instanceof CellDataNode && childNode.getChild(0) instanceof DynamicDataNode) {
                fmlNode = (DynamicDataNode)childNode.getChild(0);
                if (this.containsDataNode(nodeList, fmlNode)) continue;
                nodeList.add(childNode);
                continue;
            }
            if (childNode.childrenSize() <= 0) continue;
            this.collectDataNodes(childNode, nodeList, queryContext);
        }
    }

    private boolean containsDataNode(List<IASTNode> nodeList, DynamicDataNode fmlNode) {
        for (IASTNode currentNode : nodeList) {
            if (!(currentNode instanceof DynamicDataNode ? this.nodeEquals(fmlNode, currentNode) : currentNode instanceof CellDataNode && currentNode.getChild(0) instanceof DynamicDataNode && this.nodeEquals(fmlNode, currentNode.getChild(0)))) continue;
            return true;
        }
        return false;
    }

    private boolean nodeEquals(DynamicDataNode fmlNode, IASTNode currentNode) {
        DynamicDataNode dataNode = (DynamicDataNode)currentNode;
        if (dataNode.equals((Object)fmlNode)) {
            if (dataNode.getDataLink() == null && fmlNode.getDataLink() == null) {
                return true;
            }
            if (dataNode.getDataLink() != null && dataNode.getDataLink().equals(fmlNode.getDataLink())) {
                return true;
            }
        }
        return false;
    }

    private BinaryOperator findCompareNode(IASTNode mainNode, QueryContext context) {
        if (mainNode instanceof BinaryOperator) {
            return (BinaryOperator)mainNode;
        }
        if (mainNode instanceof IfThenElse) {
            try {
                boolean conditionResult = mainNode.getChild(0).judge((IContext)context);
                if (conditionResult) {
                    return this.findCompareNode(mainNode.getChild(1), context);
                }
                if (mainNode.childrenSize() == 3) {
                    return this.findCompareNode(mainNode.getChild(2), context);
                }
            }
            catch (SyntaxException e) {
                context.getMonitor().exception((Exception)((Object)e));
            }
        }
        return null;
    }

    public List<IDataChangeListener> getDataChangeListeners() {
        return this.dataChangeListeners;
    }

    public boolean isOutFMLPlan() {
        if (this.collector != null) {
            return true;
        }
        return this.getLoggerConfig().isFmlplan();
    }

    public IFieldsInfo getFieldsInfo() {
        return this.fieldsInfo;
    }

    public void setFieldsInfo(IFieldsInfo fieldsInfo) {
        this.fieldsInfo = fieldsInfo;
    }

    @Override
    public IDatabase getDatabase() {
        if (this.queryParam != null) {
            return this.queryParam.getDatabase();
        }
        return null;
    }

    public void setQueryParam(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    public QueryParam getQueryParam() {
        return this.queryParam;
    }

    public void setLoggerConfig(LoggerConfig loggerConfig) {
        this.loggerConfig = loggerConfig;
    }

    public LoggerConfig getLoggerConfig() {
        if (this.loggerConfig == null) {
            this.loggerConfig = new LoggerConfig();
        }
        return this.loggerConfig;
    }

    @Override
    public boolean isDebug() {
        if (this.collector != null) {
            return true;
        }
        return this.getLoggerConfig().isDebug();
    }

    public FmlExecuteCollector getCollector() {
        return this.collector;
    }

    public void setCollector(FmlExecuteCollector collector) {
        this.collector = collector;
    }

    public DateFormat getDateFormat() {
        return this.dateFormat;
    }
}


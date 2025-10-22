/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.function.logic.InList
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.common.TempResource
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.engine.summary.exe.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.function.logic.InList;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.summary.common.SumEngineConsts;
import com.jiuqi.nr.data.engine.summary.define.ISumBaseDefineFactory;
import com.jiuqi.nr.data.engine.summary.exe.ISumReportExecuter;
import com.jiuqi.nr.data.engine.summary.exe.SumCell;
import com.jiuqi.nr.data.engine.summary.exe.impl.CellsSumExecuter;
import com.jiuqi.nr.data.engine.summary.exe.impl.SumExeInfo;
import com.jiuqi.nr.data.engine.summary.exe.impl.group.GroupCellsSumExecuter;
import com.jiuqi.nr.data.engine.summary.parse.SumContext;
import com.jiuqi.nr.data.engine.summary.parse.SumNode;
import com.jiuqi.nr.data.engine.summary.parse.SumNodeProvider;
import com.jiuqi.nr.data.engine.summary.parse.func.COUNTFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.InCollectionFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.MAXFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.MINFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.SUMFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.StartWithFunction;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SumReportExecuterImpl
implements ISumReportExecuter {
    private static final Logger logger = LoggerFactory.getLogger(SumReportExecuterImpl.class);
    private final List<SumCell> cells = new ArrayList<SumCell>();
    private ISumBaseDefineFactory baseDefineFactory;
    private final IDataAccessProvider dataAccessProvider;
    private int parallelSize = 5;
    private int sum_strategy = 2;
    private String fmlFilter;
    private DimensionValueSet srcDimension;
    private DimensionValueSet destDimension;
    private final IDataDefinitionRuntimeController runtimeController;
    private final DataSource dataSource;
    private boolean allSum = true;
    private ReportFormulaParser parser;

    public SumReportExecuterImpl(ISumBaseDefineFactory baseDefineFactory, IDataAccessProvider dataAccessProvider, IDataDefinitionRuntimeController runtimeController, DataSource dataSource) {
        this.baseDefineFactory = baseDefineFactory;
        this.dataAccessProvider = dataAccessProvider;
        this.runtimeController = runtimeController;
        this.dataSource = dataSource;
        this.parser = this.createFormulaParser(baseDefineFactory);
    }

    @Override
    public void doExecute(ExecutorContext srcExecutorContext) throws Exception {
        if (this.cells.isEmpty()) {
            return;
        }
        this.judgeStrategy();
        SumCell oneCell = this.cells.get(0);
        FieldDefine destField = oneCell.getDestField();
        String tableName = srcExecutorContext.getCache().getDataModelDefinitionsCache().getTableName(destField);
        TableModelRunInfo tableRunInfo = srcExecutorContext.getCache().getDataModelDefinitionsCache().getTableInfo(tableName);
        ColumnModelDefine orderFiecolumn = tableRunInfo.getOrderField();
        FieldDefine orderField = null;
        IDataQuery query = this.dataAccessProvider.newDataQuery();
        HashMap<String, FieldDefine> fieldMap = new HashMap<String, FieldDefine>();
        for (SumCell cell : this.cells) {
            destField = cell.getDestField();
            query.addColumn(destField);
            fieldMap.put(cell.getAlias(), destField);
        }
        if (orderFiecolumn != null) {
            orderField = srcExecutorContext.getCache().getDataModelDefinitionsCache().getFieldDefine(orderFiecolumn);
            query.addColumn(orderField);
        }
        query.setMasterKeys(this.destDimension);
        ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
        IDataUpdator updator = query.openForUpdate(executorContext, true);
        IDataRow row = null;
        if (orderFiecolumn == null) {
            row = updator.addInsertedRow(new DimensionValueSet(this.destDimension));
        }
        Map<String, TempAssistantTable> tempAssistantTables = null;
        try (Connection conn = this.dataSource.getConnection();
             TempResource tempResource = new TempResource();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            tempAssistantTables = this.tryCreateTempTables(database, srcExecutorContext, tempResource);
            this.createTempTables(conn, tempAssistantTables);
            if (this.parallelSize <= 1) {
                List<CellsSumExecuter> executers = this.splitCells(srcExecutorContext, orderField, updator, tempAssistantTables);
                this.doSumBySerialStrategy(executers, updator, row);
            } else {
                List<CellsSumExecuter> executers = this.splitCells(srcExecutorContext, orderField, null, tempAssistantTables);
                executers = this.tryGroupSumOptimize(orderField, srcExecutorContext, tempAssistantTables, executers);
                updator.commitChanges(true);
                this.doSumByParallelStrategy(executers);
            }
        }
    }

    private List<CellsSumExecuter> tryGroupSumOptimize(FieldDefine orderField, ExecutorContext executorContext, Map<String, TempAssistantTable> tempAssistantTables, List<CellsSumExecuter> executers) throws ParseException {
        HashMap<SumNode, ArrayList<CellsSumExecuter>> oneKeyFilterExecuters = new HashMap<SumNode, ArrayList<CellsSumExecuter>>();
        ArrayList<CellsSumExecuter> noKeyFilterExecuters = new ArrayList<CellsSumExecuter>();
        ArrayList<CellsSumExecuter> multiFilterExecuters = new ArrayList<CellsSumExecuter>();
        SumContext context = new SumContext();
        context.setBaseDefineFactory(this.baseDefineFactory);
        context.setExecutorContext(executorContext);
        if (this.allSum && orderField == null && this.sum_strategy == 2 || this.sum_strategy == 3) {
            for (CellsSumExecuter cellsSumExecuter : executers) {
                String condition = cellsSumExecuter.getCondition();
                if (StringUtils.isNotEmpty((String)condition) && !condition.startsWith("(//")) {
                    try {
                        IExpression exp = this.parser.parseCond(condition, (IContext)context);
                        if (exp == null) continue;
                        cellsSumExecuter.setConditionExp(exp);
                        HashSet<SumNode> zbs = new HashSet<SumNode>();
                        for (IASTNode node : exp) {
                            SumNode sumNode;
                            if (!(node instanceof SumNode) || zbs.contains((Object)(sumNode = (SumNode)node))) continue;
                            zbs.add(sumNode);
                        }
                        if (zbs.size() == 1) {
                            for (SumNode groupFieldKey : zbs) {
                                ArrayList<CellsSumExecuter> list = (ArrayList<CellsSumExecuter>)oneKeyFilterExecuters.get((Object)groupFieldKey);
                                if (list == null) {
                                    list = new ArrayList<CellsSumExecuter>();
                                    oneKeyFilterExecuters.put(groupFieldKey, list);
                                }
                                list.add(cellsSumExecuter);
                            }
                            continue;
                        }
                        if (zbs.size() == 0) {
                            noKeyFilterExecuters.add(cellsSumExecuter);
                            continue;
                        }
                        multiFilterExecuters.add(cellsSumExecuter);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        noKeyFilterExecuters.add(cellsSumExecuter);
                    }
                    continue;
                }
                noKeyFilterExecuters.add(cellsSumExecuter);
            }
            if (oneKeyFilterExecuters.size() > 0) {
                executers = new ArrayList<CellsSumExecuter>();
                executers.addAll(multiFilterExecuters);
                for (Map.Entry entry : oneKeyFilterExecuters.entrySet()) {
                    SumNode groupField = (SumNode)((Object)entry.getKey());
                    List list = (List)entry.getValue();
                    CellsSumExecuter cellsSumExecuter = (CellsSumExecuter)list.get(0);
                    GroupCellsSumExecuter groupCellsSumExecuter = new GroupCellsSumExecuter();
                    groupCellsSumExecuter.setGroupField(groupField);
                    groupCellsSumExecuter.getExecuters().addAll(list);
                    if (noKeyFilterExecuters.size() > 0) {
                        groupCellsSumExecuter.getExecuters().addAll(noKeyFilterExecuters);
                        noKeyFilterExecuters.clear();
                    }
                    groupCellsSumExecuter.setBaseDefineFactory(this.baseDefineFactory);
                    groupCellsSumExecuter.setDataAccessProvider(this.dataAccessProvider);
                    groupCellsSumExecuter.setDataSource(this.dataSource);
                    groupCellsSumExecuter.setExecutorContext(executorContext);
                    SumExeInfo exeInfo = new SumExeInfo();
                    exeInfo.setSrcDimension(this.srcDimension);
                    exeInfo.setDestDimension(this.destDimension);
                    groupCellsSumExecuter.setExeInfo(exeInfo);
                    exeInfo.setFilter(cellsSumExecuter.getExeInfo().getFilter());
                    groupCellsSumExecuter.setTempAssistantTables(tempAssistantTables);
                    groupCellsSumExecuter.setParser(this.parser);
                    executers.add(groupCellsSumExecuter);
                }
            }
        }
        return executers;
    }

    private void judgeStrategy() {
        boolean hasRowCondition = false;
        boolean hasColCondition = false;
        boolean unknownSumType = false;
        for (SumCell cell : this.cells) {
            if (!StringUtils.isEmpty((String)cell.getRowCondition())) {
                hasRowCondition = true;
            }
            if (StringUtils.isEmpty((String)cell.getColCondition())) {
                hasColCondition = true;
            }
            if (cell.getSumType() == SumEngineConsts.SumType.UNKNOWN.getValue()) {
                unknownSumType = true;
            }
            if (cell.getSumType() != SumEngineConsts.SumType.UNKNOWN.getValue() && cell.getSumType() <= SumEngineConsts.SumType.MIN.getValue()) continue;
            this.allSum = false;
        }
        if (hasColCondition && !hasRowCondition) {
            this.sum_strategy = 3;
        } else if (hasColCondition && hasRowCondition && unknownSumType) {
            this.sum_strategy = 1;
        }
    }

    private Map<String, TempAssistantTable> tryCreateTempTables(IDatabase database, ExecutorContext executorContext, TempResource tempResource) throws ParseException {
        HashMap<String, TempAssistantTable> tempAssistantTables = null;
        for (int i = 0; i < this.srcDimension.size(); ++i) {
            int dataType;
            List values;
            String dimName;
            TempAssistantTable tempAssistantTable;
            Object dimValue = this.srcDimension.getValue(i);
            if (!(dimValue instanceof List) || (tempAssistantTable = tempResource.getTempAssistantTable(database, dimName = this.srcDimension.getName(i), values = (List)dimValue, dataType = this.getDimDataType(executorContext, dimName))) == null) continue;
            if (tempAssistantTables == null) {
                tempAssistantTables = new HashMap<String, TempAssistantTable>();
            }
            tempAssistantTables.put(dimName, tempAssistantTable);
        }
        return tempAssistantTables;
    }

    private int getDimDataType(ExecutorContext executorContext, String dimName) throws ParseException {
        int dataType = 33;
        DataModelDefinitionsCache dataDefinitionsCache = executorContext.getCache().getDataModelDefinitionsCache();
        ColumnModelDefine dimensionField = null;
        for (SumCell cell : this.cells) {
            String tableName;
            TableModelRunInfo tableInfo;
            if (cell.getSrcField() != null && (dimensionField = (tableInfo = dataDefinitionsCache.getTableInfo(tableName = dataDefinitionsCache.getTableName(cell.getSrcField()))).getDimensionField(dimName)) != null) break;
        }
        if (dimensionField != null) {
            dataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType());
        }
        return dataType;
    }

    private void doSumBySerialStrategy(List<CellsSumExecuter> executers, IDataUpdator updator, IDataRow row) throws Exception {
        for (int i = 0; i < executers.size(); ++i) {
            CellsSumExecuter executer = executers.get(i);
            executer.setRow(row);
            try {
                executer.run();
                continue;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        updator.commitChanges(true);
    }

    private void doSumByParallelStrategy(List<CellsSumExecuter> executers) {
        CellsSumExecuter executer;
        HashSet<CellsSumExecuter> executings = new HashSet<CellsSumExecuter>();
        int nextIndex = 0;
        for (int i = 0; i < this.parallelSize && i < executers.size(); ++i) {
            executer = executers.get(i);
            executer.start();
            executings.add(executer);
            ++nextIndex;
        }
        while (executings.size() > 0) {
            this.threadSleep();
            Iterator it = executings.iterator();
            while (it.hasNext()) {
                executer = (CellsSumExecuter)it.next();
                if (executer.getState() != Thread.State.TERMINATED && executer.getState() != Thread.State.BLOCKED) continue;
                it.remove();
            }
            while (nextIndex < executers.size() && executings.size() < this.parallelSize) {
                executer = executers.get(nextIndex);
                executer.start();
                executings.add(executer);
                ++nextIndex;
            }
        }
    }

    private List<CellsSumExecuter> splitCells(ExecutorContext executorContext, FieldDefine orderField, IDataUpdator updator, Map<String, TempAssistantTable> tempAssistantTables) {
        HashMap<String, CellsSumExecuter> executerMap = new HashMap<String, CellsSumExecuter>();
        for (SumCell cell : this.cells) {
            String colFilter = null;
            String key = null;
            if (this.sum_strategy == 2) {
                key = "R" + cell.getRow();
                if (StringUtils.isNotEmpty((String)cell.getRowCondition())) {
                    colFilter = "(" + cell.getRowCondition() + ")";
                }
                cell.setRowCondition(null);
            } else if (this.sum_strategy == 3) {
                key = "C" + cell.getCol();
                if (StringUtils.isNotEmpty((String)cell.getColCondition())) {
                    colFilter = "(" + cell.getColCondition() + ")";
                }
                cell.setColCondition(null);
            } else if (this.sum_strategy == 1) {
                key = cell.getAlias();
                colFilter = cell.getCondition();
                cell.setRowCondition(null);
                cell.setColCondition(null);
            }
            CellsSumExecuter executer = (CellsSumExecuter)executerMap.get(key);
            if (executer == null) {
                executer = this.sum_strategy == 2 || this.sum_strategy == 3 ? this.createParallelExecuter(executorContext, null, orderField, updator, tempAssistantTables) : this.createParallelExecuter(executorContext, colFilter, orderField, updator, tempAssistantTables);
                executer.setCondition(colFilter);
                executer.setKey(key);
                executerMap.put(key, executer);
            }
            executer.getCells().add(cell);
        }
        return new ArrayList<CellsSumExecuter>(executerMap.values());
    }

    private CellsSumExecuter createParallelExecuter(ExecutorContext executorContext, String colFilter, FieldDefine orderField, IDataUpdator updator, Map<String, TempAssistantTable> tempAssistantTables) {
        CellsSumExecuter executer = new CellsSumExecuter();
        executer.setBaseDefineFactory(this.baseDefineFactory);
        executer.setDataSource(this.dataSource);
        executer.setExecutorContext(executorContext);
        SumExeInfo exeInfo = new SumExeInfo();
        exeInfo.setSrcDimension(this.srcDimension);
        exeInfo.setDestDimension(this.destDimension);
        exeInfo.setOrderField(orderField);
        executer.setExeInfo(exeInfo);
        executer.setUpdator(updator);
        executer.setDataAccessProvider(this.dataAccessProvider);
        executer.setTempAssistantTables(tempAssistantTables);
        executer.setParser(this.parser);
        String filter = colFilter;
        if (StringUtils.isNotEmpty((String)colFilter)) {
            if (StringUtils.isNotEmpty((String)this.fmlFilter)) {
                filter = colFilter + " and (" + this.fmlFilter + ")";
            }
        } else {
            filter = this.fmlFilter;
        }
        exeInfo.setFilter(filter);
        return executer;
    }

    private void createTempTables(Connection connection, Map<String, TempAssistantTable> tempAssistantTables) throws Exception {
        if (tempAssistantTables == null || tempAssistantTables.size() <= 0) {
            return;
        }
        for (TempAssistantTable tempAssistantTable : tempAssistantTables.values()) {
            tempAssistantTable.createTempTable(connection);
            tempAssistantTable.insertIntoTempTable(connection);
        }
    }

    private void dropTempTables(Map<String, TempAssistantTable> tempAssistantTables) {
        if (tempAssistantTables == null || tempAssistantTables.size() <= 0) {
            return;
        }
        for (TempAssistantTable tempAssistantTable : tempAssistantTables.values()) {
            try {
                tempAssistantTable.close();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private ReportFormulaParser createFormulaParser(ISumBaseDefineFactory baseDefineFactory) {
        ReportFormulaParser parser = new ReportFormulaParser();
        FunctionProvider SumFunProvider = new FunctionProvider();
        SumFunProvider.add((IFunction)new SUMFunction());
        SumFunProvider.add((IFunction)new COUNTFunction());
        SumFunProvider.add((IFunction)new MINFunction());
        SumFunProvider.add((IFunction)new MAXFunction());
        SumFunProvider.add((IFunction)new InCollectionFunction());
        SumFunProvider.add((IFunction)new InList());
        SumFunProvider.add((IFunction)new StartWithFunction());
        parser.registerFunctionProvider((IFunctionProvider)SumFunProvider);
        SumNodeProvider nodeProvider = new SumNodeProvider();
        parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)nodeProvider);
        return parser;
    }

    private void threadSleep() {
        try {
            Thread.sleep(200L);
        }
        catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<SumCell> getCells() {
        return this.cells;
    }

    @Override
    public void setFmlFilter(String fmlFilter) {
        this.fmlFilter = fmlFilter;
    }

    public ISumBaseDefineFactory getBaseDefineFactory() {
        return this.baseDefineFactory;
    }

    public void setBaseDefineFactory(ISumBaseDefineFactory baseDefineFactory) {
        this.baseDefineFactory = baseDefineFactory;
    }

    @Override
    public void setSrcDimension(DimensionValueSet srcDimension) {
        this.srcDimension = srcDimension;
    }

    @Override
    public void setDestDimension(DimensionValueSet destDimension) {
        this.destDimension = destDimension;
    }

    @Override
    public void setParallelSize(int parallelSize) {
        this.parallelSize = parallelSize;
    }
}


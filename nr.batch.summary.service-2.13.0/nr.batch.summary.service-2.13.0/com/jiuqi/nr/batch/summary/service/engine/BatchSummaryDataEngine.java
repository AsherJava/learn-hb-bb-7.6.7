/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.nr.batch.summary.common.StringLogger;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDeleteSqlBuilder;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntityData;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableInsertSqlBuilder;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableSelectSqlBuilder;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBSaver;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummarySqlBuilder;
import com.jiuqi.nr.batch.summary.service.engine.TempTableProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class BatchSummaryDataEngine {
    private final StringLogger logger;
    private final ITableDBUtil tableDBUtil;
    protected final SummaryScheme summaryScheme;
    protected final TargetDimProvider targetDimProvider;
    private final TargetFromProvider targetFromProvider;
    private final BatchSummaryNrDBSaver nrDBSaver;

    public BatchSummaryDataEngine(SummaryScheme summaryScheme, TargetFromProvider targetFromProvider, TargetDimProvider targetDimProvider, ITableDBUtil tableDBUtil, BatchSummaryNrDBSaver nrDBSaver, StringLogger logger) {
        this.logger = logger;
        this.tableDBUtil = tableDBUtil;
        this.summaryScheme = summaryScheme;
        this.targetDimProvider = targetDimProvider;
        this.targetFromProvider = targetFromProvider;
        this.nrDBSaver = nrDBSaver;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(TempTableProvider tempTableProvider, List<String> periods) {
        this.logger.logInfo("\u5f00\u59cb\u6267\u884c...");
        this.logger.logInfo("\u6c47\u603b\u65b9\u6848\uff1a" + this.summaryScheme.getTitle() + "[" + this.summaryScheme.getCode() + "]");
        if (periods.isEmpty()) {
            this.logger.logError("\u6ca1\u6709\u53ef\u4ee5\u6c47\u603b\u7684\u65f6\u671f\u8303\u56f4\uff01\u65e0\u6cd5\u8fdb\u884c\u6c47\u603b\uff01");
            return;
        }
        Connection connection = null;
        ITableEntity tempTable = null;
        double progress = 0.0;
        try {
            connection = this.tableDBUtil.createConnection();
            for (String period : periods) {
                if (this.targetDimProvider.isEmptyTargetDim(period)) {
                    this.logger.logError("\u76ee\u6807\u7eac\u5ea6\u4e3a\u7a7a\uff01\u65e0\u6cd5\u8fdb\u884c\u6c47\u603b\uff01");
                    return;
                }
                if (tempTable == null) {
                    this.logger.logInfo("\u6b63\u5728\u751f\u6210\u6c47\u603b\u6240\u9700\u7684\u4e34\u65f6\u8868...");
                    tempTable = tempTableProvider.createTempTable(connection, this.summaryScheme);
                    progress = 100.0 / (double)periods.size();
                }
                this.logger.logInfo("\u65f6\u671f\uff1a\u3010" + period + "\u3011");
                List<FormDefine> rangeForms = this.targetFromProvider.getRangeForms(period);
                if (rangeForms.isEmpty()) {
                    this.logger.logWarn("\u8be5\u65f6\u671f[" + period + "]\u4e0b\u6ca1\u6709\u53ef\u6c47\u603b\u7684\u62a5\u8868\uff0c\u8df3\u8fc7\u6267\u884c\uff01\uff01\uff01").addProcess(progress);
                    continue;
                }
                progress /= (double)rangeForms.size();
                this.logger.logInfo("\u6b63\u5728\u8ba1\u7b97\u53c2\u4e0e\u6c47\u603b\u7684\u7269\u7406\u8868\u8303\u56f4...");
                List<OriTableModelInfo> summaryTableModels = this.targetFromProvider.getRangeFormTables(rangeForms, period);
                if (summaryTableModels.isEmpty()) {
                    this.logger.logWarn("\u6ca1\u6709\u53ef\u4ee5\u6267\u884c\u6c47\u603b\u7684\u7269\u7406\u8868\u8303\u56f4\uff0c\u8df3\u8fc7\u6267\u884c\uff01\uff01\uff01").addProcess(progress);
                    continue;
                }
                this.logger.logInfo("\u603b\u5171\u9700\u8981\u64cd\u4f5c" + summaryTableModels.size() + "\u5f20\u7269\u7406\u8868");
                progress /= (double)summaryTableModels.size();
                this.logger.logInfo("\u6b63\u5728\u5f80\u4e34\u65f6\u8868\u4e2d\u5199\u5165\u660e\u7ec6\u5355\u4f4d\u6570\u636e...");
                tempTableProvider.insertTempTableData(connection, tempTable, this.targetDimProvider, this.summaryScheme, period);
                for (OriTableModelInfo oriTableModel : summaryTableModels) {
                    SumTableModelInfo sumTableModel = this.targetFromProvider.getSumTableModel(oriTableModel, period);
                    this.logger.logInfo("\u6b63\u5728\u6c47\u603b\u8868\u6570\u636e...");
                    this.logger.logInfo("\u6765\u6e90\u8868\uff1a\u3010" + oriTableModel.getTableModel().getName() + "\u3011");
                    this.logger.logInfo("\u6c47\u603b\u8868\uff1a\u3010" + sumTableModel.getTableModel().getName() + "\u3011");
                    if (!oriTableModel.isSimpleTable() && oriTableModel.getBizKeyColumns().isEmpty()) {
                        this.logger.logInfo(oriTableModel.getTableModel().getTitle() + "[" + oriTableModel.getTableModel().getName() + "]\u660e\u7ec6\u8868\u6ca1\u6709\u8868\u5185\u6c47\u603b\u5b57\u6bb5\uff0c\u4e0d\u6c47\u603b!!!");
                        continue;
                    }
                    this.sumTableData(connection, tempTable, oriTableModel, sumTableModel, period, progress);
                }
                tempTableProvider.clearTempTableData(connection, tempTable);
            }
        }
        catch (Exception e) {
            this.logger.logError(e.getMessage(), e);
            e.printStackTrace();
        }
        finally {
            if (tempTable != null) {
                try {
                    tempTable.getTempTableDefine().close();
                }
                catch (IOException e) {
                    this.logger.logError(e.getMessage(), e);
                }
            }
            this.tableDBUtil.releaseConnection(connection);
            this.logger.logInfo("\u6c47\u603b\u5b8c\u6210\uff01").addProcess(100.0);
        }
    }

    private void sumTableData(Connection connection, ITableEntity tempTable, OriTableModelInfo oriTableModel, SumTableModelInfo sumTableModel, String period, double progress) throws Exception {
        if (this.nrDBSaver.isEnableNrdb()) {
            this.nrDBSaver.sumTableDataWithNrDB(connection, tempTable, oriTableModel, sumTableModel, period, progress);
        } else {
            this.sumTableDataWithSQL(connection, tempTable, oriTableModel, sumTableModel, period, progress);
        }
    }

    private void sumTableDataWithSQL(Connection connection, ITableEntity tempTable, OriTableModelInfo oriTableModel, SumTableModelInfo sumTableModel, String period, double progress) {
        this.logger.logInfo("\u6b63\u5728\u6e05\u7a7a\u5386\u53f2\u6570\u636e...");
        ITableDeleteSqlBuilder deleteSqlBuilder = this.makeClearTableDataSql(sumTableModel, period);
        this.tableDBUtil.implementSQL(connection, deleteSqlBuilder.toString());
        this.logger.addProcess(progress / 3.0);
        this.logger.logInfo("\u6b63\u5728\u6267\u884c\u6570\u636e\u6c47\u603b...");
        BatchSummarySqlBuilder summarySqlBuilder = this.makeSummarySQL(tempTable, oriTableModel, sumTableModel, period);
        ITableSelectSqlBuilder selectSqlBuilder = summarySqlBuilder.getSelectSummaryBuilder();
        List<String> insertColumns = summarySqlBuilder.getQuerySelectColumns();
        this.logger.addProcess(progress / 3.0);
        this.logger.logInfo("\u6b63\u5728\u4fdd\u5b58\u6c47\u603b\u6570\u636e...");
        if (oriTableModel.isSimpleTable()) {
            ITableInsertSqlBuilder insertSqlBuilder = new ITableInsertSqlBuilder(sumTableModel.getTableName());
            insertSqlBuilder.addInsertColumns(insertColumns);
            insertSqlBuilder.concat(selectSqlBuilder);
            this.tableDBUtil.implementSQL(connection, insertSqlBuilder.toString());
            this.logger.addProcess(progress / 3.0);
        } else {
            ITableEntityData sumTableData = this.tableDBUtil.selectSQLImplement(connection, summarySqlBuilder);
            this.tableDBUtil.insertTableData(connection, sumTableData, sumTableModel.getTableName(), insertColumns);
        }
    }

    protected BatchSummarySqlBuilder makeSummarySQL(ITableEntity tempTable, OriTableModelInfo oriTableModel, SumTableModelInfo sumTableModel, String period) {
        BatchSummarySqlBuilder sumSqlBuilder = new BatchSummarySqlBuilder(this.summaryScheme);
        sumSqlBuilder.appendGatherColumn(sumTableModel.getGatherColumn());
        sumSqlBuilder.appendDWColumn(oriTableModel.getDWColumn(), tempTable.getTableName());
        sumSqlBuilder.appendPeriodColumn(oriTableModel.getPeriodColumn(), oriTableModel.getTableName());
        sumSqlBuilder.appendSituationColumns(oriTableModel.getSituationColumns(), oriTableModel.getTableName());
        sumSqlBuilder.appendBizKeyColumns(oriTableModel.getBizKeyColumns(), oriTableModel.getTableName());
        sumSqlBuilder.appendBuildColumns(oriTableModel.getBuildColumns(), oriTableModel.getTableName());
        sumSqlBuilder.appendZBColumns(oriTableModel.getZBColumns(), oriTableModel.getTableName());
        sumSqlBuilder.fromTable(oriTableModel.getTableName());
        sumSqlBuilder.rightJoinTable(tempTable.getTableName());
        sumSqlBuilder.joinOnCondition(tempTable, oriTableModel);
        sumSqlBuilder.whereCondition(oriTableModel, period);
        sumSqlBuilder.groupCondition(tempTable, oriTableModel);
        sumSqlBuilder.end();
        return sumSqlBuilder;
    }

    protected ITableDeleteSqlBuilder makeClearTableDataSql(SumTableModelInfo sumTableModel, String period) {
        String sumTableName = sumTableModel.getTableModel().getCode();
        String gatherColumnCode = sumTableModel.getGatherColumn().getColumnModel().getCode();
        String periodColumnCode = sumTableModel.getPeriodColumn().getColumnModel().getCode();
        ITableDeleteSqlBuilder sqlBuilder = new ITableDeleteSqlBuilder(sumTableName);
        sqlBuilder.where(gatherColumnCode, this.summaryScheme.getKey());
        sqlBuilder.andWhereCondition(periodColumnCode, period);
        sqlBuilder.end();
        return sqlBuilder;
    }
}


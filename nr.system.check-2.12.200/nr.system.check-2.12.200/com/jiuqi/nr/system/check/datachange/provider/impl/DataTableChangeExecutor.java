/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.system.check.datachange.provider.impl;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.system.check.common.DBUtils;
import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import com.jiuqi.nr.system.check.datachange.bean.RepairParam;
import com.jiuqi.nr.system.check.datachange.provider.DataChangeExecutor;
import com.jiuqi.nr.system.check.datachange.util.DwUpper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataTableChangeExecutor
implements DataChangeExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DataTableChangeExecutor.class);
    @Autowired
    private RuntimeViewController runtimeView;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SplitTableHelper splitTableHelper;
    private final List<Integer> dataTableType = new ArrayList<Integer>(Arrays.asList(DataTableType.TABLE.getValue(), DataTableType.MD_INFO.getValue(), DataTableType.DETAIL.getValue()));
    private static final String MDCODE = "MDCODE";
    private static final String DATATIME = "DATATIME";
    private static final String selectSql = "SELECT %s,%s FROM %s WHERE %s GROUP BY %s,%s";
    private static final String updateSql = "UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?";

    @Override
    public float getOrder() {
        return 5.0f;
    }

    @Override
    public String getExecutorType() {
        return "dataTable";
    }

    @Override
    public List<DataChangeRecord> execute(RepairParam repairParam) {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext exeContext = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment((IRunTimeViewController)this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, null);
        env.setDataScehmeKey(repairParam.getDataSchemeKey());
        exeContext.setEnv((IFmlExecEnvironment)env);
        String varDataScheme = "NR.var.dataScheme";
        VariableManager variableManager = exeContext.getVariableManager();
        variableManager.add(new Variable(varDataScheme, varDataScheme, 6, (Object)repairParam.getDataSchemeKey()));
        HashSet result = new HashSet();
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(repairParam.getDataSchemeKey());
        DwUpper dwUpper = new DwUpper(this.jdbcTemplate);
        long startTime = System.currentTimeMillis();
        logger.info("\u6570\u636e\u65b9\u6848{}\u6570\u636e\u8868\u5f00\u59cb\u6267\u884c\u5355\u4f4d\u8f6c\u5927\u5199\u3002", (Object)dataScheme.getTitle());
        List allDataTable = this.dataSchemeService.getAllDataTable(dataScheme.getKey());
        int threadNumber = repairParam.getThreadNumber();
        if (threadNumber <= 0) {
            threadNumber = 1;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        for (DataTable dataTable : allDataTable) {
            if (!this.dataTableType.contains(dataTable.getDataTableType().getValue())) continue;
            executorService.submit(() -> this.lambda$execute$1(dataTable, (ExecutorContext)exeContext, result, dwUpper));
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10L, TimeUnit.HOURS)) {
                executorService.shutdownNow();
            }
        }
        catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        long endTime = System.currentTimeMillis();
        logger.info("\u6570\u636e\u65b9\u6848{}\u6570\u636e\u8868\u6267\u884c\u5355\u4f4d\u8f6c\u5927\u5199\u5b8c\u6210\uff0c\u5171\u8017\u65f6{}\u79d2", (Object)dataScheme.getTitle(), (Object)((endTime - startTime) / 1000L));
        return new ArrayList<DataChangeRecord>(result);
    }

    private /* synthetic */ void lambda$execute$1(DataTable dataTable, ExecutorContext exeContext, Set result, DwUpper dwUpper) {
        DataField unitField = this.dataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), MDCODE);
        List deployInfo = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{unitField.getKey()});
        Map<String, DataFieldDeployInfo> collect = deployInfo.stream().collect(Collectors.toMap(DataFieldDeployInfo::getTableName, a -> a));
        for (String tableName : collect.keySet()) {
            String currentSplitTableName = this.splitTableHelper.getCurrentSplitTableName(exeContext, tableName);
            String select = String.format(selectSql, MDCODE, DATATIME, currentSplitTableName, DBUtils.buildCondition(MDCODE), MDCODE, DATATIME);
            String update = String.format(updateSql, currentSplitTableName, MDCODE, MDCODE, DATATIME);
            try {
                result.addAll(dwUpper.doExec(select, update, null));
                logger.info("\u5b58\u50a8\u8868{}\u5355\u4f4d\u8f6c\u5927\u5199\u6210\u529f", (Object)currentSplitTableName);
            }
            catch (Exception e) {
                logger.error("\u5b58\u50a8\u8868" + currentSplitTableName + "\u5355\u4f4d\u8f6c\u5927\u5199\u5931\u8d25\uff1a" + e.getMessage(), e);
            }
        }
    }
}


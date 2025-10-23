/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.summary.executor.deploy;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.executor.deploy.AbstractExecutor;
import com.jiuqi.nr.summary.executor.deploy.DataFieldExecutor;
import com.jiuqi.nr.summary.executor.deploy.DeployContext;
import com.jiuqi.nr.summary.executor.deploy.comparator.ComparatorHolder;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryReportDTO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryReportService;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.utils.ParamComparator;
import com.jiuqi.nr.summary.utils.SummaryReportModelHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

class DataTableExecutor
extends AbstractExecutor {
    private final AbstractExecutor dataFieldExecutor;
    private final IDesignDataSchemeService iDesignDataSchemeService;
    private final IDesignSummaryReportService designReportService;
    private final IRuntimeSummaryReportService runtimeReportService;
    private SummaryReportModelHelper reportModelHelper;

    public DataTableExecutor(DeployContext context, AsyncTaskMonitor monitor, IDesignDataSchemeService iDesignDataSchemeService, IDesignSummaryReportService designReportService, IRuntimeSummaryReportService runtimeReportService) {
        super(context, monitor);
        this.dataFieldExecutor = new DataFieldExecutor(context, monitor, iDesignDataSchemeService);
        this.iDesignDataSchemeService = iDesignDataSchemeService;
        this.designReportService = designReportService;
        this.runtimeReportService = runtimeReportService;
    }

    @Override
    public boolean execute() {
        this.reportModelHelper = new SummaryReportModelHelper(this.context.getDesignReportModel());
        this.floatTableProcess();
        this.fixTableProcess();
        return true;
    }

    private void floatTableProcess() {
        ParamComparator<SummaryFloatRegion, DesignDataTable> floatTableComparator = this.processFloatDiff();
        List<DesignDataTable> deleteList = floatTableComparator.getDeleteList();
        List<SummaryFloatRegion> modifyList = floatTableComparator.getModifyList();
        List<SummaryFloatRegion> insertList = floatTableComparator.getInsertList();
        if (!CollectionUtils.isEmpty(deleteList)) {
            Iterator<SummaryFloatRegion> deleteTitles = new ArrayList();
            ArrayList deleteKeys = new ArrayList();
            deleteList.forEach(dataTable -> {
                deleteTitles.add((SummaryFloatRegion)((Object)dataTable.getTitle()));
                deleteKeys.add(dataTable.getKey());
            });
            this.iDesignDataSchemeService.deleteDataTables(deleteKeys);
            this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6570\u636e\u65b9\u6848:\u5220\u9664\u6570\u636e\u8868\u3010%s\u3011", String.join((CharSequence)",", deleteTitles));
        }
        for (SummaryFloatRegion floatRegion : modifyList) {
            this.floatRegionProcess(floatRegion);
        }
        for (SummaryFloatRegion floatRegion : insertList) {
            this.floatRegionProcess(floatRegion);
        }
        SummaryReportModel designReportModel = this.context.getDesignReportModel();
        List<SummaryFloatRegion> floatRegions = Objects.requireNonNull(designReportModel).getConfig().getRegions();
        if (!CollectionUtils.isEmpty(floatRegions)) {
            Map<Integer, String> dataTableMap = this.context.getDataTableMap();
            boolean change = false;
            ArrayList<String> tableNames = new ArrayList<String>(dataTableMap.values());
            for (SummaryFloatRegion region : floatRegions) {
                if (StringUtils.hasLength(region.getTableName())) continue;
                String tableName = dataTableMap.get(region.getPosition());
                List matchTableNames = tableNames.stream().filter(tableName::equals).collect(Collectors.toList());
                if (matchTableNames.size() > 1) {
                    Collections.sort(tableNames);
                    String sortedLastTableName = (String)tableNames.get(tableNames.size() - 1);
                    String tableNameIndex = sortedLastTableName.substring(sortedLastTableName.lastIndexOf("F") + 1);
                    int newTableNameIndex = Integer.parseInt(tableNameIndex) + 1;
                    tableName = tableName.substring(0, tableName.lastIndexOf("F") + 1) + String.valueOf(newTableNameIndex);
                    dataTableMap.put(region.getPosition(), tableName);
                }
                region.setTableName(tableName);
                change = true;
            }
            if (change) {
                DesignSummaryReportDTO reportDTO = Convert.designSummaryReportModelConvert.VO2DTO(designReportModel);
                reportDTO.setOrder(this.context.getCurrReport().getOrder());
                reportDTO.setSummarySolutionKey(Objects.requireNonNull(this.context.getSolutionModel()).getKey());
                try {
                    this.designReportService.updateSummaryReport(reportDTO, false);
                    this.runtimeReportService.updateSummaryReport(reportDTO, false);
                }
                catch (SummaryCommonException e) {
                    this.error((Throwable)((Object)e));
                }
            }
        }
    }

    private ParamComparator<SummaryFloatRegion, DesignDataTable> processFloatDiff() {
        List<SummaryFloatRegion> designRegions = Objects.requireNonNull(this.context.getDesignReportModel()).getConfig().getRegions();
        List designDataTables = this.iDesignDataSchemeService.getDataTableByGroup(this.context.getCurrDataGroup().getKey());
        designDataTables = designDataTables.stream().filter(dataTable -> dataTable.getDataTableType().equals((Object)DataTableType.DETAIL)).collect(Collectors.toList());
        ParamComparator<SummaryFloatRegion, DesignDataTable> floatRegionComparator = ComparatorHolder.getFloatRegionComparator(this.context);
        floatRegionComparator.processDiff(designRegions, designDataTables);
        return floatRegionComparator;
    }

    private void floatRegionProcess(SummaryFloatRegion floatRegion) {
        String floatTableName = this.reportModelHelper.generateFloatTableCode(floatRegion);
        String floatTableTitle = this.reportModelHelper.generateFloatTableTitle(floatRegion);
        String tableKey = this.createOrUpdateDataTable(false, floatTableName, floatTableTitle);
        DesignDataTable dataTable = this.iDesignDataSchemeService.getDataTable(tableKey);
        this.context.setCurrDataTable((DataTable)dataTable);
        this.context.setCurrentFloatRegion(floatRegion);
        this.context.setCurrentRegionDataCells(this.reportModelHelper.getFloatDataCells(floatRegion));
        this.context.putDataTableMap(floatRegion.getPosition(), floatTableName);
        this.dataFieldExecutor.execute();
    }

    private void fixTableProcess() {
        if (this.reportModelHelper.hasFixCell()) {
            SummaryReportModel designReportModel = this.context.getDesignReportModel();
            String tableName = this.reportModelHelper.generateFixTableCode();
            String tableTitle = this.reportModelHelper.generateFixTableTitle();
            String tableKey = this.createOrUpdateDataTable(true, tableName, tableTitle);
            DesignDataTable dataTable = this.iDesignDataSchemeService.getDataTable(tableKey);
            SummaryReportModelHelper reportModelHelper = new SummaryReportModelHelper(designReportModel);
            this.context.setCurrDataTable((DataTable)dataTable);
            this.context.setCurrentFloatRegion(null);
            this.context.setCurrentRegionDataCells(reportModelHelper.getFixDataCells());
            this.context.putDataTableMap(-1, tableName);
            this.dataFieldExecutor.execute();
        }
    }

    private String createOrUpdateDataTable(boolean fix, String tableCode, String tableTitle) {
        DataScheme currDataScheme = this.context.getDataScheme();
        DataGroup currDataGroup = this.context.getCurrDataGroup();
        DesignDataTable designDataTable = this.iDesignDataSchemeService.getDataTableByCode(tableCode);
        boolean update = true;
        if (designDataTable == null) {
            designDataTable = this.iDesignDataSchemeService.initDataTable();
            update = false;
        }
        designDataTable.setDataSchemeKey(currDataScheme.getKey());
        designDataTable.setDataGroupKey(currDataGroup.getKey());
        designDataTable.setCode(tableCode);
        designDataTable.setTitle(tableTitle);
        if (fix) {
            designDataTable.setDataTableType(DataTableType.TABLE);
        } else {
            designDataTable.setDataTableType(DataTableType.DETAIL);
        }
        designDataTable.setRepeatCode(Boolean.valueOf(true));
        designDataTable.setDataTableGatherType(DataTableGatherType.CLASSIFY);
        designDataTable.setOwner("SR");
        designDataTable.setOrder(OrderGenerator.newOrder());
        designDataTable.setDesc("\u63cf\u8ff0");
        if (update) {
            this.iDesignDataSchemeService.updateDataTable(designDataTable);
            this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6570\u636e\u65b9\u6848:\u4fee\u6539\u6570\u636e\u8868\u3010%s\u3011", designDataTable.getTitle());
        } else {
            this.iDesignDataSchemeService.insertDataTable(designDataTable);
            this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6570\u636e\u65b9\u6848:\u521b\u5efa\u6570\u636e\u8868\u3010%s\u3011", designDataTable.getTitle());
        }
        return designDataTable.getKey();
    }
}


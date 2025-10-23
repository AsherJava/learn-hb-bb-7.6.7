/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.summary.executor.deploy;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.summary.executor.deploy.AbstractExecutor;
import com.jiuqi.nr.summary.executor.deploy.DeployContext;
import com.jiuqi.nr.summary.executor.deploy.comparator.ComparatorHolder;
import com.jiuqi.nr.summary.executor.deploy.comparator.TemporaryCell;
import com.jiuqi.nr.summary.model.caliber.CaliberApplyType;
import com.jiuqi.nr.summary.model.caliber.CaliberFloatInfo;
import com.jiuqi.nr.summary.model.caliber.CaliberInfo;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.MainCell;
import com.jiuqi.nr.summary.model.cell.SummaryZb;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.utils.ParamComparator;
import com.jiuqi.nr.summary.utils.SummaryReportModelHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

class DataFieldExecutor
extends AbstractExecutor {
    private static final int BATCH_SIZE = 1000;
    private final IDesignDataSchemeService iDesignDataSchemeService;

    public DataFieldExecutor(DeployContext context, AsyncTaskMonitor monitor, IDesignDataSchemeService iDesignDataSchemeService) {
        super(context, monitor);
        this.iDesignDataSchemeService = iDesignDataSchemeService;
    }

    @Override
    public boolean execute() {
        ParamComparator<TemporaryCell, DesignDataField> cellParamComparator = this.processCellDiff();
        List<DesignDataField> deleteList = cellParamComparator.getDeleteList();
        List<TemporaryCell> modifyList = cellParamComparator.getModifyList();
        List<TemporaryCell> insertList = cellParamComparator.getInsertList();
        if (!CollectionUtils.isEmpty(deleteList)) {
            ArrayList deleteKeys = new ArrayList();
            deleteList.forEach(dataField -> deleteKeys.add(dataField.getKey()));
            this.iDesignDataSchemeService.deleteDataFields(deleteKeys);
            this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6570\u636e\u65b9\u6848:\u5220\u9664\u4e86\u3010%s\u3011\u4e2a\u5b57\u6bb5", String.valueOf(deleteList.size()));
        }
        this.batchUpdateCell(modifyList);
        this.batchInsertCell(insertList);
        return true;
    }

    private void batchUpdateCell(List<TemporaryCell> modifyList) {
        int modifySize = modifyList.size();
        int m = modifySize / 1000;
        int n = modifySize % 1000;
        int groupCount = n == 0 ? m : m + 1;
        for (int i = 0; i < groupCount; ++i) {
            List<Object> subCells = new ArrayList();
            subCells = (i + 1) * 1000 <= modifySize ? modifyList.subList(i * 1000, (i + 1) * 1000) : modifyList.subList(i * 1000, i * 1000 + n);
            this.createOrUpdateField(subCells, true);
        }
        this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6570\u636e\u65b9\u6848:\u4fee\u6539\u4e86\u3010%s\u3011\u4e2a\u5b57\u6bb5", String.valueOf(modifyList.size()));
    }

    private void batchInsertCell(List<TemporaryCell> insertList) {
        int insertSize = insertList.size();
        int m = insertSize / 1000;
        int n = insertSize % 1000;
        int groupCount = n == 0 ? m : m + 1;
        for (int i = 0; i < groupCount; ++i) {
            List<Object> subCells = new ArrayList();
            subCells = (i + 1) * 1000 <= insertSize ? insertList.subList(i * 1000, (i + 1) * 1000) : insertList.subList(i * 1000, i * 1000 + n);
            this.createOrUpdateField(subCells, false);
        }
        this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6570\u636e\u65b9\u6848:\u521b\u5efa\u4e86\u3010%s\u3011\u4e2a\u5b57\u6bb5", String.valueOf(insertList.size()));
    }

    private ParamComparator<TemporaryCell, DesignDataField> processCellDiff() {
        List<TemporaryCell> cells = this.buildTempCells();
        String tableName = this.context.getCurrDataTable().getCode();
        List designDataFields = this.iDesignDataSchemeService.getDataFieldByTableCode(tableName);
        designDataFields = designDataFields.stream().filter(dataField -> {
            DataFieldKind dataFieldKind = dataField.getDataFieldKind();
            return dataFieldKind.equals((Object)DataFieldKind.FIELD) || dataFieldKind.equals((Object)DataFieldKind.FIELD_ZB) || dataFieldKind.equals((Object)DataFieldKind.TABLE_FIELD_DIM);
        }).collect(Collectors.toList());
        ParamComparator<TemporaryCell, DesignDataField> cellParamComparator = ComparatorHolder.getCellComparator();
        cellParamComparator.processDiff(cells, designDataFields);
        return cellParamComparator;
    }

    private List<TemporaryCell> buildTempCells() {
        ArrayList<TemporaryCell> tempCells = new ArrayList<TemporaryCell>();
        List<DataCell> currentRegionDataCells = this.context.getCurrentRegionDataCells();
        if (this.isFloatTable()) {
            List<TemporaryCell> innerDimCells = this.getInnerDimFields();
            tempCells.addAll(innerDimCells);
        }
        SummaryFloatRegion currentFloatRegion = this.context.getCurrentFloatRegion();
        for (DataCell dataCell : currentRegionDataCells) {
            TemporaryCell cell = new TemporaryCell();
            cell.key = dataCell.getKey();
            cell.code = dataCell.getSummaryZb().getName();
            cell.title = dataCell.getSummaryZb().getTitle();
            cell.fieldKey = dataCell.getSummaryZb() != null ? dataCell.getSummaryZb().getFieldKey() : null;
            cell.fieldKind = currentFloatRegion == null ? DataFieldKind.FIELD_ZB : DataFieldKind.FIELD;
            cell.fieldType = dataCell.getSummaryZb().getDataType();
            cell.precision = dataCell.getSummaryZb().getPrecision();
            cell.decimal = dataCell.getSummaryZb().getDecimal();
            cell.gatherType = dataCell.getSummaryMode();
            cell.nullable = true;
            cell.modityTime = dataCell.getModifyTime().getTime();
            dataCell.getSummaryZb().setFieldKey(cell.key);
            tempCells.add(cell);
        }
        return tempCells;
    }

    private boolean isFloatTable() {
        DataTable currDataTable = this.context.getCurrDataTable();
        return currDataTable.getDataTableType().equals((Object)DataTableType.DETAIL);
    }

    private List<TemporaryCell> getInnerDimFields() {
        String tableName = this.context.getCurrDataTable().getCode();
        ArrayList<TemporaryCell> innerDimCells = new ArrayList<TemporaryCell>();
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        SummaryReportModelHelper reportModelHelper = new SummaryReportModelHelper(this.context.getDesignReportModel());
        List<MainCell> regionMainCells = reportModelHelper.getMainCellsByRegion(this.context.getCurrentFloatRegion());
        ArrayList<String> caliberDefineCodes = new ArrayList<String>();
        HashMap<String, TemporaryCell> innerDimZBCellMap = new HashMap<String, TemporaryCell>();
        block0: for (MainCell mainCell : regionMainCells) {
            List<CaliberInfo> caliberInfos = mainCell.getCaliberInfos();
            for (CaliberInfo caliberInfo : caliberInfos) {
                CaliberFloatInfo caliberFloatInfo = caliberInfo.getFloatInfo();
                if (!caliberInfo.getApplyType().equals((Object)CaliberApplyType.FLOAT)) continue;
                String caliberDefineCode = caliberFloatInfo.getDisplayField().split("\\.")[0];
                if (caliberDefineCodes.contains(caliberDefineCode)) {
                    SummaryZb innerDimZb = this.getSummaryZbByCell((TemporaryCell)innerDimZBCellMap.get(caliberDefineCode));
                    mainCell.setInnerDimZb(innerDimZb);
                    continue;
                }
                caliberDefineCodes.add(caliberDefineCode);
                String entityId = entityMetaService.getEntityIdByCode(caliberDefineCode);
                TemporaryCell cell = new TemporaryCell();
                cell.key = UUIDUtils.getKey();
                cell.code = reportModelHelper.generateMainCellFieldCode(tableName, mainCell);
                cell.title = reportModelHelper.getMainCellFieldTitle(mainCell);
                cell.fieldKey = mainCell.getInnerDimZb() != null ? mainCell.getInnerDimZb().getFieldKey() : null;
                cell.fieldKind = DataFieldKind.TABLE_FIELD_DIM;
                cell.fieldType = DataFieldType.STRING;
                cell.precision = 50;
                cell.gatherType = DataFieldGatherType.NONE;
                cell.referEntityKey = entityId;
                cell.nullable = false;
                cell.modityTime = this.context.getCurrReport().getModifyTime().getTime();
                innerDimCells.add(cell);
                innerDimZBCellMap.put(caliberDefineCode, cell);
                SummaryZb innerDimZb = this.getSummaryZbByCell(cell);
                mainCell.setInnerDimZb(innerDimZb);
                continue block0;
            }
        }
        return innerDimCells;
    }

    private SummaryZb getSummaryZbByCell(TemporaryCell cell) {
        Map<Integer, String> dataTableMap = this.context.getDataTableMap();
        SummaryZb innerDimZb = new SummaryZb();
        innerDimZb.setFieldKey(cell.key);
        innerDimZb.setName(cell.code);
        innerDimZb.setTitle(cell.title);
        innerDimZb.setTableName(dataTableMap.get(this.context.getCurrentFloatRegion().getPosition()));
        innerDimZb.setDataType(cell.fieldType);
        innerDimZb.setPrecision(cell.precision);
        return innerDimZb;
    }

    private void createOrUpdateField(List<TemporaryCell> cells, boolean update) {
        ArrayList<DesignDataField> dataFields = new ArrayList<DesignDataField>();
        if (!CollectionUtils.isEmpty(cells)) {
            for (TemporaryCell cell : cells) {
                DataScheme dataScheme = this.context.getDataScheme();
                DataTable dataTable = this.context.getCurrDataTable();
                DesignDataField dataField = null;
                dataField = update ? this.iDesignDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), cell.code) : this.iDesignDataSchemeService.initDataField();
                dataField.setDataSchemeKey(dataScheme.getKey());
                dataField.setDataTableKey(dataTable.getKey());
                if (!update) {
                    dataField.setKey(StringUtils.hasLength(cell.fieldKey) ? cell.fieldKey : cell.key);
                }
                dataField.setCode(cell.code);
                dataField.setTitle(StringUtils.hasLength(cell.title) ? cell.title : cell.code);
                dataField.setDataFieldKind(cell.fieldKind);
                dataField.setDataFieldType(cell.fieldType);
                dataField.setPrecision(cell.precision);
                dataField.setDecimal(cell.decimal);
                dataField.setDataFieldGatherType(cell.gatherType);
                dataField.setRefDataEntityKey(cell.referEntityKey);
                dataField.setNullable(Boolean.valueOf(cell.nullable));
                dataField.setOrder(OrderGenerator.newOrder());
                dataFields.add(dataField);
            }
        }
        if (update) {
            this.iDesignDataSchemeService.updateDataFields(dataFields);
        } else {
            this.iDesignDataSchemeService.insertDataFields(dataFields);
        }
    }
}


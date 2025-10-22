/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.service.dataio.IColumnType;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoBuilder;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoSub;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModel;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModelSub;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.data.estimation.service.dataio.model.DataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.model.DataRegionInfoSub;
import com.jiuqi.nr.data.estimation.service.dataio.model.RegionDimensionColumn;
import com.jiuqi.nr.data.estimation.service.dataio.model.RegionTableModel;
import com.jiuqi.nr.data.estimation.service.dataio.model.RegionTableModelSub;
import com.jiuqi.nr.data.estimation.service.dataio.model.TableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.model.TableBizKeyColumnSub;
import com.jiuqi.nr.data.estimation.service.dataio.model.TableCellLinkColumn;
import com.jiuqi.nr.data.estimation.service.dataio.model.TableCellLinkColumnSub;
import com.jiuqi.nr.data.estimation.service.dataio.model.TableDimensionColumn;
import com.jiuqi.nr.data.estimation.service.dataio.model.TableDimensionColumnSub;
import com.jiuqi.nr.data.estimation.service.dataio.model.TablePrimaryKeyColumn;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DataRegionInfoBuilder
implements IDataRegionInfoBuilder {
    @Resource
    public DataModelService dataModelService;
    @Resource
    public IEntityMetaService entityMetaService;
    @Resource
    public IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    public IRuntimeDataSchemeService dataSchemeService;
    @Resource
    public IRunTimeViewController nrTaskRuntimeService;

    @Override
    public IDataRegionInfo buildDataRegionInfo(String dataRegionKey) {
        DataRegionDefine dataRegionDefine = this.nrTaskRuntimeService.queryDataRegionDefine(dataRegionKey);
        return this.buildDataRegionInfo(dataRegionDefine);
    }

    @Override
    public IDataRegionInfo buildDataRegionInfo(DataRegionDefine dataRegionDefine) {
        DataRegionInfo regionInfo = new DataRegionInfo(dataRegionDefine);
        regionInfo.setDimensionColumns(this.getRegionDimensionColumns(dataRegionDefine));
        List<DataLinkDefine> zbDataLinksInRegion = this.getValidLinksInRegion(dataRegionDefine);
        if (!zbDataLinksInRegion.isEmpty()) {
            List<ITableCellLinkColumn> tableCellLinkColumns = this.getTableCellLinkColumns(zbDataLinksInRegion);
            regionInfo.setCellLinksColumns(tableCellLinkColumns);
            Map<String, List<ITableCellLinkColumn>> groupDataFieldsByDataTable = tableCellLinkColumns.stream().collect(Collectors.groupingBy(c -> c.getDataField().getDataTableKey()));
            for (Map.Entry<String, List<ITableCellLinkColumn>> dataTableEntry : groupDataFieldsByDataTable.entrySet()) {
                DataTable dataTable = this.dataSchemeService.getDataTable(dataTableEntry.getKey());
                Map<String, List<ITableCellLinkColumn>> groupColumnsByTableModel = dataTableEntry.getValue().stream().collect(Collectors.groupingBy(c -> c.getColumnModel().getTableID()));
                for (Map.Entry<String, List<ITableCellLinkColumn>> tableModelEntry : groupColumnsByTableModel.entrySet()) {
                    TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(tableModelEntry.getKey());
                    RegionTableModel regionTableModel = new RegionTableModel(tableModelDefine);
                    regionTableModel.setDataTable(dataTable);
                    regionTableModel.setCellLinkColumns(tableModelEntry.getValue());
                    regionTableModel.setDimensionColumns(this.getTableDimensionColumns(dataTable, tableModelDefine));
                    regionTableModel.setBizKeyColumns(this.getTableBizKeyColumns(dataTable, tableModelDefine));
                    regionTableModel.setBuildColumns(this.getTableBuildColumns(dataTable, tableModelDefine));
                    regionInfo.getRegionTableModels().add(regionTableModel);
                }
            }
        }
        if (DataRegionKind.DATA_REGION_SIMPLE != regionInfo.getDataRegion().getRegionKind()) {
            IRegionTableModel regionTableModel = regionInfo.getRegionTableModels().get(0);
            regionInfo.setBizKeyColumns(regionTableModel.getBizKeyColumns());
            regionInfo.setBuildColumns(regionTableModel.getBuildColumns());
        }
        return regionInfo;
    }

    private List<ITableBizKeyColumn> getRegionDimensionColumns(DataRegionDefine dataRegionDefine) {
        FormDefine formDefine = this.nrTaskRuntimeService.queryFormById(dataRegionDefine.getFormKey());
        FormSchemeDefine formSchemeDefine = this.nrTaskRuntimeService.getFormScheme(formDefine.getFormScheme());
        TaskDefine taskDefine = this.nrTaskRuntimeService.queryTaskDefine(formSchemeDefine.getTaskKey());
        List pubDimensions = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        return pubDimensions.stream().map(RegionDimensionColumn::new).collect(Collectors.toList());
    }

    private List<ITableBizKeyColumn> getTableDimensionColumns(DataTable dataTable, TableModelDefine tableModelDefine) {
        List deployFieldsInTable = this.dataSchemeService.getDeployInfoByTableModelKey(tableModelDefine.getID());
        List dimensionDataFields = this.dataSchemeService.getDataFieldByTableKeyAndKind(dataTable.getKey(), new DataFieldKind[]{DataFieldKind.PUBLIC_FIELD_DIM});
        ArrayList<ITableBizKeyColumn> dimensionColumns = new ArrayList<ITableBizKeyColumn>();
        for (DataField dimDataField : dimensionDataFields) {
            ColumnModelDefine columnModel = this.getColumnModelByDataField(dimDataField, deployFieldsInTable);
            TableDimensionColumn dimensionColumn = new TableDimensionColumn(columnModel);
            dimensionColumn.setDataField(dimDataField);
            dimensionColumn.setColumnModel(columnModel);
            dimensionColumns.add(dimensionColumn);
        }
        return dimensionColumns;
    }

    private List<ITableBizKeyColumn> getTableBizKeyColumns(DataTable dataTable, TableModelDefine tableModelDefine) {
        ArrayList<ITableBizKeyColumn> bizKeyColumns = new ArrayList<ITableBizKeyColumn>();
        List deployFieldsInTable = this.dataSchemeService.getDeployInfoByTableModelKey(tableModelDefine.getID());
        List bizKeyDataFields = this.dataSchemeService.getDataFieldByTableKeyAndKind(dataTable.getKey(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
        for (DataField bizDataField : bizKeyDataFields) {
            ColumnModelDefine columnModel = this.getColumnModelByDataField(bizDataField, deployFieldsInTable);
            TableBizKeyColumn tableBizKeyColumn = new TableBizKeyColumn(columnModel);
            tableBizKeyColumn.setDataField(bizDataField);
            tableBizKeyColumn.setColumnType(IColumnType.bizKey_column);
            bizKeyColumns.add(tableBizKeyColumn);
        }
        return bizKeyColumns;
    }

    private List<ITableBizKeyColumn> getTableBuildColumns(DataTable dataTable, TableModelDefine tableModelDefine) {
        ArrayList<ITableBizKeyColumn> buildColumns = new ArrayList<ITableBizKeyColumn>();
        List deployFieldsInTable = this.dataSchemeService.getDeployInfoByTableModelKey(tableModelDefine.getID());
        List buildDataFields = this.dataSchemeService.getDataFieldByTableKeyAndKind(dataTable.getKey(), new DataFieldKind[]{DataFieldKind.BUILT_IN_FIELD});
        for (DataField bizDataField : buildDataFields) {
            ColumnModelDefine columnModel = this.getColumnModelByDataField(bizDataField, deployFieldsInTable);
            TableBizKeyColumn tableBizKeyColumn = new TableBizKeyColumn(columnModel);
            tableBizKeyColumn.setDataField(bizDataField);
            tableBizKeyColumn.setColumnType(Arrays.stream(dataTable.getBizKeys()).anyMatch(dataFieldKey -> dataFieldKey.equals(bizDataField.getKey())) ? IColumnType.bizKey_column : IColumnType.normal_column);
            buildColumns.add(tableBizKeyColumn);
        }
        return buildColumns;
    }

    private List<ITableCellLinkColumn> getTableCellLinkColumns(List<DataLinkDefine> dataLinkDefines) {
        ArrayList<ITableCellLinkColumn> cellLinkColumns = new ArrayList<ITableCellLinkColumn>();
        for (DataLinkDefine linkDefine : dataLinkDefines) {
            DataField dataField = this.dataSchemeService.getDataField(linkDefine.getLinkExpression());
            List deployFields = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()});
            DataFieldDeployInfo deployField = deployFields.stream().findFirst().orElse(null);
            if (deployField == null) {
                throw new UnsupportedOperationException("\u94fe\u63a5\u7684\u6620\u5c04\u5173\u7cfb\u5b58\u5728\u95ee\u9898\uff01\uff01\uff01");
            }
            ColumnModelDefine columnModel = this.dataModelService.getColumnModelDefineByID(deployField.getColumnModelKey());
            TableCellLinkColumn cellLinkColumn = new TableCellLinkColumn();
            cellLinkColumn.setLinkDefine(linkDefine);
            cellLinkColumn.setDataField(dataField);
            cellLinkColumn.setColumnModel(columnModel);
            cellLinkColumns.add(cellLinkColumn);
        }
        return cellLinkColumns;
    }

    @Override
    public IDataRegionInfoSub buildDataRegionInfo(IDataRegionInfo oriDataRegionInfo, IDataSchemeSubDatabase dataSchemeSubDatabase) {
        DataRegionInfoSub subRegionInfo = new DataRegionInfoSub(oriDataRegionInfo.getDataRegion());
        List subPrimaryColumnCodes = dataSchemeSubDatabase.getOtherPrimaryColumnCodes("");
        for (IRegionTableModel oriTableModel : oriDataRegionInfo.getRegionTableModels()) {
            TableModelDefine subTableModel = this.dataModelService.getTableModelDefineByCode(dataSchemeSubDatabase.getSubTableCode(oriTableModel.getTableModelDefine().getCode()));
            RegionTableModelSub regionTableModelSub = new RegionTableModelSub(oriTableModel.getTableModelDefine(), subTableModel);
            regionTableModelSub.setDataTable(oriTableModel.getDataTable());
            regionTableModelSub.setOtherKeyColumns(this.getSubTableOtherKeyColumns(subTableModel, subPrimaryColumnCodes));
            regionTableModelSub.setDimensionColumns(this.getSubTableDimensionColumns(subTableModel, oriTableModel.getDimensionColumns()));
            regionTableModelSub.setBizKeyColumns(this.getSubTableBizKeyColumns(subTableModel, oriTableModel.getBizKeyColumns()));
            regionTableModelSub.setBuildColumns(this.getSubTableBizKeyColumns(subTableModel, oriTableModel.getBuildColumns()));
            regionTableModelSub.setCellLinkColumns(this.getSubTableCellLinkColumns(subTableModel, oriTableModel.getCellLinkColumns()));
            subRegionInfo.getRegionTableModels().add(regionTableModelSub);
            subRegionInfo.getCellLinksColumns().addAll(regionTableModelSub.getCellLinkColumns());
        }
        subRegionInfo.setDimensionColumns(oriDataRegionInfo.getDimensionColumns());
        subRegionInfo.setOtherKeyColumns(subPrimaryColumnCodes.stream().map(TablePrimaryKeyColumn::new).collect(Collectors.toList()));
        List<IRegionTableModelSub> regionTableModels = subRegionInfo.getRegionTableModels();
        if (!regionTableModels.isEmpty() && DataRegionKind.DATA_REGION_SIMPLE != subRegionInfo.getDataRegion().getRegionKind()) {
            IRegionTableModelSub regionTableModel = regionTableModels.get(0);
            subRegionInfo.setBizKeyColumns(regionTableModel.getBizKeyColumns());
            subRegionInfo.setBuildColumns(regionTableModel.getBuildColumns());
        }
        return subRegionInfo;
    }

    private List<ITableBizKeyColumn> getSubTableOtherKeyColumns(TableModelDefine subTableModel, List<String> subPrimaryColumnCodes) {
        ArrayList<ITableBizKeyColumn> otherKeyColumns = new ArrayList<ITableBizKeyColumn>();
        if (subPrimaryColumnCodes != null) {
            for (String columnCode : subPrimaryColumnCodes) {
                ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(subTableModel.getID(), columnCode);
                otherKeyColumns.add(new TablePrimaryKeyColumn(columnModelDefine));
            }
        }
        return otherKeyColumns;
    }

    private List<ITableBizKeyColumn> getSubTableDimensionColumns(TableModelDefine subTableModel, List<ITableBizKeyColumn> oriDimensionColumns) {
        ArrayList<ITableBizKeyColumn> subDimensionColumns = new ArrayList<ITableBizKeyColumn>();
        for (ITableBizKeyColumn oriDimCol : oriDimensionColumns) {
            ColumnModelDefine oriColumnModel = oriDimCol.getColumnModel();
            ColumnModelDefine subColumnModel = this.dataModelService.getColumnModelDefineByCode(subTableModel.getID(), oriColumnModel.getCode());
            TableDimensionColumnSub dimColumnSub = new TableDimensionColumnSub(subColumnModel, oriColumnModel);
            dimColumnSub.setDataField(oriDimCol.getDataField());
            subDimensionColumns.add(dimColumnSub);
        }
        return subDimensionColumns;
    }

    private List<ITableBizKeyColumn> getSubTableBizKeyColumns(TableModelDefine subTableModel, List<ITableBizKeyColumn> oriBizKeyColumns) {
        ArrayList<ITableBizKeyColumn> subBizKeyColumns = new ArrayList<ITableBizKeyColumn>();
        for (ITableBizKeyColumn oriBizKeyCol : oriBizKeyColumns) {
            ColumnModelDefine oriColumnModel = oriBizKeyCol.getColumnModel();
            ColumnModelDefine subColumnModel = this.dataModelService.getColumnModelDefineByCode(subTableModel.getID(), oriColumnModel.getCode());
            TableBizKeyColumnSub bizKeyColumnSub = new TableBizKeyColumnSub(subColumnModel);
            bizKeyColumnSub.setDataField(oriBizKeyCol.getDataField());
            bizKeyColumnSub.setColumnType(oriBizKeyCol.getColumnType());
            bizKeyColumnSub.setOriColumnModel(oriColumnModel);
            subBizKeyColumns.add(bizKeyColumnSub);
        }
        return subBizKeyColumns;
    }

    private List<ITableCellLinkColumn> getSubTableCellLinkColumns(TableModelDefine subTableModel, List<ITableCellLinkColumn> oriCellLinkColumns) {
        ArrayList<ITableCellLinkColumn> subCellLinkColumns = new ArrayList<ITableCellLinkColumn>();
        for (ITableCellLinkColumn oriCellLinkCol : oriCellLinkColumns) {
            ColumnModelDefine oriColumnModel = oriCellLinkCol.getColumnModel();
            TableCellLinkColumnSub cellLinkColumnSub = new TableCellLinkColumnSub(oriColumnModel);
            ColumnModelDefine subColumn = this.dataModelService.getColumnModelDefineByCode(subTableModel.getID(), oriColumnModel.getCode());
            cellLinkColumnSub.setColumnModel(subColumn);
            cellLinkColumnSub.setDataField(oriCellLinkCol.getDataField());
            cellLinkColumnSub.setLinkDefine(oriCellLinkCol.getLinkDefine());
            subCellLinkColumns.add(cellLinkColumnSub);
        }
        return subCellLinkColumns;
    }

    @Override
    public DimensionValueSet buildPubColumnValueSet(String formSchemeKey, DimensionValueSet dimValueSet) {
        DimensionValueSet pubDimValueSet = new DimensionValueSet();
        FormSchemeDefine formSchemeDefine = this.nrTaskRuntimeService.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.nrTaskRuntimeService.queryTaskDefine(formSchemeDefine.getTaskKey());
        List pubDimensions = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        pubDimensions.forEach(dimension -> {
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                pubDimValueSet.setValue("MDCODE", dimValueSet.getValue(dimensionName));
            } else if (DimensionType.PERIOD == dimension.getDimensionType()) {
                pubDimValueSet.setValue("DATATIME", dimValueSet.getValue(dimensionName));
            } else {
                pubDimValueSet.setValue(dimensionName, dimValueSet.getValue(dimensionName));
            }
        });
        return pubDimValueSet;
    }

    private ColumnModelDefine getColumnModelByDataField(DataField dataField, List<DataFieldDeployInfo> deployFieldsInTable) {
        DataFieldDeployInfo deployField = deployFieldsInTable.stream().filter(df -> df.getDataFieldKey().equals(dataField.getKey())).findFirst().orElse(null);
        if (deployField != null) {
            return this.dataModelService.getColumnModelDefineByID(deployField.getColumnModelKey());
        }
        return null;
    }

    private List<DataLinkDefine> getValidLinksInRegion(DataRegionDefine dataRegionDefine) {
        List<DataLinkDefine> allLinksInRegion = this.nrTaskRuntimeService.getAllLinksInRegion(dataRegionDefine.getKey());
        allLinksInRegion = allLinksInRegion.stream().filter(e -> DataLinkType.DATA_LINK_TYPE_FIELD == e.getType() && StringUtils.hasText(e.getLinkExpression())).collect(Collectors.toList());
        if (DataRegionKind.DATA_REGION_SIMPLE != dataRegionDefine.getRegionKind()) {
            allLinksInRegion.sort((linkData0, linkData1) -> {
                if (DataRegionKind.DATA_REGION_ROW_LIST == dataRegionDefine.getRegionKind()) {
                    return linkData0.getPosX() - linkData1.getPosX();
                }
                if (DataRegionKind.DATA_REGION_COLUMN_LIST == dataRegionDefine.getRegionKind()) {
                    return linkData0.getPosY() - linkData1.getPosY();
                }
                return 0;
            });
        }
        return allLinksInRegion;
    }

    public String getDimensionName(String entityId) {
        if (this.periodEntityAdapter.isPeriodEntity(entityId)) {
            return this.periodEntityAdapter.getPeriodEntity(entityId).getDimensionName();
        }
        return this.entityMetaService.queryEntity(entityId).getDimensionName();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.nr.batch.summary.service.enumeration.SummaryFunction;
import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn;
import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumnImpl;
import com.jiuqi.nr.batch.summary.service.targetform.BSTableColumn;
import com.jiuqi.nr.batch.summary.service.targetform.BSTableColumnImpl;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SumTableModelImpl
implements SumTableModelInfo {
    private TableModelDefine sumTableModel;
    private OriTableModelInfo oriTableModelInfo;
    private BSTableColumn dwColumn;
    private BSTableColumn periodColumn;
    private BSBizKeyColumn gatherColumn;
    private List<BSBizKeyColumn> situationColumns = new ArrayList<BSBizKeyColumn>();
    private List<BSBizKeyColumn> bizKeyColumns = new ArrayList<BSBizKeyColumn>();
    private List<BSBizKeyColumn> buildColumns = new ArrayList<BSBizKeyColumn>();
    private List<BSTableColumn> zbColumns = new ArrayList<BSTableColumn>();

    public SumTableModelImpl(TableModelDefine sumTableModel, DataModelService dataModelService, OriTableModelInfo oriTableModelInfo) {
        this.sumTableModel = sumTableModel;
        this.oriTableModelInfo = oriTableModelInfo;
        this.init(dataModelService);
    }

    @Override
    public String getTableName() {
        return this.sumTableModel.getName();
    }

    @Override
    public boolean isSimpleTable() {
        return this.oriTableModelInfo.isSimpleTable();
    }

    @Override
    public TableModelDefine getTableModel() {
        return this.sumTableModel;
    }

    @Override
    public BSBizKeyColumn getGatherColumn() {
        return this.gatherColumn;
    }

    @Override
    public BSTableColumn getDWColumn() {
        return this.dwColumn;
    }

    @Override
    public BSTableColumn getPeriodColumn() {
        return this.periodColumn;
    }

    @Override
    public List<BSBizKeyColumn> getSituationColumns() {
        return this.situationColumns;
    }

    @Override
    public List<BSBizKeyColumn> getBizKeyColumns() {
        return this.bizKeyColumns;
    }

    @Override
    public List<BSBizKeyColumn> getBuildColumns() {
        return this.buildColumns;
    }

    @Override
    public List<BSTableColumn> getZBColumns() {
        return this.zbColumns;
    }

    private void init(DataModelService dataModelService) {
        this.gatherColumn = this.getGatherColumn(dataModelService);
        this.dwColumn = this.getDWColumn(dataModelService, this.oriTableModelInfo.getDWColumn());
        this.periodColumn = this.getPeriodColumn(dataModelService, this.oriTableModelInfo.getPeriodColumn());
        this.situationColumns = this.getSituationColumns(dataModelService, this.oriTableModelInfo.getSituationColumns());
        this.bizKeyColumns = this.getBizKeyColumns(dataModelService, this.oriTableModelInfo.getBizKeyColumns());
        this.buildColumns = this.getBuildColumns(dataModelService, this.oriTableModelInfo.getBuildColumns());
        this.zbColumns = this.getZBColumns(dataModelService, this.oriTableModelInfo.getZBColumns());
    }

    private BSBizKeyColumn getGatherColumn(DataModelService dataModelService) {
        ColumnModelDefine columnModel = dataModelService.getColumnModelDefineByCode(this.sumTableModel.getID(), "GATHER_SCHEME_CODE");
        BSBizKeyColumnImpl dwColumn = new BSBizKeyColumnImpl(columnModel);
        dwColumn.setSQLGroupFunc(SummaryFunction.MIN);
        return dwColumn;
    }

    private BSTableColumn getDWColumn(DataModelService dataModelService, BSTableColumn oriDWColumn) {
        ColumnModelDefine columnModel = dataModelService.getColumnModelDefineByCode(this.sumTableModel.getID(), oriDWColumn.getColumnModel().getCode());
        BSTableColumnImpl dwColumn = new BSTableColumnImpl(columnModel);
        dwColumn.setSQLGroupFunc(oriDWColumn.getSQLGroupFunc());
        return dwColumn;
    }

    private BSTableColumn getPeriodColumn(DataModelService dataModelService, BSTableColumn oriPeriodColumn) {
        ColumnModelDefine columnModel = dataModelService.getColumnModelDefineByCode(this.sumTableModel.getID(), oriPeriodColumn.getColumnModel().getCode());
        BSTableColumnImpl dwColumn = new BSTableColumnImpl(columnModel);
        dwColumn.setSQLGroupFunc(SummaryFunction.MIN);
        return dwColumn;
    }

    private List<BSBizKeyColumn> getSituationColumns(DataModelService dataModelService, List<BSBizKeyColumn> oriSituationColumns) {
        return oriSituationColumns.stream().map(sColumn -> this.buildBSBizKeyColumn(dataModelService, (BSBizKeyColumn)sColumn)).collect(Collectors.toList());
    }

    private List<BSBizKeyColumn> getBizKeyColumns(DataModelService dataModelService, List<BSBizKeyColumn> oriBizKeyColumns) {
        return oriBizKeyColumns.stream().map(bzColumn -> this.buildBSBizKeyColumn(dataModelService, (BSBizKeyColumn)bzColumn)).collect(Collectors.toList());
    }

    private List<BSBizKeyColumn> getBuildColumns(DataModelService dataModelService, List<BSBizKeyColumn> oriBuildColumns) {
        return oriBuildColumns.stream().map(bdColumn -> this.buildBSBizKeyColumn(dataModelService, (BSBizKeyColumn)bdColumn)).collect(Collectors.toList());
    }

    private List<BSTableColumn> getZBColumns(DataModelService dataModelService, List<BSTableColumn> oriZBColumns) {
        return oriZBColumns.stream().map(zbColumn -> {
            ColumnModelDefine columnModel = dataModelService.getColumnModelDefineByCode(this.sumTableModel.getID(), zbColumn.getColumnModel().getCode());
            BSTableColumnImpl column = new BSTableColumnImpl(columnModel);
            column.setSQLGroupFunc(zbColumn.getSQLGroupFunc());
            return column;
        }).collect(Collectors.toList());
    }

    private BSBizKeyColumn buildBSBizKeyColumn(DataModelService dataModelService, BSBizKeyColumn oriBizKeyColumn) {
        ColumnModelDefine columnModel = dataModelService.getColumnModelDefineByCode(this.sumTableModel.getID(), oriBizKeyColumn.getColumnModel().getCode());
        BSBizKeyColumnImpl column = new BSBizKeyColumnImpl(columnModel);
        column.setSQLGroupFunc(oriBizKeyColumn.getSQLGroupFunc());
        column.setIsCorporate(oriBizKeyColumn.isCorporate());
        column.setDefaultValue(oriBizKeyColumn.getDefaultValue());
        return column;
    }
}


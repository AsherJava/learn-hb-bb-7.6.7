/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.workflow2.form.reject.model;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.form.reject.model.FRStatusTable;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FRStatusDesTableModelDefine {
    protected DesignTableModelDefine tableModelDefine;
    protected DesignIndexModelDefine indexModelDefine;
    protected List<DesignColumnModelDefine> columnModelDefines;
    protected List<DesignColumnModelDefine> bizKeyColumnModelDefines;

    public FRStatusDesTableModelDefine() {
    }

    public FRStatusDesTableModelDefine(FRStatusTable statusTable, DesignDataModelService designDataModelService) {
        this.tableModelDefine = this.initTableModelDefine(statusTable, designDataModelService);
        this.bizKeyColumnModelDefines = this.initBizKeyColumnModelDefines(statusTable, designDataModelService);
        this.indexModelDefine = this.initIndexModelDefines(this.bizKeyColumnModelDefines, designDataModelService);
        this.columnModelDefines = this.intColumnModelDefines(statusTable, designDataModelService);
        this.tableModelDefine.setKeys(this.bizKeyColumnModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";")));
        this.tableModelDefine.setBizKeys(this.bizKeyColumnModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";")));
    }

    protected DesignTableModelDefine initTableModelDefine(FRStatusTable statusTable, DesignDataModelService designDataModelService) {
        FormSchemeDefine formSchemeDefine = statusTable.getFormSchemeDefine();
        DesignTableModelDefine tableModelDefine = designDataModelService.createTableModelDefine();
        tableModelDefine.setCode(statusTable.getTableName());
        tableModelDefine.setName(statusTable.getTableName());
        tableModelDefine.setTitle(formSchemeDefine.getTitle() + "\u3010" + formSchemeDefine.getFormSchemeCode() + "\u3011\u7684\u5355\u8868\u9000\u56de\u8bb0\u5f55\u5b58\u50a8\u8868");
        return tableModelDefine;
    }

    protected List<DesignColumnModelDefine> initBizKeyColumnModelDefines(FRStatusTable statusTable, DesignDataModelService designDataModelService) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        IReportDimensionHelper reportDimensionHelper = (IReportDimensionHelper)SpringBeanUtils.getBean(IReportDimensionHelper.class);
        ArrayList<DesignColumnModelDefine> columnModelDefines = new ArrayList<DesignColumnModelDefine>();
        List reportDimensions = reportDimensionHelper.getAllReportDimensions(statusTable.getFormSchemeDefine().getTaskKey());
        for (DataDimension dimension : reportDimensions) {
            DesignColumnModelDefine columnModelDefine;
            String dimensionName = reportDimensionHelper.getDimensionName(dimension);
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                columnModelDefine = this.createDataDimensionColumnModelDefine(dimensionName, dimension, designDataModelService, entityMetaService);
                columnModelDefine.setCode(statusTable.getUnitColumnCode());
                columnModelDefines.add(columnModelDefine);
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                columnModelDefine = this.createDataDimensionColumnModelDefine(dimensionName, dimension, designDataModelService);
                columnModelDefine.setCode(statusTable.getPeriodColumnCode());
                columnModelDefine.setTitle("\u65f6\u671f");
                columnModelDefines.add(columnModelDefine);
                continue;
            }
            if ("ADJUST".equals(dimension.getDimKey())) {
                columnModelDefine = this.createDataDimensionColumnModelDefine(dimensionName, dimension, designDataModelService);
                columnModelDefine.setCode(statusTable.getAdjustColumnCode());
                columnModelDefine.setTitle("\u8c03\u6574\u671f");
                columnModelDefines.add(columnModelDefine);
                continue;
            }
            columnModelDefine = this.createDataDimensionColumnModelDefine(dimensionName, dimension, designDataModelService, entityMetaService);
            columnModelDefines.add(columnModelDefine);
        }
        columnModelDefines.add(this.createFormIdColumnModelDefine(statusTable, designDataModelService));
        return columnModelDefines;
    }

    protected List<DesignColumnModelDefine> intColumnModelDefines(FRStatusTable statusTable, DesignDataModelService designDataModelService) {
        ArrayList<DesignColumnModelDefine> columnModelDefines = new ArrayList<DesignColumnModelDefine>();
        columnModelDefines.add(this.createStatusColumnModelDefine(statusTable, designDataModelService));
        return columnModelDefines;
    }

    protected DesignColumnModelDefine createDataDimensionColumnModelDefine(String dimensionName, DataDimension dimension, DesignDataModelService designDataModelService, IEntityMetaService entityMetaService) {
        IEntityDefine entityDefine = entityMetaService.queryEntity(dimension.getDimKey());
        IEntityAttribute entityAttribute = this.getIEntityAttribute(dimension, entityMetaService);
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setTableID(this.tableModelDefine.getID());
        columnModelDefine.setCode(dimensionName);
        columnModelDefine.setName(dimensionName);
        columnModelDefine.setTitle(entityDefine.getTitle());
        columnModelDefine.setColumnType(entityAttribute.getColumnType());
        columnModelDefine.setPrecision(entityAttribute.getPrecision());
        columnModelDefine.setReferTableID(dimension.getDimKey());
        columnModelDefine.setNullAble(false);
        return columnModelDefine;
    }

    protected DesignColumnModelDefine createDataDimensionColumnModelDefine(String dimensionName, DataDimension dimension, DesignDataModelService designDataModelService) {
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setTableID(this.tableModelDefine.getID());
        columnModelDefine.setCode(dimensionName);
        columnModelDefine.setName(dimensionName);
        columnModelDefine.setTitle(dimension.getDimensionType().getTitle());
        columnModelDefine.setColumnType(ColumnModelType.STRING);
        columnModelDefine.setPrecision(50);
        columnModelDefine.setReferTableID(dimension.getDimKey());
        columnModelDefine.setNullAble(false);
        return columnModelDefine;
    }

    protected IEntityAttribute getIEntityAttribute(DataDimension dimension, IEntityMetaService entityMetaService) {
        IEntityModel entityModel = entityMetaService.getEntityModel(dimension.getDimKey());
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        if (bizKeyField != null) {
            return bizKeyField;
        }
        return entityModel.getRecordKeyField();
    }

    protected DesignColumnModelDefine createFormIdColumnModelDefine(FRStatusTable statusTable, DesignDataModelService designDataModelService) {
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setTableID(this.tableModelDefine.getID());
        columnModelDefine.setCode(statusTable.getFormIdColumnName());
        columnModelDefine.setName(statusTable.getFormIdColumnName());
        columnModelDefine.setTitle("\u9000\u56de\u8868\u5355ID");
        columnModelDefine.setColumnType(ColumnModelType.STRING);
        columnModelDefine.setPrecision(60);
        columnModelDefine.setNullAble(false);
        return columnModelDefine;
    }

    protected DesignColumnModelDefine createStatusColumnModelDefine(FRStatusTable statusTable, DesignDataModelService designDataModelService) {
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setTableID(this.tableModelDefine.getID());
        columnModelDefine.setCode(statusTable.getStatusColumnName());
        columnModelDefine.setName(statusTable.getStatusColumnName());
        columnModelDefine.setTitle("\u8868\u5355\u72b6\u6001");
        columnModelDefine.setColumnType(ColumnModelType.STRING);
        columnModelDefine.setPrecision(60);
        columnModelDefine.setNullAble(false);
        return columnModelDefine;
    }

    protected DesignIndexModelDefine initIndexModelDefines(List<DesignColumnModelDefine> columnModelDefines, DesignDataModelService designDataModelService) {
        DesignIndexModelDefine indexModel = designDataModelService.createIndexModel();
        indexModel.setTableID(this.tableModelDefine.getID());
        indexModel.setType(IndexModelType.UNIQUE);
        indexModel.setName("PK_" + this.tableModelDefine.getCode());
        indexModel.setFieldIDs(columnModelDefines.stream().map(ColumnModelDefine::getTableID).collect(Collectors.joining(";")));
        indexModel.setDesc("\u4e1a\u52a1\u4e3b\u952e\u666e\u901a\u7d22\u5f15");
        return indexModel;
    }

    public DesignTableModelDefine getTableModelDefine() {
        return this.tableModelDefine;
    }

    public List<DesignColumnModelDefine> getColumnModelDefines() {
        ArrayList<DesignColumnModelDefine> allColumnModelDefines = new ArrayList<DesignColumnModelDefine>();
        allColumnModelDefines.addAll(this.bizKeyColumnModelDefines);
        allColumnModelDefines.addAll(this.columnModelDefines);
        return allColumnModelDefines;
    }

    public DesignIndexModelDefine getIndexModelDefine() {
        return this.indexModelDefine;
    }
}


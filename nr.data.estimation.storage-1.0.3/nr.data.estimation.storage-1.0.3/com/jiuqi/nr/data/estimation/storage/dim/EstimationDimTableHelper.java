/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.data.estimation.common.exception.EstimationRuntimeException
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.storage.dim;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.data.estimation.common.exception.EstimationRuntimeException;
import com.jiuqi.nr.data.estimation.storage.dao.IEstimationDimTableDao;
import com.jiuqi.nr.data.estimation.storage.dim.IEstimationDimTableHelper;
import com.jiuqi.nr.data.estimation.storage.entity.impl.DimTableRecord;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class EstimationDimTableHelper
implements IEstimationDimTableHelper {
    @Resource
    private IEstimationDimTableDao dimTableDao;
    @Resource
    private DataModelDeployService dataModelDeployService;
    @Resource
    private DesignDataModelService designDataModelService;
    @Resource
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private IPeriodEntityAdapter periodEntityService;
    @Resource
    private IRunTimeViewController runTimeViewController;

    @Override
    public boolean existEstimationDimTable(String dataScheme) {
        return this.dimTableDao.findDimTableRecord(dataScheme) != null;
    }

    @Override
    public boolean createEstimationDimTable(String dataSchemeKey) {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        if (dataScheme != null && !this.existEstimationDimTable(dataSchemeKey)) {
            String tableName = "ES_DIM_" + OrderGenerator.newOrder() + "_DE_";
            String tableModelId = UUID.randomUUID().toString();
            List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
            List<DesignColumnModelDefine> columnModelDefines = this.madeDesignTableColumns(tableModelId, dataSchemeDimension);
            List primaryKeys = columnModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.toList());
            List<String> fieldCodes = columnModelDefines.stream().map(IModelDefineItem::getCode).collect(Collectors.toList());
            DesignTableModelDefine tableModelDefine = this.designDataModelService.createTableModelDefine();
            tableModelDefine.setID(tableModelId);
            tableModelDefine.setCode(tableName);
            tableModelDefine.setName(tableName);
            tableModelDefine.setTitle(dataScheme.getTitle() + "\u7684\u6d4b\u7b97\u7ef4\u5ea6\u4fe1\u606f\u8868");
            tableModelDefine.setKeys(String.join((CharSequence)";", primaryKeys));
            tableModelDefine.setCreateTime(new Date());
            tableModelDefine.setDesc("\u7528\u6765\u8bb0\u5f55\u9694\u79bb\u3010\u6d4b\u7b97\u65b9\u6848\u3011\u7684\u7ef4\u5ea6");
            tableModelDefine.setKind(TableModelKind.DEFAULT);
            try {
                this.designDataModelService.insertTableModelDefine(tableModelDefine);
                this.designDataModelService.insertColumnModelDefines(columnModelDefines.toArray(new DesignColumnModelDefine[0]));
                this.dataModelDeployService.deployTable(tableModelId);
                this.dimTableDao.insertDimTableRecord(new DimTableRecord(dataScheme.getKey(), tableModelDefine.getName(), fieldCodes));
            }
            catch (Exception e) {
                throw new EstimationRuntimeException((Throwable)e);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getEstimationDimTableName(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            throw new EstimationRuntimeException("\u83b7\u53d6\u6d4b\u7b97\u65b9\u6848\u7ef4\u5ea6\u4fe1\u606f\u8868\u5931\u8d25\uff1a\u62a5\u8868\u65b9\u6848\u4e3a\u7a7a\uff01");
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DimTableRecord dimTableRecord = this.dimTableDao.findDimTableRecord(taskDefine.getDataScheme());
        if (dimTableRecord == null) {
            throw new EstimationRuntimeException("\u83b7\u53d6\u6d4b\u7b97\u65b9\u6848\u7ef4\u5ea6\u4fe1\u606f\u8868\u5931\u8d25\uff1a\u6ca1\u6709\u751f\u6210\u7ef4\u5ea6\u4fe1\u606f\u8868\uff0c\u8bf7\u53bb\u53d1\u5e03\u6570\u636e\u65b9\u6848");
        }
        return dimTableRecord.getTableName();
    }

    private List<DesignColumnModelDefine> madeDesignTableColumns(String tableModelId, List<DataDimension> dataSchemeDimension) {
        ArrayList<DesignColumnModelDefine> columns = new ArrayList<DesignColumnModelDefine>();
        columns.add(this.getEstimationFieldDefine(tableModelId));
        for (DataDimension dimension : dataSchemeDimension) {
            DesignColumnModelDefine impl = this.designDataModelService.createColumnModelDefine();
            impl.setTableID(tableModelId);
            columns.add(impl);
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                IPeriodEntity periodEntity = this.periodEntityService.getPeriodEntity(dimension.getDimKey());
                impl.setCode(periodEntity.getDimensionName());
                impl.setName(periodEntity.getDimensionName());
                impl.setTitle(periodEntity.getTitle());
            } else if ("ADJUST".equals(dimension.getDimKey())) {
                impl.setCode("ADJUST");
                impl.setName("ADJUST");
                impl.setTitle("\u8c03\u6574\u671f");
            } else {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(dimension.getDimKey());
                impl.setCode(entityDefine.getDimensionName());
                impl.setName(entityDefine.getDimensionName());
                impl.setTitle(entityDefine.getTitle());
            }
            impl.setColumnType(ColumnModelType.STRING);
            impl.setPrecision(50);
            impl.setNullAble(false);
        }
        return columns;
    }

    private DesignColumnModelDefine getEstimationFieldDefine(String tableModelId) {
        DesignColumnModelDefine estimationSchemeCol = this.designDataModelService.createColumnModelDefine();
        estimationSchemeCol.setTableID(tableModelId);
        estimationSchemeCol.setCode("ESTIMATION_SCHEME");
        estimationSchemeCol.setName("ESTIMATION_SCHEME");
        estimationSchemeCol.setTitle("\u6d4b\u7b97\u65b9\u6848\u6807\u8bc6");
        estimationSchemeCol.setColumnType(ColumnModelType.STRING);
        estimationSchemeCol.setPrecision(50);
        return estimationSchemeCol;
    }
}


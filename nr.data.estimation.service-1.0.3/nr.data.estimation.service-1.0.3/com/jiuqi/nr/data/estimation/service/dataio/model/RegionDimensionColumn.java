/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.data.estimation.service.dataio.IColumnType;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class RegionDimensionColumn
implements ITableBizKeyColumn {
    private final String columnName;
    private final DataDimension dimension;

    public RegionDimensionColumn(DataDimension dimension) {
        this.dimension = dimension;
        this.columnName = this.getColumnName(dimension);
    }

    @Override
    public String getColumnName() {
        return this.columnName;
    }

    @Override
    public IColumnType getColumnType() {
        return IColumnType.bizKey_column;
    }

    @Override
    public DataField getDataField() {
        return null;
    }

    @Override
    public ColumnModelDefine getColumnModel() {
        return null;
    }

    public DataDimension getDimension() {
        return this.dimension;
    }

    public String getColumnName(DataDimension dimension) {
        switch (dimension.getDimensionType()) {
            case UNIT: {
                return "MDCODE";
            }
            case PERIOD: {
                return "DATATIME";
            }
        }
        String entityId = dimension.getDimKey();
        IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)SpringBeanUtils.getBean(IPeriodEntityAdapter.class);
        if (periodEntityAdapter.isPeriodEntity(entityId)) {
            return periodEntityAdapter.getPeriodEntity(entityId).getCode();
        }
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        return entityMetaService.queryEntity(entityId).getCode();
    }
}


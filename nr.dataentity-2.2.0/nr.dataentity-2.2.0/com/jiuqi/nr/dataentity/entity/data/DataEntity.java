/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.dataentity.entity.data;

import com.jiuqi.nr.dataentity.entity.DataEntityType;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.dataentity.entity.data.DataEntityRow;
import com.jiuqi.nr.dataentity.entity.data.DataEntityRowAdjust;
import com.jiuqi.nr.dataentity.entity.data.DataEntityRowPeriod;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.ArrayList;
import java.util.List;

public class DataEntity
implements IDataEntity {
    private DataEntityType type;
    private IEntityTable enittyTable;
    private List<AdjustPeriod> adjustPeriod;
    private IPeriodEntity periodEntity;

    public void setType(DataEntityType type) {
        this.type = type;
    }

    public DataEntity(DataEntityType type) {
        this.type = type;
    }

    public DataEntity(IEntityTable enittyTable, List<AdjustPeriod> adjustPeriod, IPeriodEntity periodEntity, DataEntityType type) {
        this.enittyTable = enittyTable;
        this.adjustPeriod = adjustPeriod;
        this.periodEntity = periodEntity;
        this.type = type;
    }

    public DataEntity(IEntityTable enittyTable, DataEntityType type) {
        this.enittyTable = enittyTable;
        this.type = type;
    }

    public DataEntity(IPeriodEntity periodEntity) {
        this.periodEntity = periodEntity;
    }

    public void setEnittyTable(IEntityTable enittyTable) {
        if (this.enittyTable == null) {
            this.enittyTable = enittyTable;
        }
    }

    public void setAdjustPeriod(List<AdjustPeriod> adjustPeriod) {
        if (this.adjustPeriod == null) {
            this.adjustPeriod = adjustPeriod;
        }
    }

    public void setPeriodEntity(IPeriodEntity periodEntity) {
        if (this.periodEntity == null) {
            this.periodEntity = periodEntity;
        }
    }

    @Override
    public DataEntityType type() {
        return this.type;
    }

    @Override
    public IDataEntityRow getAllRow() {
        if (this.type.getValue() == DataEntityType.DataEntity.getValue()) {
            return new DataEntityRow(this.enittyTable.getAllRows());
        }
        if (this.type.getValue() == DataEntityType.DataEntityPeriod.getValue()) {
            return new DataEntityRowPeriod(this.periodEntity);
        }
        if (this.type.getValue() == DataEntityType.DataEntityAdjust.getValue()) {
            return new DataEntityRowAdjust(this.adjustPeriod);
        }
        return null;
    }

    @Override
    public IDataEntityRow findByCode(String code) {
        if (this.type.getValue() == DataEntityType.DataEntity.getValue()) {
            return new DataEntityRow(this.enittyTable.findAllByCode(code));
        }
        if (this.type.getValue() == DataEntityType.DataEntityPeriod.getValue()) {
            if (this.periodEntity.getCode().equals(code)) {
                return new DataEntityRowPeriod(this.periodEntity);
            }
        } else if (this.type.getValue() == DataEntityType.DataEntityAdjust.getValue()) {
            for (AdjustPeriod adjust : this.adjustPeriod) {
                if (!adjust.getCode().equals(code)) continue;
                ArrayList<AdjustPeriod> result = new ArrayList<AdjustPeriod>();
                result.add(adjust);
                return new DataEntityRowAdjust(result);
            }
        }
        return null;
    }

    @Override
    public IDataEntityRow findAllByCode(String code) {
        if (this.type.getValue() == DataEntityType.DataEntity.getValue()) {
            return new DataEntityRow(this.enittyTable.findAllByCode(code));
        }
        if (this.type.getValue() == DataEntityType.DataEntityPeriod.getValue()) {
            if (this.periodEntity.getCode().equals(code)) {
                return new DataEntityRowPeriod(this.periodEntity);
            }
        } else if (this.type.getValue() == DataEntityType.DataEntityAdjust.getValue()) {
            for (AdjustPeriod adjust : this.adjustPeriod) {
                if (!adjust.getCode().equals(code)) continue;
                ArrayList<AdjustPeriod> result = new ArrayList<AdjustPeriod>();
                result.add(adjust);
                return new DataEntityRowAdjust(result);
            }
        }
        return null;
    }

    @Override
    public IDataEntityRow getAllChildRows(String entityKeyData) {
        if (this.type.getValue() == DataEntityType.DataEntity.getValue()) {
            return new DataEntityRow(this.enittyTable.getAllChildRows(entityKeyData));
        }
        return null;
    }

    @Override
    public IDataEntityRow findByEntityKey(String entityKeyData) {
        if (this.type.getValue() == DataEntityType.DataEntity.getValue()) {
            ArrayList<IEntityRow> rowList = new ArrayList<IEntityRow>();
            rowList.add(this.enittyTable.findByEntityKey(entityKeyData));
            return new DataEntityRow(rowList);
        }
        if (this.type.getValue() == DataEntityType.DataEntityPeriod.getValue()) {
            if (this.periodEntity.getKey().equals(entityKeyData)) {
                return new DataEntityRowPeriod(this.periodEntity);
            }
        } else if (this.type.getValue() == DataEntityType.DataEntityAdjust.getValue()) {
            for (AdjustPeriod adjust : this.adjustPeriod) {
                if (!adjust.getCode().equals(entityKeyData)) continue;
                ArrayList<AdjustPeriod> result = new ArrayList<AdjustPeriod>();
                result.add(adjust);
                return new DataEntityRowAdjust(result);
            }
        }
        return null;
    }

    @Override
    public IDataEntityRow getRootRows() {
        if (this.type.getValue() == DataEntityType.DataEntity.getValue()) {
            return new DataEntityRow(this.enittyTable.getRootRows());
        }
        ArrayList<IEntityRow> list = new ArrayList<IEntityRow>();
        return new DataEntityRow(list);
    }

    @Override
    public IDataEntityRow getChildRows(String entityKeyData) {
        if (this.type.getValue() == DataEntityType.DataEntity.getValue()) {
            return new DataEntityRow(this.enittyTable.getChildRows(entityKeyData));
        }
        ArrayList<IEntityRow> list = new ArrayList<IEntityRow>();
        return new DataEntityRow(list);
    }

    @Override
    public IEntityTable getEntityTable() {
        return this.enittyTable;
    }
}


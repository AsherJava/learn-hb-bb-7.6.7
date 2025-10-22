/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.lwtree.query;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.lwtree.para.ITreeParamsInitializer;
import com.jiuqi.nr.lwtree.query.IEntityRowCounter;
import com.jiuqi.nr.lwtree.query.IEntityRowQueryer;
import com.jiuqi.nr.lwtree.query.IEntityTableQueryer;
import java.util.List;

public class IEntityRowQueryerImpl
implements IEntityRowQueryer,
IEntityRowCounter {
    protected IEntityTable rs;
    protected ITreeParamsInitializer loadInfo;
    protected IEntityTableQueryer dataEngineMgr = (IEntityTableQueryer)BeanUtil.getBean(IEntityTableQueryer.class);

    public IEntityRowQueryerImpl(ITreeParamsInitializer loadInfo) {
        this.loadInfo = loadInfo;
    }

    @Override
    public List<IEntityRow> getAllRows() {
        this.rs = this.getIEntityTable();
        return this.rs.getAllRows();
    }

    @Override
    public List<IEntityRow> getRootRows() {
        this.rs = this.getIEntityTable();
        return this.rs.getRootRows();
    }

    @Override
    public List<IEntityRow> getChildRows(String entKey) {
        this.rs = this.getIEntityTable();
        return this.rs.getChildRows(entKey);
    }

    @Override
    public List<IEntityRow> getAllChildRows(String entKey) {
        this.rs = this.getIEntityTable();
        return this.rs.getAllChildRows(entKey);
    }

    @Override
    public IEntityRow findByEntityKey(String entKey) {
        if (StringUtils.isNotEmpty((String)entKey)) {
            this.rs = this.getIEntityTable();
            return this.rs.findByEntityKey(entKey);
        }
        return null;
    }

    @Override
    public IEntityRow findParentEntityRow(IEntityRow row) {
        return this.findByEntityKey(row.getParentEntityKey());
    }

    @Override
    public IEntityRowCounter getRowCounter() {
        return this;
    }

    @Override
    public int getCount() {
        this.rs = this.getIEntityTable();
        return this.rs.getTotalCount();
    }

    @Override
    public int getChildCount(String entKey) {
        this.rs = this.getIEntityTable();
        return this.rs.getDirectChildCount(entKey);
    }

    @Override
    public int getAllChildCount(String entKey) {
        this.rs = this.getIEntityTable();
        return this.rs.getAllChildCount(entKey);
    }

    @Override
    public boolean hasChildren(String entKey) {
        return this.getChildCount(entKey) > 0;
    }

    @Override
    public IEntityTable getIEntityTable() {
        if (this.rs == null) {
            String periodDimName = this.loadInfo.getPeriodDimName();
            String period = this.loadInfo.getPeriod();
            DimensionValueSet valueSet = new DimensionValueSet();
            valueSet.setValue(periodDimName, (Object)period);
            this.rs = this.loadInfo.getCustomVariable().has("UnitViewId") && this.loadInfo.getCustomVariable().get("UnitViewId") != null ? this.dataEngineMgr.makeIEntityTableAndFilterEntities(this.loadInfo) : this.dataEngineMgr.makeIEntityTable(this.loadInfo);
        }
        return this.rs;
    }
}


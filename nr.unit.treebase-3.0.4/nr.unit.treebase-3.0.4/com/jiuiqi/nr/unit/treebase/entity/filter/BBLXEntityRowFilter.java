/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 */
package com.jiuiqi.nr.unit.treebase.entity.filter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BBLXEntityRowFilter
implements IFilterEntityRow {
    private IUnitTreeContext context;
    private List<String> codesOfBBLX;
    private IEntityRefer referEntityOfBBLX;
    private IUnitTreeEntityDataQuery entityDataQuery;
    private List<IEntityRow> filterSet = new ArrayList<IEntityRow>();

    public BBLXEntityRowFilter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
        this.init(context);
    }

    public BBLXEntityRowFilter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, List<String> codesOfBBLX) {
        this(context, entityDataQuery);
        this.codesOfBBLX = codesOfBBLX;
    }

    private void init(IUnitTreeContext context) {
        IUnitTreeContextWrapper contextWrapper = (IUnitTreeContextWrapper)SpringBeanUtils.getBean(IUnitTreeContextWrapper.class);
        this.referEntityOfBBLX = contextWrapper.getBBLXEntityRefer(context.getEntityDefine());
    }

    @Override
    public boolean matchRow(IEntityRow row) {
        return this.filterSet.stream().anyMatch(fRow -> fRow.getEntityKeyData().equals(row.getEntityKeyData()));
    }

    @Override
    public void setMatchRangeRows(List<IEntityRow> rows) {
        IEntityDefine entityDefine = this.context.getEntityDefine();
        List<String> rowKeys = rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        String joinExpression = this.codesOfBBLX.stream().map(code -> "'" + code + "'").collect(Collectors.joining(","));
        String rowFilter = String.format("%s[%s] in {%s}", entityDefine.getCode(), this.referEntityOfBBLX.getOwnField(), joinExpression);
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context, rowKeys, rowFilter);
        this.filterSet.addAll(dataTable.getAllRows());
    }

    @Override
    public List<IEntityRow> getMatchResultSet(List<String> codesOfBBLX) {
        IEntityDefine entityDefine = this.context.getEntityDefine();
        String joinExpression = codesOfBBLX.stream().map(code -> "'" + code + "'").collect(Collectors.joining(","));
        String rowFilter = String.format("%s[%s] in {%s}", entityDefine.getCode(), this.referEntityOfBBLX.getOwnField(), joinExpression);
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context, rowFilter);
        this.filterSet = dataTable.getAllRows();
        return this.filterSet;
    }
}


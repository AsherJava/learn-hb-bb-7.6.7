/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityRefer
 */
package com.jiuiqi.nr.unit.treebase.entity.query;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.query.ICommonEntityDataQuery;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityRefer;
import java.util.ArrayList;
import java.util.List;

public class BBLXNodeDataQuery {
    private IEntityTable dataTable;

    public BBLXNodeDataQuery(IUnitTreeContext ctx) {
        IUnitTreeContextWrapper contextWrapper = (IUnitTreeContextWrapper)SpringBeanUtils.getBean(IUnitTreeContextWrapper.class);
        ICommonEntityDataQuery entityRowQuery = (ICommonEntityDataQuery)SpringBeanUtils.getBean(ICommonEntityDataQuery.class);
        IEntityRefer referOfBBLX = contextWrapper.getBBLXEntityRefer(ctx.getEntityDefine());
        this.dataTable = entityRowQuery.makeIEntityTable(referOfBBLX.getReferEntityId());
    }

    public String getBBLXCode(String objectId) {
        IEntityRow bblxRow = this.getBBLXRow(objectId);
        return bblxRow != null ? bblxRow.getCode() : null;
    }

    public String getBBLXTitle(String objectId) {
        IEntityRow bblxRow = this.getBBLXRow(objectId);
        return bblxRow != null ? bblxRow.getTitle() : null;
    }

    public IEntityRow getBBLXRow(String objectId) {
        return this.dataTable.findByEntityKey(objectId);
    }

    public List<IEntityRow> getAllBBLXRows() {
        List allRows = this.dataTable.getAllRows();
        return allRows != null ? allRows : new ArrayList();
    }
}


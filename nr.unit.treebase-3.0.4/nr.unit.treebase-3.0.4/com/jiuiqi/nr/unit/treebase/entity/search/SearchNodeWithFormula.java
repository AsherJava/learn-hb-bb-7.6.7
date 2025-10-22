/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuiqi.nr.unit.treebase.entity.search;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.List;
import java.util.stream.Collectors;

public class SearchNodeWithFormula
implements ISearchNodeProvider {
    protected int totalSize;
    protected IUnitTreeContext context;
    protected IEntityMetaService metaService;
    protected IUnitTreeEntityDataQuery entityDataQuery;

    public SearchNodeWithFormula(IUnitTreeContext context, IUnitTreeEntityDataQuery entityRowQuery) {
        this.context = context;
        this.entityDataQuery = entityRowQuery;
        this.metaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
    }

    public int getTotalSize() {
        return this.totalSize;
    }

    public List<IBaseNodeData> getTotalPage(String[] keywords) {
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context, this.translate2Formula(keywords));
        List matchRows = dataTable.getAllRows();
        this.totalSize = matchRows.size();
        return matchRows.stream().map(this::madeSearchNodeData).collect(Collectors.toList());
    }

    public List<IBaseNodeData> getOnePage(String[] keywords, int pageSize, int currentPage) {
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context, this.translate2Formula(keywords));
        List matchRows = dataTable.getAllRows();
        this.totalSize = matchRows.size();
        int fromIndex = currentPage * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, this.totalSize);
        List pageRows = matchRows.subList(fromIndex, toIndex);
        return pageRows.stream().map(this::madeSearchNodeData).collect(Collectors.toList());
    }

    protected String translate2Formula(String[] keywords) {
        String tableName = this.context.getEntityDefine().getCode();
        IEntityModel entityModel = this.metaService.getEntityModel(this.context.getEntityDefine().getId());
        IEntityAttribute codeField = entityModel.getCodeField();
        IEntityAttribute nameField = entityModel.getNameField();
        if (keywords.length == 1) {
            return String.format("%s OR %s", this.matchFunction(keywords[0], tableName, nameField.getCode()), this.matchFunction(keywords[0], tableName, codeField.getCode()));
        }
        return String.format("%s OR %s", this.matchFunction(keywords[0], tableName, codeField.getCode()), this.matchFunction(keywords[1], tableName, nameField.getCode()));
    }

    private String matchFunction(String keyword, String tableName, String fieldCode) {
        return String.format("POS('%s', %s[%s]) > 0", keyword, tableName, fieldCode);
    }

    protected IBaseNodeData madeSearchNodeData(IEntityRow row) {
        BaseNodeDataImpl impl = new BaseNodeDataImpl();
        impl.putKey(row.getEntityKeyData());
        impl.putCode(row.getCode());
        impl.putTitle(row.getTitle());
        impl.setPath((String[])JavaBeanUtils.appendElement((Object[])row.getParentsEntityKeyDataPath(), (Object[])new String[]{row.getEntityKeyData()}));
        return impl;
    }
}


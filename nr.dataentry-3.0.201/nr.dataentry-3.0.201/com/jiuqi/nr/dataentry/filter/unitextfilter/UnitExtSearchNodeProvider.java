/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.dataentity_ext.api.IEntityDataService
 *  com.jiuqi.nr.dataentity_ext.common.EntityDataException
 *  com.jiuqi.nr.dataentity_ext.dto.EntityDataType
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO
 *  com.jiuqi.nr.dataentity_ext.dto.PageInfo
 *  com.jiuqi.nr.dataentity_ext.dto.QueryParam
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 */
package com.jiuqi.nr.dataentry.filter.unitextfilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.dataentity_ext.api.IEntityDataService;
import com.jiuqi.nr.dataentity_ext.common.EntityDataException;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.dataentity_ext.dto.PageInfo;
import com.jiuqi.nr.dataentity_ext.dto.QueryParam;
import com.jiuqi.nr.dataentry.filter.unitextfilter.EntityRowExt;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnitExtSearchNodeProvider
implements ISearchNodeProvider {
    protected int totalSize;
    private IUnitTreeContext context;
    private IEntityDataService iEntityDataService;
    private String resourceId;

    private QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.resourceId);
        if (this.context.getCustomVariable().toMap().containsKey("types")) {
            List types = (List)this.context.getCustomVariable().toMap().get("types");
            List entityDataTypes = types.stream().map(EntityDataType::getByCode).collect(Collectors.toList());
            queryParam.setTypes(entityDataTypes);
        }
        return queryParam;
    }

    public UnitExtSearchNodeProvider(IUnitTreeContext context, IEntityDataService iEntityDataService) {
        this.context = context;
        this.iEntityDataService = iEntityDataService;
        this.resourceId = context.getCustomVariable().toMap().get("resourceId").toString();
    }

    private List<IEntityRow> filter(List<IEntityRow> source) {
        List soruceKeys = source.stream().map(IEntityItem::getParentEntityKey).collect(Collectors.toList());
        QueryParam queryParam = this.getQueryParam();
        queryParam.setKeys(soruceKeys);
        try {
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).listAllRows();
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTotalSize() {
        return this.totalSize;
    }

    public List<IBaseNodeData> getTotalPage(String[] keywords) {
        ArrayList<String> keywordsList = new ArrayList<String>(Arrays.asList(keywords));
        QueryParam queryParam = this.getQueryParam();
        queryParam.setKeyWords(keywordsList);
        try {
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).listAllRows();
            this.totalSize = iEntityDataDTOS.size();
            return iEntityDataDTOS.stream().map(this::madeSearchNodeData).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IBaseNodeData> getOnePage(String[] keywords, int pageSize, int currentPage) {
        ArrayList<String> keywordsList = new ArrayList<String>(Arrays.asList(keywords));
        QueryParam queryParam = this.getQueryParam();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRowsPerPage(pageSize);
        pageInfo.setPageIndex(currentPage);
        queryParam.setPageInfo(pageInfo);
        queryParam.setKeyWords(keywordsList);
        try {
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).listAllRows();
            this.totalSize = iEntityDataDTOS.size();
            return iEntityDataDTOS.stream().map(this::madeSearchNodeData).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    private IBaseNodeData madeSearchNodeData(IEntityDataDTO row) {
        BaseNodeDataImpl impl = new BaseNodeDataImpl();
        impl.putKey(row.getKey());
        impl.putCode(row.getCode());
        impl.putTitle(row.getTitle());
        impl.setPath(row.getPath());
        return impl;
    }
}


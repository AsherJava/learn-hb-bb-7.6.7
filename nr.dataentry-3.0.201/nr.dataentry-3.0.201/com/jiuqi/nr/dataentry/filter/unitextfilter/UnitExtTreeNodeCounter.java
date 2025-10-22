/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter
 *  com.jiuqi.nr.dataentity_ext.api.IEntityDataService
 *  com.jiuqi.nr.dataentity_ext.common.EntityDataException
 *  com.jiuqi.nr.dataentity_ext.dto.EntityDataType
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO
 *  com.jiuqi.nr.dataentity_ext.dto.QueryParam
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuqi.nr.dataentry.filter.unitextfilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuqi.nr.dataentity_ext.api.IEntityDataService;
import com.jiuqi.nr.dataentity_ext.common.EntityDataException;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.dataentity_ext.dto.QueryParam;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UnitExtTreeNodeCounter
implements IUnitTreeNodeCounter {
    protected IUnitTreeContext context;
    private String resourceId;
    private IEntityDataService iEntityDataService;

    private QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.resourceId);
        if (this.context.getCustomVariable().toMap().containsKey("types")) {
            List types = (List)this.context.getCustomVariable().toMap().get("types");
            List entityDataTypes = types.stream().map(EntityDataType::getByCode).collect(Collectors.toList());
            queryParam.setTypes(entityDataTypes);
        }
        return queryParam;
    }

    public UnitExtTreeNodeCounter(IUnitTreeContext context, IEntityDataService iEntityDataService) {
        this.context = context;
        this.resourceId = context.getCustomVariable().toMap().get("resourceId").toString();
        this.iEntityDataService = iEntityDataService;
    }

    public Map<String, Integer> getRootNodeCountMap() {
        QueryParam queryParam = this.getQueryParam();
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        try {
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).listRootRows();
            for (IEntityDataDTO iEntityDataDTO : iEntityDataDTOS) {
                result.put(iEntityDataDTO.getKey(), this.iEntityDataService.getEntityQuery(queryParam).countAllChildRows(iEntityDataDTO.getKey()));
            }
            return result;
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Integer> getChildNodeCountMap(IBaseNodeData parent) {
        QueryParam queryParam = this.getQueryParam();
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        try {
            ArrayList<String> parents = new ArrayList<String>();
            parents.add(parent.getKey());
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).getChildRows(parents);
            for (IEntityDataDTO iEntityDataDTO : iEntityDataDTOS) {
                result.put(iEntityDataDTO.getKey(), this.iEntityDataService.getEntityQuery(queryParam).countAllChildRows(iEntityDataDTO.getKey()));
            }
            return result;
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Integer> getTreeNodeCountMap(IBaseNodeData locateNode) {
        if (locateNode == null) {
            return this.getRootNodeCountMap();
        }
        QueryParam queryParam = this.getQueryParam();
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        try {
            result.put(locateNode.getKey(), this.iEntityDataService.getEntityQuery(queryParam).countAllChildRows(locateNode.getKey()));
            return result;
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }
}


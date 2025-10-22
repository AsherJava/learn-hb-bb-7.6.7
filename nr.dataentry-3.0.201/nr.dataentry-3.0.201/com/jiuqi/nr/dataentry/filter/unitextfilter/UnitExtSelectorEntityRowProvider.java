/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.dataentity_ext.api.IEntityDataService
 *  com.jiuqi.nr.dataentity_ext.common.EntityDataException
 *  com.jiuqi.nr.dataentity_ext.dto.EntityDataType
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO
 *  com.jiuqi.nr.dataentity_ext.dto.QueryParam
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider
 */
package com.jiuqi.nr.dataentry.filter.unitextfilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.dataentity_ext.api.IEntityDataService;
import com.jiuqi.nr.dataentity_ext.common.EntityDataException;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.dataentity_ext.dto.QueryParam;
import com.jiuqi.nr.dataentry.filter.unitextfilter.EntityRowExt;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class UnitExtSelectorEntityRowProvider
implements IUSelectorEntityRowProvider {
    protected IUnitTreeContext context;
    private String resourceId;
    private IEntityDataService iEntityDataService;

    private QueryParam getQueryParam() {
        List types;
        List entityDataTypes;
        QueryParam queryParam = new QueryParam(this.resourceId);
        if (this.context.getCustomVariable().toMap().containsKey("types") && (entityDataTypes = (types = (List)this.context.getCustomVariable().toMap().get("types")).stream().map(EntityDataType::getByCode).collect(Collectors.toList())).size() > 0) {
            List filtTypes = entityDataTypes.stream().filter(item -> item.getCode() != EntityDataType.EXIST_DISABLE.getCode()).collect(Collectors.toList());
            queryParam.setTypes(filtTypes);
            return queryParam;
        }
        ArrayList<EntityDataType> entityDataTypes2 = new ArrayList<EntityDataType>();
        entityDataTypes2.add(EntityDataType.EXIST);
        entityDataTypes2.add(EntityDataType.NOT_EXIST);
        queryParam.setTypes(entityDataTypes2);
        return queryParam;
    }

    public UnitExtSelectorEntityRowProvider(IUnitTreeContext context, IEntityDataService iEntityDataService) {
        this.context = context;
        this.resourceId = context.getCustomVariable().toMap().get("resourceId").toString();
        this.iEntityDataService = iEntityDataService;
    }

    public int getTotalCount() {
        try {
            QueryParam queryParam = this.getQueryParam();
            return this.iEntityDataService.getEntityQuery(queryParam).getTotalCount();
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getParentsEntityKeyDataPath(String rowKey) {
        try {
            QueryParam queryParam = this.getQueryParam();
            return this.iEntityDataService.getEntityQuery(queryParam).queryByKey(rowKey).getPath();
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasParent(String rowKey) {
        try {
            QueryParam queryParam = this.getQueryParam();
            IEntityDataDTO entityDataDTO = this.iEntityDataService.getEntityQuery(queryParam).queryByKey(rowKey);
            String[] path = entityDataDTO.getPath();
            return path != null && path.length != 0 && !path[0].equals(entityDataDTO.getKey());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isLeaf(String rowKey) {
        try {
            QueryParam queryParam = this.getQueryParam();
            return this.iEntityDataService.getEntityQuery(queryParam).queryByKey(rowKey).isLeaf();
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public IEntityRow findEntityRow(String rowKey) {
        try {
            QueryParam queryParam = this.getQueryParam();
            EntityRowExt entityRowExt = new EntityRowExt(this.iEntityDataService.getEntityQuery(queryParam).queryByKey(rowKey));
            return entityRowExt;
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getAllRows() {
        try {
            QueryParam queryParam = this.getQueryParam();
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).listAllRows();
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getAllLeaveRows() {
        try {
            QueryParam queryParam = this.getQueryParam();
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).listAllLeafRows();
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getAllNonLeaveRows() {
        try {
            QueryParam queryParam = this.getQueryParam();
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).listAllNonLeafRows();
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getChildRows(List<String> parents) {
        try {
            QueryParam queryParam = this.getQueryParam();
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).getChildRows(parents);
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getChildRowsAndSelf(List<String> parents) {
        try {
            QueryParam queryParam = this.getQueryParam();
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).getChildRowsAndSelf(parents);
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getAllChildRows(List<String> parents) {
        try {
            QueryParam queryParam = this.getQueryParam();
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).getAllChildRows(parents);
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getAllChildRowsAndSelf(List<String> parents) {
        try {
            QueryParam queryParam = this.getQueryParam();
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).getAllChildRowsAndSelf(parents);
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getAllChildLeaveRows(List<String> parents) {
        try {
            QueryParam queryParam = this.getQueryParam();
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).getAllChildLeaveRows(parents);
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getAllChildNonLeaveRows(List<String> parents) {
        try {
            QueryParam queryParam = this.getQueryParam();
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).getAllChildNonLeaveRows(parents);
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getAllParentRows(List<String> rowKeys) {
        List<IEntityRow> checkRows = this.getCheckRows(rowKeys);
        LinkedHashSet parentKeys = new LinkedHashSet();
        checkRows.forEach(row -> parentKeys.addAll(Arrays.asList(row.getParentsEntityKeyDataPath())));
        return this.getCheckRows(new ArrayList<String>(parentKeys));
    }

    public List<IEntityRow> getCheckRows(List<String> rowKeys) {
        try {
            QueryParam queryParam = this.getQueryParam();
            queryParam.setKeys(rowKeys);
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).listAllRows();
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> filterByFormulas(String ... expressions) {
        return null;
    }

    public List<IEntityRow> filterByFormulas(IUnitTreeContext context, String ... expressions) {
        return null;
    }

    public List<IEntityRow> getContinueNodeAndAllChildren(List<String> rangeNodeKeys) {
        return new ArrayList<IEntityRow>();
    }

    public List<IEntityRow> getContinueNode(List<String> rangeNodeKeys) {
        return new ArrayList<IEntityRow>();
    }
}


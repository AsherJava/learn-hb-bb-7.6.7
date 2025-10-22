/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuqi.nr.dataentity_ext.api.IEntityDataService
 *  com.jiuqi.nr.dataentity_ext.common.EntityDataException
 *  com.jiuqi.nr.dataentity_ext.dto.EntityDataType
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO
 *  com.jiuqi.nr.dataentity_ext.dto.QueryParam
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuqi.nr.dataentry.filter.unitextfilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuqi.nr.dataentity_ext.api.IEntityDataService;
import com.jiuqi.nr.dataentity_ext.common.EntityDataException;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.dataentity_ext.dto.QueryParam;
import com.jiuqi.nr.dataentry.filter.unitextfilter.EntityRowExt;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class UnitExtTreeEntityRowProvider
implements IUnitTreeEntityRowProvider {
    protected IUnitTreeContext context;
    private String resourceId;
    @Autowired
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

    public UnitExtTreeEntityRowProvider(IUnitTreeContext context, IEntityDataService iEntityDataService) {
        this.context = context;
        this.resourceId = context.getCustomVariable().toMap().get("resourceId").toString();
        this.iEntityDataService = iEntityDataService;
    }

    public List<IEntityRow> getRootRows() {
        QueryParam queryParam = this.getQueryParam();
        try {
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).listRootRows();
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getChildRows(IBaseNodeData parent) {
        QueryParam queryParam = this.getQueryParam();
        try {
            ArrayList<String> parents = new ArrayList<String>();
            parents.add(parent.getKey());
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).getChildRows(parents);
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public IEntityTable getStructTreeRows() {
        return null;
    }

    public String[] getNodePath(IBaseNodeData rowData) {
        QueryParam queryParam = this.getQueryParam();
        try {
            IEntityDataDTO iEntityDataDTO = this.iEntityDataService.getEntityQuery(queryParam).queryByKey(rowData.getKey());
            return iEntityDataDTO.getPath();
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public IEntityRow findEntityRow(IBaseNodeData rowData) {
        QueryParam queryParam = this.getQueryParam();
        try {
            IEntityDataDTO iEntityDataDTO = this.iEntityDataService.getEntityQuery(queryParam).queryByKey(rowData.getKey());
            return new EntityRowExt(iEntityDataDTO);
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IEntityRow> getAllRows() {
        QueryParam queryParam = this.getQueryParam();
        try {
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).listAllRows();
            return iEntityDataDTOS.stream().map(EntityRowExt::new).collect(Collectors.toList());
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isLeaf(IBaseNodeData rowData) {
        QueryParam queryParam = this.getQueryParam();
        try {
            IEntityDataDTO iEntityDataDTO = this.iEntityDataService.getEntityQuery(queryParam).queryByKey(rowData.getKey());
            return iEntityDataDTO.isLeaf();
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public int getDirectChildCount(IBaseNodeData parent) {
        QueryParam queryParam = this.getQueryParam();
        try {
            ArrayList<String> parents = new ArrayList<String>();
            parents.add(parent.getKey());
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).getChildRows(parents);
            return iEntityDataDTOS.size();
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    public int getAllChildCount(IBaseNodeData parent) {
        QueryParam queryParam = this.getQueryParam();
        try {
            ArrayList<String> parents = new ArrayList<String>();
            parents.add(parent.getKey());
            List iEntityDataDTOS = this.iEntityDataService.getEntityQuery(queryParam).getAllChildRows(parents);
            return iEntityDataDTOS.size();
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }
}


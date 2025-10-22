/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.internal;

import com.jiuqi.nr.dataentity_ext.api.IEntityQuery;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataDTO;
import com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow;
import com.jiuqi.nr.dataentity_ext.dto.QueryParam;
import com.jiuqi.nr.dataentity_ext.internal.EntityDataDTO;
import com.jiuqi.nr.dataentity_ext.internal.db.EntityDataDO;
import com.jiuqi.nr.dataentity_ext.internal.db.EntityDataDao;
import com.jiuqi.nr.dataentity_ext.internal.db.QueryModel;
import com.jiuqi.nr.dataentity_ext.internal.db.TypeCount;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

public class EntityQueryImpl
implements IEntityQuery {
    private static final Logger log = LoggerFactory.getLogger(EntityQueryImpl.class);
    private final QueryParam queryParam;
    private final EntityDataDao entityDataDao;

    public EntityQueryImpl(QueryParam queryParam, EntityDataDao dao) {
        this.queryParam = queryParam;
        this.entityDataDao = dao;
    }

    @Override
    public List<IEntityDataDTO> listAllRows() {
        QueryModel queryModel = this.getQueryModel(this.queryParam);
        return this.getEntityDataDTOList(queryModel);
    }

    @Override
    public int getTotalCount() {
        QueryModel queryModel = this.getQueryModel(this.queryParam);
        return this.count(queryModel);
    }

    @Override
    public IEntityDataDTO queryByKey(String key) {
        List<String> keys = this.queryParam.getKeys();
        if (keys != null && !keys.contains(key)) {
            return null;
        }
        EntityDataDO entityDataDO = this.entityDataDao.selectByKey(this.queryParam.getResourceId(), key);
        return new EntityDataDTO(entityDataDO);
    }

    @Override
    public List<IEntityDataDTO> listAllLeafRows() {
        QueryModel queryModel = this.getQueryModel(this.queryParam);
        queryModel.setLeaf(true);
        return this.getEntityDataDTOList(queryModel);
    }

    @Override
    public List<IEntityDataDTO> listAllNonLeafRows() {
        QueryModel queryModel = this.getQueryModel(this.queryParam);
        queryModel.setLeaf(false);
        return this.getEntityDataDTOList(queryModel);
    }

    @Override
    public List<IEntityDataDTO> listRootRows() {
        QueryModel queryModel = this.getQueryModel(this.queryParam);
        queryModel.setParents(Collections.singletonList("-"));
        return this.getEntityDataDTOList(queryModel);
    }

    @Override
    public List<IEntityDataDTO> getChildRows(List<String> parents) {
        QueryModel queryModel = this.getQueryModel(this.queryParam);
        return this.getChildRows(parents, queryModel);
    }

    private List<IEntityDataDTO> getChildRows(List<String> parents, QueryModel queryModel) {
        queryModel.setParents(parents);
        return this.getEntityDataDTOList(queryModel);
    }

    @Override
    public int countChildRows(String parent) {
        return this.entityDataDao.selectDirChildCount(this.queryParam.getResourceId(), parent);
    }

    @Override
    public List<IEntityDataDTO> getChildRowsAndSelf(List<String> parents) {
        List<IEntityDataDTO> childRows = this.getChildRows(parents);
        List<IEntityDataDTO> checkRows = this.getCheckRows(parents);
        childRows.addAll(checkRows);
        return childRows;
    }

    @Override
    public List<IEntityDataDTO> getAllChildRows(List<String> parents) {
        return this.getAllChildRowsByParentRows(parents);
    }

    @Override
    public int countAllChildRows(String parent) {
        return this.entityDataDao.selectAllChildCount(this.queryParam.getResourceId(), parent);
    }

    @Override
    public List<IEntityDataDTO> getAllChildRowsAndSelf(List<String> parents) {
        List<IEntityDataDTO> checkRows = this.getCheckRows(parents);
        List<IEntityDataDTO> result = this.getAllChildRowsByParentRows(parents);
        result.addAll(checkRows);
        return result;
    }

    @Override
    public List<IEntityDataDTO> getAllChildLeaveRows(List<String> parents) {
        List<IEntityDataDTO> allChildRows = this.getAllChildRows(parents);
        return allChildRows.stream().filter(IEntityDataDTO::isLeaf).collect(Collectors.toList());
    }

    @Override
    public List<IEntityDataDTO> getAllChildNonLeaveRows(List<String> parents) {
        List<IEntityDataDTO> allChildRows = this.getAllChildRows(parents);
        return allChildRows.stream().filter(o -> !o.isLeaf()).collect(Collectors.toList());
    }

    @Override
    public List<IEntityDataDTO> getAllParentRows(List<String> rowKeys) {
        List<IEntityDataDTO> checkRows = this.getCheckRows(rowKeys);
        return this.getAllParentRowsByChildRows(checkRows);
    }

    @Override
    public List<IEntityDataDTO> getCheckRows(List<String> rowKeys) {
        if (CollectionUtils.isEmpty(rowKeys)) {
            return Collections.emptyList();
        }
        ArrayList<String> checkKeys = new ArrayList<String>(rowKeys);
        if (!CollectionUtils.isEmpty(this.queryParam.getKeys())) {
            checkKeys.retainAll(this.queryParam.getKeys());
        }
        QueryModel queryModel = this.getQueryModel(this.queryParam);
        queryModel.setKeys(checkKeys);
        return this.getEntityDataDTOList(queryModel);
    }

    @Override
    public Map<EntityDataType, Integer> countTypes() {
        QueryModel queryModel = this.getQueryModel(this.queryParam);
        List<TypeCount> typeCounts = null;
        try {
            typeCounts = this.entityDataDao.countType(queryModel);
        }
        catch (SQLException e) {
            log.error(e.getMessage());
        }
        if (CollectionUtils.isEmpty(typeCounts)) {
            return Collections.emptyMap();
        }
        HashMap<EntityDataType, Integer> typeCountMap = new HashMap<EntityDataType, Integer>();
        for (TypeCount typeCount : typeCounts) {
            typeCountMap.put(EntityDataType.getByCode(typeCount.getType()), typeCount.getCount());
        }
        return typeCountMap;
    }

    private QueryModel getQueryModel(QueryParam queryParam) {
        QueryModel queryModel = new QueryModel(queryParam.getResourceId());
        queryModel.setKeys(queryParam.getKeys());
        queryModel.setTypes(queryParam.getTypes());
        queryModel.setKeyWords(queryParam.getKeyWords());
        queryModel.setSort(queryParam.isSort());
        queryModel.setPageInfo(queryParam.getPageInfo());
        return queryModel;
    }

    @NonNull
    private List<IEntityDataDTO> getEntityDataDTOList(QueryModel queryModel) {
        List<EntityDataDO> list = null;
        try {
            list = this.entityDataDao.list(queryModel);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(EntityDataDTO::new).collect(Collectors.toList());
    }

    private int count(QueryModel queryModel) {
        try {
            return this.entityDataDao.count(queryModel);
        }
        catch (SQLException e) {
            log.error(e.getMessage(), e);
            return 0;
        }
    }

    private List<IEntityDataDTO> getAllChildRowsByParentRows(List<String> parents) {
        ArrayList<IEntityDataDTO> allChildRows = new ArrayList<IEntityDataDTO>();
        QueryModel queryModel = this.getQueryModel(this.queryParam);
        queryModel.setTypes(Collections.emptyList());
        List<IEntityDataDTO> childRows = this.getChildRows(parents, queryModel);
        while (!CollectionUtils.isEmpty(childRows)) {
            allChildRows.addAll(childRows.stream().filter(o -> CollectionUtils.isEmpty(this.queryParam.getTypes()) || this.queryParam.getTypes().contains((Object)o.getType())).collect(Collectors.toList()));
            childRows = this.getChildRows(childRows.stream().map(IEntityDataRow::getKey).collect(Collectors.toList()), queryModel);
        }
        return allChildRows;
    }

    private List<IEntityDataDTO> getAllParentRowsByChildRows(List<IEntityDataDTO> childRows) {
        List<String> parentKeys = EntityQueryImpl.getAllParentKeys(childRows);
        if (CollectionUtils.isEmpty(parentKeys)) {
            return Collections.emptyList();
        }
        return this.getCheckRows(parentKeys);
    }

    @NonNull
    private static List<String> getAllParentKeys(List<IEntityDataDTO> childRows) {
        ArrayList<String> parentKeys = new ArrayList<String>();
        for (IEntityDataDTO checkRow : childRows) {
            String[] parentPath = checkRow.getPath();
            if (parentPath == null) continue;
            for (String parent : parentPath) {
                if (parentKeys.contains(parent)) continue;
                parentKeys.add(parent);
            }
        }
        return parentKeys;
    }

    private List<String> getAllChildKeys(List<String> parentKeys) {
        QueryModel queryModel = this.getQueryModel(this.queryParam);
        queryModel.setParents(parentKeys);
        ArrayList<String> r = new ArrayList<String>();
        try {
            List<String> childKeys = this.entityDataDao.listKeys(queryModel);
            while (!CollectionUtils.isEmpty(childKeys)) {
                r.addAll(childKeys);
                queryModel.setParents(childKeys);
                childKeys = this.entityDataDao.listKeys(queryModel);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return r;
    }
}


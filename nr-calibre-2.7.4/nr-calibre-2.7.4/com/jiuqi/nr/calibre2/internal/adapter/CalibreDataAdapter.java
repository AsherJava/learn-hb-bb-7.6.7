/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.exception.EntityUpdateException
 *  com.jiuqi.nr.entity.engine.result.EntityCheckResult
 *  com.jiuqi.nr.entity.engine.result.EntityDataRow
 *  com.jiuqi.nr.entity.engine.result.EntityResultSet
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 *  com.jiuqi.nr.entity.param.IEntityDeleteParam
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 *  com.jiuqi.nr.entity.param.IEntityUpdateParam
 */
package com.jiuqi.nr.calibre2.internal.adapter;

import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.BatchCalibreDataOptionsDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreExpressionDO;
import com.jiuqi.nr.calibre2.internal.adapter.CalibreResultSet;
import com.jiuqi.nr.calibre2.internal.adapter.CalibreTreeData;
import com.jiuqi.nr.calibre2.internal.dao.mapper.CalibreDataMapper;
import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.param.IEntityDeleteParam;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CalibreDataAdapter {
    @Autowired
    private ICalibreDataService calibreDataService;
    @Autowired
    private ICalibreDefineService calibreDefineService;

    private EntityResultSet buildRowsQueryResult(String tableCode, Result<List<CalibreDataDTO>> list) {
        if (list.getCode() != 1) {
            return new CalibreResultSet(tableCode, list.getData(), this.calibreDataService, this.calibreDefineService);
        }
        return this.buildRowsQueryResult(tableCode, list.getData());
    }

    private EntityResultSet buildRowsQueryResult(String tableCode, List<CalibreDataDTO> calibreData) {
        return new CalibreResultSet(tableCode, calibreData, this.calibreDataService, this.calibreDefineService);
    }

    private CalibreDataDTO buildQueryParam(IEntityQueryParam query) {
        CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
        calibreDataDTO.setCalibreCode(query.getEntityId());
        return calibreDataDTO;
    }

    public List<CalibreDataDTO> internalGetAllRows(IEntityQueryParam query) {
        CalibreDataDTO calibreDataDTO = this.buildQueryParam(query);
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataDTO);
        if (list.getCode() != 1) {
            return new ArrayList<CalibreDataDTO>();
        }
        return list.getData();
    }

    public EntityResultSet getAllRows(IEntityQueryParam query) {
        return this.findByEntityKeys(query);
    }

    public EntityResultSet getRootRows(IEntityQueryParam query) {
        CalibreDataDTO calibreDataDTO = this.buildQueryParam(query);
        calibreDataDTO.setDataTreeType(CalibreDataOption.DataTreeType.ROOT);
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataDTO);
        return this.buildRowsQueryResult(query.getEntityId(), list);
    }

    public EntityResultSet getChildRows(IEntityQueryParam query, String ... entityKeyData) {
        EntityResultSet resultSet = null;
        for (String entityKeyDatum : entityKeyData) {
            Result<List<CalibreDataDTO>> list = this.getDirectChildren(query, entityKeyDatum);
            EntityResultSet result = this.buildRowsQueryResult(query.getEntityId(), list);
            if (resultSet == null) {
                resultSet = result;
                continue;
            }
            resultSet.merge(result);
        }
        return resultSet;
    }

    private Result<List<CalibreDataDTO>> getDirectChildren(IEntityQueryParam query, String entityKeyData) {
        CalibreDataDTO calibreDataDTO = this.buildQueryParam(query);
        calibreDataDTO.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
        calibreDataDTO.setCode(entityKeyData);
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataDTO);
        return list;
    }

    public EntityResultSet getAllChildRows(IEntityQueryParam query, String entityKeyData) {
        CalibreDataDTO calibreDataDTO = this.buildQueryParam(query);
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataDTO);
        CalibreTreeData treeData = new CalibreTreeData(list.getData());
        List<CalibreDataDTO> allChildRows = treeData.getAllChildRows(entityKeyData);
        return this.buildRowsQueryResult(query.getEntityId(), allChildRows);
    }

    public EntityResultSet findByEntityKeys(IEntityQueryParam query) {
        CalibreDataDTO calibreDataDTO = this.buildQueryParam(query);
        calibreDataDTO.setCodes(query.getMasterKey());
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataDTO);
        return this.buildRowsQueryResult(query.getEntityId(), list);
    }

    public String getParent(IEntityQueryParam query, String queryCode) {
        CalibreDataDTO calibreDataDTO = this.buildQueryParam(query);
        calibreDataDTO.setCode(queryCode);
        Result<List<CalibreDataDTO>> list = this.calibreDataService.list(calibreDataDTO);
        if (list.getCode() != 1) {
            return null;
        }
        List<CalibreDataDTO> data = list.getData();
        if (data.size() > 1) {
            return null;
        }
        return data.get(0).getParent();
    }

    public int getMaxDepth(IEntityQueryParam query) {
        List<CalibreDataDTO> allRows = this.internalGetAllRows(query);
        CalibreTreeData treeData = new CalibreTreeData(allRows);
        int treeDepth = 1;
        List<CalibreDataDTO> rootRows = treeData.getRootRows();
        for (CalibreDataDTO rootRow : rootRows) {
            int currentDepth = this.getMaxDepthByEntityKey(treeData, rootRow.getCode());
            if (treeDepth >= currentDepth) continue;
            treeDepth = currentDepth;
        }
        return treeDepth;
    }

    public int getMaxDepthByEntityKey(CalibreTreeData treeData, String entityKeyData) {
        int treeDepth = 1;
        List<CalibreDataDTO> childrenRows = treeData.getChildRows(entityKeyData);
        if (childrenRows.size() <= 0 || StringUtils.isEmpty(entityKeyData)) {
            return treeDepth;
        }
        HashMap<String, String> parentCache = new HashMap<String, String>();
        parentCache.put(entityKeyData, null);
        for (CalibreDataDTO childRow : childrenRows) {
            String entityKey = childRow.getCode();
            if (StringUtils.isEmpty(entityKey) || parentCache.containsKey(entityKey)) continue;
            parentCache.put(entityKey, childRow.getParent());
        }
        for (CalibreDataDTO childRow : childrenRows) {
            String parentCode = childRow.getParent();
            if (StringUtils.isEmpty(parentCode) || !parentCache.containsKey(parentCode)) continue;
            HashSet<String> parentKeys = new HashSet<String>();
            this.getParentKeys(parentKeys, parentCode, parentCache);
            int currentDepth = parentKeys.size() + 1;
            if (currentDepth <= treeDepth) continue;
            treeDepth = currentDepth;
        }
        return treeDepth;
    }

    private void getParentKeys(HashSet<String> parentKeys, String parentCode, HashMap<String, String> parentCache) {
        if (parentKeys.contains(parentCode)) {
            return;
        }
        parentKeys.add(parentCode);
        if (StringUtils.isEmpty(parentCode) || !parentCache.containsKey(parentCode)) {
            return;
        }
        String newParentCode = parentCache.get(parentCode);
        if (StringUtils.isEmpty(newParentCode) || !parentCache.containsKey(newParentCode)) {
            return;
        }
        this.getParentKeys(parentKeys, newParentCode, parentCache);
    }

    public int getMaxDepthByEntityKey(IEntityQueryParam query, String entityKeyData) {
        List<CalibreDataDTO> allRows = this.internalGetAllRows(query);
        CalibreTreeData treeData = new CalibreTreeData(allRows);
        return this.getMaxDepthByEntityKey(treeData, entityKeyData);
    }

    public EntityResultSet findByCode(IEntityQueryParam query) {
        return this.findByEntityKeys(query);
    }

    public int getDirectChildCount(IEntityQueryParam query, String entityKeyData) {
        Result<List<CalibreDataDTO>> directChildren = this.getDirectChildren(query, entityKeyData);
        if (directChildren.getCode() != 1) {
            return 0;
        }
        return directChildren.getData().size();
    }

    public int getAllChildCount(IEntityQueryParam query, String entityKeyData) {
        List<CalibreDataDTO> allRows = this.internalGetAllRows(query);
        CalibreTreeData treeData = new CalibreTreeData(allRows);
        return treeData.getAllChildCount(entityKeyData);
    }

    public Map<String, Integer> getDirectChildCountByParent(IEntityQueryParam query, String parentKey) {
        List<CalibreDataDTO> allRows = this.internalGetAllRows(query);
        CalibreTreeData treeData = new CalibreTreeData(allRows);
        return treeData.getDirectChildCountByParent(parentKey);
    }

    public Map<String, Integer> getAllChildCountByParent(IEntityQueryParam query, String parentKey) {
        List<CalibreDataDTO> allRows = this.internalGetAllRows(query);
        CalibreTreeData treeData = new CalibreTreeData(allRows);
        return treeData.getAllChildCountByParent(parentKey);
    }

    public String[] getParentsEntityKeyDataPath(IEntityQueryParam query, String entityKeyData) {
        String directParent;
        String[] parentsPath = new String[]{};
        String parent = this.getParent(query, entityKeyData);
        if (parent == null) {
            return parentsPath;
        }
        ArrayList<String> parentInfos = new ArrayList<String>();
        while (StringUtils.hasText(directParent = this.getParent(query, parent)) && !parentInfos.contains(directParent)) {
            parentInfos.add(directParent);
        }
        int count = parentInfos.size();
        String[] result = new String[count];
        for (int i = 0; i < count; ++i) {
            result[count - i - 1] = (String)parentInfos.get(i);
        }
        return result;
    }

    public String[] getParentsEntityKeyDataPath(IEntityQueryParam query, Map<String, Object> rowData) {
        Object code = rowData.get(CalibreDataMapper.FIELD_CODE);
        if (code == null) {
            return new String[0];
        }
        return this.getParentsEntityKeyDataPath(query, code.toString());
    }

    public int getTotalCount(IEntityQueryParam query) {
        return this.internalGetAllRows(query).size();
    }

    public EntityUpdateResult insertRows(IEntityUpdateParam addParam) throws EntityUpdateException {
        BatchCalibreDataOptionsDTO batchOptions = new BatchCalibreDataOptionsDTO();
        batchOptions.setDefineCode(addParam.getEntityId());
        List<CalibreDataDTO> calibreDataDTOS = batchOptions.getCalibreDataDTOS();
        batchOptions.setCalibreDataDTOS(this.buildOptionData(addParam.getModifyRows()));
        Result<List<UpdateResult>> listResult = this.calibreDataService.batchAdd(batchOptions);
        return this.getEntityUpdateResult(listResult);
    }

    private EntityUpdateResult getEntityUpdateResult(Result<List<UpdateResult>> listResult) {
        EntityUpdateResult updateResult = new EntityUpdateResult();
        if (listResult.getCode() == 0) {
            return updateResult;
        }
        List<UpdateResult> data = listResult.getData();
        for (UpdateResult result : data) {
            updateResult.getCodeToKey().put(result.getkey(), result.getCode());
        }
        return updateResult;
    }

    private List<CalibreDataDTO> buildOptionData(List<EntityDataRow> modifyRows) {
        ArrayList<CalibreDataDTO> calibreDataDTOS = new ArrayList<CalibreDataDTO>(modifyRows.size());
        for (EntityDataRow modifyRow : modifyRows) {
            CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
            calibreDataDTO.setCode(modifyRow.getEntityKeyData());
            calibreDataDTO.setName(modifyRow.getTitle());
            calibreDataDTO.setParent(modifyRow.getParentId());
            Object value = modifyRow.getValue(CalibreDataMapper.FIELD_VALUE);
            calibreDataDTO.setValue((CalibreExpressionDO)value);
            calibreDataDTOS.add(calibreDataDTO);
        }
        return calibreDataDTOS;
    }

    public EntityUpdateResult deleteRows(IEntityDeleteParam deleteParam) throws EntityUpdateException {
        BatchCalibreDataOptionsDTO batchOptions = new BatchCalibreDataOptionsDTO();
        batchOptions.setDefineCode(deleteParam.getEntityId());
        batchOptions.setCalibreDataDTOS(this.buildOptionData(deleteParam.getDeleteRows()));
        Result<List<UpdateResult>> listResult = this.calibreDataService.batchDelete(batchOptions, false);
        return this.getEntityUpdateResult(listResult);
    }

    public EntityUpdateResult updateRows(IEntityUpdateParam updateParam) throws EntityUpdateException {
        BatchCalibreDataOptionsDTO batchOptions = new BatchCalibreDataOptionsDTO();
        batchOptions.setDefineCode(updateParam.getEntityId());
        batchOptions.setCalibreDataDTOS(this.buildOptionData(updateParam.getModifyRows()));
        Result<List<UpdateResult>> listResult = this.calibreDataService.batchUpdate(batchOptions);
        return this.getEntityUpdateResult(listResult);
    }

    public EntityCheckResult rowsCheck(IEntityUpdateParam updateParam, boolean insert) throws EntityUpdateException {
        return new EntityCheckResult();
    }
}


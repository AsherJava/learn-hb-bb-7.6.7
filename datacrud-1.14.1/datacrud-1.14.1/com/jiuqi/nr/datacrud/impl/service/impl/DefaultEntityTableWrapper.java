/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 *  com.jiuqi.nr.entity.engine.var.PageCondition
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.datacrud.impl.service.impl;

import com.jiuqi.nr.datacrud.impl.service.impl.EntityTableDTO;
import com.jiuqi.nr.datacrud.spi.entity.IEntityTableWrapper;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.engine.var.PageCondition;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultEntityTableWrapper
implements IEntityTableWrapper {
    protected EntityTableDTO entityTableDTO;
    protected final Map<String, EntityTableDTO> entityTableCache = new HashMap<String, EntityTableDTO>();
    protected Map<String, Map<String, IEntityRow>> key2RowCache = new HashMap<String, Map<String, IEntityRow>>();
    protected Map<String, Map<String, IEntityRow>> code2RowCache = new HashMap<String, Map<String, IEntityRow>>();
    protected Map<String, List<IEntityRow>> allRowsCache = new HashMap<String, List<IEntityRow>>();

    @Override
    public void putIEntityTable(String cacheKey, IEntityTable entityTable) {
        if (cacheKey == null) {
            this.entityTableDTO = new EntityTableDTO(null, entityTable);
        } else {
            this.entityTableCache.put(cacheKey, new EntityTableDTO(cacheKey, entityTable));
        }
    }

    @Override
    public boolean resetIEntityTable(String cacheKey) {
        if (cacheKey == null) {
            return this.entityTableDTO != null;
        }
        this.entityTableDTO = this.entityTableCache.get(cacheKey);
        return this.entityTableDTO != null;
    }

    @Override
    public IEntityTable getIEntityTable(String cacheKey) {
        if (cacheKey == null && this.entityTableDTO != null) {
            return this.entityTableDTO.getEntityTable();
        }
        EntityTableDTO entityTableDTO = this.entityTableCache.get(cacheKey);
        if (entityTableDTO != null) {
            return entityTableDTO.getEntityTable();
        }
        return null;
    }

    @Override
    public List<IEntityRow> getAllRows() {
        String cacheKey = this.entityTableDTO.getCacheKey();
        if (cacheKey == null) {
            return this.entityTableDTO.getEntityTable().getAllRows();
        }
        return this.allRowsCache.computeIfAbsent(cacheKey, k -> {
            List rows = this.entityTableDTO.getEntityTable().getAllRows();
            Map keyCache = this.key2RowCache.computeIfAbsent(cacheKey, key -> new HashMap());
            for (IEntityRow allRow : rows) {
                keyCache.put(allRow.getEntityKeyData(), allRow);
            }
            Map codeCache = this.code2RowCache.computeIfAbsent(cacheKey, key -> new HashMap());
            for (IEntityRow allRow : rows) {
                codeCache.put(allRow.getCode(), allRow);
            }
            return rows;
        });
    }

    @Override
    public List<IEntityRow> getRootRows() {
        return this.entityTableDTO.getEntityTable().getRootRows();
    }

    @Override
    public List<IEntityRow> getChildRows(String entityKeyData) {
        return this.entityTableDTO.getEntityTable().getChildRows(entityKeyData);
    }

    @Override
    public List<IEntityRow> getAllChildRows(String entityKeyData) {
        return this.entityTableDTO.getEntityTable().getAllChildRows(entityKeyData);
    }

    @Override
    public IEntityRow findByEntityKey(String entityKeyData) {
        String cacheKey = this.entityTableDTO.getCacheKey();
        if (cacheKey == null) {
            return this.entityTableDTO.getEntityTable().findByEntityKey(entityKeyData);
        }
        Map key2Row = this.key2RowCache.computeIfAbsent(cacheKey, k -> new HashMap());
        if (key2Row.containsKey(entityKeyData)) {
            return (IEntityRow)key2Row.get(entityKeyData);
        }
        IEntityRow row = this.entityTableDTO.getEntityTable().findByEntityKey(entityKeyData);
        key2Row.put(entityKeyData, row);
        return row;
    }

    @Override
    public Map<String, IEntityRow> findByEntityKeys(Set<String> entityKeyDatas) {
        String cacheKey = this.entityTableDTO.getCacheKey();
        if (cacheKey == null) {
            return this.entityTableDTO.getEntityTable().findByEntityKeys(entityKeyDatas);
        }
        HashMap<String, IEntityRow> res = new HashMap<String, IEntityRow>();
        Map key2Row = this.key2RowCache.computeIfAbsent(cacheKey, k -> new HashMap());
        HashSet<String> unFindKeys = null;
        for (String entityKeyData : entityKeyDatas) {
            if (key2Row.containsKey(entityKeyData)) {
                res.put(entityKeyData, (IEntityRow)key2Row.get(entityKeyData));
                continue;
            }
            if (unFindKeys == null) {
                unFindKeys = new HashSet<String>();
            }
            unFindKeys.add(entityKeyData);
        }
        if (unFindKeys != null) {
            Map byEntityKeys = this.entityTableDTO.getEntityTable().findByEntityKeys(unFindKeys);
            key2Row.putAll(byEntityKeys);
            res.putAll(byEntityKeys);
        }
        return res;
    }

    @Override
    public int getMaxDepth() {
        return this.entityTableDTO.getEntityTable().getMaxDepth();
    }

    @Override
    public int getMaxDepthByEntityKey(String entityKeyData) {
        return this.entityTableDTO.getEntityTable().getMaxDepthByEntityKey(entityKeyData);
    }

    @Override
    public IEntityRow findByCode(String codeValue) {
        String cacheKey = this.entityTableDTO.getCacheKey();
        if (cacheKey == null) {
            return this.entityTableDTO.getEntityTable().findByCode(codeValue);
        }
        Map code2Row = this.code2RowCache.computeIfAbsent(cacheKey, k -> new HashMap());
        if (code2Row.containsKey(codeValue)) {
            return (IEntityRow)code2Row.get(codeValue);
        }
        IEntityRow row = this.entityTableDTO.getEntityTable().findByCode(codeValue);
        code2Row.put(codeValue, row);
        return row;
    }

    @Override
    public List<IEntityRow> findAllByCode(String codeValue) {
        return this.entityTableDTO.getEntityTable().findAllByCode(codeValue);
    }

    @Override
    public Map<String, IEntityRow> findByCodes(List<String> codeValue) {
        return this.entityTableDTO.getEntityTable().findByCodes(codeValue);
    }

    @Override
    public Map<String, List<IEntityRow>> findAllByCodes(List<String> codeValue) {
        return this.entityTableDTO.getEntityTable().findAllByCodes(codeValue);
    }

    @Override
    public int getDirectChildCount(String entityKeyData) {
        return this.entityTableDTO.getEntityTable().getDirectChildCount(entityKeyData);
    }

    @Override
    public int getAllChildCount(String entityKeyData) {
        return this.entityTableDTO.getEntityTable().getAllChildCount(entityKeyData);
    }

    @Override
    public Map<String, Integer> getDirectChildCountByParent(String parentKey) {
        return this.entityTableDTO.getEntityTable().getDirectChildCountByParent(parentKey);
    }

    @Override
    public Map<String, Integer> getAllChildCountByParent(String parentKey) {
        return this.entityTableDTO.getEntityTable().getAllChildCountByParent(parentKey);
    }

    @Override
    public String[] getParentsEntityKeyDataPath(String entityKeyData) {
        return this.entityTableDTO.getEntityTable().getParentsEntityKeyDataPath(entityKeyData);
    }

    @Override
    public int getTotalCount() {
        return this.entityTableDTO.getEntityTable().getTotalCount();
    }

    @Override
    public List<IEntityRow> getPageRows(PageCondition pageCondition) {
        return this.entityTableDTO.getEntityTable().getPageRows(pageCondition);
    }

    @Override
    public IEntityRow quickFindByEntityKey(String entityKeyData) {
        return this.entityTableDTO.getEntityTable().quickFindByEntityKey(entityKeyData);
    }

    @Override
    public Map<String, IEntityRow> quickFindByEntityKeys(Set<String> entityKeyDatas) {
        return this.entityTableDTO.getEntityTable().quickFindByEntityKeys(entityKeyDatas);
    }

    @Override
    public IFieldsInfo getFieldsInfo() {
        return this.entityTableDTO.getEntityTable().getFieldsInfo();
    }

    @Override
    public TableModelDefine getEntityTableDefine() {
        return this.entityTableDTO.getEntityTable().getEntityTableDefine();
    }

    @Override
    public IEntityModel getEntityModel() {
        return this.entityTableDTO.getEntityTable().getEntityModel();
    }

    @Override
    public boolean isI18nAttribute(String attributeCode) {
        return this.entityTableDTO.getEntityTable().isI18nAttribute(attributeCode);
    }
}


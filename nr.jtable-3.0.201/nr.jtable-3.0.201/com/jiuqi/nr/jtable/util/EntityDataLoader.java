/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.jtable.filter.EntityFilter;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityDataLoader {
    private static final Logger logger = LoggerFactory.getLogger(EntityDataLoader.class);
    private final IEntityTable entityTable;
    private EntityQueryByViewInfo entityQueryInfo;
    private EntityFilter entityFilter;
    private final List<String> captionFields = new ArrayList<String>();
    private final List<String> dropDownFields = new ArrayList<String>();
    private final List<String> cells = new ArrayList<String>();
    private final boolean isFilter;
    private final boolean isLazy;
    private boolean queryChildrenCount = true;

    public EntityDataLoader(IEntityTable entityTable) {
        this(entityTable, false, true);
    }

    public EntityDataLoader(IEntityTable entityTable, boolean isFilter, boolean islazy) {
        this.entityTable = entityTable;
        this.isFilter = isFilter;
        this.isLazy = islazy;
    }

    public boolean IsLazy() {
        return this.isLazy;
    }

    public boolean isQueryChildrenCount() {
        return this.queryChildrenCount;
    }

    public void setQueryChildrenCount(boolean queryChildrenCount) {
        this.queryChildrenCount = queryChildrenCount;
    }

    public void setFields(EntityQueryInfo entityQueryInfo) {
        this.captionFields.addAll(entityQueryInfo.getCaptionFields());
        this.dropDownFields.addAll(entityQueryInfo.getDropDownFields());
        LinkedHashSet<String> fieldset = new LinkedHashSet<String>();
        fieldset.addAll(this.captionFields);
        fieldset.addAll(this.dropDownFields);
        this.cells.addAll(fieldset);
    }

    public List<EntityData> getEntityDatas(EntityQueryByViewInfo entityQueryInfo) {
        this.entityQueryInfo = entityQueryInfo;
        ArrayList<EntityData> entitys = new ArrayList<EntityData>();
        if (StringUtils.isNotEmpty((String)entityQueryInfo.getSearch())) {
            this.entityFilter = new EntityFilter(entityQueryInfo.getSearch(), entityQueryInfo.isFullPath(), this.cells);
            this.entityFilter.setMatchAll(entityQueryInfo.isMatchAll());
            this.entityFilter.setUseDefaultSearch(entityQueryInfo.isUseDefaultSearch());
        }
        if (StringUtils.isEmpty((String)entityQueryInfo.getParentKey())) {
            entitys.addAll(this.getRootList());
        } else {
            IEntityRow entityRow = this.getEntityRow(entityQueryInfo.getParentKey());
            if (entityRow != null) {
                entitys.addAll(this.getChildrenList(entityRow.getEntityKeyData()));
            }
        }
        return entitys;
    }

    public EntityData getEntityDataByKey(String entityDataKey) {
        EntityData entityDataInfo = null;
        IEntityRow entityRow = this.getEntityRow(entityDataKey);
        if (entityRow == null && entityDataKey.indexOf("\\|") >= 0) {
            String entityKey;
            String[] entityKeyList;
            String[] stringArray = entityKeyList = entityDataKey.split("\\|");
            int n = stringArray.length;
            for (int i = 0; i < n && (entityRow = this.getEntityRow(entityKey = stringArray[i])) == null; ++i) {
            }
        }
        if (entityRow != null) {
            entityDataInfo = this.createEntityDataInfo(entityRow);
        }
        return entityDataInfo;
    }

    public IEntityRow getEntityRow(String entityDataKey) {
        if (this.entityTable == null) {
            return null;
        }
        IEntityRow entityRow = this.isFilter ? this.entityTable.findByEntityKey(entityDataKey) : this.entityTable.quickFindByEntityKey(entityDataKey);
        if (entityRow != null) {
            return entityRow;
        }
        entityRow = this.entityTable.findByCode(entityDataKey);
        return entityRow;
    }

    public Map<String, EntityData> getEntityDataByKeys(Set<String> entityDataKeys) {
        HashMap<String, EntityData> entitys = new HashMap<String, EntityData>();
        Map findByEntityKeys = this.entityTable.quickFindByEntityKeys(entityDataKeys);
        if (findByEntityKeys != null) {
            for (String entityDataKey : findByEntityKeys.keySet()) {
                IEntityRow entityRow = (IEntityRow)findByEntityKeys.get(entityDataKey);
                if (entityRow == null) continue;
                EntityData entityDataInfo = this.createEntityDataInfo(entityRow);
                entitys.put(entityDataKey, entityDataInfo);
            }
        }
        return entitys;
    }

    private List<EntityData> getRootList() {
        ArrayList<EntityData> entitys = new ArrayList<EntityData>();
        if (this.entityTable == null) {
            return entitys;
        }
        List rootRows = this.entityTable.getRootRows();
        for (IEntityRow entityRow : rootRows) {
            boolean addParent = false;
            EntityData entityDataInfo = null;
            if (this.entityFilter == null || this.entityFilter.accept(entityRow)) {
                entityDataInfo = this.createEntityDataInfo(entityRow);
                if (!this.entityQueryInfo.isSearchLeaf() || entityDataInfo.isLeaf()) {
                    entitys.add(entityDataInfo);
                    addParent = true;
                }
            }
            if (!this.entityQueryInfo.isAllChildren()) continue;
            List<EntityData> childrenList = this.getChildrenList(entityRow.getEntityKeyData());
            if (this.entityQueryInfo.isTreeToList()) {
                entitys.addAll(childrenList);
                continue;
            }
            if (addParent) {
                entityDataInfo.getChildren().addAll(childrenList);
                continue;
            }
            entitys.addAll(childrenList);
        }
        return entitys;
    }

    private List<EntityData> getChildrenList(String parentKeyData) {
        ArrayList<EntityData> entitys = new ArrayList<EntityData>();
        if (this.entityTable == null) {
            return entitys;
        }
        List entityRows = this.entityTable.getChildRows(parentKeyData);
        for (IEntityRow entityRow : entityRows) {
            if (entityRow.getEntityKeyData().equals(parentKeyData) || entityRow.getCode().equals(parentKeyData)) {
                String errorMessage = String.format("\u57fa\u7840\u6570\u636e\u6761\u76ee\u6709\u8bef\uff01\u5f53\u524d\u57fa\u7840\u6570\u636e\u6761\u76ee\u7684code\u4e0epartentCode\u76f8\u540c\uff01\u5f53\u524d\u57fa\u7840\u6570\u636e\u6807\u8bc6\uff1a%s\uff0c\u5f53\u524d\u6761\u76eekey\uff1a%s\uff0c\u5f53\u524d\u6761\u76eecode\uff1a%s\uff0c\u5f53\u524d\u6761\u76eetitle\uff1a%s\uff0c\u5f53\u524d\u6761\u76eeparentCode\uff1a%s\uff0c\u8bf7\u68c0\u67e5\u57fa\u7840\u6570\u636e\uff01", this.entityQueryInfo.getEntityViewKey(), entityRow.getEntityKeyData(), entityRow.getCode(), entityRow.getTitle(), entityRow.getParentEntityKey());
                logger.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }
            EntityData entityDataInfo = null;
            boolean addParent = false;
            if (this.entityFilter == null || this.entityFilter.accept(entityRow)) {
                entityDataInfo = this.createEntityDataInfo(entityRow);
                if (!this.entityQueryInfo.isSearchLeaf() || entityDataInfo.isLeaf()) {
                    entitys.add(entityDataInfo);
                    addParent = true;
                }
            }
            if (!this.entityQueryInfo.isAllChildren()) continue;
            List<EntityData> childrenList = this.getChildrenList(entityRow.getEntityKeyData());
            if (this.entityQueryInfo.isTreeToList()) {
                entitys.addAll(childrenList);
                continue;
            }
            if (addParent) {
                entityDataInfo.getChildren().addAll(childrenList);
                continue;
            }
            entitys.addAll(childrenList);
        }
        return entitys;
    }

    private EntityData createEntityDataInfo(IEntityRow entityRow) {
        String[] parentspath;
        EntityData entityData = new EntityData();
        entityData.setId(entityRow.getEntityKeyData());
        entityData.setRowCaption(entityRow.getTitle());
        entityData.setCode(entityRow.getCode());
        entityData.setTitle(entityRow.getTitle());
        entityData.setParentId(entityRow.getParentEntityKey());
        Object entityOrder = entityRow.getEntityOrder();
        if (entityOrder instanceof Double) {
            entityData.setOrder((Double)entityOrder);
        }
        if ((parentspath = entityRow.getParentsEntityKeyDataPath()) != null && parentspath.length > 0) {
            entityData.setParents(Arrays.asList(parentspath));
        }
        ArrayList<String> data = new ArrayList<String>();
        for (int i = 0; i < this.cells.size(); ++i) {
            data.add("");
        }
        entityData.setData(data);
        try {
            StringBuilder rowCaption = new StringBuilder();
            for (int index = 0; index < this.cells.size(); ++index) {
                String fieldCode = this.cells.get(index);
                String fieldValue = this.getValue(fieldCode, entityRow, this.entityTable);
                data.set(index, fieldValue);
                if (this.captionFields.indexOf(fieldCode) < 0) continue;
                if (rowCaption.length() > 0) {
                    rowCaption.append("|");
                }
                rowCaption.append(fieldValue);
            }
            if (rowCaption.length() > 0) {
                entityData.setRowCaption(rowCaption.toString());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (this.queryChildrenCount) {
            int directChildCount = this.entityTable.getDirectChildCount(entityRow.getEntityKeyData());
            entityData.setLeaf(directChildCount == 0);
            entityData.setChildrenCount(directChildCount);
        } else {
            entityData.setLeaf(entityRow.isLeaf());
        }
        return entityData;
    }

    private String getValue(String fieldCode, IEntityRow entityRow, IEntityTable entityTable) {
        boolean isI18NField;
        AbstractData value = entityRow.getValue(fieldCode);
        String fieldValue = value.getAsString();
        if (StringUtils.isEmpty((String)fieldValue) && (isI18NField = entityTable.isI18nAttribute(fieldCode))) {
            fieldValue = entityRow.getTitle();
        }
        return fieldValue;
    }

    public Map<String, DimensionValue> getParentEntityKeys() {
        IEntityRow entityRow;
        if (StringUtils.isNotEmpty((String)this.entityQueryInfo.getParentKey()) && (entityRow = this.getEntityRow(this.entityQueryInfo.getParentKey())) != null) {
            return DimensionValueSetUtil.getDimensionSet(entityRow.getRowKeys());
        }
        return null;
    }

    public List<String> getCells() {
        return this.cells;
    }

    public static List<EntityData> splitEntityDatas(List<EntityData> entitys) {
        ArrayList<EntityData> entityDatas = new ArrayList<EntityData>();
        for (EntityData entity : entitys) {
            entityDatas.add(entity);
            if (entity.getChildren() == null || entity.getChildren().isEmpty()) continue;
            List<EntityData> childrenEntitys = EntityDataLoader.splitEntityDatas(entity.getChildren());
            entityDatas.addAll(childrenEntitys);
            entity.setChildren(new ArrayList<EntityData>());
        }
        return entityDatas;
    }
}


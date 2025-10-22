/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.executors.QueryInfo;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl;
import com.jiuqi.nr.entity.engine.intf.impl.EntityTableBase;
import com.jiuqi.nr.entity.engine.var.PageCondition;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class EntityTableImpl
extends EntityTableBase
implements IEntityTable {
    private static final Logger logger = LoggerFactory.getLogger(EntityTableImpl.class);
    private List<IEntityRow> allRows;
    List<IEntityRow> rootRows;
    Map<String, List<IEntityRow>> childrenRows;
    Map<String, List<IEntityRow>> notExitRows;
    Map<String, EntityRowImpl> entityKeySearch;
    Map<String, List<IEntityRow>> codeSearch;
    boolean hasBuild = false;

    public EntityTableImpl(QueryContext queryContext, QueryInfo queryInfo) {
        super(queryContext, queryInfo);
    }

    public void tableInit() {
        this.rootRows = new ArrayList<IEntityRow>();
        this.childrenRows = new HashMap<String, List<IEntityRow>>();
        this.notExitRows = new HashMap<String, List<IEntityRow>>();
        this.buildEntityKeySearch();
        String parentFiled = this.queryInfo.getTableRunInfo().getEntityModel().getParentField().getCode();
        if (StringUtils.isEmpty((String)parentFiled)) {
            this.rootRows = new ArrayList<IEntityRow>(this.internalGetAllRows());
        }
    }

    private void addToChildrenRows(EntityRowImpl entityRowImpl, String parentKey) {
        List<Object> children;
        if (!this.childrenRows.containsKey(parentKey)) {
            children = new ArrayList();
            this.childrenRows.put(parentKey, children);
        } else {
            children = this.childrenRows.get(parentKey);
        }
        children.add(entityRowImpl);
    }

    private void buildNormalTree() {
        if (this.hasBuild) {
            return;
        }
        this.queryContext.getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("\u6784\u9020\u6811\u5f62, \u5171\u8ba1:{}\u884c", this.dataRows.size());
            }
        });
        String tableName = this.queryInfo.getTableRunInfo().getTableName();
        for (EntityRowImpl dataRowImpl : this.dataRows) {
            String parentKey = dataRowImpl.getParentEntityKey();
            if (StringUtils.isEmpty((String)parentKey)) {
                this.rootRows.add(dataRowImpl);
                continue;
            }
            EntityRowImpl parentRow = this.entityKeySearch.get(parentKey);
            if (parentRow == null) {
                List<Object> children;
                this.rootRows.add(dataRowImpl);
                if (!this.notExitRows.containsKey(parentKey)) {
                    children = new ArrayList();
                    this.notExitRows.put(parentKey, children);
                } else {
                    children = this.notExitRows.get(parentKey);
                }
                children.add(dataRowImpl);
                continue;
            }
            if (parentRow.getEntityKeyData().equals(dataRowImpl.getEntityKeyData())) {
                this.rootRows.add(dataRowImpl);
                logger.error("\u5b58\u50a8\u8868[".concat(tableName).concat("]\u7684\u4e3b\u4f53[".concat(parentKey).concat("]\u81ea\u8eab\u5b58\u5728\u73af\u94fe\uff0c\u5efa\u8bae\u68c0\u67e5")));
                continue;
            }
            this.addToChildrenRows(dataRowImpl, parentRow.getEntityKeyData());
        }
        this.hasBuild = true;
        this.queryContext.getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("\u6811\u5f62\u6784\u9020\u5b8c\u6bd5, root-{}\u884c, children-{}\u884c", this.rootRows.size(), this.childrenRows.size());
            }
        });
    }

    @Override
    public List<IEntityRow> getAllRows() {
        return this.internalGetAllRows();
    }

    private List<IEntityRow> internalGetAllRows() {
        this.buildNormalTree();
        return this.allRows;
    }

    @Override
    public List<IEntityRow> getRootRows() {
        this.buildNormalTree();
        return this.rootRows;
    }

    @Override
    public List<IEntityRow> getChildRows(String entityKeyData) {
        this.buildNormalTree();
        return this.directGetChildRows(entityKeyData);
    }

    @Override
    protected List<IEntityRow> directGetChildRows(String entityKeyData) {
        this.buildNormalTree();
        ArrayList childRows = this.childrenRows.get(entityKeyData);
        return childRows == null ? new ArrayList() : childRows;
    }

    @Override
    public List<IEntityRow> getAllChildRows(String entityKeyData) {
        this.buildNormalTree();
        return this.directGetAllChildRows(entityKeyData);
    }

    @Override
    public IEntityRow findByEntityKey(String entityKeyData) {
        if (StringUtils.isEmpty((String)entityKeyData)) {
            return null;
        }
        return this.entityKeySearch.get(entityKeyData);
    }

    private void buildEntityKeySearch() {
        this.allRows = new ArrayList<IEntityRow>();
        this.entityKeySearch = new LinkedHashMap<String, EntityRowImpl>();
        for (EntityRowImpl dataRow : this.dataRows) {
            this.allRows.add(dataRow);
            this.entityKeySearch.put(dataRow.getEntityKeyData(), dataRow);
        }
    }

    @Override
    public int getMaxDepth() {
        int treeDepth = 1;
        List<IEntityRow> rootRows = this.getRootRows();
        for (IEntityRow rootRow : rootRows) {
            int currentDepth = this.getMaxDepthByEntityKey(rootRow.getEntityKeyData());
            if (treeDepth >= currentDepth) continue;
            treeDepth = currentDepth;
        }
        return treeDepth;
    }

    @Override
    public int getMaxDepthByEntityKey(String entityKeyData) {
        int treeDepth = 1;
        List<IEntityRow> childrenRows = this.getAllChildRows(entityKeyData);
        if (childrenRows.size() <= 0 || StringUtils.isEmpty((String)entityKeyData)) {
            return treeDepth;
        }
        HashMap<String, String> parentCache = new HashMap<String, String>();
        parentCache.put(entityKeyData, null);
        for (IEntityRow childRow : childrenRows) {
            String entityKey = childRow.getEntityKeyData();
            if (StringUtils.isEmpty((String)entityKey) || parentCache.containsKey(entityKey)) continue;
            parentCache.put(entityKey, childRow.getParentEntityKey());
        }
        for (IEntityRow childRow : childrenRows) {
            String parentCode = childRow.getParentEntityKey();
            if (StringUtils.isEmpty((String)parentCode) || !parentCache.containsKey(parentCode)) continue;
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
        if (StringUtils.isEmpty((String)parentCode) || !parentCache.containsKey(parentCode)) {
            return;
        }
        String newParentCode = parentCache.get(parentCode);
        if (StringUtils.isEmpty((String)newParentCode) || !parentCache.containsKey(newParentCode)) {
            return;
        }
        this.getParentKeys(parentKeys, newParentCode, parentCache);
    }

    @Override
    public IEntityRow findByCode(String codeValue) {
        List<IEntityRow> allByCode = this.findAllByCode(codeValue);
        if (!CollectionUtils.isEmpty(allByCode)) {
            return allByCode.get(0);
        }
        return null;
    }

    @Override
    public List<IEntityRow> findAllByCode(String codeValue) {
        if (StringUtils.isEmpty((String)codeValue)) {
            return null;
        }
        this.buildCodeSearch();
        return this.codeSearch.get(codeValue);
    }

    @Override
    public Map<String, IEntityRow> findByCodes(List<String> codeValue) {
        HashMap<String, IEntityRow> rows = new HashMap<String, IEntityRow>(codeValue.size());
        for (String code : codeValue) {
            IEntityRow byCode = this.findByCode(code);
            if (byCode == null) continue;
            rows.put(code, byCode);
        }
        return rows;
    }

    @Override
    public Map<String, List<IEntityRow>> findAllByCodes(List<String> codeValue) {
        HashMap<String, List<IEntityRow>> rows = new HashMap<String, List<IEntityRow>>(codeValue.size());
        for (String code : codeValue) {
            List<IEntityRow> allByCode = this.findAllByCode(code);
            if (allByCode == null) continue;
            rows.put(code, allByCode);
        }
        return rows;
    }

    private void buildCodeSearch() {
        if (this.codeSearch != null) {
            return;
        }
        this.codeSearch = new HashMap<String, List<IEntityRow>>(this.dataRows.size());
        if (null == this.queryInfo.getTableRunInfo().getEntityModel().getCodeField()) {
            return;
        }
        for (EntityRowImpl dataRowImpl : this.dataRows) {
            String code = dataRowImpl.getCode();
            this.codeSearch.computeIfAbsent(code, key -> new ArrayList()).add(dataRowImpl);
        }
    }

    @Override
    public Map<String, IEntityRow> findByEntityKeys(Set<String> entityKeyDatas) {
        HashMap<String, IEntityRow> rowMaps = new HashMap<String, IEntityRow>();
        if (entityKeyDatas == null || entityKeyDatas.size() <= 0) {
            return rowMaps;
        }
        this.buildEntityKeySearch();
        for (String entityKeyData : entityKeyDatas) {
            if (!this.entityKeySearch.containsKey(entityKeyData)) continue;
            rowMaps.put(entityKeyData, this.entityKeySearch.get(entityKeyData));
        }
        return rowMaps;
    }

    @Override
    public String[] getParentsEntityKeyDataPath(String entityKeyData) {
        String[] parentsPath = new String[]{};
        IEntityAttribute parentField = this.queryInfo.getTableRunInfo().getEntityModel().getParentField();
        if (null == parentField) {
            return parentsPath;
        }
        try {
            String parentKey;
            IEntityRow entityRow = this.findByEntityKey(entityKeyData);
            if (entityRow == null) {
                return parentsPath;
            }
            ArrayList<String> parentInfos = new ArrayList<String>();
            while ((entityRow = this.findByEntityKey(parentKey = entityRow.getAsString(parentField.getCode()))) != null && !parentInfos.contains(entityRow.getEntityKeyData())) {
                parentInfos.add(entityRow.getEntityKeyData());
            }
            int count = parentInfos.size();
            String[] result = new String[count];
            for (int i = 0; i < count; ++i) {
                result[count - i - 1] = (String)parentInfos.get(i);
            }
            return result;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return parentsPath;
        }
    }

    @Override
    public int getTotalCount() {
        return null == this.allRows ? 0 : this.allRows.size();
    }

    @Override
    public List<IEntityRow> getPageRows(PageCondition pageCondition) {
        Integer pageSize = pageCondition.getPageSize();
        Integer pageIndex = pageCondition.getPageIndex();
        if (pageSize == null && pageIndex == null) {
            return this.allRows;
        }
        int endNum = pageIndex + pageSize;
        endNum = endNum > this.allRows.size() ? this.allRows.size() : endNum;
        return this.allRows.subList(pageIndex, endNum);
    }

    @Override
    public IEntityRow quickFindByEntityKey(String entityKeyData) {
        return this.findByEntityKey(entityKeyData);
    }

    @Override
    public Map<String, IEntityRow> quickFindByEntityKeys(Set<String> entityKeyDatas) {
        return this.findByEntityKeys(entityKeyDatas);
    }
}


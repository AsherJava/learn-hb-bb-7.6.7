/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.tag.manager.resultset;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.tag.manager.bean.TagImpl;
import com.jiuqi.nr.tag.manager.entitydata.query.ITagEntityDataQuery;
import com.jiuqi.nr.tag.manager.entitydata.query.TagQueryParam;
import com.jiuqi.nr.tag.manager.resultset.ITagRecordData;
import com.jiuqi.nr.tag.manager.service.impl.TagObjectService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TagRecordData
implements ITagRecordData {
    protected Map<String, List<String>> node2TagsMap = new HashMap<String, List<String>>();
    protected Map<String, List<String>> tags2NodeMap = new HashMap<String, List<String>>();
    protected Map<String, IEntityTable> tagFormulaEntityTableMap = new HashMap<String, IEntityTable>(0);
    protected TagQueryParam queryParam;
    protected TagObjectService tagService;
    protected ITagEntityDataQuery entityDataQuery;

    public TagRecordData(TagQueryParam queryParam, ITagEntityDataQuery entityDataQuery) {
        this.queryParam = queryParam;
        this.entityDataQuery = entityDataQuery;
        this.tagService = (TagObjectService)BeanUtil.getBean(TagObjectService.class);
    }

    @Override
    public Map<String, List<String>> batchQueryTags(List<String> rowKeys) {
        this.node2TagsMap.clear();
        if (null != rowKeys && !rowKeys.isEmpty()) {
            String entityId = this.queryParam.getEntityDefine().getId();
            Map<String, List<String>> map = this.tagService.countTagsOfEntityDatas(entityId, rowKeys);
            this.node2TagsMap.putAll(map);
            List<TagImpl> tagImpls = this.tagService.findAllByOV(entityId);
            Map<String, List<String>> tags2Entities = this.buildTag2Entities(tagImpls);
            for (String entKey : rowKeys) {
                for (Map.Entry<String, List<String>> entry : tags2Entities.entrySet()) {
                    if (!entry.getValue().contains(entKey)) continue;
                    this.node2TagsMap.computeIfAbsent(entKey, k -> new ArrayList());
                    this.node2TagsMap.get(entKey).add(entry.getKey());
                }
            }
        }
        return this.node2TagsMap;
    }

    @Override
    public Map<String, List<String>> batchQueryEntities(List<String> tagKeys) {
        this.tags2NodeMap.clear();
        List<TagImpl> tagImpls = this.getTagsByKey(tagKeys);
        this.tags2NodeMap = this.buildTag2Entities(tagImpls);
        for (Map.Entry<String, List<String>> entry : this.tags2NodeMap.entrySet()) {
            List entityKeys = this.entityDataQuery.makeIEntityTable(this.queryParam, entry.getValue()).getAllRows().stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            this.tags2NodeMap.put(entry.getKey(), entityKeys);
        }
        return this.tags2NodeMap;
    }

    @Override
    public List<TagImpl> getTagsByKey(List<String> tagKeys) {
        ArrayList<TagImpl> impls = new ArrayList<TagImpl>();
        for (String tagKey : tagKeys) {
            TagImpl impl = this.tagService.findByKey(tagKey);
            if (impl == null) continue;
            impls.add(impl);
        }
        return impls;
    }

    private Map<String, List<String>> buildTag2Entities(List<TagImpl> tagImpls) {
        HashMap<String, List<String>> tags2Entities = new HashMap<String, List<String>>();
        if (null != tagImpls && !tagImpls.isEmpty()) {
            ArrayList<String> tagKeys = new ArrayList<String>();
            tagImpls.forEach(e -> tagKeys.add(e.getKey()));
            Map<String, List<String>> map = this.tagService.countEntityDatasOfTags(tagKeys);
            tags2Entities.putAll(map);
            for (TagImpl impl : tagImpls) {
                List allRows;
                IEntityTable filterDataTable = this.getIEntityTableByTagKey(impl);
                if (null == filterDataTable || null == (allRows = filterDataTable.getAllRows())) continue;
                for (IEntityRow row : allRows) {
                    ((List)tags2Entities.get(impl.getKey())).add(row.getEntityKeyData());
                }
            }
        }
        return tags2Entities;
    }

    private IEntityTable getIEntityTableByTagKey(TagImpl tagImpl) {
        String rowFilter = tagImpl.getFormula();
        if (this.checkFormula(rowFilter)) {
            IEntityTable rs = this.tagFormulaEntityTableMap.get(tagImpl.getKey());
            if (rs == null) {
                rs = this.entityDataQuery.makeIEntityTable(this.queryParam, rowFilter);
                this.tagFormulaEntityTableMap.put(tagImpl.getKey(), rs);
            }
            return rs;
        }
        return null;
    }

    private boolean checkFormula(String formula) {
        return StringUtils.isNotEmpty((String)formula);
    }
}


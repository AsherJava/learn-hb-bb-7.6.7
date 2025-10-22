/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider
 *  com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider
 *  com.jiuqi.nr.entity.adapter.provider.QueryOptions$TreeType
 *  com.jiuqi.nr.entity.engine.result.EntityResultSet
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

import com.jiuqi.nr.batch.summary.service.ext.entityframe.ConditionGatherResultSet;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.QueryOptions;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.CollectionUtils;

public class ConditionSubDataProvider
implements IDataQueryProvider {
    private CustomConditionRowProvider provider;
    private IEntityQueryParam param;
    private Map<String, CorporateEntityData> corporateColumn2Value;

    public ConditionSubDataProvider(CustomConditionRowProvider provider, Map<String, CorporateEntityData> corporateColumn2Value, IEntityQueryParam param) {
        this.provider = provider;
        this.corporateColumn2Value = corporateColumn2Value;
        this.param = param;
    }

    public EntityResultSet getAllData() {
        List keyFilterRows = null;
        List codeFilterRows = null;
        List allRows = this.provider.getAllRows();
        if (CollectionUtils.isEmpty(this.param.getMasterKey()) && CollectionUtils.isEmpty(this.param.getCodes())) {
            return new ConditionGatherResultSet(allRows, this.corporateColumn2Value);
        }
        if (!CollectionUtils.isEmpty(this.param.getMasterKey())) {
            keyFilterRows = allRows.stream().filter(row -> this.param.getMasterKey().contains(row.getKey())).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(this.param.getCodes())) {
            codeFilterRows = allRows.stream().filter(row -> this.param.getCodes().contains(row.getCode())).collect(Collectors.toList());
        }
        List<CustomConditionRow> allEligibleRows = Stream.of(keyFilterRows, codeFilterRows).filter(Objects::nonNull).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        return new ConditionGatherResultSet(allEligibleRows, this.corporateColumn2Value);
    }

    public EntityResultSet getRootData() {
        return new ConditionGatherResultSet(this.provider.getRootRows(), this.corporateColumn2Value);
    }

    public EntityResultSet getChildData(QueryOptions.TreeType treeType, String ... entityKeyDatas) {
        if (QueryOptions.TreeType.DIRECT_CHILDREN == treeType) {
            return new ConditionGatherResultSet(this.provider.getChildRows(entityKeyDatas[0]), this.corporateColumn2Value);
        }
        if (QueryOptions.TreeType.ALL_CHILDREN == treeType) {
            return new ConditionGatherResultSet(this.provider.getAllChildRows(entityKeyDatas[0]), this.corporateColumn2Value);
        }
        return null;
    }

    public EntityResultSet findByEntityKeys() {
        return this.getAllData();
    }

    public EntityResultSet findByCodes() {
        return this.getAllData();
    }

    public EntityResultSet simpleQueryByKeys() {
        return this.getAllData();
    }

    public String getParent(String entityKeyData) {
        CustomConditionRow row = this.provider.findRow(entityKeyData);
        return row != null ? row.getCode() : null;
    }

    public String getParent(Map<String, Object> rowData) {
        return null;
    }

    public int getMaxDepth() {
        return this.provider.getMaxDepth();
    }

    public int getMaxDepthByEntityKey(String entityKeyData) {
        CustomConditionRow row = this.provider.findRow(entityKeyData);
        return row != null ? row.getMaxDepth() : 0;
    }

    public int getChildCount(String entityKeyData, QueryOptions.TreeType treeType) {
        CustomConditionRow row = this.provider.findRow(entityKeyData);
        if (row != null) {
            switch (treeType) {
                case DIRECT_CHILDREN: {
                    return row.getChildCount();
                }
                case ALL_CHILDREN: {
                    return row.getAllChildCount();
                }
            }
        }
        return 0;
    }

    public Map<String, Integer> getChildCountByParent(String entityKeyData, QueryOptions.TreeType treeType) {
        HashMap<String, Integer> countMap = new HashMap<String, Integer>();
        List childRows = this.provider.getChildRows(entityKeyData);
        if (childRows != null && !childRows.isEmpty()) {
            switch (treeType) {
                case DIRECT_CHILDREN: {
                    childRows.forEach(row -> countMap.put(row.getCode(), row.getChildCount()));
                    break;
                }
                case ALL_CHILDREN: {
                    childRows.forEach(row -> countMap.put(row.getCode(), row.getAllChildCount()));
                }
            }
        }
        return countMap;
    }

    public String[] getParentsEntityKeyDataPath(String entityKeyData) {
        CustomConditionRow row = this.provider.findRow(entityKeyData);
        return row != null ? row.getPath() : new String[]{};
    }

    public String[] getParentsEntityKeyDataPath(Map<String, Object> rowData) {
        return new String[0];
    }

    public int getTotalCount() {
        return this.provider.getTotalCount();
    }
}


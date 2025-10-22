/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.var;

import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;
import java.util.List;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class LevelRangeQuery
extends RangeQuery {
    private int level;
    private List<String> rootKeys;

    public LevelRangeQuery(int level, List<String> rootKeys) {
        Assert.isTrue(level > 0, "\u81f3\u5c11\u67e5\u8be2\u4e00\u7ea7\u6df1\u5ea6\u3002");
        this.level = level;
        this.rootKeys = rootKeys;
    }

    @Override
    protected EntityResultSet executeQuery(EntityQueryParam entityQueryParam, IEntityAdapter entityAdapter) {
        List<String> allKeys;
        EntityResultSet childRows;
        if (CollectionUtils.isEmpty(this.rootKeys)) {
            EntityResultSet rootRows = entityAdapter.getRootRows(entityQueryParam);
            while (rootRows.next()) {
                String key = rootRows.getKey();
                this.rootKeys.add(key);
            }
        }
        if (CollectionUtils.isEmpty(this.rootKeys)) {
            return null;
        }
        List<String> copyMasterKeys = entityQueryParam.getMasterKey();
        entityQueryParam.setMasterKey(this.rootKeys);
        EntityResultSet rs = entityAdapter.findByEntityKeys(entityQueryParam);
        entityQueryParam.setMasterKey(copyMasterKeys);
        String[] lastLevelCodes = this.rootKeys.toArray(new String[0]);
        for (int i = 1; i < this.level && (childRows = entityAdapter.getChildRows(entityQueryParam, lastLevelCodes)) != null && !CollectionUtils.isEmpty(allKeys = childRows.getAllKeys()); ++i) {
            lastLevelCodes = allKeys.toArray(new String[0]);
            rs.merge(childRows);
        }
        return rs;
    }
}


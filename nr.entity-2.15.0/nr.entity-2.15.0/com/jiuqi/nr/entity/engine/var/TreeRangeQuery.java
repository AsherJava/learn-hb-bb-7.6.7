/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.var;

import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class TreeRangeQuery
extends RangeQuery {
    List<String> parentKey;

    public void setParentKey(List<String> parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    protected EntityResultSet executeQuery(EntityQueryParam entityQueryParam, IEntityAdapter entityAdapter) {
        List<String> oldKeys = entityQueryParam.getMasterKey();
        if (!CollectionUtils.isEmpty(oldKeys)) {
            throw new RuntimeException("\u6309\u8303\u56f4\u67e5\u8be2\u4e0e\u6309\u4e3b\u952e\u67e5\u8be2\u7684\u8bbe\u7f6e\u51b2\u7a81");
        }
        if (CollectionUtils.isEmpty(this.parentKey)) {
            throw new RuntimeException("\u672a\u8bbe\u7f6e\u67e5\u8be2\u8303\u56f4\uff0c\u65e0\u6cd5\u6267\u884c\u67e5\u8be2");
        }
        ArrayList<String> queryKeys = new ArrayList<String>(this.parentKey);
        entityQueryParam.setMasterKey(queryKeys);
        EntityResultSet rs = entityAdapter.findByEntityKeys(entityQueryParam);
        entityQueryParam.setMasterKey(Collections.emptyList());
        for (String parent : this.parentKey) {
            EntityResultSet allChildRs = entityAdapter.getAllChildRows(entityQueryParam, parent);
            rs.merge(allChildRs);
        }
        return rs;
    }
}


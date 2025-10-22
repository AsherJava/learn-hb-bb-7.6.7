/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuiqi.nr.unit.treebase.source;

import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceHelper;
import com.jiuqi.bi.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitTreeDataSourceHelper
implements IUnitTreeDataSourceHelper {
    private Map<String, IUnitTreeDataSource> sourceMap;

    @Autowired(required=true)
    public UnitTreeDataSourceHelper(List<IUnitTreeDataSource> list) {
        if (null != list) {
            this.sourceMap = new HashMap<String, IUnitTreeDataSource>();
            for (IUnitTreeDataSource e : list) {
                this.sourceMap.put(e.getSourceId(), e);
            }
        }
    }

    @Override
    public IUnitTreeDataSource getBaseTreeDataSource(String sourceId) {
        return StringUtils.isNotEmpty((String)sourceId) ? this.sourceMap.get(sourceId) : null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.unit.uselector.source.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class USelectorDataSourceHelper
implements IUSelectorDataSourceHelper {
    private Map<String, IUSelectorDataSource> sourceMap;

    @Autowired(required=true)
    public USelectorDataSourceHelper(List<IUSelectorDataSource> list) {
        if (null != list) {
            this.sourceMap = new HashMap<String, IUSelectorDataSource>();
            for (IUSelectorDataSource e : list) {
                this.sourceMap.put(e.getSourceId(), e);
            }
        }
    }

    @Override
    public IUSelectorDataSource getBaseTreeDataSource(String sourceId) {
        return StringUtils.isNotEmpty((String)sourceId) ? this.sourceMap.get(sourceId) : null;
    }
}


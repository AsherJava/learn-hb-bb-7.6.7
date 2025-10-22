/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.nr.form.selector.tree.IReportFormTreeSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IReportFormTreeFactory {
    private Map<String, IReportFormTreeSource> sourceMap;

    @Autowired
    public IReportFormTreeFactory(List<IReportFormTreeSource> list) {
        this.init(list);
    }

    private void init(List<IReportFormTreeSource> list) {
        this.sourceMap = new HashMap<String, IReportFormTreeSource>();
        if (null != list && !list.isEmpty()) {
            list.forEach(source -> this.sourceMap.put(source.getFormSourceId(), (IReportFormTreeSource)source));
        }
    }

    public IReportFormTreeSource getFormTreeSource(String formSourceId) {
        return this.sourceMap.get(formSourceId);
    }
}


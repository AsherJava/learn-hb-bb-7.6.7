/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.xlib.utils.CollectionUtils
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractReportTemplateDao<T extends ReportTemplateDefine>
extends BaseDao {
    public abstract Class<T> getClz();

    public T getByKey(String key) {
        return (T)((ReportTemplateDefine)super.getByKey((Object)key, this.getClz()));
    }

    public List<T> getByKey(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        ArrayList result = new ArrayList();
        int perSize = 100;
        int size = keys.size();
        int count = size / 100 + 1;
        int start = 0;
        int end = 0;
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            start = 100 * i;
            end = 100 * (i + 1) > size ? size : 100 * (i + 1);
            List<String> subList = keys.subList(start, end);
            if (CollectionUtils.isEmpty(subList)) continue;
            sbr.setLength(0);
            sbr.append("RT_KEY").append(" IN (").append(subList.stream().map(s -> "?").collect(Collectors.joining(","))).append(")");
            List list = super.list(sbr.toString(), subList.toArray(), this.getClz());
            result.addAll(list);
        }
        return result;
    }

    public List<T> getByTask(String taskKey) {
        return super.list(new String[]{"taskKey"}, (Object[])new String[]{taskKey}, this.getClz());
    }

    public List<T> getByFormScheme(String formSchemeKey) {
        return super.list(new String[]{"formSchemeKey"}, (Object[])new String[]{formSchemeKey}, this.getClz());
    }
}


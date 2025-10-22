/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.i18n.impl;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.batch.summary.i18n.enumeration.BatchSummaryI18nKeys;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BatchSummaryI18nResource
implements I18NResource {
    static final String NAME = "\u65b0\u62a5\u8868/\u6279\u91cf\u6c47\u603b\u56fd\u9645\u5316";
    public static final String NAME_SPACE = "nr_batch_summary";

    public String name() {
        return NAME;
    }

    public String getNameSpace() {
        return NAME_SPACE;
    }

    public List<I18NResourceItem> getCategory(String parentId) {
        ArrayList<I18NResourceItem> resources = new ArrayList<I18NResourceItem>();
        if (StringUtils.isEmpty((String)parentId)) {
            for (BatchSummaryI18nKeys i18nKey : BatchSummaryI18nKeys.values()) {
                resources.add(new I18NResourceItem(i18nKey.key, i18nKey.title));
            }
        }
        return resources;
    }

    public List<I18NResourceItem> getResource(String parentId) {
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.util;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.DataSourceInfoTreeNodeDto;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class DataSourceInfoTreeNodeComparator
implements Comparator<DataSourceInfoTreeNodeDto> {
    private static final Collator collator = Collator.getInstance(Locale.SIMPLIFIED_CHINESE);

    @Override
    public int compare(DataSourceInfoTreeNodeDto o1, DataSourceInfoTreeNodeDto o2) {
        if ("_default".equals(o1.getKey())) {
            return -1;
        }
        if ("_default".equals(o2.getKey())) {
            return 1;
        }
        return collator.compare(o1.getTitle(), o2.getTitle());
    }
}


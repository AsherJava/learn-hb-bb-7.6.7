/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.common;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.IMetaItem;
import java.util.Comparator;
import java.util.List;

public class MetaComparator
implements Comparator<IMetaItem> {
    private static final MetaComparator DEFAULT = new MetaComparator();

    @Override
    public int compare(IMetaItem define1, IMetaItem define2) {
        if (define1 == null || StringUtils.isEmpty((String)define1.getOrder())) {
            return -1;
        }
        if (define2 == null || StringUtils.isEmpty((String)define2.getOrder())) {
            return 1;
        }
        return define1.getOrder().compareTo(define2.getOrder());
    }

    public static <T extends IMetaItem> List<T> sort(List<T> metaItems) {
        if (metaItems == null) {
            throw new IllegalArgumentException("'metaItems' must not be null.");
        }
        metaItems.sort(DEFAULT);
        return metaItems;
    }
}


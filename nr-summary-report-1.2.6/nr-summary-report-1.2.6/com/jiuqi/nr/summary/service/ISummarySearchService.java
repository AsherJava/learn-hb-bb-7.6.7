/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.service;

import com.jiuqi.nr.summary.vo.search.SummarySearchItem;
import com.jiuqi.nr.summary.vo.search.SummarySearchPosition;
import com.jiuqi.nr.summary.vo.search.SummarySearchPositionRequestParam;
import com.jiuqi.nr.summary.vo.search.SummarySearchRequestParam;
import java.util.List;

public interface ISummarySearchService {
    public List<SummarySearchItem> search(SummarySearchRequestParam var1);

    public SummarySearchPosition position(SummarySearchPositionRequestParam var1);
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary;

import com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary.QueryParamCache;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class DetailUnitQueryParamCache
extends QueryParamCache {
    private String hbUnitCode;
    private String diffUnitCode;
    protected Map<String, BigDecimal> subjectCode2AmtMap;
    protected Map<String, Double> subjectCode2DisposalUnitOffsetValueMap;
    protected Map<String, Double> subjectCode2SameParentUnitOffsetValueMap;
    protected Set<String> virtualCodeByDisposalUnitCodeSet;
    protected Set<String> changeCodeByChangedParentUnitCodeSet;
    protected Set<String> virtualCodeBySameParentCodeSet;

    public DetailUnitQueryParamCache() {
    }

    public DetailUnitQueryParamCache(QueryParamCache queryParamCache) {
        super.assignFrom(queryParamCache);
    }

    public String getHbUnitCode() {
        return this.hbUnitCode;
    }

    public void setHbUnitCode(String hbUnitCode) {
        this.hbUnitCode = hbUnitCode;
    }

    public String getDiffUnitCode() {
        return this.diffUnitCode;
    }

    public void setDiffUnitCode(String diffUnitCode) {
        this.diffUnitCode = diffUnitCode;
    }

    public void setSubjectCode2AmtMap(Map<String, BigDecimal> subjectCode2AmtMap) {
        this.subjectCode2AmtMap = subjectCode2AmtMap;
    }

    private void setVirtualCodeBySameParentCodeSet(Set<String> virtualCodeBySameParentCodeSet) {
        this.virtualCodeBySameParentCodeSet = virtualCodeBySameParentCodeSet;
    }
}


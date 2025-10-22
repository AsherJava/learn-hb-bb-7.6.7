/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject
 */
package com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryParamCache {
    protected Set<String> tableDefineCodeSet;
    protected Set<ArrayKey> currYearTotalZbCodeSet;
    protected DoubleKeyMap<String, String, ReWriteSubject> tableCode2FieldCode2ReWriteSubjectMap;
    private Map<String, Set<ArrayKey>> tableCode2CurrYearTotalZbCodeSetMap;

    public QueryParamCache setTableDefineCodeSet(Set<String> tableDefineCodeSet) {
        this.tableDefineCodeSet = tableDefineCodeSet;
        return this;
    }

    public QueryParamCache setCurrYearTotalZbCodeSet(Set<ArrayKey> currYearTotalZbCodeSet) {
        this.currYearTotalZbCodeSet = currYearTotalZbCodeSet;
        this.tableCode2CurrYearTotalZbCodeSetMap = null;
        return this;
    }

    public void setTableCode2FieldCode2ReWriteSubjectMap(DoubleKeyMap<String, String, ReWriteSubject> tableCode2FieldCode2ReWriteSubjectMap) {
        this.tableCode2FieldCode2ReWriteSubjectMap = tableCode2FieldCode2ReWriteSubjectMap;
    }

    public Map<String, Set<ArrayKey>> calcTableCode2CurrYearTotalZbCodeSetMap() {
        if (null == this.tableCode2CurrYearTotalZbCodeSetMap) {
            this.tableCode2CurrYearTotalZbCodeSetMap = this.currYearTotalZbCodeSet.stream().collect(Collectors.groupingBy(zbArrayKey -> (String)zbArrayKey.get(0), Collectors.toSet()));
        }
        return this.tableCode2CurrYearTotalZbCodeSetMap;
    }

    protected void assignFrom(QueryParamCache queryParamCache) {
        this.tableDefineCodeSet = queryParamCache.tableDefineCodeSet;
        this.currYearTotalZbCodeSet = queryParamCache.currYearTotalZbCodeSet;
        this.tableCode2FieldCode2ReWriteSubjectMap = queryParamCache.tableCode2FieldCode2ReWriteSubjectMap;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.NrSqlUtils
 *  com.jiuqi.gcreport.common.util.NrSqlUtils$Condition
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject
 */
package com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.NrSqlUtils;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary.DetailUnitQueryParamCache;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary.QueryParamCache;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReWriteValue {
    private Map<String, Double> fieldCode2DisposalUnitAmtMap = Collections.EMPTY_MAP;
    private Map<String, Double> fieldCode2ChangedUnitAmtMap = Collections.EMPTY_MAP;
    private Map<String, Double> fieldCode2SameParentUnitAmtMap = Collections.EMPTY_MAP;
    private Map<String, Double> fieldCode2DisposalUnitOffsetValueMap = Collections.EMPTY_MAP;
    private Map<String, Double> fieldCode2SameParentUnitOffsetValueMap = Collections.EMPTY_MAP;
    private Map<String, Double> fieldCode2OffsetValueMap = Collections.EMPTY_MAP;

    protected void appendFieldCode2DisposalUnitAmtMap(String tableDefineCode, GcActionParamsVO paramsVO, DetailUnitQueryParamCache queryParamCache) {
        Map<String, Set<ArrayKey>> tableCode2CurrYearTotalZbCodeSetMap = queryParamCache.calcTableCode2CurrYearTotalZbCodeSetMap();
        this.fieldCode2DisposalUnitAmtMap = this.currYearTotalDisposalUnitAmt(tableDefineCode, paramsVO, tableCode2CurrYearTotalZbCodeSetMap, queryParamCache.virtualCodeByDisposalUnitCodeSet);
    }

    public void appendFieldCode2ChangeUnitAmtMap(String tableDefineCode, GcActionParamsVO paramsVO, DetailUnitQueryParamCache queryParamCache) {
        Map<String, Set<ArrayKey>> tableCode2CurrYearTotalZbCodeSetMap = queryParamCache.calcTableCode2CurrYearTotalZbCodeSetMap();
        this.fieldCode2ChangedUnitAmtMap = this.currYearTotalDisposalUnitAmt(tableDefineCode, paramsVO, tableCode2CurrYearTotalZbCodeSetMap, queryParamCache.changeCodeByChangedParentUnitCodeSet);
    }

    protected void appendFieldCode2SameParentUnitAmtMap(String tableDefineCode, GcActionParamsVO paramsVO, DetailUnitQueryParamCache queryParamCache) {
        Map<String, Set<ArrayKey>> tableCode2CurrYearTotalZbCodeSetMap = queryParamCache.calcTableCode2CurrYearTotalZbCodeSetMap();
        this.fieldCode2SameParentUnitAmtMap = this.currYearTotalSameParentUnitAmt(tableDefineCode, paramsVO, tableCode2CurrYearTotalZbCodeSetMap, queryParamCache.virtualCodeBySameParentCodeSet);
    }

    protected void appendFieldCode2DisposalUnitOffsetValueMap(String tableDefineCode, DetailUnitQueryParamCache queryParamCache) {
        this.fieldCode2DisposalUnitOffsetValueMap = this.getFieldCode2UnitOffsetValueMap(tableDefineCode, queryParamCache.subjectCode2DisposalUnitOffsetValueMap, queryParamCache);
    }

    protected void appendFieldCode2SameParentUnitOffsetValueMap(String tableDefineCode, DetailUnitQueryParamCache queryParamCache) {
        this.fieldCode2SameParentUnitOffsetValueMap = this.getFieldCode2UnitOffsetValueMap(tableDefineCode, queryParamCache.subjectCode2SameParentUnitOffsetValueMap, queryParamCache);
    }

    protected void appendFieldCode2OffsetValueMap(String tableDefineCode, DetailUnitQueryParamCache queryParamCache) {
        this.fieldCode2OffsetValueMap = this.currYearTotalOffsetValue(tableDefineCode, queryParamCache.subjectCode2AmtMap, queryParamCache);
    }

    private Map<String, Double> currYearTotalSameParentUnitAmt(String tableDefineCode, GcActionParamsVO paramsVO, Map<String, Set<ArrayKey>> currYearTotalZb_tableCode2ZbCodeListMap, Set<String> sameCtrlSameParentUnitCodeSet) {
        return this.batchQueryUnitAmt(tableDefineCode, sameCtrlSameParentUnitCodeSet, paramsVO, currYearTotalZb_tableCode2ZbCodeListMap);
    }

    private Map<String, Double> currYearTotalOffsetValue(String tableDefineCode, Map<String, BigDecimal> subjectCode2AmtMap, QueryParamCache queryParamCache) {
        HashMap<String, Double> fieldCode2OffsetValueMap = new HashMap<String, Double>(16);
        Map fieldCode2SubjectMap = queryParamCache.tableCode2FieldCode2ReWriteSubjectMap.get((Object)tableDefineCode);
        fieldCode2SubjectMap.entrySet().forEach(fieldCode2SubjectEntry -> {
            String fieldCode = (String)fieldCode2SubjectEntry.getKey();
            ReWriteSubject subject = (ReWriteSubject)fieldCode2SubjectEntry.getValue();
            ArrayKey zbCode = subject.getZbCode();
            if (!queryParamCache.currYearTotalZbCodeSet.contains(zbCode)) {
                return;
            }
            BigDecimal value = BigDecimal.ZERO;
            for (String subjectCode : OneKeyMergeUtils.calcAllChildSubjectCodeSet(subject)) {
                BigDecimal tempValue = (BigDecimal)subjectCode2AmtMap.get(subjectCode);
                if (null == tempValue) continue;
                value = value.add(tempValue);
            }
            if (subject.getOrient() == OrientEnum.C.getValue()) {
                value = BigDecimal.ZERO.subtract(value);
            }
            fieldCode2OffsetValueMap.put(fieldCode, value.doubleValue());
        });
        return fieldCode2OffsetValueMap;
    }

    private Map<String, Double> getFieldCode2UnitOffsetValueMap(String tableDefineCode, Map<String, Double> subjectCode2DisposalUnitOffsetValueMap, QueryParamCache queryParamCache) {
        HashMap<String, Double> fieldCode2UnitOffsetValueMap = new HashMap<String, Double>(16);
        Map fieldCode2ReWriteSubjectMap = queryParamCache.tableCode2FieldCode2ReWriteSubjectMap.get((Object)tableDefineCode);
        fieldCode2ReWriteSubjectMap.entrySet().forEach(fieldCode2SubjectEntry -> {
            String fieldCode = (String)fieldCode2SubjectEntry.getKey();
            ReWriteSubject subject = (ReWriteSubject)fieldCode2SubjectEntry.getValue();
            ArrayKey zbCode = subject.getZbCode();
            if (!queryParamCache.currYearTotalZbCodeSet.contains(zbCode)) {
                return;
            }
            Double value = 0.0;
            for (String subjectCode : OneKeyMergeUtils.calcAllChildSubjectCodeSet(subject)) {
                Double tempValue = (Double)subjectCode2DisposalUnitOffsetValueMap.get(subjectCode);
                if (null == tempValue) continue;
                value = value + tempValue;
            }
            if (subject.getOrient() == OrientEnum.C.getValue()) {
                value = -value.doubleValue();
            }
            fieldCode2UnitOffsetValueMap.put(fieldCode, value);
        });
        return fieldCode2UnitOffsetValueMap;
    }

    private Map<String, Double> currYearTotalDisposalUnitAmt(String tableDefineCode, GcActionParamsVO paramsVO, Map<String, Set<ArrayKey>> tableCode2CurrYearTotalZbCodeSetMap, Set<String> virtualCodeByDisposalUnitCodeSet) {
        return this.batchQueryUnitAmt(tableDefineCode, virtualCodeByDisposalUnitCodeSet, paramsVO, tableCode2CurrYearTotalZbCodeSetMap);
    }

    private Map<String, Double> batchQueryUnitAmt(String tableName, Set<String> unitCodeSet, GcActionParamsVO paramsVO, Map<String, Set<ArrayKey>> tableCode2ZbCodeListMap) {
        HashMap<String, Double> fieldCode2DisposalUnitAmtMap = new HashMap<String, Double>(16);
        Set<ArrayKey> zbCodeList = tableCode2ZbCodeListMap.get(tableName);
        NrSqlUtils.Condition condition = NrSqlUtils.getCondition((Object)paramsVO, null);
        List zbAmtList = NrSqlUtils.select((Object)condition, (String)tableName, unitCodeSet);
        zbCodeList.forEach(zbArrayKey -> {
            String zbCode = (String)zbArrayKey.get(1);
            if (CollectionUtils.isEmpty((Collection)zbAmtList)) {
                return;
            }
            for (Map zbCode2AmtMap : zbAmtList) {
                BigDecimal amt = new BigDecimal(zbCode2AmtMap.get(zbCode).toString());
                if (null == amt) continue;
                MapUtils.add((Map)fieldCode2DisposalUnitAmtMap, (Object)zbCode, (Double)amt.doubleValue());
            }
        });
        return fieldCode2DisposalUnitAmtMap;
    }

    public Map<String, Double> doCalc() {
        HashSet<String> allFieldCodeSet = new HashSet<String>(256);
        allFieldCodeSet.addAll(this.fieldCode2DisposalUnitAmtMap.keySet());
        allFieldCodeSet.addAll(this.fieldCode2ChangedUnitAmtMap.keySet());
        allFieldCodeSet.addAll(this.fieldCode2SameParentUnitAmtMap.keySet());
        allFieldCodeSet.addAll(this.fieldCode2DisposalUnitOffsetValueMap.keySet());
        allFieldCodeSet.addAll(this.fieldCode2SameParentUnitOffsetValueMap.keySet());
        allFieldCodeSet.addAll(this.fieldCode2OffsetValueMap.keySet());
        HashMap<String, Double> fieldCode2ReWriteValueMap = new HashMap<String, Double>(128);
        fieldCode2ReWriteValueMap.putAll(this.fieldCode2DisposalUnitAmtMap);
        for (String fieldCode : allFieldCodeSet) {
            MapUtils.sub(fieldCode2ReWriteValueMap, (Object)fieldCode, (Double)this.fieldCode2ChangedUnitAmtMap.get(fieldCode));
            MapUtils.sub(fieldCode2ReWriteValueMap, (Object)fieldCode, (Double)this.fieldCode2SameParentUnitAmtMap.get(fieldCode));
            MapUtils.add(fieldCode2ReWriteValueMap, (Object)fieldCode, (Double)this.fieldCode2DisposalUnitOffsetValueMap.get(fieldCode));
            MapUtils.sub(fieldCode2ReWriteValueMap, (Object)fieldCode, (Double)this.fieldCode2SameParentUnitOffsetValueMap.get(fieldCode));
            MapUtils.add(fieldCode2ReWriteValueMap, (Object)fieldCode, (Double)this.fieldCode2OffsetValueMap.get(fieldCode));
        }
        return fieldCode2ReWriteValueMap;
    }

    public List<String> generateDebugInfo(String fieldCode) {
        ArrayList<String> messageList = new ArrayList<String>();
        messageList.add("\u5904\u7f6e\u65b9\u5355\u4f4d\u6307\u6807\u6570:" + NumberUtils.doubleToString((Double)this.fieldCode2DisposalUnitAmtMap.get(fieldCode)));
        messageList.add("\u975e\u540c\u63a7\u53d8\u52a8\u5355\u4f4d\u6307\u6807\u6570:" + NumberUtils.doubleToString((Double)this.fieldCode2ChangedUnitAmtMap.get(fieldCode)));
        messageList.add("\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u6307\u6807\u6570:" + NumberUtils.doubleToString((Double)this.fieldCode2SameParentUnitAmtMap.get(fieldCode)));
        messageList.add("\u5904\u7f6e\u65b9\u5355\u4f4d\u62b5\u9500\u6570:" + NumberUtils.doubleToString((Double)this.fieldCode2DisposalUnitOffsetValueMap.get(fieldCode)));
        messageList.add("\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u62b5\u9500\u6570:" + NumberUtils.doubleToString((Double)this.fieldCode2SameParentUnitOffsetValueMap.get(fieldCode)));
        messageList.add("\u6b63\u5e38\u62b5\u9500\u6570:" + NumberUtils.doubleToString((Double)this.fieldCode2OffsetValueMap.get(fieldCode)));
        Map<String, Double> fieldCode2ReWriteValueMap = this.doCalc();
        messageList.add("\u8ba1\u7b97\u5173\u7cfb\uff1a\u56de\u5199\u6570=\u5904\u7f6e\u65b9\u5355\u4f4d\u6307\u6807\u6570-\u975e\u540c\u63a7\u53d8\u52a8\u5355\u4f4d\u6307\u6807\u6570-\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u6307\u6807\u6570+\u5904\u7f6e\u65b9\u5355\u4f4d\u62b5\u9500\u6570-\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u62b5\u9500\u6570+\u6b63\u5e38\u62b5\u9500\u6570");
        messageList.add("\u56de\u5199\u6570:" + NumberUtils.doubleToString((Double)fieldCode2ReWriteValueMap.get(fieldCode)));
        return messageList;
    }
}


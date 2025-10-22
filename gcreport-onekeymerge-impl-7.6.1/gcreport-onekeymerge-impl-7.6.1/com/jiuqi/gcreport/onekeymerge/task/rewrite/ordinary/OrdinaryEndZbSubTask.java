/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.NrSqlUtils
 *  com.jiuqi.gcreport.common.util.NrSqlUtils$Condition
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.NrSqlUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.ordinary.DetailUnitQueryParamCache;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OrdinaryEndZbSubTask {
    public void reWriteEndOffset(GcActionParamsVO paramsVO, DetailUnitQueryParamCache queryParamCache) {
        boolean enableSameCtr = false;
        if (this.needStop(paramsVO, queryParamCache, enableSameCtr)) {
            return;
        }
        queryParamCache.tableDefineCodeSet.forEach(tableDefineCode -> {
            HashMap<String, Double> fieldCode2OffsetValueMap = new HashMap<String, Double>(16);
            if (!queryParamCache.tableCode2FieldCode2ReWriteSubjectMap.containsKey(tableDefineCode)) {
                return;
            }
            Map fieldCode2ReWriteSubjectMap = queryParamCache.tableCode2FieldCode2ReWriteSubjectMap.get(tableDefineCode);
            fieldCode2ReWriteSubjectMap.entrySet().forEach(fieldCode2SubjectEntry -> {
                String fieldCode = (String)fieldCode2SubjectEntry.getKey();
                ReWriteSubject subject = (ReWriteSubject)fieldCode2SubjectEntry.getValue();
                ArrayKey zbCode = subject.getZbCode();
                if (enableSameCtr && queryParamCache.currYearTotalZbCodeSet.contains(zbCode)) {
                    return;
                }
                BigDecimal totalValue = BigDecimal.ZERO;
                for (String subjectCode : OneKeyMergeUtils.calcAllChildSubjectCodeSet(subject)) {
                    BigDecimal currSubjectVal = queryParamCache.subjectCode2AmtMap.get(subjectCode);
                    if (null == currSubjectVal) continue;
                    totalValue = totalValue.add(currSubjectVal);
                }
                if (subject.getOrient() == OrientEnum.C.getValue()) {
                    totalValue = BigDecimal.ZERO.subtract(totalValue);
                }
                fieldCode2OffsetValueMap.put(fieldCode, totalValue.doubleValue());
            });
            if (fieldCode2OffsetValueMap.isEmpty()) {
                return;
            }
            if (this.debugZbMode(paramsVO, queryParamCache, (Map<String, Double>)fieldCode2OffsetValueMap, (String)tableDefineCode, enableSameCtr)) {
                return;
            }
            GcOrgCacheVO diffUnit = OrgUtils.getCurrentUnit(paramsVO.getOrgType(), paramsVO.getPeriodStr(), queryParamCache.getDiffUnitCode());
            NrSqlUtils.Condition condition = NrSqlUtils.getCondition((Object)paramsVO, (String)queryParamCache.getDiffUnitCode(), (String)diffUnit.getOrgTypeId());
            NrSqlUtils.merge((Object)condition, (String)tableDefineCode, fieldCode2OffsetValueMap);
        });
    }

    private boolean needStop(GcActionParamsVO paramsVO, DetailUnitQueryParamCache queryParamCache, boolean enableSameCtr) {
        if (null == paramsVO.getDebugZb()) {
            return false;
        }
        if (!enableSameCtr) {
            return false;
        }
        if (CollectionUtils.isEmpty((Collection)queryParamCache.currYearTotalZbCodeSet)) {
            return false;
        }
        return queryParamCache.currYearTotalZbCodeSet.contains(paramsVO.getDebugZb().getDebugZb());
    }

    private boolean debugZbMode(GcActionParamsVO paramsVO, DetailUnitQueryParamCache queryParamCache, Map<String, Double> fieldCode2OffsetValueMap, String tableDefineCode, boolean enableSameCtr) {
        if (null == paramsVO.getDebugZb()) {
            return false;
        }
        ArrayKey debugZb = paramsVO.getDebugZb().getDebugZb();
        String tableName = (String)debugZb.get(0);
        if (!tableName.equals(tableDefineCode)) {
            return true;
        }
        String fieldCode = (String)debugZb.get(1);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByName(tableName);
        if (tableModelDefine == null) {
            return true;
        }
        ColumnModelDefine fieldDefine = dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), fieldCode);
        ReWriteSubject reWriteSubject = (ReWriteSubject)queryParamCache.tableCode2FieldCode2ReWriteSubjectMap.get((Object)tableName, (Object)fieldDefine.getName());
        if (null == reWriteSubject) {
            return true;
        }
        paramsVO.getDebugZb().writeMessage(reWriteSubject.generateDebugInfo());
        paramsVO.getDebugZb().writeMessage(enableSameCtr ? "\u542f\u7528\u540c\u63a7" : "\u672a\u542f\u7528\u540c\u63a7");
        paramsVO.getDebugZb().writeMessage("\u547d\u4e2d\u671f\u672b\u6307\u6807");
        paramsVO.getDebugZb().writeMessage("\u56de\u5199\u6570:" + NumberUtils.doubleToString((Double)fieldCode2OffsetValueMap.get(fieldCode)));
        return true;
    }
}


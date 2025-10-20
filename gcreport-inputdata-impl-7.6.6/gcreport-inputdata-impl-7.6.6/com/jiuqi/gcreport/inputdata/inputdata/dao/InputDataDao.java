/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.inputdata.dto.InputDataDTO
 *  com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition
 *  com.jiuqi.gcreport.inputdata.dto.MergeCalcFilterCondition
 *  com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.ManualBatchOffsetParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao;

import com.jiuqi.gcreport.inputdata.dto.InputDataDTO;
import com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition;
import com.jiuqi.gcreport.inputdata.dto.MergeCalcFilterCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.ManualBatchOffsetParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InputDataDao {
    public Pagination<Map<String, Object>> queryUnOffsetRecords(QueryParamsVO var1, Boolean var2);

    public List<InputDataEO> queryUnOffsetRecordsForCalc(QueryParamsVO var1);

    public List<InputDataEO> queryByCondition(InputRuleFilterCondition var1);

    public List<InputDataEO> queryByRuleCondition(InputRuleFilterCondition var1);

    public List<InputDataEO> queryByRuleAndMergeCondition(String var1, MergeCalcFilterCondition var2, String var3);

    public List<DesignFieldDefineVO> queryUnOffsetColumnSelect(String var1);

    public List<InputDataEO> queryByIds(Collection<String> var1, String var2);

    public void updateCustomInfoByKeys(Collection<InputDataEO> var1, Map<String, String> var2, String var3);

    public void updateInputDataToOffset(Map<String, Double> var1, String var2, String var3);

    public void updateInputDataToOffsetNoDiff(Set<String> var1, String var2, String var3);

    public List<InputDataEO> queryUnOffsetRecordsByWhere(String[] var1, String[] var2, Object[] var3, String var4);

    public long updateOffsetInfo(List<InputDataEO> var1, String var2);

    public void updateOffsetInfoByConvertGroupId(List<InputDataEO> var1, String var2);

    public List<String> queryByIdAndRecordTimeStamp(Collection<String> var1, long var2, String var4);

    public long updateRuleInfo(Collection<InputDataEO> var1, String var2);

    public List<InputDataEO> queryCheckAmtErrorRecords(OffsetDataCheckVO var1);

    public void cancelLockedOffset(String var1, String var2);

    public void cancelLockedOffsetByCurrency(String var1, String var2, List<String> var3);

    public List<InputDataEO> queryByTaskAndDimensions(String var1, Map<String, String> var2);

    public List<InputDataEO> queryIdLimitFieldsByOffsetGroupId(Collection<String> var1, String var2);

    public Set<String> queryByElmModeAndSrcOffsetGroupId(List<String> var1, String var2);

    public void udpateRelationToMergeInputData(List<InputDataDTO> var1, String var2);

    public List<InputDataEO> queryInputDataForRelationToMerge(QueryParamsVO var1);

    public void updateMemoById(String var1, String var2, String var3);

    public void updateRuleAndDcById(Collection<InputDataEO> var1, String var2);

    public List<String> listSubjectCodeBySystemIdAndSubjectCode(String var1, String var2, Set<String> var3);

    public List<Map<String, Object>> queryUnOffset(QueryParamsVO var1, boolean var2);

    public List<InputDataEO> queryByManualBatchOffsetParams(QueryParamsVO var1, String var2, List<Map<String, Object>> var3, ManualBatchOffsetParamsVO var4);

    public List<InputDataEO> queryManualConversionInputDataByOffsetGroupIds(Collection<String> var1, String var2, String var3);

    public void updateOffsetInfoManualConversion(List<InputDataEO> var1, String var2);

    public int getUnOffsetInputDataItemCount(QueryParamsVO var1);
}


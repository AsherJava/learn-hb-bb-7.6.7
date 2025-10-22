/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.inputdata.dto.InputDataDTO
 *  com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition
 *  com.jiuqi.gcreport.inputdata.dto.MergeCalcFilterCondition
 *  com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.CheckFailedInputDataVO
 *  com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO
 *  com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service;

import com.jiuqi.gcreport.inputdata.dto.InputDataDTO;
import com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition;
import com.jiuqi.gcreport.inputdata.dto.MergeCalcFilterCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.CheckFailedInputDataVO;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO;
import com.jiuqi.gcreport.nr.vo.InputDataChangeMonitorEnvVo;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public interface InputDataService {
    public List<InputDataEO> queryByCondition(InputRuleFilterCondition var1);

    public Integer doOffset(List<InputDataEO> var1, GcOffSetVchrDTO var2);

    public Integer doOffsetNewTran(List<InputDataEO> var1, GcOffSetVchrDTO var2);

    public List<InputDataEO> queryUnoffsetedByRuleAndMergeCondition(String var1, MergeCalcFilterCondition var2, String var3);

    public List<InputDataEO> queryOffsetedByRuleAndMergeCondition(String var1, MergeCalcFilterCondition var2);

    public List<InputDataEO> queryOffsetedByInputRuleFilterCondition(InputRuleFilterCondition var1);

    public List<InputDataEO> queryIdLimitFieldsByOffsetGroupId(Collection<String> var1, String var2);

    public void afterSave(DataEntryContext var1, List<InputDataEO> var2, List<String> var3, String var4, InputDataChangeMonitorEnvVo var5);

    public String beforeDelete(List<String> var1, String var2, Map<String, String> var3, String var4);

    public String beforeUpdate(List<InputDataEO> var1, String var2, Map<String, String> var3);

    public void beforeSave(DataEntryContext var1, Map<String, Object> var2);

    public String queryRecordOffsetState(String var1, String var2);

    public List<InputDataEO> queryByIds(Collection<String> var1, String var2);

    public void updateRuleInfo(Collection<InputDataEO> var1);

    public List<CheckFailedInputDataVO> checkOffsetData(OffsetDataCheckVO var1);

    public List<CheckFailedInputDataVO> cancelOffset(OffsetDataCheckVO var1, Boolean var2, Collection<String> var3);

    public String canOffset(List<String> var1, boolean var2, String var3, String var4);

    public JSONObject efdcPenetrableSearch(JtableContext var1, List<String> var2, String var3);

    public List<InputDataEO> queryByTaskAndDimensions(String var1, Map<String, String> var2);

    public void udpateRelationToMergeInputData(List<InputDataDTO> var1);

    public List<InputDataDTO> queryInputDataForRelationToMerge(QueryParamsVO var1);

    public String canOffsetByData(List<InputDataEO> var1, boolean var2, String var3);

    public int getUnOffsetInputDataItemCount(QueryParamsVO var1);
}


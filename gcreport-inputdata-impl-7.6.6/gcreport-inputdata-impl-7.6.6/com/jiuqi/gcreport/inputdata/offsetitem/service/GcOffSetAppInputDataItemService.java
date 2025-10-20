/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.offsetitem.vo.ManalOffsetParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.inputdata.offsetitem.service;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.offsetitem.vo.ManalOffsetParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;
import java.util.Map;

public interface GcOffSetAppInputDataItemService {
    public void handleUnitAndOppUnitParam(QueryParamsVO var1);

    public List<Map<String, Object>> handleRuleSpanMethod(List<Map<String, Object>> var1);

    public void manualoffset(ManalOffsetParamsVO var1) throws Exception;

    public List<Map<String, Object>> manualRecordsMergeCalc(GcCalcArgmentsDTO var1, List<InputDataEO> var2, List<String> var3);

    public BusinessResponseEntity<String> listEntryDetails(String var1, String var2, List<String> var3);

    public BusinessResponseEntity<List<String>> queryInputDataOtherShowColumns(String var1, List<String> var2);
}


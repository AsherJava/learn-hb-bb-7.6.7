/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.inputdata.offsetitem.service;

import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public interface GcOffsetManualService {
    public ReadWriteAccessDesc getUnitReadWriteAccessDesc(List<Object> var1, DimensionParamsVO var2);

    public void getUnitStatus(List<Object> var1, DimensionParamsVO var2);

    public void setTitles(List<Map<String, Object>> var1, String var2, String var3, String var4);

    public void setZjTitles(List<Map<String, Object>> var1, boolean var2);

    public JSONObject calcDxje(List<Map<String, Object>> var1);

    public void getInputUnitStatus(List<Object> var1, List<InputDataEO> var2, GcCalcArgmentsDTO var3);
}


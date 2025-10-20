/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import java.util.List;
import java.util.Map;

public interface FetchSettingDesBaseDataService {
    public Map<String, List<BaseDataVO>> getBaseDataByTableAndCode(Map<String, BaseDataParam> var1);

    public List<BaseDataVO> getBaseDataByTableAndCodeSingle(String var1, String var2);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 */
package com.jiuqi.gcreport.inputdata.check.dao;

import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface InputDataCheckDao {
    public Pagination<Map<String, Object>> checkTabDatas(InputDataCheckCondition var1);

    public Pagination<Map<String, Object>> unCheckTabDatas(InputDataCheckCondition var1);

    public Pagination<Map<String, Object>> allCheckTabDatas(InputDataCheckCondition var1);

    public Map<String, Object> manualCheck(InputDataCheckCondition var1);

    public List<InputDataEO> queryInputDataByCheckGroupIds(Collection<String> var1, String var2);

    public void updateInputDataCheckInfos(List<InputDataEO> var1, String var2);

    public void cancelLockedCheck(String var1, String var2);

    public List<InputDataEO> listUnCheckData(InputDataCheckCondition var1);
}


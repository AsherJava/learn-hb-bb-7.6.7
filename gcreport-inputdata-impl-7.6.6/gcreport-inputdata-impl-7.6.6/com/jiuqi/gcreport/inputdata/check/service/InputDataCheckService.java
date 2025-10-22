/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckInitCondition
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckInitVO
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckUpdateMemoVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 */
package com.jiuqi.gcreport.inputdata.check.service;

import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckInitCondition;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckInitVO;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckUpdateMemoVO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import java.util.Map;

public interface InputDataCheckService {
    public Pagination<Map<String, Object>> checkTabDatas(InputDataCheckCondition var1);

    public Pagination<Map<String, Object>> unCheckTabDatas(InputDataCheckCondition var1);

    public Pagination<Map<String, Object>> allCheckTabDatas(InputDataCheckCondition var1);

    public InputDataCheckInitVO initData(InputDataCheckInitCondition var1);

    public Object autoCheck(InputDataCheckCondition var1);

    public Object manualCheck(InputDataCheckCondition var1);

    public String cancelCheck(InputDataCheckCondition var1);

    public void manualCheckSave(InputDataCheckCondition var1);

    public void saveInputDataCheckInfo(String var1, String var2, List<InputDataEO> var3);

    public String cancelCheckData(String var1, String var2, List<InputDataEO> var3);

    public void updateMemo(InputDataCheckUpdateMemoVO var1);

    public Object sumAmt(String var1, List<String> var2);
}


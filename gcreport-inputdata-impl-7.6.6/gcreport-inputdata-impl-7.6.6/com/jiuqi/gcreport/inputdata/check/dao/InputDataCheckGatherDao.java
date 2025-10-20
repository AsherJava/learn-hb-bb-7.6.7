/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 */
package com.jiuqi.gcreport.inputdata.check.dao;

import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckTabEnum;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.Map;

public interface InputDataCheckGatherDao {
    public Pagination<Map<String, Object>> getSumCheckTabData(InputDataCheckCondition var1, InputDataCheckTabEnum var2);
}


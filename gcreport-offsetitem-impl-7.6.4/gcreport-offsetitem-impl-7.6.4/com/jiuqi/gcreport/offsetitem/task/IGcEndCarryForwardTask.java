/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardDataPoolVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.task;

import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardDataPoolVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;

public interface IGcEndCarryForwardTask {
    public void calculate(EndCarryForwardDataPoolVO var1, QueryParamsVO var2, ConsolidatedOptionVO var3);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetPenetrateVO
 */
package com.jiuqi.gcreport.offsetitem.task;

import com.jiuqi.gcreport.offsetitem.vo.GcOffSetPenetrateVO;
import java.util.List;

public interface OffSetPenetrateTask {
    public String getTaskType();

    public List<GcOffSetPenetrateVO> getPenetrateData(String var1, String var2, String var3);
}


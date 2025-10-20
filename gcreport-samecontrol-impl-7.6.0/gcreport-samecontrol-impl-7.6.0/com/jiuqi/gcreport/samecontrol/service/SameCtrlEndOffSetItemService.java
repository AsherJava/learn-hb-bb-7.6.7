/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import java.util.List;

public interface SameCtrlEndOffSetItemService {
    public List<SameCtrlOffSetItemEO> rewriteDisposeParentOffset(GcActionParamsVO var1, String var2);

    public List<SameCtrlOffSetItemEO> rewriteSameParentUnitOffset(GcActionParamsVO var1, String var2);
}


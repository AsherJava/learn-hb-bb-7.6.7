/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.nr.multcheck2.service.IMCMonitor;
import com.jiuqi.nr.multcheck2.web.result.MCUploadResult;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;

public interface IMCExecuteUploadSingleService {
    public MCUploadResult uploadSingleExecute(IMCMonitor var1, MCRunVO var2);
}


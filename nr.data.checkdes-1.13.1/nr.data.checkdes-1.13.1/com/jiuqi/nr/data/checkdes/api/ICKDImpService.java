/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.Message
 */
package com.jiuqi.nr.data.checkdes.api;

import com.jiuqi.nr.data.checkdes.exception.CKDIOException;
import com.jiuqi.nr.data.checkdes.facade.obj.CKDImpMes;
import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.common.Message;

public interface ICKDImpService {
    public Message<CKDImpMes> importSync(CKDImpPar var1) throws CKDIOException;
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.param.CommonParams
 */
package com.jiuqi.nr.data.checkdes.service;

import com.jiuqi.nr.data.checkdes.obj.CKDImpMes;
import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.param.CommonParams;

@Deprecated
public interface IImportCKDService {
    @Deprecated
    public String importAsync(CKDImpPar var1, CommonParams var2);

    public Message<CKDImpMes> importSync(CKDImpPar var1, CommonParams var2);
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.api;

import com.jiuqi.nr.data.checkdes.exception.CKDIOException;
import com.jiuqi.nr.data.checkdes.facade.obj.ExpInfo;
import com.jiuqi.nr.data.checkdes.obj.CKDExpPar;

public interface ICKDExportService {
    public ExpInfo export(CKDExpPar var1) throws CKDIOException;

    public ExpInfo export(CKDExpPar var1, String var2) throws CKDIOException;
}


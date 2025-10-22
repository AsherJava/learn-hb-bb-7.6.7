/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.service;

import nr.single.para.compare.bean.ParaCompareContext;

public interface PrintSchemeCompareService {
    public boolean comparePrintSchemeDefine(ParaCompareContext var1) throws Exception;

    public boolean comparPrintSchemeDefine(ParaCompareContext var1, String var2) throws Exception;

    public void batchDelete(ParaCompareContext var1, String var2) throws Exception;
}


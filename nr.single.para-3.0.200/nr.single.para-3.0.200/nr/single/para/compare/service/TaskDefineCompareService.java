/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.service;

import java.util.List;
import nr.single.para.compare.bean.ParaCompareContext;
import nr.single.para.compare.bean.ParaCompareResult;

public interface TaskDefineCompareService {
    public boolean compareTaskDefine(ParaCompareContext var1, ParaCompareResult var2) throws Exception;

    public boolean compareFMDMDefine(ParaCompareContext var1, ParaCompareResult var2) throws Exception;

    public boolean compareEnumListDefine(ParaCompareContext var1, ParaCompareResult var2) throws Exception;

    public boolean compareEnumDefine(ParaCompareContext var1, String var2, ParaCompareResult var3) throws Exception;

    public boolean compareEnumDefines(ParaCompareContext var1, List<String> var2, ParaCompareResult var3) throws Exception;

    public boolean compareFormListDefine(ParaCompareContext var1, ParaCompareResult var2) throws Exception;

    public boolean compareFormDefine(ParaCompareContext var1, String var2, ParaCompareResult var3) throws Exception;

    public boolean compareFormDefines(ParaCompareContext var1, List<String> var2, ParaCompareResult var3) throws Exception;

    public boolean compareFormRegionDefine(ParaCompareContext var1, String var2, int var3, String var4, ParaCompareResult var5) throws Exception;

    public void batchDelete(ParaCompareContext var1, String var2) throws Exception;

    public void batchDelete(ParaCompareContext var1, String var2, double var3, double var5) throws Exception;
}


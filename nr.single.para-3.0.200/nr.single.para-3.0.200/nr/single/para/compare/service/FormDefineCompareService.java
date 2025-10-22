/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.service;

import java.util.List;
import nr.single.para.compare.bean.ParaCompareContext;

public interface FormDefineCompareService {
    public boolean compareFormsDefine(ParaCompareContext var1) throws Exception;

    public boolean compareFormsDefine(ParaCompareContext var1, String var2) throws Exception;

    public boolean compareFormsDefines(ParaCompareContext var1, List<String> var2) throws Exception;

    public boolean compareFormRegionDefine(ParaCompareContext var1, String var2, int var3, String var4) throws Exception;

    public void batchDelete(ParaCompareContext var1, String var2) throws Exception;
}


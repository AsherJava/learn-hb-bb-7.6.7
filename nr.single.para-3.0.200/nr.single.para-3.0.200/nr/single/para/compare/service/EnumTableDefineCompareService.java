/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.service;

import java.util.List;
import nr.single.para.compare.bean.ParaCompareContext;

public interface EnumTableDefineCompareService {
    public boolean compareEnumTableDefine(ParaCompareContext var1) throws Exception;

    public boolean compareEnumTableDefinePrefix(ParaCompareContext var1, List<String> var2) throws Exception;

    public boolean compareEnumItemDefine(ParaCompareContext var1, String var2) throws Exception;

    public void batchDelete(ParaCompareContext var1, String var2) throws Exception;
}


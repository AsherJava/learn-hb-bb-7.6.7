/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.service;

import nr.single.para.compare.bean.ParaCompareContext;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.common.CompareContextType;

public interface EnumItemDefineCompareService {
    public boolean compareEnumItemsDefine(ParaCompareContext var1, String var2, String var3, boolean var4, CompareContextType var5, String var6) throws Exception;

    public boolean compareEnumItemsDefine(ParaCompareContext var1, String var2, String var3, boolean var4, CompareContextType var5, CompareDataEnumDTO var6) throws Exception;

    public void batchDelete(ParaCompareContext var1, String var2) throws Exception;
}


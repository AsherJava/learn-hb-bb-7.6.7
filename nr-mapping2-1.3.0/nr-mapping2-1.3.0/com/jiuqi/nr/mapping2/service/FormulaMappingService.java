/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.service;

import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import java.util.List;
import java.util.Map;

public interface FormulaMappingService {
    public List<FormulaMapping> findByMSFormulaForm(String var1, String var2, String var3);

    public Map<String, List<FormulaMapping>> findByMSFormula(String var1, String var2);

    public List<FormulaMapping> findByMS(String var1);

    public void save(String var1, String var2, String var3, List<FormulaMapping> var4);

    public void batchAdd(List<FormulaMapping> var1);

    public void clear(String var1, String var2, String var3);

    public void deleteByMS(String var1);
}


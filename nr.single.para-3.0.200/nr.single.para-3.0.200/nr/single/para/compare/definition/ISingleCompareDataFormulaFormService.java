/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataFormulaFormDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataFormulaFormService {
    public List<CompareDataFormulaFormDTO> listByScheme(String var1, String var2);

    public List<CompareDataFormulaFormDTO> list(CompareDataFormulaFormDTO var1);

    public void add(CompareDataFormulaFormDTO var1) throws SingleCompareException;

    public void update(CompareDataFormulaFormDTO var1) throws SingleCompareException;

    public void delete(CompareDataFormulaFormDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataFormulaFormDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataFormulaFormDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataFormulaFormDTO> var1) throws SingleCompareException;
}


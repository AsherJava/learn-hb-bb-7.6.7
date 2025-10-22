/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataFormulaSchemeDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataFormulaScemeService {
    public List<CompareDataFormulaSchemeDTO> list(CompareDataFormulaSchemeDTO var1);

    public void add(CompareDataFormulaSchemeDTO var1) throws SingleCompareException;

    public void update(CompareDataFormulaSchemeDTO var1) throws SingleCompareException;

    public void delete(CompareDataFormulaSchemeDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataFormulaSchemeDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataFormulaSchemeDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataFormulaSchemeDTO> var1) throws SingleCompareException;
}


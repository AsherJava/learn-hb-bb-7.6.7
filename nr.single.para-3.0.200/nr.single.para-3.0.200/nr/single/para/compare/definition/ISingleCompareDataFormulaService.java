/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataFormulaDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataFormulaService {
    public List<CompareDataFormulaDTO> list(CompareDataFormulaDTO var1);

    public void add(CompareDataFormulaDTO var1) throws SingleCompareException;

    public void update(CompareDataFormulaDTO var1) throws SingleCompareException;

    public void delete(CompareDataFormulaDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataFormulaDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataFormulaDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataFormulaDTO> var1) throws SingleCompareException;
}


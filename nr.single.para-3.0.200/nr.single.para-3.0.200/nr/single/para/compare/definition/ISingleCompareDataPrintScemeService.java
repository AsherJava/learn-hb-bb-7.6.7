/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataPrintSchemeDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataPrintScemeService {
    public List<CompareDataPrintSchemeDTO> list(CompareDataPrintSchemeDTO var1);

    public void add(CompareDataPrintSchemeDTO var1) throws SingleCompareException;

    public void update(CompareDataPrintSchemeDTO var1) throws SingleCompareException;

    public void delete(CompareDataPrintSchemeDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataPrintSchemeDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataPrintSchemeDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataPrintSchemeDTO> var1) throws SingleCompareException;
}


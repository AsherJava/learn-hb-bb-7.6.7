/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataPrintItemDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataPrintItemService {
    public List<CompareDataPrintItemDTO> list(CompareDataPrintItemDTO var1);

    public void add(CompareDataPrintItemDTO var1) throws SingleCompareException;

    public void update(CompareDataPrintItemDTO var1) throws SingleCompareException;

    public void delete(CompareDataPrintItemDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataPrintItemDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataPrintItemDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataPrintItemDTO> var1) throws SingleCompareException;
}


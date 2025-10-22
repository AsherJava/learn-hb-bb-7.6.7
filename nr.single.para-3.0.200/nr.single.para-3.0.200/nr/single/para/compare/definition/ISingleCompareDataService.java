/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataDTO;
import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataService {
    public List<ICompareData> list(CompareDataDTO var1);

    public void add(CompareDataDTO var1) throws SingleCompareException;

    public void update(CompareDataDTO var1) throws SingleCompareException;

    public void delete(CompareDataDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataDTO> var1) throws SingleCompareException;
}


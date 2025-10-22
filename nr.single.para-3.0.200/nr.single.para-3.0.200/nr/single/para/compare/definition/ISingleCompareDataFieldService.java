/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataFieldDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataFieldService {
    public List<CompareDataFieldDTO> list(CompareDataFieldDTO var1);

    public void add(CompareDataFieldDTO var1) throws SingleCompareException;

    public void update(CompareDataFieldDTO var1) throws SingleCompareException;

    public void delete(CompareDataFieldDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataFieldDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataFieldDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataFieldDTO> var1) throws SingleCompareException;
}


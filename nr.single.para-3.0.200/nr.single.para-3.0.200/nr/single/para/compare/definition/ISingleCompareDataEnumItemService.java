/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataEnumItemDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataEnumItemService {
    public List<CompareDataEnumItemDTO> list(CompareDataEnumItemDTO var1);

    public void add(CompareDataEnumItemDTO var1) throws SingleCompareException;

    public void update(CompareDataEnumItemDTO var1) throws SingleCompareException;

    public void delete(CompareDataEnumItemDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataEnumItemDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataEnumItemDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataEnumItemDTO> var1) throws SingleCompareException;
}


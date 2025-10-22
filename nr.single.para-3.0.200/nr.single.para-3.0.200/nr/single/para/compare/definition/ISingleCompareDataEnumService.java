/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataEnumService {
    public CompareDataEnumDTO getByKey(String var1);

    public List<CompareDataEnumDTO> list(CompareDataEnumDTO var1);

    public void add(CompareDataEnumDTO var1) throws SingleCompareException;

    public void update(CompareDataEnumDTO var1) throws SingleCompareException;

    public void delete(CompareDataEnumDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataEnumDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataEnumDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataEnumDTO> var1) throws SingleCompareException;
}


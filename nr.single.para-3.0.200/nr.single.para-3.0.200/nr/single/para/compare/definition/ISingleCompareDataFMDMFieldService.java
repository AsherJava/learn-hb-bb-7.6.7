/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataFMDMFieldDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataFMDMFieldService {
    public List<CompareDataFMDMFieldDTO> list(CompareDataFMDMFieldDTO var1);

    public void add(CompareDataFMDMFieldDTO var1) throws SingleCompareException;

    public void update(CompareDataFMDMFieldDTO var1) throws SingleCompareException;

    public void delete(CompareDataFMDMFieldDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataFMDMFieldDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataFMDMFieldDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataFMDMFieldDTO> var1) throws SingleCompareException;
}


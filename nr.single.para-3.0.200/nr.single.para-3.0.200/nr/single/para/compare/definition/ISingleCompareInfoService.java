/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareInfoDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareInfoService {
    public List<CompareInfoDTO> list(CompareInfoDTO var1);

    public CompareInfoDTO getByKey(String var1);

    public void add(CompareInfoDTO var1) throws SingleCompareException;

    public void update(CompareInfoDTO var1) throws SingleCompareException;

    public void delete(CompareInfoDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareInfoDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareInfoDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareInfoDTO> var1) throws SingleCompareException;
}


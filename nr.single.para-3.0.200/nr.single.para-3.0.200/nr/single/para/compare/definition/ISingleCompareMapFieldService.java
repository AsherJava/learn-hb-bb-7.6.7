/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareMapFieldDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareMapFieldService {
    public List<CompareMapFieldDTO> list(CompareMapFieldDTO var1);

    public void add(CompareMapFieldDTO var1) throws SingleCompareException;

    public void update(CompareMapFieldDTO var1) throws SingleCompareException;

    public void delete(CompareMapFieldDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareMapFieldDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareMapFieldDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareMapFieldDTO> var1) throws SingleCompareException;
}


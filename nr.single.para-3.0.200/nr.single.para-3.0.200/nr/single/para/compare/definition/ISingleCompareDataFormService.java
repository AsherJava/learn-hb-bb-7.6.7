/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataFormService {
    public List<CompareDataFormDTO> list(CompareDataFormDTO var1);

    public void add(CompareDataFormDTO var1) throws SingleCompareException;

    public void update(CompareDataFormDTO var1) throws SingleCompareException;

    public void delete(CompareDataFormDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataFormDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataFormDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataFormDTO> var1) throws SingleCompareException;
}


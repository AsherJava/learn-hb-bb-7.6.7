/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.util.List;
import nr.single.para.compare.definition.CompareDataTaskLinkDTO;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface ISingleCompareDataTaskLinkService {
    public List<CompareDataTaskLinkDTO> list(CompareDataTaskLinkDTO var1);

    public void add(CompareDataTaskLinkDTO var1) throws SingleCompareException;

    public void update(CompareDataTaskLinkDTO var1) throws SingleCompareException;

    public void delete(CompareDataTaskLinkDTO var1) throws SingleCompareException;

    public void batchAdd(List<CompareDataTaskLinkDTO> var1) throws SingleCompareException;

    public void batchUpdate(List<CompareDataTaskLinkDTO> var1) throws SingleCompareException;

    public void batchDelete(List<CompareDataTaskLinkDTO> var1) throws SingleCompareException;
}


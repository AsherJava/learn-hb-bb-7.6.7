/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore2.data.midstoreresult.service;

import java.util.List;
import nr.midstore2.data.midstoreresult.bean.BatchResultsFilter;
import nr.midstore2.data.midstoreresult.bean.MidstoreResult;

public interface IBatchMidstoreResultService {
    public String addResult(MidstoreResult var1);

    public void deleteResult(String var1);

    public void batchDeleteResult(List<String> var1);

    public List<MidstoreResult> getBatchResults(BatchResultsFilter var1);

    public String getResultByKey(String var1);
}


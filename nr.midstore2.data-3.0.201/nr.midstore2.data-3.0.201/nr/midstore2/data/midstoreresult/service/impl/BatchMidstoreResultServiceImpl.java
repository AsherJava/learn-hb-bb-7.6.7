/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore2.data.midstoreresult.service.impl;

import java.util.List;
import java.util.UUID;
import nr.midstore2.data.midstoreresult.bean.BatchResultsFilter;
import nr.midstore2.data.midstoreresult.bean.MidstoreResult;
import nr.midstore2.data.midstoreresult.dao.MidstoreResultDao;
import nr.midstore2.data.midstoreresult.service.IBatchMidstoreResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BatchMidstoreResultServiceImpl
implements IBatchMidstoreResultService {
    @Autowired
    MidstoreResultDao midstoreResultDao;

    @Override
    public String addResult(MidstoreResult result) {
        if (!StringUtils.hasText(result.getKey())) {
            result.setKey(UUID.randomUUID().toString());
        }
        return this.midstoreResultDao.add(result);
    }

    @Override
    public void deleteResult(String resultKey) {
        this.midstoreResultDao.delete(resultKey);
    }

    @Override
    public void batchDeleteResult(List<String> keys) {
        this.midstoreResultDao.batchDelete(keys);
    }

    @Override
    public List<MidstoreResult> getBatchResults(BatchResultsFilter filter) {
        return this.midstoreResultDao.getBatchResults(filter);
    }

    @Override
    public String getResultByKey(String key) {
        return this.midstoreResultDao.getDetail(key);
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.service.impl;

import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dao.FinancialAutoFetchDao;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FetchRangCondition;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.service.FinancialAutoFetchService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialAutoFetchServiceImpl
implements FinancialAutoFetchService {
    @Autowired
    private FinancialAutoFetchDao financialAutoFetchDao;

    @Override
    public List<String> getCashCodesByCf(FetchRangCondition financialFetchCondition) {
        return this.financialAutoFetchDao.getCashCodesByCf(financialFetchCondition);
    }

    @Override
    public List<String> getSubjectCodesByDim(FetchRangCondition financialFetchCondition) {
        return this.financialAutoFetchDao.getSubjectCodesByDim(financialFetchCondition);
    }

    @Override
    public List<String> getSubjectCodesByAging(FetchRangCondition financialFetchCondition) {
        return this.financialAutoFetchDao.getSubjectCodesByAging(financialFetchCondition);
    }
}


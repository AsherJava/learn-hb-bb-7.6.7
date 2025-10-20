/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionResult
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.conversion.executor;

import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionResult;
import com.jiuqi.gcreport.conversion.executor.AbstractConversionFormExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConversionFormExecutorDispatcher {
    @Autowired(required=false)
    private List<AbstractConversionFormExecutor> conversionFormExecutors = Collections.emptyList();

    public List<AbstractConversionFormExecutor> getConversionFormExecutors() {
        ArrayList<AbstractConversionFormExecutor> sortedList = new ArrayList<AbstractConversionFormExecutor>(this.conversionFormExecutors);
        Collections.sort(sortedList, new Comparator<AbstractConversionFormExecutor>(){

            @Override
            public int compare(AbstractConversionFormExecutor o1, AbstractConversionFormExecutor o2) {
                return Integer.valueOf(o1.matchOrder()).compareTo(o2.matchOrder());
            }
        });
        return sortedList;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public GcConversionResult conversion(GcConversionOrgAndFormContextEnv formContextEnv) {
        GcConversionResult conversionResult = new GcConversionResult(0, 0, 0);
        List<AbstractConversionFormExecutor> conversionFormExecutors = this.getConversionFormExecutors();
        for (AbstractConversionFormExecutor conversionFormExecutor : conversionFormExecutors) {
            boolean match = conversionFormExecutor.isMatch(formContextEnv);
            if (!match) continue;
            conversionResult = conversionFormExecutor.conversion(formContextEnv);
            return conversionResult;
        }
        return conversionResult;
    }
}


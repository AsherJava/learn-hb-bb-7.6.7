/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext
 *  com.jiuqi.gcreport.efdcdatacheck.executor.GcFetchDataExecutor
 *  com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo
 *  com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext;
import com.jiuqi.gcreport.efdcdatacheck.executor.GcFetchDataExecutor;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.GcFetchDataService;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcFetchDataServiceImpl
implements GcFetchDataService {
    @Autowired
    private List<GcFetchDataExecutor> fetchDataExecutors;

    @Override
    public GcFetchDataResultInfo fetchData(GcFetchDataEnvContext context) {
        GcFetchDataExecutor fetchDataExecutor = this.findFetchDataExecutor(context);
        GcFetchDataResultInfo fetchDataResult = fetchDataExecutor.execute(context);
        return fetchDataResult;
    }

    @Override
    public GcFetchDataInfo getFieldDefineList(GcFetchDataEnvContext context) {
        GcFetchDataExecutor fetchDataExecutor = this.findFetchDataExecutor(context);
        return fetchDataExecutor.getFieldDefineList(context);
    }

    private GcFetchDataExecutor findFetchDataExecutor(GcFetchDataEnvContext context) {
        List executors = this.fetchDataExecutors.stream().filter(fetchDataExecutor -> fetchDataExecutor.isMatch(context)).collect(Collectors.toList());
        if (executors == null || executors.size() == 0) {
            String errorMsg = "\u5355\u4f4d\uff1a" + context.getOrg().getCode() + " - \u8868\u5355\uff1a" + context.getFormDefine().getTitle() + " \u6839\u636e\u4e0a\u4e0b\u6587\u672a\u5339\u914d\u5230\u5bf9\u5e94\u7684\u53d6\u6570\u5668";
            throw new BusinessRuntimeException(errorMsg);
        }
        if (executors.size() > 1) {
            String executorNames = executors.stream().map(executor -> executor.getClass().getName()).reduce("", (s1, s2) -> s1 + "," + s2);
            String errorMsg = "\u5355\u4f4d\uff1a" + context.getOrg().getCode() + " - \u8868\u5355\uff1a" + context.getFormDefine().getTitle() + " \u6839\u636e\u4e0a\u4e0b\u6587\u5339\u914d\u5230\u591a\u4e2a\u53d6\u6570\u5668\u3010" + executorNames + "\u3011\uff0c\u65e0\u6cd5\u6267\u884c";
            throw new BusinessRuntimeException(errorMsg);
        }
        return (GcFetchDataExecutor)executors.get(0);
    }
}


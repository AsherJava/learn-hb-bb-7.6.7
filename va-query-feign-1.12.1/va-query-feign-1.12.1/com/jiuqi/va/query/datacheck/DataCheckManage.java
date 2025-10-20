/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.datacheck;

import com.jiuqi.va.query.datacheck.DataBaseInterceptor;
import com.jiuqi.va.query.datacheck.InterceptorEnum;
import com.jiuqi.va.query.domain.DataCheckParam;
import com.jiuqi.va.query.domain.DataCheckResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DataCheckManage
implements InitializingBean {
    private static final Map<InterceptorEnum, List<DataBaseInterceptor>> INTERCEPTOR_TYPE_MAP = new HashMap<InterceptorEnum, List<DataBaseInterceptor>>();
    @Autowired(required=false)
    private List<DataBaseInterceptor> checkItemList;

    @Override
    public void afterPropertiesSet() {
        if (!CollectionUtils.isEmpty(this.checkItemList)) {
            for (DataBaseInterceptor dataBaseInterceptor : this.checkItemList) {
                if (CollectionUtils.isEmpty((Collection)INTERCEPTOR_TYPE_MAP.get((Object)dataBaseInterceptor.getType()))) {
                    ArrayList<DataBaseInterceptor> list = new ArrayList<DataBaseInterceptor>();
                    list.add(dataBaseInterceptor);
                    INTERCEPTOR_TYPE_MAP.put(dataBaseInterceptor.getType(), list);
                    continue;
                }
                INTERCEPTOR_TYPE_MAP.get((Object)dataBaseInterceptor.getType()).add(dataBaseInterceptor);
            }
        }
    }

    public DataCheckResult preHandlerByType(DataCheckParam dataCheckParam, InterceptorEnum type) {
        List<DataBaseInterceptor> typeList = INTERCEPTOR_TYPE_MAP.get((Object)type);
        if (CollectionUtils.isEmpty(typeList)) {
            return DataCheckResult.pass();
        }
        DataCheckResult result = DataCheckResult.pass();
        StringBuilder sb = new StringBuilder();
        for (DataBaseInterceptor dataBaseInterceptor : typeList) {
            DataCheckResult dataCheckResult = dataBaseInterceptor.preHandler(dataCheckParam);
            if (dataCheckResult.isPass()) continue;
            result.setPass(false);
            sb.append(dataCheckResult.getMessage()).append("\n");
        }
        result.setMessage(sb.toString());
        return result;
    }
}


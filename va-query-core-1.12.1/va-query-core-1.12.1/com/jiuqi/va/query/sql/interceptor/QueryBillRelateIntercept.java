/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.interceptor.QueryResultInterceptor
 *  com.jiuqi.va.query.template.vo.TemplateRelateQueryVO
 */
package com.jiuqi.va.query.sql.interceptor;

import com.jiuqi.va.query.sql.interceptor.QueryResultInterceptor;
import com.jiuqi.va.query.sql.service.VaQueryRelateInterceptHelper;
import com.jiuqi.va.query.template.vo.TemplateRelateQueryVO;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryBillRelateIntercept
implements QueryResultInterceptor {
    public static final String PROCESSOR_NAME = "QueryBill";
    @Autowired
    private VaQueryRelateInterceptHelper vaQueryRelateInterceptHelper;

    public String getProcessorName() {
        return PROCESSOR_NAME;
    }

    public Boolean isNeedIntercept(TemplateRelateQueryVO templateRelateQueryVO) {
        return true;
    }

    public Map<String, Object> intercept(Map<String, Object> jsonObjects, TemplateRelateQueryVO templateRelateQueryVO) {
        return this.vaQueryRelateInterceptHelper.dealBillVerifyCode(this.getProcessorName(), jsonObjects, templateRelateQueryVO);
    }
}


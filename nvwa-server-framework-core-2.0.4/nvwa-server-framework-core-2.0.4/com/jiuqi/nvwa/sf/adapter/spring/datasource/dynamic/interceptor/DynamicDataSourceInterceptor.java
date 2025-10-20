/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.springadapter.servlet.NvwaHandlerInterceptor
 *  com.jiuqi.nvwa.springadapter.servlet.NvwaRequstResponseBase
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.interceptor;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.util.DynamicDataSourceContextHolder;
import com.jiuqi.nvwa.springadapter.servlet.NvwaHandlerInterceptor;
import com.jiuqi.nvwa.springadapter.servlet.NvwaRequstResponseBase;
import org.springframework.stereotype.Component;

@Component
public class DynamicDataSourceInterceptor
implements NvwaHandlerInterceptor {
    public void afterCompletion(NvwaRequstResponseBase nvwaRequstResponseBase, Object handler, Exception ex) throws Exception {
        DynamicDataSourceContextHolder.clear();
    }
}


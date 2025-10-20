/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.gcreport.onekeymerge.task.factory;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.onekeymerge.task.MergeTaskExecutor;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

@Component
public class MergeTaskExecutorFactory {
    @Autowired(required=false)
    private Map<String, MergeTaskExecutor> mergeTaskExecutorMap;

    public MergeTaskExecutor create(String taskCode) {
        if (StringUtil.isEmpty((String)taskCode)) {
            throw new BusinessRuntimeException("\u4efb\u52a1code\u4e0d\u80fd\u4e3a\u7a7a");
        }
        MergeTaskExecutor mergeTaskExecutor = this.mergeTaskExecutorMap.get(taskCode);
        if (Objects.isNull(mergeTaskExecutor)) {
            throw new BusinessRuntimeException("\u672a\u521b\u5efa\u4efb\u52a1\u6267\u884c\u5668\uff1a" + taskCode);
        }
        return mergeTaskExecutor;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.inputdata.conversion.realtime;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.inputdata.conversion.realtime.IConversionRealTimeExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionRealTimeGather
implements InitializingBean {
    @Autowired(required=false)
    private List<IConversionRealTimeExecutor> conversionRealTimeExecutorList;
    private final Map<String, IConversionRealTimeExecutor> nameToExecutorMap = new ConcurrentHashMap<String, IConversionRealTimeExecutor>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        this.nameToExecutorMap.clear();
        if (CollectionUtils.isEmpty(this.conversionRealTimeExecutorList)) {
            this.conversionRealTimeExecutorList = new ArrayList<IConversionRealTimeExecutor>();
        }
        this.conversionRealTimeExecutorList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getExecutorName())) {
                this.logger.warn("\u5b9e\u65f6\u6298\u7b97\u6267\u884c\u5668{}\uff0c\u6267\u884c\u5668\u540d\u79f0\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (this.nameToExecutorMap.containsKey(item.getExecutorName())) {
                this.logger.warn("\u5b9e\u65f6\u6298\u7b97\u6267\u884c\u5668{}\u88ab\u91cd\u590d\u6ce8\u518c\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getExecutorName());
                return;
            }
            this.nameToExecutorMap.put(item.getExecutorName(), (IConversionRealTimeExecutor)item);
        });
    }

    public IConversionRealTimeExecutor getByExecutorName(String executorName) {
        Assert.isNotEmpty((String)executorName);
        IConversionRealTimeExecutor modelLoader = this.nameToExecutorMap.get(executorName);
        Assert.isNotNull((Object)modelLoader, (String)String.format("\u6ca1\u6709\u540d\u79f0\u4e3a\u3010%s\u3011\u7684\u5b9e\u65f6\u6298\u7b97\u6267\u884c\u5668", executorName), (Object[])new Object[0]);
        return modelLoader;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}


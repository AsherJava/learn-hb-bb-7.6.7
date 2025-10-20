/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.datamapping.impl.gather.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.impl.gather.IAutoMatchServiceGather;
import com.jiuqi.dc.datamapping.impl.service.AutoMatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoMatchServiceGather
implements InitializingBean,
IAutoMatchServiceGather {
    @Autowired(required=false)
    private List<AutoMatchService> autoMatchServiceList;
    private Map<String, AutoMatchService> autoMatchServiceMap = CollectionUtils.newHashMap();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(this.autoMatchServiceList)) {
            this.autoMatchServiceMap.clear();
            return;
        }
        this.autoMatchServiceList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getCode())) {
                this.logger.warn("\u81ea\u52a8\u6620\u5c04\u5339\u914d\u5668\u6807\u8bc6\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.autoMatchServiceMap.containsKey(item.getCode())) {
                this.autoMatchServiceMap.put(item.getCode(), (AutoMatchService)item);
            }
        });
    }

    @Override
    public AutoMatchService getServiceByCode(String code) {
        return this.autoMatchServiceMap.get(code);
    }

    @Override
    public List<AutoMatchService> listAll() {
        return new ArrayList<AutoMatchService>(this.autoMatchServiceMap.values());
    }
}


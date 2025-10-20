/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.bizmodel.impl.orgmapping.provider;

import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.OrgMappingService;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgMappingServiceProvider
implements IOrgMappingServiceProvider {
    private List<OrgMappingService> serviceList;
    private final Map<String, OrgMappingService> serviceMap = new ConcurrentHashMap<String, OrgMappingService>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public OrgMappingServiceProvider(@Autowired(required=false) List<OrgMappingService> serviceList) {
        this.serviceList = serviceList;
        this.afterPropertiesSet();
    }

    private void init() {
        if (CollectionUtils.isEmpty(this.serviceList)) {
            this.serviceMap.clear();
            return;
        }
        this.serviceList.forEach(item -> {
            String[] codeArr;
            if (item.getCode() == null) {
                this.logger.warn("\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u7c7b\u578b\u63d2\u4ef6{}\u89c4\u5219\u7c7b\u578b\u53c2\u6570\u9519\u8bef\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            for (String code : codeArr = item.getCode().split(",")) {
                if (this.serviceMap.containsKey(code)) continue;
                this.serviceMap.put(code, (OrgMappingService)item);
            }
        });
    }

    @Override
    public OrgMappingService getByCode(String code) {
        OrgMappingService orgMappingService = null;
        orgMappingService = StringUtils.isEmpty((String)code) ? this.serviceMap.get("DEFAULT") : ((orgMappingService = this.serviceMap.get(code)) == null ? this.serviceMap.get("DEFAULT") : orgMappingService);
        Assert.isNotNull((Object)orgMappingService, (String)String.format("\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u5355\u4f4d\u6620\u5c04\u4e1a\u52a1\u5c42\u63a5\u53e3", code), (Object[])new Object[0]);
        return orgMappingService;
    }

    public void afterPropertiesSet() {
        this.init();
    }
}


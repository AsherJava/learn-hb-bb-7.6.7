/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.feign.util.FeignUtil
 *  org.springframework.cloud.client.ServiceInstance
 *  org.springframework.cloud.client.discovery.DiscoveryClient
 */
package com.jiuqi.dc.taskscheduling.api;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.core.data.TaskEnableQueryDTO;
import com.jiuqi.va.feign.util.FeignUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class TaskHandlerFactory {
    @Autowired
    private TaskHandlerClient taskHandlerClient;
    @Autowired
    private DiscoveryClient disconvertClient;
    @Value(value="${spring.application.name}")
    private String currAppName;
    private Random random = new Random();

    public TaskHandlerClient getMainTaskHandlerClient() {
        return this.taskHandlerClient;
    }

    @Deprecated
    public TaskHandlerClient getTaskHandlerClientByModule(String moduleName) {
        throw new IllegalAccessError("getTaskHandlerClientByModule\u65b9\u6cd5\u5df2\u7ecf\u5e9f\u5f03\uff0c\u8bf7\u5c06\u4e1a\u52a1\u6a21\u5757\u4ee3\u7801\u4fee\u6539\u4e3a\u8c03\u7528getTaskHandlerClientByAppName\u65b9\u6cd5");
    }

    public TaskHandlerClient getTaskHandlerClientByAppName(TaskHandlerVO handler) {
        return this.getTaskHandlerClientByAppName(this.getExecuteApplicationName(handler));
    }

    public TaskHandlerClient getTaskHandlerClientByAppName(String applicationName) {
        if (StringUtils.isEmpty((String)applicationName)) {
            throw new IllegalArgumentException("\u670d\u52a1\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (Objects.equals(applicationName, this.currAppName)) {
            return this.taskHandlerClient;
        }
        List services = this.disconvertClient.getServices();
        if (!services.contains(applicationName)) {
            throw new IllegalArgumentException(String.format("\u670d\u52a1\u3010%1$s\u3011\u4e0d\u5b58\u5728", applicationName));
        }
        List instances = this.disconvertClient.getInstances(applicationName);
        if (instances.isEmpty()) {
            throw new IllegalArgumentException(String.format("\u670d\u52a1\u3010%1$s\u3011\u6ca1\u6709\u6d3b\u8dc3\u7684\u8282\u70b9", applicationName));
        }
        ServiceInstance node = (ServiceInstance)instances.get(this.random.nextInt(instances.size()));
        String address = node.getUri().toString();
        if (!StringUtils.isEmpty((String)address)) {
            try {
                return (TaskHandlerClient)FeignUtil.getDynamicClient(TaskHandlerClient.class, (String)address);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(String.format("\u627e\u4e0d\u5230TaskHandlerClient\uff0c\u670d\u52a1\u540d\u79f0\uff1a%1$s\uff0c\u670d\u52a1\u5730\u5740\uff1a%2$s", applicationName, address), (Throwable)e);
            }
        }
        throw new BusinessRuntimeException(String.format("\u627e\u4e0d\u5230TaskHandlerClient\uff0c\u670d\u52a1\u540d\u79f0\uff1a%1$s\uff0c\u670d\u52a1\u5730\u5740\uff1a%2$s", applicationName, address));
    }

    public String getExecuteApplicationName(TaskHandlerVO handler) {
        List<String> applicationNames = handler.getApplicationNames();
        if (applicationNames.size() == 1) {
            return applicationNames.get(0);
        }
        if (applicationNames.contains(this.currAppName)) {
            return this.currAppName;
        }
        return applicationNames.get(this.random.nextInt(applicationNames.size()));
    }

    public boolean isEnable(TaskHandlerVO handler, String preTaskName, String preParam) {
        TaskHandlerClient feignClient = this.getTaskHandlerClientByAppName(this.getExecuteApplicationName(handler));
        TaskEnableQueryDTO queryParam = new TaskEnableQueryDTO(handler.getName(), preTaskName, preParam);
        return (Boolean)feignClient.enable(queryParam).getData();
    }

    @Deprecated
    public Collection<String> getSubServices() {
        throw new IllegalAccessError("getSubServices\u65b9\u6cd5\u5df2\u7ecf\u5e9f\u5f03\uff0c\u8bf7\u5c06\u4e1a\u52a1\u6a21\u5757\u4ee3\u7801\u4fee\u6539\u4e3a\u8c03\u7528getRemoteServiceApplicationNames\u65b9\u6cd5");
    }

    public List<String> getRemoteServiceApplicationNames() {
        ArrayList<String> services = new ArrayList<String>(this.disconvertClient.getServices());
        services.remove(this.currAppName);
        Iterator iterator = services.iterator();
        while (iterator.hasNext()) {
            String applicationName = (String)iterator.next();
            List instances = this.disconvertClient.getInstances(applicationName);
            if (!CollectionUtils.isEmpty((Collection)instances)) continue;
            iterator.remove();
        }
        return services;
    }
}


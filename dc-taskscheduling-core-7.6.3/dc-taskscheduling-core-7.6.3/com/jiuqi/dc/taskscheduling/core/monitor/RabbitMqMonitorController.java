/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.taskscheduling.core.monitor;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.taskscheduling.core.monitor.RabbitMessageQueueInfo;
import com.jiuqi.dc.taskscheduling.core.monitor.RabbitMqQueueMonitor;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMqMonitorController {
    @GetMapping(value={"/api/datacenter/v1/rabbitmq/queue/state/{queueName}"})
    public BusinessResponseEntity<RabbitMessageQueueInfo> getQueueState(@PathVariable(value="queueName") String queueName) {
        RabbitMessageQueueInfo info = RabbitMqQueueMonitor.getInstance().getQueueDetailInfo(queueName);
        return BusinessResponseEntity.ok((Object)info);
    }

    @GetMapping(value={"/api/datacenter/v1/rabbitmq/module/state/{moduleName}"})
    public BusinessResponseEntity<List<String>> getQueueStateByModule(@PathVariable(value="moduleName") String moduleName) {
        return BusinessResponseEntity.ok(RabbitMqQueueMonitor.getInstance().listAllQueueStateByModule(moduleName).stream().map(info -> info.toString()).collect(Collectors.toList()));
    }

    @GetMapping(value={"/api/datacenter/v1/rabbitmq/queue/repair/{queueName}"})
    public BusinessResponseEntity<String> tryRepairQueueConsumer(@PathVariable(value="queueName") String queueName) {
        return BusinessResponseEntity.ok((Object)RabbitMqQueueMonitor.getInstance().restart(queueName));
    }

    @GetMapping(value={"/api/datacenter/v1/rabbitmq/record/enable/switch"})
    public BusinessResponseEntity<Boolean> switchRecordEnableState() {
        RabbitMqQueueMonitor.getInstance().switchEnableState();
        return BusinessResponseEntity.ok();
    }
}


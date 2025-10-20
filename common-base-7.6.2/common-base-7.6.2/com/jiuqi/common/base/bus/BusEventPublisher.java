/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.bus;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.bus.BusEvent;
import com.jiuqi.common.base.bus.BusEventListener;
import com.jiuqi.common.base.bus.BusEventResultCallBack;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class BusEventPublisher {
    private final Logger LOGGER = LoggerFactory.getLogger(BusEventPublisher.class);
    @Autowired(required=false)
    private List<BusEventListener> eventListeners = Collections.emptyList();

    public void publish(BusEvent busEvent, Object ... busEventParams) {
        this.publish(busEvent, null, busEventParams);
    }

    public void publish(BusEvent busEvent, BusEventResultCallBack resultCallBack, Object ... busEventParams) {
        if (CollectionUtils.isEmpty(this.eventListeners)) {
            throw new BusinessRuntimeException("\u7cfb\u7edf\u65e0\u4e1a\u52a1\u4e8b\u4ef6\u76d1\u542c\u5668\uff0c\u4e0d\u5141\u8bb8\u53d1\u5e03\u4e1a\u52a1\u6d88\u606f\u4e8b\u4ef6\u3002");
        }
        String busEventType = busEvent.getBusEventType();
        if (ObjectUtils.isEmpty(busEventType)) {
            throw new BusinessRuntimeException("\u4e8b\u4ef6\u7c7b\u578b\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u53d1\u5e03\u4e1a\u52a1\u6d88\u606f\u4e8b\u4ef6\u3002");
        }
        List matchBusEventListeners = this.eventListeners.stream().filter(eventListener -> {
            if (ObjectUtils.isEmpty(eventListener.getEventType())) {
                return false;
            }
            return busEventType.equals(eventListener.getEventType());
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(matchBusEventListeners)) {
            this.LOGGER.warn("\u7cfb\u7edf\u672a\u5339\u914d\u5230\u6807\u8bc6\u4e3a[{}]\u7684\u4e8b\u4ef6\u7c7b\u578b\uff0c\u8df3\u8fc7\u3002", (Object)busEventType);
            return;
        }
        matchBusEventListeners.stream().forEach(busEventListener -> {
            Object result = busEventListener.run(busEventParams);
            if (resultCallBack != null) {
                resultCallBack.run((BusEventListener)busEventListener, result);
            }
        });
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.task;

import com.jiuqi.np.message.task.AbstractMessageProcessor;
import com.jiuqi.np.message.task.StarterMessageProcessor;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class MessageHandleTask {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandleTask.class);
    private final StarterMessageProcessor starterMessageProcessor;

    public MessageHandleTask(ApplicationContext context, StarterMessageProcessor starterMessageProcessor) {
        this.starterMessageProcessor = starterMessageProcessor;
        this.init(context);
    }

    private void init(ApplicationContext context) {
        Map<String, AbstractMessageProcessor> processorMap = context.getBeansOfType(AbstractMessageProcessor.class);
        AbstractMessageProcessor tempProcessor = this.starterMessageProcessor;
        for (Map.Entry<String, AbstractMessageProcessor> entry : processorMap.entrySet()) {
            if (entry.getValue() instanceof StarterMessageProcessor) continue;
            tempProcessor.setNext(entry.getValue());
            tempProcessor = entry.getValue();
        }
        logger.info("found message processors: " + processorMap.keySet().stream().collect(Collectors.joining(", ", "[", "]")));
    }
}


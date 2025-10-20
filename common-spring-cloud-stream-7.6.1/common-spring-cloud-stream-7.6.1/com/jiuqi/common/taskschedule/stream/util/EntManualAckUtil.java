/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  org.springframework.messaging.Message
 */
package com.jiuqi.common.taskschedule.stream.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.taskschedule.stream.core.ack.EntManualAck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

public class EntManualAckUtil {
    private static final Logger logger = LoggerFactory.getLogger(EntManualAckUtil.class);

    public static void ack(Message message) {
        try {
            EntManualAck entManualAck = (EntManualAck)SpringContextUtils.getBean(EntManualAck.class);
            if (entManualAck != null) {
                entManualAck.ack(message);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}


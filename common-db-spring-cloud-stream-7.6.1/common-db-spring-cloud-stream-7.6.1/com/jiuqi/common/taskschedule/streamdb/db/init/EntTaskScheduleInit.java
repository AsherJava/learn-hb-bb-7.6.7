/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.common.taskschedule.streamdb.db.init;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.taskschedule.streamdb.db.util.EntTaskUtils;
import com.jiuqi.common.taskschedule.streamdb.event.EntTaskScheduleReadyEvent;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class EntTaskScheduleInit
implements SmartInitializingSingleton {
    @Override
    public void afterSingletonsInstantiated() {
        new Thread(() -> {
            EntTaskUtils.waitServerStartUp();
            SpringContextUtils.getApplicationContext().publishEvent(new EntTaskScheduleReadyEvent());
        }).start();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.taskschedule.stream.core.entconsumer.EntStreamConsumer
 *  com.jiuqi.common.taskschedule.stream.util.EntMqStreamDynamicUtils
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.base.common.utils.CompressUtil
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.core.service.MessageHandleService
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.dc.taskscheduling.core.consumer;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.taskschedule.stream.core.entconsumer.EntStreamConsumer;
import com.jiuqi.common.taskschedule.stream.util.EntMqStreamDynamicUtils;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.base.common.utils.CompressUtil;
import com.jiuqi.dc.taskscheduling.core.domain.MqErrorInfoDO;
import com.jiuqi.dc.taskscheduling.core.mapper.MqErrorInfoMapper;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.core.service.MessageHandleService;
import com.jiuqi.dc.taskscheduling.core.util.MqLogInfoUtil;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskHandlerStreamConsumer
implements EntStreamConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TaskHandlerStreamConsumer.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void accept(String msgStr) {
        TaskHandleMsg msg = null;
        try {
            CommonUtil.waitServerStartUp();
            msg = (TaskHandleMsg)JsonUtils.readValue((String)msgStr, TaskHandleMsg.class);
            ThreadContext.put((Object)"TASKHANDLEMSG_KEY", (Object)msg);
            ThreadContext.put((Object)"TASKHANDLER_INSTANCEID_KEY", (Object)msg.getInstanceId());
            ThreadContext.put((Object)"TASKHANDLER_RUNNERID_KEY", (Object)msg.getRunnerId());
            ThreadContext.put((Object)"SQLLOGID_KEY", (Object)msg.getTaskItemId());
            ThreadContext.put((Object)"QUEUENAME_KEY", (Object)EntMqStreamDynamicUtils.getQueueNameByBean((Object)this));
            ThreadContext.put((Object)"SQL_RECORD_ENABLE_KEY", (Object)msg.getSqlRecordEnable());
            msg.setParam(Boolean.TRUE.equals(msg.getParamCompress()) ? CompressUtil.deCompress((String)msg.getParam()) : msg.getParam());
            msg.setParamCompress(Boolean.valueOf(false));
            ((MessageHandleService)SpringContextUtils.getBean(MessageHandleService.class)).handlerMsg(msg);
        }
        catch (Exception e) {
            logger.error("\u961f\u5217\u6d88\u606f\u63a5\u6536\u6267\u884c\u5931\u8d25\uff0c\u6d88\u606f\u5185\u5bb9\uff1a" + msgStr + "\uff0c\u5931\u8d25\u539f\u56e0\uff1a\n", e);
            try {
                MqErrorInfoDO mqErrorInfoEO = MqLogInfoUtil.createMqErrorInfo(msgStr, msg != null ? msg.getTaskItemId() : null, e);
                ((MqErrorInfoMapper)SpringContextUtils.getBean(MqErrorInfoMapper.class)).insert((Object)mqErrorInfoEO);
            }
            catch (Exception e1) {
                logger.error("\u8bb0\u5f55MQ\u5f02\u5e38\u65e5\u5fd7\u62a5\u9519\uff1a", e1);
            }
        }
        finally {
            ThreadContext.remove((Object)"TASKHANDLEMSG_KEY");
            ThreadContext.remove((Object)"TASKHANDLER_INSTANCEID_KEY");
            ThreadContext.remove((Object)"TASKHANDLER_RUNNERID_KEY");
            ThreadContext.remove((Object)"SQLLOGID_KEY");
            ThreadContext.remove((Object)"QUEUENAME_KEY");
            ThreadContext.remove((Object)"SQL_RECORD_ENABLE_KEY");
        }
    }
}


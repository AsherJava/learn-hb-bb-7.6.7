/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.common.taskschedule.streamdb.db.init;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.taskschedule.streamdb.db.enums.EntTaskState;
import com.jiuqi.common.taskschedule.streamdb.db.eo.EntTaskInfoEo;
import com.jiuqi.common.taskschedule.streamdb.db.init.EntDbTaskExecuteCollect;
import com.jiuqi.common.taskschedule.streamdb.db.init.pool.EntDbTaskScheduleNamedThreadFactory;
import com.jiuqi.common.taskschedule.streamdb.db.init.pool.EntTaskDbInitPoolHandle;
import com.jiuqi.common.taskschedule.streamdb.db.service.EntDbTaskScheduleService;
import com.jiuqi.common.taskschedule.streamdb.db.util.EntTaskUtils;
import com.jiuqi.common.taskschedule.streamdb.event.EntTaskScheduleReadyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EntDbTaskDispatcherHandler
implements ApplicationListener<EntTaskScheduleReadyEvent> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EntDbTaskScheduleService service;

    @Override
    public void onApplicationEvent(EntTaskScheduleReadyEvent event) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new EntDbTaskScheduleNamedThreadFactory("\u4efb\u52a1\u8c03\u5ea6\u4e3b\u7ebf\u7a0b"));
        this.logger.info("\u542f\u52a8\u5f02\u6b65\u4efb\u52a1\u6267\u884c\u5668");
        this.retry();
        LinkedList sendMessageIdList = new LinkedList();
        executorService.execute(() -> {
            while (true) {
                try {
                    while (true) {
                        this.taskDispatcher(sendMessageIdList);
                        Thread.sleep(1000L);
                    }
                }
                catch (Exception e) {
                    this.logger.error("\u4efb\u52a1\u8c03\u5ea6\u5206\u53d1\u5931\u8d25", e);
                    continue;
                }
                break;
            }
        });
    }

    private void taskDispatcher(List<String> sendMessageIdList) {
        Map<String, ThreadPoolExecutor> queueNameToPoolMap = EntTaskDbInitPoolHandle.getQueueNameToPoolMap();
        if (sendMessageIdList.size() > 0) {
            List<String> finishIdList = this.service.listFinishMessageId(sendMessageIdList);
            sendMessageIdList.removeAll(finishIdList);
            this.service.updateTime(sendMessageIdList);
        }
        ArrayList<String> queueNameList = new ArrayList<String>();
        queueNameToPoolMap.forEach((key, value) -> {
            int max;
            int queueSize = value.getQueue().size();
            if (queueSize < (max = value.getMaximumPoolSize())) {
                queueNameList.add((String)key);
            }
        });
        if (CollectionUtils.isEmpty(queueNameList)) {
            return;
        }
        List<EntTaskInfoEo> receiveTaskList = this.receive(queueNameList);
        if (!CollectionUtils.isEmpty(receiveTaskList)) {
            receiveTaskList.forEach(item -> this.messageDispatcher(queueNameToPoolMap, sendMessageIdList, (EntTaskInfoEo)item));
        }
    }

    private List<EntTaskInfoEo> receive(List<String> queueNameList) {
        List<EntTaskInfoEo> entTaskInfoEoList = this.service.listQueueFirstByQueueNameList(queueNameList);
        if (CollectionUtils.isEmpty(entTaskInfoEoList)) {
            return Collections.emptyList();
        }
        ArrayList<EntTaskInfoEo> receiveList = new ArrayList<EntTaskInfoEo>();
        entTaskInfoEoList.forEach(item -> {
            if (this.service.receive(item.getId())) {
                item.setStatus(EntTaskState.PROCESSING.getValue());
                if (0 == item.getStoreType()) {
                    item.setMessageBody(this.service.getMessageBodyFormClob(item.getId()));
                }
                receiveList.add((EntTaskInfoEo)item);
            }
        });
        return receiveList;
    }

    private void messageDispatcher(Map<String, ThreadPoolExecutor> queueNameToPoolMap, List<String> sendMessageIdList, EntTaskInfoEo item) {
        queueNameToPoolMap.get(item.getQueueName()).execute(() -> {
            Map<String, Function> listenerNameToFunctionMap = EntDbTaskExecuteCollect.getListenerNameToFunctionMap();
            try {
                Function function = listenerNameToFunctionMap.get(item.getQueueName());
                function.apply(item.getMessageBody());
                this.service.updateMessageStatus(item.getId(), EntTaskState.FINISHED);
            }
            catch (Exception e) {
                this.logger.error("\u4efb\u52a1\u8c03\u5ea6\uff0c\u6d88\u606f\u6267\u884c\u8fc7\u7a0b\u4e2d\u5f02\u5e38\uff1a" + e.getMessage(), e);
                this.service.updateErrorMessage(item.getId(), EntTaskUtils.getExceptionStackStr(e));
            }
        });
        sendMessageIdList.add(item.getId());
    }

    private void retry() {
        try {
            List<EntTaskInfoEo> timeoutMessageList = this.service.listAllTimeoutMessage();
            if (CollectionUtils.isEmpty(timeoutMessageList)) {
                return;
            }
            timeoutMessageList.forEach(item -> this.service.retry((EntTaskInfoEo)item));
        }
        catch (Exception e) {
            this.logger.error("\u4efb\u52a1\u8c03\u5ea6\u8d85\u65f6\u91cd\u53d1\u5931\u8d25:" + e.getMessage(), e);
        }
    }
}


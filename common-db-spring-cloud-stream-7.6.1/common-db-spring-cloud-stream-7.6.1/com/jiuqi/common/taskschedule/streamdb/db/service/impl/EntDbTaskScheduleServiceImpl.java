/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.taskschedule.streamdb.db.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.taskschedule.streamdb.db.dao.EntDbTaskScheduleDao;
import com.jiuqi.common.taskschedule.streamdb.db.enums.EntTaskState;
import com.jiuqi.common.taskschedule.streamdb.db.eo.EntTaskInfoEo;
import com.jiuqi.common.taskschedule.streamdb.db.service.EntDbTaskScheduleService;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EntDbTaskScheduleServiceImpl
implements EntDbTaskScheduleService {
    @Resource
    private EntDbTaskScheduleDao dao;

    @Override
    public List<EntTaskInfoEo> listQueueFirstByQueueNameList(List<String> queueNameList) {
        return this.dao.listQueueFirstByQueueNameList(queueNameList);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public boolean receive(String id) {
        return this.dao.receive(id);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void publish(EntTaskInfoEo eo) {
        this.dao.publish(eo);
    }

    @Override
    public List<String> listFinishMessageId(List<String> sendMessageIdList) {
        return this.dao.listFinishMessageId(sendMessageIdList);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void updateTime(List<String> sendMessageIdList) {
        if (CollectionUtils.isEmpty(sendMessageIdList)) {
            return;
        }
        this.dao.updateTime(sendMessageIdList);
    }

    @Override
    public List<EntTaskInfoEo> listAllTimeoutMessage() {
        return this.dao.listAllTimeoutMessage();
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void retry(EntTaskInfoEo timeoutMessage) {
        this.dao.retry(timeoutMessage);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void updateMessageStatus(String id, EntTaskState state) {
        this.dao.updateMessageStatus(id, state);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteHistory() {
        Date now = DateUtils.now();
        this.dao.insertHistoryTableData(now);
        this.dao.deleteHistoryData(now);
        List<String> idList = this.dao.listIdByUpdateTime(now);
        if (!CollectionUtils.isEmpty(idList)) {
            this.dao.deleteClobByIdList(idList);
            this.dao.deleteErrorClobByIdList(idList);
            this.dao.deleteHistoryTableData(idList);
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void updateErrorMessage(String id, String errorInfo) {
        this.dao.updateMessageStatus(id, EntTaskState.ERROR);
        this.dao.updateErrorMessage(id, errorInfo);
    }

    @Override
    public String getMessageBodyFormClob(String id) {
        return this.dao.getMessageBodyFormClob(id);
    }
}


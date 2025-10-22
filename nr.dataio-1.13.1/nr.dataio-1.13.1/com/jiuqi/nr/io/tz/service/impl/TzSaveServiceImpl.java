/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.FlagState;
import com.jiuqi.nr.io.tz.service.IDataSaveDao;
import com.jiuqi.nr.io.tz.service.TzSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

@Service
public class TzSaveServiceImpl
implements TzSaveService {
    @Autowired
    private IDataSaveDao dataSaveDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(TzParams param, FlagState flagState, AsyncTaskMonitor monitor) {
        StopWatch transactionWatch = new StopWatch();
        transactionWatch.start();
        monitor.progressAndMessage(0.0, "\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5f00\u542f\u4e8b\u7269,\u5f00\u59cb\u5c06\u6807\u8bb0\u597d\u7684\u6570\u636e\u88c5\u5165");
        if (flagState.isContainsAdd()) {
            this.addRecord(param, monitor);
        }
        if (flagState.isContainsDel()) {
            this.delRecord(param, monitor);
        }
        if (flagState.isContainsRecordUpdate()) {
            this.dealVersionRecord(param, monitor);
        }
        if (flagState.isContainsNoRecordUpdate()) {
            this.dealOnlyModifyRecord(param, monitor);
        }
        this.dealSBData(param, monitor);
        transactionWatch.stop();
        monitor.progressAndMessage(0.0, "\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u53f0\u8d26\u8868\u88c5\u5165\u6570\u636e\u5b8c\u6210: " + transactionWatch.getTotalTimeSeconds() + "\u79d2");
    }

    public void addRecord(TzParams param, AsyncTaskMonitor monitor) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        this.dataSaveDao.handleAddStateData(param);
        stopWatch.stop();
        monitor.progressAndMessage(0.0, "\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u65b0\u589e\u7684\u6570\u636e\u4ece\u8f85\u52a9\u4e34\u65f6\u8868\u65b0\u589e\u5230\u53f0\u8d26\u4fe1\u606f\u8868\u8017\u65f6: " + stopWatch.getTotalTimeSeconds() + " \u79d2");
    }

    public void delRecord(TzParams param, AsyncTaskMonitor monitor) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        this.dataSaveDao.handleDelStateData2His(param);
        this.dataSaveDao.handleDelStateData(param);
        stopWatch.stop();
        monitor.progressAndMessage(0.0, "\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5904\u7406\u5220\u9664\u53f0\u8d26\u8bb0\u5f55\u8017\u65f6: " + stopWatch.getTotalTimeSeconds() + " \u79d2");
    }

    public void dealVersionRecord(TzParams param, AsyncTaskMonitor monitor) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        this.dataSaveDao.handleUpdateRecordStateData2His(param);
        this.dataSaveDao.handleUpdateRecordStateData(param);
        stopWatch.stop();
        monitor.progressAndMessage(0.0, "\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5904\u7406\u4fee\u6539\u72b6\u6001\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u53f0\u8d26\u8bb0\u5f55\u8017\u65f6: " + stopWatch.getTotalTimeSeconds() + " \u79d2");
    }

    public void dealOnlyModifyRecord(TzParams param, AsyncTaskMonitor monitor) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        this.dataSaveDao.handleUpdateNotRecordStateData(param);
        stopWatch.stop();
        monitor.progressAndMessage(0.0, "\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5904\u7406\u4fee\u6539\u72b6\u6001\u4e0d\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u53f0\u8d26\u8bb0\u5f55\u8017\u65f6: " + stopWatch.getTotalTimeSeconds() + " \u79d2");
    }

    public void dealSBData(TzParams param, AsyncTaskMonitor monitor) {
        if (CollectionUtils.isEmpty(param.getTmpTable().getPeriodicDeploys())) {
            monitor.progressAndMessage(0.0, "\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u6ca1\u6709\u65f6\u671f\u7c7b\u578b\u6307\u6807\u65e0\u9700\u5904\u7406\u53f0\u8d26\u62a5\u8868");
            return;
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        if (!param.getFullOrAdd().equals("F")) {
            this.dataSaveDao.delRptUpdateStateData(param);
            this.dataSaveDao.handleRptUpdateStateData(param);
            this.dataSaveDao.handleRptBizKeyOrderIsNUllData(param);
        } else {
            this.dataSaveDao.delStateRptData(param);
            this.dataSaveDao.handleAddAllRptData(param);
        }
        stopWatch.stop();
        monitor.progressAndMessage(0.0, "\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5904\u7406\u53f0\u8d26\u62a5\u8868\u6570\u636e\u8017\u65f6: " + stopWatch.getTotalTimeSeconds() + "\u79d2");
    }
}


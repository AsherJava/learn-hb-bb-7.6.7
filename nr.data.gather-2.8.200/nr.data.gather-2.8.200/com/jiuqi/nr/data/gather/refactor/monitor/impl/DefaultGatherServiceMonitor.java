/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.data.gather.refactor.monitor.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.gather.bean.GatherParam;
import com.jiuqi.nr.data.gather.bean.NodeGatherParam;
import com.jiuqi.nr.data.gather.bean.SelectDataGatherParam;
import com.jiuqi.nr.data.gather.bean.event.GatherCompleteEvent;
import com.jiuqi.nr.data.gather.bean.event.GatherCompleteSource;
import com.jiuqi.nr.data.gather.listener.DataGatherHandler;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.MonitorEventParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.context.ApplicationContext;

public class DefaultGatherServiceMonitor
implements IGatherServiceMonitor {
    private AsyncTaskMonitor monitor;
    private List<DataGatherHandler> dataGatherHandlerList;
    private ApplicationContext applicationContext;

    public DefaultGatherServiceMonitor() {
    }

    public DefaultGatherServiceMonitor(AsyncTaskMonitor monitor, List<DataGatherHandler> dataGatherHandlerList, ApplicationContext context) {
        this.monitor = monitor;
        this.dataGatherHandlerList = dataGatherHandlerList;
        this.applicationContext = context;
    }

    public List<DataGatherHandler> getDataGatherHandlerList() {
        if (this.dataGatherHandlerList == null) {
            this.dataGatherHandlerList = new ArrayList<DataGatherHandler>();
        }
        return this.dataGatherHandlerList;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void executeBefore(MonitorEventParam param) {
        this.getDataGatherHandlerList().forEach(handler -> handler.executeBefore(param.getTargetKey(), param.getDataGatherParam(), param.getGatherEntityMap()));
    }

    @Override
    public void executeAfter(MonitorEventParam param) {
        GatherParam dataGatherParam = param.getDataGatherParam();
        Set<String> formKeys = param.getFormKeys();
        this.getDataGatherHandlerList().forEach(handler -> handler.executeAfter(param.getTargetKey(), dataGatherParam, param.getGatherEntityMap()));
        GatherCompleteSource source = null;
        if (dataGatherParam instanceof NodeGatherParam) {
            NodeGatherParam serviceParam = (NodeGatherParam)dataGatherParam;
            source = new GatherCompleteSource(serviceParam.getFormSchemeKey(), new ArrayList<String>(formKeys), serviceParam.getDimensionCollection(), null, serviceParam.isRecursive());
        } else if (dataGatherParam instanceof SelectDataGatherParam) {
            SelectDataGatherParam serviceParam = (SelectDataGatherParam)dataGatherParam;
            source = new GatherCompleteSource(serviceParam.getFormSchemeKey(), new ArrayList<String>(formKeys), serviceParam.getDimensionCollection(), serviceParam.getSourceKeys(), false);
        }
        if (source != null) {
            this.applicationContext.publishEvent(new GatherCompleteEvent(source));
        }
    }

    public String getTaskId() {
        if (this.monitor != null) {
            return this.monitor.getTaskId();
        }
        return null;
    }

    public String getTaskPoolTask() {
        if (this.monitor != null) {
            return this.monitor.getTaskPoolTask();
        }
        return null;
    }

    public void progressAndMessage(double progress, String message) {
        if (this.monitor != null) {
            this.monitor.progressAndMessage(progress, message);
        }
    }

    public boolean isCancel() {
        if (this.monitor != null) {
            return this.monitor.isCancel();
        }
        return false;
    }

    public void finish(String result, Object detail) {
        if (this.monitor != null) {
            this.monitor.finish(result, detail);
        }
    }

    public void canceling(String result, Object detail) {
        if (this.monitor != null) {
            this.monitor.canceling(result, detail);
        }
    }

    public void canceled(String result, Object detail) {
        if (this.monitor != null) {
            this.monitor.canceled(result, detail);
        }
    }

    public void error(String result, Throwable t) {
        if (this.monitor != null) {
            this.monitor.error(result, t);
        }
    }

    public void error(String result, Throwable t, String detail) {
        if (this.monitor != null) {
            this.monitor.error(result, t, detail);
        }
    }

    public boolean isFinish() {
        if (this.monitor != null) {
            return this.monitor.isFinish();
        }
        return false;
    }
}


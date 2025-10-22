/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.data.logic.internal.impl;

import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.api.param.CalEvent;
import com.jiuqi.nr.data.logic.api.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.facade.listener.ICalculateListener;
import com.jiuqi.nr.data.logic.facade.listener.obj.CalculateInfo;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.spi.ICalculateMonitor;
import com.jiuqi.nr.data.logic.spi.IFmlExecInfoProvider;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class DefCalMonitor
implements ICalculateMonitor {
    private static final Logger logger = LoggerFactory.getLogger(DefCalMonitor.class);
    private final IFmlMonitor fmlMonitor;
    private final List<ICalculateListener> calculateListeners;

    public DefCalMonitor(IFmlMonitor fmlMonitor, List<ICalculateListener> calculateListeners) {
        this.fmlMonitor = fmlMonitor;
        this.calculateListeners = calculateListeners;
    }

    @Override
    public void executeBefore(CalEvent calEvent) {
    }

    @Override
    public void executeAfter(CalEvent calEvent) {
        if (!CollectionUtils.isEmpty(this.calculateListeners)) {
            CalculateInfo calculateInfo = new CalculateInfo();
            calculateInfo.setFormSchemeKey(calEvent.getFormSchemeKey());
            IFmlExecInfoProvider fmlExecInfoProvider = calEvent.getFmlExecInfoProvider();
            if (fmlExecInfoProvider != null) {
                calculateInfo.setDimensionCollection(fmlExecInfoProvider.getDimensionCollection());
                List<FmlExecInfo> fmlExecInfos = fmlExecInfoProvider.getFmlExecInfo();
                if (!CollectionUtils.isEmpty(fmlExecInfos)) {
                    HashSet formKeys = new HashSet();
                    for (FmlExecInfo fmlExecInfo : fmlExecInfos) {
                        List<IParsedExpression> parsedExpressions = fmlExecInfo.getParsedExpressions();
                        if (CollectionUtils.isEmpty(parsedExpressions)) continue;
                        formKeys.addAll(parsedExpressions.stream().map(IParsedExpression::getFormKey).filter(Objects::nonNull).collect(Collectors.toList()));
                    }
                    calculateInfo.setFormKeys(new ArrayList<String>(formKeys));
                }
            }
            for (ICalculateListener calculateListener : this.calculateListeners) {
                try {
                    calculateListener.afterCalculate(calculateInfo);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public String getTaskId() {
        return this.fmlMonitor.getTaskId();
    }

    @Override
    public void progressAndMessage(double currProgress, String message) {
        this.fmlMonitor.progressAndMessage(currProgress, message);
    }

    @Override
    public void error(String message, Throwable sender) {
        this.fmlMonitor.error(message, sender);
    }

    @Override
    public void error(String message, Throwable sender, Object detail) {
        this.fmlMonitor.error(message, sender, detail);
    }

    @Override
    public void finish(String result, Object detail) {
        this.fmlMonitor.finish(result, detail);
    }

    @Override
    public void cancel(String message, Object detail) {
        this.fmlMonitor.cancel(message, detail);
    }

    @Override
    public boolean isCancel() {
        return this.fmlMonitor.isCancel();
    }

    @Override
    public void canceled(String result, Object detail) {
        this.fmlMonitor.canceled(result, detail);
    }

    @Override
    public AbstractMonitor getFmlEngineMonitor() {
        return this.fmlMonitor.getFmlEngineMonitor();
    }
}


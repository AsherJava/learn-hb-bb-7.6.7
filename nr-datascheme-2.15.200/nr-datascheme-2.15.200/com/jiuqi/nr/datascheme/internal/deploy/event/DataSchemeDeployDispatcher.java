/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.core.DeployResult$Result
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployListener
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareEvent
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareListener
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareSource
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeploySource
 *  com.jiuqi.nr.datascheme.api.event.DataTableDeployEvent
 *  com.jiuqi.nr.datascheme.api.event.DataTableDeployListener
 *  com.jiuqi.nr.datascheme.api.event.DataTableDeploySource
 */
package com.jiuqi.nr.datascheme.internal.deploy.event;

import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployListener;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareEvent;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareListener;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareSource;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeploySource;
import com.jiuqi.nr.datascheme.api.event.DataTableDeployEvent;
import com.jiuqi.nr.datascheme.api.event.DataTableDeployListener;
import com.jiuqi.nr.datascheme.api.event.DataTableDeploySource;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DataSchemeDeployDispatcher {
    @Autowired(required=false)
    private List<DataSchemeDeployListener> listeners;
    @Autowired(required=false)
    private List<DataSchemeDeployPrepareListener> prepareListeners;
    @Autowired(required=false)
    private List<DataTableDeployListener> tableListeners;
    @Autowired
    private DataSchemeDeployStatusDaoImpl dataSchemeDeployStatusDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSchemeDeployDispatcher.class);

    private void updateWarning(String dataSchemeKey, String className, String message) {
        String deployWarning;
        DataSchemeDeployStatusDO status = this.dataSchemeDeployStatusDao.getByDataSchemeKey(dataSchemeKey);
        DeployResult deployResult = status.getDeployResult();
        if (null == deployResult) {
            deployResult = new DeployResult();
            status.setDeployResult(deployResult);
        }
        if (null == (deployWarning = deployResult.getMessage())) {
            deployWarning = "\u6570\u636e\u65b9\u6848\u53d1\u5e03\u524d\u4e8b\u4ef6\u6267\u884c\u5f02\u5e38\uff1a";
        }
        deployWarning = deployWarning + "\r\n" + className + "\uff1a" + message;
        deployResult.setMessage(deployWarning);
        if (DeployResult.Result.SUCCESS == deployResult.getResult()) {
            deployResult.setResult(DeployResult.Result.DEPLOY_WARN);
        }
        this.dataSchemeDeployStatusDao.update(status);
    }

    @Component
    public class DataTableDeployEventDispatcher
    implements ApplicationListener<DataTableDeployEvent> {
        @Override
        public void onApplicationEvent(DataTableDeployEvent event) {
            if (CollectionUtils.isEmpty(DataSchemeDeployDispatcher.this.listeners)) {
                return;
            }
            DataTableDeploySource source = event.getSource();
            if (CollectionUtils.isEmpty(DataSchemeDeployDispatcher.this.tableListeners)) {
                return;
            }
            for (DataTableDeployListener listener : DataSchemeDeployDispatcher.this.tableListeners) {
                try {
                    listener.onDataTableDeploy(source);
                }
                catch (Exception e) {
                    LOGGER.error("\u54cd\u5e94\u6570\u636e\u8868\u53d1\u5e03\u4e8b\u4ef6\u5931\u8d25\uff1a{}", (Object)listener.getClass().getName(), (Object)e);
                    DataSchemeDeployDispatcher.this.updateWarning(source.getDataSchemeKey(), listener.getClass().getName(), e.getMessage());
                }
            }
            LOGGER.info("\u54cd\u5e94\u6570\u636e\u8868\u53d1\u5e03\u4e8b\u4ef6\u6267\u884c\u5b8c\u6bd5\uff0c\u53c2\u6570\uff1a{}", (Object)source);
        }
    }

    @Component
    public class DataSchemeDeployEventDispatcher
    implements ApplicationListener<DataSchemeDeployEvent> {
        @Override
        public void onApplicationEvent(DataSchemeDeployEvent event) {
            if (CollectionUtils.isEmpty(DataSchemeDeployDispatcher.this.listeners)) {
                return;
            }
            DataSchemeDeploySource source = event.getSource();
            for (DataSchemeDeployListener listener : DataSchemeDeployDispatcher.this.listeners) {
                try {
                    listener.onDataSchemeDeploy(source);
                }
                catch (Exception e) {
                    LOGGER.error("\u54cd\u5e94\u6570\u636e\u65b9\u6848\u53d1\u5e03\u540e\u4e8b\u4ef6\u5931\u8d25\uff1a{}", (Object)listener.getClass().getName(), (Object)e);
                    DataSchemeDeployDispatcher.this.updateWarning(source.getDataSchemeKey(), listener.getClass().getName(), e.getMessage());
                }
            }
            LOGGER.info("\u54cd\u5e94\u6570\u636e\u65b9\u6848\u53d1\u5e03\u540e\u4e8b\u4ef6\u6267\u884c\u5b8c\u6bd5\uff0c\u53c2\u6570\uff1a{}", (Object)source);
        }
    }

    @Component
    public class DataSchemeDeployPrepareEventDispatcher
    implements ApplicationListener<DataSchemeDeployPrepareEvent> {
        @Override
        public void onApplicationEvent(DataSchemeDeployPrepareEvent event) {
            if (CollectionUtils.isEmpty(DataSchemeDeployDispatcher.this.prepareListeners)) {
                return;
            }
            DataSchemeDeployPrepareSource source = event.getSource();
            for (DataSchemeDeployPrepareListener listener : DataSchemeDeployDispatcher.this.prepareListeners) {
                try {
                    listener.onDataSchemeDeploy(source);
                }
                catch (Exception e) {
                    LOGGER.error("\u54cd\u5e94\u6570\u636e\u65b9\u6848\u53d1\u5e03\u524d\u4e8b\u4ef6\u5931\u8d25\uff1a{}", (Object)listener.getClass().getName(), (Object)e);
                    DataSchemeDeployDispatcher.this.updateWarning(source.getDataSchemeKey(), listener.getClass().getName(), e.getMessage());
                }
            }
            LOGGER.info("\u54cd\u5e94\u6570\u636e\u65b9\u6848\u53d1\u5e03\u524d\u4e8b\u4ef6\u6267\u884c\u5b8c\u6bd5\uff0c\u53c2\u6570\uff1a{}", (Object)source);
        }
    }
}


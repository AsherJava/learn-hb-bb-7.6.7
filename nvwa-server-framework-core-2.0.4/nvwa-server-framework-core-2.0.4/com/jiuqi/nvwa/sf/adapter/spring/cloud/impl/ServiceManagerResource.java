/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.LicenceException
 *  com.jiuqi.bi.authz.licence.LicenceInfo
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.springframework.cloud.client.ServiceInstance
 *  org.springframework.cloud.client.discovery.DiscoveryClient
 */
package com.jiuqi.nvwa.sf.adapter.spring.cloud.impl;

import com.jiuqi.bi.authz.LicenceException;
import com.jiuqi.bi.authz.licence.LicenceInfo;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.ServiceUrl;
import com.jiuqi.nvwa.sf.adapter.spring.Response;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceManager;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceUrlExtend;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.RemoteServiceException;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.SFService;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.ServiceNode;
import com.jiuqi.nvwa.sf.adapter.spring.product.IProductJarService;
import com.jiuqi.nvwa.sf.adapter.spring.product.IProductLineService;
import com.jiuqi.nvwa.sf.adapter.spring.product.domain.ProductJarBean;
import com.jiuqi.nvwa.sf.adapter.spring.product.domain.ProductLineBean;
import com.jiuqi.nvwa.sf.daemon.SFStateRefreshDaemon;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.sf.models.SQLFile;
import com.jiuqi.nvwa.sf.operator.AbstractSQLOperator;
import com.jiuqi.nvwa.sf.operator.LicenceOperator;
import com.jiuqi.nvwa.sf.operator.ModulePreUpdater;
import com.jiuqi.nvwa.sf.operator.ModuleUpdater;
import com.jiuqi.nvwa.sf.operator.ModuleUpgradeLockOperator;
import com.jiuqi.nvwa.sf.operator.SQLOperator;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class ServiceManagerResource
implements IServiceManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IProductJarService productJarService;
    @Autowired
    private IProductLineService productLineService;
    @Autowired(required=false)
    private IServiceUrlExtend serviceUrlExtend;

    @Override
    public String getServiceName() {
        return SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("spring.application.name");
    }

    @Override
    public SFService getService() throws RemoteServiceException {
        SFStateRefreshDaemon.reloadModulesAndLicence();
        SFService sfService = new SFService();
        Framework framework = Framework.getInstance();
        if (Framework.useAuthzCenterMode()) {
            sfService.setKmsAddress(Framework.getAuthzCenterAddress());
        }
        sfService.setServiceTime(Instant.now().toEpochMilli());
        sfService.setServiceName(this.getServiceName());
        sfService.setProductId(framework.getProductId());
        sfService.setProductVersion(framework.getProductVersion());
        String property = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("jiuqi.nvwa.databaseLimitMode", "false");
        sfService.setDatabaseLimitMode(Boolean.parseBoolean(property));
        sfService.setDevMode(Framework.getInstance().isDevMode());
        try {
            LicenceInfo productLicence = framework.getLicenceManager().getProductLicence(sfService.getProductId());
            sfService.setLicenceInfo(productLicence);
        }
        catch (LicenceException e) {
            this.logger.error(e.getMessage(), e);
        }
        sfService.getModuleList().addAll(framework.getModuleWrappers().values().stream().sorted(Comparator.comparing(o -> o.getModule().getId())).collect(Collectors.toList()));
        sfService.getNodeList().addAll(this.getNodes());
        return sfService;
    }

    @Override
    public ServiceUrl getServiceUrl() {
        if (null != this.serviceUrlExtend) {
            return this.serviceUrlExtend.getServiceUrl(this.getServiceName());
        }
        return null;
    }

    @Override
    public Response saveSerciceUrl(String serviceName, ServiceUrl serviceUrl) {
        if (null != this.serviceUrlExtend) {
            return this.serviceUrlExtend.save(serviceName, serviceUrl);
        }
        return Response.error("\u4e0d\u652f\u6301\u66f4\u65b0\u670d\u52a1\u5730\u5740");
    }

    @Override
    public SFService getProductInfo() {
        SFService sfService = new SFService();
        Framework framework = Framework.getInstance();
        sfService.setServiceName(this.getServiceName());
        sfService.setProductId(framework.getProductId());
        sfService.setProductVersion(framework.getProductVersion());
        return sfService;
    }

    @Override
    public void addLicenceInfo(byte[] data) throws RemoteServiceException {
        if (Framework.useAuthzCenterMode()) {
            return;
        }
        try (Connection conn = Framework.getInstance().getConnectionProvider().getConnection();){
            LicenceOperator.addLicence(conn, Framework.getInstance().getLicenceManager(), Framework.getInstance().getProductId(), data);
            Framework.getInstance().updateServerStatus();
        }
        catch (Exception e) {
            throw new RemoteServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void executeModule(String moduleId) throws RemoteServiceException {
        block31: {
            String property = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("jiuqi.nvwa.databaseLimitMode", "false");
            if (!property.equalsIgnoreCase("false")) {
                throw new RuntimeException("jiuqi.nvwa.databaseLimitMode\u6a21\u5f0f\u4e0b\u4e0d\u5141\u8bb8\u6267\u884c\u6a21\u5757\u5347\u7ea7");
            }
            Framework framework = Framework.getInstance();
            SFStateRefreshDaemon.reloadModulesAndLicence();
            if (moduleId.equalsIgnoreCase("ALL")) {
                try (Connection conn = framework.getConnectionProvider().getConnection();){
                    ModuleUpdater updater = new ModuleUpdater(conn, framework);
                    updater.addCallback(this.logSql(conn));
                    updater.executeAll();
                    break block31;
                }
                catch (Exception e) {
                    throw new RemoteServiceException("\u6267\u884cSQL\u5347\u7ea7\u51fa\u9519", e);
                }
            }
            ModuleWrapper wrapper = framework.getModuleWrappers().get(moduleId);
            if (wrapper == null) {
                return;
            }
            try (Connection conn = framework.getConnectionProvider().getConnection();){
                ModuleUpdater updater = new ModuleUpdater(conn, framework);
                updater.addCallback(this.logSql(conn));
                updater.executeSingle(wrapper);
            }
            catch (Exception e) {
                throw new RemoteServiceException("\u6267\u884cSQL\u5347\u7ea7\u51fa\u9519", e);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<ModuleUpgradeLockOperator.UpgradeLogInfo> executeLogs(long lastTimestamp) {
        try (Connection connection = Framework.getInstance().getConnectionProvider().getConnection();){
            List<ModuleUpgradeLockOperator.UpgradeLogInfo> list = ModuleUpgradeLockOperator.queryLog(connection, lastTimestamp);
            return list;
        }
        catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
            return new ArrayList<ModuleUpgradeLockOperator.UpgradeLogInfo>();
        }
    }

    private AbstractSQLOperator.SQLExecutionCallBack logSql(final Connection conn) {
        return new AbstractSQLOperator.SQLExecutionCallBack(){

            @Override
            public void beginTask() {
            }

            @Override
            public void endTask() {
            }

            @Override
            public void beginModule(ModuleDescriptor module, String moduleVersionGuid) {
            }

            @Override
            public void endModule(ModuleDescriptor module, String moduleVersionGuid) {
            }

            @Override
            public void beginSQLFile(SQLFile file, String moduleVersionGuid) {
                if (Framework.getInstance().isDevMode()) {
                    ModuleUpgradeLockOperator.doLog(conn, 0, file.toString() + "[" + file.getUrl() + "]");
                } else {
                    ModuleUpgradeLockOperator.doLog(conn, 0, file.toString());
                }
            }

            @Override
            public void sqlExecuted(String sql, String moduleVersionGuid) {
                ModuleUpgradeLockOperator.doLog(conn, 1, sql);
            }

            @Override
            public void sqlFailed(String sql, String moduleVersionGuid, Throwable cause, int type) {
                ModuleUpgradeLockOperator.doLog(conn, 1, sql);
                if (type == 0) {
                    ModuleUpgradeLockOperator.doLog(conn, 2, cause.getMessage());
                } else {
                    ModuleUpgradeLockOperator.doLog(conn, 3, cause.getMessage());
                }
            }

            @Override
            public void endSQLFile(SQLFile file, String moduleVersionGuid) {
            }
        };
    }

    private List<ServiceNode> getNodes() {
        ArrayList<ServiceNode> nodes = new ArrayList<ServiceNode>();
        String serverName = this.getServiceName();
        List instances = ((DiscoveryClient)SpringBeanUtils.getBean(DiscoveryClient.class)).getInstances(serverName);
        for (ServiceInstance instance : instances) {
            Map metadata = instance.getMetadata();
            String machineName = metadata.getOrDefault("machine-name", "Unknown");
            ServiceNode node = new ServiceNode();
            node.setIp(instance.getHost());
            node.setPort(String.valueOf(instance.getPort()));
            node.setAlive(true);
            node.setHttps(instance.isSecure());
            node.setName(machineName);
            node.setApplicationName(serverName);
            node.setContextPath(metadata.getOrDefault("context-path", "/"));
            node.setMachineName(machineName);
            node.setMachineCode((String)metadata.get("machine-code"));
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public ModuleWrapper getModuleWrapper(String moduleId) throws RemoteServiceException {
        if (moduleId == null || moduleId.length() == 0) {
            throw new RemoteServiceException("\u6a21\u5757\u4fe1\u606f\u4e3a\u7a7a");
        }
        SFStateRefreshDaemon.reloadModulesAndLicence();
        Framework framework = Framework.getInstance();
        ModuleWrapper wrapper = framework.getModuleWrappers().get(moduleId);
        if (wrapper == null) {
            throw new RemoteServiceException("\u672a\u83b7\u53d6\u5230\u6a21\u5757\u4fe1\u606f");
        }
        StringBuffer sb = new StringBuffer();
        try {
            SQLOperator.printSQL(sb, wrapper, framework.getCurrentDatabase());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new RemoteServiceException(e.getMessage(), e);
        }
        return wrapper;
    }

    @Override
    public List<ProductJarBean> getJarList() {
        return this.productJarService.list();
    }

    @Override
    public List<ProductLineBean> getProductLineList() {
        return this.productLineService.list();
    }

    @Override
    public void preExecute() {
        Framework framework = Framework.getInstance();
        try (Connection conn = framework.getConnectionProvider().getConnection();){
            ModulePreUpdater updater = new ModulePreUpdater(conn, framework);
            updater.addCallback(this.preLogSql(conn));
            updater.exportAll();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void preExecuteForward() {
        Framework framework = Framework.getInstance();
        try (Connection conn = framework.getConnectionProvider().getConnection();){
            ModulePreUpdater updater = new ModulePreUpdater(conn, framework);
            updater.addCallback(this.preLogSql(conn));
            updater.executeForward();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private AbstractSQLOperator.SQLExecutionCallBack preLogSql(final Connection conn) {
        return new AbstractSQLOperator.SQLExecutionCallBack(){

            @Override
            public void sqlFailed(String sql, String moduleVersionGuid, Throwable cause, int type) {
                String log = sql + "\n <span style = 'color: red;'>SQL\u5f02\u5e38: \t" + cause.getMessage() + "</span>\n\n";
                ModuleUpgradeLockOperator.doLog(conn, 1, log);
            }

            @Override
            public void sqlExecuted(String sql, String moduleVersionGuid) {
                ModuleUpgradeLockOperator.doLog(conn, 1, sql + "\n\n");
            }

            @Override
            public void endSQLFile(SQLFile file, String moduleVersionGuid) {
            }

            @Override
            public void endModule(ModuleDescriptor module, String moduleVersionGuid) {
            }

            @Override
            public void beginSQLFile(SQLFile file, String moduleVersionGuid) {
                ModuleUpgradeLockOperator.doLog(conn, 0, " -- " + file.toString() + "\n");
            }

            @Override
            public void beginTask() {
            }

            @Override
            public void endTask() {
            }

            @Override
            public void beginModule(ModuleDescriptor module, String moduleVersionGuid) {
            }
        };
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.LicenceException
 *  com.jiuqi.bi.authz.licence.LicenceInfo
 *  com.jiuqi.bi.authz.licence.LicenceManager
 *  com.jiuqi.bi.authz.licence.MachineCodeGenerator
 *  com.jiuqi.bi.authz.licence.MachineCodeGenerator$MachineInfo
 *  com.jiuqi.bi.core.messagequeue.IMessageQueuePolicy
 *  com.jiuqi.bi.core.messagequeue.MessageEngine
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.IApplicationDetailProvider
 *  com.jiuqi.bi.core.nodekeeper.IGroupProvider
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeState
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder$StateChangeCallBack
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.Version
 *  com.jiuqi.bi.util.topo.LoopGraphException
 *  com.jiuqi.bi.util.topo.Node
 *  com.jiuqi.bi.util.topo.NodeProvider
 *  com.jiuqi.bi.util.topo.TopoOrder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nvwa.sf;

import com.jiuqi.bi.authz.LicenceException;
import com.jiuqi.bi.authz.licence.LicenceInfo;
import com.jiuqi.bi.authz.licence.LicenceManager;
import com.jiuqi.bi.authz.licence.MachineCodeGenerator;
import com.jiuqi.bi.core.messagequeue.IMessageQueuePolicy;
import com.jiuqi.bi.core.messagequeue.MessageEngine;
import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.IApplicationDetailProvider;
import com.jiuqi.bi.core.nodekeeper.IGroupProvider;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeState;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.Version;
import com.jiuqi.bi.util.topo.LoopGraphException;
import com.jiuqi.bi.util.topo.Node;
import com.jiuqi.bi.util.topo.NodeProvider;
import com.jiuqi.bi.util.topo.TopoOrder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.IConnectionProvider;
import com.jiuqi.nvwa.sf.InitializationException;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.ServerStateChangeCallback;
import com.jiuqi.nvwa.sf.adapter.IModuleFileCallback;
import com.jiuqi.nvwa.sf.adapter.spring.NvwaInitiator;
import com.jiuqi.nvwa.sf.adapter.spring.SpringAdapter;
import com.jiuqi.nvwa.sf.adapter.spring.message.amqp.MessageQueueAMQPPolicy;
import com.jiuqi.nvwa.sf.adapter.spring.message.redis.MessageQueueRedisPolicy;
import com.jiuqi.nvwa.sf.adapter.spring.product.IProductLineService;
import com.jiuqi.nvwa.sf.adapter.spring.product.domain.ProductLineBean;
import com.jiuqi.nvwa.sf.adapter.spring.util.CheckLaunchEnvUtil;
import com.jiuqi.nvwa.sf.adapter.spring.util.StackTraceUtil;
import com.jiuqi.nvwa.sf.authz.AuthzUtil;
import com.jiuqi.nvwa.sf.authz.ClientSocketService;
import com.jiuqi.nvwa.sf.authz.KmsServerChecker;
import com.jiuqi.nvwa.sf.authz.KmsServerStateCheckDeamon;
import com.jiuqi.nvwa.sf.authz.impl.ClientSocketServiceImpl;
import com.jiuqi.nvwa.sf.authz.impl.ClientSocketServiceParam;
import com.jiuqi.nvwa.sf.daemon.SFStateRefreshDaemon;
import com.jiuqi.nvwa.sf.legacy.LegacyModule;
import com.jiuqi.nvwa.sf.models.Dependence;
import com.jiuqi.nvwa.sf.models.ISFSystemIdentityService;
import com.jiuqi.nvwa.sf.models.LicenceObj;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.sf.models.ProductDescriptor;
import com.jiuqi.nvwa.sf.models.SQLFile;
import com.jiuqi.nvwa.sf.models.Users;
import com.jiuqi.nvwa.sf.operator.FrameworkOperator;
import com.jiuqi.nvwa.sf.operator.LicenceOperator;
import com.jiuqi.nvwa.sf.operator.ModuleUpdater;
import com.jiuqi.nvwa.sf.operator.VersionOperator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Framework {
    public static final Version NON_VERSION = new Version("0.0.0");
    private static final Logger logger = LoggerFactory.getLogger(Framework.class);
    private static final String JDK_VERSION = System.getProperty("java.version", "0.0.0");
    private static final Framework instance = new Framework();
    private final ProductDescriptor product = new ProductDescriptor();
    private final Map<String, ModuleWrapper> modules = new ConcurrentHashMap<String, ModuleWrapper>();
    private final List<ModuleDescriptor> sortedModules = new ArrayList<ModuleDescriptor>();
    private final LicenceManager licenceManager = new LicenceManager();
    private IConnectionProvider connectionProvider;
    private IGroupProvider groupProvider;
    private IApplicationDetailProvider applicationDetailProvider;
    private ISFSystemIdentityService systemIdentityService;
    private boolean licenceValidate = false;
    private String licenceInvalidateMessage = null;
    private boolean moduleInit = false;
    private IDatabase currentDatabase;
    private boolean isDevMode = false;
    private boolean autoUpdate = false;
    private static final String PROP_DEVMODE_KEY = "jiuqi.nvwa.devmode";
    private static final String PROP_AUTOUPDATE_KEY = "jiuqi.nvwa.autoupdate";
    private static final String PROP_MESSAGE_POLICY = "jiuqi.nvwa.messagequeue";
    private ClientSocketService clientSocketService = null;
    private KmsServerStateCheckDeamon kmsServerStateCheckDeamon = null;
    private SFStateRefreshDaemon sfStateRefreshDaemon = null;
    private boolean kmsSystemPaused = false;
    private String kmsSystemPausedReason = "";
    private boolean licenceSystemPaused = false;
    private String licenceSystemPausedReason = "";
    private long systemPausedTimestamp = 0L;
    private static String authzCenterAddress = null;
    private static Boolean useAuthzCenterMode = null;
    private static final String TODAY_HMS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final long SEVEN_DAY_MILS = 604800000L;

    private Framework() {
    }

    public boolean isDevMode() {
        return this.isDevMode;
    }

    public boolean startSuccessful() {
        return ServiceNodeStateHolder.getState() == ServiceNodeState.RUNNING && this.licenceValidate && this.isModuleValidate();
    }

    public boolean isLicenceValidate() {
        return this.licenceValidate;
    }

    public void setLicenceValidate(boolean licenceValidate) {
        StackTraceUtil.printStackTrace("Framework:licenceValidate:" + licenceValidate);
        this.licenceValidate = licenceValidate;
        if (!this.licenceValidate && ServiceNodeStateHolder.getState() == ServiceNodeState.RUNNING) {
            ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.LICENCE_UNHANDLED);
        }
    }

    public boolean isModuleValidate() {
        for (ModuleWrapper moduleWrapper : this.modules.values()) {
            if ("tobeupdate".equalsIgnoreCase(moduleWrapper.getStatus())) {
                StackTraceUtil.printStackTrace("Framework:isModuleValidate:tobeupdate");
                if (null != moduleWrapper.getModule()) {
                    String id = moduleWrapper.getModule().getId();
                    String name = moduleWrapper.getModule().getName();
                    String version = moduleWrapper.getModule().getVersion();
                    StackTraceUtil.printStackTrace("Framework:isModuleValidate:tobeupdate:id:" + id + ":name:" + name + ":version:" + version);
                }
                return false;
            }
            if (!"preupdate".equalsIgnoreCase(moduleWrapper.getStatus())) continue;
            StackTraceUtil.printStackTrace("Framework:isModuleValidate:preupdate");
            if (null != moduleWrapper.getModule()) {
                String id = moduleWrapper.getModule().getId();
                String name = moduleWrapper.getModule().getName();
                String version = moduleWrapper.getModule().getVersion();
                StackTraceUtil.printStackTrace("Framework:isModuleValidate:preupdate:id:" + id + ":name:" + name + ":version:" + version);
            }
            return false;
        }
        return true;
    }

    public void updateServerStatus() {
        if (!this.isModuleValidate()) {
            ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.MODULE_UNHANDLED);
        }
        if (!this.isLicenceValidate()) {
            ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.LICENCE_UNHANDLED);
        }
        if (this.isModuleValidate() && this.isLicenceValidate() && (ServiceNodeStateHolder.getState() == ServiceNodeState.MODULE_UNHANDLED || ServiceNodeStateHolder.getState() == ServiceNodeState.LICENCE_UNHANDLED)) {
            ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.INIT_MODULES);
        }
    }

    public IDatabase getCurrentDatabase() {
        return this.currentDatabase;
    }

    public String getProductId() {
        return this.getProduct().getId();
    }

    public String getProductVersion() {
        return this.getProduct().getVersion();
    }

    public IConnectionProvider getConnectionProvider() {
        return this.connectionProvider;
    }

    public void setConnectionProvider(IConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void setGroupProvider(IGroupProvider groupProvider) {
        this.groupProvider = groupProvider;
    }

    public void setApplicationDetailProvider(IApplicationDetailProvider applicationDetailProvider) {
        this.applicationDetailProvider = applicationDetailProvider;
    }

    public LicenceManager getLicenceManager() {
        return this.licenceManager;
    }

    public Map<String, ModuleWrapper> getModuleWrappers() {
        return this.modules;
    }

    public List<ModuleDescriptor> getModules() {
        return this.sortedModules;
    }

    public String getLicenceInvalidateMessage() {
        return this.licenceInvalidateMessage;
    }

    public static Framework getInstance() {
        return instance;
    }

    public static Version getJDKVersion() {
        String version = JDK_VERSION;
        int i = version.indexOf(95);
        if (i > 0) {
            version = version.substring(0, i);
        }
        return new Version(version);
    }

    protected ProductDescriptor getProduct() {
        return this.product;
    }

    public String getProductProperty(String key) {
        return this.getProduct().getProperty(key, null);
    }

    public ClientSocketService getClientSocketService() {
        return this.clientSocketService;
    }

    public synchronized void initInternal() throws Exception {
        switch (ServiceNodeStateHolder.getState()) {
            case STOP: {
                logger.info("*****************\u670d\u52a1\u5668\u6846\u67b6\u5f00\u59cb\u542f\u52a8******************");
                this.printProductLine();
                this.registNode();
                ServiceNodeStateHolder.addStateChangeCallback((ServiceNodeStateHolder.StateChangeCallBack)new ServerStateChangeCallback());
                ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.LAUNCHING);
                break;
            }
            case LAUNCHING: {
                this.internalLanching();
                break;
            }
            case INIT_ENV: {
                this.internalInitEnv();
                break;
            }
            case LICENCE_UNHANDLED: 
            case MODULE_UNHANDLED: {
                this.sfStateRefreshDaemon.setDaemonMode(1);
                break;
            }
            case INIT_MODULES: {
                this.internalInitModules();
                break;
            }
            case RUNNING: {
                this.sfStateRefreshDaemon.setDaemonMode(0);
                logger.info("*****************\u670d\u52a1\u5668\u542f\u52a8\u6210\u529f*****************");
                break;
            }
        }
    }

    private void printProductLine() {
        IProductLineService productLineService = (IProductLineService)SpringBeanUtils.getBean(IProductLineService.class);
        List<ProductLineBean> productLineBeans = productLineService.list();
        for (ProductLineBean productLineBean : productLineBeans) {
            logger.info("** \u4ea7\u54c1\u7ebf\u4fe1\u606f\uff1a {}[{}]\t \u7248\u672c\u53f7\uff1a {}\t ", productLineBean.getTitle(), productLineBean.getId(), productLineBean.getVersion());
        }
    }

    private void internalInitEnv() throws Exception {
        try (Connection connection = this.getConnectionProvider().getConnection();){
            this.currentDatabase = FrameworkOperator.checkSelfVersion(connection);
            this.loadUsers();
            this.loadProductFile();
            this.loadModules();
            if (Framework.useAuthzCenterMode()) {
                this.bootstrapAuthzClient(connection);
            } else {
                this.loadDefaultLicence(connection);
                this.loadLicence(connection, true);
            }
            this.checkModulesVersion(connection, true);
            this.checkModuleDependency();
            this.collectModuleSQLFiles();
            this.startStateRefreshDaemon();
            if (!this.isModuleValidate() && this.autoUpdate) {
                new ModuleUpdater(connection, this).executeAll();
            }
            if (this.isLicenceValidate() && this.isModuleValidate()) {
                ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.INIT_MODULES);
            } else {
                logger.error("*****************\u670d\u52a1\u5668\u542f\u52a8\u5931\u8d25*****************");
            }
            if (!this.isLicenceValidate()) {
                logger.info("\u670d\u52a1\u5668\u6388\u6743\u9a8c\u8bc1\u5931\u8d25\uff0c\u8bf7\u5230\u670d\u52a1\u5668\u5730\u5740{}\u5b89\u88c5\u6388\u6743", (Object)"http://<server>:<port>/<context>/sf");
                ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.LICENCE_UNHANDLED);
            } else if (!this.isModuleValidate()) {
                logger.info("\u670d\u52a1\u5668\u6a21\u5757\u7248\u672c\u9a8c\u8bc1\u5931\u8d25\uff0c\u8bf7\u5230\u670d\u52a1\u5668\u5730\u5740{}\u5347\u7ea7\u6a21\u5757", (Object)"http://<server>:<port>/<context>/sf");
                ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.MODULE_UNHANDLED);
            }
        }
        catch (Exception e) {
            ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.STOP);
            logger.error("*****************\u670d\u52a1\u5668\u542f\u52a8\u5931\u8d25*****************", e);
            throw e;
        }
    }

    private void internalInitModules() throws Exception {
        try {
            if (!this.moduleInit) {
                this.initModules();
                this.moduleInit = true;
            }
            ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.RUNNING);
        }
        catch (Exception e) {
            logger.error("*****************\u670d\u52a1\u5668\u542f\u52a8\u5931\u8d25*****************", e);
            ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.STOP);
            throw e;
        }
    }

    private void internalLanching() {
        this.autoUpdate = "true".equalsIgnoreCase(SpringBeanUtils.getApplicationContext().getEnvironment().getProperty(PROP_AUTOUPDATE_KEY));
        if (System.getProperty("nvwa.autoupdate") != null) {
            logger.warn("\u865a\u62df\u673a\u53c2\u6570nvwa.autoupdate\u5c06\u4f1a\u88ab\u5e9f\u5f03\uff0c\u4f7f\u7528jiuqi.nvwa.autoupdate=true\u4ee3\u66ff");
            this.autoUpdate = true;
        }
        this.isDevMode = "true".equalsIgnoreCase(SpringBeanUtils.getApplicationContext().getEnvironment().getProperty(PROP_DEVMODE_KEY));
        if (this.autoUpdate) {
            logger.info("\u670d\u52a1\u5668\u8bbe\u7f6e\u4e86jiuqi.nvwa.autoupdate\u5c5e\u6027\uff0c\u5f53\u670d\u52a1\u9700\u8981\u5347\u7ea7\u65f6\u5c06\u4f1a\u81ea\u52a8\u6267\u884c");
        }
        if (this.isDevMode()) {
            logger.info("\u6b63\u5728\u4ee5\u5f00\u53d1\u6a21\u5f0f\u542f\u52a8\u670d\u52a1");
        }
        CheckLaunchEnvUtil.check();
        ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.INIT_ENV);
    }

    public void init() throws Exception {
        if (ServiceNodeStateHolder.getState() != ServiceNodeState.STOP) {
            logger.warn("\u670d\u52a1\u5668\u6846\u67b6\u5df2\u7ecf\u542f\u52a8\u8fc7");
            return;
        }
        this.initInternal();
    }

    private void startStateRefreshDaemon() {
        try {
            this.sfStateRefreshDaemon = new SFStateRefreshDaemon();
            this.sfStateRefreshDaemon.setDaemon(true);
            this.sfStateRefreshDaemon.start();
        }
        catch (Exception e) {
            logger.error("\u670d\u52a1\u72b6\u6001\u5237\u65b0\u5b88\u62a4\u8fdb\u7a0b\u542f\u52a8\u5f02\u5e38", e);
        }
    }

    private String getServerIp() {
        String defaultIp = "127.0.0.1";
        try {
            String nodeIp = DistributionManager.getInstance().self().getIp();
            if (StringUtils.isNotEmpty((String)nodeIp)) {
                return nodeIp;
            }
            logger.debug("\u4eceInetAddress\u5bf9\u8c61\u53d6Ip\u5730\u5740\u4e3a\u7a7a\uff0c\u51fd\u6570\u8fd4\u56de\u9ed8\u8ba4IP\u5730\u5740\uff1a127.0.0.1");
            return defaultIp;
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5f53\u524d\u670d\u52a1\u5668IP\u5730\u5740\u53d1\u751f\u5f02\u5e38\uff0c\u8fd4\u56de\u9ed8\u8ba4IP\u5730\u5740\uff1a127.0.0.1", e);
            return defaultIp;
        }
    }

    public static String getAuthzCenterAddress() {
        if (null == authzCenterAddress) {
            String kmsAddress = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("jiuqi.nvwa.kms.address");
            authzCenterAddress = null != kmsAddress && !kmsAddress.isEmpty() ? kmsAddress : "";
        }
        return authzCenterAddress;
    }

    private String getServerPort() {
        return DistributionManager.getInstance().self().getPort();
    }

    private String getAdminPort() {
        String customAdminPort = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("jiuqi.nvwa.kms.adminPort");
        if (AuthzUtil.isBlank(customAdminPort)) {
            return "8187";
        }
        return customAdminPort;
    }

    public static boolean useAuthzCenterMode() {
        if (useAuthzCenterMode == null) {
            try {
                useAuthzCenterMode = AuthzUtil.isValidAddress(Framework.getAuthzCenterAddress());
            }
            catch (Exception e) {
                logger.error("\u5224\u65ad\u96c6\u4e2d\u6388\u6743\u6a21\u5f0f\u51fa\u73b0\u672a\u77e5\u5f02\u5e38\uff0c\u5c06\u5f3a\u5236\u7cfb\u7edf\u4f7f\u7528\u975e\u96c6\u4e2d\u6388\u6743\u6a21\u5f0f", e);
                useAuthzCenterMode = false;
            }
        }
        return useAuthzCenterMode;
    }

    private void bootstrapAuthzClient(Connection conn) throws Exception {
        logger.info("\u542f\u52a8\u96c6\u4e2d\u6388\u6743\u6a21\u5f0f");
        String productId = this.getProductId();
        if (AuthzUtil.isBlank(productId)) {
            throw new RuntimeException("\u4ea7\u54c1Id\u4e3a\u7a7a");
        }
        String productVersion = this.getProductVersion();
        if (AuthzUtil.isBlank(productVersion)) {
            throw new RuntimeException("\u4ea7\u54c1\u7248\u672c\u4e3a\u7a7a");
        }
        MachineCodeGenerator.MachineInfo machineInfo = MachineCodeGenerator.getMachineInfo((String)productId);
        if (machineInfo == null) {
            throw new RuntimeException("\u673a\u5668\u4fe1\u606f\u4e3anull");
        }
        String serverIp = this.getServerIp();
        if (AuthzUtil.isBlank(serverIp)) {
            throw new RuntimeException("serverIp\u4e3a\u7a7a");
        }
        String serverPort = this.getServerPort();
        if (AuthzUtil.isBlank(serverPort)) {
            throw new RuntimeException("serverPort\u4e3a\u7a7a");
        }
        String machineCode = machineInfo.getMachineCode();
        if (AuthzUtil.isBlank(machineCode)) {
            throw new RuntimeException("\u673a\u5668\u7801\u4e3a\u7a7a");
        }
        String authzCenterAddress = Framework.getAuthzCenterAddress();
        String adminPort = this.getAdminPort();
        KmsServerChecker serverChecker = KmsServerChecker.getInstance(authzCenterAddress, adminPort);
        serverChecker.refreshState();
        if (serverChecker.isAllServerDown()) {
            throw new Exception("\u6240\u6709KMS\u670d\u52a1\u5668\u5df2\u4e0b\u7ebf\uff0c\u8bf7\u7ba1\u7406\u5458\u68c0\u67e5KMS\u670d\u52a1\u5668\u8fd0\u884c\u662f\u5426\u6b63\u5e38");
        }
        this.kmsServerStateCheckDeamon = new KmsServerStateCheckDeamon(serverChecker);
        this.kmsServerStateCheckDeamon.setDaemon(true);
        this.kmsServerStateCheckDeamon.start();
        ClientSocketServiceParam param = new ClientSocketServiceParam();
        param.setAuthzCenterAddress(AuthzUtil.getFirstAddress(authzCenterAddress));
        param.setProductId(productId);
        param.setProductVersion(productVersion);
        param.setMachineCode(machineCode);
        param.setServerIp(serverIp);
        param.setServerPort(serverPort);
        this.clientSocketService = new ClientSocketServiceImpl(param);
        this.clientSocketService.setSystemStateChangeCallback((systemPaused, reason) -> {
            logger.info("\u7cfb\u7edf\u662f\u5426\u6682\u505c\uff1a[{}]\uff0c{}", (Object)systemPaused, (Object)reason);
            this.setSystemState(systemPaused, reason);
        });
        this.clientSocketService.setLicenceFileReceivedCallback(licenceData -> {
            logger.info("licenceReceived()\u5f00\u59cb");
            try {
                LicenceOperator.addLicenceToMemory(this.licenceManager, productId, licenceData);
            }
            catch (Exception e) {
                logger.error("\u52a0\u8f7d\u6388\u6743\u51fa\u73b0\u5f02\u5e38", e);
                this.clientSocketService.pauseSystem("\u52a0\u8f7d\u6388\u6743\u51fa\u73b0\u5f02\u5e38\uff0c\u539f\u56e0\uff1a" + e.getMessage());
                throw new RuntimeException(e);
            }
            this.clientSocketService.resumeSystem("\u5df2\u4ece\u6388\u6743\u670d\u52a1\u5668\u83b7\u53d6\u6388\u6743\u6587\u4ef6");
            logger.info("licenceReceived()\u7ed3\u675f");
        });
        this.clientSocketService.startup();
    }

    public boolean tryInitModules() throws Exception {
        if (!this.isModuleValidate() || !this.isLicenceValidate()) {
            return false;
        }
        if (ServiceNodeStateHolder.getState() != ServiceNodeState.INIT_MODULES) {
            return false;
        }
        try {
            this.initModules();
            ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.RUNNING);
            this.initInternal();
        }
        catch (Exception e) {
            logger.error("*****************\u670d\u52a1\u5668\u542f\u52a8\u5931\u8d25*****************", e);
            ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.STOP);
            throw e;
        }
        return true;
    }

    public void registNode() throws DistributionException {
        boolean isAutoMaster = "true".equalsIgnoreCase(SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("jiuqi.nvwa.nodekeeper.autosetmaster"));
        DistributionManager dm = DistributionManager.getInstance();
        dm.setPropertiesProvider(key -> SpringBeanUtils.getApplicationContext().getEnvironment().getProperty(key));
        dm.setGroupProvider(this.groupProvider);
        dm.setApplicationDetailProvider(this.applicationDetailProvider);
        dm.start(isAutoMaster);
    }

    private void initModules() throws Exception {
        ((NvwaInitiator)SpringBeanUtils.getBean(NvwaInitiator.class)).onApplicationReady();
        MessageEngine.getInstance().registMessageQueuePolicy((IMessageQueuePolicy)SpringBeanUtils.getBean(MessageQueueAMQPPolicy.class));
        MessageEngine.getInstance().registMessageQueuePolicy((IMessageQueuePolicy)SpringBeanUtils.getBean(MessageQueueRedisPolicy.class));
        MessageEngine.getInstance().setMessageQueuePolicy(SpringBeanUtils.getApplicationContext().getEnvironment().getProperty(PROP_MESSAGE_POLICY, "DATABASE"));
        MessageEngine.getInstance().start();
        List<ModuleDescriptor> list = this.getModules();
        for (ModuleDescriptor desc : list) {
            logger.info("\u6b63\u5728\u542f\u52a8\u6a21\u5757[{}]", (Object)desc);
            new SpringAdapter().execModuleInitiator(desc);
            logger.info("\u6a21\u5757[{}]\u542f\u52a8\u5b8c\u6bd5", (Object)desc);
            ModuleWrapper wrapper = this.modules.get(desc.getId());
            wrapper.setStatus("started");
        }
        for (ModuleDescriptor desc : list) {
            try {
                new SpringAdapter().execModuleInitiatorWhenServerStarted(desc);
            }
            catch (Exception e) {
                logger.error("\u6a21\u5757[{}]\u542f\u52a8\u540e\u6267\u884c\u4e8b\u4ef6\u51fa\u9519", (Object)desc, (Object)e);
            }
        }
    }

    private void loadUsers() throws Exception {
        logger.info("\u6b63\u5728\u52a0\u8f7d\u9ed8\u8ba4\u7528\u6237");
        this.systemIdentityService = (ISFSystemIdentityService)SpringBeanUtils.getBean(ISFSystemIdentityService.class);
    }

    public void loadProductFile() throws Exception {
        logger.info("\u6b63\u5728\u52a0\u8f7d\u4ea7\u54c1\u6587\u4ef6...");
        try (InputStream is = new SpringAdapter().getProductFile();){
            if (is == null) {
                throw new Exception("\u65e0\u6cd5\u627e\u5230\u4ea7\u54c1\u6587\u4ef6");
            }
            this.getProduct().load(is);
        }
        catch (Exception e) {
            logger.error("\u52a0\u8f7d\u4ea7\u54c1\u6587\u4ef6\u51fa\u9519", e);
            throw e;
        }
    }

    private void loadModules() throws Exception {
        logger.info("\u6b63\u5728\u52a0\u8f7d\u6240\u6709\u6a21\u5757\u63cf\u8ff0\u6587\u4ef6");
        try {
            new SpringAdapter().loadModuleFiles(new IModuleFileCallback(){

                @Override
                public void fileLoaded(ModuleWrapper wrapper) throws Exception {
                    Framework.this.modules.put(wrapper.getModule().getId(), wrapper);
                    logger.info("\u5df2\u8bfb\u53d6\u5230\u6a21\u5757\u6587\u4ef6[{}]", (Object)wrapper.getModule());
                    logger.debug("\u6587\u4ef6URL:{}", (Object)wrapper.getUrl());
                }
            });
        }
        catch (Exception e) {
            logger.error("\u52a0\u8f7d\u6a21\u5757\u6587\u4ef6\u9519\u8bef", e);
            throw e;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadDefaultLicence(Connection conn) throws Exception {
        try (InputStream is = new SpringAdapter().getDefaultLicenceFile();){
            if (is == null) {
                return;
            }
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();){
                try {
                    logger.debug("\u52a0\u8f7d\u9ed8\u8ba4\u6388\u6743");
                    byte[] buf = new byte[1024];
                    int length = 0;
                    while ((length = is.read(buf)) != -1) {
                        baos.write(buf, 0, length);
                    }
                }
                finally {
                    is.close();
                }
                LicenceOperator.insertLicenceToDB(conn, baos.toByteArray(), this.getProductId());
            }
        }
    }

    private void loadLicence(Connection conn, boolean doLog) throws Exception {
        if (doLog) {
            logger.info("\u6b63\u5728\u52a0\u8f7d\u6388\u6743\u6587\u4ef6");
        }
        MachineCodeGenerator.createMachineInfo((String)this.getProductId(), (String)"");
        try {
            LicenceObj[] all;
            for (LicenceObj obj : all = LicenceOperator.getAllLicences(conn)) {
                if (!this.getProduct().getId().equals(obj.getProductId())) continue;
                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(obj.getData());){
                    this.licenceManager.load((InputStream)byteArrayInputStream, this.getProduct().getId());
                    this.licenceValidate = true;
                    if (doLog) {
                        logger.info("\u6388\u6743\u6587\u4ef6\u52a0\u8f7d\u6210\u529f");
                    }
                }
                catch (LicenceException e) {
                    if (doLog) {
                        logger.error("\u9a8c\u8bc1\u6388\u6743\u5931\u8d25", e);
                    }
                    this.setLicenceValidate(false);
                    this.licenceInvalidateMessage = e.getMessage();
                }
                return;
            }
            if (doLog) {
                logger.error("\u670d\u52a1\u5668\u672a\u5b89\u88c5\u5f53\u524d\u4ea7\u54c1\u6388\u6743");
            }
            this.setLicenceValidate(false);
            this.licenceInvalidateMessage = "\u670d\u52a1\u5668\u672a\u5b89\u88c5\u5f53\u524d\u4ea7\u54c1\u6388\u6743";
        }
        catch (SQLException e) {
            logger.error("\u8bfb\u53d6\u6388\u6743\u6587\u4ef6\u51fa\u73b0\u9519\u8bef", e);
            throw e;
        }
    }

    public void checkLicence(Connection connection, boolean doLog) {
        try {
            if (!Framework.useAuthzCenterMode()) {
                this.loadLicence(connection, doLog);
            }
            this.licenceManager.getProductLicence(this.getProductId());
        }
        catch (Exception e) {
            this.setLicenceValidate(false);
            this.licenceInvalidateMessage = e.getMessage();
        }
    }

    public Version getModuleInitialVersion(String moduleId) throws Exception {
        try (Connection connection = this.getConnectionProvider().getConnection();){
            Version version = VersionOperator.getModuleInitialVersion(connection, moduleId);
            return version;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void checkModulesVersion(Connection conn, boolean doLog) throws Exception {
        if (doLog) {
            logger.info("\u5f00\u59cb\u6821\u9a8c\u6a21\u5757\u7248\u672c\u4fe1\u606f");
        }
        Map<String, ModuleWrapper> map = this.modules;
        synchronized (map) {
            new SpringAdapter().loadModuleVersions(conn, this);
            Iterator<ModuleWrapper> iterator = this.modules.values().iterator();
            while (iterator.hasNext()) {
                ModuleWrapper mw = iterator.next();
                if ((mw.getDbVersion() == null || mw.getDbVersion().compareTo(NON_VERSION) == 0) && mw.getModule().isDeprecated()) {
                    if (doLog) {
                        logger.info("\u68c0\u6d4b\u5230\u5e9f\u5f03\u6a21\u5757[{}]\u5e76\u5df2\u79fb\u9664\u8be5\u6a21\u5757", (Object)mw.getModule());
                    }
                    iterator.remove();
                    continue;
                }
                if ("preupdate".equalsIgnoreCase(mw.getStatus())) continue;
                if (mw.getDbVersion() == null || mw.getModuleVersion().compareTo(mw.getDbVersion()) > 0) {
                    mw.setStatus("tobeupdate");
                    continue;
                }
                if (mw.getModuleVersion().compareTo(mw.getDbVersion()) == 0) {
                    mw.setStatus("updated");
                    continue;
                }
                if (mw.getModuleVersion().compareTo(mw.getDbVersion()) >= 0) continue;
                if (this.isDevMode()) {
                    if (doLog) {
                        logger.warn("\u68c0\u6d4b\u5230\u6a21\u5757[{}]\u7684\u7a0b\u5e8f\u7248\u672c\u6bd4\u6570\u636e\u5e93\u7248\u672c\u66f4\u65e7\uff0c\u8bf7\u68c0\u67e5\u7a0b\u5e8f\u7248\u672c\uff0c\u5728\u5f00\u53d1\u6a21\u5f0f\u4e0b\u5c06\u5ffd\u7565\u8be5\u95ee\u9898\u3002", (Object)mw.getModule());
                    }
                    mw.setStatus("updated");
                    continue;
                }
                mw.setStatus("unmatch");
            }
        }
    }

    public boolean checkUser(Users.User user) throws Exception {
        String property = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("jiuqi.np.user.superAdmin");
        if (StringUtils.isNotEmpty((String)property) && !property.equalsIgnoreCase(user.getName())) {
            throw new Exception("\u5f53\u524d\u7528\u6237\u65e0\u6743\u767b\u5f55");
        }
        return this.systemIdentityService.checkUser(user.getName(), user.getPassword());
    }

    private void checkModuleDependency() throws Exception {
        try {
            this.getModules().clear();
            this.getModules().addAll(Framework.sort(this.getModuleWrappers().values()));
        }
        catch (Exception e) {
            logger.error("\u68c0\u6d4b\u5230\u6a21\u5757\u95f4\u5b58\u5728\u5faa\u73af\u4f9d\u8d56");
            logger.error(e.getMessage());
            throw e;
        }
    }

    private void collectModuleSQLFiles() {
        ArrayList<String> versionErrors = new ArrayList<String>();
        for (ModuleWrapper mw : this.getModuleWrappers().values()) {
            try {
                new SpringAdapter().loadModuleSQLFiles(mw);
                boolean isDelta = false;
                if (mw.getDbVersion() == null || mw.getDbVersion().compareTo(NON_VERSION) == 0) {
                    for (LegacyModule legacyModule : mw.getLegacies()) {
                        if (legacyModule.getDbVersion() == null || legacyModule.getDbVersion().compareTo(NON_VERSION) <= 0) continue;
                        isDelta = true;
                        break;
                    }
                } else {
                    isDelta = true;
                }
                if (isDelta) {
                    this.excludeSQLFiles(mw.getSqlFiles(), mw.getDbVersion(), mw.getModuleVersion(), true, !this.isDevMode());
                    mw.getSqlFiles().sort(new SQLFileComparator());
                    for (LegacyModule legacyModule : mw.getLegacies()) {
                        this.excludeSQLFiles(legacyModule.getSqlFiles(), legacyModule.getDbVersion(), new Version(legacyModule.getVersion()), true, false);
                        legacyModule.getSqlFiles().sort(new SQLFileComparator());
                    }
                    continue;
                }
                SQLFile maxFullFile = null;
                for (Object file : mw.getSqlFiles()) {
                    if (!((SQLFile)file).isFull()) continue;
                    if (maxFullFile == null) {
                        maxFullFile = file;
                        continue;
                    }
                    if (((SQLFile)file).getVersion().compareTo(maxFullFile.getVersion()) <= 0) continue;
                    maxFullFile = file;
                }
                if (maxFullFile != null) {
                    Object file;
                    Version version = maxFullFile.getVersion();
                    this.excludeSQLFiles(mw.getSqlFiles(), version, mw.getModuleVersion(), true, !this.isDevMode());
                    mw.getSqlFiles().add(maxFullFile);
                    file = mw.getLegacies().iterator();
                    while (file.hasNext()) {
                        LegacyModule lm3 = (LegacyModule)file.next();
                        lm3.getSqlFiles().clear();
                    }
                } else {
                    for (LegacyModule lm4 : mw.getLegacies()) {
                        lm4.getSqlFiles().sort(new SQLFileComparator());
                    }
                }
                mw.getSqlFiles().sort(new SQLFileComparator());
            }
            catch (IllegalArgumentException e) {
                versionErrors.add("\u52a0\u8f7d\u6821\u9a8c\u6a21\u5757[" + mw.getModule().toString() + "]\u7248\u672c\u4fe1\u606f\u51fa\u9519:\u6a21\u5757\u7248\u672c\u76f8\u8f83\u4e8e\u6570\u636e\u5e93\u66f4\u65e7\uff0c\u8bf7\u68c0\u67e5\u90e8\u7f72\u7a0b\u5e8f\u662f\u5426\u6709\u95ee\u9898\u3002" + e.getMessage());
            }
            catch (Exception e) {
                throw new RuntimeException("\u52a0\u8f7d\u6821\u9a8c\u6a21\u5757[" + mw.getModule().toString() + "]\u7248\u672c\u4fe1\u606f\u51fa\u9519:" + e.getMessage(), e);
            }
        }
        if (!versionErrors.isEmpty()) {
            throw new RuntimeException("\n" + StringUtils.join(versionErrors.iterator(), (String)"\n"));
        }
    }

    private void excludeSQLFiles(List<SQLFile> files, Version min, Version max, boolean excludeFull, boolean isStrict) {
        if (min == null) {
            min = NON_VERSION;
        }
        if (max.compareTo(min) < 0 && isStrict) {
            throw new IllegalArgumentException("\u7248\u672c\u53f7\u9519\u8bef\uff0c \u6a21\u5757\u7248\u672c(max):" + max + ", \u6570\u636e\u5e93\u7248\u672c(min):" + min);
        }
        Iterator<SQLFile> iterator = files.iterator();
        while (iterator.hasNext()) {
            SQLFile file = iterator.next();
            if (excludeFull && file.isFull()) {
                iterator.remove();
                continue;
            }
            if (file.getVersion().compareTo(min) > 0 && file.getVersion().compareTo(max) <= 0) continue;
            iterator.remove();
        }
    }

    private static List<ModuleDescriptor> sort(Collection<ModuleWrapper> modules) throws InitializationException {
        ArrayList<ModuleDescriptor> sortedList = new ArrayList<ModuleDescriptor>();
        TopoOrder to = new TopoOrder();
        final ArrayList<Node> nodes = new ArrayList<Node>();
        HashMap<String, Node> allNodesMap = new HashMap<String, Node>();
        ConcurrentHashMap unknownDependencies = new ConcurrentHashMap();
        for (ModuleWrapper m : modules) {
            Node n = new Node((Object)m.getModule());
            allNodesMap.put(m.getModule().getId(), n);
            nodes.add(n);
        }
        for (Node n : allNodesMap.values()) {
            ModuleDescriptor desc = (ModuleDescriptor)n.getBindingData();
            List<Dependence> dependencies = desc.getDependencies();
            for (Dependence dependence : dependencies) {
                String id = dependence.id;
                Node dependenceNode = (Node)allNodesMap.get(id);
                if (dependenceNode == null) {
                    unknownDependencies.putIfAbsent(desc.getId(), new ArrayList());
                    List unknownList = (List)unknownDependencies.get(desc.getId());
                    unknownList.add(id);
                    continue;
                }
                n.getNextNodes().add(dependenceNode);
                dependenceNode.getPrevNodes().add(n);
            }
        }
        if (!unknownDependencies.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            sb.append("\u6a21\u5757\u4f9d\u8d56\u51fa\u73b0\u95ee\u9898\uff0c\u5b58\u5728\u65e0\u6cd5\u627e\u5230\u7684\u4f9d\u8d56\n");
            for (Map.Entry ud : unknownDependencies.entrySet()) {
                sb.append((String)ud.getKey()).append(" : ");
                for (String did : (List)ud.getValue()) {
                    sb.append(" ").append(did);
                }
                sb.append("\n");
            }
        }
        try {
            List list = to.topoOrder(new NodeProvider(){

                public List<Node> getNodes() {
                    return nodes;
                }
            }, true);
            for (Node n : list) {
                sortedList.add((ModuleDescriptor)n.getBindingData());
            }
            Collections.reverse(sortedList);
        }
        catch (LoopGraphException e) {
            throw new InitializationException(e.getMessage(), e, 3);
        }
        return sortedList;
    }

    public void setSystemState(boolean systemPaused, String reason) {
        this.kmsSystemPaused = systemPaused;
        this.kmsSystemPausedReason = reason;
        this.setupSystemPauseTimeStamp();
    }

    public void setLicenceSystemState(boolean licenceSystemPaused, String reason) {
        this.licenceSystemPaused = licenceSystemPaused;
        this.licenceSystemPausedReason = reason;
        this.setupSystemPauseTimeStamp();
    }

    private void setupSystemPauseTimeStamp() {
        if (this.licenceSystemPaused || this.kmsSystemPaused) {
            if (this.systemPausedTimestamp == 0L) {
                this.systemPausedTimestamp = System.currentTimeMillis();
            }
        } else {
            this.systemPausedTimestamp = 0L;
        }
    }

    public boolean isKmsSystemPaused() {
        return this.kmsSystemPaused;
    }

    public String getKmsSystemPausedReason() {
        return this.kmsSystemPausedReason;
    }

    public boolean isLicenceSystemPaused() {
        return this.licenceSystemPaused;
    }

    public String getLicenceSystemPausedReason() {
        return this.licenceSystemPausedReason;
    }

    public long getSystemPausedTimestamp() {
        return this.systemPausedTimestamp;
    }

    public String getLicenceExpire() {
        try {
            LicenceInfo licenceInfo = this.licenceManager.getProductLicence(this.getProductId());
            try {
                this.licenceManager.validateExpiry(this.getProductId());
            }
            catch (LicenceException e) {
                SimpleDateFormat df = new SimpleDateFormat(TODAY_HMS_FORMAT);
                String useTimeStr = df.format(new Date(licenceInfo.getUseTime()));
                return useTimeStr;
            }
            long useTime = licenceInfo.getUseTime();
            long now = System.currentTimeMillis();
            long offset = useTime - now;
            if (offset < 604800000L) {
                SimpleDateFormat df = new SimpleDateFormat(TODAY_HMS_FORMAT);
                String useTimeStr = df.format(new Date(licenceInfo.getUseTime()));
                return useTimeStr;
            }
            return "";
        }
        catch (Exception e) {
            logger.debug("\u83b7\u53d6\u7cfb\u7edf\u6388\u6743\u5230\u671f\u65f6\u95f4\u51fa\u73b0\u5f02\u5e38", e);
            return "";
        }
    }

    private static class SQLFileComparator
    implements Comparator<SQLFile> {
        private SQLFileComparator() {
        }

        @Override
        public int compare(SQLFile o1, SQLFile o2) {
            Version v2;
            Version v1 = o1.getVersion();
            if (v1.compareTo(v2 = o2.getVersion()) == 0) {
                if (o1.isFull()) {
                    return o2.isFull() ? 0 : 1;
                }
                return o2.isFull() ? -1 : 0;
            }
            return v1.compareTo(v2);
        }
    }
}


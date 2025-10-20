/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.snowflake.IdWorker
 *  com.jiuqi.bi.util.type.GUID
 */
package com.jiuqi.bi.core.nodekeeper;

import com.jiuqi.bi.core.nodekeeper.DistributionDAO;
import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.core.nodekeeper.IApplicationDetailProvider;
import com.jiuqi.bi.core.nodekeeper.IGroupProvider;
import com.jiuqi.bi.core.nodekeeper.IPropertiesProvider;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.snowflake.IdWorker;
import com.jiuqi.bi.util.type.GUID;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistributionManager {
    private boolean isStart;
    private Thread deamon;
    private IdWorker snowflakeWorker;
    private Node self;
    private IPropertiesProvider propertiesProvider = new IPropertiesProvider(){

        @Override
        public String getProperty(String key) {
            return System.getProperty(key);
        }
    };
    private IGroupProvider groupProvider;
    private IApplicationDetailProvider applicationDetailProvider;
    private static Logger logger = LoggerFactory.getLogger(DistributionManager.class);
    private static final DistributionManager instance = new DistributionManager();
    private static final String DEFAULT_GROUP = "__default_group";
    private static final String DEFAULT_APPLICATION_NAME = "ALL";

    public static DistributionManager getInstance() {
        return instance;
    }

    private DistributionManager() {
    }

    public void setPropertiesProvider(IPropertiesProvider propertiesProvider) {
        this.propertiesProvider = propertiesProvider;
    }

    public void setGroupProvider(IGroupProvider provider) {
        this.groupProvider = provider;
    }

    public void setApplicationDetailProvider(IApplicationDetailProvider applicationDetailProvider) {
        this.applicationDetailProvider = applicationDetailProvider;
    }

    public synchronized void start() throws DistributionException {
        this.start(false);
    }

    public synchronized void start(final boolean isAutoMaster) throws DistributionException {
        if (this.isStart) {
            logger.info("\u5206\u5e03\u5f0f\u8282\u70b9\u767b\u8bb0\u5f15\u64ce\u5df2\u7ecf\u542f\u52a8");
            return;
        }
        this.isStart = true;
        logger.info("\u68c0\u6d4b\u5e76\u521b\u5efa\u5206\u5e03\u5f0f\u8282\u70b9\u767b\u8bb0\u8868");
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            DistributionDAO.createDistributionTableIfNotExists(conn);
            DistributionDAO.deleteExpireNodes(conn);
            this.registSelf(conn);
        }
        catch (SQLException e2) {
            e2.printStackTrace();
            throw new DistributionException("\u542f\u52a8\u5206\u5e03\u5f0f\u8282\u70b9\u767b\u8bb0\u5f15\u64ce\u5931\u8d25", e2);
        }
        this.deamon = new Thread(new Runnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                while (DistributionManager.this.isStart) {
                    try {
                        block16: {
                            Connection conn = GlobalConnectionProviderManager.getConnection();
                            if (conn == null) {
                                throw new Exception("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
                            }
                            try {
                                if (isAutoMaster) {
                                    boolean autoCommit = conn.getAutoCommit();
                                    conn.setAutoCommit(false);
                                    try {
                                        String machineName = DistributionManager.this.getMachineName();
                                        DistributionDAO.updateTimestamp(conn, machineName, DistributionManager.getDiff(conn));
                                        DistributionDAO.autoSetMaster(conn, machineName);
                                        conn.commit();
                                        break block16;
                                    }
                                    catch (SQLException e) {
                                        conn.rollback();
                                        throw e;
                                    }
                                    finally {
                                        conn.setAutoCommit(autoCommit);
                                    }
                                }
                                DistributionDAO.updateTimestamp(conn, DistributionManager.this.getMachineName(), DistributionManager.getDiff(conn));
                            }
                            finally {
                                conn.close();
                            }
                        }
                        Thread.sleep(15000L);
                    }
                    catch (InterruptedException e) {
                        logger.warn("\u5b9a\u65f6\u53d1\u9001\u5fc3\u8df3\u54cd\u5e94\u7ebf\u7a0b\u88ab\u4e2d\u65ad", e);
                        return;
                    }
                    catch (Throwable e) {
                        logger.error("\u5fc3\u8df3\u54cd\u5e94\u7ebf\u7a0b\u53d1\u751f\u9519\u8bef\uff0c\u4e00\u5206\u949f\u540e\u91cd\u65b0\u5c1d\u8bd5", e);
                        try {
                            Thread.sleep(60000L);
                            logger.info("\u91cd\u65b0\u5c1d\u8bd5\u53d1\u9001\u5fc3\u8df3\u54cd\u5e94");
                        }
                        catch (InterruptedException e1) {
                            logger.warn("\u5b9a\u65f6\u53d1\u9001\u5fc3\u8df3\u54cd\u5e94\u7ebf\u7a0b\u88ab\u4e2d\u65ad", e1);
                            return;
                        }
                    }
                }
            }
        }, "\u5fc3\u8df3\u54cd\u5e94\u7ebf\u7a0b");
        this.deamon.start();
    }

    private void registSelf(Connection conn) throws SQLException, DistributionException {
        String machineName;
        List<Node> list = this.getAllNode();
        boolean hasMaster = false;
        String nodeName = machineName = this.getMachineName();
        String ext_name = this.getExtName();
        if (ext_name != null && ext_name.length() > 0) {
            nodeName = ext_name + "_" + nodeName;
        }
        for (Node node : list) {
            if (node.isMaster()) {
                hasMaster = true;
            }
            if (!node.getMachineName().equals(machineName)) continue;
            if (node.getSnowflakeId() == -1) {
                int snowflake = DistributionDAO.updateSnowFlakeId(conn, machineName);
                node.setSnowflakeId(snowflake);
            }
            this.self = node;
            this.initSnowFlake();
            this.self.setName(nodeName);
            this.self.setIp(this.getIp());
            this.self.setServiceAddress(this.getServiceAddress());
            String configPort = this.getPort();
            if (StringUtils.isNotEmpty((String)configPort)) {
                this.self.setPort(configPort);
            }
            this.self.setContextPath(this.getContext());
            this.self.setHttps(this.getHttps());
            this.self.setGroup(this.getGroup());
            this.self.setApplicationName(this.getApplicationName());
            this.self.setMachineCode(this.getMachineCode());
            this.self.setTimeDiff(DistributionManager.getDiff(conn));
            DistributionDAO.updateNode(conn, this.self);
            DistributionDAO.resetNodeServices(conn, node, this.getServices());
            logger.info("\u672c\u673a\u8282\u70b9[{}]\u767b\u8bb0\u5b8c\u6bd5", (Object)this.self.getName());
            return;
        }
        this.self = new Node();
        this.self.setMachineName(machineName);
        this.self.setName(nodeName);
        this.self.setMaster(!hasMaster);
        this.self.setLastTimeStamp(System.currentTimeMillis());
        this.self.setIp(this.getIp());
        this.self.setAlive(true);
        this.self.setServiceAddress(this.getServiceAddress());
        this.self.setPort(this.getPort());
        this.self.setContextPath(this.getContext());
        this.self.setHttps(this.getHttps());
        this.self.setGroup(this.getGroup());
        this.self.setApplicationName(this.getApplicationName());
        this.self.setMachineCode(this.getMachineCode());
        this.self.setTimeDiff(DistributionManager.getDiff(conn));
        int snowflake = DistributionDAO.addNode(conn, this.self);
        DistributionDAO.resetNodeServices(conn, this.self, this.getServices());
        this.self.setSnowflakeId(snowflake);
        this.initSnowFlake();
        logger.info("\u672c\u673a\u8282\u70b9[{}]\u767b\u8bb0\u5b8c\u6bd5", (Object)this.self.getName());
    }

    private static Long getDiff(Connection conn) throws SQLException {
        Date now;
        ISQLMetadata metadata = DatabaseManager.getInstance().createMetadata(conn);
        long databaseTimestamp = metadata.compareTimestamp((now = new Date()).getTime());
        if (databaseTimestamp == Long.MAX_VALUE) {
            return null;
        }
        return databaseTimestamp;
    }

    private void initSnowFlake() {
        int snowflakeId = this.self.getSnowflakeId();
        this.snowflakeWorker = new IdWorker((long)snowflakeId, 0L);
    }

    public synchronized void stop() {
        this.isStart = false;
        if (this.deamon != null) {
            this.deamon.interrupt();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Node> findNodesByPreName(String prename) throws DistributionException {
        List<Node> list;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        if (conn == null) {
            throw new DistributionException("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
        }
        try {
            list = DistributionDAO.getNodesByPreName(conn, prename);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new DistributionException("\u83b7\u53d6\u8282\u70b9\u5931\u8d25", e);
            }
        }
        conn.close();
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Node findNodeByName(String name) throws DistributionException {
        Node node;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        if (conn == null) {
            throw new DistributionException("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
        }
        try {
            node = DistributionDAO.getNodeByName(conn, name);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new DistributionException("\u83b7\u53d6\u8282\u70b9\u5931\u8d25", e);
            }
        }
        conn.close();
        return node;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resetNodeServices(Node node, List<String> services) throws DistributionException {
        try {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            if (conn == null) {
                throw new DistributionException("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
            }
            try {
                DistributionDAO.resetNodeServices(conn, node, services);
            }
            finally {
                conn.close();
            }
        }
        catch (SQLException e) {
            throw new DistributionException("\u91cd\u7f6e\u8282\u70b9\u670d\u52a1\u5931\u8d25", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Node findNodeByMachineName(String machineName) throws DistributionException {
        Node node;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        if (conn == null) {
            throw new DistributionException("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
        }
        try {
            node = DistributionDAO.getNodeByMachine(conn, machineName);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new DistributionException("\u83b7\u53d6\u8282\u70b9\u5931\u8d25", e);
            }
        }
        conn.close();
        return node;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public List<Node> findNodesByService(String service) throws DistributionException {
        List<Node> list;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        if (conn == null) {
            throw new DistributionException("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
        }
        String group = this.getGroup();
        try {
            list = DistributionDAO.getNodesByFunction(conn, service, group);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new DistributionException("\u83b7\u53d6\u8282\u70b9\u5931\u8d25", e);
            }
        }
        conn.close();
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Node> findNodesByFunctionName(String functionName) throws DistributionException {
        List<Node> list;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        if (conn == null) {
            throw new DistributionException("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
        }
        String group = this.getGroup();
        try {
            list = DistributionDAO.getNodesByFunction(conn, functionName, group);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new DistributionException("\u83b7\u53d6\u8282\u70b9\u5931\u8d25", e);
            }
        }
        conn.close();
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Node> findNodesByApplicationName(String service) throws DistributionException {
        List<Node> list;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        if (conn == null) {
            throw new DistributionException("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
        }
        try {
            list = DistributionDAO.getNodesByApplicationName(conn, service);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new DistributionException("\u83b7\u53d6\u8282\u70b9\u5931\u8d25", e);
            }
        }
        conn.close();
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<String> findAvaliableServices() throws DistributionException {
        ArrayList arrayList;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        if (conn == null) {
            throw new DistributionException("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
        }
        try {
            List<Node> nodes = DistributionDAO.getNodes(conn);
            HashSet<String> set = new HashSet<String>();
            for (Node node : nodes) {
                if (!node.isAlive() || set.contains(node.getApplicationName())) continue;
                set.add(node.getApplicationName());
            }
            arrayList = new ArrayList(set);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new DistributionException("\u83b7\u53d6\u670d\u52a1\u5217\u8868\u5931\u8d25", e);
            }
        }
        conn.close();
        return arrayList;
    }

    public IdWorker getSnowFlakeWorker() {
        return this.snowflakeWorker;
    }

    public Node getSelfNode() throws DistributionException {
        return this.getAndUpdateSelfNode();
    }

    public Node self() {
        return this.self;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Node getAndUpdateSelfNode() throws DistributionException {
        Node node;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        if (conn == null) {
            throw new DistributionException("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
        }
        try {
            Node self = DistributionDAO.getNodeByMachine(conn, this.getMachineName());
            if (self == null) {
                throw new DistributionException("\u65e0\u6cd5\u627e\u5230\u672c\u673a\u8282\u70b9\uff1a" + this.getMachineName());
            }
            this.self = self;
            node = self;
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new DistributionException("\u83b7\u53d6\u8282\u70b9\u5931\u8d25", e);
            }
        }
        conn.close();
        return node;
    }

    public boolean checkTimeDiff() throws DistributionException {
        List<Node> allNode = this.getAllNode();
        for (Node node : allNode) {
            Long timeDiff = node.getTimeDiff();
            if (timeDiff == null) {
                return false;
            }
            if (Math.abs(timeDiff) < 1000L) continue;
            return false;
        }
        return true;
    }

    public List<Node> getAllNode() throws DistributionException {
        List<Node> list;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        if (conn == null) {
            throw new DistributionException("\u65e0\u6cd5\u83b7\u53d6\u4e3b\u8fde\u63a5");
        }
        try {
            list = DistributionDAO.getNodes(conn);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new DistributionException("\u83b7\u53d6\u8282\u70b9\u5931\u8d25", e);
            }
        }
        conn.close();
        return list;
    }

    public String getExtName() {
        String extName = this.propertiesProvider.getProperty("nk.prename");
        if (extName == null) {
            extName = "";
        }
        return extName;
    }

    public String getMachineName() {
        try {
            String machineName = this.propertiesProvider.getProperty("MachineName");
            if (machineName != null && machineName.length() > 0) {
                return machineName;
            }
            machineName = this.propertiesProvider.getProperty("nk.machinename");
            if (machineName != null && machineName.length() > 0) {
                return machineName;
            }
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostName().toUpperCase();
        }
        catch (UnknownHostException e) {
            logger.warn("\u5206\u5e03\u5f0f\u8282\u70b9\u5f15\u64ce\u65e0\u6cd5\u83b7\u53d6\u672c\u673a\u540d\u79f0\uff0c\u5c06\u4ee5\u968f\u673a\u540d\u79f0\u66ff\u4ee3\u3002\u9700\u8981\u7ba1\u7406\u5458\u914d\u7f6e\u673a\u5668\u540d", e);
            return "UnknownHost-" + GUID.newGUID();
        }
    }

    private String getServiceAddress() {
        return this.propertiesProvider.getProperty("nk.serviceAddress");
    }

    private String getContext() {
        String context = this.propertiesProvider.getProperty("nk.contextPath");
        if (context == null) {
            context = this.propertiesProvider.getProperty("server.servlet.context-path");
        }
        return context;
    }

    private boolean getHttps() {
        return "true".equalsIgnoreCase(this.propertiesProvider.getProperty("nk.https"));
    }

    private String getPort() {
        String port = this.propertiesProvider.getProperty("nk.port");
        if (port == null || port.length() == 0) {
            port = this.propertiesProvider.getProperty("server.port");
        }
        return port;
    }

    private String getGroup() {
        String groupProp = this.propertiesProvider.getProperty("nk.nodegroup");
        if (groupProp != null && groupProp.length() > 0) {
            return groupProp;
        }
        if (this.groupProvider != null) {
            return this.groupProvider.getGroup();
        }
        return DEFAULT_GROUP;
    }

    private String getApplicationName() {
        String applicationName = this.propertiesProvider.getProperty("nk.application.name");
        if (applicationName != null && applicationName.length() > 0) {
            return applicationName;
        }
        if (this.applicationDetailProvider != null) {
            return this.applicationDetailProvider.getName();
        }
        return DEFAULT_APPLICATION_NAME;
    }

    private String getMachineCode() {
        if (this.applicationDetailProvider != null) {
            return this.applicationDetailProvider.getMachineCode();
        }
        return "";
    }

    public String getIp() {
        try {
            String nodeIP = this.propertiesProvider.getProperty("NodeIP");
            if (nodeIP != null && !nodeIP.isEmpty()) {
                return nodeIP.toUpperCase();
            }
            nodeIP = this.propertiesProvider.getProperty("nk.nodeip");
            if (nodeIP != null && !nodeIP.isEmpty()) {
                return nodeIP.toUpperCase();
            }
            String interfaceName = this.propertiesProvider.getProperty("nk.interface");
            boolean supportIPV6 = this.supportIPV6();
            InetAddress localhost = DistributionManager.getLocalHostLANAddress(interfaceName, supportIPV6);
            String hostAddress = localhost.getHostAddress();
            if (supportIPV6 && localhost instanceof Inet6Address) {
                return "[" + hostAddress.substring(0, hostAddress.lastIndexOf("%")) + "]";
            }
            return hostAddress;
        }
        catch (UnknownHostException e) {
            logger.warn("\u5206\u5e03\u5f0f\u8282\u70b9\u5f15\u64ce\u65e0\u6cd5\u83b7\u53d6\u672c\u673aIP\uff0c\u5c06\u4ee5\u968f\u673a\u540d\u79f0\u66ff\u4ee3\u3002", e);
            return "UnknownIP-" + GUID.newGUID();
        }
    }

    private List<String> getServices() {
        ArrayList<String> list = new ArrayList<String>();
        String services = this.propertiesProvider.getProperty("nk.services");
        if (services != null) {
            String[] split;
            for (String str : split = services.split(",")) {
                if ((str = str.trim()).length() <= 0) continue;
                list.add(str);
            }
        }
        return list;
    }

    private boolean supportIPV6() {
        String supportIPV6 = this.propertiesProvider.getProperty("nk.supportIPV6");
        if (StringUtils.isNotEmpty((String)supportIPV6)) {
            return Boolean.parseBoolean(supportIPV6);
        }
        return false;
    }

    private static InetAddress getLocalHostLANAddress(String interfaceName, boolean supportIPV6) throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
            while (ifaces.hasMoreElements()) {
                NetworkInterface iface = ifaces.nextElement();
                if (StringUtils.isNotEmpty((String)interfaceName) && !iface.getName().equals(interfaceName) && !iface.getDisplayName().equals(interfaceName) || iface.getName().startsWith("docker") || iface.getDisplayName().startsWith("docker")) continue;
                Enumeration<InetAddress> inetAddrs = iface.getInetAddresses();
                while (inetAddrs.hasMoreElements()) {
                    InetAddress inetAddr = inetAddrs.nextElement();
                    if (!supportIPV6 && inetAddr instanceof Inet6Address || inetAddr.isLoopbackAddress()) continue;
                    if (inetAddr.isSiteLocalAddress()) {
                        return inetAddr;
                    }
                    if (candidateAddress != null) continue;
                    candidateAddress = inetAddr;
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        }
        catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}


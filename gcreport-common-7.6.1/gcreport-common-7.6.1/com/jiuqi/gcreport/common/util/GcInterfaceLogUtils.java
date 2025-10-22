/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.log.entity.LogEntity
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobCreator
 *  org.springframework.jdbc.support.lob.LobHandler
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.log.entity.LogEntity;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Enumeration;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GcInterfaceLogUtils {
    private static final String IP_HEADER_REMOTE_ADDR = "X-Real-IP";
    private static final String IP_HEADER_FORWARDED_FOR = "X-Forwarded-For";
    private static boolean hasInit = false;
    private static String INTRANET_IP = "";
    private static String INTERNET_IP = "";
    private static JdbcTemplate jdbcTemplate;

    public static void info(String moudle, String title, String message) {
        LogEntity entity = new LogEntity();
        entity.setEventLevel(2);
        GcInterfaceLogUtils.logEntityByRequest(entity, moudle, title, message);
        GcInterfaceLogUtils.save(entity);
    }

    public static void warn(String moudle, String title, String message) {
        LogEntity entity = new LogEntity();
        entity.setEventLevel(3);
        GcInterfaceLogUtils.logEntityByRequest(entity, moudle, title, message);
        GcInterfaceLogUtils.save(entity);
    }

    public static void error(String moudle, String title, String message) {
        LogEntity entity = new LogEntity();
        entity.setEventLevel(4);
        GcInterfaceLogUtils.logEntityByRequest(entity, moudle, title, message);
        GcInterfaceLogUtils.save(entity);
    }

    private static void save(final LogEntity entity) {
        String sql = "insert into GC_INTERFACELOG(id, EVENTLEVEL, MOUDLE,TITLE, MESSAGE, EVENTTIME,USERKEY, USERNAME, CLIENTCODE, SERVERIP, CLIENTIP) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        GcInterfaceLogUtils.getJdbcTemplate().execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                int i = 1;
                ps.setObject(i++, UUIDUtils.newUUIDStr());
                ps.setInt(i++, entity.getEventLevel());
                ps.setString(i++, entity.getMoudle());
                ps.setString(i++, entity.getTitle());
                ps.setString(i++, entity.getMessage());
                ps.setTimestamp(i++, Timestamp.from(Instant.now()));
                ps.setString(i++, entity.getUserKey());
                ps.setString(i++, entity.getUserName());
                ps.setString(i++, entity.getClientCode());
                ps.setString(i++, entity.getServerIp());
                ps.setString(i++, entity.getClientIp());
            }
        });
    }

    public static JdbcTemplate getJdbcTemplate() {
        if (null == jdbcTemplate) {
            jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        }
        return jdbcTemplate;
    }

    private static void logEntityByRequest(LogEntity entity, String moudle, String title, String message) {
        ServletRequestAttributes attributes;
        entity.setLogKey(UUID.randomUUID().toString());
        entity.setMoudle(moudle);
        entity.setTitle(title);
        entity.setMessage(message);
        entity.setEventTime(Instant.now());
        NpContext context = NpContextHolder.getContext();
        if (null == context) {
            context = new NpContextImpl();
        }
        String tenant = context.getTenant();
        entity.setTenantId(tenant);
        ContextUser contextUser = context.getUser();
        if (contextUser != null) {
            entity.setUserKey(null == contextUser.getId() ? "" : contextUser.getId());
            entity.setUserName(null == contextUser.getName() ? "" : contextUser.getName());
            entity.setUserType(contextUser.getType());
        }
        if ((attributes = GcInterfaceLogUtils.currentRequestAttributes()) == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String clientCode = request.getHeader("User-Agent");
        entity.setClientCode(clientCode);
        if (!hasInit) {
            INTRANET_IP = GcInterfaceLogUtils.getIntranetIp();
            INTERNET_IP = GcInterfaceLogUtils.getInternetIp();
            hasInit = true;
        }
        if (StringUtils.isEmpty((String)INTERNET_IP)) {
            String serverIp = request.getLocalAddr();
            entity.setServerIp(serverIp);
        } else {
            entity.setServerIp(INTERNET_IP);
        }
        String clientIp = GcInterfaceLogUtils.getClientIpAddress(request);
        entity.setClientIp(clientIp);
    }

    private static ServletRequestAttributes currentRequestAttributes() {
        RequestAttributes requestAttr = RequestContextHolder.getRequestAttributes();
        if (requestAttr != null && !(requestAttr instanceof ServletRequestAttributes)) {
            throw new IllegalStateException("Current request is not a servlet request");
        }
        return (ServletRequestAttributes)requestAttr;
    }

    private static String getClientIpAddress(HttpServletRequest request) {
        String requestHeader = request.getHeader(IP_HEADER_FORWARDED_FOR);
        if (StringUtils.isNotEmpty((String)requestHeader) && !"unknown".equalsIgnoreCase(requestHeader)) {
            int index = requestHeader.indexOf(",");
            if (index != -1) {
                return requestHeader.substring(0, index);
            }
            return requestHeader;
        }
        requestHeader = request.getHeader(IP_HEADER_REMOTE_ADDR);
        if (StringUtils.isNotEmpty((String)requestHeader) && !"unknown".equalsIgnoreCase(requestHeader)) {
            return requestHeader;
        }
        return request.getRemoteAddr();
    }

    private static String getIntranetIp() {
        try {
            return DistributionManager.getInstance().getIp();
        }
        catch (Exception e) {
            return "";
        }
    }

    private static String getInternetIp() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            while (networks.hasMoreElements()) {
                Enumeration<InetAddress> addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress ip = addrs.nextElement();
                    if (ip == null || !(ip instanceof Inet4Address) || !ip.isSiteLocalAddress() || ip.getHostAddress().equals(INTRANET_IP)) continue;
                    return ip.getHostAddress();
                }
            }
            return INTRANET_IP;
        }
        catch (Exception e) {
            return "";
        }
    }
}


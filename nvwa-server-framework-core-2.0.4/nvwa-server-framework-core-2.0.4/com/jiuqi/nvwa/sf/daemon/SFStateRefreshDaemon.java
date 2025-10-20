/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.LicenceException
 *  com.jiuqi.bi.authz.licence.LicenceInfo
 *  com.jiuqi.bi.authz.licence.LicenceManager
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeState
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.springframework.mail.MailSender
 *  org.springframework.mail.SimpleMailMessage
 */
package com.jiuqi.nvwa.sf.daemon;

import com.jiuqi.bi.authz.LicenceException;
import com.jiuqi.bi.authz.licence.LicenceInfo;
import com.jiuqi.bi.authz.licence.LicenceManager;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeState;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.Framework;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SFStateRefreshDaemon
extends Thread {
    public static final int STANDARD_DAEMON_MODE = 0;
    private static final int STANDARD_DAEMON_MODE_SLEEP_SECONDS = 60;
    public static final int LAUNCHING_DAEMON_MODE = 1;
    private static final int LAUNCHING_DAEMON_MODE_SLEEP_SECONDS = 15;
    private int daemonMode = 0;
    private static final long CHECK_PERIOD_MILS = 21600000L;
    private static long latestCheckTime = 0L;
    private static final long THIRTY_DAYS_MILS = 2592000000L;
    private static String MSG_WARN_DATE = "";
    private static final String TODAY_FORMAT = "yyyy-MM-dd";
    private static final String TODAY_HMS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final Logger logger = LoggerFactory.getLogger(SFStateRefreshDaemon.class);
    private final Framework framework = Framework.getInstance();
    private static final ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public void setDaemonMode(int daemonMode) {
        this.daemonMode = daemonMode;
        this.interrupt();
    }

    @Override
    public void run() {
        while (true) {
            this.doSleep();
            SFStateRefreshDaemon.reloadModulesAndLicence();
            boolean moduleValidate = this.framework.isModuleValidate();
            boolean licenceValidate = this.framework.isLicenceValidate();
            if (moduleValidate && licenceValidate) {
                if (ServiceNodeStateHolder.getState() == ServiceNodeState.LICENCE_UNHANDLED || ServiceNodeStateHolder.getState() == ServiceNodeState.MODULE_UNHANDLED) {
                    try {
                        ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.INIT_MODULES);
                        this.framework.tryInitModules();
                    }
                    catch (Exception e) {
                        logger.error("\u670d\u52a1\u91cd\u542f\u5931\u8d25\uff1a" + e.getMessage(), e);
                    }
                    continue;
                }
                if (ServiceNodeStateHolder.getState() != ServiceNodeState.STOP) continue;
                logger.info("\u670d\u52a1\u505c\u6b62\uff0cSF\u670d\u52a1\u72b6\u6001\u5237\u65b0\u7ebf\u7a0b\u9000\u51fa");
                return;
            }
            this.unNormalProcess(moduleValidate, licenceValidate);
        }
    }

    public static synchronized void reloadModulesAndLicence() {
        Framework framework = Framework.getInstance();
        try (Connection connection = framework.getConnectionProvider().getConnection();){
            LicenceInfo licenceInfo;
            framework.checkModulesVersion(connection, false);
            framework.checkLicence(connection, false);
            LicenceManager licenceManager = framework.getLicenceManager();
            try {
                licenceInfo = licenceManager.getProductLicence(framework.getProductId());
            }
            catch (LicenceException e) {
                if (connection != null) {
                    if (var2_3 != null) {
                        try {
                            connection.close();
                        }
                        catch (Throwable throwable) {
                            var2_3.addSuppressed(throwable);
                        }
                    } else {
                        connection.close();
                    }
                }
                return;
            }
            long now = System.currentTimeMillis();
            long offset = now - latestCheckTime;
            if (offset < 21600000L) {
                return;
            }
            latestCheckTime = now;
            try {
                licenceManager.validateExpiry(framework.getProductId());
            }
            catch (LicenceException e) {
                String message = e.getMessage();
                logger.error(message);
                String userMsg = "\u7cfb\u7edf\u6388\u6743\u5df2\u8fc7\u671f\uff0c\u8bf7\u7ba1\u7406\u5458\u53ca\u65f6\u66f4\u6362\u6388\u6743\u3002";
                framework.setLicenceSystemState(true, userMsg);
                SFStateRefreshDaemon.doSendMsgAction(userMsg);
                if (connection != null) {
                    if (var2_3 != null) {
                        try {
                            connection.close();
                        }
                        catch (Throwable throwable) {
                            var2_3.addSuppressed(throwable);
                        }
                    } else {
                        connection.close();
                    }
                }
                return;
            }
            long useTime = licenceInfo.getUseTime();
            SimpleDateFormat df = new SimpleDateFormat(TODAY_HMS_FORMAT);
            if (useTime - now < 2592000000L) {
                String useTimeStr = df.format(new Date(licenceInfo.getUseTime()));
                String message = "\u7cfb\u7edf\u5c06\u4e8e\u3010" + useTimeStr + "\u3011\u8fc7\u671f\uff0c\u8bf7\u7ba1\u7406\u5458\u53ca\u65f6\u66f4\u6362\u6388\u6743\u3002";
                SFStateRefreshDaemon.sendMsg(message);
            } else {
                framework.setLicenceSystemState(false, "\u7cfb\u7edf\u6388\u6743\u5df2\u6062\u590d");
            }
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u6a21\u5757\u6821\u9a8c\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    private void unNormalProcess(boolean moduleValidate, boolean licenceValidate) {
        if (ServiceNodeStateHolder.getState() == ServiceNodeState.RUNNING) {
            if (moduleValidate) {
                logger.error("\u6388\u6743\u6587\u4ef6\u6821\u9a8c\u5f02\u5e38\uff0c\u8bf7\u91cd\u542f\u670d\u52a1\u67e5\u770b\u8be6\u60c5");
                ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.LICENCE_UNHANDLED);
            } else if (licenceValidate) {
                logger.error("\u6a21\u5757\u6821\u9a8c\u5f02\u5e38\uff0c\u8bf7\u91cd\u542f\u670d\u52a1\u67e5\u770b\u8be6\u60c5");
                ServiceNodeStateHolder.setState((ServiceNodeState)ServiceNodeState.MODULE_UNHANDLED);
            }
        }
    }

    private static void sendMsg(String userMsg) {
        if (SFStateRefreshDaemon.thisWeekAlreadyNoticed()) {
            return;
        }
        MSG_WARN_DATE = SFStateRefreshDaemon.getTodayStr();
        logger.info("\u8bb0\u5f55\u7cfb\u7edf\u901a\u77e5\u52a8\u4f5c\u65f6\u95f4\u4e3a\uff1a{}", (Object)MSG_WARN_DATE);
        SFStateRefreshDaemon.doSendMsgAction(userMsg);
        SFStateRefreshDaemon.doSendMailAction(userMsg);
    }

    private static void doSendMsgAction(String userMsg) {
        logger.warn("\u3010\u7cfb\u7edf\u6388\u6743\u901a\u77e5\u3011" + userMsg);
    }

    private static void doSendMailAction(String userMsg) {
        try {
            tpe.execute(new Thread(() -> {
                try {
                    logger.debug("\u53d1\u9001\u90ae\u4ef6\u903b\u8f91\u5f00\u59cb");
                    String adminMail = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("jiuqi.np.user.system[0].email");
                    if (null == adminMail || adminMail.isEmpty()) {
                        logger.info("\u7ba1\u7406\u5458\u90ae\u7bb1\u672a\u586b\uff0c\u65e0\u6cd5\u5411\u7ba1\u7406\u5458\u53d1\u9001\u90ae\u4ef6\u901a\u77e5");
                        return;
                    }
                    if (!SFStateRefreshDaemon.mailFormatIsValidate(adminMail) || !SFStateRefreshDaemon.mailAddressIsValidate(adminMail)) {
                        logger.info("\u7ba1\u7406\u5458\u90ae\u7bb1\u5730\u5740\u672a\u901a\u8fc7\u90ae\u7bb1\u683c\u5f0f\u6821\u9a8c\uff0c\u65e0\u6cd5\u5411\u7ba1\u7406\u5458\u53d1\u9001\u90ae\u4ef6\u901a\u77e5");
                        return;
                    }
                    String mailUsername = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("spring.mail.username");
                    if (null == mailUsername || mailUsername.isEmpty()) {
                        logger.info("\u90ae\u7bb1\u7528\u6237\u540d\u4fe1\u606f\u672a\u914d\u7f6e\uff0c\u65e0\u6cd5\u5411\u7ba1\u7406\u5458\u53d1\u9001\u90ae\u4ef6\u901a\u77e5");
                        return;
                    }
                    MailSender sender = SpringBeanUtils.getApplicationContext().getBean(MailSender.class);
                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                    simpleMailMessage.setFrom(mailUsername);
                    simpleMailMessage.setTo(adminMail);
                    simpleMailMessage.setSubject("\u7cfb\u7edf\u6388\u6743\u901a\u77e5");
                    StringBuffer sb = new StringBuffer();
                    sb.append("\u4ea7\u54c1Id\uff1a").append(Framework.getInstance().getProductId()).append("\r\n");
                    String applicationName = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("spring.application.name");
                    if (null != applicationName && !applicationName.isEmpty()) {
                        sb.append("\u670d\u52a1\u540d\uff1a").append(applicationName).append("\r\n");
                    }
                    sb.append(userMsg);
                    simpleMailMessage.setText(sb.toString());
                    simpleMailMessage.setSentDate(new Date());
                    sender.send(simpleMailMessage);
                    logger.info("\u5df2\u5411\u7ba1\u7406\u5458\u90ae\u7bb1\u3010" + adminMail + "\u3011\u53d1\u9001\u7cfb\u7edf\u6388\u6743\u4e34\u671f\u901a\u77e5");
                }
                catch (Exception e) {
                    logger.error("\u5411\u7ba1\u7406\u5458\u53d1\u9001\u90ae\u4ef6\u901a\u77e5\u5931\u8d25\uff0c\u8be5\u5f02\u5e38\u4e0d\u5f71\u54cd\u7cfb\u7edf\u8fd0\u884c\u3002\u539f\u56e0\u8bf7\u68c0\u67e5\u914d\u7f6e\u6587\u4ef6\u4e2d\u7684SMTP\u670d\u52a1\u5668\u5730\u5740\uff08\u5982\u5728\u5185\u7f51\u73af\u5883\uff0c\u8bf7\u4f7f\u7528\u5185\u7f51\u7684SMTP\uff09\u3001\u90ae\u7bb1\u8d26\u53f7\u3001\u90ae\u7bb1\u6388\u6743\u7801\u4ee5\u53ca\u7f51\u7edc\u662f\u5426\u901a\u7545\u7b49\u7b49", e);
                }
                finally {
                    logger.debug("\u53d1\u9001\u90ae\u4ef6\u903b\u8f91\u7ed3\u675f");
                }
            }));
        }
        catch (Exception e) {
            logger.error("\u7ebf\u7a0b\u6267\u884c\u65f6\u53d1\u73b0\u5176\u4ed6\u5f02\u5e38", e);
        }
    }

    private static boolean mailFormatIsValidate(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }
        catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    private static boolean mailAddressIsValidate(String email) {
        boolean flag = true;
        if (email.endsWith("xx")) {
            return false;
        }
        return flag;
    }

    private static boolean thisWeekAlreadyNoticed() {
        try {
            if ("".equals(MSG_WARN_DATE) || null == MSG_WARN_DATE) {
                return false;
            }
            Date weekStart = SFStateRefreshDaemon.getWeekStart();
            Date weekEnd = SFStateRefreshDaemon.getWeekEnd();
            SimpleDateFormat sdf = new SimpleDateFormat(TODAY_FORMAT);
            Date msgWarnDate = sdf.parse(MSG_WARN_DATE);
            long msgWarnDateTime = msgWarnDate.getTime();
            long weekStartTime = weekStart.getTime();
            long weekEndTime = weekEnd.getTime();
            return msgWarnDateTime >= weekStartTime && weekEndTime >= msgWarnDateTime;
        }
        catch (Exception e) {
            logger.error("\u5224\u65ad\u672c\u5468\u662f\u5426\u53d1\u9001\u8fc7\u901a\u77e5\u7684\u903b\u8f91\u53d1\u751f\u5f02\u5e38\uff0c\u5f3a\u5236\u51fd\u6570\u8fd4\u56defalse", e);
            return false;
        }
    }

    public static Date getWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(4, 0);
        cal.set(7, 2);
        cal.set(11, 0);
        cal.set(13, 0);
        cal.set(12, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(7, cal.getActualMaximum(7));
        cal.add(7, 1);
        cal.set(11, 23);
        cal.set(13, 59);
        cal.set(12, 59);
        return cal.getTime();
    }

    private static boolean isNotToday() {
        return !MSG_WARN_DATE.equalsIgnoreCase(SFStateRefreshDaemon.getTodayStr());
    }

    private static String getTodayStr() {
        SimpleDateFormat df = new SimpleDateFormat(TODAY_FORMAT);
        return df.format(new Date());
    }

    private void doSleep() {
        try {
            if (this.daemonMode == 0) {
                TimeUnit.SECONDS.sleep(60L);
            } else if (this.daemonMode == 1) {
                TimeUnit.SECONDS.sleep(15L);
            }
        }
        catch (InterruptedException e) {
            logger.info("\u670d\u52a1\u72b6\u6001\u5237\u65b0\u7ebf\u7a0b\u6b63\u5728\u8fd0\u884c");
        }
    }
}


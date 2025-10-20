/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.join.api.common;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.join.api.config.JoinApiConfig;
import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.JoinSubDeclare;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinCacheUtil {
    private static final Logger logger = LoggerFactory.getLogger(JoinCacheUtil.class);
    private static Map<String, JoinDeclare> declareCache = new ConcurrentHashMap<String, JoinDeclare>();
    private static Map<String, JoinListener> listenerCache = new ConcurrentHashMap<String, JoinListener>();
    private static final String NAME_CHECK_FAIL = "\u8fde\u63a5\u70b9\u6807\u8bc6\u4e0d\u5408\u7b26\u89c4\u8303\uff08\u5927\u5199\u5b57\u6bcd\u5f00\u5934\uff0c\u4ec5\u53ef\u5305\u542b\u6570\u5b57\u3001\u5927\u5199\u5b57\u6bcd\u3001\u4e0b\u5212\u7ebf\uff0c\u957f\u5ea6\u4e0d\u8d8548\uff09\uff1a";
    private static final String DECLARE_REPEAT = "\u91cd\u590d\u7684\u8fde\u63a5\u70b9\u6ce8\u518c\uff1a";
    private static final String LISTENER_REPEAT = "\u91cd\u590d\u7684\u76d1\u542c\u6ce8\u518c\uff1a";
    private static final String JOIN_NOT_EXIST = "\u8fde\u63a5\u70b9\u4e0d\u5b58\u5728\uff1a";

    public static R addDeclare(JoinDeclare declare) {
        String joinName = declare.getName();
        String errorMsg = null;
        if (!(declare instanceof JoinSubDeclare)) {
            if (joinName.length() > 48) {
                logger.warn("\u8b66\u544a\uff1a\u8fde\u63a5\u70b9\u6807\u8bc6\u957f\u5ea6\u8d85\u51fa48\uff0c\u987b\u8c03\u6574\uff1a" + declare.getClass().getName());
                logger.warn("\u8b66\u544a\uff1a\u8fde\u63a5\u70b9\u6807\u8bc6\u957f\u5ea6\u8d85\u51fa48\uff0c\u987b\u8c03\u6574\uff1a" + joinName);
            }
            if (!JoinCacheUtil.validator(joinName)) {
                if (JoinApiConfig.isCheckJoinName()) {
                    errorMsg = NAME_CHECK_FAIL + joinName;
                } else {
                    logger.warn(NAME_CHECK_FAIL + declare.getClass().getName());
                    logger.warn(NAME_CHECK_FAIL + joinName);
                }
            }
        }
        if (errorMsg == null && declareCache.putIfAbsent(joinName, declare) != null) {
            errorMsg = DECLARE_REPEAT + joinName;
        }
        if (errorMsg != null) {
            logger.error(errorMsg);
            return R.error(errorMsg);
        }
        return R.ok();
    }

    public static R addListener(JoinListener joinListener) {
        String joinName = joinListener.getJoinName();
        String errorMsg = null;
        JoinDeclare delcare = JoinCacheUtil.getDelcare(joinName);
        if (delcare == null) {
            errorMsg = JOIN_NOT_EXIST + joinName;
        } else if (!JoinCacheUtil.validator(joinName)) {
            errorMsg = NAME_CHECK_FAIL + joinName;
        } else if (listenerCache.putIfAbsent(joinName, joinListener) != null) {
            errorMsg = LISTENER_REPEAT + joinName;
        }
        if (errorMsg != null) {
            logger.error(errorMsg);
            return R.error((String)errorMsg);
        }
        return R.ok();
    }

    public static JoinDeclare getDelcare(String joinName) {
        return declareCache.get(joinName);
    }

    public static JoinListener getListener(String joinName) {
        return listenerCache.get(joinName);
    }

    public static void removeDeclare(String joinName) {
        declareCache.remove(joinName);
    }

    public static void removeListener(String joinName) {
        listenerCache.remove(joinName);
    }

    public static boolean validator(String joinName) {
        if (joinName == null) {
            return false;
        }
        return joinName.matches("^[A-Z0-9_]{3,60}$");
    }

    public static JoinDeclare exist(String joinName) {
        JoinDeclare delcare = JoinCacheUtil.getDelcare(joinName);
        if (delcare == null) {
            throw new RuntimeException(JOIN_NOT_EXIST + joinName);
        }
        return delcare;
    }
}


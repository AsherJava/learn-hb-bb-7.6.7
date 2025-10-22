/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.offsetitem.utils;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class CalcLogUtil {
    private static CalcLogUtil calcLogUtil;

    public static CalcLogUtil getInstance() {
        if (null == calcLogUtil) {
            calcLogUtil = new CalcLogUtil();
        }
        return calcLogUtil;
    }

    public void log(Class<?> clazz, String operateInfo, String taskId, Integer acctYear, Integer acctPeriod, String ruleId) {
        Logger logger = LoggerFactory.getLogger(clazz);
        ContextUser user = NpContextHolder.getContext().getUser();
        String userTitle = "";
        if (null != user) {
            userTitle = StringUtils.isEmpty(user.getFullname()) ? user.getName() : user.getFullname() + "(" + user.getName() + ")";
        }
        logger.info("{}:\u7528\u6237{}\uff0ctaskId-{}\uff0cacctYear-{},acctPeriod-{},ruleId-{}", operateInfo, userTitle, taskId, acctYear, acctPeriod, ruleId);
    }

    public void log(Class<?> clazz, String operateInfo, Object queryParamsVO) {
        Logger logger = LoggerFactory.getLogger(clazz);
        ContextUser user = NpContextHolder.getContext().getUser();
        String userTitle = "";
        if (null != user) {
            userTitle = StringUtils.isEmpty(user.getFullname()) ? user.getName() : user.getFullname() + "(" + user.getName() + ")";
        }
        logger.info("{}:\u7528\u6237{}\uff0c\u53c2\u6570\uff1a-{}", operateInfo, userTitle, JsonUtils.writeValueAsString((Object)queryParamsVO));
    }

    public void log(Class<?> clazz, String operateInfo, Object ... queryParamsVO) {
        Logger logger = LoggerFactory.getLogger(clazz);
        ContextUser user = NpContextHolder.getContext().getUser();
        String userTitle = "";
        if (null != user) {
            userTitle = StringUtils.isEmpty(user.getFullname()) ? user.getName() : user.getFullname() + "(" + user.getName() + ")";
        }
        logger.info("{}:\u7528\u6237{}\uff0c\u53c2\u6570\uff1a-{}", operateInfo, userTitle, JsonUtils.writeValueAsString((Object)queryParamsVO));
    }
}


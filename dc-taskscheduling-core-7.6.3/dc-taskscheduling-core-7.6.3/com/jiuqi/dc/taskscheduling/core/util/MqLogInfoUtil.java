/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.LogUtil
 */
package com.jiuqi.dc.taskscheduling.core.util;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.LogUtil;
import com.jiuqi.dc.taskscheduling.core.domain.MqErrorInfoDO;
import java.util.Date;

public class MqLogInfoUtil {
    public static MqErrorInfoDO createMqErrorInfo(String msgStr, String msgId, Throwable e) {
        return MqLogInfoUtil.createMqErrorInfo(msgStr, msgId, LogUtil.getExceptionStackStr((Throwable)e));
    }

    public static MqErrorInfoDO createMqErrorInfo(String msgStr, String msgId, String errorInfo) {
        MqErrorInfoDO mqErrorInfoEO = new MqErrorInfoDO();
        mqErrorInfoEO.setId(UUIDUtils.newHalfGUIDStr());
        mqErrorInfoEO.setVer(0L);
        mqErrorInfoEO.setMsgId(msgId);
        mqErrorInfoEO.setLogTime(new Date());
        mqErrorInfoEO.setMessage(new String(msgStr));
        mqErrorInfoEO.setErrorinfo(errorInfo);
        return mqErrorInfoEO;
    }
}


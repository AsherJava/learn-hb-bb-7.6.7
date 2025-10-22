/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.nvwa.systemoption.service.impl.NvwaSystemOptionServiceImpl
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONArray
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg;

import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.nvwa.systemoption.service.impl.NvwaSystemOptionServiceImpl;
import com.jiuqi.util.StringUtils;
import org.json.JSONArray;

public final class SendMessageTaskConfig {
    public static final String BATCH_UPLOAD_CAN_SEND_MSG = "BATCH_UPLOAD_CAN_SEND_MSG";

    private SendMessageTaskConfig() {
    }

    public static boolean canSendMessage() {
        NvwaSystemOptionServiceImpl sysOption = (NvwaSystemOptionServiceImpl)BeanUtil.getBean(NvwaSystemOptionServiceImpl.class);
        String value = sysOption.get("nr-flow-todo-id", "PROCESS_UPLOAD_CAN_SEND_MSG");
        if (StringUtils.isNotEmpty((String)value)) {
            JSONArray array = new JSONArray(value);
            if (array != null && array.length() > 0) {
                for (int i = 0; i < array.length(); ++i) {
                    Object object = array.get(i);
                    if (!object.toString().equals("0")) continue;
                    return true;
                }
            }
        } else {
            String hisValue = sysOption.get("nr-data-entry-group", BATCH_UPLOAD_CAN_SEND_MSG);
            return !"0".equals(hisValue);
        }
        return false;
    }

    public static boolean canSendReturnMessage() {
        INvwaSystemOptionService sysOption = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        String value = sysOption.get("nr-flow-todo-id", "PROCESS_UPLOAD_CAN_SEND_MSG");
        if (StringUtils.isNotEmpty((String)value)) {
            JSONArray array = new JSONArray(value);
            if (array != null && array.length() > 0) {
                for (int i = 0; i < array.length(); ++i) {
                    Object object = array.get(i);
                    if (!object.toString().equals("1")) continue;
                    return true;
                }
            }
        } else {
            String hisValue = sysOption.get("nr-data-entry-group", BATCH_UPLOAD_CAN_SEND_MSG);
            return !"0".equals(hisValue);
        }
        return false;
    }
}


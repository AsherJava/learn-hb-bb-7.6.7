/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONArray
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo;

import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoBeanUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.StringUtils;
import org.json.JSONArray;

public class TodoSystemOption {
    private static final String OPTION_GROUP_ID = "nr-flow-todo-id";
    private static final String OPTION_ID = "PROCESS_UPLOAD_CAN_SEND_MSG";
    private static final String OPTION_GROUP_ID_OLD = "nr-data-entry-group";
    private static final String OPTION_ID_OLD = "BATCH_UPLOAD_CAN_SEND_MSG";

    public static boolean isEnableTodo() {
        INvwaSystemOptionService systemOptionService = TodoBeanUtils.INSTANCE.getSystemOptionService();
        String value = systemOptionService.get(OPTION_GROUP_ID, OPTION_ID);
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
            String hisValue = systemOptionService.get(OPTION_GROUP_ID_OLD, OPTION_ID_OLD);
            return !"0".equals(hisValue);
        }
        return false;
    }
}


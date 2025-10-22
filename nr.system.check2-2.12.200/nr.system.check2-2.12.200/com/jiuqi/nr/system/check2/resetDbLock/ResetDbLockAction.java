/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.resetDbLock;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ResetDbLockAction
implements ICheckAction {
    public static final String RESET_DB_LUCK_APP_NAME = "resetDbLock";
    public static final String RESET_DB_LUCK_TITLE = "\u4fee\u590d\u53c2\u6570\u8bfb\u5199\u9501";

    public String getCheckResourceKey() {
        return "resource-reset-db-lock";
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.EVENT;
    }

    public String getConfirmMessage() {
        return "\u786e\u8ba4\u6267\u884c\u68c0\u67e5\u5e76\u4fee\u590d\u672a\u80fd\u6b63\u5e38\u91ca\u653e\u7684\u53c2\u6570\u8bfb\u5199\u9501\uff1f";
    }

    public Map<String, Object> option() throws Exception {
        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
        objectObjectHashMap.put(RESET_DB_LUCK_TITLE, "\u4fee\u590d\u53c2\u6570\u8bfb\u5199\u9501\u6210\u529f");
        return objectObjectHashMap;
    }

    public PopFrameVO getPageMessage() throws Exception {
        PopFrameVO popFrameVO = new PopFrameVO();
        popFrameVO.setAppName(RESET_DB_LUCK_APP_NAME);
        popFrameVO.setTitle(RESET_DB_LUCK_TITLE);
        popFrameVO.setEntry("refreshCkd");
        return popFrameVO;
    }
}


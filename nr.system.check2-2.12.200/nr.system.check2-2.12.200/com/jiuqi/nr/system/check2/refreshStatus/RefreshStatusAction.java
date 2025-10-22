/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.refreshStatus;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class RefreshStatusAction
implements ICheckAction {
    public static final String REFRESH_STATUS_APP_NAME = "refreshStatus";
    public static final String REFRESH_STATUS_TITLE = "\u5237\u65b0\u72b6\u6001";

    public String getCheckResourceKey() {
        return "resource-refresh-status";
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.MODAL;
    }

    public String getConfirmMessage() {
        return null;
    }

    public Map<String, Object> option() throws Exception {
        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
        objectObjectHashMap.put("\u6e05\u9664\u6570\u636e", "\u6e05\u9664\u6570\u636e\u6210\u529f");
        return objectObjectHashMap;
    }

    public PopFrameVO getPageMessage() throws Exception {
        PopFrameVO popFrameVO = new PopFrameVO();
        popFrameVO.setAppName(REFRESH_STATUS_APP_NAME);
        popFrameVO.setTitle(REFRESH_STATUS_TITLE);
        popFrameVO.setEntry("refreshCkd");
        return popFrameVO;
    }
}


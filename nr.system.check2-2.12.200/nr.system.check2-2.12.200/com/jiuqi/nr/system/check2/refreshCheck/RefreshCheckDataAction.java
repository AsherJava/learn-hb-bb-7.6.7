/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.refreshCheck;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class RefreshCheckDataAction
implements ICheckAction {
    public static final String REFRESH_CHECK_DATA_APP_NAME = "refreshErrorState";
    public static final String REFRESH_CHECK_DATA_TITLE = "\u5237\u65b0\u51fa\u9519\u8bf4\u660e";

    public String getCheckResourceKey() {
        return "resource-0000-0000-refresh-check-data";
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.MODAL;
    }

    public String getConfirmMessage() {
        return null;
    }

    public Map<String, Object> option() throws Exception {
        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
        objectObjectHashMap.put("\u66f4\u65b0\u51fa\u9519\u8bf4\u660e", "\u66f4\u65b0\u51fa\u9519\u8bf4\u660e\u6210\u529f");
        return objectObjectHashMap;
    }

    public PopFrameVO getPageMessage() throws Exception {
        PopFrameVO popFrameVO = new PopFrameVO();
        popFrameVO.setAppName(REFRESH_CHECK_DATA_APP_NAME);
        popFrameVO.setTitle(REFRESH_CHECK_DATA_TITLE);
        popFrameVO.setEntry("refreshCkd");
        return popFrameVO;
    }
}


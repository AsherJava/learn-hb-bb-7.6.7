/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.clearDataByDataScheme;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ClearDataByDataSchemeAction
implements ICheckAction {
    public static final String CLEAR_DATA_BY_DATA_SCHEME_APP_NAME = "cleanDataByDs";
    public static final String CLEAR_DATA_BY_DATA_SCHEME_TITLE = "\u6e05\u9664\u6570\u636e(\u6309\u6570\u636e\u65b9\u6848)";

    public String getCheckResourceKey() {
        return "resource-clear-data-by-data-scheme";
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
        popFrameVO.setAppName(CLEAR_DATA_BY_DATA_SCHEME_APP_NAME);
        popFrameVO.setTitle(CLEAR_DATA_BY_DATA_SCHEME_TITLE);
        popFrameVO.setEntry("refreshCkd");
        return popFrameVO;
    }
}


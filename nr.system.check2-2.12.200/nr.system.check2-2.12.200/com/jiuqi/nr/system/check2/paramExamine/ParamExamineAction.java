/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.paramExamine;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ParamExamineAction
implements ICheckAction {
    public static final String PARAM_EXAMINE_APP_NAME = "parameter-check";
    public static final String PARAM_EXAMINE_TITLE = "\u53c2\u6570\u68c0\u67e5";

    public String getCheckResourceKey() {
        return "resource-0000-0000-param-examine";
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.PAGE;
    }

    public String getConfirmMessage() {
        return null;
    }

    public Map<String, Object> option() throws Exception {
        return null;
    }

    public PopFrameVO getPageMessage() throws Exception {
        PopFrameVO popFrameVO = new PopFrameVO();
        popFrameVO.setAppName(PARAM_EXAMINE_APP_NAME);
        popFrameVO.setTitle(PARAM_EXAMINE_TITLE);
        popFrameVO.setEntry("");
        return popFrameVO;
    }
}


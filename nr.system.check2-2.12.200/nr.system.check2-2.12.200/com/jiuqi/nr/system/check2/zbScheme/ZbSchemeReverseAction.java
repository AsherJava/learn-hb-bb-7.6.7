/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.zbScheme;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.system.check2.zbScheme.ZbSchemeReverseResource;
import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ZbSchemeReverseAction
implements ICheckAction {
    public String getCheckResourceKey() {
        return ZbSchemeReverseResource.RESOURCE_KEY;
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.MODAL;
    }

    public String getConfirmMessage() {
        return "\u6570\u636e\u65b9\u6848\u751f\u6210\u4f53\u7cfb\u540e\u4e0d\u80fd\u624b\u52a8\u521b\u5efa\u6307\u6807\uff0c\u4e5f\u4e0d\u80fd\u5220\u9664\u6307\u6807\u4f53\u7cfb";
    }

    public Map<String, Object> option() throws Exception {
        return Collections.emptyMap();
    }

    public PopFrameVO getPageMessage() throws Exception {
        PopFrameVO popFrameVO = new PopFrameVO();
        popFrameVO.setAppName("zbScheme");
        popFrameVO.setTitle("\u6307\u6807\u4f53\u7cfb");
        return popFrameVO;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckResource
 */
package com.jiuqi.nr.sbdata.carry.bean;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TzCarryDownResource
implements ICheckResource {
    public static final String resourceKey = "tz_data_carry_down";
    public static final String appName = "sb-data-copy-plugin";

    public String getKey() {
        return resourceKey;
    }

    public String getTitle() {
        return "\u53f0\u8d26\u6570\u636e\u7ed3\u8f6c";
    }

    public String getGroupKey() {
        return "group-0000-data-management-tool";
    }

    public Double getOrder() {
        return 0.1;
    }

    public String getIcon() {
        return "";
    }

    public String getMessage() {
        return "\u5c06\u53f0\u8d26\u6708\u62a5\u4efb\u52a1\u6216\u5b63\u62a5\u4efb\u52a1\u6570\u636e\u7ed3\u8f6c\u5230\u53f0\u8d26\u5e74\u62a5\u4efb\u52a1";
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add("\u53f0\u8d26\u8868");
        tagMessages.add("\u53f0\u8d26\u6570\u636e\u7ed3\u8f6c");
        tagMessages.add("\u6570\u636e\u7ba1\u7406\u5de5\u5177");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        return true;
    }

    public ICheckAction getCheckOption() {
        return new ICheckAction(){

            public String getCheckResourceKey() {
                return TzCarryDownResource.resourceKey;
            }

            public CheckOptionType getOptionType() {
                return CheckOptionType.MODAL;
            }

            public String getConfirmMessage() {
                return "";
            }

            public Map<String, Object> option() throws Exception {
                return Collections.emptyMap();
            }

            public PopFrameVO getPageMessage() throws Exception {
                PopFrameVO popFrameVO = new PopFrameVO();
                popFrameVO.setAppName(TzCarryDownResource.appName);
                popFrameVO.setTitle("\u53f0\u8d26\u6570\u636e\u7ed3\u8f6c");
                popFrameVO.setVueVersion(2);
                return popFrameVO;
            }
        };
    }
}


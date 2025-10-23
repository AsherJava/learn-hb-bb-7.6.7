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
public class TzDataClearResource
implements ICheckResource {
    public static final String resourceKey = "tz_data_clear";
    public static final String appName = "sb-data-carry-plugin";

    public String getKey() {
        return resourceKey;
    }

    public String getTitle() {
        return "\u53f0\u8d26\u6570\u636e\u6e05\u9664";
    }

    public String getGroupKey() {
        return "group-0000-data-management-tool";
    }

    public Double getOrder() {
        return 0.0;
    }

    public String getIcon() {
        return "";
    }

    public String getMessage() {
        return "\u6309\u6240\u9009\u8303\u56f4\u6e05\u7a7a\u53f0\u8d26\u8868\u6570\u636e\uff0c\u5e76\u5c06\u6570\u636e\u8ba1\u5165\u5386\u53f2\u8868";
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add("\u53f0\u8d26\u8868");
        tagMessages.add("\u53f0\u8d26\u6570\u636e\u6e05\u9664");
        tagMessages.add("\u6570\u636e\u7ba1\u7406\u5de5\u5177");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        return true;
    }

    public ICheckAction getCheckOption() {
        return new ICheckAction(){

            public String getCheckResourceKey() {
                return TzDataClearResource.resourceKey;
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
                popFrameVO.setAppName(TzDataClearResource.appName);
                popFrameVO.setTitle("\u53f0\u8d26\u6570\u636e\u6e05\u9664");
                popFrameVO.setVueVersion(2);
                return popFrameVO;
            }
        };
    }
}


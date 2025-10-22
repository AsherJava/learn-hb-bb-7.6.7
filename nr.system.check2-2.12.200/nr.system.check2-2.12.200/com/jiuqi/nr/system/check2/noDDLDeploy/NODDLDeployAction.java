/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.noDDLDeploy;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NODDLDeployAction
implements ICheckAction {
    public static final String NO_DDL_DEPLOY_APP_NAME = "NoDDLDeploy";
    public static final String NO_DDL_DEPLOY_TITLE = "\u65e0DDL\u6743\u9650\u53d1\u5e03";

    public String getCheckResourceKey() {
        return "resource-no-ddl_deploy";
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.MODAL;
    }

    public String getConfirmMessage() {
        return null;
    }

    public Map<String, Object> option() throws Exception {
        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
        objectObjectHashMap.put(NO_DDL_DEPLOY_TITLE, "\u65e0DDL\u6743\u9650\u53d1\u5e03\u6210\u529f");
        return objectObjectHashMap;
    }

    public PopFrameVO getPageMessage() throws Exception {
        PopFrameVO popFrameVO = new PopFrameVO();
        popFrameVO.setAppName(NO_DDL_DEPLOY_APP_NAME);
        popFrameVO.setTitle(NO_DDL_DEPLOY_TITLE);
        popFrameVO.setEntry("refreshCkd");
        return popFrameVO;
    }
}


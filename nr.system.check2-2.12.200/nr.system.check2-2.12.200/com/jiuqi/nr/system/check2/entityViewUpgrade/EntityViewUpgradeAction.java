/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 *  com.jiuqi.nr.definition.paramcheck.EntityViewUpgradeService
 */
package com.jiuqi.nr.system.check2.entityViewUpgrade;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.definition.paramcheck.EntityViewUpgradeService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityViewUpgradeAction
implements ICheckAction {
    public static final String ENTITY_VIEW_UPGRADE_CONFIRM_MESSAGE = "\u786e\u8ba4\u5c06\u6240\u6709\u4efb\u52a1\u7684\u4e3b\u7ef4\u5ea6\u548c\u5355\u5143\u683c\u7684\u8fc7\u6ee4\u6761\u4ef6\u5347\u7ea7\u4e3a\u8fc7\u6ee4\u6a21\u677f\uff1f";
    @Autowired
    private EntityViewUpgradeService entityViewUpgradeService;

    public String getCheckResourceKey() {
        return "resource-0000-entity-view-upgrade";
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.EVENT;
    }

    public String getConfirmMessage() {
        return ENTITY_VIEW_UPGRADE_CONFIRM_MESSAGE;
    }

    public Map<String, Object> option() throws Exception {
        this.entityViewUpgradeService.upgrade();
        return null;
    }

    public PopFrameVO getPageMessage() throws Exception {
        return new PopFrameVO();
    }
}


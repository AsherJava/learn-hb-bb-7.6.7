/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.systemcheck.spi;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import java.util.Map;

public interface ICheckAction {
    public String getCheckResourceKey();

    public CheckOptionType getOptionType();

    public String getConfirmMessage();

    public Map<String, Object> option() throws Exception;

    public PopFrameVO getPageMessage() throws Exception;
}


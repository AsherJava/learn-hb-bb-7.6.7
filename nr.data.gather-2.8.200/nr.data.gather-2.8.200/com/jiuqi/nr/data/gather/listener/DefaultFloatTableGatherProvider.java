/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.gather.FloatTableGatherSetting
 */
package com.jiuqi.nr.data.gather.listener;

import com.jiuqi.nr.data.engine.gather.FloatTableGatherSetting;
import com.jiuqi.nr.data.gather.listener.FloatTableGatherProvider;
import java.util.List;
import org.springframework.stereotype.Component;

@Component(value="defaultFloatTableGatherProvider")
public class DefaultFloatTableGatherProvider
implements FloatTableGatherProvider {
    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public double getOrder() {
        return 10.0;
    }

    @Override
    public List<FloatTableGatherSetting> getFloatTableGatherParam() {
        return null;
    }
}


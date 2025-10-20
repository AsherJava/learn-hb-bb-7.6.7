/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.service.impl;

import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.common.service.LogModuleService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LogModuleServiceImpl
implements LogModuleService {
    @Override
    public Map<String, String> getAllLogModules() {
        LogModuleEnum[] values;
        HashMap<String, String> modules = new HashMap<String, String>();
        for (LogModuleEnum module : values = LogModuleEnum.values()) {
            modules.put(module.getCode(), module.getTitle());
        }
        return modules;
    }
}


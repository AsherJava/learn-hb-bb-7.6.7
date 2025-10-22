/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.common.tool.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.tool.web.DebugParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/server"})
public class DebugControl {
    @GetMapping(value={"enableDebug/{enabled}"})
    public BusinessResponseEntity<String> enableDebug(@PathVariable(value="enabled") Boolean enabled) {
        DebugParam.enableDebug = enabled;
        return BusinessResponseEntity.ok();
    }

    @GetMapping(value={"enableCopyInitOffset/{enabled}"})
    public BusinessResponseEntity<String> enableCopyInitOffset(@PathVariable(value="enabled") Boolean enabled) {
        DebugParam.enableDebug = true;
        DebugParam.enableCopyInitOffset = enabled;
        return BusinessResponseEntity.ok();
    }
}


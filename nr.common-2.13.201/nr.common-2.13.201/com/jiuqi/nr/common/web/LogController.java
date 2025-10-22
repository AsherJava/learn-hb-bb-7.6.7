/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.common.web;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.service.LogModuleService;
import io.swagger.annotations.Api;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/logs/module"})
@Api(tags={"\u65e5\u5fd7\u6a21\u5757\u5b9a\u4e49\u7ec4\u4ef6"})
public class LogController {
    @Autowired
    private LogModuleService logModuleService;

    @GetMapping(value={"/get-modules"})
    public Map<String, String> getAllModules() {
        return this.logModuleService.getAllLogModules();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.system.check.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.system.check.common.Util;
import com.jiuqi.nr.system.check.service.impl.TableParamFixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/system-check/table-fix"})
public class TableParamFixController {
    @Autowired
    private TableParamFixService service;

    @GetMapping(value={"/field/{key}"})
    public void fixField(@PathVariable String key) throws JQException {
        try {
            this.service.fixField(key);
        }
        catch (Exception e) {
            throw Util.getError("1000", e);
        }
    }

    @GetMapping(value={"/table/{key}"})
    public void fixTable(@PathVariable String key) throws JQException {
        try {
            this.service.fixTable(key);
        }
        catch (Exception e) {
            throw Util.getError("1000", e);
        }
    }
}


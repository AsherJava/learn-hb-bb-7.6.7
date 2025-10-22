/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.graph.rwlock.executer.DatabaseLock
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.examine.web;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseLock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/paramcheck/"})
@Api(tags={"\u91cd\u7f6e\u53c2\u6570\u8bfb\u5199\u9501"})
public class ResetDBLockController {
    @Autowired
    private DatabaseLock databaseLock;

    @ApiOperation(value="\u91cd\u7f6e\u53c2\u6570\u8bfb\u5199\u9501", httpMethod="GET")
    @GetMapping(value={"reset/db/lock/{lockId}"})
    public void resetLock(@PathVariable String lockId) {
        if (null == lockId || lockId.isEmpty()) {
            return;
        }
        this.databaseLock.reset(lockId);
    }

    @ApiOperation(value="\u6062\u590d\u53c2\u6570\u8bfb\u5199\u9501", httpMethod="GET")
    @GetMapping(value={"restore/db/lock"})
    public void restore() {
        this.databaseLock.deadlockReset(0L);
    }
}


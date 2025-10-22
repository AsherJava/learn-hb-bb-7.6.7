/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.np.asynctask.impl.web;

import com.jiuqi.np.asynctask.impl.service.AsyncTaskDispatcherThread;
import com.jiuqi.np.asynctask.impl.service.SimpleAsyncTaskDispatcher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u5f02\u6b65\u4efb\u52a1\u7ec4\u4ef6"})
public class NpAsyncTaskController {
    @Autowired
    SimpleAsyncTaskDispatcher dispatcher;
    @Autowired
    AsyncTaskDispatcherThread asyncTaskDispatcherThread;

    @GetMapping(value={"/asynctask/serverInfo"})
    @ApiOperation(value="serverInfo", tags={"\u83b7\u53d6\u670d\u52a1\u5668ServerId\u548cSystemName"})
    public String serverInfo() throws Exception {
        return this.dispatcher.getSERVE_ID() + ";" + this.dispatcher.getSYSTEM_NAME();
    }

    @GetMapping(value={"/asynctask/clearDispatcherThread"})
    @ApiOperation(value="clearDispThread", tags={"\u6e05\u7a7a\u4efb\u52a1\u5206\u53d1\u7f13\u5b58\u7684\u4e24\u4e2amap"})
    public void clearDispThread() {
        Logger logger = LoggerFactory.getLogger(NpAsyncTaskController.class);
        logger.info("========begin=========API====clearDispatcherThread");
        this.asyncTaskDispatcherThread.clearDispatcherThread();
        logger.info("========end=========API====clearDispatcherThread");
    }
}


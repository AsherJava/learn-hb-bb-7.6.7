/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon;

import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceManager;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.impl.SFRemoteResourceManage;
import com.jiuqi.nvwa.sf.operator.ModuleUpgradeLockOperator;
import com.jiuqi.va.feign.util.RequestContextUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@RequestMapping(value={"/anon/sf/api"})
public class AnonExecutePreModeController {
    Logger logger = LoggerFactory.getLogger(AnonExecutePreModeController.class);
    @Autowired
    private SFRemoteResourceManage remoteResourceManage;

    @PostMapping(value={"/module/preExecute"})
    public void preExecute(String serviceName) throws IOException {
        RequestContextUtil.setResponseContentType((String)"application/json; charset=UTF-8");
        RequestContextUtil.setResponseCharacterEncoding((String)"UTF-8");
        OutputStream outputStream = RequestContextUtil.getOutputStream();
        CountDownLatch latch = new CountDownLatch(1);
        IServiceManager serviceManagerResource = this.remoteResourceManage.getServiceManagerResource(serviceName);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            try {
                requestAttributes.getSessionId();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        new Thread(() -> {
            try {
                if (requestAttributes != null) {
                    RequestContextHolder.setRequestAttributes((RequestAttributes)requestAttributes);
                }
                serviceManagerResource.preExecute();
                latch.countDown();
            }
            catch (Exception e) {
                try {
                    outputStream.write(("\n <span style = 'color: red;'>\u6267\u884cSQL\u5931\u8d25: \t" + e.getMessage() + "</span>\n").getBytes(""));
                    outputStream.flush();
                    RequestContextUtil.setResponseStatus((int)500);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                latch.countDown();
            }
        }).start();
        this.printLogs(outputStream, latch, serviceManagerResource);
        outputStream.close();
    }

    @PostMapping(value={"/module/preExecuteForward"})
    public void preExecuteForward(String serviceName) throws IOException {
        RequestContextUtil.setResponseContentType((String)"application/json; charset=UTF-8");
        RequestContextUtil.setResponseCharacterEncoding((String)"UTF-8");
        OutputStream outputStream = RequestContextUtil.getOutputStream();
        CountDownLatch latch = new CountDownLatch(1);
        IServiceManager serviceManagerResource = this.remoteResourceManage.getServiceManagerResource(serviceName);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            try {
                requestAttributes.getSessionId();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        new Thread(() -> {
            try {
                if (requestAttributes != null) {
                    RequestContextHolder.setRequestAttributes((RequestAttributes)requestAttributes);
                }
                serviceManagerResource.preExecuteForward();
                latch.countDown();
            }
            catch (Exception e) {
                try {
                    outputStream.write(("\n <span style = 'color: red;'>\u6267\u884cSQL\u5931\u8d25: \t" + e.getMessage() + "</span>\n").getBytes(""));
                    outputStream.flush();
                    RequestContextUtil.setResponseStatus((int)500);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                latch.countDown();
            }
        }).start();
        this.printLogs(outputStream, latch, serviceManagerResource);
        outputStream.close();
    }

    private void printLogs(OutputStream writer, CountDownLatch latch, IServiceManager serviceManagerResource) {
        long lastTimestamp = 0L;
        boolean logEOF = false;
        while (true) {
            try {
                boolean await;
                do {
                    await = latch.await(1L, TimeUnit.SECONDS);
                    List<ModuleUpgradeLockOperator.UpgradeLogInfo> lockLogInfos = serviceManagerResource.executeLogs(lastTimestamp);
                    block10: for (ModuleUpgradeLockOperator.UpgradeLogInfo lockLogInfo : lockLogInfos) {
                        switch (lockLogInfo.getLogType()) {
                            case 0: {
                                writer.write((" -- " + lockLogInfo.getSql() + "\n").getBytes("utf-8"));
                                continue block10;
                            }
                            case 1: {
                                writer.write((lockLogInfo.getSql() + "\n").getBytes("utf-8"));
                                continue block10;
                            }
                            case 2: {
                                writer.write(("\n --  <span style = 'color: red;'>\u6267\u884cSQL\u5931\u8d25: \t" + lockLogInfo.getSql() + "</span>\n").getBytes("utf-8"));
                                continue block10;
                            }
                            case 3: {
                                writer.write(("\n -- <span style = 'color: red;font-weight: bold;'>\u6a21\u5757\u5347\u7ea7\u5931\u8d25\uff0c\u5347\u7ea7\u8fc7\u7a0b\u5c06\u88ab\u4e2d\u65ad: \n\t" + lockLogInfo.getSql() + "</span>\n\n").getBytes("utf-8"));
                                continue block10;
                            }
                        }
                        logEOF = true;
                    }
                    if (!lockLogInfos.isEmpty()) {
                        lastTimestamp = lockLogInfos.get(lockLogInfos.size() - 1).getTimestamp();
                    }
                    writer.flush();
                } while (!await && !logEOF);
                writer.close();
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
                continue;
            }
            break;
        }
    }
}


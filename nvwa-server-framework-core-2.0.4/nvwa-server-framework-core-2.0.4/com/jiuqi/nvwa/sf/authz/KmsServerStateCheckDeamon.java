/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.authz;

import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.authz.ClientSocketService;
import com.jiuqi.nvwa.sf.authz.KmsServerChecker;
import com.jiuqi.nvwa.sf.authz.KmsServerState;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KmsServerStateCheckDeamon
extends Thread {
    public static final Logger log = LoggerFactory.getLogger(KmsServerStateCheckDeamon.class);
    private int[] systemRetryIntervalSeconds = new int[]{30, 60, 120, 300};
    private boolean looperCloseFlag = false;
    private KmsServerChecker kmsServerChecker;
    private long systemPausedTimestamp = 0L;

    public KmsServerStateCheckDeamon(KmsServerChecker kmsServerChecker) {
        this.kmsServerChecker = kmsServerChecker;
    }

    public void setLooperCloseFlag(boolean looperCloseFlag) {
        this.looperCloseFlag = looperCloseFlag;
    }

    @Override
    public void run() {
        while (!this.looperCloseFlag) {
            try {
                this.switchServerIfNessary();
                TimeUnit.SECONDS.sleep(15L);
            }
            catch (Exception e) {
                log.error("\u7ebf\u7a0bKmsServerStateCheckDeamon\u5f02\u5e38", e);
                throw new RuntimeException(e);
            }
        }
    }

    public void switchServerIfNessary() throws Exception {
        this.kmsServerChecker.refreshState();
        if (this.kmsServerChecker.isAllServerDown()) {
            if (this.systemPausedTimestamp == 0L) {
                this.systemPausedTimestamp = System.currentTimeMillis();
            }
            this.kmsServerChecker.setCurrentAddress("");
            log.error("\u6240\u6709KMS\u670d\u52a1\u5668\u4e0b\u7ebf\uff0c\u8bf7\u68c0\u67e5KMS\u670d\u52a1\u5668\u72b6\u6001\uff0c{}", (Object)this.calcSystemAvaliableTime());
            return;
        }
        this.systemPausedTimestamp = 0L;
        if (this.kmsServerChecker.isCurrentAddressAlive()) {
            return;
        }
        KmsServerState state = this.kmsServerChecker.getNextAliveServer();
        if (state != null) {
            this.doSwitchServer(state);
        }
    }

    private String calcSystemAvaliableTime() {
        StringBuilder result = new StringBuilder();
        try {
            long offset = System.currentTimeMillis() - this.systemPausedTimestamp;
            long milliseconds = 21600000L - offset;
            if (milliseconds > 0L) {
                result.append("\u5f53\u524d\u7cfb\u7edf\u53ef\u7528\u65f6\u95f4\u7ea6\u4e3a\uff1a");
                long day = TimeUnit.MILLISECONDS.toDays(milliseconds);
                long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milliseconds));
                long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds));
                long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds));
                result.append(String.format("%d \u5929 %d \u5c0f\u65f6 %d \u5206\u949f %d \u79d2", day, hours, minutes, seconds));
            } else {
                result.append("\u5f53\u524d\u7cfb\u7edf\u5df2\u9501\u5b9a");
            }
        }
        catch (Exception e) {
            log.error("calcSystemAvaliableTime()\u5f02\u5e38", e);
        }
        return result.toString();
    }

    private void doSwitchServer(KmsServerState state) throws Exception {
        this.kmsServerChecker.setCurrentAddress(state.getServerAddress());
        Framework instance = Framework.getInstance();
        ClientSocketService clientSocketService = instance.getClientSocketService();
        for (int i = 0; i < this.systemRetryIntervalSeconds.length; ++i) {
            clientSocketService.reConnect(this.kmsServerChecker.getCurrentAddress());
            TimeUnit.SECONDS.sleep(5L);
            if (null != clientSocketService.getSocket() && clientSocketService.getSocket().connected()) {
                log.info("\u5ba2\u6237\u7aef\u5df2\u91cd\u65b0\u8fde\u63a5");
                break;
            }
            int retryInterval = this.systemRetryIntervalSeconds[i];
            log.info("\u5c1d\u8bd5\u6b21\u6570\uff1a{}\uff0c\u5f53\u524d\u7b49\u5f85\u65f6\u95f4\uff1a{}\u79d2", (Object)i, (Object)retryInterval);
            TimeUnit.SECONDS.sleep(retryInterval);
        }
        this.switchServerIfNessary();
    }
}


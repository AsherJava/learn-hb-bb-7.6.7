/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.authz;

import com.jiuqi.nvwa.sf.authz.AuthzUtil;
import com.jiuqi.nvwa.sf.authz.KmsServerState;
import com.jiuqi.nvwa.sf.authz.KmsServerStateCheckDeamon;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KmsServerChecker {
    private static KmsServerChecker instance = null;
    public static final Logger log = LoggerFactory.getLogger(KmsServerStateCheckDeamon.class);
    private final Map<String, KmsServerState> serverStates;
    private String currentAddress;
    private final String adminPort;

    public static KmsServerChecker getInstance(String authzCenterAddress, String adminPort) {
        if (instance == null) {
            instance = new KmsServerChecker(authzCenterAddress, adminPort);
        }
        return instance;
    }

    public KmsServerChecker(String authzCenterAddress, String adminPort) {
        List<String> kmsAddressList = AuthzUtil.getAllKmsAddress(authzCenterAddress);
        this.currentAddress = AuthzUtil.getFirstAddress(authzCenterAddress);
        this.adminPort = adminPort;
        this.serverStates = new HashMap<String, KmsServerState>();
        for (String kmsAddress : kmsAddressList) {
            this.serverStates.put(kmsAddress, new KmsServerState(kmsAddress, true, new Date()));
        }
    }

    private void refreshServerState(KmsServerState state) {
        state.setAlive(this.checkState(state.getServerAddress()));
        state.setLastUpdateTime(new Date());
    }

    public boolean checkState(String address) {
        String result = null;
        try {
            URL url = new URL(address);
            String host = url.getHost();
            result = AuthzUtil.doGet("http://" + host + ":" + this.adminPort);
        }
        catch (Exception e) {
            log.error("ping\u670d\u52a1\u5668\u53d1\u751f\u5f02\u5e38\uff0c\u5165\u53c2\uff1a{}", (Object)address, (Object)e);
        }
        return AuthzUtil.isNotBlank(result);
    }

    public void refreshState() {
        for (KmsServerState state : this.serverStates.values()) {
            this.refreshServerState(state);
        }
    }

    public boolean isAllServerDown() {
        boolean allServerDown = true;
        for (KmsServerState state : this.serverStates.values()) {
            if (!Boolean.TRUE.equals(state.getAlive())) continue;
            allServerDown = false;
            break;
        }
        return allServerDown;
    }

    public Map<String, KmsServerState> getServerStates() {
        return this.serverStates;
    }

    public String getCurrentAddress() {
        return this.currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public boolean isCurrentAddressAlive() {
        if (AuthzUtil.isBlank(this.currentAddress) || this.serverStates.get(this.currentAddress) == null) {
            return false;
        }
        return Boolean.TRUE.equals(this.serverStates.get(this.currentAddress).getAlive());
    }

    public KmsServerState getNextAliveServer() {
        for (KmsServerState state : this.serverStates.values()) {
            if (this.currentAddress.equals(state.getServerAddress()) || !Boolean.TRUE.equals(state.getAlive())) continue;
            return state;
        }
        return null;
    }
}


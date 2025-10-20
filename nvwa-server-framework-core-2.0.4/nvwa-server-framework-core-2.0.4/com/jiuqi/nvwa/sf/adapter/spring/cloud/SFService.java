/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.licence.LicenceInfo
 *  com.jiuqi.bi.core.nodekeeper.Node
 */
package com.jiuqi.nvwa.sf.adapter.spring.cloud;

import com.jiuqi.bi.authz.licence.LicenceInfo;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.ServiceUrl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SFService
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String kmsAddress;
    private String serviceName;
    private String productId;
    private String productVersion;
    private Long serviceTime;
    private boolean databaseLimitMode;
    private boolean devMode;
    private LicenceInfo licenceInfo;
    private List<ModuleWrapper> moduleList = new ArrayList<ModuleWrapper>();
    private List<Node> nodeList = new ArrayList<Node>();
    private ServiceUrl serviceUrl;

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public LicenceInfo getLicenceInfo() {
        return this.licenceInfo;
    }

    public void setLicenceInfo(LicenceInfo licenceInfo) {
        this.licenceInfo = licenceInfo;
    }

    public List<ModuleWrapper> getModuleList() {
        return this.moduleList;
    }

    public List<Node> getNodeList() {
        return this.nodeList;
    }

    public String getKmsAddress() {
        return this.kmsAddress;
    }

    public void setKmsAddress(String kmsAddress) {
        this.kmsAddress = kmsAddress;
    }

    public Long getServiceTime() {
        return this.serviceTime;
    }

    public void setServiceTime(Long serviceTime) {
        this.serviceTime = serviceTime;
    }

    public boolean isDatabaseLimitMode() {
        return this.databaseLimitMode;
    }

    public void setDatabaseLimitMode(boolean databaseLimitMode) {
        this.databaseLimitMode = databaseLimitMode;
    }

    public boolean isDevMode() {
        return this.devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public ServiceUrl getServiceUrl() {
        return this.serviceUrl;
    }

    public void setServiceUrl(ServiceUrl serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}


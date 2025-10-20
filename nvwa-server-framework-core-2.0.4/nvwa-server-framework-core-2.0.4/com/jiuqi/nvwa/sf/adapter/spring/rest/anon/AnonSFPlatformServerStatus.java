/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon;

import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.impl.SFExtendValueManage;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.impl.SFRemoteResourceManage;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/anon/sf/platform/api"})
public class AnonSFPlatformServerStatus {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SFRemoteResourceManage sfRemoteResourceManage;
    @Autowired
    private SFExtendValueManage sfExtendValueManage;

    @GetMapping(value={"/status"})
    public ReturnObject getStatus() {
        Framework instance = Framework.getInstance();
        boolean startSuccessful = instance.startSuccessful();
        boolean licenceValidate = instance.isLicenceValidate();
        boolean moduleValidate = instance.isModuleValidate();
        ReturnObject ro = new ReturnObject();
        switch (ServiceNodeStateHolder.getState()) {
            case LICENCE_UNHANDLED: 
            case MODULE_UNHANDLED: 
            case RUNNING: {
                ro.setLaunchSuccessful(true);
                break;
            }
            case STOP: 
            case LAUNCHING: 
            case INIT_MODULES: 
            case INIT_ENV: {
                ro.setLaunchSuccessful(false);
            }
        }
        ro.setStartSuccessful(startSuccessful);
        ro.setLicenceValidate(licenceValidate);
        ro.setModuleValidate(moduleValidate);
        ro.setProductId(instance.getProductId());
        ro.setVersion(instance.getProductVersion());
        ro.setLicenceExpire(instance.getLicenceExpire());
        if (startSuccessful && licenceValidate && moduleValidate) {
            if (!this.sfRemoteResourceManage.isAllServiceManagerResourceReady()) {
                this.LOGGER.warn("\u670d\u52a1\u8282\u70b9\u72b6\u6001\uff1a\u672c\u670d\u52a1\u542f\u52a8\u6210\u529f\uff0c\u8fdc\u7aef\u670d\u52a1\u5b58\u5728\u672a\u6b63\u5e38\u542f\u52a8\uff0c\u8282\u70b9\u72b6\u6001\u6539\u4e3a\u672a\u6b63\u5e38\u542f\u52a8");
                ro.setModuleValidate(false);
                ro.setStartSuccessful(false);
                ro.setUrl("/sf");
            }
        } else {
            ro.setUrl("/sf");
        }
        return ro;
    }

    @GetMapping(value={"/extend"})
    public Map<String, String> getExtend() {
        return this.sfExtendValueManage.getExtendValueMap();
    }

    public static class ReturnObject {
        private String productId;
        private String version;
        private boolean startSuccessful;
        private boolean launchSuccessful;
        private boolean licenceValidate;
        private boolean moduleValidate;
        private String url;
        private String licenceExpire;

        public boolean isStartSuccessful() {
            return this.startSuccessful;
        }

        public void setStartSuccessful(boolean startSuccessful) {
            this.startSuccessful = startSuccessful;
        }

        public boolean isLicenceValidate() {
            return this.licenceValidate;
        }

        public void setLicenceValidate(boolean licenceValidate) {
            this.licenceValidate = licenceValidate;
        }

        public boolean isModuleValidate() {
            return this.moduleValidate;
        }

        public void setModuleValidate(boolean moduleValidate) {
            this.moduleValidate = moduleValidate;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductId() {
            return this.productId;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getVersion() {
            return this.version;
        }

        public boolean isLaunchSuccessful() {
            return this.launchSuccessful;
        }

        public void setLaunchSuccessful(boolean launchSuccessful) {
            this.launchSuccessful = launchSuccessful;
        }

        public String getLicenceExpire() {
            return this.licenceExpire;
        }

        public void setLicenceExpire(String licenceExpire) {
            this.licenceExpire = licenceExpire;
        }
    }
}


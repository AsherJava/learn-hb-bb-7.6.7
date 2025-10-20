/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.plugin.cloud_acca.penetrate.penetratePlugin.controller;

import com.jiuqi.bde.plugin.cloud_acca.penetrate.penetratePlugin.client.BdeCloudAccaPenetratePluginClient;
import com.jiuqi.bde.plugin.cloud_acca.penetrate.penetratePlugin.service.BdeCloudAccaVoucherPenetratePluginService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BdeCloudAccaPenetratePluginController
implements BdeCloudAccaPenetratePluginClient {
    @Autowired
    private BdeCloudAccaVoucherPenetratePluginService egasVoucherPenetratePluginService;

    @Override
    public BusinessResponseEntity<String> getEgasSsoAppId(String unitCode) {
        return BusinessResponseEntity.ok((Object)this.egasVoucherPenetratePluginService.getEgasPenetrateSsoAppId(unitCode));
    }
}


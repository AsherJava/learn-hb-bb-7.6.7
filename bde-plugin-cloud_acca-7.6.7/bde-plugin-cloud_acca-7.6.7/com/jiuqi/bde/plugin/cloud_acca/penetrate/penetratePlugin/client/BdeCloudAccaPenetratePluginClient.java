/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.bde.plugin.cloud_acca.penetrate.penetratePlugin.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BdeCloudAccaPenetratePluginClient {
    public static final String API_PATH = "/api/bde/v1/penetrate/plugin/egas";

    @GetMapping(value={"/api/bde/v1/penetrate/plugin/egas/getEgasSsoAppId/{unitCode}"})
    public BusinessResponseEntity<String> getEgasSsoAppId(@PathVariable(value="unitCode") String var1);
}


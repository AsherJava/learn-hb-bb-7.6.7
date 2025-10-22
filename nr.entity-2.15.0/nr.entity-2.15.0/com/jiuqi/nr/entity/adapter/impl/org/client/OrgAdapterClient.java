/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.organization.domain.OrgAuthDO
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RequestPart
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.entity.adapter.impl.org.client;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(contextId="com.jiuqi.nr.entity.adapter.impl.org.client.OrgAdapterClient", name="NRORGADAPTER", url="${feignClient.orgMgr.url}")
@RefreshScope
public interface OrgAdapterClient {
    @GetMapping(value={"/org/data/export"})
    public byte[] exportData(@RequestParam Map<String, Object> var1);

    @PostMapping(value={"/org/data/importData"}, consumes={"multipart/form-data"})
    public String importData(@RequestPart(name="multipartFile") MultipartFile var1, @RequestParam Map<String, Object> var2);

    @PostMapping(value={"/org/auth/mgr/detail/update"})
    public R updateDetail(@RequestBody List<OrgAuthDO> var1);
}


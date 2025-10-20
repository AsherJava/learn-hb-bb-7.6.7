/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.common.reportsync.api;

import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.va.domain.common.R;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.common.reportsync.api.ReportSyncTokenClient.java", name="gcreport-service", url="url-placeholder", primary=false)
public interface ReportSyncTokenClient {
    @PostMapping(value={"/nvwa/login"})
    public R nvwaLogin(URI var1, @RequestBody NvwaLoginUserDTO var2);
}


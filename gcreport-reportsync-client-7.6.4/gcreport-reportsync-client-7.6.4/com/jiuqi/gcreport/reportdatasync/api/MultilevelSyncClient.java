/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  feign.Request$Options
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestPart
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelSyncVO;
import feign.Request;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface MultilevelSyncClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/multileveSync";

    default public Request.Options getOptions() {
        Request.Options options = new Request.Options(60L, TimeUnit.SECONDS, 1800L, TimeUnit.SECONDS, true);
        return options;
    }

    @PostMapping(value={"/api/gcreport/v1/multileveSync/export"})
    public BusinessResponseEntity<Boolean> multilevelSync(@RequestBody MultilevelSyncVO var1);

    @PostMapping(value={"/api/gcreport/v1/multileveSync/import"}, consumes={"multipart/form-data"})
    public BusinessResponseEntity<Boolean> multilevelImport(Request.Options var1, @RequestPart(value="importContext") String var2, @RequestPart(value="syncAttachFiles") MultipartFile[] var3);

    @PostMapping(value={"/api/gcreport/v1/multileveSync/return"})
    public BusinessResponseEntity<Boolean> multilevelReturn(@RequestBody MultilevelSyncVO var1);
}


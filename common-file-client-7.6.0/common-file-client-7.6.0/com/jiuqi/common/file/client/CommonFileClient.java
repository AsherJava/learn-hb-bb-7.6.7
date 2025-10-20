/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.file.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(contextId="com.jiuqi.common.file.client.CommonFileClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface CommonFileClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/common/file/";

    @PostMapping(value={"/api/gcreport/v1/common/file/queryOssFileByFileKeys"})
    public BusinessResponseEntity<Map<String, MultipartFile>> queryOssFileByFileKeys(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/common/file/queryOssFileByFileKeysByBucket"})
    public BusinessResponseEntity<Map<String, MultipartFile>> queryOssFileByFileKeysByBucket(@RequestParam(value="bucket") String var1, @RequestBody List<String> var2);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.file.client.CommonFileClient
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.file.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.file.client.CommonFileClient;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.service.CommonFileService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CommonFileController
implements CommonFileClient {
    @Autowired
    private CommonFileService fileService;

    public BusinessResponseEntity<Map<String, MultipartFile>> queryOssFileByFileKeys(List<String> fileKeys) {
        HashMap multipartFiles = new HashMap();
        if (CollectionUtils.isEmpty(fileKeys)) {
            return BusinessResponseEntity.ok(multipartFiles);
        }
        fileKeys.stream().forEach(fileKey -> {
            CommonFileDTO file = this.fileService.queryOssFileByFileKey((String)fileKey);
            multipartFiles.put(fileKey, file);
        });
        return BusinessResponseEntity.ok(multipartFiles);
    }

    public BusinessResponseEntity<Map<String, MultipartFile>> queryOssFileByFileKeysByBucket(String bucket, List<String> fileKeys) {
        HashMap multipartFiles = new HashMap();
        if (CollectionUtils.isEmpty(fileKeys)) {
            return BusinessResponseEntity.ok(multipartFiles);
        }
        fileKeys.stream().forEach(fileKey -> {
            CommonFileDTO file = this.fileService.queryOssFileByFileKey(bucket, (String)fileKey);
            multipartFiles.put(fileKey, file);
        });
        return BusinessResponseEntity.ok(multipartFiles);
    }
}


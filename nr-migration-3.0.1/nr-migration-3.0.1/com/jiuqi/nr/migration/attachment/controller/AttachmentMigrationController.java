/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.user.service.SystemUserService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.migration.attachment.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.migration.attachment.bean.IndexFileInfo;
import com.jiuqi.nr.migration.attachment.bean.ReturnObject;
import com.jiuqi.nr.migration.attachment.business.AttachmentMigrationBusiness;
import com.jiuqi.nr.migration.attachment.business.AttachmentMigrationUpgradeExecutor;
import com.jiuqi.nr.migration.attachment.util.AttachmentMigrationFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags={"\u8fc1\u79fb(jqr->nr)\uff1a \u9644\u4ef6\u8fc1\u79fb"})
@RestController
@RequestMapping(value={"/nr-attachment-jqr/migration"})
public class AttachmentMigrationController {
    @Autowired
    SystemIdentityService systemIdentityServiceByIdentity;
    @Autowired
    SystemUserService systemUserService;
    @Autowired
    AttachmentMigrationUpgradeExecutor attachmentMigrationUpgradeExecutor;
    private static final Logger logger = LoggerFactory.getLogger(AttachmentMigrationController.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u5355\u4e2a\u9644\u4ef6\u8fc1\u79fb", notes="blobFile\u9644\u4ef6\uff1bindexFile\u7d22\u5f15\u6587\u4ef6")
    @PostMapping(value={"/single"})
    public ReturnObject singleMigration(@RequestHeader(name="username", required=true) String username, @RequestParam(name="files") MultipartFile[] files) throws IOException {
        if (!this.hasAuth(username)) {
            return ReturnObject.Error("\u975e\u7cfb\u7edf\u7ba1\u7406\u5458\u7528\u6237\uff0c\u65e0\u9644\u4ef6\u8fc1\u79fb\u6743\u9650");
        }
        if (files == null) {
            return ReturnObject.Error("\u6279\u91cf\u4f20\u8f93\u6587\u4ef6zipFile\u4e3a\u7a7a\u3002");
        }
        logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u5f00\u59cb\u6267\u884c\u2026\u2026");
        List<File> singleFileInfos = AttachmentMigrationFileUtils.cacheSingleAttachmentInfos(files);
        List idxJsonFile = singleFileInfos.stream().filter(file -> file.getName().contains("index.json")).collect(Collectors.toList());
        if (idxJsonFile.size() == 0) {
            logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u65e0index.json\u7d22\u5f15\u6587\u4ef6\u3002");
            return ReturnObject.Error("\u9644\u4ef6\u4fe1\u606f\u6709\u8bef\u3002");
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        IndexFileInfo attachmentInfo = (IndexFileInfo)mapper.readValue((File)idxJsonFile.get(0), IndexFileInfo.class);
        if (attachmentInfo == null || attachmentInfo.getData() == null || attachmentInfo.getData().size() == 0) {
            logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1aindex.json\u7d22\u5f15\u6587\u4ef6\u5185\u5bb9\u6709\u8bef\u3002");
            return ReturnObject.Error("\u9644\u4ef6\u4fe1\u606f\u6709\u8bef\u3002");
        }
        List mainFile = singleFileInfos.stream().filter(file -> !file.getName().contains("index.json")).collect(Collectors.toList());
        if (mainFile.size() == 0) {
            logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u9644\u4ef6\u4fe1\u606f\u6709\u8bef\u3002");
            return ReturnObject.Error("\u9644\u4ef6\u4fe1\u606f\u6709\u8bef\u3002");
        }
        try (InputStream fileIs = Files.newInputStream(((File)mainFile.get(0)).toPath(), new OpenOption[0]);){
            logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u8fc1\u79fb\u7684\u9644\u4ef6\u6570\uff1a1\u4e2a\uff1b\u8fc1\u79fb\u7684\u9644\u4ef6\u662f\uff1a" + attachmentInfo.getData().get(0).getFile());
            AttachmentMigrationBusiness.migrationOpertion(attachmentInfo.getData().get(0), fileIs, null);
        }
        catch (ObjectStorageException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            ReturnObject returnObject = ReturnObject.Error("\u9644\u4ef6RequestHeader\u5b58\u50a8\u5f02\u5e38" + e.getMessage());
            return returnObject;
        }
        finally {
            AttachmentMigrationFileUtils.deleteSingleAttachmentCache(singleFileInfos.get(0).getParentFile());
        }
        logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u6267\u884c\u6210\u529f\u2026\u2026");
        return ReturnObject.Success();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u6279\u91cf\u8fc1\u79fb\u9644\u4ef6", notes="zipFile \u591a\u9644\u4ef6\u538b\u7f29\u6587\u4ef6 \u76ee\u5f55\u7ed3\u6784\uff1a/FILE index.json")
    @PostMapping(value={"/batch"})
    public ReturnObject batchMigration(@RequestHeader(name="username", required=true) String username, @RequestParam(value="zipFile") MultipartFile zipFile) {
        if (!this.hasAuth(username)) {
            return ReturnObject.Error("\u975e\u7cfb\u7edf\u7ba1\u7406\u5458\u7528\u6237\uff0c\u65e0\u9644\u4ef6\u8fc1\u79fb\u6743\u9650");
        }
        File cacheDir = null;
        if (zipFile == null) {
            return ReturnObject.Error("\u6279\u91cf\u4f20\u8f93\u6587\u4ef6zipFile\u4e3a\u7a7a\u3002");
        }
        logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u5f00\u59cb\u6267\u884c\u2026\u2026");
        try {
            cacheDir = AttachmentMigrationFileUtils.cacheBatchAttachmentInfos(zipFile);
            if (cacheDir == null) {
                ReturnObject returnObject = ReturnObject.Error("\u9644\u4ef6\u4f20\u8f93\u5f02\u5e38: \u7f13\u5b58\u5730\u5740\u521b\u5efa\u5931\u8d25\uff0c\u8be6\u60c5\u8bf7\u67e5\u770b\u65e5\u5fd7\u3002");
                return returnObject;
            }
            if (cacheDir.exists() && cacheDir.isDirectory()) {
                boolean unzipFlag = AttachmentMigrationFileUtils.unzip(cacheDir);
                if (!unzipFlag) {
                    logger.error("\u9644\u4ef6\u8fc1\u79fb\uff1azip\u6587\u4ef6\uff1a" + cacheDir.getAbsolutePath() + "\u89e3\u538b\u5931\u8d25\u3002");
                    ReturnObject returnObject = ReturnObject.Error("\u9644\u4ef6\u4f20\u8f93\u5f02\u5e38: zip\u6587\u4ef6\u52a0\u538b\u5931\u8d25\u3002");
                    return returnObject;
                }
                logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1azip\u6587\u4ef6\uff1a" + cacheDir.getAbsolutePath() + "\u89e3\u538b\u6210\u529f\u3002");
                AttachmentMigrationBusiness.batchMigrationOpertion(cacheDir);
            }
        }
        catch (ObjectStorageException | IOException e) {
            e.printStackTrace();
            ReturnObject returnObject = ReturnObject.Error("\u9644\u4ef6\u4f20\u8f93\u5f02\u5e38: " + e);
            return returnObject;
        }
        finally {
            AttachmentMigrationFileUtils.deleteBatchAttachmentCache(cacheDir);
        }
        logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u6267\u884c\u6210\u529f\u2026\u2026");
        return ReturnObject.Success();
    }

    private boolean hasAuth(String username) {
        if (username != null && !"".equals(username)) {
            return this.systemUserService.exists(username) || this.systemIdentityServiceByIdentity.isBusinessManager(username) || this.systemIdentityServiceByIdentity.isSystemIdentity(username);
        }
        return false;
    }

    @ApiOperation(value="\u9644\u4ef6\u5347\u7ea7", notes="\u8fc1\u79fb\u540e\uff0c\u5347\u7ea7\u9644\u4ef6")
    @PostMapping(value={"/upgrade"})
    public ReturnObject upgradeFileStorge(@RequestHeader(name="username", required=true) String username) {
        if (!this.hasAuth(username)) {
            return ReturnObject.Error("\u975e\u7cfb\u7edf\u7ba1\u7406\u5458\u7528\u6237\uff0c\u65e0\u9644\u4ef6\u8fc1\u79fb\u6743\u9650");
        }
        return this.attachmentMigrationUpgradeExecutor.execute();
    }
}


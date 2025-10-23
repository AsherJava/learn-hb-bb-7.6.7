/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.migration.transfer.controller;

import com.jiuqi.nr.migration.attachment.bean.ReturnObject;
import com.jiuqi.nr.migration.transfer.service.IOrgDataTransferService;
import com.jiuqi.nr.migration.transfer.vo.OrgDataFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/nr-transfer-jqr/orgData"})
public class OrgDataTransferController {
    private static final Logger logger = LoggerFactory.getLogger(OrgDataTransferController.class);
    @Autowired
    private IOrgDataTransferService orgDataTransferService;

    @PostMapping(value={"/upload"})
    public ReturnObject upload(@RequestParam(name="file") MultipartFile file) {
        try {
            OrgDataFile orgDataFile = this.orgDataTransferService.uploadFile(file);
            return ReturnObject.Success(orgDataFile);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnObject.Error(e.toString());
        }
    }

    @GetMapping(value={"/import/{fileKey}"})
    public ReturnObject importOrgData(@PathVariable String fileKey) {
        try {
            this.orgDataTransferService.importOrgData(fileKey);
            return ReturnObject.Success();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnObject.Error(e.toString());
        }
    }
}


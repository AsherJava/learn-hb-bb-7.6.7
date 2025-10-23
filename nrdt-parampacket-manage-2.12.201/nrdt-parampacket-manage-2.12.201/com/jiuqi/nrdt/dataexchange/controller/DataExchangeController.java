/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO
 *  com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO
 *  com.jiuqi.nr.transmission.data.internal.file.FileHandleService
 *  com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nrdt.dataexchange.controller;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.internal.file.FileHandleService;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value={"/api/nrdt/dataexchange"})
@RestController
public class DataExchangeController {
    private static final Logger logger = LoggerFactory.getLogger(DataExchangeController.class);
    @Autowired
    private FileHandleService fileHandleService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @PostMapping(value={"/exportData"})
    public String exportData(@RequestBody SyncSchemeParamDO syncSchemeParamDO) throws Exception {
        SyncSchemeParamDTO syncSchemeParamDTO = SyncSchemeParamDTO.getInstance((SyncSchemeParamDO)syncSchemeParamDO);
        String executeKey = syncSchemeParamDO.getKey();
        if (!StringUtils.hasText(executeKey)) {
            executeKey = UUID.randomUUID().toString();
            syncSchemeParamDTO.setKey(executeKey);
        }
        TransmissionMonitor monitor = new TransmissionMonitor(executeKey, this.cacheObjectResourceRemote);
        try {
            this.adaptSyncParam(syncSchemeParamDTO);
            File file = this.fileHandleService.fileExport((AsyncTaskMonitor)monitor, syncSchemeParamDTO);
            return new String(this.getBytes(file), "ISO-8859-1");
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fanrd\u6570\u636e\u6587\u4ef6\u5931\u8d25", e);
            return null;
        }
    }

    private void adaptSyncParam(SyncSchemeParamDTO syncSchemeParamDTO) {
        if (StringUtils.hasText(syncSchemeParamDTO.getForm())) {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(syncSchemeParamDTO.getPeriodValue(), syncSchemeParamDTO.getTask());
            String formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
            String[] nrdtFormCodes = syncSchemeParamDTO.getForm().split(";");
            StringBuilder netFormKeyBuilder = new StringBuilder();
            int index = 0;
            for (int i = 0; i < nrdtFormCodes.length; ++i) {
                FormDefine formDefine = this.runTimeViewController.listFormByCodeAndFormScheme(nrdtFormCodes[i], formSchemeKey);
                if (formDefine == null) continue;
                if (index > 0) {
                    netFormKeyBuilder.append(";" + formDefine.getKey());
                } else {
                    netFormKeyBuilder.append(formDefine.getKey());
                }
                ++index;
            }
            syncSchemeParamDTO.setForm(netFormKeyBuilder.toString());
        }
    }

    public byte[] getBytes(File file) throws IOException {
        byte[] data = null;
        try {
            PathUtils.validatePathManipulation((String)file.getPath());
            try (FileInputStream fis = new FileInputStream(file);
                 ByteArrayOutputStream baos = new ByteArrayOutputStream();){
                int len;
                byte[] buffer = new byte[1024];
                while ((len = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                data = baos.toByteArray();
            }
            catch (Exception e) {
                logger.error("\u8bfb\u53d6\u6587\u4ef6\u5931\u8d25", e);
            }
        }
        catch (SecurityContentException e) {
            logger.error("\u8bfb\u53d6\u6587\u4ef6\u5931\u8d25", e);
        }
        return data;
    }
}


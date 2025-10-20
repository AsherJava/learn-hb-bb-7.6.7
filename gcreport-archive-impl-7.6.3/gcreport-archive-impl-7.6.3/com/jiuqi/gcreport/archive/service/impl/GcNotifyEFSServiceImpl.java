/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData
 *  com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO
 *  org.springframework.http.HttpHeaders
 */
package com.jiuqi.gcreport.archive.service.impl;

import com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData;
import com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO;
import com.jiuqi.gcreport.archive.common.ArchiveProperties;
import com.jiuqi.gcreport.archive.service.GcNotifyEFSService;
import java.util.Base64;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class GcNotifyEFSServiceImpl
implements GcNotifyEFSService {
    private Logger logger = LoggerFactory.getLogger(GcNotifyEFSServiceImpl.class);
    @Autowired
    private ArchiveProperties archiveProperties;

    @Override
    public List<EFSResponseData> notifyEFSArchive(SendArchiveVO sendArchiveInfo) {
        String url = this.archiveProperties.getEfsAddress() + "/EleRec/eleFiscalStatement";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json;charset=utf-8");
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("appID", this.archiveProperties.getAppID());
        headers.add("keySecret", this.archiveProperties.getKeySecret());
        Base64.Encoder encoder = Base64.getEncoder();
        String authorizationStr = this.archiveProperties.getAppID() + ":" + this.archiveProperties.getKeySecret();
        headers.add("Authorization", "Basic " + encoder.encodeToString(authorizationStr.getBytes()));
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import org.springframework.stereotype.Service;

@Service
public class MetaDeployHandle {
    public void doPublish(MetaDataDeployDim metaDataDeployDim, String userName, long newVersion, IMetaInfoService metaInfoService, IMetaGroupService metaGroupSerice) {
        if ("group".equals(metaDataDeployDim.getType())) {
            metaGroupSerice.deployGroupById(userName, metaDataDeployDim, newVersion);
        } else {
            metaInfoService.deployMetaById(userName, metaDataDeployDim, newVersion);
        }
    }
}


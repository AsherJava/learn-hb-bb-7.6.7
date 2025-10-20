/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.common.utils.VersionManage;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.IMetaParamSyncService;
import com.jiuqi.va.bizmeta.service.IMetaVersionService;
import com.jiuqi.va.domain.biz.MetaDataDTO;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/meta/param"})
public class MetaDataParamSyncController {
    @Autowired
    private IMetaParamSyncService metaParamSyncService;
    @Autowired
    private IMetaGroupService metaGroupService;
    @Autowired
    private IMetaInfoService metaInfoService;
    @Autowired
    private IMetaVersionService metaVersionService;

    @PostMapping(value={"/update/metaInfoUser"})
    public void updateByPrimaryKey(@RequestBody MetaInfoEditionDO editionDO) {
        this.metaParamSyncService.updateByPrimaryKeySelective(editionDO);
    }

    @PostMapping(value={"/get/groupsByMetaType"})
    public List<MetaGroupDO> getGroupListByMetaType(@RequestBody String metaType) {
        return this.metaGroupService.getGroupListByMetaType(metaType);
    }

    @PostMapping(value={"/get/metaInfos"})
    public List<MetaInfoDO> getMetaInfoList(@RequestBody MetaInfoDO metaInfoDO) {
        return this.metaInfoService.getMetaInfoList(metaInfoDO);
    }

    @PostMapping(value={"/get/metaInfoEdition"})
    public MetaInfoEditionDO getMetaInfoEdition(@RequestBody MetaInfoEditionDO paramMetaInfoEdition) {
        return this.metaParamSyncService.getMetaInfoEdition(paramMetaInfoEdition);
    }

    @PostMapping(value={"/get/version"})
    public Long getVersion() {
        return VersionManage.getInstance().newVersion(this.metaVersionService);
    }

    @PostMapping(value={"/query/metaInfo"})
    public MetaInfoDO queryMetaInfo(@RequestBody MetaInfoDO paramInfoDO) {
        return this.metaParamSyncService.queryMetaInfo(paramInfoDO);
    }

    public MetaDataDTO createMetaData(MetaInfoDTO data) {
        return this.metaParamSyncService.createMetaData(data);
    }

    @PostMapping(value={"/add/infoEdition"})
    public void insertInfoEdition(@RequestBody MetaInfoEditionDO metaInfoEditionDO) {
        this.metaParamSyncService.insertInfoEdition(metaInfoEditionDO);
    }

    @PostMapping(value={"/update/metaData"})
    public void updateMetaData(@RequestBody MetaInfoDTO IMetaDataDTO) {
        this.metaParamSyncService.updateMetaData(IMetaDataDTO);
    }

    @PostMapping(value={"/doPublish"})
    public void doPublish(@RequestBody UUID metaInfoId, MetaInfoDO importDefine, MetaInfoEditionDO editionDO) {
        this.metaParamSyncService.doPublish(metaInfoId, importDefine, editionDO);
    }
}


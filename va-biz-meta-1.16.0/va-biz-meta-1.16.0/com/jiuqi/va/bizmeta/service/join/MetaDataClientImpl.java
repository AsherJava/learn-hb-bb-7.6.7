/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployVO
 *  com.jiuqi.va.domain.metainfo.MetaInfoVO
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.bizmeta.service.join;

import com.jiuqi.va.bizmeta.common.utils.ModulesServerProvider;
import com.jiuqi.va.bizmeta.common.utils.VersionManage;
import com.jiuqi.va.bizmeta.controller.MetaBaseInfoController;
import com.jiuqi.va.bizmeta.controller.MetaDataController;
import com.jiuqi.va.bizmeta.controller.MetaDataModulesController;
import com.jiuqi.va.bizmeta.controller.MetaDataParamSyncController;
import com.jiuqi.va.bizmeta.controller.MetaDeployController;
import com.jiuqi.va.bizmeta.controller.MetaGroupController;
import com.jiuqi.va.bizmeta.controller.MetaInfoController;
import com.jiuqi.va.bizmeta.controller.MetaVersionController;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupVO;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.service.IMetaAuthService;
import com.jiuqi.va.bizmeta.service.IMetaOptionService;
import com.jiuqi.va.bizmeta.service.IMetaVersionService;
import com.jiuqi.va.bizmeta.service.MetaDataUpdateService;
import com.jiuqi.va.bizmeta.service.MetaHelperService;
import com.jiuqi.va.bizmeta.service.impl.MetaBaseInfoService;
import com.jiuqi.va.domain.biz.MetaDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployVO;
import com.jiuqi.va.domain.metainfo.MetaInfoVO;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Primary
public class MetaDataClientImpl
implements MetaDataClient {
    @Autowired
    private MetaDataController metaDataService;
    @Autowired
    private MetaInfoController metaInfoService;
    @Autowired
    private MetaGroupController metaGroupController;
    @Autowired
    private MetaBaseInfoService metaBaseInfo;
    @Autowired
    private IMetaVersionService metaVersionService;
    @Autowired
    private MetaDataModulesController metaModulesController;
    @Autowired
    private MetaHelperService metaHelperService;
    private MetaDataParamSyncController metaParamSyncController;
    @Autowired
    private IMetaOptionService metaOptionService;
    @Autowired
    private IMetaAuthService metaAuthService;
    @Autowired
    private MetaVersionController metaVersionController;
    @Autowired
    private MetaDataUpdateService metaDataUpdateService;
    @Autowired
    private MetaDeployController metaDeployService;
    private MetaBaseInfoController metaBaseInfoService;

    public MetaDataParamSyncController getMetaParamSyncController() {
        if (this.metaParamSyncController == null) {
            this.metaParamSyncController = (MetaDataParamSyncController)ApplicationContextRegister.getBean(MetaDataParamSyncController.class);
        }
        return this.metaParamSyncController;
    }

    public MetaBaseInfoController getMetaBaseInfoService() {
        if (this.metaBaseInfoService == null) {
            this.metaBaseInfoService = (MetaBaseInfoController)ApplicationContextRegister.getBean(MetaBaseInfoController.class);
        }
        return this.metaBaseInfoService;
    }

    public void updateMetaInfoAndEdition(MetaInfoDTO metaDataDTO, MetaInfoEditionDO editionDO) {
        editionDO.setVersionNO(Long.valueOf(VersionManage.getInstance().newVersion(this.metaVersionService)));
        this.getMetaParamSyncController().updateMetaData(metaDataDTO);
        this.getMetaParamSyncController().updateByPrimaryKey(editionDO);
    }

    public R addDesignAndInfoEdition(MetaInfoDTO data, MetaInfoEditionDO metaInfoEditionDO) {
        this.getMetaParamSyncController().createMetaData(data);
        this.getMetaParamSyncController().insertInfoEdition(metaInfoEditionDO);
        return R.ok();
    }

    public R getMetaDesign(MetaDesignDTO designDTO) {
        return this.metaDataService.getMetaDesign(designDTO);
    }

    public PageVO<MetaTreeInfoDTO> getAllMetas(@RequestBody TenantDO param) {
        return this.getMetaBaseInfoService().getAllMetas(param);
    }

    public R findMetaInfoByDefineCode(TenantDO param) {
        return this.metaInfoService.findMetaInfoByUniqueCode(param);
    }

    public PageVO<MetaInfoDim> getAllMetaInfoByMetaType(TenantDO param) {
        return this.metaInfoService.getAllMetaInfoByMetaType(param);
    }

    public R addMetaGroup(@RequestBody MetaTreeInfoDTO metaTreeInfoDTO) {
        MetaGroupDTO metaGroupDTO = new MetaGroupDTO();
        metaGroupDTO.setName(metaTreeInfoDTO.getName());
        metaGroupDTO.setModuleName(metaTreeInfoDTO.getModelName());
        metaGroupDTO.setTitle(metaTreeInfoDTO.getTitle());
        metaGroupDTO.setParentName(metaTreeInfoDTO.getParentName());
        metaGroupDTO.setMetaType(metaTreeInfoDTO.getGroupName());
        MetaGroupVO metaGroupVO = this.metaGroupController.addMetaGroup(metaGroupDTO);
        if (metaGroupVO.isFlag().booleanValue()) {
            return R.ok();
        }
        return R.error((String)metaGroupVO.getMessage());
    }

    public void doPulish(UUID metaInfoId, MetaInfoDO importDefine, MetaInfoEditionDO editionDO) {
        this.getMetaParamSyncController().doPublish(metaInfoId, importDefine, editionDO);
    }

    public MetaInfoEditionDO getMetaInfoEdition(MetaInfoEditionDO paramMetaInfoEdition) {
        return this.getMetaParamSyncController().getMetaInfoEdition(paramMetaInfoEdition);
    }

    public Long getVersion() {
        return VersionManage.getInstance().newVersion(this.metaVersionService);
    }

    public MetaDataDTO gatherMetaData(MetaDataDTO metaDataDTO) {
        return this.metaBaseInfo.gatherMetaData(metaDataDTO);
    }

    public R getModuleByName(ModuleDTO moduleDTO) {
        ModuleServer moduleServer = ModulesServerProvider.getModuleServer(moduleDTO.getModuleName(), moduleDTO.getFunctionType());
        if (moduleServer == null) {
            return R.error((String)String.format("\u672a\u627e\u5230\u6a21\u5757\u6807\u8bc6\u4e3a%s\u5bf9\u5e94\u7684\u6a21\u5757", moduleDTO.getModuleName()));
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", moduleServer.getName());
        map.put("title", moduleServer.getTitle());
        map.put("server", moduleServer.getServer());
        map.put("path", moduleServer.getPath());
        return R.ok(map);
    }

    public PageVO<MetaTreeInfoDTO> getAllWorkflowMetas(TenantDO tenantDO) {
        return this.getMetaBaseInfoService().getAllWorkflowMetas(tenantDO);
    }

    public List<MetaTreeInfoDTO> getAllModule(TenantDO param) {
        return this.metaModulesController.getAllModule(param);
    }

    public List<MetaTreeInfoDTO> getMetaDataGroup(TenantDO tenant) {
        return this.metaGroupController.getMetaDataGroup(tenant);
    }

    public MetaInfoDTO getMetaInfoAndData(MetaInfoDTO metaInfoDTO) {
        return this.metaInfoService.getMetaInfoAndData(metaInfoDTO);
    }

    public List<MetaTreeInfoDTO> getMetaInfoList(TenantDO param) {
        return this.metaInfoService.getMetaInfoList(param);
    }

    public List<MetaTreeInfoDTO> listAllMetaData(TenantDO tenantDO) {
        return this.getMetaBaseInfoService().listAllMetaData(tenantDO);
    }

    public List<VaI18nResourceItem> listWorkFlowVersionItemResourceList(TenantDO tenant) {
        return this.metaHelperService.listWorkFlowVersionItemResourceList(tenant);
    }

    public List<VaI18nResourceItem> listWorkFlowVersionResourceList(TenantDO tempTenantDO) {
        return this.metaHelperService.listWorkFlowVersionResourceList(tempTenantDO);
    }

    public List<OptionItemVO> listOption(OptionItemDTO param) {
        return this.metaOptionService.list(param);
    }

    public Set<String> checkUserAuth(TenantDO tenantParam) {
        Map extInfo = tenantParam.getExtInfo();
        MetaAuthDTO metaAuthCheck = new MetaAuthDTO();
        metaAuthCheck.setTenantName(extInfo.get("TenantName").toString());
        metaAuthCheck.setGroupflag(0);
        metaAuthCheck.setMetaType(extInfo.get("MetaType").toString());
        return this.metaAuthService.checkUserAuth(metaAuthCheck);
    }

    public R listMetaInfoHis(MetaInfoDTO metaInfoDTO) {
        return this.metaVersionController.listMetaInfoHis(metaInfoDTO);
    }

    public R getFirstPublishedByModelName(MetaInfoDTO param) {
        return this.metaInfoService.getFirstPublishedByModelName(param);
    }

    public MetaDataBatchUpdateProgress singleUpdate(MetaInfoDTO metaInfoDTO) {
        return this.metaDataUpdateService.singleUpdate(metaInfoDTO);
    }

    public R checkMetaName(MetaInfoDTO metaInfoDTO) {
        return this.metaInfoService.checkMetaName(metaInfoDTO);
    }

    public MetaInfoVO addMetaInfo(MetaInfoDTO metaInfoDTO) {
        return this.metaInfoService.addMedtaInfo(metaInfoDTO);
    }

    public MetaInfoVO updateMetaData(UUID dataId, String designData) {
        return this.metaDataService.updateMetaData(dataId, designData);
    }

    public R getMetaDesignByUniqueCode(MetaDesignDTO designParam) {
        return this.metaDataService.getMetaDesignByUniqueCode(designParam);
    }

    public MetaDataDeployVO publishMetaDataByUniqueCode(MetaInfoDTO param) {
        return this.metaDeployService.publishMetaDataByUniqueCode(param);
    }
}


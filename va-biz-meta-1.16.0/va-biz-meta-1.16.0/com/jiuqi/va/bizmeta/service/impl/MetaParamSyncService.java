/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.biz.domain.PluginCheckResultDTO
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.paramsync.domain.VaParamSyncGroupTypeEnum
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMetaDataDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMetaGroupDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMetaRelevanceDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncModuleEnum
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 *  com.jiuqi.va.paramsync.domain.VaParamSyncOptEnum
 *  com.jiuqi.va.paramsync.domain.VaParamSyncParamDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncResultDO
 *  com.jiuqi.va.paramsync.feign.client.VaParamSyncFeignClient
 *  feign.RetryableException
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.common.utils.MetaUtils;
import com.jiuqi.va.bizmeta.common.utils.VersionManage;
import com.jiuqi.va.bizmeta.dao.IMetaDataGroupDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataGroupUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoUserDao;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupEditionDO;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDTO;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.domain.multimodule.Modules;
import com.jiuqi.va.bizmeta.exception.MetaCheckException;
import com.jiuqi.va.bizmeta.service.IMetaBaseInfoService;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.IMetaParamSyncService;
import com.jiuqi.va.bizmeta.service.IMetaVersionService;
import com.jiuqi.va.bizmeta.service.impl.MetaDeployHandle;
import com.jiuqi.va.bizmeta.service.impl.help.MetaDataGroupService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.paramsync.domain.VaParamSyncGroupTypeEnum;
import com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMetaDataDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMetaGroupDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMetaRelevanceDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncModuleEnum;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;
import com.jiuqi.va.paramsync.domain.VaParamSyncOptEnum;
import com.jiuqi.va.paramsync.domain.VaParamSyncParamDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncResultDO;
import com.jiuqi.va.paramsync.feign.client.VaParamSyncFeignClient;
import feign.RetryableException;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MetaParamSyncService
implements IMetaParamSyncService {
    private static final Logger log = LoggerFactory.getLogger(MetaParamSyncService.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private IMetaDataGroupDao metaGroupDao;
    @Autowired
    private IMetaDataGroupUserDao metaGroupUserDao;
    @Autowired
    private IMetaGroupService metaGroupService;
    @Autowired
    private IMetaInfoService metaInfoService;
    @Autowired
    private IMetaDataInfoUserDao metaInfoEditionDao;
    @Autowired
    private IMetaBaseInfoService metaBaseInfoService;
    @Autowired
    private IMetaDataService metaDataService;
    @Autowired
    private IMetaVersionService metaVersionService;
    @Autowired
    private IMetaGroupService metaGroupSerice;
    @Autowired
    private IMetaDataInfoDao metaInfoDao;
    @Autowired
    private MetaDeployHandle metaDeployHandle;
    @Autowired
    private MetaDataGroupService metaDataGroupService;
    @Value(value="${feignClient.basedataMgr.name}")
    private String basedataMgrName;
    @Value(value="${feignClient.basedataMgr.path}")
    private String basedataMgrPath;
    @Value(value="${feignClient.basedataMgr.url}")
    private String basedataMgrURL;
    @Value(value="${feignClient.datamodelMgr.name}")
    private String datamodelMgrName;
    @Value(value="${feignClient.datamodelMgr.path}")
    private String datamodelMgrPath;
    @Value(value="${feignClient.datamodelMgr.url}")
    private String datamodelMgrURL;
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;

    @Override
    public void updateByPrimaryKeySelective(MetaInfoEditionDO editionDO) {
        this.metaInfoEditionDao.updateByPrimaryKey(editionDO);
    }

    @Override
    public MetaInfoEditionDO getMetaInfoEdition(MetaInfoEditionDO paramMetaInfoEdition) {
        return (MetaInfoEditionDO)this.metaInfoEditionDao.selectOne(paramMetaInfoEdition);
    }

    @Override
    public MetaInfoDO queryMetaInfo(MetaInfoDO paramInfoDO) {
        return (MetaInfoDO)this.metaInfoDao.selectOne(paramInfoDO);
    }

    @Override
    public void insertInfoEdition(MetaInfoEditionDO metaInfoEditionDO) {
        this.metaInfoEditionDao.insert(metaInfoEditionDO);
    }

    @Override
    public void doPublish(UUID metaInfoId, MetaInfoDO metaInfoDO, MetaInfoEditionDO metaInfoEditionDO) {
        Map<String, String> moduleServerMap = Modules.getModules().stream().collect(Collectors.toMap(ModuleServer::getName, ModuleServer::getTitle));
        Map<String, String> metaTypeMap = Arrays.stream(MetaTypeEnum.values()).collect(Collectors.toMap(MetaTypeEnum::getName, MetaTypeEnum::getTitle));
        String path = this.getPath(moduleServerMap, metaTypeMap, metaInfoEditionDO);
        ArrayList<MetaDataDeployDim> deploys = new ArrayList<MetaDataDeployDim>();
        MetaDataDeployDim deploy = new MetaDataDeployDim();
        deploy.setModuleName(metaInfoDO.getModuleName());
        deploy.setState(MetaState.APPENDED.getValue());
        deploy.setId(metaInfoId);
        deploy.setPath(path);
        deploy.setType("metaData");
        deploys.add(deploy);
        List<MetaGroupEditionDO> groupEditionDOs = this.metaDataGroupService.getGroupEditionList();
        MetaUtils.gatherUnPublishGroup(groupEditionDOs, deploys);
        MetaUtils.sortPublishDatas(deploys);
        long newVersion = VersionManage.getInstance().newVersion(this.metaVersionService);
        HashMap<String, String> deployMap = new HashMap<String, String>(5);
        String userId = ShiroUtil.getUser().getId();
        for (MetaDataDeployDim metaDataDeployDim : deploys) {
            if (deployMap.containsKey(metaDataDeployDim.getId().toString())) continue;
            deployMap.put(metaDataDeployDim.getId().toString(), metaDataDeployDim.getPath());
            try {
                this.metaDeployHandle.doPublish(metaDataDeployDim, userId, newVersion, this.metaInfoService, this.metaGroupSerice);
                if (!"metaData".equals(metaDataDeployDim.getType())) continue;
                this.metaSyncCacheService.pushSyncMsg(metaDataDeployDim.getId(), MetaState.DEPLOYED.getValue());
            }
            catch (MetaCheckException e) {
                log.error(e.getMessage(), e);
            }
        }
        MetaDataVersionDTO dataVersionDO = new MetaDataVersionDTO();
        dataVersionDO.setCreateTime(new Date());
        dataVersionDO.setUserName(ShiroUtil.getUser().getId());
        dataVersionDO.setVersionNo(newVersion);
        this.metaVersionService.addMetaVersionInfo(dataVersionDO);
        metaInfoDO.setRowVersion(Long.valueOf(newVersion));
    }

    @Override
    public com.jiuqi.va.domain.biz.MetaDataDTO createMetaData(MetaInfoDTO data) {
        MetaDataDTO metaDataDTO = new MetaDataDTO();
        metaDataDTO.setId(data.getId());
        metaDataDTO.setDesignData(data.getDesignData());
        this.metaDataService.createMetaData(metaDataDTO);
        com.jiuqi.va.domain.biz.MetaDataDTO dataDTO = new com.jiuqi.va.domain.biz.MetaDataDTO();
        dataDTO.setDatas(data.getDesignData());
        return dataDTO;
    }

    @Override
    public void updateMetaData(MetaInfoDTO metaInfoDTO) {
        MetaDataDTO metaDataDTO = new MetaDataDTO();
        metaDataDTO.setId(metaInfoDTO.getId());
        metaDataDTO.setDesignData(metaInfoDTO.getDesignData());
        this.metaDataService.updateMetaData(metaDataDTO);
    }

    @Override
    public List<VaParamSyncMetaGroupDO> getImportGroups(String metaType, VaParamSyncMainfestDO mainfestDO) {
        List groups = mainfestDO.getGroups();
        if (groups == null || groups.size() == 0) {
            throw new RuntimeException("\u89e3\u6790\u5931\u8d25\uff1a\u65e0groups\u4fe1\u606f");
        }
        List<VaParamSyncMetaGroupDO> result = groups.stream().filter(o -> VaParamSyncModuleEnum.METADATA.getName().equals(o.getMetaType())).collect(Collectors.toList());
        List metaDatas = mainfestDO.getMetaDatas();
        if (metaDatas == null || metaDatas.size() == 0) {
            throw new RuntimeException("\u89e3\u6790\u5931\u8d25\uff1a\u65e0metaDatas\u4fe1\u606f");
        }
        HashMap<String, Set<VaParamSyncMetaGroupDO>> map = new HashMap<String, Set<VaParamSyncMetaGroupDO>>();
        if ("all".equals(metaType)) {
            this.buildGroupMap(metaDatas, MetaTypeEnum.BILL.getName(), map);
            this.buildGroupMap(metaDatas, MetaTypeEnum.BILLLIST.getName(), map);
            this.buildGroupMap(metaDatas, MetaTypeEnum.WORKFLOW.getName(), map);
            this.buildGroups(result, map);
        } else {
            this.buildGroupMap(metaDatas, metaType, map);
            this.buildGroups(result, map);
        }
        return result;
    }

    @Override
    public VaParamSyncResponseDO export(VaParamSyncParamDO param) {
        VaParamSyncResponseDO response = new VaParamSyncResponseDO();
        HashMap<String, String> basedataResponse = new HashMap<String, String>();
        HashMap<String, String> datamodelResponse = new HashMap<String, String>();
        HashMap<String, String> billResponse = new HashMap<String, String>();
        HashMap<String, String> billlistResponse = new HashMap<String, String>();
        HashMap<String, String> workflowResponse = new HashMap<String, String>();
        response.setBasedata(basedataResponse);
        response.setDatamodel(datamodelResponse);
        response.setBill(billResponse);
        response.setBilllist(billlistResponse);
        response.setWorkflow(workflowResponse);
        HashSet<String> exportBasedatas = new HashSet<String>();
        HashSet<String> exportDatamodels = new HashSet<String>();
        HashSet<String> exportBillDefines = new HashSet<String>();
        HashSet<String> exportWorkflows = new HashSet<String>();
        HashSet<String> exportBillLists = new HashSet<String>();
        HashMap<String, DataModelDO> dataModelDefineMap = new HashMap<String, DataModelDO>();
        HashMap<String, BaseDataDefineDO> baseDataDefineMap = new HashMap<String, BaseDataDefineDO>();
        try {
            HashMap<String, String> metaGroupMap = new HashMap<String, String>();
            VaParamSyncMainfestDO mainfest = param.getMainfest();
            List metaDatas = mainfest.getMetaDatas();
            if (metaDatas == null || metaDatas.size() == 0) {
                return null;
            }
            boolean exportRefData = param.getExportRefData() == null ? false : param.getExportRefData() == 1;
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            HashSet<VaParamSyncMetaDataDO> newMetaDatas = new HashSet<VaParamSyncMetaDataDO>();
            LinkedHashSet<VaParamSyncMetaGroupDO> newGroups = new LinkedHashSet<VaParamSyncMetaGroupDO>();
            for (Object metaData : metaDatas) {
                Object plugin2;
                ArrayNode plugins;
                String metaType = metaData.getMetaType();
                String moduleName = metaData.getModuleName();
                String defineName = metaData.getDefineName();
                StringBuilder builder = new StringBuilder();
                builder.append(moduleName);
                builder.append("_");
                if (MetaTypeEnum.BILL.getName().equals(metaType)) {
                    builder.append("B_");
                } else if (MetaTypeEnum.WORKFLOW.getName().equals(metaType)) {
                    builder.append("W_");
                } else if (MetaTypeEnum.BILLLIST.getName().equals(metaType)) {
                    builder.append("L_");
                }
                builder.append(defineName);
                metaGroupMap.put(builder.toString(), metaData.getGroupName());
                MetaInfoDTO metainfo = this.metaInfoService.findMetaData(moduleName, metaType, defineName);
                if (metainfo == null || StringUtils.isEmpty(metainfo.getDesignData())) continue;
                ObjectNode designData = JSONUtil.parseObject((String)metainfo.getDesignData());
                if (MetaTypeEnum.BILL.getName().equals(metaType) && !exportBillDefines.contains(metainfo.getUniqueCode())) {
                    billResponse.put(metainfo.getUniqueCode(), JSONUtil.toJSONString((Object)designData));
                    exportBillDefines.add(metainfo.getUniqueCode());
                } else if (MetaTypeEnum.WORKFLOW.getName().equals(metaType) && !exportWorkflows.contains(metainfo.getUniqueCode())) {
                    workflowResponse.put(metainfo.getUniqueCode(), JSONUtil.toJSONString((Object)designData));
                    exportWorkflows.add(metainfo.getUniqueCode());
                } else {
                    if (!MetaTypeEnum.BILLLIST.getName().equals(metaType) || exportBillLists.contains(metainfo.getUniqueCode())) continue;
                    billlistResponse.put(metainfo.getUniqueCode(), JSONUtil.toJSONString((Object)designData));
                    exportBillLists.add(metainfo.getUniqueCode());
                }
                if (!exportRefData) continue;
                if (MetaTypeEnum.BILL.getName().equals(metaType)) {
                    plugins = designData.withArray("plugins");
                    ArrayList<String> tables = new ArrayList<String>();
                    for (Object plugin2 : plugins) {
                        if (!plugin2.has("tables")) continue;
                        ArrayNode tablesArray = JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)plugin2)).withArray("tables");
                        for (JsonNode table : tablesArray) {
                            tables.add(table.get("name").asText());
                        }
                    }
                    this.exportRef(datamodelResponse, basedataResponse, exportBasedatas, exportDatamodels, dataModelDefineMap, baseDataDefineMap, newMetaDatas, newGroups, (VaParamSyncMetaDataDO)metaData, tables);
                    continue;
                }
                if (!MetaTypeEnum.BILLLIST.getName().equals(metaType)) continue;
                plugins = designData.withArray("plugins");
                String bills = null;
                ArrayList<String> datamodels = new ArrayList<String>();
                plugin2 = plugins.iterator();
                while (plugin2.hasNext()) {
                    JsonNode plugin3 = (JsonNode)plugin2.next();
                    if (plugin3.has("tableName")) {
                        datamodels.add(plugin3.get("tableName").asText());
                    }
                    if (plugin3.has("childTableName")) {
                        datamodels.add(plugin3.get("childTableName").asText());
                    }
                    if (!plugin3.has("bills")) continue;
                    datamodels.add(plugin3.get("bills").asText());
                }
                if (datamodels != null && datamodels.size() > 0) {
                    this.exportRef(datamodelResponse, basedataResponse, exportBasedatas, exportDatamodels, dataModelDefineMap, baseDataDefineMap, newMetaDatas, newGroups, (VaParamSyncMetaDataDO)metaData, datamodels);
                }
                if (StringUtils.isEmpty(bills)) continue;
                String[] billsArr = bills.split(",");
                for (int i = 0; i < billsArr.length; ++i) {
                    String billDefine = billsArr[i];
                    String name = billDefine.substring(moduleName.length() + 3, billDefine.length());
                    metainfo = this.metaInfoService.findMetaData(moduleName, MetaTypeEnum.BILL.getName(), name);
                    if (metainfo == null || StringUtils.isEmpty(metainfo.getDesignData())) continue;
                    String design = metainfo.getDesignData();
                    billResponse.put(metainfo.getUniqueCode(), design);
                    metaGroupMap.put(billDefine, metainfo.getGroupName());
                    designData = JSONUtil.parseObject((String)design);
                    VaParamSyncMetaRelevanceDO relevanceItem = new VaParamSyncMetaRelevanceDO();
                    relevanceItem.setDefineName(metainfo.getName());
                    relevanceItem.setDefineTitle(metainfo.getTitle());
                    relevanceItem.setGroupName(metainfo.getGroupName());
                    relevanceItem.setMetaType(metainfo.getMetaType());
                    relevanceItem.setModuleName(metainfo.getModuleName());
                    relevanceItem.setModelName(metainfo.getModelName());
                    metaData.addRelevanceItem(relevanceItem);
                    if (exportBillDefines.contains(metainfo.getUniqueCode())) continue;
                    VaParamSyncMetaDataDO billMetaData = new VaParamSyncMetaDataDO();
                    billMetaData.setDefineName(metainfo.getName());
                    billMetaData.setDefineTitle(metainfo.getTitle());
                    billMetaData.setGroupName(metainfo.getGroupName());
                    billMetaData.setMetaType(metainfo.getMetaType());
                    billMetaData.setModelName(metainfo.getModelName());
                    billMetaData.setRelevance(new ArrayList());
                    billMetaData.setModuleName(metainfo.getModuleName());
                    newMetaDatas.add(billMetaData);
                    exportBillDefines.add(metainfo.getUniqueCode());
                    plugins = designData.withArray("plugins");
                    ArrayList<String> tables = new ArrayList<String>();
                    for (JsonNode plugin4 : plugins) {
                        if (!plugin4.has("tables")) continue;
                        ArrayNode tablesArray = JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)plugin4)).withArray("tables");
                        for (JsonNode table : tablesArray) {
                            tables.add(table.get("name").asText());
                        }
                    }
                    this.exportRef(datamodelResponse, basedataResponse, exportBasedatas, exportDatamodels, dataModelDefineMap, baseDataDefineMap, newMetaDatas, newGroups, billMetaData, tables);
                }
            }
            metaDatas.addAll(newMetaDatas);
            LinkedHashSet<VaParamSyncMetaGroupDO> metaGroupDOs = new LinkedHashSet<VaParamSyncMetaGroupDO>();
            for (ModuleServer moduleServer : Modules.getModules()) {
                VaParamSyncMetaGroupDO metaGroupDO = new VaParamSyncMetaGroupDO();
                metaGroupDO.setGroupName(moduleServer.getName());
                metaGroupDO.setGroupTitle(moduleServer.getTitle());
                metaGroupDO.setModuleName(moduleServer.getName());
                metaGroupDO.setParentName("");
                metaGroupDO.setMetaType("metadata");
                metaGroupDO.setDefineName(moduleServer.getName());
                metaGroupDOs.add(metaGroupDO);
                if (exportBillDefines.size() > 0) {
                    metaGroupDO = new VaParamSyncMetaGroupDO();
                    metaGroupDO.setGroupName(moduleServer.getName() + "_B");
                    metaGroupDO.setGroupTitle(MetaTypeEnum.BILL.getTitle());
                    metaGroupDO.setModuleName(moduleServer.getName());
                    metaGroupDO.setParentName(moduleServer.getName());
                    metaGroupDO.setMetaType("metadata");
                    metaGroupDO.setDefineName(MetaTypeEnum.BILL.getName());
                    metaGroupDOs.add(metaGroupDO);
                }
                if (exportBillLists.size() > 0) {
                    metaGroupDO = new VaParamSyncMetaGroupDO();
                    metaGroupDO.setGroupName(moduleServer.getName() + "_L");
                    metaGroupDO.setGroupTitle(MetaTypeEnum.BILLLIST.getTitle());
                    metaGroupDO.setModuleName(moduleServer.getName());
                    metaGroupDO.setParentName(moduleServer.getName());
                    metaGroupDO.setMetaType("metadata");
                    metaGroupDO.setDefineName(MetaTypeEnum.BILLLIST.getName());
                    metaGroupDOs.add(metaGroupDO);
                }
                if (exportWorkflows.size() <= 0) continue;
                metaGroupDO = new VaParamSyncMetaGroupDO();
                metaGroupDO.setGroupName(moduleServer.getName() + "_W");
                metaGroupDO.setGroupTitle(MetaTypeEnum.WORKFLOW.getTitle());
                metaGroupDO.setModuleName(moduleServer.getName());
                metaGroupDO.setParentName(moduleServer.getName());
                metaGroupDO.setMetaType("metadata");
                metaGroupDO.setDefineName(MetaTypeEnum.WORKFLOW.getName());
                metaGroupDOs.add(metaGroupDO);
            }
            for (String billdefine : exportBillDefines) {
                this.buildMetaGroup(metaGroupMap, metaGroupDOs, billdefine, MetaTypeEnum.BILL.getName());
            }
            for (String billlist : exportBillLists) {
                this.buildMetaGroup(metaGroupMap, metaGroupDOs, billlist, MetaTypeEnum.BILLLIST.getName());
            }
            for (String workflow : exportWorkflows) {
                this.buildMetaGroup(metaGroupMap, metaGroupDOs, workflow, MetaTypeEnum.WORKFLOW.getName());
            }
            Set<VaParamSyncMetaGroupDO> vaParamSyncMetaGroupDOS = this.listToTree(metaGroupDOs);
            newGroups.addAll(vaParamSyncMetaGroupDOS);
            mainfest.setGroups(new ArrayList(newGroups));
            response.setMainfest(mapper.writeValueAsString((Object)mainfest));
        }
        catch (Exception e) {
            log.error("\u5143\u6570\u636e\u5bfc\u51fa\u5f02\u5e38", e);
        }
        return response;
    }

    private void exportRef(Map<String, String> datamodelResponse, Map<String, String> basedataResponse, Set<String> exportBasedatas, Set<String> exportDatamodels, Map<String, DataModelDO> dataModelDefineMap, Map<String, BaseDataDefineDO> baseDataDefineMap, Set<VaParamSyncMetaDataDO> newMetaDatas, Set<VaParamSyncMetaGroupDO> newGroups, VaParamSyncMetaDataDO metaData, List<String> tables) {
        Map datamodel;
        List groups;
        VaParamSyncMainfestDO mainfestDO;
        String mainfestStr;
        VaParamSyncParamDO exportDataModelParam;
        VaParamSyncMetaRelevanceDO relevanceItem;
        VaParamSyncMainfestDO exportMainfest = new VaParamSyncMainfestDO();
        ArrayList<VaParamSyncMetaDataDO> exportMetaDatas = new ArrayList<VaParamSyncMetaDataDO>();
        HashSet refBasedatas = new HashSet();
        for (int i = 0; i < tables.size(); ++i) {
            List collect;
            List columns;
            String tableName = tables.get(i);
            DataModelDO dataModelDO = dataModelDefineMap.computeIfAbsent(tableName, Key -> {
                DataModelDTO dataModelDTO = new DataModelDTO();
                dataModelDTO.setName(tableName);
                return this.dataModelClient.get(dataModelDTO);
            });
            if (dataModelDO == null) continue;
            relevanceItem = new VaParamSyncMetaRelevanceDO();
            relevanceItem.setDefineName(dataModelDO.getName());
            relevanceItem.setDefineTitle(dataModelDO.getTitle());
            relevanceItem.setGroupName(dataModelDO.getGroupcode());
            relevanceItem.setMetaType("datamodel");
            metaData.addRelevanceItem(relevanceItem);
            if (!exportDatamodels.contains(dataModelDO.getName())) {
                exportDatamodels.add(dataModelDO.getName());
                VaParamSyncMetaDataDO exportMetaData = new VaParamSyncMetaDataDO();
                exportMetaData.setBizType(dataModelDO.getBiztype().toString());
                exportMetaData.setDefineName(dataModelDO.getName());
                exportMetaData.setDefineTitle(dataModelDO.getTitle());
                exportMetaData.setGroupName(dataModelDO.getGroupcode());
                exportMetaData.setMetaType("datamodel");
                exportMetaDatas.add(exportMetaData);
            }
            if ((columns = dataModelDO.getColumns()) == null || columns.size() == 0 || (collect = columns.stream().filter(o -> o.getMappingType() != null && o.getMappingType() == 1).collect(Collectors.toList())) == null || collect.size() == 0) continue;
            refBasedatas.addAll(collect.stream().map(o -> o.getMapping().split("[.]")[0]).collect(Collectors.toSet()));
        }
        if (exportMetaDatas.size() > 0) {
            VaParamSyncFeignClient feignClient = !StringUtils.isEmpty(this.datamodelMgrURL) ? (VaParamSyncFeignClient)FeignUtil.getDynamicClient(VaParamSyncFeignClient.class, (String)(this.datamodelMgrURL + "/dataModel"), (String)"") : (VaParamSyncFeignClient)FeignUtil.getDynamicClient(VaParamSyncFeignClient.class, (String)this.datamodelMgrName, (String)(this.datamodelMgrPath + "/dataModel"));
            exportDataModelParam = new VaParamSyncParamDO();
            exportDataModelParam.setExportRefData(Integer.valueOf(0));
            exportDataModelParam.setParamType(VaParamSyncModuleEnum.DATAMODEL);
            exportMainfest.setMetaDatas(exportMetaDatas);
            exportDataModelParam.setMainfest(exportMainfest);
            VaParamSyncResponseDO exportDataModelRes = feignClient.export(exportDataModelParam);
            if (exportDataModelRes != null && StringUtils.hasText(mainfestStr = exportDataModelRes.getMainfest())) {
                mainfestDO = (VaParamSyncMainfestDO)JSONUtil.parseObject((String)mainfestStr, VaParamSyncMainfestDO.class);
                List dataModelMetaDatas = mainfestDO.getMetaDatas();
                if (dataModelMetaDatas != null && dataModelMetaDatas.size() > 0) {
                    newMetaDatas.addAll(dataModelMetaDatas);
                }
                if ((groups = mainfestDO.getGroups()) != null && groups.size() > 0) {
                    newGroups.addAll(groups);
                }
                if ((datamodel = exportDataModelRes.getDatamodel()) != null) {
                    datamodelResponse.putAll(datamodel);
                }
            }
        }
        if (refBasedatas.size() > 0) {
            exportMetaDatas.clear();
            for (String refBasedata : refBasedatas) {
                BaseDataDefineDO baseDataDefineDO = baseDataDefineMap.computeIfAbsent(refBasedata, Key -> {
                    BaseDataDefineDTO dataDefineDTO = new BaseDataDefineDTO();
                    dataDefineDTO.setName(refBasedata);
                    return this.baseDataDefineClient.get(dataDefineDTO);
                });
                if (baseDataDefineDO == null) continue;
                relevanceItem = new VaParamSyncMetaRelevanceDO();
                relevanceItem.setDefineName(baseDataDefineDO.getName());
                relevanceItem.setDefineTitle(baseDataDefineDO.getTitle());
                relevanceItem.setGroupName(baseDataDefineDO.getGroupname());
                relevanceItem.setMetaType("basedata");
                metaData.addRelevanceItem(relevanceItem);
                if (exportBasedatas.contains(baseDataDefineDO.getName())) continue;
                exportBasedatas.add(baseDataDefineDO.getName());
                VaParamSyncMetaDataDO exportBaseData = new VaParamSyncMetaDataDO();
                exportBaseData.setDefineName(baseDataDefineDO.getName());
                exportBaseData.setDefineTitle(baseDataDefineDO.getTitle());
                exportBaseData.setGroupName(baseDataDefineDO.getGroupname());
                exportBaseData.setMetaType("basedata");
                exportMetaDatas.add(exportBaseData);
            }
            if (exportMetaDatas.size() > 0) {
                VaParamSyncFeignClient feignClient = !StringUtils.isEmpty(this.basedataMgrURL) ? (VaParamSyncFeignClient)FeignUtil.getDynamicClient(VaParamSyncFeignClient.class, (String)(this.basedataMgrURL + "/baseData/define"), (String)"") : (VaParamSyncFeignClient)FeignUtil.getDynamicClient(VaParamSyncFeignClient.class, (String)this.basedataMgrName, (String)(this.basedataMgrPath + "/baseData/define"));
                exportDataModelParam = new VaParamSyncParamDO();
                exportDataModelParam.setExportRefData(Integer.valueOf(0));
                exportDataModelParam.setParamType(VaParamSyncModuleEnum.BASEDATA);
                exportMainfest.setMetaDatas(exportMetaDatas);
                exportDataModelParam.setMainfest(exportMainfest);
                VaParamSyncResponseDO exportBaseDataRes = feignClient.export(exportDataModelParam);
                if (exportBaseDataRes != null && StringUtils.hasText(mainfestStr = exportBaseDataRes.getMainfest())) {
                    mainfestDO = (VaParamSyncMainfestDO)JSONUtil.parseObject((String)mainfestStr, VaParamSyncMainfestDO.class);
                    List baseDataMetaDatas = mainfestDO.getMetaDatas();
                    if (baseDataMetaDatas != null && baseDataMetaDatas.size() > 0) {
                        newMetaDatas.addAll(baseDataMetaDatas);
                    }
                    if ((groups = mainfestDO.getGroups()) != null && groups.size() > 0) {
                        newGroups.addAll(groups);
                    }
                    if ((datamodel = exportBaseDataRes.getBasedata()) != null) {
                        basedataResponse.putAll(datamodel);
                    }
                }
            }
        }
    }

    private void buildGroupMap(List<VaParamSyncMetaDataDO> metaDatas, String metaType, Map<String, Set<VaParamSyncMetaGroupDO>> map) {
        for (VaParamSyncMetaDataDO metaData : metaDatas) {
            List relevances = metaData.getRelevance();
            if (relevances != null) {
                for (VaParamSyncMetaRelevanceDO relevance : relevances) {
                    if (!relevance.getMetaType().equals(metaType)) continue;
                    String defineName = relevance.getDefineName();
                    VaParamSyncMetaGroupDO groupDO = new VaParamSyncMetaGroupDO();
                    groupDO.setGroupTitle(relevance.getDefineTitle());
                    groupDO.setMetaType(relevance.getMetaType());
                    groupDO.setModuleName(relevance.getModuleName());
                    StringBuilder builder = new StringBuilder();
                    builder.append(metaData.getModuleName());
                    builder.append("_");
                    if (MetaTypeEnum.BILL.getName().equals(metaType)) {
                        builder.append("B_");
                    } else if (MetaTypeEnum.WORKFLOW.getName().equals(metaType)) {
                        builder.append("W_");
                    } else if (MetaTypeEnum.BILLLIST.getName().equals(metaType)) {
                        builder.append("L_");
                    }
                    groupDO.setGroupName(VaParamSyncModuleEnum.METADATA.getName() + "_" + builder.toString() + relevance.getDefineName());
                    groupDO.setParentName(builder.toString() + relevance.getDefineName());
                    groupDO.setDefineName(defineName);
                    groupDO.setGroupType(Integer.valueOf(VaParamSyncGroupTypeEnum.DEFINE.getValue()));
                    groupDO.setModelName(relevance.getModelName());
                    map.computeIfAbsent(groupDO.getParentName(), key -> new HashSet()).add(groupDO);
                }
            }
            if (!metaType.equals(metaData.getMetaType())) continue;
            String defineName = metaData.getDefineName();
            StringBuilder builder = new StringBuilder();
            builder.append(metaData.getModuleName());
            builder.append("_");
            if (MetaTypeEnum.BILL.getName().equals(metaType)) {
                builder.append("B_");
            } else if (MetaTypeEnum.WORKFLOW.getName().equals(metaType)) {
                builder.append("W_");
            } else if (MetaTypeEnum.BILLLIST.getName().equals(metaType)) {
                builder.append("L_");
            }
            builder.append(defineName);
            String tmpDefineName = builder.toString();
            String[] split = tmpDefineName.split("_");
            VaParamSyncMetaGroupDO groupDO = new VaParamSyncMetaGroupDO();
            groupDO.setGroupName(VaParamSyncModuleEnum.METADATA.getName() + "_" + tmpDefineName);
            groupDO.setGroupTitle(metaData.getDefineTitle());
            groupDO.setMetaType(metaData.getMetaType());
            groupDO.setModuleName(metaData.getModuleName());
            groupDO.setParentName(split[0] + "_" + split[1] + "_" + metaData.getGroupName());
            groupDO.setDefineName(defineName);
            groupDO.setGroupType(Integer.valueOf(VaParamSyncGroupTypeEnum.DEFINE.getValue()));
            groupDO.setModelName(metaData.getModelName());
            groupDO.setHasRelevance(Integer.valueOf(relevances != null && relevances.size() > 0 ? 1 : 0));
            map.computeIfAbsent(groupDO.getParentName(), key -> new HashSet()).add(groupDO);
        }
    }

    public void buildGroups(List<VaParamSyncMetaGroupDO> groups, Map<String, Set<VaParamSyncMetaGroupDO>> map) {
        for (VaParamSyncMetaGroupDO group : groups) {
            Set<VaParamSyncMetaGroupDO> groupDOs;
            if (group.getChildren() == null || group.getChildren().size() == 0) {
                groupDOs = map.get(group.getGroupName());
                if (groupDOs == null) continue;
                group.setChildren(new ArrayList<VaParamSyncMetaGroupDO>(groupDOs));
                continue;
            }
            groupDOs = map.get(group.getGroupName());
            if (groupDOs != null && group.getChildren() != null) {
                group.getChildren().addAll(0, new ArrayList<VaParamSyncMetaGroupDO>(groupDOs));
            }
            this.buildGroups(group.getChildren(), map);
        }
    }

    private void buildMetaGroup(Map<String, String> metaGroupMap, Set<VaParamSyncMetaGroupDO> metaGroupDOs, String billdefine, String metaType) {
        String[] split = billdefine.split("_");
        String prefix = split[0] + "_" + split[1] + "_";
        String groupName = metaGroupMap.get(billdefine);
        String uniqueCode = prefix + groupName;
        MetaGroupDO groupDO = new MetaGroupDO();
        groupDO.setUniqueCode(uniqueCode);
        MetaGroupDO result = (MetaGroupDO)((Object)this.metaGroupDao.selectOne((Object)groupDO));
        if (result == null) {
            MetaGroupEditionDO groupEditionDO = new MetaGroupEditionDO();
            groupEditionDO.setUniqueCode(uniqueCode);
            MetaGroupEditionDO selectOne = (MetaGroupEditionDO)((Object)this.metaGroupUserDao.selectOne((Object)groupEditionDO));
            if (selectOne == null) {
                return;
            }
            this.getMetaGroups(uniqueCode, metaGroupDOs);
        }
        this.getMetaGroups(uniqueCode, metaGroupDOs);
    }

    private Set<VaParamSyncMetaGroupDO> getMetaGroups(String uniqueCode, Set<VaParamSyncMetaGroupDO> groups) {
        MetaGroupDO groupDO = new MetaGroupDO();
        groupDO.setUniqueCode(uniqueCode);
        MetaGroupDO result = (MetaGroupDO)((Object)this.metaGroupDao.selectOne((Object)groupDO));
        if (result != null) {
            VaParamSyncMetaGroupDO metaGroupDO = new VaParamSyncMetaGroupDO();
            metaGroupDO.setGroupName(result.getUniqueCode());
            metaGroupDO.setGroupTitle(result.getTitle());
            metaGroupDO.setMetaType(result.getMetaType());
            metaGroupDO.setModuleName(result.getModuleName());
            metaGroupDO.setDefineName(result.getName());
            if (!StringUtils.isEmpty(result.getParentName())) {
                String[] split = result.getUniqueCode().split("_");
                String prefix = split[0] + "_" + split[1] + "_";
                String parentUniqueCode = prefix + result.getParentName();
                if ("bill".equals(result.getParentName())) {
                    metaGroupDO.setParentName(split[0] + "_" + split[1]);
                } else {
                    metaGroupDO.setParentName(parentUniqueCode);
                }
                this.getMetaGroups(parentUniqueCode, groups);
            } else {
                String[] split = result.getUniqueCode().split("_");
                String prefix = split[0] + "_" + split[1];
                metaGroupDO.setParentName(prefix);
            }
            groups.add(metaGroupDO);
        } else {
            MetaGroupEditionDO groupEditionDO = new MetaGroupEditionDO();
            groupEditionDO.setUniqueCode(uniqueCode);
            MetaGroupEditionDO selectOne = (MetaGroupEditionDO)((Object)this.metaGroupUserDao.selectOne((Object)groupEditionDO));
            if (selectOne != null) {
                VaParamSyncMetaGroupDO metaGroupDO = new VaParamSyncMetaGroupDO();
                metaGroupDO.setGroupName(selectOne.getUniqueCode());
                metaGroupDO.setGroupTitle(selectOne.getTitle());
                metaGroupDO.setMetaType(selectOne.getMetaType());
                metaGroupDO.setModuleName(selectOne.getModuleName());
                metaGroupDO.setDefineName(selectOne.getName());
                if (!StringUtils.isEmpty(selectOne.getParentName())) {
                    String[] split = selectOne.getUniqueCode().split("_");
                    String prefix = split[0] + "_" + split[1] + "_";
                    String parentUniqueCode = prefix + selectOne.getParentName();
                    if ("bill".equals(selectOne.getParentName())) {
                        metaGroupDO.setParentName(split[0] + "_" + split[1]);
                    } else {
                        metaGroupDO.setParentName(parentUniqueCode);
                    }
                    this.getMetaGroups(parentUniqueCode, groups);
                } else {
                    String[] split = selectOne.getUniqueCode().split("_");
                    String prefix = split[0] + "_" + split[1];
                    metaGroupDO.setParentName(prefix);
                }
                groups.add(metaGroupDO);
            }
        }
        return groups;
    }

    private Set<VaParamSyncMetaGroupDO> listToTree(Set<VaParamSyncMetaGroupDO> groups) {
        LinkedHashSet<VaParamSyncMetaGroupDO> list = new LinkedHashSet<VaParamSyncMetaGroupDO>();
        for (VaParamSyncMetaGroupDO groupDO : groups) {
            if (!StringUtils.isEmpty(groupDO.getParentName())) continue;
            list.add(this.findChildren(groupDO, groups));
        }
        return list;
    }

    private VaParamSyncMetaGroupDO findChildren(VaParamSyncMetaGroupDO metaGroupDO, Set<VaParamSyncMetaGroupDO> groups) {
        for (VaParamSyncMetaGroupDO group : groups) {
            if (!group.getParentName().equals(metaGroupDO.getGroupName())) continue;
            if (metaGroupDO.getChildren() == null) {
                metaGroupDO.setChildren(new ArrayList());
            }
            metaGroupDO.getChildren().add(this.findChildren(group, groups));
        }
        return metaGroupDO;
    }

    private void collectMetaRelevance(Map<String, VaParamSyncMetaDataDO> metaDataMap, VaParamSyncMetaRelevanceDO relevance, Set<VaParamSyncMetaDataDO> bills, Set<VaParamSyncMetaDataDO> datamodels, Set<VaParamSyncMetaDataDO> basedatas) {
        if (metaDataMap == null || metaDataMap.size() == 0 || relevance == null) {
            return;
        }
        VaParamSyncMetaDataDO metaDataDO = metaDataMap.get(relevance.getModuleName() + relevance.getMetaType() + relevance.getDefineName());
        if (metaDataDO == null) {
            return;
        }
        if (metaDataDO.getMetaType().equals("bill")) {
            bills.add(metaDataDO);
        } else if (metaDataDO.getMetaType().equals("basedata")) {
            basedatas.add(metaDataDO);
        } else if (metaDataDO.getMetaType().equals("datamodel")) {
            datamodels.add(metaDataDO);
        }
        List relevances = metaDataDO.getRelevance();
        if (relevances == null || relevances.size() == 0) {
            return;
        }
        for (VaParamSyncMetaRelevanceDO metaRelevanceDO : relevances) {
            this.collectMetaRelevance(metaDataMap, metaRelevanceDO, bills, datamodels, basedatas);
        }
    }

    @Override
    public R importParam(VaParamSyncParamDO paramDO, Map<String, byte[]> fileMap) {
        List collect;
        String requestID = paramDO.getRequestID();
        String redisKey = ShiroUtil.getTenantName() + ":PARAMSYNC:RESULT:" + requestID;
        VaParamSyncResultDO syncResult = new VaParamSyncResultDO();
        HashSet<VaParamSyncModuleEnum> resultRange = new HashSet<VaParamSyncModuleEnum>();
        HashMap resultSet = new HashMap();
        resultRange.add(VaParamSyncModuleEnum.METADATA);
        syncResult.setResultRange(resultRange);
        syncResult.setResultSet(resultSet);
        ArrayList<String> importMetas = new ArrayList<String>();
        ArrayList<Map<String, Object>> sunccess = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> faileds = new ArrayList<Map<String, Object>>();
        Integer exportRefData = paramDO.getExportRefData();
        Integer publish = paramDO.getPublish();
        byte[] mainFestbytes = fileMap.get("mainfest.json");
        if (mainFestbytes == null) {
            return R.error((String)String.format("\u89e3\u538b\u5931\u8d25\uff0c%s\u6587\u4ef6\u4e0d\u5b58\u5728", "mainfest"));
        }
        String mainfestStr = null;
        mainfestStr = new String(mainFestbytes, Charset.forName("UTF-8"));
        VaParamSyncMainfestDO mainfestDO = (VaParamSyncMainfestDO)JSONUtil.parseObject((String)mainfestStr, VaParamSyncMainfestDO.class);
        List importMetaDatas = paramDO.getMainfest().getMetaDatas();
        ArrayList groups = new ArrayList(mainfestDO.getGroups());
        List<VaParamSyncMetaGroupDO> metaGroups = groups.stream().filter(o -> o.getMetaType().equals("metadata")).collect(Collectors.toList());
        HashMap<String, VaParamSyncMetaGroupDO> groupMap = new HashMap<String, VaParamSyncMetaGroupDO>();
        HashMap<String, String> treeMap = new HashMap<String, String>();
        this.buildMetaGroup(metaGroups, groupMap, treeMap);
        if (exportRefData != null && exportRefData == 1) {
            try {
                HashMap<String, String> detail;
                ArrayList list;
                HashMap map;
                HashMap rsMap;
                VaParamSyncResultDO resultDO;
                VaParamSyncMainfestDO syncMainfestDO;
                VaParamSyncParamDO param;
                VaParamSyncFeignClient feignClient;
                VaParamSyncMultipartFile multipartFile;
                ZipOutputStream zipOutputStream;
                ByteArrayOutputStream bos;
                List<String> fileNames;
                VaParamSyncResultDO resultDO2;
                String result;
                collect = mainfestDO.getMetaDatas().stream().filter(o -> {
                    if (StringUtils.isEmpty(o.getModuleName())) {
                        return false;
                    }
                    String key = o.getModuleName() + o.getMetaType() + o.getDefineName();
                    for (VaParamSyncMetaDataDO importMetaData : importMetaDatas) {
                        if (!key.equals(importMetaData.getModuleName() + importMetaData.getMetaType() + importMetaData.getDefineName())) continue;
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
                Map<String, VaParamSyncMetaDataDO> metaDataMap = mainfestDO.getMetaDatas().stream().collect(Collectors.toMap(o -> o.getModuleName() + o.getMetaType() + o.getDefineName(), o -> o));
                HashSet<VaParamSyncMetaDataDO> bills = new HashSet<VaParamSyncMetaDataDO>();
                HashSet<VaParamSyncMetaDataDO> datamodels = new HashSet<VaParamSyncMetaDataDO>();
                HashSet<VaParamSyncMetaDataDO> basedatas = new HashSet<VaParamSyncMetaDataDO>();
                for (VaParamSyncMetaDataDO importMetaData : collect) {
                    List relevances = importMetaData.getRelevance();
                    if (relevances == null || relevances.size() == 0) continue;
                    for (Object relevance : relevances) {
                        this.collectMetaRelevance(metaDataMap, (VaParamSyncMetaRelevanceDO)relevance, bills, datamodels, basedatas);
                    }
                }
                if (datamodels.size() > 0) {
                    resultRange.add(VaParamSyncModuleEnum.DATAMODEL);
                    result = (String)this.stringRedisTemplate.opsForValue().get((Object)redisKey);
                    if (StringUtils.hasText(result)) {
                        resultDO2 = (VaParamSyncResultDO)JSONUtil.parseObject((String)result, VaParamSyncResultDO.class);
                        resultDO2.addResultRange(syncResult.getResultRange());
                        if (resultDO2.getResultSet() != null) {
                            resultDO2.getResultSet().putAll(syncResult.getResultSet());
                        } else {
                            resultDO2.setResultSet(syncResult.getResultSet());
                        }
                        this.stringRedisTemplate.opsForValue().set((Object)redisKey, (Object)JSONUtil.toJSONString((Object)resultDO2), 7200L, TimeUnit.SECONDS);
                    } else {
                        this.stringRedisTemplate.opsForValue().set((Object)redisKey, (Object)JSONUtil.toJSONString((Object)syncResult), 7200L, TimeUnit.SECONDS);
                    }
                    fileNames = this.getFilenames(datamodels);
                    bos = new ByteArrayOutputStream();
                    zipOutputStream = new ZipOutputStream(bos);
                    zipOutputStream.putNextEntry(new ZipEntry("mainfest.json"));
                    zipOutputStream.write(mainFestbytes);
                    for (String fileName : fileNames) {
                        zipOutputStream.putNextEntry(new ZipEntry(fileName));
                        zipOutputStream.write(fileMap.get(fileName));
                    }
                    zipOutputStream.close();
                    multipartFile = new VaParamSyncMultipartFile("multipartFile", UUID.randomUUID().toString(), "multipart/form-data; charset=ISO-8859-1", bos.toByteArray());
                    feignClient = !StringUtils.isEmpty(this.datamodelMgrURL) ? (VaParamSyncFeignClient)FeignUtil.getDynamicClient(VaParamSyncFeignClient.class, (String)(this.datamodelMgrURL + "/dataModel"), (String)"") : (VaParamSyncFeignClient)FeignUtil.getDynamicClient(VaParamSyncFeignClient.class, (String)this.datamodelMgrName, (String)(this.datamodelMgrPath + "/dataModel"));
                    param = new VaParamSyncParamDO();
                    param.setPublish(publish);
                    param.setExportRefData(Integer.valueOf(0));
                    syncMainfestDO = new VaParamSyncMainfestDO();
                    syncMainfestDO.setMetaDatas(new ArrayList<VaParamSyncMetaDataDO>(datamodels));
                    param.setMainfest(syncMainfestDO);
                    param.setRequestID(requestID);
                    try {
                        feignClient.importParam((MultipartFile)multipartFile, JSONUtil.toJSONString((Object)param));
                    }
                    catch (RetryableException e) {
                        log.error("\u5143\u6570\u636e\u5bfc\u5165\u5173\u8054\u6570\u636e\u5efa\u6a21\u5f02\u5e38", e);
                    }
                    catch (Exception e) {
                        result = (String)this.stringRedisTemplate.opsForValue().get((Object)redisKey);
                        resultDO = (VaParamSyncResultDO)JSONUtil.parseObject((String)result, VaParamSyncResultDO.class);
                        resultDO.addResultRange(syncResult.getResultRange());
                        rsMap = new HashMap();
                        map = new HashMap();
                        list = new ArrayList();
                        detail = new HashMap<String, String>();
                        detail.put("type", VaParamSyncModuleEnum.DATAMODEL.getTitle());
                        detail.put("msg", e.getMessage());
                        list.add(detail);
                        map.put("faileds", list);
                        rsMap.put(VaParamSyncModuleEnum.DATAMODEL, map);
                        if (resultDO.getResultSet() != null) {
                            resultDO.getResultSet().putAll(rsMap);
                        } else {
                            resultDO.setResultSet(rsMap);
                        }
                        this.stringRedisTemplate.opsForValue().set((Object)redisKey, (Object)JSONUtil.toJSONString((Object)resultDO), 7200L, TimeUnit.SECONDS);
                        log.error("\u5143\u6570\u636e\u5bfc\u5165\u5173\u8054\u6570\u636e\u5efa\u6a21\u5f02\u5e38", e);
                    }
                }
                if (basedatas.size() > 0) {
                    resultRange.add(VaParamSyncModuleEnum.BASEDATA);
                    result = (String)this.stringRedisTemplate.opsForValue().get((Object)redisKey);
                    if (StringUtils.hasText(result)) {
                        resultDO2 = (VaParamSyncResultDO)JSONUtil.parseObject((String)result, VaParamSyncResultDO.class);
                        resultDO2.addResultRange(syncResult.getResultRange());
                        if (resultDO2.getResultSet() != null) {
                            resultDO2.getResultSet().putAll(syncResult.getResultSet());
                        } else {
                            resultDO2.setResultSet(syncResult.getResultSet());
                        }
                        this.stringRedisTemplate.opsForValue().set((Object)redisKey, (Object)JSONUtil.toJSONString((Object)resultDO2), 7200L, TimeUnit.SECONDS);
                    } else {
                        this.stringRedisTemplate.opsForValue().set((Object)redisKey, (Object)JSONUtil.toJSONString((Object)syncResult), 7200L, TimeUnit.SECONDS);
                    }
                    fileNames = this.getFilenames(basedatas);
                    bos = new ByteArrayOutputStream();
                    zipOutputStream = new ZipOutputStream(bos);
                    zipOutputStream.putNextEntry(new ZipEntry("mainfest.json"));
                    zipOutputStream.write(mainFestbytes);
                    for (String fileName : fileNames) {
                        zipOutputStream.putNextEntry(new ZipEntry(fileName));
                        zipOutputStream.write(fileMap.get(fileName));
                    }
                    zipOutputStream.close();
                    multipartFile = new VaParamSyncMultipartFile("multipartFile", UUID.randomUUID().toString(), "multipart/form-data; charset=ISO-8859-1", bos.toByteArray());
                    feignClient = !StringUtils.isEmpty(this.basedataMgrURL) ? (VaParamSyncFeignClient)FeignUtil.getDynamicClient(VaParamSyncFeignClient.class, (String)(this.basedataMgrURL + "/baseData/define"), (String)"") : (VaParamSyncFeignClient)FeignUtil.getDynamicClient(VaParamSyncFeignClient.class, (String)this.basedataMgrName, (String)(this.basedataMgrPath + "/baseData/define"));
                    param = new VaParamSyncParamDO();
                    param.setPublish(publish);
                    param.setExportRefData(Integer.valueOf(0));
                    param.setRequestID(requestID);
                    syncMainfestDO = new VaParamSyncMainfestDO();
                    syncMainfestDO.setMetaDatas(new ArrayList<VaParamSyncMetaDataDO>(basedatas));
                    param.setMainfest(syncMainfestDO);
                    try {
                        feignClient.importParam((MultipartFile)multipartFile, JSONUtil.toJSONString((Object)param));
                    }
                    catch (RetryableException e) {
                        log.error("\u5143\u6570\u636e\u5bfc\u5165\u5173\u8054\u57fa\u7840\u6570\u636e\u5f02\u5e38", e);
                    }
                    catch (Exception e) {
                        result = (String)this.stringRedisTemplate.opsForValue().get((Object)redisKey);
                        resultDO = (VaParamSyncResultDO)JSONUtil.parseObject((String)result, VaParamSyncResultDO.class);
                        resultDO.addResultRange(syncResult.getResultRange());
                        rsMap = new HashMap();
                        map = new HashMap();
                        list = new ArrayList();
                        detail = new HashMap();
                        detail.put("type", VaParamSyncModuleEnum.BASEDATA.getTitle());
                        detail.put("msg", e.getMessage());
                        list.add(detail);
                        map.put("faileds", list);
                        rsMap.put(VaParamSyncModuleEnum.BASEDATA, map);
                        if (resultDO.getResultSet() != null) {
                            resultDO.getResultSet().putAll(rsMap);
                        } else {
                            resultDO.setResultSet(rsMap);
                        }
                        this.stringRedisTemplate.opsForValue().set((Object)redisKey, (Object)JSONUtil.toJSONString((Object)resultDO), 7200L, TimeUnit.SECONDS);
                        log.error("\u5143\u6570\u636e\u5bfc\u5165\u5173\u8054\u57fa\u7840\u6570\u636e\u5f02\u5e38", e);
                    }
                }
                if (bills.size() > 0) {
                    this.importMetaData(bills, fileMap, publish, sunccess, faileds, importMetas, groupMap, treeMap);
                }
                this.importMetaData(new HashSet<VaParamSyncMetaDataDO>(collect), fileMap, publish, sunccess, faileds, importMetas, groupMap, treeMap);
                HashMap<String, ArrayList<Map<String, Object>>> resultMap = new HashMap<String, ArrayList<Map<String, Object>>>();
                resultMap.put("success", sunccess);
                resultMap.put("faileds", faileds);
                resultSet.put(VaParamSyncModuleEnum.METADATA, resultMap);
            }
            catch (Exception e) {
                log.error("\u5143\u6570\u636e\u5bfc\u5165\u5f02\u5e38", e);
                return R.error((String)e.getMessage());
            }
        }
        try {
            collect = mainfestDO.getMetaDatas().stream().filter(o -> {
                if (StringUtils.isEmpty(o.getModuleName())) {
                    return false;
                }
                String key = o.getModuleName() + o.getMetaType() + o.getDefineName();
                for (VaParamSyncMetaDataDO importMetaData : importMetaDatas) {
                    if (!key.equals(importMetaData.getModuleName() + importMetaData.getMetaType() + importMetaData.getDefineName())) continue;
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            this.importMetaData(new HashSet<VaParamSyncMetaDataDO>(collect), fileMap, publish, sunccess, faileds, importMetas, groupMap, treeMap);
            HashMap<String, ArrayList<Map<String, Object>>> r = new HashMap<String, ArrayList<Map<String, Object>>>();
            r.put("success", sunccess);
            r.put("faileds", faileds);
            resultSet.put(VaParamSyncModuleEnum.METADATA, r);
        }
        catch (Exception e) {
            log.error("\u5143\u6570\u636e\u5bfc\u5165\u5f02\u5e38", e);
            return R.error((String)e.getMessage());
        }
        R r = R.ok();
        r.put("success", sunccess);
        r.put("faileds", faileds);
        String result = (String)this.stringRedisTemplate.opsForValue().get((Object)redisKey);
        if (StringUtils.hasText(result)) {
            VaParamSyncResultDO resultDO = (VaParamSyncResultDO)JSONUtil.parseObject((String)result, VaParamSyncResultDO.class);
            resultDO.addResultRange(syncResult.getResultRange());
            if (resultDO.getResultSet() != null) {
                resultDO.getResultSet().putAll(syncResult.getResultSet());
            } else {
                resultDO.setResultSet(syncResult.getResultSet());
            }
            this.stringRedisTemplate.opsForValue().set((Object)redisKey, (Object)JSONUtil.toJSONString((Object)resultDO), 7200L, TimeUnit.SECONDS);
        } else {
            this.stringRedisTemplate.opsForValue().set((Object)redisKey, (Object)JSONUtil.toJSONString((Object)syncResult), 7200L, TimeUnit.SECONDS);
        }
        return r;
    }

    private List<String> getFilenames(Set<VaParamSyncMetaDataDO> metaDatas) {
        ArrayList<String> fileNames = new ArrayList<String>();
        for (VaParamSyncMetaDataDO datamodel : metaDatas) {
            String metaType = datamodel.getMetaType();
            String defineName = datamodel.getDefineName();
            fileNames.add(metaType + "/" + defineName + ".json");
        }
        return fileNames;
    }

    private String importMetaGroup(String moduleName, String metaType, String defineName, String groupName, Map<String, VaParamSyncMetaGroupDO> groupMap, Map<String, String> treeMap) {
        MetaInfoDTO findMeta = this.metaInfoService.findMeta(moduleName, metaType, defineName);
        if (findMeta == null) {
            VaParamSyncMetaGroupDO importGroup;
            MetaGroupDO groupDO;
            ArrayList<VaParamSyncMetaGroupDO> importGroups = new ArrayList<VaParamSyncMetaGroupDO>();
            this.collectImportGroups(groupName, importGroups, groupMap, treeMap);
            Iterator iterator = importGroups.iterator();
            while (iterator.hasNext() && (groupDO = this.metaGroupService.findGroup(moduleName, (importGroup = (VaParamSyncMetaGroupDO)iterator.next()).getMetaType(), importGroup.getDefineName())) == null) {
                MetaGroupDTO metaGroup = new MetaGroupDTO();
                metaGroup.setName(importGroup.getDefineName());
                metaGroup.setModuleName(moduleName);
                metaGroup.setMetaType(importGroup.getMetaType());
                metaGroup.setTitle(importGroup.getGroupTitle());
                if (!StringUtils.isEmpty(importGroup.getParentName())) {
                    VaParamSyncMetaGroupDO parent = groupMap.get(importGroup.getParentName());
                    if (parent.getMetaType().equals("metadata")) {
                        metaGroup.setParentName("");
                    } else {
                        metaGroup.setParentName(parent.getDefineName());
                    }
                } else {
                    metaGroup.setParentName("");
                }
                this.metaGroupService.createGroup(ShiroUtil.getUser().getId().toString(), metaGroup);
            }
            return ((VaParamSyncMetaGroupDO)importGroups.get(0)).getDefineName();
        }
        return findMeta.getGroupName();
    }

    private void importMetaData(Set<VaParamSyncMetaDataDO> impMetaDatas, Map<String, byte[]> fileMap, Integer publish, List<Map<String, Object>> sunccess, List<Map<String, Object>> faileds, List<String> importMetas, Map<String, VaParamSyncMetaGroupDO> groupMap, Map<String, String> treeMap) {
        Map<String, String> moduleServerMap = Modules.getModules().stream().collect(Collectors.toMap(ModuleServer::getName, ModuleServer::getTitle));
        Map<String, String> metaTypeMap = Arrays.asList(MetaTypeEnum.values()).stream().collect(Collectors.toMap(MetaTypeEnum::getName, MetaTypeEnum::getTitle));
        for (VaParamSyncMetaDataDO impMetaData : impMetaDatas) {
            String path = null;
            VaParamSyncOptEnum operation = VaParamSyncOptEnum.ADD;
            UUID metaInfoId = null;
            boolean importResult = true;
            try {
                String metaType = impMetaData.getMetaType();
                String moduleName = impMetaData.getModuleName();
                String defineName = impMetaData.getDefineName();
                String groupName = impMetaData.getGroupName();
                StringBuilder builder = new StringBuilder();
                builder.append(moduleName);
                builder.append("_");
                if (MetaTypeEnum.BILL.getName().equals(metaType)) {
                    builder.append("B_");
                } else if (MetaTypeEnum.WORKFLOW.getName().equals(metaType)) {
                    builder.append("W_");
                } else if (MetaTypeEnum.BILLLIST.getName().equals(metaType)) {
                    builder.append("L_");
                }
                String uniqueCode = builder.toString() + defineName;
                if (importMetas.contains(uniqueCode)) continue;
                importMetas.add(uniqueCode);
                String fileName = metaType + "/" + builder.toString() + defineName + ".json";
                byte[] bs = fileMap.get(fileName);
                String designData = null;
                if (bs != null) {
                    designData = new String(bs, Charset.forName("UTF-8"));
                }
                String curGroupName = this.importMetaGroup(moduleName, metaType, impMetaData.getDefineName(), builder.toString() + groupName, groupMap, treeMap);
                MetaInfoEditionDO paramMetaInfoEdition = new MetaInfoEditionDO();
                paramMetaInfoEdition.setModuleName(moduleName);
                paramMetaInfoEdition.setMetaType(impMetaData.getMetaType());
                paramMetaInfoEdition.setName(impMetaData.getDefineName());
                paramMetaInfoEdition.setUserName(ShiroUtil.getUser().getId().toString());
                MetaInfoEditionDO resultMetaInfoEdition = (MetaInfoEditionDO)this.metaInfoEditionDao.selectOne(paramMetaInfoEdition);
                if (resultMetaInfoEdition == null) {
                    MetaDataDTO data = new MetaDataDTO();
                    data.setId(UUID.randomUUID());
                    if (StringUtils.isEmpty(designData)) {
                        com.jiuqi.va.domain.biz.MetaDataDTO dataDTO = new com.jiuqi.va.domain.biz.MetaDataDTO();
                        dataDTO.setMetaType(impMetaData.getMetaType());
                        dataDTO.setModelName(impMetaData.getModelName());
                        dataDTO.setModule(impMetaData.getModuleName());
                        dataDTO.setUniqueCode(MetaUtils.buildUniqueCode(impMetaData.getModuleName(), impMetaData.getMetaType(), impMetaData.getDefineName()));
                        dataDTO = this.metaBaseInfoService.gatherMetaData(dataDTO);
                        data.setDesignData(dataDTO.getDatas());
                    } else {
                        data.setDesignData(designData);
                    }
                    MetaInfoEditionDO metaInfoEditionDO = new MetaInfoEditionDO();
                    metaInfoEditionDO.setId(data.getId());
                    metaInfoEditionDO.setGroupName(curGroupName);
                    metaInfoEditionDO.setMetaState(Integer.valueOf(MetaState.APPENDED.getValue()));
                    metaInfoEditionDO.setMetaType(impMetaData.getMetaType());
                    metaInfoEditionDO.setModelName(impMetaData.getModelName());
                    metaInfoEditionDO.setModuleName(impMetaData.getModuleName());
                    metaInfoEditionDO.setName(impMetaData.getDefineName());
                    metaInfoEditionDO.setTitle(impMetaData.getDefineTitle());
                    metaInfoEditionDO.setUserName(ShiroUtil.getUser().getId().toString());
                    metaInfoEditionDO.setOrgVersion(0L);
                    metaInfoEditionDO.setVersionNO(Long.valueOf(VersionManage.getInstance().newVersion(this.metaVersionService)));
                    metaInfoEditionDO.setRowVersion(metaInfoEditionDO.getVersionNO());
                    metaInfoEditionDO.setUniqueCode(MetaUtils.buildUniqueCode(impMetaData.getModuleName(), impMetaData.getMetaType(), impMetaData.getDefineName()));
                    path = this.getPath(moduleServerMap, metaTypeMap, metaInfoEditionDO);
                    MetaInfoDO paramInfoDO = new MetaInfoDO();
                    paramInfoDO.setModuleName(moduleName);
                    paramInfoDO.setMetaType(impMetaData.getMetaType());
                    paramInfoDO.setName(impMetaData.getDefineName());
                    MetaInfoDO resultMetaInfo = (MetaInfoDO)this.metaInfoDao.selectOne(paramInfoDO);
                    if (resultMetaInfo == null) {
                        this.metaDataService.createMetaData(data);
                        this.metaInfoEditionDao.insert(metaInfoEditionDO);
                        operation = VaParamSyncOptEnum.ADD;
                    } else {
                        MetaDataDTO oldMetaData = this.metaDataService.getMetaDataById(resultMetaInfo.getId());
                        String oldDesignData = oldMetaData.getDesignData();
                        if (oldDesignData.equals(designData)) {
                            operation = VaParamSyncOptEnum.UNCHANGE;
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("path", path);
                            map.put("type", VaParamSyncModuleEnum.METADATA.getTitle());
                            map.put("msg", "\u5bfc\u5165\u6210\u529f");
                            map.put("operation", operation.getValue());
                            sunccess.add(map);
                            continue;
                        }
                        this.metaDataService.createMetaData(data);
                        metaInfoEditionDO.setMetaState(Integer.valueOf(MetaState.MODIFIED.getValue()));
                        metaInfoEditionDO.setOrgVersion(resultMetaInfo.getVersionNO().longValue());
                        this.metaInfoEditionDao.insert(metaInfoEditionDO);
                        operation = VaParamSyncOptEnum.MODIFY;
                    }
                    metaInfoId = metaInfoEditionDO.getId();
                } else {
                    MetaInfoEditionDO editionDO = new MetaInfoEditionDO();
                    editionDO.setId(resultMetaInfoEdition.getId());
                    editionDO.setMetaState(Integer.valueOf(MetaState.MODIFIED.getValue()));
                    editionDO.setGroupName(curGroupName);
                    editionDO.setMetaType(impMetaData.getMetaType());
                    editionDO.setModelName(impMetaData.getModelName());
                    editionDO.setModuleName(impMetaData.getModuleName());
                    editionDO.setName(impMetaData.getDefineName());
                    editionDO.setTitle(impMetaData.getDefineTitle());
                    editionDO.setUniqueCode(resultMetaInfoEdition.getUniqueCode());
                    editionDO.setUserName(ShiroUtil.getUser().getId().toString());
                    editionDO.setVersionNO(Long.valueOf(VersionManage.getInstance().newVersion(this.metaVersionService)));
                    editionDO.setRowVersion(editionDO.getVersionNO());
                    path = this.getPath(moduleServerMap, metaTypeMap, editionDO);
                    MetaDataDTO oldMetaData = this.metaDataService.getMetaDataById(resultMetaInfoEdition.getId());
                    String oldDesignData = oldMetaData.getDesignData();
                    if (oldDesignData.equals(designData) && (publish == null || publish != 1)) {
                        operation = VaParamSyncOptEnum.UNCHANGE;
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("path", path);
                        map.put("type", VaParamSyncModuleEnum.METADATA.getTitle());
                        map.put("msg", "\u5bfc\u5165\u6210\u529f");
                        map.put("operation", operation.getValue());
                        sunccess.add(map);
                        continue;
                    }
                    operation = VaParamSyncOptEnum.MODIFY;
                    MetaDataDTO data = new MetaDataDTO();
                    data.setDesignData(designData);
                    data.setId(resultMetaInfoEdition.getId());
                    this.metaDataService.updateMetaData(data);
                    this.metaInfoEditionDao.updateByPrimaryKeySelective(editionDO);
                    metaInfoId = resultMetaInfoEdition.getId();
                }
                if (publish == null || publish != 1) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("path", path);
                    map.put("type", VaParamSyncModuleEnum.METADATA.getTitle());
                    map.put("msg", "\u5bfc\u5165\u6210\u529f");
                    map.put("operation", operation.getValue());
                    sunccess.add(map);
                }
            }
            catch (Exception e) {
                importResult = false;
                if (publish == null || publish != 1) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("path", path);
                    map.put("type", VaParamSyncModuleEnum.METADATA.getTitle());
                    map.put("msg", "\u5bfc\u5165\u5931\u8d25\uff1a" + e.getMessage());
                    faileds.add(map);
                }
                log.error("\u5143\u6570\u636e\u5bfc\u5165\u5931\u8d25", e);
            }
            if (!importResult || publish == null || publish != 1) continue;
            try {
                ArrayList<MetaDataDeployDim> deploys = new ArrayList<MetaDataDeployDim>();
                MetaDataDeployDim deploy = new MetaDataDeployDim();
                deploy.setModuleName(impMetaData.getModuleName());
                deploy.setState(MetaState.APPENDED.getValue());
                deploy.setId(metaInfoId);
                deploy.setPath(path);
                deploys.add(deploy);
                List<MetaGroupEditionDO> groupEditionDOs = this.metaDataGroupService.getGroupEditionList();
                MetaUtils.gatherUnPublishGroup(groupEditionDOs, deploys);
                MetaUtils.sortPublishDatas(deploys);
                long newVersion = VersionManage.getInstance().newVersion(this.metaVersionService);
                for (MetaDataDeployDim metaDataDeployDim : deploys) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("path", metaDataDeployDim.getPath());
                    map.put("type", VaParamSyncModuleEnum.METADATA.getTitle());
                    map.put("operation", operation.getValue());
                    try {
                        this.metaDeployHandle.doPublish(metaDataDeployDim, ShiroUtil.getUser().getId().toString(), newVersion, this.metaInfoService, this.metaGroupSerice);
                        if ("metaData".equals(metaDataDeployDim.getType())) {
                            this.metaSyncCacheService.pushSyncMsg(metaDataDeployDim.getId(), MetaState.DEPLOYED.getValue());
                        }
                        map.put("msg", "\u5bfc\u5165\u6210\u529f");
                        sunccess.add(map);
                    }
                    catch (MetaCheckException e) {
                        StringBuilder errorMsg = new StringBuilder();
                        ObjectMapper mapper = new ObjectMapper();
                        List pluginCheckResultVOS = (List)mapper.convertValue(e.getPluginCheckResultVOS(), (TypeReference)new TypeReference<List<PluginCheckResultVO>>(){});
                        for (PluginCheckResultVO pluginCheckResultVO : pluginCheckResultVOS) {
                            List checkResults = pluginCheckResultVO.getCheckResults();
                            if (checkResults == null) continue;
                            for (PluginCheckResultDTO checkResult : checkResults) {
                                errorMsg.append(checkResult.getMessage()).append(";");
                            }
                        }
                        map.put("msg", "\u53d1\u5e03\u5931\u8d25\uff1a" + errorMsg);
                        faileds.add(map);
                    }
                    catch (Exception e) {
                        map.put("msg", "\u53d1\u5e03\u5931\u8d25\uff1a" + e.getMessage());
                        faileds.add(map);
                    }
                }
                MetaDataVersionDTO dataVersionDO = new MetaDataVersionDTO();
                dataVersionDO.setCreateTime(new Date());
                dataVersionDO.setUserName(ShiroUtil.getUser().getId().toString());
                dataVersionDO.setVersionNo(newVersion);
                this.metaVersionService.addMetaVersionInfo(dataVersionDO);
            }
            catch (Exception e) {
                log.error("\u5143\u6570\u636e\u5bfc\u5165\u53d1\u5e03\u5931\u8d25", e);
            }
        }
    }

    private String getPath(Map<String, String> moduleServerMap, Map<String, String> metaTypeMap, MetaInfoEditionDO metaInfoEditionDO) {
        ArrayList<MetaGroupDO> groupDOs = new ArrayList<MetaGroupDO>();
        this.gatherGroups(metaInfoEditionDO.getModuleName(), metaInfoEditionDO.getMetaType(), metaInfoEditionDO.getGroupName(), groupDOs);
        Collections.reverse(groupDOs);
        StringBuilder path = new StringBuilder();
        path.append(moduleServerMap.get(metaInfoEditionDO.getModuleName()) + "(" + metaInfoEditionDO.getModuleName() + ")");
        path.append("/");
        path.append(metaTypeMap.get(metaInfoEditionDO.getMetaType()) + "(" + metaInfoEditionDO.getMetaType() + ")");
        path.append("/");
        for (MetaGroupDO groupDO : groupDOs) {
            path.append(groupDO.getTitle() + "(" + groupDO.getName() + ")");
            path.append("/");
        }
        path.append(metaInfoEditionDO.getTitle() + "(" + metaInfoEditionDO.getName() + ")");
        return path.toString();
    }

    private void gatherGroups(String moduleName, String metaType, String groupName, List<MetaGroupDO> groups) {
        if (StringUtils.isEmpty(groupName)) {
            return;
        }
        MetaGroupDO group = this.metaGroupService.findGroup(moduleName, metaType, groupName);
        if (group != null) {
            groups.add(group);
            if (group.getParentName() != null) {
                this.gatherGroups(moduleName, metaType, group.getParentName(), groups);
            }
        }
    }

    private void collectImportGroups(String uniqueCode, List<VaParamSyncMetaGroupDO> groups, Map<String, VaParamSyncMetaGroupDO> groupMap, Map<String, String> treeMap) {
        String curGroup = treeMap.get(uniqueCode);
        VaParamSyncMetaGroupDO group = groupMap.get(uniqueCode);
        if (group != null && !group.getMetaType().equals("metadata")) {
            groups.add(group);
            VaParamSyncMetaGroupDO parentGroup = groupMap.get(curGroup);
            this.collectImportGroups(parentGroup.getGroupName(), groups, groupMap, treeMap);
        }
    }

    private void buildMetaGroup(List<VaParamSyncMetaGroupDO> groups, Map<String, VaParamSyncMetaGroupDO> groupMap, Map<String, String> treeMap) {
        for (VaParamSyncMetaGroupDO group : groups) {
            groupMap.put(group.getGroupName(), group);
            treeMap.put(group.getGroupName(), group.getParentName());
            if (group.getChildren() == null || group.getChildren().size() <= 0) continue;
            ArrayList<VaParamSyncMetaGroupDO> children = new ArrayList<VaParamSyncMetaGroupDO>(group.getChildren());
            this.buildMetaGroup(children, groupMap, treeMap);
            group.setChildren(null);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.DeployDTO
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.feign.client.BizClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.common.utils.ModulesServerProvider;
import com.jiuqi.va.bizmeta.dao.IMetaDataDepInfoVersionDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignVersionDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoVersionDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataVersionDao;
import com.jiuqi.va.bizmeta.dao.IMetaVersionManageDao;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataEditionDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.bizmeta.domain.metaupdate.MetaDataUpdateDTO;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDO;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDTO;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaVersionManageDTO;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.exception.MetaCheckException;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.IMetaVersionService;
import com.jiuqi.va.bizmeta.service.impl.MetaUpdateHandle;
import com.jiuqi.va.bizmeta.service.impl.help.MetaDataInfoService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.domain.biz.DeployDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.feign.client.BizClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class MetaVersionService
implements IMetaVersionService {
    private static final Logger logger = LoggerFactory.getLogger(MetaVersionService.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private IMetaDataVersionDao metaDataVersionDao;
    @Autowired
    private IMetaDataDepInfoVersionDao metaDataDepInfoVersionDao;
    @Autowired
    private IMetaDataInfoVersionDao metaInfoVersionDao;
    @Autowired
    private IMetaDataDesignVersionDao metaDataDesignVersionDao;
    @Autowired
    private IMetaDataDesignDao metaDataDesignDao;
    @Autowired
    private IMetaDataInfoUserDao metaDataInfoUserDao;
    @Autowired
    private IMetaDataDesignUserDao metaDataDesignUserDao;
    @Autowired
    private IMetaDataInfoDao metaDataInfoDao;
    @Autowired
    private MetaUpdateHandle metaUpdateHandle;
    @Autowired
    private IMetaVersionManageDao metaVersionManageDao;
    @Autowired
    private IMetaDataService metaDataService;
    @Autowired
    private MetaDataInfoService metaDataInfoService;
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;
    private static final String EXCEPTION_MSG = "\u66f4\u65b0\u6570\u636e\u5df2\u53d1\u751f\u6539\u53d8";
    private static final String FIELD_ID = "id";
    private static final String FIELD_MSG = "msg";

    @Override
    public long getMaxVersion(MetaDataVersionDTO dataVersionDTO) {
        Long version = this.metaDataVersionDao.selectMaxVersion(dataVersionDTO);
        if (version == null) {
            return 0L;
        }
        return version;
    }

    @Override
    public MetaDataVersionDTO addMetaVersionInfo(MetaDataVersionDTO dataVersionDTO) {
        MetaDataVersionDO dataVersionDO = new MetaDataVersionDO();
        dataVersionDO.setCreateTime(new Date());
        dataVersionDO.setInfo(dataVersionDTO.getInfo());
        dataVersionDO.setUserName(dataVersionDTO.getUserName());
        dataVersionDO.setVersionNo(dataVersionDTO.getVersionNo());
        this.metaDataVersionDao.insert((Object)dataVersionDO);
        return dataVersionDTO;
    }

    @Override
    public R updatePublished(MetaDataDTO dataDTO, MetaInfoDTO infoDTO) {
        ModuleServer itemServer = ModulesServerProvider.getModuleServer(infoDTO.getModuleName(), "workflow");
        BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)itemServer.getServer(), (String)itemServer.getRealPath());
        DeployDTO deployDTO = new DeployDTO();
        deployDTO.setUniqueCode(infoDTO.getUniqueCode());
        deployDTO.setVersion(infoDTO.getVersionNO());
        deployDTO.setDatas(new String(dataDTO.getDesignData()));
        R r = bizClient.updatePublished(deployDTO);
        if (0 != (Integer)r.get((Object)"code")) {
            throw new RuntimeException((String)r.get((Object)FIELD_MSG));
        }
        return r;
    }

    @Override
    public List<MetaInfoDTO> getMetaInfoAllByUniqueCode(String uniqueCode) {
        MetaInfoHistoryDO metaInfoHistoryDO = new MetaInfoHistoryDO();
        metaInfoHistoryDO.setUniqueCode(uniqueCode);
        List<MetaInfoHistoryDO> metaInfoHistoryDOList = this.metaDataDepInfoVersionDao.getMetaInfoAllByUniqueCode(metaInfoHistoryDO);
        if (metaInfoHistoryDOList == null) {
            return null;
        }
        ArrayList<MetaInfoDTO> infoDTOs = new ArrayList<MetaInfoDTO>();
        for (MetaInfoHistoryDO infoHistoryDO : metaInfoHistoryDOList) {
            MetaInfoDTO infoDTO = new MetaInfoDTO();
            infoDTO.setId(infoHistoryDO.getId());
            infoDTO.setModelName(infoHistoryDO.getModelName());
            infoDTO.setName(infoHistoryDO.getName());
            infoDTO.setTitle(infoHistoryDO.getTitle());
            infoDTO.setVersionNO(infoHistoryDO.getVersionNO());
            infoDTO.setGroupName(infoHistoryDO.getGroupName());
            infoDTO.setMetaType(infoHistoryDO.getMetaType());
            infoDTO.setModuleName(infoHistoryDO.getModuleName());
            infoDTO.setInfo(infoHistoryDO.getInfo());
            infoDTOs.add(infoDTO);
        }
        return infoDTOs;
    }

    @Override
    public R gatherMetaInfoVersionNos(TenantDO param) {
        String metaType = (String)param.getExtInfo("metaType");
        String defineCode = (String)param.getExtInfo("defineCode");
        List<MetaInfoDTO> metaInfoDTOs = this.getMetaInfoAllByUniqueCode(defineCode);
        ModuleServer itemServer = ModulesServerProvider.getModuleServer("*", metaType);
        BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)itemServer.getServer(), (String)itemServer.getRealPath());
        R r = bizClient.gatherMetaVersionRelation(param);
        if ((Integer)r.get((Object)"code") == 1) {
            return r;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList resultList = new ArrayList();
        HashMap<String, Object> result = null;
        for (MetaInfoDTO metaInfoDTO : metaInfoDTOs) {
            result = new HashMap<String, Object>();
            result.put(FIELD_ID, metaInfoDTO.getId());
            result.put("metaVersion", metaInfoDTO.getVersionNO());
            result.put("rowVersion", metaInfoDTO.getRowVersion());
            result.put("depInfo", metaInfoDTO.getInfo());
            Date date = new Date(metaInfoDTO.getVersionNO());
            result.put("date", sdf.format(date));
            List relations = (List)r.get((Object)"relations");
            for (Map relation : relations) {
                if (!metaInfoDTO.getVersionNO().equals(relation.get("METAVERSION"))) continue;
                result.put("version", relation.get("WORKFLOWDEFINEVERSION"));
            }
            resultList.add(result);
        }
        r.put("versions", resultList);
        return r;
    }

    @Override
    public MetaDataDTO getDesignDataById(TenantDO param) {
        UUID id = UUID.fromString((String)param.getExtInfo(FIELD_ID));
        MetaDataHistoryDO dataHistoryDO = new MetaDataHistoryDO();
        dataHistoryDO.setId(id);
        MetaDataHistoryDO metaData = (MetaDataHistoryDO)((Object)this.metaDataDesignVersionDao.selectOne((Object)dataHistoryDO));
        if (metaData == null) {
            return null;
        }
        MetaDataDTO dataDTO = new MetaDataDTO();
        dataDTO.setDesignData(metaData.getDesignData());
        dataDTO.setId(id);
        return dataDTO;
    }

    @Override
    @Transactional
    public void updatePublished(UUID dataId, MetaDataDTO dataDTO) throws Exception {
        String tenantName = ShiroUtil.getTenantName();
        MetaDataDO metaDataDO = new MetaDataDO();
        metaDataDO.setId(dataId);
        MetaDataHistoryDO metaDataHistoryDO = null;
        MetaDataDO selectMetaData = (MetaDataDO)((Object)this.metaDataDesignDao.selectOne((Object)metaDataDO));
        MetaInfoHistoryDO metaInfoHistoryDO = new MetaInfoHistoryDO();
        metaInfoHistoryDO.setId(dataId);
        metaInfoHistoryDO = (MetaInfoHistoryDO)((Object)this.metaInfoVersionDao.selectOne((Object)metaInfoHistoryDO));
        this.metaSyncCacheService.handleStatusCache(metaInfoHistoryDO.getUniqueCode(), metaInfoHistoryDO.getVersionNO(), 0, false);
        if (MetaTypeEnum.WORKFLOW.getName().equals(metaInfoHistoryDO.getMetaType())) {
            MetaInfoDTO metaInfoDTO = new MetaInfoDTO();
            metaInfoDTO.setUniqueCode(metaInfoHistoryDO.getUniqueCode());
            metaInfoDTO.setVersionNO(metaInfoHistoryDO.getVersionNO());
            metaInfoDTO.setMetaType(metaInfoHistoryDO.getMetaType());
            metaInfoDTO.setModuleName(metaInfoHistoryDO.getModuleName());
            String newData = this.handleWorkflowPublicParam(dataDTO.getDesignData(), metaInfoDTO);
            dataDTO.setDesignData(newData);
        }
        boolean latestVersion = true;
        if (selectMetaData != null) {
            metaDataDO.setDesignData(dataDTO.getDesignData());
            metaDataHistoryDO = new MetaDataHistoryDO();
            metaDataHistoryDO.setId(dataId);
            metaDataHistoryDO.setDesignData(dataDTO.getDesignData());
            this.metaDataDesignDao.updateByPrimaryKey((Object)metaDataDO);
            MetaInfoHistoryDO infoDO = new MetaInfoHistoryDO();
            infoDO.setId(dataId);
            infoDO.setRowVersion(Calendar.getInstance().getTimeInMillis());
            this.metaDataInfoDao.updateByPrimaryKeySelective((Object)infoDO);
            this.metaDataDesignVersionDao.updateByPrimaryKey((Object)metaDataHistoryDO);
            this.metaInfoVersionDao.updateByPrimaryKeySelective((Object)infoDO);
            String rediskey = tenantName + ":" + metaInfoHistoryDO.getUniqueCode();
            this.redisTemplate.opsForHash().put((Object)rediskey, (Object)"version", (Object)metaInfoHistoryDO.getVersionNO().toString());
            this.redisTemplate.opsForHash().put((Object)rediskey, (Object)"rowVersion", (Object)infoDO.getRowVersion().toString());
            String rediskeyHistory = tenantName + ":" + metaInfoHistoryDO.getUniqueCode() + ":" + metaInfoHistoryDO.getVersionNO();
            this.redisTemplate.opsForValue().set((Object)rediskeyHistory, (Object)infoDO.getRowVersion().toString());
        } else {
            metaDataHistoryDO = new MetaDataHistoryDO();
            metaDataHistoryDO.setId(dataId);
            MetaDataHistoryDO selectMetaDataHistory = (MetaDataHistoryDO)((Object)this.metaDataDesignVersionDao.selectOne((Object)metaDataHistoryDO));
            if (selectMetaDataHistory == null) {
                throw new Exception(EXCEPTION_MSG);
            }
            metaDataHistoryDO.setDesignData(dataDTO.getDesignData());
            this.metaDataDesignVersionDao.updateByPrimaryKey((Object)metaDataHistoryDO);
            MetaInfoHistoryDO infoDO = new MetaInfoHistoryDO();
            infoDO.setId(dataId);
            infoDO.setRowVersion(Calendar.getInstance().getTimeInMillis());
            this.metaInfoVersionDao.updateByPrimaryKeySelective((Object)infoDO);
            String rediskeyHistory = tenantName + ":" + metaInfoHistoryDO.getUniqueCode() + ":" + metaInfoHistoryDO.getVersionNO();
            this.redisTemplate.opsForValue().set((Object)rediskeyHistory, (Object)infoDO.getRowVersion().toString());
            latestVersion = false;
        }
        MetaInfoDTO infoDTO = this.metaDataInfoService.findHistoryVersionById(dataId);
        MetaDataDTO metaDataDTO = new MetaDataDTO();
        metaDataDTO.setId(metaDataHistoryDO.getId());
        metaDataDTO.setDesignData(metaDataHistoryDO.getDesignData());
        if (latestVersion) {
            this.metaSyncCacheService.updateCache(metaDataDTO, infoDTO, MetaState.DEPLOYED.getValue());
        } else {
            this.metaSyncCacheService.updateCache(metaDataDTO, infoDTO, MetaState.MODIFIED.getValue());
        }
        this.metaSyncCacheService.handleStatusCache(metaInfoHistoryDO.getUniqueCode(), metaInfoHistoryDO.getVersionNO(), 0, true);
        this.updatePublished(dataDTO, infoDTO);
    }

    public String handleWorkflowPublicParam(String designData, MetaInfoDTO infoDTO) {
        String workflowDesignStr = designData;
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("designData", (Object)designData);
        tenantDO.addExtInfo("workflowMetaInfo", (Object)infoDTO);
        try {
            ModuleServer itemServer = ModulesServerProvider.getModuleServer(infoDTO.getModuleName(), infoDTO.getMetaType());
            BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)itemServer.getServer(), (String)itemServer.getRealPath());
            R r = bizClient.handlePublicParam(tenantDO);
            if (r.getCode() == 0) {
                workflowDesignStr = (String)r.get((Object)"designData");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return workflowDesignStr;
    }

    @Override
    public MetaDataUpdateDTO updateMetaHistoryBatch(TenantDO param) throws Exception {
        MetaDataUpdateDTO dataUpdateDTO = new MetaDataUpdateDTO();
        UUID id = UUID.fromString((String)param.getExtInfo(FIELD_ID));
        int state = (Integer)param.getExtInfo("state");
        long version = (Long)param.getExtInfo("version");
        List ids = (List)param.getExtInfo("ids");
        ArrayList<String> sussessData = new ArrayList<String>();
        ArrayList<Map<String, String>> failedData = new ArrayList<Map<String, String>>();
        if (MetaState.MODIFIED.getValue() == state) {
            MetaInfoEditionDO conditionMetaInfoEditionDO = new MetaInfoEditionDO();
            conditionMetaInfoEditionDO.setId(id);
            conditionMetaInfoEditionDO.setMetaState(Integer.valueOf(state));
            conditionMetaInfoEditionDO.setVersionNO(Long.valueOf(version));
            MetaInfoEditionDO queryMetaInfoEditionDO = (MetaInfoEditionDO)this.metaDataInfoUserDao.selectOne(conditionMetaInfoEditionDO);
            if (queryMetaInfoEditionDO == null) {
                throw new Exception("\u6e90\u66f4\u65b0\u6570\u636e\u5df2\u53d1\u751f\u6539\u53d8");
            }
            MetaDataEditionDO conditionMetaDataEditionDO = new MetaDataEditionDO();
            conditionMetaDataEditionDO.setId(id);
            MetaDataEditionDO queryMetaDataEditionDO = (MetaDataEditionDO)((Object)this.metaDataDesignUserDao.selectOne((Object)conditionMetaDataEditionDO));
            if (queryMetaDataEditionDO == null) {
                throw new Exception("\u6e90\u66f4\u65b0\u6570\u636e\u5df2\u53d1\u751f\u6539\u53d8");
            }
            this.executeUpdate(ids, queryMetaInfoEditionDO, queryMetaDataEditionDO, sussessData, failedData);
            if (failedData.size() == 0) {
                queryMetaDataEditionDO.setDesignData(null);
                this.metaDataDesignUserDao.delete((Object)queryMetaDataEditionDO);
                this.metaDataInfoUserDao.delete(queryMetaInfoEditionDO);
                if (MetaTypeEnum.WORKFLOW.getName().equals(queryMetaInfoEditionDO.getMetaType())) {
                    queryMetaInfoEditionDO.setMetaState(Integer.valueOf(MetaState.MODIFIED.getValue()));
                    this.updatePublicParamRel(ids, queryMetaInfoEditionDO);
                }
            }
        } else if (MetaState.DEPLOYED.getValue() == state) {
            MetaInfoDO conditionMetaInfoDO = new MetaInfoDO();
            conditionMetaInfoDO.setId(id);
            conditionMetaInfoDO.setVersionNO(Long.valueOf(version));
            MetaInfoDO queryMetaInfoDO = (MetaInfoDO)this.metaDataInfoDao.selectOne(conditionMetaInfoDO);
            if (queryMetaInfoDO == null) {
                throw new Exception("\u6e90\u66f4\u65b0\u6570\u636e\u5df2\u53d1\u751f\u6539\u53d8");
            }
            MetaDataDO conditionMetaDataDO = new MetaDataDO();
            conditionMetaDataDO.setId(id);
            MetaDataDO queryMetaDataDO = (MetaDataDO)((Object)this.metaDataDesignDao.selectOne((Object)conditionMetaDataDO));
            this.executeUpdate(ids, queryMetaInfoDO, queryMetaDataDO, sussessData, failedData);
            if (MetaTypeEnum.WORKFLOW.getName().equals(queryMetaInfoDO.getMetaType())) {
                MetaInfoEditionDO editionDO = new MetaInfoEditionDO();
                editionDO.setMetaState(Integer.valueOf(MetaState.DEPLOYED.getValue()));
                editionDO.setUniqueCode(queryMetaInfoDO.getUniqueCode());
                editionDO.setVersionNO(queryMetaInfoDO.getVersionNO());
                editionDO.setModuleName(queryMetaInfoDO.getModuleName());
                editionDO.setMetaType(queryMetaInfoDO.getMetaType());
                this.updatePublicParamRel(ids, editionDO);
            }
        }
        dataUpdateDTO.setSuccessData(sussessData);
        dataUpdateDTO.setFailedData(failedData);
        return dataUpdateDTO;
    }

    private void updatePublicParamRel(List<String> ids, MetaInfoEditionDO editionDO) {
        try {
            ModuleServer itemServer = ModulesServerProvider.getModuleServer(editionDO.getModuleName(), editionDO.getMetaType());
            BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)itemServer.getServer(), (String)itemServer.getRealPath());
            editionDO.addExtInfo("ids", ids);
            R r = bizClient.workflowPublishUpdate(editionDO);
            if (r.getCode() != 0) {
                logger.error("\u66f4\u65b0\u5de5\u4f5c\u6d41\u516c\u5171\u53c2\u6570\u5f15\u7528\u5931\u8d25");
            }
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u5de5\u4f5c\u6d41\u516c\u5171\u53c2\u6570\u5f15\u7528\u5931\u8d25" + e.getMessage(), e);
        }
    }

    private <T> void executeUpdate(List<String> ids, T metaInfo, T metaData, List<String> sussessData, List<Map<String, String>> failedData) {
        for (final String updateId : ids) {
            String errorMsg = "";
            try {
                ArrayList checkinfos;
                boolean errorFlag;
                com.jiuqi.va.domain.biz.MetaDataDTO metaDataDTO = new com.jiuqi.va.domain.biz.MetaDataDTO();
                MetaInfoDO metaInfoDO = (MetaInfoDO)metaInfo;
                metaDataDTO.setModule(metaInfoDO.getModuleName());
                metaDataDTO.setMetaType(metaInfoDO.getMetaType());
                metaDataDTO.setModelName(metaInfoDO.getModelName());
                MetaDataDO metaDataDO = (MetaDataDO)((Object)metaData);
                metaDataDTO.setDatas(metaDataDO.getDesignData());
                metaDataDTO.setTenantName(ShiroUtil.getTenantName());
                R checkData = this.metaDataService.checkMetaData(metaDataDTO);
                if (checkData != null && (errorFlag = (checkinfos = (ArrayList)checkData.get((Object)"checkInfos")).stream().anyMatch(checkinfo -> {
                    Map checkinfoMap = (Map)checkinfo;
                    List checkResults = (List)checkinfoMap.get("checkResults");
                    return checkResults.stream().anyMatch(info -> {
                        Map infoMap = (Map)info;
                        return "ERROR".equals(infoMap.get("type"));
                    });
                }))) {
                    throw new MetaCheckException("\u6821\u9a8c\u89c4\u5219\u4e0d\u901a\u8fc7,\u8bf7\u91cd\u65b0\u68c0\u67e5\u5b9a\u4e49\u3002", checkinfos);
                }
                errorMsg = this.metaUpdateHandle.doUpdate(UUID.fromString(updateId), metaInfo, metaData);
                if (!StringUtils.hasText(errorMsg)) {
                    MetaInfoDTO infoDTO = this.metaDataInfoService.findHistoryVersionById(UUID.fromString(updateId));
                    MetaDataDTO dataDTO = this.metaDataService.getMetaDataHistoryById(UUID.fromString(updateId));
                    if (dataDTO == null) {
                        dataDTO = this.metaDataService.getMetaDataVById(UUID.fromString(updateId));
                    }
                    MetaInfoDO queryDO = new MetaInfoDO();
                    queryDO.setUniqueCode(infoDTO.getUniqueCode());
                    Long maxVer = this.metaDataInfoService.findMaxVersion(queryDO);
                    this.metaSyncCacheService.handleStatusCache(infoDTO.getUniqueCode(), infoDTO.getVersionNO(), 0, false);
                    this.metaSyncCacheService.updateDesignCache(dataDTO, MetaState.MODIFIED.getValue());
                    if (infoDTO.getVersionNO().equals(maxVer)) {
                        this.metaSyncCacheService.updateMetaInfoCache(infoDTO, MetaState.DEPLOYED.getValue());
                    } else {
                        this.metaSyncCacheService.updateMetaInfoCache(infoDTO, MetaState.MODIFIED.getValue());
                    }
                    this.metaSyncCacheService.handleStatusCache(infoDTO.getUniqueCode(), infoDTO.getVersionNO(), 0, true);
                    this.metaSyncCacheService.pushSyncMsg(UUID.fromString(updateId), MetaState.MODIFIED.getValue());
                    sussessData.add(updateId);
                    continue;
                }
                final String errorStr = errorMsg;
                failedData.add((Map<String, String>)new HashMap<String, String>(){
                    {
                        this.put(MetaVersionService.FIELD_ID, updateId);
                        this.put(MetaVersionService.FIELD_MSG, errorStr);
                    }
                });
            }
            catch (IllegalArgumentException e) {
                failedData.add((Map<String, String>)new HashMap<String, String>(){
                    private static final long serialVersionUID = 1L;
                    {
                        this.put(MetaVersionService.FIELD_ID, updateId);
                        this.put(MetaVersionService.FIELD_MSG, "\u8bf7\u4f20\u9012\u6b63\u786e\u7684UUID\u7c7b\u578b\u5b57\u7b26\u4e32," + e.getMessage());
                    }
                });
            }
            catch (Exception e) {
                failedData.add((Map<String, String>)new HashMap<String, String>(){
                    {
                        this.put(MetaVersionService.FIELD_ID, updateId);
                        this.put(MetaVersionService.FIELD_MSG, e.getMessage());
                    }
                });
            }
        }
    }

    @Override
    public List<MetaVersionManageDTO> getMetaVersionInfos(String uniqueCode) {
        MetaInfoDO param = new MetaInfoDO();
        param.setUniqueCode(uniqueCode);
        return this.metaVersionManageDao.getMetaVersionInfos(param);
    }
}


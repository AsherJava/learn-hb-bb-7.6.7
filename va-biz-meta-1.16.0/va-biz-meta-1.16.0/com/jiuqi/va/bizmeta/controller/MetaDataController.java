/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.domain.metainfo.MetaInfoVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BizClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.utils.VaI18nParamUtil
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.common.utils.ModulesServerProvider;
import com.jiuqi.va.bizmeta.dao.IMetaDataLockDao;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaLockDo;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.MetaDataUpdateService;
import com.jiuqi.va.bizmeta.service.MetaHelperService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.bizmeta.util.MetaI18nUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.domain.metainfo.MetaInfoVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BizClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.utils.VaI18nParamUtil;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/meta/datas"})
public class MetaDataController {
    private static final Logger logger = LoggerFactory.getLogger(MetaDataController.class);
    @Autowired
    private IMetaDataService metaDataService;
    @Autowired
    private IMetaInfoService metaInfoService;
    @Autowired
    private IMetaDataLockDao iMetaDataLockDao;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private MetaHelperService metaHelperService;
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;
    @Autowired
    private MetaDataUpdateService metaDataUpdateService;

    @GetMapping(value={"/{dataId}"})
    public String getMetaData(@PathVariable UUID dataId) {
        ObjectNode returnJson = null;
        MetaDataDTO dataDTO = this.metaDataService.getMetaDataById(dataId);
        MetaInfoDTO findMetaById = this.metaInfoService.findMetaById(dataId);
        if (dataDTO != null && dataDTO.getDesignData() != null) {
            String returnValue = dataDTO.getDesignData();
            returnJson = JSONUtil.parseObject((String)returnValue);
            returnJson.put("rowVersion", findMetaById.getRowVersion());
        }
        return JSONUtil.toJSONString(returnJson);
    }

    @PostMapping(value={"/get"})
    public R getMetaDesign(@RequestBody MetaDesignDTO designDTO) {
        String tenantName = ShiroUtil.getTenantName();
        if (tenantName == null && (tenantName = designDTO.getTenantName()) != null) {
            ShiroUtil.bindTenantName((String)tenantName);
        }
        Utils.setTraceId((String)(designDTO.getTraceId() == null ? Utils.getTraceId() : designDTO.getTraceId()));
        R r = null;
        boolean readDesignFromDb = Boolean.TRUE.equals(designDTO.getExtInfo("readDesignFromDb"));
        try {
            r = R.ok();
            MetaDataDTO dataDTO = null;
            if (OperateType.DESIGN.equals((Object)designDTO.getOperateType())) {
                dataDTO = this.metaDataService.getMetaDataById(designDTO.getId());
            } else {
                MetaInfoDTO infoDTO = null;
                String defineCode = designDTO.getDefineCode();
                Long defineVersion = designDTO.getDefineVersion();
                Long realVersion = defineVersion != null && defineVersion > 0L ? defineVersion : -1L;
                if (readDesignFromDb) {
                    infoDTO = this.metaSyncCacheService.getMetaInfoByDb(defineCode, realVersion);
                    dataDTO = infoDTO != null ? this.metaSyncCacheService.getDesignByDb(infoDTO.getId()) : null;
                } else {
                    try {
                        boolean syncFlag = true;
                        while (syncFlag) {
                            syncFlag = this.metaSyncCacheService.isSyncCache(defineCode, realVersion);
                            if (!syncFlag) continue;
                            try {
                                Thread.sleep(100L);
                            }
                            catch (InterruptedException e) {
                                logger.error("Interrupted!", e);
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                    catch (Exception e) {
                        logger.error("\u5143\u6570\u636e\u7f13\u5b58\u540c\u6b65\u672a\u5b8c\u6210 ", e);
                        return R.error((String)("\u83b7\u53d6\u5143\u6570\u636e\u8bbe\u8ba1\u5f02\u5e38\uff1a" + e.getMessage()));
                    }
                    infoDTO = this.metaSyncCacheService.getMetaInfoByCache(defineCode, realVersion);
                    MetaDataDTO metaDataDTO = dataDTO = infoDTO != null ? this.metaSyncCacheService.getDesignByCache(infoDTO.getId()) : null;
                }
                if (infoDTO != null) {
                    r.put("modelName", (Object)infoDTO.getModelName());
                    r.put("name", (Object)infoDTO.getName());
                    r.put("title", (Object)infoDTO.getTitle());
                    r.put("versionNO", (Object)infoDTO.getVersionNO());
                    r.put("rowVersion", (Object)infoDTO.getRowVersion());
                    r.put("metaType", (Object)infoDTO.getMetaType());
                    r.put("groupName", (Object)infoDTO.getGroupName());
                }
            }
            if (dataDTO != null && dataDTO.getDesignData() != null) {
                String returnValue = dataDTO.getDesignData();
                designDTO.setDatas(returnValue);
            }
            ObjectMapper mapper = new ObjectMapper();
            r.put("data", (Object)mapper.writeValueAsString((Object)designDTO));
        }
        catch (Exception e) {
            r = R.error((String)("\u83b7\u53d6\u5143\u6570\u636e\u8bbe\u8ba1\u5f02\u5e38\uff1a" + e.getMessage()));
        }
        return r;
    }

    @PostMapping(value={"/getByUniqueCode"})
    public R getMetaDesignByUniqueCode(@RequestBody MetaDesignDTO designParam) {
        String tenantName = ShiroUtil.getTenantName();
        if (tenantName == null && (tenantName = designParam.getTenantName()) != null) {
            ShiroUtil.bindTenantName((String)tenantName);
        }
        Utils.setTraceId((String)(designParam.getTraceId() == null ? Utils.getTraceId() : designParam.getTraceId()));
        try {
            MetaInfoDTO data = this.metaDataService.getMetaDesignByUniqueCode(designParam);
            return R.ok().put("data", (Object)data);
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5143\u6570\u636e\u8bbe\u8ba1\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            return R.error((String)("\u83b7\u53d6\u5143\u6570\u636e\u8bbe\u8ba1\u5f02\u5e38\uff1a" + e.getMessage()));
        }
    }

    @PostMapping(value={"/get/simple"})
    public R getSimpleMetaDesign(@RequestBody MetaDesignDTO designDTO) {
        R r;
        String tenantName = ShiroUtil.getTenantName();
        if (tenantName == null && (tenantName = designDTO.getTenantName()) != null) {
            ShiroUtil.bindTenantName((String)tenantName);
        }
        try {
            r = R.ok();
            Long defineVersion = designDTO.getDefineVersion();
            Long cacheVersion = defineVersion != null && defineVersion > 0L ? defineVersion : -1L;
            MetaInfoDTO infoDTO = this.metaSyncCacheService.getMetaInfoByCache(designDTO.getDefineCode(), cacheVersion);
            if (infoDTO != null) {
                R result;
                MetaInfoDO metaParam = new MetaInfoDO();
                metaParam.setUniqueCode(infoDTO.getUniqueCode());
                metaParam.setModuleName(infoDTO.getModuleName());
                metaParam.setTitle(infoDTO.getTitle());
                this.metaHelperService.convertMetaInfoI18nLanguage(metaParam, t -> MetaI18nUtils.generateBillDefineI18nKey(t.getModuleName(), t.getUniqueCode()), MetaInfoDO::setTitle);
                if (Boolean.TRUE.equals(VaI18nParamUtil.getTranslationEnabled()) && (result = this.metaHelperService.findZhEnI18nLanguage(infoDTO)).getCode() == 0) {
                    r.put("title_zh", result.get((Object)"zh"));
                    r.put("title_en", result.get((Object)"en"));
                }
                r.put("title", (Object)metaParam.getTitle());
                r.put("modelName", (Object)infoDTO.getModelName());
                r.put("name", (Object)infoDTO.getName());
                r.put("versionNO", (Object)infoDTO.getVersionNO());
                r.put("rowVersion", (Object)infoDTO.getRowVersion());
                r.put("metaType", (Object)infoDTO.getMetaType());
            }
        }
        catch (Exception e) {
            r = R.error((String)("\u83b7\u53d6\u5143\u6570\u636e\u4fe1\u606f\u5f02\u5e38\uff1a" + e.getMessage()));
        }
        return r;
    }

    @PostMapping(value={"/{dataId}"})
    public MetaInfoVO updateMetaData(@PathVariable(name="dataId") UUID dataId, @RequestBody String designData) {
        MetaInfoVO infoVO = new MetaInfoVO();
        MetaInfoDim metaInfo = new MetaInfoDim();
        infoVO.setMetaInfoDim(metaInfo);
        try {
            if (this.metaDataUpdateService.isControllerUpdating() || this.metaDataUpdateService.isUpdating()) {
                infoVO.setFlag(Boolean.valueOf(false));
                infoVO.setMessage("\u6b63\u5728\u5347\u7ea7\u5143\u6570\u636e\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
                return infoVO;
            }
            MetaDataDTO dataDTO = this.metaDataService.getMetaDataById(dataId);
            if (dataDTO == null) {
                infoVO.setFlag(Boolean.valueOf(false));
                infoVO.setMessage("\u4fee\u6539\u7684\u5143\u6570\u636e\u8bbe\u8ba1\u6570\u636e\u4e0d\u5b58\u5728");
                return infoVO;
            }
            dataDTO.setDesignData(designData);
            MetaInfoDTO infoDTO = this.metaInfoService.findMetaById(dataId);
            if (infoDTO == null) {
                infoVO.setFlag(Boolean.valueOf(false));
                infoVO.setMessage("\u4fee\u6539\u7684\u5143\u6570\u636e\u4fe1\u606f\u4e0d\u5b58\u5728");
                return infoVO;
            }
            if (MetaTypeEnum.WORKFLOW.getName().equalsIgnoreCase(infoDTO.getMetaType())) {
                String newDesign = this.handleWorkflowPublicParam(designData, infoDTO);
                dataDTO.setDesignData(newDesign);
            }
            UUID orgId = infoDTO.getId();
            MetaInfoDTO resultMetaInfo = this.metaInfoService.updateMeta(ShiroUtil.getUser().getUsername(), infoDTO, dataDTO);
            infoVO.setFlag(Boolean.valueOf(true));
            metaInfo.setId(resultMetaInfo.getId());
            metaInfo.setVersion(resultMetaInfo.getVersionNO());
            metaInfo.setRowVersion(resultMetaInfo.getRowVersion());
            metaInfo.setState(resultMetaInfo.getState().intValue());
            if (!infoDTO.getId().equals(orgId)) {
                metaInfo.setOrgId(orgId);
            }
        }
        catch (Exception e) {
            infoVO.setFlag(Boolean.valueOf(false));
            infoVO.setMessage(e.getMessage());
        }
        return infoVO;
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

    @PostMapping(value={"/check"})
    public R checkMetaData(@RequestBody String designData) {
        com.jiuqi.va.domain.biz.MetaDataDTO metaDataDTO = new com.jiuqi.va.domain.biz.MetaDataDTO();
        ObjectNode jsonObject = JSONUtil.parseObject((String)designData);
        metaDataDTO.setMetaType(jsonObject.path("metaType").asText(null));
        metaDataDTO.setModelName(jsonObject.path("modelName").asText(null));
        metaDataDTO.setUniqueCode(jsonObject.path("uniqueCode").asText(null));
        metaDataDTO.setTenantName(jsonObject.path("tenantName").asText(null));
        metaDataDTO.setModule(jsonObject.path("module").asText(null));
        JsonNode datas = jsonObject.get("datas");
        if (datas != null) {
            metaDataDTO.setDatas(JSONUtil.toJSONString((Object)jsonObject.get("datas")));
        }
        R checkResult = this.metaDataService.checkMetaData(metaDataDTO);
        return checkResult;
    }

    @PostMapping(value={"getDatasByMetaType"})
    public R getDatasByMetaType(@RequestBody TenantDO tenantDO) {
        try {
            String metaType = (String)tenantDO.getExtInfo("metaType");
            if (StringUtils.isEmpty(metaType)) {
                return R.error((String)"metaType\u4e0d\u80fd\u4e3a\u7a7a");
            }
            return this.metaDataService.getDatasByMetaType(tenantDO);
        }
        catch (Exception e) {
            return R.error((String)("\u83b7\u53d6\u8bbe\u8ba1\u6570\u636e\u53d1\u751f\u5f02\u5e38" + e.getMessage()));
        }
    }

    @PostMapping(value={"/getLockInfo"})
    public R getLockInfo(@RequestBody MetaInfoDTO infoDTO) {
        R r = new R();
        MetaLockDo metaLockDo = new MetaLockDo();
        metaLockDo.setUniqueCode(infoDTO.getUniqueCode());
        MetaLockDo metaLock = (MetaLockDo)((Object)this.iMetaDataLockDao.selectOne((Object)metaLockDo));
        if (metaLock == null) {
            r.put("isLocked", (Object)false);
            r.put("isSaved", (Object)true);
        } else {
            r.put("isLocked", (Object)true);
            if (metaLock.getLockuser().equals(ShiroUtil.getUser().getId()) || metaLock.getLockuser().equals(ShiroUtil.getUser().getMgrFlag())) {
                r.put("isSaved", (Object)true);
            } else {
                r.put("isSaved", (Object)false);
            }
            UserDTO userDTO = new UserDTO();
            userDTO.setId(metaLock.getLockuser());
            UserDO userDO = this.authUserClient.get(userDTO);
            if (userDO != null) {
                r.put("lockuser", (Object)userDO.getName());
            } else if ("super".equals(metaLock.getLockuser())) {
                r.put("lockuser", (Object)"admin");
            } else {
                this.iMetaDataLockDao.delete((Object)metaLockDo);
                r.put("isLocked", (Object)false);
                r.put("isSaved", (Object)true);
            }
        }
        return r;
    }

    @PostMapping(value={"/lockMetaData"})
    public R lockMetaData(@RequestBody MetaInfoDTO infoDTO) {
        MetaLockDo metaLockDo = new MetaLockDo();
        metaLockDo.setId(UUID.randomUUID());
        metaLockDo.setUniqueCode(infoDTO.getUniqueCode());
        if ("super".equals(ShiroUtil.getUser().getMgrFlag())) {
            metaLockDo.setLockuser("super");
        } else {
            metaLockDo.setLockuser(ShiroUtil.getUser().getId().toString());
        }
        metaLockDo.setLocktime(new Date());
        try {
            this.iMetaDataLockDao.insert((Object)metaLockDo);
        }
        catch (Exception e) {
            logger.error("\u5143\u6570\u636e\u9501\u5b9a\u5931\u8d25", e);
            return R.error((String)"\u5143\u6570\u636e\u9501\u5b9a\u5931\u8d25\u3002");
        }
        return R.ok((String)"\u5143\u6570\u636e\u9501\u5b9a\u6210\u529f\u3002");
    }

    @PostMapping(value={"/delMetalock"})
    public R delMetalock(@RequestBody MetaInfoDTO infoDTO) {
        if (StringUtils.isEmpty(infoDTO.getUniqueCode())) {
            return R.error((String)"\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570");
        }
        MetaLockDo metaLockDo = new MetaLockDo();
        metaLockDo.setUniqueCode(infoDTO.getUniqueCode());
        try {
            this.iMetaDataLockDao.delete((Object)metaLockDo);
            return R.ok((String)"\u5143\u6570\u636e\u89e3\u9501\u6210\u529f\u3002");
        }
        catch (Exception e) {
            logger.error("\u5143\u6570\u636e\u89e3\u9501\u5931\u8d25", e);
            return R.error((String)"\u5143\u6570\u636e\u89e3\u9501\u5931\u8d25\u3002");
        }
    }
}


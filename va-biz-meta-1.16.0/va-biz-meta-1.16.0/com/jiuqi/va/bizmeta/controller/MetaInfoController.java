/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.ModelDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.meta.MetaType
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.domain.metainfo.MetaInfoVO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.interceptor.TransactionAspectSupport
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.common.utils.MetaUtils;
import com.jiuqi.va.bizmeta.common.utils.PaginationProvider;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.service.IMetaAuthService;
import com.jiuqi.va.bizmeta.service.IMetaBaseInfoService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.IMetaOptionService;
import com.jiuqi.va.bizmeta.service.MetaDataUpdateService;
import com.jiuqi.va.bizmeta.service.MetaHelperService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.bizmeta.util.MetaI18nUtils;
import com.jiuqi.va.domain.biz.ModelDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.meta.MetaType;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.domain.metainfo.MetaInfoVO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/meta/infos"})
public class MetaInfoController {
    private static final Logger logger = LoggerFactory.getLogger(MetaInfoController.class);
    @Autowired
    private IMetaInfoService metaInfoService;
    @Autowired
    private IMetaBaseInfoService metaBaseInfoService;
    @Autowired
    private MetaHelperService metaHelperService;
    @Autowired
    private IMetaAuthService metaAuthService;
    @Autowired
    private IMetaOptionService metaOptionService;
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;
    @Autowired
    private MetaDataUpdateService metaDataUpdateService;

    @PostMapping(value={"/get"})
    public MetaInfoVO getAllMetaInfos(@RequestBody MetaInfoPageDTO infoPageDTO) {
        MetaInfoVO infoVO = new MetaInfoVO();
        ArrayList<MetaInfoDim> infoDims = new ArrayList<MetaInfoDim>();
        infoVO.setMetaInfos(infoDims);
        List<MetaInfoDTO> infoDTOs = null;
        try {
            if (!StringUtils.hasText(infoPageDTO.getModule())) {
                throw new RuntimeException("\u6a21\u5757\u540d\u4e3a\u7a7a");
            }
            boolean isPagination = infoPageDTO.isPagination();
            if (isPagination) {
                infoPageDTO.setPagination(false);
            }
            if ((infoDTOs = this.metaAuthService.getAuthMetaList(infoPageDTO)) == null) {
                return infoVO;
            }
            if (isPagination) {
                infoVO.setTotal(infoDTOs.size());
                PaginationProvider<MetaInfoDTO> paginationProvider = new PaginationProvider<MetaInfoDTO>(infoPageDTO.getOffset(), infoPageDTO.getLimit(), infoDTOs);
                infoDTOs = paginationProvider.getPaginationDatas();
            }
            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setMetaType(infoPageDTO.getMetaType());
            modelDTO.setModule(infoPageDTO.getModule());
            List<ModelDTO> modelDTOs = this.metaBaseInfoService.gatherModelsAll();
            for (MetaInfoDTO metaInfoDTO : infoDTOs) {
                MetaInfoDim infoDim = new MetaInfoDim();
                infoDim.setGroupName(metaInfoDTO.getGroupName());
                infoDim.setId(metaInfoDTO.getId());
                infoDim.setMetaType(metaInfoDTO.getMetaType());
                infoDim.setModelName(metaInfoDTO.getModelName());
                infoDim.setModelTitle(MetaUtils.getModelTitle(modelDTOs, metaInfoDTO.getModelName()));
                infoDim.setModuleName(metaInfoDTO.getModuleName());
                infoDim.setName(metaInfoDTO.getName());
                infoDim.setTitle(metaInfoDTO.getTitle());
                infoDim.setVersion(metaInfoDTO.getVersionNO());
                infoDim.setRowVersion(metaInfoDTO.getRowVersion());
                infoDim.setState(metaInfoDTO.getState().intValue());
                infoDim.setUniqueCode(metaInfoDTO.getUniqueCode());
                infoDims.add(infoDim);
            }
        }
        catch (Exception e) {
            infoVO.setFlag(Boolean.valueOf(false));
            infoVO.setMessage("\u83b7\u53d6\u5143\u6570\u636e\u5f02\u5e38\uff1a" + e.getMessage());
        }
        return infoVO;
    }

    @PostMapping(value={"/check/metaName"})
    public R checkMetaName(@RequestBody MetaInfoDTO metaInfoDTO) {
        return this.metaInfoService.checkMetaName(metaInfoDTO) ? R.ok() : R.error((String)"\u5728\u540c\u6a21\u5757\u540c\u7c7b\u578b\u4e0b\u5143\u6570\u636e\u540d\u79f0\u91cd\u590d");
    }

    @PostMapping(value={"/add"})
    public MetaInfoVO addMedtaInfo(@RequestBody MetaInfoDTO metaInfoDTO) {
        MetaInfoVO infoVO = new MetaInfoVO();
        try {
            MetaInfoDim infoDim = new MetaInfoDim();
            infoVO.setMetaInfoDim(infoDim);
            MetaInfoDTO infoDTO = this.metaInfoService.createMeta(ShiroUtil.getUser().getId().toString(), metaInfoDTO);
            infoDim.setId(infoDTO.getId());
            infoDim.setVersion(infoDTO.getVersionNO());
            infoDim.setRowVersion(infoDTO.getRowVersion());
            infoDim.setState(infoDTO.getState().intValue());
            infoDim.setGroupName(infoDTO.getGroupName());
            infoDim.setMetaType(infoDTO.getMetaType());
            infoDim.setModelName(infoDTO.getModelName());
            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setMetaType(metaInfoDTO.getMetaType());
            modelDTO.setModule(metaInfoDTO.getModuleName());
            List<ModelDTO> modelDTOs = this.metaBaseInfoService.gatherModels(modelDTO);
            infoDim.setModelTitle(MetaUtils.getModelTitle(modelDTOs, infoDTO.getModelName()));
            infoDim.setModuleName(infoDTO.getModuleName());
            infoDim.setName(infoDTO.getName());
            infoDim.setTitle(infoDTO.getTitle());
            infoVO.setFlag(Boolean.valueOf(true));
        }
        catch (Exception e) {
            logger.error("\u521b\u5efa\u5143\u6570\u636e\u5931\u8d25\uff1a", e);
            infoVO.setFlag(Boolean.valueOf(false));
            infoVO.setMessage("\u521b\u5efa\u5143\u6570\u636e\u5931\u8d25\uff1a" + e.getMessage());
        }
        return infoVO;
    }

    @PostMapping(value={"/update/{infoId}"})
    @Transactional
    public MetaInfoVO updateMetaInfo(@RequestBody MetaInfoDTO metaInfoDTO) {
        MetaInfoVO infoVO = new MetaInfoVO();
        try {
            MetaInfoDim metaInfo = new MetaInfoDim();
            infoVO.setMetaInfoDim(metaInfo);
            UUID orgId = metaInfoDTO.getId();
            MetaInfoDTO infoDTO = this.metaInfoService.updateMeta(ShiroUtil.getUser().getId().toString(), metaInfoDTO);
            infoVO.setFlag(Boolean.valueOf(true));
            metaInfo.setId(infoDTO.getId());
            metaInfo.setVersion(infoDTO.getVersionNO());
            metaInfo.setRowVersion(infoDTO.getRowVersion());
            metaInfo.setState(infoDTO.getState().intValue());
            metaInfo.setGroupName(infoDTO.getGroupName());
            metaInfo.setMetaType(infoDTO.getMetaType());
            metaInfo.setModelName(infoDTO.getModelName());
            List<ModelDTO> modelDTOs = this.metaBaseInfoService.gatherModelsAll();
            metaInfo.setModelTitle(MetaUtils.getModelTitle(modelDTOs, infoDTO.getModelName()));
            metaInfo.setModuleName(infoDTO.getModuleName());
            metaInfo.setName(infoDTO.getName());
            metaInfo.setTitle(infoDTO.getTitle());
            if (!infoDTO.getId().equals(orgId)) {
                metaInfo.setOrgId(orgId);
            }
        }
        catch (Exception e) {
            infoVO.setFlag(Boolean.valueOf(false));
            infoVO.setMessage("\u66f4\u65b0\u5143\u6570\u636e\u5931\u8d25\uff1a" + e.getMessage());
            try {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return infoVO;
    }

    @PostMapping(value={"/delete/{infoId}"})
    public MetaInfoVO removeMetaInfo(@PathVariable UUID infoId) {
        MetaInfoVO infoVO = new MetaInfoVO();
        try {
            if (this.metaDataUpdateService.isControllerUpdating() || this.metaDataUpdateService.isUpdating()) {
                infoVO.setFlag(Boolean.valueOf(false));
                infoVO.setMessage("\u6b63\u5728\u5347\u7ea7\u5143\u6570\u636e\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
                return infoVO;
            }
            MetaInfoEditionDO editionDO = this.metaInfoService.deleteMetaById(ShiroUtil.getUser().getId().toString(), infoId);
            infoVO.setFlag(Boolean.valueOf(true));
            infoVO.setMessage("\u5143\u6570\u636e\u5220\u9664\u6210\u529f");
            if (editionDO != null) {
                MetaInfoDim infoDim = new MetaInfoDim();
                infoVO.setMetaInfoDim(infoDim);
                infoDim.setId(editionDO.getId());
                infoDim.setVersion(editionDO.getVersionNO());
                infoDim.setRowVersion(editionDO.getRowVersion());
                infoDim.setState(editionDO.getMetaState().intValue());
                infoDim.setGroupName(editionDO.getGroupName());
                infoDim.setMetaType(editionDO.getMetaType());
                infoDim.setModelName(editionDO.getModelName());
                infoDim.setModuleName(editionDO.getModuleName());
                infoDim.setName(editionDO.getName());
                infoDim.setTitle(editionDO.getTitle());
            }
        }
        catch (Exception e) {
            infoVO.setFlag(Boolean.valueOf(false));
            infoVO.setMessage("\u5143\u6570\u636e\u5220\u9664\u5931\u8d25\uff1a" + e.getMessage());
        }
        return infoVO;
    }

    @PostMapping(value={"/find"})
    public R findMetaInfoByUniqueCode(@RequestBody TenantDO param) {
        R r = R.ok();
        try {
            String uniqueCode = (String)param.getExtInfo("defineCode");
            MetaInfoDTO infoDTO = this.metaSyncCacheService.getMetaInfoByCache(uniqueCode, -1L);
            MetaInfoDO metaParam = new MetaInfoDO();
            metaParam.setUniqueCode(infoDTO.getUniqueCode());
            metaParam.setModuleName(infoDTO.getModuleName());
            metaParam.setTitle(infoDTO.getTitle());
            this.metaHelperService.convertMetaInfoI18nLanguage(metaParam, t -> MetaI18nUtils.generateBillDefineI18nKey(t.getModuleName(), t.getUniqueCode()), MetaInfoDO::setTitle);
            for (Field field : infoDTO.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().equals("id") || field.get(infoDTO) == null) continue;
                if (field.getName().equals("title")) {
                    r.put(field.getName(), (Object)metaParam.getTitle());
                    continue;
                }
                r.put(field.getName(), field.get(infoDTO));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            r = R.error((String)e.getMessage());
        }
        return r;
    }

    @PostMapping(value={"/getAllInfo"})
    public PageVO<MetaInfoDim> getAllMetaInfoByMetaType(@RequestBody TenantDO param) {
        PageVO pageVO = new PageVO();
        ArrayList<MetaInfoDim> infoDims = new ArrayList<MetaInfoDim>();
        try {
            String metaType = (String)param.getExtInfo("metaType");
            String modelName = (String)param.getExtInfo("modelName");
            Boolean ignoreModelTitle = (Boolean)param.getExtInfo("ignoreModelTitle");
            List metaInfoDtoList = Optional.ofNullable(this.metaInfoService.getMetaInfoListByMetaType(StringUtils.hasText(metaType) ? metaType : null, modelName)).orElse(Collections.emptyList());
            List<ModelDTO> modelList = null;
            if (ignoreModelTitle == null || !ignoreModelTitle.booleanValue()) {
                modelList = this.metaBaseInfoService.gatherModelsAll();
            }
            for (MetaInfoDTO metaInfoDTO : metaInfoDtoList) {
                MetaInfoDim infoDim = new MetaInfoDim();
                infoDim.setGroupName(metaInfoDTO.getGroupName());
                infoDim.setId(metaInfoDTO.getId());
                infoDim.setMetaType(metaInfoDTO.getMetaType());
                infoDim.setModelName(metaInfoDTO.getModelName());
                if (modelList != null) {
                    infoDim.setModelTitle(MetaUtils.getModelTitle(modelList, metaInfoDTO.getModelName()));
                }
                infoDim.setModuleName(metaInfoDTO.getModuleName());
                infoDim.setName(metaInfoDTO.getName());
                infoDim.setTitle(metaInfoDTO.getTitle());
                infoDim.setVersion(metaInfoDTO.getVersionNO());
                infoDim.setRowVersion(metaInfoDTO.getRowVersion());
                infoDim.setState(metaInfoDTO.getState().intValue());
                infoDim.setUniqueCode(metaInfoDTO.getUniqueCode());
                infoDims.add(infoDim);
            }
            this.metaHelperService.convertMetaInfoI18nLanguage(infoDims, t -> MetaI18nUtils.generateBillDefineI18nKey(t.getModuleName(), t.getUniqueCode()), MetaInfoDim::setTitle);
            pageVO.setRows(infoDims);
        }
        catch (Exception e) {
            pageVO.setRs(R.error((String)("\u83b7\u53d6\u5143\u6570\u636e\u5f02\u5e38\uff1a" + e.getMessage())));
        }
        return pageVO;
    }

    @PostMapping(value={"/getRowVersion/{infoId}"})
    public R getRowVersion(@PathVariable UUID infoId) {
        R r = R.ok();
        try {
            MetaInfoDTO findMetaById = this.metaInfoService.findMetaById(infoId);
            r.put("rowVersion", (Object)(findMetaById != null ? findMetaById.getRowVersion() : null));
        }
        catch (Exception e) {
            r = R.error((String)("\u83b7\u53d6\u5143\u6570\u636e\u884c\u7248\u672c\u5931\u8d25" + e.getMessage().toString()));
        }
        return r;
    }

    @PostMapping(value={"/restore/{id}/{cover}"})
    public R restoreMetaInfo(@PathVariable String id, @PathVariable Integer cover, @RequestBody MetaInfoDTO metaInfoDTO) {
        R r;
        try {
            Integer state = this.metaInfoService.restoreMetaInfo(id, cover, metaInfoDTO);
            r = R.ok();
            r.put("state", (Object)state);
        }
        catch (Exception e) {
            r = R.error((String)e.getMessage());
        }
        return r;
    }

    @PostMapping(value={"/copy/{infoId}"})
    public MetaInfoVO copyMedtaInfo(@PathVariable UUID infoId, @RequestBody MetaInfoDTO metaInfoDTO) {
        MetaInfoVO infoVO = new MetaInfoVO();
        try {
            metaInfoDTO.setId(null);
            MetaInfoDim infoDim = new MetaInfoDim();
            infoVO.setMetaInfoDim(infoDim);
            MetaInfoDTO infoDTO = this.metaInfoService.copyMedta(infoId, metaInfoDTO);
            infoDim.setId(infoDTO.getId());
            infoDim.setVersion(infoDTO.getVersionNO());
            infoDim.setRowVersion(infoDTO.getRowVersion());
            infoDim.setState(infoDTO.getState().intValue());
            infoDim.setGroupName(infoDTO.getGroupName());
            infoDim.setMetaType(infoDTO.getMetaType());
            infoDim.setModelName(infoDTO.getModelName());
            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setMetaType(metaInfoDTO.getMetaType());
            modelDTO.setModule(metaInfoDTO.getModuleName());
            List<ModelDTO> modelDTOs = this.metaBaseInfoService.gatherModels(modelDTO);
            infoDim.setModelTitle(MetaUtils.getModelTitle(modelDTOs, infoDTO.getModelName()));
            infoDim.setModuleName(infoDTO.getModuleName());
            infoDim.setName(infoDTO.getName());
            infoDim.setTitle(infoDTO.getTitle());
            infoVO.setFlag(Boolean.valueOf(true));
        }
        catch (Exception e) {
            logger.error("\u521b\u5efa\u5143\u6570\u636e\u5931\u8d25\uff1a", e);
            infoVO.setFlag(Boolean.valueOf(false));
            infoVO.setMessage("\u521b\u5efa\u5143\u6570\u636e\u5931\u8d25\uff1a" + e.getMessage());
        }
        return infoVO;
    }

    @PostMapping(value={"/get/infodata"})
    public MetaInfoDTO getMetaInfoAndData(@RequestBody MetaInfoDTO metaInfoDTO) {
        return this.metaInfoService.getMetaInfoAndData(metaInfoDTO);
    }

    @PostMapping(value={"/list"})
    public List<MetaTreeInfoDTO> getMetaInfoList(@RequestBody TenantDO param) {
        String module = param.getExtInfo("module") != null ? param.getExtInfo("module").toString() : null;
        String bizType = param.getExtInfo("bizType") != null ? param.getExtInfo("bizType").toString() : null;
        String groupName = param.getExtInfo("groupName") != null ? param.getExtInfo("groupName").toString() : null;
        String uniqueCode = param.getExtInfo("uniqueCode") != null ? param.getExtInfo("uniqueCode").toString() : null;
        boolean bizAuth = param.getExtInfo("bizAuth") != null && (Boolean)param.getExtInfo("bizAuth") != false;
        MetaInfoPageDTO metaInfoParam = new MetaInfoPageDTO();
        metaInfoParam.setPagination(false);
        metaInfoParam.setModule(module);
        metaInfoParam.setMetaType(bizType);
        metaInfoParam.setUniqueCode(uniqueCode);
        metaInfoParam.setOperateType(OperateType.EXECUTE);
        if (groupName != null) {
            ArrayList<String> groupNames = new ArrayList<String>();
            groupNames.add(groupName);
            metaInfoParam.setGroupNames(groupNames);
        }
        List<Object> list = new ArrayList();
        list = bizAuth ? this.metaAuthService.getAuthMetaList(metaInfoParam) : this.metaInfoService.getMetaList(metaInfoParam);
        ArrayList<MetaTreeInfoDTO> metaInfos = new ArrayList<MetaTreeInfoDTO>();
        for (MetaInfoDTO metaInfoDTO : list) {
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setId(metaInfoDTO.getId());
            infoDTO.setName(metaInfoDTO.getName());
            infoDTO.setTitle(metaInfoDTO.getTitle());
            infoDTO.setModuleName(metaInfoDTO.getModuleName());
            infoDTO.setGroupName(metaInfoDTO.getGroupName());
            infoDTO.setUniqueCode(metaInfoDTO.getUniqueCode());
            infoDTO.setModelName(metaInfoDTO.getModelName());
            infoDTO.setType(MetaType.METADATA);
            infoDTO.setVersion(metaInfoDTO.getVersionNO());
            metaInfos.add(infoDTO);
        }
        return metaInfos;
    }

    @PostMapping(value={"/published/first/get"})
    public R getFirstPublishedByModelName(@RequestBody MetaInfoDTO param) {
        R r;
        String modelName = param.getModelName();
        if (!StringUtils.hasText(modelName)) {
            return R.error((String)"\u6a21\u578b\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        try {
            MetaInfoHistoryDO metaInfoHistoryDO = new MetaInfoHistoryDO();
            metaInfoHistoryDO.setModelName(modelName);
            String uniqueCode = this.metaInfoService.getFirstPublishedByModelName(metaInfoHistoryDO);
            if (uniqueCode != null) {
                r = R.ok();
                r.put("data", (Object)uniqueCode);
            } else {
                r = R.error((String)"\u672a\u67e5\u8be2\u5230\u5df2\u53d1\u5e03\u7684\u5b9a\u4e49");
            }
        }
        catch (Exception e) {
            r = R.error((String)e.getMessage());
        }
        return r;
    }
}


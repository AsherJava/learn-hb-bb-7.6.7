/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.ModelDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.meta.MetaType
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.BizClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncGroupTypeEnum
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMetaGroupDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncModuleEnum
 *  com.jiuqi.va.paramsync.domain.VaParamSyncParamDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.common.utils.MetaUtils;
import com.jiuqi.va.bizmeta.common.utils.ModulesServerProvider;
import com.jiuqi.va.bizmeta.domain.dimension.MetaBaseDim;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDTO;
import com.jiuqi.va.bizmeta.domain.metabase.MetaBaseInfoVO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoFilterDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelVO;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.domain.multimodule.Modules;
import com.jiuqi.va.bizmeta.domain.plugin.MetaPluginVO;
import com.jiuqi.va.bizmeta.service.IMetaAuthService;
import com.jiuqi.va.bizmeta.service.IMetaBaseInfoService;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.IMetaOptionService;
import com.jiuqi.va.bizmeta.service.IMetaParamSyncService;
import com.jiuqi.va.bizmeta.service.impl.MetaDataResourceGatherService;
import com.jiuqi.va.domain.biz.ModelDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.meta.MetaType;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.BizClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncGroupTypeEnum;
import com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMetaGroupDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncModuleEnum;
import com.jiuqi.va.paramsync.domain.VaParamSyncParamDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/biz"})
public class MetaBaseInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaBaseInfoController.class);
    @Autowired
    private IMetaBaseInfoService metaBaseInfoService;
    @Autowired
    private IMetaGroupService metaGroupService;
    @Autowired
    private IMetaInfoService metaInfoService;
    @Autowired
    private IMetaParamSyncService metaParamSyncService;
    @Autowired
    private MetaDataResourceGatherService metaDataResourceGatherService;
    @Autowired
    private BizClient bizClient;
    @Autowired
    private IMetaOptionService metaOptionService;
    @Autowired
    private IMetaAuthService metaAuthService;

    @PostMapping(value={"/base"})
    public MetaBaseInfoVO getModulesAndTypes(@RequestBody MetaModelDTO infoDTO) {
        ArrayList<MetaBaseDim> metaBaseDims = new ArrayList<MetaBaseDim>();
        MetaBaseInfoVO metaModuleVO = new MetaBaseInfoVO();
        metaModuleVO.setBaseInfos(metaBaseDims);
        for (ModuleServer moduleServer : Modules.getModules()) {
            MetaTypeEnum[] baseInfo = new MetaBaseDim(moduleServer.getName(), moduleServer.getTitle());
            metaBaseDims.add((MetaBaseDim)baseInfo);
        }
        if (StringUtils.isEmpty(infoDTO.getMetaType()) || "all".equals(infoDTO.getMetaType())) {
            for (ModuleServer moduleServer : Modules.getModules()) {
                for (MetaTypeEnum metaType : MetaTypeEnum.values()) {
                    MetaBaseDim dim = new MetaBaseDim(metaType.getName(), metaType.getTitle(), moduleServer.getName());
                    metaBaseDims.add(dim);
                }
            }
        } else {
            MetaTypeEnum typeEnum = MetaUtils.getMetaTypeByName(infoDTO.getMetaType());
            for (ModuleServer moduleServer : Modules.getModules()) {
                MetaBaseDim dim = new MetaBaseDim(typeEnum.getName(), typeEnum.getTitle(), moduleServer.getName());
                metaBaseDims.add(dim);
            }
        }
        return metaModuleVO;
    }

    @PostMapping(value={"/models"})
    public MetaModelVO getModels(@RequestBody MetaModelDTO metaModelDTO) {
        MetaModelVO modelVO = new MetaModelVO();
        ArrayList<MetaBaseDim> baseInfos = new ArrayList<MetaBaseDim>();
        modelVO.setModels(baseInfos);
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setMetaType(metaModelDTO.getMetaType());
        modelDTO.setModule(metaModelDTO.getModuleName());
        List<ModelDTO> dtos = null;
        dtos = StringUtils.isEmpty(metaModelDTO.getMetaType()) ? this.metaBaseInfoService.gatherModelsAll() : this.metaBaseInfoService.gatherModels(modelDTO);
        if (dtos == null) {
            return modelVO;
        }
        for (ModelDTO billListModel : dtos) {
            MetaBaseDim billList = new MetaBaseDim(billListModel.getModelName(), billListModel.getModelTitle());
            baseInfos.add(billList);
        }
        return modelVO;
    }

    @PostMapping(value={"/metas/tree"})
    public PageVO<MetaTreeInfoDTO> getMetasFilter(@RequestBody MetaInfoFilterDTO metaInfoFilterDTO) {
        PageVO pageVO = new PageVO();
        ArrayList<MetaTreeInfoDTO> infoDTOs = new ArrayList<MetaTreeInfoDTO>();
        pageVO.setRows(infoDTOs);
        MetaBaseInfoVO baseInfoVO = this.getModulesAndTypes(new MetaModelDTO());
        for (MetaBaseDim metaBaseDim : baseInfoVO.getBaseInfos()) {
            if (!StringUtils.isEmpty(metaBaseDim.getModuleName()) || !StringUtils.isEmpty(metaInfoFilterDTO.getModuleName()) && !metaBaseDim.getName().equals(metaInfoFilterDTO.getModuleName())) continue;
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setName(metaBaseDim.getName());
            infoDTO.setTitle(metaBaseDim.getTitle());
            infoDTO.setType(MetaType.MODULE);
            infoDTO.setUniqueCode(metaBaseDim.getName() + "_" + MetaTypeEnum.BILL.getCode());
            infoDTOs.add(infoDTO);
        }
        metaInfoFilterDTO.setMetaType(MetaTypeEnum.BILL.getName());
        List<MetaGroupDO> groupDOs = this.metaGroupService.getGroupListByFilter(metaInfoFilterDTO);
        for (MetaGroupDO metaGroupDO : groupDOs) {
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setId(metaGroupDO.getId());
            infoDTO.setName(metaGroupDO.getName());
            infoDTO.setTitle(metaGroupDO.getTitle());
            infoDTO.setModuleName(metaGroupDO.getModuleName());
            infoDTO.setParentName(metaGroupDO.getParentName());
            infoDTO.setUniqueCode(metaGroupDO.getUniqueCode());
            infoDTO.setType(MetaType.GROUP);
            infoDTOs.add(infoDTO);
        }
        metaInfoFilterDTO.setMetaType(MetaTypeEnum.BILL.getName());
        List<MetaInfoDO> list = this.metaInfoService.getMetaListFilter(metaInfoFilterDTO);
        for (MetaInfoDO metaInfoDO : list) {
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setId(metaInfoDO.getId());
            infoDTO.setName(metaInfoDO.getName());
            infoDTO.setTitle(metaInfoDO.getTitle());
            infoDTO.setModuleName(metaInfoDO.getModuleName());
            infoDTO.setGroupName(metaInfoDO.getGroupName());
            infoDTO.setUniqueCode(metaInfoDO.getUniqueCode());
            infoDTO.setModelName(metaInfoDO.getModelName());
            infoDTO.setType(MetaType.METADATA);
            infoDTOs.add(infoDTO);
        }
        pageVO.setTotal(infoDTOs.size());
        return pageVO;
    }

    @PostMapping(value={"/metas/paramsync/export"})
    public VaParamSyncResponseDO exportMetas(@RequestBody VaParamSyncParamDO param) {
        try {
            return this.metaParamSyncService.export(param);
        }
        catch (Exception e) {
            LOGGER.error("\u5bfc\u51fa\u5931\u8d25: ", e);
            return null;
        }
    }

    @PostMapping(value={"/metas/paramsync/import/groups"})
    public R getImportGroups(@RequestBody VaParamSyncMainfestDO params, String metaType) {
        try {
            List<VaParamSyncMetaGroupDO> result = this.metaParamSyncService.getImportGroups(metaType, params);
            return R.ok(Stream.of(result).collect(Collectors.toMap(o -> "groups", o -> result)));
        }
        catch (Exception e) {
            LOGGER.error("\u83b7\u53d6\u5bfc\u5165\u5206\u7ec4\u5931\u8d25: ", e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/metas/paramsync/import"})
    public R importParam(@RequestBody MultipartFile multipartFile, String params) {
        try {
            VaParamSyncParamDO paramDO = (VaParamSyncParamDO)JSONUtil.parseObject((String)params, VaParamSyncParamDO.class);
            Map fileMap = VaParamSyncUtils.uncompress((InputStream)multipartFile.getInputStream());
            if (fileMap.size() == 0) {
                return R.error((String)"\u89e3\u538b\u5931\u8d25\uff0c\u6587\u4ef6\u4e0d\u5b58\u5728");
            }
            return this.metaParamSyncService.importParam(paramDO, fileMap);
        }
        catch (IOException e) {
            LOGGER.error("\u5bfc\u5165\u5931\u8d25: ", e);
            return R.error((String)e.getMessage());
        }
    }

    @GetMapping(value={"/metas/export/{metaType}/tree"})
    public PageVO<VaParamSyncMetaGroupDO> getExportMetaTrees(@PathVariable(value="metaType") String metaType, @RequestBody(required=false) TenantDO param) {
        PageVO pageVO = new PageVO();
        ArrayList<VaParamSyncMetaGroupDO> infoDTOs = new ArrayList<VaParamSyncMetaGroupDO>();
        MetaModelDTO metaModelDTO = new MetaModelDTO();
        metaModelDTO.setMetaType(metaType);
        MetaBaseInfoVO baseInfoVO = this.getModulesAndTypes(metaModelDTO);
        for (MetaBaseDim metaTreeInfoDTO : baseInfoVO.getBaseInfos()) {
            VaParamSyncMetaGroupDO infoDTO = new VaParamSyncMetaGroupDO();
            if (StringUtils.isEmpty(metaTreeInfoDTO.getModuleName())) {
                if ("all".equals(metaType)) {
                    infoDTO.setGroupName(metaTreeInfoDTO.getName());
                } else {
                    infoDTO.setGroupName(metaTreeInfoDTO.getName() + "/" + metaType);
                }
            } else {
                if (!"all".equals(metaType)) continue;
                infoDTO.setGroupName(metaTreeInfoDTO.getModuleName() + "/" + metaTreeInfoDTO.getName());
            }
            infoDTO.setParentName(metaTreeInfoDTO.getModuleName());
            infoDTO.setDefineName(metaTreeInfoDTO.getName());
            infoDTO.setGroupTitle(metaTreeInfoDTO.getTitle());
            infoDTO.setParentName(metaTreeInfoDTO.getModuleName());
            infoDTO.setModuleName(metaTreeInfoDTO.getModuleName());
            infoDTO.setGroupType(Integer.valueOf(VaParamSyncGroupTypeEnum.MODULE.getValue()));
            infoDTOs.add(infoDTO);
        }
        List<MetaGroupDTO> groupDOs = this.metaAuthService.getAuthGroupList(null, "all".equals(metaType) ? null : metaType, OperateType.DESIGN);
        ArrayList<VaParamSyncMetaGroupDO> metGroupTreeInfos = new ArrayList<VaParamSyncMetaGroupDO>();
        HashMap<String, String> groupTemp = new HashMap<String, String>();
        for (MetaGroupDTO metaGroupDO : groupDOs) {
            String Key = metaGroupDO.getModuleName() + metaGroupDO.getMetaType() + metaGroupDO.getName();
            if (!groupTemp.containsKey(Key)) {
                groupTemp.put(Key, Key);
                VaParamSyncMetaGroupDO treeInfoDTO = new VaParamSyncMetaGroupDO();
                treeInfoDTO.setDefineName(metaGroupDO.getName());
                treeInfoDTO.setGroupTitle(metaGroupDO.getTitle());
                treeInfoDTO.setModuleName(metaGroupDO.getModuleName());
                treeInfoDTO.setParentName(metaGroupDO.getParentName());
                treeInfoDTO.setGroupType(Integer.valueOf(VaParamSyncGroupTypeEnum.GROUP.getValue()));
                treeInfoDTO.setMetaType(metaGroupDO.getMetaType());
                metGroupTreeInfos.add(treeInfoDTO);
                continue;
            }
            if (metaGroupDO.getState() != MetaState.DEPLOYED.getValue()) continue;
            for (VaParamSyncMetaGroupDO vaParamSyncMetaGroupDO : metGroupTreeInfos) {
                String treeInfoKey = vaParamSyncMetaGroupDO.getModuleName() + vaParamSyncMetaGroupDO.getMetaType() + vaParamSyncMetaGroupDO.getDefineName();
                if (!Key.equals(treeInfoKey)) continue;
                vaParamSyncMetaGroupDO.setGroupTitle(metaGroupDO.getTitle());
            }
        }
        for (VaParamSyncMetaGroupDO treeInfoDTO : metGroupTreeInfos) {
            if (!StringUtils.isEmpty(treeInfoDTO.getParentName())) continue;
            treeInfoDTO.setGroupName(treeInfoDTO.getModuleName() + "/" + treeInfoDTO.getMetaType() + "/" + treeInfoDTO.getDefineName());
            treeInfoDTO.setParentName(treeInfoDTO.getModuleName() + "/" + treeInfoDTO.getMetaType());
            this.getChildren(metGroupTreeInfos, treeInfoDTO);
        }
        List groupDatas = metGroupTreeInfos.stream().filter(o -> o.getGroupName() != null).collect(Collectors.toList());
        Map<String, String> groupDatasMap = groupDatas.stream().collect(Collectors.toMap(o -> o.getModuleName() + o.getMetaType() + o.getDefineName(), VaParamSyncMetaGroupDO::getGroupName));
        ArrayList<MetaInfoDTO> infoDOs = new ArrayList<MetaInfoDTO>();
        MetaInfoPageDTO infoPageDTO = new MetaInfoPageDTO();
        infoPageDTO.setMetaType(metaType);
        infoPageDTO.setPagination(false);
        infoPageDTO.setOperateType(OperateType.DESIGN);
        for (ModuleServer moduleServer : Modules.getModules()) {
            infoPageDTO.setModule(moduleServer.getName());
            List<MetaInfoDTO> authMetaList = this.metaAuthService.getAuthMetaList(infoPageDTO);
            if (authMetaList == null) continue;
            infoDOs.addAll(authMetaList);
        }
        HashSet<String> hashSet = new HashSet<String>();
        for (MetaInfoDTO metaInfoDO : infoDOs) {
            VaParamSyncMetaGroupDO infoDTO = new VaParamSyncMetaGroupDO();
            infoDTO.setMetaType(metaInfoDO.getMetaType());
            infoDTO.setDefineName(metaInfoDO.getName());
            infoDTO.setGroupTitle(metaInfoDO.getTitle());
            infoDTO.setModuleName(metaInfoDO.getModuleName());
            infoDTO.setGroupName(VaParamSyncModuleEnum.METADATA.getName() + "_" + metaInfoDO.getUniqueCode());
            String treePID = groupDatasMap.get(metaInfoDO.getModuleName() + metaInfoDO.getMetaType() + metaInfoDO.getGroupName());
            if (StringUtils.isEmpty(treePID)) {
                infoDTO.setParentName(metaInfoDO.getModuleName() + "/" + metaInfoDO.getMetaType());
            } else {
                hashSet.add(treePID);
                infoDTO.setParentName(treePID);
            }
            infoDTO.setGroupType(Integer.valueOf(VaParamSyncGroupTypeEnum.DEFINE.getValue()));
            infoDTO.setModelName(metaInfoDO.getModelName());
            infoDTOs.add(infoDTO);
        }
        infoDTOs.addAll(groupDatas.stream().filter(o -> {
            String treeID = o.getGroupName();
            Boolean flag = false;
            for (String treePid : treePids) {
                if (treePid.indexOf(treeID) == -1) continue;
                flag = true;
                break;
            }
            return flag;
        }).collect(Collectors.toList()));
        pageVO.setRows(infoDTOs);
        return pageVO;
    }

    private void getChildren(List<VaParamSyncMetaGroupDO> metGroupTreeInfos, VaParamSyncMetaGroupDO curtreeInfo) {
        for (VaParamSyncMetaGroupDO treeInfo : metGroupTreeInfos) {
            if (!treeInfo.getModuleName().equals(curtreeInfo.getModuleName()) || StringUtils.isEmpty(treeInfo.getParentName()) || !treeInfo.getParentName().equals(curtreeInfo.getDefineName()) || !treeInfo.getMetaType().equals(curtreeInfo.getMetaType())) continue;
            treeInfo.setGroupName(curtreeInfo.getGroupName() + "/" + treeInfo.getDefineName());
            treeInfo.setParentName(curtreeInfo.getGroupName());
            this.getChildren(metGroupTreeInfos, treeInfo);
        }
    }

    @GetMapping(value={"/metas/tree"})
    public PageVO<MetaTreeInfoDTO> getAllMetas(@RequestBody(required=false) TenantDO param) {
        PageVO pageVO = new PageVO();
        ArrayList<MetaTreeInfoDTO> infoDTOs = new ArrayList<MetaTreeInfoDTO>();
        pageVO.setRows(infoDTOs);
        MetaBaseInfoVO baseInfoVO = this.getModulesAndTypes(new MetaModelDTO());
        for (MetaBaseDim metaBaseDim : baseInfoVO.getBaseInfos()) {
            if (!StringUtils.isEmpty(metaBaseDim.getModuleName())) continue;
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setName(metaBaseDim.getName());
            infoDTO.setTitle(metaBaseDim.getTitle());
            infoDTO.setType(MetaType.MODULE);
            infoDTO.setUniqueCode(metaBaseDim.getName() + "_" + MetaTypeEnum.BILL.getCode());
            infoDTOs.add(infoDTO);
        }
        List<MetaGroupDO> groupDOs = this.metaGroupService.getGroupListByMetaType(MetaTypeEnum.BILL.getName());
        for (MetaGroupDO metaGroupDO : groupDOs) {
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setId(metaGroupDO.getId());
            infoDTO.setName(metaGroupDO.getName());
            infoDTO.setTitle(metaGroupDO.getTitle());
            infoDTO.setModuleName(metaGroupDO.getModuleName());
            infoDTO.setParentName(metaGroupDO.getParentName());
            infoDTO.setUniqueCode(metaGroupDO.getUniqueCode());
            infoDTO.setType(MetaType.GROUP);
            infoDTOs.add(infoDTO);
        }
        List<MetaInfoDO> list = this.metaInfoService.getMetaListByMetaType(MetaTypeEnum.BILL.getName());
        for (MetaInfoDO metaInfoDO : list) {
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setId(metaInfoDO.getId());
            infoDTO.setName(metaInfoDO.getName());
            infoDTO.setTitle(metaInfoDO.getTitle());
            infoDTO.setModuleName(metaInfoDO.getModuleName());
            infoDTO.setGroupName(metaInfoDO.getGroupName());
            infoDTO.setUniqueCode(metaInfoDO.getUniqueCode());
            infoDTO.setModelName(metaInfoDO.getModelName());
            infoDTO.setType(MetaType.METADATA);
            infoDTOs.add(infoDTO);
        }
        return pageVO;
    }

    @PostMapping(value={"/plugins/{modelType}/{uniqueCode}"})
    public MetaPluginVO getAllPlugins(@PathVariable(value="modelType") String modelType, @PathVariable(value="uniqueCode") String uniqueCode) {
        List<Map<String, Object>> plugins = this.metaBaseInfoService.gatherPluginsAll(modelType, uniqueCode);
        return new MetaPluginVO(plugins);
    }

    @PostMapping(value={"/meta/tree"})
    public PageVO<MetaTreeInfoDTO> getAllWorkflowMetas(@RequestBody(required=false) TenantDO param) {
        HashMap<String, Integer> hashMap;
        Set<String> userAuth;
        MetaAuthDTO metaAuthCheck;
        OptionItemVO optionItemVO;
        PageVO pageVO = new PageVO();
        ArrayList<MetaTreeInfoDTO> infoDTOs = new ArrayList<MetaTreeInfoDTO>();
        pageVO.setRows(infoDTOs);
        MetaBaseInfoVO baseInfoVO = this.getModulesAndTypes(new MetaModelDTO());
        String metaType = null;
        if (param != null) {
            metaType = (String)param.getExtInfo("metaType");
        }
        for (MetaBaseDim metaTreeInfoDTO : baseInfoVO.getBaseInfos()) {
            if (!StringUtils.isEmpty(metaTreeInfoDTO.getModuleName())) continue;
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setName(metaTreeInfoDTO.getName());
            infoDTO.setTitle(metaTreeInfoDTO.getTitle());
            infoDTO.setType(MetaType.MODULE);
            if (metaType != null) {
                infoDTO.setUniqueCode(metaTreeInfoDTO.getName() + "_" + (Object)((Object)MetaTypeEnum.valueOf(metaType.toUpperCase())));
            } else {
                infoDTO.setUniqueCode(metaTreeInfoDTO.getName() + "_" + MetaTypeEnum.BILL.getCode());
            }
            infoDTOs.add(infoDTO);
        }
        List<MetaGroupDO> groupDOs = null;
        groupDOs = metaType != null ? this.metaGroupService.getGroupListByMetaType(metaType) : this.metaGroupService.getGroupListByMetaType(MetaTypeEnum.BILL.getName());
        UserLoginDTO userNow = ShiroUtil.getUser();
        OptionItemDTO optionParam = new OptionItemDTO();
        optionParam.setName("META001");
        List<OptionItemVO> optionItemList = this.metaOptionService.list(optionParam);
        if (userNow.getMgrFlag().equals("normal") && optionItemList != null && !optionItemList.isEmpty() && (optionItemVO = optionItemList.get(0)).getVal().equals("1") && (MetaTypeEnum.WORKFLOW.getName().equals(metaType) || metaType == null)) {
            HashMap<String, String> groupRelationship = new HashMap<String, String>();
            for (MetaGroupDO groupDto : groupDOs) {
                this.metaAuthService.addRelationshipToMap(groupDto, groupRelationship);
            }
            metaAuthCheck = new MetaAuthDTO();
            metaAuthCheck.setTenantName(ShiroUtil.getTenantName());
            metaAuthCheck.setGroupflag(1);
            metaAuthCheck.setMetaType(MetaTypeEnum.WORKFLOW.getName());
            userAuth = this.metaAuthService.checkUserAuth(metaAuthCheck);
            hashMap = new HashMap<String, Integer>(16);
            HashSet groupSet = new HashSet(8);
            for (String string : userAuth) {
                groupSet.add(string);
                this.metaGroupService.addChildrenToSet(string, groupRelationship, groupSet);
                this.metaGroupService.addParentToSet(string, groupRelationship, groupSet);
                hashMap.put(string, 1);
            }
            ArrayList<MetaGroupDO> infoTemp = new ArrayList<MetaGroupDO>();
            for (MetaGroupDO groupDO : groupDOs) {
                if (groupDO.getMetaType().equals(MetaTypeEnum.WORKFLOW.getName()) && !groupSet.contains(groupDO.getUniqueCode())) continue;
                infoTemp.add(groupDO);
            }
            groupDOs = infoTemp;
        }
        for (MetaGroupDO metaGroupDO : groupDOs) {
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setId(metaGroupDO.getId());
            infoDTO.setName(metaGroupDO.getName());
            infoDTO.setTitle(metaGroupDO.getTitle());
            infoDTO.setModuleName(metaGroupDO.getModuleName());
            infoDTO.setParentName(metaGroupDO.getParentName());
            infoDTO.setUniqueCode(metaGroupDO.getUniqueCode());
            infoDTO.setType(MetaType.GROUP);
            infoDTOs.add(infoDTO);
        }
        List<MetaInfoDO> infoDOs = null;
        if (metaType != null) {
            OptionItemVO optionItemVO2;
            infoDOs = this.metaInfoService.getMetaListByMetaType(metaType);
            if (userNow.getMgrFlag().equals("normal") && optionItemList != null && !optionItemList.isEmpty() && (optionItemVO2 = optionItemList.get(0)).getVal().equals("1") && MetaTypeEnum.WORKFLOW.getName().equals(metaType)) {
                metaAuthCheck = new MetaAuthDTO();
                metaAuthCheck.setTenantName(ShiroUtil.getTenantName());
                metaAuthCheck.setGroupflag(0);
                metaAuthCheck.setMetaType(metaType);
                userAuth = this.metaAuthService.checkUserAuth(metaAuthCheck);
                hashMap = new HashMap(16);
                for (String name : userAuth) {
                    hashMap.put(name, 1);
                }
                ArrayList<MetaInfoDO> infoTemp = new ArrayList<MetaInfoDO>();
                for (MetaInfoDO metaInfoDO : infoDOs) {
                    if (!hashMap.containsKey(metaInfoDO.getUniqueCode())) continue;
                    infoTemp.add(metaInfoDO);
                }
                if (!infoTemp.isEmpty()) {
                    infoDOs = infoTemp;
                }
            }
        } else {
            infoDOs = this.metaInfoService.getMetaListByMetaType(MetaTypeEnum.BILL.getName());
        }
        TenantDO tenantDO = new TenantDO();
        List relations = null;
        if (MetaTypeEnum.WORKFLOW.getName().equals(metaType)) {
            ModuleServer itemServer = ModulesServerProvider.getModuleServer("*", metaType);
            BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)itemServer.getServer(), (String)itemServer.getRealPath());
            R workflowVersions = bizClient.getWorkflowVersion(tenantDO);
            relations = (List)workflowVersions.get((Object)"relations");
        }
        for (MetaInfoDO metaInfoDO : infoDOs) {
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setId(metaInfoDO.getId());
            infoDTO.setName(metaInfoDO.getName());
            infoDTO.setTitle(metaInfoDO.getTitle());
            infoDTO.setModuleName(metaInfoDO.getModuleName());
            infoDTO.setGroupName(metaInfoDO.getGroupName());
            infoDTO.setUniqueCode(metaInfoDO.getUniqueCode());
            infoDTO.setModelName(metaInfoDO.getModelName());
            infoDTO.setType(MetaType.METADATA);
            infoDTO.setVersion(metaInfoDO.getVersionNO());
            if (relations != null) {
                relations.forEach(workflowRelation -> {
                    String workflowdefinekey = (String)workflowRelation.get("WORKFLOWDEFINEKEY");
                    Long metaVersion = (Long)workflowRelation.get("METAVERSION");
                    if (metaInfoDO.getUniqueCode().equals(workflowdefinekey) && metaInfoDO.getVersionNO().longValue() == metaVersion.longValue()) {
                        infoDTO.setSimpleVersion((Integer)workflowRelation.get("WORKFLOWDEFINEVERSION"));
                    }
                });
            }
            infoDTOs.add(infoDTO);
        }
        return pageVO;
    }

    @PostMapping(value={"/language/gather/category"})
    public List<VaI18nResourceItem> gatherCategory(@RequestBody TenantDO param) {
        return this.metaDataResourceGatherService.i18nGatherCategory();
    }

    @PostMapping(value={"/language/gather/resource"})
    public List<VaI18nResourceItem> gatherResource(@RequestBody TenantDO param) {
        return this.metaDataResourceGatherService.i18nGatherResource(param);
    }

    /*
     * WARNING - void declaration
     */
    @PostMapping(value={"/metadata/list"})
    public List<MetaTreeInfoDTO> listAllMetaData(@RequestBody TenantDO tenantDO) {
        void var11_19;
        ArrayList<MetaTreeInfoDTO> list = new ArrayList<MetaTreeInfoDTO>();
        String metaType = (String)tenantDO.getExtInfo("metaType");
        String metaDataUniqueCode = (String)tenantDO.getExtInfo("metaDataUniqueCode");
        String moduleName = (String)tenantDO.getExtInfo("moduleName");
        String metaGroupUniqueCode = (String)tenantDO.getExtInfo("metaGroupUniqueCode");
        MetaModelDTO tempMetaModel = new MetaModelDTO();
        tempMetaModel.setMetaType(metaType);
        MetaBaseInfoVO baseInfoVO = this.getModulesAndTypes(tempMetaModel);
        List<MetaBaseDim> moduleInfoList = baseInfoVO.getBaseInfos();
        moduleInfoList = moduleInfoList.stream().filter(x -> ObjectUtils.isEmpty(x.getModuleName())).collect(Collectors.toList());
        if (StringUtils.hasText(moduleName)) {
            moduleInfoList = moduleInfoList.stream().filter(x -> moduleName.equals(x.getName())).collect(Collectors.toList());
        }
        for (MetaBaseDim metaBaseDim : moduleInfoList) {
            MetaTreeInfoDTO metaTreeInfoDTO = new MetaTreeInfoDTO();
            metaTreeInfoDTO.setName(metaBaseDim.getName());
            metaTreeInfoDTO.setTitle(metaBaseDim.getTitle());
            metaTreeInfoDTO.setType(MetaType.MODULE);
            metaTreeInfoDTO.setUniqueCode(metaBaseDim.getName());
            list.add(metaTreeInfoDTO);
        }
        List<Object> metaGroupDOList = new ArrayList();
        if (StringUtils.hasText(metaType)) {
            metaGroupDOList = this.metaGroupService.getGroupListByMetaType(metaType);
        } else {
            for (MetaBaseDim metaBaseDim : moduleInfoList) {
                String name = metaBaseDim.getName();
                List<MetaGroupDO> tempMetaGroupList = this.metaGroupService.getGroupListByModule(name);
                if (CollectionUtils.isEmpty(tempMetaGroupList)) continue;
                metaGroupDOList.addAll(tempMetaGroupList);
            }
        }
        if (StringUtils.hasText(metaGroupUniqueCode)) {
            metaGroupDOList = metaGroupDOList.stream().filter(x -> metaGroupUniqueCode.equals(x.getUniqueCode())).collect(Collectors.toList());
        }
        for (MetaGroupDO metaGroupDO : metaGroupDOList) {
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setId(metaGroupDO.getId());
            infoDTO.setName(metaGroupDO.getName());
            infoDTO.setTitle(metaGroupDO.getTitle());
            infoDTO.setModuleName(metaGroupDO.getModuleName());
            infoDTO.setParentName(metaGroupDO.getParentName());
            infoDTO.setUniqueCode(metaGroupDO.getUniqueCode());
            infoDTO.setType(MetaType.GROUP);
            list.add(infoDTO);
        }
        if (StringUtils.hasText(metaType)) {
            List<MetaInfoDO> list2 = this.metaInfoService.getMetaListByMetaType(metaType);
        } else {
            List<MetaInfoDO> list3 = this.metaInfoService.getMetaInfoList(new MetaInfoDO());
        }
        if (StringUtils.hasText(metaDataUniqueCode)) {
            void var11_17;
            List list4 = var11_17.stream().filter(x -> metaDataUniqueCode.equals(x.getUniqueCode())).collect(Collectors.toList());
        }
        for (MetaInfoDO metaInfoDO : var11_19) {
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setId(metaInfoDO.getId());
            infoDTO.setName(metaInfoDO.getName());
            infoDTO.setTitle(metaInfoDO.getTitle());
            infoDTO.setModuleName(metaInfoDO.getModuleName());
            infoDTO.setGroupName(metaInfoDO.getGroupName());
            infoDTO.setUniqueCode(metaInfoDO.getUniqueCode());
            infoDTO.setModelName(metaInfoDO.getModelName());
            infoDTO.setType(MetaType.METADATA);
            list.add(infoDTO);
        }
        return list;
    }
}


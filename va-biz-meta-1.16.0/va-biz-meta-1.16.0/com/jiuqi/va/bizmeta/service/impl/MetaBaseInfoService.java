/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.biz.ModelDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BizClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.common.utils.MetaItemServer;
import com.jiuqi.va.bizmeta.common.utils.ModulesServerProvider;
import com.jiuqi.va.bizmeta.domain.dimension.MetaBaseDim;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.domain.multimodule.Modules;
import com.jiuqi.va.bizmeta.service.IMetaBaseInfoService;
import com.jiuqi.va.domain.biz.MetaDataDTO;
import com.jiuqi.va.domain.biz.ModelDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BizClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MetaBaseInfoService
implements IMetaBaseInfoService {
    private static final Logger logger = LoggerFactory.getLogger(MetaBaseInfoService.class);

    @Override
    public List<ModelDTO> gatherModels(ModelDTO modelDTO) {
        ModuleServer ms = ModulesServerProvider.getModuleServer(modelDTO.getModule(), modelDTO.getMetaType());
        if (ms == null) {
            return null;
        }
        BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)ms.getServer(), (String)ms.getRealPath());
        try {
            PageVO pageVO = bizClient.getModels(modelDTO);
            return pageVO.getRows();
        }
        catch (Exception e) {
            logger.error(ms.getName() + "\u83b7\u53d6\u5f02\u5e38", e);
            return new ArrayList<ModelDTO>();
        }
    }

    @Override
    public List<ModelDTO> gatherModelsAll() {
        ArrayList<ModelDTO> modelDTOs = new ArrayList<ModelDTO>();
        ArrayList<MetaBaseDim> metaBaseDims = new ArrayList<MetaBaseDim>();
        for (MetaTypeEnum metaType : MetaTypeEnum.values()) {
            for (ModuleServer moduleServer : Modules.getModules()) {
                MetaBaseDim dim = new MetaBaseDim(metaType.getName(), metaType.getTitle(), moduleServer.getName());
                metaBaseDims.add(dim);
            }
        }
        for (MetaBaseDim metaBaseDim : metaBaseDims) {
            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setMetaType(metaBaseDim.getName());
            modelDTO.setModule(metaBaseDim.getModuleName());
            List<ModelDTO> dtos = this.gatherModels(modelDTO);
            if (dtos == null) continue;
            modelDTOs.addAll(dtos);
        }
        return modelDTOs;
    }

    @Override
    public MetaDataDTO gatherMetaData(MetaDataDTO metaDataDTO) {
        ModuleServer ms = ModulesServerProvider.getModuleServer(metaDataDTO.getModule(), metaDataDTO.getMetaType());
        BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)ms.getServer(), (String)ms.getRealPath());
        R retuanData = bizClient.createDefine(metaDataDTO);
        String returnValue = "";
        Object rtnObj = retuanData.get((Object)"data");
        if (rtnObj != null) {
            returnValue = rtnObj instanceof Map ? JSONUtil.toJSONString((Object)rtnObj) : rtnObj.toString();
        }
        metaDataDTO.setDatas(returnValue);
        return metaDataDTO;
    }

    @Override
    public List<Map<String, Object>> gatherPluginsAll(String modelType, String uniqueCode) {
        ArrayList<Map<String, Object>> allPlugins = new ArrayList<Map<String, Object>>();
        TenantDO param = new TenantDO();
        param.addExtInfo("modelType", (Object)modelType);
        param.addExtInfo("uniqueCode", (Object)uniqueCode);
        Arrays.asList(MetaItemServer.values()).forEach(type -> {
            String[] moduleName = uniqueCode.split("_");
            if (moduleName.length == 0) {
                return;
            }
            ModuleServer moduleServer = ModulesServerProvider.getModuleServer(moduleName[0], type.getMetaType());
            BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)moduleServer.getServer(), (String)moduleServer.getPath());
            R r = bizClient.getAllPlugins(param);
            List plugins = (List)r.get((Object)"plugins");
            if (allPlugins.isEmpty()) {
                allPlugins.addAll(plugins);
            } else {
                plugins.forEach(plugin -> {
                    Map result = allPlugins.stream().filter(o -> plugin.get("name").equals(o.get("name"))).findFirst().orElse(null);
                    if (result == null) {
                        allPlugins.add((Map<String, Object>)plugin);
                    }
                });
            }
        });
        return allPlugins;
    }
}


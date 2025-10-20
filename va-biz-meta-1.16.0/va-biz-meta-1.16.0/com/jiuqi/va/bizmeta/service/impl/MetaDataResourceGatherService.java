/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.I18nPluginManager
 *  com.jiuqi.va.biz.intf.model.I18nPlugin
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.intf.model.PluginManager
 *  com.jiuqi.va.biz.intf.model.PluginType
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.feign.client.BizClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.biz.impl.model.I18nPluginManager;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.model.PluginManager;
import com.jiuqi.va.biz.intf.model.PluginType;
import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.common.utils.ModulesServerProvider;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.domain.multimodule.Modules;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.feign.client.BizClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MetaDataResourceGatherService {
    @Autowired
    private IMetaGroupService metaGroupService;
    @Autowired
    private IMetaInfoService metaInfoService;
    @Autowired
    private PluginManager pluginManager;
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private I18nPluginManager i18nPluginManager;

    public List<VaI18nResourceItem> i18nGatherCategory() {
        ArrayList<VaI18nResourceItem> metadataModuleResources = new ArrayList<VaI18nResourceItem>();
        this.gatherMetaDataModules(metadataModuleResources);
        return metadataModuleResources;
    }

    public List<VaI18nResourceItem> i18nGatherResource(TenantDO param) {
        String parentsStr = (String)param.getExtInfo("parents");
        String[] parents = parentsStr.split("#");
        if (parents.length <= 0) {
            return null;
        }
        int length = parents.length;
        param.addExtInfo("pluginName", (Object)parents[length - 1]);
        param.addExtInfo("defineName", (Object)parents[length - 2]);
        ModuleServer itemServer = ModulesServerProvider.getModuleServer(parents[1], parents[2]);
        BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)itemServer.getServer(), (String)itemServer.getRealPath());
        return bizClient.getI18nPluginsResource(param);
    }

    private void gatherMetaDataModules(List<VaI18nResourceItem> metadataModuleResources) {
        for (ModuleServer moduleServer : Modules.getModules()) {
            VaI18nResourceItem metaDataModuleItem = new VaI18nResourceItem();
            metaDataModuleItem.setName(moduleServer.getName());
            metaDataModuleItem.setTitle(moduleServer.getTitle());
            metadataModuleResources.add(metaDataModuleItem);
            ArrayList<VaI18nResourceItem> metadataTypeResources = new ArrayList<VaI18nResourceItem>();
            metaDataModuleItem.setChildren(metadataTypeResources);
            this.gatherMetaDataTypes(moduleServer, metadataTypeResources);
        }
    }

    private void gatherMetaDataTypes(ModuleServer moduleServer, List<VaI18nResourceItem> metadataTypeResources) {
        for (MetaTypeEnum metaType : MetaTypeEnum.values()) {
            VaI18nResourceItem metaDataTypeItem = new VaI18nResourceItem();
            metaDataTypeItem.setName(metaType.getName());
            metaDataTypeItem.setTitle(metaType.getTitle());
            metadataTypeResources.add(metaDataTypeItem);
            ArrayList metaGroupsResources = new ArrayList();
            metaDataTypeItem.setChildren(metaGroupsResources);
            HashMap<String, VaI18nResourceItem> metaGroupResource = new HashMap<String, VaI18nResourceItem>();
            List<MetaGroupDTO> groupDOs = this.metaGroupService.getGroupList(moduleServer.getName(), metaType.getName(), OperateType.EXECUTE);
            for (MetaGroupDTO group : groupDOs) {
                VaI18nResourceItem metaGroupItem = new VaI18nResourceItem();
                metaGroupItem.setName(group.getName());
                metaGroupItem.setTitle(group.getTitle());
                metaGroupResource.put(group.getName(), metaGroupItem);
            }
            MetaInfoPageDTO infoPageDTO = new MetaInfoPageDTO();
            infoPageDTO.setMetaType(metaType.getName());
            infoPageDTO.setPagination(false);
            infoPageDTO.setOperateType(OperateType.EXECUTE);
            infoPageDTO.setModule(moduleServer.getName());
            List<MetaInfoDTO> metaInfoList = this.metaInfoService.getMetaList(infoPageDTO);
            if (metaInfoList == null || metaInfoList.size() <= 0) continue;
            for (MetaInfoDTO metaInfo : metaInfoList) {
                String groupName;
                List plugins;
                VaI18nResourceItem metaInfoItem = new VaI18nResourceItem();
                metaInfoItem.setName(metaInfo.getUniqueCode());
                metaInfoItem.setTitle(metaInfo.getTitle());
                ModelType modelType = (ModelType)this.modelManager.find(metaInfo.getModelName());
                if (modelType != null && (plugins = this.pluginManager.getPluginList(modelType.getModelClass())) != null && plugins.size() > 0) {
                    ArrayList<VaI18nResourceItem> i18nPlugins = new ArrayList<VaI18nResourceItem>();
                    for (PluginType plugin : plugins) {
                        I18nPlugin i18nPlugin = (I18nPlugin)this.i18nPluginManager.find(plugin.getName());
                        if (i18nPlugin == null) continue;
                        VaI18nResourceItem pluginResourceItem = new VaI18nResourceItem();
                        pluginResourceItem.setName(i18nPlugin.getName());
                        pluginResourceItem.setTitle(i18nPlugin.getTitle());
                        i18nPlugins.add(pluginResourceItem);
                    }
                    metaInfoItem.setChildren(i18nPlugins);
                }
                if (!StringUtils.hasText(groupName = metaInfo.getGroupName()) || !metaGroupResource.containsKey(groupName)) continue;
                VaI18nResourceItem metaGroupItem = (VaI18nResourceItem)metaGroupResource.get(groupName);
                ArrayList<VaI18nResourceItem> metaInfosResource = metaGroupItem.getChildren();
                if (metaInfosResource == null) {
                    metaInfosResource = new ArrayList<VaI18nResourceItem>();
                    metaGroupItem.setChildren(metaInfosResource);
                }
                metaInfosResource.add(metaInfoItem);
            }
            for (MetaGroupDTO group : groupDOs) {
                String parentGroupName = group.getParentName();
                if (StringUtils.hasText(parentGroupName) && metaGroupResource.containsKey(parentGroupName)) {
                    VaI18nResourceItem metaGroupItem = (VaI18nResourceItem)metaGroupResource.get(parentGroupName);
                    ArrayList metaChildGroupResource = metaGroupItem.getChildren();
                    if (metaChildGroupResource == null) {
                        metaChildGroupResource = new ArrayList();
                        metaGroupItem.setChildren(metaChildGroupResource);
                    }
                    metaChildGroupResource.add(metaGroupResource.get(group.getName()));
                    continue;
                }
                metaGroupsResources.add(metaGroupResource.get(group.getName()));
            }
        }
    }
}


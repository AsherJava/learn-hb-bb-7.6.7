/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine
 *  com.jiuqi.nr.definition.internal.service.DesignDataLinkMappingDefineService
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 */
package com.jiuqi.nr.task.form.link.service.impl;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkMappingDefineService;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.form.controller.dto.EntityModeDTO;
import com.jiuqi.nr.task.form.controller.dto.LinkMappingDataDTO;
import com.jiuqi.nr.task.form.link.dto.LinkMappingDTO;
import com.jiuqi.nr.task.form.link.service.ILinkMappingService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkMappingServiceImpl
implements ILinkMappingService {
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DesignDataLinkMappingDefineService designDataLinkMappingDefineService;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @Override
    public List<EntityModeDTO> queryEntityLevel(List<String> entityIds) {
        if (entityIds == null) {
            return Collections.emptyList();
        }
        ArrayList<EntityModeDTO> res = new ArrayList<EntityModeDTO>();
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        for (String entityId : entityIds) {
            if (!BaseDataAdapterUtil.isBaseData((String)entityId)) continue;
            String tableName = entityId.split("@")[0];
            baseDataDefineDTO.setName(tableName);
            BaseDataDefineDO defineDO = this.baseDataDefineClient.get(baseDataDefineDTO);
            if (defineDO == null) continue;
            EntityModeDTO entityModeDTO = new EntityModeDTO();
            entityModeDTO.setEntityId(entityId);
            entityModeDTO.setCode(defineDO.getName());
            entityModeDTO.setTitle(defineDO.getTitle());
            entityModeDTO.setMaxLevel(this.getMaxLevel(tableName));
            res.add(entityModeDTO);
        }
        return res;
    }

    private int getMaxLevel(String tableName) {
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(tableName);
        PageVO pageVO = this.baseDataClient.list(baseDataDTO);
        int maxLevel = 0;
        if (pageVO != null && pageVO.getTotal() > 0) {
            for (BaseDataDO row : pageVO.getRows()) {
                String parents = row.getParents();
                if (parents == null) continue;
                int count = 1;
                for (int i = 0; i < parents.length(); ++i) {
                    if (parents.charAt(i) != '/') continue;
                    ++count;
                }
                maxLevel = Math.max(count, maxLevel);
            }
        }
        return maxLevel;
    }

    @Override
    public void saveLinkMapping(String formKey, List<LinkMappingDTO> dataLinkMapping) {
        if (dataLinkMapping != null) {
            this.designTimeViewController.deleteDataLinkMappingByForm(formKey);
            DesignDataLinkMappingDefine[] defines = new DesignDataLinkMappingDefine[dataLinkMapping.size()];
            for (int i = 0; i < dataLinkMapping.size(); ++i) {
                DesignDataLinkMappingDefine define = this.designTimeViewController.initDataLinkMapping();
                define.setFormKey(formKey);
                define.setLeftDataLinkKey(dataLinkMapping.get(i).getLeftLinkKey());
                define.setRightDataLinkKey(dataLinkMapping.get(i).getRightLinkKey());
                defines[i] = define;
            }
            this.designTimeViewController.insertDataLinkMapping(formKey, defines);
        }
    }

    @Override
    public LinkMappingDataDTO queryLinkMappingData(String formKey, List<LinkMappingDTO> mappings) {
        LinkMappingDataDTO res = new LinkMappingDataDTO();
        if (formKey != null) {
            ArrayList<LinkMappingDTO> mappingDTOS = new ArrayList<LinkMappingDTO>();
            res.setLinkMapping(mappingDTOS);
            List dataLinkMapping = this.designDataLinkMappingDefineService.getDataLinkMapping(formKey);
            for (DesignDataLinkMappingDefine define : dataLinkMapping) {
                LinkMappingDTO dto = new LinkMappingDTO();
                dto.setId(define.getId());
                dto.setLeftLinkKey(define.getLeftDataLinkKey());
                dto.setRightLinkKey(define.getRightDataLinkKey());
                mappingDTOS.add(dto);
            }
        }
        if (mappings != null) {
            HashMap<String, List<String>> relations = new HashMap<String, List<String>>();
            res.setRelations(relations);
            for (LinkMappingDTO mapping : mappings) {
                if (mapping.getEntityId() == null) continue;
                ArrayList<String> links = new ArrayList<String>();
                for (LinkMappingDTO temp : mappings) {
                    if (temp.getEntityId() == null || !this.entityMetaService.estimateEntityRefer(mapping.getEntityId(), temp.getEntityId())) continue;
                    links.add(temp.getLeftLinkKey());
                }
                if (links.isEmpty()) continue;
                relations.put(mapping.getLeftLinkKey(), links);
            }
        }
        return res;
    }
}


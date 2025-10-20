/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 */
package com.jiuqi.dc.datamapping.impl.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DataConversionManagementTransferModule
implements DcTransferModule {
    private static final String MODULE_NAME = "DC_YbzTransferModule";
    private static final String DATA_SCHEME = "DataScheme";
    private static final String DATA_SCHEME_TITLE = "\u6570\u636e\u6620\u5c04\u65b9\u6848";
    private static final String DATA_REF_MAPPING = "DataRefMapping";
    private static final String DATA_REF_MAPPING_TITLE = "\u6570\u636e\u6620\u5c04";
    private static final String DATA_MAPPING_GROUP_PRFX = "dataScheme##";
    private static final String DATA_MAPPING_DEFINE_PRFX = "define##";
    private static final String DEFINE_NODEID_SPLIT_CHAR = "-";
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private BaseDataRefDefineService baseDataRefDefineService;
    @Autowired
    private DataRefConfigureService configureService;
    @Autowired
    private DataRefConfigureDao dataRefConfigureDao;
    @Autowired
    IsolateRefDefineCacheProvider isolateRefDefineCacheProvider;

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categorys = new ArrayList<VaParamTransferCategory>();
        VaParamTransferCategory category = new VaParamTransferCategory();
        category.setName(DATA_SCHEME);
        category.setTitle(DATA_SCHEME_TITLE);
        category.setSupportExport(true);
        category.setSupportExportData(false);
        categorys.add(category);
        category = new VaParamTransferCategory();
        category.setName(DATA_REF_MAPPING);
        category.setTitle(DATA_REF_MAPPING_TITLE);
        category.setSupportExport(true);
        category.setSupportExportData(false);
        categorys.add(category);
        return categorys;
    }

    public List<VaParamTransferFolderNode> getFolderNodes(String category, String parent) {
        if (!StringUtils.hasText(category)) {
            return null;
        }
        if (StringUtils.hasText(parent)) {
            return null;
        }
        switch (category) {
            case "DataScheme": {
                return null;
            }
            case "DataRefMapping": {
                ArrayList<VaParamTransferFolderNode> businessNodes = new ArrayList<VaParamTransferFolderNode>();
                List allDataSchemes = this.dataSchemeService.listAll();
                for (DataSchemeDTO dataScheme : allDataSchemes) {
                    VaParamTransferFolderNode node = new VaParamTransferFolderNode();
                    node.setId(DATA_MAPPING_GROUP_PRFX + dataScheme.getCode());
                    node.setName(dataScheme.getCode());
                    node.setTitle(dataScheme.getName());
                    businessNodes.add(node);
                }
                return businessNodes;
            }
        }
        return null;
    }

    public VaParamTransferFolderNode getFolderNode(String category, String nodeId) {
        if (!StringUtils.hasText(category)) {
            return null;
        }
        if (!StringUtils.hasText(nodeId) || !nodeId.startsWith(DATA_MAPPING_GROUP_PRFX)) {
            return null;
        }
        switch (category) {
            case "DataScheme": {
                return null;
            }
            case "DataRefMapping": {
                DataSchemeDTO dataSchemeDTO = this.dataSchemeService.findByCode(nodeId.substring(DATA_MAPPING_GROUP_PRFX.length()));
                if (dataSchemeDTO == null) {
                    return null;
                }
                VaParamTransferFolderNode node = new VaParamTransferFolderNode();
                node.setId(DATA_MAPPING_GROUP_PRFX + dataSchemeDTO.getCode());
                node.setName(dataSchemeDTO.getCode());
                node.setTitle(dataSchemeDTO.getName());
                return node;
            }
        }
        return null;
    }

    public VaParamTransferFolderNode addFolderNode(String category, VaParamTransferFolderNode node) {
        return node;
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String parent) {
        if (!StringUtils.hasText(category)) {
            return null;
        }
        ArrayList<VaParamTransferBusinessNode> businessNodes = new ArrayList<VaParamTransferBusinessNode>();
        switch (category) {
            case "DataScheme": {
                List dataSchemes = this.dataSchemeService.listAll();
                if (CollectionUtils.isEmpty(dataSchemes)) {
                    return businessNodes;
                }
                for (DataSchemeDTO dataScheme : dataSchemes) {
                    VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
                    node.setId(dataScheme.getCode());
                    node.setName(dataScheme.getCode());
                    node.setTitle(dataScheme.getName());
                    businessNodes.add(node);
                }
                return businessNodes;
            }
            case "DataRefMapping": {
                if (!StringUtils.hasText(parent) || !parent.startsWith(DATA_MAPPING_GROUP_PRFX)) {
                    return null;
                }
                String dataSchemeCode = parent.substring(DATA_MAPPING_GROUP_PRFX.length());
                List<DataMappingDefineDTO> dataMappingDefines = this.configureService.listDefine(dataSchemeCode);
                if (CollectionUtils.isEmpty(dataMappingDefines)) {
                    return businessNodes;
                }
                DataRefListDTO dataRefListDTO = new DataRefListDTO();
                dataRefListDTO.setPagination(false);
                for (DataMappingDefineDTO defineDTO : dataMappingDefines) {
                    String id = DATA_MAPPING_DEFINE_PRFX + defineDTO.getDataSchemeCode() + DEFINE_NODEID_SPLIT_CHAR + defineDTO.getCode();
                    List<DataRefDTO> dataRefDTOList = this.isolateRefDefineCacheProvider.getBaseMappingCache(new IsolationParamContext(defineDTO.getDataSchemeCode()), Objects.requireNonNull(this.getDataMappingDefineByNodeId(DATA_REF_MAPPING, id)).getCode());
                    if (CollectionUtils.isEmpty(dataRefDTOList)) continue;
                    VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
                    node.setId(id);
                    node.setName(defineDTO.getCode());
                    node.setTitle(defineDTO.getName());
                    businessNodes.add(node);
                }
                return businessNodes;
            }
        }
        return null;
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        if (!StringUtils.hasText(category)) {
            return null;
        }
        VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
        switch (category) {
            case "DataScheme": {
                DataSchemeDTO dataScheme = this.dataSchemeService.findByCode(nodeId);
                if (Objects.isNull(dataScheme)) {
                    return null;
                }
                node.setId(dataScheme.getCode());
                node.setName(dataScheme.getCode());
                node.setTitle(dataScheme.getName());
                node.setType(DATA_SCHEME);
                node.setTypeTitle(DATA_SCHEME_TITLE);
                return node;
            }
            case "DataRefMapping": {
                if (!StringUtils.hasText(nodeId) || !nodeId.startsWith(DATA_MAPPING_DEFINE_PRFX)) {
                    return null;
                }
                BaseDataMappingDefineDTO defineDTO = this.getDataMappingDefineByNodeId(category, nodeId);
                if (Objects.isNull(defineDTO)) {
                    return null;
                }
                node.setId(nodeId);
                node.setName(defineDTO.getCode());
                node.setTitle(defineDTO.getName());
                node.setType(DATA_REF_MAPPING);
                node.setTypeTitle(DATA_REF_MAPPING_TITLE);
                node.setFolderId(DATA_MAPPING_GROUP_PRFX + defineDTO.getDataSchemeCode());
                return node;
            }
        }
        return null;
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        if (!StringUtils.hasText(category)) {
            return null;
        }
        ArrayList<VaParamTransferFolderNode> folders = new ArrayList<VaParamTransferFolderNode>();
        switch (category) {
            case "DataScheme": {
                return folders;
            }
            case "DataRefMapping": {
                if (!StringUtils.hasText(nodeId) || !nodeId.startsWith(DATA_MAPPING_DEFINE_PRFX)) {
                    return null;
                }
                String[] codes = nodeId.substring(DATA_MAPPING_DEFINE_PRFX.length()).split(DEFINE_NODEID_SPLIT_CHAR);
                DataSchemeDTO dataScheme = this.dataSchemeService.getByCode(codes[0]);
                VaParamTransferFolderNode node = new VaParamTransferFolderNode();
                node.setId(DATA_MAPPING_GROUP_PRFX + dataScheme.getCode());
                node.setName(dataScheme.getCode());
                node.setTitle(dataScheme.getName());
                folders.add(node);
                return folders;
            }
        }
        return null;
    }

    public List<VaParamTransferBusinessNode> getRelatedBusiness(String category, String nodeId) {
        if (!StringUtils.hasText(category) || !StringUtils.hasText(nodeId)) {
            return null;
        }
        ArrayList<VaParamTransferBusinessNode> resList = new ArrayList<VaParamTransferBusinessNode>();
        switch (category) {
            case "DataScheme": {
                List<DataMappingDefineDTO> dataMappingDefines = this.configureService.listDefine(nodeId);
                if (CollectionUtils.isEmpty(dataMappingDefines)) {
                    return resList;
                }
                DataRefListDTO dataRefListDTO = new DataRefListDTO();
                dataRefListDTO.setPagination(false);
                for (DataMappingDefineDTO defineDTO : dataMappingDefines) {
                    String id = DATA_MAPPING_DEFINE_PRFX + defineDTO.getDataSchemeCode() + DEFINE_NODEID_SPLIT_CHAR + defineDTO.getCode();
                    List<DataRefDTO> dataRefDTOList = this.isolateRefDefineCacheProvider.getBaseMappingCache(new IsolationParamContext(defineDTO.getDataSchemeCode()), Objects.requireNonNull(this.getDataMappingDefineByNodeId(DATA_REF_MAPPING, id)).getCode());
                    if (CollectionUtils.isEmpty(dataRefDTOList)) continue;
                    VaParamTransferBusinessNode resNode = new VaParamTransferBusinessNode();
                    resNode.setId(DATA_MAPPING_DEFINE_PRFX + nodeId + DEFINE_NODEID_SPLIT_CHAR + defineDTO.getCode());
                    resNode.setCategoryId(DATA_REF_MAPPING);
                    resNode.setModuleId(MODULE_NAME);
                    resList.add(resNode);
                }
                return resList;
            }
            case "DataRefMapping": {
                if (!nodeId.startsWith(DATA_MAPPING_DEFINE_PRFX)) {
                    return null;
                }
                String[] codes = nodeId.substring(DATA_MAPPING_DEFINE_PRFX.length()).split(DEFINE_NODEID_SPLIT_CHAR);
                DataSchemeDTO dataScheme = this.dataSchemeService.findByCode(codes[0]);
                VaParamTransferBusinessNode resNode = new VaParamTransferBusinessNode();
                resNode.setId(dataScheme.getCode());
                resNode.setCategoryId(DATA_SCHEME);
                resNode.setModuleId(MODULE_NAME);
                resList.add(resNode);
                return resList;
            }
        }
        return null;
    }

    public String getExportModelInfo(String category, String nodeId) {
        if (!StringUtils.hasText(category)) {
            return null;
        }
        if (!StringUtils.hasText(nodeId)) {
            return null;
        }
        switch (category) {
            case "DataScheme": {
                DataSchemeDTO dataScheme = this.dataSchemeService.findByCode(nodeId);
                return dataScheme != null ? JsonUtils.writeValueAsString((Object)dataScheme) : null;
            }
            case "DataRefMapping": {
                if (!nodeId.startsWith(DATA_MAPPING_DEFINE_PRFX)) {
                    return null;
                }
                DataRefListDTO dataRefListDTO = new DataRefListDTO();
                dataRefListDTO.setPagination(false);
                List<DataRefDTO> list = this.dataRefConfigureDao.selectHasref(this.getDataMappingDefineByNodeId(category, nodeId), dataRefListDTO);
                return !CollectionUtils.isEmpty(list) ? JsonUtils.writeValueAsString(list) : JsonUtils.writeValueAsString(new ArrayList());
            }
        }
        return null;
    }

    public void importModelInfo(String category, String info) {
        if (!StringUtils.hasText(category)) {
            return;
        }
        if (!StringUtils.hasText(info)) {
            return;
        }
        switch (category) {
            case "DataScheme": {
                DataSchemeDTO dataScheme = (DataSchemeDTO)JsonUtils.readValue((String)info, DataSchemeDTO.class);
                Assert.isNotNull((Object)dataScheme);
                DataSchemeDTO oldDataScheme = this.dataSchemeService.findByCode(dataScheme.getCode());
                if (Objects.isNull(oldDataScheme)) {
                    this.dataSchemeService.create(dataScheme);
                } else {
                    dataScheme.setId(oldDataScheme.getId());
                    dataScheme.setVer(oldDataScheme.getVer());
                    this.dataSchemeService.modify(dataScheme);
                }
                return;
            }
            case "DataRefMapping": {
                List dataRefDTOList = JSONUtil.parseArray((String)info, DataRefDTO.class);
                if (CollectionUtils.isEmpty(dataRefDTOList)) {
                    return;
                }
                BaseDataMappingDefineDTO baseDataRefDefine = this.baseDataRefDefineService.findByCode(((DataRefDTO)dataRefDTOList.get(0)).getDataSchemeCode(), ((DataRefDTO)dataRefDTOList.get(0)).getTableName());
                DataRefListDTO dataRefListDTO = new DataRefListDTO();
                dataRefListDTO.setPagination(false);
                Set hasRefIdSet = this.dataRefConfigureDao.selectHasref(baseDataRefDefine, dataRefListDTO).stream().map(DataRefDTO::getId).collect(Collectors.toSet());
                ArrayList<DataRefDTO> createList = new ArrayList<DataRefDTO>();
                ArrayList<DataRefDTO> updateList = new ArrayList<DataRefDTO>();
                for (DataRefDTO dto : dataRefDTOList) {
                    dto.setOperateTime(new Date());
                    if (!hasRefIdSet.contains(dto.getId())) {
                        createList.add(dto);
                        continue;
                    }
                    updateList.add(dto);
                }
                if (!createList.isEmpty()) {
                    this.dataRefConfigureDao.batchInsert(baseDataRefDefine, createList);
                }
                if (!updateList.isEmpty()) {
                    this.dataRefConfigureDao.batchUpdate(baseDataRefDefine, updateList);
                }
                return;
            }
        }
    }

    public String getExportDataInfo(String category, String nodeId) {
        return super.getExportDataInfo(category, nodeId);
    }

    public void importDataInfo(String category, String targetId, String info) {
        super.importDataInfo(category, targetId, info);
    }

    public List<String> getDependenceFactoryIds(String category) {
        if (!StringUtils.hasText(category)) {
            return null;
        }
        ArrayList<String> factoryIds = new ArrayList<String>();
        if (category.equals(DATA_REF_MAPPING)) {
            factoryIds.add(DATA_SCHEME);
        }
        return factoryIds;
    }

    private BaseDataMappingDefineDTO getDataMappingDefineByNodeId(String category, String nodeId) {
        if (!DATA_REF_MAPPING.equals(category)) {
            return null;
        }
        if (!nodeId.startsWith(DATA_MAPPING_DEFINE_PRFX) || !nodeId.contains(DEFINE_NODEID_SPLIT_CHAR)) {
            return null;
        }
        String[] codes = nodeId.substring(DATA_MAPPING_DEFINE_PRFX.length()).split(DEFINE_NODEID_SPLIT_CHAR);
        if (Objects.isNull(this.dataSchemeService.findByCode(codes[0]))) {
            return null;
        }
        return this.baseDataRefDefineService.findByCode(codes[0], codes[1]);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.biz.intf.meta.MetaState
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.biz.intf.meta.MetaState;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.workflow.dao.WorkflowPublicParamRelationDao;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.service.WorkflowPublicParamRelationService;
import com.jiuqi.va.workflow.service.WorkflowPublicParamService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowPublicParamRelationServiceImpl
implements WorkflowPublicParamRelationService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowPublicParamRelationServiceImpl.class);
    @Autowired
    private WorkflowPublicParamRelationDao publicParamRelationDao;
    @Autowired
    private WorkflowPublicParamService publicParamService;
    @Autowired
    private WorkflowPublicParamRelationService publicParamRelationService;
    @Autowired
    private MetaDataClient metaDataClient;
    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String handleWorkflowPublicParam(String designData, MetaInfoDTO metaInfo) {
        Map designMap = JSONUtil.parseMap((String)designData);
        List plugins = (List)designMap.get("plugins");
        if (plugins == null || plugins.isEmpty()) {
            return designData;
        }
        List processParamList = null;
        Map processDesignMap = null;
        for (Map plugin : plugins) {
            if ("processParamPlugin".equals(plugin.get("type"))) {
                processParamList = (List)plugin.get("processParam");
                continue;
            }
            if (!"processDesignPlugin".equals(plugin.get("type"))) continue;
            processDesignMap = (Map)plugin.get("data");
        }
        if (processDesignMap == null || processDesignMap.isEmpty()) {
            return designData;
        }
        List<Map<String, Object>> newProcessParam = this.parseWorkflowPublicParam(processParamList, processDesignMap, metaInfo);
        for (Map plugin : plugins) {
            if (!"processParamPlugin".equals(plugin.get("type"))) continue;
            plugin.put("processParam", newProcessParam);
        }
        return JSONUtil.toJSONString((Object)designMap, (String)DATEFORMAT, (JsonInclude.Include)JsonInclude.Include.ALWAYS);
    }

    public List<Map<String, Object>> parseWorkflowPublicParam(List<Map<String, Object>> processParamList, Map<String, Object> processDesignMap, MetaInfoDTO metaInfo) {
        ArrayList<Map<String, Object>> newProcessParam = new ArrayList<Map<String, Object>>();
        String processDesignStr = JSONUtil.toJSONString(processDesignMap);
        WorkflowPublicParamDTO publicParamDTO = new WorkflowPublicParamDTO();
        publicParamDTO.setSearchtitle(false);
        publicParamDTO.setSearchdata(false);
        List<WorkflowPublicParamDTO> publicParamList = this.publicParamService.list(publicParamDTO);
        Set publicParamNames = publicParamList.stream().map(WorkflowPublicParamDO::getParamname).collect(Collectors.toSet());
        HashSet<String> publicParams = new HashSet<String>();
        for (String publicParamName : publicParamNames) {
            publicParams.add("[" + publicParamName + "]");
        }
        ArrayList<String> customParamNames = new ArrayList<String>();
        HashMap<String, String> publicParamNameTitleMap = new HashMap<String, String>();
        for (Map<String, Object> param2 : processParamList) {
            Integer ownershipNature = (Integer)param2.get("ownershipNature");
            if (ownershipNature == null || !WorkflowOption.WorkflowParamType.PUBLIC.getType().equals(ownershipNature)) {
                newProcessParam.add(param2);
                customParamNames.add("[" + (String)param2.get("paramName") + "]");
                continue;
            }
            publicParamNameTitleMap.put((String)param2.get("paramName"), (String)param2.get("paramTitle"));
        }
        publicParams.removeIf(customParamNames::contains);
        Set<String> relationParam = publicParams.stream().filter(processDesignStr::contains).collect(Collectors.toSet());
        List<String> newRelationParam = this.filterRelationParams(relationParam, processDesignStr);
        List<WorkflowPublicParamDTO> publicParamDTOS = publicParamList.stream().filter(param -> newRelationParam.contains("[" + param.getParamname() + "]")).collect(Collectors.toList());
        if (metaInfo.getRowVersion() == null) {
            this.publicParamRelationService.handlePublishedPublicParamRel(metaInfo, publicParamDTOS);
        } else {
            this.publicParamRelationService.handleAddPublicParamRelation(metaInfo, publicParamDTOS);
        }
        ArrayList list = new ArrayList();
        for (WorkflowPublicParamDTO paramDTO : publicParamDTOS) {
            HashMap<String, Object> map = new HashMap<String, Object>(8);
            String paramname = paramDTO.getParamname();
            String paramtitle = paramDTO.getParamtitle();
            if (publicParamNameTitleMap.containsKey(paramname)) {
                paramtitle = (String)publicParamNameTitleMap.get(paramname);
            }
            map.put("paramName", paramname);
            map.put("paramTitle", paramtitle);
            map.put("paramType", paramDTO.getParamtype());
            map.put("ownershipNature", WorkflowOption.WorkflowParamType.PUBLIC.getType());
            list.add(map);
        }
        newProcessParam.addAll(list);
        return newProcessParam;
    }

    private List<String> filterRelationParams(Set<String> relationParam, String processDesignStr) {
        ArrayList<String> newRelationParam = new ArrayList<String>();
        for (String param : relationParam) {
            int index = processDesignStr.indexOf(param);
            boolean flag = false;
            while (index != -1) {
                char previousSymbol;
                if (index > 0 && !Character.isLetter(previousSymbol = processDesignStr.charAt(index - 1))) {
                    flag = true;
                    break;
                }
                index = processDesignStr.indexOf(param, index + 1);
            }
            if (!flag) continue;
            newRelationParam.add(param);
        }
        return newRelationParam;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void handlePublishedPublicParamRel(MetaInfoDTO metaInfo, List<WorkflowPublicParamDTO> publicParamDTOS) {
        WorkflowPublicParamRelationDTO dto = new WorkflowPublicParamRelationDTO();
        dto.setDefinekey(metaInfo.getUniqueCode());
        dto.setDefineversion(metaInfo.getVersionNO());
        dto.setUsername(null);
        this.publicParamRelationDao.deleteRelation(dto);
        Set needAddParamNames = publicParamDTOS.stream().map(WorkflowPublicParamDO::getParamname).collect(Collectors.toSet());
        for (String paramName : needAddParamNames) {
            dto.setId(UUID.randomUUID());
            dto.setParamname(paramName);
            this.publicParamRelationDao.insert(dto);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void handleAddPublicParamRelation(MetaInfoDTO metaInfo, List<WorkflowPublicParamDTO> publicParamDTOS) {
        Set needAddParamNames = publicParamDTOS.stream().map(WorkflowPublicParamDO::getParamname).collect(Collectors.toSet());
        String userId = ShiroUtil.getUser().getId();
        if (MetaState.DEPLOYED.getValue() != metaInfo.getState().intValue()) {
            WorkflowPublicParamRelationDTO relationDTO = new WorkflowPublicParamRelationDTO();
            relationDTO.setDefinekey(metaInfo.getUniqueCode());
            relationDTO.setUsername(userId);
            this.publicParamRelationDao.deleteRelation(relationDTO);
            relationDTO.setDefineversion(metaInfo.getRowVersion());
            for (String name : needAddParamNames) {
                relationDTO.setId(UUID.randomUUID());
                relationDTO.setParamname(name);
                this.publicParamRelationDao.insert(relationDTO);
            }
        } else {
            WorkflowPublicParamRelationDTO relationDTO = new WorkflowPublicParamRelationDTO();
            relationDTO.setDefinekey(metaInfo.getUniqueCode());
            relationDTO.setDefineversion(metaInfo.getRowVersion());
            relationDTO.setUsername(userId);
            for (String name : needAddParamNames) {
                relationDTO.setId(UUID.randomUUID());
                relationDTO.setParamname(name);
                this.publicParamRelationDao.insert(relationDTO);
            }
        }
    }

    @Override
    public void updatePublicParamRel(WorkflowPublicParamRelationDTO dto) {
        this.publicParamRelationDao.updatePublicParamRel(dto);
    }

    @Override
    public void deletePublicParamRel(WorkflowPublicParamRelationDTO dto) {
        this.publicParamRelationDao.deleteRelation(dto);
    }

    @Override
    public void delete(WorkflowPublicParamRelationDTO dto) {
        if (StringUtils.hasText(dto.getDefinekey())) {
            this.publicParamRelationDao.delete(dto);
        }
    }

    @Override
    public void workflowPublishUpdate(List<String> ids, MetaInfoEditionDO editionDO) {
        Integer state = editionDO.getMetaState();
        String uniqueCode = editionDO.getUniqueCode();
        Long versionNO = editionDO.getVersionNO();
        WorkflowPublicParamRelationDTO dto = new WorkflowPublicParamRelationDTO();
        dto.setDefineversion(versionNO);
        dto.setDefinekey(uniqueCode);
        if (MetaState.MODIFIED.getValue() == state.intValue()) {
            dto.setUsername(ShiroUtil.getUser().getId());
            List<WorkflowPublicParamRelationDO> relationDOList = this.publicParamRelationDao.list(dto);
            this.publicParamRelationDao.deleteRelation(dto);
            this.updateWorkflowPublishRel(ids, uniqueCode, relationDOList);
        } else if (MetaState.DEPLOYED.getValue() == state.intValue()) {
            dto.setUsername(null);
            List<WorkflowPublicParamRelationDO> relationDOList = this.publicParamRelationDao.list(dto);
            this.updateWorkflowPublishRel(ids, uniqueCode, relationDOList);
        }
    }

    public void updateWorkflowPublishRel(List<String> ids, String uniqueCode, List<WorkflowPublicParamRelationDO> relationDOList) {
        for (String id : ids) {
            MetaInfoDTO metaInfoDTO = new MetaInfoDTO();
            metaInfoDTO.setId(UUID.fromString(id));
            R r = this.metaDataClient.listMetaInfoHis(metaInfoDTO);
            if (r.getCode() != 0) {
                logger.error("\u83b7\u53d6\u5de5\u4f5c\u6d41\u5143\u6570\u636e\u7248\u672c\u5931\u8d25");
                continue;
            }
            Object data = r.get((Object)"data");
            List list = JSONUtil.parseMapArray((String)JSONUtil.toJSONString((Object)data));
            if (CollectionUtils.isEmpty(list)) continue;
            Map metaInfoHisMap = (Map)list.get(0);
            Long hisVersionNO = (Long)metaInfoHisMap.get("versionNO");
            this.publicParamRelationService.updateRel(uniqueCode, relationDOList, hisVersionNO);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateRel(String uniqueCode, List<WorkflowPublicParamRelationDO> relationDOList, Long hisVersionNO) {
        WorkflowPublicParamRelationDTO relationDTO = new WorkflowPublicParamRelationDTO();
        relationDTO.setUsername(null);
        relationDTO.setDefinekey(uniqueCode);
        relationDTO.setDefineversion(hisVersionNO);
        this.publicParamRelationDao.deleteRelation(relationDTO);
        WorkflowPublicParamRelationDO record = new WorkflowPublicParamRelationDO();
        record.setDefinekey(uniqueCode);
        record.setDefineversion(hisVersionNO);
        for (WorkflowPublicParamRelationDO relationDO : relationDOList) {
            record.setId(UUID.randomUUID());
            record.setParamname(relationDO.getParamname());
            this.publicParamRelationDao.insert(record);
        }
    }
}


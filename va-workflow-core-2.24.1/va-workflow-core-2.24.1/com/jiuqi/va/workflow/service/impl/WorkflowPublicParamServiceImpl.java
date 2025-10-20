/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDataDTO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDataDTO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.workflow.dao.WorkflowPublicParamDao;
import com.jiuqi.va.workflow.dao.WorkflowPublicParamDataDao;
import com.jiuqi.va.workflow.dao.WorkflowPublicParamRelationDao;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.service.WorkflowPublicParamDataService;
import com.jiuqi.va.workflow.service.WorkflowPublicParamService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowPublicParamServiceImpl
implements WorkflowPublicParamService {
    @Autowired
    private WorkflowPublicParamDao workflowPublicParamDao;
    @Autowired
    private WorkflowPublicParamDataDao workflowPublicParamDataDao;
    @Autowired
    private WorkflowPublicParamService workflowPublicParamService;
    @Autowired
    private WorkflowPublicParamDataService workflowPublicParamDataService;
    @Autowired
    private WorkflowPublicParamRelationDao publicParamRelationDao;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private EnumDataClient enumDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    @Override
    public void addPublicParam(WorkflowPublicParamDTO publicParamDTO) {
        String paramname = publicParamDTO.getParamname();
        WorkflowPublicParamDTO record = new WorkflowPublicParamDTO();
        record.setParamname(paramname);
        List publicParamList = this.workflowPublicParamDao.select(record);
        this.workflowPublicParamService.add(publicParamDTO, publicParamList);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void add(WorkflowPublicParamDTO publicParamDTO, List<WorkflowPublicParamDO> publicParamList) {
        String data = publicParamDTO.getParamdata();
        if (publicParamList.isEmpty()) {
            UUID id = UUID.randomUUID();
            publicParamDTO.setId(id);
            publicParamDTO.setModifytime(new Date());
            publicParamDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            publicParamDTO.setFixedflag(BigDecimal.ZERO);
            publicParamDTO.setRemoveflag(BigDecimal.ZERO);
            this.workflowPublicParamDao.insert(publicParamDTO);
            if (StringUtils.hasText(data)) {
                WorkflowPublicParamDataDTO dataDTO = new WorkflowPublicParamDataDTO();
                dataDTO.setParamdata(data);
                dataDTO.setId(id);
                this.workflowPublicParamDataDao.insert(dataDTO);
            }
        } else {
            WorkflowPublicParamDO publicParamDO = publicParamList.get(0);
            if (Objects.equals(publicParamDO.getRemoveflag(), BigDecimal.ZERO)) {
                throw new RuntimeException(publicParamDO.getParamname() + VaWorkFlowI18nUtils.getInfo("va.workflow.identifyduplicates"));
            }
            UUID id = publicParamDO.getId();
            publicParamDTO.setId(id);
            publicParamDTO.setRemoveflag(BigDecimal.ZERO);
            publicParamDTO.setModifytime(new Date());
            publicParamDTO.setFixedflag(publicParamDO.getFixedflag());
            publicParamDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            publicParamDTO.setModifytime(new Date());
            this.workflowPublicParamDao.updateByPrimaryKey(publicParamDTO);
            this.workflowPublicParamDataService.updatePublicParamData(data, id);
        }
    }

    @Override
    public void updatePublicParam(WorkflowPublicParamDTO dto) {
        UUID id = dto.getId();
        String paramname = dto.getParamname();
        WorkflowPublicParamDTO record = new WorkflowPublicParamDTO();
        record.setId(id);
        record.setParamname(paramname);
        List publicParamList = this.workflowPublicParamDao.select(record);
        if (CollectionUtils.isEmpty(publicParamList)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.informationdoesnotexist"));
        }
        WorkflowPublicParamDO publicDO = (WorkflowPublicParamDO)publicParamList.get(0);
        if (BigDecimal.ONE.equals(publicDO.getFixedflag())) {
            if (!(Objects.equals(publicDO.getParamtype(), dto.getParamtype()) && Objects.equals(publicDO.getMappingtype(), dto.getMappingtype()) && Objects.equals(publicDO.getMapping(), dto.getMapping()))) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.solidifieddataisnotallowedupdated"));
            }
        } else if (!(Objects.equals(dto.getParamtype(), publicDO.getParamtype()) && Objects.equals(dto.getMapping(), publicDO.getMapping()) || !this.check(dto))) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahasbeenreferencedupdateisnotallowed"));
        }
        this.workflowPublicParamService.update(dto);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(WorkflowPublicParamDTO workflowPublicParamDTO) {
        UUID id = workflowPublicParamDTO.getId();
        workflowPublicParamDTO.setModifytime(new Date());
        this.workflowPublicParamDao.update(workflowPublicParamDTO);
        String data = workflowPublicParamDTO.getParamdata();
        this.workflowPublicParamDataService.updatePublicParamData(data, id);
    }

    @Override
    public void removePublicParam(WorkflowPublicParamDTO publicParamDTO) {
        boolean check = this.check(publicParamDTO);
        if (check) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahasbeenreferenceddeletionisnotallowed"));
        }
        WorkflowPublicParamDTO paramDTO = new WorkflowPublicParamDTO();
        paramDTO.setId(publicParamDTO.getId());
        paramDTO.setRemoveflag(BigDecimal.ONE);
        paramDTO.setFixedflag(BigDecimal.ZERO);
        paramDTO.setModifytime(new Date());
        this.workflowPublicParamDao.updateByPrimaryKeySelective(paramDTO);
    }

    @Override
    public boolean check(WorkflowPublicParamDTO publicParamDTO) {
        WorkflowPublicParamRelationDTO relationDTO = new WorkflowPublicParamRelationDTO();
        relationDTO.setParamname(publicParamDTO.getParamname());
        int count = this.publicParamRelationDao.selectCount(relationDTO);
        return count > 0;
    }

    @Override
    public void updown(WorkflowPublicParamDTO publicParamDTO) {
        String movetype = publicParamDTO.getMovetype();
        UUID currentId = publicParamDTO.getId();
        publicParamDTO.setId(null);
        publicParamDTO.setSearchdata(false);
        publicParamDTO.setSearchtitle(false);
        List<WorkflowPublicParamDTO> paramDTOList = this.list(publicParamDTO);
        WorkflowPublicParamDTO currentDO = null;
        WorkflowPublicParamDTO targetDO = null;
        int targetIndex = -1;
        int size = paramDTOList.size();
        for (int i = 0; i < size; ++i) {
            if (!Objects.equals(currentId, paramDTOList.get(i).getId())) continue;
            currentDO = paramDTOList.get(i);
            if (WorkflowOption.MoveType.UP.name().equals(movetype)) {
                targetIndex = i - 1;
                break;
            }
            if (!WorkflowOption.MoveType.DOWN.name().equals(movetype)) break;
            targetIndex = i + 1;
            break;
        }
        if (targetIndex < 0 || targetIndex >= paramDTOList.size()) {
            if (WorkflowOption.MoveType.UP.name().equals(movetype)) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.unabletomoveup"));
            }
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.unabletomovedown"));
        }
        targetDO = paramDTOList.get(targetIndex);
        this.workflowPublicParamService.exchangeParamDO(currentDO, targetDO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void exchangeParamDO(WorkflowPublicParamDTO currentDO, WorkflowPublicParamDTO targetDO) {
        WorkflowPublicParamDO publicParamDO = new WorkflowPublicParamDO();
        publicParamDO.setId(currentDO.getId());
        publicParamDO.setModifytime(new Date());
        publicParamDO.setOrdernum(targetDO.getOrdernum());
        this.workflowPublicParamDao.updateByPrimaryKeySelective(publicParamDO);
        publicParamDO.setId(targetDO.getId());
        publicParamDO.setModifytime(new Date());
        publicParamDO.setOrdernum(currentDO.getOrdernum());
        this.workflowPublicParamDao.updateByPrimaryKeySelective(publicParamDO);
    }

    @Override
    public List<WorkflowPublicParamDTO> list(WorkflowPublicParamDTO workflowPublicParamDTO) {
        boolean searchtitle = workflowPublicParamDTO.isSearchtitle();
        List<WorkflowPublicParamDTO> paramDTOList = this.workflowPublicParamDao.list(workflowPublicParamDTO);
        if (searchtitle) {
            int size = paramDTOList.size();
            HashMap<String, String> baseDataMap = new HashMap<String, String>(size);
            HashMap<String, String> enumDataMap = new HashMap<String, String>(size);
            for (WorkflowPublicParamDTO publicParamDTO : paramDTOList) {
                String mapping = publicParamDTO.getMapping();
                if (!StringUtils.hasText(mapping)) continue;
                this.getMappingTitle(baseDataMap, enumDataMap, publicParamDTO, mapping);
            }
        }
        return paramDTOList;
    }

    public void getMappingTitle(Map<String, String> baseDataMap, Map<String, String> enumDataMap, WorkflowPublicParamDTO publicParamDTO, String mapping) {
        BigDecimal mappingtype = publicParamDTO.getMappingtype();
        if (mappingtype == null) {
            return;
        }
        int mappingtypeInt = mappingtype.intValue();
        switch (mappingtypeInt) {
            case 1: {
                String title = baseDataMap.get(mapping);
                if (StringUtils.hasText(title)) {
                    publicParamDTO.setMappingtitle(title);
                    break;
                }
                BaseDataDefineDTO param = new BaseDataDefineDTO();
                param.setName(mapping);
                BaseDataDefineDO defineDO = this.baseDataDefineClient.get(param);
                if (defineDO == null || !StringUtils.hasText(defineDO.getTitle())) break;
                publicParamDTO.setMappingtitle(defineDO.getTitle());
                baseDataMap.put(mapping, defineDO.getTitle());
                break;
            }
            case 2: {
                EnumDataDO enumDataDO;
                String description = enumDataMap.get(mapping);
                if (StringUtils.hasText(description)) {
                    publicParamDTO.setMappingtitle(description);
                    break;
                }
                EnumDataDTO param = new EnumDataDTO();
                param.setBiztype(mapping);
                List list = this.enumDataClient.list(param);
                if (CollectionUtils.isEmpty(list) || !Objects.equals((enumDataDO = (EnumDataDO)list.get(0)).getBiztype(), mapping)) break;
                description = enumDataDO.getDescription();
                publicParamDTO.setMappingtitle(description);
                enumDataMap.put(mapping, description);
                break;
            }
            case 4: {
                OrgCategoryDO orgCategoryDO;
                OrgCategoryDO orgCatDTO = new OrgCategoryDO();
                orgCatDTO.setName(mapping);
                PageVO pageVO = this.orgCategoryClient.list(orgCatDTO);
                if (pageVO == null || pageVO.getRs().getCode() != 0 || pageVO.getTotal() <= 0 || !Objects.equals((orgCategoryDO = (OrgCategoryDO)pageVO.getRows().get(0)).getName(), mapping)) break;
                publicParamDTO.setMappingtitle(orgCategoryDO.getTitle());
            }
        }
    }
}


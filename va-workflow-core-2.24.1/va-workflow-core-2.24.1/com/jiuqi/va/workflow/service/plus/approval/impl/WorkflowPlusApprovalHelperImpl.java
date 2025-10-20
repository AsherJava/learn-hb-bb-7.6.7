/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDO
 */
package com.jiuqi.va.workflow.service.plus.approval.impl;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDO;
import com.jiuqi.va.workflow.domain.WorkflowOptionDTO;
import com.jiuqi.va.workflow.domain.plus.approval.PlusApprovalInfoExtendsDTO;
import com.jiuqi.va.workflow.domain.plus.approval.PlusApprovalUserExtendsDTO;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import com.jiuqi.va.workflow.service.plus.approval.WorkflowPlusApprovalHelper;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowPlusApprovalHelperImpl
implements WorkflowPlusApprovalHelper {
    @Autowired
    private WorkflowHelperService workflowHelperService;
    @Value(value="${jiuqi.va.staff-basedata.enableposts:false}")
    private boolean staffPartTimeJobFlag;

    @Override
    public List<PlusApprovalInfoDTO> convertPlusApprovalDoToDtoList(List<? extends PlusApprovalInfoDO> plusApprovalInfoDoList) {
        ArrayList<PlusApprovalInfoDTO> plusApprovalInfoDtoList = new ArrayList<PlusApprovalInfoDTO>();
        if (CollectionUtils.isEmpty(plusApprovalInfoDoList)) {
            return plusApprovalInfoDtoList;
        }
        for (PlusApprovalInfoDO plusApprovalInfoDO : plusApprovalInfoDoList) {
            PlusApprovalInfoDTO dto = new PlusApprovalInfoDTO();
            dto.setId(plusApprovalInfoDO.getId());
            dto.setUsername(plusApprovalInfoDO.getUsername());
            dto.setApprovaluser(plusApprovalInfoDO.getApprovaluser());
            dto.setApprovalunitcode(plusApprovalInfoDO.getApprovalunitcode());
            dto.setProcessid(plusApprovalInfoDO.getProcessid());
            dto.setNodeid(plusApprovalInfoDO.getNodeid());
            dto.setNodecode(plusApprovalInfoDO.getNodecode());
            dto.setCreatetime(plusApprovalInfoDO.getCreatetime());
            dto.setApprovalcomment(plusApprovalInfoDO.getApprovalcomment());
            dto.setPlusSignApprovalFlag(plusApprovalInfoDO.getPlusSignApprovalFlag());
            dto.setStaffCode(plusApprovalInfoDO.getStaffCode());
            plusApprovalInfoDtoList.add(dto);
        }
        return plusApprovalInfoDtoList;
    }

    @Override
    public Map<String, String> getRemoveUserNodeIdMap(List<? extends PlusApprovalInfoDTO> plusApprovalInfoDtoList, Map<String, String> userNodeIdMap) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (CollectionUtils.isEmpty(plusApprovalInfoDtoList) || CollectionUtils.isEmpty(userNodeIdMap)) {
            return resultMap;
        }
        for (PlusApprovalInfoDTO plusApprovalInfoDTO : plusApprovalInfoDtoList) {
            String approvalUser = plusApprovalInfoDTO.getApprovaluser();
            if (!userNodeIdMap.containsKey(approvalUser)) continue;
            String tempStr = userNodeIdMap.get(approvalUser);
            resultMap.put(approvalUser, tempStr);
        }
        return resultMap;
    }

    @Override
    public List<PlusApprovalInfoDTO> getRemoveFromUpdatePlusApprovalDtoList(List<PlusApprovalInfoDTO> updatePlusApprovalDtoList) {
        ArrayList<PlusApprovalInfoDTO> list = new ArrayList<PlusApprovalInfoDTO>();
        if (CollectionUtils.isEmpty(updatePlusApprovalDtoList)) {
            return list;
        }
        for (PlusApprovalInfoDTO plusApprovalInfoDTO : updatePlusApprovalDtoList) {
            PlusApprovalInfoDTO tempDto = new PlusApprovalInfoDTO();
            BeanUtils.copyProperties(plusApprovalInfoDTO, tempDto);
            Integer plusSignApprovalFlag = tempDto.getPlusSignApprovalFlag();
            if (Objects.nonNull(plusSignApprovalFlag)) {
                if (0 == plusSignApprovalFlag) {
                    tempDto.setPlusSignApprovalFlag(Integer.valueOf(1));
                } else {
                    tempDto.setPlusSignApprovalFlag(Integer.valueOf(0));
                }
            }
            list.add(tempDto);
        }
        return list;
    }

    @Override
    public boolean getChooseStaffOption() {
        Map wf1016Map;
        String isStaff;
        WorkflowOptionDTO workFlowOptionDto = this.workflowHelperService.getWorkFlowOptionDto();
        String wf1016Str = workFlowOptionDto.getWf1016();
        if (!StringUtils.hasText(wf1016Str)) {
            return false;
        }
        boolean ifChooseStaff = false;
        Object o = JSONUtil.parseObject((String)wf1016Str, Object.class);
        if (o instanceof Map && Objects.equals("1", isStaff = (String)(wf1016Map = (Map)JSONUtil.parseObject((String)wf1016Str, Map.class)).get("isStaff"))) {
            ifChooseStaff = true;
        }
        return ifChooseStaff;
    }

    @Override
    public void handleStaffInfo(boolean ifChooseStaff, PlusApprovalInfoDO plusdo, PlusApprovalInfoExtendsDTO infoDTO) {
        if (!ifChooseStaff) {
            return;
        }
        String staffObjectCode = plusdo.getStaffCode();
        if (!StringUtils.hasText(staffObjectCode)) {
            return;
        }
        infoDTO.setStaffCode(staffObjectCode);
        if (this.staffPartTimeJobFlag) {
            this.handleStaffPartTimeJob(infoDTO, staffObjectCode);
        } else {
            this.handleStaffNotPartTimeJob(infoDTO, staffObjectCode);
        }
    }

    @Override
    public void handleStaffInfo(boolean ifChooseStaff, PlusApprovalUserDO plusdo, PlusApprovalUserExtendsDTO infoDTO) {
        if (!ifChooseStaff) {
            return;
        }
        String staffObjectCode = plusdo.getStaffCode();
        if (!StringUtils.hasText(staffObjectCode)) {
            return;
        }
        infoDTO.setStaffCode(staffObjectCode);
        if (this.staffPartTimeJobFlag) {
            this.handleStaffPartTimeJob(infoDTO, staffObjectCode);
        } else {
            this.handleStaffNotPartTimeJob(infoDTO, staffObjectCode);
        }
    }

    private void handleStaffNotPartTimeJob(PlusApprovalInfoExtendsDTO infoDTO, String staffObjectCode) {
        BaseDataDO staff = VaWorkFlowDataUtils.getStaff(staffObjectCode);
        if (staff == null) {
            return;
        }
        infoDTO.setStaffobjectcode(staffObjectCode);
        String unitcode = staff.getUnitcode();
        String deptcode = (String)staff.get((Object)"deptcode");
        infoDTO.setUnitcode(unitcode);
        infoDTO.setDeptcode(deptcode);
        infoDTO.setStaffName(staff.getName());
        this.packageStaffInfo(infoDTO, unitcode, deptcode);
    }

    private void handleStaffNotPartTimeJob(PlusApprovalUserExtendsDTO infoDTO, String staffObjectCode) {
        BaseDataDO staff = VaWorkFlowDataUtils.getStaff(staffObjectCode);
        if (staff == null) {
            return;
        }
        infoDTO.setStaffobjectcode(staffObjectCode);
        String unitcode = staff.getUnitcode();
        String deptcode = (String)staff.get((Object)"deptcode");
        String positioncode = (String)staff.get((Object)"positioncode");
        infoDTO.setPositionCode(positioncode);
        infoDTO.setUnitcode(unitcode);
        infoDTO.setEmail((String)staff.get((Object)"email"));
        infoDTO.setTelephone((String)staff.get((Object)"telephone"));
        infoDTO.setDeptcode(deptcode);
        infoDTO.setStaffName(staff.getName());
        this.packageStaffInfo(infoDTO, unitcode, deptcode, positioncode);
    }

    private void handleStaffPartTimeJob(PlusApprovalInfoExtendsDTO infoDTO, String staffObjectCode) {
        BaseDataDO staff = VaWorkFlowDataUtils.getStaffPost(staffObjectCode);
        if (staff == null) {
            return;
        }
        infoDTO.setStaffName(staff.getName());
        String unitcode = staff.getUnitcode();
        infoDTO.setUnitcode(unitcode);
        infoDTO.setStaffobjectcode(staff.getObjectcode());
        String deptcode = (String)staff.get((Object)"deptcode");
        infoDTO.setDeptcode(deptcode);
        this.packageStaffInfo(infoDTO, unitcode, deptcode);
    }

    private void handleStaffPartTimeJob(PlusApprovalUserExtendsDTO infoDTO, String staffObjectCode) {
        BaseDataDO staff = VaWorkFlowDataUtils.getStaffPost(staffObjectCode);
        BaseDataDO staffPost = VaWorkFlowDataUtils.getStaffPostInfo(staffObjectCode);
        if (staff == null) {
            return;
        }
        infoDTO.setStaffobjectcode(staff.getObjectcode());
        infoDTO.setStaffName(staff.getName());
        String unitcode = staff.getUnitcode();
        String positioncode = null;
        if (staffPost != null) {
            positioncode = (String)staffPost.get((Object)"positioncode");
        }
        infoDTO.setPositionCode(positioncode);
        infoDTO.setUnitcode(unitcode);
        infoDTO.setEmail((String)staff.get((Object)"email"));
        infoDTO.setTelephone((String)staff.get((Object)"telephone"));
        String deptcode = (String)staff.get((Object)"deptcode");
        infoDTO.setDeptcode(deptcode);
        this.packageStaffInfo(infoDTO, unitcode, deptcode, positioncode);
    }

    private void packageStaffInfo(PlusApprovalInfoExtendsDTO infoDTO, String unitcode, String deptcode) {
        PageVO<BaseDataDO> deptPageVO;
        String staffUnitCodeName = "";
        String staffDepartmentName = "";
        PageVO<OrgDO> staffOrgPageVO = VaWorkFlowDataUtils.getOrgDOByUnitCode(unitcode);
        if (staffOrgPageVO != null && staffOrgPageVO.getTotal() > 0) {
            OrgDO orgInfo = (OrgDO)staffOrgPageVO.getRows().get(0);
            staffUnitCodeName = orgInfo.getName();
        }
        if ((deptPageVO = VaWorkFlowDataUtils.getDeptByDeptCode(deptcode)) != null && deptPageVO.getTotal() > 0) {
            BaseDataDO deptInfo = (BaseDataDO)deptPageVO.getRows().get(0);
            staffDepartmentName = deptInfo.getName();
        }
        infoDTO.setDeptname(staffDepartmentName);
        infoDTO.setUnitname(staffUnitCodeName);
    }

    private void packageStaffInfo(PlusApprovalUserExtendsDTO infoDTO, String unitcode, String deptcode, String positionCode) {
        PageVO<BaseDataDO> positionPageVO;
        PageVO<BaseDataDO> deptPageVO;
        PageVO<OrgDO> staffOrgPageVO;
        String staffUnitCodeName = "";
        String staffDepartmentName = "";
        String staffPositionName = "";
        if (StringUtils.hasText(unitcode) && (staffOrgPageVO = VaWorkFlowDataUtils.getOrgDOByUnitCode(unitcode)) != null && staffOrgPageVO.getTotal() > 0) {
            OrgDO orgInfo = (OrgDO)staffOrgPageVO.getRows().get(0);
            staffUnitCodeName = orgInfo.getName();
        }
        if (StringUtils.hasText(deptcode) && (deptPageVO = VaWorkFlowDataUtils.getDeptByDeptCode(deptcode)) != null && deptPageVO.getTotal() > 0) {
            BaseDataDO deptInfo = (BaseDataDO)deptPageVO.getRows().get(0);
            staffDepartmentName = deptInfo.getName();
        }
        if (StringUtils.hasText(positionCode) && (positionPageVO = VaWorkFlowDataUtils.getPositionByCode(positionCode)) != null && positionPageVO.getTotal() > 0) {
            BaseDataDO positionInfo = (BaseDataDO)positionPageVO.getRows().get(0);
            staffPositionName = positionInfo.getName();
        }
        infoDTO.setDeptname(staffDepartmentName);
        infoDTO.setUnitname(staffUnitCodeName);
        infoDTO.setPositionName(staffPositionName);
    }
}


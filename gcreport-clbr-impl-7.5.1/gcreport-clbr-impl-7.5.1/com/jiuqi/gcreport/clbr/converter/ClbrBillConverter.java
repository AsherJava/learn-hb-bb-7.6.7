/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemParamDTO
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillVO
 */
package com.jiuqi.gcreport.clbr.converter;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemParamDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillDeleteEO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class ClbrBillConverter {
    public static List<ClbrBillEO> convertDTO2EO(List<ClbrBillDTO> clbrBillDTOs) {
        List<ClbrBillEO> clbrBillEOS = clbrBillDTOs.stream().map(clbrBillDTO -> ClbrBillConverter.convertDTO2EO(clbrBillDTO)).collect(Collectors.toList());
        return clbrBillEOS;
    }

    public static List<ClbrBillDTO> convertEO2DTO(List<ClbrBillEO> clbrBillEOs) {
        List<ClbrBillDTO> clbrBillDTOS = clbrBillEOs.stream().map(clbrBillEO -> ClbrBillConverter.convertEO2DTO(clbrBillEO)).collect(Collectors.toList());
        return clbrBillDTOS;
    }

    public static List<ClbrBillVO> convertDTO2VO(List<ClbrBillDTO> clbrBillDTOs) {
        List<ClbrBillVO> clbrBillVOs = clbrBillDTOs.stream().map(clbrBillDTO -> ClbrBillConverter.convertDTO2VO(clbrBillDTO)).collect(Collectors.toList());
        return clbrBillVOs;
    }

    public static PageInfo<ClbrBillEO> convertDTO2EO(PageInfo<ClbrBillDTO> pageInfo) {
        List clbrBillEOS = pageInfo.getList().stream().map(clbrBillDTO -> ClbrBillConverter.convertDTO2EO(clbrBillDTO)).collect(Collectors.toList());
        PageInfo eoPageInfo = PageInfo.of(clbrBillEOS, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return eoPageInfo;
    }

    public static PageInfo<ClbrBillVO> convertDTO2VO(PageInfo<ClbrBillDTO> pageInfo) {
        List clbrBillVOS = pageInfo.getList().stream().map(clbrBillDTO -> ClbrBillConverter.convertDTO2VO(clbrBillDTO)).collect(Collectors.toList());
        PageInfo voPageInfo = PageInfo.of(clbrBillVOS, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return voPageInfo;
    }

    public static PageInfo<ClbrBillDTO> convertEO2DTO(PageInfo<ClbrBillEO> pageInfo) {
        List clbrBillDTOS = pageInfo.getList().stream().map(clbrBillEO -> ClbrBillConverter.convertEO2DTO(clbrBillEO)).collect(Collectors.toList());
        PageInfo eoPageInfo = PageInfo.of(clbrBillDTOS, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return eoPageInfo;
    }

    public static PageInfo<ClbrBillVO> convertEO2VO(PageInfo<ClbrBillEO> pageInfo) {
        List clbrBillDTOS = pageInfo.getList().stream().map(clbrBillEO -> ClbrBillConverter.convertEO2VO(clbrBillEO)).collect(Collectors.toList());
        PageInfo eoPageInfo = PageInfo.of(clbrBillDTOS, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return eoPageInfo;
    }

    public static ClbrBillEO convertDTO2EO(ClbrBillDTO clbrBillDTO) {
        ClbrBillEO clbrBillEO = new ClbrBillEO();
        if (Objects.nonNull(clbrBillDTO.getExtendInfo())) {
            HashMap extendFieldsMap = new HashMap(clbrBillDTO.getExtendInfo());
            clbrBillEO.resetFields(extendFieldsMap);
        }
        BeanUtils.copyProperties(clbrBillDTO, (Object)clbrBillEO);
        return clbrBillEO;
    }

    public static ClbrBillVO convertDTO2VO(ClbrBillDTO clbrBillDTO) {
        GcBaseData thatRelationBdo;
        GcBaseData thisRelationBdo;
        GcBaseData oppRelationBdo;
        GcBaseData relationBdo;
        ClbrBillVO clbrBillVO = new ClbrBillVO();
        BeanUtils.copyProperties(clbrBillDTO, clbrBillVO);
        GcBaseData clbrTypeBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CLBRTYPE", clbrBillDTO.getClbrType());
        if (clbrTypeBdo != null) {
            clbrBillVO.getExtendInfo().put("CLBRTYPE", clbrTypeBdo.getTitle());
        }
        if (!StringUtils.isEmpty((String)clbrBillDTO.getOppClbrType())) {
            String[] oppClbrTypeArray = clbrBillDTO.getOppClbrType().split(",");
            StringBuilder oppClbrTypeTitle = new StringBuilder();
            for (String oppClbrTypeCode : oppClbrTypeArray) {
                GcBaseData oppClbrTypeBaseData = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CLBRTYPE", oppClbrTypeCode);
                if (oppClbrTypeBaseData == null) continue;
                oppClbrTypeTitle.append(oppClbrTypeBaseData.getTitle()).append(",");
            }
            clbrBillVO.getExtendInfo().put("OPPCLBRTYPE", oppClbrTypeTitle.substring(0, oppClbrTypeTitle.length() > 0 ? oppClbrTypeTitle.length() - 1 : 0));
        }
        if ((relationBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_RELATION", clbrBillDTO.getRelation())) != null) {
            clbrBillVO.getExtendInfo().put("RELATION", relationBdo.getTitle());
        }
        if ((oppRelationBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_RELATION", clbrBillDTO.getOppRelation())) != null) {
            clbrBillVO.getExtendInfo().put("OPPRELATION", oppRelationBdo.getTitle());
        }
        if ((thisRelationBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_RELATION", clbrBillDTO.getThisRelation())) != null) {
            clbrBillVO.getExtendInfo().put("THISRELATION", thisRelationBdo.getTitle());
        }
        if ((thatRelationBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_RELATION", clbrBillDTO.getThatRelation())) != null) {
            clbrBillVO.getExtendInfo().put("THATRELATION", thatRelationBdo.getTitle());
        }
        return clbrBillVO;
    }

    public static ClbrBillVO convertEO2VO(ClbrBillEO clbrBillEO) {
        GcBaseData thatRelationBdo;
        GcBaseData thisRelationBdo;
        GcBaseData oppRelationBdo;
        GcBaseData relationBdo;
        GcBaseData clbrTypeBdo;
        ClbrBillVO clbrBillVO = new ClbrBillVO();
        BeanUtils.copyProperties((Object)clbrBillEO, clbrBillVO);
        HashMap extendFiledsMap = new HashMap(clbrBillEO.getFields());
        clbrBillVO.setExtendInfo(extendFiledsMap);
        if (!StringUtils.isEmpty((String)clbrBillEO.getClbrType()) && (clbrTypeBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CLBRTYPE", clbrBillEO.getClbrType())) != null) {
            clbrBillVO.setClbrTypeTitle(clbrTypeBdo.getTitle());
            clbrBillVO.getExtendInfo().put("CLBRTYPE", clbrTypeBdo.getTitle());
        }
        if (!StringUtils.isEmpty((String)clbrBillEO.getOppClbrType())) {
            String[] oppClbrTypeArray = clbrBillEO.getOppClbrType().split(",");
            StringBuilder oppClbrTypeTitle = new StringBuilder();
            for (String oppClbrTypeCode : oppClbrTypeArray) {
                GcBaseData oppClbrTypeBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CLBRTYPE", oppClbrTypeCode);
                if (oppClbrTypeBdo == null) continue;
                oppClbrTypeTitle.append(oppClbrTypeBdo.getTitle()).append(",");
            }
            clbrBillVO.setOppClbrTypeTitle(oppClbrTypeTitle.substring(0, oppClbrTypeTitle.length() > 0 ? oppClbrTypeTitle.length() - 1 : 0));
            clbrBillVO.getExtendInfo().put("OPPCLBRTYPE", oppClbrTypeTitle.substring(0, oppClbrTypeTitle.length() > 0 ? oppClbrTypeTitle.length() - 1 : 0));
        }
        if (!StringUtils.isEmpty((String)clbrBillEO.getRelation()) && (relationBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_RELATION", clbrBillEO.getRelation())) != null) {
            clbrBillVO.setRelationTitle(relationBdo.getTitle());
            clbrBillVO.getExtendInfo().put("RELATION", relationBdo.getTitle());
        }
        if (!StringUtils.isEmpty((String)clbrBillEO.getOppRelation()) && (oppRelationBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_RELATION", clbrBillEO.getOppRelation())) != null) {
            clbrBillVO.setOppRelationTitle(oppRelationBdo.getTitle());
            clbrBillVO.getExtendInfo().put("OPPRELATION", oppRelationBdo.getTitle());
        }
        if (!StringUtils.isEmpty((String)clbrBillEO.getThisRelation()) && (thisRelationBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_RELATION", clbrBillEO.getThisRelation())) != null) {
            clbrBillVO.setOppRelationTitle(thisRelationBdo.getTitle());
            clbrBillVO.getExtendInfo().put("THISRELATION", thisRelationBdo.getTitle());
        }
        if (!StringUtils.isEmpty((String)clbrBillEO.getThatRelation()) && (thatRelationBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_RELATION", clbrBillEO.getThatRelation())) != null) {
            clbrBillVO.setOppRelationTitle(thatRelationBdo.getTitle());
            clbrBillVO.getExtendInfo().put("THATRELATION", thatRelationBdo.getTitle());
        }
        return clbrBillVO;
    }

    public static ClbrBillDTO convertEO2DTO(ClbrBillEO clbrBillEO) {
        ClbrBillDTO clbrBillDTO = new ClbrBillDTO();
        BeanUtils.copyProperties((Object)clbrBillEO, clbrBillDTO);
        HashMap extendFiledsMap = new HashMap(clbrBillEO.getFields());
        clbrBillDTO.setExtendInfo(extendFiledsMap);
        return clbrBillDTO;
    }

    public static List<ClbrBillDeleteEO> convertBillEO2DeleteEO(List<ClbrBillEO> clbrBillEOs) {
        List<ClbrBillDeleteEO> clbrBillDeleteEOs = clbrBillEOs.stream().map(ClbrBillConverter::convertBillEO2DeleteEO).collect(Collectors.toList());
        return clbrBillDeleteEOs;
    }

    public static ClbrBillDeleteEO convertBillEO2DeleteEO(ClbrBillEO clbrBillEO) {
        ClbrBillDeleteEO clbrBillDeleteEO = new ClbrBillDeleteEO();
        BeanUtils.copyProperties((Object)clbrBillEO, (Object)clbrBillDeleteEO);
        HashMap<String, Object> fieldsMap = new HashMap<String, Object>(clbrBillEO.getFields());
        fieldsMap.put("BILLSTATE", clbrBillEO.getBillState());
        fieldsMap.put("MODIFYTIME", clbrBillEO.getModifyTime());
        fieldsMap.put("REJECTUSERNAME", clbrBillEO.getRejectUserName());
        fieldsMap.put("REJECTTIME", clbrBillEO.getRejectTime());
        clbrBillDeleteEO.resetFields(fieldsMap);
        return clbrBillDeleteEO;
    }

    public static PageInfo<ClbrBillVO> convertDeleteEO2VO(PageInfo<ClbrBillDeleteEO> pageInfo) {
        List clbrBillDTOS = pageInfo.getList().stream().map(clbrBillDeleteEO -> {
            ClbrBillEO clbrBillEO = new ClbrBillEO();
            BeanUtils.copyProperties(clbrBillDeleteEO, (Object)clbrBillEO);
            HashMap extendFiledsMap = new HashMap(clbrBillDeleteEO.getFields());
            clbrBillEO.resetFields(extendFiledsMap);
            return ClbrBillConverter.convertEO2VO(clbrBillEO);
        }).collect(Collectors.toList());
        PageInfo eoPageInfo = PageInfo.of(clbrBillDTOS, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return eoPageInfo;
    }

    public static ClbrBillDTO convertPushItemDTO2DTO(ClbrBillPushItemParamDTO pushItemParamDTO) {
        ClbrBillDTO clbrBillDTO = new ClbrBillDTO();
        clbrBillDTO.setId(UUIDUtils.newUUIDStr());
        clbrBillDTO.setClbrBillCode(pushItemParamDTO.getClbrBillCode());
        clbrBillDTO.setClbrBillType(pushItemParamDTO.getClbrBillType());
        clbrBillDTO.setClbrType(pushItemParamDTO.getClbrType());
        clbrBillDTO.setCreateTime(new Date());
        clbrBillDTO.setCurrency(ConverterUtils.getAsString((Object)pushItemParamDTO.getCurrency(), (String)"CNY"));
        clbrBillDTO.setUserTitle(pushItemParamDTO.getUserTitle());
        clbrBillDTO.setAmount(pushItemParamDTO.getAmount());
        clbrBillDTO.setNextUserName(pushItemParamDTO.getNextUserName());
        clbrBillDTO.setOppClbrBillCode(pushItemParamDTO.getOppClbrBillCode());
        clbrBillDTO.setOppRelation(pushItemParamDTO.getOppRelation());
        clbrBillDTO.setRelation(pushItemParamDTO.getRelation());
        clbrBillDTO.setSrcId(pushItemParamDTO.getSrcId());
        clbrBillDTO.setUserName(pushItemParamDTO.getUserName());
        clbrBillDTO.setVerifyedAmount(Double.valueOf(0.0));
        clbrBillDTO.setBillState(ClbrBillStateEnum.INIT.getCode());
        clbrBillDTO.setSn(pushItemParamDTO.getSn());
        clbrBillDTO.setSysCode(pushItemParamDTO.getSysCode());
        clbrBillDTO.setNoverifyAmount(pushItemParamDTO.getAmount());
        clbrBillDTO.setOppSrcId(pushItemParamDTO.getOppSrcId());
        clbrBillDTO.setOrgCode(pushItemParamDTO.getOrgCode());
        clbrBillDTO.setOppOrgCode(pushItemParamDTO.getOppOrgCode());
        clbrBillDTO.setOppUserName(pushItemParamDTO.getOppUserName());
        clbrBillDTO.setExtendInfo(pushItemParamDTO.getExtendInfo());
        clbrBillDTO.setGroupId(pushItemParamDTO.getGroupId());
        clbrBillDTO.setClbrCode(pushItemParamDTO.getClbrCode());
        clbrBillDTO.setArbitrationUserName(pushItemParamDTO.getArbitrationUserName());
        return clbrBillDTO;
    }
}


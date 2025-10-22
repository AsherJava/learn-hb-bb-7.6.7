/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.clbr.api.ClbrBillClient
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillManualConfirmParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillTableColumnVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition
 *  com.jiuqi.gcreport.clbr.vo.ClbrTodoCountVO
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.springframework.web.bind.annotation.RestController
 *  tk.mybatis.spring.annotation.MapperScannerRegistrar
 */
package com.jiuqi.gcreport.clbr.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.clbr.api.ClbrBillClient;
import com.jiuqi.gcreport.clbr.converter.ClbrBillConverter;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillManualConfirmParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.service.ClbrBillReceBillCodeService;
import com.jiuqi.gcreport.clbr.service.ClbrBillService;
import com.jiuqi.gcreport.clbr.service.ClbrProcessService;
import com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO;
import com.jiuqi.gcreport.clbr.vo.ClbrBillTableColumnVO;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrTodoCountVO;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScannerRegistrar;

@RestController
public class ClbrBillController
implements ClbrBillClient {
    private final Logger logger = LoggerFactory.getLogger(ClbrBillController.class);
    @Autowired
    private ClbrBillService clbrBillService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ClbrProcessService clbrProcessService;
    @Autowired
    private ClbrBillReceBillCodeService clbrBillReceBillCodeService;

    public BusinessResponseEntity<ClbrBillPushResultDTO> batchVerify(ClbrBillPushParamDTO clbrBillReqDTO) {
        return BusinessResponseEntity.ok((Object)this.clbrBillService.checkBatchPush(clbrBillReqDTO));
    }

    public BusinessResponseEntity<ClbrBillPushResultDTO> batchPush(ClbrBillPushParamDTO clbrBillReqDTO) {
        ClbrBillPushResultDTO clbrBillBatchResponseDTO = this.clbrBillService.batchPush(clbrBillReqDTO);
        return BusinessResponseEntity.ok((Object)clbrBillBatchResponseDTO);
    }

    public BusinessResponseEntity<ClbrBillPushResultDTO> batchPush(String clbrBillReqDTOJson) {
        ClbrBillPushParamDTO clbrBillPushParamDTO = (ClbrBillPushParamDTO)JsonUtils.readValue((String)clbrBillReqDTOJson, ClbrBillPushParamDTO.class);
        ClbrBillPushResultDTO clbrBillBatchResponseDTO = this.clbrBillService.batchPush(clbrBillPushParamDTO);
        return BusinessResponseEntity.ok((Object)clbrBillBatchResponseDTO);
    }

    public BusinessResponseEntity<List<ClbrBillTableColumnVO>> queryColumnDefines(String sysCode) {
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode("GC_CLBR_BILL");
        List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        List tableColumnVOS = columnModelDefines.stream().map(columnModelDefine -> {
            TableModelDefine tableDefine;
            StringBuilder columnRefTableFeild = new StringBuilder();
            String referTableID = columnModelDefine.getReferTableID();
            String referColumnID = columnModelDefine.getReferColumnID();
            if (!ObjectUtils.isEmpty(referTableID) && !ObjectUtils.isEmpty(referColumnID) && (tableDefine = this.dataModelService.getTableModelDefineById(referTableID)) != null) {
                columnRefTableFeild.append(tableDefine.getName());
                ColumnModelDefine columnDefine = this.dataModelService.getColumnModelDefineByID(referColumnID);
                if (columnDefine != null) {
                    columnRefTableFeild.append(".").append(columnDefine.getName());
                }
            }
            ClbrBillTableColumnVO clbrBillTableColumnVO = new ClbrBillTableColumnVO(columnModelDefine.getName(), columnModelDefine.getTitle(), columnModelDefine.getColumnType().name(), columnRefTableFeild.toString());
            return clbrBillTableColumnVO;
        }).collect(Collectors.toList());
        return BusinessResponseEntity.ok(tableColumnVOS);
    }

    public BusinessResponseEntity<ClbrBillVO> query(String sysCode, String clbrBillcode) {
        ClbrBillDTO clbrBillDTO = this.clbrBillService.query(sysCode, clbrBillcode);
        ClbrBillVO clbrBillVO = ClbrBillConverter.convertDTO2VO(clbrBillDTO);
        return BusinessResponseEntity.ok((Object)clbrBillVO);
    }

    public BusinessResponseEntity<Integer> countInitiatorNotConfirmByUser(ClbrTodoCountVO clbrTodoCountVO) {
        Integer count = this.clbrBillService.countInitiatorNotConfirmByUser(clbrTodoCountVO.getSysCode(), clbrTodoCountVO.getUserName(), clbrTodoCountVO.getRoleCode(), clbrTodoCountVO.getRelation());
        return BusinessResponseEntity.ok((Object)count);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BusinessResponseEntity<List<ClbrBillDTO>> listInitiatorNotConfirmByUser(String sysCode, String userName, String roleCode, String relation, String clbrBillCode) {
        List<ClbrBillDTO> clbrBillDTOS;
        ClbrBillSsoConditionDTO clbrBillSsoConditionDTO = new ClbrBillSsoConditionDTO();
        clbrBillSsoConditionDTO.setSysCode(sysCode);
        clbrBillSsoConditionDTO.setUserName(userName);
        clbrBillSsoConditionDTO.setRoleCode(roleCode);
        clbrBillSsoConditionDTO.setRelation(relation);
        clbrBillSsoConditionDTO.setClbrBillCode(clbrBillCode);
        try {
            clbrBillDTOS = this.clbrBillService.listInitiatorNotConfirmByUser(clbrBillSsoConditionDTO);
        }
        finally {
            ShiroUtil.unbindUser();
        }
        return BusinessResponseEntity.ok(clbrBillDTOS);
    }

    public BusinessResponseEntity<Integer> countProcessInitiatorNotConfirmByUser(ClbrTodoCountVO clbrTodoCountVO) {
        Integer count = this.clbrBillService.countProcessInitiatorNotConfirmByUser(clbrTodoCountVO.getSysCode(), clbrTodoCountVO.getUserName(), clbrTodoCountVO.getRoleCode(), clbrTodoCountVO.getRelation());
        return BusinessResponseEntity.ok((Object)count);
    }

    public BusinessResponseEntity<PageInfo<ClbrBillCheckVO>> listProcessInitiatorNotConfirmByUser(ClbrProcessCondition clbrProcessCondition, String tabFlag) {
        return BusinessResponseEntity.ok(this.clbrBillService.listProcessInitiatorNotConfirmByUser(clbrProcessCondition, tabFlag));
    }

    public BusinessResponseEntity<PageInfo<ClbrBillVO>> listByReceiverInfo(ClbrBillSsoConditionDTO clbrBillSsoConditionDTO) {
        PageInfo<ClbrBillDTO> pageInfo = this.clbrBillService.listByReceiverInfo(clbrBillSsoConditionDTO);
        PageInfo<ClbrBillVO> voPageInfo = ClbrBillConverter.convertDTO2VO(pageInfo);
        return BusinessResponseEntity.ok(voPageInfo);
    }

    public BusinessResponseEntity<PageInfo<ClbrBillVO>> listInitiatorConfirmByRelation(ClbrBillGenerateQueryParamDTO queryConfirmParamDTO) {
        PageInfo<ClbrBillDTO> pageInfo = this.clbrBillService.listInitiatorConfirmByRelation(queryConfirmParamDTO);
        PageInfo<ClbrBillVO> voPageInfo = ClbrBillConverter.convertDTO2VO(pageInfo);
        return BusinessResponseEntity.ok(voPageInfo);
    }

    public BusinessResponseEntity<PageInfo<ClbrBillVO>> listInitiatorNotConfirmByRelation(ClbrBillGenerateQueryParamDTO queryNotConfirmParamDTO) {
        PageInfo<ClbrBillDTO> pageInfo = this.clbrBillService.listInitiatorNotConfirmByRelation(queryNotConfirmParamDTO);
        PageInfo<ClbrBillVO> voPageInfo = ClbrBillConverter.convertDTO2VO(pageInfo);
        return BusinessResponseEntity.ok(voPageInfo);
    }

    public BusinessResponseEntity<ClbrGenerateAttributeDTO> queryClbrGenerateAttribute(String sysCode) {
        ClbrGenerateAttributeDTO clbrGenerateAttributeDTO = this.clbrBillService.queryClbrGenerateAttribute(sysCode);
        return BusinessResponseEntity.ok((Object)clbrGenerateAttributeDTO);
    }

    public BusinessResponseEntity<Object> generateOppClbrBill(Set<String> clbrBillIds) {
        Object result = this.clbrBillService.generateOppClbrBill(clbrBillIds);
        return BusinessResponseEntity.ok((Object)result);
    }

    public BusinessResponseEntity<Object> rejectClbrBill(ClbrBillRejectDTO clbrBillRejectDTO) {
        this.clbrBillService.rejectClbrBill(clbrBillRejectDTO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> cancelClbrBill(Set<String> clbrBillIds) {
        this.clbrBillService.cancelClbrBill(clbrBillIds);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> cancelSingleClbrBill(Set<String> groupIds) {
        this.clbrBillService.cancelSingleClbrBill(groupIds);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> manualConfirmByGrouping(Set<String> groupIds) {
        this.clbrBillService.manualConfirmByGrouping(groupIds);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> manualConfirm(ClbrBillManualConfirmParamDTO manualConfirmParamDTO) {
        this.clbrBillService.manualConfirm(manualConfirmParamDTO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> showBill(String clbrBillId) {
        return BusinessResponseEntity.ok((Object)this.clbrBillService.showBill(clbrBillId));
    }

    public BusinessResponseEntity<Object> updateUnClbrReceBillCode(ClbrBillReceBillCodeDTO clbrBillReceBillCodeDTO) {
        if (!StringUtils.hasText(clbrBillReceBillCodeDTO.getSysCode())) {
            return BusinessResponseEntity.error((String)"\u7cfb\u7edf\u6765\u6e90\u4e3a\u7a7a");
        }
        if (CollectionUtils.isEmpty(clbrBillReceBillCodeDTO.getSendBillIdList())) {
            return BusinessResponseEntity.error((String)"\u53d1\u8d77\u65b9\u5355\u636e\u7f16\u53f7\u4e3a\u7a7a");
        }
        try {
            clbrBillReceBillCodeDTO.setDeleteFlag(false);
            this.clbrBillReceBillCodeService.updateOrDeleteClbrReceBillCode(clbrBillReceBillCodeDTO);
        }
        catch (Exception e) {
            MapperScannerRegistrar.LOGGER.error("\u4fee\u6539\u63a5\u6536\u65b9\u5355\u636e\u7f16\u53f7\u65f6\u51fa\u73b0\u5f02\u5e38\uff1a", e);
            return BusinessResponseEntity.error((String)"\u4fee\u6539\u63a5\u6536\u65b9\u5355\u636e\u7f16\u53f7\u51fa\u73b0\u5f02\u5e38");
        }
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> deleteUnClbrReceBillCode(ClbrBillReceBillCodeDTO clbrBillReceBillCodeDTO) {
        if (!StringUtils.hasText(clbrBillReceBillCodeDTO.getSysCode())) {
            return BusinessResponseEntity.error((String)"\u7cfb\u7edf\u6765\u6e90\u4e3a\u7a7a");
        }
        if (CollectionUtils.isEmpty(clbrBillReceBillCodeDTO.getSendBillIdList())) {
            return BusinessResponseEntity.error((String)"\u53d1\u8d77\u65b9\u5355\u636e\u7f16\u53f7\u4e3a\u7a7a");
        }
        try {
            clbrBillReceBillCodeDTO.setDeleteFlag(true);
            this.clbrBillReceBillCodeService.updateOrDeleteClbrReceBillCode(clbrBillReceBillCodeDTO);
        }
        catch (Exception e) {
            MapperScannerRegistrar.LOGGER.error("\u6e05\u9664\u63a5\u6536\u65b9\u5355\u636e\u7f16\u53f7\u65f6\u51fa\u73b0\u5f02\u5e38\uff1a", e);
            return BusinessResponseEntity.error((String)"\u6e05\u9664\u63a5\u6536\u65b9\u5355\u636e\u7f16\u53f7\u51fa\u73b0\u5f02\u5e38");
        }
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> generateCheckOppClbrBill(Set<String> clbrBillIds) {
        List<ClbrBillEO> clbrBillEOS = this.clbrBillService.queryReceBillCodeState(clbrBillIds);
        if (!CollectionUtils.isEmpty(clbrBillEOS)) {
            HashSet<String> receBillCodeList = new HashSet<String>();
            for (ClbrBillEO clbrBillEO : clbrBillEOS) {
                receBillCodeList.add(clbrBillEO.getUnClbrReceBillCode());
            }
            String billCodeListString = String.join((CharSequence)",", receBillCodeList);
            return BusinessResponseEntity.error((String)"10001", (String)("\u5f53\u524d\u5b58\u5728\u5df2\u53d1\u8d77\u534f\u540c\u786e\u8ba4\u6d41\u7a0b\u7684\u5355\u636e\uff0c\u8bf7\u5148\u5904\u7406\u5df2\u751f\u6210\u7684\u5355\u636e:" + billCodeListString + "\u540e\u518d\u534f\u540c"));
        }
        return BusinessResponseEntity.ok();
    }
}


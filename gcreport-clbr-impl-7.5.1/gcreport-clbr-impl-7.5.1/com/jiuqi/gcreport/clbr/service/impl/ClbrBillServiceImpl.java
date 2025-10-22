/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.clbr.dto.ClbrBaseInfoDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillConfirmMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillManualConfirmParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemResultDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillRejectMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrSchemeDTO
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillConfirmTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillOperateEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrConfirmStatusEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrFlowControlEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrOperateTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrRigidOptionsEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrTabEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.financialcheckapi.clbr.ClbrCheckClient
 *  com.jiuqi.gcreport.financialcheckapi.clbr.vo.ClbrVoucherItemVO
 *  com.jiuqi.np.authz2.Namable
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.interceptor.TransactionAspectSupport
 */
package com.jiuqi.gcreport.clbr.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.clbr.adapter.context.ClbrBilCancelMsgContext;
import com.jiuqi.gcreport.clbr.adapter.context.ClbrBillConfirmMsgContext;
import com.jiuqi.gcreport.clbr.adapter.factory.ClbrSystemAdapterFactory;
import com.jiuqi.gcreport.clbr.converter.ClbrBillConverter;
import com.jiuqi.gcreport.clbr.dao.ClbrBillCheckDao;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDao;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDeleteDao;
import com.jiuqi.gcreport.clbr.dao.ClbrSchemeDao;
import com.jiuqi.gcreport.clbr.dto.ClbrBaseInfoDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillConfirmMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillManualConfirmParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemResultDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrSchemeDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillCheckEO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillDeleteEO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.entity.ClbrSchemeEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillConfirmTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrBillOperateEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrConfirmStatusEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrFlowControlEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrOperateTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrRigidOptionsEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrTabEnum;
import com.jiuqi.gcreport.clbr.service.ClbrBillService;
import com.jiuqi.gcreport.clbr.service.ClbrProcessService;
import com.jiuqi.gcreport.clbr.service.ClbrReceiveSettingService;
import com.jiuqi.gcreport.clbr.service.ClbrSchemeService;
import com.jiuqi.gcreport.clbr.util.ClbrTypePenetrationControlUtils;
import com.jiuqi.gcreport.clbr.util.ClbrUtils;
import com.jiuqi.gcreport.clbr.util.RelationUtils;
import com.jiuqi.gcreport.clbr.vo.ClbrBillCheckVO;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.financialcheckapi.clbr.ClbrCheckClient;
import com.jiuqi.gcreport.financialcheckapi.clbr.vo.ClbrVoucherItemVO;
import com.jiuqi.np.authz2.Namable;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
public class ClbrBillServiceImpl
implements ClbrBillService {
    private static Logger LOGGER = LoggerFactory.getLogger(ClbrBillServiceImpl.class);
    @Autowired
    private ClbrBillDao clbrBillDao;
    @Autowired
    private ClbrBillDeleteDao clbrBillDeleteDao;
    @Autowired
    private ClbrBillCheckDao clbrBillCheckDao;
    @Autowired
    private ClbrProcessService clbrProcessService;
    @Autowired
    private ClbrReceiveSettingService clbrReceiveSettingService;
    @Autowired
    private ClbrSchemeService clbrSchemeService;
    @Autowired
    private ClbrSchemeDao clbrSchemeDao;
    @Autowired
    private ClbrSystemAdapterFactory adapterFactory;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ClbrCheckClient clbrCheckClient;
    @Value(value="${custom.service-url.gcreport:#{null}}")
    private String url;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ClbrBillPushResultDTO checkBatchPush(ClbrBillPushParamDTO clbrBillReqDTO) {
        ClbrBillPushResultDTO clbrBillPushResultDTO;
        try {
            clbrBillPushResultDTO = this.batchPush(clbrBillReqDTO);
        }
        finally {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return clbrBillPushResultDTO;
    }

    private void generateClbrCodeAndGroupId(List<ClbrBillPushItemParamDTO> items) {
        List receiveBills = items.stream().filter(item -> ClbrBillOperateEnum.ADD.getCode().equals(item.getOperate()) && ClbrBillTypeEnum.RECEIVER.getCode().equals(item.getClbrBillType())).collect(Collectors.toList());
        Map initiatorSrcIdToItemMap = receiveBills.stream().collect(Collectors.toMap(ClbrBillPushItemParamDTO::getOppSrcId, Function.identity(), (k1, k2) -> k2));
        List<ClbrBillEO> initiatorBills = this.clbrBillDao.listBySrcIds(new ArrayList<String>(initiatorSrcIdToItemMap.keySet()));
        Map relationToInitiatorBillMap = initiatorBills.stream().collect(Collectors.groupingBy(ClbrBillEO::getRelation, Collectors.mapping(Function.identity(), Collectors.toList())));
        relationToInitiatorBillMap.forEach((relation, bills) -> {
            Set clbrCodes = bills.stream().map(ClbrBillEO::getClbrCode).filter(Objects::nonNull).collect(Collectors.toSet());
            String groupId = UUIDUtils.newUUIDStr();
            if (clbrCodes.size() > 1) {
                throw new BusinessRuntimeException("\u4e0d\u5141\u8bb8\u534f\u540c\uff0c\u76f8\u540c\u53d1\u8d77\u65b9\u5355\u4f4d\u7684\u53d1\u8d77\u5355\u5b58\u5728\u4e0d\u540c\u534f\u540c\u7801\u3002");
            }
            String finalClbrCode = clbrCodes.size() == 1 ? (String)new ArrayList(clbrCodes).get(0) : ClbrUtils.newClbrCode();
            for (ClbrBillEO clbrBillEO : bills) {
                ClbrBillPushItemParamDTO clbrBillPushItemParamDTO = (ClbrBillPushItemParamDTO)initiatorSrcIdToItemMap.get(clbrBillEO.getSrcId());
                clbrBillPushItemParamDTO.setClbrCode(finalClbrCode);
                clbrBillPushItemParamDTO.setGroupId(groupId);
            }
        });
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ClbrBillPushResultDTO batchPush(ClbrBillPushParamDTO clbrBillReqDTO) {
        LOGGER.info("\u63a8\u9001\u62a5\u6587\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)clbrBillReqDTO));
        String sn = clbrBillReqDTO.getSn();
        String sysCode = clbrBillReqDTO.getSysCode();
        GcBaseData clbrSystemBdo = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CLBRSYSTEM", sysCode);
        Assert.isTrue(!ObjectUtils.isEmpty(clbrSystemBdo), "\u672a\u652f\u6301\u7684\u6765\u6e90\u7cfb\u7edf[" + sysCode + "]");
        List items = clbrBillReqDTO.getItems();
        this.generateClbrCodeAndGroupId(items);
        ArrayList respItemDTOs = new ArrayList();
        AtomicBoolean pushStatus = new AtomicBoolean(Boolean.TRUE);
        StringBuilder messageBuilder = new StringBuilder();
        Collections.sort(items, (o1, o2) -> {
            ClbrBillOperateEnum operateEnum = ClbrBillOperateEnum.getEnumByCode((String)o1.getOperate());
            Assert.isTrue(!ObjectUtils.isEmpty(operateEnum), "\u4e0d\u652f\u6301\u7684\u64cd\u4f5c\u7c7b\u578b[" + o1.getOperate() + "]");
            if (ClbrBillOperateEnum.DELETE.equals((Object)operateEnum)) {
                return -1;
            }
            return 0;
        });
        items.stream().forEach(item -> {
            ClbrBillPushItemResultDTO respItemDTO = null;
            try {
                item.setSn(sn);
                item.setSysCode(sysCode);
                Assert.isTrue(!ObjectUtils.isEmpty(item.getSrcId()), "\u6765\u6e90ID\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
                ClbrBillOperateEnum operateEnum = ClbrBillOperateEnum.getEnumByCode((String)item.getOperate());
                Assert.isTrue(!ObjectUtils.isEmpty(operateEnum), "\u4e0d\u652f\u6301\u7684\u64cd\u4f5c\u7c7b\u578b[" + item.getOperate() + "]");
                switch (operateEnum) {
                    case DELETE: {
                        respItemDTO = this.deleteClbrBill((ClbrBillPushItemParamDTO)item);
                        break;
                    }
                    case ADD: {
                        respItemDTO = this.addClbrBill((ClbrBillPushItemParamDTO)item);
                        break;
                    }
                }
                if (!respItemDTO.getStatus().booleanValue()) {
                    pushStatus.set(false);
                }
            }
            catch (Exception e) {
                pushStatus.set(false);
                LOGGER.error(e.getMessage(), e);
                messageBuilder.append(item.getClbrBillCode()).append("\u534f\u540c\u5355\u636e\u7f16\u7801\u6570\u636e\u63a8\u9001\u5931\u8d25\uff1a").append(e.getMessage()).append("\n");
                respItemDTO = ClbrBillPushItemResultDTO.builder().srcId(item.getSrcId()).clbrBillCode(item.getClbrBillCode()).message(e.getMessage()).status(Boolean.valueOf(false));
            }
            finally {
                respItemDTOs.add(respItemDTO);
            }
        });
        if (!pushStatus.get()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        ClbrBillPushResultDTO clbrBillRespDTO = new ClbrBillPushResultDTO();
        clbrBillRespDTO.setSn(sn);
        clbrBillRespDTO.setItems(respItemDTOs);
        clbrBillRespDTO.setMessage(messageBuilder.toString());
        clbrBillRespDTO.setStatus(Boolean.valueOf(pushStatus.get()));
        return clbrBillRespDTO;
    }

    private ClbrBillPushItemResultDTO addClbrBill(ClbrBillPushItemParamDTO pushItemParamDTO) {
        ClbrBillTypeEnum billTypeEnum = ClbrBillTypeEnum.getEnumByCode((Integer)pushItemParamDTO.getClbrBillType());
        Assert.isTrue(!ObjectUtils.isEmpty(billTypeEnum), "\u4e1a\u52a1\u5355\u7c7b\u578b[" + pushItemParamDTO.getClbrBillType() + "]\u4e0d\u652f\u6301\u3002");
        ClbrBillDTO clbrBillDTO = ClbrBillConverter.convertPushItemDTO2DTO(pushItemParamDTO);
        switch (billTypeEnum) {
            case INITIATOR: {
                clbrBillDTO = this.addClbrInitiatorBill(clbrBillDTO);
                break;
            }
            case RECEIVER: {
                clbrBillDTO = this.addClbrReceiverBill(clbrBillDTO);
                break;
            }
        }
        return ClbrBillPushItemResultDTO.builder().srcId(clbrBillDTO.getSrcId()).clbrBillCode(clbrBillDTO.getClbrBillCode()).clbrCode(clbrBillDTO.getClbrCode()).message("\u6210\u529f").status(Boolean.valueOf(true));
    }

    private void checkClbrBillValid(ClbrBillDTO clbrBillDTO) {
        Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getSrcId()), "\u5355\u636eid\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getClbrBillType()), "\u534f\u540c\u5355\u636e\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getUserName()), "\u7528\u6237\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getUserTitle()), "\u7528\u6237\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getClbrBillCode()), "\u534f\u540c\u6e90\u5355\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getAmount()), "\u91d1\u989d\u4e0d\u80fd\u4e3a\u7a7a");
        if (ClbrBillTypeEnum.INITIATOR.getCode().equals(clbrBillDTO.getClbrBillType())) {
            Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getClbrType()), "\u53d1\u8d77\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
            Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getRelation()), "\u53d1\u8d77\u5173\u8054\u65b9\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (ClbrBillTypeEnum.RECEIVER.getCode().equals(clbrBillDTO.getClbrBillType())) {
            Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getClbrType()), "\u63a5\u6536\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
            Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getRelation()), "\u63a5\u6536\u5173\u8054\u65b9\u4e0d\u80fd\u4e3a\u7a7a");
            Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getOppRelation()), "\u53d1\u8d77\u5173\u8054\u65b9\u4e0d\u80fd\u4e3a\u7a7a");
            Assert.isTrue(!ObjectUtils.isEmpty(clbrBillDTO.getOppSrcId()), "\u6e90\u534f\u540c\u8bb0\u5f55id\u4e0d\u80fd\u4e3a\u7a7a");
        }
    }

    private ClbrBillDTO addClbrInitiatorBill(ClbrBillDTO clbrBillDTO) {
        this.checkClbrBillValid(clbrBillDTO);
        ClbrSchemeDTO clbrScheme = this.clbrSchemeService.getFirstClbrScheme(clbrBillDTO.getClbrType(), clbrBillDTO.getOppClbrType(), clbrBillDTO.getRelation(), clbrBillDTO.getOppRelation());
        if (clbrScheme == null) {
            throw new BusinessRuntimeException("\u672a\u5339\u914d\u5230\u534f\u540c\u65b9\u6848\u914d\u7f6e\u3002");
        }
        String oppClbrType = clbrScheme.getOppClbrTypes().split(",")[0];
        clbrBillDTO.setOppClbrType(oppClbrType);
        clbrBillDTO.setClbrSchemeId(clbrScheme.getId());
        clbrBillDTO.setFlowControlType(clbrScheme.getFlowControlType());
        clbrBillDTO.setVchrControlType(clbrScheme.getVchrControlType());
        clbrBillDTO.setOppClbrType(clbrScheme.getOppClbrTypes());
        clbrBillDTO.setThisRelation(clbrBillDTO.getRelation());
        clbrBillDTO.setThatRelation(clbrBillDTO.getOppRelation());
        ClbrBillEO clbrBillEO = ClbrBillConverter.convertDTO2EO(clbrBillDTO);
        this.clbrBillDao.add((BaseEntity)clbrBillEO);
        return clbrBillDTO;
    }

    private ClbrBillDTO addClbrReceiverBill(ClbrBillDTO clbrBillDTO) {
        this.checkClbrBillValid(clbrBillDTO);
        ArrayList<String> srcId = new ArrayList<String>();
        srcId.add(clbrBillDTO.getOppSrcId());
        List<ClbrBillEO> clbrBillEOs = this.clbrBillDao.listBySrcIds(srcId);
        if (CollectionUtils.isEmpty(clbrBillEOs)) {
            LOGGER.warn("\u5355\u636e\u7f16\u53f7\u4e3a\u3010" + clbrBillDTO.getClbrBillCode() + "\u3011\u7684\u53d1\u8d77\u65b9\u8bb0\u5f55\u5728\u534f\u540c\u5e73\u53f0\u4e0d\u5b58\u5728, OPPSRCID\u4e3a\uff1a" + clbrBillDTO.getOppSrcId());
            throw new BusinessRuntimeException("\u5355\u636e\u7f16\u53f7\u4e3a\u3010" + clbrBillDTO.getClbrBillCode() + "\u3011\u7684\u53d1\u8d77\u65b9\u8bb0\u5f55\u5728\u534f\u540c\u5e73\u53f0\u4e0d\u5b58\u5728");
        }
        double initiatorSumNoverifyAmount = clbrBillEOs.stream().mapToDouble(ClbrBillEO::getNoverifyAmount).sum();
        if (clbrBillEOs.size() == 1) {
            if (Math.abs(clbrBillDTO.getAmount()) > Math.abs(initiatorSumNoverifyAmount)) {
                throw new BusinessRuntimeException("\u63a5\u6536\u65b9\u91d1\u989d" + clbrBillDTO.getAmount() + "\u4e0d\u5141\u8bb8\u8d85\u8fc7\u5f15\u7528\u7684\u53d1\u8d77\u65b9\u672a\u534f\u540c\u91d1\u989d" + initiatorSumNoverifyAmount);
            }
        } else if (!clbrBillDTO.getAmount().equals(initiatorSumNoverifyAmount)) {
            throw new BusinessRuntimeException("\u63a5\u6536\u65b9\u91d1\u989d" + clbrBillDTO.getAmount() + "\u4e0e\u53d1\u8d77\u65b9\u672a\u534f\u540c\u603b\u91d1\u989d" + initiatorSumNoverifyAmount + "\u4e0d\u4e00\u81f4");
        }
        for (ClbrBillEO initiatorBill : clbrBillEOs) {
            if (initiatorBill.getRelation().equals(clbrBillDTO.getRelation()) && clbrBillDTO.getOppRelation().equals(initiatorBill.getOppRelation())) continue;
            throw new BusinessRuntimeException("\u63a5\u6536\u65b9\u5355\u636e\u4e0a\u7684\u53d1\u8d77\u5173\u8054\u65b9\u548c\u63a5\u6536\u5173\u8054\u65b9\u8ddf\u5f15\u7528\u7684\u53d1\u8d77\u65b9\u5355\u636e\u4e0a\u7684\u53d1\u8d77\u5173\u8054\u65b9\u548c\u63a5\u6536\u5173\u8054\u65b9\u4e0d\u4e00\u81f4");
        }
        clbrBillDTO.setThisRelation(clbrBillDTO.getOppRelation());
        clbrBillDTO.setThatRelation(clbrBillDTO.getRelation());
        ClbrBillEO clbrBillEO = ClbrBillConverter.convertDTO2EO(clbrBillDTO);
        clbrBillEO.setClbrSchemeId(clbrBillEOs.get(0).getClbrSchemeId());
        clbrBillEO.setFlowControlType(clbrBillEOs.get(0).getFlowControlType());
        clbrBillEO.setVchrControlType(clbrBillEOs.get(0).getVchrControlType());
        this.clbrBillDao.add((BaseEntity)clbrBillEO);
        List<ClbrBillConfirmMsgDTO> clbrBillConfirmMsgDTOS = this.confirm(ClbrBillConfirmTypeEnum.AUTO, clbrBillEOs, Arrays.asList(clbrBillEO), true, clbrBillDTO.getClbrCode(), clbrBillDTO.getGroupId());
        Optional<ClbrBillConfirmMsgDTO> existClbrAmount = clbrBillConfirmMsgDTOS.stream().filter(clbrBillConfirmMsgDTO -> clbrBillEO.getSrcId().equals(clbrBillConfirmMsgDTO.getSrcId())).filter(clbrBillConfirmMsgDTO -> Math.abs(clbrBillConfirmMsgDTO.getCurrentAmount()) > 0.0).findAny();
        if (!existClbrAmount.isPresent()) {
            throw new BusinessRuntimeException("\u63a5\u6536\u65b9\u81ea\u52a8\u534f\u540c\u672a\u6210\u529f");
        }
        clbrBillDTO = ClbrBillConverter.convertEO2DTO(clbrBillEO);
        return clbrBillDTO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ClbrBillConfirmMsgDTO> confirm(ClbrBillConfirmTypeEnum clbrBillConfirmTypeEnum, List<ClbrBillEO> initiatorClbrBillEOs, List<ClbrBillEO> receiverClbrBillEOs, boolean isUpdateRespository, String clbrCode, String groupId) {
        this.checkConfirmArgsValid(initiatorClbrBillEOs, receiverClbrBillEOs);
        this.checkInitiatorClbrBillsValid(clbrBillConfirmTypeEnum, initiatorClbrBillEOs);
        Collections.sort(initiatorClbrBillEOs, (o1, o2) -> {
            Double o1InitiatorNoverifyAmount = ConverterUtils.getAsDouble((Object)o1.getNoverifyAmount(), (Double)0.0);
            Double o2InitiatorNoverifyAmount = ConverterUtils.getAsDouble((Object)o2.getNoverifyAmount(), (Double)0.0);
            return o1InitiatorNoverifyAmount.compareTo(o2InitiatorNoverifyAmount);
        });
        ArrayList clbrBillCheckEOS = new ArrayList();
        ArrayList<ClbrBillConfirmMsgDTO> clbrBillConfirmResultDTOS = new ArrayList<ClbrBillConfirmMsgDTO>();
        if (!org.springframework.util.StringUtils.hasText(groupId)) {
            groupId = UUIDUtils.newUUIDStr();
        }
        if (!org.springframework.util.StringUtils.hasText(clbrCode)) {
            clbrCode = ClbrUtils.newClbrCode();
        }
        String finalClbrCode = clbrCode;
        String finalGroupId = groupId;
        initiatorClbrBillEOs.stream().forEach(initiatorClbrBillEO -> {
            List<ClbrBillEO> filterReceiverClbrBillEOs = this.sortAndFilterReceiverClbrBills(clbrBillConfirmTypeEnum, (ClbrBillEO)((Object)initiatorClbrBillEO), receiverClbrBillEOs);
            filterReceiverClbrBillEOs.stream().forEach(filterReceiverClbrBillEO -> {
                ClbrBaseInfoDTO clbrBaseInfoDTO = this.buildOne2OneClbrConfirmInfo(clbrBillConfirmTypeEnum, (ClbrBillEO)((Object)initiatorClbrBillEO), (ClbrBillEO)((Object)((Object)filterReceiverClbrBillEO)), finalGroupId, finalClbrCode);
                List<ClbrBillCheckEO> groupClbrBillCheckEO = this.buildOne2OneClbrAmountCheckEO(clbrBillConfirmTypeEnum, (ClbrBillEO)((Object)initiatorClbrBillEO), (ClbrBillEO)((Object)((Object)filterReceiverClbrBillEO)), clbrBaseInfoDTO);
                clbrBillCheckEOS.addAll(groupClbrBillCheckEO);
                HashMap<String, ClbrBillEO> id2ClbrBillInfoMap = new HashMap<String, ClbrBillEO>();
                id2ClbrBillInfoMap.put(initiatorClbrBillEO.getId(), (ClbrBillEO)((Object)initiatorClbrBillEO));
                id2ClbrBillInfoMap.put(filterReceiverClbrBillEO.getId(), (ClbrBillEO)((Object)((Object)filterReceiverClbrBillEO)));
                List<ClbrBillConfirmMsgDTO> groupClbrBillConfirmResultDTOS = this.buildOne2OneClbBillConfirmMsgDTOs(groupClbrBillCheckEO, id2ClbrBillInfoMap);
                clbrBillConfirmResultDTOS.addAll(groupClbrBillConfirmResultDTOS);
            });
        });
        if (isUpdateRespository) {
            ArrayList<ClbrBillEO> updateEOs = new ArrayList<ClbrBillEO>();
            updateEOs.addAll(initiatorClbrBillEOs);
            updateEOs.addAll(receiverClbrBillEOs);
            this.clbrBillDao.updateBatch(updateEOs);
        }
        if (!CollectionUtils.isEmpty(clbrBillCheckEOS)) {
            this.clbrBillCheckDao.addBatch(clbrBillCheckEOS);
        }
        Map sysCode2ClbrBillConfirmMsgDTOsMap = clbrBillConfirmResultDTOS.stream().map(clbrBillConfirmMsgDTO -> {
            clbrBillConfirmMsgDTO.setClbrCode("");
            return clbrBillConfirmMsgDTO;
        }).collect(Collectors.groupingBy(ClbrBillConfirmMsgDTO::getSysCode, Collectors.mapping(Function.identity(), Collectors.toList())));
        ClbrBillConfirmMsgContext context = new ClbrBillConfirmMsgContext();
        context.setClbrBillConfirmTypeEnum(clbrBillConfirmTypeEnum);
        sysCode2ClbrBillConfirmMsgDTOsMap.forEach((sysCode, clbrBillConfirmMsgDTOS) -> this.adapterFactory.create((String)sysCode).sendClbrBillConfirmMessage(context, (List<ClbrBillConfirmMsgDTO>)clbrBillConfirmMsgDTOS, ClbrOperateTypeEnum.CHECK.getCode()));
        return clbrBillConfirmResultDTOS;
    }

    private List<ClbrBillConfirmMsgDTO> buildOne2OneClbBillConfirmMsgDTOs(List<ClbrBillCheckEO> clbrBillCheckEOS, Map<String, ClbrBillEO> id2ClbrBillInfoMap) {
        ArrayList<ClbrBillConfirmMsgDTO> groupClbrBillConfirmResultDTOS = new ArrayList<ClbrBillConfirmMsgDTO>();
        clbrBillCheckEOS.forEach(clbrAmountCheckEO -> {
            ClbrBillEO clbrBillEO = (ClbrBillEO)((Object)((Object)id2ClbrBillInfoMap.get(clbrAmountCheckEO.getBillId())));
            ClbrBillConfirmMsgDTO confirmResultDTO = ClbrBillConfirmMsgDTO.builder().sn(clbrBillEO.getSn()).amount(clbrBillEO.getAmount()).noverifyAmount(clbrBillEO.getNoverifyAmount()).verifyedAmount(clbrBillEO.getVerifyedAmount()).currentAmount(clbrAmountCheckEO.getClbrAmount()).clbrBillCode(clbrBillEO.getClbrBillCode()).oppClbrBillCode(clbrBillEO.getClbrBillCode()).clbrCode(clbrBillEO.getClbrCode()).clbrTime(clbrBillEO.getClbrTime()).srcId(clbrBillEO.getSrcId()).oppSrcId(clbrBillEO.getOppSrcId()).sysCode(clbrBillEO.getSysCode()).oppSysCode(clbrBillEO.getSysCode());
            groupClbrBillConfirmResultDTOS.add(confirmResultDTO);
        });
        return groupClbrBillConfirmResultDTOS;
    }

    private List<ClbrBillCheckEO> buildOne2OneClbrAmountCheckEO(ClbrBillConfirmTypeEnum clbrBillConfirmTypeEnum, ClbrBillEO filterInitiatorClbrBillEO, ClbrBillEO filterReceiverClbrBillEO, ClbrBaseInfoDTO clbrBaseInfoDTO) {
        ClbrBillCheckEO initiatorClbrBillCheckEO = new ClbrBillCheckEO();
        BeanUtils.copyProperties((Object)filterInitiatorClbrBillEO, (Object)initiatorClbrBillCheckEO);
        initiatorClbrBillCheckEO.setId(UUIDUtils.newUUIDStr());
        initiatorClbrBillCheckEO.setBillId(filterInitiatorClbrBillEO.getId());
        initiatorClbrBillCheckEO.setConfirmType(clbrBillConfirmTypeEnum.getCode());
        initiatorClbrBillCheckEO.setClbrAmount(clbrBaseInfoDTO.getCurrentClbrAmount());
        initiatorClbrBillCheckEO.setClbrCode(clbrBaseInfoDTO.getClbrCode());
        initiatorClbrBillCheckEO.setClbrTime(clbrBaseInfoDTO.getClbrTime());
        initiatorClbrBillCheckEO.setClbrBillType(ClbrBillTypeEnum.INITIATOR.getCode());
        initiatorClbrBillCheckEO.setGroupId(clbrBaseInfoDTO.getGroupId());
        initiatorClbrBillCheckEO.setConfirmStatus(clbrBaseInfoDTO.getConfirmStatus());
        if (ClbrBillConfirmTypeEnum.AUTO.equals((Object)clbrBillConfirmTypeEnum)) {
            initiatorClbrBillCheckEO.setInitiateConfirmUsername(filterReceiverClbrBillEO.getUserName());
            initiatorClbrBillCheckEO.setReceiveConfirmUsername(filterReceiverClbrBillEO.getUserName());
        } else {
            initiatorClbrBillCheckEO.setInitiateConfirmUsername(NpContextHolder.getContext().getUserName());
        }
        ClbrBillCheckEO receiverClbrBillCheckEO = new ClbrBillCheckEO();
        BeanUtils.copyProperties((Object)filterReceiverClbrBillEO, (Object)receiverClbrBillCheckEO);
        receiverClbrBillCheckEO.setId(UUIDUtils.newUUIDStr());
        receiverClbrBillCheckEO.setBillId(filterReceiverClbrBillEO.getId());
        receiverClbrBillCheckEO.setConfirmType(clbrBillConfirmTypeEnum.getCode());
        receiverClbrBillCheckEO.setClbrAmount(clbrBaseInfoDTO.getCurrentClbrAmount());
        receiverClbrBillCheckEO.setClbrCode(clbrBaseInfoDTO.getClbrCode());
        receiverClbrBillCheckEO.setClbrTime(clbrBaseInfoDTO.getClbrTime());
        receiverClbrBillCheckEO.setClbrBillType(ClbrBillTypeEnum.RECEIVER.getCode());
        receiverClbrBillCheckEO.setGroupId(clbrBaseInfoDTO.getGroupId());
        receiverClbrBillCheckEO.setConfirmStatus(clbrBaseInfoDTO.getConfirmStatus());
        if (ClbrBillConfirmTypeEnum.AUTO.equals((Object)clbrBillConfirmTypeEnum)) {
            receiverClbrBillCheckEO.setInitiateConfirmUsername(filterReceiverClbrBillEO.getUserName());
            receiverClbrBillCheckEO.setReceiveConfirmUsername(filterReceiverClbrBillEO.getUserName());
        } else {
            receiverClbrBillCheckEO.setInitiateConfirmUsername(NpContextHolder.getContext().getUserName());
        }
        ArrayList<ClbrBillCheckEO> clbrBillCheckEOS = new ArrayList<ClbrBillCheckEO>();
        clbrBillCheckEOS.add(initiatorClbrBillCheckEO);
        clbrBillCheckEOS.add(receiverClbrBillCheckEO);
        return clbrBillCheckEOS;
    }

    private ClbrBaseInfoDTO buildOne2OneClbrConfirmInfo(ClbrBillConfirmTypeEnum clbrBillConfirmTypeEnum, ClbrBillEO filterInitiatorClbrBillEO, ClbrBillEO filterReceiverClbrBillEO, String groupId, String clbrCode) {
        String initiatorClbrCode = filterInitiatorClbrBillEO.getClbrCode();
        String receiverClbrCode = filterReceiverClbrBillEO.getClbrCode();
        String initiatorClbrBillCode = ConverterUtils.getAsString((Object)filterInitiatorClbrBillEO.getClbrBillCode(), (String)"");
        String newClbrCode = !ObjectUtils.isEmpty(initiatorClbrCode) ? initiatorClbrCode : (!ObjectUtils.isEmpty(receiverClbrCode) ? receiverClbrCode : clbrCode);
        Double currentClbrAmount = Math.abs(filterInitiatorClbrBillEO.getNoverifyAmount()) <= Math.abs(filterReceiverClbrBillEO.getNoverifyAmount()) ? filterInitiatorClbrBillEO.getNoverifyAmount() : filterReceiverClbrBillEO.getNoverifyAmount();
        Date clbrTime = new Date();
        filterInitiatorClbrBillEO.setClbrTime(clbrTime);
        filterInitiatorClbrBillEO.setClbrCode(newClbrCode);
        filterInitiatorClbrBillEO.setVerifyedAmount(filterInitiatorClbrBillEO.getVerifyedAmount() + currentClbrAmount);
        filterInitiatorClbrBillEO.setNoverifyAmount(filterInitiatorClbrBillEO.getAmount() - filterInitiatorClbrBillEO.getVerifyedAmount());
        filterInitiatorClbrBillEO.setBillState(filterInitiatorClbrBillEO.getNoverifyAmount() == 0.0 ? ClbrBillStateEnum.CONFIRM.getCode() : ClbrBillStateEnum.PARTCONFIRM.getCode());
        filterInitiatorClbrBillEO.setConfirmType(clbrBillConfirmTypeEnum.getCode());
        filterInitiatorClbrBillEO.setOppClbrType(Objects.isNull(filterInitiatorClbrBillEO.getOppClbrType()) ? filterReceiverClbrBillEO.getClbrType() : filterInitiatorClbrBillEO.getOppClbrType());
        filterReceiverClbrBillEO.setClbrTime(clbrTime);
        filterReceiverClbrBillEO.setClbrCode(newClbrCode);
        filterReceiverClbrBillEO.setVerifyedAmount(filterReceiverClbrBillEO.getVerifyedAmount() + currentClbrAmount);
        filterReceiverClbrBillEO.setNoverifyAmount(filterReceiverClbrBillEO.getAmount() - filterReceiverClbrBillEO.getVerifyedAmount());
        filterReceiverClbrBillEO.setOppClbrBillCode(initiatorClbrBillCode);
        filterReceiverClbrBillEO.setBillState(filterReceiverClbrBillEO.getNoverifyAmount() == 0.0 ? ClbrBillStateEnum.CONFIRM.getCode() : ClbrBillStateEnum.PARTCONFIRM.getCode());
        filterReceiverClbrBillEO.setConfirmType(clbrBillConfirmTypeEnum.getCode());
        filterReceiverClbrBillEO.setOppClbrType(Objects.isNull(filterReceiverClbrBillEO.getOppClbrType()) ? filterInitiatorClbrBillEO.getClbrType() : filterReceiverClbrBillEO.getOppClbrType());
        ClbrBaseInfoDTO clbrBaseInfoDTO = new ClbrBaseInfoDTO();
        clbrBaseInfoDTO.setClbrCode(newClbrCode);
        clbrBaseInfoDTO.setClbrTime(clbrTime);
        clbrBaseInfoDTO.setCurrentClbrAmount(currentClbrAmount);
        clbrBaseInfoDTO.setGroupId(groupId);
        clbrBaseInfoDTO.setConfirmStatus(ClbrBillConfirmTypeEnum.AUTO.equals((Object)clbrBillConfirmTypeEnum) ? ClbrConfirmStatusEnum.BOTH_CONFIRMED.getCode() : ClbrConfirmStatusEnum.SINGLE_CONFIRMED.getCode());
        return clbrBaseInfoDTO;
    }

    private List<ClbrBillEO> filterMatchSchemeReceiverClbrBills(ClbrBillConfirmTypeEnum clbrBillConfirmTypeEnum, List<ClbrBillEO> receiverClbrBillEOs, ClbrBillEO initiatorClbrBillEO) {
        if (ClbrBillConfirmTypeEnum.AUTO.equals((Object)clbrBillConfirmTypeEnum)) {
            return receiverClbrBillEOs;
        }
        List<ClbrSchemeDTO> clbrSchemeDTOS = this.getClbrSchemeDTOsByClbrTypeAndRelation(initiatorClbrBillEO);
        if (CollectionUtils.isEmpty(clbrSchemeDTOS)) {
            return Collections.emptyList();
        }
        String initiatorClbrType = ConverterUtils.getAsString((Object)initiatorClbrBillEO.getClbrType());
        String initiatorRelation = ConverterUtils.getAsString((Object)initiatorClbrBillEO.getRelation());
        List<ClbrBillEO> filterReceiverClbrBillEOs = this.sortAndFilterReceiverClbrBills(clbrBillConfirmTypeEnum, initiatorClbrBillEO, receiverClbrBillEOs);
        filterReceiverClbrBillEOs.stream().filter(receiverClbrBillEO -> {
            String receiverRelation = receiverClbrBillEO.getRelation();
            String receiverClbrType = receiverClbrBillEO.getClbrType();
            Optional<ClbrSchemeDTO> matchClbrSchemeOptional = clbrSchemeDTOS.stream().filter(schemeDTO -> {
                String clbrTypes = ConverterUtils.getAsString((Object)schemeDTO.getClbrTypes(), (String)"");
                String relations = ConverterUtils.getAsString((Object)schemeDTO.getRelations(), (String)"");
                String oppClbrTypes = ConverterUtils.getAsString((Object)schemeDTO.getOppClbrTypes(), (String)"");
                String oppRelations = ConverterUtils.getAsString((Object)schemeDTO.getOppRelations(), (String)"");
                return oppClbrTypes.contains(receiverClbrType) && oppRelations.contains(receiverRelation) && clbrTypes.contains(initiatorClbrType) && relations.contains(initiatorRelation);
            }).findAny();
            return matchClbrSchemeOptional.isPresent();
        }).collect(Collectors.toList());
        return filterReceiverClbrBillEOs;
    }

    private List<ClbrBillEO> sortAndFilterReceiverClbrBills(ClbrBillConfirmTypeEnum clbrBillConfirmTypeEnum, ClbrBillEO initiatorClbrBillEO, List<ClbrBillEO> receiverClbrBillEOs) {
        String initiatorOppRelation = ConverterUtils.getAsString((Object)initiatorClbrBillEO.getOppRelation());
        String initiatorRelation = ConverterUtils.getAsString((Object)initiatorClbrBillEO.getRelation());
        String initiatorClbrBillCode = ConverterUtils.getAsString((Object)initiatorClbrBillEO.getClbrBillCode(), (String)"");
        String initiatorSrcId = ConverterUtils.getAsString((Object)initiatorClbrBillEO.getSrcId(), (String)"");
        String initiatorClbrCode = initiatorClbrBillEO.getClbrCode();
        List<ClbrBillEO> filterReceiverClbrBillEOs = receiverClbrBillEOs.stream().filter(receiverClbrBillEO -> {
            String receiverRelation = receiverClbrBillEO.getRelation();
            String receiverOppRelation = receiverClbrBillEO.getOppRelation();
            String receiverClbrCode = receiverClbrBillEO.getClbrCode();
            boolean finishClbrConfirm = receiverClbrBillEO.getAmount().equals(receiverClbrBillEO.getVerifyedAmount());
            if (finishClbrConfirm) {
                return false;
            }
            if (!(!ClbrBillConfirmTypeEnum.MANUAL.equals((Object)clbrBillConfirmTypeEnum) || initiatorRelation.equals(receiverOppRelation) && initiatorOppRelation.equals(receiverRelation))) {
                return false;
            }
            if (!(!ClbrBillConfirmTypeEnum.AUTO.equals((Object)clbrBillConfirmTypeEnum) || initiatorRelation.equals(receiverRelation) && initiatorOppRelation.equals(receiverOppRelation))) {
                return false;
            }
            return ObjectUtils.isEmpty(initiatorClbrCode) || ObjectUtils.isEmpty(receiverClbrCode) || initiatorClbrCode.equals(receiverClbrCode);
        }).collect(Collectors.toList());
        Double initiatorNoverifyAmount = ConverterUtils.getAsDouble((Object)initiatorClbrBillEO.getNoverifyAmount(), (Double)0.0);
        Collections.sort(filterReceiverClbrBillEOs, (o1, o2) -> {
            int sortValue = 0;
            Double o1ReceiverNoverifyAmount = ConverterUtils.getAsDouble((Object)o1.getNoverifyAmount(), (Double)0.0);
            Double o2ReceiverNoverifyAmount = ConverterUtils.getAsDouble((Object)o2.getNoverifyAmount(), (Double)0.0);
            String o1ReceiverOppClbrBillCode = ConverterUtils.getAsString((Object)o1.getOppClbrBillCode(), (String)"");
            String o2ReceiverOppClbrBillCode = ConverterUtils.getAsString((Object)o2.getOppClbrBillCode(), (String)"");
            String o1ReceiverOppSrcId = ConverterUtils.getAsString((Object)o1.getOppSrcId(), (String)"");
            String o2ReceiverOppSrcId = ConverterUtils.getAsString((Object)o2.getOppSrcId(), (String)"");
            sortValue = o1ReceiverOppSrcId.equals(initiatorSrcId) ? -1 : (o2ReceiverOppSrcId.equals(initiatorSrcId) ? 1 : (o1ReceiverOppClbrBillCode.equals(initiatorClbrBillCode) ? -1 : (o2ReceiverOppClbrBillCode.equals(initiatorClbrBillCode) ? 1 : (o1ReceiverNoverifyAmount.equals(initiatorNoverifyAmount) ? -1 : (o2ReceiverNoverifyAmount.equals(initiatorNoverifyAmount) ? 1 : Double.valueOf(Math.abs(o1ReceiverNoverifyAmount)).compareTo(Math.abs(o2ReceiverNoverifyAmount)))))));
            return sortValue;
        });
        return filterReceiverClbrBillEOs;
    }

    private void checkInitiatorClbrBillsValid(ClbrBillConfirmTypeEnum clbrBillConfirmTypeEnum, List<ClbrBillEO> initiatorClbrBillEOs) {
        initiatorClbrBillEOs.stream().forEach(initiatorClbrBillEO -> {
            boolean finishClbrConfirm = initiatorClbrBillEO.getAmount().equals(initiatorClbrBillEO.getVerifyedAmount());
            if (finishClbrConfirm) {
                throw new BusinessRuntimeException("\u5355\u636e\u7f16\u53f7\u4e3a\u3010" + initiatorClbrBillEO.getClbrBillCode() + "\u3011\u7684\u53d1\u8d77\u65b9\u8bb0\u5f55\u5df2\u5b8c\u5168\u534f\u540c\uff0c\u8bf7\u9009\u62e9\u5176\u4ed6\u672a\u534f\u540c\u7684\u53d1\u8d77\u65b9\u8bb0\u5f55");
            }
            if (ClbrBillConfirmTypeEnum.MANUAL.equals((Object)clbrBillConfirmTypeEnum) && ClbrBillConfirmTypeEnum.AUTO.getCode().equals(initiatorClbrBillEO.getConfirmType())) {
                throw new BusinessRuntimeException("\u5355\u636e\u7f16\u53f7\u4e3a\u3010" + initiatorClbrBillEO.getClbrBillCode() + "\u3011\u7684\u53d1\u8d77\u65b9\u8bb0\u5f55\u6267\u884c\u8fc7\u624b\u5de5\u90e8\u5206\u534f\u540c\uff0c\u4e0d\u5141\u8bb8\u518d\u53c2\u4e0e\u81ea\u52a8\u534f\u540c");
            }
        });
    }

    private void checkConfirmArgsValid(List<ClbrBillEO> initiatorClbrBillEOs, List<ClbrBillEO> receiverClbrBillEOs) {
        Set initiatorClbrIds = initiatorClbrBillEOs.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        Set receiverClbrIds = receiverClbrBillEOs.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        initiatorClbrIds.retainAll(receiverClbrIds);
        if (!CollectionUtils.isEmpty(initiatorClbrIds)) {
            throw new BusinessRuntimeException("\u53d1\u8d77\u65b9\u6570\u636e\u548c\u63a5\u6536\u65b9\u6570\u636e\u505a\u534f\u540c\u786e\u8ba4\u65f6\u4e0d\u5141\u8bb8\u5b58\u5728\u4ea4\u96c6");
        }
    }

    private List<ClbrSchemeDTO> getClbrSchemeDTOsByClbrTypeAndRelation(ClbrBillEO initiatorClbrBillEO) {
        String initiatorClbrType = ConverterUtils.getAsString((Object)initiatorClbrBillEO.getClbrType());
        String initiatorRelation = ConverterUtils.getAsString((Object)initiatorClbrBillEO.getRelation());
        ClbrSchemeCondition clbrSchemeCondition = new ClbrSchemeCondition();
        clbrSchemeCondition.setClbrTypes(initiatorClbrType);
        clbrSchemeCondition.setRelations(initiatorRelation);
        clbrSchemeCondition.setPageNum(Integer.valueOf(-1));
        clbrSchemeCondition.setPageSize(Integer.valueOf(-1));
        List<ClbrSchemeDTO> clbrSchemeDTOS = this.clbrSchemeService.listByConditionToDTO(clbrSchemeCondition);
        return clbrSchemeDTOS;
    }

    private void cancelConfirm(ClbrBillConfirmTypeEnum clbrBillConfirmTypeEnum, List<ClbrBillEO> clbrBillEOS) {
        List filterClbrBillEOs = clbrBillEOS.stream().filter(clbrBillEO -> !ObjectUtils.isEmpty(clbrBillEO.getClbrCode())).collect(Collectors.toList());
        Set<String> billIds = filterClbrBillEOs.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        List<ClbrBillCheckEO> clbrBillCheckEOS = this.clbrBillCheckDao.queryGroupByBillIds(billIds);
        this.executeCancelConfirm(clbrBillConfirmTypeEnum, clbrBillCheckEOS);
    }

    private ClbrBillPushItemResultDTO deleteClbrBill(ClbrBillPushItemParamDTO pushItemParamDTO) {
        ClbrBillEO clbrBillEO = this.clbrBillDao.queryBySrcId(pushItemParamDTO.getSrcId());
        if (clbrBillEO == null) {
            return ClbrBillPushItemResultDTO.builder().srcId(pushItemParamDTO.getSrcId()).clbrBillCode(pushItemParamDTO.getClbrBillCode()).message("\u534f\u540c\u5e73\u53f0\u4e0d\u5b58\u5728\u8be5\u6761\u8bb0\u5f55").status(Boolean.valueOf(true));
        }
        Double verifyedAmount = ConverterUtils.getAsDouble((Object)clbrBillEO.getVerifyedAmount(), (Double)0.0);
        if (Math.abs(verifyedAmount) > 0.0) {
            this.cancelConfirm(ClbrBillConfirmTypeEnum.AUTO, Arrays.asList(clbrBillEO));
        }
        clbrBillEO.setBillState(ClbrBillStateEnum.DELETE.getCode());
        ClbrBillDeleteEO clbrBillDeleteEO = ClbrBillConverter.convertBillEO2DeleteEO(clbrBillEO);
        this.clbrBillDeleteDao.add((BaseEntity)clbrBillDeleteEO);
        this.clbrBillDao.delete((BaseEntity)clbrBillEO);
        return ClbrBillPushItemResultDTO.builder().srcId(pushItemParamDTO.getSrcId()).clbrBillCode(pushItemParamDTO.getClbrBillCode()).message("\u5df2\u5220\u9664").status(Boolean.valueOf(true));
    }

    @Override
    public ClbrBillDTO query(String sysCode, String clbrBillcode) {
        ClbrBillEO paramClbrBillEO = new ClbrBillEO();
        paramClbrBillEO.setSysCode(sysCode);
        paramClbrBillEO.setClbrBillCode(clbrBillcode);
        ClbrBillEO clbrBillEO = (ClbrBillEO)this.clbrBillDao.selectByEntity((BaseEntity)paramClbrBillEO);
        ClbrBillDTO clbrBillDTO = ClbrBillConverter.convertEO2DTO(clbrBillEO);
        return clbrBillDTO;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Integer countInitiatorNotConfirmByUser(String sysCode, String userName, String roleCode, String relation) {
        List<ClbrBillDTO> clbrBillDTOs;
        if (!org.springframework.util.StringUtils.hasText(userName)) {
            LOGGER.error("\u534f\u540c\u4ee3\u529e\u67e5\u8be2\u53c2\u6570\u7528\u6237\u540d\u4e3a\u7a7a\u3002");
            return 0;
        }
        this.changeUserContext(userName);
        try {
            ClbrBillSsoConditionDTO clbrBillSsoConditionDTO = new ClbrBillSsoConditionDTO();
            clbrBillSsoConditionDTO.setSysCode(sysCode);
            clbrBillSsoConditionDTO.setUserName(userName);
            clbrBillSsoConditionDTO.setRoleCode(roleCode);
            clbrBillSsoConditionDTO.setRelation(relation);
            clbrBillDTOs = this.listInitiatorNotConfirmByUser(clbrBillSsoConditionDTO);
        }
        finally {
            ShiroUtil.unbindUser();
        }
        int count = clbrBillDTOs.size();
        return count;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ClbrBillDTO> listInitiatorNotConfirmByUser(ClbrBillSsoConditionDTO clbrBillSsoConditionDTO) {
        boolean exist;
        if (!org.springframework.util.StringUtils.hasText(clbrBillSsoConditionDTO.getRoleCode())) {
            List roleList = this.roleService.getByIdentity(NpContextHolder.getContext().getIdentityId());
            List roleCodes = roleList.stream().map(Namable::getName).collect(Collectors.toList());
            String roleCodeStr = String.join((CharSequence)",", roleCodes);
            clbrBillSsoConditionDTO.setRoleCode(roleCodeStr);
        }
        if (!org.springframework.util.StringUtils.hasText(NpContextHolder.getContext().getUserName())) {
            this.changeUserContext(clbrBillSsoConditionDTO.getUserName());
        }
        if (exist = this.clbrReceiveSettingService.exist()) {
            Map<String, Set<String>> receiveClbrType2Relations = this.clbrReceiveSettingService.getReceiveClbrType2Relations(clbrBillSsoConditionDTO.getUserName(), clbrBillSsoConditionDTO.getRoleCode());
            if (receiveClbrType2Relations.isEmpty()) {
                return new ArrayList<ClbrBillDTO>();
            }
            clbrBillSsoConditionDTO.setReceiveClbrType2Relations(receiveClbrType2Relations);
        } else {
            clbrBillSsoConditionDTO.setReceiveClbrType2Relations(new HashMap());
        }
        List<ClbrBillEO> clbrBillEOs = this.clbrBillDao.listInitiatorNotConfirmByUser(clbrBillSsoConditionDTO);
        clbrBillEOs = this.handleBillAmount(clbrBillEOs, clbrBillSsoConditionDTO.getClbrBillCode());
        ClbrTypePenetrationControlUtils.validPenetrationControl(clbrBillEOs);
        List<ClbrBillDTO> beforeFilterClbrBillDTOs = ClbrBillConverter.convertEO2DTO(clbrBillEOs);
        return beforeFilterClbrBillDTOs;
    }

    @Override
    public Integer countProcessInitiatorNotConfirmByUser(String sysCode, String userName, String roleCode, String relation) {
        ClbrProcessCondition clbrProcessCondition = new ClbrProcessCondition();
        clbrProcessCondition.setPageSize(-1);
        clbrProcessCondition.setPageNum(-1);
        clbrProcessCondition.setSysCode(sysCode);
        clbrProcessCondition.setUserName(userName);
        clbrProcessCondition.setRoleCode(roleCode);
        clbrProcessCondition.setRelation(relation);
        List clbrBillCheckVOS = this.listProcessInitiatorNotConfirmByUser(clbrProcessCondition, ClbrTabEnum.PROCESS_INITIATOR_NOT_CONFIRM.getCode()).getList();
        if (CollectionUtils.isEmpty(clbrBillCheckVOS)) {
            return clbrBillCheckVOS.size();
        }
        return ((ClbrBillCheckVO)clbrBillCheckVOS.get(clbrBillCheckVOS.size() - 1)).getIndex();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public PageInfo<ClbrBillCheckVO> listProcessInitiatorNotConfirmByUser(ClbrProcessCondition clbrProcessCondition, String tabFlag) {
        PageInfo<ClbrBillCheckVO> clbrAmountCheckVOPageInfo;
        String userName = clbrProcessCondition.getUserName();
        if (!org.springframework.util.StringUtils.hasText(userName)) {
            LOGGER.error("\u534f\u540c\u4ee3\u529e\u67e5\u8be2\u53c2\u6570\u7528\u6237\u540d\u4e3a\u7a7a\u3002");
            return PageInfo.empty();
        }
        this.changeUserContext(userName);
        try {
            boolean exist = this.clbrReceiveSettingService.exist();
            if (exist) {
                Map<String, Set<String>> receiveClbrType2Relations = this.clbrReceiveSettingService.getReceiveClbrType2Relations(clbrProcessCondition.getUserName(), clbrProcessCondition.getRoleCode());
                if (receiveClbrType2Relations.isEmpty()) {
                    PageInfo pageInfo = PageInfo.empty();
                    return pageInfo;
                }
                clbrProcessCondition.setReceiveClbrType2Relations(receiveClbrType2Relations);
            } else {
                clbrProcessCondition.setReceiveClbrType2Relations(new HashMap());
            }
            clbrProcessCondition.setTabFlag(ClbrTabEnum.getEnumByCode((String)tabFlag));
            clbrAmountCheckVOPageInfo = this.clbrProcessService.queryConfirm(clbrProcessCondition, tabFlag);
        }
        finally {
            ShiroUtil.unbindUser();
        }
        return clbrAmountCheckVOPageInfo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillDTO> listByReceiverInfo(ClbrBillSsoConditionDTO clbrBillSsoConditionDTO) {
        if (ClbrBillStateEnum.INIT.getCode().equals(clbrBillSsoConditionDTO.getBillState())) {
            return this.listInitiatorNotConfirmBySsoCondition(clbrBillSsoConditionDTO);
        }
        boolean exist = this.clbrReceiveSettingService.exist();
        if (exist) {
            Map<String, Set<String>> receiveClbrType2Relations = this.clbrReceiveSettingService.getReceiveClbrType2Relations(clbrBillSsoConditionDTO.getUserName(), clbrBillSsoConditionDTO.getRoleCode());
            if (receiveClbrType2Relations.isEmpty()) {
                return PageInfo.empty();
            }
            clbrBillSsoConditionDTO.setReceiveClbrType2Relations(receiveClbrType2Relations);
        } else {
            clbrBillSsoConditionDTO.setReceiveClbrType2Relations(new HashMap());
        }
        return this.listInitiatorConfirmBySsoCondition(clbrBillSsoConditionDTO);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<ClbrBillCheckVO> filterAuthCheckBillData(String userName, List<ClbrBillCheckVO> clbrBillCheckVOS) {
        List authRelations;
        if (ObjectUtils.isEmpty(userName)) {
            return clbrBillCheckVOS;
        }
        this.changeUserContext(userName);
        try {
            authRelations = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_RELATION");
            if (CollectionUtils.isEmpty(authRelations)) {
                List<ClbrBillCheckVO> list = Collections.emptyList();
                return list;
            }
        }
        finally {
            ShiroUtil.unbindUser();
        }
        List authRelationCodes = authRelations.stream().map(GcBaseData::getCode).collect(Collectors.toList());
        return clbrBillCheckVOS.stream().filter(beforeFilterClbrBillDTO -> authRelationCodes.contains(beforeFilterClbrBillDTO.getRelation())).collect(Collectors.toList());
    }

    private List<ClbrBillEO> handleBillAmount(List<ClbrBillEO> notHandledClbrBillEOs, String clbrBillCode) {
        if (CollectionUtils.isEmpty(notHandledClbrBillEOs) || !org.springframework.util.StringUtils.hasText(clbrBillCode)) {
            return notHandledClbrBillEOs;
        }
        ClbrBillCheckEO checkCondition = new ClbrBillCheckEO();
        checkCondition.setClbrBillCode(clbrBillCode);
        checkCondition.setClbrBillType(ClbrBillTypeEnum.RECEIVER.getCode());
        checkCondition.setConfirmStatus(ClbrConfirmStatusEnum.BOTH_CONFIRMED.getCode());
        List receiverClbrBillCheckEOS = this.clbrBillCheckDao.selectList((BaseEntity)checkCondition);
        if (CollectionUtils.isEmpty(receiverClbrBillCheckEOS)) {
            return notHandledClbrBillEOs;
        }
        List<String> groupIds = receiverClbrBillCheckEOS.stream().map(ClbrBillCheckEO::getGroupId).collect(Collectors.toList());
        List<ClbrBillCheckEO> initiatorClbrBillCheckEOS = this.clbrBillCheckDao.listByGroupIds(groupIds);
        Map<String, Double> billId2CheckedAmountMap = initiatorClbrBillCheckEOS.stream().filter(clbrAmountCheckEO -> ClbrBillTypeEnum.INITIATOR.getCode().equals(clbrAmountCheckEO.getClbrBillType())).collect(Collectors.groupingBy(ClbrBillCheckEO::getBillId, Collectors.summingDouble(ClbrBillCheckEO::getClbrAmount)));
        List noNeedHandledClbrBillEOs = notHandledClbrBillEOs.stream().filter(clbrBillEO -> !billId2CheckedAmountMap.containsKey(clbrBillEO.getId())).collect(Collectors.toList());
        ArrayList<ClbrBillEO> finalClbrBillEOs = new ArrayList<ClbrBillEO>(noNeedHandledClbrBillEOs);
        List<ClbrBillEO> needHandledclbrBillEOs = this.clbrBillDao.listByIds(billId2CheckedAmountMap.keySet());
        for (ClbrBillEO needHandledclbrBill : needHandledclbrBillEOs) {
            needHandledclbrBill.setVerifyedAmount(NumberUtils.round((double)(needHandledclbrBill.getVerifyedAmount() - Objects.requireNonNull(billId2CheckedAmountMap.get(needHandledclbrBill.getId())))));
            needHandledclbrBill.setNoverifyAmount(NumberUtils.round((double)(needHandledclbrBill.getNoverifyAmount() + Objects.requireNonNull(billId2CheckedAmountMap.get(needHandledclbrBill.getId())))));
            finalClbrBillEOs.add(needHandledclbrBill);
        }
        return finalClbrBillEOs;
    }

    @Override
    public PageInfo<ClbrBillDTO> listInitiatorNotConfirmBySsoCondition(ClbrBillSsoConditionDTO clbrBillSsoConditionDTO) {
        List<Object> currentPageClbrBillDTOs;
        List<ClbrBillDTO> clbrBillDTOs;
        try {
            clbrBillDTOs = this.listInitiatorNotConfirmByUser(clbrBillSsoConditionDTO);
        }
        finally {
            ShiroUtil.unbindUser();
        }
        int fromIndex = (clbrBillSsoConditionDTO.getPageNum() - 1) * clbrBillSsoConditionDTO.getPageSize();
        if (fromIndex < 0 || fromIndex >= clbrBillDTOs.size()) {
            currentPageClbrBillDTOs = Collections.emptyList();
        } else {
            int toIndex = clbrBillSsoConditionDTO.getPageNum() * clbrBillSsoConditionDTO.getPageSize();
            if (toIndex >= clbrBillDTOs.size()) {
                toIndex = clbrBillDTOs.size();
            }
            currentPageClbrBillDTOs = clbrBillDTOs.subList(fromIndex, toIndex);
        }
        PageInfo pageInfo = PageInfo.of(currentPageClbrBillDTOs, (int)clbrBillSsoConditionDTO.getPageNum(), (int)clbrBillSsoConditionDTO.getPageSize(), (int)clbrBillDTOs.size());
        return pageInfo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<ClbrBillDTO> filterAuthBillData(String userName, List<ClbrBillDTO> beforeFilterClbrBillDTOs) {
        List authRelationCodes;
        if (ObjectUtils.isEmpty(userName)) {
            return beforeFilterClbrBillDTOs;
        }
        this.changeUserContext(userName);
        try {
            List authRelations = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_RELATION");
            if (CollectionUtils.isEmpty(authRelations)) {
                List<ClbrBillDTO> list = Collections.emptyList();
                return list;
            }
            authRelationCodes = authRelations.stream().map(GcBaseData::getCode).collect(Collectors.toList());
        }
        finally {
            ShiroUtil.unbindUser();
        }
        List<ClbrBillDTO> afterFilterClbrBillDTOS = beforeFilterClbrBillDTOs.stream().filter(beforeFilterClbrBillDTO -> authRelationCodes.contains(beforeFilterClbrBillDTO.getOppRelation())).collect(Collectors.toList());
        return afterFilterClbrBillDTOS;
    }

    private void changeUserContext(String userName) {
        Optional sysUser;
        User user = null;
        Optional userOptional = this.userService.findByUsername(userName);
        if (userOptional.isPresent()) {
            user = (User)userOptional.get();
        }
        if ((sysUser = this.systemUserService.findByUsername(userName)).isPresent()) {
            user = (User)sysUser.get();
        }
        Assert.isTrue(!ObjectUtils.isEmpty(user), "\u534f\u540c\u7cfb\u7edf\u627e\u4e0d\u5230\u7528\u6237\u540d\u4e3a[" + userName + "]\u7684\u7528\u6237\u4fe1\u606f\u3002");
        NpContextImpl contextImpl = new NpContextImpl();
        NpContextUser contextUser = new NpContextUser();
        contextUser.setName(user.getName());
        contextUser.setId(user.getId());
        contextUser.setNickname(user.getNickname());
        contextUser.setOrgCode(user.getOrgCode());
        contextUser.setDescription(user.getDescription());
        contextUser.setType(user.getUserType());
        contextImpl.setUser((ContextUser)contextUser);
        NpContextIdentity contextIdentity = new NpContextIdentity();
        contextIdentity.setId(user.getId());
        contextIdentity.setTitle(user.getName());
        contextImpl.setIdentity((ContextIdentity)contextIdentity);
        NpContextHolder.setContext((NpContext)contextImpl);
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setId(user.getId());
        userDTO.setTenantName("__default_tenant__");
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getName());
        ShiroUtil.bindUser((UserLoginDTO)userDTO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillDTO> listInitiatorConfirmByRelation(ClbrBillGenerateQueryParamDTO queryParamDTO) {
        List roleList = this.roleService.getByIdentity(NpContextHolder.getContext().getIdentityId());
        List roleCodes = roleList.stream().map(Namable::getName).collect(Collectors.toList());
        String roleCodeStr = String.join((CharSequence)",", roleCodes);
        ClbrBillSsoConditionDTO clbrBillSsoConditionDTO = new ClbrBillSsoConditionDTO();
        boolean exist = this.clbrReceiveSettingService.exist();
        if (exist) {
            Map<String, Set<String>> receiveClbrType2Relations = this.clbrReceiveSettingService.getReceiveClbrType2Relations(NpContextHolder.getContext().getUserName(), roleCodeStr);
            if (receiveClbrType2Relations.isEmpty()) {
                return PageInfo.empty();
            }
            clbrBillSsoConditionDTO.setReceiveClbrType2Relations(receiveClbrType2Relations);
        } else {
            clbrBillSsoConditionDTO.setReceiveClbrType2Relations(new HashMap());
        }
        clbrBillSsoConditionDTO.setPageNum(queryParamDTO.getPageNum());
        clbrBillSsoConditionDTO.setPageSize(queryParamDTO.getPageSize());
        clbrBillSsoConditionDTO.setUserName(NpContextHolder.getContext().getUserName());
        clbrBillSsoConditionDTO.setRoleCode(roleCodeStr);
        clbrBillSsoConditionDTO.setConditionClbrType(queryParamDTO.getClbrType());
        clbrBillSsoConditionDTO.setConditionRelation(queryParamDTO.getRelation());
        clbrBillSsoConditionDTO.setConditionOppRelation(queryParamDTO.getOppRelation());
        clbrBillSsoConditionDTO.setUserCodes(queryParamDTO.getUserCodes());
        clbrBillSsoConditionDTO.setConfirmUserCodes(queryParamDTO.getConfirmUserCodes());
        clbrBillSsoConditionDTO.setMode(queryParamDTO.getMode());
        clbrBillSsoConditionDTO.setClbrCode(queryParamDTO.getClbrCode());
        clbrBillSsoConditionDTO.setClbrBillCode(queryParamDTO.getClbrBillCode());
        clbrBillSsoConditionDTO.setAmountMax(queryParamDTO.getAmountMax());
        clbrBillSsoConditionDTO.setAmountMin(queryParamDTO.getAmountMin());
        clbrBillSsoConditionDTO.setRemark(queryParamDTO.getRemark());
        return this.listInitiatorConfirmBySsoCondition(clbrBillSsoConditionDTO);
    }

    private PageInfo<ClbrBillDTO> listInitiatorConfirmBySsoCondition(ClbrBillSsoConditionDTO clbrBillSsoConditionDTO) {
        PageInfo<ClbrBillCheckEO> clbrBillCheckEOPageInfo = this.clbrBillCheckDao.listInitiatorConfirmByUser(clbrBillSsoConditionDTO);
        if (CollectionUtils.isEmpty(clbrBillCheckEOPageInfo.getList())) {
            return PageInfo.empty();
        }
        List<String> srcIds = clbrBillCheckEOPageInfo.getList().stream().map(ClbrBillCheckEO::getSrcId).collect(Collectors.toList());
        List<ClbrBillEO> clbrBillEOS = this.clbrBillDao.listBySrcIds(srcIds);
        ClbrTypePenetrationControlUtils.validPenetrationControl(clbrBillEOS);
        List<ClbrBillDTO> dtoPageInfo = ClbrBillConverter.convertEO2DTO(clbrBillEOS);
        return PageInfo.of(dtoPageInfo, (int)clbrBillCheckEOPageInfo.getPageNum(), (int)clbrBillCheckEOPageInfo.getPageSize(), (int)clbrBillCheckEOPageInfo.getSize());
    }

    @Override
    public PageInfo<ClbrBillDTO> listInitiatorNotConfirmByRelation(ClbrBillGenerateQueryParamDTO queryNotConfirmParamDTO) {
        List roleList = this.roleService.getByIdentity(NpContextHolder.getContext().getIdentityId());
        List roleCodes = roleList.stream().map(Namable::getName).collect(Collectors.toList());
        String roleCodeStr = String.join((CharSequence)",", roleCodes);
        ClbrBillSsoConditionDTO clbrBillSsoConditionDTO = new ClbrBillSsoConditionDTO();
        clbrBillSsoConditionDTO.setPageNum(queryNotConfirmParamDTO.getPageNum());
        clbrBillSsoConditionDTO.setPageSize(queryNotConfirmParamDTO.getPageSize());
        clbrBillSsoConditionDTO.setUserName(NpContextHolder.getContext().getUserName());
        clbrBillSsoConditionDTO.setRoleCode(roleCodeStr);
        clbrBillSsoConditionDTO.setConditionClbrType(queryNotConfirmParamDTO.getClbrType());
        clbrBillSsoConditionDTO.setConditionRelation(queryNotConfirmParamDTO.getRelation());
        clbrBillSsoConditionDTO.setConditionOppRelation(queryNotConfirmParamDTO.getOppRelation());
        clbrBillSsoConditionDTO.setUserCodes(queryNotConfirmParamDTO.getUserCodes());
        clbrBillSsoConditionDTO.setMode(queryNotConfirmParamDTO.getMode());
        clbrBillSsoConditionDTO.setClbrCode(queryNotConfirmParamDTO.getClbrCode());
        clbrBillSsoConditionDTO.setClbrBillCode(queryNotConfirmParamDTO.getClbrBillCode());
        clbrBillSsoConditionDTO.setAmountMax(queryNotConfirmParamDTO.getAmountMax());
        clbrBillSsoConditionDTO.setAmountMin(queryNotConfirmParamDTO.getAmountMin());
        clbrBillSsoConditionDTO.setRemark(queryNotConfirmParamDTO.getRemark());
        return this.listInitiatorNotConfirmBySsoCondition(clbrBillSsoConditionDTO);
    }

    @Override
    public ClbrGenerateAttributeDTO queryClbrGenerateAttribute(String sysCode) {
        ClbrGenerateAttributeDTO clbrGenerateAttributeDTO = this.adapterFactory.create(sysCode).queryClbrGenerateAttribute();
        return clbrGenerateAttributeDTO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Object generateOppClbrBill(Set<String> clbrBillIds) {
        String optValue;
        INvwaSystemOptionService service;
        List<ClbrBillEO> initiatorClbrBillEOs = this.clbrBillDao.listByIds(clbrBillIds);
        if (clbrBillIds.size() > 1 && Objects.nonNull(service = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class)) && org.springframework.util.StringUtils.hasText(optValue = service.findValueById("GCREPORT_CLBR_RIGID")) && ClbrRigidOptionsEnum.YES.getCode().toString().equals(optValue) && this.checkRigidFilter(initiatorClbrBillEOs)) {
            throw new BusinessRuntimeException("\u5b58\u5728\u6d41\u7a0b\u521a\u6027\u7684\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u540c\u65f6\u9009\u62e9\u591a\u6761\u6570\u636e\u534f\u540c\uff01");
        }
        List gcBaseDataList = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_SYSMAPPING");
        if (CollectionUtils.isEmpty(gcBaseDataList)) {
            throw new BusinessRuntimeException("\u672a\u914d\u7f6e\u7cfb\u7edf\u6620\u5c04\u914d\u7f6e\u3002");
        }
        HashSet<String> sysCodes = new HashSet<String>();
        for (ClbrBillEO initiatorClbrBillEO : initiatorClbrBillEOs) {
            String clbrType = initiatorClbrBillEO.getClbrType();
            String oppRelation = initiatorClbrBillEO.getOppRelation();
            boolean existSys = false;
            for (GcBaseData gcBaseData : gcBaseDataList) {
                Object clbrtype = gcBaseData.getFieldVal("CLBRTYPE");
                Object relation = gcBaseData.getFieldVal("RELATION");
                if (Objects.isNull(relation) || Objects.isNull(clbrtype)) continue;
                HashSet clbrTypes = new HashSet((ArrayList)clbrtype);
                ArrayList relations = (ArrayList)relation;
                List<String> allRelation = RelationUtils.queryAllItems(relations);
                if (!clbrTypes.contains(clbrType) || !allRelation.contains(oppRelation)) continue;
                sysCodes.add(gcBaseData.getFieldVal("CLBRSYSTEM").toString());
                existSys = true;
            }
            if (existSys) continue;
            throw new BusinessRuntimeException("\u63a5\u6536\u65b9\uff1a" + oppRelation + " \u4e1a\u52a1\u7c7b\u578b\uff1a" + clbrType + "\u7684\u534f\u540c\u5355\u672a\u627e\u5230\u5355\u70b9\u7684\u7cfb\u7edf\u914d\u7f6e\u3002");
        }
        if (sysCodes.size() > 1) {
            throw new BusinessRuntimeException("\u8bf7\u52fe\u9009\u540c\u4e00\u63a5\u6536\u65b9\u7cfb\u7edf\u5355\u636e\u8fdb\u884c\u534f\u540c\uff01");
        }
        return this.generateOppClbrBillBySysCode((String)new ArrayList(sysCodes).get(0), initiatorClbrBillEOs);
    }

    private Object generateOppClbrBillBySysCode(String sysCode, List<ClbrBillEO> initiatorClbrBillEOs) {
        List<ClbrBillDTO> initiatorClbrBillDTOs = ClbrBillConverter.convertEO2DTO(initiatorClbrBillEOs);
        Map relationGroupingMap = initiatorClbrBillDTOs.stream().collect(Collectors.groupingBy(ClbrBillDTO::getRelation, Collectors.mapping(Function.identity(), Collectors.toList())));
        relationGroupingMap.forEach((relation, bills) -> {
            Set clbrCodes = bills.stream().map(ClbrBillDTO::getClbrCode).filter(Objects::nonNull).collect(Collectors.toSet());
            if (clbrCodes.size() > 1) {
                throw new BusinessRuntimeException("\u4e0d\u5141\u8bb8\u534f\u540c\uff0c\u76f8\u540c\u53d1\u8d77\u65b9\u5355\u4f4d\u7684\u53d1\u8d77\u5355\u5b58\u5728\u4e0d\u540c\u534f\u540c\u7801\u3002");
            }
        });
        Object result = this.adapterFactory.create(sysCode).generateOppClbrBill(initiatorClbrBillDTOs);
        return result;
    }

    private void executeCancelConfirm(ClbrBillConfirmTypeEnum clbrBillConfirmTypeEnum, List<ClbrBillCheckEO> clbrBillCheckEOS) {
        List bothConfirmBillList = clbrBillCheckEOS.stream().filter(clbrAmountCheckEO -> ClbrConfirmStatusEnum.BOTH_CONFIRMED.getCode().equals(clbrAmountCheckEO.getConfirmStatus()) && clbrAmountCheckEO.getClbrBillType().equals(1)).collect(Collectors.toList());
        HashMap sysCheckResult = new HashMap();
        if (!ClbrBillConfirmTypeEnum.AUTO.equals((Object)clbrBillConfirmTypeEnum)) {
            Map sysCode2ClbrBillEOsMap = bothConfirmBillList.stream().collect(Collectors.groupingBy(ClbrBillCheckEO::getSysCode, Collectors.mapping(Function.identity(), Collectors.toList())));
            sysCode2ClbrBillEOsMap.forEach((sysCode, clbrBillCheckEOList) -> {
                Set<String> collect = clbrBillCheckEOList.stream().map(ClbrBillCheckEO::getBillId).collect(Collectors.toSet());
                List<ClbrBillEO> clbrBillEOList = this.clbrBillDao.listByIds(collect);
                List<ClbrBillDTO> clbrBillDTOS = ClbrBillConverter.convertEO2DTO(clbrBillEOList);
                List<Map<String, Object>> checkResult = this.adapterFactory.create((String)sysCode).allowCancelClbrBill(clbrBillDTOS);
                for (Map<String, Object> stringObjectMap : checkResult) {
                    sysCheckResult.put(stringObjectMap.get("clbrBillCode").toString(), Integer.parseInt(stringObjectMap.get("cancelType").toString()));
                    sysCheckResult.put(stringObjectMap.get("oppClbrBillCode").toString(), Integer.parseInt(stringObjectMap.get("cancelType").toString()));
                }
            });
        }
        Map<String, Double> bothConfirmBillId2ClbrAmountMap = clbrBillCheckEOS.stream().filter(clbrAmountCheckEO -> ClbrConfirmStatusEnum.BOTH_CONFIRMED.getCode().equals(clbrAmountCheckEO.getConfirmStatus()) && clbrAmountCheckEO.getClbrBillType().equals(1)).collect(Collectors.groupingBy(ClbrBillCheckEO::getBillId, Collectors.summingDouble(ClbrBillCheckEO::getClbrAmount)));
        Date now = new Date();
        ArrayList deleteBillEOs = new ArrayList();
        ArrayList updateBillEOs = new ArrayList();
        Set deleteCheckEOGroupIds = clbrBillCheckEOS.stream().map(ClbrBillCheckEO::getGroupId).collect(Collectors.toSet());
        ArrayList clbrBillCancelMsgDTOs = new ArrayList();
        ArrayList<ClbrVoucherItemVO> clbrVoucherItemVOS = new ArrayList<ClbrVoucherItemVO>();
        Map<String, Double> billId2ClbrAmountMap = clbrBillCheckEOS.stream().collect(Collectors.groupingBy(ClbrBillCheckEO::getBillId, Collectors.summingDouble(ClbrBillCheckEO::getClbrAmount)));
        List<ClbrBillEO> clbrBillEOList = this.clbrBillDao.listByIds(billId2ClbrAmountMap.keySet());
        clbrBillEOList.forEach(clbrBillEO -> {
            ClbrBillEO paramForUpdateEO = new ClbrBillEO();
            BeanUtils.copyProperties(clbrBillEO, (Object)paramForUpdateEO);
            paramForUpdateEO.setVerifyedAmount(paramForUpdateEO.getVerifyedAmount() - (Double)billId2ClbrAmountMap.get(clbrBillEO.getId()));
            paramForUpdateEO.setNoverifyAmount(paramForUpdateEO.getAmount() - paramForUpdateEO.getVerifyedAmount());
            paramForUpdateEO.setBillState(paramForUpdateEO.getVerifyedAmount() == 0.0 ? ClbrBillStateEnum.INIT.getCode() : ClbrBillStateEnum.PARTCONFIRM.getCode());
            paramForUpdateEO.setModifyTime(now);
            paramForUpdateEO.setClbrTime(paramForUpdateEO.getVerifyedAmount() == 0.0 ? null : paramForUpdateEO.getClbrTime());
            paramForUpdateEO.setClbrCode(paramForUpdateEO.getVerifyedAmount() == 0.0 ? null : paramForUpdateEO.getClbrCode());
            paramForUpdateEO.setConfirmType(null == paramForUpdateEO.getClbrCode() ? null : paramForUpdateEO.getConfirmType());
            Integer clbrBillType = paramForUpdateEO.getClbrBillType();
            Integer checkType = (Integer)sysCheckResult.get(clbrBillEO.getClbrBillCode());
            if (ClbrBillConfirmTypeEnum.AUTO.getCode().equals(clbrBillEO.getConfirmType()) && paramForUpdateEO.getVerifyedAmount() == 0.0 && ClbrBillConfirmTypeEnum.MANUAL.equals((Object)clbrBillConfirmTypeEnum) && (ClbrBillTypeEnum.RECEIVER.getCode().equals(clbrBillType) || !new Integer(2).equals(checkType))) {
                deleteBillEOs.add(paramForUpdateEO);
            } else {
                updateBillEOs.add(paramForUpdateEO);
            }
            if (paramForUpdateEO.getVerifyedAmount() == 0.0) {
                ClbrVoucherItemVO clbrVoucherItemVO = new ClbrVoucherItemVO();
                clbrVoucherItemVO.setBillCode(clbrBillEO.getClbrBillCode());
                clbrVoucherItemVO.setGcNumber(clbrBillEO.getClbrCode());
                clbrVoucherItemVO.setAmt(clbrBillEO.getAmount());
                clbrVoucherItemVO.setOppUnitId(clbrBillEO.getThatRelation());
                clbrVoucherItemVOS.add(clbrVoucherItemVO);
            }
            if (bothConfirmBillId2ClbrAmountMap.containsKey(clbrBillEO.getId())) {
                ClbrBillCancelMsgDTO clbrBillCancelMsgDTO = new ClbrBillCancelMsgDTO();
                clbrBillCancelMsgDTO.setClbrCode(clbrBillEO.getClbrCode());
                clbrBillCancelMsgDTO.setSrcId(clbrBillEO.getSrcId());
                clbrBillCancelMsgDTO.setOppSrcId(clbrBillEO.getOppSrcId());
                clbrBillCancelMsgDTO.setClbrBillCode(clbrBillEO.getClbrBillCode());
                clbrBillCancelMsgDTO.setOppClbrBillCode(clbrBillEO.getOppClbrBillCode());
                clbrBillCancelMsgDTO.setSysCode(clbrBillEO.getSysCode());
                clbrBillCancelMsgDTO.setCurrentAmount((Double)bothConfirmBillId2ClbrAmountMap.get(clbrBillEO.getId()));
                clbrBillCancelMsgDTO.setClbrTime(clbrBillEO.getClbrTime());
                clbrBillCancelMsgDTO.setConfirmType(clbrBillEO.getConfirmType());
                clbrBillCancelMsgDTO.setCancelType((Integer)sysCheckResult.get(clbrBillEO.getClbrBillCode()));
                clbrBillCancelMsgDTOs.add(clbrBillCancelMsgDTO);
            }
        });
        if (CollectionUtils.isEmpty(clbrBillCheckEOS)) {
            return;
        }
        if (!CollectionUtils.isEmpty(deleteCheckEOGroupIds)) {
            this.clbrBillCheckDao.deleteByGroupIds(new ArrayList<String>(deleteCheckEOGroupIds));
        }
        this.clbrBillDao.updateBatch(updateBillEOs);
        if (!CollectionUtils.isEmpty(deleteBillEOs)) {
            List clbrBillDeleteEOS = deleteBillEOs.stream().map(clbrBillEO -> {
                clbrBillEO.setBillState(ClbrBillStateEnum.CANCEL.getCode());
                return ClbrBillConverter.convertBillEO2DeleteEO(clbrBillEO);
            }).collect(Collectors.toList());
            this.clbrBillDeleteDao.addBatch(clbrBillDeleteEOS);
            this.clbrBillDao.deleteBatch(deleteBillEOs);
        }
        if (!CollectionUtils.isEmpty(clbrVoucherItemVOS) && org.springframework.util.StringUtils.hasText(this.url)) {
            this.financialcheckDelClbrCode(clbrVoucherItemVOS);
        }
        if (ClbrBillConfirmTypeEnum.AUTO.equals((Object)clbrBillConfirmTypeEnum)) {
            return;
        }
        Map sysCode2CancelMsgDTOs = clbrBillCancelMsgDTOs.stream().collect(Collectors.groupingBy(ClbrBillCancelMsgDTO::getSysCode, Collectors.mapping(Function.identity(), Collectors.toList())));
        ClbrBilCancelMsgContext clbrBilCancelMsgContext = new ClbrBilCancelMsgContext();
        clbrBilCancelMsgContext.setClbrBillConfirmTypeEnum(clbrBillConfirmTypeEnum);
        sysCode2CancelMsgDTOs.forEach((sysCode, cancelMsgDTOs) -> this.adapterFactory.create((String)sysCode).sendClbrBillCancelMessage(clbrBilCancelMsgContext, (List<ClbrBillCancelMsgDTO>)cancelMsgDTOs));
    }

    private void financialcheckDelClbrCode(List<ClbrVoucherItemVO> clbrVoucherItemVOS) {
        try {
            BusinessResponseEntity responseEntity = this.clbrCheckClient.updateVoucherGcNumber(clbrVoucherItemVOS);
            if (!responseEntity.isSuccess()) {
                throw new BusinessRuntimeException(responseEntity.getErrorMessage());
            }
        }
        catch (Exception e) {
            LOGGER.info("\u5220\u9664\u5173\u8054\u4ea4\u6613\u534f\u540c\u7801\u5931\u8d25\u3002\u53c2\u6570={}", (Object)clbrVoucherItemVOS);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void rejectClbrBill(ClbrBillRejectDTO clbrBillRejectDTO) {
        Set clbrBillIds = clbrBillRejectDTO.getClbrBillIds();
        String rejectReason = clbrBillRejectDTO.getRejectReason();
        if (StringUtils.isBlank((CharSequence)rejectReason)) {
            throw new BusinessRuntimeException("\u9a73\u56de\u8bf4\u660e\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        List<ClbrBillEO> clbrBillEOs = this.clbrBillDao.listByIds(clbrBillIds);
        clbrBillEOs.stream().forEach(clbrBillEO -> {
            String clbrSchemeId = clbrBillEO.getClbrSchemeId();
            if (ObjectUtils.isEmpty(clbrSchemeId)) {
                return;
            }
            ClbrSchemeEO schemeEO = new ClbrSchemeEO();
            schemeEO.setId(clbrSchemeId);
            ClbrSchemeEO clbrSchemeEO = (ClbrSchemeEO)this.clbrSchemeDao.selectByEntity((BaseEntity)schemeEO);
            if (clbrSchemeEO == null) {
                return;
            }
            ClbrFlowControlEnum clbrFlowControlEnum = ClbrFlowControlEnum.getEnumByCode((String)clbrSchemeEO.getFlowControlType());
            if (ClbrFlowControlEnum.WEAK.equals((Object)clbrFlowControlEnum)) {
                throw new BusinessRuntimeException("\u5355\u636e\u7f16\u53f7\u4e3a\u3010" + clbrBillEO.getClbrBillCode() + "\u3011\u7684\u534f\u540c\u65b9\u6848\u3010" + clbrSchemeEO.getTitle() + "\u3011\u6d41\u7a0b\u63a7\u5236\u4e3a\u67d4\u6027\uff0c\u4e0d\u5141\u8bb8\u534f\u540c\u9a73\u56de\uff0c\u8bf7\u4fee\u6539\u8be5\u6761\u65b9\u6848\u7684\u6d41\u7a0b\u63a7\u5236\u4e3a\u521a\u6027\u540e\u9a73\u56de");
            }
            if (clbrBillEO.getVerifyedAmount() > 0.0 || ClbrBillStateEnum.PARTCONFIRM.getCode().equals(clbrBillEO.getBillState())) {
                throw new BusinessRuntimeException("\u5355\u636e\u7f16\u53f7\u4e3a\u3010" + clbrBillEO.getClbrBillCode() + "\u3011\u7684\u5355\u636e\u8fdb\u884c\u8fc7\u534f\u540c\uff0c\u4e0d\u5141\u8bb8\u534f\u540c\u9a73\u56de");
            }
        });
        this.executeRejectClbrBill(clbrBillEOs, rejectReason);
    }

    private void executeRejectClbrBill(List<ClbrBillEO> clbrBillEOs, String rejectReason) {
        ContextUser user = NpContextHolder.getContext().getUser();
        clbrBillEOs.stream().forEach(sysClbrBillEO -> {
            sysClbrBillEO.setBillState(ClbrBillStateEnum.REJECT.getCode());
            sysClbrBillEO.setModifyTime(new Date());
            sysClbrBillEO.setRejectUserName(user.getName());
            sysClbrBillEO.setRejectTime(new Date());
        });
        List<ClbrBillDeleteEO> clbrBillDeleteEOs = ClbrBillConverter.convertBillEO2DeleteEO(clbrBillEOs);
        this.clbrBillDeleteDao.addBatch(clbrBillDeleteEOs);
        this.clbrBillDao.deleteBatch(clbrBillEOs);
        List clbrBillRejectMsgDTOs = clbrBillEOs.stream().map(sysClbrBillEO -> {
            ClbrBillRejectMsgDTO clbrBillRejectMsgDTO = ClbrBillRejectMsgDTO.builder().sn(sysClbrBillEO.getSn()).clbrBillCode(sysClbrBillEO.getClbrBillCode()).oppClbrBillCode(sysClbrBillEO.getOppClbrBillCode()).clbrCode(sysClbrBillEO.getClbrCode()).clbrTime(sysClbrBillEO.getClbrTime()).amount(sysClbrBillEO.getAmount()).noverifyAmount(sysClbrBillEO.getNoverifyAmount()).verifyedAmount(sysClbrBillEO.getVerifyedAmount()).srcId(sysClbrBillEO.getSrcId()).sysCode(sysClbrBillEO.getSysCode());
            return clbrBillRejectMsgDTO;
        }).collect(Collectors.toList());
        Map sysCode2RejectMsgDTOsMap = clbrBillRejectMsgDTOs.stream().collect(Collectors.groupingBy(ClbrBillRejectMsgDTO::getSysCode, Collectors.mapping(Function.identity(), Collectors.toList())));
        sysCode2RejectMsgDTOsMap.forEach((sysCode, rejectMsgDTOs) -> this.adapterFactory.create((String)sysCode).sendClbrBillRejectMessage((List<ClbrBillRejectMsgDTO>)rejectMsgDTOs, rejectReason));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cancelClbrBill(Set<String> clbrBillIds) {
        List<ClbrBillEO> clbrBillEOs = this.clbrBillDao.listByIds(clbrBillIds);
        clbrBillEOs.forEach(clbrBillEO -> {
            String clbrSchemeId = clbrBillEO.getClbrSchemeId();
            if (ObjectUtils.isEmpty(clbrSchemeId)) {
                return;
            }
            ClbrSchemeEO schemeEO = new ClbrSchemeEO();
            schemeEO.setId(clbrSchemeId);
            ClbrSchemeEO clbrSchemeEO = (ClbrSchemeEO)this.clbrSchemeDao.selectByEntity((BaseEntity)schemeEO);
            if (clbrSchemeEO == null) {
                return;
            }
            ClbrFlowControlEnum clbrFlowControlEnum = ClbrFlowControlEnum.getEnumByCode((String)clbrSchemeEO.getFlowControlType());
            if (ClbrFlowControlEnum.WEAK.equals((Object)clbrFlowControlEnum) && ClbrBillConfirmTypeEnum.AUTO.getCode().equals(clbrBillEO.getConfirmType())) {
                throw new BusinessRuntimeException("\u4e1a\u52a1\u5355[\u7f16\u7801\uff1a" + clbrBillEO.getClbrBillCode() + "]\u6d41\u7a0b\u63a7\u5236\u4e3a" + clbrFlowControlEnum.getTitle() + "\uff0c\u4e0d\u5141\u8bb8\u53d6\u6d88");
            }
        });
        this.cancelConfirm(ClbrBillConfirmTypeEnum.MANUAL, clbrBillEOs);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cancelSingleClbrBill(Set<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            throw new BusinessRuntimeException("\u6240\u9009\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a");
        }
        List<ClbrBillCheckEO> amountCheckEOS = this.clbrBillCheckDao.listByGroupIds(new ArrayList<String>(groupIds));
        Map<String, Double> bothConfirmBillId2ClbrAmountMap = amountCheckEOS.stream().filter(clbrAmountCheckEO -> ClbrConfirmStatusEnum.BOTH_CONFIRMED.getCode().equals(clbrAmountCheckEO.getConfirmStatus())).collect(Collectors.groupingBy(ClbrBillCheckEO::getBillId, Collectors.summingDouble(ClbrBillCheckEO::getClbrAmount)));
        this.executeCancelConfirm(ClbrBillConfirmTypeEnum.MANUAL, amountCheckEOS);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void manualConfirmByGrouping(Set<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            throw new BusinessRuntimeException("\u6240\u9009\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a");
        }
        this.clbrBillCheckDao.updateConfirmStatusByGroupIds(new ArrayList<String>(groupIds), NpContextHolder.getContext().getUserName());
        List<ClbrBillCheckEO> amountCheckEOS = this.clbrBillCheckDao.listByGroupIds(new ArrayList<String>(groupIds));
        Set<String> billIds = amountCheckEOS.stream().map(ClbrBillCheckEO::getBillId).collect(Collectors.toSet());
        List<ClbrBillEO> clbrBillEOList = this.clbrBillDao.listByIds(billIds);
        Map<String, ClbrBillEO> id2ClbrBillInfoMap = clbrBillEOList.stream().collect(Collectors.toMap(DefaultTableEntity::getId, Function.identity()));
        List<ClbrBillConfirmMsgDTO> clbrBillConfirmMsgDTOS = this.buildOne2OneClbBillConfirmMsgDTOs(amountCheckEOS, id2ClbrBillInfoMap);
        Map sysCode2ClbrBillConfirmMsgDTOsMap = clbrBillConfirmMsgDTOS.stream().collect(Collectors.groupingBy(ClbrBillConfirmMsgDTO::getSysCode, Collectors.mapping(Function.identity(), Collectors.toList())));
        ClbrBillConfirmMsgContext context = new ClbrBillConfirmMsgContext();
        context.setClbrBillConfirmTypeEnum(ClbrBillConfirmTypeEnum.MANUAL);
        sysCode2ClbrBillConfirmMsgDTOsMap.forEach((sysCode, billConfirmMsgDTOS) -> this.adapterFactory.create((String)sysCode).sendClbrBillConfirmMessage(context, (List<ClbrBillConfirmMsgDTO>)billConfirmMsgDTOS, ClbrOperateTypeEnum.CONFIRM.getCode()));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void manualConfirm(ClbrBillManualConfirmParamDTO manualConfirmParamDTO) {
        Set ids = manualConfirmParamDTO.getIds();
        Set oppIds = manualConfirmParamDTO.getOppIds();
        if (CollectionUtils.isEmpty(oppIds) || CollectionUtils.isEmpty(ids)) {
            return;
        }
        List<ClbrBillEO> initiatorClbrBillEOs = this.clbrBillDao.listByIds(ids);
        if (CollectionUtils.isEmpty(initiatorClbrBillEOs)) {
            throw new BusinessRuntimeException("\u53d1\u8d77\u65b9\u6570\u636e\u5931\u6548\uff0c\u8bf7\u5237\u65b0");
        }
        List<ClbrBillEO> receiverClbrBillEOs = this.clbrBillDao.listByIds(oppIds);
        if (CollectionUtils.isEmpty(receiverClbrBillEOs)) {
            throw new BusinessRuntimeException("\u63a5\u6536\u65b9\u6570\u636e\u5931\u6548\uff0c\u8bf7\u5237\u65b0");
        }
        if (initiatorClbrBillEOs.size() == 1 && receiverClbrBillEOs.size() == 1) {
            ClbrBillEO initiatorBill = initiatorClbrBillEOs.get(0);
            ClbrBillEO receiverBill = receiverClbrBillEOs.get(0);
            if (!initiatorBill.getRelation().equals(receiverBill.getOppRelation()) || !receiverBill.getRelation().equals(initiatorBill.getOppRelation())) {
                throw new BusinessRuntimeException("\u4e0d\u6ee1\u8db3\u4e92\u4e3a\u672c\u5bf9\u65b9\u5355\u4f4d\u6761\u4ef6\uff0c\u4e0d\u5141\u8bb8\u534f\u540c");
            }
        }
        ArrayList<ClbrBillEO> allClbrBillEOs = new ArrayList<ClbrBillEO>();
        allClbrBillEOs.addAll(initiatorClbrBillEOs);
        allClbrBillEOs.addAll(receiverClbrBillEOs);
        Set relationSet = allClbrBillEOs.stream().map(ClbrBillEO::getRelation).collect(Collectors.toSet());
        Set oppRelationSet = allClbrBillEOs.stream().map(ClbrBillEO::getOppRelation).collect(Collectors.toSet());
        HashSet selectRelations = new HashSet();
        selectRelations.addAll(oppRelationSet);
        selectRelations.addAll(relationSet);
        if (selectRelations.size() > 2) {
            throw new BusinessRuntimeException("\u5173\u8054\u65b9\u8d85\u8fc7\u4e24\u4e2a\uff0c\u4e0d\u5141\u8bb8\u534f\u540c");
        }
        Set initiatorClbrCodes = initiatorClbrBillEOs.stream().map(ClbrBillEO::getClbrCode).filter(Objects::nonNull).collect(Collectors.toSet());
        Set receiverClbrCodes = receiverClbrBillEOs.stream().map(ClbrBillEO::getClbrCode).filter(Objects::nonNull).collect(Collectors.toSet());
        HashSet selectClbrCodes = new HashSet();
        selectClbrCodes.addAll(initiatorClbrCodes);
        selectClbrCodes.addAll(receiverClbrCodes);
        if (selectClbrCodes.size() > 1) {
            throw new BusinessRuntimeException("\u5b58\u5728\u534f\u540c\u7801\u4e0d\u540c\u7684\u8bb0\u5f55\uff0c\u4e0d\u5141\u8bb8\u534f\u540c\uff0c\u8bf7\u9009\u62e9\u76f8\u540c\u534f\u540c\u7801\u6216\u8005\u65e0\u534f\u540c\u7801\u7684\u8bb0\u5f55");
        }
        this.confirm(ClbrBillConfirmTypeEnum.MANUAL, initiatorClbrBillEOs, receiverClbrBillEOs, true, CollectionUtils.isEmpty(selectClbrCodes) ? null : (String)new ArrayList(selectClbrCodes).get(0), null);
    }

    @Override
    public String showBill(String clbrBillId) {
        ClbrBillEO param = new ClbrBillEO();
        param.setId(clbrBillId);
        ClbrBillEO clbrBillEO = (ClbrBillEO)this.clbrBillDao.selectByEntity((BaseEntity)param);
        if (Objects.isNull((Object)clbrBillEO)) {
            throw new BusinessRuntimeException("\u5355\u636e\u4e0d\u5b58\u5728\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        ClbrBillDTO clbrBillDTO = new ClbrBillDTO();
        BeanUtils.copyProperties((Object)clbrBillEO, clbrBillDTO);
        return this.adapterFactory.create(clbrBillEO.getSysCode()).getBillSsoUrl(clbrBillDTO, clbrBillEO.getUserName());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void rejectArbitrationClbrBill(Set<String> clbrBillIds) {
        List<ClbrBillEO> clbrBillEOs = this.clbrBillDao.listByIds(clbrBillIds);
        clbrBillEOs.stream().forEach(clbrBillEO -> {
            String clbrSchemeId = clbrBillEO.getClbrSchemeId();
            if (ObjectUtils.isEmpty(clbrSchemeId)) {
                return;
            }
            ClbrSchemeEO schemeEO = new ClbrSchemeEO();
            schemeEO.setId(clbrSchemeId);
            ClbrSchemeEO clbrSchemeEO = (ClbrSchemeEO)this.clbrSchemeDao.selectByEntity((BaseEntity)schemeEO);
            if (clbrSchemeEO == null) {
                return;
            }
            ClbrFlowControlEnum clbrFlowControlEnum = ClbrFlowControlEnum.getEnumByCode((String)clbrSchemeEO.getFlowControlType());
            if (ClbrFlowControlEnum.WEAK.equals((Object)clbrFlowControlEnum)) {
                throw new BusinessRuntimeException("\u5355\u636e\u7f16\u53f7\u4e3a\u3010" + clbrBillEO.getClbrBillCode() + "\u3011\u7684\u534f\u540c\u65b9\u6848\u3010" + clbrSchemeEO.getTitle() + "\u3011\u6d41\u7a0b\u63a7\u5236\u4e3a\u67d4\u6027\uff0c\u4e0d\u5141\u8bb8\u534f\u540c\u9a73\u56de\uff0c\u8bf7\u4fee\u6539\u8be5\u6761\u65b9\u6848\u7684\u6d41\u7a0b\u63a7\u5236\u4e3a\u521a\u6027\u540e\u9a73\u56de");
            }
            if (clbrBillEO.getVerifyedAmount() > 0.0 || ClbrBillStateEnum.PARTCONFIRM.getCode().equals(clbrBillEO.getBillState())) {
                throw new BusinessRuntimeException("\u5355\u636e\u7f16\u53f7\u4e3a\u3010" + clbrBillEO.getClbrBillCode() + "\u3011\u7684\u5355\u636e\u8fdb\u884c\u8fc7\u534f\u540c\uff0c\u4e0d\u5141\u8bb8\u534f\u540c\u9a73\u56de");
            }
        });
        for (ClbrBillEO clbrBillEO2 : clbrBillEOs) {
            ArrayList<ClbrBillEO> clbrBillEOsList = new ArrayList<ClbrBillEO>();
            clbrBillEOsList.add(clbrBillEO2);
            this.executeRejectClbrBill(clbrBillEOsList, clbrBillEO2.getRejectionMessage());
        }
    }

    @Override
    public List<ClbrBillEO> queryReceBillCodeState(Set<String> clbrBillIds) {
        List<ClbrBillEO> clbrBillEOS = this.clbrBillDao.selectUnClbrReceBillCode(clbrBillIds);
        if (!CollectionUtils.isEmpty(clbrBillEOS)) {
            return clbrBillEOS;
        }
        return null;
    }

    private boolean checkRigidFilter(List<ClbrBillEO> clbrBillEOS) {
        Set schemeIds = clbrBillEOS.stream().map(ClbrBillEO::getClbrSchemeId).collect(Collectors.toSet());
        List<ClbrSchemeEO> clbrSchemeEOS = this.clbrSchemeDao.selectByIds(new ArrayList<String>(schemeIds));
        for (ClbrSchemeEO clbrSchemeEO : clbrSchemeEOS) {
            if (!ClbrFlowControlEnum.RIGID.getCode().equals(clbrSchemeEO.getFlowControlType())) continue;
            return true;
        }
        return false;
    }
}


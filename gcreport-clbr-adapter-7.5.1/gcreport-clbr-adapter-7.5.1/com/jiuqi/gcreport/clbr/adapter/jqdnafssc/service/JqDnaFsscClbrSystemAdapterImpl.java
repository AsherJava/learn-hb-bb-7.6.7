/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.RestTemplateUtils
 *  com.jiuqi.gcreport.clbr.adapter.AbstractClbrSystemAdapterImpl
 *  com.jiuqi.gcreport.clbr.adapter.context.ClbrBilCancelMsgContext
 *  com.jiuqi.gcreport.clbr.adapter.context.ClbrBillConfirmMsgContext
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillConfirmMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillRejectMsgDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO$ClbrGenerateAttribute
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillConfirmTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrOperateTypeEnum
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.apache.commons.lang3.ArrayUtils
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.util.UriComponentsBuilder
 */
package com.jiuqi.gcreport.clbr.adapter.jqdnafssc.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.RestTemplateUtils;
import com.jiuqi.gcreport.clbr.adapter.AbstractClbrSystemAdapterImpl;
import com.jiuqi.gcreport.clbr.adapter.context.ClbrBilCancelMsgContext;
import com.jiuqi.gcreport.clbr.adapter.context.ClbrBillConfirmMsgContext;
import com.jiuqi.gcreport.clbr.adapter.jqdnafssc.dto.ClbrItemDTO;
import com.jiuqi.gcreport.clbr.adapter.jqdnafssc.dto.ClbrOperateDTO;
import com.jiuqi.gcreport.clbr.adapter.jqdnafssc.dto.ClbrPushDnaDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillConfirmMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillConfirmTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrOperateTypeEnum;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class JqDnaFsscClbrSystemAdapterImpl
extends AbstractClbrSystemAdapterImpl {
    private static Logger log = LoggerFactory.getLogger(JqDnaFsscClbrSystemAdapterImpl.class);
    public static final String SYSCODE = "JQ_DNA_FSSC";
    private static final String PATH_REJECT = "nvwagc/bill/clbrConfirmAndReject";
    private static final String PATH_PUSH = "cwag/sso/login";
    private static final String PATH_CONFIRM_CHECK_CANCEL = "/nvwagc/bill/clbrConfirm";
    private final RestTemplate restTemplate = new RestTemplateBuilder(new RestTemplateCustomizer[0]).build();
    @Value(value="${http.dnafssc.url:#{null}}")
    private String url;

    public String getSysCode() {
        return SYSCODE;
    }

    public ClbrGenerateAttributeDTO queryClbrGenerateAttribute() {
        ClbrGenerateAttributeDTO clbrGenerateAttributeDTO = new ClbrGenerateAttributeDTO();
        ArrayList<ClbrGenerateAttributeDTO.ClbrGenerateAttribute> clbrGenerateAttributes = new ArrayList<ClbrGenerateAttributeDTO.ClbrGenerateAttribute>();
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("sysCode", "\u6765\u6e90\u7cfb\u7edf\u6807\u8bc6"));
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("relation", "\u53d1\u8d77\u65b9"));
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("clbrType", "\u534f\u540c\u4e1a\u52a1\u7c7b\u578b"));
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("clbrBillCode", "\u534f\u540c\u5355\u636e\u7f16\u53f7"));
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("amount", "\u5df2\u534f\u540c\u91d1\u989d"));
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("srcId", "\u6765\u6e90\u6570\u636eID"));
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("oppRelation", "\u63a5\u6536\u65b9"));
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("clbrCode", "\u534f\u540c\u7801"));
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("verifyedAmount", "\u5df2\u534f\u540c\u91d1\u989d"));
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("orgCode", "\u672c\u65b9\u7ec4\u7ec7\u673a\u6784"));
        clbrGenerateAttributes.add(new ClbrGenerateAttributeDTO.ClbrGenerateAttribute("oppOrgCode", "\u5bf9\u65b9\u7ec4\u7ec7\u673a\u6784"));
        clbrGenerateAttributeDTO.setItems(clbrGenerateAttributes);
        return clbrGenerateAttributeDTO;
    }

    public boolean sendClbrBillRejectMessage(List<ClbrBillRejectMsgDTO> clbrBillRejectMsgDTOs, String rejectReason) {
        Map clbrCode2RejectMsgDTOsMap = clbrBillRejectMsgDTOs.stream().filter(clbrBillRejectMsgDTO -> {
            if (Objects.isNull(clbrBillRejectMsgDTO.getClbrCode())) {
                clbrBillRejectMsgDTO.setClbrCode("");
            }
            return true;
        }).collect(Collectors.groupingBy(ClbrBillRejectMsgDTO::getClbrCode, Collectors.mapping(Function.identity(), Collectors.toList())));
        clbrCode2RejectMsgDTOsMap.forEach((clbrCode, rejectMsgDTOs) -> {
            ClbrOperateDTO clbrOperateDO = new ClbrOperateDTO();
            List<ClbrItemDTO> clbrItemDOS = rejectMsgDTOs.stream().map(rejectMsgDTO -> {
                ClbrItemDTO clbrItemDO = new ClbrItemDTO();
                clbrItemDO.setClbrBillCode(rejectMsgDTO.getClbrBillCode());
                clbrItemDO.setSrcId(rejectMsgDTO.getSrcId());
                clbrItemDO.setSn(rejectMsgDTO.getSn());
                clbrItemDO.setClbrCode(rejectMsgDTO.getClbrCode());
                return clbrItemDO;
            }).collect(Collectors.toList());
            clbrOperateDO.setClbrState("reject");
            clbrOperateDO.setClbrItems(clbrItemDOS);
            clbrOperateDO.setClbrCode((String)clbrCode);
            clbrOperateDO.setClbrReason(rejectReason);
            clbrOperateDO.setOperateUser(NpContextHolder.getContext().getUserName());
            this.sendRejectMessage(clbrOperateDO);
        });
        return true;
    }

    public boolean sendClbrBillConfirmMessage(ClbrBillConfirmMsgContext context, List<ClbrBillConfirmMsgDTO> clbrBillConfirmMsgDTOs, String clbrOperateType) {
        if (ClbrBillConfirmTypeEnum.AUTO.equals((Object)context.getClbrBillConfirmTypeEnum())) {
            return true;
        }
        Map clbrCode2ConfirmMsgDTOsMap = clbrBillConfirmMsgDTOs.stream().collect(Collectors.groupingBy(ClbrBillConfirmMsgDTO::getClbrCode, Collectors.mapping(Function.identity(), Collectors.toList())));
        clbrCode2ConfirmMsgDTOsMap.forEach((clbrCode, confirmMsgDTOs) -> {
            ClbrOperateDTO clbrOperateDO = new ClbrOperateDTO();
            List<ClbrItemDTO> clbrItemDOS = confirmMsgDTOs.stream().map(confirmMsgDTO -> {
                ClbrItemDTO clbrItemDO = new ClbrItemDTO();
                clbrItemDO.setClbrBillCode(confirmMsgDTO.getClbrBillCode());
                clbrItemDO.setSrcId(confirmMsgDTO.getSrcId());
                clbrItemDO.setSn(confirmMsgDTO.getSn());
                clbrItemDO.setClbrCode(confirmMsgDTO.getClbrCode());
                clbrItemDO.setOppClbrBillCode(confirmMsgDTO.getOppSrcId());
                return clbrItemDO;
            }).collect(Collectors.toList());
            clbrOperateDO.setClbrState(clbrOperateType);
            clbrOperateDO.setClbrItems(clbrItemDOS);
            clbrOperateDO.setClbrCode((String)clbrCode);
            clbrOperateDO.setOperateUser(NpContextHolder.getContext().getUserName());
            this.sendConfirm5Check5CancelMessage(clbrOperateDO);
        });
        return true;
    }

    public boolean sendClbrBillCancelMessage(ClbrBilCancelMsgContext context, List<ClbrBillCancelMsgDTO> clbrBillCancelMsgDTOS) {
        if (ClbrBillConfirmTypeEnum.AUTO.equals((Object)context.getClbrBillConfirmTypeEnum())) {
            return true;
        }
        Map clbrCode2ConfirmMsgDTOsMap = clbrBillCancelMsgDTOS.stream().collect(Collectors.groupingBy(ClbrBillCancelMsgDTO::getClbrCode, Collectors.mapping(Function.identity(), Collectors.toList())));
        clbrCode2ConfirmMsgDTOsMap.forEach((clbrCode, confirmMsgDTOs) -> {
            ClbrOperateDTO clbrOperateDO = new ClbrOperateDTO();
            List<ClbrItemDTO> clbrItemDOS = confirmMsgDTOs.stream().map(confirmMsgDTO -> {
                ClbrItemDTO clbrItemDO = new ClbrItemDTO();
                clbrItemDO.setClbrBillCode(confirmMsgDTO.getClbrBillCode());
                clbrItemDO.setSrcId(confirmMsgDTO.getSrcId());
                clbrItemDO.setClbrCode(confirmMsgDTO.getClbrCode());
                clbrItemDO.setOppClbrBillCode(confirmMsgDTO.getOppSrcId());
                clbrItemDO.setConfirmType(confirmMsgDTO.getConfirmType());
                return clbrItemDO;
            }).collect(Collectors.toList());
            clbrOperateDO.setClbrState(ClbrOperateTypeEnum.CANCEL.getCode());
            clbrOperateDO.setClbrItems(clbrItemDOS);
            clbrOperateDO.setClbrCode((String)clbrCode);
            clbrOperateDO.setClbrTime(DateUtils.format((Date)((ClbrBillCancelMsgDTO)confirmMsgDTOs.get(0)).getClbrTime(), (String)"yyyy-MM-dd HH:mm:ss.SSS"));
            clbrOperateDO.setOperateUser(NpContextHolder.getContext().getUserName());
            this.sendConfirm5Check5CancelMessage(clbrOperateDO);
        });
        return true;
    }

    public Object generateOppClbrBill(List<ClbrBillDTO> initiatorClbrBillDTOs) {
        ClbrPushDnaDTO clbrPushDnaDTO = new ClbrPushDnaDTO();
        clbrPushDnaDTO.setUserName(NpContextHolder.getContext().getUserName());
        clbrPushDnaDTO.setPageParams(initiatorClbrBillDTOs);
        String url = this.sendPushMessage(clbrPushDnaDTO);
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("sameService", false);
        result.put("url", url);
        return result;
    }

    public String getBillSsoUrl(ClbrBillDTO clbrBillDTO, String userName) {
        Object[] params = clbrBillDTO.getSrcId().split("&");
        if (ArrayUtils.isEmpty((Object[])params)) {
            log.error("srcId\u89e3\u6790\u5f02\u5e38={}", (Object)clbrBillDTO);
            throw new BusinessRuntimeException("\u534f\u540c\u5355\u636e\u683c\u5f0f\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\u7cfb\u7edf\u6765\u6e90");
        }
        Object defineId = params[1];
        Object dataId = params[0];
        StringBuilder builder = new StringBuilder();
        builder.append(this.url).append("sso/oa/login?userName=").append(userName).append("&openType=BILL&sso=sso_oa&defineId=").append((String)defineId).append("&dataId=").append((String)dataId).append("&showToolBar=true");
        return builder.toString();
    }

    private void sendRejectMessage(ClbrOperateDTO clbrOperateDO) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl((String)(this.url + PATH_REJECT));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity httpEntity = new HttpEntity((Object)clbrOperateDO, (MultiValueMap)headers);
        ResponseEntity result = this.restTemplate.postForEntity(builder.build().toString(), (Object)httpEntity, String.class, new Object[0]);
        log.info("\u53d1\u9001\u6d88\u606f\u54cd\u5e94={}", (Object)result);
        Map response = (Map)JSONUtil.parseObject((String)((String)result.getBody()), Map.class);
        if (Objects.isNull(response)) {
            throw new BusinessRuntimeException("\u53d1\u9001\u534f\u540c\u6d88\u606f\u5f02\u5e38\u3002");
        }
        if (!Boolean.TRUE.equals(response.get("success"))) {
            throw new BusinessRuntimeException(response.get("errorMessage").toString());
        }
    }

    private String sendPushMessage(ClbrPushDnaDTO clbrPushDnaDTO) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl((String)(this.url + PATH_PUSH));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity httpEntity = new HttpEntity((Object)clbrPushDnaDTO, (MultiValueMap)headers);
        ResponseEntity result = this.restTemplate.postForEntity(builder.build().toString(), (Object)httpEntity, String.class, new Object[0]);
        log.info("\u53d1\u9001\u6d88\u606f\u54cd\u5e94={}", (Object)result);
        Map response = (Map)JSONUtil.parseObject((String)((String)result.getBody()), Map.class);
        if (Objects.isNull(response)) {
            throw new BusinessRuntimeException("\u53d1\u9001\u534f\u540c\u6d88\u606f\u5f02\u5e38\u3002");
        }
        if (!"s".equals(response.get("code"))) {
            throw new BusinessRuntimeException(response.get("msg").toString());
        }
        return response.get("url").toString();
    }

    private void sendConfirm5Check5CancelMessage(ClbrOperateDTO clbrOperateDO) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl((String)(this.url + PATH_CONFIRM_CHECK_CANCEL));
        Map response = (Map)RestTemplateUtils.postJSON((String)builder.build().toString(), (Object)clbrOperateDO, (TypeReference)new TypeReference<Map<String, Object>>(){});
        log.info("\u53d1\u9001\u6d88\u606f\u54cd\u5e94={}", (Object)response);
        if (Objects.isNull(response)) {
            throw new BusinessRuntimeException("\u53d1\u9001\u534f\u540c\u6d88\u606f\u5f02\u5e38\u3002");
        }
        if (!Boolean.parseBoolean(response.get("success").toString())) {
            throw new BusinessRuntimeException(response.get("errorMessage").toString());
        }
    }
}


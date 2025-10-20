/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.coop.feign.client.ExternalClient
 *  com.jiuqi.coop.feign.config.domain.ClbrBillPullDTO
 *  com.jiuqi.coop.feign.config.domain.ClbrItemDTO
 *  com.jiuqi.coop.feign.config.domain.ClbrOperateDTO
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
 *  com.jiuqi.va.domain.common.R
 *  org.apache.commons.codec.digest.DigestUtils
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.client.ResourceAccessException
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.util.UriComponentsBuilder
 */
package com.jiuqi.gcreport.clbr.adapter.jqfssc;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.coop.feign.client.ExternalClient;
import com.jiuqi.coop.feign.config.domain.ClbrBillPullDTO;
import com.jiuqi.coop.feign.config.domain.ClbrItemDTO;
import com.jiuqi.coop.feign.config.domain.ClbrOperateDTO;
import com.jiuqi.gcreport.clbr.adapter.AbstractClbrSystemAdapterImpl;
import com.jiuqi.gcreport.clbr.adapter.context.ClbrBilCancelMsgContext;
import com.jiuqi.gcreport.clbr.adapter.context.ClbrBillConfirmMsgContext;
import com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillConfirmMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrGenerateAttributeDTO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillConfirmTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrOperateTypeEnum;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class JqFsscClbrSystemAdapterImpl
extends AbstractClbrSystemAdapterImpl {
    private static Logger LOGGER = LoggerFactory.getLogger(JqFsscClbrSystemAdapterImpl.class);
    public static final String SYSCODE = "JQ_FSSC";
    private static final String GET_TOKEN_URL = "openApi/anon/token/get";
    @Autowired
    private ExternalClient externalClient;
    @Value(value="${openApi.openId:#{null}}")
    private String openId;
    @Value(value="${feignClient.coop.url:#{null}}")
    private String url;
    @Value(value="${ssobill.salt:#{null}}")
    private String salt;
    private static final RestTemplate restTemplate = new RestTemplateBuilder(new RestTemplateCustomizer[0]).build();

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

    public Object generateOppClbrBill(List<ClbrBillDTO> initiatorClbrBillDTOs) {
        List clbrRecordDTOS = initiatorClbrBillDTOs.stream().map(initiatorClbrBillDTO -> {
            ClbrBillPullDTO clbrBillPullDTO = new ClbrBillPullDTO();
            clbrBillPullDTO.setAmount(initiatorClbrBillDTO.getAmount());
            clbrBillPullDTO.setNoverifyAmount(initiatorClbrBillDTO.getNoverifyAmount());
            clbrBillPullDTO.setVerifyedAmount(initiatorClbrBillDTO.getVerifyedAmount());
            clbrBillPullDTO.setClbrBillCode(initiatorClbrBillDTO.getClbrBillCode());
            clbrBillPullDTO.setClbrBillType(initiatorClbrBillDTO.getClbrBillType());
            clbrBillPullDTO.setClbrType(initiatorClbrBillDTO.getClbrType());
            clbrBillPullDTO.setCurrency(initiatorClbrBillDTO.getCurrency());
            clbrBillPullDTO.setNextUserName(initiatorClbrBillDTO.getNextUserName());
            clbrBillPullDTO.setOppClbrType(initiatorClbrBillDTO.getOppClbrType());
            clbrBillPullDTO.setOppClbrBillCode(initiatorClbrBillDTO.getOppClbrBillCode());
            clbrBillPullDTO.setOppOrgCode(initiatorClbrBillDTO.getOppOrgCode());
            clbrBillPullDTO.setOppRelation(initiatorClbrBillDTO.getOppRelation());
            clbrBillPullDTO.setOppSrcId(initiatorClbrBillDTO.getOppSrcId());
            clbrBillPullDTO.setOrgCode(initiatorClbrBillDTO.getOrgCode());
            clbrBillPullDTO.setRelation(initiatorClbrBillDTO.getRelation());
            clbrBillPullDTO.setSn(initiatorClbrBillDTO.getSn());
            clbrBillPullDTO.setSrcId(initiatorClbrBillDTO.getSrcId());
            clbrBillPullDTO.setSysCode(initiatorClbrBillDTO.getSysCode());
            clbrBillPullDTO.setUserName(initiatorClbrBillDTO.getUserName());
            clbrBillPullDTO.setUserTitle(initiatorClbrBillDTO.getUserTitle());
            clbrBillPullDTO.setId(initiatorClbrBillDTO.getId());
            clbrBillPullDTO.setVchrControlType(initiatorClbrBillDTO.getVchrControlType());
            clbrBillPullDTO.setFlowControlType(initiatorClbrBillDTO.getFlowControlType());
            clbrBillPullDTO.setClbrSchemeId(initiatorClbrBillDTO.getClbrSchemeId());
            clbrBillPullDTO.setExtendInfo(initiatorClbrBillDTO.getExtendInfo());
            return clbrBillPullDTO;
        }).collect(Collectors.toList());
        return this.externalClient.openBill(clbrRecordDTOS, NpContextHolder.getContext().getUserName());
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
            List clbrItemDOS = rejectMsgDTOs.stream().map(rejectMsgDTO -> {
                ClbrItemDTO clbrItemDO = new ClbrItemDTO();
                clbrItemDO.setClbrBillCode(rejectMsgDTO.getClbrBillCode());
                clbrItemDO.setSrcId(rejectMsgDTO.getSrcId());
                clbrItemDO.setSn(rejectMsgDTO.getSn());
                clbrItemDO.setClbrCode(rejectMsgDTO.getClbrCode());
                return clbrItemDO;
            }).collect(Collectors.toList());
            clbrOperateDO.setClbrState("reject");
            clbrOperateDO.setClbrItems(clbrItemDOS);
            clbrOperateDO.setClbrCode(clbrCode);
            clbrOperateDO.setClbrReason(rejectReason);
            LOGGER.info("\u53d1\u9001\u534f\u540c\u9a73\u56de\u53c2\u6570={}", (Object)JsonUtils.writeValueAsString((Object)clbrOperateDO));
            R r = this.externalClient.clbrOperate(clbrOperateDO);
            LOGGER.info("\u53d1\u9001\u534f\u540c\u9a73\u56de\u8fd4\u56de\u503c={}", (Object)JsonUtils.writeValueAsString((Object)clbrOperateDO));
            if (r.getCode() != 0) {
                throw new BusinessRuntimeException(r.getMsg());
            }
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
            List clbrItemDOS = confirmMsgDTOs.stream().map(confirmMsgDTO -> {
                ClbrItemDTO clbrItemDO = new ClbrItemDTO();
                clbrItemDO.setClbrBillCode(confirmMsgDTO.getClbrBillCode());
                clbrItemDO.setSrcId(confirmMsgDTO.getSrcId());
                clbrItemDO.setSn(confirmMsgDTO.getSn());
                clbrItemDO.setClbrCode(confirmMsgDTO.getClbrCode());
                return clbrItemDO;
            }).collect(Collectors.toList());
            clbrOperateDO.setClbrState(clbrOperateType);
            clbrOperateDO.setClbrItems(clbrItemDOS);
            clbrOperateDO.setClbrCode(clbrCode);
            clbrOperateDO.setClbrTime(DateUtils.format((Date)((ClbrBillConfirmMsgDTO)confirmMsgDTOs.get(0)).getClbrTime(), (String)"yyyy-MM-dd HH:mm:ss.SSS"));
            LOGGER.info("\u53d1\u9001\u534f\u540c\u786e\u8ba4\u53c2\u6570={}", (Object)JsonUtils.writeValueAsString((Object)clbrOperateDO));
            R r = this.externalClient.clbrOperate(clbrOperateDO);
            LOGGER.info("\u53d1\u9001\u534f\u540c\u786e\u8ba4\u8fd4\u56de\u503c={}", (Object)JsonUtils.writeValueAsString((Object)r));
            if (r.getCode() != 0) {
                throw new BusinessRuntimeException(r.getMsg());
            }
        });
        return true;
    }

    public List<Map<String, Object>> allowCancelClbrBill(List<ClbrBillDTO> clbrBillDTOS) {
        Map clbrCode2ConfirmMsgDTOsMap = clbrBillDTOS.stream().collect(Collectors.groupingBy(ClbrBillDTO::getClbrCode, Collectors.mapping(Function.identity(), Collectors.toList())));
        ArrayList<Map<String, Object>> allClbrItems = new ArrayList<Map<String, Object>>();
        clbrCode2ConfirmMsgDTOsMap.forEach((clbrCode, confirmMsgDTOs) -> {
            ClbrOperateDTO clbrOperateDO = new ClbrOperateDTO();
            List clbrItemDOS = confirmMsgDTOs.stream().map(confirmMsgDTO -> {
                ClbrItemDTO clbrItemDO = new ClbrItemDTO();
                clbrItemDO.setClbrBillCode(confirmMsgDTO.getClbrBillCode());
                clbrItemDO.setSrcId(confirmMsgDTO.getSrcId());
                clbrItemDO.setClbrCode(confirmMsgDTO.getClbrCode());
                clbrItemDO.setClbrReceiveBillCode(confirmMsgDTO.getOppClbrBillCode());
                clbrItemDO.setConfirmType(confirmMsgDTO.getConfirmType());
                return clbrItemDO;
            }).collect(Collectors.toList());
            clbrOperateDO.setClbrState(ClbrOperateTypeEnum.CANCELCHECK.getCode());
            clbrOperateDO.setClbrItems(clbrItemDOS);
            clbrOperateDO.setClbrCode(clbrCode);
            clbrOperateDO.setClbrTime(DateUtils.format((Date)((ClbrBillDTO)confirmMsgDTOs.get(0)).getClbrTime(), (String)"yyyy-MM-dd HH:mm:ss.SSS"));
            LOGGER.info("\u53d1\u9001\u53d6\u6d88\u534f\u540c\u53c2\u6570={}", (Object)JsonUtils.writeValueAsString((Object)clbrOperateDO));
            R r = this.externalClient.clbrOperate(clbrOperateDO);
            LOGGER.info("\u53d1\u9001\u53d6\u6d88\u534f\u540c\u8fd4\u56de\u503c={}", (Object)JsonUtils.writeValueAsString((Object)r));
            if (r.getCode() != 0) {
                throw new BusinessRuntimeException(r.getMsg());
            }
            allClbrItems.addAll((List)r.get((Object)"clbrItems"));
        });
        return allClbrItems;
    }

    public boolean sendClbrBillCancelMessage(ClbrBilCancelMsgContext context, List<ClbrBillCancelMsgDTO> clbrBillCancelMsgDTOS) {
        if (ClbrBillConfirmTypeEnum.AUTO.equals((Object)context.getClbrBillConfirmTypeEnum())) {
            return true;
        }
        Map clbrCode2ConfirmMsgDTOsMap = clbrBillCancelMsgDTOS.stream().collect(Collectors.groupingBy(ClbrBillCancelMsgDTO::getClbrCode, Collectors.mapping(Function.identity(), Collectors.toList())));
        clbrCode2ConfirmMsgDTOsMap.forEach((clbrCode, confirmMsgDTOs) -> {
            ClbrOperateDTO clbrOperateDO = new ClbrOperateDTO();
            List clbrItemDOS = confirmMsgDTOs.stream().map(confirmMsgDTO -> {
                ClbrItemDTO clbrItemDO = new ClbrItemDTO();
                clbrItemDO.setClbrBillCode(confirmMsgDTO.getClbrBillCode());
                clbrItemDO.setSrcId(confirmMsgDTO.getSrcId());
                clbrItemDO.setClbrCode(confirmMsgDTO.getClbrCode());
                clbrItemDO.setClbrReceiveBillCode(confirmMsgDTO.getOppClbrBillCode());
                clbrItemDO.setConfirmType(confirmMsgDTO.getConfirmType());
                return clbrItemDO;
            }).collect(Collectors.toList());
            clbrOperateDO.setClbrState(ClbrOperateTypeEnum.CANCEL.getCode());
            clbrOperateDO.setClbrItems(clbrItemDOS);
            clbrOperateDO.setClbrCode(clbrCode);
            clbrOperateDO.setClbrTime(DateUtils.format((Date)((ClbrBillCancelMsgDTO)confirmMsgDTOs.get(0)).getClbrTime(), (String)"yyyy-MM-dd HH:mm:ss.SSS"));
            LOGGER.info("\u53d1\u9001\u53d6\u6d88\u534f\u540c\u53c2\u6570={}", (Object)JsonUtils.writeValueAsString((Object)clbrOperateDO));
            R r = this.externalClient.clbrOperate(clbrOperateDO);
            LOGGER.info("\u53d1\u9001\u53d6\u6d88\u534f\u540c\u8fd4\u56de\u503c={}", (Object)JsonUtils.writeValueAsString((Object)r));
            if (r.getCode() != 0) {
                throw new BusinessRuntimeException(r.getMsg());
            }
        });
        return true;
    }

    public String getSysCode() {
        return SYSCODE;
    }

    public String getBillSsoUrl(ClbrBillDTO clbrBillDTO, String userName) {
        String token = this.sendPostForRestful(userName);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl((String)(this.url + "/ssobill/show"));
        String time = UUIDUtils.newUUIDStr();
        String clbrBillCode = clbrBillDTO.getClbrBillCode();
        String ordinaryVerify = clbrBillCode + time + this.salt;
        String localVerifyCode = DigestUtils.md5Hex((String)ordinaryVerify);
        builder.queryParam("token", new Object[]{token});
        builder.queryParam("user", new Object[]{userName});
        builder.queryParam("billcode", new Object[]{clbrBillCode});
        builder.queryParam("time", new Object[]{time});
        builder.queryParam("verfiycode", new Object[]{localVerifyCode});
        builder.queryParam("showtoolbar", new Object[]{"false"});
        return builder.build().toString();
    }

    private String sendPostForRestful(String userName) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl((String)(this.url + GET_TOKEN_URL));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HashMap<String, String> param = new HashMap<String, String>();
        if (!StringUtils.isEmpty((String)userName)) {
            param.put("username", userName);
        }
        param.put("openid", this.openId);
        HttpEntity httpEntity = new HttpEntity(param, (MultiValueMap)headers);
        LOGGER.info("\u83b7\u53d6\u8ba4\u8bc1token\u53c2\u6570={}", (Object)JsonUtils.writeValueAsString(param));
        ResponseEntity result = null;
        for (int i = 1; i <= 3; ++i) {
            try {
                result = restTemplate.postForEntity(builder.build().toString(), (Object)httpEntity, String.class, new Object[0]);
                if (!Objects.nonNull(result)) continue;
                break;
            }
            catch (ResourceAccessException resourceAccessException) {
                // empty catch block
            }
        }
        LOGGER.info("\u53d1\u9001\u6d88\u606f\u54cd\u5e94={}", (Object)result);
        if (Objects.isNull(result.getBody())) {
            throw new BusinessRuntimeException("\u83b7\u53d6token\u5f02\u5e38");
        }
        Map response = (Map)JSONUtil.parseObject((String)((String)result.getBody()), Map.class);
        if (Objects.isNull(response)) {
            throw new BusinessRuntimeException("\u83b7\u53d6token\u5f02\u5e38");
        }
        if (!"0".equals(response.get("code").toString())) {
            throw new BusinessRuntimeException(response.get("msg").toString());
        }
        return response.get("token").toString();
    }
}


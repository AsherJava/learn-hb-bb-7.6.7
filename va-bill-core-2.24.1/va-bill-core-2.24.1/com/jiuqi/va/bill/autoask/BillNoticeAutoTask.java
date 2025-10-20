/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.autotask.AutoTask
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.option.OptionItemDO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BillVerifyClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.VaSystemOptionClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageTemplateSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageTemplateClient
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateDTO
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO
 */
package com.jiuqi.va.bill.autoask;

import com.jiuqi.va.bill.message.BillNoticeMessageTemplate;
import com.jiuqi.va.bill.utils.Base64Utils;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.intf.autotask.AutoTask;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.option.OptionItemDO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BillVerifyClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.VaSystemOptionClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageTemplateSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageTemplateClient;
import com.jiuqi.va.message.template.domain.VaMessageTemplateDTO;
import com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class BillNoticeAutoTask
implements AutoTask {
    @Autowired
    private VaMessageTemplateClient vaMessageTemplateClient;
    @Autowired
    private VaSystemOptionClient vaSystemOptionClient;
    @Autowired
    private BillVerifyClient billVerifyClient;
    @Autowired
    private BillNoticeMessageTemplate billNoticeMessageTemplate;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private EnumDataClient enumDataClient;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private BaseDataClient baseDataClient;
    private static final Logger logger = LoggerFactory.getLogger(BillNoticeAutoTask.class);

    public String getName() {
        return "BillNotice";
    }

    public String getTitle() {
        return "\u5355\u636e\u6d88\u606f\u901a\u77e5";
    }

    public String getAutoTaskModule() {
        return "bill";
    }

    public Boolean canRetract() {
        return false;
    }

    public R execute(Object params) {
        boolean enableEmailTemplate = this.enableEmailTemplate();
        try {
            VaMessageTemplateSendDTO vaMessageDO = new VaMessageTemplateSendDTO();
            vaMessageDO.setMsgtype("\u4e8b\u9879\u901a\u77e5");
            vaMessageDO.setGrouptype("\u901a\u77e5");
            vaMessageDO.setCreateuser("system");
            TenantDO param = (TenantDO)params;
            if (param.getExtInfo("bizCode") == null) {
                return R.error((String)"\u672a\u627e\u5230\u4e1a\u52a1\u6807\u8bc6\u53c2\u6570");
            }
            String bizCode = (String)param.getExtInfo("bizCode");
            String bizType = (String)param.getExtInfo("bizType");
            int approvalResult = param.getExtInfo("approvalResult") != null ? (Integer)param.getExtInfo("approvalResult") : 1;
            Map<String, Object> todoParam = BillUtils.getMap(param.getExtInfo("todoParam"));
            List autoTaskParams = (List)param.getExtInfo("autoTaskParam");
            ArrayList<UserDO> userList = new ArrayList<UserDO>();
            ArrayList<String> userIds = new ArrayList<String>();
            UserDTO userDTO = new UserDTO();
            for (Object obj : autoTaskParams) {
                Object realValue;
                Object receiveUser;
                if (!(obj instanceof Map) || !((receiveUser = ((Map)obj).get("receiveUser")) instanceof Map) || (realValue = ((Map)receiveUser).get("realValue")) == null) continue;
                if (realValue instanceof List) {
                    List realList = (List)realValue;
                    for (Object real : realList) {
                        userDTO.setId(real.toString());
                        UserDO userDO = this.authUserClient.get(userDTO);
                        if (userDO == null) continue;
                        userList.add(userDO);
                        userIds.add(userDO.getId());
                    }
                    continue;
                }
                if (!(realValue instanceof String)) continue;
                userDTO.setId(realValue.toString());
                UserDO userDO = this.getOneUserData(ShiroUtil.getTenantName(), String.valueOf(realValue));
                if (userDO == null) continue;
                userList.add(userDO);
                userIds.add(userDO.getId());
            }
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("billCode", bizCode);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("defineCode", (Object)bizType);
            MetaDataClient metaDataClient = (MetaDataClient)ApplicationContextRegister.getBean(MetaDataClient.class);
            R r2 = metaDataClient.findMetaInfoByDefineCode(tenant);
            if (r2.getCode() != 0 || r2.get((Object)"title") == null) {
                return R.error((String)"\u672a\u627e\u5230\u5355\u636e\u5b9a\u4e49");
            }
            String defineTitle = (String)r2.get((Object)"title");
            UserDO userDO = null;
            if (todoParam.get("SUBMITUSER") != null) {
                userDTO.setId(todoParam.get("SUBMITUSER").toString());
                userDO = this.authUserClient.get(userDTO);
            }
            Object submitTimeObj = todoParam.get("SUBMITTIME");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String stringTime = submitTimeObj instanceof Date ? sdf.format(submitTimeObj) : (submitTimeObj instanceof Long ? sdf.format(new Date((Long)submitTimeObj)) : (String)submitTimeObj);
            String titleTime = StringUtils.hasText(stringTime) ? "\u4e8e" + stringTime : "";
            String titlePrefix = userDO != null ? userDO.getName() + titleTime + "\u63d0\u4ea4\u7684" : "";
            String titleSuffix = defineTitle + bizCode + "\u5df2" + (approvalResult == 1 ? "\u901a\u8fc7\u5ba1\u6838" : "\u9a73\u56de");
            String title = titlePrefix + titleSuffix;
            vaMessageDO.setTitle(title);
            OptionItemDO optionItemDO = new OptionItemDO();
            optionItemDO.setName("SYSADDR");
            List optionItemVoList = this.vaSystemOptionClient.list(optionItemDO);
            String sysaddr = ((OptionItemVO)optionItemVoList.get(0)).getVal();
            StringBuilder memo = new StringBuilder();
            if (todoParam.get("REMARK") != null) {
                try {
                    List array = JSONUtil.parseMapArray((String)todoParam.get("REMARK").toString());
                    for (Object obj : array) {
                        memo.append("\n").append(obj.toString());
                    }
                }
                catch (Exception e) {
                    memo = new StringBuilder(todoParam.get("REMARK").toString());
                }
            }
            if (this.ifMessageTemplateSendMessage(autoTaskParams)) {
                this.useMessageTemplateSendMessage(autoTaskParams, userList, param, title);
            } else {
                Map<String, String> verifyCodes = this.getVerifyCodeMap(bizCode, userIds);
                for (UserDO user : userList) {
                    String verifycode = verifyCodes.get(user.getId());
                    String pcBillUrl = "/#/sso/bill/" + bizType + "/" + bizCode + "/BROWSE/" + verifycode;
                    HashMap<String, String> json = new HashMap<String, String>();
                    json.put("url", pcBillUrl);
                    json.put("appName", "bill-app");
                    json.put("appTitle", defineTitle);
                    json.put("funcName", "VaBill");
                    HashMap<String, String> appConfigJson = new HashMap<String, String>();
                    appConfigJson.put("defineCode", bizType);
                    appConfigJson.put("billCode", bizCode);
                    appConfigJson.put("dataState", "BROWSE");
                    appConfigJson.put("verifyCode", verifycode);
                    json.put("appConfig", JSONUtil.toJSONString(appConfigJson));
                    vaMessageDO.setParam(JSONUtil.toJSONString(json));
                    String billUrl = "/gmt/bill/billApproval?fromType=1&defineCode=" + bizType + "&billCode=" + bizCode;
                    if (!verifyCodes.isEmpty()) {
                        billUrl = billUrl + "&verifyCode=" + verifycode;
                    }
                    String url = sysaddr + "/anon/wxwork/login?tenantName=" + tenant.getTenantName() + "&hashMode=" + false + "&url=" + Base64Utils.encodeToUrlSafeString(billUrl.getBytes(StandardCharsets.UTF_8));
                    String content = defineTitle + "\n\u5236\u5355\u4eba\uff1a" + (userDO != null ? userDO.getName() : "") + "\n\u901a\u77e5\u4e8b\u9879\uff1a" + bizCode;
                    if (StringUtils.hasText(memo.toString())) {
                        content = content + "\n\u6458\u8981\uff1a" + memo;
                    }
                    content = content + "\n<a href=\"" + url + "\">\u70b9\u51fb\u6b64\u5904\u8fdb\u884c\u67e5\u770b\u2026</a>";
                    vaMessageDO.setContent(content);
                    ArrayList<String> receiveUsers = new ArrayList<String>();
                    receiveUsers.add(user.getId());
                    vaMessageDO.setReceiveUserIds(receiveUsers);
                    vaMessageDO.setForbidEmail(true);
                    this.vaMessageTemplateClient.sendMessage(vaMessageDO);
                }
            }
            if (enableEmailTemplate) {
                this.sendEmailMsgByTemplate(userIds, todoParam, defineTitle);
            } else {
                VaMessageTemplateSendDTO vaMessageTemplateSendDTO = new VaMessageTemplateSendDTO();
                vaMessageTemplateSendDTO.setMsgChannel(VaMessageOption.MsgChannel.EMAIL);
                vaMessageTemplateSendDTO.setReceiveUserIds(userIds);
                vaMessageTemplateSendDTO.setTitle(title);
                String mailContent = defineTitle + "\n\u5236\u5355\u4eba\uff1a" + (userDO != null ? userDO.getName() : "") + "\n\u901a\u77e5\u4e8b\u9879\uff1a" + bizCode;
                if (StringUtils.hasText(memo.toString())) {
                    mailContent = mailContent + "\n\u6458\u8981\uff1a" + memo;
                }
                mailContent = mailContent + "\n<a href=\"" + sysaddr + "\">\u70b9\u51fb\u6b64\u5904\u8fdb\u884c\u67e5\u770b\u2026</a>";
                vaMessageTemplateSendDTO.setContent(mailContent);
                this.vaMessageTemplateClient.sendMessage(vaMessageTemplateSendDTO);
            }
        }
        catch (Exception e) {
            logger.error("\u5355\u636e\u6d88\u606f\u901a\u77e5", e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    private Map<String, String> getVerifyCodeMap(String bizCode, List<String> userIds) {
        BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
        billVerifyDTO.setAuth(1);
        billVerifyDTO.setBillCode(bizCode);
        billVerifyDTO.setUserIds(userIds);
        R re = this.billVerifyClient.encodeBillCode(billVerifyDTO);
        Object dataObj = re.get((Object)"data");
        Map verifyCodes = null;
        if (dataObj instanceof Map) {
            verifyCodes = (Map)dataObj;
        }
        return Optional.ofNullable(verifyCodes).orElse(Collections.emptyMap());
    }

    private boolean ifMessageTemplateSendMessage(List<Object> autoTaskParamList) {
        if (CollectionUtils.isEmpty(autoTaskParamList)) {
            return false;
        }
        Map<String, Object> billNoticeMessageConfigMap = BillUtils.getMap(autoTaskParamList.get(0));
        String messageTemplateCode = (String)billNoticeMessageConfigMap.get("messageTemplateCode");
        return StringUtils.hasText(messageTemplateCode);
    }

    private R useMessageTemplateSendMessage(List<Object> autoTaskParamList, List<UserDO> userList, TenantDO param, String title) {
        if (CollectionUtils.isEmpty(autoTaskParamList)) {
            return R.error((String)"\u5355\u636e\u6d88\u606f\u901a\u77e5\u81ea\u52a8\u4efb\u52a1\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Map<String, Object> billNoticeMessageConfigMap = BillUtils.getMap(autoTaskParamList.get(0));
        String messageTemplateCode = (String)billNoticeMessageConfigMap.get("messageTemplateCode");
        String bizCode = (String)param.getExtInfo("bizCode");
        String bizDefine = (String)param.getExtInfo("bizType");
        Map todoParam = (Map)param.getExtInfo("todoParam");
        List messageTemplateParamObjectList = BillUtils.getList(param.getExtInfo("carbonCopyTemplateParam"));
        List<Object> vaMessageTemplateParamDOList = new ArrayList();
        if ("BILLNOTICE".equals(messageTemplateCode)) {
            vaMessageTemplateParamDOList = this.billNoticeMessageTemplate.getTemplateParams();
        } else if ("CARBONCOPYMESSAGE".equalsIgnoreCase(messageTemplateCode)) {
            for (Object object : messageTemplateParamObjectList) {
                VaMessageTemplateParamDO vaMessageTemplateParamDO = (VaMessageTemplateParamDO)JSONUtil.parseObject((String)JSONUtil.toJSONString(object), VaMessageTemplateParamDO.class);
                vaMessageTemplateParamDOList.add(vaMessageTemplateParamDO);
            }
        }
        TenantDO tenant = new TenantDO();
        tenant.addExtInfo("defineCode", (Object)bizDefine);
        MetaDataClient metaDataClient = (MetaDataClient)ApplicationContextRegister.getBean(MetaDataClient.class);
        R r2 = metaDataClient.findMetaInfoByDefineCode(tenant);
        if (r2.getCode() != 0 || r2.get((Object)"title") == null) {
            return R.error((String)"\u672a\u627e\u5230\u5355\u636e\u5b9a\u4e49");
        }
        String bizDefineTitle = (String)r2.get((Object)"title");
        String unitCode = todoParam.get("UNITCODE") != null ? todoParam.get("UNITCODE").toString() : null;
        List<String> userIds = userList.stream().map(UserDO::getId).collect(Collectors.toList());
        Map<String, String> verifyCodes = this.getVerifyCodeMap(bizCode, userIds);
        String approveUserId = (String)param.getExtInfo("approveUserId");
        todoParam.put("APPROVEUSERID", approveUserId);
        HashMap<String, Object> messageParamMap = new HashMap<String, Object>();
        messageParamMap.put("BIZDEFINE", bizDefineTitle);
        messageParamMap.put("BIZCODE", bizCode);
        this.getParamMap(todoParam, vaMessageTemplateParamDOList, messageParamMap, ShiroUtil.getTenantName());
        String approveUserName = (String)messageParamMap.get("APPROVEUSERID");
        String messageTitle = "\u60a8\u6536\u5230\u7531" + approveUserName + "\u53d1\u9001\u7684" + bizDefineTitle + bizCode + "\u5ba1\u6279\u6284\u9001\u901a\u77e5";
        if ("BILLNOTICE".equals(messageTemplateCode)) {
            messageTitle = title;
        }
        OptionItemDO optionItemDO = new OptionItemDO();
        optionItemDO.setName("SYSADDR");
        List optionItemVoList = this.vaSystemOptionClient.list(optionItemDO);
        String sysaddr = ((OptionItemVO)optionItemVoList.get(0)).getVal();
        VaMessageTemplateSendDTO vaMessageSendDTO = new VaMessageTemplateSendDTO();
        vaMessageSendDTO.setMsgtype("\u4e8b\u9879\u901a\u77e5");
        vaMessageSendDTO.setGrouptype("\u901a\u77e5");
        vaMessageSendDTO.setCreateuser("system");
        for (UserDO userDO : userList) {
            String verifyCode = verifyCodes.get(userDO.getId());
            String pcOpenBill = "/#/sso/bill/" + bizDefine + "/" + bizCode + "/BROWSE/" + verifyCode;
            HashMap<String, String> json = new HashMap<String, String>();
            json.put("url", pcOpenBill);
            json.put("appName", "bill-app");
            json.put("appTitle", bizDefineTitle);
            json.put("funcName", "VaBill");
            HashMap<String, String> appConfigJson = new HashMap<String, String>();
            appConfigJson.put("defineCode", bizDefine);
            appConfigJson.put("billCode", bizCode);
            appConfigJson.put("dataState", "BROWSE");
            appConfigJson.put("verifyCode", verifyCode);
            json.put("appConfig", JSONUtil.toJSONString(appConfigJson));
            String billUrl = "/gmt/bill/billApproval?fromType=1&defineCode=" + bizDefine + "&billCode=" + bizCode;
            if (!verifyCodes.isEmpty()) {
                billUrl = billUrl + "&verifyCode=" + verifyCode;
            }
            String url = sysaddr + "/anon/wxwork/login?tenantName=" + ShiroUtil.getTenantName() + "&hashMode=" + true + "&url=" + Base64Utils.encodeToUrlSafeString(billUrl.getBytes(StandardCharsets.UTF_8));
            HashMap<String, String> urlMap = new HashMap<String, String>(4);
            urlMap.put("wxWorkUrl", url);
            urlMap.put("emailUrl", url);
            vaMessageSendDTO.setContent(messageTitle);
            vaMessageSendDTO.setUrlMap(urlMap);
            vaMessageSendDTO.setTitle(messageTitle);
            vaMessageSendDTO.setTitle(messageTitle);
            vaMessageSendDTO.setParam(JSONUtil.toJSONString(json));
            ArrayList<String> tempId = new ArrayList<String>();
            tempId.add(userDO.getId());
            vaMessageSendDTO.setReceiveUserIds(tempId);
            vaMessageSendDTO.setParamMap(messageParamMap);
            vaMessageSendDTO.setTemplateName(messageTemplateCode);
            vaMessageSendDTO.setUnitcode(unitCode);
            this.vaMessageTemplateClient.sendMessage(vaMessageSendDTO);
        }
        return R.ok();
    }

    private boolean enableEmailTemplate() {
        VaMessageTemplateDTO templateParam = new VaMessageTemplateDTO();
        templateParam.setName("BILLNOTICE");
        templateParam.setFunctionmodule("VABILLNOTICE");
        R r = this.vaMessageTemplateClient.getTemplateConfig(templateParam);
        if (r.getCode() != 0 || !r.containsKey((Object)"templateConfig")) {
            return false;
        }
        VaMessageTemplateDTO templateConfig = (VaMessageTemplateDTO)r.get((Object)"templateConfig");
        Map configMap = templateConfig.getConfigJson();
        if (configMap == null || !configMap.containsKey("EMAIL")) {
            return false;
        }
        Map emailConfig = (Map)configMap.get("EMAIL");
        return emailConfig.containsKey("enable") && Boolean.TRUE.equals(emailConfig.get("enable"));
    }

    private void sendEmailMsgByTemplate(List<String> userIds, Map<String, Object> taskInfo, String bizDefineTitle) {
        VaMessageTemplateSendDTO vaMessageTemplateSendDTO = new VaMessageTemplateSendDTO();
        vaMessageTemplateSendDTO.setMsgChannel(VaMessageOption.MsgChannel.EMAIL);
        vaMessageTemplateSendDTO.setTemplateName("BILLNOTICE");
        vaMessageTemplateSendDTO.setReceiveUserIds(userIds);
        vaMessageTemplateSendDTO.setUnitcode(taskInfo.get("UNITCODE") != null ? taskInfo.get("UNITCODE").toString() : null);
        List<VaMessageTemplateParamDO> params = this.billNoticeMessageTemplate.getTemplateParams();
        HashMap<String, Object> messageParamMap = new HashMap<String, Object>();
        this.getParamMap(taskInfo, params, messageParamMap, ShiroUtil.getTenantName());
        messageParamMap.put("BIZDEFINE", bizDefineTitle);
        vaMessageTemplateSendDTO.setParamMap(messageParamMap);
        this.vaMessageTemplateClient.sendMessage(vaMessageTemplateSendDTO);
    }

    private void getParamMap(Map<String, Object> todoMap, List<VaMessageTemplateParamDO> params, Map<String, Object> messageParamMap, String tenantName) {
        for (VaMessageTemplateParamDO vaMessageTemplateParamDO : params) {
            BaseDataDO baseDataDO;
            Integer mappingType;
            String paramName = vaMessageTemplateParamDO.getName();
            if (messageParamMap.get(paramName) != null) continue;
            String value = todoMap.get(paramName) == null ? "" : todoMap.get(paramName);
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String columnType = vaMessageTemplateParamDO.getType();
            if (DataModelType.ColumnType.TIMESTAMP.name().equals(columnType) && value instanceof Long) {
                messageParamMap.put(paramName, dateTimeFormat.format(new Date((Long)((Object)value))));
            } else if (DataModelType.ColumnType.TIMESTAMP.name().equals(columnType) && value instanceof Date) {
                messageParamMap.put(paramName, dateTimeFormat.format(value));
            } else if (DataModelType.ColumnType.DATE.name().equals(columnType) && value instanceof Date) {
                messageParamMap.put(paramName, dateFormat.format(value));
            } else {
                messageParamMap.put(paramName, value);
            }
            if ((mappingType = vaMessageTemplateParamDO.getMappingType()) == null || ObjectUtils.isEmpty(value)) continue;
            String mapping = vaMessageTemplateParamDO.getMapping();
            if (mappingType == 0) {
                messageParamMap.put(paramName, "1".equals(value) ? "\u662f" : "\u5426");
                continue;
            }
            if (mappingType == 4) {
                OrgDO orgDO = Optional.ofNullable(this.getOneOrgData(tenantName, value.toString())).orElse((OrgDO)new OrgDTO());
                messageParamMap.put(paramName, orgDO.getName());
                continue;
            }
            if (mappingType == 3) {
                UserDO userDO = this.getOneUserData(tenantName, value.toString());
                messageParamMap.put(paramName, userDO.getName());
                continue;
            }
            if (mappingType == 2) {
                EnumDataDO enumDataDO = this.getEnumData(mapping, tenantName, value.toString());
                if (enumDataDO == null) continue;
                messageParamMap.put(paramName, enumDataDO.getTitle());
                continue;
            }
            if (mappingType != 1 || (baseDataDO = this.getOneBaseData(mapping, tenantName, value.toString())) == null) continue;
            messageParamMap.put(paramName, baseDataDO.getName());
        }
    }

    private UserDO getOneUserData(String tenantName, String fieldValue) {
        UserDTO userDTO = new UserDTO();
        userDTO.setTenantName(tenantName);
        userDTO.setId(fieldValue);
        UserDO userDO = this.authUserClient.get(userDTO);
        if (Objects.nonNull(userDO)) {
            return userDO;
        }
        userDTO.setId(null);
        userDTO.setUsername(fieldValue);
        userDO = this.authUserClient.get(userDTO);
        return userDO;
    }

    private OrgDO getOneOrgData(String tenantName, String fieldValue) {
        OrgDO orgDO = null;
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setRecoveryflag(Integer.valueOf(-1));
        orgDTO.setCode(fieldValue);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setTenantName(tenantName);
        PageVO list = this.orgDataClient.list(orgDTO);
        List rows = list.getRows();
        if (rows != null && !rows.isEmpty()) {
            orgDO = (OrgDO)rows.get(0);
        }
        return orgDO;
    }

    private EnumDataDO getEnumData(String mapping, String tenantName, String fieldValue) {
        EnumDataDO enumDataDO = null;
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setBiztype(mapping.split("\\.")[0]);
        enumDataDTO.setVal(fieldValue);
        enumDataDTO.setTenantName(tenantName);
        List enumDatas = this.enumDataClient.list(enumDataDTO);
        if (enumDatas != null && !enumDatas.isEmpty()) {
            enumDataDO = (EnumDataDO)enumDatas.get(0);
        }
        return enumDataDO;
    }

    private BaseDataDO getOneBaseData(String mapping, String tenantName, String fieldValue) {
        BaseDataDO baseDataDO = null;
        BaseDataDTO basedataDTO = new BaseDataDTO();
        String[] mapp = mapping.split("\\.");
        basedataDTO.setTableName(mapp[0]);
        if ("OBJECTCODE".equals(mapp[1])) {
            basedataDTO.setObjectcode(fieldValue);
        } else {
            basedataDTO.setCode(fieldValue);
        }
        basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        basedataDTO.setTenantName(tenantName);
        PageVO list = this.baseDataClient.list(basedataDTO);
        if (list != null && !list.getRows().isEmpty()) {
            baseDataDO = (BaseDataDO)list.getRows().get(0);
        }
        return baseDataDO;
    }

    public R retrack(Object params) {
        return R.ok();
    }
}


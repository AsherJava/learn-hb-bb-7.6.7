/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.auto.task.MessageDetailInfo
 *  com.jiuqi.va.domain.workflow.auto.task.MessageNoticeParam
 *  com.jiuqi.va.domain.workflow.auto.task.ParamHandleResult
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BillVerifyClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.VaSystemOptionClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.service.auto.task;

import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.service.auto.task.BillAutoTaskService;
import com.jiuqi.va.bill.utils.Base64Utils;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.auto.task.MessageDetailInfo;
import com.jiuqi.va.domain.workflow.auto.task.MessageNoticeParam;
import com.jiuqi.va.domain.workflow.auto.task.ParamHandleResult;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BillVerifyClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.VaSystemOptionClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BillAutoTaskServiceImpl
implements BillAutoTaskService {
    private static final String REMARK = "REMARK";
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private BillVerifyClient billVerifyClient;
    @Autowired
    private VaSystemOptionClient vaSystemOptionClient;

    @Override
    public ParamHandleResult handleMessageNoticeParam(MessageNoticeParam messageNoticeParam) {
        ParamHandleResult paramHandleResult = new ParamHandleResult();
        Map param = messageNoticeParam.getParam();
        String bizDefine = (String)param.get("bizType");
        String defineTitle = this.getBizDefineTitle(bizDefine);
        paramHandleResult.setBizDefineTitle(defineTitle);
        String bizCode = (String)param.get("bizCode");
        Map<String, Object> todoParam = BillUtils.getMap(param.get("todoParam"));
        String memo = this.getMemo(todoParam);
        UserDO submitUser = this.getSubmitUser(todoParam);
        String systemAddress = this.getSystemAddress();
        String tenantName = TenantUtil.getTenantName();
        String messageTemplateCode = messageNoticeParam.getMessageTemplateCode();
        String title = null;
        String mailContent = null;
        if ("BILLNOTICE".equalsIgnoreCase(messageTemplateCode)) {
            title = BillAutoTaskServiceImpl.createBillNoticeDefaultTitle(todoParam, submitUser, param, defineTitle, bizCode);
            mailContent = BillAutoTaskServiceImpl.createBillNoticeDefaultMailContent(defineTitle, submitUser, bizCode, memo, systemAddress);
        } else if ("CARBONCOPYMESSAGE".equalsIgnoreCase(messageTemplateCode)) {
            mailContent = title = this.creatCarbonCopyDefaultTitle(param, tenantName, defineTitle, bizCode);
        }
        paramHandleResult.setTitle(title);
        paramHandleResult.setMailContent(mailContent);
        List userList = messageNoticeParam.getReceiveUserIdList();
        Map<String, String> verifyCodes = this.getVerifyCodeMap(bizCode, userList);
        ArrayList<MessageDetailInfo> messageDetailInfoList = new ArrayList<MessageDetailInfo>(userList.size());
        for (String userId : userList) {
            MessageDetailInfo messageDetailInfo = new MessageDetailInfo();
            String verifyCode = verifyCodes.get(userId);
            messageDetailInfo.setParam(this.createParamJson(bizDefine, bizCode, verifyCode, defineTitle));
            String url = BillAutoTaskServiceImpl.getVisitUrl(bizDefine, bizCode, verifyCode, systemAddress, tenantName);
            String content = null;
            if ("BILLNOTICE".equalsIgnoreCase(messageTemplateCode)) {
                content = BillAutoTaskServiceImpl.createBillNoticeContent(defineTitle, submitUser, bizCode, memo, url);
            } else if ("CARBONCOPYMESSAGE".equalsIgnoreCase(messageTemplateCode)) {
                content = title;
            }
            messageDetailInfo.setContent(content);
            HashMap<String, String> urlMap = new HashMap<String, String>(2);
            urlMap.put("wxWorkUrl", url);
            urlMap.put("emailUrl", url);
            messageDetailInfo.setUrlMap(urlMap);
            ArrayList<String> receiveUsers = new ArrayList<String>();
            receiveUsers.add(userId);
            messageDetailInfo.setReceiveUserIds(receiveUsers);
            messageDetailInfoList.add(messageDetailInfo);
        }
        paramHandleResult.setMessageDetailInfoList(messageDetailInfoList);
        return paramHandleResult;
    }

    private String getBizDefineTitle(String bizDefine) {
        TenantDO tenant = new TenantDO();
        tenant.addExtInfo("defineCode", (Object)bizDefine);
        MetaDataClient metaDataClient = (MetaDataClient)ApplicationContextRegister.getBean(MetaDataClient.class);
        R r = metaDataClient.findMetaInfoByDefineCode(tenant);
        if (r.getCode() == 0 && r.get((Object)"title") != null) {
            return (String)r.get((Object)"title");
        }
        throw new BillException("\u672a\u627e\u5230\u5355\u636e\u5b9a\u4e49");
    }

    private String getMemo(Map<String, Object> todoParam) {
        StringBuilder memo = new StringBuilder();
        if (todoParam.get(REMARK) != null) {
            try {
                List array = JSONUtil.parseMapArray((String)todoParam.get(REMARK).toString());
                for (Object obj : array) {
                    memo.append("\n").append(obj.toString());
                }
            }
            catch (Exception e) {
                memo = new StringBuilder(todoParam.get(REMARK).toString());
            }
        }
        return memo.toString();
    }

    private UserDO getSubmitUser(Map<String, Object> todoParam) {
        UserDO userDO = null;
        if (todoParam.get("SUBMITUSER") != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(todoParam.get("SUBMITUSER").toString());
            userDO = this.authUserClient.get(userDTO);
        }
        return userDO;
    }

    private String getSystemAddress() {
        OptionItemDO optionItemDO = new OptionItemDO();
        optionItemDO.setName("SYSADDR");
        List optionItemVoList = this.vaSystemOptionClient.list(optionItemDO);
        return ((OptionItemVO)optionItemVoList.get(0)).getVal();
    }

    private static String createBillNoticeDefaultTitle(Map<String, Object> todoParam, UserDO submitUser, Map<String, Object> param, String defineTitle, String bizCode) {
        int approvalResult;
        Object submitTimeObj = todoParam.get("SUBMITTIME");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringTime = submitTimeObj instanceof Date ? sdf.format(submitTimeObj) : (submitTimeObj instanceof Long ? sdf.format(new Date((Long)submitTimeObj)) : (String)submitTimeObj);
        int n = approvalResult = param.get("approvalResult") != null ? (Integer)param.get("approvalResult") : 1;
        String title = submitUser != null ? (StringUtils.hasText(stringTime) ? (approvalResult == 1 ? BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice1", new Object[]{submitUser.getName(), stringTime, defineTitle + bizCode}) : BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice2", new Object[]{submitUser.getName(), stringTime, defineTitle + bizCode})) : (approvalResult == 1 ? BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice5", new Object[]{submitUser.getName(), defineTitle + bizCode}) : BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice6", new Object[]{submitUser.getName(), defineTitle + bizCode}))) : (approvalResult == 1 ? BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice3", new Object[]{defineTitle + bizCode}) : BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice4", new Object[]{defineTitle + bizCode}));
        return title;
    }

    private static String createBillNoticeDefaultMailContent(String defineTitle, UserDO submitUser, String bizCode, String memo, String systemAddress) {
        String mailContent = StringUtils.hasText(memo) ? BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice7", new Object[]{defineTitle, submitUser != null ? submitUser.getName() : "", bizCode, memo, systemAddress}) : BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice8", new Object[]{defineTitle, submitUser != null ? submitUser.getName() : "", bizCode, systemAddress});
        return mailContent;
    }

    private String creatCarbonCopyDefaultTitle(Map<String, Object> param, String tenantName, String defineTitle, String bizCode) {
        String approveUserId = (String)param.get("approveUserId");
        String name = this.getOneUserData(tenantName, approveUserId).getName();
        return BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.carboncopynotice", new Object[]{name, defineTitle + bizCode});
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

    private String createParamJson(String bizDefine, String bizCode, String verifyCode, String defineTitle) {
        String pcBillUrl = "/#/sso/bill/" + bizDefine + "/" + bizCode + "/BROWSE/" + verifyCode;
        HashMap<String, String> json = new HashMap<String, String>();
        json.put("url", pcBillUrl);
        json.put("appName", "bill-app");
        json.put("appTitle", defineTitle);
        json.put("funcName", "VaBill");
        HashMap<String, String> appConfigJson = new HashMap<String, String>();
        appConfigJson.put("defineCode", bizDefine);
        appConfigJson.put("billCode", bizCode);
        appConfigJson.put("dataState", "BROWSE");
        appConfigJson.put("verifyCode", verifyCode);
        json.put("appConfig", JSONUtil.toJSONString(appConfigJson));
        return JSONUtil.toJSONString(json);
    }

    private static String getVisitUrl(String bizDefine, String bizCode, String verifyCode, String systemAddress, String tenantName) {
        String billUrl = "/gmt/bill/billApproval?fromType=1&defineCode=" + bizDefine + "&billCode=" + bizCode;
        if (StringUtils.hasText(verifyCode)) {
            billUrl = billUrl + "&verifyCode=" + verifyCode;
        }
        return systemAddress + "/anon/wxwork/login?tenantName=" + tenantName + "&hashMode=" + false + "&url=" + Base64Utils.encodeToUrlSafeString(billUrl.getBytes(StandardCharsets.UTF_8));
    }

    private static String createBillNoticeContent(String defineTitle, UserDO submitUser, String bizCode, String memo, String url) {
        String title = StringUtils.hasText(memo) ? BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice7", new Object[]{defineTitle, submitUser != null ? submitUser.getName() : "", bizCode, memo, url}) : BillCoreI18nUtil.getMessage("va.bill.core.msgnotice.approvalnotice8", new Object[]{defineTitle, submitUser != null ? submitUser.getName() : "", bizCode, url});
        return title;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.certification.dto.ThirdAuthorizeDTO
 *  com.jiuqi.nvwa.certification.extend.INvwaCertifyExtendService
 *  com.jiuqi.nvwa.intergration.sdk.cer.dto.NvwaTokenDTO
 */
package com.jiuqi.gcreport.oauth2.extend.dingtalk;

import com.jiuqi.gcreport.oauth2.extend.dingtalk.DingtalkCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.service.DingtalkService;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.util.DingtalkNvwaCertifyUtil;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.certification.dto.ThirdAuthorizeDTO;
import com.jiuqi.nvwa.certification.extend.INvwaCertifyExtendService;
import com.jiuqi.nvwa.intergration.sdk.cer.dto.NvwaTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DingtalkCertifyExtendService
implements INvwaCertifyExtendService {
    @Lazy
    @Autowired
    private DingtalkService dingtalkService;

    public int getOrder() {
        return 103;
    }

    public String getType() {
        return "DINGTALK_ENT_INTERNAL_APP_LOGON";
    }

    public String getTitle() {
        return "\u9489\u9489\u4f01\u4e1a\u5185\u90e8\u5e94\u7528\u5355\u70b9";
    }

    public boolean ssoPageCacheTokenId() {
        return false;
    }

    public boolean ssoPageLoginRedirectUrlMode(NvwaCertify nvwaCertify) {
        return true;
    }

    public boolean ssoPageFullInterception(NvwaCertify nvwaCertify) {
        DingtalkCertifyExtInfo ext = DingtalkNvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        return ext.getBlockLoginPage() != null ? ext.getBlockLoginPage() : true;
    }

    public ThirdAuthorizeDTO ssoPageThirdAuthorizeInfo(NvwaCertify nvwaCertify) {
        ThirdAuthorizeDTO thirdPageLoginDTO = new ThirdAuthorizeDTO();
        thirdPageLoginDTO.setAuthorizeAddress(this.dingtalkService.getLoginUrl(nvwaCertify));
        thirdPageLoginDTO.setRedirectUrlKey("n");
        return thirdPageLoginDTO;
    }

    public String ssoUnifiedLoginPage(NvwaCertify nvwaCertify) {
        DingtalkCertifyExtInfo ext = DingtalkNvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        if (ext.getBlockLoginPage() != null && ext.getBlockLoginPage().booleanValue() && StringUtils.hasText(ext.getThirdPartyLoginPage())) {
            return ext.getThirdPartyLoginPage();
        }
        return null;
    }

    public NvwaTokenDTO verifyTicket(NvwaCertify nvwaCertify, String ticketId, String redirectUri) {
        return null;
    }
}


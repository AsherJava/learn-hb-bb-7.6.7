/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.certification.dto.ThirdAuthorizeDTO
 *  com.jiuqi.nvwa.certification.extend.INvwaCertifyExtendService
 *  com.jiuqi.nvwa.intergration.sdk.cer.dto.NvwaTokenDTO
 *  com.jiuqi.nvwa.login.config.NvwaLoginProperties
 */
package com.jiuqi.gcreport.oauth2.extend;

import com.jiuqi.gcreport.oauth2.pojo.GcCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.service.GcOAuth2Service;
import com.jiuqi.gcreport.oauth2.util.Base64Utils;
import com.jiuqi.gcreport.oauth2.util.FingerprintUtil;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyUtil;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.certification.dto.ThirdAuthorizeDTO;
import com.jiuqi.nvwa.certification.extend.INvwaCertifyExtendService;
import com.jiuqi.nvwa.intergration.sdk.cer.dto.NvwaTokenDTO;
import com.jiuqi.nvwa.login.config.NvwaLoginProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class GcOAuth2CertifyExtendService
implements INvwaCertifyExtendService {
    @Lazy
    @Autowired
    private GcOAuth2Service gcOAuth2Service;
    @Lazy
    @Autowired
    private NvwaLoginProperties nvwaLoginProperties;

    public int getOrder() {
        return 102;
    }

    public String getType() {
        return "GC_OAuth2";
    }

    public String getTitle() {
        return "\u65e7\u7248\u5408\u5e76\u62a5\u8868OAuth2";
    }

    public boolean ssoPageCacheTokenId() {
        return false;
    }

    public boolean ssoPageLoginRedirectUrlMode(NvwaCertify nvwaCertify) {
        return true;
    }

    public boolean ssoPageFullInterception(NvwaCertify nvwaCertify) {
        GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        return ext.getBlockLoginPage() != null ? ext.getBlockLoginPage() : true;
    }

    public ThirdAuthorizeDTO ssoPageThirdAuthorizeInfo(NvwaCertify nvwaCertify) {
        ThirdAuthorizeDTO thirdPageLoginDTO = new ThirdAuthorizeDTO();
        GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        String authUrl = this.gcOAuth2Service.getAuthUrl(nvwaCertify);
        authUrl = this.appendAuthUrlDefaultStateFingerprint(authUrl, this.nvwaLoginProperties.getClientIpHeader(), ext);
        thirdPageLoginDTO.setAuthorizeAddress(authUrl);
        thirdPageLoginDTO.setRedirectUrlKey("n");
        return thirdPageLoginDTO;
    }

    public String ssoUnifiedLoginPage(NvwaCertify nvwaCertify) {
        GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nvwaCertify);
        if (ext.getBlockLoginPage() != null && ext.getBlockLoginPage().booleanValue()) {
            String authUrl = this.gcOAuth2Service.getAuthUrl(nvwaCertify);
            return this.appendAuthUrlDefaultStateFingerprint(authUrl, this.nvwaLoginProperties.getClientIpHeader(), ext);
        }
        return null;
    }

    public NvwaTokenDTO verifyTicket(NvwaCertify nvwaCertify, String ticketId, String redirectUri) {
        return null;
    }

    protected String appendAuthUrlDefaultStateFingerprint(String authUrl, String clientIpHeaderKey, GcCertifyExtInfo ext) {
        if (ext.getStrongSecurity() != null && ext.getStrongSecurity().booleanValue()) {
            String fingerPrint = "FP" + FingerprintUtil.genFp4State(this.nvwaLoginProperties.getClientIpHeader());
            String stateEncode = Base64Utils.base64Encode(fingerPrint);
            return authUrl + "&state=" + stateEncode;
        }
        return authUrl;
    }
}


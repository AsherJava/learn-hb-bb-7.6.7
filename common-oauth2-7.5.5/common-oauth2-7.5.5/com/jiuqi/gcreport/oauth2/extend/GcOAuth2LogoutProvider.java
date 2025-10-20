/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.certification.dao.INvwaCertifyDao
 *  com.jiuqi.nvwa.login.provider.NvwaLoginProvider
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.gcreport.oauth2.extend;

import com.jiuqi.gcreport.oauth2.cache.OAuth2CertifyServiceCache;
import com.jiuqi.gcreport.oauth2.pojo.GcCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.util.FingerprintUtil;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyUtil;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.certification.dao.INvwaCertifyDao;
import com.jiuqi.nvwa.login.provider.NvwaLoginProvider;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GcOAuth2LogoutProvider
implements NvwaLoginProvider {
    @Lazy
    @Autowired
    private INvwaCertifyDao nvwaCertifyDao;
    @Autowired
    private OAuth2CertifyServiceCache sessionFingerprintCache;
    @Value(value="${server.servlet.context-path:}")
    protected String contextPath;

    public void logout(R logoutRs) {
        GcCertifyExtInfo ext;
        NvwaCertify selectByCode;
        String username = ShiroUtil.getUser().getUsername();
        String fingerprint = FingerprintUtil.genFingerprint(username, this.contextPath);
        String cerCode = this.sessionFingerprintCache.getCerCodeByFingerprint(fingerprint);
        if (StringUtils.hasText(cerCode) && (selectByCode = this.nvwaCertifyDao.selectByCode(cerCode)) != null && ("GC_OAuth2".equals(selectByCode.getType()) || "GC_OAUTH2_V2".equals(selectByCode.getType())) && selectByCode.getExtraInfo() != null && StringUtils.hasText((ext = NvwaCertifyUtil.getExtInfoFromJson(selectByCode)).getLogoutPage())) {
            String ssoRedirectUrl = ext.getLogoutPage().startsWith("/") ? selectByCode.getUrl() + ext.getLogoutPage() : ext.getLogoutPage();
            logoutRs.put("ssoRedirectUrl", (Object)ssoRedirectUrl);
        }
    }
}


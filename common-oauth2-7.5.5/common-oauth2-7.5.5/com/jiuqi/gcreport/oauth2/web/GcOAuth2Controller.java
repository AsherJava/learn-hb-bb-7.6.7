/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.login.config.NvwaLoginProperties
 *  com.jiuqi.va.domain.common.R
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.oauth2.web;

import com.jiuqi.gcreport.oauth2.cache.OAuth2CertifyServiceCache;
import com.jiuqi.gcreport.oauth2.pojo.GcCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.service.GcNvwaCertifyService;
import com.jiuqi.gcreport.oauth2.service.GcOAuth2Service;
import com.jiuqi.gcreport.oauth2.service.GcOAuth2UserService;
import com.jiuqi.gcreport.oauth2.service.GcRenderErrorService;
import com.jiuqi.gcreport.oauth2.service.NvwaUiSchemeExtendService;
import com.jiuqi.gcreport.oauth2.util.Base64Utils;
import com.jiuqi.gcreport.oauth2.util.FingerprintUtil;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyThreadLocal;
import com.jiuqi.gcreport.oauth2.util.NvwaCertifyUtil;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.login.config.NvwaLoginProperties;
import com.jiuqi.va.domain.common.R;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcOAuth2Controller {
    private static final Logger logger = LoggerFactory.getLogger(GcOAuth2Controller.class);
    private static final String TODO_PREFIX = "TODO";
    private static final String AGILE_PREFIX = "AGILE";
    private static final String UICODE_PREFIX = "UICODE";
    private static final String CSCODE_PREFIX = "CSCODE";
    @Autowired
    private GcOAuth2Service oAuth2Service;
    @Autowired
    private GcOAuth2UserService oAuth2UserService;
    @Autowired
    private GcNvwaCertifyService nvwaCertifyService;
    @Lazy
    @Autowired
    private NvwaUiSchemeExtendService nvwaUiSchemeExtendService;
    @Autowired
    private GcRenderErrorService renderErrorService;
    @Autowired
    private OAuth2CertifyServiceCache oAuth2CerityServiceCache;
    @Value(value="${server.servlet.context-path:}")
    private String contextPath;
    @Lazy
    @Autowired
    private NvwaLoginProperties nvwaLoginProperties;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @GetMapping(value={"/api/oauth2/sso"})
    public void oauth2Sso(@RequestParam(value="code", required=false) String code, @RequestParam(value="state", required=false) String state, @RequestParam(value="uiCode", required=false) String uiCode, @RequestParam(value="csCode", required=false) String csCode, HttpServletResponse response) {
        if (StringUtils.hasText(code)) {
            this.oauth2Callback(code, state, response);
            return;
        }
        String authUrl = null;
        try {
            NvwaCertify nc = this.getNvwaCertify(csCode);
            if (null == nc) {
                logger.warn("\u8ba4\u8bc1\u670d\u52a1\u7ba1\u7406\u914d\u7f6e\u53ef\u80fd\u6709\u9519\u8bef\uff0c\u627e\u4e0d\u5230\u3010{}\u3011\u7c7b\u578b\u7684\u8ba4\u8bc1\u670d\u52a1", (Object)"\u65e7\u7248\u5408\u5e76\u62a5\u8868OAuth2");
                this.renderErrorService.renderError("<h3>\u7cfb\u7edf\u914d\u7f6e\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u5904\u7406\u3002</h3>", response);
                return;
            }
            NvwaCertifyThreadLocal.put(nc);
            GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nc);
            if (ObjectUtils.isEmpty(state) && ext.getStateIsUiCode().booleanValue()) {
                this.renderErrorService.renderError("<h3>\u60a8\u7684\u8d26\u53f7\u5df2\u767b\u5f55\u8d85\u65f6\u6216\u7cfb\u7edf\u5c4f\u853d\u4e86\u9ed8\u8ba4\u767b\u5f55\u9875\uff0c\u4e3a\u4e86\u8d26\u53f7\u5b89\u5168\u8bf7\u5173\u95ed\u6b64\u9875\u9762\uff0c\u91cd\u65b0\u767b\u5f55\u3002</h3>", response);
                return;
            }
            authUrl = this.oAuth2Service.getAuthUrl(nc);
            StringBuilder buf = new StringBuilder();
            if (StringUtils.hasText(uiCode)) {
                buf.append("&").append(UICODE_PREFIX).append(uiCode);
            } else if (StringUtils.hasText(state) && ext.getStateIsUiCode().booleanValue()) {
                buf.append("&").append(UICODE_PREFIX).append(state);
            }
            if (StringUtils.hasText(csCode)) {
                buf.append("&").append(CSCODE_PREFIX).append(csCode);
            }
            if (StringUtils.hasText(state) && !ext.getStateIsUiCode().booleanValue()) {
                buf.append("&").append(state);
            }
            if (ext.getStrongSecurity() != null && ext.getStrongSecurity().booleanValue()) {
                buf.append("&").append("FP").append(FingerprintUtil.genFp4State(this.nvwaLoginProperties.getClientIpHeader()));
            }
            if (!StringUtils.hasText(state = Base64Utils.base64Encode(buf.toString()))) {
                state = "DF" + System.currentTimeMillis();
            }
            authUrl = authUrl + "&state=" + state;
            logger.info("\u6253\u5370\u62fc\u63a5\u6388\u6743\u63a5\u53e3\u5730\u5740\uff1a{}", (Object)authUrl);
        }
        finally {
            NvwaCertifyThreadLocal.clear();
        }
        try {
            response.sendRedirect(authUrl);
        }
        catch (IOException e) {
            logger.error("\u8bbf\u95ee\u6388\u6743\u63a5\u53e3\u5730\u5740\u65f6\u51fa\u73b0\u9519\u8bef", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @GetMapping(value={"/api/oauth2/callback"})
    public void oauth2Callback(@RequestParam(value="code") String code, @RequestParam(value="state", required=false) String state, HttpServletResponse response) {
        try {
            NvwaCertify nc;
            String base64Decode;
            String[] split;
            String csCode = null;
            String uiCode = null;
            String todoStr = null;
            String agileStr = null;
            String fingerPrint = null;
            if (StringUtils.hasText(state) && (split = (base64Decode = Base64Utils.base64Decode(state)).split("&")).length > 0) {
                for (String s : split) {
                    if (!StringUtils.hasText(s)) continue;
                    if (s.startsWith(CSCODE_PREFIX)) {
                        csCode = s.substring(CSCODE_PREFIX.length());
                        continue;
                    }
                    if (s.startsWith(UICODE_PREFIX)) {
                        uiCode = s.substring(UICODE_PREFIX.length());
                        continue;
                    }
                    if (s.startsWith(TODO_PREFIX)) {
                        todoStr = s.substring(TODO_PREFIX.length());
                        continue;
                    }
                    if (s.startsWith(AGILE_PREFIX)) {
                        agileStr = s.substring(AGILE_PREFIX.length());
                        continue;
                    }
                    if (!s.startsWith("FP")) continue;
                    fingerPrint = s.substring("FP".length());
                }
            }
            if (null == (nc = this.getNvwaCertify(csCode))) {
                logger.warn("\u8ba4\u8bc1\u670d\u52a1\u7ba1\u7406\u914d\u7f6e\u53ef\u80fd\u6709\u9519\u8bef\uff0c\u627e\u4e0d\u5230\u3010{}\u3011\u7c7b\u578b\u7684\u8ba4\u8bc1\u670d\u52a1", (Object)"\u65e7\u7248\u5408\u5e76\u62a5\u8868OAuth2");
                this.renderErrorService.renderError("<h3>\u7cfb\u7edf\u914d\u7f6e\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u5904\u7406\u3002</h3>", response);
                return;
            }
            NvwaCertifyThreadLocal.put(nc);
            logger.info("\u5f53\u524d\u7ebf\u7a0b\u7ed1\u5b9a\u7684\u8ba4\u8bc1\u670d\u52a1CODE\u4e3a{}", (Object)nc.getCode());
            GcCertifyExtInfo ext = NvwaCertifyUtil.getExtInfoFromJson(nc);
            if (ext.getStrongSecurity() != null && ext.getStrongSecurity().booleanValue()) {
                boolean pass = true;
                if (!StringUtils.hasText(state)) {
                    pass = false;
                } else if (fingerPrint == null || !FingerprintUtil.validateFp4State(fingerPrint, this.nvwaLoginProperties.getClientIpHeader())) {
                    pass = false;
                }
                if (!pass) {
                    logger.warn("\u5df2\u542f\u7528\u5f3a\u5b89\u5168\u6a21\u5f0f\uff0c\u8bf7\u6c42\u4e2d\u4e0d\u5305\u542bstate\u53c2\u6570\u503c\u6216\u6d4f\u89c8\u5668\u6307\u7eb9\u6821\u9a8c\u5931\u8d25\uff0cstate={},fingerPrint={}", (Object)state, (Object)fingerPrint);
                    this.renderErrorService.renderError("\u975e\u6cd5\u8bf7\u6c42", response);
                    return;
                }
            }
            String accessToken = this.oAuth2Service.getAccessToken(code);
            String username = this.oAuth2Service.getLoginUserName(accessToken);
            if (ext.getStateIsUiCode().booleanValue() && !this.nvwaUiSchemeExtendService.hasUiSchemeAuthority(username, uiCode)) {
                logger.info("\u7528\u6237[{}]\u6ca1\u6709\u754c\u9762\u65b9\u6848[{}]\u7684\u6743\u9650\uff0c\u5df2\u505a\u63d0\u793a\u3002", (Object)username, (Object)uiCode);
                this.renderErrorService.renderError("<h3>" + ext.getNoUiSchemePermissionsTips() + "</h3>", response);
                return;
            }
            R r = this.oAuth2UserService.simulationLogin(username, uiCode);
            String ssoToken = (String)r.get((Object)"token");
            if (!StringUtils.hasLength(ssoToken)) {
                logger.warn("\u7528\u6237{}\u5728\u4e1a\u52a1\u7cfb\u7edf\u6a21\u62df\u767b\u5f55\u5931\u8d25\uff0c\u8be6\u60c5\uff1a{}", (Object)username, (Object)r.getMsg());
                this.renderErrorService.renderError("<h3>\u5982\u679c\u60a8\u770b\u5230\u8fd9\u4e2a\u63d0\u793a\uff0c\u8bf4\u660e\u60a8\u7684\u8d26\u53f7\uff08" + username + "\uff09\u5728\u4e1a\u52a1\u7cfb\u7edf\u4e2d\u53ef\u80fd\u4e0d\u5b58\u5728\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u5904\u7406\u3002</h3>", response);
                return;
            }
            this.oAuth2CerityServiceCache.putCerCode(FingerprintUtil.genFingerprint(username, this.contextPath), nc.getCode());
            if (StringUtils.hasText(todoStr)) {
                this.oAuth2Service.todoRedirectDataentry(username, todoStr, response);
                return;
            }
            if (StringUtils.hasText(agileStr)) {
                this.oAuth2Service.agileRedirect(username, agileStr, response);
                return;
            }
            this.oAuth2Service.loginSuccessRedirect(username, uiCode, response);
        }
        catch (Exception e) {
            logger.warn(e.getMessage(), e);
            this.renderErrorService.renderError(e.getMessage(), response);
        }
        finally {
            NvwaCertifyThreadLocal.clear();
        }
    }

    private NvwaCertify getNvwaCertify(String csCode) {
        NvwaCertify nc = StringUtils.hasText(csCode) ? this.nvwaCertifyService.getNvwaCertifyByCode(csCode) : this.nvwaCertifyService.getNvwaCertifyByTypeFirstIfMoreUseCode("GC_OAuth2", null);
        return nc;
    }
}


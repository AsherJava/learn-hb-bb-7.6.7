/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.va.domain.common.R
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.oauth2.extend.dingtalk;

import com.jiuqi.gcreport.oauth2.extend.dingtalk.DingtalkCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.pojo.DingtalkConfigCombine;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.service.DingtalkService;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.util.DingtalkNvwaCertifyUtil;
import com.jiuqi.gcreport.oauth2.extend.dingtalk.util.DingtalkThreadLocal;
import com.jiuqi.gcreport.oauth2.pojo.GcSsoBuildDTO;
import com.jiuqi.gcreport.oauth2.service.GcNvwaCertifyService;
import com.jiuqi.gcreport.oauth2.service.Oauth2SsoLocationService;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.va.domain.common.R;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DingtalkLoginController {
    private static final Logger logger = LoggerFactory.getLogger(DingtalkLoginController.class);
    @Autowired
    private DingtalkService dingtalkService;
    @Autowired
    private GcNvwaCertifyService gcNvwaCertifyService;
    @Autowired
    private Oauth2SsoLocationService oauth2SsoLocationService;

    @GetMapping(value={"/anon/gcreport/dingtalk/auth"})
    public void auth(HttpServletResponse response) {
        NvwaCertify nvwaCertifyByType = this.gcNvwaCertifyService.getGcOAuth2TypeNvwaCertify("DINGTALK_ENT_INTERNAL_APP_LOGON");
        String loginUrl = this.dingtalkService.getLoginUrl(nvwaCertifyByType);
        logger.info("\u9489\u9489\u5355\u70b9\u5373\u5c06\u8df3\u8f6c\u5730\u5740{}", (Object)loginUrl);
        try {
            response.sendRedirect(loginUrl);
        }
        catch (IOException e) {
            logger.warn("\u9489\u9489\u5355\u70b9\u91cd\u5b9a\u5411\u5931\u8d25", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @GetMapping(value={"/anon/gcreport/dingtalk/callback"})
    public void callback(@RequestParam(required=false) String authCode, @RequestParam(required=false) String state, HttpServletResponse response) {
        logger.info("\u9489\u9489\u5355\u70b9\u6536\u5230\u56de\u8c03\u8bf7\u6c42");
        try {
            NvwaCertify nvwaCertifyByType = this.gcNvwaCertifyService.getGcOAuth2TypeNvwaCertify("DINGTALK_ENT_INTERNAL_APP_LOGON");
            DingtalkCertifyExtInfo extInfoFromJson = DingtalkNvwaCertifyUtil.getExtInfoFromJson(nvwaCertifyByType);
            DingtalkConfigCombine combine = new DingtalkConfigCombine(nvwaCertifyByType, extInfoFromJson);
            DingtalkThreadLocal.put(combine);
            String accessToken = this.dingtalkService.getAccessToken(authCode);
            String userInfoJson = this.dingtalkService.getUserInfo(accessToken);
            String username = this.dingtalkService.getRelatedPeopleUsername(userInfoJson);
            R innerLogin = this.dingtalkService.innerLogin(username);
            String ssoToken = (String)innerLogin.get((Object)"token");
            if (!StringUtils.hasLength(ssoToken)) {
                logger.warn("\u7528\u6237{}\u5728\u4e1a\u52a1\u7cfb\u7edf\u6a21\u62df\u767b\u5f55\u5931\u8d25\uff0c\u8be6\u60c5\uff1a{}", (Object)username, (Object)innerLogin.getMsg());
                this.printErrorOnBroswer("<h3>\u5982\u679c\u60a8\u770b\u5230\u8fd9\u4e2a\u63d0\u793a\uff0c\u8bf4\u660e\u60a8\u7684\u8d26\u53f7\uff08" + username + "\uff09\u5728\u4e1a\u52a1\u7cfb\u7edf\u4e2d\u53ef\u80fd\u4e0d\u5b58\u5728\uff0c\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u5904\u7406\u3002</h3>", response);
                return;
            }
            this.loginSuccessRedirect(username, response);
        }
        catch (Exception e) {
            logger.warn("\u9489\u9489-\u5355\u70b9\u767b\u5f55\u51fa\u73b0\u5f02\u5e38", e);
            this.printErrorOnBroswer("\u5355\u70b9\u767b\u5f55\u51fa\u73b0\u5f02\u5e38,\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u5904\u7406\uff1a" + e.getMessage(), response);
        }
        finally {
            DingtalkThreadLocal.clear();
        }
    }

    private void printErrorOnBroswer(String error, HttpServletResponse response) {
        String unionUserLoginFailTips = DingtalkThreadLocal.getExtInfo().getUnionUserLoginFailTips();
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            if (StringUtils.hasLength(unionUserLoginFailTips)) {
                writer.println(unionUserLoginFailTips);
            } else {
                writer.println(error);
            }
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    private void loginSuccessRedirect(String username, HttpServletResponse response) {
        DingtalkConfigCombine combine = DingtalkThreadLocal.get();
        DingtalkCertifyExtInfo extInfo = combine.getExtInfo();
        GcSsoBuildDTO dto = new GcSsoBuildDTO();
        dto.setFrontAddress(this.handleFrontendUrl(combine));
        dto.setUserName(username);
        dto.setUserName(username);
        dto.setJumpType(extInfo.getJumpType());
        dto.setAppName(extInfo.getAppName());
        dto.setScope(extInfo.getScope());
        dto.setExpose(extInfo.getExpose());
        dto.setRouter(extInfo.getRouter());
        dto.setExtInfo(extInfo.getExtInfo());
        dto.setTitle(extInfo.getTitle());
        dto.setScheme(extInfo.getScheme());
        dto.setAppConfig(extInfo.getAppConfig());
        dto.setOpenConfig(extInfo.getOpenConfig());
        String curSsoLocation = this.oauth2SsoLocationService.buildCurSsoLocation(dto);
        logger.info("\u7528\u6237{}\u6b63\u5728\u8df3\u8f6c\u5730\u5740{}", (Object)username, (Object)curSsoLocation);
        try {
            response.sendRedirect(curSsoLocation);
        }
        catch (Exception e) {
            logger.warn("\u9489\u9489\u5355\u70b9\u91cd\u5b9a\u5411\u51fa\u73b0\u5f02\u5e38", e);
        }
    }

    private String handleFrontendUrl(DingtalkConfigCombine combine) {
        String frontUrl = combine.getNvwaCertify().getFrontendURL();
        DingtalkCertifyExtInfo extInfo = combine.getExtInfo();
        if (!extInfo.getHashModeRouter().booleanValue()) {
            return frontUrl;
        }
        if (ObjectUtils.isEmpty((Object)frontUrl)) {
            logger.warn("\u5f53\u524d\u524d\u7aef\u5730\u5740\u672a\u8bbe\u7f6e\uff0c\u5c06\u4f5c\u4e3awar\u90e8\u7f72\u65b9\u5f0f\u76f8\u5bf9\u8def\u5f84\u751f\u6210\u767b\u5f55\u5730\u5740");
            return "/#/";
        }
        frontUrl = frontUrl.indexOf("#") != -1 ? frontUrl.substring(0, frontUrl.indexOf("#") + 1) + "/" : (frontUrl.endsWith("/") ? frontUrl + "#/" : frontUrl + "/#/");
        logger.debug("\u6253\u5370\u5904\u7406\u8fc7\u7684\u524d\u7aef\u5730\u5740: {}", (Object)frontUrl);
        return frontUrl;
    }
}


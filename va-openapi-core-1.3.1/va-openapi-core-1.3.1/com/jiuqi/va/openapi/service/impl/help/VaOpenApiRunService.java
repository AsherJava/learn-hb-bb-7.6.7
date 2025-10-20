/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.openapi.OpenApiGetTokenDTO
 *  com.jiuqi.va.domain.openapi.OpenApiRegisterDO
 *  com.jiuqi.va.domain.openapi.OpenApiValidateTokenDTO
 *  com.jiuqi.va.feign.extend.OpenApiCheckParam
 *  com.jiuqi.va.feign.extend.VaOpenApiInterceptor
 *  io.jsonwebtoken.Claims
 *  io.jsonwebtoken.ExpiredJwtException
 *  org.apache.shiro.codec.Base64
 */
package com.jiuqi.va.openapi.service.impl.help;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.openapi.OpenApiGetTokenDTO;
import com.jiuqi.va.domain.openapi.OpenApiRegisterDO;
import com.jiuqi.va.domain.openapi.OpenApiValidateTokenDTO;
import com.jiuqi.va.feign.extend.OpenApiCheckParam;
import com.jiuqi.va.feign.extend.VaOpenApiInterceptor;
import com.jiuqi.va.openapi.domain.OpenApiAuthDO;
import com.jiuqi.va.openapi.domain.OpenApiAuthDTO;
import com.jiuqi.va.openapi.service.VaOpenApiService;
import com.jiuqi.va.openapi.service.impl.help.VaOpenApiCacheService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.List;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VaOpenApiRunService {
    private static Logger logger = LoggerFactory.getLogger(VaOpenApiRunService.class);
    @Autowired
    private VaOpenApiCacheService openApiCacheService;
    @Autowired
    private VaOpenApiService openApiService;
    @Autowired(required=false)
    private List<VaOpenApiInterceptor> interceptors;
    private static String rsParamEmpty = "\u7f3a\u5c11\u5fc5\u4f20\u53c2\u6570";
    private static String rsParamIllegal = "\u975e\u6cd5\u7684\u53c2\u6570";
    private static String rsAuthInvalid = "\u65e0\u6548\u7684\u6388\u6743";

    public R getToken(OpenApiGetTokenDTO openApi) {
        String openid = openApi.getOpenid();
        if (!StringUtils.hasText(openid)) {
            return R.error((int)101, (String)rsParamEmpty);
        }
        String decodeOpenid = null;
        try {
            decodeOpenid = Base64.decodeToString((String)openid.substring(0, openid.length() - 7));
        }
        catch (Exception e) {
            return R.error((int)102, (String)rsParamIllegal);
        }
        String[] strs = decodeOpenid.split("\\#");
        if (strs.length < 3) {
            return R.error((int)102, (String)rsParamIllegal);
        }
        OpenApiAuthDTO param = new OpenApiAuthDTO();
        param.setTenantName(strs[0]);
        param.setClientid(strs[1]);
        param.setRandomcode(strs[2]);
        param.setOpenid(openid);
        R ckRs = this.checkBefore(param);
        if (ckRs.getCode() != 0) {
            return ckRs;
        }
        OpenApiAuthDO oaado = this.openApiCacheService.get(param);
        if (oaado == null) {
            return R.error((int)103, (String)rsAuthInvalid);
        }
        if (oaado.getStopflag() == 1) {
            return R.error((int)104, (String)"\u6388\u6743\u5df2\u88ab\u505c\u7528");
        }
        if (!StringUtils.hasText(oaado.getAuthdata())) {
            return R.error((int)105, (String)"\u6388\u6743\u5c1a\u672a\u914d\u7f6e\u63a5\u53e3\u8303\u56f4");
        }
        if (StringUtils.hasText(openApi.getUsername())) {
            oaado.addExtInfo("username", openApi.getUsername());
        }
        R rs = R.ok();
        rs.put("token", (Object)this.openApiService.createJWT(oaado));
        return rs;
    }

    public R validateToken(OpenApiValidateTokenDTO openApi) {
        String token = openApi.getToken();
        String apiName = openApi.getApiName();
        if (!StringUtils.hasText(token) || !StringUtils.hasText(apiName)) {
            return R.error((int)201, (String)rsParamEmpty);
        }
        Claims claims = null;
        try {
            claims = this.openApiService.parseJWT(token);
        }
        catch (ExpiredJwtException expiredException) {
            return R.error((int)202, (String)"token\u5df2\u8fc7\u671f");
        }
        String subject = claims.getSubject();
        boolean isFromEsp = subject.endsWith("#@fromEsp");
        if (isFromEsp) {
            subject = subject.substring(0, subject.length() - 9);
        }
        String[] strs = subject.split("\\#");
        if (!isFromEsp || apiName.startsWith("ESP#")) {
            if (strs.length < 2) {
                return R.error((int)203, (String)"\u5bf9\u5f53\u524d\u63a5\u53e3\u65e0\u4f7f\u7528\u6743\u9650");
            }
            String registers = Base64.decodeToString((String)strs[1]);
            List arList = JSONUtil.parseArray((String)registers, OpenApiRegisterDO.class);
            boolean flag = false;
            for (OpenApiRegisterDO openApiRegisterDO : arList) {
                if (!openApiRegisterDO.getName().equalsIgnoreCase(apiName)) continue;
                flag = true;
                break;
            }
            if (!flag) {
                return R.error((int)203, (String)"\u5bf9\u5f53\u524d\u63a5\u53e3\u65e0\u4f7f\u7528\u6743\u9650");
            }
        }
        String username = "";
        if (strs.length > 2) {
            username = Base64.decodeToString((String)strs[2]);
        }
        if (openApi.getUserName() != null && !openApi.getUserName().equals(username)) {
            return R.error((int)204, (String)"\u65e0\u6548\u7684\u8ba4\u8bc1\u884c\u4e3a");
        }
        String openid = strs[0];
        String decodeOpenid = Base64.decodeToString((String)openid.substring(0, openid.length() - 7));
        strs = decodeOpenid.split("\\#");
        OpenApiAuthDTO param = new OpenApiAuthDTO();
        param.setTenantName(strs[0]);
        param.setClientid(strs[1]);
        param.setRandomcode(strs[2]);
        param.setOpenid(openid);
        OpenApiAuthDO oaado = this.openApiCacheService.get(param);
        if (oaado == null) {
            return R.error((int)103, (String)rsAuthInvalid);
        }
        if (isFromEsp && apiName.startsWith("ESP#")) {
            param.setRandomcode(null);
            param.setOpenid(null);
            R ckRs = this.checkBefore(param);
            if (ckRs.getCode() != 0) {
                return ckRs;
            }
        }
        R rs = R.ok((String)"\u9a8c\u8bc1\u901a\u8fc7");
        rs.put("tenantName", (Object)strs[0]);
        rs.put("clientid", (Object)strs[1]);
        return rs;
    }

    private R checkBefore(OpenApiAuthDTO param) {
        if (this.interceptors == null || this.interceptors.isEmpty()) {
            return R.ok();
        }
        OpenApiCheckParam checkParam = new OpenApiCheckParam();
        checkParam.setTenantName(param.getTenantName());
        checkParam.setClientId(param.getClientid());
        R rs = null;
        for (VaOpenApiInterceptor inter : this.interceptors) {
            try {
                rs = inter.execute(checkParam);
                if (rs.getCode() == 0) continue;
                return rs;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return R.ok();
    }
}


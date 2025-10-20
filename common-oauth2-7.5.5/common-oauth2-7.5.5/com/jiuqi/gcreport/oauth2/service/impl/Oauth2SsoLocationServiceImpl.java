/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket
 *  com.jiuqi.nvwa.ticket.service.TicketService
 */
package com.jiuqi.gcreport.oauth2.service.impl;

import com.jiuqi.gcreport.oauth2.pojo.GcSsoBuildDTO;
import com.jiuqi.gcreport.oauth2.service.Oauth2SsoLocationService;
import com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket;
import com.jiuqi.nvwa.ticket.service.TicketService;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Oauth2SsoLocationServiceImpl
implements Oauth2SsoLocationService {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2SsoLocationServiceImpl.class);
    private static final List<String> SUPPORT_NVWA_GENURL_PARAMS = Arrays.asList("jumpType", "name", "scope", "expose", "router", "loginUnit", "pv", "os-jump", "open-config", "extInfo", "uiCode", "scheme", "title");
    @Lazy
    @Autowired
    private TicketService ticketService;

    @Override
    public String buildCurSsoLocation(GcSsoBuildDTO buildDto) {
        StringBuilder build = new StringBuilder();
        try {
            String encodedText;
            byte[] textByte;
            if (StringUtils.hasLength(buildDto.getFrontAddress())) {
                String frontAddress = buildDto.getFrontAddress();
                if (frontAddress.endsWith("/")) {
                    build.append(frontAddress.substring(0, frontAddress.length() - 1));
                } else {
                    build.append(frontAddress);
                }
            }
            build.append("/sso?jumpType=").append(buildDto.getJumpType());
            if (StringUtils.hasLength(buildDto.getAppName())) {
                build.append("&name=").append(buildDto.getAppName());
            }
            if (StringUtils.hasLength(buildDto.getScope())) {
                build.append("&scope=").append(buildDto.getScope());
            }
            if (StringUtils.hasLength(buildDto.getExpose())) {
                build.append("&expose=").append(buildDto.getExpose());
            }
            if (StringUtils.hasLength(buildDto.getRouter())) {
                build.append("&router=").append(buildDto.getRouter());
            }
            if (StringUtils.hasLength(buildDto.getLoginUnit())) {
                build.append("&loginUnit=").append(buildDto.getLoginUnit());
            }
            if (StringUtils.hasLength(buildDto.getLoginDate())) {
                build.append("&loginDate=").append(buildDto.getLoginDate());
            }
            build.append("&pv=").append(buildDto.getPv());
            build.append("&tokenId=");
            if (buildDto.isAddTokenId()) {
                Ticket apply = this.ticketService.apply(null, buildDto.getUserName(), null);
                build.append(apply.getId());
            } else {
                build.append("${tokenId}");
            }
            Base64.Encoder encoder = Base64.getEncoder();
            if (StringUtils.hasLength(buildDto.getAppConfig())) {
                textByte = buildDto.getAppConfig().getBytes("UTF-8");
                encodedText = encoder.encodeToString(textByte);
                build.append("&os-jump=").append(encodedText);
            }
            if (StringUtils.hasLength(buildDto.getOpenConfig())) {
                textByte = buildDto.getOpenConfig().getBytes("UTF-8");
                encodedText = encoder.encodeToString(textByte);
                build.append("&open-config=").append(encodedText);
            }
            if (StringUtils.hasLength(buildDto.getExtInfo())) {
                textByte = buildDto.getExtInfo().getBytes("UTF-8");
                encodedText = encoder.encodeToString(textByte);
                build.append("&extInfo=").append(encodedText);
            }
            if (StringUtils.hasLength(buildDto.getUiCode())) {
                build.append("&uiCode=").append(buildDto.getUiCode());
            }
            if (StringUtils.hasLength(buildDto.getScheme())) {
                build.append("&scheme=").append(buildDto.getScheme());
            }
            if (StringUtils.hasLength(buildDto.getTitle())) {
                build.append("&title=").append(buildDto.getTitle());
            }
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u751f\u6210\u5355\u70b9\u53c2\u6570\u62a5\u9519", e);
        }
        return build.toString();
    }

    @Override
    public String buildCurSsoLocationFromGenUrl(String url, String frontUrl, String username) {
        String[] paramSplit;
        logger.info("\u8f6c\u6362\u751f\u6210\u94fe\u63a5\u5165\u53c2\uff1aurl={}, frontUrl={}, username={}", url, frontUrl, username);
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
        int indexOfQuestionMark = -1;
        indexOfQuestionMark = url.indexOf(63);
        if (indexOfQuestionMark != -1) {
            url = url.substring(indexOfQuestionMark + 1);
        }
        if ((paramSplit = url.split("&")).length > 0) {
            String temp = null;
            for (int i = 0; i < paramSplit.length; ++i) {
                temp = paramSplit[i];
                String[] split = temp.split("=");
                paramMap.put(split[0], split[1]);
            }
        }
        GcSsoBuildDTO ssoBuildDto = this.setGcSsoBuildDtoVal(paramMap, frontUrl, username);
        return this.buildCurSsoLocation(ssoBuildDto);
    }

    private GcSsoBuildDTO setGcSsoBuildDtoVal(Map<String, String> paramMap, String frontUrl, String username) {
        GcSsoBuildDTO ssoBuildDto = new GcSsoBuildDTO();
        for (String key : SUPPORT_NVWA_GENURL_PARAMS) {
            String value;
            if (!paramMap.containsKey(key) || (value = paramMap.get(key)) == null) continue;
            if (key.equals("jumpType")) {
                ssoBuildDto.setJumpType(value);
                continue;
            }
            if (key.equals("name")) {
                ssoBuildDto.setAppName(value);
                continue;
            }
            if (key.equals("scope")) {
                ssoBuildDto.setScope(value);
                continue;
            }
            if (key.equals("expose")) {
                ssoBuildDto.setExpose(value);
                continue;
            }
            if (key.equals("router")) {
                ssoBuildDto.setRouter(value);
                continue;
            }
            if (key.equals("loginUnit")) {
                ssoBuildDto.setLoginUnit(value);
                continue;
            }
            if (key.equals("os-jump")) {
                ssoBuildDto.setAppConfig(value);
                continue;
            }
            if (key.equals("open-config")) {
                ssoBuildDto.setOpenConfig(value);
                continue;
            }
            if (key.equals("extInfo")) {
                ssoBuildDto.setExtInfo(value);
                continue;
            }
            if (key.equals("uiCode")) {
                ssoBuildDto.setUiCode(value);
                continue;
            }
            if (key.equals("scheme")) {
                ssoBuildDto.setScheme(value);
                continue;
            }
            if (!key.equals("title")) continue;
            ssoBuildDto.setTitle(value);
        }
        if (!StringUtils.hasLength(ssoBuildDto.getJumpType())) {
            ssoBuildDto.setJumpType("go");
        }
        if (!StringUtils.hasLength(ssoBuildDto.getPv())) {
            ssoBuildDto.setPv("nvwa-cer");
        }
        ssoBuildDto.setFrontAddress(frontUrl);
        ssoBuildDto.setUserName(username);
        return ssoBuildDto;
    }
}


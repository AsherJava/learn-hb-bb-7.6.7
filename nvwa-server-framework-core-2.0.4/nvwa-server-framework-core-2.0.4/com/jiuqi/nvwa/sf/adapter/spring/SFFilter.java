/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.springadapter.filter.NvwaFilterChain
 *  com.jiuqi.nvwa.springadapter.filter.NvwaGenericFilterBean
 *  org.apache.commons.collections4.CollectionUtils
 *  org.springframework.http.HttpStatus
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.impl.SFRemoteResourceManage;
import com.jiuqi.nvwa.sf.adapter.spring.login.SFLoginCheckManage;
import com.jiuqi.nvwa.springadapter.filter.NvwaFilterChain;
import com.jiuqi.nvwa.springadapter.filter.NvwaGenericFilterBean;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SFFilter
extends NvwaGenericFilterBean {
    @Autowired
    private SFRemoteResourceManage sfRemoteResourceManage;
    @Autowired
    private SFLoginCheckManage sfLoginCacheManage;

    /*
     * Enabled aggressive block sorting
     */
    public void doFilter(NvwaFilterChain nvwaFilterChain) throws Exception {
        Framework fw = Framework.getInstance();
        String uri = Paths.get(nvwaFilterChain.getRequestURI().replaceAll("/+", "/"), new String[0]).toString().replace("\\", "/");
        String contextPath = nvwaFilterChain.getContextPath();
        if ((contextPath + "/sf").equalsIgnoreCase(uri)) {
            this.doSFDirect(nvwaFilterChain);
            return;
        }
        if (uri.startsWith(contextPath + "/anon/sf/api/")) {
            if (this.sfRemoteResourceManage.isAllServiceManagerResourceReady()) {
                this.writeResp(nvwaFilterChain, HttpStatus.INTERNAL_SERVER_ERROR.value(), "\u670d\u52a1\u8fd0\u884c\u6b63\u5e38\uff0c\u8bf7\u8fdb\u5165\u7cfb\u7edf\u540e\u8bbf\u95ee\u201d\u670d\u52a1\u7ba1\u7406\u201d\u529f\u80fd\u83dc\u5355");
                return;
            }
            if (!uri.equalsIgnoreCase(contextPath + "/anon/sf/api/login") && !this.sfLoginCacheManage.checkLogin(nvwaFilterChain)) {
                this.writeResp(nvwaFilterChain, HttpStatus.UNAUTHORIZED.value(), "\u5f53\u524d\u8bf7\u6c42\u672a\u767b\u5f55");
                return;
            }
        }
        if (Framework.useAuthzCenterMode()) {
            if (uri.equalsIgnoreCase(contextPath) || uri.equalsIgnoreCase(contextPath + "/") || uri.startsWith(contextPath + "/config/os.config.js") || uri.startsWith(contextPath + "/config") || uri.startsWith(contextPath + "/login/") || uri.startsWith(contextPath + "/static/") || uri.startsWith(contextPath + "/js/")) {
                nvwaFilterChain.doFilter();
                return;
            }
            if (uri.contains("/login") || uri.contains("/getLoginContext") || uri.contains("/sf") || uri.contains("/serverframework")) {
                nvwaFilterChain.doFilter();
                return;
            }
            if (uri.startsWith(contextPath + "/nvwa-remote/")) {
                nvwaFilterChain.doFilter();
                return;
            }
            if (!fw.isKmsSystemPaused() && !fw.isLicenceSystemPaused()) {
                nvwaFilterChain.doFilter();
                return;
            }
            long availableTime = 21600000L;
            if (fw.isLicenceSystemPaused()) {
                availableTime = 15000L;
            }
            StringBuffer msgTemp = new StringBuffer();
            if (fw.isKmsSystemPaused()) {
                msgTemp.append(fw.getKmsSystemPausedReason()).append(" ");
            }
            if (fw.isLicenceSystemPaused()) {
                msgTemp.append(fw.getLicenceSystemPausedReason());
            }
            if (System.currentTimeMillis() - fw.getSystemPausedTimestamp() <= availableTime) {
                nvwaFilterChain.doFilter();
                return;
            }
            ArrayList<String> buttonList = new ArrayList<String>(1);
            buttonList.add("close");
            this.writeResp(nvwaFilterChain, HttpStatus.INTERNAL_SERVER_ERROR.value(), msgTemp.toString(), buttonList);
        } else {
            if (fw.isModuleValidate() && fw.isLicenceValidate()) {
                nvwaFilterChain.doFilter();
                return;
            }
            if (uri.startsWith(contextPath + "/console")) {
                nvwaFilterChain.sendResponseRedirect(contextPath + "/sf");
            }
            if (uri.startsWith(contextPath + "/anon/framework/api/encrypt")) {
                nvwaFilterChain.doFilter();
                return;
            }
        }
        if (uri.equalsIgnoreCase(contextPath) || uri.equalsIgnoreCase(contextPath + "/") || uri.startsWith(contextPath + "/config/os.config.js") || uri.startsWith(contextPath + "/login/") || uri.startsWith(contextPath + "/static/") || uri.startsWith(contextPath + "/js/")) {
            nvwaFilterChain.doFilter();
            return;
        }
        if (uri.contains("/login") || uri.contains("/getLoginContext") || uri.contains("/sf") || uri.contains("/serverframework")) {
            nvwaFilterChain.doFilter();
            return;
        }
        if (uri.startsWith(contextPath + "/nvwa-remote/")) {
            nvwaFilterChain.doFilter();
            return;
        }
    }

    private void doSFDirect(NvwaFilterChain nvwaFilterChain) throws IOException {
        String contextPath = nvwaFilterChain.getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath = contextPath + "/";
        }
        if (!this.sfLoginCacheManage.checkLogin(nvwaFilterChain)) {
            nvwaFilterChain.sendResponseRedirect(contextPath + "anon/serverframework/index.html?page=login&timeStamp=" + Calendar.getInstance().getTimeInMillis());
        } else {
            nvwaFilterChain.sendResponseRedirect(contextPath + "anon/serverframework/index.html?timeStamp=" + Calendar.getInstance().getTimeInMillis());
        }
    }

    private void writeResp(NvwaFilterChain nvwaFilterChain, int status, String msg) throws Exception {
        this.writeResp(nvwaFilterChain, status, msg, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeResp(NvwaFilterChain nvwaFilterChain, int status, String msg, List<String> buttonStyleList) throws Exception {
        nvwaFilterChain.setResponseStatus(status);
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"code\":").append(status).append(",\"message\":\"").append(msg).append("\"");
        if (CollectionUtils.isNotEmpty(buttonStyleList)) {
            StringBuilder sb = new StringBuilder();
            for (String str : buttonStyleList) {
                sb.append("\"");
                sb.append(str);
                sb.append("\",");
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
            buffer.append(",\"button\":[").append((CharSequence)sb).append("]");
        }
        buffer.append("}");
        nvwaFilterChain.setResponseCharacterEncoding("UTF-8");
        nvwaFilterChain.setResponseContentType("application/json");
        OutputStream outputStream = null;
        try {
            outputStream = nvwaFilterChain.getOutputStream();
            outputStream.write(buffer.toString().getBytes("utf-8"));
            outputStream.flush();
        }
        finally {
            if (null != outputStream) {
                outputStream.close();
            }
        }
    }

    public void destroy() {
    }
}


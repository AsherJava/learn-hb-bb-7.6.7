/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.etl.common;

import com.jiuqi.nr.etl.common.ServeType;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import org.springframework.util.StringUtils;

public class EtlServeEntity {
    private String protocol;
    private String url;
    private String userName;
    private String pwd;
    private ServeType type;
    private String createUser;
    private Instant createTime;
    private static final String UNAME = ":name:";
    private static final String UPWD = ":password:";
    private static final String UTYPE = ":type:";
    private static final String CHAR = "@:@";

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public ServeType getType() {
        return this.type;
    }

    public void setType(ServeType type) {
        this.type = type;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return this.protocol + "://" + this.url;
    }

    public String toString() {
        return this.getAddress() + CHAR + UNAME + this.userName + UPWD + this.pwd + UTYPE + (this.type == null ? "0" : Integer.valueOf(this.type.getValue()));
    }

    public static EtlServeEntity valueOf(String value) {
        String[] serverInfos;
        if (StringUtils.hasLength(value) && (serverInfos = value.split(CHAR)).length == 2) {
            URL url;
            String address = serverInfos[0];
            EtlServeEntity serve = new EtlServeEntity();
            try {
                url = new URL(address);
            }
            catch (MalformedURLException e) {
                throw new RuntimeException("\u89e3\u6790url\u5931\u8d25:" + value, e);
            }
            serve.setProtocol(url.getProtocol());
            StringBuilder urlStr = new StringBuilder(url.getHost());
            int port = url.getPort();
            if (port != -1) {
                urlStr.append(":").append(port);
            }
            urlStr.append(url.getPath());
            serve.setUrl(urlStr.toString());
            String serverInfo = serverInfos[1];
            int uiName = serverInfo.indexOf(UNAME);
            int uiPwd = serverInfo.indexOf(UPWD);
            int uiType = serverInfo.indexOf(UTYPE);
            if (uiName == -1 || uiPwd == -1 || uiType == -1) {
                throw new RuntimeException("\u89e3\u6790\u670d\u52a1\u4fe1\u606f\u5931\u8d25" + value);
            }
            serve.setUserName(serverInfo.substring(uiName + UNAME.length(), uiPwd));
            serve.setPwd(serverInfo.substring(uiPwd + UPWD.length(), uiType));
            try {
                serve.setType(ServeType.valueOf(Integer.parseInt(serverInfo.substring(UTYPE.length() + uiType))));
            }
            catch (Exception e) {
                throw new RuntimeException("\u89e3\u6790\u670d\u52a1\u7c7b\u578b\u5931\u8d25" + value);
            }
            return serve;
        }
        return null;
    }
}


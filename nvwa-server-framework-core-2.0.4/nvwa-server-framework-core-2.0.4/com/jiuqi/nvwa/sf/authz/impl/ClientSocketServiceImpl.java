/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.socket.client.IO
 *  io.socket.client.IO$Options
 *  io.socket.client.Socket
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.sf.authz.impl;

import com.jiuqi.nvwa.sf.authz.AuthzUtil;
import com.jiuqi.nvwa.sf.authz.ClientSocketService;
import com.jiuqi.nvwa.sf.authz.ILicenceFileReceivedCallback;
import com.jiuqi.nvwa.sf.authz.ISystemStateChangeCallback;
import com.jiuqi.nvwa.sf.authz.impl.ClientSocketServiceParam;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URI;
import java.util.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientSocketServiceImpl
implements ClientSocketService {
    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private Socket socket;
    private ISystemStateChangeCallback systemStateChangeCallback = null;
    private ILicenceFileReceivedCallback licenceFileReceivedCallback = null;
    private boolean receivedLicenceFile = false;
    private final ClientSocketServiceParam param;
    private static final int TRACE_INT = 0;
    private static final int DEBUG_INT = 10;
    private static final int INFO_INT = 20;
    private static final int WARN_INT = 30;
    private static final int ERROR_INT = 40;
    private static final String REQUEST_LICENCE = "requestLicence";

    public ClientSocketServiceImpl(ClientSocketServiceParam param) {
        this.param = param;
    }

    @Override
    public void init() {
        this.LOGGER.debug("init");
        StringBuilder sb = new StringBuilder();
        sb.append(this.param.getAuthzCenterAddress()).append("?mac=").append(this.param.getMachineCode()).append("&productId=").append(this.param.getProductId()).append("&serverPort=").append(this.param.getServerPort()).append("&serverIp=").append(this.param.getServerIp());
        URI uri = URI.create(sb.toString());
        IO.Options options = new IO.Options();
        options.reconnection = true;
        options.reconnectionDelay = 1000L;
        options.reconnectionDelayMax = 5000L;
        options.reconnectionAttempts = 3;
        options.transports = new String[]{"websocket"};
        this.socket = IO.socket((URI)uri, (IO.Options)options);
    }

    @Override
    public void setSystemStateChangeCallback(ISystemStateChangeCallback systemStateChangeCallback) {
        this.systemStateChangeCallback = systemStateChangeCallback;
    }

    @Override
    public void setLicenceFileReceivedCallback(ILicenceFileReceivedCallback licenceFileReceivedCallback) {
        this.licenceFileReceivedCallback = licenceFileReceivedCallback;
    }

    @Override
    public Socket getSocket() {
        this.LOGGER.debug("getSocket");
        return this.socket;
    }

    private String objectConvertToString(Object ... args) {
        if (null == args || args.length == 0) {
            return "";
        }
        if (args[0] instanceof String) {
            return (String)args[0];
        }
        return JSONObject.valueToString((Object)args);
    }

    @Override
    public void regListener() throws Exception {
        this.LOGGER.debug("regListener");
        if (this.socket == null) {
            this.LOGGER.error("socket is null");
            return;
        }
        this.socket.on("connect", args -> {
            try {
                this.requestLicence();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.printLog(20, "connect", this.objectConvertToString(args) + "socketId\uff1a" + this.socket.id());
        });
        this.socket.on(REQUEST_LICENCE, args -> {
            this.printLog(20, REQUEST_LICENCE, "");
            byte[] licenceData = null;
            try {
                if (args[0] instanceof String) {
                    String dataBase64 = (String)args[0];
                    licenceData = Base64.getDecoder().decode(dataBase64);
                } else if (args[0] instanceof byte[]) {
                    licenceData = (byte[])args[0];
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (this.licenceFileReceivedCallback != null) {
                this.licenceFileReceivedCallback.licenceReceived(licenceData);
            }
            this.receivedLicenceFile = true;
        });
        this.socket.on("disconnect", args -> {
            String reason = this.objectConvertToString(args);
            this.printLog(40, "disconnect", reason);
            this.pauseSystem("\u4e0e\u6388\u6743\u670d\u52a1\u5668\u65ad\u5f00\u8fde\u63a5\u3002\u8bf7\u6ce8\u610f\uff1a1.\u4ee5\u96c6\u4e2d\u6388\u6743\u6a21\u5f0f\u542f\u52a8\u65f6\u5fc5\u987b\u4fdd\u6301\u548c\u6388\u6743\u670d\u52a1\u5668\u7684\u901a\u4fe1\u6b63\u5e38\uff1b2.\u5982\u679c\u8be5\u670d\u52a1\u88ab\u7ba1\u7406\u5458\u624b\u52a8\u4e0b\u7ebf\uff0c\u9700\u91cd\u542f\u670d\u52a1\u624d\u80fd\u91cd\u65b0\u63a5\u5165\uff1b3.\u5982\u88ab\u62c9\u9ed1\u8bf7\u8054\u7cfb\u6388\u6743\u670d\u52a1\u5668\u7ba1\u7406\u5458\u89e3\u9664\uff0c\u5426\u5219\u65e0\u6cd5\u63a5\u5165\u3002");
        });
        this.socket.on("message", args -> {
            String reason = this.objectConvertToString(args);
            this.printLog(40, "\u6388\u6743\u670d\u52a1\u5668\u6d88\u606f", reason);
        });
        this.socket.on("connect_error", args -> {
            String reason = this.objectConvertToString(args);
            this.printLog(40, "connect_error", "\u4e0e\u6388\u6743\u670d\u52a1\u5668\u8fde\u63a5\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u8fde\u63a5IP\u3001\u7aef\u53e3\u914d\u7f6e\u662f\u5426\u6b63\u786e\uff0c\u7f51\u7edc\u662f\u5426\u7545\u901a\u3002\u539f\u56e0\uff1a" + reason);
            this.pauseSystem("\u4e0e\u6388\u6743\u670d\u52a1\u5668\u8fde\u63a5\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5\u8fde\u63a5IP\u3001\u7aef\u53e3\u914d\u7f6e\u662f\u5426\u6b63\u786e\uff0c\u7f51\u7edc\u662f\u5426\u7545\u901a\u3002");
        });
    }

    private void printLog(String event, Object ... args) {
        this.printLog(20, event, this.objectConvertToString(args));
    }

    private void printLog(int level, String event, String detail) {
        StringBuilder sb = new StringBuilder();
        sb.append("[{}]");
        if (AuthzUtil.isNotBlank(detail)) {
            sb.append("\uff0c{}");
        }
        if (40 == level) {
            this.LOGGER.error(sb.toString(), (Object)event, (Object)detail);
        } else if (30 == level) {
            this.LOGGER.warn(sb.toString(), (Object)event, (Object)detail);
        } else if (20 == level) {
            this.LOGGER.info(sb.toString(), (Object)event, (Object)detail);
        } else if (10 == level) {
            this.LOGGER.debug(sb.toString(), (Object)event, (Object)detail);
        } else {
            this.LOGGER.trace(sb.toString(), (Object)event, (Object)detail);
        }
    }

    @Override
    public void off() {
        this.LOGGER.debug("off");
        this.socket.off();
    }

    @Override
    public void requestLicence() throws Exception {
        this.LOGGER.debug("requestLicence()\u5f00\u59cb");
        this.receivedLicenceFile = false;
        JSONObject msgJson = new JSONObject();
        msgJson.put("mac", (Object)this.param.getMachineCode());
        msgJson.put("msg", (Object)REQUEST_LICENCE);
        msgJson.put("productId", (Object)this.param.getProductId());
        msgJson.put("productVersion", (Object)this.param.getProductVersion());
        this.socket.emit(REQUEST_LICENCE, new Object[]{msgJson});
        this.LOGGER.debug("requestLicence()\u7ed3\u675f");
    }

    @Override
    public Socket connect() {
        this.LOGGER.debug("connect");
        if (this.socket == null) {
            this.LOGGER.error("socket is null");
            return null;
        }
        return this.socket.connect();
    }

    @Override
    public void disconnect() throws Exception {
        this.LOGGER.debug("disconnect()\u5f00\u59cb");
        if (this.socket == null) {
            this.LOGGER.info("socket is null");
            return;
        }
        this.socket.disconnect();
        this.pauseSystem("\u4e0e\u6388\u6743\u670d\u52a1\u5668\u7684\u8fde\u63a5\u5df2\u5173\u95ed");
        this.LOGGER.debug("disconnect()\u7ed3\u675f");
    }

    @Override
    public void pauseSystem(String reason) {
        if (this.systemStateChangeCallback != null) {
            this.systemStateChangeCallback.setSystemState(true, reason);
        }
    }

    @Override
    public void resumeSystem(String reason) {
        if (this.systemStateChangeCallback != null) {
            this.systemStateChangeCallback.setSystemState(false, reason);
        }
    }

    private void waitForLicence() throws InterruptedException {
        int countMax = 20;
        int count = 0;
        while (!this.receivedLicenceFile) {
            if (count < countMax) {
                Thread.sleep(500L);
                ++count;
                continue;
            }
            throw new RuntimeException("\u83b7\u53d6\u6388\u6743\u6587\u4ef6\u8d85\u65f6\u3002\u8bf7\u68c0\u67e5\u670d\u52a1\u5668\u4e0e\u6388\u6743\u670d\u52a1\u5668\u7684\u7f51\u7edc\u662f\u5426\u901a\u7545\uff1b\u5982\u679c\u670d\u52a1\u6216IP\u88ab\u6388\u6743\u670d\u52a1\u5668\u7ba1\u7406\u5458\u7981\u6b62\uff0c\u4e5f\u65e0\u6cd5\u83b7\u5f97\u6388\u6743\u6587\u4ef6\u3002");
        }
    }

    @Override
    public void startup() throws Exception {
        this.init();
        this.regListener();
        this.connect();
        this.waitForLicence();
    }

    @Override
    public void reConnect(String serverAddress) throws Exception {
        this.LOGGER.info("reConnect serverAddress:{}", (Object)serverAddress);
        if (this.socket != null) {
            this.socket.disconnect();
            this.socket.off();
        }
        this.param.setAuthzCenterAddress(serverAddress);
        this.startup();
    }

    @Override
    public JSONObject getServiceParam() {
        JSONObject jsonObject = new JSONObject();
        if (this.param != null) {
            jsonObject.put("authzCenterAddress", (Object)this.param.getAuthzCenterAddress());
            jsonObject.put("serverPort", (Object)this.param.getServerPort());
            jsonObject.put("serverIp", (Object)this.param.getServerIp());
        }
        return jsonObject;
    }
}


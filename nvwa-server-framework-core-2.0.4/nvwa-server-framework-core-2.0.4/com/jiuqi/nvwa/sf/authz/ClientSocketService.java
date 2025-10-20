/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.socket.client.Socket
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.sf.authz;

import com.jiuqi.nvwa.sf.authz.ILicenceFileReceivedCallback;
import com.jiuqi.nvwa.sf.authz.ISystemStateChangeCallback;
import io.socket.client.Socket;
import org.json.JSONObject;

public interface ClientSocketService {
    public void setSystemStateChangeCallback(ISystemStateChangeCallback var1);

    public void setLicenceFileReceivedCallback(ILicenceFileReceivedCallback var1);

    public void init();

    public Socket getSocket();

    public void regListener() throws Exception;

    public void off();

    public void requestLicence() throws Exception;

    public Socket connect();

    public void disconnect() throws Exception;

    public void startup() throws Exception;

    public void reConnect(String var1) throws Exception;

    public JSONObject getServiceParam();

    public void resumeSystem(String var1);

    public void pauseSystem(String var1);
}


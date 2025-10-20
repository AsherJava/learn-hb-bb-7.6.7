/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.springadapter.servlet.NvwaHandlerInterceptor
 *  com.jiuqi.nvwa.springadapter.servlet.NvwaRequstResponseBase
 *  org.springframework.http.HttpStatus
 */
package com.jiuqi.nvwa.sf.adapter.spring.interceptor;

import com.jiuqi.nvwa.springadapter.servlet.NvwaHandlerInterceptor;
import com.jiuqi.nvwa.springadapter.servlet.NvwaRequstResponseBase;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class AuthzInterceptor
implements NvwaHandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthzInterceptor.class);
    private boolean systemPaused = false;
    private String systemPausedReason = "";
    private long systemPausedTimestamp = 0L;

    public void setSystemState(boolean systemPaused, String reason) {
        this.systemPaused = systemPaused;
        this.systemPausedReason = reason;
        if (systemPaused) {
            if (this.systemPausedTimestamp == 0L) {
                this.systemPausedTimestamp = System.currentTimeMillis();
            }
        } else {
            this.systemPausedTimestamp = 0L;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean preHandle(NvwaRequstResponseBase nvwaRequstResponseBase, Object handler) throws Exception {
        LOGGER.debug("preHandle()");
        if (!this.systemPaused) {
            return true;
        }
        if (System.currentTimeMillis() - this.systemPausedTimestamp > 21600000L) {
            LOGGER.info("\u7cfb\u7edf\u6682\u505c\uff0c\u539f\u56e0\uff1a[{}]", (Object)this.systemPausedReason);
            StringBuffer buffer = new StringBuffer();
            nvwaRequstResponseBase.setResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            buffer.append("{\"code\":\"500\",\"message\":\"\u7cfb\u7edf\u6682\u505c\uff0c\u539f\u56e0\uff1a" + this.systemPausedReason + "\",\"datas\":\"");
            buffer.append("\"}");
            nvwaRequstResponseBase.setResponseCharacterEncoding("UTF-8");
            nvwaRequstResponseBase.setResponseContentType("application/json");
            OutputStream outputStream = null;
            try {
                outputStream = nvwaRequstResponseBase.getOutputStream();
                outputStream.write(buffer.toString().getBytes("UTF-8"));
                outputStream.flush();
            }
            finally {
                if (null != outputStream) {
                    outputStream.close();
                }
            }
            return false;
        }
        return true;
    }
}


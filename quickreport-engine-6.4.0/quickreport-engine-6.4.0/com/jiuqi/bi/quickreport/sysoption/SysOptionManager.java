/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.LogManager
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.sysoption;

import com.jiuqi.bi.logging.LogManager;
import com.jiuqi.bi.quickreport.sysoption.ISysOptionProvider;
import com.jiuqi.bi.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class SysOptionManager {
    private static final SysOptionManager instance = new SysOptionManager();
    public static final String DEFAULT_BTN_LAYOUT = "{common:['query'],more:['writeback', 'export', 'exportPDF', 'print','filter','batchExport','snapshot','viewSnapshot']}";
    private JSONObject defBtnLayout;
    private ISysOptionProvider provider;

    private SysOptionManager() {
        try {
            this.defBtnLayout = new JSONObject(DEFAULT_BTN_LAYOUT);
        }
        catch (JSONException e) {
            LogManager.getLogger(this.getClass()).error("\u521b\u5efa\u6309\u94ae\u5e03\u5c40\u914d\u7f6e\u5931\u8d25", (Throwable)e);
        }
    }

    public static SysOptionManager getInstance() {
        return instance;
    }

    public void setProvider(ISysOptionProvider provider) {
        this.provider = provider;
    }

    public ISysOptionProvider getProvider() {
        return this.provider;
    }

    public String getBtnLayout() {
        String providerLayout;
        if (this.provider != null && !StringUtils.isEmpty((String)(providerLayout = this.provider.getBtnLayout()))) {
            try {
                JSONObject getLayout = new JSONObject(providerLayout);
                return getLayout.toString().toLowerCase();
            }
            catch (JSONException e) {
                LogManager.getLogger(this.getClass()).error("\u521b\u5efa\u6309\u94ae\u5e03\u5c40\u914d\u7f6e\u5931\u8d25", (Throwable)e);
            }
        }
        return this.defBtnLayout.toString();
    }

    public boolean isMsgUTF8() {
        if (this.provider == null) {
            return false;
        }
        return this.provider.isMsgUTF8();
    }
}


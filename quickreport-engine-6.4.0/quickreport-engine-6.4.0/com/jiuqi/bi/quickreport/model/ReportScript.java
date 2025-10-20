/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.model.JSONHelper;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ReportScript
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private String engineScript;
    private String uiScript;
    private String qrScript;
    private List<String> uiReferences = new ArrayList<String>();
    public static final String SCRIPT_DIR = "scripts";
    public static final String SCRIPT_MF = "script.mf";
    public static final String SCRIPT_ENGINE = "script.engine.js";
    public static final String SCRIPT_UI = "script.ui.js";
    public static final String SCRIPT_QR = "script.qr.js";
    private static final String SCRIPT_MF_REFS = "references";

    public String getEngineScript() {
        return this.engineScript;
    }

    public void setEngineScript(String engineScript) {
        this.engineScript = engineScript;
    }

    public String getUIScript() {
        return this.uiScript;
    }

    public void setUIScript(String uiScript) {
        this.uiScript = uiScript;
    }

    public String getQrScript() {
        return this.qrScript;
    }

    public void setQrScript(String qrScript) {
        this.qrScript = qrScript;
    }

    public List<String> getUIReferences() {
        return this.uiReferences;
    }

    public Object clone() {
        ReportScript result;
        try {
            result = (ReportScript)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        result.uiReferences = new ArrayList<String>(this.uiReferences);
        return result;
    }

    public void save(ZipOutputStream zip) throws JSONException, IOException {
        this.saveManifest(zip);
        if (!StringUtils.isEmpty((String)this.engineScript)) {
            this.saveScript(zip, this.engineScript, SCRIPT_ENGINE);
        }
        if (!StringUtils.isEmpty((String)this.uiScript)) {
            this.saveScript(zip, this.uiScript, SCRIPT_UI);
        }
        if (!StringUtils.isEmpty((String)this.qrScript)) {
            this.saveScript(zip, this.qrScript, SCRIPT_QR);
        }
    }

    public void load(ZipInputStream zip, String entryName) throws IOException, JSONException {
        if (entryName.endsWith(SCRIPT_MF)) {
            this.loadManifest(zip);
        } else if (entryName.endsWith(SCRIPT_UI)) {
            this.loadUIScript(zip);
        } else if (entryName.endsWith(SCRIPT_QR)) {
            this.loadQRScript(zip);
        } else if (entryName.endsWith(SCRIPT_ENGINE)) {
            this.loadEngineScript(zip);
        }
    }

    private void loadManifest(ZipInputStream zip) throws IOException, JSONException {
        JSONObject mf = JSONHelper.readJSONObject(zip);
        JSONArray refs = mf.getJSONArray(SCRIPT_MF_REFS);
        for (int i = 0; i < refs.length(); ++i) {
            this.uiReferences.add(refs.getString(i));
        }
    }

    private void loadUIScript(ZipInputStream zip) throws IOException {
        this.uiScript = JSONHelper.readString(zip);
    }

    private void loadQRScript(ZipInputStream zip) throws IOException {
        this.qrScript = JSONHelper.readString(zip);
    }

    private void loadEngineScript(ZipInputStream zip) throws IOException {
        this.engineScript = JSONHelper.readString(zip);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveManifest(ZipOutputStream zip) throws JSONException, IOException {
        zip.putNextEntry(new ZipEntry("scripts/script.mf"));
        try {
            JSONObject mf = new JSONObject();
            JSONArray refs = new JSONArray(this.uiReferences);
            mf.put(SCRIPT_MF_REFS, (Object)refs);
            JSONHelper.writeJSONObject(zip, mf);
        }
        finally {
            zip.closeEntry();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveScript(ZipOutputStream zip, String script, String scriptName) throws IOException {
        zip.putNextEntry(new ZipEntry("scripts/" + scriptName));
        try {
            JSONHelper.writeString(zip, script);
        }
        finally {
            zip.closeEntry();
        }
    }

    void clear() {
        this.uiReferences.clear();
        this.engineScript = null;
        this.uiScript = null;
    }
}


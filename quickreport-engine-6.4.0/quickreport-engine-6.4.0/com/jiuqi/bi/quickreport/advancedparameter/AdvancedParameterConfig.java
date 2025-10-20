/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.model.ParameterModel
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.advancedparameter;

import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.quickreport.advancedparameter.QuickFilterConfig;
import com.jiuqi.bi.quickreport.model.JSONHelper;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class AdvancedParameterConfig
implements Serializable {
    private static final long serialVersionUID = -5535173221168631634L;
    public static final String SAVE_DIR = "advancedParaConf";
    private static final String QUICK_EXT = ".quick";
    private QuickFilterConfig quickFilterConfig;

    public QuickFilterConfig getQuickFilterConfig() {
        return this.quickFilterConfig;
    }

    public void setQuickFilterConfig(QuickFilterConfig quickFilterConfig) {
        this.quickFilterConfig = quickFilterConfig;
    }

    public void save(ZipOutputStream zip) throws IOException, JSONException {
        if (this.quickFilterConfig != null) {
            zip.putNextEntry(new ZipEntry("advancedParaConf.quick"));
            try {
                JSONObject maps = this.quickFilterConfig.toJson();
                JSONHelper.writeJSONObject(zip, maps);
            }
            finally {
                zip.closeEntry();
            }
        }
    }

    public void load(String name, ZipInputStream zip) throws IOException, JSONException {
        if (name.equalsIgnoreCase("advancedParaConf.quick")) {
            JSONObject json = JSONHelper.readJSONObject(zip);
            this.quickFilterConfig = new QuickFilterConfig();
            this.quickFilterConfig.fromJson(json);
        }
    }

    @Deprecated
    public void load(String name, ZipInputStream zip, List<ParameterModel> paraModels) throws IOException, JSONException {
        this.load(name, zip);
    }
}


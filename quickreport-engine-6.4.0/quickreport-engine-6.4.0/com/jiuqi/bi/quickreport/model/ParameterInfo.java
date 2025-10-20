/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DSParamUtils
 *  com.jiuqi.bi.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceIdentify
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.dataset.DSParamUtils;
import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.model.ReportModelException;
import com.jiuqi.bi.quickreport.model.ReportParamType;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceIdentify;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public final class ParameterInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private ReportParamType type;
    private ParameterModel model;
    private String datasetName;
    private String datasetType;
    private String storageType;
    private boolean resetConfig;
    private String name;
    private String title;
    private transient com.jiuqi.bi.parameter.model.ParameterModel compatibleModel;
    static final String PARAM_TYPE = "type";
    static final String PARAM_NAME = "name";
    static final String PARAM_TITLE = "title";
    static final String PARAM_DATASETNAME = "datasetName";
    static final String PARAM_DATASETTYPE = "datasetType";
    static final String PARAM_STORAGETYPE = "storageType";
    static final String PARAM_RESETCONFIG = "resetConfig";
    static final String PARAM_CONFIG = "config";
    static final String PARAM_CONFIG_SELMODE = "selectMode";
    static final String PARAM_CONFIG_HIDDEN = "hidden";
    static final String PARAM_CONFIG_WIDGETTYPE = "widgetType";

    public ReportParamType getType() {
        return this.type;
    }

    public void setType(ReportParamType type) {
        this.type = type;
    }

    @Deprecated
    public com.jiuqi.bi.parameter.model.ParameterModel getModel() {
        if (this.compatibleModel == null && this.model != null) {
            try {
                this.compatibleModel = DSParamUtils.convertParameterModel((ParameterModel)this.model);
            }
            catch (ParameterException e) {
                throw new QuickReportError(e);
            }
        }
        return this.compatibleModel;
    }

    @Deprecated
    public void setModel(com.jiuqi.bi.parameter.model.ParameterModel model) {
        try {
            this.model = DSParamUtils.convertParameterModel((com.jiuqi.bi.parameter.model.ParameterModel)model);
        }
        catch (ParameterException e) {
            throw new QuickReportError(e);
        }
        this.compatibleModel = model;
    }

    public ParameterModel getParamModel() {
        return this.model;
    }

    public void setParamModel(ParameterModel model) {
        this.model = model;
        this.compatibleModel = null;
    }

    public String getDatasetType() {
        return this.datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

    public String getDatasetName() {
        return this.datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getStorageType() {
        return this.storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public boolean isResetConfig() {
        return this.resetConfig;
    }

    public void setResetConfig(boolean resetConfig) {
        this.resetConfig = resetConfig;
    }

    public String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public Object clone() {
        ParameterInfo result;
        try {
            result = (ParameterInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        if (this.model != null) {
            result.model = this.model.clone();
        }
        result.compatibleModel = null;
        return result;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject param = new JSONObject();
        if (this.type == null) {
            return param;
        }
        param.put(PARAM_TYPE, (Object)this.type.toString());
        switch (this.type) {
            case PUBLIC: {
                param.put(PARAM_NAME, (Object)(this.model == null ? this.name : this.model.getName()));
                param.put(PARAM_TITLE, (Object)(this.model == null ? this.title : this.model.getTitle()));
                param.put(PARAM_STORAGETYPE, (Object)this.storageType);
                param.put(PARAM_RESETCONFIG, this.resetConfig);
                if (!this.resetConfig) break;
                JSONObject conf = this.toConfigJSON();
                param.put(PARAM_CONFIG, (Object)conf);
                break;
            }
            case FROMDATASET: {
                param.put(PARAM_NAME, (Object)(this.model == null ? this.name : this.model.getName()));
                param.put(PARAM_TITLE, (Object)(this.model == null ? this.title : this.model.getTitle()));
                param.put(PARAM_DATASETNAME, (Object)this.datasetName);
                param.put(PARAM_DATASETTYPE, (Object)this.datasetType);
                param.put(PARAM_STORAGETYPE, (Object)this.storageType);
                param.put(PARAM_RESETCONFIG, this.resetConfig);
                if (!this.resetConfig) break;
                JSONObject conf = this.toConfigJSON();
                param.put(PARAM_CONFIG, (Object)conf);
                break;
            }
            case LOCAL: {
                JSONObject paramObj = new JSONObject();
                this.model.toJson(paramObj);
                param.put(PARAM_CONFIG, (Object)paramObj);
                break;
            }
        }
        return param;
    }

    private JSONObject toConfigJSON() throws JSONException {
        JSONObject conf = new JSONObject();
        conf.put(PARAM_CONFIG_SELMODE, (Object)this.model.getSelectMode().toString());
        conf.put(PARAM_CONFIG_HIDDEN, this.model.isHidden());
        conf.put(PARAM_CONFIG_WIDGETTYPE, this.model.getWidgetType());
        return conf;
    }

    public void fromJSON(JSONObject json, boolean autoLoad) throws JSONException, ParameterStorageException, ReportModelException {
        this.fromJSON(json, 327681, autoLoad);
    }

    public void fromJSON(JSONObject json, int version, boolean autoLoad) throws JSONException, ParameterStorageException, ReportModelException {
        this.type = ReportParamType.valueOf(json.optString(PARAM_TYPE));
        switch (this.type) {
            case PUBLIC: {
                this.name = json.optString(PARAM_NAME);
                this.storageType = json.optString(PARAM_STORAGETYPE);
                if (!autoLoad) break;
                ParameterModel model = ParameterStorageManager.getInstance().findModel(new ParameterResourceIdentify(this.name), this.storageType);
                this.setParamModel(model, json);
                break;
            }
            case FROMDATASET: {
                this.name = json.optString(PARAM_NAME);
                this.datasetName = json.optString(PARAM_DATASETNAME);
                this.datasetType = json.optString(PARAM_DATASETTYPE);
                this.storageType = json.optString(PARAM_STORAGETYPE);
                if (!autoLoad) break;
                ParameterResourceIdentify paramId = new ParameterResourceIdentify(this.name, this.datasetName, "com.jiuqi.bi.dataset");
                ParameterModel model = ParameterStorageManager.getInstance().findModel(paramId, this.storageType);
                this.setParamModel(model, json);
                break;
            }
            case LOCAL: {
                JSONObject conf = json.optJSONObject(PARAM_CONFIG);
                if (version >= 131072) {
                    this.model = new ParameterModel();
                    this.model.fromJson(conf);
                    break;
                }
                this.compatibleModel = new com.jiuqi.bi.parameter.model.ParameterModel();
                this.compatibleModel.load(conf);
                try {
                    this.model = DSParamUtils.convertParameterModel((com.jiuqi.bi.parameter.model.ParameterModel)this.compatibleModel);
                    break;
                }
                catch (ParameterException e) {
                    throw new ParameterStorageException((Throwable)e);
                }
            }
            default: {
                throw new ReportModelException("\u672a\u77e5\u7684\u62a5\u8868\u53c2\u6570\u7c7b\u578b\uff1a" + json.optString(PARAM_TYPE));
            }
        }
    }

    void setParamModel(ParameterModel model, JSONObject modelJson) {
        this.setParamModel(model);
        if (model == null && !modelJson.isNull(PARAM_TITLE)) {
            this.title = modelJson.optString(PARAM_TITLE);
        }
        this.resetConfig = modelJson.optBoolean(PARAM_RESETCONFIG);
        if (this.resetConfig) {
            JSONObject conf = modelJson.optJSONObject(PARAM_CONFIG);
            this.fromConfigJSON(conf);
        }
    }

    void fromConfigJSON(JSONObject conf) throws JSONException {
        if (this.model == null || conf == null) {
            return;
        }
        this.model.setSelectMode(ParameterSelectMode.valueOf((String)conf.optString(PARAM_CONFIG_SELMODE)));
        this.model.setHidden(conf.optBoolean(PARAM_CONFIG_HIDDEN));
        this.model.setWidgetType(conf.optInt(PARAM_CONFIG_WIDGETTYPE));
    }

    public String toString() {
        return this.model == null ? this.name : this.model.getName();
    }
}


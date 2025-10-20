/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.model.config;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractParameterValueConfig
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    private ParameterCandidateValueMode candidateMode = ParameterCandidateValueMode.ALL;
    private List<String> candidateValue = new ArrayList<String>();
    private List<String> candidateDimTrees = new ArrayList<String>();
    private String defaultValueMode = "none";
    private AbstractParameterValue defaultValue;
    private List<ParameterDependMember> depends = new ArrayList<ParameterDependMember>();
    private String acceptMinValue;
    private String acceptMaxValue;

    public ParameterCandidateValueMode getCandidateMode() {
        return this.candidateMode;
    }

    public void setCandidateMode(ParameterCandidateValueMode candidateMode) {
        this.candidateMode = candidateMode;
    }

    public List<String> getCandidateValue() {
        return this.candidateValue;
    }

    public String getDefaultValueMode() {
        return this.defaultValueMode;
    }

    public void setDefaultValueMode(String defaultValueMode) {
        this.defaultValueMode = defaultValueMode;
    }

    public AbstractParameterValue getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(AbstractParameterValue defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<ParameterDependMember> getDepends() {
        return this.depends;
    }

    public void setAcceptMaxValue(String acceptMaxValue) {
        this.acceptMaxValue = acceptMaxValue;
    }

    public void setAcceptMinValue(String acceptMinValue) {
        this.acceptMinValue = acceptMinValue;
    }

    public String getAcceptMaxValue() {
        return this.acceptMaxValue;
    }

    public String getAcceptMinValue() {
        return this.acceptMinValue;
    }

    public List<String> getCandidateDimTrees() {
        return this.candidateDimTrees;
    }

    public void fromJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        JSONArray dp_array;
        JSONArray dt_array;
        this.candidateMode = ParameterCandidateValueMode.valueOf(json.optInt("candidateMode"));
        JSONArray cv_array = json.optJSONArray("candidateValue");
        if (cv_array != null) {
            for (int i = 0; i < cv_array.length(); ++i) {
                this.candidateValue.add(cv_array.getString(i));
            }
        }
        if ((dt_array = json.optJSONArray("candidateDimTrees")) != null) {
            for (int i = 0; i < dt_array.length(); ++i) {
                this.candidateDimTrees.add(dt_array.getString(i));
            }
        }
        this.defaultValueMode = this.parseValueMode(json, "defaultValueMode", "extDefaultValueMode");
        JSONObject dv_json = json.optJSONObject("defaultValue");
        if (dv_json != null) {
            this.defaultValue = AbstractParameterValue.loadFromJson(dv_json, datasource);
            if (this.defaultValueMode.equals("none")) {
                String valueMode = this.defaultValue.getValueMode();
                this.defaultValueMode = valueMode.equals("expr") ? "expr" : "appoint";
            }
        }
        if ((dp_array = json.optJSONArray("depends")) != null) {
            for (int i = 0; i < dp_array.length(); ++i) {
                JSONObject j = dp_array.getJSONObject(i);
                ParameterDependMember mb = new ParameterDependMember(j.getString("parameterName"), j.optString("datasourceFieldName"));
                this.depends.add(mb);
            }
        }
        this.acceptMaxValue = json.optString("acceptMaxValue");
        this.acceptMinValue = json.optString("acceptMinValue");
        if (datasource instanceof NonDataSourceModel && datasource.getDataType() == 2) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy;mm;dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMDD");
            try {
                if (StringUtils.isNotEmpty((String)this.acceptMinValue) && this.acceptMinValue.contains(";")) {
                    Date min = sdf.parse(this.acceptMinValue);
                    this.acceptMinValue = sdf1.format(min);
                }
                if (StringUtils.isNotEmpty((String)this.acceptMaxValue) && this.acceptMaxValue.contains(";")) {
                    Date max = sdf.parse(this.acceptMaxValue);
                    this.acceptMaxValue = sdf1.format(max);
                }
            }
            catch (Exception e) {
                throw new JSONException((Throwable)e);
            }
        }
    }

    String parseValueMode(JSONObject json, String valueModeKey, String extValueModeKey) throws JSONException {
        String extDefaultValueMode;
        String valueMode = json.optString(valueModeKey, "none");
        if (valueMode.length() <= 2) {
            try {
                int v = Integer.parseInt(valueMode);
                if (v == 0) {
                    valueMode = "appoint";
                } else if (v == 1) {
                    valueMode = "first";
                } else if (v == 2) {
                    valueMode = "expr";
                } else if (v == 3) {
                    valueMode = "none";
                } else if (v == 11) {
                    valueMode = "firstChild";
                }
            }
            catch (NumberFormatException e) {
                valueMode = "none";
            }
        }
        if ((extDefaultValueMode = json.optString("extValueModeKey", null)) != null) {
            valueMode = extDefaultValueMode;
        }
        return valueMode;
    }

    public void toJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        json.put("candidateMode", this.candidateMode.value());
        json.put("candidateValue", this.candidateValue);
        json.put("candidateDimTrees", this.candidateDimTrees);
        json.put("defaultValueMode", (Object)this.defaultValueMode);
        if (this.defaultValue != null) {
            JSONObject defaultValueJson = new JSONObject();
            this.defaultValue.toJson(defaultValueJson, datasource);
            json.put("defaultValue", (Object)defaultValueJson);
        }
        JSONArray array = new JSONArray();
        for (ParameterDependMember mb : this.depends) {
            JSONObject j = new JSONObject();
            j.put("parameterName", (Object)mb.getParameterName());
            j.put("datasourceFieldName", (Object)mb.getDatasourceFieldName());
            array.put((Object)j);
        }
        json.put("depends", (Object)array);
        if (this.acceptMinValue != null) {
            json.put("acceptMinValue", (Object)this.acceptMinValue);
        }
        if (this.acceptMaxValue != null) {
            json.put("acceptMaxValue", (Object)this.acceptMaxValue);
        }
    }

    public AbstractParameterValueConfig clone() {
        AbstractParameterValueConfig cfg;
        try {
            cfg = (AbstractParameterValueConfig)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        cfg.candidateDimTrees = new ArrayList<String>(this.candidateDimTrees);
        cfg.candidateValue = new ArrayList<String>(this.candidateValue);
        if (this.defaultValue != null) {
            cfg.defaultValue = this.defaultValue.clone();
        }
        cfg.depends = new ArrayList<ParameterDependMember>();
        for (ParameterDependMember m : this.depends) {
            cfg.depends.add(new ParameterDependMember(m.getParameterName(), m.getDatasourceFieldName()));
        }
        return cfg;
    }
}


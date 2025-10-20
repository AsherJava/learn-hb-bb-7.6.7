/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.model;

import com.jiuqi.bi.dataset.DSParamUtils;
import com.jiuqi.bi.dataset.model.DSModelException;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.DSCalcField;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class DSModel
implements Cloneable {
    public static final String TYPE_DSMODULE = "com.jiuqi.bi.dataset";
    private String guid;
    private String name;
    private String title;
    private String descr;
    private List<DSField> fields = new ArrayList<DSField>();
    private List<DSCalcField> calcFields = new ArrayList<DSCalcField>();
    private List<DSHierarchy> hiers = new ArrayList<DSHierarchy>();
    private List<ParameterModel> params = new ArrayList<ParameterModel>();
    private int minFiscalMonth = -1;
    private int maxFiscalMonth = -1;
    public static final String TAG_DS = "dataset";
    public static final String TAG_GUID = "guid";
    public static final String TAG_TYPE = "type";
    private static final String TAG_NAME = "name";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DESCR = "descr";
    private static final String TAG_MINFISCAL = "minFiscalMonth";
    private static final String TAG_MAXFISCAL = "maxFiscalMonth";
    private static final String TAG_FIELDS = "fields";
    private static final String TAG_CALCFIELD = "calcFields";
    private static final String TAG_FD_NAME = "name";
    private static final String TAG_FD_TITLE = "title";
    private static final String TAG_FD_VALTYPE = "valType";
    private static final String TAG_FD_TYPE = "fieldType";
    private static final String TAG_FD_KEYFD = "keyField";
    private static final String TAG_FD_NAMEFD = "nameField";
    private static final String TAG_FD_AGGREG = "aggregation";
    private static final String TAG_FD_APPLYTYPE = "applyType";
    private static final String TAG_FD_TIMEGRANULARITY = "timegranularity";
    private static final String TAG_FD_DATAPATTERN = "dataPattern";
    private static final String TAG_FD_SHOWPATTERN = "showPattern";
    private static final String TAG_FD_SOURCETYPE = "sourceType";
    private static final String TAG_FD_SOURCEDATA = "sourceData";
    private static final String TAG_FD_FORMULA = "formula";
    private static final String TAG_FD_ISUNITDIM = "unitdim";
    private static final String TAG_FD_ISTIMEKEY = "timekey";
    private static final String TAG_FD_CALCMODE = "calcmode";
    private static final String TAG_FD_MESSAGEALIAS = "messageAlias";
    private static final String TAG_HIERS = "hiers";
    private static final String TAG_HR_NAME = "name";
    private static final String TAG_HR_TITLE = "title";
    private static final String TAG_HR_TYPE = "type";
    private static final String TAG_HR_LEVELS = "levels";
    private static final String TAG_HR_PNAME = "parentFieldName";
    private static final String TAG_HR_CNAME = "codeFieldName";
    private static final String TAG_PARAMS = "params";
    private static final String TAG_EXT = "ext";

    @Deprecated
    public void prepareParameterEnv(IParameterEnv env) throws DSModelException {
    }

    public void prepareParameterEnv(com.jiuqi.nvwa.framework.parameter.IParameterEnv env) throws DSModelException {
    }

    public final void toJSON(JSONObject json) throws DSModelException {
        JSONObject ds = new JSONObject();
        try {
            json.put(TAG_DS, (Object)ds);
            this.putValue(ds, TAG_GUID, this._getGuid());
            this.putValue(ds, "type", this.getType());
            this.putValue(ds, "name", this.getName());
            this.putValue(ds, "title", this.getTitle());
            this.putValue(ds, TAG_DESCR, this.getDescr());
            this.putValue(ds, TAG_MINFISCAL, this.getMinFiscalMonth());
            this.putValue(ds, TAG_MAXFISCAL, this.getMaxFiscalMonth());
            this.makeCommonFieldToJSON(ds);
            this.makeCalcFieldToJSON(ds);
            this.makeHierarchyToJSON(ds);
            this.makeParamterToJSON(ds);
            JSONObject extJsonObject = new JSONObject();
            this.saveExtToJSON(extJsonObject);
            json.put(TAG_EXT, (Object)extJsonObject);
        }
        catch (Exception e) {
            throw new DSModelException(e.getMessage(), e);
        }
    }

    public final void fromJSON(JSONObject json) throws DSModelException {
        try {
            JSONObject ds = json.getJSONObject(TAG_DS);
            this._setGuid(ds.getString(TAG_GUID));
            this.setName(ds.getString("name"));
            this.setTitle(ds.getString("title"));
            if (!ds.isNull(TAG_DESCR)) {
                this.setDescr(ds.getString(TAG_DESCR));
            }
            this.setMinFiscalMonth(ds.optInt(TAG_MINFISCAL, -1));
            this.setMaxFiscalMonth(ds.optInt(TAG_MAXFISCAL, -1));
            this.loadFieldFromJSON(ds);
            this.loadCalcFieldFromJSON(ds);
            this.loadHierarchyFromJSON(ds);
            this.loadParamFromJSON(ds);
            JSONObject extJsonObject = json.getJSONObject(TAG_EXT);
            this.loadExtFromJSON(extJsonObject);
        }
        catch (Exception e) {
            throw new DSModelException(e.getMessage(), e);
        }
    }

    public final void saveExt(OutputStream os) throws DSModelException {
        JSONObject json = new JSONObject();
        try {
            this.saveExtToJSON(json);
        }
        catch (Exception e1) {
            throw new DSModelException(e1);
        }
        String jsonStr = json.toString();
        try {
            os.write(jsonStr.getBytes("UTF-8"));
        }
        catch (IOException e) {
            throw new DSModelException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void loadExt(InputStream is) throws DSModelException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            String jsonStr = null;
            try {
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                jsonStr = new String(os.toByteArray(), "UTF-8");
            }
            finally {
                os.close();
            }
            if (jsonStr != null && jsonStr.length() > 0) {
                JSONObject json = new JSONObject(jsonStr);
                this.loadExtFromJSON(json);
            }
        }
        catch (Exception e) {
            throw new DSModelException(e);
        }
    }

    public abstract String getType();

    protected abstract void saveExtToJSON(JSONObject var1) throws Exception;

    protected abstract void loadExtFromJSON(JSONObject var1) throws Exception;

    public DSModel clone() {
        try {
            DSModel cloned = (DSModel)super.clone();
            cloned.calcFields = new ArrayList<DSCalcField>();
            for (DSCalcField dSCalcField : this.calcFields) {
                cloned.calcFields.add(dSCalcField.clone());
            }
            cloned.fields = new ArrayList<DSField>();
            for (DSField dSField : this.fields) {
                cloned.fields.add(dSField.clone());
            }
            cloned.hiers = new ArrayList<DSHierarchy>();
            for (DSHierarchy dSHierarchy : this.hiers) {
                cloned.hiers.add(dSHierarchy.clone());
            }
            cloned.params = new ArrayList<ParameterModel>();
            for (ParameterModel parameterModel : this.params) {
                cloned.params.add(parameterModel.clone());
            }
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void _setGuid(String guid) {
        this.guid = guid;
    }

    public String _getGuid() {
        return this.guid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescr() {
        return this.descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public List<DSField> getCommonFields() {
        return this.fields;
    }

    public List<DSCalcField> getCalcFields() {
        return this.calcFields;
    }

    public int getMaxFiscalMonth() {
        return this.maxFiscalMonth;
    }

    public int getMinFiscalMonth() {
        return this.minFiscalMonth;
    }

    public void setMaxFiscalMonth(int maxFiscalMonth) {
        this.maxFiscalMonth = maxFiscalMonth;
    }

    public void setMinFiscalMonth(int minFiscalMonth) {
        this.minFiscalMonth = minFiscalMonth;
    }

    public List<DSField> getFields() {
        ArrayList<DSField> allFields = new ArrayList<DSField>(this.fields.size() + this.calcFields.size());
        allFields.addAll(this.fields);
        allFields.addAll(this.calcFields);
        return allFields;
    }

    public DSField findField(String fieldName) {
        for (DSField dSField : this.fields) {
            if (!fieldName.equalsIgnoreCase(dSField.getName())) continue;
            return dSField;
        }
        for (DSField dSField : this.calcFields) {
            if (!fieldName.equalsIgnoreCase(dSField.getName())) continue;
            return dSField;
        }
        return null;
    }

    public List<DSHierarchy> getHiers() {
        return this.hiers;
    }

    @Deprecated
    public List<com.jiuqi.bi.parameter.model.ParameterModel> getParams() {
        ArrayList<com.jiuqi.bi.parameter.model.ParameterModel> list = new ArrayList<com.jiuqi.bi.parameter.model.ParameterModel>();
        try {
            for (ParameterModel pm : this.params) {
                list.add(DSParamUtils.convertParameterModel(pm));
            }
        }
        catch (ParameterException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<ParameterModel> getParameterModels() {
        return this.params;
    }

    @Deprecated
    public com.jiuqi.bi.parameter.model.ParameterModel findParam(String paramName) {
        ParameterModel pm = this.findParamemterModel(paramName);
        if (pm != null) {
            try {
                return DSParamUtils.convertParameterModel(pm);
            }
            catch (ParameterException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public ParameterModel findParamemterModel(String paramName) {
        for (ParameterModel param : this.params) {
            if (!paramName.equalsIgnoreCase(param.getName())) continue;
            return param;
        }
        return null;
    }

    private void makeCommonFieldToJSON(JSONObject ds) throws Exception {
        JSONArray fds = new JSONArray();
        ds.put(TAG_FIELDS, (Object)fds);
        for (DSField field : this.fields) {
            JSONObject fd = new JSONObject();
            this.putValue(fd, "name", field.getName());
            this.putValue(fd, "title", field.getTitle());
            this.putValue(fd, TAG_FD_VALTYPE, field.getValType());
            if (field.getFieldType() != null) {
                this.putValue(fd, TAG_FD_TYPE, field.getFieldType().value());
            }
            this.putValue(fd, TAG_FD_KEYFD, field.getKeyField());
            this.putValue(fd, TAG_FD_NAMEFD, field.getNameField());
            if (field.getAggregation() != null) {
                this.putValue(fd, TAG_FD_AGGREG, field.getAggregation().value());
            }
            if (field.getApplyType() != null) {
                this.putValue(fd, TAG_FD_APPLYTYPE, field.getApplyType().value());
            }
            if (field.getTimegranularity() != null) {
                this.putValue(fd, TAG_FD_TIMEGRANULARITY, field.getTimegranularity().value());
            }
            this.putValue(fd, TAG_FD_DATAPATTERN, field.getDataPattern());
            this.putValue(fd, TAG_FD_SHOWPATTERN, field.getShowPattern());
            this.putValue(fd, TAG_FD_SOURCETYPE, field.getSourceType());
            this.putValue(fd, TAG_FD_SOURCEDATA, field.getSourceData());
            this.putValue(fd, TAG_FD_ISUNITDIM, field.isUnitDim());
            this.putValue(fd, TAG_FD_ISTIMEKEY, field.isTimekey());
            this.putValue(fd, TAG_FD_MESSAGEALIAS, field.getMessageAlias());
            fds.put((Object)fd);
        }
    }

    private void makeCalcFieldToJSON(JSONObject ds) throws Exception {
        JSONArray fds = new JSONArray();
        ds.put(TAG_CALCFIELD, (Object)fds);
        for (DSCalcField field : this.calcFields) {
            JSONObject fd = new JSONObject();
            this.putValue(fd, "name", field.getName());
            this.putValue(fd, "title", field.getTitle());
            this.putValue(fd, TAG_FD_VALTYPE, field.getValType());
            this.putValue(fd, TAG_FD_FORMULA, field.getFormula());
            this.putValue(fd, TAG_FD_VALTYPE, field.getValType());
            if (field.getAggregation() != null) {
                this.putValue(fd, TAG_FD_AGGREG, field.getAggregation().value());
            }
            if (field.getApplyType() != null) {
                this.putValue(fd, TAG_FD_APPLYTYPE, field.getApplyType().value());
            }
            this.putValue(fd, TAG_FD_SHOWPATTERN, field.getShowPattern());
            if (field.getCalcMode() != null) {
                this.putValue(fd, TAG_FD_CALCMODE, field.getCalcMode().value());
            }
            fds.put((Object)fd);
        }
    }

    private void makeHierarchyToJSON(JSONObject ds) throws JSONException {
        JSONArray hys = new JSONArray();
        ds.put(TAG_HIERS, (Object)hys);
        for (DSHierarchy hier : this.hiers) {
            JSONObject hy = new JSONObject();
            this.putValue(hy, "name", hier.getName());
            this.putValue(hy, "title", hier.getTitle());
            this.putValue(hy, "type", hier.getType().value());
            this.putValue(hy, TAG_HR_LEVELS, this.link(hier.getLevels(), ";"));
            this.putValue(hy, TAG_HR_PNAME, hier.getParentFieldName());
            this.putValue(hy, TAG_HR_CNAME, hier.getCodePattern());
            hys.put((Object)hy);
        }
    }

    private void makeParamterToJSON(JSONObject ds) throws JSONException {
        JSONArray pms = new JSONArray();
        ds.put(TAG_PARAMS, (Object)pms);
        for (ParameterModel pm : this.params) {
            JSONObject jsPm = new JSONObject();
            pm.toJson(jsPm);
            pms.put((Object)jsPm);
        }
    }

    private void loadFieldFromJSON(JSONObject ds) throws Exception {
        this.fields.clear();
        JSONArray fds = ds.getJSONArray(TAG_FIELDS);
        for (int i = 0; i < fds.length(); ++i) {
            JSONObject fd = fds.getJSONObject(i);
            DSField field = new DSField();
            if (!fd.isNull("name")) {
                field.setName(fd.getString("name"));
            }
            if (!fd.isNull("title")) {
                field.setTitle(fd.getString("title"));
            }
            if (!fd.isNull(TAG_FD_VALTYPE)) {
                field.setValType(fd.getInt(TAG_FD_VALTYPE));
            }
            if (!fd.isNull(TAG_FD_TYPE)) {
                field.setFieldType(FieldType.valueOf(fd.getInt(TAG_FD_TYPE)));
            }
            if (!fd.isNull(TAG_FD_KEYFD)) {
                field.setKeyField(fd.getString(TAG_FD_KEYFD));
            }
            if (!fd.isNull(TAG_FD_NAMEFD)) {
                field.setNameField(fd.getString(TAG_FD_NAMEFD));
            }
            if (!fd.isNull(TAG_FD_AGGREG)) {
                field.setAggregation(AggregationType.valueOf(fd.getInt(TAG_FD_AGGREG)));
            }
            if (!fd.isNull(TAG_FD_APPLYTYPE)) {
                field.setApplyType(ApplyType.valueOf(fd.getInt(TAG_FD_APPLYTYPE)));
            }
            if (!fd.isNull(TAG_FD_TIMEGRANULARITY)) {
                field.setTimegranularity(TimeGranularity.valueOf(fd.getInt(TAG_FD_TIMEGRANULARITY)));
            }
            if (!fd.isNull(TAG_FD_DATAPATTERN)) {
                field.setDataPattern(fd.getString(TAG_FD_DATAPATTERN));
            }
            if (!fd.isNull(TAG_FD_SHOWPATTERN)) {
                field.setShowPattern(fd.getString(TAG_FD_SHOWPATTERN));
            }
            if (!fd.isNull(TAG_FD_SOURCETYPE)) {
                field.setSourceType(fd.getString(TAG_FD_SOURCETYPE));
            }
            if (!fd.isNull(TAG_FD_SOURCEDATA)) {
                field.setSourceData(fd.getString(TAG_FD_SOURCEDATA));
            }
            if (!fd.isNull(TAG_FD_ISUNITDIM)) {
                field.setUnitDim(fd.getBoolean(TAG_FD_ISUNITDIM));
            }
            if (!fd.isNull(TAG_FD_ISTIMEKEY)) {
                field.setTimekey(fd.getBoolean(TAG_FD_ISTIMEKEY));
            }
            if (!fd.isNull(TAG_FD_MESSAGEALIAS)) {
                field.setMessageAlias(fd.getString(TAG_FD_MESSAGEALIAS));
            }
            this.getCommonFields().add(field);
        }
    }

    private void loadCalcFieldFromJSON(JSONObject ds) throws Exception {
        this.calcFields.clear();
        JSONArray fds = ds.getJSONArray(TAG_CALCFIELD);
        for (int i = 0; i < fds.length(); ++i) {
            JSONObject fd = fds.getJSONObject(i);
            DSCalcField field = new DSCalcField();
            if (!fd.isNull("name")) {
                field.setName(fd.getString("name"));
            }
            if (!fd.isNull("title")) {
                field.setTitle(fd.getString("title"));
            }
            if (!fd.isNull(TAG_FD_VALTYPE)) {
                field.setValType(fd.getInt(TAG_FD_VALTYPE));
            }
            if (!fd.isNull(TAG_FD_FORMULA)) {
                field.setFormula(fd.getString(TAG_FD_FORMULA));
            }
            if (!fd.isNull(TAG_FD_VALTYPE)) {
                field.setValType(fd.getInt(TAG_FD_VALTYPE));
            }
            if (!fd.isNull(TAG_FD_AGGREG)) {
                field.setAggregation(AggregationType.valueOf(fd.getInt(TAG_FD_AGGREG)));
            }
            if (!fd.isNull(TAG_FD_APPLYTYPE)) {
                field.setApplyType(ApplyType.valueOf(fd.getInt(TAG_FD_APPLYTYPE)));
            }
            if (!fd.isNull(TAG_FD_SHOWPATTERN)) {
                field.setShowPattern(fd.getString(TAG_FD_SHOWPATTERN));
            }
            if (!fd.isNull(TAG_FD_CALCMODE)) {
                field.setCalcMode(CalcMode.valueOf(fd.getInt(TAG_FD_CALCMODE)));
            }
            this.getCalcFields().add(field);
        }
    }

    private void loadHierarchyFromJSON(JSONObject ds) throws JSONException {
        this.hiers.clear();
        JSONArray hys = ds.getJSONArray(TAG_HIERS);
        for (int i = 0; i < hys.length(); ++i) {
            JSONObject hy = hys.getJSONObject(i);
            DSHierarchy hier = new DSHierarchy();
            if (!hy.isNull("name")) {
                hier.setName(hy.getString("name"));
            }
            if (!hy.isNull("title")) {
                hier.setTitle(hy.getString("title"));
            }
            if (!hy.isNull("type")) {
                hier.setType(DSHierarchyType.valueOf(hy.getInt("type")));
            }
            if (!hy.isNull(TAG_HR_LEVELS)) {
                String[] levels = hy.getString(TAG_HR_LEVELS).split(";");
                hier.getLevels().addAll(Arrays.asList(levels));
            }
            if (!hy.isNull(TAG_HR_PNAME)) {
                hier.setParentFieldName(hy.getString(TAG_HR_PNAME));
            }
            if (!hy.isNull(TAG_HR_CNAME)) {
                hier.setCodePattern(hy.getString(TAG_HR_CNAME));
            }
            this.getHiers().add(hier);
        }
    }

    private void loadParamFromJSON(JSONObject ds) throws JSONException {
        this.params.clear();
        JSONArray pms = ds.getJSONArray(TAG_PARAMS);
        for (int i = 0; i < pms.length(); ++i) {
            JSONObject jsPm = pms.getJSONObject(i);
            if (jsPm.isNull("title")) continue;
            ParameterModel pm = new ParameterModel();
            if (this.isOrginParameterJson(jsPm)) {
                try {
                    pm = DSParamUtils.parseOrginParameterJson(jsPm);
                }
                catch (ParameterException e) {
                    throw new JSONException(e.getMessage(), (Throwable)e);
                }
            } else {
                pm.fromJson(jsPm);
            }
            this.params.add(pm);
        }
    }

    private boolean isOrginParameterJson(JSONObject json) {
        return json.isNull("valueConfig");
    }

    protected void putValue(JSONObject json, String key, Object value) throws JSONException {
        if (value == null) {
            json.put(key, JSONObject.NULL);
        } else {
            json.put(key, value);
        }
    }

    private String link(List<String> strs, String sep) {
        StringBuffer buf = new StringBuffer();
        if (strs.size() > 0) {
            buf.append(strs.get(0));
            for (int i = 1; i < strs.size(); ++i) {
                buf.append(sep).append(strs.get(i));
            }
        }
        return buf.toString();
    }
}


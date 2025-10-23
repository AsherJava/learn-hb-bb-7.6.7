/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSDefine;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModel;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModelBuilder;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import java.util.Map;
import org.json.JSONObject;

public class ZBQueryDSModel
extends DSModel {
    public static final String TYPE = "com.jiuqi.nr.dataset.zbquery";
    private static final String EXT_FIELD_ZBQUERYDSDEFINE = "zbQueryDSDefine";
    private ZBQueryDSDefine zbQueryDSDefine;

    public String getType() {
        return TYPE;
    }

    public ZBQueryDSDefine getZbQueryDSDefine() {
        return this.zbQueryDSDefine;
    }

    protected void loadExtFromJSON(JSONObject define) throws Exception {
        if (define.isEmpty()) {
            return;
        }
        this.zbQueryDSDefine = new ZBQueryDSDefine();
        if (define.has(EXT_FIELD_ZBQUERYDSDEFINE)) {
            this.zbQueryDSDefine.fromJson(define.getJSONObject(EXT_FIELD_ZBQUERYDSDEFINE));
        } else {
            this.zbQueryDSDefine.fromJson(define);
        }
        ZBQueryModel zbQueryModel = this.zbQueryDSDefine.getZbQueryModel();
        Map<String, String> fieldAlias = this.zbQueryDSDefine.getFieldAlias();
        Map<String, String> paramAlias = this.zbQueryDSDefine.getParamAlias();
        QueryDSModelBuilder dsModelBuilder = new QueryDSModelBuilder(zbQueryModel);
        dsModelBuilder.build(true);
        QueryDSModel dsModel = dsModelBuilder.getDSModel();
        this.processDSModel(dsModel);
        JSONObject ds = new JSONObject();
        dsModel.toJSON(ds);
        String title = this.getTitle();
        String name = this.getName();
        String guid = this._getGuid();
        String descr = this.getDescr();
        this.fromJSON(ds);
        this.getFields().forEach(dsField -> {
            if (fieldAlias.containsKey(dsField.getName())) {
                dsField.setMessageAlias((String)fieldAlias.get(dsField.getName()));
            }
        });
        this.getParameterModels().forEach(parameterModel -> {
            if (paramAlias.containsKey(parameterModel.getName())) {
                parameterModel.setMessageAlias((String)paramAlias.get(parameterModel.getName()));
            }
        });
        this.setTitle(title);
        this.setName(name);
        this._setGuid(guid);
        this.setDescr(descr);
    }

    protected void saveExtToJSON(JSONObject define) throws Exception {
        if (this.zbQueryDSDefine == null) {
            return;
        }
        define.put(EXT_FIELD_ZBQUERYDSDEFINE, (Object)this.zbQueryDSDefine.toJson());
    }

    private void processDSModel(DSModel dsModel) {
        DSField rownum = dsModel.findField("SYS_ROWNUM");
        if (rownum != null) {
            dsModel.getCommonFields().remove(rownum);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.web.vo;

import com.jiuqi.nr.dafafill.model.QueryField;
import java.util.ArrayList;
import java.util.List;

public class DataSchemeDim {
    private String schemeCode;
    private QueryField period;
    private QueryField master;
    private List<QueryField> scopes;
    private List<QueryField> scenes;
    private List<String> hideDimensions;

    public String getSchemeCode() {
        return this.schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public QueryField getPeriod() {
        return this.period;
    }

    public void setPeriod(QueryField period) {
        this.period = period;
    }

    public QueryField getMaster() {
        return this.master;
    }

    public void setMaster(QueryField master) {
        this.master = master;
    }

    public List<QueryField> getScopes() {
        return this.scopes;
    }

    public void setScopes(List<QueryField> scopes) {
        this.scopes = scopes;
    }

    public List<QueryField> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<QueryField> scenes) {
        this.scenes = scenes;
    }

    public List<String> getHideDimensions() {
        return this.hideDimensions;
    }

    public void setHideDimensions(List<String> hideDimensions) {
        this.hideDimensions = hideDimensions;
    }

    public void addScope(QueryField scope) {
        if (this.scopes == null) {
            this.scopes = new ArrayList<QueryField>();
        }
        this.scopes.add(scope);
    }

    public void addScene(QueryField scene) {
        if (this.scenes == null) {
            this.scenes = new ArrayList<QueryField>();
        }
        this.scenes.add(scene);
    }

    public void addHideDimensions(String dimension) {
        if (this.hideDimensions == null) {
            this.hideDimensions = new ArrayList<String>();
        }
        this.hideDimensions.add(dimension);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.database.metadata;

import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.ProcedureParameterMode;
import org.json.JSONException;
import org.json.JSONObject;

public class LogicProcedureParameter
implements Cloneable {
    private String name;
    private ProcedureParameterMode mode;
    private int dataType;
    private static final String NAME = "name";
    private static final String MODE = "mode";
    private static final String DATATYPE = "dataType";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProcedureParameterMode getMode() {
        return this.mode;
    }

    public void setMode(ProcedureParameterMode mode) {
        this.mode = mode;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public void toJson(JSONObject json) throws JSONException {
        json.put(NAME, (Object)this.name);
        json.put(MODE, (Object)this.mode.getValue());
        json.put(DATATYPE, this.dataType);
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.name = json.getString(NAME);
        this.mode = ProcedureParameterMode.find(json.getString(MODE));
        this.dataType = json.getInt(DATATYPE);
    }

    public LogicTable clone() {
        try {
            return (LogicTable)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }
}


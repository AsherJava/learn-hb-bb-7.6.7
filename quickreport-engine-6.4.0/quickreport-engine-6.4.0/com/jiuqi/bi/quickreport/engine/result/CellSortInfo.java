/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.syntax.cell.Position;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class CellSortInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private Position position;
    private String expression;
    private static final String SORTINFO_POS = "pos";
    private static final String SORTINFO_EXPR = "expr";

    public CellSortInfo() {
    }

    public CellSortInfo(Position postion, String expression) {
        this.position = postion;
        this.expression = expression;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPostion(Position position) {
        this.position = position;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public CellSortInfo clone() {
        try {
            return (CellSortInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
    }

    public String toString() {
        return this.position + "=" + this.expression;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(SORTINFO_POS, (Object)this.position.toString());
        json.put(SORTINFO_EXPR, (Object)this.expression);
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.position = Position.valueOf((String)json.getString(SORTINFO_POS));
        this.expression = json.getString(SORTINFO_EXPR);
    }
}


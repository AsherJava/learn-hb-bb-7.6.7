/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.summary;

import java.io.Serializable;
import java.util.Map;

public class SummaryField
implements Serializable {
    public static String data_link = "data_link";
    public static String upload_state = "upload_state";
    private static final long serialVersionUID = 5320550999572017650L;
    private String dimKey;
    private String dimTitle;
    private String dimType = data_link;
    private String dimReferView;
    private String[] structValues;
    private Map<String, String> structMaps;

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public String getDimTitle() {
        return this.dimTitle;
    }

    public void setDimTitle(String dimTitle) {
        this.dimTitle = dimTitle;
    }

    public String getDimType() {
        return this.dimType;
    }

    public void setDimType(String dimType) {
        this.dimType = dimType;
    }

    public String getDimReferView() {
        return this.dimReferView;
    }

    public void setDimReferView(String dimReferView) {
        this.dimReferView = dimReferView;
    }

    public String[] getStructValues() {
        return this.structValues;
    }

    public void setStructValues(String[] structValues) {
        this.structValues = structValues;
    }

    public Map<String, String> getStructMaps() {
        return this.structMaps;
    }

    public void setStructMaps(Map<String, String> structMaps) {
        this.structMaps = structMaps;
    }
}


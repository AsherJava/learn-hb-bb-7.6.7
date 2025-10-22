/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DimensionAccessFormInfo
implements Serializable {
    private static final long serialVersionUID = -7809876868565039033L;
    private List<String> allFormKeys = new ArrayList<String>();
    private List<AccessFormInfo> accessForms = new ArrayList<AccessFormInfo>();
    private List<NoAccessFormInfo> noAccessForms = new ArrayList<NoAccessFormInfo>();

    public DimensionAccessFormInfo() {
    }

    public DimensionAccessFormInfo(List<String> allFormKeys) {
        this.allFormKeys = allFormKeys;
    }

    public List<String> getAllFormKeys() {
        return this.allFormKeys;
    }

    public void setAllFormKeys(List<String> allFormKeys) {
        this.allFormKeys = allFormKeys;
    }

    public List<AccessFormInfo> getAccessForms() {
        return this.accessForms;
    }

    public void setAccessForms(List<AccessFormInfo> accessForms) {
        this.accessForms = accessForms;
    }

    public List<NoAccessFormInfo> getNoAccessForms() {
        return this.noAccessForms;
    }

    public void setNoAccessForms(List<NoAccessFormInfo> noAccessForms) {
        this.noAccessForms = noAccessForms;
    }

    public static class AccessFormInfo
    implements Serializable {
        private Map<String, DimensionValue> dimensions;
        private List<String> formKeys;

        public AccessFormInfo(Map<String, DimensionValue> dimensions, List<String> formKeys) {
            this.dimensions = dimensions;
            this.formKeys = formKeys;
        }

        public List<String> getFormKeys() {
            return this.formKeys;
        }

        public void setFormKeys(List<String> formKeys) {
            this.formKeys = formKeys;
        }

        public Map<String, DimensionValue> getDimensions() {
            return this.dimensions;
        }

        public void setDimensions(Map<String, DimensionValue> dimensions) {
            this.dimensions = dimensions;
        }
    }

    public static class NoAccessFormInfo
    implements Serializable {
        private Map<String, DimensionValue> dimensions;
        private String formKey;
        private String reason;

        public NoAccessFormInfo(Map<String, DimensionValue> dimensions, String formKey) {
            this.dimensions = dimensions;
            this.formKey = formKey;
        }

        public NoAccessFormInfo(Map<String, DimensionValue> dimensions, String formKey, String reason) {
            this.dimensions = dimensions;
            this.formKey = formKey;
            this.reason = reason;
        }

        public Map<String, DimensionValue> getDimensions() {
            return this.dimensions;
        }

        public void setDimensions(Map<String, DimensionValue> dimensions) {
            this.dimensions = dimensions;
        }

        public String getFormKey() {
            return this.formKey;
        }

        public void setFormKey(String formKey) {
            this.formKey = formKey;
        }

        public String getReason() {
            return this.reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}


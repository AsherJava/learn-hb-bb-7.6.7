/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreFieldDTO
 */
package nr.midstore2.data.publish.internal;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreFieldDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MidstorePublishFieldCache {
    private Map<String, List<DataField>> floatFieldMap = new HashMap<String, List<DataField>>();
    private Map<String, MidstoreFieldDTO> midstoreFieldMap = new HashMap<String, MidstoreFieldDTO>();
    private Map<String, List<String>> fixFieldMap = new HashMap<String, List<String>>();
    private List<String> fixFieldCodes = new ArrayList<String>();
    private List<String> fixDimFieldCodes = new ArrayList<String>();
    private Set<String> fixFieldcodesMap = new HashSet<String>();
    private Set<String> fixDimFieldcodesMap = new HashSet<String>();

    public Map<String, List<DataField>> getFloatFieldMap() {
        if (this.floatFieldMap == null) {
            this.floatFieldMap = new HashMap<String, List<DataField>>();
        }
        return this.floatFieldMap;
    }

    public void setFloatFieldMap(Map<String, List<DataField>> floatFieldMap) {
        this.floatFieldMap = floatFieldMap;
    }

    public Map<String, MidstoreFieldDTO> getMidstoreFieldMap() {
        if (this.midstoreFieldMap == null) {
            this.midstoreFieldMap = new HashMap<String, MidstoreFieldDTO>();
        }
        return this.midstoreFieldMap;
    }

    public void setMidstoreFieldMap(Map<String, MidstoreFieldDTO> midstoreFieldMap) {
        this.midstoreFieldMap = midstoreFieldMap;
    }

    public Map<String, List<String>> getFixFieldMap() {
        if (this.fixFieldMap == null) {
            this.fixFieldMap = new HashMap<String, List<String>>();
        }
        return this.fixFieldMap;
    }

    public void setFixFieldMap(Map<String, List<String>> fixFieldMap) {
        this.fixFieldMap = fixFieldMap;
    }

    public List<String> getFixFieldCodes() {
        if (this.fixFieldCodes == null) {
            this.fixFieldCodes = new ArrayList<String>();
        }
        return this.fixFieldCodes;
    }

    public void setFixFieldCodes(List<String> fixFieldCodes) {
        this.fixFieldCodes = fixFieldCodes;
    }

    public List<String> getFixDimFieldCodes() {
        if (this.fixDimFieldCodes == null) {
            this.fixDimFieldCodes = new ArrayList<String>();
        }
        return this.fixDimFieldCodes;
    }

    public void setFixDimFieldCodes(List<String> fixDimFieldCodes) {
        this.fixDimFieldCodes = fixDimFieldCodes;
    }

    public Set<String> getFixFieldcodesMap() {
        if (this.fixFieldcodesMap == null) {
            this.fixFieldcodesMap = new HashSet<String>();
        }
        return this.fixFieldcodesMap;
    }

    public void setFixFieldcodesMap(Set<String> fixFieldcodesMap) {
        this.fixFieldcodesMap = fixFieldcodesMap;
    }

    public Set<String> getFixDimFieldcodesMap() {
        if (this.fixDimFieldcodesMap == null) {
            this.fixDimFieldcodesMap = new HashSet<String>();
        }
        return this.fixDimFieldcodesMap;
    }

    public void setFixDimFieldcodesMap(Set<String> fixDimFieldcodesMap) {
        this.fixDimFieldcodesMap = fixDimFieldcodesMap;
    }
}


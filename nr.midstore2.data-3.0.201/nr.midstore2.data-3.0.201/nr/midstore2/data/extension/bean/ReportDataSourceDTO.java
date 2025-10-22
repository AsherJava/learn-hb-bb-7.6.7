/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 */
package nr.midstore2.data.extension.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class ReportDataSourceDTO
extends MidstoreSourceDTO {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String dimensions;
    Map<String, DimensionValue> dimSetMap;
    private boolean useDimensionField;

    public ReportDataSourceDTO(MidstoreSourceDTO sourceDTO) {
        this.setKey(sourceDTO.getKey());
        this.setSchemeKey(sourceDTO.getSchemeKey());
        this.setSourceType(sourceDTO.getSourceType());
        this.setExtendData(sourceDTO.getExtendData());
        this.setOrder(sourceDTO.getOrder());
        this.setUpdateTime(sourceDTO.getUpdateTime());
        if (StringUtils.isNotEmpty((CharSequence)this.getExtendData())) {
            JSONObject json = new JSONObject(this.getExtendData());
            if (json.has("taskKey")) {
                this.taskKey = json.getString("taskKey");
            }
            if (json.has("dimensionSet")) {
                JSONObject dimensions1 = json.getJSONObject("dimensionSet");
                this.dimensions = dimensions1.toString();
                for (String dimName : dimensions1.keySet()) {
                    JSONObject dimensions2 = dimensions1.getJSONObject(dimName);
                    String value = dimensions2.getString("value");
                    DimensionValue dimValue = new DimensionValue();
                    dimValue.setName(dimName);
                    dimValue.setValue(value);
                    dimValue.setType(0);
                    if (this.getDimSetMap().containsKey(dimName)) continue;
                    this.getDimSetMap().put(dimName, dimValue);
                }
            }
            if (json.has("hasDimension")) {
                this.useDimensionField = json.getBoolean("hasDimension");
            }
        }
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
        JSONObject json = new JSONObject(this.getExtendData());
        json.put("taskKey", (Object)taskKey);
        this.extendData = json.toString();
    }

    public String getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
        JSONObject json = new JSONObject(this.getExtendData());
        json.put("dimensions", (Object)dimensions);
        this.extendData = json.toString();
    }

    public boolean isUseDimensionField() {
        return this.useDimensionField;
    }

    public void setUseDimensionField(boolean useDimensionField) {
        this.useDimensionField = useDimensionField;
        JSONObject json = new JSONObject(this.getExtendData());
        json.put("useDimensionField", useDimensionField);
        this.extendData = json.toString();
    }

    public Map<String, DimensionValue> getDimSetMap() {
        if (this.dimSetMap == null) {
            this.dimSetMap = new HashMap<String, DimensionValue>();
        }
        return this.dimSetMap;
    }

    public void setDimSetMap(Map<String, DimensionValue> dimSetMap) {
        this.dimSetMap = dimSetMap;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package nr.single.map.configurations.internal.bean;

import com.jiuqi.nr.definition.common.DataRegionKind;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.configurations.internal.bean.DictionaryData;
import org.springframework.util.CollectionUtils;

public class RegionDictionaryData
implements Serializable {
    private static final long serialVersionUID = 2651366247022759356L;
    private int top;
    private int bottom;
    private int left;
    private int right;
    private DataRegionKind regionKind;
    private Map<String, List<DictionaryData>> enumData;

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return this.bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return this.right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public DataRegionKind getRegionKind() {
        return this.regionKind;
    }

    public void setRegionKind(DataRegionKind regionKind) {
        this.regionKind = regionKind;
    }

    public Map<String, List<DictionaryData>> getEnumData() {
        return this.enumData;
    }

    public void setEnumData(Map<String, List<DictionaryData>> enumData) {
        this.enumData = enumData;
    }

    public void put(String key, List<DictionaryData> enumData) {
        if (CollectionUtils.isEmpty(this.enumData)) {
            this.enumData = new HashMap<String, List<DictionaryData>>();
        }
        this.enumData.put(key, enumData);
    }
}


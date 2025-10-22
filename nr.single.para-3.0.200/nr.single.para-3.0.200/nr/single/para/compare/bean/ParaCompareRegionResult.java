/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nr.single.para.compare.definition.CompareDataFieldDTO;

public class ParaCompareRegionResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String infoKey;
    private String compareDataKey;
    private Integer singleFloatingId;
    private String newTableKey;
    private List<CompareDataFieldDTO> addFieldItems;
    private List<CompareDataFieldDTO> updateItems;

    public List<CompareDataFieldDTO> getAddFieldItems() {
        if (this.addFieldItems == null) {
            this.addFieldItems = new ArrayList<CompareDataFieldDTO>();
        }
        return this.addFieldItems;
    }

    public void setAddFieldItems(List<CompareDataFieldDTO> addFieldItems) {
        this.addFieldItems = addFieldItems;
    }

    public List<CompareDataFieldDTO> getUpdateItems() {
        if (this.updateItems == null) {
            this.updateItems = new ArrayList<CompareDataFieldDTO>();
        }
        return this.updateItems;
    }

    public void setUpdateItems(List<CompareDataFieldDTO> updateItems) {
        this.updateItems = updateItems;
    }

    public String getInfoKey() {
        return this.infoKey;
    }

    public void setInfoKey(String infoKey) {
        this.infoKey = infoKey;
    }

    public String getCompareDataKey() {
        return this.compareDataKey;
    }

    public void setCompareDataKey(String compareDataKey) {
        this.compareDataKey = compareDataKey;
    }

    public Integer getSingleFloatingId() {
        return this.singleFloatingId;
    }

    public void setSingleFloatingId(Integer singleFloatingId) {
        this.singleFloatingId = singleFloatingId;
    }

    public String getNewTableKey() {
        return this.newTableKey;
    }

    public void setNewTableKey(String newTableKey) {
        this.newTableKey = newTableKey;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.para.compare.bean;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.compare.bean.ParaImportItemResult;
import nr.single.para.compare.definition.ICompareData;
import org.apache.commons.lang3.StringUtils;

public class ParaImportInfoResult
extends ParaImportItemResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int successCount;
    private String compareInfoKey;
    private List<String> statMessage;
    private List<ParaImportInfoResult> items;
    private Map<String, ParaImportInfoResult> finder;
    private Map<String, ParaImportInfoResult> codeFinder;

    public void addItem(ParaImportInfoResult item) {
        if (!this.getFinder().containsKey(item.getKey())) {
            this.getFinder().put(item.getKey(), item);
            this.getItems().add(item);
        }
        if (!this.getCodeFinder().containsKey(item.getCode())) {
            this.getCodeFinder().put(item.getCode(), item);
        }
    }

    public void insert(ParaImportInfoResult item, int index) {
        if (!this.getFinder().containsKey(item.getKey())) {
            this.getFinder().put(item.getKey(), item);
            this.getItems().add(index, item);
        }
        if (!this.getCodeFinder().containsKey(item.getCode())) {
            this.getCodeFinder().put(item.getCode(), item);
        }
    }

    public String getCompareInfoKey() {
        return this.compareInfoKey;
    }

    public void setCompareInfoKey(String compareInfoKey) {
        this.compareInfoKey = compareInfoKey;
    }

    public List<ParaImportInfoResult> getItems() {
        if (this.items == null) {
            this.items = new ArrayList<ParaImportInfoResult>();
        }
        return this.items;
    }

    public void setItems(List<ParaImportInfoResult> items) {
        this.items = items;
    }

    public int getSuccessCount() {
        return this.successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public Map<String, ParaImportInfoResult> getFinder() {
        if (this.finder == null) {
            this.finder = new HashMap<String, ParaImportInfoResult>();
        }
        return this.finder;
    }

    public void setFinder(Map<String, ParaImportInfoResult> finder) {
        this.finder = finder;
    }

    public Map<String, ParaImportInfoResult> getCodeFinder() {
        if (this.codeFinder == null) {
            this.codeFinder = new HashMap<String, ParaImportInfoResult>();
        }
        return this.codeFinder;
    }

    public void setCodeFinder(Map<String, ParaImportInfoResult> codeFinder) {
        this.codeFinder = codeFinder;
    }

    public List<String> getStatMessage() {
        if (this.statMessage == null) {
            this.statMessage = new ArrayList<String>();
        }
        return this.statMessage;
    }

    public void setStatMessage(List<String> statMessage) {
        this.statMessage = statMessage;
    }

    public void copyForm(ICompareData data) {
        this.setKey(data.getKey());
        this.setCompareDataKey(data.getKey());
        this.setData(data);
        this.setCompareInfoKey(data.getInfoKey());
        this.setDataType(data.getDataType());
        this.setChangeType(data.getChangeType());
        this.setNetCode(data.getNetCode());
        this.setNetKey(data.getNetKey());
        this.setNetTitle(data.getNetTitle());
        this.setSingleCode(data.getSingleCode());
        this.setSingleTitle(data.getSingleTitle());
        this.setUpdateType(data.getUpdateType());
        this.setUpdateTime(Instant.now());
        this.setCode(data.getNetCode());
        if (StringUtils.isEmpty((CharSequence)data.getNetCode())) {
            this.setCode(data.getNetKey());
        }
    }
}


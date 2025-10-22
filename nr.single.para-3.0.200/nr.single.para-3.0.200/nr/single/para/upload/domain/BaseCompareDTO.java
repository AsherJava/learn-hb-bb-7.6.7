/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.upload.domain;

import com.jiuqi.bi.util.StringUtils;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareUpdateType;

public class BaseCompareDTO {
    private String key;
    private String singleCode;
    private String singleTitle;
    private String matchKey;
    private String netKey;
    private String netCode;
    private String netTitle;
    private CompareChangeType compareContent;
    private CompareUpdateType coverType;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSingleCode() {
        return this.singleCode;
    }

    public void setSingleCode(String singleCode) {
        this.singleCode = singleCode;
    }

    public String getSingleTitle() {
        return this.singleTitle;
    }

    public void setSingleTitle(String singleTitle) {
        this.singleTitle = singleTitle;
    }

    public String getNetKey() {
        return this.netKey;
    }

    public void setNetKey(String netKey) {
        this.netKey = netKey;
    }

    public String getNetCode() {
        return this.netCode;
    }

    public void setNetCode(String netCode) {
        this.netCode = netCode;
    }

    public String getNetTitle() {
        return this.netTitle;
    }

    public void setNetTitle(String netTitle) {
        this.netTitle = netTitle;
    }

    public CompareChangeType getCompareContent() {
        return this.compareContent;
    }

    public void setCompareContent(CompareChangeType compareContent) {
        this.compareContent = compareContent;
    }

    public CompareUpdateType getCoverType() {
        return this.coverType;
    }

    public void setCoverType(CompareUpdateType coverType) {
        this.coverType = coverType;
    }

    public String getMatchKey() {
        return this.matchKey;
    }

    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }

    public boolean equals(Object o) {
        BaseCompareDTO compare = (BaseCompareDTO)o;
        return compare != null && StringUtils.equals((String)this.key, (String)compare.key);
    }
}


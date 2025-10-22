/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.bean;

import java.io.Serializable;
import java.time.Instant;
import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;

public class ParaImportItemResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String key;
    private String code;
    private String message;
    private String singleCode;
    private String singleTitle;
    private String netCode;
    private String netKey;
    private String netTitle;
    private boolean success;
    private CompareDataType dataType;
    private CompareUpdateType updateType;
    private CompareChangeType changeType;
    private String compareDataKey;
    private String parentCompareKey;
    private String parentNetKey;
    private Instant updateTime;
    private ICompareData data;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getNetCode() {
        return this.netCode;
    }

    public void setNetCode(String netCode) {
        this.netCode = netCode;
    }

    public String getNetKey() {
        return this.netKey;
    }

    public void setNetKey(String netKey) {
        this.netKey = netKey;
    }

    public String getNetTitle() {
        return this.netTitle;
    }

    public void setNetTitle(String netTitle) {
        this.netTitle = netTitle;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CompareDataType getDataType() {
        return this.dataType;
    }

    public void setDataType(CompareDataType dataType) {
        this.dataType = dataType;
    }

    public String getCompareDataKey() {
        return this.compareDataKey;
    }

    public void setCompareDataKey(String compareDataKey) {
        this.compareDataKey = compareDataKey;
    }

    public String getParentCompareKey() {
        return this.parentCompareKey;
    }

    public void setParentCompareKey(String parentCompareKey) {
        this.parentCompareKey = parentCompareKey;
    }

    public CompareUpdateType getUpdateType() {
        return this.updateType;
    }

    public void setUpdateType(CompareUpdateType updateType) {
        this.updateType = updateType;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public ICompareData getData() {
        return this.data;
    }

    public void setData(ICompareData data) {
        this.data = data;
    }

    public CompareChangeType getChangeType() {
        return this.changeType;
    }

    public void setChangeType(CompareChangeType changeType) {
        this.changeType = changeType;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentNetKey() {
        return this.parentNetKey;
    }

    public void setParentNetKey(String parentNetKey) {
        this.parentNetKey = parentNetKey;
    }
}


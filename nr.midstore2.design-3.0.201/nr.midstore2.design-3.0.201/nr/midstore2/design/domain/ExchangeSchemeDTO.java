/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nvwa.midstore.core.definition.common.ExchangeModeType
 *  com.jiuqi.nvwa.midstore.core.definition.common.StorageModeType
 */
package nr.midstore2.design.domain;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nvwa.midstore.core.definition.common.ExchangeModeType;
import com.jiuqi.nvwa.midstore.core.definition.common.StorageModeType;
import java.util.HashMap;
import java.util.Map;
import nr.midstore2.design.domain.SchemeBaseDTO;

public class ExchangeSchemeDTO
extends SchemeBaseDTO {
    protected String desc;
    private ExchangeModeType exchangeMode;
    private StorageModeType storageMode;
    private String storageInfo;
    private String dataBaseLink;
    private String tablePrefix;
    private String taskKey;
    private String groupKey;
    private String configKey;
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ExchangeModeType getExchangeMode() {
        return this.exchangeMode;
    }

    public void setExchangeMode(ExchangeModeType exchangeMode) {
        this.exchangeMode = exchangeMode;
    }

    public StorageModeType getStorageMode() {
        return this.storageMode;
    }

    public void setStorageMode(StorageModeType storageMode) {
        this.storageMode = storageMode;
    }

    public String getStorageInfo() {
        return this.storageInfo;
    }

    public void setStorageInfo(String storageInfo) {
        this.storageInfo = storageInfo;
    }

    public String getDataBaseLink() {
        return this.dataBaseLink;
    }

    public void setDataBaseLink(String dataBaseLink) {
        this.dataBaseLink = dataBaseLink;
    }

    public String getTablePrefix() {
        return this.tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String getGroupKey() {
        return this.groupKey;
    }

    @Override
    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }
}


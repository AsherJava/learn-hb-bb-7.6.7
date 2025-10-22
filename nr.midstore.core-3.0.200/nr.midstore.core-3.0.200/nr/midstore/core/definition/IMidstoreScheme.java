/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition;

import nr.midstore.core.definition.IMidstoreData;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.common.StorageModeType;

public interface IMidstoreScheme
extends IMidstoreData {
    public String getCode();

    public String getTitle();

    public String getDesc();

    public ExchangeModeType getExchangeMode();

    public StorageModeType getStorageMode();

    public String getStorageInfo();

    public String getDataBaseLink();

    public String getTablePrefix();

    public String getTaskKey();

    public String getGroupKey();

    public String getConfigKey();

    public String getDimensions();

    public String getSceneCode();
}


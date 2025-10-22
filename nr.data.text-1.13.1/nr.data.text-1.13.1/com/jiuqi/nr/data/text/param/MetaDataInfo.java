/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.data.text.param;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetaDataInfo {
    private List<IMetaData> allMetaData = new ArrayList<IMetaData>();
    private int dwIndex;
    private int periodIndex;
    private Set<Integer> fileIndex = new HashSet<Integer>();
    private Map<Integer, String> index2RefEntityId = new HashMap<Integer, String>();

    public MetaDataInfo(List<IMetaData> allMetaData) {
        this.allMetaData = allMetaData;
        for (IMetaData fieldMeta : allMetaData) {
            if (fieldMeta.getCode().equals("MDCODE")) {
                this.dwIndex = allMetaData.indexOf(fieldMeta);
                continue;
            }
            if (fieldMeta.getCode().equals("DATATIME")) {
                this.periodIndex = allMetaData.indexOf(fieldMeta);
                continue;
            }
            if (fieldMeta.isEnumType()) {
                this.index2RefEntityId.put(allMetaData.indexOf(fieldMeta), fieldMeta.getEntityId());
                continue;
            }
            if (fieldMeta.getDataField().getDataFieldType() != DataFieldType.FILE && fieldMeta.getDataField().getDataFieldType() != DataFieldType.PICTURE) continue;
            this.fileIndex.add(allMetaData.indexOf(fieldMeta));
        }
    }

    public List<IMetaData> getAllMetaData() {
        return this.allMetaData;
    }

    public int getPeriodIndex() {
        return this.periodIndex;
    }

    public int getDwIndex() {
        return this.dwIndex;
    }

    public Set<Integer> getFileIndex() {
        return this.fileIndex;
    }

    public Map<Integer, String> getIndex2RefEntityId() {
        return this.index2RefEntityId;
    }
}


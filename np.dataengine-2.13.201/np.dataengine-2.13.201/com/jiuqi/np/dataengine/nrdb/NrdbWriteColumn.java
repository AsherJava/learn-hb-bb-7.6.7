/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.impl.nrdb.writers.DatetimeColumnWriter
 *  com.jiuqi.nvwa.dataengine.impl.nrdb.writers.NrdbColumnWriter
 *  com.jiuqi.nvwa.encryption.common.EncryptionException
 *  com.jiuqi.nvwa.memdb.api.record.ArrayRecord
 */
package com.jiuqi.np.dataengine.nrdb;

import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nvwa.dataengine.impl.nrdb.writers.DatetimeColumnWriter;
import com.jiuqi.nvwa.dataengine.impl.nrdb.writers.NrdbColumnWriter;
import com.jiuqi.nvwa.encryption.common.EncryptionException;
import com.jiuqi.nvwa.memdb.api.record.ArrayRecord;
import java.util.List;

public class NrdbWriteColumn {
    private String name;
    private String dimensionName;
    private List<Integer> indexes;
    private int dataType;
    private int metaIndex;
    private NrdbColumnWriter writer;
    private String sceneId;

    public NrdbWriteColumn(String dimensionName, String name, int dataType, int metaIndex, String sceneId) {
        this.dimensionName = dimensionName;
        this.name = name;
        this.dataType = dataType;
        this.metaIndex = metaIndex;
        this.writer = this.createWriter(dataType, metaIndex);
        this.sceneId = sceneId;
    }

    public NrdbWriteColumn(String name, List<Integer> indexes, int dataType, int metaIndex, String sceneId) {
        this.name = name;
        this.indexes = indexes;
        this.dataType = dataType;
        this.metaIndex = metaIndex;
        this.writer = this.createWriter(dataType, metaIndex);
        this.sceneId = sceneId;
    }

    public void writerValue(QueryContext qContext, ArrayRecord record, Object value) {
        if (value != null && this.dataType == 6) {
            try {
                value = qContext.encrypt(this.sceneId, value.toString());
            }
            catch (EncryptionException e) {
                qContext.getMonitor().exception((Exception)((Object)e));
            }
        }
        this.writer.writeValue(record, value);
    }

    private NrdbColumnWriter createWriter(int dataType, int metaIndex) {
        if (dataType == 5 || dataType == 2) {
            return new DatetimeColumnWriter(metaIndex, dataType);
        }
        return new NrdbColumnWriter(metaIndex, dataType);
    }

    public String getName() {
        return this.name;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public List<Integer> getIndexes() {
        return this.indexes;
    }

    public int getDataType() {
        return this.dataType;
    }

    public int getMetaIndex() {
        return this.metaIndex;
    }
}


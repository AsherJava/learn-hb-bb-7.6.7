/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.idx.DSIndex;
import com.jiuqi.bi.dataset.manager.TimeKeyHelper;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.util.StringUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FieldStatDevice {
    protected final int srcIndex;
    protected final int destIndex;
    protected int[] dimColIdx;
    protected int[] parentColIdx;
    protected int[] srcKeyColIdx;
    private List<Integer> refDimKeyColIdx;
    private Set<RecordBuffer> refDimKeyBuffer;
    private TimeGranularity msTimeGranularity;
    private int sys_timekey_index = -1;
    private Set<String> statTimekey;
    protected BIDataSetImpl dataset;

    public FieldStatDevice(int srcIndex, int destIndex, int[] dimColIdx, int[] parentColIdx) {
        this.srcIndex = srcIndex;
        this.destIndex = destIndex;
        this.dimColIdx = dimColIdx;
        this.parentColIdx = parentColIdx;
    }

    public FieldStatDevice(int srcIndex, int destIndex) {
        this(srcIndex, destIndex, null, null);
    }

    public void setDataSet(BIDataSetImpl dataset) {
        this.dataset = dataset;
        this.sys_timekey_index = this.dataset.getMetadata().indexOf("SYS_TIMEKEY");
    }

    public void setRefDimKeyColIdx(List<Integer> refDimKeyInDest) {
        this.refDimKeyColIdx = refDimKeyInDest;
        this.refDimKeyBuffer = new HashSet<RecordBuffer>();
    }

    public void setSrcKeyColIdx(int[] srcKeyColIdx) {
        this.srcKeyColIdx = srcKeyColIdx;
    }

    public void setMsTimeGranularity(TimeGranularity msTimeGranularity) {
        this.msTimeGranularity = msTimeGranularity;
        this.statTimekey = new HashSet<String>();
    }

    public void init(Object[] dest) {
    }

    public abstract void stat(BIDataRow var1, RecordBuffer var2);

    public abstract Object toValue(RecordBuffer var1);

    boolean hasParent(int colIdx, int parentColIdx, BIDataRow row) {
        List<Integer> keyrows;
        if (parentColIdx == -1) {
            return false;
        }
        Object parentValue = row.getValue(parentColIdx);
        if (parentValue == null) {
            return false;
        }
        DSIndex dsIdx = this.dataset.getDSIndex();
        if (this.srcKeyColIdx != null && this.srcKeyColIdx.length > 0) {
            boolean containInSrc = false;
            Object[] keyD = new Object[this.srcKeyColIdx.length];
            for (int i = 0; i < this.srcKeyColIdx.length; ++i) {
                if (this.srcKeyColIdx[i] == colIdx) {
                    keyD[i] = parentValue;
                    containInSrc = true;
                    continue;
                }
                keyD[i] = row.getValue(this.srcKeyColIdx[i]);
            }
            if (containInSrc) {
                keyrows = dsIdx.search(this.srcKeyColIdx, keyD);
            } else {
                int[] cols = new int[this.srcKeyColIdx.length + 1];
                Object[] keys = new Object[keyD.length + 1];
                System.arraycopy(this.srcKeyColIdx, 0, cols, 0, this.srcKeyColIdx.length);
                System.arraycopy(keyD, 0, keys, 0, keyD.length);
                cols[cols.length - 1] = colIdx;
                keys[keys.length - 1] = parentValue;
                keyrows = dsIdx.search(cols, keys);
            }
        } else {
            keyrows = dsIdx.search(colIdx, parentValue);
        }
        return keyrows.size() > 0;
    }

    protected boolean needStat(BIDataRow dataRow) {
        if (this.parentColIdx != null && this.dimColIdx != null) {
            for (int i = 0; i < this.parentColIdx.length; ++i) {
                if (!this.hasParent(this.dimColIdx[i], this.parentColIdx[i], dataRow)) continue;
                return false;
            }
        }
        if (this.refDimKeyColIdx != null && !this.refDimKeyColIdx.isEmpty()) {
            ArrayList<Integer> destKeyDimList = new ArrayList<Integer>();
            for (int i = 0; i < this.refDimKeyColIdx.size(); ++i) {
                destKeyDimList.add(i);
            }
            RecordBuffer rb = new RecordBuffer(this.refDimKeyColIdx.size(), destKeyDimList);
            if (this.refDimKeyColIdx.size() == 1 && this.refDimKeyColIdx.get(0) == -1) {
                rb.setValue(0, "*");
            } else {
                for (int i = 0; i < this.refDimKeyColIdx.size(); ++i) {
                    rb.setValue(i, dataRow.getValue(this.refDimKeyColIdx.get(i)));
                }
            }
            if (this.refDimKeyBuffer.contains(rb)) {
                return false;
            }
            this.refDimKeyBuffer.add(rb);
        }
        if (this.msTimeGranularity != null) {
            String timekey = dataRow.getString(this.sys_timekey_index);
            try {
                if (StringUtils.isNotEmpty((String)timekey)) {
                    timekey = TimeKeyHelper.convertTo(timekey, this.msTimeGranularity);
                }
            }
            catch (ParseException parseException) {
                // empty catch block
            }
            if (this.statTimekey.contains(timekey)) {
                return false;
            }
            this.statTimekey.add(timekey);
        }
        return true;
    }
}


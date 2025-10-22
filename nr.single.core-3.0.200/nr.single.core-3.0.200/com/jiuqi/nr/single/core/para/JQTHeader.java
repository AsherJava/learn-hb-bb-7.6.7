/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nr.single.core.para.util.WriteUtil;
import java.util.Calendar;

public class JQTHeader {
    private String identity;
    private int version;
    private String distributeTime;
    private int[] blocksPrt;
    private double creationTime;
    private int[] reserved;
    public static final int SIZE = 96;
    private int creationYear;
    private int creationMonth;
    private int creationDay;

    public int getSize() {
        return 96;
    }

    public void init(Stream mask0) throws StreamException {
        this.setIdentity(ReadUtil.readStringValue(mask0, 8));
        this.setVersion(ReadUtil.readIntValue(mask0));
        this.setDistributeTime(ReadUtil.readStringValue(mask0, 8));
        this.setBlocksPrt(ReadUtil.readArrayValue2(mask0, 6));
        this.setCreationTime(mask0.readDouble());
        this.setReserved(ReadUtil.readArrayValue2(mask0, 11));
        Calendar cal = Calendar.getInstance();
        cal.set(2000, 0, 0);
        int days = (int)this.creationTime;
        int seccords = (int)((this.creationTime - (double)days) * 24.0 * 60.0 * 60.0);
        cal.add(6, days);
        cal.add(13, seccords);
        this.creationYear = cal.get(1);
        this.creationMonth = cal.get(2) + 1;
        this.creationDay = cal.get(5);
    }

    public void save(Stream mask0) throws StreamException {
        WriteUtil.writeStringValue(mask0, this.getIdentity());
        WriteUtil.writeIntValue(mask0, this.version);
        WriteUtil.writeStringValue(mask0, this.getDistributeTime());
        WriteUtil.writeArrayValue2(mask0, this.getBlocksPrt());
        mask0.writeDouble(this.getCreationTime());
        WriteUtil.writeArrayValue2(mask0, this.getReserved());
    }

    public final String getIdentity() {
        return this.identity;
    }

    public final void setIdentity(String identity_0) {
        this.identity = identity_0;
    }

    public final int getVersion() {
        return this.version;
    }

    public final void setVersion(int version_0) {
        this.version = version_0;
    }

    public final String getDistributeTime() {
        return this.distributeTime;
    }

    public final void setDistributeTime(String distributeTime_0) {
        this.distributeTime = distributeTime_0;
    }

    public final int[] getBlocksPrt() {
        return this.blocksPrt;
    }

    public final void setBlocksPrt(int[] blocksPrt_0) {
        this.blocksPrt = blocksPrt_0;
    }

    public final double getCreationTime() {
        return this.creationTime;
    }

    public final void setCreationTime(double creationTime_0) {
        this.creationTime = creationTime_0;
    }

    public final int[] getReserved() {
        return this.reserved;
    }

    public final void setReserved(int[] reserved_0) {
        this.reserved = reserved_0;
    }

    public int getCreationYear() {
        return this.creationYear;
    }

    public void setCreationYear(int creationYear) {
        this.creationYear = creationYear;
    }

    public int getCreationMonth() {
        return this.creationMonth;
    }

    public void setCreationMonth(int creationMonth) {
        this.creationMonth = creationMonth;
    }

    public int getCreationDay() {
        return this.creationDay;
    }

    public void setCreationDay(int creationDay) {
        this.creationDay = creationDay;
    }
}


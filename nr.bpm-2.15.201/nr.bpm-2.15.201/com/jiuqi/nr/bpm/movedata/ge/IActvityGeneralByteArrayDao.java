/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.ge;

import com.jiuqi.nr.bpm.movedata.NrActvityGeneralByteArray;
import java.util.List;

public interface IActvityGeneralByteArrayDao {
    public boolean batchInsert(List<NrActvityGeneralByteArray> var1);

    public void deleteById(String var1);

    public List<NrActvityGeneralByteArray> queryById(String var1);
}


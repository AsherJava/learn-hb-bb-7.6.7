/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.dao;

import nr.single.map.configurations.bean.SingleFileInfo;

public interface FileInfoDao {
    public void insert(SingleFileInfo var1);

    public void updata(SingleFileInfo var1);

    public void deleteBykey(String var1);

    public SingleFileInfo query(String var1);
}


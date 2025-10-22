/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.service;

import java.io.InputStream;
import java.util.List;
import nr.single.map.configurations.bean.SingleFileInfo;

public interface MappingFileService {
    public SingleFileInfo queryFileInfo(String var1);

    public List<SingleFileInfo> queryFileInfoInScheme(String var1);

    public byte[] queryFile(String var1);

    public String insertFile(InputStream var1);

    public void insertMappingFileInfo(List<SingleFileInfo> var1);
}


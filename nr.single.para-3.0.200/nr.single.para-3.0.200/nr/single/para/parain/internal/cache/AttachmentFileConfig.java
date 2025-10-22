/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package nr.single.para.parain.internal.cache;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.file.FileAreaConfig;

public class AttachmentFileConfig
implements FileAreaConfig {
    private String areaName = "";
    public static final String PARTITION = "NR_LINK_TEMP";
    private static long FILE_MAX_SIZE = 0xC800000L;
    private static final Boolean ENABLE_FAST = false;
    private static final Boolean ENABLE_ENCRYPT = true;
    private static final Boolean ENABLE_RECYCLEBIN = true;

    public String getName() {
        if (StringUtils.isEmpty((String)this.areaName)) {
            return PARTITION;
        }
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public long getFileMaxSize() {
        return FILE_MAX_SIZE;
    }

    public void setFileMaxSize(long fileMaxSize) {
        FILE_MAX_SIZE = fileMaxSize;
    }

    public String getDesc() {
        return "\u9644\u4ef6\u6307\u6807\u9ed8\u8ba4\u6a21\u677f";
    }
}


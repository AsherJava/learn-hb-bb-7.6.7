/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package nr.midstore2.core.internal.util.service;

import com.jiuqi.nr.file.FileAreaConfig;
import org.springframework.stereotype.Component;

@Component
public class MidstoreFileAreaConfig
implements FileAreaConfig {
    public static final String MIDSTORE_AREA = "MIDSTORE";
    public static final String MIDSTORE_AREA_DESC = "NR\u4e2d\u95f4\u5e93";

    public String getName() {
        return MIDSTORE_AREA;
    }

    public long getMaxFileSize() {
        return 0x40000000L;
    }

    public String getDesc() {
        return MIDSTORE_AREA_DESC;
    }
}


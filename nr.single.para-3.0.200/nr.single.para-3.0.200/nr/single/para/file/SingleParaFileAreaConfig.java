/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package nr.single.para.file;

import com.jiuqi.nr.file.FileAreaConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SingleParaFileAreaConfig
implements FileAreaConfig {
    public static final String SINGLEPARA_UPLOAD_AREA = "SINGLEPARA";
    public static final String SINGLEPARA_UPLOAD_AREA_DESC = "\u7528\u4e8eJIO\u53c2\u6570\u4e0a\u4f20";
    @Value(value="${nr.single.para.upload.fileSize:1073741824}")
    private long MAXFILESIZE;

    public String getName() {
        return SINGLEPARA_UPLOAD_AREA;
    }

    public long getMaxFileSize() {
        return this.MAXFILESIZE;
    }

    public String getDesc() {
        return SINGLEPARA_UPLOAD_AREA_DESC;
    }
}


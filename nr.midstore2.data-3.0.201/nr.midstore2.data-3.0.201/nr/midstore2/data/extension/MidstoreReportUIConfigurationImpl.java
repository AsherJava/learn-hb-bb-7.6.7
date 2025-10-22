/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.midstore.extension.IMidstoreUIConfiguration
 */
package nr.midstore2.data.extension;

import com.jiuqi.nvwa.midstore.extension.IMidstoreUIConfiguration;
import org.springframework.stereotype.Component;

@Component
public class MidstoreReportUIConfigurationImpl
implements IMidstoreUIConfiguration {
    public String getAppTitle() {
        return "\u62a5\u8868\u6307\u6807";
    }

    public String getAppName() {
        return "midstore-zb-report-plugin";
    }

    public String getProdLine() {
        return "@nr";
    }
}


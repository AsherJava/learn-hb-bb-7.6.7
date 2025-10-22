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
public class MidstoreReportPropertyUIConfigImpl
implements IMidstoreUIConfiguration {
    public String getAppTitle() {
        return "\u62a5\u8868\u6307\u6807";
    }

    public String getAppName() {
        return "nr-midstore-property-plugin";
    }

    public String getProdLine() {
        return "@nr";
    }
}


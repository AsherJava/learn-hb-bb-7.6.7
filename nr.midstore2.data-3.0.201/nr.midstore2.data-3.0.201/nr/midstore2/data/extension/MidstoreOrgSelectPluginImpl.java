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
public class MidstoreOrgSelectPluginImpl
implements IMidstoreUIConfiguration {
    public String getAppTitle() {
        return "\u62a5\u8868\u6307\u6807\u5355\u4f4d\u9009\u62e9\u5668";
    }

    public String getAppName() {
        return "nr-midstore-org-select-plugin";
    }

    public String getProdLine() {
        return "@nr";
    }
}


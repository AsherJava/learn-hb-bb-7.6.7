/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.Properties;
import org.json.JSONObject;

public class WorksheetWriterSetting
implements Cloneable,
Serializable {
    private static final long serialVersionUID = -1393374950321735222L;
    public static final String PRINT_SETTING = "printSetting";
    private PrintSetting printSetting;

    public PrintSetting getPrintSetting() {
        return this.printSetting;
    }

    public void setPrintSetting(PrintSetting printSetting) {
        this.printSetting = printSetting;
    }

    public void fromProperties(Properties properties) {
        if (properties == null) {
            return;
        }
        String printSettingStr = properties.getProperty(PRINT_SETTING);
        if (StringUtils.isNotEmpty(printSettingStr)) {
            this.printSetting = new PrintSetting();
            this.printSetting.fromJson(new JSONObject(printSettingStr));
        }
    }

    public Properties toProperties() {
        Properties properties = new Properties();
        if (this.printSetting != null) {
            properties.put(PRINT_SETTING, this.printSetting.toJson().toString());
        }
        return properties;
    }
}


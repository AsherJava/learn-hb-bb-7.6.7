/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.internal.icon;

import com.jiuqi.nr.formtype.internal.icon.AbstractIconSourceProvider;
import java.io.File;
import org.springframework.util.StringUtils;

public class CommonIconSource
extends AbstractIconSourceProvider {
    public static final String ICON_ROOT_FOLDER = "static" + File.separator + "icons";
    private String schemeKey;
    private String folderPath;
    private String propertiesFileName;

    public CommonIconSource(String schemeKey, String folderPath, String propertiesFileName) {
        this.schemeKey = schemeKey;
        this.folderPath = ICON_ROOT_FOLDER + File.separator + folderPath;
        this.propertiesFileName = StringUtils.hasText(propertiesFileName) ? propertiesFileName : "zkeys.properties";
    }

    @Override
    public String getSchemeKey() {
        return this.schemeKey;
    }

    @Override
    protected String getIconPath(String fileName) {
        return this.folderPath + File.separator + fileName;
    }

    @Override
    protected String getPropertiesPath() {
        return this.folderPath + File.separator + this.propertiesFileName;
    }
}


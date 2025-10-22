/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.internal.icon;

import com.jiuqi.nr.formtype.common.FormTypeUtils;
import com.jiuqi.nr.formtype.internal.icon.AbstractIconSourceProvider;
import com.jiuqi.nr.formtype.internal.icon.IconSourceProvider;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;

public class DefaultIconSource
implements IconSourceProvider {
    public static final String SCHEME_KEY = "default-icon-source-provider";
    public static final String ICON_KEY = "deficon";
    private static final String FILE_PATH = "static" + File.separator + "default-icons";

    @Override
    public String getSchemeKey() {
        return SCHEME_KEY;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String getBase64Icon(String key) throws IOException {
        String fileName = "deficon.svg";
        String iconType = FormTypeUtils.substring(fileName, fileName.lastIndexOf(".") + 1);
        ClassPathResource resource = new ClassPathResource(FILE_PATH + File.separator + fileName);
        try (InputStream inputStream = resource.getInputStream();){
            String string = AbstractIconSourceProvider.toBase64(inputStream, iconType);
            return string;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Properties getProperties() throws IOException {
        return new Properties();
    }
}


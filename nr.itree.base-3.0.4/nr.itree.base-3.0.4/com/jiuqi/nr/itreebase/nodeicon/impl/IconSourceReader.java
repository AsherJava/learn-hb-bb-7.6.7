/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.itreebase.nodeicon.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSource;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.ImageType;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceImpl;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class IconSourceReader
implements IconSourceScheme {
    private String sourceId;
    private String sourcePath;
    private String sourceProperties;

    public IconSourceReader(String sourceId, String sourcePath, String sourceProperties) {
        this.sourceId = sourceId;
        this.sourcePath = sourcePath;
        this.sourceProperties = sourceProperties;
    }

    @Override
    public String getSchemeId() {
        return this.sourceId;
    }

    @Override
    public IconCategory getCategory() {
        return null;
    }

    @Override
    public Set<String> getValues() {
        Properties properties = this.getProperties(this.sourcePath + File.separator + this.sourceProperties);
        return properties.keySet().stream().map(Object::toString).collect(Collectors.toSet());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IconSource getIconSource(String key) {
        Properties properties = this.getProperties(this.sourcePath + File.separator + this.sourceProperties);
        if (properties.containsKey(key)) {
            String fileName = properties.getProperty(key);
            ImageType imageType = ImageType.getType(StringUtils.substring((String)fileName, (int)(fileName.lastIndexOf(".") + 1)));
            ClassPathResource resource = new ClassPathResource(this.sourcePath + File.separator + fileName);
            InputStream is = null;
            try {
                is = resource.getInputStream();
                byte[] imageBytes = IOUtils.toByteArray(is);
                IconSourceImpl iconSourceImpl = new IconSourceImpl(this.sourceId, key, imageType, imageBytes);
                return iconSourceImpl;
            }
            catch (IOException e) {
                LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            }
            finally {
                if (is != null) {
                    try {
                        is.close();
                    }
                    catch (IOException e) {
                        LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
                    }
                }
            }
        }
        return null;
    }

    private Properties getProperties(String resourcePath) {
        Properties properties = new Properties();
        ClassPathResource resource = new ClassPathResource(resourcePath);
        try (InputStream inputStream = resource.getInputStream();){
            properties.load(inputStream);
        }
        catch (IOException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return properties;
    }
}


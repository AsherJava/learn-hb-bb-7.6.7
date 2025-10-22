/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.internal.icon;

import com.jiuqi.nr.formtype.common.FormTypeUtils;
import com.jiuqi.nr.formtype.internal.icon.IconSourceProvider;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

public abstract class AbstractIconSourceProvider
implements IconSourceProvider {
    private static EnumMap<ImageType, String> extension2Scheme = new EnumMap(ImageType.class);

    private static ImageType getScheme(String imageExtension) {
        if (StringUtils.hasText(imageExtension)) {
            for (ImageType type : ImageType.values()) {
                if (!type.toString().equalsIgnoreCase(imageExtension)) continue;
                return type;
            }
        }
        return null;
    }

    public static String toBase64(InputStream inputStream, ImageType type) throws IOException {
        byte[] imageBytes = IOUtils.toByteArray(inputStream);
        String imageBase64 = AbstractIconSourceProvider.bytesEncode2Base64(imageBytes);
        if (null != type) {
            imageBase64 = extension2Scheme.get((Object)type) + imageBase64;
        }
        return imageBase64;
    }

    public static String toBase64(InputStream inputStream, String imageExtension) throws IOException {
        ImageType type = AbstractIconSourceProvider.getScheme(imageExtension);
        return AbstractIconSourceProvider.toBase64(inputStream, type);
    }

    private static String bytesEncode2Base64(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }

    protected abstract String getIconPath(String var1);

    protected abstract String getPropertiesPath();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String getBase64Icon(String key) throws IOException {
        Properties properties = this.getProperties();
        if (!properties.containsKey(key)) return null;
        String fileName = properties.getProperty(key);
        String iconType = FormTypeUtils.substring(fileName, fileName.lastIndexOf(".") + 1);
        ClassPathResource resource = new ClassPathResource(this.getIconPath(fileName));
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
        Properties properties = new Properties();
        String propertiesPath = this.getPropertiesPath();
        if (StringUtils.hasText(propertiesPath)) {
            ClassPathResource resource = new ClassPathResource(propertiesPath);
            try (InputStream inputStream = resource.getInputStream();){
                properties.load(inputStream);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return properties;
    }

    static {
        extension2Scheme.put(ImageType.JPG, "data:image/jpg;base64,");
        extension2Scheme.put(ImageType.JPEG, "data:image/jpeg;base64,");
        extension2Scheme.put(ImageType.PNG, "data:image/png;base64,");
        extension2Scheme.put(ImageType.GIF, "data:image/gif;base64,");
        extension2Scheme.put(ImageType.ICON, "data:image/x-icon;base64,");
        extension2Scheme.put(ImageType.SVG, "data:image/svg+xml;base64,");
    }

    public static enum ImageType {
        JPG,
        JPEG,
        PNG,
        GIF,
        ICON,
        SVG;

    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.product;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

class ManifestResolver {
    private static Logger logger = LoggerFactory.getLogger(ManifestResolver.class);
    private static volatile HashMap<URI, Manifest> uriManifestMap;

    private ManifestResolver() {
    }

    public static String findProperty(Class<?> clazz, String property) {
        Manifest manifest = ManifestResolver.getManifestFromClasspath(clazz);
        if (manifest != null) {
            Attributes mainAttributes = manifest.getMainAttributes();
            for (Map.Entry<Object, Object> entry : mainAttributes.entrySet()) {
                String key = entry.getKey().toString();
                if (!property.equals(key)) continue;
                return entry.getValue().toString();
            }
        }
        return "Unknown";
    }

    public static Manifest getManifestFromClasspath(Class<?> clazz) {
        ProtectionDomain protectionDomain = clazz.getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        URI codeJarUri = null;
        try {
            codeJarUri = codeSource != null ? codeSource.getLocation().toURI() : null;
        }
        catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
        if (codeJarUri == null) {
            return null;
        }
        if (codeJarUri.getScheme().equals("jar")) {
            String suffix;
            String newPath = codeJarUri.getSchemeSpecificPart();
            if (newPath.endsWith(suffix = "!/BOOT-INF/classes!/")) {
                newPath = newPath.substring(0, newPath.length() - suffix.length());
            }
            if (newPath.endsWith("!/")) {
                newPath = newPath.substring(0, newPath.length() - 2);
            }
            try {
                codeJarUri = new URI(newPath);
            }
            catch (URISyntaxException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return ManifestResolver.uriManifestMap().get(codeJarUri);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Map<URI, Manifest> uriManifestMap() {
        if (uriManifestMap != null) return uriManifestMap;
        Class<ManifestResolver> clazz = ManifestResolver.class;
        synchronized (ManifestResolver.class) {
            if (uriManifestMap != null) return uriManifestMap;
            try {
                uriManifestMap = ManifestResolver.readClasspathAllManifest();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return uriManifestMap;
        }
    }

    private static HashMap<URI, Manifest> readClasspathAllManifest() throws Exception {
        Resource[] resources;
        HashMap<URI, Manifest> manifestMap = new HashMap<URI, Manifest>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        for (Resource resource : resources = resolver.getResources("classpath*:META-INF/MANIFEST.MF")) {
            URL manifestUrl = resource.getURL();
            int lastIndex = 0;
            String manifestPath = null;
            if (manifestUrl.getProtocol().equals("file")) {
                manifestPath = manifestUrl.toString();
                lastIndex = manifestPath.indexOf("META-INF/MANIFEST.MF");
            } else if (manifestUrl.getProtocol().equals("jar")) {
                manifestPath = manifestUrl.getPath();
                lastIndex = manifestPath.indexOf("!/META-INF/MANIFEST.MF");
            } else {
                logger.error("jar\u4f4d\u7f6e\u7684\u683c\u5f0f\u4e0d\u652f\u6301");
                continue;
            }
            URI jarUri = new URI(manifestPath.substring(0, lastIndex));
            try (InputStream inputStream = resource.getInputStream();){
                Manifest manifest = new Manifest(inputStream);
                manifestMap.put(jarUri, manifest);
            }
        }
        return manifestMap;
    }
}


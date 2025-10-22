/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.de.dataflow.tree.util;

import com.jiuqi.nr.bpm.de.dataflow.service.ITreeNodeIconColorService;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeNodeColorInfo;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.ImageConvert2Base64;
import com.jiuqi.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class TreeIconAndColor {
    private final Logger logger = LoggerFactory.getLogger(TreeIconAndColor.class);
    private static final String FILEPATH = "static" + File.separator + "work-flow-icons";
    private static final String PROPERTIESNAME = "zkeys.properties";
    @Autowired
    private ITreeNodeIconColorService treeNodeIconColorService;

    public String getBase64Icon(String stateCode) {
        Properties properties = this.getProperties(FILEPATH + File.separator + PROPERTIESNAME);
        if (properties.containsKey(stateCode)) {
            String fileName = properties.getProperty(stateCode);
            String iconType = StringUtils.substring((String)fileName, (int)(fileName.lastIndexOf(".") + 1));
            ClassPathResource resource = new ClassPathResource(FILEPATH + File.separator + fileName);
            InputStream inputStream = null;
            try {
                inputStream = resource.getInputStream();
                String string = ImageConvert2Base64.toBase64(resource.getInputStream(), iconType);
                return string;
            }
            catch (IOException e) {
                throw new RuntimeException("\u6587\u4ef6\u8f6c\u6362\u4e3a\u6d41\u65f6\u5f02\u5e38");
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    }
                    catch (Exception e) {
                        this.logger.error("\u6587\u4ef6\u6d41\u5173\u95ed\u65f6\u5f02\u5e38");
                    }
                }
            }
        }
        return null;
    }

    public Map<String, String> getBase64IconMap() {
        HashMap<String, String> iconMap = new HashMap<String, String>();
        Set<String> allPropertyKeys = this.getAllPropertyKeys();
        for (String propertyKey : allPropertyKeys) {
            String base64Icon = this.getBase64Icon(propertyKey);
            iconMap.put(propertyKey, base64Icon);
        }
        return iconMap;
    }

    private Set<String> getAllPropertyKeys() {
        return this.getPropertyKeys(FILEPATH + File.separator + PROPERTIESNAME);
    }

    private Properties getProperties(String resourcePath) {
        Properties properties = new Properties();
        ClassPathResource resource = new ClassPathResource(resourcePath);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            properties.load(inputStream);
        }
        catch (IOException e) {
            throw new RuntimeException("\u6587\u4ef6\u8f6c\u6362\u4e3a\u6d41\u65f6\u5f02\u5e38");
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (Exception e) {
                    this.logger.error("\u6587\u4ef6\u6d41\u5173\u95ed\u65f6\u5f02\u5e38");
                }
            }
        }
        return properties;
    }

    private Set<String> getPropertyKeys(String resourcePath) {
        Properties properties = this.getProperties(resourcePath);
        HashSet<String> keys = new HashSet<String>();
        if (null != properties) {
            properties.keySet().forEach(k -> keys.add(k.toString()));
        }
        return keys;
    }

    public boolean isIcon() {
        return this.treeNodeIconColorService.isNodeIconType();
    }

    public String getColor(String stateCode) {
        TreeNodeColorInfo nodeIconColor = this.treeNodeIconColorService.getNodeIconColorByUploadState(stateCode);
        return nodeIconColor.getColor();
    }

    public Map<String, String> getColorMap() {
        HashMap<String, String> colorMap = new HashMap<String, String>();
        List<TreeNodeColorInfo> allNodeIconColor = this.treeNodeIconColorService.getAllNodeIconColor();
        for (TreeNodeColorInfo treeNodeColorInfo : allNodeIconColor) {
            colorMap.put(treeNodeColorInfo.getState(), treeNodeColorInfo.getColor());
        }
        return colorMap;
    }
}


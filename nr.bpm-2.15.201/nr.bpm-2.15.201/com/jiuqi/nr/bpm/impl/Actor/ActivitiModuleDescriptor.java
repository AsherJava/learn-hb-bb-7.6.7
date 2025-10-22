/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.jdom2.Document
 *  org.jdom2.Element
 *  org.jdom2.input.SAXBuilder
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.bpm.exception.ActivitiFileXmlException;
import com.jiuqi.nr.bpm.impl.Actor.AutoStartProcessEngine;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public class ActivitiModuleDescriptor {
    private static final Logger logger = LoggerFactory.getLogger(AutoStartProcessEngine.class);
    public static final String SEPARATOR = File.separator;
    public static final String RESOURCESUFFIX = ".bpmn20.xml";
    private String key;
    private String title;
    private String version;
    private String filePath;
    private String oldVersion;
    private boolean needUpdate;
    private ArrayList<String> dependencies = new ArrayList();

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOldVersion() {
        return this.oldVersion;
    }

    public void setOldVersion(String oldVersion) {
        this.oldVersion = oldVersion;
    }

    public boolean isNeedUpdate() {
        return this.needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public ArrayList<String> getDependencies() {
        return this.dependencies;
    }

    public static ActivitiModuleDescriptor createModuleDescriptor(Resource r) {
        InputStream is = null;
        try {
            ActivitiModuleDescriptor module = new ActivitiModuleDescriptor();
            is = r.getInputStream();
            SAXBuilder builder = new SAXBuilder();
            Document doc = null;
            try {
                doc = builder.build(is);
            }
            catch (Exception e1) {
                logger.error("\u6784\u5efa\u6587\u4ef6\u5931\u8d25");
                throw new ActivitiFileXmlException("\u6784\u5efa\u6587\u4ef6\u5931\u8d25", e1);
            }
            Element m = doc.getRootElement();
            if (!m.getName().equals("module")) {
                m = m.getChild("module");
            }
            if (m == null) {
                throw new ActivitiFileXmlException("\u627e\u4e0d\u5230module\u8282\u70b9");
            }
            String key = m.getAttributeValue("key");
            if (StringUtils.isEmpty((String)key)) {
                throw new ActivitiFileXmlException("module\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u627e\u4e0d\u5230\u6a21\u5757key");
            }
            module.key = key;
            Element title = m.getChild("title");
            module.title = title == null ? null : title.getText();
            Element version = m.getChild("version");
            if (version == null || StringUtils.isEmpty((String)version.getText())) {
                throw new ActivitiFileXmlException("module\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u6ca1\u6709\u6307\u5b9a\u6a21\u5757\u7248\u672c");
            }
            module.version = version.getText();
            Element filePath = m.getChild("filePath");
            if (filePath == null || StringUtils.isEmpty((String)filePath.getText())) {
                throw new ActivitiFileXmlException("module\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u6ca1\u6709\u6307\u5b9a\u8def\u5f84");
            }
            module.filePath = String.format("%s%s%s%s%s", SEPARATOR, filePath.getText(), SEPARATOR, module.title, RESOURCESUFFIX);
            Element dependencies = m.getChild("dependencies");
            if (dependencies != null) {
                for (Object o : dependencies.getChildren("dependence")) {
                    Element dependence = (Element)o;
                    module.dependencies.add(dependence.getAttributeValue("id"));
                }
            }
            ActivitiModuleDescriptor activitiModuleDescriptor = module;
            return activitiModuleDescriptor;
        }
        catch (IOException e) {
            logger.error("\u89e3\u6790\u6587\u4ef6\u5931\u8d25");
            throw new ActivitiFileXmlException("\u89e3\u6790\u6587\u4ef6\u5931\u8d25", e);
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (Exception e2) {
                    logger.error("\u6587\u4ef6\u6d41\u5173\u95ed\u5931\u8d25");
                }
            }
        }
    }
}


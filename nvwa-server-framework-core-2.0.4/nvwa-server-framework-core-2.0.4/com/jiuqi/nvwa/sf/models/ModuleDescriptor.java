/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.jdom2.Document
 *  org.jdom2.Element
 *  org.jdom2.input.SAXBuilder
 */
package com.jiuqi.nvwa.sf.models;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.sf.InitializationException;
import com.jiuqi.nvwa.sf.models.Dependence;
import com.jiuqi.nvwa.sf.operator.FrameworkOperator;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class ModuleDescriptor
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private boolean deprecated;
    private String name;
    private String version;
    private String initiatorClassName;
    private String shortModuleId;
    private List<Dependence> dependencies = new ArrayList<Dependence>();
    private boolean isMain;
    private String jdkRequirement = "1.6.0";
    private List<String> legacies = new ArrayList<String>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDeprecated() {
        return this.deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInitiatorClassName() {
        return this.initiatorClassName;
    }

    public void setInitiatorClassName(String initiatorClassName) {
        this.initiatorClassName = initiatorClassName;
    }

    public boolean isMain() {
        return this.isMain;
    }

    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }

    public String getJdkRequirement() {
        return this.jdkRequirement;
    }

    public void setJdkRequirement(String jdkRequirement) {
        this.jdkRequirement = jdkRequirement;
    }

    public List<Dependence> getDependencies() {
        return this.dependencies;
    }

    public List<String> getLegacies() {
        return this.legacies;
    }

    public void setShortModuleId(String shortModuleId) {
        this.shortModuleId = shortModuleId;
    }

    public String getShortModuleId() {
        return this.shortModuleId;
    }

    public static ModuleDescriptor createModuleDescriptor(InputStream is) throws InitializationException {
        Element legacyElement;
        Element dependencies;
        Element initclass;
        Element jdkRequirementElement;
        Document doc;
        ModuleDescriptor module = new ModuleDescriptor();
        SAXBuilder builder = new SAXBuilder();
        try {
            doc = builder.build(is);
        }
        catch (Exception e1) {
            throw new InitializationException("\u6a21\u5757\u52a0\u8f7d\u5931\u8d25", e1, 4);
        }
        Element m = doc.getRootElement();
        if (!m.getName().equals("module")) {
            m = m.getChild("module");
        }
        if (m == null) {
            throw new InitializationException("\u627e\u4e0d\u5230module\u8282\u70b9", 4);
        }
        String id = m.getAttributeValue("id");
        String deprecated = m.getAttributeValue("deprecated");
        if (id == null || id.length() == 0) {
            throw new InitializationException("module\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u627e\u4e0d\u5230\u6a21\u5757ID", 4);
        }
        module.deprecated = "true".equals(deprecated);
        String main = m.getAttributeValue("ismain");
        module.isMain = "1".equalsIgnoreCase(main) || "true".equalsIgnoreCase(main);
        module.id = id;
        Element title = m.getChild("title");
        module.name = title == null ? null : title.getText();
        Element version = m.getChild("version");
        if (version == null) {
            throw new InitializationException("module\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u6ca1\u6709\u6307\u5b9a\u6a21\u5757\u7248\u672c", 4);
        }
        String versionString = version.getText();
        if (versionString == null || versionString.length() == 0) {
            throw new InitializationException("module\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u6ca1\u6709\u6307\u5b9a\u6a21\u5757\u7248\u672c", 4);
        }
        module.version = versionString;
        Element shortModuleIdElement = m.getChild("shortname");
        if (shortModuleIdElement != null) {
            module.shortModuleId = shortModuleIdElement.getText();
        }
        if ((jdkRequirementElement = m.getChild("jdkRequirement")) != null) {
            String require = jdkRequirementElement.getText();
            if (require != null && require.length() > 0) {
                module.jdkRequirement = require;
            }
            if (FrameworkOperator.lowerThan(module.jdkRequirement)) {
                return null;
            }
        }
        if ((initclass = m.getChild("initclass")) != null && !StringUtils.isEmpty((String)initclass.getText())) {
            module.initiatorClassName = initclass.getText();
        }
        if ((dependencies = m.getChild("dependencies")) != null) {
            Dependence dep;
            for (Object o : dependencies.getChildren("dependence")) {
                Element dependence = (Element)o;
                dep = new Dependence();
                try {
                    dep.load(dependence);
                }
                catch (Exception e) {
                    throw new InitializationException("\u6a21\u5757\u52a0\u8f7d\u5931\u8d25", e, 4);
                }
                module.dependencies.add(dep);
            }
            for (Object o : dependencies.getChildren("dependency")) {
                Element dependency = (Element)o;
                dep = new Dependence();
                try {
                    dep.load(dependency);
                }
                catch (Exception e) {
                    throw new InitializationException("\u6a21\u5757\u52a0\u8f7d\u5931\u8d25", e, 4);
                }
                module.dependencies.add(dep);
            }
        }
        if ((legacyElement = m.getChild("legacymodules")) != null) {
            String legacyModuleString = legacyElement.getText();
            Collections.addAll(module.legacies, legacyModuleString.split(","));
        }
        return module;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getName()).append("#").append(this.id).append(":").append(this.version);
        return sb.toString();
    }
}


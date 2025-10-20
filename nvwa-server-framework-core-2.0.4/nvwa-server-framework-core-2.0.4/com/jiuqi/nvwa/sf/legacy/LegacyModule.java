/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.Version
 *  org.jdom2.Document
 *  org.jdom2.Element
 *  org.jdom2.input.SAXBuilder
 */
package com.jiuqi.nvwa.sf.legacy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.Version;
import com.jiuqi.nvwa.sf.InitializationException;
import com.jiuqi.nvwa.sf.models.SQLFile;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class LegacyModule
implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private transient URL url;
    private List<SQLFile> sqlFiles = new ArrayList<SQLFile>();
    private String id;
    private String title;
    private String description;
    private String version;
    private String sqlPath;
    private Version dbVersion;
    private boolean needUpdate;
    private String beginExecutor;
    private String endExecutor;

    public void setURL(URL url) {
        this.url = url;
    }

    public URL getURL() {
        return this.url;
    }

    public List<SQLFile> getSqlFiles() {
        return this.sqlFiles;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSqlPath() {
        return this.sqlPath;
    }

    public void setSqlPath(String sqlPath) {
        this.sqlPath = sqlPath;
    }

    public Version getDbVersion() {
        return this.dbVersion;
    }

    public void setDbVersion(Version dbVersion) {
        this.dbVersion = dbVersion;
    }

    public boolean isNeedUpdate() {
        return this.needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public String getBeginExecutor() {
        return this.beginExecutor;
    }

    public void setBeginExecutor(String beginExecutor) {
        this.beginExecutor = beginExecutor;
    }

    public String getEndExecutor() {
        return this.endExecutor;
    }

    public void setEndExecutor(String endExecutor) {
        this.endExecutor = endExecutor;
    }

    public static LegacyModule createModuleDescriptor(InputStream is) throws InitializationException {
        Document doc;
        LegacyModule module = new LegacyModule();
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
        if (StringUtils.isEmpty((String)id)) {
            throw new InitializationException("module\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u627e\u4e0d\u5230\u6a21\u5757ID", 4);
        }
        module.id = id;
        Element title = m.getChild("title");
        module.title = title == null ? null : title.getText();
        Element description = m.getChild("description");
        module.description = description == null ? null : description.getText();
        Element sqlPath = m.getChild("sqlPath");
        module.sqlPath = sqlPath == null ? null : sqlPath.getText();
        Element version = m.getChild("version");
        if (version == null || StringUtils.isEmpty((String)version.getText())) {
            throw new InitializationException("module\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u6ca1\u6709\u6307\u5b9a\u6a21\u5757\u7248\u672c", 4);
        }
        module.version = version.getText();
        Element beginExecutor = m.getChild("beginExecutor");
        module.beginExecutor = beginExecutor == null ? null : beginExecutor.getText();
        Element endExecutor = m.getChild("endExecutor");
        module.endExecutor = endExecutor == null ? null : endExecutor.getText();
        return module;
    }

    public String toString() {
        return "Module [id=" + this.id + ", title=" + this.title + ", description=" + this.description + "]";
    }
}


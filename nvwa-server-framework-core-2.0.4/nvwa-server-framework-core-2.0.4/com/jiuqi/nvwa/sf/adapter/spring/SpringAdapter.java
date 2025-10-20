/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Version
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  com.jiuqi.sf.module.ModuleContext
 *  com.jiuqi.sf.module.ModuleInitiator
 *  com.jiuqi.sf.module.ModuleInitiatorWithEvent
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.bi.util.Version;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.adapter.IModuleFileCallback;
import com.jiuqi.nvwa.sf.legacy.LegacyModule;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import com.jiuqi.nvwa.sf.models.SQLFile;
import com.jiuqi.nvwa.sf.operator.CustomSqlCommandContext;
import com.jiuqi.nvwa.sf.operator.VersionOperator;
import com.jiuqi.sf.module.ModuleContext;
import com.jiuqi.sf.module.ModuleInitiatorWithEvent;
import com.jiuqi.sf.module.ModuleUpdateExecutor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

public class SpringAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public InputStream getProductFile() throws IOException {
        Resource[] resources = this._getResources("classpath*:config/product.mf");
        if (resources.length > 0) {
            return resources[0].getInputStream();
        }
        return null;
    }

    public InputStream getDefaultLicenceFile() throws IOException {
        Resource[] resources = this._getResources("classpath*:config/default.lic");
        if (resources.length > 0) {
            return resources[0].getInputStream();
        }
        return null;
    }

    public InputStream getWebinfResource(String path) throws IOException {
        Resource resource;
        if (path == null) {
            return null;
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if ((resource = this._getResource("/WEB-INF/" + path)) != null && resource.exists()) {
            return resource.getInputStream();
        }
        return null;
    }

    private Resource[] _getResources(String locationPattern) throws IOException {
        ApplicationContext applicationContext = SpringBeanUtils.getApplicationContext();
        if (applicationContext instanceof AbstractApplicationContext) {
            AbstractApplicationContext abstractContext = (AbstractApplicationContext)applicationContext;
            abstractContext.clearResourceCaches();
        }
        return applicationContext.getResources(locationPattern);
    }

    private Resource _getResource(String locationPattern) throws IOException {
        ApplicationContext applicationContext = SpringBeanUtils.getApplicationContext();
        if (applicationContext instanceof AbstractApplicationContext) {
            AbstractApplicationContext abstractContext = (AbstractApplicationContext)applicationContext;
            abstractContext.clearResourceCaches();
        }
        return applicationContext.getResource(locationPattern);
    }

    public void loadModuleFiles(IModuleFileCallback callback) throws Exception {
        Resource[] resources;
        Resource[] oldResources;
        this.logger.info("\u52a0\u8f7d\u65e7\u7248\u672c\u63cf\u8ff0\u6587\u4ef6");
        HashMap<String, LegacyModule> legacyModules = new HashMap<String, LegacyModule>();
        for (Resource resource : oldResources = this._getResources("classpath*:config/*.module")) {
            URL url = null;
            LegacyModule module = null;
            try {
                url = resource.getURL();
                try (InputStream inputStream = resource.getInputStream();){
                    byte[] data = SpringAdapter.readFile(inputStream);
                    this.logger.trace("\u6253\u5370\u6a21\u5757\u6587\u4ef6\uff1a{} \n {}", (Object)(url == null ? "\u65e0" : url.getPath()), (Object)new String(data, "UTF-8"));
                    module = LegacyModule.createModuleDescriptor(new ByteArrayInputStream(data));
                }
            }
            catch (Exception e) {
                this.logger.error("\u8bfb\u53d6\u6a21\u5757\u51fa\u9519\uff01urlPath:" + (url == null ? "\u65e0" : url.getPath()), e);
                throw e;
            }
            module.setURL(url);
            this.logger.info("\u8bfb\u53d6\u5230\u6a21\u5757{} : {}", (Object)module.getId(), (Object)module.getVersion());
            legacyModules.put(module.getId(), module);
        }
        this.logger.info("\u52a0\u8f7d\u65b0\u7248\u672c\u63cf\u8ff0\u6587\u4ef6");
        for (Resource resource : resources = this._getResources("classpath*:nvwamodule/module.xml")) {
            try (InputStream inputStream = resource.getInputStream();){
                ModuleDescriptor module = ModuleDescriptor.createModuleDescriptor(inputStream);
                ModuleWrapper wrapper = new ModuleWrapper(module);
                wrapper.setUrl(resource.getURL());
                if (!module.getLegacies().isEmpty()) {
                    for (String legacy : module.getLegacies()) {
                        LegacyModule legacyModule = (LegacyModule)legacyModules.get(legacy);
                        if (legacyModule == null) {
                            this.logger.warn("\u65e0\u6cd5\u627e\u5230\u65e7\u6a21\u5757[{}],\u5c06\u5ffd\u7565\u8be5\u6a21\u5757", (Object)legacy);
                            continue;
                        }
                        wrapper.getLegacies().add(legacyModule);
                    }
                }
                callback.fileLoaded(wrapper);
            }
        }
    }

    private static byte[] readFile(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = is.read(buf)) != -1) {
            baos.write(buf, 0, count);
        }
        return baos.toByteArray();
    }

    private static URL createRelativeURL(URL url, String relativePath) throws MalformedURLException {
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }
        relativePath = StringUtils.replace(relativePath, "#", "%23");
        return new URL(url, relativePath);
    }

    public void loadModuleVersions(Connection conn, Framework framework) throws Exception {
        Map<String, Version> versions = VersionOperator.getAllModuleVersion(conn);
        Map<String, Version> legacyVersions = VersionOperator.getLegacyModuleVersions(conn);
        List<String> preUpdateModelList = VersionOperator.getPreUpdateModelList(conn);
        for (ModuleWrapper mw : framework.getModuleWrappers().values()) {
            String id = mw.getModule().getId();
            mw.setDbVersion(versions.get(id));
            if (preUpdateModelList.contains(id)) {
                mw.setStatus("preupdate");
            }
            for (LegacyModule lm : mw.getLegacies()) {
                lm.setDbVersion(legacyVersions.get(lm.getId()));
            }
        }
    }

    public void loadModuleSQLFiles(ModuleWrapper wrapper) throws Exception {
        URL url = wrapper.getUrl();
        URL folderUrl = SpringAdapter.createRelativeURL(url, "");
        if (folderUrl.sameFile(url)) {
            folderUrl = SpringAdapter.createRelativeURL(url, ".");
        }
        Resource[] resources = this._getResources(folderUrl.toString() + "*.sqlx");
        for (Resource resource : resources) {
            String filename = resource.getFilename();
            URL sqlFileUrl = resource.getURL();
            SQLFile file = new SQLFile();
            file.setBelongsModuleId(wrapper.getModule().getId());
            file.setUrl(sqlFileUrl);
            file.setFilename(filename);
            SQLFile.fillProperties(file, filename, null);
            wrapper.getSqlFiles().add(file);
        }
        for (LegacyModule lm : wrapper.getLegacies()) {
            this.loadLegacyModuleSQLFiles(lm, wrapper.getModule().getId());
        }
    }

    private void loadLegacyModuleSQLFiles(LegacyModule lm, String moduleId) throws Exception {
        Resource[] resources;
        URL lmUrl = lm.getURL();
        URL lmFolderUrl = SpringAdapter.createRelativeURL(lmUrl, "");
        if (lmFolderUrl.sameFile(lmUrl)) {
            lmFolderUrl = SpringAdapter.createRelativeURL(lmUrl, ".");
        }
        String sqlPattern = "*.sqls";
        if (lm.getSqlPath() != null && lm.getSqlPath().length() > 0) {
            String sqlPath = lm.getSqlPath();
            if (sqlPath.startsWith("/")) {
                sqlPath = sqlPath.substring(1);
            }
            if (sqlPath.startsWith("config")) {
                sqlPath = sqlPath.substring("config".length());
            }
            if (sqlPath.startsWith("/")) {
                sqlPath = sqlPath.substring(1);
            }
            if (sqlPath.length() > 0) {
                sqlPattern = sqlPath + "/" + sqlPattern;
            }
        }
        this.logger.trace("\u8bfb\u53d6\u65e7\u6a21\u5757SQL\u811a\u672c\u6587\u4ef6\uff0c\u5bfb\u627e\u8def\u5f84\uff1a{}", (Object)(lmFolderUrl.toString() + sqlPattern));
        for (Resource resource : resources = this._getResources(lmFolderUrl.toString() + sqlPattern)) {
            String filename = resource.getFilename();
            if (!filename.toLowerCase().endsWith(".sqls")) continue;
            URL sqlFileUrl = resource.getURL();
            SQLFile file = new SQLFile();
            file.setFilename(filename);
            file.setBelongsModuleId(moduleId);
            file.setUrl(sqlFileUrl);
            if (filename.equalsIgnoreCase(lm.getId() + ".sqls")) {
                file.setShortModule(lm.getId());
                file.setVersion(new Version("0.0.1"));
            } else {
                if (!filename.toLowerCase().startsWith(lm.getId().toLowerCase() + "-")) continue;
                String versionString = filename.substring(lm.getId().length() + 1, filename.length() - 5);
                file.setShortModule(lm.getId());
                file.setVersion(new Version(versionString));
            }
            lm.getSqlFiles().add(file);
            this.logger.trace("\u5df2\u52a0\u8f7d\u9057\u7559\u6a21\u5757\u7684SQL\u6587\u4ef6\uff1a{}", (Object)sqlFileUrl);
        }
    }

    public void execModuleInitiator(ModuleDescriptor module) throws Exception {
        Object bean;
        Class<?> clazz;
        String className = module.getInitiatorClassName();
        if (className == null || className.length() == 0) {
            this.logger.debug("\u6a21\u5757[{}]\u672a\u5b9a\u4e49\u521d\u59cb\u5316\u7c7b\uff0c\u76f4\u63a5\u8df3\u8fc7\u521d\u59cb\u5316\u8fc7\u7a0b", (Object)module);
            return;
        }
        try {
            clazz = Class.forName(className);
        }
        catch (Exception e) {
            this.logger.error("\u6a21\u5757[{}]\u521d\u59cb\u5316\u5931\u8d25\uff0c\u65e0\u6cd5\u521b\u5efa\u6a21\u5757\u521d\u59cb\u5316\u7c7b[{}]", module, className, e);
            throw e;
        }
        try {
            bean = SpringBeanUtils.getApplicationContext().getBean(clazz);
        }
        catch (Exception e) {
            try {
                bean = clazz.newInstance();
            }
            catch (Exception e2) {
                this.logger.error("\u6a21\u5757[{}]\u521d\u59cb\u5316\u5931\u8d25\uff0c\u65e0\u6cd5\u521b\u5efa\u6a21\u5757\u521d\u59cb\u5316\u7c7b[{}]", (Object)module, (Object)className);
                this.logger.error("", e);
                this.logger.error("", e2);
                throw e2;
            }
        }
        try {
            if (bean instanceof ModuleInitiator) {
                ModuleInitiator initiator = (ModuleInitiator)bean;
                this.logger.debug("\u6a21\u5757\u521d\u59cb\u5316\u7c7b[{}]\u5b9e\u73b0\u4e86\u5e9f\u5f03\u7684\u521d\u59cb\u5316\u63a5\u53e3\u3002", (Object)clazz);
                initiator.init();
            } else if (bean instanceof com.jiuqi.sf.module.ModuleInitiator) {
                com.jiuqi.sf.module.ModuleInitiator initiator = (com.jiuqi.sf.module.ModuleInitiator)bean;
                this.logger.debug("\u6a21\u5757\u521d\u59cb\u5316\u7c7b[{}]\u5b9e\u73b0\u4e86\u5e9f\u5f03\u7684\u521d\u59cb\u5316\u63a5\u53e3\u3002", (Object)clazz);
                initiator.init();
            } else {
                Method toBeExecuted = clazz.getMethod("init", new Class[0]);
                toBeExecuted.invoke(bean, new Object[0]);
            }
        }
        catch (Exception e) {
            this.logger.error("\u6267\u884c\u6a21\u5757[{}]\u7684\u521d\u59cb\u5316\u7c7b\u65f6\u51fa\u73b0\u9519\u8bef", (Object)module);
            throw e;
        }
    }

    public void execModuleInitiatorWhenServerStarted(ModuleDescriptor module) throws Exception {
        Object bean;
        Class<?> clazz;
        String className = module.getInitiatorClassName();
        if (className == null || className.length() == 0) {
            this.logger.debug("\u6a21\u5757[{}]\u672a\u5b9a\u4e49\u521d\u59cb\u5316\u7c7b\uff0c\u76f4\u63a5\u8df3\u8fc7\u521d\u59cb\u5316\u8fc7\u7a0b", (Object)module);
            return;
        }
        try {
            clazz = Class.forName(className);
        }
        catch (Exception e) {
            this.logger.error("\u6a21\u5757[{}]\u521d\u59cb\u5316\u5931\u8d25\uff0c\u65e0\u6cd5\u521b\u5efa\u6a21\u5757\u521d\u59cb\u5316\u7c7b[{}]", module, className, e);
            throw e;
        }
        try {
            bean = SpringBeanUtils.getApplicationContext().getBean(clazz);
        }
        catch (Exception e) {
            try {
                bean = clazz.newInstance();
            }
            catch (Exception e2) {
                this.logger.error("\u6a21\u5757[{}]\u521d\u59cb\u5316\u5931\u8d25\uff0c\u65e0\u6cd5\u521b\u5efa\u6a21\u5757\u521d\u59cb\u5316\u7c7b[{}]", (Object)module, (Object)className);
                this.logger.error("", e);
                this.logger.error("", e2);
                throw e2;
            }
        }
        try {
            if (bean instanceof ModuleInitiator) {
                ModuleInitiator initiator = (ModuleInitiator)bean;
                this.logger.debug("\u6a21\u5757\u521d\u59cb\u5316\u7c7b[{}]\u5b9e\u73b0\u4e86\u5e9f\u5f03\u7684\u521d\u59cb\u5316\u63a5\u53e3\u3002", (Object)clazz);
                initiator.initWhenStarted();
            } else if (bean instanceof ModuleInitiatorWithEvent) {
                ModuleInitiatorWithEvent initiator = (ModuleInitiatorWithEvent)bean;
                this.logger.debug("\u6a21\u5757\u521d\u59cb\u5316\u7c7b[{}]\u5b9e\u73b0\u4e86\u5e9f\u5f03\u7684\u521d\u59cb\u5316\u63a5\u53e3\u3002", (Object)clazz);
                initiator.onServerStarted();
            } else {
                try {
                    Method toBeExecuted = clazz.getMethod("initWhenStarted", new Class[0]);
                    toBeExecuted.invoke(bean, new Object[0]);
                }
                catch (NoSuchMethodException toBeExecuted) {}
            }
        }
        catch (Exception e) {
            this.logger.error("\u6267\u884c\u6a21\u5757[{}]\u7684\u521d\u59cb\u5316\u7c7b\u65f6\u51fa\u73b0\u9519\u8bef", (Object)module);
            throw e;
        }
    }

    public void execUpdateExecutorCompatible(ModuleUpdateExecutor executor) throws Exception {
        DataSource ds = (DataSource)SpringBeanUtils.getBean(DataSource.class);
        try (Connection conn = ds.getConnection();){
            executor.execute(conn, new ModuleContext());
        }
    }

    public boolean execSQLUpdateExecutor(Method method, Object instance, CustomSqlCommandContext customSqlCommandContext) throws Exception {
        Class<?>[] types = method.getParameterTypes();
        if (types.length == 2) {
            try {
                method.invoke(instance, SpringBeanUtils.getBean(DataSource.class), customSqlCommandContext);
            }
            catch (Throwable e) {
                throw new Exception("\u5347\u7ea7\u811a\u672c[" + instance.getClass().getName() + "]\u8c03\u7528\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
            return true;
        }
        if (types.length == 1) {
            try {
                method.invoke(instance, SpringBeanUtils.getBean(DataSource.class));
            }
            catch (Throwable e) {
                throw new Exception("\u5347\u7ea7\u811a\u672c[" + instance.getClass().getName() + "]\u8c03\u7528\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
            return true;
        }
        return false;
    }

    public String preExecuteSql(Method preExecuteSql, Object instance) throws Exception {
        CustomSqlCommandContext customSqlCommandContext = new CustomSqlCommandContext();
        customSqlCommandContext.setPreExecuteMode(true);
        return (String)preExecuteSql.invoke(instance, SpringBeanUtils.getBean(DataSource.class), customSqlCommandContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendUserMessage(String message) {
        try {
            this.logger.debug("sendUserMessage()\u5f00\u59cb\uff0c\u5165\u53c2\uff1a{}", (Object)message);
            String msgServiceClassName = "com.jiuqi.va.message.feign.client.VaMessageClient";
            Class<?> msgServiceClazz = Class.forName(msgServiceClassName);
            Object msgServiceBean = SpringBeanUtils.getApplicationContext().getBean(msgServiceClazz);
            Class<?> paramClazz = Class.forName("com.jiuqi.va.message.domain.VaMessageSendDTO");
            Object paramInstance = paramClazz.newInstance();
            Method setTitleMethod = paramClazz.getSuperclass().getDeclaredMethod("setTitle", String.class);
            setTitleMethod.invoke(paramInstance, "\u7cfb\u7edf\u6388\u6743\u901a\u77e5");
            Method setContentMethod = paramClazz.getSuperclass().getDeclaredMethod("setContent", String.class);
            setContentMethod.invoke(paramInstance, message);
            Method setGrouptypeMethod = paramClazz.getSuperclass().getDeclaredMethod("setGrouptype", String.class);
            setGrouptypeMethod.invoke(paramInstance, "\u516c\u544a");
            Method setDatasourceMethod = paramClazz.getSuperclass().getDeclaredMethod("setDatasource", String.class);
            setDatasourceMethod.invoke(paramInstance, "\u5973\u5a32\u6388\u6743\u68c0\u67e5");
            Method setNoticeflagMethod = paramClazz.getDeclaredMethod("setNoticeflag", Boolean.TYPE);
            setNoticeflagMethod.invoke(paramInstance, true);
            Method setFinishtimeMethod = paramClazz.getDeclaredMethod("setFinishtime", Date.class);
            setFinishtimeMethod.invoke(paramInstance, this.addDays(new Date(), 7));
            Method toBeExecuted = msgServiceClazz.getMethod("addMsg", paramClazz);
            toBeExecuted.invoke(msgServiceBean, paramInstance);
        }
        catch (Exception e) {
            this.logger.error("\u53d1\u9001\u7528\u6237\u6d88\u606f[{}]\u5f02\u5e38", (Object)message, (Object)e);
        }
        finally {
            this.logger.debug("sendUserMessage()\u7ed3\u675f");
        }
    }

    private Date addDays(Date date, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(5, amount);
        return c.getTime();
    }
}


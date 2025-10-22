/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.EntityUtils
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.entity.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.common.AdapterException;
import com.jiuqi.nr.entity.common.AssertException;
import com.jiuqi.nr.entity.common.Utils;
import com.jiuqi.nr.entity.ext.filter.IEntityImportFilter;
import com.jiuqi.nr.entity.internal.model.impl.EntityDefineImpl;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityIOService;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityIOServiceImpl
implements IEntityIOService {
    @Autowired
    private AdapterService adapterService;
    @Autowired(required=false)
    private IEntityImportFilter entityImportFilter;
    ObjectMapper objectMapper = new ObjectMapper();
    public static final String ZIP_PATH = System.getProperty("java.io.tmpdir") + File.separator + "test" + File.separator + "zip";
    public static final String FILE_PATH = System.getProperty("java.io.tmpdir") + File.separator + "test" + File.separator + "file";
    public static final String FILE_ENTITY = "entityDefine";
    public static final String FILE_VIEW = "entityView";

    @Override
    public void exportEntityData(String entityId, OutputStream outputStream) throws JQException {
        Assert.notNull((Object)entityId, (String)AssertException.ILLEGAL_ARGUMENT.getMessage("entityId"));
        try {
            Utils.appendEntityId(entityId, outputStream);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)AdapterException.ERROR_APPEND_HEADER);
        }
        this.adapterService.getEntityAdapter(entityId).exportEntityData(EntityUtils.getId((String)entityId), outputStream);
    }

    @Override
    public void importEntityData(InputStream inputStream) throws JQException {
        String entityId;
        try {
            entityId = Utils.readerEntityId(inputStream);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)AdapterException.ERROR_READ_HEADER);
        }
        if (entityId == null) {
            throw new JQException((ErrorEnum)AdapterException.ERROR_READ_HEADER, "\u8bfb\u53d6\u7684\u5b9e\u4f53id\u4e3a\u7a7a,\u65e0\u6cd5\u6267\u884c\u5bfc\u5165\u3002");
        }
        String id = EntityUtils.getId((String)entityId);
        if (this.entityImportFilter != null && !this.entityImportFilter.importEntityData(id)) {
            return;
        }
        this.adapterService.getEntityAdapter(entityId).importEntityData(id, inputStream);
    }

    @Override
    public void exportEntityDefine(List<String> entityId, OutputStream outputStream) throws JQException {
        Assert.notNull(entityId, (String)AssertException.ILLEGAL_ARGUMENT.getMessage("entityId"));
        try (ZipOutputStream zipOut = new ZipOutputStream(outputStream);
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut);){
            for (String id : entityId) {
                Throwable throwable;
                String filePath = FILE_PATH + File.separator + id;
                try {
                    PathUtils.validatePathManipulation((String)filePath);
                }
                catch (SecurityContentException exception) {
                    continue;
                }
                File defineFile = new File(filePath);
                if (!defineFile.exists()) {
                    FileUtils.forceMkdirParent(defineFile);
                }
                try {
                    throwable = null;
                    try (FileOutputStream defineOutputStream = new FileOutputStream(defineFile);){
                        this.adapterService.getEntityAdapter(id).exportEntityDefine(EntityUtils.getId((String)id), defineOutputStream);
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                }
                catch (Exception e) {
                    throw new JQException((ErrorEnum)AdapterException.ERROR_APPEND_FILE, (Throwable)e);
                }
                FileChannel fileChannel = new FileInputStream(defineFile).getChannel();
                throwable = null;
                try {
                    zipOut.putNextEntry(new ZipEntry(FILE_ENTITY + File.separator + id));
                    fileChannel.transferTo(0L, defineFile.length(), writableByteChannel);
                }
                catch (Throwable throwable3) {
                    throwable = throwable3;
                    throw throwable3;
                }
                finally {
                    if (fileChannel == null) continue;
                    if (throwable != null) {
                        try {
                            fileChannel.close();
                        }
                        catch (Throwable throwable4) {
                            throwable.addSuppressed(throwable4);
                        }
                        continue;
                    }
                    fileChannel.close();
                }
            }
        }
        catch (Exception e) {
            try {
                throw new JQException((ErrorEnum)AdapterException.ERROR_APPEND_FILE, (Throwable)e);
            }
            catch (Throwable throwable) {
                this.close(outputStream);
                throw throwable;
            }
        }
        this.close(outputStream);
    }

    @Override
    public List<IEntityDefine> importEntityDefine(InputStream inputStream) throws JQException {
        ArrayList<IEntityDefine> entityDefines = new ArrayList<IEntityDefine>();
        String path = ZIP_PATH + File.separator + System.currentTimeMillis();
        try {
            PathUtils.validatePathManipulation((String)path);
        }
        catch (SecurityContentException exception) {
            throw new JQException((ErrorEnum)AdapterException.ERROR_READ_FILE, exception.getMessage());
        }
        File file = new File(path);
        try {
            FileUtils.copyToFile(inputStream, file);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)AdapterException.ERROR_READ_FILE, (Throwable)e);
        }
        try (ZipFile zipFile = new ZipFile(file);){
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                InputStream is = zipFile.getInputStream(zipEntry);
                String id = zipEntry.getName().substring((FILE_ENTITY + File.separator).length());
                if (this.entityImportFilter != null && !this.entityImportFilter.importEntityDefine(EntityUtils.getId((String)id))) continue;
                IEntityAdapter entityAdapter = this.adapterService.getEntityAdapter(id);
                IEntityDefine entityDefine = entityAdapter.importEntityDefine(is);
                entityDefines.add(this.convertEntity(entityAdapter.getId(), entityDefine));
                this.close(is);
            }
        }
        catch (IOException e) {
            try {
                throw new JQException((ErrorEnum)AdapterException.ERROR_READ_FILE, (Throwable)e);
            }
            catch (Throwable throwable) {
                this.close(inputStream);
                if (file.exists()) {
                    FileUtils.deleteQuietly(file);
                }
                throw throwable;
            }
        }
        this.close(inputStream);
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }
        return entityDefines;
    }

    public void close(Closeable ... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            this.closeQuietly(closeable);
        }
    }

    public void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private EntityDefineImpl convertEntity(String category, IEntityDefine entityDefine) {
        EntityDefineImpl impl = (EntityDefineImpl)entityDefine;
        impl.setId(EntityUtils.getEntityId((String)entityDefine.getId(), (String)category));
        return impl;
    }
}


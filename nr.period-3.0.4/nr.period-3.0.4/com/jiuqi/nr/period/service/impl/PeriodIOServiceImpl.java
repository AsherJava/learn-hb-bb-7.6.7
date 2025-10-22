/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.period.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.rest.ExpObject;
import com.jiuqi.nr.period.common.utils.EntityUtils;
import com.jiuqi.nr.period.common.utils.PeriodException;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.dao.PeriodDao;
import com.jiuqi.nr.period.dao.PeriodDataDao;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;
import com.jiuqi.nr.period.service.PeriodIOService;
import com.jiuqi.nr.period.service.PeriodService;
import com.jiuqi.nr.period.util.JacksonUtils;
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
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodIOServiceImpl
implements PeriodIOService {
    @Autowired
    private PeriodDao periodDao;
    @Autowired
    private PeriodDataDao periodDataDao;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private PeriodService periodService;
    public static final String ZIP_PATH = System.getProperty("java.io.tmpdir") + File.separator + "test" + File.separator + "zip";
    public static final String FILE_PATH = System.getProperty("java.io.tmpdir") + File.separator + "test" + File.separator + "file";

    @Override
    public void importPeriod(InputStream inputStream) throws JQException {
        String path = ZIP_PATH + File.separator + System.currentTimeMillis();
        try {
            PathUtils.validatePathManipulation((String)path);
        }
        catch (SecurityContentException e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122, (Throwable)e);
        }
        File file = new File(path);
        try {
            FileUtils.copyToFile(inputStream, file);
            try (ZipFile zipFile = new ZipFile(file);){
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = entries.nextElement();
                    InputStream is = zipFile.getInputStream(zipEntry);
                    Throwable throwable = null;
                    try {
                        this.importDefine(is);
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (is == null) continue;
                        if (throwable != null) {
                            try {
                                is.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        is.close();
                    }
                }
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122, (Throwable)e);
        }
    }

    @Override
    public void exportPeriods(List<String> periodKeys, OutputStream outputStream) throws JQException {
        if (StringUtils.isEmpty(periodKeys)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try (ZipOutputStream zipOut = new ZipOutputStream(outputStream);
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut);){
            for (String id : periodKeys) {
                String path = FILE_PATH + File.separator + id;
                try {
                    PathUtils.validatePathManipulation((String)path);
                }
                catch (SecurityContentException e) {
                    throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_120, (Throwable)e);
                }
                File file = new File(path);
                if (!file.exists()) {
                    FileUtils.forceMkdirParent(file);
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                Throwable throwable = null;
                try {
                    this.exportDefine(id, fileOutputStream);
                    FileChannel fileChannel = new FileInputStream(file).getChannel();
                    Throwable throwable2 = null;
                    try {
                        zipOut.putNextEntry(new ZipEntry(id));
                        fileChannel.transferTo(0L, file.length(), writableByteChannel);
                    }
                    catch (Throwable throwable3) {
                        throwable2 = throwable3;
                        throw throwable3;
                    }
                    finally {
                        if (fileChannel == null) continue;
                        if (throwable2 != null) {
                            try {
                                fileChannel.close();
                            }
                            catch (Throwable throwable4) {
                                throwable2.addSuppressed(throwable4);
                            }
                            continue;
                        }
                        fileChannel.close();
                    }
                }
                catch (Throwable throwable5) {
                    throwable = throwable5;
                    throw throwable5;
                }
                finally {
                    if (fileOutputStream == null) continue;
                    if (throwable != null) {
                        try {
                            fileOutputStream.close();
                        }
                        catch (Throwable throwable6) {
                            throwable.addSuppressed(throwable6);
                        }
                        continue;
                    }
                    fileOutputStream.close();
                }
            }
        }
        catch (Exception e) {
            try {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_120, (Throwable)e);
            }
            catch (Throwable throwable) {
                this.close(outputStream);
                throw throwable;
            }
        }
        this.close(outputStream);
    }

    @Override
    public void exportPeriod(String periodKey, OutputStream outputStream) throws JQException {
        if (StringUtils.isEmpty(periodKey)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try (ZipOutputStream zipOut = new ZipOutputStream(outputStream);
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut);){
            String path = FILE_PATH + File.separator + periodKey;
            try {
                PathUtils.validatePathManipulation((String)path);
            }
            catch (SecurityContentException e) {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_120, (Throwable)e);
            }
            File file = new File(path);
            if (!file.exists()) {
                FileUtils.forceMkdirParent(file);
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 FileChannel fileChannel = new FileInputStream(file).getChannel();){
                this.exportDefine(EntityUtils.getId(periodKey), fileOutputStream);
                zipOut.putNextEntry(new ZipEntry(periodKey));
                fileChannel.transferTo(0L, file.length(), writableByteChannel);
            }
        }
        catch (Exception e) {
            try {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_120, (Throwable)e);
            }
            catch (Throwable throwable) {
                this.close(outputStream);
                throw throwable;
            }
        }
        this.close(outputStream);
    }

    @Override
    public void exportPeriodData(String periodKey, OutputStream outputStream) throws JQException {
        if (StringUtils.isEmpty(periodKey)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_101);
        }
        try (ZipOutputStream zipOut = new ZipOutputStream(outputStream);
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut);){
            IPeriodEntity queryPeriodByKey = this.periodDao.queryPeriodByKey(periodKey);
            List<PeriodDataDefineImpl> queryCustomPeriodData = this.periodDataDao.queryCustomPeriodData(queryPeriodByKey.getCode());
            String path = FILE_PATH + File.separator + periodKey + "-DATA";
            try {
                PathUtils.validatePathManipulation((String)path);
            }
            catch (SecurityContentException e) {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_120, (Throwable)e);
            }
            File file = new File(path);
            if (!file.exists()) {
                FileUtils.forceMkdirParent(file);
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 FileChannel fileChannel = new FileInputStream(file).getChannel();){
                new ObjectMapper().writeValue(file, queryCustomPeriodData);
                zipOut.putNextEntry(new ZipEntry(periodKey + "-DATA"));
                fileChannel.transferTo(0L, file.length(), writableByteChannel);
            }
        }
        catch (Exception e) {
            try {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_120, (Throwable)e);
            }
            catch (Throwable throwable) {
                this.close(outputStream);
                throw throwable;
            }
        }
        this.close(outputStream);
    }

    @Override
    public void importPeriodData(InputStream inputStream) throws JQException {
        String path = ZIP_PATH + File.separator + System.currentTimeMillis();
        try {
            PathUtils.validatePathManipulation((String)path);
        }
        catch (SecurityContentException e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122, (Throwable)e);
        }
        File file = new File(path);
        try (ZipFile zipFile = new ZipFile(file);){
            FileUtils.copyToFile(inputStream, file);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                String periodKey = PeriodUtils.removeDataSuffix(zipEntry.getName());
                IPeriodEntity queryPeriodByKey = this.periodDao.queryPeriodByKey(periodKey);
                List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(queryPeriodByKey.getCode());
                Map<String, PeriodDataDefineImpl> maps = dataListByCode.stream().collect(Collectors.toMap(PeriodDataDefineImpl::getKey, a -> a, (key1, key2) -> key1));
                InputStream is = zipFile.getInputStream(zipEntry);
                Throwable throwable = null;
                try {
                    if (null == queryPeriodByKey) continue;
                    if (PeriodType.CUSTOM.type() == queryPeriodByKey.getType().type()) {
                        this.importDefineData(maps, is, queryPeriodByKey.getCode());
                        continue;
                    }
                    this.extendDefaultData(is, dataListByCode, queryPeriodByKey);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (is == null) continue;
                    if (throwable != null) {
                        try {
                            is.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    is.close();
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122, (Throwable)e);
        }
    }

    private void extendDefaultData(InputStream is, List<PeriodDataDefineImpl> dataListByCode, IPeriodEntity queryPeriodByKey) throws JQException {
        try {
            byte[] buffer = IOUtils.toByteArray(is);
            ObjectMapper objectMapper = new ObjectMapper();
            List periodDefines = (List)objectMapper.readValue(buffer, (TypeReference)new TypeReference<List<PeriodDataDefineImpl>>(){});
            if (null != periodDefines) {
                Date[] periodDateRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(queryPeriodByKey.getKey()).getPeriodDateRegion();
                List collect = periodDefines.stream().sorted(Comparator.comparing(PeriodDataDefineImpl::getStartDate)).collect(Collectors.toList());
                PeriodDataDefineImpl maxImport = (PeriodDataDefineImpl)collect.get(collect.size() - 1);
                PeriodDataDefineImpl minImport = (PeriodDataDefineImpl)collect.get(0);
                if (maxImport.getStartDate().after(periodDateRegion[1]) || minImport.getStartDate().before(periodDateRegion[0])) {
                    this.periodService.extensionDefaultPeriod(queryPeriodByKey, minImport.getCode(), maxImport.getCode());
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122, (Throwable)e);
        }
    }

    private void importDefineData(Map<String, PeriodDataDefineImpl> maps, InputStream inputStream, String tableCode) throws JQException {
        try {
            byte[] buffer = IOUtils.toByteArray(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            List periodDefines = (List)objectMapper.readValue(buffer, (TypeReference)new TypeReference<List<PeriodDataDefineImpl>>(){});
            if (null != periodDefines) {
                for (PeriodDataDefineImpl periodDefineImpl : periodDefines) {
                    if (null == periodDefineImpl || !StringUtils.isNotEmpty(periodDefineImpl.getCode())) continue;
                    if (null != maps.get(periodDefineImpl.getKey())) {
                        this.periodDataDao.updateCustomPeriodData(tableCode, periodDefineImpl);
                        continue;
                    }
                    this.periodDataDao.insertCustomPeriodData(tableCode, periodDefineImpl);
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122, (Throwable)e);
        }
    }

    public void exportDefine(String periodKey, OutputStream outputStream) throws JQException {
        try {
            PeriodDefineImpl queryPeriodByKey = (PeriodDefineImpl)this.periodDao.queryPeriodByKey(PeriodUtils.removeSuffix(periodKey));
            ExpObject expObject = new ExpObject(queryPeriodByKey);
            String data = JacksonUtils.mapper.writeValueAsString((Object)expObject);
            IOUtils.write(data.getBytes("UTF-8"), outputStream);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_120, (Throwable)e);
        }
    }

    public void importDefine(InputStream inputStream) throws JQException {
        try {
            byte[] buffer = IOUtils.toByteArray(inputStream);
            ExpObject expObject = (ExpObject)JacksonUtils.mapper.readValue(buffer, ExpObject.class);
            PeriodDefineImpl periodDefineImpl = expObject.getPeriod();
            IPeriodEntity queryPeriodByKey = this.periodDao.queryPeriodByCode(periodDefineImpl.getCode());
            if (null != periodDefineImpl && periodDefineImpl.getType() != null && PeriodType.CUSTOM.type() == periodDefineImpl.getType().type()) {
                if (null != queryPeriodByKey) {
                    this.periodDao.updateDate(periodDefineImpl);
                } else {
                    periodDefineImpl.setCode(PeriodUtils.removePerfix(periodDefineImpl.getCode()));
                    periodDefineImpl.setKey(PeriodUtils.removePerfix(periodDefineImpl.getCode()));
                    if (StringUtils.isEmpty(periodDefineImpl.getCode()) || StringUtils.isEmpty(periodDefineImpl.getTitle())) {
                        throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122);
                    }
                    this.periodDao.insertDate(periodDefineImpl);
                    this.periodDataDao.createAndDeployTable(PeriodUtils.addPrefix(periodDefineImpl.getCode()), periodDefineImpl.getTitle(), "\u62a5\u8868" + periodDefineImpl.getTitle() + "\u62a5\u65f6\u671f\u8868", periodDefineImpl.getCode());
                }
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122, (Throwable)e);
        }
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
}


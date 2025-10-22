/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.io.record.ImportHistoryCleanInit
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.data.excel.init;

import com.jiuqi.nr.data.excel.exception.ExcelException;
import com.jiuqi.nr.io.record.ImportHistoryCleanInit;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.DefaultTempFileCreationStrategy;
import org.apache.poi.util.TempFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ExcelModuleInitiator
implements ModuleInitiator {
    @Value(value="${jiuqi.nr.data.excel.POITempDir:}")
    private String customPOITempDir;
    private static final Logger logger = LoggerFactory.getLogger(ExcelModuleInitiator.class);
    private static final ImportHistoryCleanInit importHistoryCleanInit = new ImportHistoryCleanInit();

    public void init(ServletContext context) throws Exception {
        importHistoryCleanInit.init(context);
        if (!StringUtils.hasText(this.customPOITempDir)) {
            return;
        }
        String normalizedDir = FilenameUtils.normalize(this.customPOITempDir);
        if (normalizedDir == null) {
            throw new IllegalArgumentException("\u914d\u7f6e\u6587\u4ef6\u6307\u5b9a\u7684POI\u4e34\u65f6\u6587\u4ef6\u76ee\u5f55\u8def\u5f84\u65e0\u6548\uff1a" + this.customPOITempDir);
        }
        logger.info("\u914d\u7f6e\u6587\u4ef6\u6307\u5b9aPOI\u4e34\u65f6\u6587\u4ef6\u76ee\u5f55\uff1a{}", (Object)normalizedDir);
        try {
            File poiFilesDirectory = this.createPOIFilesDirectory(normalizedDir);
            DefaultTempFileCreationStrategy defaultTempFileCreationStrategy = new DefaultTempFileCreationStrategy(poiFilesDirectory);
            TempFile.setTempFileCreationStrategy(defaultTempFileCreationStrategy);
        }
        catch (Exception e) {
            throw new ExcelException(e.getMessage() + "-POI\u4e34\u65f6\u6587\u4ef6\u76ee\u5f55\u8bbe\u7f6e\u5f02\u5e38\uff1a" + this.customPOITempDir, e);
        }
        logger.info("POI\u4e34\u65f6\u6587\u4ef6\u76ee\u5f55\u8bbe\u7f6e\u6210\u529f");
    }

    public void initWhenStarted(ServletContext context) {
    }

    private File createPOIFilesDirectory(String tempDir) throws IOException {
        Path dirPath = Paths.get(FilenameUtils.normalize(tempDir), "poifiles");
        File dir = Files.createDirectories(dirPath, new FileAttribute[0]).toFile();
        if (System.getProperty("poi.delete.tmp.files.on.exit") != null) {
            dir.deleteOnExit();
        }
        return dir;
    }
}


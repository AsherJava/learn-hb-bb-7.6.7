/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.nr.tds.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.tds.Costs;
import com.jiuqi.nr.tds.api.DataTableReader;
import com.jiuqi.nr.tds.impl.ZipExtractor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseReader
implements DataTableReader {
    protected String tempDir;
    private static final Logger logger = LoggerFactory.getLogger(BaseReader.class);

    @Override
    public void open(File file, String tableName) throws IOException {
        LocalTime hour = LocalTime.now().withMinute(0).withSecond(0).withNano(0);
        String hourStr = hour.format(Costs.FORMATTER);
        this.tempDir = Costs.TEMP_DIR + LocalDate.now() + Costs.FILE_SEPARATOR + hourStr + Costs.FILE_SEPARATOR + OrderGenerator.newOrder() + Costs.FILE_SEPARATOR;
        ZipExtractor.unzip(file.getAbsolutePath(), this.tempDir, StandardCharsets.UTF_8);
    }

    @Override
    public void destroy() {
        if (this.tempDir == null) {
            return;
        }
        File[] files = new File(this.tempDir).listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            Path path = file.toPath();
            try {
                Files.delete(path);
            }
            catch (Exception e) {
                logger.warn("\u6587\u4ef6\u5220\u9664\u5931\u8d25: {}", (Object)path, (Object)e);
            }
        }
    }
}


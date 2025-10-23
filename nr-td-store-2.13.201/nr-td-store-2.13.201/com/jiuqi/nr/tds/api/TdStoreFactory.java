/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.Version
 */
package com.jiuqi.nr.tds.api;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.Version;
import com.jiuqi.nr.tds.Costs;
import com.jiuqi.nr.tds.TdModel;
import com.jiuqi.nr.tds.api.DataTableReader;
import com.jiuqi.nr.tds.api.DataTableWriter;
import com.jiuqi.nr.tds.bdf.BlockFileWriter;
import com.jiuqi.nr.tds.csv.CsvFileWriter;
import com.jiuqi.nr.tds.impl.TableReader;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TdStoreFactory {
    private static final Logger log = LoggerFactory.getLogger(TdStoreFactory.class);
    public static Version VERSION_0_0_1 = new Version(0, 0, 1);
    public static Version VERSION_1_0_0;
    public static Version CURRENT_VERSION;

    public DataTableWriter getDataTableWriter(TdModel tdModel, Version version) {
        LocalTime hour = LocalTime.now().withMinute(0).withSecond(0).withNano(0);
        String hourStr = hour.format(Costs.FORMATTER);
        String path = Costs.TEMP_DIR + LocalDate.now() + Costs.FILE_SEPARATOR + hourStr + Costs.FILE_SEPARATOR + OrderGenerator.newOrder() + Costs.FILE_SEPARATOR;
        if (log.isDebugEnabled()) {
            log.debug("\u5bfc\u51fa\u6587\u4ef6\u8def\u5f84:{}", (Object)path);
        }
        if (version.equals((Object)VERSION_1_0_0)) {
            return new BlockFileWriter(new File(path), tdModel);
        }
        if (version.equals((Object)VERSION_0_0_1)) {
            return new CsvFileWriter(new File(path), tdModel);
        }
        return null;
    }

    public DataTableReader getDataTableReader() {
        return new TableReader();
    }

    static {
        CURRENT_VERSION = VERSION_1_0_0 = new Version(1, 0, 0);
    }
}


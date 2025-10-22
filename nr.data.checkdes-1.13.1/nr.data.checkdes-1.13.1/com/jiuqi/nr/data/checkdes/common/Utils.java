/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.common;

import com.jiuqi.nr.data.checkdes.common.Constants;
import java.util.UUID;

public class Utils {
    public static String getDefExpFilePath() {
        return Constants.TEMP_DIR_PATH + Constants.FILE_PATH_SEP + UUID.randomUUID();
    }

    public static String getFilePathWithName(String filePath) {
        if (filePath.endsWith(Constants.FILE_PATH_SEP)) {
            return filePath + "\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u51fa\u6587\u4ef6" + ".csv";
        }
        return filePath + Constants.FILE_PATH_SEP + "\u51fa\u9519\u8bf4\u660e\u6570\u636e\u5bfc\u51fa\u6587\u4ef6" + ".csv";
    }

    public static String getDesFilePathWithName(String filePath) {
        if (filePath.endsWith(Constants.FILE_PATH_SEP)) {
            return filePath + "VERSION";
        }
        return filePath + Constants.FILE_PATH_SEP + "VERSION";
    }
}


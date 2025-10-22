/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.gcreport.common.pdf;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class DownloadFileUtil {
    public static void copyFile(String givenFile, String toFilePath, String fileName) {
        File givenfile = new File(givenFile);
        File tofilePath = new File(toFilePath);
        if (!tofilePath.exists()) {
            tofilePath.mkdirs();
        }
        if (!givenfile.exists()) {
            return;
        }
        try (FileChannel input = new FileInputStream(givenfile).getChannel();
             FileChannel output = new FileOutputStream(toFilePath + File.separator + fileName).getChannel();){
            output.transferFrom(input, 0L, input.size());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.pdf.download.error") + e.getMessage());
        }
    }
}


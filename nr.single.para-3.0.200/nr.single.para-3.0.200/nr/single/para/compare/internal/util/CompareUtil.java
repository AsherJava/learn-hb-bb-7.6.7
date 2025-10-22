/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  nr.single.map.data.PathUtil
 */
package nr.single.para.compare.internal.util;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import nr.single.map.data.PathUtil;
import nr.single.para.common.NrSingleErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompareUtil {
    private static final Logger log = LoggerFactory.getLogger(CompareUtil.class);

    public static String saveToFile(String fileName, byte[] fileData) throws JQException {
        try {
            String filePath = CompareUtil.getCompareFilePath();
            log.info("JIO\u6587\u4ef6\u540d(\u542b\u5b8c\u6574\u8def\u5f84)-->" + filePath + fileName);
            CompareUtil.uploadFile(fileData, filePath, fileName);
            return filePath + fileName;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_001, (Throwable)e);
        }
    }

    public static String saveToFile(String filePath, String fileName, byte[] fileData) throws JQException {
        log.info("JIO\u6587\u4ef6\u540d(\u542b\u5b8c\u6574\u8def\u5f84)-->" + filePath + fileName);
        try {
            CompareUtil.uploadFile(fileData, filePath, fileName);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_001, (Throwable)e);
        }
        return filePath + fileName;
    }

    public static String getCompareFilePath() throws SingleFileException {
        String filePath = SinglePathUtil.getTempFilePath();
        filePath = PathUtil.createNewPath((String)filePath, (String)".nr");
        filePath = PathUtil.createNewPath((String)filePath, (String)"AppData");
        filePath = PathUtil.createNewPath((String)filePath, (String)"ComparePara");
        filePath = PathUtil.createNewPath((String)filePath, (String)(CompareUtil.getTempTimeCode() + OrderGenerator.newOrder()));
        return filePath;
    }

    public static String getTempTimeCode() {
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sfDate.format(new Date());
    }

    private static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(SinglePathUtil.normalize((String)filePath));
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(SinglePathUtil.normalize((String)(filePath + fileName)));){
            out.write(file);
            out.flush();
        }
    }
}


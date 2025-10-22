/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.impl.FileInfoService
 */
package com.jiuqi.nr.data.excel.utils;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.data.excel.param.DataExcelContext;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static List<FileInfo> getFileInfos(DataExcelContext context, String groupKey) {
        FileService fileService = (FileService)BeanUtil.getBean(FileService.class);
        FileInfoService fileInfoService = (FileInfoService)BeanUtil.getBean(FileInfoService.class);
        String area = "JTABLEAREA";
        List files = fileInfoService.getFileInfoByGroup(groupKey, area, FileStatus.AVAILABLE);
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        try {
            if (files == null || files.size() == 0) {
                return fileInfos;
            }
            for (FileInfo file : files) {
                String path = fileService.area(area).getPath(file.getKey(), NpContextHolder.getContext().getTenant());
                byte[] textByte = path.getBytes("UTF-8");
                file = FileInfoBuilder.newFileInfo((FileInfo)file, (String)file.getFileGroupKey(), (String)Base64.encodeBase64String(textByte));
                fileInfos.add(file);
            }
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return fileInfos;
    }

    public static byte[] downFile(String fileKey) {
        FileService fileService = (FileService)BeanUtil.getBean(FileService.class);
        FileAreaService fileAreaService = fileService.area("JTABLEAREA");
        byte[] downloadFile = fileAreaService.download(fileKey);
        return downloadFile;
    }
}


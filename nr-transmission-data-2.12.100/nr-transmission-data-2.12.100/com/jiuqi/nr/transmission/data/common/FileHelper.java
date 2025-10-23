/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.transmission.data.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.transmission.data.common.MappingType;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.dto.AnalysisDTO;
import com.jiuqi.nr.transmission.data.dto.AnalysisParam;
import com.jiuqi.nr.transmission.data.dto.SrcParamDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.exception.SchemeFileException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FileHelper {
    private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);

    public static File getTempFile(ZipUtils.ZipSubFile subFile, String tempPath) throws Exception {
        try (InputStream subFileInputStream = subFile.getSubFileInputStream();){
            String path = tempPath + "/" + subFile.getSubFileName();
            PathUtils.validatePathManipulation((String)path);
            File file = new File(path);
            FileUtils.copyToFile(subFileInputStream, file);
            File file2 = file;
            return file2;
        }
    }

    public static File getTempPathFile(ZipUtils.ZipSubFile subFile, String tempPath) throws Exception {
        try (InputStream subFileInputStream = subFile.getSubFileInputStream();){
            String path = tempPath + "/" + subFile.getSubFilePath();
            PathUtils.validatePathManipulation((String)path);
            File file = new File(path);
            FileUtils.copyToFile(subFileInputStream, file);
            File file2 = file;
            return file2;
        }
    }

    public static void checkFile(Map<String, ZipUtils.ZipSubFile> zipFiles) throws JQException {
        if (CollectionUtils.isEmpty(zipFiles)) {
            throw new JQException((ErrorEnum)SchemeFileException.FILE_CHECK_ERROR, "\u591a\u7ea7\u90e8\u7f72\u5bfc\u5165\u7684\u6587\u4ef6\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u6587\u4ef6\u662f\u5426\u6b63\u786e\uff01");
        }
        if (zipFiles.get("param.json") == null) {
            throw new JQException((ErrorEnum)SchemeFileException.FILE_CHECK_ERROR, "\u591a\u7ea7\u90e8\u7f72\u5bfc\u5165\u7684\u6587\u4ef6\u6821\u9a8c\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u6587\u4ef6\u662f\u5426\u6b63\u786e\uff01");
        }
    }

    public static AnalysisDTO getSchemeParam(Map<String, ZipUtils.ZipSubFile> zipFiles, AnalysisParam analysisParam) throws Exception {
        ZipUtils.ZipSubFile versionSubFile = zipFiles.get("version.json");
        AnalysisDTO analysisDTO = FileHelper.getSyncSchemeParamDTO(zipFiles, analysisParam.getMappingType());
        analysisDTO.setFormDataZipMap(zipFiles);
        if (versionSubFile != null) {
            analysisDTO.setNrd(true);
        }
        return analysisDTO;
    }

    private static AnalysisDTO getSyncSchemeParamDTO(Map<String, ZipUtils.ZipSubFile> zipFiles, MappingType mappingType) {
        AnalysisDTO analysisDTO = new AnalysisDTO();
        ZipUtils.ZipSubFile syncSchemeParamFile = zipFiles.get("param.json");
        String tempPath = null;
        SyncSchemeParamDTO param = new SyncSchemeParamDTO();
        try {
            tempPath = ZipUtils.newTempDir();
            File paramFile = FileHelper.getTempFile(syncSchemeParamFile, tempPath);
            String paramValues = FileUtils.readFileToString(paramFile, Charset.defaultCharset());
            ObjectMapper mapper = new ObjectMapper();
            param = (SyncSchemeParamDTO)mapper.readValue(paramValues, SyncSchemeParamDTO.class);
            analysisDTO.setSyncSchemeParamDTO(param);
            if (StringUtils.hasLength(param.getMappingSchemeKey())) {
                logger.info("\u591a\u7ea7\u90e8\u7f72\u5bfc\u5165\u6570\u636e\u65f6\u5019\uff0c\u68c0\u6d4b\u5230\u5bfc\u51fa\u65f6\u5019\u505a\u8fc7\u6620\u5c04\uff0c\u6620\u5c04\u65b9\u6848key\u4e3a{}", (Object)param.getMappingSchemeKey());
            }
            if (mappingType.getValue() == 0 && StringUtils.hasLength(param.getMappingSchemeKey())) {
                mappingType = MappingType.EXPORT_MAPPING;
            }
            analysisDTO.setMappingType(mappingType);
            if (zipFiles.get("srcParam.json") != null) {
                ZipUtils.ZipSubFile srcParamFileParam = zipFiles.get("srcParam.json");
                File srcParamFile = FileHelper.getTempFile(srcParamFileParam, tempPath);
                String srcParamValues = FileUtils.readFileToString(srcParamFile, Charset.defaultCharset());
                SrcParamDTO srcParamDTO = (SrcParamDTO)mapper.readValue(srcParamValues, SrcParamDTO.class);
                analysisDTO.setSrcParamDTO(srcParamDTO);
            }
        }
        catch (Exception e) {
            logger.error("\u591a\u7ea7\u90e8\u7f72\u6570\u636e\u5305\u53c2\u6570\u6587\u4ef6\u89e3\u6790\u5931\u8d25" + e.getMessage());
            throw new RuntimeException("\u591a\u7ea7\u90e8\u7f72\u6570\u636e\u5305\u53c2\u6570\u6587\u4ef6\u89e3\u6790\u5931\u8d25" + e.getMessage(), e);
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(tempPath);
        }
        return analysisDTO;
    }

    public static void exportFile(File file, String fileName, HttpServletResponse response) throws Exception {
        if (file.exists() && file.isFile()) {
            try (BufferedInputStream ins = new BufferedInputStream(Files.newInputStream(file.toPath(), new OpenOption[0]));
                 BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
                int bytesRead;
                long fileLength = file.length();
                response.setCharacterEncoding("utf-8");
                String s = fileName + ".nrd";
                String headerValue = HtmlUtils.cleanHeaderValue((String)("attachment;filename=" + URLEncoder.encode(s, "UTF-8")));
                HtmlUtils.validateHeaderValue((String)headerValue);
                response.addHeader("Content-Disposition", headerValue);
                response.addHeader("Content-Length", "" + fileLength);
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.setContentType("application/octet-stream");
                long start = 0L;
                byte[] buffer = new byte[8192];
                ((InputStream)ins).skip(start);
                for (long bytesToRead = fileLength; bytesToRead > 0L && (bytesRead = ((InputStream)ins).read(buffer, 0, (int)Math.min((long)buffer.length, bytesToRead))) != -1; bytesToRead -= (long)bytesRead) {
                    try {
                        ((OutputStream)ous).write(buffer, 0, bytesRead);
                        continue;
                    }
                    catch (IOException e) {
                        // empty catch block
                        break;
                    }
                }
                ((OutputStream)ous).flush();
            }
            catch (Exception e) {
                throw e;
            }
            finally {
                if (file.exists()) {
                    Utils.deleteAllFilesOfDirByPath(file.getParent());
                }
            }
        }
    }
}


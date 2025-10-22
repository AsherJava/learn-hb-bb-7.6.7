/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.sf.adapter.spring.multipart.dto.GlobalCheckerPropertiesDto
 *  com.jiuqi.nvwa.sf.adapter.spring.multipart.service.IMultipartCheckerService
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.fileupload.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.fileupload.FileUploadReturnExtInfo;
import com.jiuqi.nr.fileupload.FileUploadReturnInfo;
import com.jiuqi.nr.fileupload.FilesUploadReturnInfo;
import com.jiuqi.nr.fileupload.service.CheckUploadFileExtendService;
import com.jiuqi.nr.fileupload.service.CheckUploadFileService;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.fileupload.util.FileTypeByteEnum;
import com.jiuqi.nr.fileupload.util.FileUploadErrorTypeEnum;
import com.jiuqi.nr.fileupload.util.FileUploadUtils;
import com.jiuqi.nvwa.sf.adapter.spring.multipart.dto.GlobalCheckerPropertiesDto;
import com.jiuqi.nvwa.sf.adapter.spring.multipart.service.IMultipartCheckerService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CheckUploadFileServiceImpl
implements CheckUploadFileService {
    private static final Logger logger = LoggerFactory.getLogger(CheckUploadFileServiceImpl.class);
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired(required=false)
    private Map<String, CheckUploadFileExtendService> checkUploadFileExtendServiceMap;
    @Autowired
    private IMultipartCheckerService iMultipartCheckerService;

    @Override
    public FilesUploadReturnInfo checkUploadFileInfo(MultipartFile[] files, List<String> sceneList, String appName) {
        FilesUploadReturnInfo filesUploadReturnInfo = new FilesUploadReturnInfo();
        HashMap<String, FileUploadReturnInfo> fileNameUploadInfoMap = new HashMap();
        FilesUploadReturnInfo checkFileTypeReturnInfo = this.checkFileType(files);
        fileNameUploadInfoMap = checkFileTypeReturnInfo.getFileUploadReturnInfoMap();
        for (MultipartFile file : files) {
            CheckUploadFileExtendService checkUploadFileExtendService;
            if (fileNameUploadInfoMap.containsKey(file.getName())) continue;
            FileUploadReturnInfo fileUploadReturnInfo = this.checkFileInfo(file.getOriginalFilename(), file.getSize(), sceneList, appName);
            if (this.checkUploadFileExtendServiceMap != null && fileUploadReturnInfo.isSuccess() && !this.checkUploadFileExtendServiceMap.isEmpty() && (checkUploadFileExtendService = this.checkUploadFileExtendServiceMap.get(appName)) != null) {
                FileUploadReturnExtInfo fileUploadReturnExtInfo = checkUploadFileExtendService.checkUploadFileInfo(file);
                fileUploadReturnInfo.setSuccess(fileUploadReturnExtInfo.isCheckSuccess());
                fileUploadReturnInfo.setMessage(fileUploadReturnExtInfo.getErrorMessage());
                if (!fileUploadReturnExtInfo.isCheckSuccess()) {
                    fileUploadReturnInfo.setErrorType(FileUploadErrorTypeEnum.FILE_UPLOAD_OTHER_ERROR.getCode());
                }
            }
            if (fileUploadReturnInfo.isSuccess()) {
                ObjectInfo fileInfo = null;
                try {
                    fileInfo = this.fileUploadOssService.uploadFileToTemp(file);
                }
                catch (Exception e) {
                    logger.error("\u6587\u4ef6\u4e0a\u4f20oss\u5f02\u5e38\uff1a" + e.getMessage(), e);
                    fileUploadReturnInfo.setSuccess(false);
                    fileUploadReturnInfo.setMessage(e.getMessage());
                    fileUploadReturnInfo.setErrorType(FileUploadErrorTypeEnum.FILE_UPLOAD_EXCEPTION_ERROR.getCode());
                    filesUploadReturnInfo.setAllIsSuccess(false);
                }
                if (fileInfo != null) {
                    fileUploadReturnInfo.setFileInfoKey(fileInfo.getKey());
                }
                if (!fileUploadReturnInfo.isSuccess()) {
                    filesUploadReturnInfo.setAllIsSuccess(false);
                }
            } else {
                filesUploadReturnInfo.setAllIsSuccess(false);
            }
            fileNameUploadInfoMap.put(file.getOriginalFilename(), fileUploadReturnInfo);
        }
        filesUploadReturnInfo.setFileUploadReturnInfoMap(fileNameUploadInfoMap);
        return filesUploadReturnInfo;
    }

    @Override
    public FilesUploadReturnInfo checkUploadFileSuffix(Map<String, Long> fileNameSizeMap, List<String> sceneList, String appName) {
        FilesUploadReturnInfo filesUploadReturnInfo = new FilesUploadReturnInfo();
        HashMap<String, FileUploadReturnInfo> fileNameUploadInfoMap = new HashMap<String, FileUploadReturnInfo>();
        for (String fileName : fileNameSizeMap.keySet()) {
            FileUploadReturnInfo fileUploadReturnInfo = this.checkFileInfo(fileName, fileNameSizeMap.get(fileName), sceneList, appName);
            if (!fileUploadReturnInfo.isSuccess()) {
                filesUploadReturnInfo.setAllIsSuccess(false);
            }
            fileNameUploadInfoMap.put(fileName, fileUploadReturnInfo);
        }
        filesUploadReturnInfo.setFileUploadReturnInfoMap(fileNameUploadInfoMap);
        return filesUploadReturnInfo;
    }

    @Override
    public Map<String, Object> getAllCheckInfo() {
        HashMap<String, Object> checkSysOptInfo = new HashMap<String, Object>();
        GlobalCheckerPropertiesDto globalCheckerProperties = this.iMultipartCheckerService.getGlobalCheckerProperties();
        String enableBlackOrWhiteList = this.enableBlackOrWhiteList(globalCheckerProperties);
        checkSysOptInfo.put("BLACK_LIST_AND_WHITE_LIST", enableBlackOrWhiteList);
        ArrayList deniedFileTypes = globalCheckerProperties.getDeniedFileTypes();
        if (deniedFileTypes == null) {
            deniedFileTypes = new ArrayList();
        }
        String blackListStr = String.join((CharSequence)";", deniedFileTypes);
        blackListStr = blackListStr.replace(".", "-");
        checkSysOptInfo.put("BLACK_LIST_INFO", blackListStr);
        ArrayList allowedFileTypes = globalCheckerProperties.getAllowedFileTypes();
        if (allowedFileTypes == null) {
            allowedFileTypes = new ArrayList();
        }
        String whiteListStr = String.join((CharSequence)";", allowedFileTypes);
        whiteListStr = whiteListStr.replace(".", "-");
        checkSysOptInfo.put("WHITE_LIST_INFO", whiteListStr);
        DataSize maxFileSize = globalCheckerProperties.getMaxFileSize();
        if (maxFileSize != null) {
            checkSysOptInfo.put("FILE_UPLOAD_MAX_SIZE", globalCheckerProperties.getMaxFileSize().toKilobytes());
        } else {
            checkSysOptInfo.put("FILE_UPLOAD_MAX_SIZE", "");
        }
        return checkSysOptInfo;
    }

    private String enableBlackOrWhiteList(GlobalCheckerPropertiesDto globalCheckerProperties) {
        List allowedFileTypes = globalCheckerProperties.getAllowedFileTypes();
        if (allowedFileTypes != null && allowedFileTypes.size() > 0) {
            return "1";
        }
        return "0";
    }

    @Override
    public FileUploadReturnInfo checkFileInfo(String fileName, Long fileSize, List<String> sceneList, String appName) {
        String fileUploadMaxSizeStr;
        String fileSuffix = "";
        fileSuffix = (fileName = fileName.trim()).lastIndexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : "-";
        String fileLowerSuffix = fileSuffix.toLowerCase();
        FileUploadReturnInfo fileUploadReturnInfo = new FileUploadReturnInfo();
        Map<String, Object> checkSysOptInfo = this.getAllCheckInfo();
        String blackListInfo = checkSysOptInfo.get("BLACK_LIST_INFO").toString();
        String whiteListInfo = checkSysOptInfo.get("WHITE_LIST_INFO").toString();
        if (!StringUtils.isEmpty((String)blackListInfo) || !StringUtils.isEmpty((String)whiteListInfo)) {
            String[] blackOrWhiteArray;
            List<String> blackOrWhiteList;
            List blackOrWhiteLowerList;
            boolean isBlackList = checkSysOptInfo.get("BLACK_LIST_AND_WHITE_LIST").toString().equals("0");
            boolean isWhiteList = checkSysOptInfo.get("BLACK_LIST_AND_WHITE_LIST").toString().equals("1");
            if (isBlackList && (blackOrWhiteLowerList = (blackOrWhiteList = Arrays.asList(blackOrWhiteArray = blackListInfo.split(";"))).stream().map(String::toLowerCase).collect(Collectors.toList())).contains(fileLowerSuffix)) {
                if (fileSuffix.equals("-")) {
                    fileUploadReturnInfo.setMessage("\u4e0d\u5141\u8bb8\u4e0a\u4f20\u65e0\u6269\u5c55\u540d\u7684\u6587\u4ef6(" + fileName + ")");
                } else {
                    fileUploadReturnInfo.setMessage("\u4e0a\u4f20\u5931\u8d25\uff0c\u4e0d\u652f\u6301\u7684\u6587\u4ef6\u7c7b\u578b");
                }
                fileUploadReturnInfo.setSuccess(false);
                fileUploadReturnInfo.setConfigInfo(blackListInfo.replace(";", ","));
                fileUploadReturnInfo.setErrorType(FileUploadErrorTypeEnum.BLACK_LIST_ERROR.getCode());
                return fileUploadReturnInfo;
            }
            if (isWhiteList && !(blackOrWhiteLowerList = (blackOrWhiteList = Arrays.asList(blackOrWhiteArray = whiteListInfo.split(";"))).stream().map(String::toLowerCase).collect(Collectors.toList())).contains(fileLowerSuffix)) {
                if (fileSuffix.equals("-")) {
                    fileUploadReturnInfo.setMessage("\u4e0d\u5141\u8bb8\u4e0a\u4f20\u65e0\u6269\u5c55\u540d\u7684\u6587\u4ef6(" + fileName + ")");
                } else {
                    fileUploadReturnInfo.setMessage("\u4e0a\u4f20\u5931\u8d25\uff0c\u4e0d\u652f\u6301\u7684\u6587\u4ef6\u7c7b\u578b");
                }
                fileUploadReturnInfo.setSuccess(false);
                fileUploadReturnInfo.setConfigInfo(whiteListInfo.replace(";", ","));
                fileUploadReturnInfo.setErrorType(FileUploadErrorTypeEnum.WHITE_LIST_ERROR.getCode());
                return fileUploadReturnInfo;
            }
        }
        if (!StringUtils.isEmpty((String)(fileUploadMaxSizeStr = checkSysOptInfo.get("FILE_UPLOAD_MAX_SIZE").toString())) && !fileUploadMaxSizeStr.equals("0")) {
            Long fileUploadMaxSize = Long.valueOf(fileUploadMaxSizeStr) * 1024L;
            if (fileSize > Long.valueOf(fileUploadMaxSize)) {
                fileUploadReturnInfo.setMessage("\u6587\u4ef6" + this.formatFileSize(fileSize) + "\uff0c\u8d85\u8fc7\u5f53\u524d\u9650\u5236" + this.formatFileSize(fileUploadMaxSize));
                fileUploadReturnInfo.setSuccess(false);
                fileUploadReturnInfo.setConfigInfo(String.valueOf(fileUploadMaxSize) + "B");
                fileUploadReturnInfo.setErrorType(FileUploadErrorTypeEnum.FILE_SIZE_ERROR.getCode());
                return fileUploadReturnInfo;
            }
        }
        return fileUploadReturnInfo;
    }

    public String formatFileSize(long sizeBytes) {
        if (sizeBytes <= 0L) {
            return "0 B";
        }
        String[] units = new String[]{"B", "KB", "MB", "GB"};
        int unitIndex = (int)(Math.log(sizeBytes) / Math.log(1024.0));
        unitIndex = Math.min(unitIndex, units.length - 1);
        double sizeInUnit = (double)sizeBytes / Math.pow(1024.0, unitIndex);
        DecimalFormat df = new DecimalFormat("#");
        return df.format(sizeInUnit) + units[unitIndex];
    }

    @Override
    public FilesUploadReturnInfo checkFileType(MultipartFile[] files) {
        FilesUploadReturnInfo filesUploadReturnInfo = new FilesUploadReturnInfo();
        HashMap<String, FileUploadReturnInfo> fileUploadReturnInfoMap = new HashMap<String, FileUploadReturnInfo>();
        FileTypeByteEnum[] fileTypes = FileTypeByteEnum.values();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            for (FileTypeByteEnum fileType : fileTypes) {
                String fileTypeByteStr;
                if (!fileType.getSuffix().equalsIgnoreCase(fileSuffix) || (fileTypeByteStr = FileUploadUtils.getFileTypeByte(file)) == null || fileTypeByteStr.length() <= 0 || fileTypeByteStr.startsWith(fileType.getCode())) continue;
                FileUploadReturnInfo fileUploadReturnInfo = new FileUploadReturnInfo();
                fileUploadReturnInfo.setSuccess(false);
                fileUploadReturnInfo.setErrorType(FileUploadErrorTypeEnum.SUFFIX_TYPE_MISMATCH.getCode());
                fileUploadReturnInfo.setMessage(FileUploadErrorTypeEnum.SUFFIX_TYPE_MISMATCH.getMessage());
                fileUploadReturnInfoMap.put(fileName, fileUploadReturnInfo);
                filesUploadReturnInfo.setAllIsSuccess(false);
            }
        }
        filesUploadReturnInfo.setFileUploadReturnInfoMap(fileUploadReturnInfoMap);
        return filesUploadReturnInfo;
    }
}


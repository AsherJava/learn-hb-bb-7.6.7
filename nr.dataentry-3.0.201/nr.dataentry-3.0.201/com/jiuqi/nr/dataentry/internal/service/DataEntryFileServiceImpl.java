/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.fileupload.FileUploadReturnInfo
 *  com.jiuqi.nr.fileupload.FilesUploadReturnInfo
 *  com.jiuqi.nr.fileupload.service.CheckUploadFileService
 *  com.jiuqi.nr.jtable.params.base.FileLinkData
 *  com.jiuqi.nr.jtable.params.base.LinkData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.dataentry.attachment.service.AttachmentOperationService;
import com.jiuqi.nr.dataentry.bean.FilesCheckUploadResult;
import com.jiuqi.nr.dataentry.paramInfo.AttachmentInfo;
import com.jiuqi.nr.dataentry.paramInfo.FileParamInfo;
import com.jiuqi.nr.dataentry.paramInfo.FileUploadParams;
import com.jiuqi.nr.dataentry.paramInfo.FilesUploadInfo;
import com.jiuqi.nr.dataentry.service.IDataEntryFileService;
import com.jiuqi.nr.fileupload.FileUploadReturnInfo;
import com.jiuqi.nr.fileupload.FilesUploadReturnInfo;
import com.jiuqi.nr.fileupload.service.CheckUploadFileService;
import com.jiuqi.nr.jtable.params.base.FileLinkData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DataEntryFileServiceImpl
implements IDataEntryFileService {
    @Autowired
    private CheckUploadFileService checkUploadFileService;
    @Autowired
    private AttachmentOperationService attachmentOperationService;
    @Autowired
    private IJtableParamService iJtableParamService;

    @Override
    public Map<String, Object> getAllSysUploadConfig() {
        return this.checkUploadFileService.getAllCheckInfo();
    }

    @Override
    public FilesCheckUploadResult checkAndUploadFiles(MultipartFile[] files, FileUploadParams fileUploadParams) {
        FilesCheckUploadResult filesCheckUploadResult = new FilesCheckUploadResult();
        FilesUploadInfo filesUploadInfo = new FilesUploadInfo();
        if (!filesCheckUploadResult.isAllIsSuccess()) {
            return filesCheckUploadResult;
        }
        HashedMap<String, AttachmentInfo> fileUploadInfoMap = new HashedMap<String, AttachmentInfo>();
        FilesUploadReturnInfo filesUploadReturnInfo = this.checkUploadFileService.checkUploadFileInfo(files, fileUploadParams.getSceneList(), fileUploadParams.getAppName());
        Map fileUploadReturnInfoMap = filesUploadReturnInfo.getFileUploadReturnInfoMap();
        Map<String, FileParamInfo> fileParamInfoMap = fileUploadParams.getFileParamInfoMap();
        for (String fileName : fileUploadReturnInfoMap.keySet()) {
            FileParamInfo fileParamInfo;
            FileUploadReturnInfo fileUploadReturnInfo = (FileUploadReturnInfo)fileUploadReturnInfoMap.get(fileName);
            String ossFileKey = fileUploadReturnInfo.getFileInfoKey();
            if (!fileUploadReturnInfo.isSuccess() || !StringUtils.isNotEmpty((String)ossFileKey) || !fileParamInfoMap.containsKey(fileName) || (fileParamInfo = fileParamInfoMap.get(fileName)) == null) continue;
            AttachmentInfo attachmentInfo = new AttachmentInfo();
            attachmentInfo.setOssFileKey(ossFileKey);
            attachmentInfo.setCovered(fileParamInfo.isCovered());
            attachmentInfo.setDataLinkKey(fileParamInfo.getDataLinkKey());
            attachmentInfo.setFileKey(fileParamInfo.getFileKey());
            attachmentInfo.setFileSecret(fileParamInfo.getFileSecret());
            attachmentInfo.setGroupKey(fileParamInfo.getGroupKey());
            attachmentInfo.setImgFieldType(fileParamInfo.isImgFieldType());
            attachmentInfo.setUploadType(fileParamInfo.getUploadType());
            fileUploadInfoMap.put(fileName, attachmentInfo);
        }
        if (fileUploadInfoMap != null && fileUploadInfoMap.size() > 0) {
            FileUploadReturnInfo fileUploadReturnInfo;
            filesUploadInfo.setContext(fileUploadParams.getContext());
            filesUploadInfo.setCovered(fileUploadParams.isCovered());
            filesUploadInfo.setFieldKey(fileUploadParams.getFieldKey());
            filesUploadInfo.setGroupKey(fileUploadParams.getGroupKey());
            filesUploadInfo.setFileUploadInfoMap(fileUploadInfoMap);
            String fileGroupKey = this.attachmentOperationService.uploadFiles(filesUploadInfo).getMessage();
            if (StringUtils.isNotEmpty((String)fileGroupKey)) {
                filesCheckUploadResult.setGroupKey(fileGroupKey);
                for (String fileName : fileUploadReturnInfoMap.keySet()) {
                    fileUploadReturnInfo = (FileUploadReturnInfo)fileUploadReturnInfoMap.get(fileName);
                    if (!fileUploadReturnInfo.isSuccess()) continue;
                    fileUploadReturnInfo.setFileInfoKey(null);
                }
            } else {
                filesUploadReturnInfo.setAllIsSuccess(false);
                for (String fileName : fileUploadReturnInfoMap.keySet()) {
                    fileUploadReturnInfo = (FileUploadReturnInfo)fileUploadReturnInfoMap.get(fileName);
                    if (!fileUploadReturnInfo.isSuccess()) continue;
                    fileUploadReturnInfo.setFileInfoKey(null);
                    fileUploadReturnInfo.setSuccess(false);
                    fileUploadReturnInfo.setMessage("\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25");
                }
            }
        }
        filesCheckUploadResult.setAllIsSuccess(filesUploadReturnInfo.isAllIsSuccess());
        filesCheckUploadResult.setFileUploadReturnInfoMap(fileUploadReturnInfoMap);
        return filesCheckUploadResult;
    }

    public FilesCheckUploadResult checkFileCellCondition(MultipartFile[] files, String linkKey) {
        FilesCheckUploadResult filesCheckUploadResult = new FilesCheckUploadResult();
        if (StringUtils.isEmpty((String)linkKey)) {
            return filesCheckUploadResult;
        }
        HashMap<String, FileUploadReturnInfo> fileUploadReturnInfoMap = new HashMap<String, FileUploadReturnInfo>();
        LinkData link = this.iJtableParamService.getLink(linkKey);
        if (link == null || !(link instanceof FileLinkData)) {
            return filesCheckUploadResult;
        }
        FileLinkData fileLinkData = (FileLinkData)link;
        int num = fileLinkData.getNum();
        List types = fileLinkData.getTypes();
        float minSizeMB = fileLinkData.getMinSize();
        long fileMinSizeMB = (long)minSizeMB;
        Long fileMinSizeB = 0L;
        if (minSizeMB > 0.0f) {
            fileMinSizeB = fileMinSizeMB * 1024L * 1024L;
        }
        float maxSizeMB = fileLinkData.getSize();
        long fileMaxSizeMB = (long)maxSizeMB;
        Long fileMaxSizeB = 20480000000L;
        if (maxSizeMB > 0.0f) {
            fileMaxSizeB = fileMaxSizeMB * 1024L * 1024L;
        }
        boolean checkSuccess = true;
        for (MultipartFile file : files) {
            String extName;
            int fileNameIndex;
            FileUploadReturnInfo fileUploadReturnInfo = new FileUploadReturnInfo();
            long fileSize = file.getSize();
            String fileName = file.getOriginalFilename();
            if (num > 0 && files.length > num) {
                checkSuccess = false;
                fileUploadReturnInfo.setSuccess(false);
                fileUploadReturnInfo.setMessage("\u6587\u4ef6\u4e0a\u4f20\u5df2\u8fbe\u5230\u6700\u5927\u4e0a\u9650\u6570");
                fileUploadReturnInfoMap.put(fileName, fileUploadReturnInfo);
                break;
            }
            if (fileSize > fileMaxSizeB) {
                checkSuccess = false;
                fileUploadReturnInfo.setSuccess(false);
                fileUploadReturnInfo.setMessage("\u6587\u4ef6\u5927\u5c0f\u4e0d\u80fd\u8d85\u8fc7" + String.valueOf(fileMaxSizeMB) + "M");
                fileUploadReturnInfoMap.put(fileName, fileUploadReturnInfo);
                break;
            }
            if (fileSize < fileMinSizeB) {
                checkSuccess = false;
                fileUploadReturnInfo.setSuccess(false);
                fileUploadReturnInfo.setMessage("\u6587\u4ef6\u5927\u5c0f\u4e0d\u80fd\u4f4e\u4e8e" + String.valueOf(fileMinSizeMB) + "M");
                fileUploadReturnInfoMap.put(fileName, fileUploadReturnInfo);
                break;
            }
            if (types == null || types.get(0) == "*" || (fileNameIndex = fileName.lastIndexOf(46)) < 0 || types.contains(extName = fileName.substring(fileNameIndex + 1).toLowerCase())) continue;
            checkSuccess = false;
            fileUploadReturnInfo.setSuccess(false);
            fileUploadReturnInfo.setMessage("\u4e0a\u4f20\u5931\u8d25\uff0c\u4e0d\u652f\u6301\u7684\u6587\u4ef6\u7c7b\u578b");
            fileUploadReturnInfoMap.put(fileName, fileUploadReturnInfo);
            break;
        }
        if (!checkSuccess) {
            filesCheckUploadResult.setAllIsSuccess(false);
            filesCheckUploadResult.setFileUploadReturnInfoMap(fileUploadReturnInfoMap);
        }
        return filesCheckUploadResult;
    }
}


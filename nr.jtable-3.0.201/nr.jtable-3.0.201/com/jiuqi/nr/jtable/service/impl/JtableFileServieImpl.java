/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.zip.ZipEntry
 *  com.jiuqi.bi.util.zip.ZipOutputStream
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.output.FileInfosAndGroup
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.attachment.utils.FileInfoBuilder
 *  com.jiuqi.nr.attachment.utils.FileStatus
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.impl.FileInfoImpl
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.file.web.FileType
 *  com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject
 *  com.jiuqi.nr.sensitive.service.CheckSensitiveWordService
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  net.coobird.thumbnailator.Thumbnails
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.zip.ZipEntry;
import com.jiuqi.bi.util.zip.ZipOutputStream;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FileInfosAndGroup;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.file.web.FileType;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.FieldNameIsSensitiveWordException;
import com.jiuqi.nr.jtable.exception.FileTypeErrorException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FileLinkData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.LinkFileOptionInfo;
import com.jiuqi.nr.jtable.params.input.LinkImgOptionInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableFileService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.service.impl.JtableResourceServiceImpl;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject;
import com.jiuqi.nr.sensitive.service.CheckSensitiveWordService;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JtableFileServieImpl
implements IJtableFileService {
    private static final Logger logger = LoggerFactory.getLogger(JtableResourceServiceImpl.class);
    @Autowired
    private ISecretLevelService secretLevelService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CheckSensitiveWordService checkSensitiveWordService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private FilePoolService filePoolService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    private static final String[] acceptFileTypes = new String[]{"doc", "docx", "txt", "xls", "xlsx", "ppt", "pptx", "html", "htm", "pdf", "jpg", "png", "gif", "bmp", "psd", "tiff", "avi", "swf", "wmv", "rmvb", "mp4", "3gp", "mov", "mp3", "m4a", "wma", "rar", "zip", "7z", "jio", "wps", "wpt", "rtf", "dps", "dpt", "pot", "potx", "pps", "ppsx", "et", "ett", "xlt", "xltx", "csv"};

    @Override
    public List<FileInfo> getJableFiles(JtableContext jtableContext) {
        Map<String, Object> variableMap = jtableContext.getVariableMap();
        String groupKey = (String)variableMap.get("groupKey");
        SecretLevelInfo secretLevel = null;
        if (this.secretLevelService.secretLevelEnable(jtableContext.getTaskKey())) {
            secretLevel = this.secretLevelService.getSecretLevel(jtableContext);
        }
        CommonParamsDTO params = this.getCommonParamsDTO(jtableContext.getTaskKey());
        List fileInfoByGroupAboutFilepool = this.filePoolService.getFileInfoByGroup(groupKey, params);
        ArrayList files = new ArrayList(fileInfoByGroupAboutFilepool);
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        try {
            if (files.size() == 0) {
                return fileInfos;
            }
            for (FileInfo file : files) {
                boolean canAccess = true;
                if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && null != secretLevel) {
                    SecretLevelItem secretLevelItem = this.secretLevelService.getSecretLevelItem(file.getSecretlevel());
                    boolean bl = canAccess = this.secretLevelService.compareSercetLevel(secretLevel.getSecretLevelItem(), secretLevelItem) && this.secretLevelService.canAccess(secretLevelItem);
                }
                if (!canAccess) continue;
                String path = this.fileOperationService.getFilePath(file.getKey(), NpContextHolder.getContext().getTenant(), params);
                byte[] textByte = path.getBytes("UTF-8");
                FileInfo ff = com.jiuqi.nr.attachment.utils.FileInfoBuilder.newFileInfo((FileInfo)file, (String)groupKey, (String)Base64.getEncoder().encodeToString(textByte));
                if (null != secretLevel) {
                    ff.setSecretlevel(file.getSecretlevel());
                }
                fileInfos.add(ff);
            }
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return fileInfos;
    }

    @Override
    public List<SecretLevelItem> getFileSecretItems(JtableContext jtableContext) {
        return this.secretLevelService.getSecretLevelItems(jtableContext);
    }

    @Override
    public com.jiuqi.nr.file.FileInfo uploadJableFiles(byte[] bytes, String fileName, LinkFileOptionInfo linkFileOptionInfo) {
        String jtableFileArea = "JTABLEAREA";
        String groupKey = this.getGroupKey(linkFileOptionInfo);
        LinkData link = this.jtableParamService.getLink(linkFileOptionInfo.getDataLinkKey());
        List<String> acceptTypes = null;
        FileInfoImpl fi = null;
        if (bytes.length <= 0) {
            throw new FileTypeErrorException(JtableExceptionCodeCost.EXCEPTION_FILE_SIZE_IS_NULL, new String[]{"\u4e0a\u4f20\u6587\u4ef6\u5927\u5c0f\u4e0d\u80fd\u4e3a0"});
        }
        if (link instanceof FileLinkData) {
            acceptTypes = ((FileLinkData)link).getTypes();
        }
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!JtableFileServieImpl.isContains(Arrays.asList(acceptFileTypes), fileType) || !JtableFileServieImpl.isContains(acceptTypes, fileType)) {
            String msg = "\u4e0a\u4f20\u6587\u4ef6\u7c7b\u578b\u9519\u8bef";
            throw new FileTypeErrorException(JtableExceptionCodeCost.EXCEPTION_FILE_TYPE_IS_ERROR, new String[]{msg.toString()});
        }
        try {
            fileName = fileName.replaceAll("\n", "");
            List sensitiveWordList = this.checkSensitiveWordService.thisWordIsSensitiveWord(fileName.substring(0, fileName.lastIndexOf(".")));
            if (sensitiveWordList.size() > 0) {
                StringBuilder msg = new StringBuilder();
                msg.append("\u6587\u4ef6\u540d\u5305\u542b\u654f\u611f\u8bcd\u4fe1\u606f,");
                for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordList) {
                    if (sensitiveWordDaoObject.getSensitiveDescription() == null || sensitiveWordDaoObject.getSensitiveDescription().length() <= 0) continue;
                    msg.append(sensitiveWordDaoObject.getSensitiveDescription());
                    msg.append(";");
                }
                msg.append("\u8bf7\u4fee\u6539\u540e\u4e0a\u4f20\uff01");
                throw new FieldNameIsSensitiveWordException(JtableExceptionCodeCost.EXCEPTION_FILE_NAME_IS_SENSITIVE, new String[]{msg.toString()});
            }
            String fileSecret = linkFileOptionInfo.getFileSecret();
            if (linkFileOptionInfo.isCovered()) {
                if (!StringUtils.isEmpty((String)fileSecret)) {
                    this.fileService.area(jtableFileArea).delete(linkFileOptionInfo.getFileKey());
                    fi = (FileInfoImpl)this.fileService.area(jtableFileArea).uploadByKeyLevle(fileName, linkFileOptionInfo.getFileKey(), groupKey, bytes, fileSecret);
                } else {
                    this.fileService.area(jtableFileArea).delete(linkFileOptionInfo.getFileKey());
                    fi = (FileInfoImpl)this.fileService.area(jtableFileArea).uploadByKey(fileName, linkFileOptionInfo.getFileKey(), groupKey, bytes);
                }
            } else {
                fi = !StringUtils.isEmpty((String)fileSecret) ? (FileInfoImpl)this.fileService.area(jtableFileArea).uploadByGroupLevel(fileName, groupKey, fileSecret, bytes) : (FileInfoImpl)this.fileService.area(jtableFileArea).uploadByGroup(fileName, groupKey, bytes);
            }
            String path = this.fileService.area(jtableFileArea).getPath(fi.getKey(), NpContextHolder.getContext().getTenant());
            byte[] textByte = path.getBytes("UTF-8");
            fi = (FileInfoImpl)FileInfoBuilder.newFileInfo((com.jiuqi.nr.file.FileInfo)fi, (String)fi.getFileGroupKey(), (String)org.apache.commons.codec.binary.Base64.encodeBase64String(textByte));
            fi.setSecretlevel(fileSecret);
            if (linkFileOptionInfo.isImgFieldType()) {
                fileName = fi.getName();
                long size = fi.getSize();
                byte[] smallBytes = null;
                if (size > 20480L) {
                    float proportion = 0.1f;
                    if (size < 512000L && (proportion = (float)(1.0 - (double)((size - 20480L) / 50000L) * 0.1)) < 0.1f) {
                        proportion = 0.1f;
                    }
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                    ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
                    Thumbnails.of((InputStream[])new InputStream[]{inputStream}).scale((double)proportion).toOutputStream((OutputStream)outPutStream);
                    smallBytes = outPutStream.toByteArray();
                } else {
                    smallBytes = bytes;
                }
                com.jiuqi.nr.file.FileInfo smallFile = this.fileService.area(jtableFileArea).uploadByGroup(fileName, fi.getKey(), smallBytes);
                path = this.fileService.area(jtableFileArea).getPath(smallFile.getKey(), NpContextHolder.getContext().getTenant());
                textByte = path.getBytes("UTF-8");
                smallFile = FileInfoBuilder.newFileInfo((com.jiuqi.nr.file.FileInfo)smallFile, (String)smallFile.getFileGroupKey(), (String)org.apache.commons.codec.binary.Base64.encodeBase64String(textByte));
            }
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return fi;
    }

    @Override
    public void downloadJtableFiles(LinkFileOptionInfo linkFileOptionInfo, HttpServletResponse response) {
        String jtableFileArea = this.getFilesArea(linkFileOptionInfo);
        List<com.jiuqi.nr.file.FileInfo> files = this.getFiles(linkFileOptionInfo, jtableFileArea, false);
        if (files.isEmpty()) {
            return;
        }
        FileAreaService fileAreaService = this.fileService.area(jtableFileArea);
        ServletOutputStream outputStream = null;
        String separator_code = "_";
        boolean secretLevelEnable = this.secretLevelService.secretLevelEnable(linkFileOptionInfo.getContext().getTaskKey());
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        if (files.size() == 1) {
            com.jiuqi.nr.file.FileInfo file = files.get(0);
            FileType fileType = FileType.valueOfExtension((String)file.getExtension());
            String fileName = file.getName();
            int length = fileName.length();
            String extension = file.getExtension();
            if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevelEnable) {
                fileName = file.getName().substring(0, length - extension.length()) + separator_code + this.secretLevelService.getSecretLevelItem(file.getSecretlevel()).getTitle() + extension;
            }
            response.setContentType(fileType.getContentType());
            byte[] databytes = fileAreaService.download(file.getKey());
            try {
                fileName = HtmlUtils.cleanHeaderValue((String)URLEncoder.encode(fileName, "UTF-8"));
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + fileName);
                outputStream = response.getOutputStream();
                outputStream.write(databytes);
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        } else {
            String zipName = "\u9644\u4ef6.zip";
            SecretLevelItem enclosureSecert = null;
            for (com.jiuqi.nr.file.FileInfo file : files) {
                SecretLevelItem secretLevelItem = this.secretLevelService.getSecretLevelItem(file.getSecretlevel());
                if (!StringUtils.isNotEmpty((String)file.getSecretlevel()) || !secretLevelEnable || null != enclosureSecert && !this.secretLevelService.compareSercetLevel(secretLevelItem, enclosureSecert)) continue;
                enclosureSecert = secretLevelItem;
            }
            if (null != enclosureSecert && secretLevelEnable) {
                String secretTitle = enclosureSecert.getTitle();
                zipName = "\u9644\u4ef6" + separator_code + secretTitle + ".zip";
            }
            if (!"JTABLEAREA".equals(jtableFileArea)) {
                zipName = "\u6a21\u677f.zip";
            }
            response.setContentType(FileType.ZIP.getContentType());
            BufferedInputStream bis = null;
            try {
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(zipName, "UTF-8"));
                outputStream = response.getOutputStream();
                try (ZipOutputStream zos = new ZipOutputStream((OutputStream)outputStream);){
                    zos.setEncoding("gb2312");
                    for (com.jiuqi.nr.file.FileInfo file : files) {
                        SecretLevelItem secretLevelItem = this.secretLevelService.getSecretLevelItem(file.getSecretlevel());
                        byte[] databytes = fileAreaService.download(file.getKey());
                        String fileName = file.getName();
                        int length = fileName.length();
                        String extension = file.getExtension();
                        if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevelEnable) {
                            fileName = file.getName().substring(0, length - extension.length()) + separator_code + secretLevelItem.getTitle() + extension;
                        }
                        if (null == databytes || databytes.length <= 0) continue;
                        byte[] bufs = new byte[0x6400000];
                        zos.putNextEntry(new ZipEntry(fileName));
                        ByteArrayInputStream swapStream = new ByteArrayInputStream(databytes);
                        bis = new BufferedInputStream(swapStream, 10240);
                        int read = 0;
                        while ((read = bis.read(bufs, 0, 10240)) != -1) {
                            zos.write(bufs, 0, read);
                        }
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        try {
            response.flushBuffer();
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private String getFilesArea(LinkFileOptionInfo linkFileOptionInfo) {
        String downLoadTemplate = linkFileOptionInfo.getDownLoadTemplate();
        if (!StringUtils.isEmpty((String)downLoadTemplate)) {
            String[] split = downLoadTemplate.split("\\|");
            return split[1];
        }
        return "JTABLEAREA";
    }

    @Override
    public ReturnInfo updateJtableFilesSecret(LinkFileOptionInfo linkFileOptionInfo) {
        List<com.jiuqi.nr.file.FileInfo> files = this.getFiles(linkFileOptionInfo, "JTABLEAREA", false);
        ArrayList<com.jiuqi.nr.file.FileInfo> updateFiles = new ArrayList<com.jiuqi.nr.file.FileInfo>();
        FileAreaService fileAreaService = this.fileService.area("JTABLEAREA");
        for (com.jiuqi.nr.file.FileInfo file : files) {
            try {
                if (StringUtils.isNotEmpty((String)file.getSecretlevel())) {
                    fileAreaService.updateLevel(file.getKey(), linkFileOptionInfo.getFileSecret());
                } else {
                    byte[] databytes = fileAreaService.download(file.getKey());
                    fileAreaService.delete(file.getKey(), Boolean.valueOf(false));
                    this.fileService.area(file.getArea()).uploadByKeyLevle(file.getName(), file.getKey(), file.getFileGroupKey(), databytes, linkFileOptionInfo.getFileSecret());
                }
            }
            catch (ObjectStorageException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            updateFiles.add(file);
        }
        ReturnInfo returnInfo = new ReturnInfo();
        if (updateFiles.size() == 1) {
            returnInfo.setMessage("\u6210\u529f\u4fee\u6539\u9644\u4ef6\u5bc6\u7ea7");
        } else {
            returnInfo.setMessage("\u6210\u529f\u4fee\u6539" + updateFiles.size() + "\u4e2a\u9644\u4ef6\u7684\u5bc6\u7ea7");
        }
        return returnInfo;
    }

    @Override
    public ReturnInfo removeJtableFiles(LinkFileOptionInfo linkFileOptionInfo) {
        List<com.jiuqi.nr.file.FileInfo> files = this.getFiles2(linkFileOptionInfo, "JTABLEAREA", true);
        JtableContext context = linkFileOptionInfo.getContext();
        CommonParamsDTO commonParamsDTO = this.getCommonParamsDTO(context.getTaskKey());
        Set fileKeys = files.stream().map(com.jiuqi.nr.file.FileInfo::getKey).collect(Collectors.toSet());
        this.fileOperationService.physicalDeleteFile(fileKeys, commonParamsDTO);
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage("\u6210\u529f\u5220\u9664" + fileKeys.size() + "\u4e2a\u9644\u4ef6\u3002");
        return returnInfo;
    }

    @Override
    public void copyFileGroup(FieldDefine fieldDefine, String fromGroupKey, String toGroupKey) {
        FileAreaService fileAreaService = this.fileService.area("JTABLEAREA");
        if (fieldDefine.getType() == FieldType.FIELD_TYPE_PICTURE) {
            fileAreaService.copyFileGroup("JTABLEAREA", fromGroupKey, toGroupKey);
            List fileInfoListOfFrom = this.fileInfoService.getFileInfoByGroup(fromGroupKey, "JTABLEAREA", FileStatus.AVAILABLE);
            List fileInfoListOfTo = this.fileInfoService.getFileInfoByGroup(toGroupKey, "JTABLEAREA", FileStatus.AVAILABLE);
            for (int index = 0; index < fileInfoListOfFrom.size(); ++index) {
                fileAreaService.copyFileGroup("JTABLEAREA", ((com.jiuqi.nr.file.FileInfo)fileInfoListOfFrom.get(index)).getKey(), ((com.jiuqi.nr.file.FileInfo)fileInfoListOfTo.get(index)).getKey());
            }
            fileAreaService.copyFileGroup("JTABLEAREA", fromGroupKey, toGroupKey);
        } else if (fieldDefine.getType() == FieldType.FIELD_TYPE_FILE) {
            fileAreaService.copyFileGroup("JTABLEAREA", fromGroupKey, toGroupKey);
        }
    }

    private String getGroupKey(LinkFileOptionInfo linkFileOptionInfo) {
        String downLoadTemplate = linkFileOptionInfo.getDownLoadTemplate();
        if (!StringUtils.isEmpty((String)downLoadTemplate)) {
            String[] split = downLoadTemplate.split("\\|");
            return split[0];
        }
        String perGroupKey = linkFileOptionInfo.getGroupKey();
        if (!StringUtils.isEmpty((String)perGroupKey)) {
            return perGroupKey;
        }
        perGroupKey = UUID.randomUUID().toString();
        JtableContext jtableContext = linkFileOptionInfo.getContext();
        LinkData link = this.jtableParamService.getLink(linkFileOptionInfo.getDataLinkKey());
        IDataQuery dataQuery = this.jtableDataEngineService.getDataQuery(jtableContext, link.getRegionKey());
        int columnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)dataQuery, link.getZbid());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        if (StringUtils.isNotEmpty((String)linkFileOptionInfo.getRowId())) {
            RegionData region = this.jtableParamService.getRegion(link.getRegionKey());
            List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(region.getKey(), jtableContext);
            IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
            String[] bizKeys = linkFileOptionInfo.getRowId().split("\\#\\^\\$");
            int i = 0;
            for (FieldData fieldData : bizKeyOrderFieldList.get(0)) {
                String dimensionName = jtableDataEngineService.getDimensionName(fieldData);
                dimensionValueSet.setValue(dimensionName, (Object)bizKeys[i]);
                ++i;
            }
        }
        dataQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = this.jtableDataEngineService.getExecutorContext(jtableContext);
        IDataTable dataTable = null;
        try {
            dataTable = dataQuery.executeQuery(executorContext);
        }
        catch (Exception e) {
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u9644\u4ef6\u5355\u5143\u683c\u67e5\u8be2\u51fa\u9519"});
        }
        String dbGroupKey = "";
        IDataRow dataRow = null;
        if (dataTable.getCount() > 0) {
            dataRow = dataTable.getItem(0);
            try {
                dbGroupKey = dataRow.getAsString(columnIndex);
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        if (StringUtils.isNotEmpty((String)dbGroupKey)) {
            perGroupKey = dbGroupKey;
        }
        return perGroupKey;
    }

    private List<com.jiuqi.nr.file.FileInfo> getFiles(LinkFileOptionInfo linkFileOptionInfo, String jtableFileArea, boolean withSmallFile) {
        List<com.jiuqi.nr.file.FileInfo> fileInfos = new ArrayList();
        String fileKeyStr = linkFileOptionInfo.getFileKey();
        if (StringUtils.isEmpty((String)fileKeyStr)) {
            String groupKey = this.getGroupKey(linkFileOptionInfo);
            fileInfos = this.fileInfoService.getFileInfoByGroup(groupKey, jtableFileArea, FileStatus.AVAILABLE);
            if (linkFileOptionInfo.isImgFieldType() && withSmallFile) {
                for (com.jiuqi.nr.file.FileInfo fileInfo : fileInfos) {
                    List fileInfoOfImg = this.fileInfoService.getFileInfoByGroup(fileInfo.getKey(), jtableFileArea, FileStatus.AVAILABLE);
                    fileInfos.addAll(fileInfoOfImg);
                }
            }
        } else {
            String[] fileKeys = fileKeyStr.split(";");
            for (String fileKey : fileKeys) {
                com.jiuqi.nr.file.FileInfo fileInfo = this.fileInfoService.getFileInfo(fileKey, jtableFileArea, FileStatus.AVAILABLE);
                if (fileInfo == null) continue;
                fileInfos.add(fileInfo);
                if (!linkFileOptionInfo.isImgFieldType() || !withSmallFile) continue;
                List fileInfoOfImg = this.fileInfoService.getFileInfoByGroup(fileInfo.getKey(), jtableFileArea, FileStatus.AVAILABLE);
                fileInfos.addAll(fileInfoOfImg);
            }
        }
        ArrayList<com.jiuqi.nr.file.FileInfo> returnFileInfos = new ArrayList<com.jiuqi.nr.file.FileInfo>();
        ISecretLevelService secretLevelService = (ISecretLevelService)BeanUtil.getBean(ISecretLevelService.class);
        boolean secretLevelEnable = secretLevelService.secretLevelEnable(linkFileOptionInfo.getContext().getTaskKey());
        SecretLevelInfo secretLevel = secretLevelService.getSecretLevel(linkFileOptionInfo.getContext());
        for (com.jiuqi.nr.file.FileInfo file : fileInfos) {
            boolean canAccess = true;
            if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevelEnable && secretLevel != null) {
                SecretLevelItem secretLevelItem = secretLevelService.getSecretLevelItem(file.getSecretlevel());
                boolean bl = canAccess = secretLevelService.compareSercetLevel(secretLevel.getSecretLevelItem(), secretLevelItem) && secretLevelService.canAccess(secretLevelItem);
            }
            if (!canAccess) continue;
            returnFileInfos.add(file);
        }
        return returnFileInfos;
    }

    private List<com.jiuqi.nr.file.FileInfo> getFiles2(LinkFileOptionInfo linkFileOptionInfo, String jtableFileArea, boolean withSmallFile) {
        List<FileInfo> fileInfos = new ArrayList();
        JtableContext context = linkFileOptionInfo.getContext();
        CommonParamsDTO commonParamsDTO = this.getCommonParamsDTO(context.getTaskKey());
        String fileKeyStr = linkFileOptionInfo.getFileKey();
        if (StringUtils.isEmpty((String)fileKeyStr)) {
            String groupKey = this.getGroupKey(linkFileOptionInfo);
            fileInfos = this.fileOperationService.getFileOrPicInfoByGroup(groupKey, commonParamsDTO);
        } else {
            String[] fileKeys;
            for (String fileKey : fileKeys = fileKeyStr.split(";")) {
                FileInfo fileInfoByKey = this.fileOperationService.getFileInfoByKey(fileKey, commonParamsDTO);
                fileInfos.add(fileInfoByKey);
            }
        }
        ArrayList<com.jiuqi.nr.file.FileInfo> returnFileInfos = new ArrayList<com.jiuqi.nr.file.FileInfo>();
        ISecretLevelService secretLevelService = (ISecretLevelService)BeanUtil.getBean(ISecretLevelService.class);
        boolean secretLevelEnable = secretLevelService.secretLevelEnable(linkFileOptionInfo.getContext().getTaskKey());
        SecretLevelInfo secretLevel = secretLevelService.getSecretLevel(linkFileOptionInfo.getContext());
        for (FileInfo file : fileInfos) {
            boolean canAccess = true;
            if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevelEnable && secretLevel != null) {
                SecretLevelItem secretLevelItem = secretLevelService.getSecretLevelItem(file.getSecretlevel());
                boolean bl = canAccess = secretLevelService.compareSercetLevel(secretLevel.getSecretLevelItem(), secretLevelItem) && secretLevelService.canAccess(secretLevelItem);
            }
            if (!canAccess) continue;
            try {
                String path = this.fileOperationService.getFilePath(file.getKey(), NpContextHolder.getContext().getTenant(), commonParamsDTO);
                byte[] textByte = path.getBytes("UTF-8");
                FileInfoImpl ff = (FileInfoImpl)FileInfoBuilder.newFileInfo((String)file.getKey(), (String)file.getArea(), (String)file.getName(), (String)file.getExtension(), (long)file.getSize(), null, (String)file.getCreater(), (Date)file.getCreateTime(), (String)file.getLastModifier(), (Date)file.getLastModifyTime(), (int)file.getVersion(), (String)file.getFileGroupKey(), (String)Base64.getEncoder().encodeToString(textByte));
                ff.setSecretlevel(file.getSecretlevel());
                returnFileInfos.add((com.jiuqi.nr.file.FileInfo)ff);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return returnFileInfos;
    }

    private static boolean isContains(List<String> list, String value) {
        if (null == list || list.size() != 0 && list.get(0).equals("*")) {
            return true;
        }
        for (String string : list) {
            if (string.contains("/")) {
                String[] split;
                for (String s : split = string.split("/")) {
                    if (!s.equalsIgnoreCase(value)) continue;
                    return true;
                }
                continue;
            }
            if (!string.equalsIgnoreCase(value)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void getFileDataMap(List<String> groupFileKeys, DataFormaterCache dataFormaterCache) {
        if (groupFileKeys == null || groupFileKeys.size() == 0) {
            return;
        }
        Map<String, List<com.jiuqi.nr.file.FileInfo>> fileDataMap = dataFormaterCache.getFileDataMap();
        boolean secretLevelEnable = this.secretLevelService.secretLevelEnable(dataFormaterCache.getJtableContext().getTaskKey());
        CommonParamsDTO params = this.getCommonParamsDTO(dataFormaterCache.getJtableContext().getTaskKey());
        List fileInfoByGroups = this.filePoolService.getFileInfoByGroup(groupFileKeys, params);
        for (FileInfosAndGroup fileInfoByGroup : fileInfoByGroups) {
            ArrayList files = new ArrayList();
            files.addAll(fileInfoByGroup.getFileInfos());
            ArrayList<FileInfoImpl> fileInfos = new ArrayList<FileInfoImpl>();
            SecretLevelInfo secretLevel = null;
            if (secretLevelEnable) {
                secretLevel = this.secretLevelService.getSecretLevel(dataFormaterCache.getJtableContext());
            }
            try {
                if (files != null && files.size() > 0) {
                    for (FileInfo file : files) {
                        boolean canAccess = true;
                        if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevelEnable && secretLevel != null) {
                            SecretLevelItem secretLevelItem = this.secretLevelService.getSecretLevelItem(file.getSecretlevel());
                            boolean bl = canAccess = this.secretLevelService.compareSercetLevel(secretLevel.getSecretLevelItem(), secretLevelItem) && this.secretLevelService.canAccess(secretLevelItem);
                        }
                        if (!canAccess) continue;
                        String path = this.fileOperationService.getFilePath(file.getKey(), NpContextHolder.getContext().getTenant(), params);
                        byte[] textByte = path.getBytes("UTF-8");
                        FileInfoImpl ff = (FileInfoImpl)FileInfoBuilder.newFileInfo((String)file.getKey(), (String)file.getArea(), (String)file.getName(), (String)file.getExtension(), (long)file.getSize(), null, (String)file.getCreater(), (Date)file.getCreateTime(), (String)file.getLastModifier(), (Date)file.getLastModifyTime(), (int)file.getVersion(), (String)file.getFileGroupKey(), (String)Base64.getEncoder().encodeToString(textByte));
                        ff.setSecretlevel(file.getSecretlevel());
                        if (com.jiuqi.nr.attachment.utils.FileStatus.UNKNOW.equals((Object)file.getStatus())) {
                            ff.setStatus(FileStatus.UNKNOW);
                        } else if (com.jiuqi.nr.attachment.utils.FileStatus.AVAILABLE.equals((Object)file.getStatus())) {
                            ff.setStatus(FileStatus.AVAILABLE);
                        } else if (com.jiuqi.nr.attachment.utils.FileStatus.RECOVERABLE.equals((Object)file.getStatus())) {
                            ff.setStatus(FileStatus.RECOVERABLE);
                        } else if (com.jiuqi.nr.attachment.utils.FileStatus.DELETED.equals((Object)file.getStatus())) {
                            ff.setStatus(FileStatus.DELETED);
                        }
                        fileInfos.add(ff);
                    }
                }
            }
            catch (UnsupportedEncodingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            fileDataMap.put(fileInfoByGroup.getGroupKey(), fileInfos);
        }
    }

    @Override
    public Map<String, List<byte[]>> queryImgDatas(LinkImgOptionInfo linkImgOptionInfo) {
        JtableContext context = linkImgOptionInfo.getContext();
        CommonParamsDTO commonParamsDTO = this.getCommonParamsDTO(context.getTaskKey());
        String groupFileKey = linkImgOptionInfo.getGroupKey();
        HashMap<String, List<byte[]>> resMap = new HashMap<String, List<byte[]>>();
        ArrayList<byte[]> list = new ArrayList<byte[]>();
        List fileInfos = this.fileOperationService.getFileInfoByGroup(groupFileKey, commonParamsDTO);
        for (FileInfo fileInfo : fileInfos) {
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Throwable throwable = null;
                try {
                    this.fileOperationService.downLoadThumbnail(fileInfo.getKey(), (OutputStream)os, commonParamsDTO);
                    byte[] downFile = os.toByteArray();
                    list.add(downFile);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (os == null) continue;
                    if (throwable != null) {
                        try {
                            os.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    os.close();
                }
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        resMap.put(groupFileKey, list);
        return resMap;
    }

    private CommonParamsDTO getCommonParamsDTO(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
        commonParamsDTO.setDataSchemeKey(taskDefine.getDataScheme());
        commonParamsDTO.setTaskKey(taskDefine.getKey());
        return commonParamsDTO;
    }
}


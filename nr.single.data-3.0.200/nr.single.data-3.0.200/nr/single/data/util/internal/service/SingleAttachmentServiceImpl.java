/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext
 *  com.jiuqi.nr.attachment.input.FileUploadInfo
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.output.FailedFileInfo
 *  com.jiuqi.nr.attachment.output.FileImportResult
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  nr.single.map.data.SingleFieldFileInfo
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.data.util.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FailedFileInfo;
import com.jiuqi.nr.attachment.output.FileImportResult;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import nr.single.data.util.bean.SingleAttachmentFailFile;
import nr.single.data.util.bean.SingleAttachmentResult;
import nr.single.data.util.service.ISingleAttachmentService;
import nr.single.map.data.SingleFieldFileInfo;
import nr.single.map.data.exception.SingleDataException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SingleAttachmentServiceImpl
implements ISingleAttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(SingleAttachmentServiceImpl.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private AttachmentIOService attachIOservice;
    @Value(value="${jiuqi.nr.jio.uploadfileKey:false}")
    private boolean jioUploadFileKey;

    @Override
    public String uploadSingleFiles(List<String> fileNames, String groupKey) throws SingleDataException {
        String newGroupKey = groupKey;
        if (StringUtils.isEmpty((String)newGroupKey)) {
            newGroupKey = UUID.randomUUID().toString();
        }
        String jtableFileArea = "JTABLEAREA";
        ArrayList<com.jiuqi.nr.file.FileInfo> FileInfoList = new ArrayList<com.jiuqi.nr.file.FileInfo>();
        for (String fileName1 : fileNames) {
            try {
                File file = new File(SinglePathUtil.normalize((String)fileName1));
                if (!file.exists()) continue;
                String fileName = file.getName();
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    Throwable throwable = null;
                    try {
                        com.jiuqi.nr.file.FileInfo fi = this.fileService.area(jtableFileArea).uploadByGroup(fileName, groupKey, (InputStream)fileIn);
                        String path = this.fileService.area(jtableFileArea).getPath(fi.getKey(), "__default_tenant__");
                        byte[] textByte = path.getBytes("UTF-8");
                        fi = FileInfoBuilder.newFileInfo((com.jiuqi.nr.file.FileInfo)fi, (String)fi.getFileGroupKey(), (String)Base64.encodeBase64String(textByte));
                        FileInfoList.add(fi);
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (fileIn == null) continue;
                        if (throwable != null) {
                            try {
                                fileIn.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        fileIn.close();
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new SingleDataException(e.getMessage(), (Throwable)e);
                }
            }
            catch (SingleFileException e1) {
                logger.error(e1.getMessage(), e1);
                throw new SingleDataException(e1.getMessage(), (Throwable)e1);
            }
        }
        if (FileInfoList.size() == 0) {
            newGroupKey = null;
        }
        return newGroupKey;
    }

    @Override
    public String uploadSingleFiles(List<String> fileNames, SingleFieldFileInfo fieldFileInfo) throws SingleDataException {
        ArrayList<SingleFieldFileInfo> fileInfos = new ArrayList<SingleFieldFileInfo>();
        for (String fileName1 : fileNames) {
            SingleFieldFileInfo fileInfo = new SingleFieldFileInfo();
            fileInfo.setFilePath(fileName1);
            fileInfos.add(fileInfo);
        }
        return this.uploadSingleFileInfos(fileInfos, fieldFileInfo);
    }

    @Override
    public String uploadSingleFileInfos(List<SingleFieldFileInfo> fileInfos, SingleFieldFileInfo fieldFileInfo) throws SingleDataException {
        SingleAttachmentResult result = this.uploadSingleFileInfosR(fileInfos, fieldFileInfo);
        return result.getGroupKey();
    }

    @Override
    public SingleAttachmentResult uploadSingleFileInfosR(List<SingleFieldFileInfo> fileInfos, SingleFieldFileInfo fieldFileInfo) throws SingleDataException {
        SingleAttachmentResult result = new SingleAttachmentResult();
        String newGroupKey = fieldFileInfo.getGroupKey();
        if (StringUtils.isEmpty((String)newGroupKey)) {
            newGroupKey = UUID.randomUUID().toString();
        }
        result.setGroupKey(newGroupKey);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        Map dimensionSet = fieldFileInfo.getDimensionSet();
        for (String key : dimensionSet.keySet()) {
            dimensionCombinationBuilder.setValue(key, (Object)((DimensionValue)dimensionSet.get(key)).getValue());
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        FileUploadByGroupKeyContext uploadByGroupContext = new FileUploadByGroupKeyContext();
        uploadByGroupContext.setDataSchemeKey(fieldFileInfo.getDataSchemeKey());
        uploadByGroupContext.setDimensionCombination(dimensionCombination);
        uploadByGroupContext.setFieldKey(fieldFileInfo.getFieldKey());
        uploadByGroupContext.setFormKey(fieldFileInfo.getFormKey());
        uploadByGroupContext.setFormSchemeKey(fieldFileInfo.getFormSchemeKey());
        uploadByGroupContext.setGroupKey(newGroupKey);
        uploadByGroupContext.setTaskKey(fieldFileInfo.getTaskKey());
        for (SingleFieldFileInfo fileInfo : fileInfos) {
            try {
                File file = new File(SinglePathUtil.normalize((String)fileInfo.getFilePath()));
                if (!file.exists()) continue;
                String fileName = file.getName();
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    Throwable throwable = null;
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        Throwable throwable2 = null;
                        try {
                            int len;
                            fileIn.skip(0L);
                            byte[] buffer = new byte[1024];
                            while ((len = fileIn.read(buffer)) > -1) {
                                baos.write(buffer, 0, len);
                            }
                            baos.flush();
                            if (fileIn == null) continue;
                            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                            Throwable throwable3 = null;
                            try {
                                FileUploadInfo upInfo = new FileUploadInfo();
                                upInfo.setName(fileName);
                                upInfo.setSize(file.length());
                                upInfo.setFile((InputStream)bais);
                                if (this.jioUploadFileKey && StringUtils.isNotEmpty((String)fileInfo.getFileKey())) {
                                    upInfo.setFileKey(fileInfo.getFileKey());
                                    upInfo.setJioImportAttachFileKey(true);
                                }
                                if (StringUtils.isNotEmpty((String)fileInfo.getFileSecret())) {
                                    upInfo.setFileSecret(fileInfo.getFileSecret());
                                }
                                if (StringUtils.isNotEmpty((String)fileInfo.getCategory())) {
                                    upInfo.setCategory(fileInfo.getCategory());
                                }
                                uploadByGroupContext.getFileUploadInfos().add(upInfo);
                            }
                            catch (Throwable throwable4) {
                                throwable3 = throwable4;
                                throw throwable4;
                            }
                            finally {
                                if (bais == null) continue;
                                if (throwable3 != null) {
                                    try {
                                        bais.close();
                                    }
                                    catch (Throwable throwable5) {
                                        throwable3.addSuppressed(throwable5);
                                    }
                                    continue;
                                }
                                bais.close();
                            }
                        }
                        catch (Throwable throwable6) {
                            throwable2 = throwable6;
                            throw throwable6;
                        }
                        finally {
                            if (baos == null) continue;
                            if (throwable2 != null) {
                                try {
                                    baos.close();
                                }
                                catch (Throwable throwable7) {
                                    throwable2.addSuppressed(throwable7);
                                }
                                continue;
                            }
                            baos.close();
                        }
                    }
                    catch (Throwable throwable8) {
                        throwable = throwable8;
                        throw throwable8;
                    }
                    finally {
                        if (fileIn == null) continue;
                        if (throwable != null) {
                            try {
                                fileIn.close();
                            }
                            catch (Throwable throwable9) {
                                throwable.addSuppressed(throwable9);
                            }
                            continue;
                        }
                        fileIn.close();
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new SingleDataException(e.getMessage(), (Throwable)e);
                }
            }
            catch (SingleFileException e1) {
                logger.error(e1.getMessage(), e1);
                throw new SingleDataException(e1.getMessage(), (Throwable)e1);
            }
        }
        FileImportResult uploadResult = this.attachIOservice.uploadByGroup(uploadByGroupContext);
        if (uploadResult != null && !uploadResult.isSuccess()) {
            result.setSuccess(false);
            result.setMessage(uploadResult.getErrorMsg());
            for (FailedFileInfo failInfo : uploadResult.getFailedFileInfoList()) {
                SingleAttachmentFailFile failFile = new SingleAttachmentFailFile(failInfo.getFileKey(), failInfo.getFileName(), failInfo.getErrorMsg());
                failFile.setErrorReason(failInfo.getErrorReason());
                result.getFailedFileList().add(failFile);
            }
        } else {
            result.setSuccess(true);
        }
        if (uploadByGroupContext.getFileUploadInfos().size() == 0) {
            newGroupKey = null;
        }
        return result;
    }

    @Override
    public byte[] downloadSingleFiles(String groupFileKey) throws SingleDataException {
        byte[] fileData = null;
        String jtableFileArea = "JTABLEAREA";
        List fileInfos = this.fileInfoService.getFileInfoByGroup(groupFileKey, jtableFileArea, FileStatus.AVAILABLE);
        if (null != fileInfos) {
            boolean hasFile = false;
            try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();){
                try (ZipOutputStream zip = new ZipOutputStream((OutputStream)outStream, StandardCharsets.UTF_8);){
                    for (com.jiuqi.nr.file.FileInfo fileInfo : fileInfos) {
                        if (!this.downLoadOneFile(fileInfo.getName(), fileInfo.getArea(), fileInfo.getKey(), zip)) continue;
                        hasFile = true;
                    }
                }
                if (hasFile) {
                    fileData = outStream.toByteArray();
                }
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new SingleDataException(e.getMessage(), (Throwable)e);
            }
        }
        return fileData;
    }

    @Override
    public byte[] downloadSingleFiles(String groupFileKey, String dataSchemeKey, String taskKey) throws SingleDataException {
        byte[] fileData = null;
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataSchemeKey);
        params.setTaskKey(taskKey);
        List appendDixInfos = this.attachIOservice.getFileByGroup(groupFileKey, params);
        if (null != appendDixInfos) {
            boolean hasFile = false;
            try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();){
                try (ZipOutputStream zip = new ZipOutputStream((OutputStream)outStream, StandardCharsets.UTF_8);){
                    for (FileInfo fileInfo : appendDixInfos) {
                        if (!this.downLoadOneFile(fileInfo.getName(), fileInfo.getArea(), fileInfo.getKey(), zip)) continue;
                        hasFile = true;
                    }
                }
                if (hasFile) {
                    fileData = outStream.toByteArray();
                }
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new SingleDataException(e.getMessage(), (Throwable)e);
            }
        }
        return fileData;
    }

    private boolean downLoadOneFile(String filename, String fileArea, String fileKey, ZipOutputStream zip) throws IOException {
        FileAreaService areaService;
        byte[] inputData;
        boolean hasFile = false;
        String filename1 = filename;
        if (StringUtils.isEmpty((String)filename1)) {
            return hasFile;
        }
        if (filename1.contains("\u2014")) {
            filename1 = filename1.replace("\u2014", "");
        }
        if (filename1.contains("\u00a0")) {
            filename1 = filename1.replace("\u00a0", "");
        }
        if ((inputData = (areaService = this.fileService.area(fileArea)).download(fileKey)) == null) {
            return hasFile;
        }
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);){
            inputStream.skip(0L);
            zip.putNextEntry(new ZipEntry(filename1));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                zip.write(buffer, 0, len);
            }
        }
        hasFile = true;
        return hasFile;
    }

    @Override
    public List<FileInfo> getFileInfoByGroup(String groupFileKey, String dataSchemeKey, String taskKey) {
        return this.getFileInfoByGroup(groupFileKey, dataSchemeKey, taskKey, null);
    }

    @Override
    public List<FileInfo> getFileInfoByGroup(String groupFileKey, String dataSchemeKey, String taskKey, String fieldKey) {
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataSchemeKey);
        params.setTaskKey(taskKey);
        if (StringUtils.isEmpty((String)fieldKey)) {
            return this.attachIOservice.getFileByGroup(groupFileKey, params);
        }
        return this.attachIOservice.getFileByGroup(groupFileKey, fieldKey, params);
    }

    @Override
    public byte[] download(String fileKey, String dataSchemeKey, String taskKey) {
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataSchemeKey);
        params.setTaskKey(taskKey);
        return this.attachIOservice.download(fileKey, params);
    }

    @Override
    public void download(String fileKey, String dataSchemeKey, String taskKey, OutputStream outputStream) {
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataSchemeKey);
        params.setTaskKey(taskKey);
        this.attachIOservice.download(fileKey, params, outputStream);
    }

    @Override
    public void deleteMarkFile(String dataSchemeKey, AsyncTaskMonitor monitor) {
    }
}


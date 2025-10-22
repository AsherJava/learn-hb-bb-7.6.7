/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext
 *  com.jiuqi.nr.attachment.input.FileUploadInfo
 *  com.jiuqi.nr.attachment.message.FileInfo
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
 */
package nr.midstore.core.internal.util.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.FileUploadByGroupKeyContext;
import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import nr.midstore.core.definition.bean.MidstoreFileInfo;
import nr.midstore.core.util.IMidstoreAttachmentService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreAttachmentServiceImpl
implements IMidstoreAttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreAttachmentServiceImpl.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private AttachmentIOService attachIOservice;

    @Override
    public String saveFileFieldDataToNR(byte[] fileData) throws Exception {
        String groupFileKey = null;
        String jtableFileArea = "JTABLEAREA";
        ArrayList<com.jiuqi.nr.file.FileInfo> fileInfos = new ArrayList<com.jiuqi.nr.file.FileInfo>();
        if (null != fileData) {
            groupFileKey = UUID.randomUUID().toString();
            FileAreaService areaService = this.fileService.area(jtableFileArea);
            boolean hasFile = false;
            try (ByteArrayInputStream inStream = new ByteArrayInputStream(fileData);
                 ZipInputStream zip = new ZipInputStream((InputStream)inStream, StandardCharsets.UTF_8);){
                ZipEntry entry;
                while ((entry = zip.getNextEntry()) != null) {
                    if (entry.isDirectory()) continue;
                    String fileName = entry.getName();
                    byte[] fileContent = null;
                    try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();){
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = zip.read(buffer)) >= 0) {
                            outStream.write(buffer, 0, len);
                        }
                        fileContent = outStream.toByteArray();
                    }
                    if (fileContent != null) {
                        com.jiuqi.nr.file.FileInfo fi = areaService.uploadByGroup(fileName, groupFileKey, fileContent);
                        String path = this.fileService.area(jtableFileArea).getPath(fi.getKey(), "__default_tenant__");
                        byte[] textByte = path.getBytes("UTF-8");
                        fi = FileInfoBuilder.newFileInfo((com.jiuqi.nr.file.FileInfo)fi, (String)fi.getFileGroupKey(), (String)Base64.encodeBase64String(textByte));
                        fileInfos.add(fi);
                    }
                    hasFile = true;
                }
                if (fileInfos.size() == 0) {
                    groupFileKey = null;
                }
            }
        }
        return groupFileKey;
    }

    @Override
    public byte[] getFieldDataFromNR(String groupFileKey) throws Exception {
        byte[] fileData = null;
        String jtableFileArea = "JTABLEAREA";
        List fileInfos = this.fileInfoService.getFileInfoByGroup(groupFileKey, jtableFileArea, FileStatus.AVAILABLE);
        if (null != fileInfos) {
            boolean hasFile = false;
            try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                 ZipOutputStream zip = new ZipOutputStream((OutputStream)outStream, StandardCharsets.UTF_8);){
                for (com.jiuqi.nr.file.FileInfo fileInfo : fileInfos) {
                    FileAreaService areaService;
                    byte[] inputData;
                    String filename1 = fileInfo.getName();
                    if (StringUtils.isEmpty((String)filename1)) continue;
                    if (filename1.contains("\u2014")) {
                        filename1 = filename1.replace("\u2014", "");
                    }
                    if (filename1.contains("\u00a0")) {
                        filename1 = filename1.replace("\u00a0", "");
                    }
                    if ((inputData = (areaService = this.fileService.area(fileInfo.getArea())).download(fileInfo.getKey())) == null) continue;
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);
                    inputStream.skip(0L);
                    zip.putNextEntry(new ZipEntry(filename1));
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) > 0) {
                        zip.write(buffer, 0, len);
                    }
                    hasFile = true;
                }
                if (hasFile) {
                    fileData = outStream.toByteArray();
                }
            }
        }
        return fileData;
    }

    @Override
    public String saveFileFieldDataToNR(byte[] fileData, MidstoreFileInfo fieldFileInfo) throws Exception {
        String groupFileKey = null;
        String jtableFileArea = "JTABLEAREA";
        if (null != fileData && null != fieldFileInfo) {
            groupFileKey = UUID.randomUUID().toString();
            FileUploadByGroupKeyContext uploadByGroupContext = new FileUploadByGroupKeyContext();
            uploadByGroupContext.setDataSchemeKey(fieldFileInfo.getDataSchemeKey());
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
            Map<String, DimensionValue> dimensionSet = fieldFileInfo.getDimensionSet();
            for (String key : dimensionSet.keySet()) {
                dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
            }
            DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
            uploadByGroupContext.setDimensionCombination(dimensionCombination);
            uploadByGroupContext.setFieldKey(fieldFileInfo.getFieldKey());
            uploadByGroupContext.setFormKey(fieldFileInfo.getFormKey());
            uploadByGroupContext.setFormSchemeKey(fieldFileInfo.getFormSchemeKey());
            uploadByGroupContext.setGroupKey(groupFileKey);
            uploadByGroupContext.setTaskKey(fieldFileInfo.getTaskKey());
            boolean hasFile = false;
            try (ByteArrayInputStream inStream = new ByteArrayInputStream(fileData);
                 ZipInputStream zip = new ZipInputStream((InputStream)inStream, StandardCharsets.UTF_8);){
                ZipEntry entry;
                while ((entry = zip.getNextEntry()) != null) {
                    if (entry.isDirectory()) continue;
                    String fileName = entry.getName();
                    byte[] fileContent = null;
                    try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();){
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = zip.read(buffer)) >= 0) {
                            outStream.write(buffer, 0, len);
                        }
                        fileContent = outStream.toByteArray();
                    }
                    if (fileContent != null) {
                        var18_23 = null;
                        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);){
                            FileUploadInfo upInfo = new FileUploadInfo();
                            upInfo.setName(fileName);
                            upInfo.setSize((long)fileContent.length);
                            upInfo.setFile((InputStream)inputStream);
                            uploadByGroupContext.getFileUploadInfos().add(upInfo);
                        }
                        catch (Throwable throwable) {
                            var18_23 = throwable;
                            throw throwable;
                        }
                    }
                    hasFile = true;
                }
                if (uploadByGroupContext.getFileUploadInfos().size() == 0) {
                    groupFileKey = null;
                }
            }
            this.attachIOservice.uploadByGroup(uploadByGroupContext);
        }
        return groupFileKey;
    }

    @Override
    public byte[] getFieldDataFromNR(String groupFileKey, String dataSchemeKey, String taskKey) throws Exception {
        byte[] fileData = null;
        CommonParamsDTO params = new CommonParamsDTO();
        params.setDataSchemeKey(dataSchemeKey);
        params.setTaskKey(taskKey);
        List appendDixInfos = this.attachIOservice.getFileByGroup(groupFileKey, params);
        if (null != appendDixInfos) {
            boolean hasFile = false;
            try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                 ZipOutputStream zip = new ZipOutputStream((OutputStream)outStream, StandardCharsets.UTF_8);){
                for (FileInfo fileInfo : appendDixInfos) {
                    byte[] inputData;
                    String filename1 = fileInfo.getName();
                    if (StringUtils.isEmpty((String)filename1)) continue;
                    if (filename1.contains("\u2014")) {
                        filename1 = filename1.replace("\u2014", "");
                    }
                    if (filename1.contains("\u00a0")) {
                        filename1 = filename1.replace("\u00a0", "");
                    }
                    if ((inputData = this.attachIOservice.download(fileInfo.getKey(), params)) == null) continue;
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
                }
                zip.close();
                if (hasFile) {
                    fileData = outStream.toByteArray();
                }
            }
        }
        return fileData;
    }
}


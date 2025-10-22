/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import java.util.List;
import nr.single.para.parain.internal.cache.AttachmentFileConfig;
import nr.single.para.parain.service.IAttachmentFileImportService;
import org.springframework.beans.factory.annotation.Autowired;

public class AttachmentFileImportServiceImpl
implements IAttachmentFileImportService {
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;

    @Override
    public String uploadFile(String fileName, String fielkGroup, byte[] bytes, String partition) {
        AttachmentFileConfig fileConfig = new AttachmentFileConfig();
        fileConfig.setAreaName(partition);
        FileAreaService areaService = this.fileService.area((FileAreaConfig)fileConfig);
        FileInfo fileInfo = areaService.uploadByGroup(fileName, fielkGroup, bytes);
        String fileGroupKey = fileInfo.getFileGroupKey() + "|" + partition;
        return fileGroupKey;
    }

    @Override
    public List<FileInfo> getFileInGroup(String fileGroup) {
        String[] split = fileGroup.split("\\|");
        return this.getFileInGroup(split[0], split[1]);
    }

    @Override
    public List<FileInfo> getFileInGroup(String fileGroup, String partition) {
        List fileInfoByGroup = this.fileInfoService.getFileInfoByGroup(fileGroup, partition, FileStatus.AVAILABLE);
        return fileInfoByGroup;
    }

    @Override
    public FileInfo deleteFile(FileInfo fileInfo, Boolean record) {
        FileInfo fileInfo1 = this.fileInfoService.deleteFile(fileInfo, record);
        return fileInfo1;
    }

    @Override
    public void deleteFiles(String fileGroup) {
        List<FileInfo> files = this.getFileInGroup(fileGroup);
        for (FileInfo info : files) {
            this.deleteFile(info, false);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 */
package com.jiuqi.nr.attachment.transfer.service.impl;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.nr.attachment.transfer.common.Utils;
import com.jiuqi.nr.attachment.transfer.dao.IWorkSpaceDao;
import com.jiuqi.nr.attachment.transfer.domain.WorkSpaceDO;
import com.jiuqi.nr.attachment.transfer.dto.FileInfoDTO;
import com.jiuqi.nr.attachment.transfer.dto.WorkSpaceDTO;
import com.jiuqi.nr.attachment.transfer.service.IWorkSpaceService;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WorkSpaceServiceImpl
implements IWorkSpaceService {
    private static final Logger log = LoggerFactory.getLogger(WorkSpaceServiceImpl.class);
    @Autowired
    private IWorkSpaceDao workSpaceDao;

    @Override
    public void save(WorkSpaceDTO workSpaceDTO) {
        if (workSpaceDTO.getType() == 1) {
            this.checkFilePath(workSpaceDTO.getFilePath());
        }
        WorkSpaceDO workSpaceDO = WorkSpaceDO.getInstance(workSpaceDTO);
        workSpaceDO.setServerId(DistributionManager.getInstance().self().getName());
        this.workSpaceDao.insert(workSpaceDO);
    }

    private void checkFilePath(String filePath) {
        File file = new File(FilenameUtils.normalize(filePath));
        if (!file.exists() || file.isFile()) {
            throw new RuntimeException("\u9519\u8bef\u7684\u5de5\u4f5c\u7a7a\u95f4");
        }
        File realFile = new File(FilenameUtils.normalize(filePath + File.separator + "JIOEXPORT"));
        if (!realFile.exists()) {
            realFile.mkdir();
        }
    }

    @Override
    public void update(WorkSpaceDTO workSpaceDTO) {
        int update;
        if (workSpaceDTO.getType() == 1) {
            this.checkFilePath(workSpaceDTO.getFilePath());
        }
        if ((update = this.workSpaceDao.update(workSpaceDTO)) == 0) {
            throw new RuntimeException("\u66f4\u65b0\u914d\u7f6e\u4fe1\u606f\u5931\u8d25");
        }
    }

    @Override
    public WorkSpaceDTO getConfig(int type) {
        WorkSpaceDO workSpaceDO = this.workSpaceDao.get(type);
        if (workSpaceDO == null) {
            return null;
        }
        return WorkSpaceDTO.getInstance(workSpaceDO);
    }

    @Override
    public List<FileInfoDTO> listGenerateFile() {
        File[] files;
        ArrayList<FileInfoDTO> fileInfos = new ArrayList<FileInfoDTO>();
        WorkSpaceDO workSpaceDO = this.workSpaceDao.get(1);
        if (workSpaceDO == null) {
            throw new RuntimeException("\u8bf7\u5148\u4fdd\u5b58\u76ee\u5f55\u914d\u7f6e");
        }
        String filePath = workSpaceDO.getFilePath();
        if (!StringUtils.hasText(filePath)) {
            return Collections.emptyList();
        }
        File dir = new File(FilenameUtils.normalize(filePath + File.separator + "JIOEXPORT"));
        if (!dir.exists()) {
            return Collections.emptyList();
        }
        for (File file : files = dir.listFiles()) {
            FileInfoDTO fileInfo = new FileInfoDTO();
            fileInfo.setFileName(file.getName());
            fileInfo.setFile(file.isFile());
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }

    @Override
    public int cleanFile(int type) {
        WorkSpaceDO workSpaceDO;
        int count = 0;
        if (type == 0) {
            type = 1;
        }
        if ((workSpaceDO = this.workSpaceDao.get(type)) == null) {
            throw new RuntimeException("\u8bf7\u5148\u4fdd\u5b58\u76ee\u5f55\u914d\u7f6e");
        }
        String filePath = workSpaceDO.getFilePath();
        if (!StringUtils.hasText(filePath)) {
            return 0;
        }
        File dir = new File(FilenameUtils.normalize(filePath + File.separator + "JIOEXPORT"));
        if (!dir.exists()) {
            return count;
        }
        Utils.deleteFile(dir);
        return 1;
    }
}


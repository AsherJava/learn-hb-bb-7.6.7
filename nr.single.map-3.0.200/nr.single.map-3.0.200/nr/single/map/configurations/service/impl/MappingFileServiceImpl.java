/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 */
package nr.single.map.configurations.service.impl;

import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.bean.SingleFileInfo;
import nr.single.map.configurations.dao.ConfigDao;
import nr.single.map.configurations.dao.FileInfoDao;
import nr.single.map.configurations.service.MappingFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MappingFileServiceImpl
implements MappingFileService {
    private static final Logger logger = LoggerFactory.getLogger(MappingFileServiceImpl.class);
    @Autowired
    private FileInfoDao fileInfoDao;
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private FileService fileService;

    @Override
    public SingleFileInfo queryFileInfo(String mappingKey) {
        return this.fileInfoDao.query(mappingKey);
    }

    @Override
    public List<SingleFileInfo> queryFileInfoInScheme(String schemeKey) {
        ArrayList<SingleFileInfo> infos = new ArrayList<SingleFileInfo>();
        List<SingleConfigInfo> singleConfigInfos = this.configDao.queryConfigByScheme(schemeKey);
        for (SingleConfigInfo configInfo : singleConfigInfos) {
            SingleFileInfo query = this.fileInfoDao.query(configInfo.getConfigKey());
            if (query == null) continue;
            infos.add(query);
        }
        return infos;
    }

    @Override
    public byte[] queryFile(String fileKey) {
        FileAreaService singleFile = this.fileService.area("single");
        return singleFile.download(fileKey);
    }

    @Override
    public String insertFile(InputStream is) {
        FileAreaService singleFile = this.fileService.area("single");
        FileInfo fileInfo = null;
        try {
            fileInfo = singleFile.upload(is);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (fileInfo != null) {
            return fileInfo.getKey();
        }
        return null;
    }

    @Override
    public void insertMappingFileInfo(List<SingleFileInfo> infos) {
        for (SingleFileInfo info : infos) {
            this.fileInfoDao.insert(info);
        }
    }
}


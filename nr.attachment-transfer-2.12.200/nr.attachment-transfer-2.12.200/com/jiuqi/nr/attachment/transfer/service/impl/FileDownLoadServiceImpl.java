/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.util.OrderGenerator
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.attachment.transfer.service.impl;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.attachment.transfer.common.Constant;
import com.jiuqi.nr.attachment.transfer.dao.IGenerateRecordDao;
import com.jiuqi.nr.attachment.transfer.domain.AttachmentRecordDO;
import com.jiuqi.nr.attachment.transfer.dto.WorkSpaceDTO;
import com.jiuqi.nr.attachment.transfer.exception.FileNotFoundException;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import com.jiuqi.nr.attachment.transfer.service.IFileDownLoadService;
import com.jiuqi.nr.attachment.transfer.service.IWorkSpaceService;
import com.jiuqi.nr.attachment.transfer.vo.DownLoadInfo;
import com.jiuqi.util.OrderGenerator;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileDownLoadServiceImpl
implements IFileDownLoadService {
    @Autowired
    private IGenerateRecordDao generateRecordDao;
    @Autowired
    private IWorkSpaceService workSpaceService;
    private NedisCache cache;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager("ATTACHMENT_TRANSFER_CACHE").getCache("FILE_TOKEN");
    }

    @Override
    public void downLoadOne(String key, String range, HttpServletResponse res) {
        AttachmentRecordDO recordDO = this.generateRecordDao.get(key);
        if (recordDO.isHistory()) {
            throw new FileNotFoundException("\u6587\u4ef6\u5df2\u88ab\u6e05\u7406\uff0c\u65e0\u6cd5\u4e0b\u8f7d");
        }
        WorkSpaceDTO workSpaceDTO = this.workSpaceService.getConfig(1);
        File file = new File(FilenameUtils.normalize(workSpaceDTO.getFilePath() + File.separator + "JIOEXPORT" + File.separator + recordDO.getFileName()));
        if (file.exists() && file.isFile()) {
            if (range == null) {
                this.generateRecordDao.updateDownloadInfo(key, recordDO.getDownloadNum() + 1);
            }
        } else {
            throw new FileNotFoundException("\u6587\u4ef6\u5df2\u88ab\u5220\u9664\uff0c\u65e0\u6cd5\u4e0b\u8f7d");
        }
        LogHelper.info((String)"\u9644\u4ef6\u5bfc\u5165\u5bfc\u51fa", (String)"\u4e0b\u8f7d\u9644\u4ef6", (String)String.format("\u901a\u8fc7\u754c\u9762\u4e0b\u8f7d\u9644\u4ef6\uff0c \u9644\u4ef6\u540d\u79f0\uff1a%s\uff0c\u4e0b\u8f7d\u6b21\u6570\uff1a%s", recordDO.getFileName(), recordDO.getDownloadNum() + 1));
        this.exportFile(file, range, res);
    }

    @Override
    public void downLoadOne(String key, String token, String range, HttpServletResponse res) {
        if (!this.cache.exists(token)) {
            throw new RuntimeException("\u975e\u6cd5\u7684\u4e0b\u8f7d\u94fe\u63a5\uff0c\u6216\u4ee4\u724c\u5df2\u8fc7\u671f\uff0c\u8bf7\u91cd\u65b0\u751f\u6210");
        }
        this.downLoadOne(key, range, res);
    }

    @Override
    public List<DownLoadInfo> batchDownLoad(int number) {
        ArrayList<DownLoadInfo> infos = new ArrayList<DownLoadInfo>();
        List<AttachmentRecordDO> records = this.generateRecordDao.list();
        for (AttachmentRecordDO recordDO : records) {
            if (recordDO.getStatus() != Constant.GenerateStatus.SUCCESS.getStatus() || recordDO.isHistory()) continue;
            boolean shouldAdd = false;
            if (number == -1) {
                shouldAdd = true;
            } else if (number == 0 && recordDO.getDownloadNum() == 0) {
                shouldAdd = true;
            } else if (number > 0 && recordDO.getDownloadNum() > 0) {
                shouldAdd = true;
            }
            if (!shouldAdd) continue;
            DownLoadInfo info = new DownLoadInfo();
            info.setKey(recordDO.getKey());
            info.setTitle(recordDO.getFileName());
            info.setDownNumber(recordDO.getDownloadNum());
            infos.add(info);
        }
        return infos;
    }

    @Override
    public String downLoadInfo(List<String> keys) {
        WorkSpaceDTO workSpaceDTO = this.workSpaceService.getConfig(1);
        List<DownLoadInfo> infos = this.batchDownLoad(-1);
        StringBuilder sbs = new StringBuilder();
        String token = OrderGenerator.newOrder();
        this.cache.put(token, (Object)Boolean.TRUE);
        for (DownLoadInfo info : infos) {
            if (!keys.contains(info.getKey())) continue;
            sbs.append(workSpaceDTO.getUrl()).append("/api/v1/attachment-transfer/download/anon/download/").append(info.getKey()).append("/").append(token).append("/").append(info.getTitle()).append("\r\n");
        }
        return sbs.toString();
    }

    public void exportFile(File file, String range, HttpServletResponse response) {
        if (!file.exists()) {
            response.setStatus(404);
            return;
        }
        long fileLength = file.length();
        long start = 0L;
        long end = fileLength - 1L;
        if (range != null) {
            String[] ranges = range.split("=")[1].split("-");
            start = Long.parseLong(ranges[0]);
            if (ranges.length > 1) {
                end = Long.parseLong(ranges[1]);
            }
        }
        long contentLength = end - start + 1L;
        response.setContentType("application/octet-stream");
        String fileName = "defaultName.jio";
        try {
            fileName = URLEncoder.encode(file.getName(), "UTF-8").replace("\\+", "%20");
        }
        catch (UnsupportedEncodingException e) {
            AttachmentLogHelper.error(e.getMessage(), e);
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + HtmlUtils.cleanHeaderValue((String)fileName));
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(contentLength));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        if (range != null) {
            response.setStatus(206);
            response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);
        }
        try (BufferedInputStream ins = new BufferedInputStream(Files.newInputStream(file.toPath(), new OpenOption[0]));
             BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
            int bytesRead;
            byte[] buffer = new byte[8192];
            ((InputStream)ins).skip(start);
            for (long bytesToRead = contentLength; bytesToRead > 0L && (bytesRead = ((InputStream)ins).read(buffer, 0, (int)Math.min((long)buffer.length, bytesToRead))) != -1; bytesToRead -= (long)bytesRead) {
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
        catch (IOException e) {
            AttachmentLogHelper.info("\u6587\u4ef6\u4e0b\u8f7d\u4e2d\u65ad");
        }
    }
}


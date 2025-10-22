/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.fileupload.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.nr.fileupload.FileChunkCacheInfo;
import com.jiuqi.nr.fileupload.FileChunkParamInfo;
import com.jiuqi.nr.fileupload.FileChunkReturnInfo;
import com.jiuqi.nr.fileupload.FileMergeReturnInfo;
import com.jiuqi.nr.fileupload.exception.FileUploadException;
import com.jiuqi.nr.fileupload.service.FileChunkUploadService;
import com.jiuqi.nr.fileupload.service.impl.FileChunkUploadCache;
import com.jiuqi.nr.fileupload.util.FileUploadUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileChunkUploadServiceImpl
implements FileChunkUploadService {
    private static final Logger logger = LoggerFactory.getLogger(FileChunkUploadServiceImpl.class);
    @Autowired
    private FileChunkUploadCache fileChunkUploadCache;

    @Override
    public FileChunkReturnInfo uploadChunk(MultipartFile file, FileChunkParamInfo fileChunkParam) {
        String fileKey = fileChunkParam.getFileKey();
        int currentIndex = fileChunkParam.getCurrentIndex();
        int shardTotal = fileChunkParam.getShardTotal();
        FileChunkCacheInfo chunkCache = this.fileChunkUploadCache.getChunkCache(fileKey);
        Set<Integer> chunks = this.getChunkSet(fileKey);
        FileChunkReturnInfo fileChunkReturnInfo = new FileChunkReturnInfo();
        if (null == chunkCache) {
            ObjectInfo fileInfo = FileUploadUtils.getFileInfo(fileKey);
            if (null != fileInfo) {
                fileChunkReturnInfo.setCompleted(true);
                return fileChunkReturnInfo;
            }
            chunkCache = new FileChunkCacheInfo();
            chunkCache.setFileName(fileChunkParam.getFileName());
            chunkCache.setShardTotal(shardTotal);
            chunkCache.setFileKey(fileKey);
            this.fileChunkUploadCache.saveChunkCache(fileKey, chunkCache);
        }
        String chunkKey = fileKey + "-" + currentIndex;
        ObjectInfo info = FileUploadUtils.getFileInfo(chunkKey);
        if (!chunks.contains(currentIndex) && info == null) {
            try (InputStream inputStream = file.getInputStream();){
                FileUploadUtils.fileUploadByKey(String.valueOf(currentIndex), chunkKey, fileKey, inputStream);
                chunks = this.getChunkSet(fileKey);
                Integer[] array = chunks.toArray(new Integer[chunks.size()]);
                fileChunkReturnInfo.setCanMerge(chunks.size() == chunkCache.getShardTotal());
                fileChunkReturnInfo.setUploadChunks(array);
            }
            catch (Exception e) {
                if (null == FileUploadUtils.getFileInfo(fileKey)) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    throw new FileUploadException(e.getMessage());
                }
                chunkCache = this.fileChunkUploadCache.getChunkCache(fileKey);
                chunks = this.getChunkSet(fileKey);
                fileChunkReturnInfo.setCanMerge(chunks.size() == chunkCache.getShardTotal());
                Integer[] array = chunks.toArray(new Integer[chunks.size()]);
                fileChunkReturnInfo.setUploadChunks(array);
            }
        } else {
            fileChunkReturnInfo.setCanMerge(chunks.size() == chunkCache.getShardTotal());
            Integer[] array = chunks.toArray(new Integer[chunks.size()]);
            fileChunkReturnInfo.setUploadChunks(array);
        }
        return fileChunkReturnInfo;
    }

    @Override
    public FileChunkReturnInfo checkChunkKey(FileChunkParamInfo fileChunkParam) {
        String fileKey = fileChunkParam.getFileKey();
        FileChunkReturnInfo fileChunkReturnInfo = new FileChunkReturnInfo();
        ObjectInfo fileInfo = FileUploadUtils.getFileInfo(fileKey);
        if (null != fileInfo) {
            fileChunkReturnInfo.setCompleted(true);
            return fileChunkReturnInfo;
        }
        FileChunkCacheInfo chunkCache = this.fileChunkUploadCache.getChunkCache(fileKey);
        if (chunkCache != null) {
            Set<Integer> chunkSet = this.getChunkSet(fileKey);
            fileChunkReturnInfo.setUploadChunks(chunkSet.toArray(new Integer[chunkSet.size()]));
            fileChunkReturnInfo.setCanMerge(chunkSet.size() == chunkCache.getShardTotal());
        } else {
            chunkCache = new FileChunkCacheInfo();
            List<ObjectInfo> fileInfoByGroup = FileUploadUtils.getFileInfosByGroup(fileKey);
            if (!fileInfoByGroup.isEmpty()) {
                Set<Integer> chunkSet = fileInfoByGroup.stream().map(i -> Integer.parseInt(i.getName())).collect(Collectors.toSet());
                fileChunkReturnInfo.setUploadChunks(chunkSet.toArray(new Integer[chunkSet.size()]));
                chunkCache.setFileName(fileChunkParam.getFileName());
                chunkCache.setShardTotal(fileChunkParam.getShardTotal());
                fileChunkReturnInfo.setCanMerge(chunkSet.size() == chunkCache.getShardTotal());
            }
        }
        return fileChunkReturnInfo;
    }

    private Set<Integer> getChunkSet(String fileKey) {
        Set<Integer> chunks = new HashSet<Integer>();
        List<ObjectInfo> fileInfoByGroup = FileUploadUtils.getFileInfosByGroup(fileKey);
        if (!fileInfoByGroup.isEmpty()) {
            chunks = fileInfoByGroup.stream().map(i -> Integer.parseInt(i.getName())).collect(Collectors.toSet());
        }
        return chunks;
    }

    private void mergeChunk(FileChunkParamInfo fileChunkParamInfo) {
        String fileKey = fileChunkParamInfo.getFileKey();
        FileChunkCacheInfo chunkCache = this.fileChunkUploadCache.getChunkCache(fileKey);
        String fileName = chunkCache.getFileName();
        try {
            Throwable throwable;
            PathUtils.validatePathManipulation((String)fileName);
            List<ObjectInfo> fileInfoByGroup = FileUploadUtils.getFileInfosByGroup(fileKey);
            fileInfoByGroup.sort(Comparator.comparing(i -> Integer.parseInt(i.getName())));
            String filePath = FileUploadUtils.getUploadFileLocation(fileKey);
            PathUtils.validatePathManipulation((String)filePath);
            try {
                throwable = null;
                try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);){
                    double progress = 0.0;
                    for (ObjectInfo fileInfo : fileInfoByGroup) {
                        FileUploadUtils.fileDownLoad(fileInfo.getKey(), fileOutputStream);
                        FileUploadUtils.fileDelete(fileInfo.getKey());
                        chunkCache.setMergeProgress(progress += 0.9 / (double)fileInfoByGroup.size());
                        this.fileChunkUploadCache.saveChunkCache(fileKey, chunkCache);
                    }
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
            }
            catch (IOException e) {
                logger.error("\u5408\u5e76\u5206\u7247\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return;
            }
            try {
                throwable = null;
                try (FileInputStream fileInputStream = new FileInputStream(filePath);){
                    FileUploadUtils.fileUploadByKey(fileName, fileKey, null, fileInputStream);
                    FileUploadUtils.fileDeleteByGroup(fileKey);
                    this.fileChunkUploadCache.clearChunkCache(fileKey);
                }
                catch (Throwable throwable3) {
                    throwable = throwable3;
                    throw throwable3;
                }
            }
            catch (Exception e) {
                logger.error("\u6587\u4ef6\u4e0a\u4f20oss\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
        catch (SecurityContentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public FileMergeReturnInfo createMergeTask(final FileChunkParamInfo fileChunkParamInfo) {
        FileMergeReturnInfo fileMergeReturnInfo = new FileMergeReturnInfo();
        boolean b = FileUploadUtils.fileExist(fileChunkParamInfo.getFileKey());
        if (b) {
            fileMergeReturnInfo.setProgress(1.0);
            fileMergeReturnInfo.setCompleted(true);
        } else {
            boolean lock = this.fileChunkUploadCache.lock(fileChunkParamInfo.getFileKey());
            if (lock) {
                FileChunkCacheInfo chunkCache = this.fileChunkUploadCache.getChunkCache(fileChunkParamInfo.getFileKey());
                if (chunkCache.getMergeProgress() == -1.0) {
                    chunkCache.setMergeProgress(0.0);
                    this.fileChunkUploadCache.saveChunkCache(fileChunkParamInfo.getFileKey(), chunkCache);
                    new Thread(){

                        @Override
                        public void run() {
                            FileChunkUploadServiceImpl.this.mergeChunk(fileChunkParamInfo);
                        }
                    }.start();
                }
                fileMergeReturnInfo.setProgress(chunkCache.getMergeProgress());
                this.fileChunkUploadCache.unLock(fileChunkParamInfo.getFileKey());
            } else {
                try {
                    Thread.sleep(50L);
                }
                catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
                return this.createMergeTask(fileChunkParamInfo);
            }
        }
        return fileMergeReturnInfo;
    }

    @Override
    public FileMergeReturnInfo queryMergeTask(String fileKey) {
        FileMergeReturnInfo fileMergeReturnInfo = new FileMergeReturnInfo();
        boolean b = FileUploadUtils.fileExist(fileKey);
        if (b) {
            fileMergeReturnInfo.setProgress(1.0);
            fileMergeReturnInfo.setCompleted(true);
        } else {
            FileChunkCacheInfo chunkCache = this.fileChunkUploadCache.getChunkCache(fileKey);
            if (null != chunkCache) {
                fileMergeReturnInfo.setProgress(chunkCache.getMergeProgress());
                fileMergeReturnInfo.setCompleted(false);
            } else {
                this.queryMergeTask(fileKey);
            }
        }
        return fileMergeReturnInfo;
    }
}


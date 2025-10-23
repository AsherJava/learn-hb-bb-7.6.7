/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContextHolder
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.file.web;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.IFileInfoDao;
import com.jiuqi.nr.file.exception.FileNotFoundException;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.file.utils.FileUtils;
import com.jiuqi.nr.file.web.FileType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/file"})
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private static final String SEPARATOR = "/";
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private Map<String, IFileInfoDao> fileInfoDao;

    @PostMapping(value={"/upload"})
    public List<FileInfo> upload(@RequestParam(value="file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        String area = request.getParameter("area");
        String groupKey = request.getParameter("groupKey");
        String directory = request.getParameter("directory");
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            String fileName = file.getOriginalFilename();
            FileInfo fi = this.fileService.area(area).uploadByGroup(fileName, groupKey, file.getBytes(), directory);
            String path = this.fileService.area(area).getPath(fi.getKey(), NpContextHolder.getContext().getTenant());
            byte[] textByte = path.getBytes("UTF-8");
            FileInfoImpl ff = (FileInfoImpl)FileInfoBuilder.newFileInfo(fi, fi.getFileGroupKey(), Base64.getEncoder().encodeToString(textByte));
            ff.setSecretlevel(fi.getSecretlevel());
            fileInfos.add(ff);
        }
        return fileInfos;
    }

    @GetMapping(value={"/download/{path}/{area}"})
    public void download(@PathVariable(value="area", required=false) String area, @PathVariable(value="path") String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String decodePath = new String(Base64.getDecoder().decode(path), "UTF-8");
        String[] uris = decodePath.split(SEPARATOR);
        String tenantId = uris[0];
        String date = uris[uris.length - 1];
        String fileKey = decodePath.replace(tenantId + SEPARATOR + uris[1] + SEPARATOR, "").replace(SEPARATOR + date, "");
        if (this.compareDate(date)) {
            return;
        }
        if (!this.checkCache(fileKey, area, request, response)) {
            this.npApplication.runAsTenant(tenantId, () -> {
                try {
                    this.download(request, response, area, fileKey);
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });
        }
    }

    @GetMapping(value={"/downloadImg/{id}/{area}"})
    public void downloadImg(@PathVariable(value="area") String area, @PathVariable(value="id") String id, @RequestHeader HttpHeaders headers, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String date;
        FileInfo upFile = this.fileService.area(area).getInfo(id);
        String path = this.fileService.area(area).getPath(upFile.getKey(), NpContextHolder.getContext().getTenant());
        String[] uris = path.split(SEPARATOR);
        String tenantId = uris[0];
        if (StringUtils.isEmpty((String)tenantId) || "null".equals(tenantId)) {
            tenantId = "__default_tenant__";
        }
        if (this.compareDate(date = uris[uris.length - 1])) {
            return;
        }
        if (!this.checkCache(id, area, request, response)) {
            this.npApplication.runAsTenant(tenantId, () -> {
                try {
                    this.download(request, response, area, id);
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });
        }
    }

    private void download(HttpServletRequest request, HttpServletResponse response, String area, String fileKey) throws IOException {
        FileAreaService fileAreaService = this.fileService.area(area);
        if (fileAreaService.getAreaConfig().isEnableFastDownload()) {
            String extension = FileInfoBuilder.tryParseExtension(fileKey);
            FileType fileType = FileType.valueOfExtension(extension);
            response.setContentType(fileType.getContentType());
        } else {
            FileInfo fileInfo = fileAreaService.getInfo(fileKey);
            if (fileInfo == null) {
                throw new FileNotFoundException(fileKey);
            }
            FileType fileType = FileType.valueOfExtension(fileInfo.getExtension());
            response.setContentType(fileType.getContentType());
            String fileName = fileInfo.getName();
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        }
        fileAreaService.download(fileKey, (OutputStream)response.getOutputStream());
        response.flushBuffer();
    }

    @GetMapping(value={"/query/{area}/{groupKey}/{status}"})
    public List<FileInfo> getFileInfos(@PathVariable(value="groupKey") String groupKey, @PathVariable(value="area") String area, @PathVariable(value="status") String status) {
        List<FileInfo> files = this.fileInfoService.getFileInfoByGroup(groupKey, area, FileStatus.valueOf(status));
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        try {
            if (files == null || files.size() == 0) {
                return fileInfos;
            }
            for (FileInfo fi : files) {
                String path = this.fileService.area(area).getPath(fi.getKey(), NpContextHolder.getContext().getTenant());
                byte[] textByte = path.getBytes("UTF-8");
                FileInfoImpl ff = (FileInfoImpl)FileInfoBuilder.newFileInfo(fi, fi.getFileGroupKey(), Base64.getEncoder().encodeToString(textByte));
                ff.setSecretlevel(fi.getSecretlevel());
                fileInfos.add(ff);
            }
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return fileInfos;
    }

    @GetMapping(value={"/remove/{area}/{fileKey}"})
    public FileInfo removeFile(@PathVariable(value="area") String area, @PathVariable(value="fileKey") String fileKey) {
        FileInfo file = this.fileService.area(area).delete(fileKey);
        return file;
    }

    private boolean compareDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date pathDate = df.parse(date);
            return pathDate.before(new Date());
        }
        catch (ParseException e1) {
            logger.error(e1.getMessage(), e1);
            return false;
        }
    }

    private boolean checkCache(String fileKey, String area, HttpServletRequest request, HttpServletResponse response) {
        if (null != fileKey && !"".equals(fileKey)) {
            String ifModifiedSince = request.getHeader("If-None-Match");
            String dbVersion = this.fileService.area(area).getInfo(fileKey).getVersion() + "";
            response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Etag", "W/" + dbVersion);
            if (null != ifModifiedSince && dbVersion.equals(ifModifiedSince.replace("W/", ""))) {
                response.setStatus(HttpStatus.NOT_MODIFIED.value());
                return true;
            }
        }
        return false;
    }

    @GetMapping(value={"/copy"})
    public String copy() throws Exception {
        IFileInfoDao dao = this.fileInfoDao.get("FileInfoMulDataVerDao");
        IFileInfoDao dao2 = this.fileInfoDao.get("fileInfoDao");
        List<String> areas = dao2.getAreas();
        String ver = "DataVer";
        for (String area : areas) {
            List<FileInfo> fileInfos = dao2.getFileInfos(area);
            for (FileInfo fileInfo : fileInfos) {
                ObjectInfo info = FileUtils.objService(area).getObjectInfo(fileInfo.getKey());
                if (info == null) {
                    InputStream inputStream = FileUtils.objService(area).download(fileInfo.getKey());
                    try {
                        FileUtils.objService(area).deleteObject(fileInfo.getKey());
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    this.fileService.area(area).uploadByGroup(fileInfo.getName(), fileInfo.getFileGroupKey(), inputStream);
                }
                dao2.delete(fileInfo.getKey());
            }
        }
        List<FileInfo> fileInfos = dao.getFileInfos(ver);
        for (FileInfo fileInfo : fileInfos) {
            ObjectInfo info = FileUtils.objService(ver).getObjectInfo(fileInfo.getKey());
            if (info == null) {
                InputStream inputStream = FileUtils.objService(ver).download(fileInfo.getKey());
                try {
                    FileUtils.objService(ver).deleteObject(fileInfo.getKey());
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                this.fileService.area(ver).uploadByGroup(fileInfo.getName(), fileInfo.getFileGroupKey(), inputStream);
            }
            dao.delete(fileInfo.getKey());
        }
        return "success";
    }
}


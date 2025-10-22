/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.file.FileAreaConfig
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nvwa.framework.nros.log.annotation.NVWAPLoggable
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.http.HttpHeaders
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.portal.web;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.portal.news.FileServiceAddressProperties;
import com.jiuqi.nr.portal.news.NewsImgFile;
import com.jiuqi.nr.portal.news.bean.AttachImgObj;
import com.jiuqi.nr.portal.news.bean.DeleteObj;
import com.jiuqi.nr.portal.news.bean.UploaderInfoObject;
import com.jiuqi.nr.portal.news.bean.UploaderReturnObject;
import com.jiuqi.nvwa.framework.nros.log.annotation.NVWAPLoggable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/uploader"}, produces={"application/json;charset=UTF-8"})
public class PortalImgLoadController {
    private static final Logger logger = LoggerFactory.getLogger(PortalImgLoadController.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private FileServiceAddressProperties addressProperties;
    private static final String[] acceptFileTypes = new String[]{"rar", "zip", "jio", "tiff", "psd", "png", "swf", "svg", "gif", "jpg", "bmp", "jpeg", "xls", "xlsx", "csv", "pdf", "txt", "ppt", "md", "rp", "doc", "docx", "pptx"};

    @NVWAPLoggable(value="\u9996\u9875\u4e0a\u4f20\u6587\u4ef6")
    @RequestMapping(value={"/upload"}, method={RequestMethod.POST})
    public UploaderReturnObject uploadImg(@RequestParam(value="file") MultipartFile file, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        UploaderReturnObject object = new UploaderReturnObject();
        try {
            String uname = request.getParameter("Guid");
            String uid = request.getParameter("id");
            String realKey = uname + uid.substring(uid.lastIndexOf("_"));
            String originalFilename = file.getOriginalFilename();
            String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if (!PortalImgLoadController.isContains(Arrays.asList(acceptFileTypes), fileType)) {
                object.setErrorMsg("\u4e0d\u652f\u6301\u7684\u6587\u4ef6\u7c7b\u578b\uff01");
                object.setResponse("403");
                return object;
            }
            FileInfo upFile = this.fileService.area((FileAreaConfig)new NewsImgFile()).uploadByKey(originalFilename, realKey, null, file.getBytes());
            String path = this.fileService.area((FileAreaConfig)new NewsImgFile()).getPath(upFile.getKey(), NpContextHolder.getContext().getTenant());
            byte[] textByte = path.getBytes("UTF-8");
            upFile = FileInfoBuilder.newFileInfo((FileInfo)upFile, (String)upFile.getFileGroupKey(), (String)Base64.encodeBase64String(textByte));
            UploaderInfoObject info = new UploaderInfoObject();
            Date lastModifyTime = upFile.getLastModifyTime();
            info.setFileName(upFile.getName());
            info.setFileGuid(upFile.getKey());
            Instant instant = lastModifyTime.toInstant();
            info.setModifyTime(instant.toString() + "154+08:00");
            String origin = "";
            String fileServiceAddress = this.addressProperties.getFileService();
            if (fileServiceAddress != null) {
                origin = headers.getOrigin().substring(0, headers.getOrigin().indexOf("//"));
                origin = origin + "//" + fileServiceAddress;
            }
            String realPath = origin + "/api/file/download/{path}/IMG_NEWS".replace("{path}", upFile.getPath());
            info.setFileUrl(realPath);
            object.setObject(info);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            object.setErrorMsg("\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25");
            object.setResponse("500");
        }
        return object;
    }

    private static boolean isContains(List<String> list, String value) {
        for (String string : list) {
            if (!string.equalsIgnoreCase(value)) continue;
            return true;
        }
        return false;
    }

    @NVWAPLoggable(value="\u9996\u9875\u4e0a\u4f20\u6587\u4ef6")
    @RequestMapping(value={"/uploadByGroup"}, method={RequestMethod.POST})
    public UploaderReturnObject uploadImgByGroup(@RequestParam(value="file") MultipartFile file, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        UploaderReturnObject object = new UploaderReturnObject();
        try {
            String groupKey = request.getParameter("Container");
            String directory = request.getParameter("Directory");
            String originalFilename = file.getOriginalFilename();
            String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if (!PortalImgLoadController.isContains(Arrays.asList(acceptFileTypes), fileType)) {
                object.setErrorMsg("\u4e0d\u652f\u6301\u7684\u6587\u4ef6\u7c7b\u578b\uff01");
                object.setResponse("403");
                return object;
            }
            FileInfo upFile = this.fileService.area((FileAreaConfig)new NewsImgFile()).uploadByGroup(file.getOriginalFilename(), groupKey, file.getBytes(), directory);
            String fileKey = upFile.getKey();
            String path = this.fileService.area((FileAreaConfig)new NewsImgFile()).getPath(fileKey, NpContextHolder.getContext().getTenant());
            byte[] textByte = path.getBytes("UTF-8");
            upFile = FileInfoBuilder.newFileInfo((FileInfo)upFile, (String)upFile.getFileGroupKey(), (String)Base64.encodeBase64String(textByte));
            UploaderInfoObject info = new UploaderInfoObject();
            Date lastModifyTime = upFile.getLastModifyTime();
            info.setFileName(upFile.getName());
            info.setFileGuid(fileKey);
            lastModifyTime.setHours(lastModifyTime.getHours() + 8);
            Instant instant = lastModifyTime.toInstant();
            info.setModifyTime(instant.toString() + "154+08:00");
            String origin = "";
            String fileServiceAddress = this.addressProperties.getFileService();
            if (fileServiceAddress != null) {
                origin = headers.getOrigin().substring(0, headers.getOrigin().indexOf("//"));
                origin = origin + "//" + fileServiceAddress;
            }
            String realPath = origin + "/api/file/download/{path}/IMG_NEWS".replace("{path}", upFile.getPath());
            info.setFileUrl(realPath);
            object.setObject(info);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            object.setErrorMsg(e.getMessage());
            object.setResponse("500");
        }
        return object;
    }

    @NVWAPLoggable(value="\u9996\u9875\u5220\u9664\u6587\u4ef6")
    @PostMapping(value={"/delete"})
    public DeleteObj deleteImg(@RequestBody Map<String, Object> delete) {
        DeleteObj obj = new DeleteObj();
        try {
            List list = (List)delete.get("groupsId");
            Map map = (Map)list.get(0);
            String key = map.get("FileGuid").toString();
            FileInfo info = this.fileService.area((FileAreaConfig)new NewsImgFile()).delete(key);
            FileStatus status = info.getStatus();
            if (!status.equals((Object)FileStatus.AVAILABLE)) {
                obj.setSuccess(true);
            } else {
                obj.setCode("500");
                obj.setSuccess(false);
                obj.setMessage(status.toString());
            }
        }
        catch (Exception e) {
            obj.setCode("500");
            obj.setSuccess(false);
            obj.setMessage(e.getMessage());
        }
        return obj;
    }

    @NVWAPLoggable(value="\u9996\u9875\u4e0a\u4f20\u5bcc\u6587\u672c")
    @RequestMapping(value={"/uploadAttach"}, method={RequestMethod.POST})
    public AttachImgObj uploadAttacheImg(@RequestParam(value="file") MultipartFile file, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        AttachImgObj object = new AttachImgObj();
        try {
            UUID randomUUID = UUID.randomUUID();
            FileInfo upFile = this.fileService.area((FileAreaConfig)new NewsImgFile()).uploadByKey(file.getOriginalFilename(), randomUUID.toString(), null, file.getBytes());
            String path = this.fileService.area((FileAreaConfig)new NewsImgFile()).getPath(upFile.getKey(), NpContextHolder.getContext().getTenant());
            byte[] textByte = path.getBytes("UTF-8");
            upFile = FileInfoBuilder.newFileInfo((FileInfo)upFile, (String)upFile.getFileGroupKey(), (String)Base64.encodeBase64String(textByte));
            String origin = "";
            String fileServiceAddress = this.addressProperties.getFileService();
            if (fileServiceAddress != null) {
                origin = headers.getOrigin().substring(0, headers.getOrigin().indexOf("//"));
                origin = origin + "//" + fileServiceAddress;
            }
            String realPath = origin + "/api/file/download/{path}/IMG_NEWS".replace("{path}", upFile.getPath());
            object.setName(upFile.getKey());
            object.setOriginalName(file.getOriginalFilename());
            object.setSize(String.valueOf(upFile.getSize()));
            object.setType(upFile.getExtension());
            object.setUrl(realPath);
            object.setState("SUCCESS");
        }
        catch (Exception e) {
            object.setState("FAILED");
        }
        return object;
    }

    @GetMapping(value={"/deleteImg/{area}/{fileKey}"})
    public DeleteObj removeFile(@PathVariable(value="area") String area, @PathVariable(value="fileKey") String fileKey) {
        DeleteObj obj = new DeleteObj();
        try {
            FileStatus status;
            byte[] decodeBase64 = Base64.decodeBase64(fileKey);
            String decodeKey = new String(decodeBase64);
            FileInfo file = this.fileService.area(area).getInfo(decodeKey);
            if (null != file && file.getStatus() == FileStatus.AVAILABLE) {
                file = this.fileService.area(area).delete(decodeKey);
            }
            if (!(status = file.getStatus()).equals((Object)FileStatus.AVAILABLE)) {
                obj.setSuccess(true);
            } else {
                obj.setCode("500");
                obj.setSuccess(false);
                obj.setMessage(status.toString());
            }
        }
        catch (Exception e) {
            obj.setCode("500");
            obj.setSuccess(false);
            obj.setMessage(e.getMessage());
        }
        return obj;
    }
}


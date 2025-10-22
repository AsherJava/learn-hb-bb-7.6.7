/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.attachment.web;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.attachment.message.FileCategoryInfo;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.FileCategoryService;
import com.jiuqi.nr.attachment.tools.AttachmentFileAreaService;
import com.jiuqi.nr.attachment.utils.FileInfoBuilder;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/attachment"})
public class AttachmentController {
    @Autowired
    private AttachmentFileAreaService attachmentFileAreaService;
    @Autowired
    private FileCategoryService fileCategoryService;

    @PostMapping(value={"/uploadByGroupKey"})
    public List<FileInfo> uploadByGroupKey(@RequestParam(value="file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        String groupKey = request.getParameter("groupKey");
        String dataSchemeKey = request.getParameter("dataSchemeKey");
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            String fileName = file.getOriginalFilename();
            byte[] fileContent = file.getBytes();
            ByteArrayInputStream input = new ByteArrayInputStream(fileContent);
            HashMap<String, String> expandInfo = new HashMap<String, String>();
            expandInfo.put("fileGroupKey", groupKey);
            FileBucketNameParam param = new FileBucketNameParam(dataSchemeKey);
            FileInfo fileInfo = this.attachmentFileAreaService.upload(param, fileName, input, expandInfo);
            String path = this.attachmentFileAreaService.getPath(param, fileInfo.getKey(), NpContextHolder.getContext().getTenant());
            byte[] textByte = path.getBytes(StandardCharsets.UTF_8);
            FileInfo newFileInfo = FileInfoBuilder.newFileInfo(fileInfo, fileInfo.getFileGroupKey(), Base64.getEncoder().encodeToString(textByte));
            newFileInfo.setSecretlevel(fileInfo.getSecretlevel());
            fileInfos.add(newFileInfo);
        }
        return fileInfos;
    }

    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u9644\u4ef6\u7c7b\u522b\u5217\u8868")
    @GetMapping(value={"/get-file-category"})
    public List<FileCategoryInfo> getFileCategory() {
        return this.fileCategoryService.getFileCategoryMapForSystem();
    }

    @ResponseBody
    @ApiOperation(value="\u66f4\u65b0\u9644\u4ef6\u7c7b\u522b\u5217\u8868")
    @PostMapping(value={"/update-file-category"})
    public boolean updateFileCategory(@RequestBody List<FileCategoryInfo> fileCategoryInfos) {
        return this.fileCategoryService.updateFileCategory(fileCategoryInfos);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.http.MediaType
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.aidocaudit.web;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.aidocaudit.dto.AnnotationFileDTO;
import com.jiuqi.gcreport.aidocaudit.dto.LineTextPosition;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditDocumentLocationService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Optional;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/docaudit/document/location"})
public class AidocauditDocumentLoactionController {
    @Autowired
    private IAidocauditDocumentLocationService documentLocationService;

    @GetMapping(value={"/find"})
    public BusinessResponseEntity<LineTextPosition> find(@RequestParam String fileId, @RequestParam(required=false) String paragraphTitle, @RequestParam String ruleItemName) {
        return this.documentLocationService.targeting(fileId, paragraphTitle, ruleItemName);
    }

    @GetMapping(value={"/downloadAnnotationFile"})
    public void downloadAnnotationFile(@RequestParam String resultId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        AnnotationFileDTO annotationFileDTO = this.documentLocationService.downloadAnnotationFile(resultId);
        ObjectInfo objectInfo = annotationFileDTO.getObjectInfo();
        ByteArrayOutputStream outputStream = annotationFileDTO.getOutputStream();
        this.setHeader(response, objectInfo);
        try (ServletOutputStream out = response.getOutputStream();){
            outputStream.writeTo((OutputStream)out);
            out.flush();
        }
    }

    private void setHeader(HttpServletResponse response, ObjectInfo objectInfo) throws UnsupportedEncodingException {
        String fileName = objectInfo.getName();
        fileName = URLEncoder.encode(fileName, "UTF-8");
        String mimeType = Optional.ofNullable(URLConnection.guessContentTypeFromName(fileName)).orElse(MediaType.ALL.getType());
        response.setHeader("Content-Type", mimeType + ";charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    }
}


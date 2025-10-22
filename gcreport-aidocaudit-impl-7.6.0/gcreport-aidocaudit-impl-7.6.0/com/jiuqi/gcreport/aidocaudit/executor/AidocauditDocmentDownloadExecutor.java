/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.common.ExpImpFileTypeEnum
 *  com.jiuqi.common.expimp.dataexport.ExportExecutor
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.http.MediaType
 */
package com.jiuqi.gcreport.aidocaudit.executor;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.common.ExpImpFileTypeEnum;
import com.jiuqi.common.expimp.dataexport.ExportExecutor;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.gcreport.aidocaudit.dto.AnnotationFileDTO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditDocumentLocationService;
import java.io.ByteArrayOutputStream;
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
import org.springframework.stereotype.Component;

@Component
public class AidocauditDocmentDownloadExecutor
implements ExportExecutor {
    @Autowired
    private IAidocauditDocumentLocationService documentLocationService;

    public String getName() {
        return "AidocauditDocmentDownloadExecutor";
    }

    public ExpImpFileTypeEnum getFileType() {
        return null;
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        Param param = (Param)JsonUtils.readValue((String)context.getParam(), Param.class);
        String resultId = param.getResultId();
        AnnotationFileDTO annotationFileDTO = this.documentLocationService.downloadAnnotationFile(resultId);
        ObjectInfo objectInfo = annotationFileDTO.getObjectInfo();
        ByteArrayOutputStream outputStream = annotationFileDTO.getOutputStream();
        this.setHeader(response, objectInfo);
        try (ServletOutputStream out = response.getOutputStream();){
            outputStream.writeTo((OutputStream)out);
            out.flush();
        }
        return null;
    }

    private void setHeader(HttpServletResponse response, ObjectInfo objectInfo) throws UnsupportedEncodingException {
        String fileName = objectInfo.getName();
        fileName = URLEncoder.encode(fileName, "UTF-8");
        String mimeType = Optional.ofNullable(URLConnection.guessContentTypeFromName(fileName)).orElse(MediaType.ALL.getType());
        response.setHeader("Content-Type", mimeType + ";charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    }

    public static class Param {
        private String resultId;

        public String getResultId() {
            return this.resultId;
        }

        public void setResultId(String resultId) {
            this.resultId = resultId;
        }
    }
}


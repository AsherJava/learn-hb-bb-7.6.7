/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nr.attachment.exception.FileNotFoundException
 *  com.jiuqi.nr.attachment.utils.FileOperationUtils
 *  com.jiuqi.nr.convert.pdf.utils.ConvertUtil
 *  com.jiuqi.nr.convert.pdf.utils.ConvertUtil$FILE_TYPE_TO_PDF
 *  com.jiuqi.nvwa.oss.service.IObjectService
 *  org.apache.pdfbox.pdmodel.PDDocument
 */
package com.jiuqi.gcreport.aidocaudit.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultDao;
import com.jiuqi.gcreport.aidocaudit.dto.AnnotationFileDTO;
import com.jiuqi.gcreport.aidocaudit.dto.LineTextPosition;
import com.jiuqi.gcreport.aidocaudit.dto.ResultitemOrderDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultEO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditAttachmentDetailService;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditDocumentLocationService;
import com.jiuqi.gcreport.aidocaudit.util.DocDiffUtil;
import com.jiuqi.gcreport.aidocaudit.util.DocxDiffUtil;
import com.jiuqi.gcreport.aidocaudit.util.PdfDiffUtil;
import com.jiuqi.nr.attachment.exception.FileNotFoundException;
import com.jiuqi.nr.attachment.utils.FileOperationUtils;
import com.jiuqi.nr.convert.pdf.utils.ConvertUtil;
import com.jiuqi.nvwa.oss.service.IObjectService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AidocauditDocumentLocationServiceImpl
implements IAidocauditDocumentLocationService {
    private Logger log = LoggerFactory.getLogger(AidocauditDocumentLocationServiceImpl.class);
    private static final String PDF = ".pdf";
    private static final String DOC = ".doc";
    private static final String DOCX = ".docx";
    private static final String LOCATION_FAILED_MSG = "\u5b9a\u4f4d\u5931\u8d25,\u8bf7\u624b\u52a8\u67e5\u627e\uff01";
    @Autowired
    private IObjectService objectService;
    @Autowired
    private IAidocauditAttachmentDetailService attachmentDetailService;
    @Autowired
    private IAidocauditResultDao resultDao;

    @Override
    public BusinessResponseEntity<LineTextPosition> targeting(String fileId, String paragraphTitle, String ruleItemName) {
        if (!StringUtils.hasText(fileId)) {
            return BusinessResponseEntity.error((String)"\u53c2\u6570\u5f02\u5e38\uff01");
        }
        boolean b = false;
        ObjectStorageService objectStorageService = null;
        try {
            objectStorageService = FileOperationUtils.objService((String)"JTABLEAREA");
            b = objectStorageService.existObject(fileId);
        }
        catch (ObjectStorageException e) {
            this.log.error(e.getMessage(), e);
        }
        if (!b) {
            throw new FileNotFoundException(fileId);
        }
        LineTextPosition lineTextPosition = null;
        byte[] bytes = null;
        PDDocument pdDocument = null;
        try (InputStream inputStream = objectStorageService.download(fileId);){
            ObjectInfo objectInfo = this.objectService.getObjectInfo("JTABLEAREA", fileId);
            String fileExtension = objectInfo.getExtension();
            if (PDF.equals(fileExtension)) {
                pdDocument = PDDocument.load((InputStream)inputStream);
            } else if (DOC.equals(fileExtension)) {
                bytes = ConvertUtil.convertToPdf((InputStream)inputStream, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.DOC);
                pdDocument = PDDocument.load((byte[])bytes);
            } else if (DOCX.equals(fileExtension)) {
                bytes = ConvertUtil.convertToPdf((InputStream)inputStream, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.DOCX);
                pdDocument = PDDocument.load((byte[])bytes);
            } else {
                this.log.error("\u5f53\u524d\u6587\u6863\u683c\u5f0f\u4e0d\u652f\u6301\u6587\u672c\u5339\u914d!");
                BusinessResponseEntity businessResponseEntity = BusinessResponseEntity.error((String)LOCATION_FAILED_MSG);
                return businessResponseEntity;
            }
            String text = StringUtils.hasText(paragraphTitle) ? paragraphTitle : ruleItemName;
            lineTextPosition = PdfDiffUtil.getLocation(text, pdDocument);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6587\u4ef6\u5b9a\u4f4d\u5931\u8d25", (Throwable)e);
        }
        finally {
            if (pdDocument != null) {
                try {
                    pdDocument.close();
                }
                catch (IOException e) {
                    this.log.warn("\u5173\u95ed PDDocument \u65f6\u53d1\u751f\u5f02\u5e38", e);
                }
            }
        }
        if (lineTextPosition == null) {
            this.log.error(LOCATION_FAILED_MSG);
            return BusinessResponseEntity.error((String)LOCATION_FAILED_MSG);
        }
        return BusinessResponseEntity.ok((Object)lineTextPosition);
    }

    @Override
    public AnnotationFileDTO downloadAnnotationFile(String resultId) {
        AidocauditResultEO aidocauditResultEO = (AidocauditResultEO)this.resultDao.get((Serializable)((Object)resultId));
        String fileId = aidocauditResultEO.getAttachmentId();
        List<ResultitemOrderDTO> resultitemOrderDTOList = this.attachmentDetailService.auditDetails(resultId, 0);
        boolean b = false;
        ObjectStorageService objectStorageService = null;
        try {
            objectStorageService = FileOperationUtils.objService((String)"JTABLEAREA");
            b = objectStorageService.existObject(fileId);
        }
        catch (ObjectStorageException e) {
            this.log.error(e.getMessage(), e);
        }
        if (!b) {
            throw new FileNotFoundException(fileId);
        }
        HashMap<String, String> annotationInfo = new HashMap<String, String>();
        for (ResultitemOrderDTO resultitemOrderDTO : resultitemOrderDTOList) {
            List<ResultitemOrderDTO> children = resultitemOrderDTO.getChildren();
            for (ResultitemOrderDTO child : children) {
                String text = StringUtils.hasText(child.getParagraphTitle()) ? child.getParagraphTitle() : child.getRuleItemName();
                String annotationMsg = "\u6ee1\u5206\uff1a" + child.getFullScore() + "    \u5f97\u5206\uff1a" + child.getScore() + "\n\u5f97\u5206\u4f9d\u636e\uff1a" + child.getScoreBasis();
                annotationInfo.put(text, annotationMsg);
            }
        }
        ByteArrayOutputStream outputStream = null;
        ObjectInfo objectInfo = null;
        try {
            InputStream inputStream = objectStorageService.download(fileId);
            Object object = null;
            try {
                objectInfo = this.objectService.getObjectInfo("JTABLEAREA", fileId);
                String fileExtension = objectInfo.getExtension();
                if (PDF.equals(fileExtension)) {
                    outputStream = PdfDiffUtil.getAnnotationFile(annotationInfo, inputStream);
                } else if (DOC.equals(fileExtension)) {
                    outputStream = DocDiffUtil.getAnnotationFile(annotationInfo, inputStream);
                } else if (DOCX.equals(fileExtension)) {
                    outputStream = DocxDiffUtil.getAnnotationFile(annotationInfo, inputStream);
                } else {
                    this.log.error("\u5f53\u524d\u6587\u6863\u683c\u5f0f\u4e0d\u652f\u6301\u6587\u672c\u5339\u914d!");
                }
            }
            catch (Throwable throwable) {
                object = throwable;
                throw throwable;
            }
            finally {
                if (inputStream != null) {
                    if (object != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable) {
                            ((Throwable)object).addSuppressed(throwable);
                        }
                    } else {
                        inputStream.close();
                    }
                }
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u589e\u52a0\u6279\u6ce8\u4fe1\u606f\u5931\u8d25", (Throwable)e);
        }
        return new AnnotationFileDTO(objectInfo, outputStream);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gcreport.aidocaudit.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFComments;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTComment;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTComments;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTMarkupRange;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Node;

public class DocxDiffUtil {
    private static final int WINDOW_SIZE = 6;

    private DocxDiffUtil() {
        throw new UnsupportedOperationException("\u8be5\u7c7b\u4e0d\u5141\u8bb8\u5b9e\u4f8b\u5316");
    }

    public static ByteArrayOutputStream getAnnotationFile(Map<String, String> annotationInfos, InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (XWPFDocument document = new XWPFDocument(inputStream);){
            boolean hasCatalog = DocxDiffUtil.checkCatalog(document);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            BigInteger annotationID = BigInteger.valueOf(0L);
            for (Map.Entry<String, String> entry : annotationInfos.entrySet()) {
                String text = entry.getKey();
                String annotation = entry.getValue();
                XWPFParagraph match = DocxDiffUtil.match(paragraphs, text, 6, hasCatalog);
                if (ObjectUtils.isEmpty(match)) continue;
                DocxDiffUtil.saveAnnotationDocx(document, match, annotation, annotationID);
                annotationID = annotationID.add(BigInteger.ONE);
            }
            document.write(outputStream);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6587\u4ef6\u589e\u52a0\u6279\u6ce8\u5931\u8d25", (Throwable)e);
        }
        return outputStream;
    }

    private static boolean checkCatalog(XWPFDocument document) {
        int maxCheckParagraphs = 20;
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (int i = 0; i < Math.min(maxCheckParagraphs, paragraphs.size()); ++i) {
            XWPFParagraph paragraph = paragraphs.get(i);
            String paragraphText = paragraph.getText();
            if ((!paragraphText.contains("\u76ee") || !paragraphText.contains("\u5f55")) && (!paragraphText.contains("T") || !paragraphText.contains("O") || !paragraphText.contains("C"))) continue;
            return true;
        }
        return false;
    }

    public static XWPFParagraph match(List<XWPFParagraph> paragraphs, String text, int windowSize, boolean hasCatalog) {
        if (ObjectUtils.isEmpty(paragraphs) || text.isEmpty() || windowSize <= 0) {
            return null;
        }
        int listSize = paragraphs.size();
        if (windowSize > listSize) {
            return DocxDiffUtil.fullScanMatch(paragraphs, text);
        }
        return DocxDiffUtil.slidingWindowMatch(paragraphs, text, windowSize, hasCatalog);
    }

    private static XWPFParagraph fullScanMatch(List<XWPFParagraph> paragraphs, String text) {
        StringBuilder content = new StringBuilder();
        for (XWPFParagraph paragraph : paragraphs) {
            content.append(paragraph.getText());
            if (!content.toString().contains(text)) continue;
            return paragraph;
        }
        return null;
    }

    private static XWPFParagraph slidingWindowMatch(List<XWPFParagraph> paragraphs, String text, int windowSize, boolean hasCatalog) {
        int listSize = paragraphs.size();
        int windowStartIndex = 0;
        int windowEndIndex = windowStartIndex + windowSize;
        StringBuilder windowContent = new StringBuilder();
        for (int i = windowStartIndex; i < windowEndIndex && i < listSize; ++i) {
            windowContent.append(paragraphs.get(i).getText());
        }
        boolean skippedFirstMatch = false;
        while (windowEndIndex <= listSize) {
            boolean containsText = windowContent.toString().contains(text);
            if (hasCatalog && !skippedFirstMatch && containsText) {
                skippedFirstMatch = true;
                windowEndIndex += windowSize;
                if ((windowStartIndex += windowSize) >= listSize) break;
                windowContent.setLength(0);
                for (int j = windowStartIndex; j < Math.min(windowEndIndex, listSize); ++j) {
                    windowContent.append(paragraphs.get(j).getText());
                }
                continue;
            }
            if (containsText) {
                return DocxDiffUtil.getParagraph(windowContent, text, paragraphs, windowStartIndex, windowEndIndex);
            }
            if (windowEndIndex >= listSize) break;
            windowContent.delete(0, paragraphs.get(windowStartIndex).getText().length());
            windowContent.append(paragraphs.get(windowEndIndex).getText());
            ++windowStartIndex;
            ++windowEndIndex;
        }
        return DocxDiffUtil.checkRemainingContent(windowContent, text, paragraphs, windowStartIndex, listSize);
    }

    private static XWPFParagraph checkRemainingContent(StringBuilder windowContent, String text, List<XWPFParagraph> paragraphs, int windowStartIndex, int listSize) {
        if (windowStartIndex < listSize) {
            windowContent.setLength(0);
            for (int j = windowStartIndex; j < listSize; ++j) {
                windowContent.append(paragraphs.get(j).getText());
            }
            if (windowContent.toString().contains(text)) {
                return DocxDiffUtil.getParagraph(windowContent, text, paragraphs, windowStartIndex, listSize);
            }
        }
        return null;
    }

    private static XWPFParagraph getParagraph(StringBuilder windowContent, String text, List<XWPFParagraph> paragraphs, int windowStartIndex, int windowEndIndex) {
        while (windowStartIndex < windowEndIndex) {
            windowContent.delete(0, paragraphs.get(windowStartIndex).getText().length());
            if (!windowContent.toString().contains(text)) {
                if (windowStartIndex == 0) {
                    return paragraphs.get(0);
                }
                return paragraphs.get(windowStartIndex);
            }
            ++windowStartIndex;
        }
        return null;
    }

    private static void saveAnnotationDocx(XWPFDocument document, XWPFParagraph paragraph, String annotationMsg, BigInteger annotationId) {
        XWPFComments docComments = document.getDocComments();
        if (docComments == null) {
            docComments = document.createComments();
        }
        CTComments ctComments = docComments.getCtComments();
        CTComment ctComment = ctComments.addNewComment();
        ctComment.setId(annotationId);
        ctComment.setAuthor("AI\u5ba1\u6838\uff1a");
        ctComment.setInitials("ZNSH");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        ctComment.setDate(calendar);
        String[] lines = annotationMsg.split("\n");
        CTP p = ctComment.addNewP();
        for (int i = 0; i < lines.length; ++i) {
            CTR r = p.addNewR();
            r.addNewT().setStringValue(lines[i]);
        }
        CTMarkupRange ctMarkupRange = paragraph.getCTP().addNewCommentRangeStart();
        ctMarkupRange.setId(annotationId);
        Node beginNode = paragraph.getRuns().get(0).getCTR().getDomNode();
        paragraph.getCTP().getDomNode().insertBefore(ctMarkupRange.getDomNode(), beginNode);
        paragraph.getCTP().addNewCommentRangeEnd().setId(annotationId);
        paragraph.getCTP().addNewR().addNewCommentReference().setId(annotationId);
    }
}


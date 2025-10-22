/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  org.apache.pdfbox.pdmodel.PDDocument
 *  org.apache.pdfbox.pdmodel.PDPage
 *  org.apache.pdfbox.pdmodel.common.PDRectangle
 *  org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText
 */
package com.jiuqi.gcreport.aidocaudit.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.aidocaudit.dto.LineTextPosition;
import com.jiuqi.gcreport.aidocaudit.dto.PdfExtractResultDTO;
import com.jiuqi.gcreport.aidocaudit.service.impl.GetCharLocationAndSize;
import com.jiuqi.gcreport.aidocaudit.util.TextPreprocessUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
import org.springframework.util.ObjectUtils;

public class PdfDiffUtil {
    private static final int WINDOW_SIZE = 6;

    private PdfDiffUtil() {
        throw new UnsupportedOperationException("\u8be5\u7c7b\u4e0d\u5141\u8bb8\u5b9e\u4f8b\u5316");
    }

    public static LineTextPosition getLocation(String text, PDDocument pdDocument) {
        PdfExtractResultDTO pdfExtractResult = PdfDiffUtil.buildCharLocationAndSize(pdDocument);
        boolean hasCatalog = pdfExtractResult.isHasCatalog();
        List<GetCharLocationAndSize> charLAS1 = pdfExtractResult.getResult();
        ArrayList<LineTextPosition> lineTextPositions = new ArrayList<LineTextPosition>();
        charLAS1.forEach(getCharLocationAndSize -> lineTextPositions.addAll(getCharLocationAndSize.getLineTextPositions()));
        return PdfDiffUtil.match(lineTextPositions, text, 6, hasCatalog);
    }

    public static ByteArrayOutputStream getAnnotationFile(Map<String, String> annotationInfos, InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (PDDocument pdDocument = PDDocument.load((InputStream)inputStream);){
            PdfExtractResultDTO pdfExtractResult = PdfDiffUtil.buildCharLocationAndSize(pdDocument);
            boolean hasCatalog = pdfExtractResult.isHasCatalog();
            List<GetCharLocationAndSize> charLAS1 = pdfExtractResult.getResult();
            ArrayList<LineTextPosition> lineTextPositions = new ArrayList<LineTextPosition>();
            charLAS1.forEach(getCharLocationAndSize -> lineTextPositions.addAll(getCharLocationAndSize.getLineTextPositions()));
            for (Map.Entry<String, String> entry : annotationInfos.entrySet()) {
                String text = entry.getKey();
                String annotation = entry.getValue();
                LineTextPosition match = PdfDiffUtil.match(lineTextPositions, text, 6, hasCatalog);
                if (ObjectUtils.isEmpty(match)) continue;
                PdfDiffUtil.saveAnnotationPdf(pdDocument, match, annotation);
            }
            pdDocument.save((OutputStream)outputStream);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6587\u4ef6\u589e\u52a0\u6279\u6ce8\u5931\u8d25", (Throwable)e);
        }
        return outputStream;
    }

    private static PdfExtractResultDTO buildCharLocationAndSize(PDDocument pdDocument) {
        boolean hasCatalog = false;
        ArrayList<GetCharLocationAndSize> pdfTextStripperList = null;
        try {
            int pageCount = pdDocument.getNumberOfPages();
            pdfTextStripperList = new ArrayList<GetCharLocationAndSize>(pageCount);
            for (int i = 1; i <= pageCount; ++i) {
                GetCharLocationAndSize pdfTextStripper = new GetCharLocationAndSize();
                pdfTextStripper.setPageNum(i);
                pdfTextStripper.setSortByPosition(true);
                pdfTextStripper.setStartPage(i);
                pdfTextStripper.setEndPage(i);
                StringWriter writer = new StringWriter();
                pdfTextStripper.writeText(pdDocument, writer);
                pdfTextStripperList.add(pdfTextStripper);
                if (i != 2 || CollectionUtils.isEmpty(pdfTextStripper.getLineTextPositions())) continue;
                List<LineTextPosition> lineTextPositions = pdfTextStripper.getLineTextPositions();
                hasCatalog = lineTextPositions.stream().anyMatch(lineTextPosition -> TextPreprocessUtil.preprocessText(lineTextPosition.getLineText()).contains("\u76ee\u5f55"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new PdfExtractResultDTO(pdfTextStripperList, hasCatalog);
    }

    public static LineTextPosition match(List<LineTextPosition> lineTextPositions, String text, int windowSize, boolean hasCatalog) {
        text = TextPreprocessUtil.preprocessText(text);
        if (CollectionUtils.isEmpty(lineTextPositions) || text.isEmpty() || windowSize <= 0) {
            return null;
        }
        int listSize = lineTextPositions.size();
        if (windowSize > listSize) {
            return PdfDiffUtil.fullScanMatch(lineTextPositions, text);
        }
        int windowStartIndex = 0;
        int windowEndIndex = windowStartIndex + windowSize;
        StringBuilder windowContent = new StringBuilder();
        for (int i = windowStartIndex; i < windowEndIndex; ++i) {
            windowContent.append(lineTextPositions.get(i).getLineText());
        }
        boolean skippedFirstMatch = false;
        while (windowEndIndex <= listSize) {
            boolean containsText = TextPreprocessUtil.preprocessText(windowContent.toString()).contains(text);
            if (hasCatalog && !skippedFirstMatch && containsText) {
                skippedFirstMatch = true;
                windowEndIndex += windowSize;
                if ((windowStartIndex += windowSize) >= listSize) break;
                windowContent.setLength(0);
                for (int j = windowStartIndex; j < Math.min(windowEndIndex, listSize); ++j) {
                    windowContent.append(lineTextPositions.get(j).getLineText());
                }
                continue;
            }
            if (containsText) {
                return PdfDiffUtil.getLineTextPosition(windowContent, text, lineTextPositions, windowStartIndex, windowEndIndex);
            }
            if (windowEndIndex >= listSize) break;
            windowContent.delete(0, lineTextPositions.get(windowStartIndex).getLineText().length());
            windowContent.append(lineTextPositions.get(windowEndIndex).getLineText());
            ++windowStartIndex;
            ++windowEndIndex;
        }
        return PdfDiffUtil.checkRemainingContent(windowContent, text, lineTextPositions, windowStartIndex, listSize);
    }

    private static LineTextPosition fullScanMatch(List<LineTextPosition> lineTextPositions, String text) {
        StringBuilder content = new StringBuilder();
        for (LineTextPosition position : lineTextPositions) {
            content.append(position.getLineText());
            if (!TextPreprocessUtil.preprocessText(content.toString()).contains(text)) continue;
            return position;
        }
        return null;
    }

    private static LineTextPosition checkRemainingContent(StringBuilder windowContent, String text, List<LineTextPosition> lineTextPositions, int windowStartIndex, int listSize) {
        if (windowStartIndex < listSize) {
            windowContent.setLength(0);
            for (int j = windowStartIndex; j < listSize; ++j) {
                windowContent.append(lineTextPositions.get(j).getLineText());
            }
            if (TextPreprocessUtil.preprocessText(windowContent.toString()).contains(text)) {
                return PdfDiffUtil.getLineTextPosition(windowContent, text, lineTextPositions, windowStartIndex, listSize);
            }
        }
        return null;
    }

    private static LineTextPosition getLineTextPosition(StringBuilder windowContent, String text, List<LineTextPosition> lineTextPositions, int windowStartIndex, int windowEndIndex) {
        while (windowStartIndex < windowEndIndex) {
            if (!TextPreprocessUtil.preprocessText((windowContent = windowContent.delete(0, lineTextPositions.get(windowStartIndex).getLineText().length())).toString()).contains(text)) {
                if (windowStartIndex == 0) {
                    return lineTextPositions.get(0);
                }
                return lineTextPositions.get(windowStartIndex);
            }
            ++windowStartIndex;
        }
        return null;
    }

    private static void saveAnnotationPdf(PDDocument pdDocument, LineTextPosition lineTextPosition, String annotationMsg) throws IOException {
        PDPage page = pdDocument.getPage(lineTextPosition.getPageNum() - 1);
        float x = lineTextPosition.getX();
        float y = lineTextPosition.getY();
        float width = lineTextPosition.getWidth();
        float height = lineTextPosition.getHeight();
        PDAnnotationText comment = new PDAnnotationText();
        comment.setRectangle(new PDRectangle(x, y - 10.0f, width, height));
        comment.setContents(annotationMsg);
        comment.setOpen(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        comment.setCreationDate(calendar);
        page.getAnnotations().add(comment);
    }
}


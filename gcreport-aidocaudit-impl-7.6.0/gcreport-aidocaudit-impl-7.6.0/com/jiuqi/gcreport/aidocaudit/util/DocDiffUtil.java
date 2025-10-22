/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.spire.doc.Document
 *  com.spire.doc.FileFormat
 *  com.spire.doc.Section
 *  com.spire.doc.documents.CommentMark
 *  com.spire.doc.documents.CommentMarkType
 *  com.spire.doc.documents.Paragraph
 *  com.spire.doc.documents.TextSelection
 *  com.spire.doc.fields.Comment
 *  com.spire.doc.interfaces.IDocument
 *  com.spire.doc.interfaces.IDocumentObject
 */
package com.jiuqi.gcreport.aidocaudit.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.documents.CommentMark;
import com.spire.doc.documents.CommentMarkType;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.TextSelection;
import com.spire.doc.fields.Comment;
import com.spire.doc.interfaces.IDocument;
import com.spire.doc.interfaces.IDocumentObject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.springframework.util.ObjectUtils;

public class DocDiffUtil {
    private DocDiffUtil() {
        throw new UnsupportedOperationException("\u8be5\u7c7b\u4e0d\u5141\u8bb8\u5b9e\u4f8b\u5316");
    }

    public static ByteArrayOutputStream getAnnotationFile(Map<String, String> annotationInfos, InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = null;
        try {
            document = new Document();
            document.loadFromStream(inputStream, FileFormat.Doc);
            boolean hasCatalog = DocDiffUtil.checkCatalog(document);
            int annotationId = 0;
            for (Map.Entry<String, String> entry : annotationInfos.entrySet()) {
                String text = entry.getKey();
                String annotation = entry.getValue();
                Object[] selections = document.findAllString(text, true, false);
                if (ObjectUtils.isEmpty(selections)) continue;
                Paragraph paragraph = null;
                if (hasCatalog) {
                    if (selections.length <= 1) continue;
                    paragraph = selections[1].getAsOneRange().getOwnerParagraph();
                } else {
                    paragraph = selections[0].getAsOneRange().getOwnerParagraph();
                }
                DocDiffUtil.saveAnnotationDoc(document, paragraph, (TextSelection[])selections, annotation, annotationId, hasCatalog);
                ++annotationId;
            }
            document.saveToStream((OutputStream)outputStream, FileFormat.Doc);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6587\u4ef6\u589e\u52a0\u6279\u6ce8\u5931\u8d25", (Throwable)e);
        }
        finally {
            if (document != null) {
                document.dispose();
            }
        }
        return outputStream;
    }

    private static boolean checkCatalog(Document document) {
        for (Object sectionObj : document.getSections()) {
            Section section = (Section)sectionObj;
            for (Object paraObj : section.getParagraphs()) {
                Paragraph para = (Paragraph)paraObj;
                String styleName = para.getStyleName();
                if (styleName == null || !styleName.contains("\u76ee\u5f55") && !styleName.contains("TOC")) continue;
                return true;
            }
        }
        return false;
    }

    private static void saveAnnotationDoc(Document document, Paragraph paragraph, TextSelection[] selections, String annotationMsg, int annotationId, boolean hasCatalog) {
        int localIndex = 0;
        if (hasCatalog) {
            localIndex = 1;
        }
        int index = paragraph.getChildObjects().indexOf((IDocumentObject)selections[localIndex].getAsOneRange());
        CommentMark start = new CommentMark((IDocument)document, CommentMarkType.Comment_Start);
        start.setCommentId(annotationId);
        CommentMark end = new CommentMark((IDocument)document, CommentMarkType.Comment_End);
        end.setCommentId(annotationId);
        Comment comment = new Comment((IDocument)document);
        comment.getFormat().setCommentId(annotationId);
        comment.getBody().addParagraph().appendText(annotationMsg);
        comment.getFormat().setAuthor("AI\u5ba1\u6838\uff1a");
        comment.getFormat().setInitial("ZNSH");
        paragraph.getChildObjects().insert(index, (IDocumentObject)start);
        paragraph.getChildObjects().insert(index + 1, (IDocumentObject)selections[localIndex].getAsOneRange());
        paragraph.getChildObjects().insert(index + 2, (IDocumentObject)end);
        paragraph.getChildObjects().insert(index + 3, (IDocumentObject)comment);
    }
}


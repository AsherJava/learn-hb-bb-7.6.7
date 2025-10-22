/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.attachment.enue.FileTypes
 */
package com.jiuqi.nr.dataentry.attachment.enums;

import com.jiuqi.nr.attachment.enue.FileTypes;
import java.util.ArrayList;
import java.util.List;

public enum FileTypeEnum {
    WORD("1", "Word\u6587\u6863\uff08*.doc\uff0c*.docx\uff09", FileTypes.WORD.getType()),
    EXCEL("2", "Excel\u6587\u6863\uff08*.xls\uff0c*.xlsx\uff09", FileTypes.EXCEL.getType()),
    PPT("3", "PPT\u6587\u6863\uff08*.ppt\uff0c*.pptx\uff09", FileTypes.PPT.getType()),
    PDF("4", "PDF\u6587\u6863\uff08*.pdf\uff09", FileTypes.PDF.getType()),
    TXT("5", "\u6587\u672c\u6587\u6863\uff08*.txt\uff09", FileTypes.TXT.getType()),
    PICTURE("6", "\u56fe\u7247\uff08*.jpg\uff0c*.png\uff0c*.jpeg\uff09", FileTypes.PICTURE.getType()),
    AUDIO("7", "\u97f3\u9891\uff08*.mp3\uff0c*.wav\uff0c*.wma\uff09", FileTypes.AUDIO.getType()),
    VIDEO("8", "\u89c6\u9891\uff08*.mp4\uff0c*.avi\uff0c*.flv\uff09", FileTypes.VIDEO.getType()),
    COMPRESS("9", "\u538b\u7f29\u6587\u6863\uff08*.zip\uff0c*.rar\uff0c*.7z\uff09", FileTypes.COMPRESS.getType()),
    OTHER("0", "\u5176\u4ed6", FileTypes.OTHER.getType()),
    WORD_EN("1", "Word\uff08*.doc\uff0c*.docx\uff09", FileTypes.WORD.getType()),
    EXCEL_EN("2", "Excel\uff08*.xls\uff0c*.xlsx\uff09", FileTypes.EXCEL.getType()),
    PPT_EN("3", "PPT\uff08*.ppt\uff0c*.pptx\uff09", FileTypes.PPT.getType()),
    PDF_EN("4", "PDF\uff08*.pdf\uff09", FileTypes.PDF.getType()),
    TXT_EN("5", "TXT\uff08*.txt\uff09", FileTypes.TXT.getType()),
    PICTURE_EN("6", "picture\uff08*.jpg\uff0c*.png\uff0c*.jpeg\uff09", FileTypes.PICTURE.getType()),
    AUDIO_EN("7", "Audio\uff08*.mp3\uff0c*.wav\uff0c*.wma\uff09", FileTypes.AUDIO.getType()),
    VIDEO_EN("8", "Video\uff08*.mp4\uff0c*.avi\uff0c*.flv\uff09", FileTypes.VIDEO.getType()),
    COMPRESS_EN("9", "Compress documents\uff08*.zip\uff0c*.rar\uff0c*.7z\uff09", FileTypes.COMPRESS.getType()),
    OTHER_EN("0", "Other", FileTypes.OTHER.getType());

    private String code;
    private String title;
    private List<String> type;

    private FileTypeEnum(String code, String title, List<String> type) {
        this.code = code;
        this.title = title;
        this.type = type;
    }

    public static final List<String> getFileTypeByCode(String code) {
        switch (code) {
            case "0": {
                return OTHER.getType();
            }
            case "1": {
                return WORD.getType();
            }
            case "2": {
                return EXCEL.getType();
            }
            case "3": {
                return PPT.getType();
            }
            case "4": {
                return PDF.getType();
            }
            case "5": {
                return TXT.getType();
            }
            case "6": {
                return PICTURE.getType();
            }
            case "7": {
                return AUDIO.getType();
            }
            case "8": {
                return VIDEO.getType();
            }
            case "9": {
                return COMPRESS.getType();
            }
        }
        return new ArrayList<String>();
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getType() {
        return this.type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 */
package com.jiuqi.nr.fileupload.system.option;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
public class FileUploadScenePlugin {
    public static final String PLUGINNAME = "file-upload-config-plugin";
    @Autowired
    private SystemOptionOperator systemOptionOperator;

    public String getId() {
        return "fileuploadscene";
    }

    public String getTitle() {
        return "\u6587\u4ef6\u4e0a\u4f20";
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public String getPluginName() {
        return PLUGINNAME;
    }

    public boolean disableReset() {
        return false;
    }

    public int getOrdinal() {
        return 0;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> items = new ArrayList<ISystemOptionItem>();
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "BLACK_LIST_AND_WHITE_LIST";
            }

            public String getTitle() {
                return "\u542f\u7528\u9ed1\u540d\u5355\u6216\u767d\u540d\u5355";
            }

            public String getDefaultValue() {
                return "0";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "BLACK_LIST_INFO";
            }

            public String getTitle() {
                return "\u9ed1\u540d\u5355\u4fe1\u606f\u914d\u7f6e";
            }

            public String getDefaultValue() {
                return "php;php5;php4;php3;html;htm;phtml;Html;Htm;jsp;jspa;jspx;jtml;jSp;jSpx;jSpa;jHtml;asp;aspx;ascx;ashx;asmx;cer;js;sh;bat;-";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "WHITE_LIST_INFO";
            }

            public String getTitle() {
                return "\u767d\u540d\u5355\u4fe1\u606f\u914d\u7f6e";
            }

            public String getDefaultValue() {
                return "";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_MAX_SIZE";
            }

            public String getTitle() {
                return "\u7cfb\u7edf\u4e0a\u4f20\u6587\u4ef6\u5927\u5c0f\u9650\u5236(\u5355\u4f4dKB)";
            }

            public String getDefaultValue() {
                return "102400";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_PAGE";
            }

            public String getTitle() {
                return "\u56fe\u7247\u6587\u4ef6\u573a\u666f";
            }

            public String getDefaultValue() {
                return "bmp;cmx;cod;gif;ico;ief;jfif;jpe;jpeg;jpg;pbm;pgm;pnm;ppm;ras;rgb;svg;tif;tiff;xbm;xpm;xwd";
            }

            public String getDescribe() {
                return "sceneConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_PAGE_SIZE";
            }

            public String getTitle() {
                return "\u56fe\u7247\u6587\u4ef6\u573a\u666f\u5927\u5c0f\u9650\u5236";
            }

            public String getDefaultValue() {
                return "";
            }

            public String getDescribe() {
                return "sceneSizeConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_TABLE";
            }

            public String getTitle() {
                return "\u8868\u683c\u6587\u4ef6\u573a\u666f";
            }

            public String getDefaultValue() {
                return "xls;xlsx;et";
            }

            public String getDescribe() {
                return "sceneConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_TABLE_SIZE";
            }

            public String getTitle() {
                return "\u8868\u683c\u6587\u4ef6\u573a\u666f\u5927\u5c0f\u9650\u5236";
            }

            public String getDefaultValue() {
                return "";
            }

            public String getDescribe() {
                return "sceneSizeConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_DOC";
            }

            public String getTitle() {
                return "\u6587\u6863\u6587\u4ef6\u573a\u666f";
            }

            public String getDefaultValue() {
                return "doc;docx;xml;pdf;wps;ofd";
            }

            public String getDescribe() {
                return "sceneConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_DOC_SIZE";
            }

            public String getTitle() {
                return "\u6587\u6863\u6587\u4ef6\u573a\u666f\u5927\u5c0f\u9650\u5236";
            }

            public String getDefaultValue() {
                return "";
            }

            public String getDescribe() {
                return "sceneSizeConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_TXT";
            }

            public String getTitle() {
                return "\u6587\u672c\u6587\u4ef6\u573a\u666f";
            }

            public String getDefaultValue() {
                return "txt;csv";
            }

            public String getDescribe() {
                return "sceneConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_TXT_SIZE";
            }

            public String getTitle() {
                return "\u6587\u672c\u6587\u4ef6\u573a\u666f\u5927\u5c0f\u9650\u5236";
            }

            public String getDefaultValue() {
                return "";
            }

            public String getDescribe() {
                return "sceneSizeConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_PPT";
            }

            public String getTitle() {
                return "\u6f14\u793a\u6587\u4ef6\u573a\u666f";
            }

            public String getDefaultValue() {
                return "pptx;ppt;dps";
            }

            public String getDescribe() {
                return "sceneConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_PPT_SIZE";
            }

            public String getTitle() {
                return "\u6f14\u793a\u6587\u4ef6\u573a\u666f\u5927\u5c0f\u9650\u5236";
            }

            public String getDefaultValue() {
                return "";
            }

            public String getDescribe() {
                return "sceneSizeConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_MUSIC";
            }

            public String getTitle() {
                return "\u97f3\u9891\u6587\u4ef6\u573a\u666f";
            }

            public String getDefaultValue() {
                return "cda;wav;aif;aiff;mid;wma;ra;mp3";
            }

            public String getDescribe() {
                return "sceneConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_MUSIC_SIZE";
            }

            public String getTitle() {
                return "\u97f3\u9891\u6587\u4ef6\u573a\u666f\u5927\u5c0f\u9650\u5236";
            }

            public String getDefaultValue() {
                return "";
            }

            public String getDescribe() {
                return "sceneSizeConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_VIDEO";
            }

            public String getTitle() {
                return "\u89c6\u9891\u6587\u4ef6\u573a\u666f";
            }

            public String getDefaultValue() {
                return "avi;mov;rmvb;rm;flv;mp4";
            }

            public String getDescribe() {
                return "sceneConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_VIDEO_SIZE";
            }

            public String getTitle() {
                return "\u89c6\u9891\u6587\u4ef6\u573a\u666f\u5927\u5c0f\u9650\u5236";
            }

            public String getDefaultValue() {
                return "";
            }

            public String getDescribe() {
                return "sceneSizeConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_ZIP";
            }

            public String getTitle() {
                return "\u538b\u7f29\u6587\u4ef6\u573a\u666f";
            }

            public String getDefaultValue() {
                return "7z;rar;zip;apz;sh;tar;gz;tgz";
            }

            public String getDescribe() {
                return "sceneConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_DEFAULT_SCENE_ZIP_SIZE";
            }

            public String getTitle() {
                return "\u538b\u7f29\u6587\u4ef6\u573a\u666f\u5927\u5c0f\u9650\u5236";
            }

            public String getDefaultValue() {
                return "";
            }

            public String getDescribe() {
                return "sceneSizeConfig";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "FILE_UPLOAD_APP_CHECK_INFO";
            }

            public String getTitle() {
                return "\u6587\u4ef6\u4e0a\u4f20\u529f\u80fd\u6821\u9a8c\u4fe1\u606f";
            }

            public String getDefaultValue() {
                return "";
            }
        });
        return items;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.upload.utils;

import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;

public enum NodeColorEnum {
    ORIGINAL_UPLOAD(UploadState.ORIGINAL_UPLOAD, "\u672a\u4e0a\u62a5", "#icon-_Tyitianbao", "#808080"){

        @Override
        public NodeColorEnum getCurrentState() {
            return ORIGINAL_UPLOAD;
        }

        @Override
        public String getActionDesc(WorkFlowType workflowType) {
            return ORIGINAL_UPLOAD.getTitle();
        }
    }
    ,
    ORIGINAL_SUBMIT(UploadState.ORIGINAL_SUBMIT, "\u672a\u9001\u5ba1", "#icon-_Tyitianbao", "#808080"){

        @Override
        public NodeColorEnum getCurrentState() {
            return ORIGINAL_SUBMIT;
        }

        @Override
        public String getActionDesc(WorkFlowType workflowType) {
            return ORIGINAL_SUBMIT.getTitle();
        }
    }
    ,
    SUBMITED(UploadState.SUBMITED, "\u5df2\u9001\u5ba1", "#icon-_Tsongshen", "#808080"){

        @Override
        public NodeColorEnum getCurrentState() {
            return SUBMITED;
        }

        @Override
        public String getActionDesc(WorkFlowType workflowType) {
            return SUBMITED.getTitle();
        }
    }
    ,
    RETURNED(UploadState.RETURNED, "\u5df2\u9000\u5ba1", "#icon-_Ttuishen1", "#808080"){

        @Override
        public NodeColorEnum getCurrentState() {
            return RETURNED;
        }

        @Override
        public String getActionDesc(WorkFlowType workflowType) {
            return RETURNED.getTitle();
        }
    }
    ,
    UPLOADED(UploadState.UPLOADED, "\u5df2\u4e0a\u62a5", "#icon-_Tyishangbao", "#808080"){

        @Override
        public NodeColorEnum getCurrentState() {
            return UPLOADED;
        }

        @Override
        public String getActionDesc(WorkFlowType workflowType) {
            return UPLOADED.getTitle();
        }
    }
    ,
    CONFIRMED(UploadState.CONFIRMED, "\u5df2\u786e\u8ba4", "#icon-_Tyiqueren", "#808080"){

        @Override
        public NodeColorEnum getCurrentState() {
            return CONFIRMED;
        }

        @Override
        public String getActionDesc(WorkFlowType workflowType) {
            return CONFIRMED.getTitle();
        }
    }
    ,
    REJECTED(UploadState.REJECTED, "\u5df2\u9000\u56de", "#icon-_Tyituihui", "#808080"){

        @Override
        public NodeColorEnum getCurrentState() {
            return REJECTED;
        }

        @Override
        public String getActionDesc(WorkFlowType workflowType) {
            return REJECTED.getTitle();
        }
    }
    ,
    PART_SUBMITED(UploadState.PART_SUBMITED, "\u90e8\u5206\u9001\u5ba1", "#icon-16_Tfenzuhuofenbiaosongshen", "#808080"){

        @Override
        public NodeColorEnum getCurrentState() {
            return PART_SUBMITED;
        }

        @Override
        public String getActionDesc(WorkFlowType workflowType) {
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                return "\u62a5\u8868\u9001\u5ba1";
            }
            if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                return "\u5206\u7ec4\u9001\u5ba1";
            }
            return PART_SUBMITED.getTitle();
        }
    }
    ,
    PART_UPLOADED(UploadState.PART_UPLOADED, "\u90e8\u5206\u4e0a\u62a5", "#icon-16_Tfenzuhuofenbiaoshangbao", "#808080"){

        @Override
        public NodeColorEnum getCurrentState() {
            return PART_UPLOADED;
        }

        @Override
        public String getActionDesc(WorkFlowType workflowType) {
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                return "\u62a5\u8868\u4e0a\u62a5";
            }
            if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                return "\u5206\u7ec4\u4e0a\u62a5";
            }
            return PART_UPLOADED.getTitle();
        }
    }
    ,
    PART_CONFIRMED(UploadState.PART_CONFIRMED, "\u90e8\u5206\u786e\u8ba4", "#icon-16_Tfenzuhuofenbiaoqueren", "#808080"){

        @Override
        public NodeColorEnum getCurrentState() {
            return PART_CONFIRMED;
        }

        @Override
        public String getActionDesc(WorkFlowType workflowType) {
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                return "\u62a5\u8868\u786e\u8ba4";
            }
            if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                return "\u5206\u7ec4\u786e\u8ba4";
            }
            return PART_CONFIRMED.getTitle();
        }
    };

    private UploadState uploadState;
    private String title;
    private String icon;
    private String color;

    private NodeColorEnum(UploadState uploadState, String title, String icon, String color) {
        this.uploadState = uploadState;
        this.title = title;
        this.icon = icon;
        this.color = color;
    }

    public UploadState getUploadState() {
        return this.uploadState;
    }

    public String getTitle() {
        return this.title;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getColor() {
        return this.color;
    }

    public abstract NodeColorEnum getCurrentState();

    public abstract String getActionDesc(WorkFlowType var1);

    public static enum Type {
        ICON,
        COLOR;

    }
}


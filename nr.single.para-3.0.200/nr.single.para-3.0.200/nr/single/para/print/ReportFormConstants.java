/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.text.StringHelper
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 */
package nr.single.para.print;

import com.jiuqi.np.period.text.StringHelper;
import com.jiuqi.nr.definition.facade.DesignFormDefine;

public class ReportFormConstants {
    public static final int T_NONE = 0;
    public static final int T_ID = 2;
    public static final int T_TITLE = 4;
    public static final int T_SN = 8;
    public static final int T_SUBTITLE = 16;
    public static final int T_EXPTMPLABEL = 32;
    public static final int T_EXPBGCOLOR = 64;
    public static final int T_SECURITYCLASS = 128;
    public static final String FIELD_SEPARATOR = ",";
    public static final String REPORT_TYPE_BILLLIST = "BILLLIST";
    public static final int REPORTFORM_TYPE_REPORT = 0;
    public static final int REPORTFORM_TYPE_ANALYZE = 1;
    public static final int REPORTFORM_TYPE_ANALYZEREPORT = 2;
    public static final int REPORTFORM_TYPE_REMARKREPORT = 4;
    public static final int REPORTFORM_TYPE_DYNAMICREPORT = 5;
    public static final int REPORTFORM_TYPE_QUICKREPORT = 6;
    public static final int REPORTFORM_TYPE_BILLLISTDEFINE = 7;
    public static final int REPORTFORM_TYPE_QUESTIONNAIRE = 8;
    public static final int REPORTFORM_TYPE_QUESTIONNAIRELIST = 9;
    public static final int REPORTFORM_TYPE_DOCUMENT = 10;
    public static final int REPORT_TYPE_HB_REPORT = 103;
    public static final int REPORT_TYPE_INVENT_REPORT = 106;
    public static final int REPORT_TYPE_ASSET_REPORT = 107;
    public static final int REPORT_TYPE_INVENTORY_REPORT = 108;
    public static final int REPORT_TYPE_CONTRA_UNIT_GROUP = 109;
    public static final String STR_REPORT_TYPE = "\u62a5\u8868";
    public static final String STR_ANALYZE_TYPE = "\u5206\u6790\u8868";
    public static final String STR_ANALYZEREPORT_TYPE = "\u5206\u6790\u62a5\u544a";
    public static final String STR_IFR_TYPE = "\u5206\u6790\u8868";
    public static final String STR_REMARKREPORT_TYPE = "\u5907\u6ce8\u8868";
    public static final String STR_DYNAMICREPORT_TYPE = "\u52a8\u6001\u8868";
    public static final String STR_QUICKREPORT_TYPE = "\u5feb\u901f\u5206\u6790\u8868";
    public static final String STR_BILLLIST_TYPE = "\u5355\u636e\u5217\u8868";
    public static final String STR_QUESTIONNAIRE_TYPE = "\u95ee\u5377";
    public static final String STR_QUESTIONNAIRELIST_TYPE = "\u95ee\u5377\u5217\u8868";
    public static final String[] reportType = new String[]{"\u8868\u683c", "\u5206\u6790\u8868", "\u5206\u6790\u62a5\u544a", "\u5907\u6ce8\u8868", "\u52a8\u6001\u8868", "\u5feb\u901f\u5206\u6790\u8868", "\u5355\u636e\u5217\u8868", "\u95ee\u5377", "\u95ee\u5377\u5217\u8868", "\u6587\u6863\u578b"};
    public static final int[] reportTypeValue = new int[]{0, 1, 2, 4, 5, 6, 7, 8, 9, 10};
    public static final String[] moneyUnit = new String[]{"\u5143", "\u5341\u5143", "\u767e\u5143", "\u5343\u5143", "\u4e07\u5143", "\u5341\u4e07\u5143", "\u767e\u4e07\u5143", "\u5343\u4e07\u5143", "\u4ebf\u5143"};
    public static final String[] DATE_FORMAT_TITLE = new String[]{"2013\u5e741\u67082\u65e5", "2013\u5e741\u6708", "1\u67082\u65e5", "2013\u5e74", "\u661f\u671f\u4e09", "\u4e09", "2013-1-2", "2013-1-2 5:05", "13-1-2", "1-2", "2013", "1-2-13", "01-02-13"};
    public static final String[] DATE_FORMAT_PATTERN = new String[]{"yyyy\u5e74M\u6708d\u65e5", "yyyy\u5e74M\u6708", "M\u6708d\u65e5", "yyyy\u5e74", "E", "yyyy-M-d", "yyyy-M-d H:mm", "yy-M-d", "M-d", "yyyy", "M-d-yy", "MM-dd-yy"};
    public static final int SUMMARY_NOT_SUMMARY = 0;
    public static final int SUMMARY_LISTED = 1;
    public static final int SUMMARY_CLASSIFICATION = 2;
    public static final int SUMMARY_SEPARATE = 3;
    public static final String[] SUMMARY_TYPE_TITLE = new String[]{"\u4e0d\u6c47\u603b", "\u7f57\u5217", "\u5206\u7c7b", "\u5355\u884c"};
    public static final int ANYREPORT_TABITEM_NUM = 5;
    public static final Object[] filterZbs = new Object[]{"RECVER"};
    public static final int SEARCH_REPORT_GROUP = 0;
    public static final int SEARCH_REPORT_FORM = 1;
    public static final int EARCH_REPORT_GROUP_AND_FORM = 2;
    public static final String CELL_SEL_HANDLER = "SR_RF.rf_SrGrid_cellSelHandler";
    public static final String EXPRESS_VIEW_DESC = "\u8868\u8fbe\u5f0f\u8bbe\u7f6e\u89c6\u56fe";
    public static final String EXPRESS_VIEW_TITLE = "\u8868\u8fbe\u5f0f\u8bbe\u7f6e";
    public static final String EXPRESS_SINGLE = "1";
    public static final String EXPRESS_RECT = "2";
    public static final String EXPRESS_DATACHANGE_CLINET = "rf_SrGrid_expressDataChange";
    public static final String C_SPLIT = "\\";
    public static final String C_EXPRESS_JOIN = ";";
    public static final String C_JOIN_SPLIT = "\\;";
    public static final String C_DOT = ".";
    public static final String C_DOT_SPLIT = "\\.";
    public static final String C_TILDE = "~";
    public static final String C_TILDE_SPLIT = "\\~";
    public static final String C_COMMA = ",";
    public static final String RELATE_LESS = "1";
    public static final String RELATE_MORE = "2";
    public static final String C_ROW_SHOWTEXT = "row";
    public static final String C_COL_SHOWTEXT = "col";
    public static final int OPERATION_MAX = 10000;
    public static final String OPERATION_TYPE_REPORTFORM = "reportform";
    public static final String OPERATION_TYPE_LABEL = "label";
    public static final String OPERATION_TYPE_NUMBER = "number";
    public static final String REPORTFORM_READONLY_SETTING = "#readAreaControl";
    public static final String SOLUTION_GUID = "SolutionGuid";
    public static final String REPORT_FORM = "ReportForm";
    public static final String REPORTFORM_QUESTIONNAIRE = "\u666e\u67e5\u95ee\u5377";
    public static final int REPORTFORM_IMPORTSETTING_NEW = 1;
    public static final int REPORTFORM_IMPORTSETTING_COVER = 2;
    public static final int REPORTFORM_IMPORTSETTING_MERGE = 3;

    public static final String buildReportLabelStr(DesignFormDefine fReport, int type) {
        StringBuffer labelStr = new StringBuffer();
        if (ReportFormConstants.check(type, 2) && !StringHelper.isNull((String)fReport.getFormCode())) {
            labelStr.append("[").append(fReport.getFormCode()).append("]");
        }
        if (ReportFormConstants.check(type, 8) && !StringHelper.isNull((String)fReport.getSerialNumber())) {
            labelStr.append("[").append(fReport.getSerialNumber()).append("]");
        }
        if (ReportFormConstants.check(type, 4)) {
            labelStr.append(fReport.getTitle());
        }
        if (ReportFormConstants.check(type, 16) && !StringHelper.isNull((String)fReport.getSubTitle())) {
            if (0 < labelStr.length()) {
                labelStr.append("-");
            }
            labelStr.append(fReport.getSubTitle());
        }
        if (0 == labelStr.length()) {
            labelStr.append(fReport.getTitle());
        }
        return labelStr.toString();
    }

    public static final int getReportLabel(boolean showID, boolean showTitle, boolean showSubTitle, boolean showSN) {
        int labelType = 0;
        if (showID) {
            labelType |= 2;
        }
        if (showSubTitle) {
            labelType |= 0x10;
        }
        if (showSN) {
            labelType |= 8;
        }
        if (showTitle) {
            labelType |= 4;
        }
        return labelType;
    }

    public static final boolean check(int desLabel, int labelType) {
        return (desLabel & labelType) != 0;
    }

    public static boolean isQuickReport(int type) {
        return 6 == type;
    }

    public static boolean isReport(int type) {
        return 0 == type;
    }

    public static boolean isIfr(int type) {
        return type > 100 && type < 200;
    }

    public static boolean isAnalyzeReport(int type) {
        return 2 == type;
    }

    public static boolean isBillList(int type) {
        return 7 == type;
    }

    public static boolean isRemarkReport(int type) {
        return 4 == type;
    }

    public static boolean isDynamicReport(int type) {
        return 5 == type;
    }

    public static boolean isQuestionnaire(int type) {
        return 8 == type;
    }

    public static boolean isDocumentReport(int type) {
        return 10 == type;
    }

    public static boolean isQuestionnaireList(int type) {
        return 9 == type;
    }

    public static enum ShowType {
        ARABIC("\u6570\u5b57", 0),
        CHINESE("\u4e2d\u6587", 1);

        private final String showType;
        private final int data;

        private ShowType(String showType, int data) {
            this.showType = showType;
            this.data = data;
        }

        public String getShowType() {
            return this.showType;
        }

        public int getData() {
            return this.data;
        }
    }

    public static enum PeriodType {
        N,
        H,
        J,
        Y,
        X,
        Z,
        R,
        B;

    }

    public static enum GuidType {
        SOLUTION,
        GROUP,
        REPORTFORM,
        MAPAREA,
        ZB;

    }

    public static enum GroupType {
        ALL,
        ROOT,
        LEAF;

    }
}


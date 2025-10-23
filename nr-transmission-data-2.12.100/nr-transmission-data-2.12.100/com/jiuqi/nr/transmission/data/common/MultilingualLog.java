/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.transmission.data.common;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Locale;
import org.springframework.util.StringUtils;

public class MultilingualLog {
    private static final String EXPORT_FINISH_ENGLISH = "Export data completed!";
    private static final String EXPORT_FINISH_CHINESE = "\u5bfc\u51fa\u6570\u636e\u5b8c\u6210!";
    private static final String EXPORT_FAIL_ENGILSH = "Failed to export data!";
    private static final String EXPORT_FAIL_CHINESE = "\u5bfc\u51fa\u6570\u636e\u5931\u8d25!";
    private static final String IMPORT_FINISH_ENGLISH = "Import data completed!";
    private static final String IMPORT_FINISH_CHINESE = "\u5bfc\u5165\u6570\u636e\u5b8c\u6210!";
    private static final String IMPORT_FAIL_ENGILSH = "Failed to import data!";
    private static final String IMPORT_FAIL_CHINESE = "\u5bfc\u5165\u6570\u636e\u5931\u8d25!";

    public static boolean isEnglish() {
        NpContext context = NpContextHolder.getContext();
        Locale locale = context.getLocale();
        return Locale.US.equals(locale);
    }

    public static String getImportFinish() {
        return MultilingualLog.isEnglish() ? IMPORT_FINISH_ENGLISH : IMPORT_FINISH_CHINESE;
    }

    public static String getImportFail() {
        return MultilingualLog.isEnglish() ? IMPORT_FAIL_ENGILSH : IMPORT_FAIL_CHINESE;
    }

    public static String getExportFail() {
        return MultilingualLog.isEnglish() ? EXPORT_FAIL_ENGILSH : EXPORT_FAIL_CHINESE;
    }

    public static String executeSyncMessage(int type) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "Packaging form data!" : "\u6b63\u5728\u6253\u5305\u62a5\u8868\u6570\u636e!";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "The data of the subordinate server is packed and ready to be saved to the file server!" : "\u4e0b\u7ea7\u670d\u52a1\u5668\u6570\u636e\u6253\u5305\u5b8c\u6210\uff0c\u51c6\u5907\u5b58\u5165\u6587\u4ef6\u670d\u52a1\u5668\uff01";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "The data package of the subordinate server has been saved to the file server, and is ready to push the data package to the superior service!" : "\u4e0b\u7ea7\u670d\u52a1\u5668\u6570\u636e\u5305\u5df2\u7ecf\u5b58\u5165\u6587\u4ef6\u670d\u52a1\u5668\uff0c\u51c6\u5907\u8fdb\u884c\u5411\u4e0a\u7ea7\u670d\u52a1\u63a8\u9001\u6570\u636e\u5305\uff01";
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? "The multi-level deployment of the data package of the lower server and the push of the upper server are completed, and the upper server starts to load!" : "\u591a\u7ea7\u90e8\u7f72\u4e0b\u7ea7\u670d\u52a1\u5668\u6570\u636e\u6253\u5305\u5e76\u63a8\u9001\u4e0a\u7ea7\u670d\u52a1\u5b8c\u6210\uff0c\u4e0a\u7ea7\u670d\u52a1\u5668\u5f00\u59cb\u6267\u884c\u88c5\u5165\uff01";
        }
        return "";
    }

    public static String onlineImportMessage(int type, String message) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "Failed to query the primary and sub service associated users: " : "\u67e5\u8be2\u4e3b\u5b50\u670d\u52a1\u5173\u8054\u7528\u6237\u5931\u8d25\uff1a";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "Failed to save the subordinate service file to the file server! " : "\u4e0b\u7ea7\u670d\u52a1\u6587\u4ef6\u5b58\u5165\u6587\u4ef6\u670d\u52a1\u5668\u5931\u8d25\uff01";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "Multi-level deployment, online synchronization, and asynchronous data loading by the parent service! " : "\u591a\u7ea7\u90e8\u7f72\u5728\u7ebf\u540c\u6b65\u4e0a\u7ea7\u670d\u52a1\u5f00\u59cb\u8fdb\u884c\u6570\u636e\u5f02\u6b65\u88c5\u5165\uff01";
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? "Online synchronization subordinate service authentication failed! " : "\u5728\u7ebf\u540c\u6b65\u4e0b\u7ea7\u670d\u52a1\u8ba4\u8bc1\u5931\u8d25\uff01";
        }
        return "";
    }

    public static String checkFlowTypeMessage(int type, String message) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "Error loading log server while offline loading parsing file for multi-level deployment: " : "\u591a\u7ea7\u90e8\u7f72\u79bb\u7ebf\u88c5\u5165\u89e3\u6790\u6587\u4ef6\u65f6\u5019\u88c5\u5165\u65e5\u5fd7\u670d\u52a1\u5668\u51fa\u9519\uff1a";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "An error occurred in the log server while loading the parsing file offline for multi-level deployment: " : "\u591a\u7ea7\u90e8\u7f72\u79bb\u7ebf\u88c5\u5165\u89e3\u6790\u6587\u4ef6\u65f6\u65e5\u5fd7\u670d\u52a1\u5668\u51fa\u9519\uff1a";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "Error loading parsing file offline for multi-level deployment: " : "\u591a\u7ea7\u90e8\u7f72\u79bb\u7ebf\u88c5\u5165\u89e3\u6790\u6587\u4ef6\u51fa\u9519\uff1a";
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? "Multi level deployment offline loading, errors in querying report schemes corresponding to tasks and periods! " : "\u591a\u7ea7\u90e8\u7f72\u79bb\u7ebf\u88c5\u5165\uff0c\u4efb\u52a1\u548c\u65f6\u671f\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u51fa\u9519\uff01";
        }
        return "";
    }

    public static String executeOffLineImportMessage(int type, String message) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "File server failed to download files! " : "\u6587\u4ef6\u670d\u52a1\u5668\u4e0b\u8f7d\u6587\u4ef6\u5931\u8d25\uff01";
        }
        return "";
    }

    public static String exportByHistoryMessage(int type, String message) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "The multi-level deployment failed to export the data package from the history record. Please directly export the file! " : "\u591a\u7ea7\u90e8\u7f72\u4ece\u5386\u53f2\u8bb0\u5f55\u5bfc\u51fa\u6570\u636e\u5305\u5931\u8d25\uff0c\u8bf7\u76f4\u63a5\u5bfc\u51fa\u6587\u4ef6\uff01";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "This file cannot be queried in history, please directly export the file! " : "\u6587\u4ef6\u5728\u5386\u53f2\u8bb0\u5f55\u67e5\u8be2\u4e0d\u5230\uff0c\u8bf7\u76f4\u63a5\u5bfc\u51fa\u6587\u4ef6\uff01";
        }
        return "";
    }

    public static String pushDataMessage(int type) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "Unable to obtain superior service!" : "\u65e0\u6cd5\u83b7\u53d6\u4e0a\u7ea7\u670d\u52a1\uff01";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "The current user does not have permission to upload data!" : "\u5f53\u524d\u7528\u6237\u6ca1\u6709\u6743\u9650\u4e0a\u4f20\u6570\u636e\uff01";
        }
        return "";
    }

    public static String fileImportMessage(int type) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "The transmission starts loading data! " : "\u591a\u7ea7\u90e8\u7f72\u5f00\u59cb\u88c5\u5165\u6570\u636e\uff01";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "After loading the form data, prepare to determine whether it needs to be upload! " : "\u62a5\u8868\u6570\u636e\u88c5\u5165\u5b8c\u6bd5\uff0c\u51c6\u5907\u5224\u65ad\u662f\u5426\u9700\u8981\u4e0a\u62a5\uff01";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "Cover code table does not exist! " : "\u5c01\u9762\u4ee3\u7801\u8868\u4e0d\u5b58\u5728\uff01";
        }
        return "";
    }

    public static String fileExportMessage() {
        return MultilingualLog.isEnglish() ? EXPORT_FINISH_ENGLISH : EXPORT_FINISH_CHINESE;
    }

    public static String filterFormAndEntityMessage(int type) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "The current user does not have permission on the task associated with the scheme!" : "\u5f53\u524d\u7528\u6237\u5bf9\u8be5\u65b9\u6848\u5173\u8054\u7684\u4efb\u52a1\u6ca1\u6709\u6743\u9650";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "The selected unit is empty when exporting data!" : "\u5bfc\u51fa\u6570\u636e\u65f6\u6240\u9009\u5355\u4f4d\u4e3a\u7a7a\uff01";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "The selected form is empty when exporting data!" : "\u5bfc\u51fa\u6570\u636e\u65f6\u6240\u9009\u62a5\u8868\u4e3a\u7a7a\uff01";
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? "The unit with permission is empty when exporting data!" : "\u5bfc\u51fa\u6570\u636e\u65f6\u6709\u6743\u9650\u7684\u5355\u4f4d\u4e3a\u7a7a\uff01";
        }
        if (type == 5) {
            return MultilingualLog.isEnglish() ? "The form with permission is empty when exporting data!" : "\u5bfc\u51fa\u6570\u636e\u65f6\u6709\u6743\u9650\u7684\u62a5\u8868\u4e3a\u7a7a\uff01";
        }
        return "";
    }

    public static String getfilterFormAndEntityMessage(String userName, String taskName, String periodValue, String adjustValue, String fmdmMessage, String noAuthFormMessage, String noAuthUnitMessage) {
        StringBuilder logMessage = new StringBuilder();
        if (MultilingualLog.isEnglish()) {
            logMessage.append("The user performing synchronization is\uff1a").append(userName).append("\r\n");
            if (StringUtils.hasLength(taskName)) {
                logMessage.append("Task is\uff1a").append(taskName);
            }
            if (StringUtils.hasLength(periodValue)) {
                logMessage.append(", Period is\uff1a").append(periodValue);
            }
            if (StringUtils.hasLength(adjustValue)) {
                logMessage.append(", The adjustment period is: ").append(adjustValue);
            }
            if (StringUtils.hasLength(fmdmMessage)) {
                logMessage.append(", The fmdm form table is: ").append(fmdmMessage);
            }
            if (StringUtils.hasLength(noAuthFormMessage)) {
                logMessage.append(", Multi level deployment no longer verifies report permissions").append(noAuthFormMessage);
            }
            if (StringUtils.hasLength(noAuthUnitMessage)) {
                logMessage.append(", The unit that failed to export without user permission is: ").append(noAuthUnitMessage);
            }
        } else {
            logMessage.append("\u6267\u884c\u540c\u6b65\u7684\u7528\u6237\u4e3a\uff1a").append(userName).append("\r\n");
            if (StringUtils.hasLength(taskName)) {
                logMessage.append("\u4efb\u52a1\u4e3a\uff1a").append(taskName);
            }
            if (StringUtils.hasLength(periodValue)) {
                logMessage.append("\uff0c\u65f6\u671f\u4e3a\uff1a").append(periodValue);
            }
            if (StringUtils.hasLength(adjustValue)) {
                logMessage.append("\uff0c\u8c03\u6574\u671f\u4e3a\uff1a").append(adjustValue);
            }
            if (StringUtils.hasLength(fmdmMessage)) {
                logMessage.append("\uff0c\u5c01\u9762\u4ee3\u7801\u8868\u4e3a\uff1a").append(fmdmMessage);
            }
            if (StringUtils.hasLength(noAuthFormMessage)) {
                logMessage.append("\uff0c\u62a5\u8868\u6743\u9650\u4e0d\u5728\u591a\u7ea7\u90e8\u7f72\u5c42\u8fdb\u884c\u6821\u9a8c").append(noAuthFormMessage);
            }
            if (StringUtils.hasLength(noAuthUnitMessage)) {
                logMessage.append("\uff0c\u7528\u6237\u6ca1\u6709\u6743\u9650\u5bfc\u51fa\u5931\u8d25\u7684\u5355\u4f4d\u4e3a\uff1a").append(noAuthUnitMessage);
            }
        }
        return logMessage.toString();
    }

    public static String buildParamFileMessage(int type) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "Error packaging parameter file during export: " : "\u5bfc\u51fa\u65f6\u5019\u6253\u5305\u53c2\u6570\u6587\u4ef6\u51fa\u9519: ";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "Parameter file packaging succeeded!" : "\u53c2\u6570\u6587\u4ef6\u6253\u5305\u6210\u529f\uff01";
        }
        return "";
    }

    public static String getFormSchemeMessage(String taskKey, String periodValue) {
        return MultilingualLog.isEnglish() ? String.format("An exception occurred when querying the scheme associated with task:%s in period:%s!", taskKey, periodValue) : String.format("\u67e5\u8be2\u4efb\u52a1:%s\u65f6\u671f:%s\u5173\u8054\u7684\u65b9\u6848\u65f6\uff0c\u53d1\u751f\u5f02\u5e38\uff01", taskKey, periodValue);
    }

    public static String checkFormAndEntityMessage(int type, String message) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "Check whether the form and unit exist. " : "\u68c0\u67e5\u62a5\u8868\u548c\u5355\u4f4d\u662f\u5426\u5b58\u5728\u3002";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "The report where the main service does not exist is: " : "\u4e3b\u670d\u52a1\u4e0d\u5b58\u5728\u7684\u62a5\u8868\u4e3a\uff1a";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "unknown title." : "\u672a\u77e5\u540d\u79f0\u3002";
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? "The form does not exist." : "\u8be5\u62a5\u8868\u4e0d\u5b58\u5728\u3002";
        }
        if (type == 5) {
            return MultilingualLog.isEnglish() ? "After checking, the report to be synchronized is empty, execution ended." : "\u68c0\u67e5\u540e\u8981\u540c\u6b65\u7684\u62a5\u8868\u4e3a\u7a7a\uff0c\u6267\u884c\u7ed3\u675f\u3002";
        }
        if (type == 6) {
            return MultilingualLog.isEnglish() ? "Verify that the report does not exist when loading data Complete. " : "\u88c5\u5165\u6570\u636e\u65f6\u6821\u9a8c\u62a5\u8868\u4e0d\u5b58\u5728\u5b8c\u6210\u3002";
        }
        if (type == 7) {
            return MultilingualLog.isEnglish() ? "The main service associated user does not have permission or does not exist. The unit is: " : "\u4e3b\u670d\u52a1\u5173\u8054\u7528\u6237\u6ca1\u6709\u6743\u9650\u6216\u4e0d\u5b58\u5728\u7684\u5355\u4f4d\u4e3a\uff1a";
        }
        if (type == 8) {
            return MultilingualLog.isEnglish() ? "The current user does not have permissions on the company. " : "\u5f53\u524d\u7528\u6237\u5bf9\u8be5\u5355\u4f4d\u6ca1\u6709\u6743\u9650\u3002";
        }
        if (type == 9) {
            return MultilingualLog.isEnglish() ? "The synchronized company is empty after being filtered for existence and permissions, and execution is complete. " : "\u540c\u6b65\u7684\u5355\u4f4d\u7ecf\u8fc7\u5b58\u5728\u6027\u548c\u6743\u9650\u8fc7\u6ee4\u540e\u4e3a\u7a7a\uff0c\u6267\u884c\u7ed3\u675f\u3002";
        }
        if (type == 10) {
            return MultilingualLog.isEnglish() ? "Verification unit does not exist when loading data Completed. " : "\u88c5\u5165\u6570\u636e\u65f6\u6821\u9a8c\u5355\u4f4d\u4e0d\u5b58\u5728\u5b8c\u6210\u3002";
        }
        return "";
    }

    public static String checkUploadMessage(int type, String message) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "Check upload. " : "\u68c0\u67e5\u4e0a\u62a5\u3002";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "There are forms under this company for data loading. " : "\u6b64\u5355\u4f4d\u4e0b\u6709\u62a5\u8868\u8fdb\u884c\u6570\u636e\u88c5\u5165\u3002";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? String.format("The dimension value of the primary dimension %s to import report data is: ", message) : String.format("\u62a5\u8868\u6570\u636e\u8981\u5bfc\u5165\u7684\u4e3b\u7ef4\u5ea6 %s\u7684\u7ef4\u5ea6\u503c\u4e3a\uff1a", message);
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? "The dimension value that has been submitted without importing data is: " : "\u5df2\u4e0a\u62a5\u800c\u4e0d\u518d\u5bfc\u5165\u6570\u636e\u7684\u7ef4\u5ea6\u503c\u4e3a\uff1a";
        }
        if (type == 5) {
            return MultilingualLog.isEnglish() ? "The unit has uploaded. " : "\u8be5\u5355\u4f4d\u5df2\u4e0a\u62a5\u3002";
        }
        if (type == 6) {
            return MultilingualLog.isEnglish() ? "The company's form %s has been submitted. " : "\u8be5\u5355\u4f4d\u4e0b\u62a5\u8868%s\u5df2\u4e0a\u62a5\u3002";
        }
        if (type == 7) {
            return MultilingualLog.isEnglish() ? "The company's form group %s has been submitted. " : "\u8be5\u5355\u4f4d\u4e0b\u62a5\u8868\u5206\u7ec4%s\u5df2\u4e0a\u62a5\u3002";
        }
        if (type == 8) {
            return MultilingualLog.isEnglish() ? String.format("There are %s dimensions that actually import data\uff1a", message) : String.format("\u5b9e\u9645\u8fdb\u884c\u5bfc\u5165\u6570\u636e\u7684\u7ef4\u5ea6\u6709%s\u4e2a\uff1a", message);
        }
        if (type == 9) {
            return MultilingualLog.isEnglish() ? "All dimensions are submitted, and data import is no longer required! " : "\u5f53\u524d\u670d\u52a1\u8981\u540c\u6b65\u7684\u672a\u4e0a\u62a5\u5355\u4f4d\u4e3a\u7a7a\uff0c\u4e0d\u518d\u8fdb\u884c\u6570\u636e\u5bfc\u5165\uff01";
        }
        if (type == 10) {
            return MultilingualLog.isEnglish() ? "Check whether the form and unit exist. " : "\u68c0\u67e5\u62a5\u8868\u548c\u5355\u4f4d\u662f\u5426\u5b58\u5728\u3002";
        }
        return "";
    }

    public static String fmdmDataImportMessage(int type, String message) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "There is no cover code form for data loading. " : "\u6ca1\u6709\u5c01\u9762\u4ee3\u7801\u8868\u8fdb\u884c\u6570\u636e\u88c5\u5165\u3002";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "Failed to add a company to the cover code table. There are no units available for data loading. Data loading ended prematurely! " : "\u5c01\u9762\u4ee3\u7801\u8868\u65b0\u589e\u5355\u4f4d\u5931\u8d25\uff0c\u6ca1\u6709\u53ef\u4ee5\u8fdb\u884c\u6570\u636e\u88c5\u5165\u7684\u5355\u4f4d\uff0c\u6570\u636e\u88c5\u5165\u63d0\u524d\u7ed3\u675f\uff01";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "The indicators that do not exist under the current task cover code table are: %s " + message : "\u5f53\u524d\u4efb\u52a1\u5c01\u9762\u4ee3\u7801\u8868\u4e0b\u4e0d\u5b58\u5728\u7684\u6307\u6807\u4e3a\uff1a" + message;
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? "The units that originally do not exist in the current task cover code table but are added through the cover code table are: " + message : "\u5f53\u524d\u4efb\u52a1\u5c01\u9762\u4ee3\u7801\u8868\u4e0b\u539f\u672c\u4e0d\u5b58\u5728\u4f46\u662f\u901a\u8fc7\u5c01\u9762\u4ee3\u7801\u8868\u65b0\u589e\u7684\u5355\u4f4d\u4e3a\uff1a" + message;
        }
        if (type == 5) {
            return MultilingualLog.isEnglish() ? "Cover code table data imported successfully! " : "\u5c01\u9762\u4ee3\u7801\u8868\u6570\u636e\u5bfc\u5165\u6210\u529f\uff01";
        }
        if (type == 6) {
            return MultilingualLog.isEnglish() ? "When importing cover code table data, the file is parsed as empty! " : "\u5bfc\u5165\u5c01\u9762\u4ee3\u7801\u8868\u6570\u636e\u65f6,\u6587\u4ef6\u89e3\u6790\u4e3a\u7a7a\uff01";
        }
        if (type == 7) {
            return MultilingualLog.isEnglish() ? "This unit does not exist in this service book. Successfully created by loading data from the cover code table! " : "\u6b64\u5355\u4f4d\u5728\u672c\u670d\u52a1\u672c\u4e0d\u5b58\u5728\uff0c\u901a\u8fc7\u5c01\u9762\u4ee3\u7801\u8868\u88c5\u5165\u6570\u636e\u65b0\u5efa\u6210\u529f\uff01";
        }
        if (type == 8) {
            return MultilingualLog.isEnglish() ? "unknown title" : "\u672a\u77e5\u540d\u79f0";
        }
        if (type == 9) {
            return MultilingualLog.isEnglish() ? "Failed to add this unit! " : "\u6b64\u5355\u4f4d\u65b0\u589e\u5931\u8d25\uff01";
        }
        if (type == 10) {
            return MultilingualLog.isEnglish() ? "Entity query error! " : "\u5b9e\u4f53\u67e5\u8be2\u51fa\u9519\uff01";
        }
        if (type == 11) {
            return MultilingualLog.isEnglish() ? "The task has not been enabled to allow editing of units. Adding this unit failed! " : "\u4efb\u52a1\u6ca1\u6709\u5f00\u542f\u5141\u8bb8\u7f16\u8f91\u5355\u4f4d\uff0c\u5355\u4f4d\u65b0\u589e\u5931\u8d25\uff01";
        }
        return "";
    }

    public static String businessDataImportMessage(int type, String message) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "There are no ordinary forms for data loading! " : "\u6ca1\u6709\u666e\u901a\u62a5\u8868\u8fdb\u884c\u6570\u636e\u88c5\u5165\uff01";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "An error occurred while importing report data: " + message : "\u5bfc\u5165\u62a5\u8868\u6570\u636e\u65f6\u53d1\u751f\u4e86\u9519\u8bef\uff1a " + message;
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? String.format("There are %s forms that have successfully imported report data! ", message) : String.format("\u62a5\u8868\u6570\u636e\u5bfc\u5165\u6210\u529f\u7684\u62a5\u8868\u6709%s\u4e2a\uff1a", message);
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? String.format("There are %s forms that failed to import report data! ", message) : String.format("\u62a5\u8868\u6570\u636e\u5bfc\u5165\u5931\u8d25\u7684\u62a5\u8868\u6709%s\u4e2a\uff1a", message);
        }
        if (type == 5) {
            return MultilingualLog.isEnglish() ? "The reason for the failure is: " : "\u5931\u8d25\u539f\u56e0\u4e3a\uff1a";
        }
        if (type == 6) {
            return MultilingualLog.isEnglish() ? "Unknown report failure reason is: " : "\u672a\u77e5\u62a5\u8868\u5931\u8d25\u539f\u56e0\u4e3a\uff1a";
        }
        if (type == 7) {
            return MultilingualLog.isEnglish() ? "unknown title" : "\u672a\u77e5\u540d\u79f0";
        }
        return "";
    }

    public static String formulaCheckImportMessage(int type, String message) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? String.format("The formula scheme %s only exists on the service of the data source. The current service does not exist!", message) : String.format("\u516c\u5f0f\u65b9\u6848%s\u53ea\u5728\u6570\u636e\u6765\u6e90\u7684\u670d\u52a1\u4e0a\u6709\uff0c\u5f53\u524d\u670d\u52a1\u4e0d\u5b58\u5728\uff01", message);
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? String.format("When importing the error description of formula scheme %s, the parsed file is empty. ", message) : String.format("\u5bfc\u5165\u516c\u5f0f\u65b9\u6848%s\u7684\u51fa\u9519\u8bf4\u660e\u65f6,\u89e3\u6790\u7684\u6587\u4ef6\u4e3a\u7a7a\u3002", message);
        }
        return "";
    }

    public static String formulaCheckLogFormulaInfoMessage(int type, String message1, String message2) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? String.format("Formula scheme %s successfully imported %s error descriptions!", message1, message2) : String.format("\u516c\u5f0f\u65b9\u6848%s\u6210\u529f\u5bfc\u5165%s\u6761\u51fa\u9519\u8bf4\u660e\u3002", message1, message2);
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? String.format("An error under formula scheme %s indicates that the import failed. Reason for failure: %s. ", message1, message2) : String.format("\u516c\u5f0f\u65b9\u6848%s\u4e0b\u7684\u51fa\u9519\u8bf4\u660e\u5bfc\u5165\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a%s\u3002", message1, message2);
        }
        return "";
    }

    public static String exportFormDataError(String dataGatherCode) {
        if ("BUSINESS_DATA".equals(dataGatherCode)) {
            return MultilingualLog.isEnglish() ? "An error occurred while exporting form data: " : "\u5bfc\u51fa\u62a5\u8868\u6570\u636e\u65f6\u53d1\u751f\u4e86\u9519\u8bef:";
        }
        if ("FORMULA_CHECK".equals(dataGatherCode)) {
            return MultilingualLog.isEnglish() ? "An error occurred while exporting the formula approval description: " : "\u5bfc\u51fa\u516c\u5f0f\u5ba1\u6838\u8bf4\u660e\u65f6\u53d1\u751f\u4e86\u9519\u8bef:";
        }
        if ("FMDM_DATA".equals(dataGatherCode)) {
            return MultilingualLog.isEnglish() ? "An error occurred while exporting cover code data: " : "\u5bfc\u51fa\u5c01\u9762\u4ee3\u7801\u6570\u636e\u65f6\u53d1\u751f\u4e86\u9519\u8bef:";
        }
        return "";
    }

    public static String exportFmdmDataError(int type) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "The cover code table image and attachment type indicators cannot synchronize data: " : "\u5c01\u9762\u4ee3\u7801\u8868\u56fe\u7247\u548c\u9644\u4ef6\u7c7b\u578b\u7684\u6307\u6807\u65e0\u6cd5\u540c\u6b65\u6570\u636e\uff1a";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "Duplicate indicator codes exist. Data linked to indicators will be imported and exported first. " : "\u5b58\u5728\u6307\u6807\u4ee3\u7801\u91cd\u590d\uff0c\u6307\u6807\u94fe\u63a5\u7684\u6570\u636e\u5c06\u4f18\u5148\u5bfc\u5165\u5bfc\u51fa\u3002";
        }
        return "";
    }

    public static String exportFormulaCheckError(String taskTitle, String formSchemeTitle, String formulaSchemeTitle, String errorMessage) {
        if (MultilingualLog.isEnglish()) {
            return String.format("An exception occurred when exporting the error description of task\uff1a%s, form scheme\uff1a%s, formula scheme\uff1a%s, it is %s\u3002", taskTitle, formSchemeTitle, formulaSchemeTitle, errorMessage);
        }
        return String.format("\u5bfc\u51fa\u4efb\u52a1\uff1a%s\uff0c\u62a5\u8868\u65b9\u6848\uff1a%s\uff0c\u516c\u5f0f\u65b9\u6848\uff1a%s \u7684\u51fa\u9519\u8bf4\u660e\u65f6\u53d1\u751f\u5f02\u5e38\u201c%s\u201d\u3002", taskTitle, formSchemeTitle, formulaSchemeTitle, errorMessage);
    }

    public static String doUploadMessage(int type, String message) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "Check upload. " : "\u68c0\u67e5\u4e0a\u62a5\u3002";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "Failed to submit. Please check whether the task submission is normal! " : "\u4e0a\u62a5\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u4efb\u52a1\u4e0a\u62a5\u662f\u5426\u6b63\u5e38\uff01";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "The dimensions for successful import data submission are: " : "\u5bfc\u5165\u6570\u636e\u4e0a\u62a5\u6210\u529f\u7684\u7ef4\u5ea6\u4e3a\uff1a";
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? "The dimension of failed submission is: " : "\u4e0a\u62a5\u5931\u8d25\u7684\u7ef4\u5ea6\u4e3a\uff1a";
        }
        if (type == 5) {
            return MultilingualLog.isEnglish() ? "The unit failed to report or failed to report due to data loading failure. " : "\u8be5\u5355\u4f4d\u56e0\u4e3a\u88c5\u5165\u6570\u636e\u5931\u8d25\u6ca1\u6709\u4e0a\u62a5\u6216\u8005\u4e0a\u62a5\u5931\u8d25\u3002";
        }
        if (type == 6) {
            return MultilingualLog.isEnglish() ? "Form under this company: %s failed to submit " : "\u8be5\u5355\u4f4d\u4e0b\u62a5\u8868\uff1a%s\u4e0a\u62a5\u5931\u8d25";
        }
        if (type == 7) {
            return MultilingualLog.isEnglish() ? "Form group under this company: %s failed to submit " : "\u8be5\u5355\u4f4d\u4e0b\u62a5\u8868\u5206\u7ec4\uff1a%s\u4e0a\u62a5\u5931\u8d25";
        }
        if (type == 8) {
            return MultilingualLog.isEnglish() ? "Due to a failure to load report data, there are no units under this task to submit. " : "\u56e0\u4e3a\u88c5\u5165\u62a5\u8868\u6570\u636e\u5931\u8d25\uff0c\u5bfc\u81f4\u8be5\u4efb\u52a1\u4e0b\u6ca1\u6709\u5355\u4f4d\u8fdb\u884c\u4e0a\u62a5\u3002";
        }
        if (type == 9) {
            return MultilingualLog.isEnglish() ? "The company has already reported or failed to report due to an error loading data. " : "\u8be5\u5355\u4f4d\u5df2\u4e0a\u62a5\u6216\u8005\u56e0\u4e3a\u88c5\u5165\u6570\u636e\u51fa\u9519\u800c\u6ca1\u6709\u4e0a\u62a5\u3002";
        }
        if (type == 10) {
            return MultilingualLog.isEnglish() ? "The process object is not enabled and cannot be submitted. " : "\u6d41\u7a0b\u5bf9\u8c61\u6ca1\u6709\u5f00\u542f\uff0c\u65e0\u6cd5\u8fdb\u884c\u4e0a\u62a5\u3002";
        }
        return "";
    }

    public static String analysisParamMessage(int type, String message1, String message2, String message3, String message4) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? String.format("The task in the multi-level deployment import file %s does not exist in the current system, synchronization ended! ", message1) : String.format("\u591a\u7ea7\u90e8\u7f72\u5bfc\u5165\u6587\u4ef6\u4e2d\u7684\u4efb\u52a1:%s\u5728\u5f53\u524d\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728\uff0c\u540c\u6b65\u7ed3\u675f\uff01", message1);
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? String.format("An exception occurred while querying the associated report scheme under task %s [%s]'s period %s! ", message1, message2, message3) : String.format("\u67e5\u8be2\u4efb\u52a1:%s[%s]\u7684\u65f6\u671f:%s\u4e0b\u5173\u8054\u7684\u62a5\u8868\u65b9\u6848\u65f6\uff0c\u53d1\u751f\u5f02\u5e38", message1, message2, message3);
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? String.format("The multi-level deployment task %s has a different form scheme %s associated with the period: %s than the report scheme %s recorded in the parameter package. Synchronization ended! ", message1, message3, message2, message4) : String.format("\u591a\u7ea7\u90e8\u7f72\u4efb\u52a1%s\u5728\u65f6\u671f:%s\u4e0b\u5173\u8054\u7684\u62a5\u8868\u65b9\u6848%s\u4e0e\u53c2\u6570\u5305\u8bb0\u5f55\u7684\u62a5\u8868\u65b9\u6848%s\u4e0d\u4e00\u6837\uff0c\u540c\u6b65\u7ed3\u675f\uff01", message1, message2, message3, message4);
        }
        return "";
    }

    public static String queryProcessMessage(int type) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "Synchronization query failed to obtain the superior user. Please check the superior service network, synchronization completed! " : "\u540c\u6b65\u67e5\u8be2\u65e0\u6cd5\u83b7\u53d6\u4e0a\u7ea7\u7528\u6237\uff0c\u8bf7\u68c0\u67e5\u4e0a\u7ea7\u670d\u52a1\u7f51\u7edc\uff0c\u540c\u6b65\u7ed3\u675f\uff01";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "The current user does not have permission to upload data! " : "\u5f53\u524d\u7528\u6237\u6ca1\u6709\u6743\u9650\u4e0a\u4f20\u6570\u636e\uff01";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "Synchronization failed, The superior service ended abnormally! " : "\u540c\u6b65\u5931\u8d25\uff0c\u4e0a\u7ea7\u670d\u52a1\u5f02\u5e38\u7ed3\u675f\uff01";
        }
        return "";
    }

    public static String reportParamServiceMessage(int type) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "User List" : "\u7528\u6237\u5217\u8868";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "User List" : "\u7528\u6237\u5217\u8868";
        }
        return "";
    }

    public static String reportParamServiceMessage1(String message1, String message2, String message3, String message4, String message5) {
        StringBuilder sbs = new StringBuilder();
        if (MultilingualLog.isEnglish()) {
            new StringBuilder("Scheme name\uff1a").append(message1);
            sbs.append("\uff0ctask name\uff1a").append(message2);
            sbs.append("\uff0cperiod\uff1a").append(message3);
            sbs.append("\uff0cunit range\uff1a").append(message4);
            sbs.append("form range\uff1a").append(message5);
            sbs.append("synchronize content\uff1a").append("\u201cform data\u201d\u3001\u201cparameter description\u201d");
        } else {
            new StringBuilder("\u65b9\u6848\u540d\u79f0\uff1a").append(message1);
            sbs.append("\uff0c\u4efb\u52a1\uff1a").append(message2);
            sbs.append("\uff0c\u65f6\u671f\uff1a").append(message3);
            sbs.append("\uff0c\u5355\u4f4d\u8303\u56f4\uff1a").append(message4);
            sbs.append("\u62a5\u8868\u8303\u56f4\uff1a").append(message5);
            sbs.append("\u540c\u6b65\u5185\u5bb9\uff1a").append("\u201c\u62a5\u8868\u6570\u636e\u201d\u3001\u201c\u53c2\u6570\u8bf4\u660e\u201d");
        }
        return sbs.toString();
    }

    public static String schemeGroupServiceMessage(int type) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "When searching for a group, the group keyword is empty! " : "\u641c\u7d22\u5206\u7ec4\u65f6\u5019\u5206\u7ec4\u5173\u952e\u5b57\u4e3a\u7a7a\uff01";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "Group name cannot be empty! " : "\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff01";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "Failed to add group! " : "\u6dfb\u52a0\u5206\u7ec4\u5931\u8d25\uff01";
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? "When deleting a group, the group keyword is empty! " : "\u5220\u9664\u5206\u7ec4\u65f6\u5019\u5206\u7ec4\u5173\u952e\u5b57\u4e3a\u7a7a\uff01";
        }
        if (type == 5) {
            return MultilingualLog.isEnglish() ? "All scheme groupings cannot be deleted! " : "\u5168\u90e8\u65b9\u6848\u5206\u7ec4\u4e0d\u53ef\u4ee5\u5220\u9664\uff01";
        }
        if (type == 6) {
            return MultilingualLog.isEnglish() ? "Failed to delete group! " : "\u5220\u9664\u5206\u7ec4\u5931\u8d25\uff01";
        }
        if (type == 7) {
            return MultilingualLog.isEnglish() ? "Grouping superiors cannot be set as themselves! " : "\u5206\u7ec4\u4e0a\u7ea7\u4e0d\u80fd\u8bbe\u7f6e\u4e3a\u81ea\u5df1\uff01";
        }
        return "";
    }

    public static String WorkflowTransmissionDataGatherMessage(int type) {
        if (type == 1) {
            return MultilingualLog.isEnglish() ? "An exception was thrown when exporting the process data" : "\u5bfc\u51fa\u6d41\u7a0b\u6570\u636e\u65f6\u629b\u51fa\u4e86\u5f02\u5e38";
        }
        if (type == 2) {
            return MultilingualLog.isEnglish() ? "An exception was thrown when importing process data" : "\u5bfc\u5165\u6d41\u7a0b\u6570\u636e\u65f6\u629b\u51fa\u4e86\u5f02\u5e38";
        }
        if (type == 3) {
            return MultilingualLog.isEnglish() ? "When importing, the process parameter data is empty!" : "\u5bfc\u5165\u65f6,\u6d41\u7a0b\u53c2\u6570\u6570\u636e\u4e3a\u7a7a\uff01";
        }
        if (type == 4) {
            return MultilingualLog.isEnglish() ? "When importing process data, the file is parsed as empty!" : "\u5bfc\u5165\u6d41\u7a0b\u6570\u636e\u65f6\uff0c\u6587\u4ef6\u89e3\u6790\u4e3a\u7a7a\uff01";
        }
        if (type == 5) {
            return MultilingualLog.isEnglish() ? "The process data import was successful!" : "\u6d41\u7a0b\u6570\u636e\u5bfc\u5165\u6210\u529f!";
        }
        if (type == 6) {
            return MultilingualLog.isEnglish() ? "The process data export was successful!" : "\u6d41\u7a0b\u6570\u636e\u5bfc\u51fa\u6210\u529f!";
        }
        return "";
    }
}


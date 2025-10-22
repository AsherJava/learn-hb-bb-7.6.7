/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.nr.dataentry.options;

import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import java.util.ArrayList;
import java.util.List;

public class AsyncTaskQueueDeclare
extends BaseNormalOptionDeclare {
    public String getId() {
        return "async-task-queue-declare";
    }

    public String getTitle() {
        return "\u5f02\u6b65\u4efb\u52a1\u6392\u961f\u6570";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_NODECHECK.getName());
            }

            public String getTitle() {
                return "\u6279\u91cf\u8282\u70b9\u68c0\u67e5\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_UPLOADFILE.getName());
            }

            public String getTitle() {
                return "\u6570\u636e\u5bfc\u5165\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_BATCHCALCULATE.getName());
            }

            public String getTitle() {
                return "\u6279\u91cf\u8fd0\u7b97\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_BATCHCHECK.getName());
            }

            public String getTitle() {
                return "\u6279\u91cf\u5ba1\u6838\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_BATCHDATASUM.getName());
            }

            public String getTitle() {
                return "\u6279\u91cf\u6c47\u603b\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_BATCHCLEAR.getName());
            }

            public String getTitle() {
                return "\u6279\u91cf\u6e05\u9664\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_BATCHCOPY.getName());
            }

            public String getTitle() {
                return "\u6279\u91cf\u590d\u5236\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_BATCHDATAPUBLISH.getName());
            }

            public String getTitle() {
                return "\u6570\u636e\u53d1\u5e03\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_BATCH_FORM_LOCK.getName());
            }

            public String getTitle() {
                return "\u6279\u91cf\u9501\u5b9a\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_ALLCALCULATE.getName());
            }

            public String getTitle() {
                return "\u5168\u7b97\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_ALLCHECK.getName());
            }

            public String getTitle() {
                return "\u5168\u5ba1\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_BATCHEFDC.getName());
            }

            public String getTitle() {
                return "\u6279\u91cfEFDC\u53d6\u6570\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return AsyncTaskQueueDeclare.this.getPrefixId(AsynctaskPoolType.ASYNCTASK_EFDC.getName());
            }

            public String getTitle() {
                return "EFDC\u53d6\u6570\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "100";
            }

            public String getVerifyRegex() {
                return "^\\d+$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
            }
        });
        return optionItems;
    }

    public int getOrdinal() {
        return 8;
    }

    private String getPrefixId(String taskPoolType) {
        return "QUEUE_".concat(taskPoolType);
    }
}


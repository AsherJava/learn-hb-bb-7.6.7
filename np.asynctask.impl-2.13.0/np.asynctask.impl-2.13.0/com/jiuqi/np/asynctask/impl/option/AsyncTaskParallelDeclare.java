/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskTypeCollecter
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.np.asynctask.impl.option;

import com.jiuqi.np.asynctask.AsyncTaskTypeCollecter;
import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class AsyncTaskParallelDeclare
extends BaseNormalOptionDeclare {
    @Autowired
    private AsyncTaskTypeCollecter asyncTaskTypeCollecter;

    public String getId() {
        return "async-task-parallel-declare";
    }

    public String getTitle() {
        return "\u5f02\u6b65\u4efb\u52a1\u5e76\u53d1\u6570";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        List taskToolTypeList = this.asyncTaskTypeCollecter.getTaskToolTypeList();
        for (final String taskToolType : taskToolTypeList) {
            if (!this.asyncTaskTypeCollecter.isConfig(taskToolType)) continue;
            optionItems.add((ISystemOptionItem)new AbstractInputOption(){

                public String getId() {
                    return taskToolType;
                }

                public String getTitle() {
                    return AsyncTaskParallelDeclare.this.asyncTaskTypeCollecter.getTitle(taskToolType);
                }

                public String getDefaultValue() {
                    return Integer.toString(AsyncTaskParallelDeclare.this.asyncTaskTypeCollecter.getParallelSize(taskToolType));
                }

                public String getVerifyRegex() {
                    return "^\\d+$";
                }

                public String getVerifyRegexMessage() {
                    return "\u8bf7\u8f93\u5165\u975e\u8d1f\u6574\u6570";
                }
            });
        }
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return "asynctask-max-parallel-size";
            }

            public String getTitle() {
                return "\u5176\u4ed6\u7c7b\u578b\u6700\u5927\u5e76\u53d1\u6570";
            }

            public String getDefaultValue() {
                return "50";
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
}


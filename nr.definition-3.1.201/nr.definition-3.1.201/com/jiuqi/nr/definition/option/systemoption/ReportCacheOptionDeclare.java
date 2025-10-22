/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.definition.option.systemoption;

import com.jiuqi.nr.definition.option.common.ReportCacheOptionType;
import com.jiuqi.nr.definition.option.dao.ReportCacheOptionDao;
import com.jiuqi.nr.definition.option.systemoperator.ReportCacheOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportCacheOptionDeclare
implements ISystemOptionDeclare {
    @Autowired
    private ReportCacheOptionOperator reportCacheOptionOperator;
    @Autowired
    private ReportCacheOptionDao cacheOptionDao;

    public String getId() {
        return "report-cache-option";
    }

    public String getTitle() {
        return "\u62a5\u8868\u7f13\u5b58";
    }

    public int getOrdinal() {
        return 2;
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public String getPluginName() {
        return "reportCachePlugin";
    }

    public boolean disableReset() {
        return true;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "report_cache_hot_update";
            }

            public String getTitle() {
                return "\u53d1\u5e03\u4e2d\u5237\u65b0\u7f13\u5b58";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.TRUE_FALSE;
            }

            public String getDefaultValue() {
                return "0";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "report_cache_expire_switch";
            }

            public String getTitle() {
                return "\u7f13\u5b58\u81ea\u52a8\u8fc7\u671f";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.TRUE_FALSE;
            }

            public String getDefaultValue() {
                return "0";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "report_cache_percent";
            }

            public String getTitle() {
                return "\u7f13\u5b58\u6700\u5927\u5360\u6bd4";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.INPUT;
            }

            public String getDefaultValue() {
                return "50";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "report_cache_expiration_time";
            }

            public String getTitle() {
                return "\u8fc7\u671f\u65f6\u95f4";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.INPUT;
            }

            public String getDefaultValue() {
                return "24";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "report_cache_mem_threshold_switch";
            }

            public String getTitle() {
                return "\u6309 JVM \u5806\u5185\u5b58\u4f7f\u7528\u7387\u5f3a\u5236\u8fc7\u671f";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.TRUE_FALSE;
            }

            public String getDefaultValue() {
                return "0";
            }
        });
        SystemOptionItem item = new SystemOptionItem();
        item.setId("report_cache_permanent_detail");
        item.setTitle("\u5e38\u9a7b\u7f13\u5b58\u89c4\u5219");
        item.setValue(this.reportCacheOptionOperator.getRules(this.cacheOptionDao, ReportCacheOptionType.CACHE_EXPIRATION));
        optionItems.add((ISystemOptionItem)item);
        item = new SystemOptionItem();
        item.setId("report_cache_preload_detail");
        item.setTitle("\u7f13\u5b58\u9884\u70ed\u89c4\u5219");
        item.setValue(this.reportCacheOptionOperator.getRules(this.cacheOptionDao, ReportCacheOptionType.CACHE_PRELOAD));
        optionItems.add((ISystemOptionItem)item);
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.reportCacheOptionOperator;
    }
}


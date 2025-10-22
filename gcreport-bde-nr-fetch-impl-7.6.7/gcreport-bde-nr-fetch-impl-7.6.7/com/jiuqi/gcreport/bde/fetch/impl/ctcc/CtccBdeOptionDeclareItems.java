/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.option.BdeOptionTypeEnum
 *  com.jiuqi.bde.common.option.IBdeOptionDeclareItems
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.gcreport.bde.fetch.impl.ctcc;

import com.jiuqi.bde.common.option.BdeOptionTypeEnum;
import com.jiuqi.bde.common.option.IBdeOptionDeclareItems;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CtccBdeOptionDeclareItems
implements IBdeOptionDeclareItems {
    public static final String FN_ETL_LOAD_UNIT_THRESHOLD = "ETL_LOAD_UNIT_THRESHOLD";

    public BdeOptionTypeEnum getOptionType() {
        return BdeOptionTypeEnum.OTHER;
    }

    public String getId() {
        return "bde-ctcc-load-option";
    }

    public String getTitle() {
        return "ETL\u52a0\u8f7d\u914d\u7f6e";
    }

    public List<ISystemOptionItem> getItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return CtccBdeOptionDeclareItems.FN_ETL_LOAD_UNIT_THRESHOLD;
            }

            public String getTitle() {
                return "\u8c03\u7528ETL\u5904\u7406\u6570\u636e\u62c6\u5206\u5355\u4f4d\u6570";
            }

            public String getDescribe() {
                return "\u591a\u5355\u4f4d\u6279\u91cf\u8c03\u7528ETL\u53d6\u6570\u65f6\uff0c\u4f1a\u62c6\u5206\u4e3a\u591a\u4e2a\u5b50\u4efb\u52a1\uff0c\u9650\u5236\u6bcf\u4e2a\u5b50\u4efb\u52a1\u7684\u5355\u4f4d\u6570\u91cf";
            }

            public String getDefaultValue() {
                return "200";
            }

            public String getVerifyRegex() {
                return "^[1-9]\\d*$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u6b63\u6574\u6570";
            }
        });
        return optionItems;
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}


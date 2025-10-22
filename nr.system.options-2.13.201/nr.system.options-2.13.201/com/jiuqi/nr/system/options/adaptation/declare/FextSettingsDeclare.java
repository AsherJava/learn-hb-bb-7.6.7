/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 */
package com.jiuqi.nr.system.options.adaptation.declare;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FextSettingsDeclare
extends BaseNormalOptionDeclare {
    public static final String DESIGNATED_ACCOUNTING_PERIOD = "DESIGNATED_ACCOUNTING_PERIOD";
    public static final String EFDC_GET_VALUE_MODIFY = "EFDC_GET_VALUE_MODIFY";
    public static final String EFDCURL = "EFDCURL";
    public static final String EFDCPIERCEURL = "EFDCPIERCEURL";
    public static final String PAY_MENT_ADJUST = "PAY_MENT_ADJUST";
    public static final String CONTAINS_ACCOUNTING_VOUCHERS = "CONTAINS_ACCOUNTING_VOUCHERS";
    public static final String DEFAULT_CONTAINS_ACCOUNTING_VOUCHERS = "DEFAULT_CONTAINS_ACCOUNTING_VOUCHERS";
    public static final String EFDC_PENETRAT_TYPE = "EFDC_PENETRAT_TYPE";
    public static final String EFDC_MAX_THREAD = "EFDC_MAX_THREAD";
    public static final String ID = "fext-settings-group";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "EFDC";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return FextSettingsDeclare.EFDC_GET_VALUE_MODIFY;
            }

            public String getTitle() {
                return "\u8d22\u52a1\u63d0\u53d6\u5355\u5143\u683c\u5141\u8bb8\u4fee\u6539";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return FextSettingsDeclare.EFDCURL;
            }

            public String getTitle() {
                return "EFDC\u5730\u5740";
            }

            @Override
            public String getVerifyRegex() {
                return "[a-zA-z]+://[^\\s]*";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u6b63\u786e\u7684URL";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return FextSettingsDeclare.EFDCPIERCEURL;
            }

            public String getTitle() {
                return "EFDC\u900f\u89c6\u5730\u5740";
            }

            @Override
            public String getVerifyRegex() {
                return "[a-zA-z]+://[^\\s]*";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u6b63\u786e\u7684URL";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return FextSettingsDeclare.DESIGNATED_ACCOUNTING_PERIOD;
            }

            public String getTitle() {
                return "\u542f\u7528\u6307\u5b9a\u4f1a\u8ba1\u671f\u95f4";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return FextSettingsDeclare.PAY_MENT_ADJUST;
            }

            public String getTitle() {
                return "\u542f\u7528\u8d26\u671f\u8c03\u6574";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return FextSettingsDeclare.CONTAINS_ACCOUNTING_VOUCHERS;
            }

            public String getTitle() {
                return "\u663e\u793a\u201c\u5305\u542b\u672a\u8bb0\u8d26\u51ed\u8bc1\u201d\u9009\u9879";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return FextSettingsDeclare.DEFAULT_CONTAINS_ACCOUNTING_VOUCHERS;
            }

            public String getTitle() {
                return "\u9ed8\u8ba4\u5305\u542b\u672a\u8bb0\u8d26\u51ed\u8bc1";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getId() {
                return FextSettingsDeclare.EFDC_PENETRAT_TYPE;
            }

            public String getTitle() {
                return "\u6570\u636e\u7a7f\u900f\u7c7b\u578b";
            }

            public String getDefaultValue() {
                return "0";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                return FextSettingsDeclare.this.getSystemOptionalValues();
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return FextSettingsDeclare.EFDC_MAX_THREAD;
            }

            public String getTitle() {
                return "EFDC\u4efb\u52a1\u6700\u5927\u7ebf\u7a0b\u6570";
            }

            @Override
            public String getVerifyRegex() {
                return "^[1-9]\\d*$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u5927\u4e8e0\u7684\u6b63\u6574\u6570";
            }
        });
        return optionItems;
    }

    private List<ISystemOptionalValue> getSystemOptionalValues() {
        ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u5408\u5e76\u7a7f\u900f";
            }

            public String getValue() {
                return "0";
            }
        });
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u8d44\u4ea7\u7a7f\u900f";
            }

            public String getValue() {
                return "1";
            }
        });
        return values;
    }

    @Override
    public int getOrdinal() {
        return 10;
    }
}


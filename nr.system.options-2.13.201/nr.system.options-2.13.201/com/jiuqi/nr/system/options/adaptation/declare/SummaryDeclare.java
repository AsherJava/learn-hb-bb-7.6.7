/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 */
package com.jiuqi.nr.system.options.adaptation.declare;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractCheckBoxOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SummaryDeclare
extends BaseNormalOptionDeclare {
    public static final String SUMMARY_AFTER_UPLOAD = "SUMMARY_AFTER_UPLOAD";
    public static final String SELECT_DATASUM_SCOPE = "SELECT_DATASUM_SCOPE";
    public static final String SELECT_DATASUM_DEFAULT = "SELECT_DATASUM_DEFAULT";
    public static final String NODE_SUM_SUMDIRECTION_DEFAULT = "NODE_SUM_SUMDIRECTION_DEFAULT";
    public static final String CALCULATION_AFTER_DATASUM = "CALCULATION_AFTER_DATASUM";
    public static final String ID = "sum-upload-group";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u6c47\u603b";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return SummaryDeclare.SUMMARY_AFTER_UPLOAD;
            }

            public String getTitle() {
                return "\u4ec5\u6c47\u603b\u5df2\u4e0a\u62a5\u5355\u4f4d";
            }
        });
        optionItems.add(new AbstractCheckBoxOption(){

            public String getId() {
                return SummaryDeclare.SELECT_DATASUM_SCOPE;
            }

            public String getTitle() {
                return "\u8282\u70b9\u6c47\u603b\u8303\u56f4\u663e\u793a";
            }

            @Override
            public String getDefaultValue() {
                return "[\"0\",\"1\"]";
            }

            @Override
            public List<ISystemOptionalValue> getOptionalValues() {
                return SummaryDeclare.this.getSystemOptionalValues();
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getId() {
                return SummaryDeclare.SELECT_DATASUM_DEFAULT;
            }

            public String getTitle() {
                return "\u8282\u70b9\u6c47\u603b\u8303\u56f4\u9ed8\u8ba4\u9009\u4e2d";
            }

            public String getDefaultValue() {
                return "0";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                return SummaryDeclare.this.getSystemOptionalValues();
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getId() {
                return SummaryDeclare.NODE_SUM_SUMDIRECTION_DEFAULT;
            }

            public String getTitle() {
                return "\u8282\u70b9\u6c47\u603b\u6c47\u603b\u65b9\u5411\u9ed8\u8ba4\u503c";
            }

            public String getDefaultValue() {
                return "0";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                return SummaryDeclare.this.getSystemOptionalSumDirectionValues();
            }
        });
        return optionItems;
    }

    private List<ISystemOptionalValue> getSystemOptionalValues() {
        ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u76f4\u63a5\u4e0b\u7ea7";
            }

            public String getValue() {
                return "0";
            }
        });
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u6240\u6709\u4e0b\u7ea7";
            }

            public String getValue() {
                return "1";
            }
        });
        return values;
    }

    private List<ISystemOptionalValue> getSystemOptionalSumDirectionValues() {
        ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u6c47\u603b\u81f3\u96c6\u56e2";
            }

            public String getValue() {
                return "0";
            }
        });
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u8c03\u6574\u81f3\u5dee\u989d\u8868";
            }

            public String getValue() {
                return "1";
            }
        });
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u65e0";
            }

            public String getValue() {
                return "2";
            }
        });
        return values;
    }

    @Override
    public int getOrdinal() {
        return 4;
    }
}


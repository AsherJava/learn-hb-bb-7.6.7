/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.system.options.adaptation.declare;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.operator.OtherOptionOperator;
import com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OtherDeclare
extends BaseNormalOptionDeclare {
    public static final String NET_SERVER_CODE = "NET_SERVER_CODE";
    public static final String IS_SET_DEFAULT_DIMENSION_VALUE = "IS_SET_DEFAULT_DIMENSION_VALUE";
    public static final String ID = "other-group";
    public static final String GRID_LINE_SPACE = "GRID_LINE_SPACE";
    @Autowired
    private OtherOptionOperator otherOptionOperator;

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u5176\u4ed6";
    }

    @Override
    public ISystemOptionOperator getSystemOptionOperator() {
        return this.otherOptionOperator;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return OtherDeclare.NET_SERVER_CODE;
            }

            public String getTitle() {
                return "\u670d\u52a1\u8bc6\u522b\u7801";
            }

            @Override
            public String getDefaultValue() {
                return "";
            }

            @Override
            public String getVerifyRegex() {
                return "^.{1,5}$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u673a\u5668\u7801\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc75\u4f4d";
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getTitle() {
                return "\u4e0a\u62a5\u662f\u5426\u91c7\u7528\u9ed8\u8ba4\u7ef4\u5ea6\u503c";
            }

            public String getId() {
                return OtherDeclare.IS_SET_DEFAULT_DIMENSION_VALUE;
            }

            public String getDefaultValue() {
                return "0";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                return OtherDeclare.this.getSystemOptionalValues();
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return OtherDeclare.GRID_LINE_SPACE;
            }

            public String getTitle() {
                return "\u8868\u683c\u6587\u5b57\u884c\u95f4\u8ddd";
            }

            public String getDefaultValue() {
                return "0";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.NUMBER_INPUT;
            }

            public String getDescribe() {
                return "\u5df2\u4fdd\u5b58\u7684\u6253\u5370\u6a21\u677f\u9700\u8981\u5728\u9875\u9762\u8bbe\u8ba1\u4e2d\u70b9\u51fb\u91cd\u65b0\u83b7\u53d6\u8868\u6837";
            }

            public String getVerifyRegex() {
                return "^(?:100|[1-9]\\d|\\d)$";
            }

            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u51650~100\u7684\u6574\u6570";
            }
        });
        return optionItems;
    }

    private List<ISystemOptionalValue> getSystemOptionalValues() {
        ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u5426";
            }

            public String getValue() {
                return "0";
            }
        });
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u662f";
            }

            public String getValue() {
                return "1";
            }
        });
        return values;
    }

    @Override
    public int getOrdinal() {
        return 11;
    }
}


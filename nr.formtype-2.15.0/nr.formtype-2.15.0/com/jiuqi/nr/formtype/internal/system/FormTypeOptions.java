/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionTip
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.formtype.internal.system;

import com.jiuqi.nr.formtype.internal.system.FormTypeOpptionOperator;
import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionTip;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormTypeOptions
extends BaseNormalOptionDeclare {
    @Autowired
    private FormTypeOpptionOperator formTypeOpptionOperator;

    public String getId() {
        return "form_type_option_id";
    }

    public String getTitle() {
        return "\u62a5\u8868\u7c7b\u578b";
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.formTypeOpptionOperator;
    }

    public boolean disableReset() {
        return true;
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "formtype_option_gzwms";
            }

            public String getTitle() {
                return "\u7ba1\u7406\u6a21\u5f0f";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.RADIO_BUTTON;
            }

            public String getDefaultValue() {
                return "0";
            }

            public ISystemOptionTip getTip() {
                return new ISystemOptionTip(){

                    public boolean isTips() {
                        return true;
                    }

                    public String getTipHtml() {
                        return "\u56fd\u8d44\u59d4\u6a21\u5f0f\u4e0d\u517c\u5bb9\u6807\u51c6\u6a21\u5f0f\u4e0b\u7684\u5dee\u989d\u548c\u672c\u90e8\u5355\u4f4d\uff0c\u9700\u8981\u624b\u52a8\u5220\u9664\u540e\u91cd\u65b0\u751f\u6210\uff1b\u5e76\u4e14\u542f\u7528\u56fd\u8d44\u59d4\u6a21\u5f0f\u540e\u65e0\u6cd5\u53d6\u6d88\uff0c\u662f\u5426\u7ee7\u7eed?";
                    }

                    public String getConfirmMsg() {
                        return "\u7ee7\u7eed";
                    }
                };
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> options = new ArrayList<ISystemOptionalValue>();
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u6807\u51c6\u6a21\u5f0f";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u56fd\u8d44\u59d4\u6a21\u5f0f";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                return options;
            }

            public String getDescribe() {
                return "\u6807\u51c6\u6a21\u5f0f\uff1a\u624b\u52a8\u7ba1\u7406\u672c\u90e8\u548c\u5dee\u989d\u5355\u4f4d\uff1b\u56fd\u8d44\u59d4\u6a21\u5f0f\uff1a\u521b\u5efa\u96c6\u56e2\u6c47\u603b\u5355\u4f4d\u65f6\u81ea\u52a8\u521b\u5efa\u672c\u90e8\u548c\u5dee\u989d\u5355\u4f4d\u3002";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "formtype_option_iconscheme";
            }

            public String getTitle() {
                return "\u7cfb\u7edf\u56fe\u6807\u65b9\u6848";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.RADIO_BUTTON;
            }

            public String getDefaultValue() {
                return "0";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> options = new ArrayList<ISystemOptionalValue>();
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u9ed8\u8ba4\u65b9\u6848";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u7ecf\u5178\u65b9\u6848";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                return options;
            }

            public String getDescribe() {
                return "\u62a5\u8868\u7c7b\u578b\u7ba1\u7406\u4e2d\uff0c\u6ca1\u6709\u4e0a\u4f20\u56fe\u6807\u7684\u6570\u636e\u9879\u4f7f\u7528\u7cfb\u7edf\u56fe\u6807\u3002";
            }
        });
        return optionItems;
    }

    public String getPluginName() {
        return "reportTypePlugin";
    }
}


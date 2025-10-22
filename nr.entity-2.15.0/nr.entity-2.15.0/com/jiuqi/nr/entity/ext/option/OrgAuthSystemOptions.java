/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 */
package com.jiuqi.nr.entity.ext.option;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import java.util.ArrayList;
import java.util.List;

public class OrgAuthSystemOptions
extends BaseNormalOptionDeclare {
    public static final String ORG_AUTH_EXT = "ORG_AUTH_EXT";
    public static final String INDEPENDENT_ORG_EXT_ENABLE = "INDEPENDENT_ORG_EXT_ENABLE";
    public static final String INDEPENDENT_ORG_BASIS = "INDEPENDENT_ORG_BASIS";
    public static final String DEFAULT_BBLX_CODE = "DEFAULT_BBLX_CODE";
    public static final String ORG_IDC_ENABLE = "ORG_IDC_ENABLE";
    public static final String ORG_IDC_LENGTH = "ORG_IDC_LENGTH";
    public static final String ORG_DB_MODE = "ORG_DB_MODE";

    public String getId() {
        return ORG_AUTH_EXT;
    }

    public String getTitle() {
        return "\u7ec4\u7ec7\u673a\u6784\u6269\u5c55";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> systemOptionItems = new ArrayList<ISystemOptionItem>();
        systemOptionItems.add((ISystemOptionItem)new AbstractTrueFalseOption(){

            public String getId() {
                return OrgAuthSystemOptions.INDEPENDENT_ORG_EXT_ENABLE;
            }

            public String getTitle() {
                return "\u5355\u6237\u6a21\u5f0f";
            }

            public String getDefaultValue() {
                return "0";
            }
        });
        systemOptionItems.add((ISystemOptionItem)new AbstractRadioOption(){

            public String getId() {
                return OrgAuthSystemOptions.INDEPENDENT_ORG_BASIS;
            }

            public String getTitle() {
                return "\u5355\u6237\u7528\u6237\u5224\u65ad\u4f9d\u636e";
            }

            public String getDefaultValue() {
                return "0";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u7528\u6237\u6240\u5c5e\u673a\u6784\u7684\u62a5\u8868\u7c7b\u578b\u662f\u5355\u6237";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u7528\u6237\u6240\u5c5e\u673a\u6784\u672b\u4f4d\u7f16\u7801\u4e3a\"0\"";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                return values;
            }
        });
        systemOptionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return OrgAuthSystemOptions.DEFAULT_BBLX_CODE;
            }

            public String getTitle() {
                return "\u62a5\u8868\u7c7b\u578b\u5355\u6237\u6807\u8bc6";
            }

            public String getDefaultValue() {
                return "0";
            }
        });
        systemOptionItems.add((ISystemOptionItem)new AbstractTrueFalseOption(){

            public String getId() {
                return OrgAuthSystemOptions.ORG_IDC_ENABLE;
            }

            public String getTitle() {
                return "\u673a\u6784\u7f16\u7801IDC\u6821\u9a8c";
            }

            public String getDefaultValue() {
                return "0";
            }

            public String getDescribe() {
                return "\u5f00\u542f\u540e\uff0c\u53ef\u5bf9\u7ec4\u7ec7\u673a\u6784\u8fdb\u884c\u5355\u4f4d\u4ee3\u7801\u6821\u9a8c";
            }
        });
        systemOptionItems.add((ISystemOptionItem)new AbstractRadioOption(){

            public String getId() {
                return OrgAuthSystemOptions.ORG_IDC_LENGTH;
            }

            public String getTitle() {
                return "\u673a\u6784\u7f16\u7801\u6a21\u5f0f";
            }

            public String getDefaultValue() {
                return "1";
            }

            public String getDescribe() {
                return "\u5f00\u542f\u2018\u673a\u6784\u7f16\u7801IDC\u6821\u9a8c\u2019\u540e\uff0c\u5f53\u524d\u6a21\u5f0f\u914d\u7f6e\u624d\u751f\u6548\u3002";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "16\u4f4d\u6a21\u5f0f";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "8\u4f4d\u6a21\u5f0f";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                return values;
            }
        });
        systemOptionItems.add((ISystemOptionItem)new AbstractTrueFalseOption(){

            public String getId() {
                return OrgAuthSystemOptions.ORG_DB_MODE;
            }

            public String getTitle() {
                return "\u542f\u7528\u975e\u6d3b\u8dc3\u673a\u6784\u4f18\u5316\u67e5\u8be2";
            }

            public String getDefaultValue() {
                return "0";
            }

            public String getDescribe() {
                return "\u5f00\u542f\u540e\uff0c\u7cfb\u7edf\u5185\u6240\u6709\u7ec4\u7ec7\u673a\u6784\u7684\u975e\u6d3b\u8dc3\u7248\u672c\u7684\u6570\u636e\u67e5\u8be2\u4f1a\u901a\u8fc7\u6570\u636e\u5e93\u67e5\u8be2\u8fdb\u884c\u4f18\u5316\uff0c\u4e14\u4e0d\u4f1a\u8fdb\u884c\u516c\u5f0f\u8fc7\u6ee4";
            }
        });
        return systemOptionItems;
    }
}


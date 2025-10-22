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
package nr.single.para.compare.internal.system;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionTip;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import nr.single.para.compare.internal.system.SingleParaOpptionOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingleParaOptions
extends BaseNormalOptionDeclare {
    @Autowired
    private SingleParaOpptionOperator paraOpptionOperator;

    public String getId() {
        return "single_para_option_id";
    }

    public String getTitle() {
        return "JIO";
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.paraOpptionOperator;
    }

    public boolean disableReset() {
        return true;
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.NORMAL;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "single_para_upload_group_id";
            }

            public String getTitle() {
                return "JIO\u53c2\u6570\u4e0a\u4f20";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.GROUP;
            }

            public String getDefaultValue() {
                return "0";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "singlepara_option_taskfindmode";
            }

            public String getTitle() {
                return "\u4efb\u52a1\u5339\u914d\u6a21\u5f0f";
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
                        return "\u5207\u6362\u5c06\u4f1a\u5f71\u54cdJIO\u53c2\u6570\u5bfc\u5165\uff0c\u662f\u5426\u7ee7\u7eed?";
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
                        return "\u6807\u51c6\u6a21\u5f0f(\u6839\u636e\u5355\u673a\u7248\u4efb\u52a1\u6807\u8bc6\u5339\u914d\u62a5\u8868\u65b9\u6848\uff1b\u53bb\u6570\u5b57\u540e\u5339\u914d\u4efb\u52a1)";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u53ef\u5207\u6362\u6a21\u5f0f(\u53ef\u5207\u6362\u4efb\u52a1\uff0c\u62a5\u8868\u65b9\u6848)";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u6309\u5e74\u5ea6\u6a21\u5f0f(\u53ef\u5207\u6362\u4efb\u52a1\uff0c\u6309\u5e74\u5ea6\u5339\u914d\u62a5\u8868\u65b9\u6848)";
                    }

                    public String getValue() {
                        return "2";
                    }
                });
                return options;
            }

            public String getDescribe() {
                return "\u6807\u51c6\u6a21\u5f0f\uff0c\u4e0d\u53ef\u5207\u6362\u4efb\u52a1\u548c\u62a5\u8868\u65b9\u6848\uff1b\u5207\u6362\u6a21\u5f0f\u53ef\u5207\u6362\u4efb\u52a1\u548c\u62a5\u8868\u65b9\u6848\uff1b\u5e74\u5ea6\u6a21\u5f0f\u53ef\u5207\u6362\u4efb\u52a1\u3001\u6309\u5e74\u5ea6\u5339\u914d\u62a5\u8868\u65b9\u6848\u3002";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "singlepara_option_parafindmode";
            }

            public String getTitle() {
                return "\u6307\u6807\u9ed8\u8ba4\u5339\u914d\u6a21\u5f0f";
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
                        return "\u6807\u51c6\u6a21\u5f0f(\u6309\u5339\u914d\u540d\u79f0\u5339\u914d)";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u6309\u6807\u8bc6\u5339\u914d";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u6309ini\u6620\u5c04\u5339\u914d";
                    }

                    public String getValue() {
                        return "2";
                    }
                });
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u6309\u5168\u5c40\u6807\u8bc6\u5339\u914d";
                    }

                    public String getValue() {
                        return "3";
                    }
                });
                return options;
            }

            public String getDescribe() {
                return "\u6307\u6807\u5339\u914d\u6a21\u5f0f\uff0c\u6807\u51c6\u6a21\u5f0f\uff08\u5148\u540d\u79f0\u540e\u6807\u8bc6\uff09\u5339\u914d\uff1b\u53ea\u6309\u6807\u8bc6\u5339\u914d\uff0c\u5c06\u6309\u6807\u8bc6\u8fdb\u884c\u8986\u76d6\u548c\u65b0\u589e\uff0c\u4ec5\u7528\u4e8e\u6307\u6807\u3002";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "singlepara_option_enumitemmode";
            }

            public String getTitle() {
                return "\u679a\u4e3e\u9879\u9ed8\u8ba4\u5339\u914d\u6a21\u5f0f";
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
                        return "\u6807\u51c6\u6a21\u5f0f(\u5148\u540d\u79f0\u540e\u6807\u8bc6\u5339\u914d)";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u53ea\u6309\u6807\u8bc6\u5339\u914d";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                return options;
            }

            public String getDescribe() {
                return "\u679a\u4e3e\u9879\u5339\u914d\u6a21\u5f0f\uff0c\u5148\u540d\u79f0\u540e\u6807\u8bc6\u5339\u914d\uff1b\u53ea\u6309\u6807\u8bc6\u5339\u914d\uff0c\u5c06\u6309\u6807\u8bc6\u8fdb\u884c\u8986\u76d6\u548c\u65b0\u589e\u3002";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "single_data_group_id";
            }

            public String getTitle() {
                return "JIO\u6570\u636e\u4e0a\u4f20";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.GROUP;
            }

            public String getDefaultValue() {
                return "0";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "singledata_option_changeupper";
            }

            public String getTitle() {
                return "\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u81ea\u52a8\u8f6c\u4e3a\u5927\u5199";
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
                        return "\u5207\u6362\u5c06\u4f1a\u5f71\u54cdJIO\u6570\u636e\u5bfc\u5165\uff0c\u662f\u5426\u7ee7\u7eed?";
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
                        return "\u662f";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u5426";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                return options;
            }

            public String getDescribe() {
                return "";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "singledata_option_upload_thread_orgcount";
            }

            public String getTitle() {
                return "\u7ec4\u7ec7\u673a\u6784\u8fbe\u5230\u6307\u5b9a\u6570\u91cf\u65f6\u5f00\u542f\u5e76\u53d1\u4f18\u5316";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.DEFAULT;
            }

            public String getDefaultValue() {
                return "10000";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "singledata_option_upload_threadcount";
            }

            public String getTitle() {
                return "\u5f00\u542f\u5e76\u53d1\u4f18\u5316\u540e\u7684\u5e76\u53d1\u7ebf\u7a0b\u6570";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.DEFAULT;
            }

            public String getDefaultValue() {
                return "3";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "singledata_option_upload_checkzdm";
            }

            public String getTitle() {
                return "\u68c0\u67e5\u4e3b\u4ee3\u7801\u548c\u4e3b\u4ee3\u7801\u6784\u6210\u4e00\u81f4";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.RADIO_BUTTON;
            }

            public String getDefaultValue() {
                return "1";
            }

            public ISystemOptionTip getTip() {
                return new ISystemOptionTip(){

                    public boolean isTips() {
                        return true;
                    }

                    public String getTipHtml() {
                        return "\u5207\u6362\u5c06\u4f1a\u5f71\u54cdJIO\u6570\u636e\u5bfc\u5165\uff0c\u662f\u5426\u7ee7\u7eed?";
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
                        return "\u662f";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                options.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u5426";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                return options;
            }

            public String getDescribe() {
                return "";
            }
        });
        return optionItems;
    }
}


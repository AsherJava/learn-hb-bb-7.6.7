/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.IRelationSystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.RelationSystemOptionItem
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.system.options.adaptation.declare;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractDropDownOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.IRelationSystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.RelationSystemOptionItem;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ExportDeclare
extends BaseNormalOptionDeclare {
    public static final String SEPARATOR_CODE = "SEPARATOR_CODE";
    public static final String SHEET_NAME = "SHEET_NAME";
    public static final String EXCEL_NAME = "EXCEL_NAME";
    public static final String ZIP_NAME = "ZIP_NAME";
    public static final String BATCH_EXPORT_MAX_DW_NUM = "BATCH_EXPORT_MAX_DW_NUM";
    public static final String EXTENT_GRID_IS_NULL_TABLE = "EXTENT_GRID_IS_NULL_TABLE";
    public static final String ID = "nr-data-entry-export";
    public static final String SIMPLIFY_EXPORT_FILE_HIERARCHY = "SIMPLIFY_EXPORT_FILE_HIERARCHY";
    public static final String DEFAULT_EXPORT_NULL_TABLE = "DEFAULT_EXPORT_NULL_TABLE";
    public static final String EXPORT_EXCEL_DROPDOWN = "EXPORT_EXCEL_DROPDOWN";
    public static final String EXPORT_EXCEL_DROPDOWN_NUM = "EXPORT_EXCEL_DROPDOWN_NUM";
    public static final String EXPORT_EXCEL_MEM_LEVEL = "@nr/excel/EXPORT_EXCEL_MEM_LEVEL";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u5bfc\u51fa";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return ExportDeclare.SIMPLIFY_EXPORT_FILE_HIERARCHY;
            }

            public String getTitle() {
                return "\u7cbe\u7b80\u5bfc\u51fa\u6587\u4ef6\u5c42\u7ea7";
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getId() {
                return ExportDeclare.SEPARATOR_CODE;
            }

            public String getTitle() {
                return "\u5206\u9694\u7b26";
            }

            public String getDefaultValue() {
                return "0";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u7a7a\u683c";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "_";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "&";
                    }

                    public String getValue() {
                        return "2";
                    }
                });
                return values;
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            @Override
            public String getDefaultValue() {
                return "1";
            }

            public String getId() {
                return ExportDeclare.EXTENT_GRID_IS_NULL_TABLE;
            }

            public String getTitle() {
                return "\u81ea\u52a8\u586b\u5145\u5c5e\u4e8e\u7a7a\u8868";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            @Override
            public String getDefaultValue() {
                return "0";
            }

            public String getId() {
                return ExportDeclare.DEFAULT_EXPORT_NULL_TABLE;
            }

            public String getTitle() {
                return "\u6570\u636e\u5bfc\u51fa\u9ed8\u8ba4\u5bfc\u51fa\u7a7a\u8868";
            }
        });
        optionItems.add(new AbstractDropDownOption(){

            public String getId() {
                return ExportDeclare.SHEET_NAME;
            }

            public String getTitle() {
                return "sheet\u9875\u7b7e";
            }

            public String getDefaultValue() {
                return "[\"1\",\"0\"]";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u62a5\u8868\u540d\u79f0";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u62a5\u8868\u6807\u8bc6";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u62a5\u8868\u7f16\u53f7";
                    }

                    public String getValue() {
                        return "2";
                    }
                });
                return values;
            }
        });
        optionItems.add(new AbstractDropDownOption(){

            public String getId() {
                return ExportDeclare.EXCEL_NAME;
            }

            public String getTitle() {
                return "excel\u6587\u4ef6\u540d\u79f0";
            }

            public String getDefaultValue() {
                return "[\"0\"]";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u5355\u4f4d\u540d\u79f0";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u5355\u4f4d\u4ee3\u7801";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u62a5\u8868\u65f6\u671f";
                    }

                    public String getValue() {
                        return "2";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u4efb\u52a1\u540d\u79f0";
                    }

                    public String getValue() {
                        return "3";
                    }
                });
                return values;
            }
        });
        optionItems.add(new AbstractDropDownOption(){

            public String getId() {
                return ExportDeclare.ZIP_NAME;
            }

            public String getTitle() {
                return "\u538b\u7f29\u5305\u540d\u79f0";
            }

            public String getDefaultValue() {
                return "[\"0\",\"2\"]";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u4efb\u52a1\u540d\u79f0";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u4efb\u52a1\u6807\u8bc6";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u65f6\u95f4\u6233";
                    }

                    public String getValue() {
                        return "2";
                    }
                });
                return values;
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return ExportDeclare.BATCH_EXPORT_MAX_DW_NUM;
            }

            public String getTitle() {
                return "Excel\u5bfc\u51fa\u6700\u5927\u652f\u6301\u5355\u4f4d\u6570";
            }

            @Override
            public String getVerifyRegex() {
                return "^[0-9]*[1-9][0-9]*$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u5bfc\u51fa\u5355\u4f4d\u6570\u9700\u5927\u4e8e\u7b49\u4e8e1";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return ExportDeclare.EXPORT_EXCEL_DROPDOWN;
            }

            public String getTitle() {
                return "excel\u5bfc\u51fa\u751f\u6210\u4e0b\u62c9\u5217\u8868";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return ExportDeclare.EXPORT_EXCEL_DROPDOWN_NUM;
            }

            public IRelationSystemOptionItem getRelationSystemOptionItem() {
                RelationSystemOptionItem item = new RelationSystemOptionItem();
                item.setAvailableValue("1");
                item.setId(ExportDeclare.EXPORT_EXCEL_DROPDOWN);
                return item;
            }

            public String getTitle() {
                return "\u4e0b\u62c9\u5217\u8868\u6700\u5927\u4e2a\u6570";
            }

            @Override
            public String getVerifyRegex() {
                return "^[0-9]*[1-9][0-9]*$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u4e0b\u62c9\u5217\u8868\u6700\u5927\u4e2a\u6570\u9700\u5927\u4e8e\u7b49\u4e8e1";
            }

            @Override
            public String getDefaultValue() {
                return "1000";
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return ExportDeclare.EXPORT_EXCEL_MEM_LEVEL;
            }

            public String getTitle() {
                return "excel\u5bfc\u51fa\u6570\u636e\u7f13\u5b58\u5927\u5c0f\u7ea7\u522b";
            }

            public String getDefaultValue() {
                return "1";
            }

            public String getDescribe() {
                return "\u8be5\u914d\u7f6e\u67090~10\u517111\u4e2a\u7ea7\u522b\u3002\u9ed8\u8ba4\u503c\u4e3a1\uff0c\u662f\u5728\u57fa\u51c6\u6d4b\u8bd5\u4e2d\u5185\u5b58\u5360\u7528\u548c\u5bfc\u51fa\u8017\u65f6\u6574\u4f53\u8868\u73b0\u8f83\u5747\u8861\u7684\u914d\u7f6e\uff1b\u914d\u7f6e0\u65f6\u5c06\u4e0d\u542f\u7528\u6570\u636e\u7f13\u5b58\uff0c\u6570\u636e\u7f13\u5b58\u7684\u5185\u5b58\u5360\u7528\u6700\u5c0f\uff0c\u5bfc\u51fa\u8017\u65f6\u8f83\u9ad8\uff1b1\u5f80\u4e0a\u8d8a\u5927\uff0c\u6570\u636e\u7f13\u5b58\u7684\u5185\u5b58\u5360\u7528\u8d8a\u5927\uff0c\u5bfc\u51fa\u8017\u65f6\u4e00\u822c\u8f83\u4f4e\u3002";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.INPUT;
            }

            public String getVerifyRegex() {
                return "^(10|[0-9])$";
            }

            public String getVerifyRegexMessage() {
                return "excel\u5bfc\u51fa\u6570\u636e\u7f13\u5b58\u5927\u5c0f\u7ea7\u522b\u5e94\u662f0~10\u4e4b\u95f4\u7684\u81ea\u7136\u6570";
            }
        });
        return optionItems;
    }

    @Override
    public int getOrdinal() {
        return 1;
    }
}


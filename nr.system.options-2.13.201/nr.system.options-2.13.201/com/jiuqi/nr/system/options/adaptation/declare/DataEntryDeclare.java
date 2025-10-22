/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.IRelationSystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.RelationSystemOptionItem
 */
package com.jiuqi.nr.system.options.adaptation.declare;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractCheckBoxOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.IRelationSystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.RelationSystemOptionItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataEntryDeclare
extends BaseNormalOptionDeclare {
    public static final String DATA_PUBLISHING = "DATA_PUBLISHING";
    public static final String DATASUM_AFTER_UPLOAD = "DATASUM_AFTER_UPLOAD";
    public static final String JTABLE_FIELDINFO_SHOW = "JTABLE_FIELDINFO_SHOW";
    public static final String JTABLE_FIELDINFO_SHOW_INFO = "JTABLE_FIELDINFO_SHOW_INFO";
    public static final String SYS_TAG_PERIOD_DATA_SHOW = "SYS_TAG_PERIOD_DATA_SHOW";
    public static final String SYS_TAG_PERIOD_DATA_SHOW_INFO = "SYS_TAG_PERIOD_DATA_SHOW_INFO";
    public static final String MATCH_ROUND_OF_IMPORT = "MATCH_ROUND_OF_IMPORT";
    public static final String SYS_TAG_USER_ISOLATE = "SYS_TAG_USER_ISOLATE";
    public static final String JTABLE_CACHE_DATA_TIME = "JTABLE_CACHE_DATA_TIME";
    public static final String JTABLE_ENTITY_MATCH_ALL = "JTABLE_ENTITY_MATCH_ALL";
    public static final String JTABLE_LOCATE_FIRST_CELL = "JTABLE_LOCATE_FIRST_CELL";
    public static final String JTABLE_AUTOCALC_CELLCOLOR = "JTABLE_AUTOCALC_COLOR";
    public static final String JTABLE_EFDC_CELLCOLOR = "JTABLE_EFDC_COLOR";
    public static final String AUTO_CALC_IMPORT = "AUTOCALCULAT_AFTER_IMPORT";
    public static final String CALC_CHECK_EXE = "CALC_CHECK_EXE";
    public static final String EXPORT_EXE = "EXPORT_EXE";
    public static final String ASYNC_QUERY_SECONDS = "ASYNC_QUERY_SECONDS";
    public static final String QUICK_INPUT_MODE = "QUICK_INPUT_MODE";
    public static final String JTABLE_ENABLE_CALC_CELL_COLOR = "JTABLE_ENABLE_CALC_CELL_COLOR";
    public static final String JTABLE_ENABLE_EFDC_CELL_COLOR = "JTABLE_ENABLE_EFDC_CELL_COLOR";
    public static final String FILE_UPLOAD_WARN = "FILE_UPLOAD_WARN";
    public static final String SELECT_BATCH_EXPORT_EXCEL_THREAD = "SELECT_BATCH_EXPORT_EXCEL_THREAD";
    public static final String BATCH_EXPORT_THREAD_COUNT = "BATCH_EXPORT_THREAD_COUNT";
    public static final String IS_ENABLE_DATA_AUTH_OPTIONS = "IS_ENABLE_DATA_AUTH_OPTIONS";
    public static final String IS_PREVIEW_OPTIONS = "IS_PREVIEW_OPTIONS";
    public static final String MAX_PREVIEW_FILE = "MAX_PREVIEW_FILE";
    public static final String MAX_QUEUING_PREVIEW = "MAX_QUEUING_PREVIEW";
    public static final String QUEU_PREVIEW_TIP = "QUEU_PREVIEW_TIP";
    public static final String MAX_FIND_NUM = "MAX_FIND_NUM";
    public static final String TASK_DEPLOY_UNLOCK = "TASK_DEPLOY_UNLOCK";
    public static final String SINGLE_IMPORT_CHECK = "SINGLE_IMPORT_CHECK";
    public static final String EXCEL_IMPOTR_HORIZONTAL_BAR_HANDLE = "EXCEL_IMPOTR_HORIZONTAL_BAR_HANDLE";
    public static final String EXCEL_IMPOTR_HORIZONTAL_BAR_HANDLE_EMPTY = "empty";
    public static final String EXCEL_IMPOTR_HORIZONTAL_BAR_HANDLE_ERROR = "error";
    public static final String CUSTOMIMPORT_MDCODE_ANALYZE_RULE = "NR/CUSTOMIMPORT/MDCODE_ANALYZE_RULE";
    public static final String ID = "nr-data-entry-group";
    public static final String MEMORIZE_CHECKTYPE = "MEMORIZE_CHECKTYPE";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u5f55\u5165";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.DATA_PUBLISHING;
            }

            public String getTitle() {
                return "\u542f\u7528\u6570\u636e\u53d1\u5e03";
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getId() {
                return DataEntryDeclare.JTABLE_FIELDINFO_SHOW;
            }

            public String getTitle() {
                return "\u8868\u683c\u5355\u5143\u683c\u6307\u6807\u5c5e\u6027\u663e\u793a\u65b9\u5f0f";
            }

            public String getDefaultValue() {
                return "HORVER";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u53f3\u952e";
                    }

                    public String getValue() {
                        return "RIGHT";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u60ac\u6d6e";
                    }

                    public String getValue() {
                        return "HORVER";
                    }
                });
                return values;
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.SYS_TAG_USER_ISOLATE;
            }

            public String getTitle() {
                return "\u5355\u4f4d\u6811\u6807\u8bb0\u7ba1\u7406\u662f\u5426\u7528\u6237\u9694\u79bb";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.DATASUM_AFTER_UPLOAD;
            }

            public String getTitle() {
                return "\u6c47\u603b\u6240\u6709\u4e0b\u7ea7\u65f6\u8986\u76d6\u4e2d\u95f4\u5df2\u4e0a\u62a5\u5355\u4f4d\u6570\u636e";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.SELECT_BATCH_EXPORT_EXCEL_THREAD;
            }

            public String getTitle() {
                return "\u6279\u91cf\u5bfc\u51fa\u662f\u5426\u542f\u7528\u591a\u7ebf\u7a0b";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.MATCH_ROUND_OF_IMPORT;
            }

            public String getTitle() {
                return "\u5bfc\u5165\u6570\u636e\u662f\u5426\u8fdb\u884c\u56db\u820d\u4e94\u5165";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.JTABLE_CACHE_DATA_TIME;
            }

            public String getTitle() {
                return "\u662f\u5426\u542f\u7528\u65f6\u671f\u7f13\u5b58";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.JTABLE_ENTITY_MATCH_ALL;
            }

            public String getTitle() {
                return "\u5b9e\u4f53\u67e5\u8be2\u5728\u6570\u636e\u5bfc\u5165\u548c\u7c98\u8d34\u65f6\u5168\u8bcd\u5339\u914d";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.JTABLE_LOCATE_FIRST_CELL;
            }

            public String getTitle() {
                return "\u6253\u5f00\u62a5\u8868\u5b9a\u4f4d\u5230\u7b2c\u4e00\u4e2a\u53ef\u586b\u62a5\u5355\u5143\u683c";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.FILE_UPLOAD_WARN;
            }

            public String getTitle() {
                return "\u9644\u4ef6\u4e0a\u4f20\u662f\u5426\u663e\u793a\u8b66\u544a\u8bed\u53e5";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.QUICK_INPUT_MODE;
            }

            public String getTitle() {
                return "\u662f\u5426\u542f\u7528\u5feb\u6377\u4fdd\u5b58\u6a21\u5f0f";
            }

            @Override
            public String getDefaultValue() {
                return "0";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return DataEntryDeclare.BATCH_EXPORT_THREAD_COUNT;
            }

            public String getTitle() {
                return "\u6279\u91cf\u5bfc\u51fa\u542f\u7528\u7ebf\u7a0b\u6570";
            }

            @Override
            public String getVerifyRegex() {
                return "^(?:[1-9]|1[0-9]|20)$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u7ebf\u7a0b\u6570\u8303\u56f4\u4e3a1-20";
            }
        });
        optionItems.add(new AbstractCheckBoxOption(){

            public String getId() {
                return DataEntryDeclare.JTABLE_FIELDINFO_SHOW_INFO;
            }

            public String getTitle() {
                return "\u6307\u6807\u8be6\u60c5\u5c55\u793a\u4fe1\u606f";
            }

            @Override
            public String getDefaultValue() {
                return "[\"0\",\"1\",\"2\"]";
            }

            @Override
            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> optionItems = new ArrayList<ISystemOptionalValue>();
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u6307\u6807\u4ee3\u7801";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u6307\u6807\u540d\u79f0";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u6307\u6807\u8bf4\u660e";
                    }

                    public String getValue() {
                        return "2";
                    }
                });
                return optionItems;
            }
        });
        optionItems.add(new AbstractCheckBoxOption(){

            public String getId() {
                return DataEntryDeclare.SYS_TAG_PERIOD_DATA_SHOW_INFO;
            }

            public String getTitle() {
                return "\u53c2\u7167\u6570\u636e";
            }

            @Override
            public String getDefaultValue() {
                return "[]";
            }

            @Override
            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> optionItems = new ArrayList<ISystemOptionalValue>();
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u4e0a\u671f\u6570\u636e";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u53bb\u5e74\u540c\u671f\u6570\u636e";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u73af\u6bd4\u589e\u957f";
                    }

                    public String getValue() {
                        return "2";
                    }
                });
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u540c\u6bd4\u589e\u957f";
                    }

                    public String getValue() {
                        return "3";
                    }
                });
                return optionItems;
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.JTABLE_ENABLE_CALC_CELL_COLOR;
            }

            public String getTitle() {
                return "\u662f\u5426\u542f\u7528\u81ea\u52a8\u8fd0\u7b97\u5355\u5143\u683c\u989c\u8272";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return DataEntryDeclare.JTABLE_AUTOCALC_CELLCOLOR;
            }

            public String getTitle() {
                return "\u81ea\u52a8\u8fd0\u7b97\u5355\u5143\u683c\u989c\u8272";
            }

            public String getPlaceholder() {
                return "\u9ed8\u8ba4#D6F6EF";
            }

            @Override
            public String getVerifyRegex() {
                return "^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u516516\u8fdb\u5236\u989c\u8272\u7801";
            }

            public IRelationSystemOptionItem getRelationSystemOptionItem() {
                RelationSystemOptionItem relationSystemOptionItem = new RelationSystemOptionItem();
                relationSystemOptionItem.setId(DataEntryDeclare.JTABLE_ENABLE_CALC_CELL_COLOR);
                relationSystemOptionItem.setAvailableValue("1");
                relationSystemOptionItem.setCloseValue("0");
                relationSystemOptionItem.setTipsMsg("\u8bf7\u5148\u52fe\u9009\u542f\u7528\u81ea\u52a8\u8fd0\u7b97\u5355\u5143\u683c\u989c\u8272");
                return relationSystemOptionItem;
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.JTABLE_ENABLE_EFDC_CELL_COLOR;
            }

            public String getTitle() {
                return "\u662f\u5426\u542f\u7528EFDC\u5355\u5143\u683c\u989c\u8272";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return DataEntryDeclare.JTABLE_EFDC_CELLCOLOR;
            }

            public String getTitle() {
                return "EFDC\u5355\u5143\u683c\u989c\u8272";
            }

            public String getPlaceholder() {
                return "\u9ed8\u8ba4#FBEEC4";
            }

            @Override
            public String getVerifyRegex() {
                return "^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u516516\u8fdb\u5236\u989c\u8272\u7801";
            }

            public IRelationSystemOptionItem getRelationSystemOptionItem() {
                RelationSystemOptionItem relationSystemOptionItem = new RelationSystemOptionItem();
                relationSystemOptionItem.setId(DataEntryDeclare.JTABLE_ENABLE_EFDC_CELL_COLOR);
                relationSystemOptionItem.setAvailableValue("1");
                relationSystemOptionItem.setCloseValue("0");
                relationSystemOptionItem.setTipsMsg("\u8bf7\u5148\u52fe\u9009\u542f\u7528EFDC\u5355\u5143\u683c\u989c\u8272");
                return relationSystemOptionItem;
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getId() {
                return DataEntryDeclare.AUTO_CALC_IMPORT;
            }

            public String getTitle() {
                return "\u5bfc\u5165\u540e\u662f\u5426\u81ea\u52a8\u8fd0\u7b97";
            }

            public String getDefaultValue() {
                return "1";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                return new ArrayList<ISystemOptionalValue>(){
                    {
                        this.add(new ISystemOptionalValue(){

                            public String getTitle() {
                                return "\u662f";
                            }

                            public String getValue() {
                                return "1";
                            }
                        });
                        this.add(new ISystemOptionalValue(){

                            public String getTitle() {
                                return "\u5426";
                            }

                            public String getValue() {
                                return "0";
                            }
                        });
                    }
                };
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getId() {
                return DataEntryDeclare.CALC_CHECK_EXE;
            }

            public String getTitle() {
                return "\u5168\u5ba1\u5168\u7b97\u662f\u5426\u7acb\u5373\u6267\u884c";
            }

            public String getDefaultValue() {
                return "1";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                return new ArrayList<ISystemOptionalValue>(){
                    {
                        this.add(new ISystemOptionalValue(){

                            public String getTitle() {
                                return "\u7acb\u5373\u6267\u884c";
                            }

                            public String getValue() {
                                return "1";
                            }
                        });
                        this.add(new ISystemOptionalValue(){

                            public String getTitle() {
                                return "\u6392\u961f\u6267\u884c";
                            }

                            public String getValue() {
                                return "0";
                            }
                        });
                    }
                };
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getId() {
                return DataEntryDeclare.EXPORT_EXE;
            }

            public String getTitle() {
                return "\u5bfc\u51fa\u662f\u5426\u7acb\u5373\u6267\u884c";
            }

            public String getDefaultValue() {
                return "1";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                return new ArrayList<ISystemOptionalValue>(){
                    {
                        this.add(new ISystemOptionalValue(){

                            public String getTitle() {
                                return "\u7acb\u5373\u6267\u884c";
                            }

                            public String getValue() {
                                return "1";
                            }
                        });
                        this.add(new ISystemOptionalValue(){

                            public String getTitle() {
                                return "\u6392\u961f\u6267\u884c";
                            }

                            public String getValue() {
                                return "0";
                            }
                        });
                    }
                };
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.IS_ENABLE_DATA_AUTH_OPTIONS;
            }

            @Override
            public String getDefaultValue() {
                return "0";
            }

            public String getTitle() {
                return "\u5f00\u542f\u6570\u636e\u64cd\u4f5c\u6743\u9650\u63a7\u5236";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return DataEntryDeclare.MAX_FIND_NUM;
            }

            public String getTitle() {
                return "\u67e5\u627e\u66ff\u6362\u529f\u80fd\u6700\u5927\u67e5\u627e\u6570\u91cf";
            }

            @Override
            public String getVerifyRegex() {
                return "^[0-9]*[1-9][0-9]*$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u6b63\u6574\u6570";
            }

            @Override
            public String getDefaultValue() {
                return "1000";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return DataEntryDeclare.ASYNC_QUERY_SECONDS;
            }

            public String getTitle() {
                return "\u5f02\u6b65\u4efb\u52a1\u8f6e\u8be2\u79d2\u6570";
            }

            @Override
            public String getVerifyRegex() {
                return "^(10|[1-9])$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u8bf7\u8f93\u5165\u5c0f\u4e8e\u7b49\u4e8e10\u7684\u6b63\u6574\u6570";
            }

            @Override
            public String getDefaultValue() {
                return "3";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.SINGLE_IMPORT_CHECK;
            }

            @Override
            public String getDefaultValue() {
                return "0";
            }

            public String getTitle() {
                return "\u6570\u636e\u5f55\u5165\u5355\u4e2a\u6587\u4ef6\u5bfc\u5165excel\u662f\u5426\u6821\u9a8c\u6587\u4ef6\u540d\u79f0";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.TASK_DEPLOY_UNLOCK;
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }

            public String getTitle() {
                return "\u4efb\u52a1\u53d1\u5e03\u4e2d\u53ef\u4ee5\u8fdb\u5165\u6570\u636e\u5f55\u5165";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return DataEntryDeclare.MEMORIZE_CHECKTYPE;
            }

            @Override
            public String getDefaultValue() {
                return "0";
            }

            public String getTitle() {
                return "\u5ba1\u6838\u5168\u5ba1\u8bb0\u4f4f\u5ba1\u6838\u7c7b\u578b";
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getId() {
                return DataEntryDeclare.CUSTOMIMPORT_MDCODE_ANALYZE_RULE;
            }

            public String getTitle() {
                return "\u81ea\u5b9a\u4e49\u5bfc\u5165\u5355\u4f4d\u89e3\u6790\u65b9\u5f0f";
            }

            public String getDefaultValue() {
                return "CODE";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                return new ArrayList<ISystemOptionalValue>(){
                    {
                        this.add(new ISystemOptionalValue(){

                            public String getTitle() {
                                return "\u6309CODE\u89e3\u6790";
                            }

                            public String getValue() {
                                return "CODE";
                            }
                        });
                        this.add(new ISystemOptionalValue(){

                            public String getTitle() {
                                return "\u6309ORGCODE\u89e3\u6790";
                            }

                            public String getValue() {
                                return "ORGCODE";
                            }
                        });
                    }
                };
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
                return "0";
            }
        });
        return values;
    }

    private List<ISystemOptionalValue> getExcelImportSystemOptionalValues() {
        ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u6309\u9519\u8bef\u6570\u636e\u5904\u7406";
            }

            public String getValue() {
                return DataEntryDeclare.EXCEL_IMPOTR_HORIZONTAL_BAR_HANDLE_ERROR;
            }
        });
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u6309\u7a7a\u5b57\u7b26\u5bfc\u5165";
            }

            public String getValue() {
                return DataEntryDeclare.EXCEL_IMPOTR_HORIZONTAL_BAR_HANDLE_EMPTY;
            }
        });
        return values;
    }

    @Override
    public int getOrdinal() {
        return 1;
    }
}


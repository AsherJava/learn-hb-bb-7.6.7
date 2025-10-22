/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingTreeVO
 *  com.jiuqi.bsp.contentcheckrules.service.CheckGroupingService
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nvwa.systemoption.extend.IRelationSystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.RelationSystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionalItemValue
 */
package com.jiuqi.nr.system.options.adaptation.declare;

import com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingTreeVO;
import com.jiuqi.bsp.contentcheckrules.service.CheckGroupingService;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractCheckBoxOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractDropDownSingleOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.IRelationSystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.RelationSystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionalItemValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AuditDeclare
extends BaseNormalOptionDeclare {
    public static final String CHECKED_ERROR_TRACK_JTABLE_SHOW = "CHECKED_ERROR_TRACK_JTABLE_SHOW";
    public static final String CHECK_RESULT_SHOW_FORMULA = "CHECK_RESULT_SHOW_FORMULA";
    @Deprecated
    public static final String CHAR_NUMBER_OF_ERROR_MSG = "CHAR_NUMBER_OF_ERROR_MSG";
    @Deprecated
    public static final String MAX_NUMBER_OF_ERROR_MSG = "MAX_NUMBER_OF_ERROR_MSG";
    @Deprecated
    public static final String ERROR_MSG_CONTAIN_CHINESE_CHAR = "ERROR_MSG_CONTAIN_CHINESE_CHAR";
    public static final String COMPATIBILITY_MODE = "@nr/logic/compatibility-mode";
    public static final String SPECIFY_DIMENSION_ASSIGNMENT = "@nr/logic/specify-dimension-assignment";
    public static final String DIVIDING_BY_ZERO = "@nr/logic/dividing-by-zero";
    public static final String RELATED_UNITS_NOT_FOUND = "@nr/logic/related-units-not-found";
    public static final String VAR_YF_FORMAT = "@nr/var/yf-format";
    public static final String CHECK_EXPLAIN_USE_RULE_GROUP = "@nr/check/explain-use-rule-group";
    public static final String RECORD_CHECK_STATUS = "@nr/check/record-check-status";
    public static final String SHOW_ERROR_MSG = "SHOW_ERROR_MSG";
    public static final String ID = "nr-audit-group";
    public static final String MAX_THREAD_COUNT = "MAX_THREAD_COUNT";
    public static final String BATCH_PARALLEL_SPLIT_COUNT = "BATCH_PARALLEL_SPLIT_COUNT";
    public static final String ALL_CHECK_COUNT = "ALL_CHECK_COUNT";
    public static final String ALLOW_EDIT_ERR_DES_AFTER_UPLOAD = "ALLOW_EDIT_ERR_DES_AFTER_UPLOAD";
    public static final String EXPORT_ROW_DIM = "EXPORT_ROW_DIM";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u5ba1\u6838";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new AbstractCheckBoxOption(){

            public String getId() {
                return AuditDeclare.CHECK_RESULT_SHOW_FORMULA;
            }

            public String getTitle() {
                return "\u5ba1\u6838\u7ed3\u679c\u663e\u793a\u6761\u76ee";
            }

            @Override
            public String getDefaultValue() {
                return "[\"0\",\"2\"]";
            }

            @Override
            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> optionItems = new ArrayList<ISystemOptionalValue>();
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u663e\u793a\u516c\u5f0f";
                    }

                    public String getValue() {
                        return "0";
                    }
                });
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u663e\u793a\u516c\u5f0f\u8bf4\u660e";
                    }

                    public String getValue() {
                        return "1";
                    }
                });
                optionItems.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u663e\u793a\u73b0\u573a\u6570\u636e";
                    }

                    public String getValue() {
                        return "2";
                    }
                });
                return optionItems;
            }
        });
        optionItems.add(new AbstractRadioOption(){

            public String getId() {
                return AuditDeclare.CHECKED_ERROR_TRACK_JTABLE_SHOW;
            }

            public String getTitle() {
                return "\u5ba1\u6838\u9519\u8bef\u8ddf\u8e2a\u5355\u5143\u683c\u663e\u793a\u65b9\u5f0f";
            }

            public String getDefaultValue() {
                return "ICON";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u56fe\u6807";
                    }

                    public String getValue() {
                        return "ICON";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u5355\u5143\u683c\u7740\u8272";
                    }

                    public String getValue() {
                        return "GridColor";
                    }
                });
                return values;
            }
        });
        optionItems.add(new AbstractDropDownSingleOption(){

            public String getId() {
                return AuditDeclare.CHECK_EXPLAIN_USE_RULE_GROUP;
            }

            public String getTitle() {
                return "\u8bf4\u660e\u68c0\u67e5\u89c4\u5219\u7ec4";
            }

            public String getDefaultValue() {
                return "";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                CheckGroupingService bean = (CheckGroupingService)BeanUtils.getBean(CheckGroupingService.class);
                List groupingTree = bean.getGroupingTree();
                List<ISystemOptionalValue> optionalValues = AuditDeclare.this.extractLeafNodes(groupingTree);
                return optionalValues;
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return AuditDeclare.SHOW_ERROR_MSG;
            }

            public String getTitle() {
                return "\u51fa\u9519\u8bf4\u660e\u5f3a\u5236\u663e\u793a";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return AuditDeclare.MAX_THREAD_COUNT;
            }

            public String getTitle() {
                return "\u5e76\u884c\u8fd0\u7b97\u6700\u5927\u7ebf\u7a0b\u6570";
            }

            @Override
            public String getVerifyRegex() {
                return "^[1-9][0-9]*$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u5e76\u884c\u8fd0\u7b97\u6700\u5927\u7ebf\u7a0b\u6570\u5fc5\u987b\u662f\u6b63\u6574\u6570";
            }

            @Override
            public String getDefaultValue() {
                return "5";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return AuditDeclare.BATCH_PARALLEL_SPLIT_COUNT;
            }

            public String getTitle() {
                return "\u6279\u91cf\u5e76\u884c\u4efb\u52a1\u6279\u6b21\u5927\u5c0f";
            }

            @Override
            public String getVerifyRegex() {
                return "^[1-9][0-9]*$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u6279\u91cf\u5e76\u884c\u4efb\u52a1\u6279\u6b21\u5927\u5c0f\u5fc5\u987b\u662f\u6b63\u6574\u6570";
            }

            @Override
            public String getDefaultValue() {
                return "500";
            }
        });
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return AuditDeclare.ALL_CHECK_COUNT;
            }

            public String getTitle() {
                return "\u5168\u5ba1\u7ed3\u679c\u5165\u5e93\u6570\u76ee";
            }

            @Override
            public String getVerifyRegex() {
                return "^[1-9][0-9]*$";
            }

            @Override
            public String getVerifyRegexMessage() {
                return "\u5168\u5ba1\u7ed3\u679c\u5165\u5e93\u6570\u76ee\u5fc5\u987b\u662f\u6b63\u6574\u6570";
            }

            @Override
            public String getDefaultValue() {
                return "500";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return AuditDeclare.ALLOW_EDIT_ERR_DES_AFTER_UPLOAD;
            }

            public String getTitle() {
                return "\u5141\u8bb8\u4e0a\u62a5\u540e\u4fee\u6539\u51fa\u9519\u8bf4\u660e";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return AuditDeclare.EXPORT_ROW_DIM;
            }

            public String getTitle() {
                return "\u5bfc\u51fa\u5ba1\u6838\u7ed3\u679c\u65f6\u5305\u542b\u6d6e\u52a8\u884c\u7ef4\u5ea6";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return AuditDeclare.RECORD_CHECK_STATUS;
            }

            public String getTitle() {
                return "\u5f00\u542f\u5ba1\u6838\u8bb0\u5f55\u8ffd\u8e2a";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return AuditDeclare.COMPATIBILITY_MODE;
            }

            public String getTitle() {
                return "\u5355\u673a\u7248\u517c\u5bb9\u6a21\u5f0f";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return AuditDeclare.SPECIFY_DIMENSION_ASSIGNMENT;
            }

            public IRelationSystemOptionItem getRelationSystemOptionItem() {
                RelationSystemOptionItem item = new RelationSystemOptionItem();
                item.setAvailableValue("1");
                item.setId(AuditDeclare.COMPATIBILITY_MODE);
                item.setTipsMsg("\u9700\u5f00\u542f\u5355\u673a\u7248\u517c\u5bb9\u6a21\u5f0f\uff0c\u5f00\u542f\u5e76\u52fe\u9009\u540e\u652f\u6301\u6b64\u7c7b\u516c\u5f0f\u53c2\u4e0e\u8fd0\u7b97\u3002\u76f8\u5173\u516c\u5f0f\u5199\u6cd5\uff1a[1,1,md_code ='A01']=[1,2]\u6216[1,1]@1=[1,2]");
                return item;
            }

            public String getDescribe() {
                return "\u5f00\u542f\u5e76\u52fe\u9009\u540e\u652f\u6301\u6b64\u7c7b\u516c\u5f0f\u53c2\u4e0e\u8fd0\u7b97\uff0c\u5426\u5219\u6b64\u7c7b\u516c\u5f0f\u5c06\u4f5c\u4e3a\u9519\u8bef\u516c\u5f0f\uff0c\u5c06\u4e0d\u4f1a\u53c2\u4e0e\u8fd0\u7b97\u3002\u9700\u6ce8\u610f\u6307\u5b9a\u7ef4\u5ea6\u8d4b\u503c\u4f1a\u6709\u6570\u636e\u88ab\u7be1\u6539\u98ce\u9669\uff0c\u8bf7\u614e\u91cd\u5f00\u542f\uff01";
            }

            public String getTitle() {
                return "\u652f\u6301\u6307\u5b9a\u5355\u5143\u683c\u7ef4\u5ea6\u8d4b\u503c";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return AuditDeclare.DIVIDING_BY_ZERO;
            }

            public IRelationSystemOptionItem getRelationSystemOptionItem() {
                RelationSystemOptionItem item = new RelationSystemOptionItem();
                item.setAvailableValue("1");
                item.setId(AuditDeclare.COMPATIBILITY_MODE);
                item.setTipsMsg("\u9700\u5f00\u542f\u5355\u673a\u7248\u517c\u5bb9\u6a21\u5f0f\uff0c\u9ed8\u8ba4\u4e0d\u652f\u6301\u9664\u6570\u4e3a0\u7684\u8fd0\u7b97\u5ba1\u6838\uff0c\u5f00\u542f\u5e76\u52fe\u9009\u540e\u9664\u6570\u4e3a0\u65f6\uff0c\u63090\u53c2\u4e0e\u8fd0\u7b97\u5ba1\u6838\uff01\u76f8\u5173\u516c\u5f0f\u5199\u6cd5\uff1a[1,1]=[1,2]/0");
                return item;
            }

            public String getDescribe() {
                return "\u9ed8\u8ba4\u4e0d\u652f\u6301\u9664\u6570\u4e3a0\u7684\u8fd0\u7b97\u5ba1\u6838\uff0c\u5f00\u542f\u5e76\u52fe\u9009\u540e\u9664\u6570\u4e3a0\u65f6\uff0c\u63090\u53c2\u4e0e\u8fd0\u7b97\u5ba1\u6838\uff01\u5982[1,1]=[1,2]/[1,3],\u5f53[1,3]\u7b49\u4e8e0\u65f6\uff0c\u9ed8\u8ba4\u6b64\u516c\u5f0f\u4e0d\u6267\u884c\u4e0d\u4f1a\u5bf9[1,1]\u8d4b\u503c\uff0c\u5f00\u542f\u540e\u5219[1,1]=0";
            }

            public String getTitle() {
                return "\u652f\u6301\u9664\u6cd5\u9664\u6570\u4e3a0";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return AuditDeclare.RELATED_UNITS_NOT_FOUND;
            }

            public IRelationSystemOptionItem getRelationSystemOptionItem() {
                RelationSystemOptionItem item = new RelationSystemOptionItem();
                item.setAvailableValue("1");
                item.setId(AuditDeclare.COMPATIBILITY_MODE);
                item.setTipsMsg("\u9700\u5f00\u542f\u5355\u673a\u7248\u517c\u5bb9\u6a21\u5f0f\uff0c\u9ed8\u8ba4\u5173\u8054\u5355\u4f4d\u4e3a\u627e\u5230\u65f6\u4e0d\u8fdb\u884c\u8fd0\u7b97\uff0c\u5f00\u542f\u5e76\u52fe\u9009\u540e\u63090\u5904\u7406\uff01\u76f8\u5173\u516c\u5f0f\u5199\u6cd5\uff1a[1,1]=[1,2]+[1,3]@1");
                return item;
            }

            public String getDescribe() {
                return "\u9ed8\u8ba4\u5173\u8054\u5355\u4f4d\u4e3a\u627e\u5230\u65f6\u4e0d\u8fdb\u884c\u8fd0\u7b97\uff0c\u5f00\u542f\u5e76\u52fe\u9009\u540e\u63090\u5904\u7406\uff01\u5982[1,1]=[1,2]+[1,3]@1\u516c\u5f0f\uff0c\u5f53\u5728\u5173\u8054\u4efb\u52a1@1\u4e2d\u672a\u627e\u5230\u5bf9\u5e94\u516c\u5f0f\uff0c\u5219\u6b64\u516c\u5f0f\u4e0d\u6267\u884c\u4e0d\u5bf9[1,1]\u8d4b\u503c\uff0c\u5f00\u542f\u540e\u8ba4\u4e3a[1,3]@1=0\uff0c\u6b64\u516c\u5f0f\u6700\u7ec8\u7ed3\u679c\u4e3a[1,1]=[1,2]+0";
            }

            public String getTitle() {
                return "\u5173\u8054\u4efb\u52a1\u5355\u4f4d\u672a\u627e\u5230\u662f\u5426\u53c2\u4e0e\u8fd0\u7b97\u3001\u5ba1\u6838";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return AuditDeclare.VAR_YF_FORMAT;
            }

            public IRelationSystemOptionItem getRelationSystemOptionItem() {
                RelationSystemOptionItem item = new RelationSystemOptionItem();
                item.setAvailableValue("1");
                item.setId(AuditDeclare.COMPATIBILITY_MODE);
                item.setTipsMsg("\u5f00\u542f\u540e\u8fd4\u56de\u4e24\u4f4d\u5b57\u7b26\u4e32\uff1a\u201c01\u201d\uff0c\u201c02\u201d\uff0c\u5173\u95ed\u540e\u8fd4\u56de\u56db\u4f4d\u5b57\u7b26\u4e32\u201c0001\u201c\uff0c\u201c0002\u201d\u7b49");
                return item;
            }

            public String getDescribe() {
                return "\u5f00\u542f\u540e\u8fd4\u56de\u4e24\u4f4d\u5b57\u7b26\u4e32\uff1a\u201c01\u201d\uff0c\u201c02\u201d\uff0c\u5173\u95ed\u540e\u8fd4\u56de\u56db\u4f4d\u5b57\u7b26\u4e32\u201c0001\u201c\uff0c\u201c0002\u201d\u7b49";
            }

            public String getTitle() {
                return "\u53d8\u91cfyf,jd\u8fd4\u56de\u4e24\u4f4d\u5b57\u7b26\u4e32";
            }

            @Override
            public String getDefaultValue() {
                return "0";
            }
        });
        return optionItems;
    }

    @Override
    public int getOrdinal() {
        return 2;
    }

    public List<ISystemOptionalValue> extractLeafNodes(List<CheckGroupingTreeVO> groupingTree) {
        if (groupingTree == null) {
            return Collections.emptyList();
        }
        ArrayList<ISystemOptionalValue> optionalValues = new ArrayList<ISystemOptionalValue>();
        for (CheckGroupingTreeVO node : groupingTree) {
            if (node.getIsLeaf()) {
                optionalValues.add(this.convertToOptionalValue(node));
                continue;
            }
            optionalValues.addAll(this.extractLeafNodes(node.getCheckGroupingTreeVOList()));
        }
        return optionalValues;
    }

    private ISystemOptionalValue convertToOptionalValue(CheckGroupingTreeVO node) {
        SystemOptionalItemValue item = new SystemOptionalItemValue();
        item.setValue(node.getKey());
        item.setTitle(node.getTitle());
        return item;
    }
}


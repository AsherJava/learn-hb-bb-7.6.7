/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.analysisreport.option;

import com.jiuqi.nr.analysisreport.annotation.AnaReportInsertVariableResource;
import com.jiuqi.nr.analysisreport.option.NrArVarSystemOptionOperator;
import com.jiuqi.nr.analysisreport.support.AbstractExprParser;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NrArVarSysOption
implements ISystemOptionDeclare {
    public static final String NAMESPACE = "\u62a5\u8868";
    private static final String TITLE = "\u5206\u6790\u62a5\u544a";
    public static final String ICONS = "#icon14_SHU_A_NW_tubiaozhongji";
    @Autowired
    private NrArVarSystemOptionOperator nrArSystemOptionOperator;
    @Lazy
    @Autowired(required=false)
    private List<AbstractExprParser> exprParsers;

    public String getId() {
        return "com.jiuqi.nr.analysisreport";
    }

    public String getTitle() {
        return TITLE;
    }

    public int getOrdinal() {
        return 11;
    }

    public String getNameSpace() {
        return NAMESPACE;
    }

    public String getIcons() {
        return ICONS;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> items = new ArrayList<ISystemOptionItem>();
        if (!CollectionUtils.isEmpty(this.exprParsers)) {
            for (final AbstractExprParser exprParser : this.exprParsers) {
                Class<?> clazz = exprParser.getClass();
                if (!clazz.isAnnotationPresent(AnaReportInsertVariableResource.class)) continue;
                AnaReportInsertVariableResource resource = clazz.getAnnotation(AnaReportInsertVariableResource.class);
                final String resouceName = resource.name();
                items.add(new ISystemOptionItem(){

                    public String getId() {
                        return AnaUtils.getVarMaxThreadOptionItem(exprParser.getName());
                    }

                    public String getTitle() {
                        return resouceName + "\u53d8\u91cf\u6700\u5927\u7ebf\u7a0b\u6570";
                    }

                    public String getDefaultValue() {
                        return "5";
                    }

                    public SystemOptionConst.EditMode getEditMode() {
                        return SystemOptionConst.EditMode.NUMBER_INPUT;
                    }

                    public String getVerifyRegex() {
                        return "^(?:[1-9]|1[0-9]|20)$";
                    }

                    public String getVerifyRegexMessage() {
                        return "\u7ebf\u7a0b\u6570\u8303\u56f4\u4e3a1-20";
                    }
                });
            }
        }
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "batchExportThreadNum";
            }

            public String getTitle() {
                return "\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa\u6700\u5927\u7ebf\u7a0b\u6570";
            }

            public String getDefaultValue() {
                return "5";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.NUMBER_INPUT;
            }

            public String getVerifyRegex() {
                return "^(?:[1-9]|1[0-9]|20)$";
            }

            public String getVerifyRegexMessage() {
                return "\u7ebf\u7a0b\u6570\u8303\u56f4\u4e3a1-20";
            }
        });
        items.add(new ISystemOptionItem(){

            public String getId() {
                return "batchExportThreadDealUnitNum";
            }

            public String getTitle() {
                return "\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa\u5355\u4e2a\u7ebf\u7a0b\u5904\u7406\u5355\u4f4d\u6570";
            }

            public String getDefaultValue() {
                return "5";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.NUMBER_INPUT;
            }

            public String getVerifyRegex() {
                return "^(?:[1-9]|[1-4][0-9]|50)$";
            }

            public String getVerifyRegexMessage() {
                return "\u8303\u56f4\u4e3a1-50";
            }
        });
        return items;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.nrArSystemOptionOperator;
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.NORMAL;
    }
}


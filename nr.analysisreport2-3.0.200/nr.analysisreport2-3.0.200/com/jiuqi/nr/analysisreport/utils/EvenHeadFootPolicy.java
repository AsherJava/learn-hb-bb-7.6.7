/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy
 *  org.apache.poi.xwpf.usermodel.XWPFFooter
 *  org.apache.poi.xwpf.usermodel.XWPFHeader
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.nr.analysisreport.utils.CustomXWPFHeaderFooterPolicy;
import com.jiuqi.nr.analysisreport.utils.HeadFootPolicyExt;
import com.jiuqi.nr.analysisreport.utils.HeadFootPolicyImpl;
import java.util.Map;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;

class EvenHeadFootPolicy
extends HeadFootPolicyExt
implements HeadFootPolicyImpl {
    XWPFHeader header = (this.showDiffHead == false && this.showHead == false || this.showDiffHead != false && "".equals(this.headerEvenText)) && (this.showDiffNo == false && this.showNumber == false || this.showDiffNo == false && this.showNumber != false && this.istotalfoot != false || this.showDiffNo != false && this.showEvenNumber == false || this.showDiffNo != false && this.showEvenNumber != false && this.isevenfoot != false) ? null : this.headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.EVEN);
    XWPFFooter footer = (this.showDiffFoot == false && this.showFoot == false || this.showDiffFoot != false && "".equals(this.footerEvenText)) && (this.showDiffNo == false && this.showNumber == false || this.showDiffNo == false && this.showNumber != false && this.istotalfoot == false || this.showDiffNo != false && this.showEvenNumber == false || this.showDiffNo != false && this.showEvenNumber != false && this.isevenfoot == false) ? null : this.headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.EVEN);

    public EvenHeadFootPolicy(CustomXWPFHeaderFooterPolicy headerFooterPolicy, Map<String, String> headerSettings, Map<String, String> footerSettings, Map<String, String> noSettings) {
        super(headerFooterPolicy, headerSettings, footerSettings, noSettings);
    }

    @Override
    public void create() {
        if (this.header != null) {
            if (this.showDiffHead.booleanValue()) {
                if (!"".equals(this.headerEvenText)) {
                    this.addParagraphHeader(this.header, this.evenEnumType, this.headerEvenText);
                }
            } else if (!"".equals(this.headerText)) {
                this.addParagraphHeader(this.header, this.evenEnumType, this.headerText);
            }
            if (this.showDiffNo.booleanValue() && !this.isevenfoot.booleanValue()) {
                if (this.showEvenNumber.booleanValue()) {
                    this.addSdtHeader(this.header, this.evenEnumType);
                }
            } else if (!this.showDiffNo.booleanValue() && !this.isevenfoot.booleanValue() && this.showNumber.booleanValue()) {
                this.addSdtHeader(this.header, this.enumType);
            }
        }
        if (this.footer != null) {
            if (this.showDiffFoot.booleanValue()) {
                if (!"".equals(this.footerEvenText)) {
                    this.addParagraphFooter(this.footer, this.evenEnumType, this.footerEvenText);
                }
            } else if (!"".equals(this.footerText)) {
                this.addParagraphFooter(this.footer, this.evenEnumType, this.footerText);
            }
            if (this.showDiffNo.booleanValue() && this.isevenfoot.booleanValue()) {
                if (this.showEvenNumber.booleanValue()) {
                    this.addSdtFooter(this.footer, this.evenEnumType);
                }
            } else if (!this.showDiffNo.booleanValue() && this.isevenfoot.booleanValue() && this.showNumber.booleanValue()) {
                this.addSdtFooter(this.footer, this.enumType);
            }
        }
    }
}


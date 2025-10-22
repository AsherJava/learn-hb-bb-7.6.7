/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.nr.analysisreport.utils.CustomXWPFHeaderFooterPolicy;
import com.jiuqi.nr.analysisreport.utils.HeadFootPolicyExt;
import com.jiuqi.nr.analysisreport.utils.HeadFootPolicyImpl;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;

class OddHeadFootPolicy
extends HeadFootPolicyExt
implements HeadFootPolicyImpl {
    XWPFHeader header = (this.showDiffHead == false && this.showHead == false || this.showDiffHead != false && "".equals(this.headerOddText)) && (this.showDiffNo == false && this.showNumber == false || this.showDiffNo == false && this.showNumber != false && this.istotalfoot != false || this.showDiffNo != false && this.showOddNumber == false || this.showDiffNo != false && this.showOddNumber != false && this.isoddfoot != false) ? null : this.headerFooterPolicy.createHeader(CustomXWPFHeaderFooterPolicy.DEFAULT);
    XWPFFooter footer = (this.showDiffFoot == false && this.showFoot == false || this.showDiffFoot != false && "".equals(this.footerOddText)) && (this.showDiffNo == false && this.showNumber == false || this.showDiffNo == false && this.showNumber != false && this.istotalfoot == false || this.showDiffNo != false && this.showOddNumber == false || this.showDiffNo != false && this.showOddNumber != false && this.isoddfoot == false) ? null : this.headerFooterPolicy.createFooter(CustomXWPFHeaderFooterPolicy.DEFAULT);

    public OddHeadFootPolicy(CustomXWPFHeaderFooterPolicy headerFooterPolicy, Map<String, String> headerSettings, Map<String, String> footerSettings, Map<String, String> noSettings) {
        super(headerFooterPolicy, headerSettings, footerSettings, noSettings);
    }

    @Override
    public void create() {
        if (this.header != null) {
            if (this.showDiffHead.booleanValue()) {
                if (!"".equals(this.headerOddText)) {
                    this.addParagraphHeader(this.header, this.oddEnumType, this.headerOddText);
                }
            } else if (!"".equals(this.headerText)) {
                this.addParagraphHeader(this.header, this.oddEnumType, this.headerText);
            }
            if (this.showDiffNo.booleanValue() && !this.isoddfoot.booleanValue()) {
                if (this.showOddNumber.booleanValue()) {
                    this.addSdtHeader(this.header, this.oddEnumType);
                }
            } else if (!this.showDiffNo.booleanValue() && !this.isoddfoot.booleanValue() && this.showNumber.booleanValue()) {
                this.addSdtHeader(this.header, this.enumType);
            }
        }
        if (this.footer != null) {
            if (this.showDiffFoot.booleanValue()) {
                if (!"".equals(this.footerOddText)) {
                    this.addParagraphFooter(this.footer, this.oddEnumType, this.footerOddText);
                }
            } else if (!"".equals(this.footerText)) {
                this.addParagraphFooter(this.footer, this.oddEnumType, this.footerText);
            }
            if (this.showDiffNo.booleanValue() && this.isoddfoot.booleanValue()) {
                if (this.showOddNumber.booleanValue()) {
                    this.addSdtFooter(this.footer, this.oddEnumType);
                }
            } else if (!this.showDiffNo.booleanValue() && this.isoddfoot.booleanValue() && this.showNumber.booleanValue()) {
                this.addSdtFooter(this.footer, this.enumType);
            }
        }
    }
}


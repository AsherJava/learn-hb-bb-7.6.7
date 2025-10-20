/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.Font
 */
package com.jiuqi.nr.definition.internal.impl.print;

import com.jiuqi.np.grid.Font;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordLabelDefineImpl
implements WordLabelDefine {
    private static final Logger logger = LoggerFactory.getLogger(WordLabelDefineImpl.class);
    private static final long serialVersionUID = -385680577672345184L;
    private String text;
    private Font font = new Font();
    private int element;
    private int horizontalPos;
    private int verticalPos;
    private int scope;
    private String order;
    private double letterSpace;
    private double lineSpace;
    private boolean isAutoWrap;

    public WordLabelDefineImpl() {
    }

    public WordLabelDefineImpl(WordLabelDefine define) {
        this();
        this.text = define.getText();
        this.font = define.getFont();
        this.element = define.getElement();
        this.horizontalPos = define.getHorizontalPos();
        this.verticalPos = define.getVerticalPos();
        this.scope = define.getScope();
        this.order = define.getOrder();
    }

    @Override
    public boolean isAutoWrap() {
        return this.isAutoWrap;
    }

    @Override
    public void setAutoWrap(boolean isAutoWrap) {
        this.isAutoWrap = isAutoWrap;
    }

    @Override
    public int getElement() {
        return this.element;
    }

    @Override
    public Font getFont() {
        return this.font;
    }

    @Override
    public int getHorizontalPos() {
        return this.horizontalPos;
    }

    @Override
    public double getLetterSpace() {
        return this.letterSpace;
    }

    @Override
    public void setLetterSpace(double letterSpace) {
        this.letterSpace = letterSpace;
    }

    @Override
    public double getLineSpace() {
        return this.lineSpace;
    }

    @Override
    public void setLineSpace(double lineSpace) {
        this.lineSpace = lineSpace;
    }

    @Override
    public String getLocationCode() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.element);
        buffer.append(this.verticalPos);
        buffer.append(this.horizontalPos);
        return buffer.toString();
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public int getScope() {
        return this.scope;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public int getVerticalPos() {
        return this.verticalPos;
    }

    @Override
    public void setElement(int element) {
        this.element = element;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public void setHorizontalPos(int horizontalPos) {
        this.horizontalPos = horizontalPos;
    }

    @Override
    public void setLocationCode(String code) {
        if (code != null && code.matches("^[01][01][012]$")) {
            this.element = Integer.valueOf(String.valueOf(code.charAt(0)));
            this.verticalPos = Integer.valueOf(String.valueOf(code.charAt(1)));
            this.horizontalPos = Integer.valueOf(String.valueOf(code.charAt(2)));
        } else {
            this.element = -1;
            this.verticalPos = -1;
            this.horizontalPos = -1;
        }
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setScope(int scope) {
        this.scope = scope;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setVerticalPos(int verticalPos) {
        this.verticalPos = verticalPos;
    }

    public WordLabelDefine clone() {
        WordLabelDefine define = null;
        try {
            define = (WordLabelDefine)super.clone();
            define.setFont(this.getFont());
        }
        catch (CloneNotSupportedException e) {
            logger.error(e.getMessage(), e);
        }
        return define;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        WordLabelDefineImpl other = (WordLabelDefineImpl)obj;
        if (this.element != other.element) {
            return false;
        }
        if (this.font == null) {
            if (other.font != null) {
                return false;
            }
        } else {
            if (this.font.getBold() != other.font.getBold()) {
                return false;
            }
            if (this.font.getItalic() != other.font.getItalic()) {
                return false;
            }
            if (this.font.getStrikeout() != other.font.getStrikeout()) {
                return false;
            }
            if (this.font.getUnderline() != other.font.getUnderline()) {
                return false;
            }
            if (this.font.getColor() != other.font.getColor()) {
                return false;
            }
            if (!this.font.getName().equals(other.font.getName())) {
                return false;
            }
            if (this.font.getSize() != other.font.getSize()) {
                return false;
            }
            if (this.font.getStylevalue() != other.font.getStylevalue()) {
                return false;
            }
        }
        if (this.horizontalPos != other.horizontalPos) {
            return false;
        }
        if (this.isAutoWrap != other.isAutoWrap) {
            return false;
        }
        if (Double.doubleToLongBits(this.letterSpace) != Double.doubleToLongBits(other.letterSpace)) {
            return false;
        }
        if (Double.doubleToLongBits(this.lineSpace) != Double.doubleToLongBits(other.lineSpace)) {
            return false;
        }
        if (this.order == null ? other.order != null : !this.order.equals(other.order)) {
            return false;
        }
        if (this.scope != other.scope) {
            return false;
        }
        if (this.text == null ? other.text != null : !this.text.equals(other.text)) {
            return false;
        }
        return this.verticalPos == other.verticalPos;
    }
}


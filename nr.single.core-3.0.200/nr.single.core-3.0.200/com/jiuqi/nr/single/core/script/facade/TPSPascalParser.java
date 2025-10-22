/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.script.facade;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.script.bean.TRTab;
import com.jiuqi.nr.single.core.script.common.TPSParserErrorKind;
import com.jiuqi.nr.single.core.script.common.TPSPasToken;
import com.jiuqi.nr.single.core.script.common.uPSUtils;
import com.jiuqi.nr.single.core.script.facade.TPSParserErrorEvent;
import com.jiuqi.nr.single.core.syntax.common.SyntaxConsts;
import com.jiuqi.nr.single.core.syntax.common.SyntaxLib;

public class TPSPascalParser {
    private String pasText;
    protected String data;
    protected String text;
    protected int lastEnterPos;
    protected int row;
    protected int currTokenPos;
    protected int tokenLength;
    protected TPSPasToken tokenId;
    protected String token;
    protected String originalToken;
    protected TPSParserErrorEvent parserError;
    protected boolean enableComments;
    protected boolean enableWhitespaces;

    public String getCurToken() {
        return this.text.substring(this.currTokenPos, this.tokenLength);
    }

    public int getCol() {
        return this.currTokenPos - this.lastEnterPos + 1;
    }

    public int getCurrTokenPos() {
        return this.currTokenPos;
    }

    public boolean isEnableComments() {
        return this.enableComments;
    }

    public boolean isEnableWhitespaces() {
        return this.enableWhitespaces;
    }

    public void setEnableComments(boolean enableComments) {
        this.enableComments = enableComments;
    }

    public void setEnableWhitespaces(boolean enableWhitespaces) {
        this.enableWhitespaces = enableWhitespaces;
    }

    public String getToken() {
        return this.token;
    }

    public String getOriginalToken() {
        return this.originalToken;
    }

    public TPSPasToken getCurrTokenID() {
        return this.tokenId;
    }

    public int getRow() {
        return this.row;
    }

    public void setText(String data) {
        this.data = data;
        this.pasText = data;
        this.text = data;
        this.tokenLength = 0;
        this.currTokenPos = 0;
        this.tokenId = TPSPasToken.CSTI_EOF;
        this.lastEnterPos = 0;
        this.row = 1;
        this.next();
    }

    public TPSParserErrorEvent getParserError() {
        return this.parserError;
    }

    public void setParserError(TPSParserErrorEvent parserError) {
        this.parserError = parserError;
    }

    public String getText() {
        return this.pasText;
    }

    public void next() {
        if (StringUtils.isEmpty((String)this.text)) {
            this.tokenLength = 0;
            this.currTokenPos = 0;
            this.tokenId = TPSPasToken.CSTI_EOF;
            return;
        }
        this.currTokenPos += this.tokenLength;
        TPSParserErrorKind err = this.parseToken();
        if (err != TPSParserErrorKind.INO_EORROR) {
            this.tokenLength = 0;
            this.tokenId = TPSPasToken.CSTI_EOF;
            this.token = "";
            this.originalToken = "";
            if (this.parserError != null) {
                this.parserError.ParserError(this, err);
            }
            return;
        }
        if (this.tokenId == TPSPasToken.CSTIINT_COMMENT && this.enableComments) {
            this.token = this.originalToken = this.text.substring(this.currTokenPos, this.currTokenPos + this.tokenLength);
        } else if (this.tokenId == TPSPasToken.CSTIINT_WHITESPACE && this.enableWhitespaces) {
            this.token = this.originalToken = this.text.substring(this.currTokenPos, this.currTokenPos + this.tokenLength);
        } else if (this.tokenId == TPSPasToken.CSTI_INTEGER || this.tokenId == TPSPasToken.CSTI_REAL || this.tokenId == TPSPasToken.CSTI_STRING || this.tokenId == TPSPasToken.CSTI_CHAR || this.tokenId == TPSPasToken.CSTI_HEXINT) {
            this.token = this.originalToken = this.text.substring(this.currTokenPos, this.currTokenPos + this.tokenLength);
        } else if (this.tokenId == TPSPasToken.CSTI_IDENTIFIER) {
            this.token = this.originalToken = this.text.substring(this.currTokenPos, this.currTokenPos + this.tokenLength);
        } else {
            this.originalToken = "";
            this.token = "";
        }
    }

    private boolean checkReserved(String s, TRTab currTokenIdTab) {
        boolean result = false;
        int l = 0;
        char j = s.charAt(0);
        int h = 64;
        int i = 0;
        while (l <= h) {
            i = l + h >> 1;
            String sName = uPSUtils.LookupTable[i].getName();
            if (j == sName.charAt(0)) {
                if (s.equalsIgnoreCase(sName)) {
                    result = true;
                    currTokenIdTab.setName(uPSUtils.LookupTable[i].getName());
                    currTokenIdTab.setToken(uPSUtils.LookupTable[i].getToken());
                    return result;
                }
                if (s.compareToIgnoreCase(sName) > 0) {
                    l = i + 1;
                    continue;
                }
                h = i - 1;
                continue;
            }
            if (s.compareToIgnoreCase(sName) > 0) {
                l = i + 1;
                continue;
            }
            h = i - 1;
        }
        return result;
    }

    private String getSubToken(int currTokenPos, int currTokenLen) {
        return this.text.substring(currTokenPos, currTokenPos + currTokenLen);
    }

    private TPSParserErrorKind parseToken() {
        TPSParserErrorKind result = TPSParserErrorKind.INO_EORROR;
        int ct = this.currTokenPos;
        int ci = 0;
        switch (this.text.charAt(ct)) {
            case '\u0000': {
                this.tokenId = TPSPasToken.CSTI_EOF;
                this.tokenLength = 0;
                break;
            }
            case 'A': 
            case 'B': 
            case 'C': 
            case 'D': 
            case 'E': 
            case 'F': 
            case 'G': 
            case 'H': 
            case 'I': 
            case 'J': 
            case 'K': 
            case 'L': 
            case 'M': 
            case 'N': 
            case 'O': 
            case 'P': 
            case 'Q': 
            case 'R': 
            case 'S': 
            case 'T': 
            case 'U': 
            case 'V': 
            case 'W': 
            case 'X': 
            case 'Y': 
            case 'Z': 
            case '_': 
            case 'a': 
            case 'b': 
            case 'c': 
            case 'd': 
            case 'e': 
            case 'f': 
            case 'g': 
            case 'h': 
            case 'i': 
            case 'j': 
            case 'k': 
            case 'l': 
            case 'm': 
            case 'n': 
            case 'o': 
            case 'p': 
            case 'q': 
            case 'r': 
            case 's': 
            case 't': 
            case 'u': 
            case 'v': 
            case 'w': 
            case 'x': 
            case 'y': 
            case 'z': {
                ci = ct + 1;
                while (SyntaxLib.checkInCharSet(this.text.charAt(ci), SyntaxConsts.ENGLISH_CHARSET2)) {
                    ++ci;
                }
                this.tokenLength = ci - ct;
                String lastUpToken1 = this.getSubToken(this.currTokenPos, this.tokenLength);
                String lastUpToken = "";
                for (int i = 0; i < lastUpToken1.length() - 1; ++i) {
                    char c = lastUpToken1.charAt(i);
                    if (c >= 'a' && c <= 'z') {
                        c = (char)(c - 32);
                    }
                    lastUpToken = lastUpToken + c;
                }
                TRTab currTokenIdTab = new TRTab();
                this.tokenId = !this.checkReserved(lastUpToken, currTokenIdTab) ? TPSPasToken.CSTI_IDENTIFIER : currTokenIdTab.getToken();
                this.token = lastUpToken;
                break;
            }
            case '$': {
                ci = ct + 1;
                while (SyntaxLib.checkInCharSet(this.text.charAt(ci), SyntaxConsts.ENGLISH_CHARSET3)) {
                    ++ci;
                }
                this.tokenId = TPSPasToken.CSTI_HEXINT;
                this.tokenLength = ci - ct;
                break;
            }
            case '0': 
            case '1': 
            case '2': 
            case '3': 
            case '4': 
            case '5': 
            case '6': 
            case '7': 
            case '8': 
            case '9': {
                boolean hs = false;
                ci = ct;
                while (SyntaxLib.checkInCharSet(this.text.charAt(ci), SyntaxConsts.NUMBER_CHARSET)) {
                    if (this.text.charAt(++ci) != '.' || hs) continue;
                    if (this.text.charAt(ci + 1) == '.') break;
                    hs = true;
                    ++ci;
                }
                char c1 = this.text.charAt(ci);
                char c2 = this.text.charAt(ci + 1);
                if ((c1 == 'E' || c1 == 'e') && SyntaxLib.checkInCharSet(c1, SyntaxConsts.NUMBER_CHARSET) || (c2 == 'E' || c2 == 'e') && SyntaxLib.checkInCharSet(c2, SyntaxConsts.NUMBER_CHARSET)) {
                    hs = true;
                    if (this.text.charAt(++ci) == '+' || this.text.charAt(ci) == '-') {
                        ++ci;
                    }
                    do {
                        ++ci;
                    } while (SyntaxLib.checkInCharSet(c2, SyntaxConsts.NUMBER_CHARSET));
                }
                this.tokenId = hs ? TPSPasToken.CSTI_REAL : TPSPasToken.CSTI_INTEGER;
                this.tokenLength = ci - ct;
                break;
            }
            case '\'': {
                ci = ct + 1;
                while (this.text.charAt(ci) != '\u0000' && this.text.charAt(ci) != '\r' && this.text.charAt(ci) != '\n') {
                    if (this.text.charAt(ci) == '\'') {
                        if (this.text.charAt(ci + 1) != '\'') break;
                        ++ci;
                    }
                    ++ci;
                }
                if (this.text.charAt(ci) == '\'') {
                    this.tokenId = TPSPasToken.CSTI_STRING;
                } else {
                    this.tokenId = TPSPasToken.CSTI_STRING;
                    result = TPSParserErrorKind.ISTRING_ERROR;
                }
                this.tokenLength = ci - ct;
                break;
            }
            case '#': {
                ci = ct + 1;
                if (this.text.charAt(ci) == '$') {
                    ++ci;
                    while (SyntaxLib.checkInCharSet(this.text.charAt(ci), SyntaxConsts.ENGLISH_CHARSET3)) {
                        ++ci;
                    }
                    this.tokenId = TPSPasToken.CSTI_CHAR;
                    this.tokenLength = ci - ct;
                    break;
                }
                while (SyntaxLib.checkInCharSet(this.text.charAt(ci), SyntaxConsts.NUMBER_CHARSET)) {
                    ++ci;
                }
                if (SyntaxLib.checkInCharSet(this.text.charAt(ci), SyntaxConsts.ENGLISH_CHARSET2)) {
                    this.tokenId = TPSPasToken.CSTI_CHAR;
                    result = TPSParserErrorKind.ICHAR_ERROR;
                } else {
                    this.tokenId = TPSPasToken.CSTI_CHAR;
                }
                this.tokenLength = ci - ct;
                break;
            }
            case '=': {
                this.tokenId = TPSPasToken.CSTI_EQUAL;
                this.tokenLength = 1;
                break;
            }
            case '>': {
                if (this.text.charAt(ct + 1) == '=') {
                    this.tokenId = TPSPasToken.CSTI_GREATEREQUAL;
                    this.tokenLength = 2;
                    break;
                }
                this.tokenId = TPSPasToken.CSTI_GREATER;
                this.tokenLength = 1;
                break;
            }
            case '<': {
                if (this.text.charAt(ct + 1) == '=') {
                    this.tokenId = TPSPasToken.CSTI_LESSEQUAL;
                    this.tokenLength = 2;
                    break;
                }
                if (this.text.charAt(ct + 1) == '>') {
                    this.tokenId = TPSPasToken.CSTI_NOTEQUAL;
                    this.tokenLength = 2;
                    break;
                }
                this.tokenId = TPSPasToken.CSTI_LESS;
                this.tokenLength = 1;
                break;
            }
            case ')': {
                this.tokenId = TPSPasToken.CSTI_CLOSEROUND;
                this.tokenLength = 1;
                break;
            }
            case '(': {
                if (this.text.charAt(ct + 1) == '*') {
                    ci = ct + 1;
                    while (this.text.charAt(ci) != '\u0000' && (this.text.charAt(ci) != '*' || this.text.charAt(ci + 1) != ')')) {
                        if (this.text.charAt(ci) == '\r') {
                            ++this.row;
                            if (this.text.charAt(ci) == '\n') {
                                ++ci;
                            }
                            this.lastEnterPos = ci + 1;
                        } else if (this.text.charAt(ci) == '\n') {
                            ++this.row;
                            this.lastEnterPos = ci + 1;
                        }
                        ++ci;
                    }
                    if (this.text.charAt(ci) == '\u0000') {
                        this.tokenId = TPSPasToken.CSTIINT_COMMENT;
                        result = TPSParserErrorKind.ICOMMENT_ERROR;
                    } else {
                        this.tokenId = TPSPasToken.CSTIINT_COMMENT;
                        ci += 2;
                    }
                    this.tokenLength = ci - ct;
                    break;
                }
                this.tokenId = TPSPasToken.CSTI_OPENROUND;
                this.tokenLength = 1;
                break;
            }
            case '[': {
                this.tokenId = TPSPasToken.CSTI_OPENBLOCK;
                this.tokenLength = 1;
                break;
            }
            case ']': {
                this.tokenId = TPSPasToken.CSTI_CLOSEBLOCK;
                this.tokenLength = 1;
                break;
            }
            case ',': {
                this.tokenId = TPSPasToken.CSTI_COMMA;
                this.tokenLength = 1;
                break;
            }
            case '.': {
                if (this.text.charAt(ct + 1) == '.') {
                    this.tokenId = TPSPasToken.CSTI_TWODOTS;
                    this.tokenLength = 2;
                    break;
                }
                this.tokenId = TPSPasToken.CSTI_PERIOD;
                this.tokenLength = 1;
                break;
            }
            case '@': {
                this.tokenId = TPSPasToken.CSTI_ADDRESSOF;
                this.tokenLength = 1;
                break;
            }
            case '^': {
                this.tokenId = TPSPasToken.CSTI_DEREFERENCE;
                this.tokenLength = 1;
                break;
            }
            case ';': {
                this.tokenId = TPSPasToken.CSTI_SEMICOLON;
                this.tokenLength = 1;
                break;
            }
            case ':': {
                if (this.text.charAt(ct + 1) == '=') {
                    this.tokenId = TPSPasToken.CSTI_ASSIGNMENT;
                    this.tokenLength = 2;
                    break;
                }
                this.tokenId = TPSPasToken.CSTI_COLON;
                this.tokenLength = 1;
                break;
            }
            case '+': {
                this.tokenId = TPSPasToken.CSTI_PLUS;
                this.tokenLength = 1;
                break;
            }
            case '-': {
                this.tokenId = TPSPasToken.CSTI_MINUS;
                this.tokenLength = 1;
                break;
            }
            case '*': {
                this.tokenId = TPSPasToken.CSTI_MULTIPLY;
                this.tokenLength = 1;
                break;
            }
            case '/': {
                if (this.text.charAt(ct + 1) == '/') {
                    ci = ct + 1;
                    while (this.text.charAt(ci) != '\u0000' && this.text.charAt(ci) != '\r' && this.text.charAt(ci) != '\n') {
                        ++ci;
                    }
                    this.tokenId = this.text.charAt(ci) == '\u0000' ? TPSPasToken.CSTIINT_COMMENT : TPSPasToken.CSTIINT_COMMENT;
                    this.tokenLength = ci - ct;
                    break;
                }
                this.tokenId = TPSPasToken.CSTI_DIVIDE;
                this.tokenLength = 1;
                break;
            }
            case '\t': 
            case '\n': 
            case '\r': 
            case ' ': {
                ci = ct;
                while (this.text.charAt(ci) == ' ' || this.text.charAt(ci) == '\t' || this.text.charAt(ci) == '\r' || this.text.charAt(ci) == '\n') {
                    if (this.text.charAt(ci) == '\r') {
                        ++this.row;
                        if (this.text.charAt(ci) == '\n') {
                            ++ci;
                        }
                        this.lastEnterPos = ci + 1;
                    } else {
                        ++this.row;
                        this.lastEnterPos = ci + 1;
                    }
                    ++ci;
                }
                this.tokenId = TPSPasToken.CSTIINT_WHITESPACE;
                this.tokenLength = ci - ct;
                break;
            }
            case '{': {
                ci = ct + 1;
                int matchCount = 0;
                while (this.text.charAt(ci) != '\u0000' && (this.text.charAt(ci) != '}' || matchCount != 0)) {
                    if (this.text.charAt(ci) == '}') {
                        --matchCount;
                    }
                    if (this.text.charAt(ci) == '{') {
                        ++matchCount;
                    }
                    if (this.text.charAt(ci) == '\r') {
                        ++this.row;
                        if (this.text.charAt(ci) == '\n') {
                            ++ci;
                        }
                        this.lastEnterPos = ci + 1;
                    } else if (this.text.charAt(ci) == '\n') {
                        ++this.row;
                        this.lastEnterPos = ci + 1;
                    }
                    ++ci;
                }
                if (this.text.charAt(ci) == '\u0000') {
                    this.tokenId = TPSPasToken.CSTIINT_COMMENT;
                    result = TPSParserErrorKind.ICOMMENT_ERROR;
                } else {
                    this.tokenId = TPSPasToken.CSTIINT_COMMENT;
                }
                this.tokenLength = ci - ct + 1;
                break;
            }
            default: {
                result = TPSParserErrorKind.ISYNTAX_ERROR;
                this.tokenId = TPSPasToken.CSTIINT_COMMENT;
                this.tokenLength = 1;
            }
        }
        return result;
    }
}


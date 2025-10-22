/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ErrorInfo
 *  com.jiuqi.bi.syntax.parser.NodeToken
 *  org.antlr.runtime.CharStream
 *  org.antlr.runtime.EarlyExitException
 *  org.antlr.runtime.Lexer
 *  org.antlr.runtime.MismatchedNotSetException
 *  org.antlr.runtime.MismatchedRangeException
 *  org.antlr.runtime.MismatchedSetException
 *  org.antlr.runtime.MismatchedTokenException
 *  org.antlr.runtime.NoViableAltException
 *  org.antlr.runtime.RecognitionException
 *  org.antlr.runtime.RecognizerSharedState
 */
package com.jiuqi.np.dataengine.parse.link;

import com.jiuqi.bi.syntax.parser.ErrorInfo;
import com.jiuqi.bi.syntax.parser.NodeToken;
import java.util.ArrayList;
import java.util.List;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedNotSetException;
import org.antlr.runtime.MismatchedRangeException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class BaseANTLRLexer
extends Lexer {
    private List<ErrorInfo> errors = new ArrayList<ErrorInfo>();

    public BaseANTLRLexer() {
    }

    public BaseANTLRLexer(CharStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public void mTokens() throws RecognitionException {
    }

    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        String msg = this.getErrorMessage(e, tokenNames);
        NodeToken token = new NodeToken(this.getCharErrorDisplay(e.c), e.line, e.charPositionInLine, e.index);
        this.errors.add(new ErrorInfo(token, msg));
    }

    public String getErrorMessage(RecognitionException e, String[] tokenNames) {
        String msg = null;
        if (e instanceof MismatchedTokenException) {
            MismatchedTokenException mte = (MismatchedTokenException)e;
            msg = this.getCharErrorDisplay(e.c) + "\u4e0d\u5e94\u51fa\u73b0\u5728\u6b64\u5904\uff0c\u9700\u8981\u4e00\u4e2a" + this.getCharErrorDisplay(mte.expecting);
        } else if (e instanceof NoViableAltException) {
            msg = this.getCharErrorDisplay(e.c) + "\u5728\u8bed\u6cd5\u6587\u4ef6\u4e2d\u672a\u5b9a\u4e49";
        } else if (e instanceof EarlyExitException) {
            msg = this.getCharErrorDisplay(e.c) + "\u4e0d\u5e94\u51fa\u73b0\u5728\u6b64\u5904\uff0c\u9700\u8981\u4e00\u4e2a";
        } else if (e instanceof MismatchedNotSetException) {
            MismatchedNotSetException mse = (MismatchedNotSetException)e;
            msg = this.getCharErrorDisplay(e.c) + "\u4e0d\u5e94\u51fa\u73b0\u5728\u6b64\u5904\uff0c\u9700\u8981\u4e00\u4e2a" + mse.expecting;
        } else if (e instanceof MismatchedSetException) {
            MismatchedSetException mse = (MismatchedSetException)e;
            msg = this.getCharErrorDisplay(e.c) + "\u4e0d\u5e94\u51fa\u73b0\u5728\u6b64\u5904\uff0c\u9700\u8981\u4e00\u4e2a" + mse.expecting;
        } else if (e instanceof MismatchedRangeException) {
            MismatchedRangeException mre = (MismatchedRangeException)e;
            msg = this.getCharErrorDisplay(e.c) + "\u4e0d\u5e94\u51fa\u73b0\u5728\u6b64\u5904\uff0c\u9700\u8981" + this.getCharErrorDisplay(mre.a) + ".." + this.getCharErrorDisplay(mre.b);
        } else {
            msg = super.getErrorMessage(e, tokenNames);
        }
        return msg;
    }

    public List<ErrorInfo> errorParserTokenSet() {
        return this.errors;
    }
}


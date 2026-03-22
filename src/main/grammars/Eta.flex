package dev.varik.eta.parsing;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static dev.varik.eta.parsing.EtaTokenTypes.*;

%%

%public
%class _EtaLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType

%state IN_ETA_TAG_START
%state IN_ETA_TAG

%%

// ========== YYINITIAL: outside Eta tags (HTML content) ==========
<YYINITIAL> {
    "<%_"                   { yybegin(IN_ETA_TAG_START); return OPEN_TAG; }
    "<%-"                   { yybegin(IN_ETA_TAG_START); return OPEN_TAG; }
    "<%"                    { yybegin(IN_ETA_TAG_START); return OPEN_TAG; }
    [^<]+                   { return TEMPLATE_DATA; }
    "<" [^%]                { return TEMPLATE_DATA; }
    "<"                     { return TEMPLATE_DATA; }
}

// ========== IN_ETA_TAG_START: check for tag type modifier ==========
<IN_ETA_TAG_START> {
    "="                     { yybegin(IN_ETA_TAG); return EQUALS; }
    "~"                     { yybegin(IN_ETA_TAG); return TILDE; }
    [^]                     { yypushback(1); yybegin(IN_ETA_TAG); }
}

// ========== IN_ETA_TAG: inside Eta tag, JavaScript content ==========
<IN_ETA_TAG> {
    "_%>"                   { yybegin(YYINITIAL); return CLOSE_TAG; }
    "-%>"                   { yybegin(YYINITIAL); return CLOSE_TAG; }
    "%>"                    { yybegin(YYINITIAL); return CLOSE_TAG; }
    [^%_\-]+                { return INNER_JS; }
    "%" [^>]                { return INNER_JS; }
    "_" [^%]                { return INNER_JS; }
    "-" [^%]                { return INNER_JS; }
    "%"                     { return INNER_JS; }
    "_"                     { return INNER_JS; }
    "-"                     { return INNER_JS; }
}

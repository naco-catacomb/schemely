package org.jetbrains.plugins.scheme.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.scheme.lexer.SchemeLexer;
import org.jetbrains.plugins.scheme.lexer.Tokens;

import java.util.HashMap;
import java.util.Map;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class SchemeSyntaxHighlighter extends SyntaxHighlighterBase implements Tokens
{
  private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<IElementType, TextAttributesKey>();

  @NotNull
  public Lexer getHighlightingLexer()
  {
    return new SchemeLexer();
  }

  @NotNull
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType)
  {
    return pack(ATTRIBUTES.get(tokenType));
  }

  @NonNls
  static final String COMMENT_ID = "Scheme Comment";
  @NonNls
  static final String IDENTIFIER_ID = "Identifier";
  @NonNls
  static final String NUMBER_ID = "Scheme Numbers";
  @NonNls
  static final String STRING_ID = "Scheme Strings";
  @NonNls
  static final String BAD_CHARACTER_ID = "Bad character";
  @NonNls
  static final String BRACES_ID = "Scheme Braces";
  @NonNls
  static final String PAREN_ID = "Scheme Parentheses";
  @NonNls
  static final String LITERAL_ID = "Scheme Literal";
  @NonNls
  static final String CHAR_ID = "Scheme Character";
  @NonNls
  static final String QUOTED_ID = "Quoted text";
  @NonNls
  static final String KEYWORD_ID = "Keyword";
  @NonNls
  static final String SPECIAL_ID = "Special";


  // Registering TextAttributes
  static
  {
    createTextAttributesKey(COMMENT_ID, defaultFor(SyntaxHighlighterColors.LINE_COMMENT));
    createTextAttributesKey(IDENTIFIER_ID, defaultFor(SyntaxHighlighterColors.KEYWORD));
    createTextAttributesKey(NUMBER_ID, defaultFor(SyntaxHighlighterColors.NUMBER));
    createTextAttributesKey(STRING_ID, defaultFor(SyntaxHighlighterColors.STRING));
    createTextAttributesKey(BRACES_ID, defaultFor(SyntaxHighlighterColors.BRACES));
    createTextAttributesKey(PAREN_ID, defaultFor(SyntaxHighlighterColors.PARENTHS));
    createTextAttributesKey(LITERAL_ID, defaultFor(SyntaxHighlighterColors.KEYWORD));
    createTextAttributesKey(CHAR_ID, defaultFor(SyntaxHighlighterColors.STRING));
    createTextAttributesKey(BAD_CHARACTER_ID, defaultFor(HighlighterColors.BAD_CHARACTER));
    createTextAttributesKey(QUOTED_ID, defaultFor(HighlighterColors.TEXT));
    createTextAttributesKey(KEYWORD_ID, defaultFor(SyntaxHighlighterColors.KEYWORD));
    createTextAttributesKey(SPECIAL_ID, defaultFor(SyntaxHighlighterColors.KEYWORD));
  }

  public static TextAttributesKey LINE_COMMENT = createTextAttributesKey(COMMENT_ID);
  public static TextAttributesKey IDENTIFIER = createTextAttributesKey(IDENTIFIER_ID);
  public static TextAttributesKey NUMBER = createTextAttributesKey(NUMBER_ID);
  public static TextAttributesKey STRING = createTextAttributesKey(STRING_ID);
  public static TextAttributesKey BRACE = createTextAttributesKey(BRACES_ID);
  public static TextAttributesKey PAREN = createTextAttributesKey(PAREN_ID);
  public static TextAttributesKey LITERAL = createTextAttributesKey(LITERAL_ID);
  public static TextAttributesKey CHAR = createTextAttributesKey(CHAR_ID);
  public static TextAttributesKey BAD_CHARACTER = createTextAttributesKey(BAD_CHARACTER_ID);
  public static TextAttributesKey QUOTED = createTextAttributesKey(QUOTED_ID);
  public static TextAttributesKey KEYWORD = createTextAttributesKey(KEYWORD_ID);
  public static TextAttributesKey SPECIAL = createTextAttributesKey(SPECIAL_ID);

  static
  {
    fillMap(ATTRIBUTES, LINE_COMMENT, Tokens.COMMENT);
    fillMap(ATTRIBUTES, NUMBER, NUMBER_LITERAL);
    fillMap(ATTRIBUTES, Tokens.STRINGS, STRING);
    fillMap(ATTRIBUTES, BRACE, Tokens.LEFT_SQUARE, Tokens.RIGHT_SQUARE, Tokens.LEFT_CURLY, Tokens.RIGHT_CURLY);
    fillMap(ATTRIBUTES, PAREN, Tokens.LEFT_PAREN, Tokens.RIGHT_PAREN);
    fillMap(ATTRIBUTES, LITERAL, Tokens.BOOLEAN_LITERAL);
    fillMap(ATTRIBUTES, CHAR, Tokens.CHAR_LITERAL);
    fillMap(ATTRIBUTES, SPECIAL, Tokens.SPECIAL);
  }

  private static TextAttributes defaultFor(TextAttributesKey key)
  {
    return key.getDefaultAttributes();
  }
}

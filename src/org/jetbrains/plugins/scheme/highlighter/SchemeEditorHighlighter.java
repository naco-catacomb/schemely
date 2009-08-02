package org.jetbrains.plugins.scheme.highlighter;

import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.util.LexerEditorHighlighter;

/**
 * @author ilyas
 */
public class SchemeEditorHighlighter extends LexerEditorHighlighter
{
  public SchemeEditorHighlighter(EditorColorsScheme scheme)
  {
    super(new SchemeSyntaxHighlighter(), scheme);
  }
}

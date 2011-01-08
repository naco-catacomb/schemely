package org.jetbrains.plugins.scheme.repl.actions;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;

public class ExecuteStatementAction extends EditorAction
{
  public static String ID = "SchemeExecuteStatementAction";

  protected ExecuteStatementAction()
  {
    super(new MyHandler());
  }

  public void update(Editor editor, Presentation presentation, DataContext dataContext)
  {
    super.update(editor, presentation, dataContext);
  }

  private static class MyHandler extends EditorWriteActionHandler
  {
    public boolean isEnabled(Editor editor, DataContext dataContext)
    {
      return false;
    }

    public void executeWriteAction(Editor editor, DataContext dataContext)
    {
    }
  }
}

/* Location:           /Users/colin/Library/Application Support/IntelliJIdea10CE/scheme-plugin/lib/scheme-intellij-plugin.jar
 * Qualified Name:     org.jetbrains.plugins.scheme.repl.actions.ExecuteStatementAction
 * JD-Core Version:    0.6.0
 */

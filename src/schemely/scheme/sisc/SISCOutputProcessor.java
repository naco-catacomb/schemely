package schemely.scheme.sisc;

import schemely.repl.LanguageConsoleImpl;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.util.text.StringUtil;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.RunAutomaton;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NonNls;
import schemely.repl.SchemeConsole;
import schemely.utils.Editors;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Colin Fleming
 */
public class SISCOutputProcessor
{
  private static final RunAutomaton ERRORS = errors();

  enum State
  {
    STARTING, EXECUTING
  }

  private State state = State.STARTING;

  @NonNls
  @Language("RegExp")
  public static final String SISC_PROMPT = "#;>\\s*";
  public static final Pattern SISC_PROMPT_PATTERN = Pattern.compile(SISC_PROMPT);

  private final SchemeConsole console;

  private final StringBuilder buffer = new StringBuilder();

  public SISCOutputProcessor(SchemeConsole console)
  {
    this.console = console;
  }

  public void processOutput(String text)
  {
    if (text != null)
    {
      boolean sawPrompt = false;

      String trimmed = text;
      Matcher matcher = SISC_PROMPT_PATTERN.matcher(trimmed);
      while (matcher.lookingAt())
      {
        sawPrompt = true;
        String prefix = matcher.group();
        trimmed = StringUtil.trimStart(trimmed, prefix);
        matcher.reset(trimmed);
      }

      if (sawPrompt)
      {
        if (state == State.STARTING)
        {
          state = State.EXECUTING;
        }
        else
        {
          flush();
        }
        buffer.append(trimmed);
      }
      else
      {
        if (state == State.STARTING)
        {
          LanguageConsoleImpl.printToConsole(console, trimmed, ConsoleViewContentType.NORMAL_OUTPUT, null);
          Editors.scrollDown(console.getHistoryViewer());
        }
        else
        {
          buffer.append(trimmed);
        }
      }
    }
  }

  public void flush()
  {
    String output = buffer.toString();
    ConsoleViewContentType style = ConsoleViewContentType.NORMAL_OUTPUT;
    if (looksLikeError(output))
    {
      style = ConsoleViewContentType.ERROR_OUTPUT;
    }
    LanguageConsoleImpl.printToConsole(console, output, style, null);
    Editors.scrollDown(console.getHistoryViewer());
    buffer.setLength(0);
  }

  static boolean looksLikeError(String output)
  {
    int length = ERRORS.run(output, 0);
    return length > 0;
  }

  private static RunAutomaton errors()
  {
    Automaton errors = BasicOperations.union(Arrays.asList(automaton("Error\\.[ \t]*\n"),
                                                           automaton("Error in .+\\.[ \t]*\n"),
                                                           automaton("Error:[^\n]+\n"),
                                                           automaton("Error in .+: [^\n]+\n"),
                                                           automaton("Error in nested call\\.[ \t]*\n"),
                                                           automaton("Error in nested call: [^\n]+\n"),
                                                           automaton("Error in nested call from .+\\.[ \t]*\n"),
                                                           automaton("Error in nested call from .+: [^\n]+\n")));
    return new RunAutomaton(errors);
  }

  private static Automaton automaton(String regexp)
  {
    return new RegExp(regexp).toAutomaton();
  }
}

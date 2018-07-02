package Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;

/**
 * Versucht mit commons-exec einen Editor zu starten
 *
 * @author Stefan
 */
public class ExecEditor implements Runnable {

  private final BlockingQueue queue;
  private final String textfile;
  //Exe Datei gefunden alle weiteren Editioren werden  nicht verwendet
  private boolean exeFilefound;

  public ExecEditor(BlockingQueue queue, String textfile) {
    this.queue = queue;
    this.textfile = textfile;
    exeFilefound = false;
  }

  /**
   * Öffnet die CSV Datei mit dem angegebenen Editor
   *
   * @param exeFile
   */
  private void openCSVinEditor(String exeFile) throws UnsupportedOperationException {
    if (exeFilefound == false) {
      try {
        Path filePath = Paths.get(textfile);

        String line = exeFile + " \"" + filePath.toAbsolutePath() + "\"";
        CommandLine cmdLine = CommandLine.parse(line);
        DefaultExecutor executor = new DefaultExecutor();

        executor.execute(cmdLine, new ExecuteResultHandler() {
          @Override
          public void onProcessFailed(ExecuteException ee) {
            throw new UnsupportedOperationException("Not found: " + exeFile);
          }

          @Override
          public void onProcessComplete(int i) {
            exeFilefound = true;
          }

        });
      } catch (IOException ex) {
      }
    }
  }

  @Override
  public void run() {
    for (Iterator iterator = queue.iterator(); iterator.hasNext();) {
      String exeFile = (String) iterator.next();
      openCSVinEditor(exeFile);
    }
  }

}

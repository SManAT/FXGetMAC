package Utils;

import SH.FX.TextFlowLogger.TextFlowLogger;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 *
 * @author hagmann
 */
public class WaitNClose extends Task<Void>{

	private final TextFlowLogger textflowLogger;
	private int i;

	public WaitNClose(TextFlowLogger textflowLogger) {
		this.textflowLogger = textflowLogger;
	}

  @Override
  protected Void call() throws Exception {
    i=3;
    while(i>0){
			Platform.runLater(()->{
				textflowLogger.AppendInline(i+" ");
			});
      updateMessage(i+"");
      Thread.sleep(1000);
      i--;
    }
		Platform.runLater(()->{
			textflowLogger.AppendInline("Closing ... ");
		});
    return null;
  }
  
}

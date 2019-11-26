import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FilePrintingOfOuput implements PrintingOfOutput {
	private boolean isInitialized = false;

	private FileWriter getFileWriter() throws PrintingOutputException {
		FileWriter fileWriter;
		try {
			if (isInitialized) {
				fileWriter = new FileWriter("ShoppingList file.txt", true); // append.
			} else {
				fileWriter = new FileWriter("ShoppingList file.txt");
				isInitialized = true;
			}
		} catch (IOException e) {
			throw new PrintingOutputException(e.getMessage());
		}
		return fileWriter;
	}

	@Override
	public void printOutLn(String fileContent) throws PrintingOutputException {
		FileWriter fileWriter = getFileWriter();
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(fileContent); 
		printWriter.close();
	}

	@Override
	public void printOut(String fileContent) throws PrintingOutputException {
		FileWriter fileWriter = getFileWriter();
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(fileContent);
		printWriter.close();

	}

}


public class ConsolePrintingOfOutput implements PrintingOfOutput {

	@Override
	public void printOutLn(String output) throws PrintingOutputException {
		System.out.println(output);

	}

	@Override
	public void printOut(String output) throws PrintingOutputException {
		System.out.print(output);

	}

}

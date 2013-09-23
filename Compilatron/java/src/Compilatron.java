import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

public class Compilatron {
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();

		File file = null;
		try {
			chooser.showOpenDialog(null);
			file = chooser.getSelectedFile();
		}
		catch(Exception e) {
			Util.printError("Could not open file: " + e);
			System.exit(1);
		}

		Compiler compiler = null;
		try {
			compiler = new Compiler(file);
		}
		catch(FileNotFoundException e) {
			Util.printError("Could not open file: " + e);
			System.exit(1);
		}

		int[] memory = null;
		try {
			memory = compiler.compile();
		}
		catch(OutOfMemoryException e) {
			Util.printError("Error: Program can only learn 100 moves");
			System.exit(3);
		}
		catch(ArgumentException e) {
			Util.printError("Error: Lonely if statement at " + compiler.getLineNumber());
			System.exit(4);
		}
		catch(InvalidVariableException e) {
			Util.printError("Error: Poor naming choice at " + compiler.getLineNumber());
			System.exit(4);
		}
		catch(NumberFormatException e) {
			Util.printError("Error: Letters != Numbers at " + compiler.getLineNumber());
			System.exit(4);
		}
		catch(SyntaxException e) {
			Util.printError("Error: You dun goofed at " + compiler.getLineNumber());
			System.exit(4);
		}
		catch(GotoException e) {
			Util.printError("Error: Tried to goto a nonexistent line at " + compiler.getLineNumber());
			System.exit(5);
		}
		catch(LineNumberException e) {
			Util.printError("Error: Clever, but you can't go back in time at " + compiler.getLineNumber());
			System.exit(5);
		}
		catch(UndefinedVariableException e) {
			Util.printError("Error: Identity crisis at " + compiler.getLineNumber());
			System.exit(5);
		}

		PrintWriter output = null;
		try {
			chooser.showOpenDialog(null);
			output = new PrintWriter(new FileWriter(chooser.getSelectedFile()));
		}
		catch(IOException e) {
			Util.printError("Could not open file: " + e);
			System.exit(1);
		}

		for(int i = 0; i <= compiler.getPointer() / 10; i++) {
			output.print(memory[i * 10]);
			for(int ii = 1; ii < 10; ii++)
				output.print(" " + memory[i * 10 + ii]);
			output.println();
		}
		output.close();
	}
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Lesson3Streams {

	//Source file
	static Path path = Paths.get("resources/JobResult_124432.txt");
	
	public static void main(String[] args) throws IOException {
		
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(new String( Files.readAllBytes(path) ).split("[,\\n]")));
		
		long streamResult;
		long paraStreamResult;
		int x = 1;
		do {
			System.out.println("Try " + x);
			
			//Regular Stream
			long streamStart = System.currentTimeMillis();
			words.stream().filter(w -> w.matches(".*00000000\\p{XDigit}{8}.*")).count();
			streamResult = System.currentTimeMillis() - streamStart;
			System.out.println("Millisecs using stream: " + streamResult);
			
			//Parallel Stream
			long paraStreamStart = System.currentTimeMillis();
			words.parallelStream().filter(w -> w.matches(".*00000000\\p{XDigit}{8}.*")).count();
			paraStreamResult = System.currentTimeMillis() - paraStreamStart;
			System.out.println("Millisecs using parellelStream: " + paraStreamResult);

			//If paraStream won
			if(streamResult > paraStreamResult) {
				System.out.println("Results: parallelStream was " + (streamResult - paraStreamResult) + " faster than stream.\nAll done!");
				break;
			}
			
			//Else continue
			System.out.println("Results: stream was " + (paraStreamResult - streamResult) + "faster than parallelStream");
			System.out.println("Doubling size and trying again");

			words.addAll(words);
			x++;
			
		} while ( true );

	}

}

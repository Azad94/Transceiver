import java.util.Scanner;

public class Transmitter{
	private UDPSocket mySock;
	private Scanner input;
	
	public Transmitter(UDPSocket sock){
		input = new Scanner(System.in);
		mySock = sock;
	}

	public void run(){
		System.out.println("ready to send");
		
		while(input.hasNextLine()){
			mySock.send(input.nextLine());
		}
		mySock.send(""+(char) 4);
		
		try{
			mySock.close();
			System.out.println("End of Transmission");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}

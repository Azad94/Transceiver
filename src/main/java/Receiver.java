
public class Receiver{
	private UDPSocket mySock;
	private int maxBytes;
	private int timeOutInSec;
	
	public Receiver(UDPSocket sock){
		mySock = sock;
		maxBytes = 512;
		timeOutInSec = 0;
	}
	
	public Receiver(UDPSocket sock,int maxB){
		mySock = sock;
		maxBytes = maxB;
		timeOutInSec = 0;
	}
	
	public Receiver(UDPSocket sock,int maxB, int timeOutSec){
		mySock = sock;
		maxBytes = maxB;
		timeOutInSec = timeOutSec;
	}
	
	public void run(){
		String rec = "";
		System.out.println("ready to receive");
		
		while(true){
			rec = mySock.receive(maxBytes, timeOutInSec);
			
			if(rec != null && rec.length() > 0)	{
				if(rec.charAt(0) == (char) 4){
					try{
						mySock.close();
						System.out.println("End of Transmission");
						break;
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
				else{
					System.out.println(rec);
				}
			}
			else{
				if(rec == null){ }
				else{
					System.out.println(rec);
				}
			}
		}
	}
}
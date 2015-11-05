import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPSocket implements AutoCloseable{
	
	private InetAddress addr;
	private int port;
	private DatagramSocket sock;
	private DatagramPacket pkt;
	private byte [] buf;
	private int bufSize;
	
	public UDPSocket(int portIn){
		
		try{
			port = portIn;
			sock = new DatagramSocket(port);
		}
		catch (SocketException e){
			e.printStackTrace();
		}
	}
	
	public UDPSocket(String addrIn,int portIn){
		
		try{
			addr = InetAddress.getByName(addrIn);
			port = portIn;
			sock = new DatagramSocket();
		}
		catch (UnknownHostException | SocketException e){
			e.printStackTrace();
		}
	}
	
	public UDPSocket(InetAddress addrIn,int portIn){
		
		addr = addrIn;
		port = portIn;
		try{
			sock = new DatagramSocket();
			sock.connect(addrIn, portIn);
		}
		catch (SocketException e){
			e.printStackTrace();
		}
	}
	
	public boolean send(String s){
		
		boolean ret = false;
		buf = s.getBytes();
		bufSize = buf.length;
		pkt = new DatagramPacket(buf, bufSize, addr, port);
		
		try{
			sock.send(pkt);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return ret;
	}
	
	public String receive(int maxBytes, int timeoutSec){
		
		String ret = "";
		buf = new byte[maxBytes];
		bufSize = buf.length;
		pkt = new DatagramPacket(buf, bufSize);
		
		try{
			sock.setSoTimeout(timeoutSec*1000);
			sock.receive(pkt);
			

			if(!sock.isConnected()){
				sock.connect(pkt.getAddress(), pkt.getPort());
			}
			
			ret = new String(pkt.getData(), 0, pkt.getLength());	
		}
		catch (IOException e){
			ret = null;
		}
		return ret;
	}
	
	public void connect(InetAddress address, int portListen){
		sock.connect(address, portListen);
	}
	
	public boolean isConnected(){
		return sock.isConnected();
	}
	
	public void disconnect(){
		sock.disconnect();
	}

	@Override
	public void close() throws Exception{
		sock.close();		
	}
}
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

class ConvoMaker2 extends Frame implements ActionListener
{
	Button X,SEND;
	TextField T1,T2;
	Label L1,L2,title;
	String state="";
	Socket myserver;
	Socket client;
	ServerSocket ss;
	int run=1;
	ConvoMaker2(){
		setTitle("ConvoMaker2");
		setBackground(Color.GREEN);
		title=new Label("ConvoMaker2");
		title.setFont(new Font("Arial", Font.PLAIN, 30));
		title.setBounds(600,50,500,50);
		add(title);
		X=new Button("X");
		SEND=new Button("SEND");
		T1=new TextField(0);
		T2=new TextField(0);
		T1.setFont(new Font("Arial", Font.PLAIN, 18));
		T2.setFont(new Font("Arial", Font.PLAIN, 18));
		L1=new Label("Type:");
		L2=new Label("Received:");
		X.setBounds(1300,40,20,20);
		X.setBackground(Color.RED);
		add(X);
		X.addActionListener(this);
		T1.setBounds(800,500,400,100);
		add(T1);
		T2.setBounds(300,200,400,100);
		add(T2);
		L1.setBounds(750,500,40,20);
		add(L1);
		L2.setBounds(200,200,60,20);
		add(L2);
		SEND.setBounds(1200,500,40,20);
		add(SEND);
		SEND.setBackground(Color.CYAN);
		SEND.addActionListener(this);	
		setSize(800,700);
		setLayout(null);
		setVisible(true);
		try{
				ss= new ServerSocket(5555);
            			ss.setSoTimeout(10000);
			}
		catch (IOException ex) {
                		ex.printStackTrace();
           		}
		
		startServer();
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==X){
			System.exit(0);
		}
		if(e.getSource()==SEND){
			try{
				client=new Socket("localhost",4444);
				DataOutputStream dout=new DataOutputStream(client.getOutputStream());
				String mesg=T1.getText();
				dout.writeUTF(mesg);
				client.close();
			}
			catch (IOException ex) {
                		ex.printStackTrace();
           		}
		}
		
		
	}

	void startServer() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket server = ss.accept();
                    DataInputStream din = new DataInputStream(server.getInputStream());
                    String mesg = din.readUTF();
                    T2.setText(mesg); 
                    din.close();
                    server.close();
                } catch (SocketTimeoutException ex) {
                    System.out.println("Timeout occurred. No connections within the specified time.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
	public static void main(String[] argu)
	{
		
		new ConvoMaker2();
	}
}

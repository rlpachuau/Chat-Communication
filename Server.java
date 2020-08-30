import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Date;
import java.io.*;

public class ChatServer2 extends Frame implements ActionListener,Runnable  {
	
	private Label label1,label2;
	private Button button1;
	private TextArea textarea1,textarea2;
	ServerSocket socket;
	Socket insocket;
	int port=13;
	Thread thread;
	
   	ServerSocket server;
	Socket connection=null;
	
	InputStream in;
	OutputStream out;
	
		
	public static void main(String[] arg)
	{
		new ChatServer2();
	}
	public ChatServer2()
	{
		setLayout(null);
		
		label2=new Label("Chat Server");
		label2.setBounds(100,35,200,30);
		label2.setFont(new Font("Times New Roman",Font.BOLD,36));
        add(label2);
        
		setSize(380,340);
		setResizable(false);
        setVisible(true);
        setTitle("Chat Communication System | Server");
        
        button1 = new Button("Send");
        button1.setBounds(300, 290, 60, 20);
        add(button1);
        button1.addActionListener(this);
        
        textarea1 = new TextArea("", 7, 45, TextArea.SCROLLBARS_VERTICAL_ONLY);
        textarea1.setBounds(20, 80, 340, 100);
        //textarea1.setText("Hi");
        add(textarea1); 
        
        label1=new Label();
        label1.setBounds(20,210,100,20);
        label1.setText("Type Here");
        add(label1);
        
        textarea2 = new TextArea("", 7, 45, TextArea.SCROLLBARS_NONE);
        textarea2.setBounds(20, 240, 340, 40);
        textarea2.setForeground(Color.RED);
        add(textarea2); 
        
        this.addWindowListener(new WindowAdapter()
        {
        	public void windowClosing(WindowEvent e)
        	{
        		System.exit(0);
        		try
        		{
        			socket.close();
        		}catch(Exception ex){}
        	}
        });
    
        try 
    	{
    		socket = new ServerSocket(port);
    		insocket = null;
    		
    			try {
    				insocket = socket.accept( );
    				//OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream( ));
    				in=insocket.getInputStream();
    				out=insocket.getOutputStream();
    				
    				thread=new Thread(this);
    				thread.start();

    				
    			}catch (IOException e) {}
    		
    	}catch (IOException e)
    	{
    		System.err.println(e);
    	}
	}
	
	public void actionPerformed(ActionEvent event)
    {
		if(event.getSource()==button1)
		{
			try
			{
				String str = textarea2.getText() + "\n";
	            byte buffer[] = str.getBytes();
	            out.write(buffer);
	            textarea1.setForeground(Color.RED);
	            textarea1.append(str+"\n");
	            textarea2.setText("");
	            textarea2.requestFocus();
			}catch(Exception e){}
		}
			
    }
	
	public void run()
	{
		String instring;
        try {

            BufferedReader in = new BufferedReader (new InputStreamReader(insocket.getInputStream()));

            textarea1.setForeground(Color.BLUE);
            
            while((instring = in.readLine()) != null){
                textarea1.append(instring + "\n");
            }

        }catch (Exception e) 
        {
            textarea1.setText(e.getMessage());
        }
		
	}
}

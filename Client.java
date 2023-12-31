import java.net.*;
import java.io.*;


public class Client {

    Socket socket;
    BufferedReader br;   //read
    PrintWriter out;     //write

    public Client(){
        try{
            System.out.println("Sending request to server");
            socket= new Socket("127.0.0.1",7777);   //to get connection to server
            System.out.println("connection done.");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());


            startReading();
            startWriting();
        }catch(Exception e){

        }
    }


    public void startReading(){

        //thread-- read karke deta rahega
        Runnable r1=()->{
            System.out.println("reader started..");
            try{
                while(true){
                    String msg=br.readLine();
                    if(msg.equals("exit")){     //only end when client write exit
                        System.out.println("Serve terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server : " + msg);
                }
            }catch(Exception e){
                // e.printStackTrace();
                System.out.println("Connection is closed");
            }
        };

        new Thread(r1).start();    //calling the thread
    }

    public void startWriting(){

        // thread-- data user se lega and then send karega client tak
        Runnable r2=()->{
            System.out.println("writer started...");
            try{
            while(!socket.isClosed()){
                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                    out.println(content);
                    out.flush();
                    
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                }
                System.out.println("Connection is closed");
            }catch(Exception e){
                e.printStackTrace();
            }
        };

        new Thread(r2).start();
    }



        public static void main(String[] args) {
            System.out.println("This is client...");
            new Client();
        }
}

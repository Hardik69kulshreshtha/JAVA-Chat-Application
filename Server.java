import java.net.*;
import java.io.*;
class Server {

    ServerSocket server;  //server
    Socket socket;        //client

    BufferedReader br;   //read
    PrintWriter out;     //write


    //Constructor..
    public Server(){

        try{
            server=new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting...");
            socket=server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }catch (Exception e){
            e.printStackTrace();   //used to print exception while traceing on console
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
                        System.out.println("Client terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Client : " + msg);
                }
            }catch(Exception e){
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
            }catch(Exception e){
                System.out.println("Connection is closed");
            }
        };

        new Thread(r2).start();
    }



    public static void main(String[] args) {
        System.out.println("this is server..going to start server");
        new Server();
    }

}

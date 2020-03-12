package HomeWork6.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public Vector<ClientHandler> clients;
    private FileOutputStream outFile;
    private Logger logger;
    String[] censList;

    public Server(Logger logger) {
        this.logger = logger;
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;
        try {
            AuthService.connect();
            logger.log(Level.SEVERE,"Connected to DateBase.");
            server = new ServerSocket(8589);
            System.out.println("Сервер запущен. Ожидаем клиентов...");
            logger.log(Level.SEVERE,"Сервер запущен. Ожидаем клиентов...");
            loadCensFile("cens.txt"); // загрузка цензуры
            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Start Server. " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Close socket" + e.getMessage());
            }
            try {
                server.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Close server " + e.getMessage());
            }
            AuthService.disconnect();
            logger.log(Level.SEVERE,"Disconnected from DateBase.");
        }

        try {
            outFile = new FileOutputStream("logChat.txt");
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE,"Chat text logger. " + e.getMessage());
        }
    }

    public void sendPersonalMsg(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nickTo)) {
                o.sendMsg("from " + from.getNick() + ": " + msg);
                from.sendMsg("to " + nickTo + ": " + msg);
                logger.log(Level.SEVERE,"MESSAGE: from " + from.getNick() + " to " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMsg("Клиент с ником " + nickTo + " не найден в чате");
    }

    public void broadcastMsg(ClientHandler from, String msg) {
        for (ClientHandler o : clients) {
            if (!o.checkBlackList(from.getNick())) {
                o.sendMsg(msg);
            }
        }
        logger.log(Level.SEVERE,"MESSAGE: " + msg);
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public void broadcastClientsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientslist ");
        for (ClientHandler o : clients) {
            sb.append(o.getNick() + " ");
        }
        String out = sb.toString();
        for (ClientHandler o : clients) {
            o.sendMsg(out);
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
        logger.log(Level.SEVERE,"Client "+ client.getNick() + " connect");
        broadcastClientsList();
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        logger.log(Level.SEVERE,"Client "+ client.getNick() + " disconnect");
        broadcastClientsList();
    }

    public void loadCensFile(String fileName) {
        try {
            BufferedReader inStream = null;
            inStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            //inStream = new BufferedReader(new InputStreamReader(new FileInputStream("fkghdasf.txt"), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            builder.append((char) inStream.read());
            char c;
            String string = "";
            while ((string = inStream.readLine())!=null){
                builder.append(string+"\n");
            }
            inStream.close();
            string = builder.toString();
            censList = string.split("\n");
            logger.log(Level.SEVERE,"File censure is loaded");
        } catch (IOException e) {
            logger.log(Level.SEVERE,e.getMessage());
        }
    }

    public String censMsg(String msg) {
        String censWord = "";
        String charCens = "*цензура*";
        if (censList!=null) {
            for (int i = 0; i < censList.length; i++) {
                censWord = censList[i];
                msg = msg.replaceAll(censWord,charCens);
            }
        }
        return msg;
    }
}

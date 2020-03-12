package HomeWork6.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nick;

    List<String> blackList;

    public String getNick() {
        return nick;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.blackList = new ArrayList<>();
            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/signUp")) { // /signUp textLogin textPass textNick
                            String[] tokens = str.split(" ");
                            String newNick = AuthService.addUser(tokens[1], tokens[2], tokens[3]);
                            if (newNick != null) {
                                sendMsg("Учетная запись успешно зарегестрирована");
                            } else {
                                sendMsg("Учетная запись уже используется");
                            }
                        }
                        else if (str.startsWith("/auth")) { // /auth login72 pass72
                            String[] tokens = str.split(" ");
                            String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                            if (newNick != null) {
                                if (!server.isNickBusy(newNick)) {
                                    sendMsg("/authok");
                                    nick = newNick;
                                    server.subscribe(this);
                                    break;
                                } else {
                                    sendMsg("Учетная запись уже используется");
                                }
                            } else {
                                sendMsg("Неверный логин/пароль");
                            }
                        }
                    }
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                out.writeUTF("/serverclosed");
                                break;
                            }
                            if (str.startsWith("/newnick ")) { // /newnick nick3
                                String[] tokens = str.split(" ");
                                blackList.remove(tokens[1]);
                                String newNick = AuthService.updateNick(this.nick, tokens[1]);
                                if (newNick != null) {
                                    sendMsg("Nick успешно изменен");
                                    this.nick = newNick;
                                    server.broadcastClientsList();
                                } else {
                                    sendMsg("Этот ник уже используется");
                                }
                            }
                            if (str.startsWith("/w ")) { // /w nick3
                                String[] tokens = str.split(" ", 3);
                                String m = str.substring(tokens[1].length() + 4);
                                server.sendPersonalMsg(this, tokens[1], tokens[2]);
                            }
                            if (str.startsWith("/blacklist ")) { // /blacklist nick3
                                String[] tokens = str.split(" ");
                                blackList.add(tokens[1]);
                                sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                            }
                            if (str.startsWith("/fromblacklist ")) { // /fromblacklist nick3
                                String[] tokens = str.split(" ");
                                blackList.remove(tokens[1]);
                                sendMsg("Вы удалили пользователя " + tokens[1] + " из черного списка");
                            }
                        } else {
                            server.broadcastMsg(this, nick + ": " + str);
                        }
                        System.out.println("Client: " + str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkBlackList(String nick) {
        return blackList.contains(nick);
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(server.censMsg(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

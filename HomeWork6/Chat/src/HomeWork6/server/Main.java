package HomeWork6.server;

import java.io.IOException;
import java.util.logging.*;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        Handler h = new FileHandler("MyServerLogger.log");
        h.setFormatter(new SimpleFormatter());
        logger.addHandler(h);
        new Server(logger);
    }
}

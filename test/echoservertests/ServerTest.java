package echoservertests;

import echoclient.EchoClient;
import echoserver.EchoServer;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ChristopherBorum
 */
public class ServerTest {
    
    public ServerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                EchoServer.main(new String[]{"localhost", "9090"});
            }
        }).start();
    }
    
    @AfterClass
    public static void tearDownClass() {
        EchoServer.stopServer();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void send() throws IOException {
        EchoClient client1 = new EchoClient();
        EchoClient client2 = new EchoClient();
        client1.connect("localhost",9090);
        client2.connect("localhost",9090);
        client1.send("Hello");
        assertEquals("HELLO", client1.receive());
        client2.send("Hello1");
        assertEquals("HELLO1", client2.receive());
    }
    
}

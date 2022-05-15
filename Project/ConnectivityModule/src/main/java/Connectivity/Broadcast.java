package Connectivity;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Broadcast implements Closeable {
    private final BroadcastSender sender;
    private final ScheduledExecutorService executor;
    private final int port;

    Broadcast(int port, int period) throws UnknownHostException, SocketException {
        this.port = port;
        sender = new BroadcastSender(port);

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(sender, 0, period, TimeUnit.SECONDS);
    }


    public Set<InetAddress> getAddresses(int timeout, boolean async) throws SocketException, UnknownHostException {
        BroadcastReceiver receiver = new BroadcastReceiver(port, timeout);

        if(async)
            receiver.start();
        else
            receiver.run();

        return receiver.getAddresses();
    }

    @Override
    public void close() throws IOException {
        sender.close();
        executor.shutdown();
    }

}
package george.resident.sync;

import connectivity.Peer;
import connectivity.connection.Connection;
import connectivity.exceptions.BroadcastFailedException;
import george.resident.exceptions.DeviceNotFound;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class PeerManager implements Closeable {
    private final Peer peer;
    private List<InetAddress> lastSearch = new ArrayList<>();
    private List<Connection> lastConnectedDevices = new ArrayList<>();

    public PeerManager(Peer peer) {
        this.peer = peer;
    }

    public Peer getPeer() {
        return peer;
    }

    public List<Connection> getConnectedDevices(){
        lastConnectedDevices = peer.getConnectedDevices();
        return lastConnectedDevices;
    }

    public List<InetAddress> findDevices() throws BroadcastFailedException {
        lastSearch = new ArrayList<>(peer.findDevices());
        return lastSearch;
    }

    public Connection getConnectedDevice(int index) throws DeviceNotFound {
        if(index < 0 || index >= lastConnectedDevices.size())
            throw new DeviceNotFound();

        return lastConnectedDevices.get(index);
    }

    public InetAddress getFoundDevice(int index) throws DeviceNotFound {
        if(index < 0 || index >= lastSearch.size())
            throw new DeviceNotFound();

        return lastSearch.get(index);
    }

    @Override
    public void close() throws IOException {
        peer.close();
    }
}

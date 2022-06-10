package george.resident.sync;

import connectivity.Peer;
import connectivity.exceptions.BroadcastFailedException;
import connectivity.exceptions.DeviceAlreadyConnectedException;
import george.resident.SynchronizedDirectory;
import george.resident.exceptions.BadSyncDirectory;
import george.resident.exceptions.DeviceNotFound;
import george.resident.tree.actions.DeleteDeal;
import george.resident.tree.actions.TreeActionsEnum;

import java.io.*;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.nio.file.Path;
import java.util.List;

public class ConnectivityResident {

    UpcomingFileThread thread;

    public ConnectivityResident(int port, Path directory) throws BadSyncDirectory, PortUnreachableException, SocketException {
        SynchronizedDirectory synchronizedDirectory = new SynchronizedDirectory(directory);
        ActionHandler actionHandler = new ActionHandler(synchronizedDirectory);
        Peer peer = new Peer(port);
        PeerManager peerManager = new PeerManager(peer);
        this.thread = new UpcomingFileThread(actionHandler, peerManager);
        this.thread.start();
    }

    public void setDirectory(Path directory) throws BadSyncDirectory {
        SynchronizedDirectory synchronizedDirectory = new SynchronizedDirectory(directory);
        ActionHandler actionHandler = new ActionHandler(synchronizedDirectory);
        PeerManager peerManager = thread.getPeerManager();
        thread.interrupt();

        this.thread = new UpcomingFileThread(actionHandler, peerManager);
        this.thread.start();
    }

    public void setPort(int port) throws PortUnreachableException, SocketException {
        Peer peer = new Peer(port);
        PeerManager peerManager = new PeerManager(peer);
        ActionHandler actionHandler = thread.getActionHandler();
        thread.interrupt();

        this.thread = new UpcomingFileThread(actionHandler, peerManager);
    }

    public void disconnect(){
        thread.interrupt();
        try {
            thread.getPeerManager().getPeer().close();
        } catch (IOException ignored) {

        }
    }

    private void actionFiles(int index, TreeActionsEnum action, Path... paths) throws DeviceNotFound, IOException {

        thread.getActionHandler().sendAction(
                thread.getPeerManager().getConnectedDevice(index),
                action,
                paths
        );
    }

    public void fetchFiles(int index, Path... paths) throws DeviceNotFound, IOException {
        actionFiles(index, TreeActionsEnum.Fetch, paths);
    }

    public void syncFiles(int index, Path... paths) throws DeviceNotFound, IOException {
        actionFiles(index, TreeActionsEnum.Sync, paths);
    }

    public void deleteFiles(int index, Path... paths) throws DeviceNotFound, IOException {
        actionFiles(index, TreeActionsEnum.Delete, paths);
    }

    public List<Device> findNewDevices() throws BroadcastFailedException {
        return thread.getPeerManager().findDevices()
                .stream().map(device -> new Device(device.getHostName(), device.getHostAddress()))
                .toList();
    }

    public List<Device> getConnectedDevices() {
        return thread.getPeerManager().getConnectedDevices()
                .stream().map(device -> new Device(device.getName(), device.getAddress()))
                .toList();
    }

    public void connectTo(int index) throws DeviceNotFound, DeviceAlreadyConnectedException, IOException {
        PeerManager peerManager = thread.getPeerManager();
        InetAddress address = peerManager.getFoundDevice(index);

        peerManager.getPeer().connectDevice(address);
    }
}

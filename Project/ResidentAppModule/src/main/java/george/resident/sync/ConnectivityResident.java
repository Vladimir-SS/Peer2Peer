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

/**
 * The class responsible for the communication between two devices
 * which is established by sending actions: fetch, sync and delete.
 */
public class ConnectivityResident {

    UpcomingFileThread thread;

    /**
     * The constructor for ConnectivityResident.
     * @param port The port given for establishing the connection.
     * @param directory The path for the directory used to store the files destined
     *                  to be syncronized.
     * @throws BadSyncDirectory Inaccesible directory.
     * @throws PortUnreachableException The port is not available for connection.
     * @throws SocketException Standard socket exception.
     */
    public ConnectivityResident(int port, Path directory) throws BadSyncDirectory, PortUnreachableException, SocketException {
        SynchronizedDirectory synchronizedDirectory = new SynchronizedDirectory(directory);
        ActionHandler actionHandler = new ActionHandler(synchronizedDirectory);
        Peer peer = new Peer(port);
        PeerManager peerManager = new PeerManager(peer);
        this.thread = new UpcomingFileThread(actionHandler, peerManager);
        this.thread.start();
    }

    /**
     * This method is used to set the direcorty that will store and receive the
     * files the user wishes to syncronize between devices.
     * @param directory The path for the directory used to store the files destined
     *                  to be syncronized.
     * @throws BadSyncDirectory Inaccesible directory.
     */
    public void setDirectory(Path directory) throws BadSyncDirectory {
        SynchronizedDirectory synchronizedDirectory = new SynchronizedDirectory(directory);
        ActionHandler actionHandler = new ActionHandler(synchronizedDirectory);
        PeerManager peerManager = thread.getPeerManager();
        thread.interrupt();

        this.thread = new UpcomingFileThread(actionHandler, peerManager);
        this.thread.start();
    }

    /**
     * This method is used to set the port for the connection.
     * @param port The port given for establishing the connection.
     * @throws PortUnreachableException The port is not available for connection.
     * @throws SocketException Standard socket exception.
     */
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


    /**
     * This method is used to send an action request to a specified device.
     * @param index The position where the desired device is found
     *              in the list of last searched connected devices.
     * @param action The type of action to be send.
     * @param subPath The path to specific files.
     * @throws DeviceNotFound The device at the given index was not
     *                       found.
     * @throws IOException
     */
    private void actionFiles(int index, TreeActionsEnum action, Path... paths) throws DeviceNotFound, IOException {
        thread.getActionHandler().sendAction(
                thread.getPeerManager().getConnectedDevice(index),
                action,
                paths
        );
    }

   /**
     * This method is used to invoke the method that will send the
     * fetch action.
     * @param index The position where the desired device is found
     *              in the list of last searched connected devices.
     * @param subPath The path to specific files.
     * @throws DeviceNotFound The device at the given index was not
     *                        found.
     * @throws IOException
     */
    public void fetchFiles(int index, Path... paths) throws DeviceNotFound, IOException {
        actionFiles(index, TreeActionsEnum.Fetch, paths);
    }

      /**
     * This method is used to invoke the method that will send the
     * sync action.
     * @param index The position where the desired device is found
     *              in the list of last searched connected devices.
     * @param subPath The path to specific files.
     * @throws DeviceNotFound The device at the given index was not found.
     * @throws IOException
     */
    public void syncFiles(int index, Path... paths) throws DeviceNotFound, IOException {
        actionFiles(index, TreeActionsEnum.Sync, paths);
    }

      /**
     * This method is used to invoke the method that will send the
     * delete action.
     * @param index The position where the desired device is found
     *              in the list of last searched connected devices.
     * @param subPath The path to specific files.
     * @throws DeviceNotFound The device at the given index was not found.
     * @throws IOException
     */
    public void deleteFiles(int index, Path... paths) throws DeviceNotFound, IOException {
        actionFiles(index, TreeActionsEnum.Delete, paths);

    }

    /**
     * This method is used to obtain a list of recently connected devices.
     * @return The list of connected devices.
     * @throws BroadcastFailedException Could not get information through the
     *                                  connection.
     */
    public List<Device> findNewDevices() throws BroadcastFailedException {
        return thread.getPeerManager().findDevices()
                .stream().map(device -> new Device(device.getHostName(), device.getHostAddress()))
                .toList();
    }

    /**
     * This method is used when the list of connected devices is needed.
     * @return The list of connected devices.
     */
    public List<Device> getConnectedDevices() {
        return thread.getPeerManager().getConnectedDevices()
                .stream().map(device -> new Device(device.getName(), device.getAddress()))
                .toList();
    }

    /**
     * The method used when user wants to establish a connection between
     * the current device and another connected device.
     * @param index The position where the desired device is found
     *              in the list of last searched connected devices.
     * @throws DeviceNotFound The device at the given index was not found.
     * @throws DeviceAlreadyConnectedException A connection between the
     *         devices already exists.
     * @throws IOException
     */
    public void connectTo(int index) throws DeviceNotFound, DeviceAlreadyConnectedException, IOException {
        PeerManager peerManager = thread.getPeerManager();
        InetAddress address = peerManager.getFoundDevice(index);

        peerManager.getPeer().connectDevice(address);
    }
}

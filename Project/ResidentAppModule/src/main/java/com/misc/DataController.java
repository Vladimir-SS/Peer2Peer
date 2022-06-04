package com.misc;

import Connectivity.Connection;
import Connectivity.Peer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

//TODO: (George: There is nothing to do, i know its bad)
public class DataController {
    private static Peer peer;
    private static List<InetAddress> addresses = new ArrayList<>();
    private static List<Connection> connections = new ArrayList<>();
    //TODO add Resident functionality

    public static void findDevices(){
        addresses = new ArrayList<>();
        try {
            addresses.addAll(peer.findDevices(false));
        } catch (IOException ignored) {

        }
    }

    public static Peer getPeer() {
        return peer;
    }

    public static List<String[]> getLastFoundDevices() {
        return addresses.stream().
                map(address -> new String[]{address.getHostName(), address.getHostAddress()})
                .toList();
    }

    public static List<String[]> getConnectedDevices() {
        if(peer == null)
            return new ArrayList<>();

        connections = peer.getConnectedDevices();

        return connections.stream()
                .map(connection -> new String[]{connection.getName(), connection.getAddress()})
                .toList();
    }

    public static void connectDevice(int index){
        try {
            if(addresses.size() > index)
                peer.connectDevice(addresses.get(index));
        } catch (Exception ignored) {
        }
    }

    public static void disconnectDevice(int index){
        try {
            if(connections.size() > index)
                peer.disconnectDevice(connections.get(index));
        } catch (IOException ignored) {
        }
    }

    public static void closePeer(){
        try {
            peer.close();
        } catch (IOException ignored) {
        }
    }

    public static void setPeer(Peer peer) {
        DataController.peer = peer;
    }
}

package Commands;

import Connectivity.Peer;

public interface Command {
    void run(Peer peer, String[] arguments) throws Exception;

    //TODO: perhaps a better way to do it...
    Exception manual();
}

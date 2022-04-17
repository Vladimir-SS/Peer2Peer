import java.util.ArrayList;
import java.util.List;

public class User {
    private String IPAddress;
    private String name;
    private List<User> connectedDevices = new ArrayList<>();
    private SharedFolderInfo sharedFolderInfo;
    private String sharedFolderPath;

    public User(String IPAddress, String name, List<User> connectedDevices, SharedFolderInfo sharedFolderInfo, String sharedFolderPath) {
        this.IPAddress = IPAddress;
        this.name = name;
        this.connectedDevices = connectedDevices;
        this.sharedFolderInfo = sharedFolderInfo;
        this.sharedFolderPath = sharedFolderPath;
    }

    public String addUserToConnect(User user) {
        if(!connectedDevices.contains(user)) {
            connectedDevices.add(user);
            return user.name + "added";
        } else {
            return user.name + "already is connected";
        }
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getConnectedDevices() {
        return connectedDevices;
    }

    public void setConnectedDevices(List<User> connectedDevices) {
        this.connectedDevices = connectedDevices;
    }

    public SharedFolderInfo getSharedFolderInfo() {
        return sharedFolderInfo;
    }

    public void setSharedFolderInfo(SharedFolderInfo sharedFolderInfo) {
        this.sharedFolderInfo = sharedFolderInfo;
    }

    public String getSharedFolderPath() {
        return sharedFolderPath;
    }

    public void setSharedFolderPath(String sharedFolderPath) {
        this.sharedFolderPath = sharedFolderPath;
    }

    @Override
    public String toString() {
        return "User{" +
                "IPAddress='" + IPAddress + '\'' +
                ", name='" + name + '\'' +
                ", connectedDevices=" + connectedDevices +
                ", sharedFolderInfo=" + sharedFolderInfo +
                ", sharedFolderPath='" + sharedFolderPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return this.IPAddress.equals(((User) obj).IPAddress);
    }

    @Override
    public int hashCode() {
        return name.hashCode()*IPAddress.hashCode();
    }
}

import java.util.ArrayList;
import java.util.List;

public class Application {
   private MainFrame UiFrame;
   private UtilFunctionalities functions;
   private List<User> connectedDevices=new ArrayList<>();


    public Application(MainFrame UiFrame) {
        this.UiFrame = UiFrame;
    }

    public Application(MainFrame UiFrame,UtilFunctionalities functions) {
        this.UiFrame = UiFrame;
        this.functions = functions;
    }

    public Application(MainFrame UiFrame,UtilFunctionalities functions, List<User> connectedDevices) {
        this.UiFrame = UiFrame;
        this.functions = functions;
        this.connectedDevices = connectedDevices;
    }


    public void addConnectedDevices(User user){
        this.connectedDevices.add(user);
    }

    public void removeConnectedDevices(User user){
        this.connectedDevices.remove(user);
    }

    public MainFrame getUiFrame() {
        return UiFrame;
    }

    public void setUiFrame(MainFrame uiFrame) {
        UiFrame = uiFrame;
    }

    public UtilFunctionalities getFunctions() {
        return functions;
    }

    public void setFunctions(UtilFunctionalities functions) {
        this.functions = functions;
    }

    public List<User> getConnectedDevices() {
        return connectedDevices;
    }

    public void setConnectedDevices(List<User> connectedDevices) {
        this.connectedDevices = connectedDevices;
    }

    @Override
    public String toString() {
        return "Application{" +
                "UiFrame=" + UiFrame +
                ", functions=" + functions +
                ", connectedDevices=" + connectedDevices +
                '}';
    }

}

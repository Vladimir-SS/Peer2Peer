package george.resident.sync;

public record Device(String name, String ip) {
    @Override
    public String toString() {
        return name + " | " + ip;
    }
}

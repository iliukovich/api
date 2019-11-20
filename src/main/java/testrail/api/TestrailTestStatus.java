package testrail.api;

public enum TestrailTestStatus {
    PASSED(1),
    FAILED(5);

    private int id;

    TestrailTestStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

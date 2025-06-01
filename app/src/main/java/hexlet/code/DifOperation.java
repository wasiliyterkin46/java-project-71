package hexlet.code;

public enum DifOperation {
    ADD,
    DELETE,
    NEUTRAL;

    private String sign;

    public DifOperation setSign(String valueSign) {
        this.sign = valueSign;
        return this;
    }

    public String getSign() {
        return this.sign;
    }

}

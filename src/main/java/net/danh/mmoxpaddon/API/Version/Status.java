package net.danh.mmoxpaddon.API.Version;

public enum Status {

    TRUE("&aTrue", "&a✔", true),
    FALSE("&cFalse", "&c✘", false);
    private final String status;
    private final String symbol;
    private final Boolean type;

    Status(String s, String symbol, Boolean type) {
        status = s;
        this.symbol = symbol;
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }
}

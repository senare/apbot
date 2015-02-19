package ao.apbot;

public enum Template {
    ADMIN("adminSession"), //
    ORG("orgSession"), //
    PVP("pvpSession"), //
    PVM("pvmSession"), //
    ; //

    public final String session;

    Template(String session) {
        this.session = session;
    }
}

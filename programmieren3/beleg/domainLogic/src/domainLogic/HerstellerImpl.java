package domainLogic;

import verwaltung.Hersteller;


record HerstellerImpl(String name) implements Hersteller {

    @Override
    public String getName() {
        return name;
    }
}

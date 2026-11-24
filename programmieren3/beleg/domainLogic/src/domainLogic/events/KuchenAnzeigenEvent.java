package domainLogic.events;

import domainLogic.Kuchensorte;


// sorte null heisst alle sorten
public record KuchenAnzeigenEvent(Kuchensorte sorte) {
}

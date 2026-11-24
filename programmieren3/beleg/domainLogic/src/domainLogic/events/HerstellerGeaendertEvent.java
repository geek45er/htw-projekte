package domainLogic.events;


// eingefuegt false heisst geloescht
public record HerstellerGeaendertEvent(String name, boolean eingefuegt) {
}

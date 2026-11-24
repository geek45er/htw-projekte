package domainLogic.events;



public interface Ereignisbehandler<E> {
    void verarbeite(E ereignis);
}

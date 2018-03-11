package main.java.pigeonssquare.event;

import java.util.*;

public class EventManager extends Observable {

    public static EventManager instance;

    private Map<Observer, List<Class<? extends Event>>> listeners;

    private EventManager() {
        this.listeners = new HashMap<>();
    }

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }

        return instance;
    }

    /**
     * Souscrire un observer à un évènement
     *
     * @param observer   observer
     * @param eventClass évènement souhaité
     */
    public void subscribe(Observer observer, Class<? extends Event> eventClass) {
        List<Class<? extends Event>> list = this.listeners.get(observer);
        if (list == null) {
            list = new ArrayList<>();
            list.add(eventClass);
            this.listeners.put(observer, list);
        }

        if (!list.contains(eventClass)) {
            list.add(eventClass);
        }
    }

    /**
     * Désinscrire un observer à un évènement
     *
     * @param observer   observer
     * @param eventClass évènement souhaité
     */
    public void unsubscribe(Observer observer, Class<? extends Event> eventClass) {
        List<Class<? extends Event>> list = this.listeners.get(observer);
        if (list != null && list.contains(eventClass)) {
            list.remove(eventClass);
        }
    }

    /**
     * Notifie tous les observers inscrits à l'évènement envoyé
     *
     * @param event évènement à envoyer
     */
    public void notify(Event event) {
        this.listeners.forEach((observer, events) -> {
            if (events.contains(event.getClass())) {
                observer.update(this, event);
            }
        });
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

}

package main.java.pigeonssquare.model.grid.event;

import java.util.Observable;

public class EventManager extends Observable {

    public static EventManager instance;

    public static EventManager getInstance(){
        if(instance == null){
            instance = new EventManager();
        }

        return instance;
    }

    // TODO Can we do better ?
    @Override
    public void setChanged(){
        super.setChanged();
    }

}

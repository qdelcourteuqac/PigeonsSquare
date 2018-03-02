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

    @Override
    public void setChanged(){
        super.setChanged();
    }

}

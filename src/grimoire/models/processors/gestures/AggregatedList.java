package grimoire.models.processors.gestures;

import java.util.LinkedList;

/**
 * Created by arthur on 23/02/17.
 */
public class AggregatedList<T> extends LinkedList<T> {

    @Override
    public boolean add(T value){
        if(isEmpty() || value != getLast()){
            return super.add(value);
        }
        return false;
    }
}

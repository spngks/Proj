package android.exhibit.lister.util;

import com.squareup.otto.Bus;

/**
 * Created by pal on 2/1/2015.
 */

    public class EventBus {

        private static Bus eventBus = new Bus();

        private EventBus() {
        }

        /* Static 'instance' method */
        public static Bus getInstance() {
            if (eventBus == null) {
                eventBus = new Bus();
            }
            return eventBus;
        }

        public static void post(Object object){
            getInstance().post(object);
        }

    }


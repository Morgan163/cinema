package modeloperations;

import exceptions.SendMailException;

/**
 * Created by niict on 20.12.2017.
 */
public interface BookingNotifier {
    void sendKeyToContacts(String key, String contacts) throws SendMailException;
}

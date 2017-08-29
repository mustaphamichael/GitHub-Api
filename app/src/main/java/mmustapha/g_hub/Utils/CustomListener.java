package mmustapha.g_hub.Utils;

/**
 * Created by mmustapha on 8/29/17.
 */

/**
 * Listener to make the Volley server response accessible by the presenters.
 * @param <T>
 */
public interface CustomListener<T> {
    void getResult(T object);
}

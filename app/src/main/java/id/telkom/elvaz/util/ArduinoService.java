package id.telkom.elvaz.util;

import android.util.Log;

import java.io.IOException;

/**
 * Created by VlovaverA on 16/08/2016.
 */
public class ArduinoService extends Thread
{
        private ArduinoConnection connectedThread;
        private final String TAG = "KeepAlive";
        private volatile boolean running = true;

        public ArduinoService(ArduinoConnection connectedThread) {
            this.connectedThread = connectedThread;
            running = true;
        }

        public synchronized void run() {
            Log.d(TAG, "KeepAlive Thread starting");
            while(running) {

                try {
                    wait(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    connectedThread.send("!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG,"KeepAlive Thread closing");
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

    }

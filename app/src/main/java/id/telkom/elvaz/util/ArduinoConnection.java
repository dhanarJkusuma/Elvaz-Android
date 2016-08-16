package id.telkom.elvaz.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by VlovaverA on 16/08/2016.
 */
public class ArduinoConnection extends Thread
{


        private BluetoothSocket mmSocket;
        private  InputStream mmInStream;
        private  OutputStream mmOutStream;
        Thread workerThread;
        byte[] readBuffer;
        int readBufferPosition;
        volatile boolean stopWorker;
        private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        public ArduinoConnection(BluetoothDevice device) throws IOException {
            mmSocket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);

        }

        public void openConnection() throws IOException {
            mmSocket.connect();
            mmInStream=mmSocket.getInputStream();
            mmOutStream=mmSocket.getOutputStream();
            beginListenForData();
            Log.d("ELVAZ(LOG): ","ARUDUINO CONNECTION OPENED.");
        }

        public void send(String msg) throws IOException {
            mmOutStream.write(msg.getBytes());
            Log.d("ELVAZ(LOG): ", "OutSream SEND");
        }

        public void closeConnection() throws IOException {
            stopWorker = true;
            mmOutStream.close();
            mmInStream.close();
            mmSocket.close();
            Log.d("ELVAZ(LOG): ","ARUDUINO CONNECTION CLOSED.");
        }

        public void beginListenForData()
        {
            final Handler handler = new Handler();
            final byte delimiter = 10; //This is the ASCII code for a newline character

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable()
            {
                public void run()
                {
                    while(!Thread.currentThread().isInterrupted() && !stopWorker)
                    {
                        try
                        {
                            int bytesAvailable = mmInStream.available();
                            if(bytesAvailable > 0)
                            {
                                byte[ ] packetBytes = new byte[bytesAvailable];
                                mmInStream.read(packetBytes);
                                for(int i=0;i<bytesAvailable;i++)
                                {
                                    byte b = packetBytes[i];
                                    if(b == delimiter)
                                    {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable()
                                        {
                                            public void run()
                                            {
                                                Log.d("ELVAZ(LOG): ", data);
                                            }
                                        });
                                    }
                                    else
                                    {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }
                        }
                        catch (IOException ex)
                        {
                            stopWorker = true;
                        }
                    }
                }
            });

            workerThread.start();
        }
}


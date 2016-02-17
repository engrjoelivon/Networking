package com.aivco;

import java.io.IOException;

/**
 * Created by johnanderson1 on 2/16/16.
 */
public class RunDatagram {



  public static void main(String args[])  {


      try {
          new DatagramExe().sender();
      } catch (IOException e) {
          e.printStackTrace();
      }


  }


}

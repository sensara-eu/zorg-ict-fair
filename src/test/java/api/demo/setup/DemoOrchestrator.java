package api.demo.setup;

import java.io.IOException;

import api.tests.ict.FallDetectionAlarmVideoGeneration;
import api.tests.ict.OutOfBedAlarmVideoGeneration;
import api.tests.ict.OutOfChairAlarmVideoGeneration;



public class DemoOrchestrator {

    public static void main(String[] args) throws Exception {

    	int cycles = 3;
    	 for (int i = 1; i <= cycles; i++) {
             System.out.println("=== Demo Cycle " + i + " ===");


             // --- Out of Bed ---
             System.out.println(java.time.LocalTime.now() +" Playing: Out Of Bed Video");
             playVideoWithAlarm("OutOfBed.mp4",16000, () -> {
 				try {
 					OutOfBedAlarmVideoGeneration.trigger();
 				} catch (IOException  | InterruptedException e) {
 					e.printStackTrace();
 				} 
 			});

             // --- Out of Chair ---
             System.out.println(java.time.LocalTime.now() +" Playing: Out Of Chair Video");
             playVideoWithAlarm("OutOfChair.mp4",16000, () -> {
 				try {
 					OutOfChairAlarmVideoGeneration.trigger();
 				} catch (IOException | InterruptedException e) {
 					e.printStackTrace();
 				} 
 			});

             // --- Fall Detection ---
             System.out.println(java.time.LocalTime.now() +" Playing: Fall Detection Video");
             playVideoWithAlarm("fall_detection.mp4",16000, () -> {
 				try {
 					FallDetectionAlarmVideoGeneration.trigger();
 				} catch (IOException | InterruptedException e) {
 					e.printStackTrace();
 				}
 			});
             System.out.println("=== Cycle " + i + " completed ===\n");
         }

         System.out.println("Demo finished after " + cycles + " cycles!");
     }

     /**
      * Play video and trigger alarm after 5 seconds while it plays
      */
     private static void playVideoWithAlarm(String videoFileName,long delayMs, Runnable alarmTrigger) {
         try {
             String projectPath = System.getProperty("user.dir");
             String fullPath = projectPath + "/src/test/java/api/videos/ict/" + videoFileName;
             
             String vlcPath = "/usr/bin/vlc";
             String[] command = { vlcPath, "--play-and-exit", fullPath };

             // Start VLC process
             Process process = Runtime.getRuntime().exec(command);
            

             // Trigger alarm (after 5 seconds) while video plays
             new Thread(() -> {
                 try {
                 	Thread.sleep(delayMs);
                     alarmTrigger.run();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }).start();

             process.waitFor();

         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 }


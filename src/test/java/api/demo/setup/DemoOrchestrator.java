package api.demo.setup;

import java.io.IOException;

import api.tests.ict.FallDetectionAlarmVideoGeneration;
import api.tests.ict.OutOfBedAlarmVideoGeneration;
import api.tests.ict.OutOfChairAlarmVideoGeneration;
import api.tests.ict.StatusGeneartion;

public class DemoOrchestrator {

    public static void main(String[] args) {
        int cycles = 1;

        for (int i = 1; i <= cycles; i++) {
            System.out.println("=== Demo Cycle " + i + " ===");

            // --- Out of Bed ---
            System.out.println(java.time.LocalTime.now() + " Playing: Out Of Bed Video");
            playVideoWithAlarm("OutOfBed.mp4", 11000, () -> {
                try {
                    OutOfBedAlarmVideoGeneration.trigger();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

			
			  // --- Out of Chair --- 
            System.out.println(java.time.LocalTime.now() + " Playing: Out Of Chair Video");
            playVideoWithAlarm("OutOfChair.mp4", 9000, () -> { 
            	try { 
            		OutOfChairAlarmVideoGeneration.trigger();
            		} catch (IOException | InterruptedException e){ 
            		    e.printStackTrace();
            		    } 
            	});
			  
			  // --- Fall Detection --- 
            System.out.println(java.time.LocalTime.now() + " Playing: Fall Detection Video"); 
            playVideoWithAlarm("fallDetection.mp4", 8000, () -> {
            	try { 
            		FallDetectionAlarmVideoGeneration.trigger(); 
            		} catch (IOException | InterruptedException e) { 
            			e.printStackTrace();
            			} 
            	});
			 
            
            // ---Status Generation---
            
            System.out.println(java.time.LocalTime.now() + " Playing: Status Generation Video");
            playVideoWithAlarm("statusGenerationLoop.mp4", 1000, () -> {
                try {
                	StatusGeneartion.trigger();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("=== Cycle " + i + " completed ===\n");
        }

        System.out.println("Demo finished after " + cycles + " cycles!");
    }

    /**
     * Play video and trigger alarm after delay while the video is playing.
     * Videos run sequentially; next video starts only after current one exits.
     */
    private static void playVideoWithAlarm(String videoFileName, long delayMs, Runnable alarmTrigger) {
        try {
            String projectPath = System.getProperty("user.dir");
            String fullPath = projectPath + "/src/test/java/api/videos/ict/" + videoFileName;
            String vlcPath = "/usr/bin/vlc";  // adjust path if needed
            String[] command = { vlcPath, "--play-and-exit", fullPath };

            // Start VLC process
            Process process = Runtime.getRuntime().exec(command);

            new Thread(() -> {
                try {
                    Thread.sleep(delayMs);
                    alarmTrigger.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            // Wait for VLC to finish before moving to the next video
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
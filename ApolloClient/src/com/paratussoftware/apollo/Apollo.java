package com.paratussoftware.apollo;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;

import javax.sound.sampled.LineUnavailableException;
import java.util.LinkedList;

public class Apollo {

    public static void main(final String[] args) {
        System.out.println("lets start ocarina-ing!!!");
        final LinkedList pitches = new LinkedList();
        final PitchDetectionHandler pitchDetectionHandler =
                (pitchDetectionResult, audioEvent) -> {
                    final float pitch = pitchDetectionResult.getPitch();
                    if (pitch >= 0) {
                        pitches.add(pitch);
                    }
                    System.out.println("Found Pitch: " + pitch);
                };
        try {
            final AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(2048, 0);
            audioDispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN,
                    44100, 2048, pitchDetectionHandler));

            final Thread audioThread = new Thread(audioDispatcher);
            audioThread.start();
            Thread.sleep(3000);
            audioDispatcher.stop();
            pitches.sort((a, b) -> Float.compare((float) a, (float) b));
            System.out.println("Highest Pitch: " + pitches.getLast());
            System.out.println("Lowest  Pitch: " + pitches.getFirst());
        } catch (final LineUnavailableException e) {
            e.printStackTrace();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}

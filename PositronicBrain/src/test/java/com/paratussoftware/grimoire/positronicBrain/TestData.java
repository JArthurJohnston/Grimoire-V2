package com.paratussoftware.grimoire.positronicBrain;

public class TestData {

    public static Brain simpleBrain(){
        Brain brain = new Brain(
                1,
                1,
                2);
        brain.getInputs().get(0).getAxons().get(0).weight = 0.111;
        brain.getInputs().get(0).getAxons().get(1).weight = 0.222;

        brain.getHiddenLayers().get(0).get(0).getAxons().get(0).weight = 0.333;
        brain.getHiddenLayers().get(0).get(1).getAxons().get(0).weight = 0.444;
        return brain;
    }

    public static final String simpleBrainJson = "[" +
            "{\"id\":0,\"weight\":1.111,\"input\":0.0}," +
            "{\"id\":1,\"weight\":2.222,\"input\":0.0}," +
            "{\"id\":2,\"weight\":3.333,\"input\":0.0}," +
            "{\"id\":3,\"weight\":4.444,\"input\":0.0}" +
            "]";
}

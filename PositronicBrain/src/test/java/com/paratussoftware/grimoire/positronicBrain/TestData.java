package com.paratussoftware.grimoire.positronicBrain;

import com.paratussoftware.grimoire.positronicBrain.neurons.InputNeuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.Neuron;

import java.util.List;

public class TestData {

    /*
              0
             / \
      input-0   0-output
             \ /
              0
     */
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

    /*
           /  0
         0 -
           \     \
              0  - 0
           /     /
         0 -
           \  0
     */
    public static Brain simpleExampleBrain(){
        Brain brain = new Brain(2, 1, 3);

        InputNeuron firstInputNeuron = brain.getInputs().get(0);
        firstInputNeuron.getAxons().get(0).weight = 0.8;
        firstInputNeuron.getAxons().get(1).weight = 0.4;
        firstInputNeuron.getAxons().get(2).weight = 0.3;

        InputNeuron secondInputNeuron = brain.getInputs().get(1);
        secondInputNeuron.getAxons().get(0).weight = 0.2;
        secondInputNeuron.getAxons().get(1).weight = 0.9;
        secondInputNeuron.getAxons().get(2).weight = 0.5;

        List<Neuron> hiddenLayer = brain.getHiddenLayers().get(0);
        hiddenLayer.get(0).getAxons().get(0).weight = 0.3;
        hiddenLayer.get(1).getAxons().get(0).weight = 0.5;
        hiddenLayer.get(2).getAxons().get(0).weight = 0.9;

        return brain;
    }

}

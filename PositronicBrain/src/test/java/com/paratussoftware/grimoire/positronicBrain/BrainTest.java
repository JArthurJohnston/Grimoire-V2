package com.paratussoftware.grimoire.positronicBrain;

import com.paratussoftware.grimoire.positronicBrain.neurons.InputNeuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.Neuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.OutputNeuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.Synapse;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class BrainTest {

    private Brain brain;
    private int numberOfInputs;
    private int numberOfOutputs;

    @Before
    public void setUp(){
        numberOfInputs = 2;
        numberOfOutputs = 1;
        int [] hiddenLayerData = new int[] {3, 4};
        brain = new Brain(numberOfInputs, numberOfOutputs, hiddenLayerData);
    }

    @Test
    public void construction() {
        List<InputNeuron> inputSynapses = brain.getInputs();
        List<OutputNeuron> outputSynapses = brain.getOutputs();
        List<List<Neuron>> hiddenLayers = brain.getHiddenLayers();

        assertEquals(numberOfInputs, inputSynapses.size());
        assertEquals(numberOfOutputs, outputSynapses.size());

        assertEquals(2, hiddenLayers.size());
        assertEquals(3, hiddenLayers.get(0).size());
        assertEquals(4, hiddenLayers.get(1).size());
    }

    @Test
    public void construction_setsUpInputNeuronConnections() {
        List<InputNeuron> inputNeurons = brain.getInputs();
        List<Neuron> firstHiddenLayer = brain.getHiddenLayers().get(0);

        for (InputNeuron eachInputNeuron : inputNeurons) {
            assertEquals(firstHiddenLayer.size(), eachInputNeuron.getAxons().size());
            for (Neuron eachNeuron : firstHiddenLayer) {
                assertEquals(inputNeurons.size(), eachNeuron.getDendrites().size());
                assertTrue(connectionExistsBetween(eachInputNeuron, eachNeuron));
            }
        }
    }

    @Test
    public void construction_setsUpOutputNeuronConnections() {
        List<OutputNeuron> outputNeurons = brain.getOutputs();
        List<Neuron> lastHiddenLayer = ((LinkedList<List<Neuron>>)brain.getHiddenLayers()).getLast();
        for (OutputNeuron eachOutputNeuron : outputNeurons) {
            assertEquals(lastHiddenLayer.size(), eachOutputNeuron.getDendrites().size());
            for (Neuron neuron : lastHiddenLayer) {
                connectionExistsBetween(eachOutputNeuron, neuron);
            }
        }
    }

    private boolean connectionExistsBetween(InputNeuron inputNeuron, Neuron receivingNeuron){
        return connectionExistsBetween(inputNeuron.getAxons(), receivingNeuron.getDendrites());
    }

    private boolean connectionExistsBetween(OutputNeuron outputNeuron, Neuron receivingNeuron){
        return connectionExistsBetween(outputNeuron.getDendrites(), receivingNeuron.getAxons());
    }

    private boolean connectionExistsBetween(List<Synapse> inputSynapses, List<Synapse> receivingSynapses) {
        for (Synapse dendriteSynapse : receivingSynapses) {
            if(inputSynapses.contains(dendriteSynapse))
                return true;
        }
        return false;
    }

}
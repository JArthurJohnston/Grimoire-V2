package com.paratussoftware.grimoire.positronicBrain;

import com.paratussoftware.grimoire.positronicBrain.functions.Sigmoid;
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
    private static double DELTA = 0.00001;

    private Brain brain;
    private int numberOfInputs;
    private int numberOfOutputs;

    @Before
    public void setUp(){
        numberOfInputs = 2;
        numberOfOutputs = 1;
        int [] hiddenLayerData = new int[] {3, 2, 4};
        brain = new Brain(numberOfInputs, numberOfOutputs, hiddenLayerData);
    }

    @Test
    public void construction() {
        List<InputNeuron> inputSynapses = brain.getInputs();
        List<OutputNeuron> outputSynapses = brain.getOutputs();
        List<List<Neuron>> hiddenLayers = brain.getHiddenLayers();

        assertEquals(numberOfInputs, inputSynapses.size());
        assertEquals(numberOfOutputs, outputSynapses.size());

        assertEquals(3, hiddenLayers.size());

        assertEquals(3, hiddenLayers.get(0).size());
        assertEquals(2, hiddenLayers.get(1).size());
        assertEquals(4, hiddenLayers.get(2).size());

        assertTrue(brain.getActivationFunction() instanceof Sigmoid);
    }

    @Test
    public void construction_setsUpInputNeuronConnections() {
        List<InputNeuron> inputNeurons = brain.getInputs();
        List<Neuron> firstHiddenLayer = brain.getHiddenLayers().get(0);

        for (InputNeuron eachInputNeuron : inputNeurons) {
            assertEquals(firstHiddenLayer.size(), eachInputNeuron.getAxons().size());
            for (Neuron eachNeuron : firstHiddenLayer) {
                assertEquals(inputNeurons.size(), eachNeuron.getDendrites().size());
                assertTrue(connectionExistsBetween(eachInputNeuron.getAxons(), eachNeuron.getDendrites()));
            }
        }
    }

    @Test
    public void construction_setsUpOutputNeuronConnections() {
        List<OutputNeuron> outputNeurons = brain.getOutputs();
        List<Neuron> lastHiddenLayer = ((LinkedList<List<Neuron>>)brain.getHiddenLayers()).getLast();
        for (OutputNeuron eachOutputNeuron : outputNeurons) {
            assertSame(brain.getActivationFunction(), eachOutputNeuron.getActivationFunction());
            assertEquals(lastHiddenLayer.size(), eachOutputNeuron.getDendrites().size());
            for (Neuron neuron : lastHiddenLayer) {
                assertTrue(connectionExistsBetween(eachOutputNeuron.getDendrites(), neuron.getAxons()));
            }
        }
    }

    @Test
    public void construction_setsUpHiddenLayerConnections(){
        List<Neuron> firstLayer = brain.getHiddenLayers().get(0);
        List<Neuron> secondLayer = brain.getHiddenLayers().get(1);
        List<Neuron> thirdLayer = brain.getHiddenLayers().get(2);

        checkConnectionsExistBetweenLayers(firstLayer, secondLayer);
        checkConnectionsExistBetweenLayers(secondLayer, thirdLayer);
    }

    @Test
    public void process_setsValuesToEachSynapse() {
        Brain brain = TestData.simpleBrain();

        double inputData = 0.567;
//        brain.trainWith(inputData).andExpectOutputs(0.789);

        double[] outputs = brain.process(inputData);

        assertEquals(1, outputs.length);
        assertEquals(0.600534, outputs[0], DELTA);

        assertEquals(inputData, brain.getInputs().get(0).getAxons().get(0).input, 0);
        assertEquals(inputData, brain.getInputs().get(0).getAxons().get(1).input, 0);

        Neuron firstHiddenLayerNeuron = brain.getHiddenLayers().get(0).get(0);
        Neuron secondHiddenLayerNeuron = brain.getHiddenLayers().get(0).get(1);

        assertEquals(0.515729, firstHiddenLayerNeuron.getAxons().get(0).input, DELTA);
        assertEquals(inputData, firstHiddenLayerNeuron.getDendrites().get(0).input ,0);

        assertEquals(0.531427, secondHiddenLayerNeuron.getAxons().get(0).input, DELTA);
        assertEquals(inputData, secondHiddenLayerNeuron.getDendrites().get(0).input ,0.0);
    }

    @Test
    public void process_exampleBrain() {
        Brain brain = TestData.simpleExampleBrain();
        OutputNeuron outputNeuron = brain.getOutputs().get(0);
        final double smallDelta = 0.01;

        double[] outputs = brain.process(1, 1);

        assertEquals(1, outputs.length);
        assertEquals(0.77, outputs[0], smallDelta);

        double deltaOutputSum = outputNeuron.deltaOutputSum(0);

        assertEquals(-0.13, deltaOutputSum, smallDelta);


    }

    private void checkConnectionsExistBetweenLayers(List<Neuron> firstLayer, List<Neuron> secondLayer) {
        for (Neuron firstLayerNeuron : firstLayer) {
            assertEquals(secondLayer.size(), firstLayerNeuron.getAxons().size());
            for (Neuron secondLayerNeuron : secondLayer) {
                assertEquals(firstLayer.size(), secondLayerNeuron.getDendrites().size());
                assertTrue(connectionExistsBetween(firstLayerNeuron.getAxons(), secondLayerNeuron.getDendrites()));
                assertTrue(connectionExistsBetween(secondLayerNeuron.getDendrites(), firstLayerNeuron.getAxons()));
            }
        }
    }

    private boolean connectionExistsBetween(List<Synapse> inputSynapses, List<Synapse> receivingSynapses) {
        for (Synapse dendriteSynapse : receivingSynapses) {
            assertTrue(dendriteSynapse.weight > 0);
            if(inputSynapses.contains(dendriteSynapse))
                return true;
        }
        return false;
    }

}
package com.paratussoftware.grimoire.positronicBrain.serilization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paratussoftware.grimoire.positronicBrain.Brain;
import com.paratussoftware.grimoire.positronicBrain.neurons.InputNeuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.Neuron;
import com.paratussoftware.grimoire.positronicBrain.neurons.Synapse;

import java.util.ArrayList;
import java.util.List;

public class BrainWriter {

    private static Gson gson = new GsonBuilder().create();


    static String writeSynapsesFrom(Brain brain) {
        int synapseCounter = 0;
        List<Synapse> synapses = new ArrayList<>();
        for (InputNeuron inputNeuron : brain.getInputs()) {
            for (Synapse eachSynapse : inputNeuron.getAxons()) {
                eachSynapse.id = synapseCounter++;
                synapses.add(eachSynapse);
            }
        }
        for (List<Neuron> neurons : brain.getHiddenLayers()) {
            for (Neuron neuron : neurons) {
                for (Synapse eachSynapse : neuron.getAxons()) {
                    eachSynapse.id = synapseCounter++;
                    synapses.add(eachSynapse);
                }

            }
        }

        return gson.toJson(synapses);
    }
}

package eternity2;

import java.io.IOException;
import java.util.HashMap;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.rl4j.learning.Learning;
import org.deeplearning4j.rl4j.learning.async.nstep.discrete.AsyncNStepQLearningDiscrete;
import org.deeplearning4j.rl4j.learning.sync.qlearning.QLearning;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.deeplearning4j.rl4j.network.dqn.IDQN;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.util.DataManager;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.RmsProp;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * @author rubenfiszel (ruben.fiszel@epfl.ch) on 8/11/16.
 * <p>
 * main example for toy DQN
 */
public class Toy {
    public static long time;
    public static HashMap<Integer, Integer> points = new HashMap<>();
    public static QLearning.QLConfiguration TOY_QL =
            new QLearning.QLConfiguration(
                    123,   //Random seed
                    196 * 80,//Max step By epoch
                    8000000, //Max step
                    10000, //Max size of experience replay
                    128,    //size of batches
                    50,   //target update (hard)
                    5,     //num step noop warmup
                    0.1,  //reward scaling
                    0.99,  //gamma
                    5.0,  //td-error clipping
                    0.1f,  //min epsilon
                    2000,  //num step for eps greedy anneal
                    true   //double DQN
            );


    public static DQNFactoryStdDense.Configuration TOY_NET =
            DQNFactoryStdDense.Configuration.builder().l2(0.01).updater(new Adam(1e-2)).numLayer(9).numHiddenNodes(32).build();

    public static void main(String[] args) throws IOException, WrongLengthException, WrongPieceException {
        //  simpleToy();
        //toyAsyncNstep();
        time = System.currentTimeMillis();
        eternity();


    }

    public static void eternity() throws IOException, WrongLengthException {
        //record the training data in rl4j-data in a new folder
        DataManager manager = new DataManager("abc", true);

        //define the mdp from toy (toy length)
        Eternity mdp = new Eternity(4, true);

        //define the training method
        Learning<Game, Integer, DiscreteSpace, IDQN> dql = new QLearningDiscreteDense<Game>(mdp, TOY_NET, TOY_QL, manager);

        //enable some logging for debug purposes on toy mdp
        //mdp.setFetchable(dql);

        //start the training
        dql.train();

        //useless on toy but good practice!
        mdp.close();
    }

 /*   public static void simpleToy() throws IOException {

        //record the training data in rl4j-data in a new folder
        DataManager manager = new DataManager("abc", true);

        //define the mdp from toy (toy length)
        test2 mdp = new test2(20);

        //define the training method
        Learning<SimpleToyState, Integer, DiscreteSpace, IDQN> dql = new QLearningDiscreteDense<SimpleToyState>(mdp, TOY_NET, TOY_QL, manager);

        //enable some logging for debug purposes on toy mdp
        //mdp.setFetchable(dql);

        //start the training
        dql.train();

        //useless on toy but good practice!
        mdp.close();

    }

    public static void hardToy() throws IOException {

        //record the training data in rl4j-data in a new folder
        DataManager manager = new DataManager("hardToy", true);

        //define the mdp from toy (toy length)
        MDP mdp = new HardDeteministicToy();

        //define the training
        ILearning<SimpleToyState, Integer, DiscreteSpace> dql = new QLearningDiscreteDense(mdp, TOY_NET, TOY_QL, manager);

        //start the training
        dql.train();

        //useless on toy but good practice!
        mdp.close();


    }


    public static void toyAsyncNstep() throws IOException {

        //record the training data in rl4j-data in a new folder
        DataManager manager = new DataManager();

        //define the mdp
        SimpleToy mdp = new SimpleToy(480);

        //define the training
        AsyncNStepQLearningDiscreteDense dql = new AsyncNStepQLearningDiscreteDense<SimpleToyState>(mdp, TOY_NET, TOY_ASYNC_QL, manager);

        //enable some logging for debug purposes on toy mdp
        mdp.setFetchable(dql);

        //start the training
        dql.train();

        //useless on toy but good practice!
        mdp.close();

    }*/
}


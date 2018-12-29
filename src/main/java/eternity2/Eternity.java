package eternity2;

import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.learning.NeuralNetFetchable;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.network.dqn.IDQN;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.Encodable;
import org.deeplearning4j.rl4j.space.ObservationSpace;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

class Eternity implements MDP<Game, Integer, DiscreteSpace>, Encodable {


    final private int size;
    //TODO 10 steps toy (always +1 reward2 actions), toylong (1000 steps), toyhard (7 actions, +1 only if actiion = (step/100+step)%7, and toyStoch (like last but reward has 0.10 odd to be somewhere else).
    private DiscreteSpace actionSpace;
    private ObservationSpace<Game> observationSpace = new ArrayObservationSpace(new int[] {4});
    private Game game;
    private NeuralNetFetchable<IDQN> fetchable;
    private boolean printOutput;

    public Eternity(int size,boolean printOutput) throws WrongLengthException {
        this.size = size;
        actionSpace=new DiscreteSpace(size*40);
        game=new Game(size,printOutput);
        this.printOutput=printOutput;
    }


    public void close() {}

    @Override
    public boolean isDone() {
        try {
            return game.isFinished();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return true;
    }

    public DiscreteSpace getActionSpace() {
        return this.actionSpace;
    }

    public ObservationSpace<Game> getObservationSpace() {
        return this.observationSpace;
    }


    public Game reset() {
        try {
            game.resetBoard();
        } catch (WrongLengthException e) {
            e.printStackTrace();
        }

        return game;
    }

    public StepReply<Game> step(Integer a) {
       // System.out.println(a);
        double reward = 0;
        try {
            reward = game.assignPiece(a);
        } catch (WrongPieceException e) {
            e.printStackTrace();
        }
        return new StepReply<>(game, reward, isDone(), new JSONObject("{}"));
    }

    public Eternity newInstance() {
        Eternity e2= null;
        try {
            e2 = new Eternity(size,printOutput);
        } catch (WrongLengthException e) {
            e.printStackTrace();
        }
        e2.setFetchable(fetchable);
        return e2;
    }
    public void setFetchable(NeuralNetFetchable<IDQN> fetchable) {
        this.fetchable = fetchable;
    }


    @Override
    public double[] toArray() {
        return new double[0];
    }
}

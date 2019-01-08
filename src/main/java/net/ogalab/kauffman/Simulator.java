package net.ogalab.kauffman;

import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Hello world!
 */
public class Simulator {

    public static void main(String[] args) {
        Simulator obj = new Simulator();
        obj.simulate();
    }

    ArrayList<Set<Integer>> data = new ArrayList<>();



    int numberOfTrials = 1000;
    int numberOfElements = 100;



    public void simulate() {
        //StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfTrials; i++) {
            System.out.println(executeSingleStep());
        }
        //return sb.toString();
    }


    public String executeSingleStep() {


        RandomDataGenerator random = new RandomDataGenerator();
        int r1 = random.nextInt(1, numberOfElements);
        int r2 = random.nextInt(1, numberOfElements);

        boolean hitFlg = false;
        if (data.size() == 0) {
            HashSet<Integer> s = new HashSet<>();
            s.add(r1);
            s.add(r2);
            data.add(s);
        } else {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).contains(r1)) {
                    data.get(i).add(r2);
                    hitFlg = true;
                } else if (data.get(i).contains(r2)) {
                    data.get(i).add(r1);
                    hitFlg = true;
                }
            }
        }

        if (hitFlg = false) {
            HashSet<Integer> s = new HashSet<>();
            s.add(r1);
            s.add(r2);
            data.add(s);
        }

        int maxSize = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).size() > maxSize) {
                maxSize = data.get(i).size();
            }
        }

        return String.format("%d\t%d\t%d", r1, r2, maxSize);

    }


    public int getNumberOfTrials() {
        return numberOfTrials;
    }

    public void setNumberOfTrials(int numberOfTrials) {
        this.numberOfTrials = numberOfTrials;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }


}



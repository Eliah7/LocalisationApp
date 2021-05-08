
package com.example.application.dca.bpso;

import java.util.Arrays;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Merve
 */
public class Particle {


    private final double maxV = 4;
    private final double minV = -4;
    double[] velocity;
    int[] position;
    int dimension;
    double fitness = 9e10;
    Particle pBestParticle;

    public Particle(int numberOfItems) {
        this.dimension = numberOfItems;

        position = new int[numberOfItems];
        velocity = new double[numberOfItems];
        pBestParticle = new Particle(fitness, numberOfItems);

        for (int i = 0; i < numberOfItems; i++) {
            velocity[i] = minV + Math.random() * (maxV - minV);
            if (Math.random() > Tanh.function(velocity[i])) {
                position[i] = 0;
            } else {
                position[i] = 1;
            }
        }

        System.arraycopy(this.position, 0, pBestParticle.position, 0, numberOfItems);
        System.arraycopy(this.velocity, 0, pBestParticle.velocity, 0, numberOfItems);
        fitness = PSO.problem.getFitness(this);
        pBestParticle.fitness = this.fitness;
    }

    public Particle(double fitness, int dimension) {
        this.dimension = dimension;
        this.fitness = fitness;
        if(fitness==0){
            throw new RuntimeException();
        }
        position = new int[dimension];
        velocity = new double[dimension];
    }

    void updateVelocity(Particle gBest, Particle pBest) {

        double w = 0.689343;
        double c1 = 1.42694, c2 = 1.42694;

        for (int i = 0; i < position.length; i++) {
            double rand1 = Math.random();
            double rand2 = Math.random();
            velocity[i] = w * velocity[i] + c1 * rand1 * (pBest.position[i] - position[i]) + c2 * rand2 * (gBest.position[i] - position[i]);

            if (velocity[i] > maxV) {
                velocity[i] = maxV;
            }
            if (velocity[i] < minV) {
                velocity[i] = minV;
            }
        }

    }

    void updatePosition() {
        for (int i = 0; i < position.length; i++) {
            if (Math.random() > Atan.function(velocity[i])) {
                position[i] = 0;
            } else {
                position[i] = 1;
            }
        }

    }

    void updateBestParticle() {

        if (this.getFitness() < pBestParticle.getFitness()) {
            pBestParticle.fitness = this.fitness;
            System.arraycopy(this.position, 0, pBestParticle.position, 0, dimension);
            System.arraycopy(this.velocity, 0, pBestParticle.velocity, 0, dimension);
        }

    }

    void setFitness(double f) {
        fitness = f;
    }

    double getFitness() {
        return fitness;
    }

}

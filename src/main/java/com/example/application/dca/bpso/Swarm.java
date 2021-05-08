
package com.example.application.dca.bpso;

import java.util.Random;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Merve
 */
public class Swarm {

    private int PARTICLE_SIZE;
    public Particle[] particles;
    public Particle gBestParticle;

    public Swarm(int size) {
        PARTICLE_SIZE = size;
    }

    Particle[] initializeSwarm(int dimension) {
        particles = new Particle[PARTICLE_SIZE];
        for (int i = 0; i < particles.length; i++) {
            Particle p = new Particle(dimension);
            particles[i] = p;
        }
        gBestParticle = new Particle(particles[0].fitness, dimension);
        System.arraycopy(particles[0].position, 0, gBestParticle.position, 0, dimension);
        System.arraycopy(particles[0].velocity, 0, gBestParticle.velocity, 0, dimension);

        return particles;
    }

    void updateGlobalBestParticle() {

        for (Particle particle : particles) {

            if (particle.pBestParticle.fitness < gBestParticle.fitness) {
                gBestParticle.fitness = particle.pBestParticle.fitness;
                System.arraycopy(particle.pBestParticle.position, 0, gBestParticle.position, 0, particle.dimension);
                System.arraycopy(particle.pBestParticle.velocity, 0, gBestParticle.velocity, 0, particle.dimension);
            }
        }
    }


}

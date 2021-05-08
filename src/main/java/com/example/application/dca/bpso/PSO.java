package com.example.application.dca.bpso;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import py4j.GatewayServer;

/**
 *
 * @author Merve
 */
public class PSO {

    private final int PARTICLE_SIZE;
    public Particle[] particles;
    private Particle gBest;
    private int maxIteration = 0;
    private double fitness = 9e10;
    static public Problem problem;
    public Swarm swarmForRl;

    public PSO(int Partical_size, Problem p, int iteration) {
        PARTICLE_SIZE = Partical_size;
        problem = p;
        this.maxIteration = iteration;

    }

    public PSO(){
        PARTICLE_SIZE = 0;
    }

    public static void main(String[] args){
        PSO app = new PSO();
//        GatewayServer server = new GatewayServer(app, 30000);
//        server.start();
    }

    public int printInt(){
        return 9;
    }

    public int getPARTICLE_SIZE() {
        return PARTICLE_SIZE;
    }

    public Particle[] getParticles() {
        return particles;
    }

    public void setParticles(Particle[] particles) {
        this.particles = particles;
    }

    public Particle getgBest() {
        return gBest;
    }

    public void setgBest(Particle gBest) {
        this.gBest = gBest;
    }

    public int getMaxIteration() {
        return maxIteration;
    }

    public void setMaxIteration(int maxIteration) {
        this.maxIteration = maxIteration;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public static Problem getProblem() {
        return problem;
    }

    public static void setProblem(Problem problem) {
        PSO.problem = problem;
    }

    public int[] solveForRL(){
        this.solve();
        return this.swarmForRl.gBestParticle.position;
    }

    public void solve() {
        Swarm swarm = new Swarm(PARTICLE_SIZE);
        particles = swarm.initializeSwarm(problem.dimension);

        swarm.updateGlobalBestParticle();
        int iteration = 0;
        while (iteration < maxIteration) {

            for (int i = 0; i < swarm.particles.length; i++) {

                swarm.particles[i].updateVelocity(swarm.gBestParticle, swarm.particles[i].pBestParticle);
                swarm.particles[i].updatePosition();

                fitness = problem.getFitness(swarm.particles[i]);
                swarm.particles[i].setFitness(fitness);

                swarm.particles[i].updateBestParticle();
            }

            swarm.updateGlobalBestParticle();
            iteration++;
            System.out.println(iteration + ". iteration     -     Global Best Value: " + swarm.gBestParticle.fitness);

        }

        this.swarmForRl = swarm;
        for (int p : swarm.gBestParticle.position) {
            System.out.print(p+ " ");
        }
        int index = 0;
        System.out.println("\nItem values to be switched:");
        for (int p : swarm.gBestParticle.position) {
            if(p == 1){
                System.out.print(problem.items[index].value + " - ");      
            }
            index++;
            

        }

    }

}

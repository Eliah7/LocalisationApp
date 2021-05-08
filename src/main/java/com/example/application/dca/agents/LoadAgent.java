//package com.example.application.dca.agents;
//
//import ac.udsm.dca.core.*;
////import jade.core.AID;
//import jade.domain.FIPAAgentManagement.DFAgentDescription;
//import jade.domain.FIPAAgentManagement.Property;
//import jade.domain.FIPAAgentManagement.ServiceDescription;
//import jade.lang.acl.ACLMessage;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class LoadAgent extends ClientAgent {
//    public int gridId;
//    public int id;
//    private AID grid;
//    private Map<Integer, Line> buses;
//
//
//    @Override
//    protected void setup() {
//        Object[] args = getArguments();
//        if (args != null && args.length > 0) {
//            gridId = Integer.parseInt(String.valueOf(args[0]));
//        }
//        if (register(LOAD_AGENT, "-Load monitor")) {
//            readNodes();
//            addBehaviour(new FindController(this));
//        }
//    }
//
//    @Override
//    protected void onTick(int timestamp) {
//        print(timestamp);
//    }
//
//    private void readNodes() {
//        Scanner sc = null;
//        try {
//            sc = new Scanner(new FileReader("nodes/node-" + id + ".txt"));
//            buses = new HashMap<>();
//            while (sc.hasNextLine()) {
//                Line bus = new Line(sc.nextLine());
//                buses.put(bus.getId(), bus);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public double[][] getBusMatrix() {
//        double[][] lines = new double[buses.size()][];
//        int index = 0;
//        for (int id : buses.keySet()) {
//            Line bus = buses.get(id);
//            double[] values = new double[]{
//                    id,
//                    bus.getRealPower(),
//                    bus.getReactivePower()
//            };
//            lines[index] = values;
//        }
//        return lines;
//    }
//
//    public double[][] getLineMatrix() {
//        double[][] lines = new double[buses.size() - 1][];
//        int index = 0;
//        for (int id : buses.keySet()) {
//            Line bus = buses.get(id);
//            if (bus.getParentId() != -1) {
//                double[] values = new double[]{
//                        bus.getIndex(),
//                        bus.getParentId(),
//                        bus.getId(),
//                        bus.getResistance(),
//                        bus.getReactance()
//                };
//                lines[index] = values;
//            }
//
//        }
//        return lines;
//    }
//
//    class FindController extends AgentBehaviour {
//        private boolean found = false;
//
//        FindController(BaseAgent agent) {
//            super(agent, Conversation.LOAD_SUBSCRIPTION);
//        }
//
//        @Override
//        protected ACLMessage handleInform(ACLMessage msg) {
//            print("my id is " + msg.getContent());
//            setDone(true);
//            return null;
//        }
//
//        @Override
//        protected void run() {
//            if (grid == null) {
//                DFAgentDescription[] items = findAgents(GRID_AGENT);
//                if (items != null) {
//                    for (int i = 0; i < items.length; i++) {
//                        DFAgentDescription item = items[i];
//                        while (item.getAllServices().hasNext()) {
//                            ServiceDescription svc = (ServiceDescription) item.getAllServices().next();
//                            if (svc.getAllProperties().hasNext()) {
//                                Property p = (Property) svc.getAllProperties().next();
//                                if (p.getName().equals("id") && Integer.parseInt((String) p.getValue()) == gridId) {
//                                    grid = item.getName();
//                                    print("Found controller " + grid.getName());
//                                    found = true;
//                                    break;
//                                }
//                            }
//                        }
//                        if (found) {
//                            break;
//                        }
//                    }
//                }
//                if (found) {
//                    sendMessage(Conversation.LOAD_SUBSCRIPTION, 0, grid, ACLMessage.SUBSCRIBE);
//                }
//            }
//
//        }
//    }
//
//    void powerFlow() {
//
//    }
//}

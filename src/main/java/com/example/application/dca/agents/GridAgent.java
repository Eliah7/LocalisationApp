//package com.example.application.dca.agents;
//
//
//import java.util.HashMap;
//
//public class GridAgent extends PowerAgent {
//    private int id;
//    private int netIdIndex = 0;
//    private HashMap<Integer, Integer> ids;
//    private double capacity;
//
//
//    public GridAgent() {
//        ids = new HashMap<>();
//        ids.put(0, TYPE_GRID);
//    }
//
//    @Override
//    protected void setup() {
//        Object[] args = getArguments();
//        if (args != null && args.length > 0) {
//            id = Integer.parseInt(String.valueOf(args[0]));
//            if (args.length > 1) {
//                //setMaxCapacity(Integer.parseInt(String.valueOf(args[1])));
//            }
//        }
//        Property property = new Property("id", id);
//        if (register(GRID_AGENT, "-Controller", property)) {
//            addBehaviour(new LoadHandler(this));
//        }
//    }
//
//    static class LoadHandler extends AgentBehaviour {
//        GridAgent gridAgent;
//
//        public LoadHandler(GridAgent agent) {
//            super(agent, Conversation.LOAD_SUBSCRIPTION);
//            gridAgent = agent;
//        }
//
//        @Override
//        protected ACLMessage handleSubscription(ACLMessage msg) {
//            ACLMessage reply = createReply(msg);
//            reply.setPerformative(ACLMessage.INFORM);
//            reply.setContent(String.valueOf(gridAgent.netIdIndex));
//            gridAgent.netIdIndex++;
//            return reply;
//        }
//    }
//}

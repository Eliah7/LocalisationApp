//package com.example.application.dca.core;
//
//import jade.core.AID;
//import jade.core.Agent;
//import jade.core.behaviours.Behaviour;
//import jade.domain.DFService;
//import jade.domain.FIPAAgentManagement.DFAgentDescription;
//import jade.domain.FIPAAgentManagement.Property;
//import jade.domain.FIPAAgentManagement.ServiceDescription;
//import jade.domain.FIPAException;
//import jade.lang.acl.ACLMessage;
//import jade.lang.acl.MessageTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BaseAgent extends Agent {
//    public static final int TYPE_CONTAINER = 0;
//    public static final int TYPE_GRID = 1;
//    public static final int TYPE_LOAD = 2;
//    public static final int TYPE_SWITCH = 3;
//    public static final String LOAD_AGENT = "Load";
//    public static final String GRID_AGENT = "Grid";
//    public static final String RDG_AGENT = "RDG";
//    public static final String BATTERY_AGENT = "Battery";
//    public static final String TIME_AGENT = "Time";
//    private String type;
//    protected boolean syncTime = true;
//    protected AID chronometer;
//    int timestamp;
//    protected boolean enableLogging = true;
//    private int id;
//    private int parentId;
//
//    protected boolean register(String type, String name) {
//        return register(type, name, (Property) null);
//    }
//
//    protected boolean register(String type, String name, List<Property> properties) {
//        this.type = type;
//        DFAgentDescription dfd = new DFAgentDescription();
//        dfd.setName(getAID());
//        ServiceDescription sd = new ServiceDescription();
//        sd.setType(type);
//        if (properties != null) {
//            for (Property property : properties) {
//                sd.addProperties(property);
//            }
//        }
//        sd.setName(getLocalName() + name);
//        dfd.addServices(sd);
//        try {
//            DFService.register(this, dfd);
//            if (syncTime) {
//                syncTime();
//            }
//            return true;
//        } catch (FIPAException fe) {
//            fe.printStackTrace();
//        }
//        return false;
//    }
//
//    protected boolean register(String type, String name, Property property) {
//        List<Property> items = null;
//        if (property != null) {
//            items = new ArrayList<>();
//            items.add(property);
//        }
//        return register(type, name, items);
//    }
//
//    protected void onTick(int timestamp) {
//
//    }
//
//    public void syncTime() {
//        addBehaviour(new FindTimer(this));
//    }
//
//    public void print(Object message) {
//        if (enableLogging) {
//            System.out.println(getName() + ":" + message);
//        }
//    }
//
//    protected DFAgentDescription[] findAgents(String type) {
//        DFAgentDescription template = new DFAgentDescription();
//        ServiceDescription sd = new ServiceDescription();
//        sd.setType(type);
//        template.addServices(sd);
//        DFAgentDescription[] result;
//        try {
//            return DFService.search(this, template);
//        } catch (FIPAException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private class FindTimer extends AgentBehaviour {
//        public FindTimer(BaseAgent agent) {
//            super(agent, Conversation.TIMER_SUBSCRIPTION);
//        }
//
//        @Override
//        public void run() {
//            DFAgentDescription[] items = findAgents(TIME_AGENT);
//            print("finding timer");
//            if (items != null && items.length > 0) {
//                DFAgentDescription item = items[0];
//                chronometer = item.getName();
//                sendMessage(Conversation.TIMER_SUBSCRIPTION, timestamp, chronometer, ACLMessage.SUBSCRIBE);
//                addBehaviour(new Tick(BaseAgent.this));
//            }
//        }
//
//        @Override
//        public boolean done() {
//            return chronometer != null;
//        }
//    }
//
//    private class Tick extends AgentBehaviour {
//
//        public Tick(BaseAgent agent) {
//            super(agent, Conversation.TIMER_TICKER_STATUS);
//        }
//
//        @Override
//        protected ACLMessage handleInform(ACLMessage msg) {
//            timestamp = Integer.parseInt(msg.getContent());
//            onTick(timestamp);
//            return null;
//        }
//
//        @Override
//        protected void run() {
//
//        }
//    }
//
//    protected MessageTemplate sendMessage(String conversationId, Object content, AID agent) {
//        return sendMessage(conversationId, content, agent, ACLMessage.UNKNOWN);
//    }
//
//    protected MessageTemplate sendMessage(String conversationId, Object content, AID agent, int messageType) {
//        ACLMessage cfp = new ACLMessage(messageType);
//        cfp.addReceiver(agent);
//        cfp.setContent(String.valueOf(content));
//        cfp.setConversationId(conversationId);
//        cfp.setReplyWith(conversationId + System.currentTimeMillis()); // Unique value
//        send(cfp);
//        return MessageTemplate.and(MessageTemplate.MatchConversationId(conversationId),
//                MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
//    }
//}

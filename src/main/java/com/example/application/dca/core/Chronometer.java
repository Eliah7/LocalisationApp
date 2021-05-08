//package com.example.application.dca.core;
//
//import jade.core.AID;
//import jade.core.behaviours.TickerBehaviour;
//import jade.lang.acl.ACLMessage;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Chronometer extends BaseAgent {
//    private static final int MAX_TIMESTAMP = 95;
//    private static final double TIME_DIVISION = 4.0;
//    private int hr = 0;
//    private int min = 0;
//    //private TimeUi ui;
//    private List<AID> agents;
//
//    @Override
//    protected void setup() {
//        // ui = new TimeUi(this);
//        agents = new ArrayList<>();
//        syncTime = false;
//        if (register(TIME_AGENT, "-Time Controller")) {
//            addBehaviour(new TickerBehaviour(this, 1000) {
//                @Override
//                protected void onTick() {
//                    timestamp++;
//                    updateHrMin();
//                    for (AID aid : agents) {
//                        sendMessage(Conversation.TIMER_TICKER_STATUS, timestamp, aid, ACLMessage.INFORM);
//                    }
//                }
//            });
//
//            addBehaviour(new AgentBehaviour(this, Conversation.TIMER_SUBSCRIPTION) {
//                @Override
//                protected ACLMessage handleSubscription(ACLMessage msg) {
//                    agents.add(msg.getSender());
//                    ACLMessage reply = msg.createReply();
//                    reply.setConversationId(Conversation.TIMER_TICKER_STATUS);
//                    reply.setContent(String.valueOf(timestamp));
//                    reply.setPerformative(ACLMessage.INFORM);
//                    setDone(true);
//                    return reply;
//                }
//
//                @Override
//                protected void run() {
//
//                }
//            });
//        }
//        //ui.setVisible(true);
//    }
//
//    private void updateHrMin() {
//        if (timestamp > MAX_TIMESTAMP) {
//            timestamp = 0;
//        }
//        min = (timestamp % (int) TIME_DIVISION) * 60;
//        double t = (timestamp / TIME_DIVISION);
//        hr = (int) t;
//        min = (int) ((t - hr) * 60);
//        //ui.setTime(hr, min, timestamp);
//    }
//
//    public void setTime(int tick) {
//        timestamp = tick;
//        updateHrMin();
//    }
//}

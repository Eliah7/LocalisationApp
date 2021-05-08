//package com.example.application.dca.core;
//
//import jade.core.Agent;
//import jade.core.behaviours.Behaviour;
//import jade.lang.acl.ACLMessage;
//import jade.lang.acl.MessageTemplate;
//
//public abstract class AgentBehaviour extends Behaviour {
//    private boolean done = false;
//    private String conversationId;
//    private MessageTemplate mt;
//    private boolean isEventful = true;
//
//    public AgentBehaviour(BaseAgent agent) {
//        super(agent);
//    }
//
//    public AgentBehaviour(BaseAgent agent, String conversationId) {
//        super(agent);
//        this.conversationId = conversationId;
//        mt = MessageTemplate.MatchConversationId(conversationId);
//    }
//
//    public void setConversationId(String conversationId) {
//        this.conversationId = conversationId;
//        mt = MessageTemplate.MatchConversationId(conversationId);
//    }
//
//    public ACLMessage createReply(ACLMessage msg){
//        ACLMessage reply = msg.createReply();
//        reply.setConversationId(msg.getConversationId());
//        return  reply;
//    }
//
//    protected void setDone(boolean done) {
//        this.done = false;
//    }
//
//    protected void run() {
//
//    }
//
//    @Override
//    public BaseAgent getAgent() {
//        return (BaseAgent) super.getAgent();
//    }
//
//    @Override
//    public final void action() {
//        ACLMessage msg = myAgent.receive(mt);
//        if (msg != null) {
//            ACLMessage reply = handleMessage(msg);
//            if (reply != null) {
//                getAgent().send(reply);
//            }
//        } else {
//            if (isEventful) {
//                run();
//                block();
//                return;
//            }
//        }
//        run();
//    }
//
//    protected ACLMessage handleSubscription(ACLMessage msg) {
//        return null;
//    }
//
//    protected ACLMessage handleInform(ACLMessage msg) {
//        return null;
//    }
//
//    protected ACLMessage handleNotUnderstood(ACLMessage msg) {
//        return null;
//    }
//
//    protected ACLMessage handleProposal(ACLMessage msg) {
//        return null;
//    }
//
//    protected ACLMessage handleRequest(ACLMessage msg) {
//        return null;
//    }
//
//    protected ACLMessage handleAcceptProposal(ACLMessage msg) {
//        return null;
//    }
//
//    protected ACLMessage handleRejectProposal(ACLMessage msg) {
//        return null;
//    }
//
//    protected ACLMessage handleOthers(ACLMessage msg) {
//        return null;
//    }
//
//
//    protected ACLMessage handleMessage(ACLMessage msg) {
//        switch (msg.getPerformative()) {
//            case ACLMessage.SUBSCRIBE:
//                return handleSubscription(msg);
//            case ACLMessage.INFORM:
//                return handleInform(msg);
//            case ACLMessage.NOT_UNDERSTOOD:
//                return handleNotUnderstood(msg);
//            case ACLMessage.REQUEST:
//                return handleRequest(msg);
//            case ACLMessage.PROPOSE:
//                return handleProposal(msg);
//            case ACLMessage.ACCEPT_PROPOSAL:
//                return handleAcceptProposal(msg);
//            case ACLMessage.REJECT_PROPOSAL:
//                return handleRejectProposal(msg);
//            default:
//                return handleOthers(msg);
//        }
//    }
//
//    @Override
//    public boolean done() {
//        return done;
//    }
//}

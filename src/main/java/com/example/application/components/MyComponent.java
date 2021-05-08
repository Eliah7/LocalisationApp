//package com.example.application.components;
//
//import com.vaadin.flow.component.dependency.JavaScript;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//@JavaScript({"mylibrary.js", "mycomponent-connector.js"})
//public class MyComponent extends AbstractJavaScriptComponent {
//    public interface ValueChangeListener extends Serializable {
//        void valueChange();
//    }
//    ArrayList<ValueChangeListener> listeners =
//            new ArrayList<ValueChangeListener>();
//    public void addValueChangeListener(
//            ValueChangeListener listener) {
//        listeners.add(listener);
//    }
//
//    public void setValue(String value) {
//        getState().value = value;
//    }
//
//    public String getValue() {
//        return getState().value;
//    }
//
//    @Override
//    protected MyComponentState getState() {
//        return (MyComponentState) super.getState();
//    }
//}
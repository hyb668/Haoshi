package com.haoshi.rxjava.example4.ui.event;

/**
 * @author Haoshi
 *         <p>
 *         置顶事件
 */

public class TopEvent {
    private String type;

    public TopEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

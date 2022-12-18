package com.example.myspringbeans.factory.parsing;

import java.util.LinkedList;

/**
 * 基于简单{@link LinkedList}的结构，用于跟踪期间的逻辑位置 解析过程。
 * 被添加到链接列表
 * 以特定于读取器的方式解析阶段中的每个点。
 *
 * @author julu
 * @date 2022/12/4 16:45
 */
public final class ParseState {

    private static final char TAB = '\t';

    private final LinkedList<Entry> state;

    public ParseState(){
        this.state = new LinkedList<>();
    }

    public ParseState(ParseState other){
        this.state = (LinkedList<Entry>) other.state.clone();
    }

    public ParseState snapshot(){
        return new ParseState(this);
    }

    public interface Entry{

    }
}

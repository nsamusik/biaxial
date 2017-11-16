package org.datacyt;

public class TileParam {
    public static enum Type {SINGLE_CHOICE,MULTI_CHOICE, NUMBER, RANGE};
    public Type type;
    public String [] values;
    public String [] current_value;
}

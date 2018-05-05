package interpreter;

import quartzBase.types.QuartzType;

public class Variable
{
    private String identifier;
    private QuartzType value;

    public Variable(String identifier, QuartzType value) {
        this.identifier = identifier;
        this.value = value;
    }

    public void set(QuartzType value) {
        this.value = value;
    }

    public QuartzType get() {
        return this.value;
    }
}

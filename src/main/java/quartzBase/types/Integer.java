package quartzBase.types;

public class Integer extends QuartzType
{
    private boolean signed;
    private int length;
    private int value;

    public Integer(boolean signed, int length) {
        this.signed = signed;
        this.length = length;
        this.value = 0;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public Integer apply(String operator, QuartzType subject) {
        Integer sub = (Integer)subject;
        switch (operator) {
            case "+":
                this.value += sub.getValue();
        }

        return this;
    }
}

package quartzBase.types;

public abstract class QuartzType {
    abstract public QuartzType apply(String operator, QuartzType subject);
}

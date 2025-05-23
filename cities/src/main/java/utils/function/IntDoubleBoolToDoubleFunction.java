package utils.function;

@FunctionalInterface
public interface IntDoubleBoolToDoubleFunction {
    double apply(int i, double d, boolean b);
}

public class QuantityMeasurementApp {

    // Inner class for Feet
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Inner class for Inches
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Static method for Feet comparison
    public static boolean compareFeet(double val1, double val2) {
        Feet f1 = new Feet(val1);
        Feet f2 = new Feet(val2);
        return f1.equals(f2);
    }

    // Static method for Inches comparison
    public static boolean compareInches(double val1, double val2) {
        Inches i1 = new Inches(val1);
        Inches i2 = new Inches(val2);
        return i1.equals(i2);
    }

    // Main method
    public static void main(String[] args) {

        // Feet comparison
        boolean feetResult = compareFeet(1.0, 1.0);
        System.out.println("Comparing 1.0 ft and 1.0 ft: " + feetResult);

        // Inches comparison
        boolean inchResult = compareInches(1.0, 1.0);
        System.out.println("Comparing 1.0 inch and 1.0 inch: " + inchResult);

        // Additional checks
        System.out.println("Comparing 1.0 inch and 2.0 inch: " + compareInches(1.0, 2.0));
        System.out.println("Comparing 1.0 ft and 2.0 ft: " + compareFeet(1.0, 2.0));
    }
}
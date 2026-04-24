public class QuantityMeasurementApp {

    // Inner class representing Feet measurement
    static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        // Override equals method
        @Override
        public boolean equals(Object obj) {
            // Same reference check (Reflexive)
            if (this == obj) {
                return true;
            }

            // Null check
            if (obj == null) {
                return false;
            }

            // Type check
            if (getClass() != obj.getClass()) {
                return false;
            }

            // Safe casting
            Feet other = (Feet) obj;

            // Compare using Double.compare()
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Main method
    public static void main(String[] args) {

        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(1.0);
        Feet feet3 = new Feet(2.0);

        System.out.println("Comparing 1.0 ft and 1.0 ft: " + feet1.equals(feet2)); // true
        System.out.println("Comparing 1.0 ft and 2.0 ft: " + feet1.equals(feet3)); // false
        System.out.println("Comparing with null: " + feet1.equals(null)); // false
        System.out.println("Same reference: " + feet1.equals(feet1)); // true
    }
}
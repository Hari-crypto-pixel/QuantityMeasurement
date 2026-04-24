public class QuantityMeasurementApp {

    // Step 1: Enum for Units with conversion to base unit (Feet)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0); // 1 inch = 1/12 feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // Step 2: Generic Quantity Class
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (Feet)
        private double toBaseUnit() {
            return unit.toFeet(value);
        }

        // Equality check
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            // Compare after converting both to same base unit
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }
    }

    // Main method
    public static void main(String[] args) {

        // Same unit comparison
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        // Cross unit comparison
        QuantityLength q3 = new QuantityLength(12.0, LengthUnit.INCH);

        // Different values
        QuantityLength q4 = new QuantityLength(2.0, LengthUnit.FEET);

        System.out.println("1 ft == 1 ft: " + q1.equals(q2));     // true
        System.out.println("1 ft == 12 inch: " + q1.equals(q3));  // true
        System.out.println("1 ft == 2 ft: " + q1.equals(q4));     // false
        System.out.println("Null comparison: " + q1.equals(null)); // false
        System.out.println("Same reference: " + q1.equals(q1));    // true
    }
}
public class QuantityMeasurementApp {

    // Step 1: Enum with all units (base = Feet)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),          // 1 inch = 1/12 feet
        YARD(3.0),                // 1 yard = 3 feet
        CM(0.0328084);            // 1 cm ≈ 0.0328084 feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // Step 2: Generic Quantity Class (UNCHANGED)
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }
    }

    // Main method for testing
    public static void main(String[] args) {

        // Same unit
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        // Cross-unit comparisons
        QuantityLength q3 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength q4 = new QuantityLength(1.0 / 3.0, LengthUnit.YARD);
        QuantityLength q5 = new QuantityLength(30.48, LengthUnit.CM); // 1 ft = 30.48 cm

        // Different value
        QuantityLength q6 = new QuantityLength(2.0, LengthUnit.FEET);

        System.out.println("1 ft == 1 ft: " + q1.equals(q2));        // true
        System.out.println("1 ft == 12 inch: " + q1.equals(q3));     // true
        System.out.println("1 ft == 0.333 yard: " + q1.equals(q4));  // true
        System.out.println("1 ft == 30.48 cm: " + q1.equals(q5));    // true
        System.out.println("1 ft == 2 ft: " + q1.equals(q6));        // false
    }
}
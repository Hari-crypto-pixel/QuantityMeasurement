public class QuantityMeasurementApp {

    // Enum for units with conversion to Feet (base unit)
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    // Immutable Quantity class
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (feet)
        private double toBaseUnit() {
            return unit.toFeet(value);
        }

        // UC7: Addition with explicit target unit
        public static QuantityLength add(QuantityLength a,
                                         QuantityLength b,
                                         LengthUnit targetUnit) {

            if (a == null || b == null || targetUnit == null) {
                throw new IllegalArgumentException("Arguments cannot be null");
            }

            double sumInFeet = a.toBaseUnit() + b.toBaseUnit();
            double result = targetUnit.fromFeet(sumInFeet);

            return new QuantityLength(round(result), targetUnit);
        }

        // Equality check
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }

        // Rounding helper
        private static double round(double value) {
            return Math.round(value * 100.0) / 100.0;
        }
    }

    // Demo method
    public static void main(String[] args) {

        QuantityLength length1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength length2 = new QuantityLength(12.0, LengthUnit.INCHES);

        // UC7: Add with target unit = YARDS
        QuantityLength result1 = QuantityLength.add(length1, length2, LengthUnit.YARDS);
        System.out.println("1 ft + 12 in in YARDS = " + result1);

        // Add with target unit = FEET
        QuantityLength result2 = QuantityLength.add(length1, length2, LengthUnit.FEET);
        System.out.println("1 ft + 12 in in FEET = " + result2);

        // Add with target unit = CENTIMETERS
        QuantityLength result3 = QuantityLength.add(length1, length2, LengthUnit.CENTIMETERS);
        System.out.println("1 ft + 12 in in CM = " + result3);

        // Commutativity check
        QuantityLength result4 = QuantityLength.add(length2, length1, LengthUnit.YARDS);
        System.out.println("12 in + 1 ft in YARDS = " + result4);
    }
}
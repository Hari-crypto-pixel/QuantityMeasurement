public class QuantityMeasurementApp {

    // ===================== ENUM (Standalone Unit System) =====================
    enum LengthUnit {

        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        // Unit → Feet
        public double toBaseUnit(double value) {
            return value * toFeetFactor;
        }

        // Feet → Unit
        public double fromBaseUnit(double baseValue) {
            return baseValue / toFeetFactor;
        }
    }

    // ===================== VALUE OBJECT =====================
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

        // Convert to base unit (Feet)
        private double toBaseUnit() {
            return unit.toBaseUnit(value);
        }

        // ===================== ADDITION (UC6 + UC7) =====================
        public static QuantityLength add(QuantityLength a,
                                         QuantityLength b,
                                         LengthUnit targetUnit) {

            if (a == null || b == null || targetUnit == null) {
                throw new IllegalArgumentException("Null values not allowed");
            }

            double sumInFeet = a.toBaseUnit() + b.toBaseUnit();
            double result = targetUnit.fromBaseUnit(sumInFeet);

            return new QuantityLength(round(result), targetUnit);
        }

        // ===================== EQUALITY (UC1–UC8) =====================
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }

        private static double round(double value) {
            return Math.round(value * 100.0) / 100.0;
        }
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {

        QuantityLength length1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength length2 = new QuantityLength(12.0, LengthUnit.INCHES);

        // Equality check
        System.out.println("Equality (1 ft vs 12 in): " + length1.equals(length2));

        // Addition in different target units
        System.out.println("Sum in FEET: " +
                QuantityLength.add(length1, length2, LengthUnit.FEET));

        System.out.println("Sum in YARDS: " +
                QuantityLength.add(length1, length2, LengthUnit.YARDS));

        System.out.println("Sum in CENTIMETERS: " +
                QuantityLength.add(length1, length2, LengthUnit.CENTIMETERS));

        // Extra checks
        QuantityLength a = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength b = new QuantityLength(36.0, LengthUnit.INCHES);

        System.out.println("1 yard vs 36 inches: " + a.equals(b));
    }
}
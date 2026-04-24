public class QuantityMeasurementApp {

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

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.toFeet(value);
        }

        // -------- UC6: ADD METHOD (Instance) --------
        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Other length cannot be null");
            }

            double sumInFeet = this.toBaseUnit() + other.toBaseUnit();

            double resultInThisUnit = this.unit.fromFeet(sumInFeet);

            return new QuantityLength(round(resultInThisUnit), this.unit);
        }

        // -------- UC6: STATIC ADD (Overloaded) --------
        public static QuantityLength add(double v1, LengthUnit u1,
                                         double v2, LengthUnit u2,
                                         LengthUnit resultUnit) {

            if (!Double.isFinite(v1) || !Double.isFinite(v2)) {
                throw new IllegalArgumentException("Invalid numeric values");
            }
            if (u1 == null || u2 == null || resultUnit == null) {
                throw new IllegalArgumentException("Units cannot be null");
            }

            double sumFeet = u1.toFeet(v1) + u2.toFeet(v2);
            double result = resultUnit.fromFeet(sumFeet);

            return new QuantityLength(round(result), resultUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }

        private static double round(double value) {
            return Math.round(value * 100.0) / 100.0;
        }
    }

    // ----------- DEMO METHODS -----------

    public static void demonstrateAddition(QuantityLength a, QuantityLength b) {
        QuantityLength result = a.add(b);
        System.out.println(a + " + " + b + " = " + result);
    }

    public static void demonstrateAddition(double v1, LengthUnit u1,
                                           double v2, LengthUnit u2,
                                           LengthUnit resultUnit) {
        QuantityLength result = QuantityLength.add(v1, u1, v2, u2, resultUnit);
        System.out.println(v1 + " " + u1 + " + " + v2 + " " + u2 +
                " = " + result);
    }

    // ----------- MAIN METHOD -----------

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        // Instance method (result in unit of first operand)
        demonstrateAddition(q1, q2);   // 1 ft + 12 in = 2 ft

        // Reverse (commutative check)
        demonstrateAddition(q2, q1);   // 12 in + 1 ft = 24 in

        // Static method (custom result unit)
        demonstrateAddition(1.0, LengthUnit.FEET,
                1.0, LengthUnit.YARDS,
                LengthUnit.FEET);

        // Mixed units
        demonstrateAddition(30.48, LengthUnit.CENTIMETERS,
                12.0, LengthUnit.INCHES,
                LengthUnit.FEET);
    }
}

class public class QuantityMeasurementApp {

    /**
     * Enum representing length units with conversion factor to base unit (Feet)
     */
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

    /**
     * Immutable value object for length
     */
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
            return unit.toFeet(value);
        }

        /**
         * Instance method for conversion
         */
        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double baseValue = this.toBaseUnit();
            double convertedValue = targetUnit.fromFeet(baseValue);

            return new QuantityLength(round(convertedValue), targetUnit);
        }

        /**
         * Static utility conversion method
         */
        public static double convert(double value, LengthUnit from, LengthUnit to) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            if (from == null || to == null) {
                throw new IllegalArgumentException("Units cannot be null");
            }

            double base = from.toFeet(value);
            return round(to.fromFeet(base));
        }

        // Equality check (same as UC3/UC4)
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        // Readable output
        @Override
        public String toString() {
            return value + " " + unit;
        }

        // Private helper for rounding
        private static double round(double value) {
            return Math.round(value * 100.0) / 100.0; // 2 decimal places
        }
    }

    // ----------- API Methods ------------

    // Method Overloading #1
    public static void demonstrateLengthConversion(double value,
                                                   LengthUnit from,
                                                   LengthUnit to) {
        double result = QuantityLength.convert(value, from, to);
        System.out.println(value + " " + from + " = " + result + " " + to);
    }

    // Method Overloading #2
    public static void demonstrateLengthConversion(QuantityLength length,
                                                   LengthUnit to) {
        QuantityLength converted = length.convertTo(to);
        System.out.println(length + " = " + converted);
    }

    public static void demonstrateLengthEquality(QuantityLength a,
                                                 QuantityLength b) {
        System.out.println(a + " == " + b + " : " + a.equals(b));
    }

    public static void demonstrateLengthComparison(double v1, LengthUnit u1,
                                                   double v2, LengthUnit u2) {
        QuantityLength q1 = new QuantityLength(v1, u1);
        QuantityLength q2 = new QuantityLength(v2, u2);
        demonstrateLengthEquality(q1, q2);
    }

    // ----------- Main Method ------------

    public static void main(String[] args) {

        // Direct conversion
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(2.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(30.48, LengthUnit.CENTIMETERS, LengthUnit.FEET);

        // Using object conversion
        QuantityLength length = new QuantityLength(3.0, LengthUnit.YARDS);
        demonstrateLengthConversion(length, LengthUnit.INCHES);

        // Equality checks
        demonstrateLengthComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);
        demonstrateLengthComparison(1.0, LengthUnit.FEET, 30.48, LengthUnit.CENTIMETERS);
    }
}
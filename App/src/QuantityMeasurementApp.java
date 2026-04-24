classpublic class QuantityMeasurementApp {

    // =========================================================
    // LENGTH UNIT SYSTEM (UC1–UC8 preserved)
    // =========================================================
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toBaseUnit(double value) {
            return value * toFeetFactor;
        }

        public double fromBaseUnit(double baseValue) {
            return baseValue / toFeetFactor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid length value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Length unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.toBaseUnit(value);
        }

        public static QuantityLength add(QuantityLength a,
                                         QuantityLength b,
                                         LengthUnit targetUnit) {

            double sum = a.toBaseUnit() + b.toBaseUnit();
            double result = targetUnit.fromBaseUnit(sum);

            return new QuantityLength(round(result), targetUnit);
        }

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

        private static double round(double v) {
            return Math.round(v * 100.0) / 100.0;
        }
    }

    // =========================================================
    // WEIGHT UNIT SYSTEM (UC9 NEW FEATURE)
    // =========================================================
    enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double toKgFactor;

        WeightUnit(double toKgFactor) {
            this.toKgFactor = toKgFactor;
        }

        public double toBaseUnit(double value) {
            return value * toKgFactor;
        }

        public double fromBaseUnit(double baseValue) {
            return baseValue / toKgFactor;
        }
    }

    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid weight value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Weight unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.toBaseUnit(value);
        }

        // ===================== CONVERSION =====================
        public QuantityWeight convertTo(WeightUnit targetUnit) {
            double base = toBaseUnit();
            double result = targetUnit.fromBaseUnit(base);
            return new QuantityWeight(round(result), targetUnit);
        }

        // ===================== ADD (default unit) =====================
        public static QuantityWeight add(QuantityWeight a, QuantityWeight b) {
            return add(a, b, a.unit);
        }

        // ===================== ADD (explicit target unit) =====================
        public static QuantityWeight add(QuantityWeight a,
                                         QuantityWeight b,
                                         WeightUnit targetUnit) {

            double sum = a.toBaseUnit() + b.toBaseUnit();
            double result = targetUnit.fromBaseUnit(sum);

            return new QuantityWeight(round(result), targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityWeight)) return false;

            QuantityWeight other = (QuantityWeight) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }

        private static double round(double v) {
            return Math.round(v * 100.0) / 100.0;
        }
    }

    // =========================================================
    // MAIN METHOD (TEST ALL UC9 FEATURES)
    // =========================================================
    public static void main(String[] args) {

        // ---------------- LENGTH TEST ----------------
        QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Length Equal: " + l1.equals(l2));

        System.out.println("Length Add (FEET): " +
                QuantityLength.add(l1, l2, LengthUnit.FEET));

        // ---------------- WEIGHT TEST ----------------
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Weight Equal: " + w1.equals(w2));

        System.out.println("Convert KG → POUND: " +
                w1.convertTo(WeightUnit.POUND));

        System.out.println("Add KG + GRAM (KG): " +
                QuantityWeight.add(w1, w2));

        System.out.println("Add KG + POUND (GRAM): " +
                QuantityWeight.add(w1, w2, WeightUnit.GRAM));

        // ---------------- CROSS CATEGORY SAFETY ----------------
        System.out.println("Length != Weight: " + l1.equals(w1));
    }
}
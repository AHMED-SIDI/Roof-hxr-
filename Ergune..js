public class Ergun {
    public static double calculatePressureDrop(double pressureInlet, double kinematicViscosity, double densityGas,
                                              double sizeLaminarFactor, double laminarFactorCorrection,
                                              double turbulentFactorCorrection, double voidFraction, double flowRate,
                                              double particlDiameter, double sizeTurbulentFactor) {

        double A = laminarFactorCorrection * 150 * Math.pow((1 - voidFraction), 2) /
                   (Math.pow(voidFraction, 3) * Math.pow(particlDiameter, 2));
        double B = turbulentFactorCorrection * 1.750 *(1 - voidFraction) /
                   (Math.pow(voidFraction, 3) *particlDiameter);

        double pressureDrop = sizeLaminarFactor * A * kinematicViscosity * flowRate +
                              sizeTurbulentFactor * B * densityGas * Math.pow(flowRate, 2);

        double pressureOutlet = pressureInlet - pressureDrop;

        return pressureOutlet;
    }

    public static void main(String[] args) {
        double pressureInlet = 166500;
        double kinematicViscosity = 0.00001;
        double densityGas = 1500;
        double sizeLaminarFactor = 0.55;
        double sizeTurbulentFactor = 0.36;
        double particlDiameter = 25;
        double voidFraction = 0.625;
        double flowRate = 10;
        double laminarFactorCorrection = 0.23625;
        double turbulentFactorCorrection = 0.950;

        double result = calculatePressureDrop(pressureInlet, kinematicViscosity, densityGas, sizeLaminarFactor,
                laminarFactorCorrection, turbulentFactorCorrection, voidFraction, flowRate, particlDiameter,
                sizeTurbulentFactor);

        System.out.println("The pressure drop is: " + result);
    }
}



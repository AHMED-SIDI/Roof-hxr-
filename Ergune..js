public class Ergun {
    public static double calculatePressureLoss(double pressureInlet, double kinematicViscosity, double gasDensity,
                                              double ergunLaminarGeometrieCoeff, double ergunLaminarCorrectionCoeff,
                                              double ergunTurbulentCorrectionCoeff, double porisity, double flowRate,
                                              double particlDiameter, double ergunTurbulentGeometrieCoeff) {

        double A = ergunLaminarCorrectionCoeff * 150 * Math.pow((1 - porisity), 2) /
                   (Math.pow(porisity, 3) * Math.pow(particlDiameter, 2));
        double B = ergunTurbulentCorrectionCoeff * 1.750 *(1 - porisity) /
                   (Math.pow(porisity, 3) *particlDiameter);

        double pressureLoss = ergunLaminarGeometrieCoeff * A * kinematicViscosity * flowRate +
                              ergunTurbulentGeometrieCoeff * B * gasDensity * Math.pow(flowRate, 2);

        double pressureOutlet = pressureInlet - pressureLoss;

        return pressureOutlet;
    }

    public static void main(String[] args) {
        double pressureInlet = 166500;
        double kinematicViscosity = 0.00001;
        double gasDensity = 1500;
        double ergunLaminarGeometrieCoeff = 0.55;
        double ergunTurbulentGeometrieCoeff = 0.36;
        double particlDiameter = 25;
        double porisity = 0.625;
        double flowRate = 10;
        double ergunLaminarCorrectionCoeff = 0.23625;
        double ergunTurbulentCorrectionCoeff = 0.950;

        double result = calculatePressureLoss(pressureInlet, kinematicViscosity, gasDensity, ergunLaminarGeometrieCoeff,
                ergunLaminarCorrectionCoeff, ergunTurbulentCorrectionCoeff, porisity, flowRate, particlDiameter,
                ergunTurbulentGeometrieCoeff);

        System.out.println("The pressure drop is: " + result);
    }
}

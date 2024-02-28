import java.util.Arrays;

public class RoofInletLoss {

    public static double calculatePressureDrop(float pressure, float temperature, float flowRateN, float pressureDropAllowed,float sheetThickness ,float holesDiameter, float numberHoles) {                                            
                                                

        double densityGas0 = 1.27;
        double densityGas = 0.0285 * pressure / (8.314 * temperature);
        double speedSound = Math.sqrt(1.4 * 8.314 * temperature / 0.0285);
        double flowRate = flowRateN * densityGas0 / densityGas;
        double flowRateF = flowRate / 60;
        double crossAreas = 1.38;
        double speedPerforatedSheet = flowRateF / crossAreas;

        double freeSpace = numberHoles * Math.PI * 0.25 * Math.pow(holesDiameter * 0.001, 2);
        double speed = flowRateF / freeSpace;
        double reynoldsNumber = (speed * holesDiameter * 0.001) / (1.7 * 0.00001);

        double areaRatioGrid = freeSpace / crossAreas;

        double index = sheetThickness / holesDiameter;

        double[] indexList = {0, 0.2, 0.4, 0.6, 0.8, 1, 1.4, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] tau = {1.35, 1.22, 1.1, 0.84, 0.42, 0.24, 0.1, 0.02, 0, 0, 0, 0, 0, 0, 0, 0};

        double y_prediction = 0;
        for (int i = 0; i < indexList.length; i++) {
            if (index == indexList[i]) {
                y_prediction = tau[i];
            }
            if (indexList[i] < index && indexList[i + 1] > index) {
                y_prediction = tau[i] + ((index - indexList[i]) * (tau[i + 1] - tau[i])) / (indexList[i + 1] - indexList[i]);
            }
        }

        double[] areaRatioList = {0, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.95};
        double[] reynoldsList = {25, 40, 60, 100, 200, 400, 1000, 2000, 4000, 10000, 20000, 100000, 200000, 1000000};
        double[] epsilonReynoldsList = {0.34, 0.36, 0.37, 0.4, 0.42, 0.46, 0.53, 0.59, 0.64, 0.74, 0.81, 0.94, 0.95, 0.98};

        double[][] zetaPhiMatrix = {
                {1.94, 1.38, 1.14, 0.89, 0.59, 0.64, 0.39, 0.3, 0.22, 0.15, 0.11, 0.04, 0.01, 0},
                {1.78, 1.36, 1.05, 0.85, 0.67, 0.57, 0.36, 0.26, 0.2, 0.13, 0.09, 0.03, 0.01, 0},
                {1.57, 1.16, 0.88, 0.75, 0.57, 0.43, 0.3, 0.22, 0.17, 0.1, 0.07, 0.02, 0.01, 0},
                {1.35, 0.99, 0.79, 0.57, 0.4, 0.28, 0.19, 0.14, 0.1, 0.06, 0.04, 0.02, 0.01, 0},
                {1.1, 0.75, 0.55, 0.34, 0.19, 0.12, 0.07, 0.05, 0.03, 0.02, 0.01, 0.01, 0},
                {0.85, 0.56, 0.3, 0.19, 0.1, 0.06, 0.03, 0.02, 0.01, 0, 0, 0, 0},
                {0.58, 0.37, 0.23, 0.11, 0.06, 0.03, 0.02, 0.01, 0, 0, 0, 0, 0, 0},
                {0.4, 0.24, 0.13, 0.06, 0.03, 0.02, 0.01, 0, 0, 0, 0, 0, 0, 0},
                {0.2, 0.13, 0.08, 0.03, 0.01, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0.03, 0.03, 0.02, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

        double  X1 = 0, X2 = 0, Y1 = 0, Y2 = 0;
        int a1 = 0, b1 = 0, a2 = 0, b2 = 0, a = 0, b = 0;
        for (int i = 0; i < areaRatioList.length - 1; i++) {
            if (areaRatioGrid == areaRatioList[i]) {
                a = i;
            } else {
                if (areaRatioList[i] < areaRatioGrid && areaRatioList[i + 1] > areaRatioGrid) {
                    X1 = areaRatioList[i];
                    X2 = areaRatioList[i + 1];
                    a1 = i;
                    b1 = i + 1;
                } else {
                    X1 = 0.0001;
                    X2 = 1;
                }
            }
        }

        double eps1 = 0, eps2 = 0;

        for (int i = 0; i < reynoldsList.length - 1; i++) {
            if (reynoldsNumber == reynoldsList[i]) {
                b = i;
            } else {
                if (reynoldsList[i] < reynoldsNumber && reynoldsList[i + 1] > reynoldsNumber) {
                    Y1 = reynoldsList[i];
                    Y2 = reynoldsList[i + 1];
                    a2 = i;
                    b2 = i + 1;
                    eps1 = epsilonReynoldsList[i];
                    eps2 = epsilonReynoldsList[i + 1];
                } else {
                    Y1 = 10;
                    Y2 = 10000000;
                }
            }
        }
        
        double z11 = zetaPhiMatrix[a1][a2];
        double z12 = zetaPhiMatrix[a1][b2];
        double z21 = zetaPhiMatrix[b1][a2];
        double z22 = zetaPhiMatrix[b1][b2];

        double Z_phi = 1 / ((X2 - X1) * (Y2 - Y1)) * (z11 * (X2 - areaRatioGrid) * (Y2 - reynoldsNumber)
                + z21 * (areaRatioGrid - X1) * (Y2 - reynoldsNumber)
                + z12 * (X2 - areaRatioGrid) * (reynoldsNumber - Y1)
                + z22 * (areaRatioGrid - X1) * (reynoldsNumber - Y1));

        double epsilonCorrect = eps1 + (eps2 - eps1) / ((Y2 - Y1) * (index - Y1));
        double lambda = 0.3164 / Math.pow(reynoldsNumber, 0.25);

        double ZetaSharpTurbulent = Math.pow(((1 + 0.707 * Math.sqrt(1 - areaRatioGrid) - areaRatioGrid) / areaRatioGrid), 2);
        double ZetaSharpTransitional = (Z_phi + epsilonCorrect * Math.pow((1 + 0.707 * Math.sqrt(1 - areaRatioGrid) - areaRatioGrid), 2)) / Math.pow(areaRatioGrid, 2);
        double ZetaThickTurbulent = (0.5 * (1 - areaRatioGrid) + y_prediction * Math.pow((1 - areaRatioGrid), 1.5) + Math.pow((1 - areaRatioGrid), 2) + lambda * index) / Math.pow(areaRatioGrid, 2);
        double ZetaThickTransitional = (Z_phi + epsilonCorrect * (0.5 * (1 - areaRatioGrid) + y_prediction * Math.pow((1 - areaRatioGrid), 1.5) + Math.pow((1 - areaRatioGrid), 2) + lambda * index)) / Math.pow(areaRatioGrid, 2);

        double compressibilityFactor = 0;
        double machNumber = speed / speedSound;
        double Z = 0;

        if (machNumber < 0.3) {
            compressibilityFactor = 1;
        } else {
            compressibilityFactor = 35.252 * Math.pow(machNumber, 4) - 28.883 * Math.pow(machNumber, 3) + 8.1252 * Math.pow(machNumber, 2) - 0.6808 * machNumber + 1;
        }

        if (index < 0.015) {
            if (reynoldsNumber < 100000) {
                Z = ZetaSharpTransitional * compressibilityFactor;
            } else {
                Z = ZetaSharpTurbulent * compressibilityFactor;
            }
        } else {
            if (reynoldsNumber < 100000) {
                Z = ZetaThickTransitional * compressibilityFactor;
            } else {
                Z = ZetaThickTurbulent * compressibilityFactor;
            }
        }

        double pressureDropAllowedCalculated = 0.5 * Z * densityGas * Math.pow(speedPerforatedSheet, 2) / 100;
        double pressureDifference = pressureDropAllowed - pressureDropAllowedCalculated;

        double result = 0.0 ;
        
        return pressureDifference;
    }

    public static void main(String[] args) {
       

        double result = calculatePressureDrop(166500.0f, 333.0f, 118.50f, 5.0f, 16.0f, 25.0f, 165.0f);
        System.out.println("The pressure drop is: " + result);
    }
}
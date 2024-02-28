
function calculculatPressureDrop(pressureInlet, kinematicViscosity, densityGas, sizeLaminarFactor,laminarFactorCorrection,turbulentFactorCorrection,voidFraction,flowRate, particulDiametre, sizeturbulentFactor ) {
     
    const A = laminarFactorCorrection*150*(1-voidFraction)**2/((voidFraction**3)*particulDiametre**2);
    const B = turbulentFactorCorrection*150*(1-voidFraction)**2/((voidFraction**3)*particulDiametre**2);


    const pressureDrop = sizeLaminarFactor*A*kinematicViscosity*flowRate + sizeturbulentFactor*B*densityGas*flowRate**2 ;

    pressureOutlet =  pressureInlet - pressureDrop ;
    
    
    return pressureOutlet;
} 
  const pressureInlet = 166500;
  const kinematicViscosity = 0.00001;
  const densityGas = 1500;
  const sizeLaminarFactor = 0.55;
  const sizeturbulentFactor = 0.36;
  const particulDiametre = 25;
  const voidFraction = 0.625;
  const flowRate = 10;
  const laminarFactorCorrection =0.23625;
  const turbulentFactorCorrection = 0.950;

  const result = calcul_DeltaP(pressureInlet, kinematicViscosity, densityGas, sizeLaminarFactor,laminarFactorCorrection,turbulentFactorCorrection, voidFraction,flowRate, particulDiametre, sizeturbulentFactor);
  console.log('the pressor drop is : ', result);
  



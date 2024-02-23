function calcul_DeltaP(P_1, mu, rho, alpha_12,f1,f2, eps,V12, d, beta_12) {
     
    const A = f1*150*(1-eps)**2/((eps**3)*d**2);
    const B = f2*150*(1-eps)**2/((eps**3)*d**2);


    const DeltaP = alpha_12*A*mu*V12 + beta_12*B*rho*V12**2 ;

    P_2 =  P_1 - DeltaP ;
    
    
    return P_2;
} 
  const P_1 = 166500;
  const mu = 0.00001;
  const rho = 1500;
  const alpha_12 = 0.55;
  const beta_12 = 0.36;
  const d = 25;
  const eps = 0.625;
  const V12 = 10;
  const f1 =0.23625;
  const f2 = 0.950;

  const result = calcul_DeltaP(P_1, mu, rho, alpha_12,f1,f2, eps,V12, d, beta_12);
  console.log('the pressor drop is : ', result);
  



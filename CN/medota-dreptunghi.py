import matplotlib.pyplot as plt
import numpy as np 
from scipy.integrate import quadrature


def midpoint(f,a,b,n):
    h=(b-a)/n
    x=np.linspace(a,b,n+1)
    int_approx=0
    x1=np.linspace(a,b,100)
    plt.plot(x1,f(x1))
    for i in range(n):
        f_mid=f((x[i]+x[i+1])/2)
        xval=[x[i],x[i+1],x[i+1],x[i]]
        yval=[0,0,f_mid,f_mid]
        plt.fill(xval,yval,color=(0.7, 0.2, 0.5, 0.5))
        int_approx=f_mid
        
    int_approx=h*int_approx
    return int_approx
    print(int_approx)

def roof(x):
    return np.sqrt(1+np.cos(x)**2)

int_approx=midpoint(roof,0,20,30)
int_exact=quadrature(roof,0,20)
er_abs=abs(int_approx-int_exact[0])
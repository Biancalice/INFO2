from sympy import Symbol, diff, lambdify
import numpy as np
from scipy.optimize import fsolve
from time import perf_counter
def contractii_er(g,a,b,er,x0=None):
    x=Symbol('x')
    f=g+'-x'
    f=lambdify(x,f)
    if x0==None:
        x0=(a+b)/2
    r_ex=fsolve(f, x0)
    g=lambdify(x,g)
    x=x0
    while abs(r_ex-x)>er:
        x=g(x)
    return x



gs='sqrt(10/(4+x))'
a=1
b=2
t0_co=perf_counter()
r_aprox=contractii_er(gs,a,b,10**-3)
print(r_aprox)         
t1_co=perf_counter()        
t_contractii=t1_co-t0_co

